package org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap;

import com.qualcomm.robotcore.hardware.*;
public class Intake {

    private DcMotor intake;

    public void init(HardwareMap hwMap) {

        // Tells the code what the intake will be called

        intake = hwMap.get(DcMotor.class, "intake");

        // Sets whether or not the intake runs with the encoder

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Sets the direction the motor is mounted

        intake.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    // Gives the intake power

    public void setPower(double power) {
        intake.setPower(power);
    }

}
