package org.firstinspires.ftc.teamcode.CVPipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class CopyPipeline extends OpenCvPipeline
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
    public CopyPipeline(Telemetry t) {telemetry=t;};
    Mat mat = new Mat();
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
        //Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HLS);
        mat=input;
        Scalar lowHSVOne = new Scalar(0,72,138);
        Scalar lowHSVTwo = new Scalar(78,3,120);
        Scalar lowHSVThree= new Scalar(11,0,83);
        Scalar highHSVOne = new Scalar(27,205,255);
        Scalar highHSVTwo = new Scalar(114,152,255);
        Scalar highHSVThree= new Scalar(81,226,255);
        Scalar lowTest = new Scalar(0,0,0);
        Scalar highTest= new Scalar(255,255,255);

        //first color test
        //Core.inRange(mat, lowTest, highTest, mat);


        //Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Scalar targetBox= new Scalar(255,0,0);

        Imgproc.rectangle(mat, target_BOX, targetBox);

        return mat;

    }

}
