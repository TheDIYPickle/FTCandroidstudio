package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.teamcode.CVPipelines.CopyPipeline;
import org.firstinspires.ftc.teamcode.CVPipelines.CopyPipelineRed;
import org.firstinspires.ftc.teamcode.CVPipelines.SecTestPipeline;
import org.firstinspires.ftc.teamcode.CVPipelines.TestPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "ExperimentingAuto")

public class ExperimentingAuto extends LinearOpMode {
    OpenCvCamera mainCam;
    String finalLocation = "0";
    boolean pictureVar = false;

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        mainCam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        //TestPipeline pipeline = new TestPipeline(telemetry);
        CopyPipelineRed pipeline = new CopyPipelineRed(telemetry);
        CopyPipeline startPipeline= new CopyPipeline(telemetry);


        mainCam.setPipeline(startPipeline);


        //mainCam.setViewportRenderer(OpenCvCamera.ViewportRenderer.GPU_ACCELERATED);
        mainCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                // Usually this is where you'll want to start streaming from the camera (see section 4)
                mainCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
                telemetry.addData("Status", "Camera crash");
                telemetry.update();
            }
        });
        //sleep(5000);
        while(!(gamepad1.a)){
            telemetry.addData("Status", "Press a to continue");
            telemetry.update();
        }
        mainCam.setPipeline(pipeline);
        waitForStart();
        mainCam.stopStreaming();
        if (pictureVar == false) {
            if(pipeline.ColorType()==CopyPipelineRed.Colors.Color1) {
                finalLocation = "1";
                pictureVar = true;
            }
            else if(pipeline.ColorType()==CopyPipelineRed.Colors.Color2) {
                finalLocation = "2";
                pictureVar = true;
            }
            else if(pipeline.ColorType()==CopyPipelineRed.Colors.Color3) {
                finalLocation = "3";
                pictureVar = true;
            }
            else{
                telemetry.addData("Status", "Option fail");
                telemetry.update();
                sleep(2000);
            }

        }
        mainCam.closeCameraDevice();
        telemetry.addData("Position", "target " + finalLocation);
        telemetry.update();
        sleep(2000);

        if(finalLocation=="1"){
            telemetry.addData("Position", "1");
            telemetry.update();
        }
        else if(finalLocation=="2"){
            telemetry.addData("Position", "2");
            telemetry.update();
        }
        else if(finalLocation=="3"){
            telemetry.addData("Position", "3");
            telemetry.update();
        }
        sleep(2000);




    }
}