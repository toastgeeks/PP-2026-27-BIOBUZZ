package org.firstinspires.ftc.teamcode.BioBuzz.LM1OpModes;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.BioBuzz.RobotHardware;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "Red Auto", group = "Autonomous")
public class RedAuto extends LinearOpMode {

    private Follower follower;

    private final RobotHardware robot = new RobotHardware();

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(57.7131, 9.1992, -90);
    private final Pose shootPreload = poseFactory.of(57.7131, 9.1992, 90);
    private final Pose park = poseFactory.of(10.9377, 97.6017, 0);
    private final Pose parkControl1 = poseFactory.of(19.2721, 38.2358, 0);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                instant(() -> robot.setShooterVelocity(1150)),
                waitMs(1000.0),
                instant(() -> robot.setIntakePower(1.0)), // let the shooter reach speed
                instant(() -> robot.setTransferPower(1.0)),
                waitMs(1000.0),                                   // feed the ball
                instant(() -> robot.setTransferPower(0.0)),
                instant(() -> robot.setShooterVelocity(0)),
                instant(() -> robot.setIntakePower(0.0)),
                follow(follower, park())
        );
    }

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path shootPreload() {
        return line(start, shootPreload).linear(start, shootPreload);
    }

    public Path park() {
        return curve(shootPreload, parkControl1, park).linear(shootPreload, park);
    }
}
