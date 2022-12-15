package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RealToggle;
import org.firstinspires.ftc.teamcode.ToggleMap;
import org.firstinspires.ftc.teamcode.UseMap;

@TeleOp(name="MecanumWheelTeleOpRedAndroid")
public class MecanumWheelTeleOpRed2 extends LinearOpMode {
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
    DcMotor led;
    public Gamepad g1;
    public Gamepad g2;

    private boolean tFlag = false;
    private boolean tGrip = false;
    private boolean tState = false;
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
        led = hardwareMap.get(DcMotor.class, "led");



        backleftDrive.setDirection(DcMotor.Direction.FORWARD);
        backrightDrive.setDirection(DcMotor.Direction.REVERSE);
        frontleftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontrightDrive.setDirection(DcMotor.Direction.FORWARD);

        backleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //reset the encoders at the beginning
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//set the motors to a useable mode
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //claw.setMode(CRServo.RunMode.RUN_WITHOUT_ENCODER);

        g1 = this.gamepad1;
        g2 = this.gamepad2;


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


            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            /*int rightEncoders = 2000;
            int armTarget = arm.getCurrentPosition() + rightEncoders;
            arm.setTargetPosition(armTarget);*/
            double potatoPower = 1;
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.1);
            double frontleftPower = (y + x + rx) / denominator;
            double backleftPower = (y - x + rx) / denominator;
            double frontrightPower = (y - x - rx) / denominator;
            double backrightPower = (y + x - rx) / denominator;
            double ledPower = (backleftPower+frontrightPower+backrightPower+frontleftPower)/6.1;
            if(ledPower<0){
                led.setPower(-ledPower);
            }
            else if(ledPower>0){
                led.setPower(ledPower);
            }
            else if (ledPower==0){
                led.setPower(0.01);
            }
            //arm.setTargetPosition(armTarget);
            //toggle.update(gamepad1.a);
            upToggle.update(gamepad1.dpad_up);
            downToggle.update(gamepad1.dpad_down);
            //capToggle.update(gamepad1.x);



            backrightDrive.setPower(backrightPower);
            backleftDrive.setPower(backleftPower);
            frontrightDrive.setPower(frontrightPower);
            frontleftDrive.setPower(frontleftPower);

            //telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", backleftPower, backrightPower, frontleftPower, frontrightPower);

            //grip claw
            if(gamepad2.a){
                if (tGrip) {
                    //Do when toggle is on
                    telemetry.addData("Status","open claw");
                    telemetry.update();
                    claw.setPower(1);
                    sleep(350);
                    claw.setPower(0);
                }
                tGrip = false;



            }
            else if(!gamepad2.a){
                if(!tGrip){
                    //Do when toggle is on
                    telemetry.addData("Status","close claw");
                    telemetry.update();
                    claw.setPower(-1);
                    //sleep(1000);
                    //claw.setPower(0);
                }
                tGrip = true;
            }
            /*if (gamepad2.a) {
                //Do when toggle is on
                telemetry.addData("Status","close claw");
                telemetry.update();
                claw.setPower(-1);
                //sleep(1000);
                //claw.setPower(0);




                //release claw
            }if (gamepad2.b) {
                //Do when toggle is on
                telemetry.addData("Status","open claw");
                telemetry.update();
                claw.setPower(1);
                sleep(450);
                claw.setPower(0);





            }*/ /*else if (!gamepad1.b && !gamepad1.a) {
                //Do when toggle is off

                //Potato.setPower(0);
                claw.setPower(0);


            }*/
            //capstone code
            if (gamepad1.x) {
                //Do when toggle is on
                telemetry.addData("Status", "You spin my hhhhead round");
                telemetry.update();
                spinnySpin.setPower(-0.5);
                telemetry.addData("Status", "Ligmat");
                telemetry.update();
                //extended = true;


            }
            if (!gamepad1.x) {
                //Do when toggle is on

                spinnySpin.setPower(0);

                //extended = true;


            }

            if(gamepad2.dpad_up) {
                //Do when toggle is on
                telemetry.addData("Status","Up");
                telemetry.update();
                if (gamepad2.dpad_up) {
                    int rightEncoders = 1000;
                    int armTarget = arm.getCurrentPosition() + rightEncoders;
                    arm.setTargetPosition(armTarget);
                }else if(gamepad2.dpad_down){
                    int rightEncoders =-1000;
                    int armTarget = arm.getCurrentPosition() + rightEncoders;
                    arm.setTargetPosition(armTarget);
                }
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if(gamepad2.dpad_up){
                    if (!tFlag) {
                        arm.setPower(0.4);
                    }
                    tFlag = true;



                }
                else if(!gamepad2.dpad_up){
                    tFlag = false;
                    arm.setPower(0);
                }
                //int armPos = arm.getCurrentPosition();
                //arm.setTargetPosition(5000);




            }

            if(gamepad2.dpad_down){
                //Do when toggle is off
                telemetry.addData("Status", "Down");
                telemetry.update();
                if (gamepad2.dpad_up) {
                    int rightEncoders = 1000;
                    int armTarget = arm.getCurrentPosition() + rightEncoders;
                    arm.setTargetPosition(armTarget);
                }else if(gamepad2.dpad_down){
                    int rightEncoders =-1000;
                    int armTarget = arm.getCurrentPosition() + rightEncoders;
                    arm.setTargetPosition(armTarget);
                }
                //arm.setPower(-1);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                if(gamepad2.dpad_down){
                    if (!tFlag) {
                        arm.setPower(0.4);
                    }
                    tFlag = true;



                }
                else if(!gamepad2.dpad_down){
                    tFlag = false;
                    arm.setPower(0);
                }
                //arm.setTargetPosition(-2);

                // 2022 lets see if you find this;

            }

        }
    }
}

