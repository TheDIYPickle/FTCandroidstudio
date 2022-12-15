package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.RealToggle;
import org.firstinspires.ftc.teamcode.ToggleMap;
import org.firstinspires.ftc.teamcode.UseMap;

@TeleOp(name="OmniWheelTeleOpRedAndroid")
public class OmniWheelTeleOpRed extends LinearOpMode {
    ToggleMap ToggleMap1 = new ToggleMap();
    UseMap UseMap1 = new UseMap();
    ToggleMap ToggleMap2 = new ToggleMap();
    UseMap UseMap2 = new UseMap();
    RealToggle toggle = new RealToggle(false);
    RealToggle upToggle = new RealToggle(false);
    RealToggle downToggle = new RealToggle(false);
    RealToggle capToggle = new RealToggle(false);
    RealToggle clawToggle = new RealToggle(false);
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor backleftDrive = null;
    private DcMotor backrightDrive = null;
    private DcMotor frontleftDrive = null;
    private DcMotor frontrightDrive = null;
    //private DcMotor Potato = null;
    private DcMotor spinnySpin = null;
    private CRServo claw = null;
    private DcMotor arm = null;
    //private CRServo lilPotato= null;
    private CRServo boxcar = null;
    private CRServo dropper = null;

    @Override
    public void runOpMode() {
        backleftDrive = hardwareMap.get(DcMotor.class, "backleftDrive");
        backrightDrive = hardwareMap.get(DcMotor.class, "backrightDrive");
        frontleftDrive = hardwareMap.get(DcMotor.class, "frontleftDrive");
        frontrightDrive = hardwareMap.get(DcMotor.class, "frontrightDrive");
        //Potato = hardwareMap.get(DcMotor.class, "Potato");
        spinnySpin = hardwareMap.get(DcMotor.class, "spinnySpin");
        claw = hardwareMap.get(CRServo.class, "claw");
        arm = hardwareMap.get(DcMotor.class, "arm");
        //lilPotato = hardwareMap.crservo.get("lilPotato");
        boxcar = hardwareMap.crservo.get("boxcar");
        dropper = hardwareMap.crservo.get("dropper");

        backleftDrive.setDirection(DcMotor.Direction.FORWARD);
        backrightDrive.setDirection(DcMotor.Direction.REVERSE);
        frontleftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontrightDrive.setDirection(DcMotor.Direction.FORWARD);
        //reset the encoders at the beginning
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


//set the motors to a useable mode
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //claw.setMode(CRServo.RunMode.RUN_WITHOUT_ENCODER);




        telemetry.addData("Status", "Initialized");
        telemetry.update();

        float tgtpower1;
        float lfPower;
        float rtPower;
        boolean extended = false;
        boolean up = false;
        boolean G1a = gamepad1.a;
        boolean G1dpad_up = gamepad1.dpad_up;
        boolean G1dpad_down = gamepad1.dpad_down;
        boolean G1x = gamepad1.x;
        double push = 0;
        double pull = 0;
        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {


            /*int rightEncoders = 2000;
            int armTarget = arm.getCurrentPosition() + rightEncoders;
            arm.setTargetPosition(armTarget);*/
            double potatoPower = 1;
            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x/2;
            double backleftPower = Range.clip(drive + turn, -1.0, 1.0);
            double backrightPower = Range.clip(drive - turn, -1.0, 1.0);
            double frontleftPower = Range.clip(drive + turn, -1.0, 1.0);
            double frontrightPower = Range.clip(drive - turn, -1.0, 1.0);

            //arm.setTargetPosition(armTarget);
            //toggle.update(gamepad1.a);
            //upToggle.update(gamepad1.dpad_up);
            //downToggle.update(gamepad1.dpad_down);
            //capToggle.update(gamepad1.x);
            clawToggle.update(gamepad1.y);


            backrightDrive.setPower(backrightPower);
            backleftDrive.setPower(backleftPower);
            frontrightDrive.setPower(frontrightPower);
            frontleftDrive.setPower(frontleftPower);

            //telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", backleftPower, backrightPower, frontleftPower, frontrightPower);

            //grip claw
            if (gamepad1.a) {
                //Do when toggle is on
                telemetry.addData("Status","close claw");
                telemetry.update();
                claw.setPower(-0.5);
                //sleep(1000);
                //claw.setPower(0);




                //release claw
            }else if (gamepad1.b) {
                //Do when toggle is on
                telemetry.addData("Status","open claw");
                telemetry.update();
                claw.setPower(1);
                sleep(250);
                claw.setPower(0);




            } /*else if (!gamepad1.b && !gamepad1.a) {
                //Do when toggle is off

                //Potato.setPower(0);
                claw.setPower(0);


            }*/
            //capstone code
            if (gamepad1.x) {
                //Do when toggle is on
                telemetry.addData("Status", "You spin my head round");
                telemetry.update();
                spinnySpin.setPower(-0.35);
                telemetry.addData("Status", "Ligma");
                telemetry.update();
                //extended = true;


            }
            else if (!gamepad1.x) {
                //Do when toggle is on

                spinnySpin.setPower(0);

                //extended = true;


            }

            if(gamepad1.dpad_up) {
                //Do when toggle is on
                telemetry.addData("Status","Up");
                telemetry.update();
                if (gamepad1.dpad_up) {
                    int rightEncoders = 1000;
                    int armTarget = arm.getCurrentPosition() + rightEncoders;
                    arm.setTargetPosition(armTarget);
                }else if(gamepad1.dpad_down){
                    int rightEncoders =-1000;
                    int armTarget = arm.getCurrentPosition() + rightEncoders;
                    arm.setTargetPosition(armTarget);
                }
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                arm.setPower(0.4);

                while (gamepad1.dpad_up && arm.isBusy()) {}
                arm.setPower(0);

                //int armPos = arm.getCurrentPosition();
                //arm.setTargetPosition(5000);




            }
            else if(!gamepad1.dpad_up) {
                //Do when toggle is on

                arm.setPower(0);

                //int armPos = arm.getCurrentPosition();
                //arm.setTargetPosition(armPos);



            }
            if(gamepad1.dpad_down){
                //Do when toggle is off
                telemetry.addData("Status", "Down");
                telemetry.update();
                if (gamepad1.dpad_up) {
                    int rightEncoders = 1000;
                    int armTarget = arm.getCurrentPosition() + rightEncoders;
                    arm.setTargetPosition(armTarget);
                }else if(gamepad1.dpad_down){
                    int rightEncoders =-1000;
                    int armTarget = arm.getCurrentPosition() + rightEncoders;
                    arm.setTargetPosition(armTarget);
                }
                //arm.setPower(-1);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                arm.setPower(-0.4);

                while (gamepad1.dpad_down && arm.isBusy()) {}
                arm.setPower(0);
                //arm.setTargetPosition(-2);

                // 2022 lets see if you find this;

            }
            else if(!gamepad1.dpad_down) {
                //Do when toggle is on

                arm.setPower(0);
                //int armPos = arm.getCurrentPosition();
                //arm.setTargetPosition(armPos);
                //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


            }
           // claw control
            if(gamepad1.dpad_right) {
                //Do when toggle is on
                boxcar.setPower(1);
                telemetry.addData("Status","out");
                telemetry.update();
                sleep(1000);
                boxcar.setPower(0);




            }
            else if(gamepad1.dpad_left){
                //Do when toggle is off

                boxcar.setPower(-1);
                telemetry.addData("Status", "in");
                telemetry.update();
                sleep(250);
                boxcar.setPower(0);





            }
            // payload dump
            if(gamepad1.y){
                telemetry.addData("Status","Intiating drop");
                telemetry.update();
                dropper.setPower(1);
                sleep(1500);
                dropper.setPower(-1);
                sleep(200);
                dropper.setPower(1);
                sleep (200);
                dropper.setPower(-1);
                sleep(200);
                dropper.setPower(1);
                sleep (200);
                dropper.setPower(-1);
                sleep(700);
                dropper.setPower(0);

            }
            
        }
        
    }

}

