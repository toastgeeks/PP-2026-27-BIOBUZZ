package org.firstinspires.ftc.teamcode.BioBuzz;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "Carson DT TeleOp")
public class CarsonTeleOp extends OpMode {
    private RobotHardware robot = new RobotHardware();

    // Used to debounce the shooter feed button so one press = one feed cycle
    private boolean lastFeedButton = false;

    // Used to debounce the dpad so one press = one increment
    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;

    private double shooterPower = 0.0;

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {

        // Shooter power adjustment — one increment per press, not per loop
        boolean dpadUp = gamepad2.dpad_up;
        boolean dpadDown = gamepad2.dpad_down;

        if (dpadUp && !lastDpadUp) {
            shooterPower += 0.05;
            System.out.println(shooterPower);
        } else if (dpadDown && !lastDpadDown) {
            shooterPower -= 0.05;
        }
        lastDpadUp = dpadUp;
        lastDpadDown = dpadDown;

        if (gamepad2.b) shooterPower = 0;

        // Clamp so it stays in a valid, predictable range
        shooterPower = Math.max(0.0, Math.min(1.0, shooterPower));

        robot.setShooterPower(shooterPower);

        // Shooter feed servo — single button press feeds once
        boolean feedButton = gamepad1.right_bumper;
        if (feedButton && !lastFeedButton) {
            robot.feedShooter();
        }
        lastFeedButton = feedButton;

        // Must be called every loop so the feed servo returns to zero on time
        robot.updateShooterFeed();

        // Drivetrain
        double y = -gamepad1.left_stick_y; // forward/backward (stick is inverted by default)
        double x = gamepad1.left_stick_x;  // strafe
        double r = gamepad1.right_stick_x; // rotation
        robot.drive(y, x, r);
    }
}