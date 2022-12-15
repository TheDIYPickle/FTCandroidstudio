package org.firstinspires.ftc.teamcode.auton;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
@Autonomous(name = "AutoMovementBackupBlueDucks")

public class AutoMovementBackupBlueDucks extends LinearOpMode {






    @Override
    public void runOpMode(){

        DcMotor frontleftDrive = hardwareMap.dcMotor.get("frontleftDrive");
        DcMotor backleftDrive = hardwareMap.dcMotor.get("backleftDrive");
        DcMotor frontrightDrive = hardwareMap.dcMotor.get("frontrightDrive");
        DcMotor backrightDrive = hardwareMap.dcMotor.get("backrightDrive");
        CRServo claw = hardwareMap.crservo.get("claw");
        CRServo boxcar = hardwareMap.crservo.get("boxcar");
        DcMotor spinnySpin = hardwareMap.dcMotor.get("spinnySpin");
        frontrightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        backrightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        //This starts the bot
        sleep(270);

        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(1);
        frontrightDrive.setPower(1);
        backleftDrive.setPower(1);
        backrightDrive.setPower(1);
        sleep(81);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(270);

        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(-0.5);
        frontrightDrive.setPower(0.5);
        backleftDrive.setPower(-0.5);
        backrightDrive.setPower(0.5);
        sleep(630);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(270);

        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(-0.5);
        frontrightDrive.setPower(-0.5);
        backleftDrive.setPower(-0.5);
        backrightDrive.setPower(-0.6);
        sleep(1000);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(270);

        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(0.5);
        frontrightDrive.setPower(-0.5);
        backleftDrive.setPower(0.5);
        backrightDrive.setPower(-0.5);
        sleep(800);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(600);

        telemetry.addData("Status","Starting motion");
        telemetry.update();
        spinnySpin.setPower(0.25);
        sleep(5000);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        spinnySpin.setPower(0);

        sleep(270);

        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(1);
        frontrightDrive.setPower(1);
        backleftDrive.setPower(1);
        backrightDrive.setPower(1);
        sleep(81);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(270);

        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(0.5);
        frontrightDrive.setPower(-0.5);
        backleftDrive.setPower(0.5);
        backrightDrive.setPower(-0.5);
        sleep(450);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(270);

        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(0.5);
        frontrightDrive.setPower(0.5);
        backleftDrive.setPower(0.5);
        backrightDrive.setPower(0.6);
        sleep(1000);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(270);

        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(-0.3);
        frontrightDrive.setPower(0.3);
        backleftDrive.setPower(-0.3);
        backrightDrive.setPower(0.4);
        sleep(300);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);
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
