package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="teleop")
public class TeleOP extends LinearOpMode {

    DcMotor lv;
    DcMotor rv;

    DcMotor backleftDrive;
    DcMotor backrightDrive;
    DcMotor frontleftDrive;
    DcMotor frontrightDrive;

    Servo verticalClawServo;
    Servo horizontalClawServo;

    public BNO055IMU imu;

    public double angle;

    public Toggle verticalClawToggle = new Toggle(false);
    public Toggle horizontalClawToggle = new Toggle(false);
    public double[] verticalClawPositions = new double[] {0.67, 0};
    public double[] horizontalClawPositions = new double[] {0.67, 0};



    @Override
    public void runOpMode() throws InterruptedException {

        // arm is the bots right hand motor
        rv = hardwareMap.dcMotor.get("arm");
        lv = hardwareMap.dcMotor.get("arm2");

        lv.setDirection(DcMotorSimple.Direction.REVERSE);
        rv.setDirection(DcMotorSimple.Direction.REVERSE);

        lv.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rv.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rv.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        verticalClawServo = hardwareMap.servo.get("leftClaw");
        horizontalClawServo = hardwareMap.servo.get("rightClaw");


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

        rv.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive()) {

            boolean surpassUpperBound = (rv.getCurrentPosition() >= 4200);
            boolean surpassLowerBound = (rv.getCurrentPosition() <= 5);

            telemetry.addData("Vertical Claw Active", verticalClawToggle.state);
            telemetry.addData("Horizontal Claw Active", horizontalClawToggle.state);



            //Gamepad stick power inversed for some reason, easy fix
            //i.e. When R stick pushed up it returned negative values

            //Upper and Lower Bounds
            double verticalSlidePower = -gamepad2.right_stick_y;
            if(surpassUpperBound){
                verticalSlidePower = Math.min(0, verticalSlidePower);
            }
            if(surpassLowerBound){
                verticalSlidePower = Math.max(0, verticalSlidePower);
            }
            rv.setPower(-verticalSlidePower);
            lv.setPower(-verticalSlidePower);

            angle = imu.getAngularOrientation().firstAngle;
            telemetry.addData("current Encoder value:",rv.getCurrentPosition());

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
            verticalClawToggle.update(gamepad2.a);
            horizontalClawToggle.update(gamepad2.b);

            //update the servos
            verticalClawServo.setPosition(verticalClawPositions[verticalClawToggle.state ? 1 : 0]);
            horizontalClawServo.setPosition(horizontalClawPositions[horizontalClawToggle.state ? 1 : 0]);


            telemetry.update();
        }
    }


}
