package org.firstinspires.ftc.teamcode;

public class Toggle {
    public boolean state;
    public boolean prevState;

    public Toggle (boolean state) {
        this.state = state;
        this.prevState = state;
    }

    public boolean update(boolean condition) {
        if (condition && condition != prevState) {
            state = !state;
        }
        prevState = condition;

        return state;
    }

}
