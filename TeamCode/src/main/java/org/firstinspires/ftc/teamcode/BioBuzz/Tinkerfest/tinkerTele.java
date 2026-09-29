package org.firstinspires.ftc.teamcode.BioBuzz.Tinkerfest;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.BioBuzz.RobotHardware;

@TeleOp (name = "Tinker TeleOp")
public class tinkerTele extends OpMode {
    private RobotHardware robot = new RobotHardware();

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {

        // Drivetrain
        double y = -gamepad1.left_stick_y; // forward/backward (stick is inverted by default)
        double x = gamepad1.left_stick_x;  // strafe
        double r = gamepad1.right_stick_x; // rotation
        robot.drive(y, x, r);

        // Intake

        if (gamepad2.right_trigger > 0.1) {
            robot.setIntakePower(.60);
        } else if (gamepad2.left_trigger > 0.1) {
            robot.setIntakePower(-.60);
        } else {
            robot.setIntakePower(0.0);
        }
    }
}