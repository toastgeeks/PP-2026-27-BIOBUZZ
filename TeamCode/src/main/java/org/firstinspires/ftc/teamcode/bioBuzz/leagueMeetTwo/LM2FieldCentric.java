package org.firstinspires.ftc.teamcode.bioBuzz.leagueMeetTwo;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap.*;
import org.firstinspires.ftc.teamcode.pedro.Constants;


@TeleOp(name = "LM2 Field Centric")
public class LM2FieldCentric extends OpMode {

    private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();
    private final Transfer transfer = new Transfer();
    private final Flicker flicker = new Flicker();
    private final FlowerIntake flowerIntake = new FlowerIntake();

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
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
        transfer.init(hardwareMap);
        flicker.init(hardwareMap);
        flowerIntake.init(hardwareMap);
        follower = Constants.create(hardwareMap);
    }

    @Override
    public void loop() {

        // Drivetrain
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                follower.pose().heading()
        );

/*
        // relocalise button
        if (gamepad1.x) {
            Pose cornerPose = new Pose(10.5, 10.5, Math.toRadians(90));
            // On the fly Pose creation, we dont recommend this for Autonomous. Only accepts radians for heading
            follower.setPose(cornerPose); // overrides our pose
        }
*/

        ManualDrive.driveOrHold(follower, powers);
        follower.update();
        Pose robotPose = follower.pose(); // returns a Pose object

        telemetry.addData("Robot X", robotPose.x());
        telemetry.addData("Robot Y", robotPose.y());
        telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading()));
        // Math.toDegrees() is a built-in java method

        // Intake
        intake.setPower(gamepad2.right_trigger - gamepad2.left_trigger);

        // Flower Intake
        if (gamepad1.x) {
            flowerIntake.setPosition(0.31); // Sets the flower intake to be 5 degrees when square is being pressed
        } else if (gamepad1.y) {
            flowerIntake.setPosition(0.65); // Sets the flower intake to be 45 degrees when triangle is being pressed
        }

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
        shooterPower = Math.max(0.0, Math.min(1300.0, shooterPower));

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

        /*
        // Flicker
        if (gamepad2.x) {
            setFlickerPosition(0.4);
        } else {
            setFlickerPosition(0.0);
        }
         */

        telemetry.update();
    }
}