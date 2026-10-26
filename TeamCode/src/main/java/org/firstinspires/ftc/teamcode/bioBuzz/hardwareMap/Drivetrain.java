package org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap;

import com.qualcomm.robotcore.hardware.*;

public class Drivetrain {

    // Tells the code what the wheel motors are named in the robot config
    private DcMotor fr;
    private DcMotor fl;
    private DcMotor rr;
    private DcMotor rl;

    public void init(HardwareMap hwMap) {

        // Tells the code what the wheel motors will be called in the code

        fr = hwMap.get(DcMotor.class, "fr");
        fl = hwMap.get(DcMotor.class, "fl");
        rr = hwMap.get(DcMotor.class, "rr");
        rl = hwMap.get(DcMotor.class, "rl");

        // Sets whether or not the wheels use encoders

        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Sets the direction of the wheel motors

        rl.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        rr.setDirection(DcMotorSimple.Direction.FORWARD);

        // Sets the motors to brake when there is no power being applied to the motors

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    // Sets the direction of forward and backwards (y), strafing (x), and rotation (r)

    public void drive(double y, double x, double r) {
        double frPower = y - x - r;
        double flPower = y + x + r;
        double rrPower = y + x - r;
        double rlPower = y - x + r;

        // Scale all four powers down together if any exceeds 1.0

        double max = Math.max(1.0, Math.max(Math.abs(frPower),
                Math.max(Math.abs(flPower), Math.max(Math.abs(rrPower), Math.abs(rlPower)))));

        // Actually gives the wheels power

        fr.setPower(frPower / max);
        fl.setPower(flPower / max);
        rr.setPower(rrPower / max);
        rl.setPower(rlPower / max);
    }

    // Sets each wheel's power individually. Used for motor testing.

    public void setWheelPowers(double flPower, double frPower, double rlPower, double rrPower) {
        fl.setPower(flPower);
        fr.setPower(frPower);
        rl.setPower(rlPower);
        rr.setPower(rrPower);

    }
}
