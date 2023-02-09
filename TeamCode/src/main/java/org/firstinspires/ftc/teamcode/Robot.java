package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

enum Direction {
    FORWARD, BACKWARD, RIGHT, LEFT
}
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

    public BNO055IMU imu;

    public Robot(HardwareMap hardwareMap) {
        // Linear Slide Initializations
        horizontalSlide = hardwareMap.dcMotor.get("hSlide");
        verticalSlide = hardwareMap.dcMotor.get("vSlide");

        horizontalSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        verticalSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        horizontalSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontalSlide.setTargetPosition(5);
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

        //imu Initialization
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);
    }

    public void setWheelMode(DcMotor.RunMode mode) {
        backLeftDrive.setMode(mode);
        backRightDrive.setMode(mode);
        frontRightDrive.setMode(mode);
        frontLeftDrive.setMode(mode);
    }

    public void setWheelTargetPosition(int input) {
        backLeftDrive.setTargetPosition(input);
        backRightDrive.setTargetPosition(input);
        frontRightDrive.setTargetPosition(input);
        frontLeftDrive.setTargetPosition(input);
    }

    public void setWheelPower(double input) {
        frontLeftDrive.setPower(input);
        frontRightDrive.setPower(input);
        backLeftDrive.setPower(input);
        backRightDrive.setPower(input);
    }

    public void setWheelZeroPowerBehavior(DcMotor.ZeroPowerBehavior input) {
        backLeftDrive.setZeroPowerBehavior(input);
        backRightDrive.setZeroPowerBehavior(input);
        frontLeftDrive.setZeroPowerBehavior(input);
        frontRightDrive.setZeroPowerBehavior(input);
    }

    public void move(Vector2D vector, double rotate, boolean isFieldCentric) {

        double imuAngle = isFieldCentric ? imu.getAngularOrientation().firstAngle : 0;

        vector = vector.rotateVector(-imuAngle);

        double denominator = Math.max(Math.abs(vector.y) + Math.abs(vector.x) + Math.abs(rotate), 1.1);
        double frontleftPower = (vector.y + vector.x + rotate) / denominator;
        double backleftPower = (vector.y - vector.x + rotate) / denominator;
        double frontrightPower = (vector.y - vector.x - rotate) / denominator;
        double backrightPower = (vector.y + vector.x - rotate) / denominator;

        backRightDrive.setPower(backrightPower);
        backLeftDrive.setPower(backleftPower);
        frontRightDrive.setPower(frontrightPower);
        frontLeftDrive.setPower(frontleftPower);
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

    public void setHorizontalClawPosition(Integer input) { horizontalClawServo.setPosition(input); }

    public void setRotatingServoPosition(Integer input) { rotatingServo.setPosition(input); }

    public void setVerticalSlide(Integer input) {
        verticalSlide.setTargetPosition(input);
    }

    public void setHorizontalSlide(Integer input) {
        horizontalSlide.setTargetPosition(input);
    }


}
