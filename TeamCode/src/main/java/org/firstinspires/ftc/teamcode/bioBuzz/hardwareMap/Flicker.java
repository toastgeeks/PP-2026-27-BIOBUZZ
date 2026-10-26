package org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap;

import com.qualcomm.robotcore.hardware.*;

public class Flicker {

    // Tells the code what the flicker is called in the hardware map

    private Servo flicker;

    public void init (HardwareMap hwMap) {

        // Sets what the flicker will be called

        flicker =  hwMap.get(Servo.class, "flicker");

        // Sets the direction the flicker spins

        flicker.setDirection(Servo.Direction.FORWARD);
    }

    // Gives the flicker a position

    public void setPosition(double position) {
        flicker.setPosition(position);
    }
}
