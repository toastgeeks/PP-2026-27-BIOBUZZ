package org.firstinspires.ftc.teamcode.BioBuzz.Tinkerfest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.BioBuzz.RobotHardware;

@Autonomous (name = "Tinker Auto")
public class tinkerAuto extends LinearOpMode {

    private RobotHardware robot = new RobotHardware();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        // Waits here until the driver presses PLAY
        waitForStart();

        if (opModeIsActive()) {

            // Strafe left at full power (y = 0, x = -1, r = 0)
            robot.drive(0.0, -1.0, 0.0);

            sleep(250); // run for 1 quarter second

            // Stop all drive motors
            robot.drive(0.0, 0.0, 0.0);
        }
    }
}