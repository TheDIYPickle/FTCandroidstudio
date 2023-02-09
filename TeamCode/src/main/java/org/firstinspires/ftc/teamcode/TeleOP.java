package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Toggle;

@TeleOp(name="teleop")
public class TeleOP extends LinearOpMode {

    Robot rob;

    public double angle;

    public Toggle verticalClawToggle = new Toggle(false);
    public Toggle horizontalClawToggle = new Toggle(false);
    public double[] verticalClawPositions = new double[] {0, 0.37};
    public double[] horizontalClawPositions = new double[] {0, 0.67};

    @Override
    public void runOpMode() throws InterruptedException {

        rob = new Robot(hardwareMap);
        rob.setWheelZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        int lowTarget = 5;
        int highTarget = 4200;

        waitForStart();


        // Please Change this
        double rotatingPos = 36;
        int turretPos=0;
        double turretLoc=0;
        int targetPos=0;
        boolean downTrack = false;


        while (opModeIsActive()) {
            telemetry.addData("direction", rob.imu.getAngularOrientation());

            int rightSlidePos = -rob.horizontalSlide.getCurrentPosition();
            int leftSlidePos = rob.verticalSlide.getCurrentPosition();

            // [0] = lower bound
            // [1] = upper bound
            int[] rightBounds = {5, 4100};

            double[] rotatingBounds = {31/280d, 220/280d, 50/280d};

            boolean isRightUpperBoundReached = (rightSlidePos >= rightBounds[1]);
            boolean isRightLowerBoundReached = (rightSlidePos <= rightBounds[0]);

            telemetry.addData("Vertical Claw Active", verticalClawToggle.state);
            telemetry.addData("Horizontal Claw Active", horizontalClawToggle.state);

            //Gamepad stick power inversed for some reason, easy fix
            //i.e. When R stick pushed up it returned negative values

            //Upper and Lower Bounds
            double horizontalSlidePower = 0;
            if(gamepad1.dpad_up && !isRightUpperBoundReached)
            {
                telemetry.addData("Pos", "1");
                rob.horizontalSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                horizontalSlidePower = -0.7;
            }
            else if(gamepad1.dpad_down && !isRightLowerBoundReached)
            {
                telemetry.addData("Pos", "2");
                rob.horizontalSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                horizontalSlidePower = 0.9;
            }
            else if(!gamepad1.dpad_down && !gamepad1.dpad_up)
            {
                rob.horizontalSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rob.horizontalSlide.setTargetPosition(rob.horizontalSlide.getCurrentPosition());
            }

            //Upper and Lower Bounds
            double verticalSlidePower = 0.75;

            float rotatingDeltaCoefficient = 1f;
            double rotatingDelta = (gamepad2.dpad_right ? 1 : gamepad2.dpad_left ? -1 : 0) * rotatingDeltaCoefficient;
            double trueLeftVal = -gamepad2.left_stick_y;

            if(trueLeftVal>=0.5)
            {
                targetPos=highTarget;
            }
            else if(trueLeftVal<=-0.5)
            {
                targetPos=lowTarget;
            }
            else
            {
                targetPos= rob.verticalSlide.getCurrentPosition();
            }

            if(rob.verticalSlide.getCurrentPosition()<100)
            {
                if (gamepad2.right_trigger < 0.5)
                {
                    turretPos = 0;
                    turretLoc=1;
                }
            }
            else if(rob.verticalSlide.getCurrentPosition()>1050)
            {
                if(turretPos==1 && gamepad2.left_trigger>0.5)
                {
                    if(gamepad2.dpad_right)
                    {
                        turretLoc+=1;
                    }
                    else if(gamepad2.dpad_left)
                    {
                        turretLoc-=1;
                    }
                }

                else if(gamepad2.dpad_right)
                {
                    turretPos=1;

                }
                else if(gamepad2.dpad_left)
                {
                    turretPos=0;
                    turretLoc=0;
                }

                else if(gamepad2.dpad_down)
                {
                    downTrack = true;
                    turretPos=2;
                    turretLoc=0;


                }
            }
            else if(rob.verticalSlide.getCurrentPosition()>=100)
            {
                if (gamepad2.right_trigger > 0.5)
                {
                    turretPos = 2;
                    if(gamepad2.dpad_right)
                    {
                        turretLoc+=1;
                    }
                    else if(gamepad2.dpad_left)
                    {
                        turretLoc-=1;
                    }
                }
                else if (gamepad2.right_trigger < 0.5)
                {
                    turretPos = 0;
                    turretLoc=1;
                }
            }

            rob.verticalSlide.setPower(verticalSlidePower);
            rob.horizontalSlide.setPower(horizontalSlidePower);

            if(rob.verticalSlide.getCurrentPosition()>900 && downTrack){
                rob.verticalSlide.setTargetPosition(900);
            }
            else if(rob.verticalSlide.getCurrentPosition()<=900 && downTrack)
            {
                downTrack=false;
            }
            else {
                rob.verticalSlide.setTargetPosition(targetPos);
            }

            double testNum=rotatingPos/280d;

            telemetry.addData("NUM: ", testNum);
            telemetry.addData("Turret loc: ", turretLoc);
            telemetry.addData("Pos: ", rotatingBounds[turretPos]+(turretLoc/280d));

            rob.rotatingServo.setPosition(rotatingBounds[turretPos]+(turretLoc/280d));

            telemetry.addData("Servo Position", trueLeftVal);
            //Potential arm control

            telemetry.addData("imu Position", rob.imu.getAngularOrientation().firstAngle);
            Vector2D joystickVector = new Vector2D(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            double rx = gamepad1.right_stick_x;

            // pressurising the right trigger slows down the drive train
            if(gamepad1.right_trigger < 0.5)
            {
                telemetry.addData("Status", "trigger off");
                joystickVector.multiply(0.35);
            }
            else
            {
                telemetry.addData("Status", "trigger on");
            }

            rob.move(joystickVector, rx, true);

            //update the toggles
            verticalClawToggle.update(gamepad2.a);
            horizontalClawToggle.update(gamepad1.a);

            //update the servos
            rob.verticalClawServo.setPosition(verticalClawPositions[verticalClawToggle.state ? 1 : 0]);
            rob.horizontalClawServo.setPosition(horizontalClawPositions[horizontalClawToggle.state ? 1 : 0]);

            telemetry.update();
        }
    }


}
