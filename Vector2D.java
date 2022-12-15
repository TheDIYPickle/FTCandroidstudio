package org.firstinspires.ftc.teamcode;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D rotateVector(double radians) {
        double x2 = x * Math.cos(radians) - y * Math.sin(radians);
        double y2 = x * Math.sin(radians) + y * Math.cos(radians);

        return new Vector2D(x2, y2);
    }
}
