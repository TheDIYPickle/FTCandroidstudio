package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RealToggle;
import org.firstinspires.ftc.teamcode.ToggleMap;
import org.firstinspires.ftc.teamcode.UseMap;

@TeleOp(name = "MecanumCode")
//change Potato to the class name when you move the bot
public class Mecanum extends LinearOpMode {
    ToggleMap ToggleMap1 = new ToggleMap();
    UseMap UseMap1 = new UseMap();
    ToggleMap ToggleMap2 = new ToggleMap();
    UseMap UseMap2 = new UseMap();
    RealToggle toggle = new RealToggle(false);
    RealToggle upToggle = new RealToggle(false);
    RealToggle downToggle = new RealToggle(false);
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontleftDrive = hardwareMap.dcMotor.get("frontleftDrive");
        DcMotor backleftDrive = hardwareMap.dcMotor.get("backleftDrive");
        DcMotor frontrightDrive = hardwareMap.dcMotor.get("frontrightDrive");
        DcMotor backrightDrive = hardwareMap.dcMotor.get("backrightDrive");
        DcMotor Potato = hardwareMap.dcMotor.get("Potato");
        Servo lilPotato = hardwareMap.servo.get("lilPotato");

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        frontleftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frontrightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        boolean G1a = gamepad1.a;
        boolean G1dpad_up= gamepad1.dpad_up;
        boolean G1dpad_down= gamepad1.dpad_down;
        boolean extended = false;
        boolean up = false;
        double push = 0;
        double pull = 0;
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;
            toggle.update(gamepad1.a);
            upToggle.update(gamepad1.dpad_up);
            downToggle.update(gamepad1.dpad_down);


            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;
            double potatoPower = 1;
            double lilPotatoPower = 1;

            frontleftDrive.setPower(frontLeftPower);
            backleftDrive.setPower(backLeftPower);
            frontrightDrive.setPower(frontRightPower);
            backrightDrive.setPower(backRightPower);
            // button code that extends the arm
            if(toggle.getToggleState()==true && extended==false) {
                //Do when toggle is on
                telemetry.addData("Status","Me when your mom");
                telemetry.update();
                Potato.setPower(-potatoPower);
                sleep(500);
                telemetry.addData("Status", "Ligma");
                telemetry.update();
                Potato.setPower(0);
                push=1;
                pull=0;
                extended=true;


            }
            else if(!toggle.getToggleState() && extended==true){
                //Do when toggle is off
                telemetry.addData("Status", "Your mom when I");
                telemetry.update();
                Potato.setPower(potatoPower);
                sleep(500);
                telemetry.addData("Status", "Ligma");
                telemetry.update();
                Potato.setPower(0);
                push=0;
                pull=1;
                extended=false;

            }
            // button code that moves the arm up and down
            if(gamepad1.dpad_up) {
                //Do when toggle is on
                telemetry.addData("Status","Doin ur mom");
                telemetry.update();
                lilPotato.setPosition(50);


                up=true;
            }
            else if(gamepad1.dpad_down){
                //Do when toggle is off
                telemetry.addData("Status", "We've been trying to reach you about you cars extended warrenty");
                telemetry.update();
                lilPotato.setPosition(-50);


                up=false;

            }
        }
    }
}