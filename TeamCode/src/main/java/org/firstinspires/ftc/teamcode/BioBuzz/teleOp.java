package org.firstinspires.ftc.teamcode.BioBuzz;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "LM1 TeleOp")
public class teleOp extends OpMode {

    private RobotHardware hwMap = new RobotHardware();

    private boolean shooterOn = true;
    private double shooterPower = 0.0;

    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;
    private double lastNonZeroPower = 0.0;
    private boolean lastXButton = false;

    @Override
    public void init() {
        hwMap.init(hardwareMap);
    }

    @Override
    public void loop() {

        // Drivetrain
        double y = -gamepad1.left_stick_y; // forward/backward (stick is inverted by default)
        double x = gamepad1.left_stick_x;  // strafe
        double r = gamepad1.right_stick_x; // rotation
        hwMap.drive(y, x, r);

        //Intake
        hwMap.setIntakePower(gamepad2.right_trigger - gamepad2.left_trigger);

            // Turn shooter off
            if (gamepad2.b) {
                shooterOn = false;
            }


            // Resume shooter at last non-zero power
            boolean xButton = gamepad2.a;
            if (xButton && !lastXButton) {
                shooterOn = true;
                shooterPower = lastNonZeroPower;
            }
            lastXButton = xButton;

            // Adjust shooter power — one increment per press, not per loop
            boolean dpadUp = gamepad2.dpad_up;
            boolean dpadDown = gamepad2.dpad_down;

            if (dpadUp && !lastDpadUp) {
                shooterPower += 0.05;
            } else if (dpadDown && !lastDpadDown) {
                shooterPower -= 0.05;
            }
            lastDpadUp = dpadUp;
            lastDpadDown = dpadDown;

            // Clamp so it stays in a valid range
            shooterPower = Math.max(0.0, Math.min(1.0, shooterPower));

            // Remember the last power that wasn't zero, so "x" has something to restore
            if (shooterPower > 0.0) {
                lastNonZeroPower = shooterPower;
            }

            hwMap.setShooterPower(shooterOn ? shooterPower : 0.0);

            // Transfer

            if (gamepad2.right_bumper) {
                hwMap.setTransferPower(1.0);
            } else if  (gamepad2.left_bumper) {
                hwMap.setTransferPower(-1.0);
            }
        }
    }