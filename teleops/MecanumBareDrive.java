package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;



@TeleOp(name="MecanumBareDrive")
public class MecanumBareDrive extends LinearOpMode {


    private DcMotor backleftDrive = null;
    private DcMotor backrightDrive = null;
    private DcMotor frontleftDrive = null;
    private DcMotor frontrightDrive = null;
    //private DcMotor Potato = null;
    private DcMotor spinnySpin = null;
    private CRServo rightClaw = null;
    private CRServo leftClaw = null;
    private DcMotor arm = null;
    private DcMotor counterWeight= null;
    //private CRServo lilPotato= null;
    private CRServo boxcar = null;
    private CRServo dropper = null;
    DcMotor led;
    public Gamepad g1;
    public Gamepad g2;

    int currentPos=0;

    private boolean prevState;
    private boolean state=true;


    public boolean onceUpdateToggle(boolean current) {
        if (current && !(prevState)) {
            return true;
        }
        prevState = current;
        return false;
    }
    public boolean updateToggle(boolean current) {
        if (!prevState) {
            state = !state;
        }
        prevState = current;
        return state;
    }

    @Override
    public void runOpMode() {
        backleftDrive = hardwareMap.get(DcMotor.class, "backleftDrive");
        backrightDrive = hardwareMap.get(DcMotor.class, "backrightDrive");
        frontleftDrive = hardwareMap.get(DcMotor.class, "frontleftDrive");
        frontrightDrive = hardwareMap.get(DcMotor.class, "frontrightDrive");
        //counterWeight = hardwareMap.get(DcMotor.class, "counterWeight");
        //Potato = hardwareMap.get(DcMotor.class, "Potato");

        rightClaw = hardwareMap.get(CRServo.class, "rightClaw");
        leftClaw = hardwareMap.get(CRServo.class, "leftClaw");
        arm = hardwareMap.get(DcMotor.class, "arm");
        //lilPotato = hardwareMap.crservo.get("lilPotato");



        backleftDrive.setDirection(DcMotor.Direction.FORWARD);
        backrightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontleftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontrightDrive.setDirection(DcMotor.Direction.FORWARD);
        //counterWeight.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);

        backleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //counterWeight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //reset the encoders at the beginning
        // arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set the motors to a useable mode
        //arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
        int delicate= 1;

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
            //capToggle.update(gamepad1.x);


            if(gamepad1.a){
                telemetry.addData("Status", "Slow Mode");
                telemetry.update();
                delicate=4;
            }
            if(gamepad1.b){
                telemetry.addData("Status", "Fast Mode");
                telemetry.update();
                delicate=1;
            }



            backrightDrive.setPower(backrightPower/delicate);
            backleftDrive.setPower(backleftPower/delicate);
            frontrightDrive.setPower(frontrightPower/delicate);
            frontleftDrive.setPower(frontleftPower/delicate);
            arm.setPower(-0.5);
            int armTarget=0;
            double height=arm.getCurrentPosition();
            if(gamepad1.dpad_up && !(arm.getCurrentPosition()>=3775)){
                armTarget = arm.getCurrentPosition() + 100;
                arm.setTargetPosition(armTarget);
                height=arm.getCurrentPosition();
                telemetry.addData("Height", height);
                telemetry.update();
            }
            if(gamepad1.dpad_down && !(arm.getCurrentPosition()<=0)){
                armTarget = arm.getCurrentPosition() - 100;
                arm.setTargetPosition(armTarget);
                height=arm.getCurrentPosition();
                telemetry.addData("Height", height);
                telemetry.update();
            }


            if(updateToggle(gamepad1.x)){
                telemetry.addData("Status","closing");
                telemetry.update();
                rightClaw.setPower(1);
                leftClaw.setPower(-1);
                sleep(100);
                leftClaw.setPower(0);
                sleep(400);
                rightClaw.setPower(0);

            }
            else if(!(updateToggle(gamepad1.x))){
                telemetry.addData("Status","open");
                telemetry.update();
                rightClaw.setPower(-1);
                leftClaw.setPower(1);
                sleep(300);
                rightClaw.setPower(0);
                leftClaw.setPower(0);
            }
            //telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", backleftPower, backrightPower, frontleftPower, frontrightPower);

            /*//grip claw
            if(gamepad2.a){
                telemetry.addData("Status","open claw");
                telemetry.update();
                claw.setPower(1);
                sleep(350);
                claw.setPower(0);
                sleep(500);
                telemetry.addData("Status", "Arm moving back to start");
                telemetry.update();
                int moveDistance = myMethod(currentPos);
                arm.setPower(-0.5);
                counterWeight.setPower(-0.5);
                sleep(moveDistance);
                arm.setPower(0);
                counterWeight.setPower(0);

            }
            else if(gamepad2.b){

                telemetry.addData("Status","close claw");
                telemetry.update();
                claw.setPower(-1);
                //sleep(1000);
                //claw.setPower(0);

            }

            //arm matrix
            //first level
            if(gamepad1.dpad_up){
                arm.setPower(0.5);
                counterWeight.setPower(0.5);
                sleep(1000);
                arm.setPower(0);
                counterWeight.setPower(0);
                currentPos=1;
            }
            //second level
            if(gamepad1.dpad_right){
                arm.setPower(0.5);
                counterWeight.setPower(0.5);
                sleep(2000);
                arm.setPower(0);
                counterWeight.setPower(0);
                currentPos=2;
            }
            //third level
            if(gamepad1.dpad_left){
                arm.setPower(0.5);
                counterWeight.setPower(0.5);
                sleep(3000);
                arm.setPower(0);
                counterWeight.setPower(0);
                currentPos=3;
            }*/













        }
    }
}

