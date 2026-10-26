package org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap;
;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Shooter {

    // Tells the code what the shooter motors are called in the robot config

    private DcMotorEx shooter_l;
    private DcMotorEx shooter_r;

    public void init (HardwareMap hwMap) {

        // Tells what the shooter motors will be called in the code

        shooter_l = hwMap.get(DcMotorEx.class, "shooter_l");
        shooter_r = hwMap.get(DcMotorEx.class, "shooter_r");

        // Sets whether or not the shooters use encoders and the PID coefficients

        shooter_l.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(326.3326, 0.7611, 0, 16.3166));
        shooter_r.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(326.3326, 0.7611, 0, 16.3166));

        // Sets what direction the shooters motors move

        shooter_r.setDirection(DcMotorSimple.Direction.FORWARD);
        shooter_l.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    // Gives the shooter power
    public void setVelocity(double power) {
        shooter_r.setVelocity(power);
        shooter_l.setVelocity(power);
    }
    public double getVelocity() {
        return shooter_r.getVelocity();
    }
}
