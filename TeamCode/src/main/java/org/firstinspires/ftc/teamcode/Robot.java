package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    public DcMotor backLeftDrive;
    public DcMotor backRightDrive;
    public DcMotor frontLeftDrive;
    public DcMotor frontRightDrive;

    public Servo verticalClawServo;
    public Servo horizontalClawServo;
    public Servo rotatingServo;

    public DcMotor verticalSlide;
    public DcMotor horizontalSlide;

    public Robot(HardwareMap hardwareMap) {
        // Linear Slide Initializations
        horizontalSlide = hardwareMap.dcMotor.get("hSlide");
        verticalSlide = hardwareMap.dcMotor.get("vSlide");

        horizontalSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        verticalSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        horizontalSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontalSlide.setTargetPosition(0);
        horizontalSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        horizontalSlide.setPower(0.5);

        // Claw Initializations
        verticalClawServo = hardwareMap.servo.get("verticalClaw");
        horizontalClawServo = hardwareMap.servo.get("intakeClaw");
        rotatingServo = hardwareMap.servo.get("turret");

        // Wheel Initializations
        backLeftDrive = hardwareMap.get(DcMotor.class, "backleftDrive");
        backRightDrive = hardwareMap.get(DcMotor.class, "backrightDrive");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "frontleftDrive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontrightDrive");

        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);

        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        backLeftDrive.setTargetPosition(0);
        backRightDrive.setTargetPosition(0);
        frontRightDrive.setTargetPosition(0);
        frontLeftDrive.setTargetPosition(0);

        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double powerVar = 0.3;
        //This turns on the chassis motors
        frontLeftDrive.setPower(powerVar);
        frontRightDrive.setPower(powerVar);
        backLeftDrive.setPower(powerVar);
        backRightDrive.setPower(powerVar);
    }

    public void forward(Integer input){
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition()+input);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition()+input);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition()+input);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition()+input);
    }
    public void backward(Integer input){
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition()-input);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition()-input);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition()-input);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition()-input);
    }
    public void right(Integer input){
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition()-input);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition()+input);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition()+input);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition()-input);
    }
    public void left(Integer input){
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition()+input);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition()-input);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition()-input);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition()+input);
    }
    public void rightTurn(Integer input){
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition()+input);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition()-input);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition()+input);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition()-input);
    }
    public void leftTurn(Integer input){
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition()-input);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition()+input);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition()-input);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition()+input);
    }
    public void setVerticalClawPosition(Integer input) {
        verticalClawServo.setPosition(input);
    }

    public void setHorizontalClawPosition(Integer input) {
        horizontalClawServo.setPosition(input);
    }

    public void setVerticalSlide(Integer input) {
        verticalSlide.setTargetPosition(input);
    }

    public void setHorizontalSlide(Integer input) {
        horizontalSlide.setTargetPosition(input);
    }
}
