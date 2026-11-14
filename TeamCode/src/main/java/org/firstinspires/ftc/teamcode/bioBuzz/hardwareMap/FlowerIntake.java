package org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap;

import com.qualcomm.robotcore.hardware.*;
public class FlowerIntake {

    // Limits for the servo's position (each must be between 0.0 and 1.0) - set these to your mechanism's safe range
    private static final double MIN_POSITION = 0.0;
    private static final double MAX_POSITION = 1.0;

    private Servo flowerIntake;

    public void init(HardwareMap hwMap) {

        // Tells the code what the flower intake will be called

        flowerIntake = hwMap.get(Servo.class, "flowerIntake");

        // Sets the direction the servo is mounted

        flowerIntake.setDirection(Servo.Direction.FORWARD);
    }

    // Moves the flower intake to a position, kept between MIN_POSITION and MAX_POSITION

    public void setPosition(double position) {
        flowerIntake.setPosition(Math.max(MIN_POSITION, Math.min(MAX_POSITION, position)));
    }
}