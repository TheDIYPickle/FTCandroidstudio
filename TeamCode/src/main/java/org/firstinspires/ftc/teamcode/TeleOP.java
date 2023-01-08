package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="teleop")
public class TeleOP extends LinearOpMode {

    DcMotor verticalSlide;
    DcMotor horizontalSlide;

    DcMotor backleftDrive;
    DcMotor backrightDrive;
    DcMotor frontleftDrive;
    DcMotor frontrightDrive;

    Servo verticalClawServo;
    Servo horizontalClawServo;
    Servo rotatingServo;

    public BNO055IMU imu;

    public double angle;

    public Toggle verticalClawToggle = new Toggle(false);
    public Toggle horizontalClawToggle = new Toggle(false);
    public double[] verticalClawPositions = new double[] {0, 0.37};
    public double[] horizontalClawPositions = new double[] {0, 0.67};



    @Override
    public void runOpMode() throws InterruptedException {

        // linear slides
        horizontalSlide = hardwareMap.dcMotor.get("hSlide");
        verticalSlide = hardwareMap.dcMotor.get("vSlide");
        int lowTarget = 5;
        int highTarget = 4200;

        //  lv.setDirection(DcMotorSimple.Direction.REVERSE);
        horizontalSlide.setDirection(DcMotorSimple.Direction.REVERSE);

        verticalSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        horizontalSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        horizontalSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        verticalClawServo = hardwareMap.servo.get("verticalClaw");
        horizontalClawServo = hardwareMap.servo.get("intakeClaw");
        rotatingServo = hardwareMap.servo.get("turret");

        backleftDrive = hardwareMap.get(DcMotor.class, "backleftDrive");
        backrightDrive = hardwareMap.get(DcMotor.class, "backrightDrive");
        frontleftDrive = hardwareMap.get(DcMotor.class, "frontleftDrive");
        frontrightDrive = hardwareMap.get(DcMotor.class, "frontrightDrive");

        backleftDrive.setDirection(DcMotor.Direction.REVERSE);
        backrightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontleftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontrightDrive.setDirection(DcMotor.Direction.FORWARD);

        backleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        imu = hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;

        imu.initialize(parameters);



        waitForStart();

        horizontalSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        verticalSlide.setTargetPosition(lowTarget);
        verticalSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Please Change this
        double rotatingPos = 36;
        int turretPos=0;

        while (opModeIsActive()) {

            int rightSlidePos = -horizontalSlide.getCurrentPosition();
            int leftSlidePos = verticalSlide.getCurrentPosition();

            // [0] = lower bound
            // [1] = upper bound
            int[] rightBounds = {5, 4200};
            int[] leftBounds = {5, 4200};
            double[] rotatingBounds = {25/280d, 200/280d, 50/280d};
            int targetPos=0;


            boolean isRightUpperBoundReached = (rightSlidePos >= rightBounds[1]);
            boolean isRightLowerBoundReached = (rightSlidePos <= rightBounds[0]);
            boolean isLeftUpperBoundReached = (leftSlidePos >= leftBounds[1]);
            boolean isLeftLowerBoundReached = (leftSlidePos <= leftBounds[0]);
            // Please change this but it already works
//            boolean isRotatingUpperBounds = (True);
//            boolean isRotatingLowerBounds = (True);

            telemetry.addData("Vertical Claw Active", verticalClawToggle.state);
            telemetry.addData("Horizontal Claw Active", horizontalClawToggle.state);

            //Gamepad stick power inversed for some reason, easy fix
            //i.e. When R stick pushed up it returned negative values

            //Upper and Lower Bounds
            double horizontalSlidePower = -gamepad2.right_stick_y;
            if(isRightUpperBoundReached){
                horizontalSlidePower = Math.min(0, horizontalSlidePower);
            } else if(isRightLowerBoundReached){
                horizontalSlidePower = Math.max(0, horizontalSlidePower);
            }

            //Upper and Lower Bounds
            double verticalSlidePower = 0.5;
            //double horizontalSlidePower = 0;

            /*if(isLeftUpperBoundReached){
                verticalSlidePower = Math.min(0, verticalSlidePower);
            } else if(isLeftLowerBoundReached){
                verticalSlidePower = Math.max(0, verticalSlidePower);
            }*/

            float rotatingDeltaCoefficient = 1f;
            double rotatingDelta = (gamepad2.dpad_right ? 1 : gamepad2.dpad_left ? -1 : 0) * rotatingDeltaCoefficient;

            if (rotatingPos <= rotatingBounds[0]) {
                rotatingPos = rotatingBounds[0];
            }

            if(verticalSlide.getCurrentPosition()<100 && verticalSlide.getCurrentPosition()<=1050)
            {
                if (gamepad2.right_trigger < 0.5)
                {
                    turretPos = 0;
                }
            }
            else if(verticalSlide.getCurrentPosition()>=100 && verticalSlide.getCurrentPosition()<=1050)
            {
                if (gamepad2.right_trigger > 0.5)
                {
                    turretPos = 2;
                }
                else if (gamepad2.right_trigger < 0.5)
                {
                    turretPos = 0;
                }
            }
            else if(verticalSlide.getCurrentPosition()>1050)
            {

                if(gamepad2.dpad_right)
                {
                    turretPos=1;
                }
                else if(gamepad2.dpad_left)
                {
                    turretPos=0;
                }

            }




            double testNum=rotatingPos/280d;

            telemetry.addData("NUM: ", testNum);

            rotatingServo.setPosition(rotatingBounds[turretPos]);

            telemetry.addData("Servo Position", rotatingPos);
            //Potential arm control
            if(gamepad2.dpad_up)
            {
                targetPos=highTarget;
            }
            else if(gamepad2.dpad_down)
            {
                targetPos=lowTarget;
            }
            else if(!gamepad2.dpad_down && !gamepad2.dpad_up)
            {
                targetPos= verticalSlide.getCurrentPosition();
            }


            verticalSlide.setPower(verticalSlidePower);
            horizontalSlide.setPower(-horizontalSlidePower);

            verticalSlide.setTargetPosition(targetPos);


            angle = imu.getAngularOrientation().firstAngle;
            telemetry.addData("current Encoder value:",verticalSlide.getCurrentPosition());

            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;

            // rotates the left stick and changes the x & y values accordingly (field centric)
            Vector2D inputVector = new Vector2D(x, y);
            Vector2D rotatedVector = inputVector.rotateVector(-angle);

            x = rotatedVector.x;
            y = rotatedVector.y;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.1);
            double frontleftPower = (y + x + rx) / denominator;
            double backleftPower = (y - x + rx) / denominator;
            double frontrightPower = (y - x - rx) / denominator;
            double backrightPower = (y + x - rx) / denominator;

            // pressurising the right trigger slows down the drive train
            double coefficient = 1 - (gamepad1.right_trigger * 0.75);

            telemetry.addData("Front Left Power", frontleftPower*coefficient);
            backrightDrive.setPower(backrightPower * coefficient);
            backleftDrive.setPower(backleftPower * coefficient);
            frontrightDrive.setPower(frontrightPower * coefficient);
            frontleftDrive.setPower(frontleftPower * coefficient);


            //update the toggles
            //verticalClawToggle.update(gamepad2.a);
            //horizontalClawToggle.update(gamepad2.b);
            if(gamepad2.a)
            {
                verticalClawServo.setPosition(verticalClawPositions[1]);
            }
            else if(gamepad2.b)
            {
                verticalClawServo.setPosition(verticalClawPositions[0]);
            }

            if(gamepad2.x)
            {
                horizontalClawServo.setPosition(horizontalClawPositions[1]);
            }
            else if(gamepad2.y)
            {
                horizontalClawServo.setPosition(horizontalClawPositions[0]);
            }



            //update the servos
            //verticalClawServo.setPosition(verticalClawPositions[verticalClawToggle.state ? 1 : 0]);
            //horizontalClawServo.setPosition(horizontalClawPositions[horizontalClawToggle.state ? 1 : 0]);

            telemetry.update();
        }
    }


}
