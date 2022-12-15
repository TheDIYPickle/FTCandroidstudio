package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.teamcode.CVPipelines.CopyPipeline;
import org.firstinspires.ftc.teamcode.CVPipelines.CopyPipelineRed;
import org.firstinspires.ftc.teamcode.CVPipelines.TestPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "AutoMovement")

public class AutoMovement extends LinearOpMode {
    OpenCvCamera mainCam;
    String finalLocation = "0";
    boolean pictureVar = false;
    private DcMotor backleftDrive = null;
    private DcMotor backrightDrive = null;
    private DcMotor frontleftDrive = null;
    private DcMotor frontrightDrive = null;
    //private DcMotor Potato = null;
    private DcMotor spinnySpin = null;
    private Servo rightClaw = null;
    private Servo leftClaw = null;
    private DcMotor arm = null;
    private DcMotor counterWeight= null;
    //private CRServo lilPotato= null;
    //private CRServo boxcar = null;
    //private CRServo dropper = null;
    DcMotor led;
    public Gamepad g1;
    public Gamepad g2;
    double powerVar=0.3;
    public void Forward(Integer input){

        backleftDrive.setTargetPosition(backleftDrive.getCurrentPosition()+input);
        backrightDrive.setTargetPosition(backrightDrive.getCurrentPosition()+input);
        frontleftDrive.setTargetPosition(frontleftDrive.getCurrentPosition()+input);
        frontrightDrive.setTargetPosition(frontrightDrive.getCurrentPosition()+input);
    }
    public void Backward(Integer input){

        backleftDrive.setTargetPosition(backleftDrive.getCurrentPosition()-input);
        backrightDrive.setTargetPosition(backrightDrive.getCurrentPosition()-input);
        frontleftDrive.setTargetPosition(frontleftDrive.getCurrentPosition()-input);
        frontrightDrive.setTargetPosition(frontrightDrive.getCurrentPosition()-input);
    }
    public void Right(Integer input){

        backleftDrive.setTargetPosition(backleftDrive.getCurrentPosition()-input);
        backrightDrive.setTargetPosition(backrightDrive.getCurrentPosition()+input);
        frontleftDrive.setTargetPosition(frontleftDrive.getCurrentPosition()+input);
        frontrightDrive.setTargetPosition(frontrightDrive.getCurrentPosition()-input);
    }

    public void Left(Integer input){

        backleftDrive.setTargetPosition(backleftDrive.getCurrentPosition()+input);
        backrightDrive.setTargetPosition(backrightDrive.getCurrentPosition()-input);
        frontleftDrive.setTargetPosition(frontleftDrive.getCurrentPosition()-input);
        frontrightDrive.setTargetPosition(frontrightDrive.getCurrentPosition()+input);
    }

    public void RightTurn(Integer input){

        backleftDrive.setTargetPosition(backleftDrive.getCurrentPosition()+input);
        backrightDrive.setTargetPosition(backrightDrive.getCurrentPosition()-input);
        frontleftDrive.setTargetPosition(frontleftDrive.getCurrentPosition()+input);
        frontrightDrive.setTargetPosition(frontrightDrive.getCurrentPosition()-input);
    }

    public void LeftTurn(Integer input){

        backleftDrive.setTargetPosition(backleftDrive.getCurrentPosition()-input);
        backrightDrive.setTargetPosition(backrightDrive.getCurrentPosition()+input);
        frontleftDrive.setTargetPosition(frontleftDrive.getCurrentPosition()-input);
        frontrightDrive.setTargetPosition(frontrightDrive.getCurrentPosition()+input);
    }
    public void CloseClaw(){
        telemetry.addData("Status","close");
        telemetry.update();
        rightClaw.setPosition(1);
        leftClaw.setPosition(0.5);
        sleep(1000);
    }
    public void OpenClaw(){
        telemetry.addData("Status","open");
        telemetry.update();
        rightClaw.setPosition(0);
        leftClaw.setPosition(0);
        sleep(1000);
    }
    public void MoveArm(Integer input){
        arm.setTargetPosition(input);

        while(arm.getCurrentPosition() != input){
            telemetry.addData("Status",arm.getCurrentPosition());
            telemetry.update();
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        mainCam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        //TestPipeline pipeline = new TestPipeline(telemetry);
        CopyPipelineRed pipeline = new CopyPipelineRed(telemetry);
        CopyPipeline startPipeline= new CopyPipeline(telemetry);


        mainCam.setPipeline(startPipeline);
        backleftDrive = hardwareMap.get(DcMotor.class, "backleftDrive");
        backrightDrive = hardwareMap.get(DcMotor.class, "backrightDrive");
        frontleftDrive = hardwareMap.get(DcMotor.class, "frontleftDrive");
        frontrightDrive = hardwareMap.get(DcMotor.class, "frontrightDrive");
        //counterWeight = hardwareMap.get(DcMotor.class, "counterWeight");
        //Potato = hardwareMap.get(DcMotor.class, "Potato");

        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        arm = hardwareMap.get(DcMotor.class, "arm");
        //lilPotato = hardwareMap.crservo.get("lilPotato");



        backleftDrive.setDirection(DcMotor.Direction.REVERSE);
        backrightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontleftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontrightDrive.setDirection(DcMotor.Direction.FORWARD);
        //counterWeight.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);

        backleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //Wheels: Back left drive
        backleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleftDrive.setTargetPosition(0);
        backleftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //back right
        backrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backrightDrive.setTargetPosition(0);
        backrightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //front right
        frontrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontrightDrive.setTargetPosition(0);
        frontrightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //front left
        frontleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleftDrive.setTargetPosition(0);
        frontleftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Arm
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontleftDrive.setPower(powerVar);
        frontrightDrive.setPower(powerVar);
        backleftDrive.setPower(powerVar);
        backrightDrive.setPower(powerVar);
        arm.setPower(0.15);


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

        waitForStart();
        mainCam.setPipeline(pipeline);
        sleep(5000);
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
            Left(1300);
            sleep(2400);
            Backward(1200);
            sleep(2000);
        }
        else if(finalLocation=="2"){
            Left(1300);
            sleep(2400);
        }
        else if(finalLocation=="3"){
            Left(1300);
            sleep(2400);
            Forward(1000);
            sleep(2000);
        }




    }
}