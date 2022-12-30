package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="teleop")
public class TeleOP extends LinearOpMode {

    DcMotor verticalSlide;
    DcMotor horizontalSlide;

    DcMotor backleftDrive;
    DcMotor backrightDrive;
    DcMotor frontleftDrive;
    DcMotor frontrightDrive;

    Servo verticalClawServo;
    Servo horizontalClawServo;
    Servo rotatingServo;

    public BNO055IMU imu;

    public double angle;

    public Toggle verticalClawToggle = new Toggle(false);
    public Toggle horizontalClawToggle = new Toggle(false);
    public double[] verticalClawPositions = new double[] {0.67, 0};
    public double[] horizontalClawPositions = new double[] {0.67, 0};

    // [0] = lower bound
    // [1] = upper bound
    int[] horizontalBounds = {5, 4200};
    int[] verticalBounds = {5, 4200};
    int[] rotatingBounds = {0, 90};

    @Override
    public void runOpMode() throws InterruptedException {

        // initialize linear slides
        horizontalSlide = hardwareMap.dcMotor.get("arm");
        verticalSlide = hardwareMap.dcMotor.get("arm2");

        horizontalSlide.setDirection(DcMotorSimple.Direction.REVERSE);

        verticalSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        horizontalSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        horizontalSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        horizontalSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // initialize claw servos
        verticalClawServo = hardwareMap.servo.get("leftClaw");
        horizontalClawServo = hardwareMap.servo.get("rightClaw");
        rotatingServo = hardwareMap.servo.get("turret");

        // initialize wheels
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

        // initialize inertial measurement unit
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;

        imu.initialize(parameters);

        waitForStart();

        // Please Change this
        int rotatingPos = 36;

        while (opModeIsActive()) {

            int horizontalSlidePos = -horizontalSlide.getCurrentPosition();
            int verticalSlidePos = verticalSlide.getCurrentPosition();

            // Check bounds of slides
            boolean horizontalUpperBoundReached = (horizontalSlidePos >= horizontalBounds[1]);
            boolean horizontalLowerBoundReached = (horizontalSlidePos <= horizontalBounds[0]);
            boolean verticalUpperBoundReached = (verticalSlidePos >= verticalBounds[1]);
            boolean verticalLowerBoundReached = (verticalSlidePos <= verticalBounds[0]);

            telemetry.addData("Vertical Claw Active", verticalClawToggle.state);
            telemetry.addData("Horizontal Claw Active", horizontalClawToggle.state);

            // Note - Stick inputs reversed, for some reason that doesn't super matter.
            // Down is positive and up is negative on the right stick.

            double horizontalSlidePower = -gamepad2.right_stick_y;
            if(horizontalUpperBoundReached){
                horizontalSlidePower = Math.min(0, horizontalSlidePower);
            } else if(horizontalLowerBoundReached){
                horizontalSlidePower = Math.max(0, horizontalSlidePower);
            }

            double verticalSlidePower = -gamepad2.left_stick_y;
            if(verticalUpperBoundReached){
                verticalSlidePower = Math.min(0, verticalSlidePower);
            } else if(verticalLowerBoundReached){
                verticalSlidePower = Math.max(0, verticalSlidePower);
            }

            float rotationSpeed = 1f;
            double rotationDirection = (gamepad2.dpad_right ? 1 : (gamepad2.dpad_left ? -1 : 0)) * rotationSpeed; // 1 if right, -1 if left, 0 if neither.
            rotatingPos += rotationDirection;
            rotatingPos = Math.max(rotatingPos, rotatingBounds[0]);
            rotatingPos = Math.min(rotatingPos, rotatingBounds[1]);

            if (-horizontalSlide.getCurrentPosition() <= 1050) {
                rotatingPos = 36;
            }

            rotatingServo.setPosition(rotatingPos/280d);

            telemetry.addData("Servo Position", rotatingPos);

            verticalSlide.setPower(verticalSlidePower);
            horizontalSlide.setPower(-horizontalSlidePower);

            // Field Centric
            angle = imu.getAngularOrientation().firstAngle;
            telemetry.addData("current Encoder value:",-horizontalSlide.getCurrentPosition());

            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;

            // rotates the left stick and changes the x & y values accordingly
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
