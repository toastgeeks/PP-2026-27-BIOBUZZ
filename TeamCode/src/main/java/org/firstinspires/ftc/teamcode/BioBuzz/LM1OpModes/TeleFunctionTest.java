package org.firstinspires.ftc.teamcode.BioBuzz.LM1OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.BioBuzz.RobotHardware;

/*
Diagnostic OpMode for testing each motor individually.
Uses RobotHardware for all motor access, so it tests the same config names,
directions, and methods your real TeleOp and autos use.

 Gamepad 1 (hold a D-pad button to spin that wheel):
   D-pad up    = front-left
   D-pad right = front-right
   D-pad left  = rear-left
   D-pad down  = rear-right

 Gamepad 2:
   Right trigger = intake forward    Left trigger = intake backward (speed follows trigger pull)
   Right bumper  = transfer forward  Left bumper  = transfer backward
   A = shooter on at 1.0             B = shooter off (0.0)
*/
@TeleOp(name = "Function Test")
public class TeleFunctionTest extends OpMode {

    // Power used for the drivetrain motors while a D-pad button is held
    private static final double DRIVE_TEST_POWER = 0.5;

    private final RobotHardware robot = new RobotHardware();

    private double shooterPower = 0.0;

    @Override
    public void init() {
        robot.init(hardwareMap);

        telemetry.addLine("Gamepad 1: \n Up Arrow - Front Left Motor \n Right Arrow - Front Right Motor \n Left Arrow - Back Left Motor \n Down Arrow - Back Right Motor");
        telemetry.addLine("Gamepad 2: \n Triggers - Intake \n Bumpers - Transfer \n X = Shooter On \n O = Shooter Off \n Square - Flicker");
        telemetry.update();
    }

    @Override
    public void loop() {

        // Drivetrain — each wheel runs only while its D-pad button is held
        // Argument order: front-left, front-right, rear-left, rear-right
        robot.setWheelPowers(
                gamepad1.dpad_up ? DRIVE_TEST_POWER : 0.0,
                gamepad1.dpad_right ? DRIVE_TEST_POWER : 0.0,
                gamepad1.dpad_left ? DRIVE_TEST_POWER : 0.0,
                gamepad1.dpad_down ? DRIVE_TEST_POWER : 0.0
        );

        // Intake — right trigger forward, left trigger backward
        robot.setIntakePower(gamepad2.right_trigger - gamepad2.left_trigger);

        // Transfer — right bumper forward, left bumper backward
        if (gamepad2.right_bumper) {
            robot.setTransferPower(1.0);
        } else if (gamepad2.left_bumper) {
            robot.setTransferPower(-1.0);
        } else {
            robot.setTransferPower(0.0);
        }

        // Shooter — A turns it on at 1.0, B turns it off; it stays at that power until changed
        if (gamepad2.a) {
            shooterPower = 1.0;
        } else if (gamepad2.b) {
            shooterPower = 0.0;
        }
        robot.setShooterPower(shooterPower);

          /*
        // Flicker
        if (gamepad2.x) {
            setFlickerPosition(0.4);
        } else {
            setFlickerPosition(0.0);
        }
         */

    }


    @Override
    public void stop() {
        robot.setWheelPowers(0.0, 0.0, 0.0, 0.0);
        robot.setIntakePower(0.0);
        robot.setTransferPower(0.0);
        robot.setShooterPower(0.0);
        //robot.setFlickerPosition(0.0);
    }
}