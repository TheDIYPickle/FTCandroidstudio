package org.firstinspires.ftc.teamcode.CVPipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class CopyPipelineRed extends OpenCvPipeline
{
    boolean viewportPaused;

    /*
     * NOTE: if you wish to use additional Mat objects in your processing pipeline, it is
     * highly recommended to declare them here as instance variables and re-use them for
     * each invocation of processFrame(), rather than declaring them as new local variables
     * each time through processFrame(). This removes the danger of causing a memory leak
     * by forgetting to call mat.release(), and it also reduces memory pressure by not
     * constantly allocating and freeing large chunks of memory.
     */
    Telemetry telemetry;
    public CopyPipelineRed(Telemetry t) {telemetry=t;};
    Mat mat = new Mat();
    Mat secMat= new Mat();
    Mat thirdMat= new Mat();
    Mat fourMat= new Mat();
    public enum Colors{
        Color1,
        Color2,
        Color3
    }
    private Colors colors;
    static final Rect target_BOX = new Rect(
            new Point(150, 120),
            new Point(210,140));
    @Override
    public Mat processFrame(Mat input)
    {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HLS);
        Imgproc.cvtColor(input, secMat, Imgproc.COLOR_RGB2HLS);
        Imgproc.cvtColor(input, thirdMat, Imgproc.COLOR_RGB2HLS);
        Imgproc.cvtColor(input, fourMat, Imgproc.COLOR_RGB2HLS);
        Scalar lowHSLRedLow = new Scalar(0,44,0);
        Scalar lowHSLRedHigh = new Scalar(23,62,90);

        Scalar lowHSLGreen= new Scalar(56,0,83);
        Scalar highHSLGreen= new Scalar(81,226,255);

        Scalar lowHSLBlue = new Scalar(83,3,100);
        Scalar highHSLBlue = new Scalar(166,205,255);



        Scalar highHSLRedLow = new Scalar(179,215,245);
        Scalar highHSLRedHigh = new Scalar(179,255,255);






        Scalar lowTest = new Scalar(0,0,0);
        Scalar highTest= new Scalar(255,255,255);
        Mat target = mat.submat(target_BOX);
        Mat targetSec= secMat.submat(target_BOX);
        Mat targetThir= thirdMat.submat(target_BOX);
        Mat targetFour = fourMat.submat(target_BOX);
        //input.release();



        //first color test part one
        Core.inRange(mat, lowHSLRedLow, lowHSLRedHigh, mat);
        //target = mat.submat(target_BOX);
        //This finds which percent of the screen is whatever color we want
        double targetValOne= Core.sumElems(target).val[0] / target_BOX.area() / 255;
        //telemetry.addData("Target box value", (int) Core.sumElems(target).val[0]);
        target.release();
        //mat.release();




        //second color test
        Core.inRange(secMat, lowHSLBlue, highHSLBlue, secMat);
        targetSec = secMat.submat(target_BOX);
        //This finds which percent of the screen is whatever color we want
        double targetValTwo = Core.sumElems(targetSec).val[0] / target_BOX.area() / 255;
        //telemetry.addData("Target box value", (int) Core.sumElems(targetSec).val[0]);
        targetSec.release();
        //secMat.release();

        //telemetry.addData("Second Color percent", Math.round(targetValTwo*100) + "%");

        //third color test
        Core.inRange(thirdMat, lowHSLGreen, highHSLGreen, thirdMat);
        targetThir = thirdMat.submat(target_BOX);
        //This finds which percent of the screen is whatever color we want
        double targetValThree = Core.sumElems(targetThir).val[0] / target_BOX.area() / 255;
        //telemetry.addData("Target box value", (int) Core.sumElems(targetThir).val[0]);
        targetThir.release();
        //thirdMat.release();
        //telemetry.addData("Third percent", Math.round(targetValThree*100) + "%");

        //first color test part two
        Core.inRange(fourMat, highHSLRedLow, highHSLRedHigh, fourMat);
        targetFour = mat.submat(target_BOX);
        //This finds which percent of the screen is whatever color we want
        double targetValOneHalf= Core.sumElems(targetFour).val[0] / target_BOX.area() / 255;
        //telemetry.addData("Target box value", (int) Core.sumElems(target).val[0]);
        targetFour.release();
        fourMat.release();

        //double secTargetValOne=targetValOne+targetValOneHalf;
        targetValOne = targetValOne+targetValOneHalf;
        //targetValOne=0;

        telemetry.addData("First Color percent", Math.round(targetValOne*100) + "%");
        telemetry.addData("Second Color percent", Math.round(targetValTwo*100) + "%");
        telemetry.addData("Third percent", Math.round(targetValThree*100) + "%");
        telemetry.addData("Fourth percent", Math.round(targetValOneHalf*100)+"%");
        //telemetry.addData("Fourth percent", Math.round(secTargetValOne*100)+"%");
        telemetry.update();


        double greaterNumberOne = Math.max(targetValOne, targetValTwo);
        double greaterNumberTwo = Math.max(greaterNumberOne, targetValThree);
        Mat cameraMat = new Mat();

        if(targetValOne==greaterNumberTwo){
            colors= Colors.Color1;
            cameraMat=mat;
            telemetry.addData("Color found", "color one");
            mat.release();
            secMat.release();
            thirdMat.release();

        }
        else if(targetValTwo==greaterNumberTwo){
            colors= Colors.Color2;
            cameraMat=secMat;
            telemetry.addData("Color found", "color two");
            mat.release();
            secMat.release();
            thirdMat.release();

        }
        else if(targetValThree==greaterNumberTwo){
            colors= Colors.Color3;
            cameraMat=thirdMat;
            telemetry.addData("Color found", "color three");
            mat.release();
            secMat.release();
            thirdMat.release();

        }

        cameraMat.release();
        //Imgproc.cvtColor(cameraMat, cameraMat, Imgproc.COLOR_GRAY2RGB);

        //Scalar targetBox= new Scalar(255,0,0);

        //Imgproc.rectangle(cameraMat, target_BOX, targetBox);

        return input;

    }
    public Colors ColorType(){

        return colors;
    }
}
