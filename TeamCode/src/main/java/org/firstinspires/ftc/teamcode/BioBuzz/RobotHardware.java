package org.firstinspires.ftc.teamcode.BioBuzz;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo; // Needs to be in angular not continuous
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RobotHardware {


    // Tells the program that the motors for the wheels, intake, and slides exist
    private DcMotor fr;
    private DcMotor fl;
    private DcMotor rr;
    private DcMotor rl;

    private DcMotor intake;

    private DcMotor shooter;
    private Servo shooterFeed;

    // Shooter feed servo positions — tune these to your mechanism
    private static final double SHOOTER_FEED_ZERO = 0.0;
    private static final double SHOOTER_FEED_PUSH = 0.6; // Placeholder, tune on robot
    private static final double SHOOTER_FEED_PUSH_TIME_MS = 250; // time before returning to zero, placeholder, tune on robot

    private final ElapsedTime shooterFeedTimer = new ElapsedTime();
    private boolean shooterFeedPushing = false;


    // Runs on init. The HardwareMap parameter type is called hwMap inside the code.
    public void init(HardwareMap hwMap) {

        // Tells the code what the motors are called in the control/driver hub config
        fr = hwMap.get(DcMotor.class, "rf");
        fl = hwMap.get(DcMotor.class, "fl");
        rr = hwMap.get(DcMotor.class, "rr");
        rl = hwMap.get(DcMotor.class, "rl");

        intake = hwMap.get(DcMotor.class, "intake");

        shooter = hwMap.get(DcMotor.class, "shooter");
        shooterFeed = hwMap.get(Servo.class, "shooterFeed");

        // Tells the motors whether to run using the encoder or not.
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Tells the code what direction to run the motors in
        rl.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        rr.setDirection(DcMotorSimple.Direction.FORWARD);

        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        shooter.setDirection(DcMotorSimple.Direction.FORWARD);

        // Start the feed servo at its zero position
        shooterFeed.setPosition(SHOOTER_FEED_ZERO);
    }

    // Sets the direction of forward and backwards (y), strafing (x), and rotation (r)
    public void drive(double y, double x, double r) {
        double frPower = y - x - r;
        double flPower = y + x + r;
        double rrPower = y + x - r;
        double rlPower = y - x + r;

        // Tells each wheel the power they need
        double max = Math.max(1.0, Math.max(Math.abs(frPower),
                Math.max(Math.abs(flPower), Math.max(Math.abs(rrPower), Math.abs(rlPower)))));

        // Actually gives the wheels power
        fr.setPower(frPower / max);
        fl.setPower(flPower / max);
        rr.setPower(rrPower / max);
        rl.setPower(rlPower / max);
    }

    // Gives the intake power
    public void setIntakePower(double power) {
        intake.setPower(power);
    }

    public void setShooterPower(double power) {
        shooter.setPower(power);
    }

    // Call this once when the feed button is pressed (already debounced by the caller).
    // Pushes the servo forward; updateShooterFeed() will bring it back to zero after the delay.
    public void feedShooter() {
        if (!shooterFeedPushing) {
            shooterFeed.setPosition(SHOOTER_FEED_PUSH);
            shooterFeedTimer.reset();
            shooterFeedPushing = true;
        }
    }

    // Call this every loop iteration (TeleOp and Autonomous) so the servo returns to zero on time.
    public void updateShooterFeed() {
        if (shooterFeedPushing && shooterFeedTimer.milliseconds() > SHOOTER_FEED_PUSH_TIME_MS) {
            shooterFeed.setPosition(SHOOTER_FEED_ZERO);
            shooterFeedPushing = false;
        }
    }
}