package org.firstinspires.ftc.teamcode.auton;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "AutoMovementBackupRedDucks")

public class AutoMovementBackupRedDucks extends LinearOpMode {






    @Override
    public void runOpMode(){

        DcMotor frontleftDrive = hardwareMap.dcMotor.get("frontleftDrive");
        DcMotor backleftDrive = hardwareMap.dcMotor.get("backleftDrive");
        DcMotor frontrightDrive = hardwareMap.dcMotor.get("frontrightDrive");
        DcMotor backrightDrive = hardwareMap.dcMotor.get("backrightDrive");
        CRServo claw = hardwareMap.crservo.get("claw");
        CRServo boxcar = hardwareMap.crservo.get("boxcar");
        DcMotor spinnySpin = hardwareMap.dcMotor.get("spinnySpin");
        DcMotor arm = hardwareMap.dcMotor.get("arm");
        frontrightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        backrightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        spinnySpin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        claw.setPower(-1);
        waitForStart();
        //This starts the bot

        //Moves forward
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
        frontleftDrive.setPower(0.8);
        frontrightDrive.setPower(-0.8);
        backleftDrive.setPower(0.8);
        backrightDrive.setPower(-0.8);
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
        spinnySpin.setPower(-0.25);
        sleep(4500);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        spinnySpin.setPower(0);

        sleep(350);


        /*telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(-0.6);
        frontrightDrive.setPower(0.6);
        backleftDrive.setPower(0.6);
        backrightDrive.setPower(-0.7);
        sleep(200);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        */

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
        sleep(500);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(270);

        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(0.3);
        frontrightDrive.setPower(-0.3);
        backleftDrive.setPower(0.3);
        backrightDrive.setPower(-0.4);
        sleep(300);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        /*
        sleep(350);


        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(0.6);
        frontrightDrive.setPower(0.6);
        backleftDrive.setPower(0.6);
        backrightDrive.setPower(0.7);
        sleep(250);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(350);


        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(0.6);
        frontrightDrive.setPower(-0.6);
        backleftDrive.setPower(-0.6);
        backrightDrive.setPower(0.7);
        sleep(250);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(350);


        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(0.6);
        frontrightDrive.setPower(-0.6);
        backleftDrive.setPower(0.6);
        backrightDrive.setPower(-0.7);
        sleep(350);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(350);


        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(-0.6);
        frontrightDrive.setPower(-0.6);
        backleftDrive.setPower(-0.6);
        backrightDrive.setPower(-0.7);
        sleep(350);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(350);


        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(0.6);
        frontrightDrive.setPower(-0.6);
        backleftDrive.setPower(-0);
        backrightDrive.setPower(-0.7);
        sleep(350);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(350);


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

        sleep(350);


        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(-0.6);
        frontrightDrive.setPower(0.6);
        backleftDrive.setPower(0.6);
        backrightDrive.setPower(-0.7);
        sleep(1050);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(350);

        arm.setPower(0.6);
        sleep(3000);
        arm.setPower(0);

        sleep(350);


        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(0.6);
        frontrightDrive.setPower(0.6);
        backleftDrive.setPower(0.6);
        backrightDrive.setPower(0.7);
        sleep(1100);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(350);


        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(0.6);
        frontrightDrive.setPower(-0.6);
        backleftDrive.setPower(0.6);
        backrightDrive.setPower(-0.7);
        sleep(140);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);

        sleep(350);
        claw.setPower(0.5);
        sleep(350);
        claw.setPower(0);


        sleep(350);


        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(-0.6);
        frontrightDrive.setPower(-0.6);
        backleftDrive.setPower(-0.6);
        backrightDrive.setPower(-0.7);
        sleep(1050);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);


        sleep(350);
        arm.setPower(-0.5);
        sleep(2000);
        arm.setPower(0);

        /*
        sleep(350);


        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(-0.6);
        frontrightDrive.setPower(0.6);
        backleftDrive.setPower(-0.6);
        backrightDrive.setPower(0.7);
        sleep(220);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);
        */

        sleep(350);


        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(0.6);
        frontrightDrive.setPower(-0.6);
        backleftDrive.setPower(-0.6);
        backrightDrive.setPower(0.7);
        sleep(300);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);
        /*
        sleep(350);


        telemetry.addData("Status","Starting motion");
        telemetry.update();
        frontleftDrive.setPower(-0.6);
        frontrightDrive.setPower(-0.6);
        backleftDrive.setPower(-0.6);
        backrightDrive.setPower(-0.7);
        sleep(500);

        telemetry.addData("All Stop", "Comencing Action");
        telemetry.update();
        frontleftDrive.setPower(0);
        frontrightDrive.setPower(0);
        backleftDrive.setPower(0);
        backrightDrive.setPower(0);


        */


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
