package org.firstinspires.ftc.teamcode.teleops;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TOF10120;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.TOF10120;

@TeleOp
public class LaserTest extends LinearOpMode {

    private TOF10120 Laser;

    @Override
    public void runOpMode() throws InterruptedException {
        Laser = hardwareMap.get(TOF10120.class, "Laser");

        waitForStart();

        while (opModeIsActive()) {

            // send the info back to driver station using telemetry function.
            telemetry.addData("Raw", Laser.getDistance(DistanceUnit.CM));
            telemetry.update();
        }

    }





}
