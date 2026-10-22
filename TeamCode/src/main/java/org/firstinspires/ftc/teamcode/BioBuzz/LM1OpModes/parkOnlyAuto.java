package org.firstinspires.ftc.teamcode.BioBuzz.LM1OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.BioBuzz.RobotHardware;

@Autonomous (name = "Park Only")
public class parkOnlyAuto extends LinearOpMode {

    private RobotHardware robot = new RobotHardware();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        // Waits here until the driver presses PLAY
        waitForStart();

        if (opModeIsActive()) {

            // Go forward at full power for RP
            robot.drive(0.5, 0.0, 0.0);

            sleep(100); // Runs the auto for 1/10 of a second

            robot.drive(0.0, 0.0, 0.0); // Stops all drive motors

            // Strafe left at full power (y = 0, x = -1, r = 0)
            robot.drive(0.0, -1.0, 0.0);

            sleep(250); // run for 1 quarter second

            // Stop all drive motors
            robot.drive(0.0, 0.0, 0.0);
        }
    }
}