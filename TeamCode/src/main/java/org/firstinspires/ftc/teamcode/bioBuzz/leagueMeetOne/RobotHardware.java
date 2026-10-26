package org.firstinspires.ftc.teamcode.bioBuzz.leagueMeetOne;

import com.qualcomm.robotcore.hardware.*;

public class RobotHardware {

    // Tells the program that the motors for the wheels, intake, transfer, and shooter exist
    private DcMotor fr;
    private DcMotor fl;
    private DcMotor rr;
    private DcMotor rl;

    private DcMotor intake;
    private DcMotor transfer;
    private DcMotorEx shooter_r;
    private DcMotorEx shooter_l;

    //private Servo flicker;

    // Runs on init. The HardwareMap parameter type is called hwMap inside the code.
    public void init(HardwareMap hwMap) {

        // Tells the code what the motors are called in the control/driver hub config
        fr = hwMap.get(DcMotor.class, "fr");
        fl = hwMap.get(DcMotor.class, "fl");
        rr = hwMap.get(DcMotor.class, "rr");
        rl = hwMap.get(DcMotor.class, "rl");

        intake = hwMap.get(DcMotor.class, "intake");
        transfer = hwMap.get(DcMotor.class, "transfer");
        shooter_r = hwMap.get(DcMotorEx.class, "shooter_r");
        shooter_l = hwMap.get(DcMotorEx.class, "shooter_l");

        //flicker = hwMap.get(Servo.class, "flicker");

        // Tells the motors whether to run using the encoder or not.
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transfer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooter_l.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(326.3326, 0.7611, 0, 16.3166));
        shooter_r.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(326.3326, 0.7611, 0, 16.3166));

        // Tells the code what direction to run the motors in
        rl.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        rr.setDirection(DcMotorSimple.Direction.FORWARD);

        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        transfer.setDirection(DcMotorSimple.Direction.FORWARD);
        shooter_r.setDirection(DcMotorSimple.Direction.FORWARD);
        shooter_l.setDirection(DcMotorSimple.Direction.REVERSE);

        // Brake the drive motors when power is zero, so the robot stops instead of coasting
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //flicker.setDirection(Servo.Direction.FORWARD);
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

    // Sets each wheel's power individually (used for motor testing)
    public void setWheelPowers(double flPower, double frPower, double rlPower, double rrPower) {
        fl.setPower(flPower);
        fr.setPower(frPower);
        rl.setPower(rlPower);
        rr.setPower(rrPower);
    }

    // Gives the intake power
    public void setIntakePower(double power) {
        intake.setPower(power);
    }

    // Gives the transfer power
    public void setTransferPower(double power) {
        transfer.setPower(power);
    }

    // Gives the shooter power
    public void setShooterVelocity(double power) {
        shooter_r.setVelocity(power);
        shooter_l.setVelocity(power);
    }

    public double getShooterVelocity() {
        return shooter_r.getVelocity();
    }

    /*
    // Gives the flicker power

    public void setFlickerPosition(double position) {

    flicker.setPosition(position);

    }
     */
}