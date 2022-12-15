package org.firstinspires.ftc.teamcode.auton;


import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "AutoMovementRight")

public class AutoMovementRight extends LinearOpMode {
    private DcMotor backleftDrive = null;
    private DcMotor backrightDrive = null;
    private DcMotor frontleftDrive = null;
    private DcMotor frontrightDrive = null;
    //private DcMotor Potato = null;
    private DcMotor spinnySpin = null;
    private Servo rightClaw = null;
    private Servo leftClaw = null;
    private DcMotor arm = null;
    private DcMotor counterWeight= null;
    //private CRServo lilPotato= null;
    private CRServo boxcar = null;
    private CRServo dropper = null;
    DcMotor led;
    public Gamepad g1;
    public Gamepad g2;
    double powerVar=0.5;

    public void Forward(Integer input){

        backleftDrive.setTargetPosition(backleftDrive.getCurrentPosition()+input);
        backrightDrive.setTargetPosition(backrightDrive.getCurrentPosition()+input);
        frontleftDrive.setTargetPosition(frontleftDrive.getCurrentPosition()+input);
        frontrightDrive.setTargetPosition(frontrightDrive.getCurrentPosition()+input);
    }
    public void Backward(Integer input){

        backleftDrive.setTargetPosition(backleftDrive.getCurrentPosition()-input);
        backrightDrive.setTargetPosition(backrightDrive.getCurrentPosition()-input);
        frontleftDrive.setTargetPosition(frontleftDrive.getCurrentPosition()-input);
        frontrightDrive.setTargetPosition(frontrightDrive.getCurrentPosition()-input);
    }
    public void Right(Integer input){

        backleftDrive.setTargetPosition(backleftDrive.getCurrentPosition()-input);
        backrightDrive.setTargetPosition(backrightDrive.getCurrentPosition()+input);
        frontleftDrive.setTargetPosition(frontleftDrive.getCurrentPosition()+input);
        frontrightDrive.setTargetPosition(frontrightDrive.getCurrentPosition()-input);
    }

    public void Left(Integer input){

        backleftDrive.setTargetPosition(backleftDrive.getCurrentPosition()+input);
        backrightDrive.setTargetPosition(backrightDrive.getCurrentPosition()-input);
        frontleftDrive.setTargetPosition(frontleftDrive.getCurrentPosition()-input);
        frontrightDrive.setTargetPosition(frontrightDrive.getCurrentPosition()+input);
    }

    public void RightTurn(Integer input){

        backleftDrive.setTargetPosition(backleftDrive.getCurrentPosition()+input);
        backrightDrive.setTargetPosition(backrightDrive.getCurrentPosition()-input);
        frontleftDrive.setTargetPosition(frontleftDrive.getCurrentPosition()+input);
        frontrightDrive.setTargetPosition(frontrightDrive.getCurrentPosition()-input);
    }

    public void LeftTurn(Integer input){

        backleftDrive.setTargetPosition(backleftDrive.getCurrentPosition()-input);
        backrightDrive.setTargetPosition(backrightDrive.getCurrentPosition()+input);
        frontleftDrive.setTargetPosition(frontleftDrive.getCurrentPosition()-input);
        frontrightDrive.setTargetPosition(frontrightDrive.getCurrentPosition()+input);
    }



    @Override
    public void runOpMode(){

        backleftDrive = hardwareMap.get(DcMotor.class, "backleftDrive");
        backrightDrive = hardwareMap.get(DcMotor.class, "backrightDrive");
        frontleftDrive = hardwareMap.get(DcMotor.class, "frontleftDrive");
        frontrightDrive = hardwareMap.get(DcMotor.class, "frontrightDrive");
        //counterWeight = hardwareMap.get(DcMotor.class, "counterWeight");
        //Potato = hardwareMap.get(DcMotor.class, "Potato");

        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        arm = hardwareMap.get(DcMotor.class, "arm");
        //lilPotato = hardwareMap.crservo.get("lilPotato");



        backleftDrive.setDirection(DcMotor.Direction.REVERSE);
        backrightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontleftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontrightDrive.setDirection(DcMotor.Direction.FORWARD);
        //counterWeight.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);

        backleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //Wheels: Back left drive
        backleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleftDrive.setTargetPosition(0);
        backleftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //back right
        backrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backrightDrive.setTargetPosition(0);
        backrightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //front right
        frontrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontrightDrive.setTargetPosition(0);
        frontrightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //front left
        frontleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleftDrive.setTargetPosition(0);
        frontleftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Arm
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontleftDrive.setPower(powerVar);
        frontrightDrive.setPower(powerVar);
        backleftDrive.setPower(powerVar);
        backrightDrive.setPower(powerVar);

        waitForStart();
        //This starts the bot
        sleep(270);

        /*telemetry.addData("Status","Forward motion");
        telemetry.update();
        Forward(1000);
        sleep(2000);
        telemetry.addData("Status","Back motion");
        telemetry.update();
        Backward(1000);
        sleep(2000);
        telemetry.addData("Status","Left motion");
        telemetry.update();
        Left(1000);
        sleep(2000);
        telemetry.addData("Status","Right motion");
        telemetry.update();
        Right(1000);
        sleep(2000);
        telemetry.addData("Status","Left turn");
        telemetry.update();
        LeftTurn(1000);
        sleep(2000);
        telemetry.addData("Status","Right turn");
        telemetry.update();
        RightTurn(1000);
        sleep(2000);*/
        telemetry.addData("Status","Forward motion");
        telemetry.update();
        Left(100);
        sleep(1000);
        telemetry.addData("Status","Backward motion");
        telemetry.update();
        Backward(1000);
        sleep(2000);











       /* sleep(1000);
        telemetry.addData("Moving Backwards","Starting Motion");
        telemetry.update();

       frontleftDrive.setPower(-1);
       frontrightDrive.setPower(-1);
       backleftDrive.setPower(-1);
       backrightDrive.setPower(-1);
       sleep (4000);
       telemetry.addData ("All Stop","Comenceing Action");
       telemetry.update();
        frontleftDrive.setPower(0);
       frontrightDrive.setPower(0);
       backleftDrive.setPower(0);
       backrightDrive.setPower(0);*/




    }
}