package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "AutoMovement")

public class AutoMovement extends LinearOpMode {
    //This just sets up an OpenCvCamera variable as mainCam
    OpenCvCamera mainCam;
    String finalLocation = "0";
    //This boolean is to ensure that we only take the first frame, allowing us not to waste memory or time.
    boolean pictureVar = false;


    Robot rob;

    DcMotor led;
    public Gamepad g1;
    public Gamepad g2;


    @Override
    public void runOpMode() throws InterruptedException {
        //This line sets up the needed resources to initialize the camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        //this line is really simple, it just is hardware mapping the camera
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //This sets up the actual OpenCv object of the camera, turning the normal camera into essentially a camera that can be used with OpenCv
        mainCam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);


        //These lines set up the needed pipelines, the first one is the main pipeline for image processing, and the second is for the targeting pipeline
        //I don't know why but for some reason the pipeline requires you to pass telemetry as an argument. I'm gonna experiment with this but for not, use the pipeline initialization like this.
        CopyPipelineRed pipeline = new CopyPipelineRed(telemetry);
        CopyPipeline startPipeline= new CopyPipeline(telemetry);

        //This part is the part that tells the computer to apply the targeting pipeline to the image, essentially taking in the current frame, and outputting the edited frame from the pipeline.
        mainCam.setPipeline(startPipeline);

        //Basic hardware mapping
        rob = new Robot(hardwareMap);
        rob.setWheelZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rob.setWheelMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rob.setWheelTargetPosition(0);
        rob.setWheelMode(DcMotor.RunMode.RUN_TO_POSITION);
        rob.setWheelPower(0.3);

        //This is the function I use to turn on the camera, I recommend you just copy paste this from here to line 224. This allows the camera to try and open, and if it fails, send out an error message
        mainCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                // Usually this is where you'll want to start streaming from the camera
                // This is what actually turns on the camera.
                mainCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                // This will be called if the camera could not be opened
                telemetry.addData("Status", "Camera crash");
                telemetry.update();
            }
        });



        waitForStart();
        //This sets the main pipeline to be the one being used. This is where the computer vision actually starts
        mainCam.setPipeline(pipeline);
        //I recommend having the program wait for five seconds to get rid of any startup lighting bugs
        sleep(5000);
        //This doesn’t turn off the camera, it just tells it to stop streaming: important distinction to make
        mainCam.stopStreaming();
        //This checks if the picturVar is false, if it is, do the image processing and decision making, and then set it to true so it never happens again.
        if (pictureVar == false) {
            if(pipeline.ColorType()==CopyPipelineRed.Colors.Color1) {
                //finalLocation is just here to tell the computer definitively where to go
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
            //This is a fail case
            else{
                telemetry.addData("Status", "Option fail");
                telemetry.update();
                sleep(2000);
            }

        }
        //This is actually how you turn off the camera
        mainCam.closeCameraDevice();
        telemetry.addData("Position", "target " + finalLocation);
        telemetry.update();
        sleep(2000);


        //and this is just checking which value is finalLocation, and moving to the corresponding location


        rob.forward(1000);
    }
}
