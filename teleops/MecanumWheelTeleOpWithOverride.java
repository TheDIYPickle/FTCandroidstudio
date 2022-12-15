package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RealToggle;
import org.firstinspires.ftc.teamcode.ToggleMap;
import org.firstinspires.ftc.teamcode.UseMap;

@TeleOp(name="MecanumWheelTeleOpWithOverride")
public class MecanumWheelTeleOpWithOverride extends LinearOpMode {
    ToggleMap ToggleMap1 = new ToggleMap();
    UseMap UseMap1 = new UseMap();
    ToggleMap ToggleMap2 = new ToggleMap();
    UseMap UseMap2 = new UseMap();
    RealToggle toggle = new RealToggle(false);
    RealToggle upToggle = new RealToggle(false);
    RealToggle downToggle = new RealToggle(false);
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
    int currentPos = 0;

    public int myMethod(int start) {
        int targetTime = 0;
        if (start == 1) {
            //This is the time it takes to get from the current position to the base position
            targetTime = 1000;
        } else if (start == 2) {
            targetTime = 2000;
        } else if (start == 3) {
            targetTime = 3000;
        }
        return targetTime;

    }

    @Override
    public void runOpMode() {
        backleftDrive = hardwareMap.get(DcMotor.class, "backleftDrive");
        backrightDrive = hardwareMap.get(DcMotor.class, "backrightDrive");
        frontleftDrive = hardwareMap.get(DcMotor.class, "frontleftDrive");
        frontrightDrive = hardwareMap.get(DcMotor.class, "frontrightDrive");
        //Potato = hardwareMap.get(DcMotor.class, "Potato");

        claw = hardwareMap.get(CRServo.class, "claw");
        arm = hardwareMap.get(DcMotor.class, "arm");
        //lilPotato = hardwareMap.crservo.get("lilPotato");


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
        boolean override = false;

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
            if (override == false) {
                //grip claw
                if (gamepad2.a) {
                    telemetry.addData("Status", "open claw");
                    telemetry.update();
                    claw.setPower(1);
                    sleep(350);
                    claw.setPower(0);
                    sleep(500);
                    telemetry.addData("Status", "Arm moving back to start");
                    telemetry.update();
                    int moveDistance = myMethod(currentPos);
                    arm.setPower(-0.5);
                    sleep(moveDistance);
                    arm.setPower(0);

                } else if (gamepad2.b) {

                    telemetry.addData("Status", "close claw");
                    telemetry.update();
                    claw.setPower(-1);
                    //sleep(1000);
                    //claw.setPower(0);

                }

                //arm matrix
                //first level
                if (gamepad1.dpad_up) {
                    arm.setPower(0.5);
                    sleep(1000);
                    arm.setPower(0);
                    currentPos = 1;
                }
                //second level
                if (gamepad1.dpad_right) {
                    arm.setPower(0.5);
                    sleep(2000);
                    arm.setPower(0);
                    currentPos = 2;
                }
                //third level
                if (gamepad1.dpad_down) {
                    arm.setPower(0.5);
                    sleep(2000);
                    arm.setPower(0);
                    currentPos = 3;
                }
                //override button
                if (gamepad1.x) {
                    override = true;
                }

            }
            if (override == true) {
                //grip claw
                if (gamepad2.a) {
                    telemetry.addData("Status", "open claw");
                    telemetry.update();
                    claw.setPower(1);
                    sleep(350);
                    claw.setPower(0);


                } else if (gamepad2.b) {

                    telemetry.addData("Status", "close claw");
                    telemetry.update();
                    claw.setPower(-1);
                    //sleep(1000);
                    //claw.setPower(0);

                }

                //arm matrix

                if (gamepad1.dpad_up) {
                    arm.setPower(0.5);

                }
                else if (gamepad1.dpad_down) {
                    arm.setPower(-0.5);

                }
                else{
                    arm.setPower(0);
                }


            }
        }
    }
}