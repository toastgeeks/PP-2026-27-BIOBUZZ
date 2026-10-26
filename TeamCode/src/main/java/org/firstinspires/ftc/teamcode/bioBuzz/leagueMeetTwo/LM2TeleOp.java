package org.firstinspires.ftc.teamcode.bioBuzz.leagueMeetTwo;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap.Drivetrain;
import org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap.Intake;
import org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap.Shooter;
import org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap.Transfer;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap.Flicker;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;

@TeleOp(name = "LM2 TeleOp")
public class LM2TeleOp extends OpMode {

    private final Drivetrain robot = new Drivetrain();
    private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();
    private final Transfer transfer = new Transfer();
    private final Flicker flicker = new Flicker();

    private boolean shooterOn = false;
    private double shooterPower = 1150.0;

    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;
    private double lastNonZeroPower = 0.0;
    private boolean lastAButton = false;

    private Follower follower;


    @Override
    // I could use telemetry.addLine(""); to give instructions on what buttons do
    public void init() {
        robot.init(hardwareMap);
        follower = Constants.create(hardwareMap);
    }

    @Override
    public void loop() {

        // Drivetrain
        double y = -gamepad1.left_stick_y; // forward/backward (stick is inverted by default)
        double x = gamepad1.left_stick_x;  // strafe
        double r = gamepad1.right_stick_x; // rotation
        robot.drive(y, x, r);


        /*
        // Drivetrain
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                follower.pose().heading()
        );


        // relocalise button
        if (gamepad1.x) {
            Pose cornerPose = new Pose(10.5, 10.5,  Math.toRadians(90));
            // On the fly Pose creation, we dont recommend this for Autonomous. Only accepts radians for heading
            follower.setPose(cornerPose); // overrides our pose
        }

        ManualDrive.driveOrHold(follower, powers);
        follower.update();
        Pose robotPose = follower.pose(); // returns a Pose object

        telemetry.addData("Robot X", robotPose.x());
        telemetry.addData("Robot Y", robotPose.y());
        telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading()));
        // Math.toDegrees() is a built-in java method
         */

        //Intake
        intake.setPower(gamepad2.right_trigger - gamepad2.left_trigger);

        // Turn shooter off
        if (gamepad2.b) {
            shooterOn = false;
        }


        // Resume shooter at last non-zero power
        boolean aButton = gamepad2.a;
        if (aButton && !lastAButton) {
            shooterOn = true;
            shooterPower = lastNonZeroPower;
        }
        lastAButton = aButton;

        // Adjust shooter power — one increment per press, not per loop
        boolean dpadUp = gamepad2.dpad_up;
        boolean dpadDown = gamepad2.dpad_down;

        if (dpadUp && !lastDpadUp) {
            shooterPower += 25.0;
            telemetry.addData("Shooter Power", shooterPower);
        } else if (dpadDown && !lastDpadDown) {
            shooterPower -= 25.0;
            telemetry.addData("Shooter Power", shooterPower);
        }
        lastDpadUp = dpadUp;
        lastDpadDown = dpadDown;

        // Clamp so it stays in a valid range
        shooterPower = Math.max(0.0, Math.min(1200.0, shooterPower));

        // Remember the last power that wasn't zero, so "a" has something to restore
        if (shooterPower > 0.0) {
            lastNonZeroPower = shooterPower;
        }
        shooter.setVelocity(shooterOn ? shooterPower : 0.0);
        telemetry.addData("Shooter Power", shooterPower);
        telemetry.addData("Current Shooter Velocity", shooter.getVelocity());

        // Transfer
        if (gamepad2.right_bumper) {
            transfer.setPower(1.0);
        } else if (gamepad2.left_bumper) {
            transfer.setPower(-1.0);
        } else {
            transfer.setPower(0.0);
        }

        // Flicker
        if (gamepad2.x) {
            flicker.setPosition(0.4);
        } else {
            flicker.setPosition(0.0);
        }
    }
}