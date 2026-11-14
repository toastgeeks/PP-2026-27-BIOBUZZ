package org.firstinspires.ftc.teamcode.bioBuzz.leagueMeetTwo;

import com.pedropathing.api.Paths;

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
import org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap.*;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "Red Shoot Only", group = "Autonomous")
public class RedShootOnlyAuto extends LinearOpMode {
    private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();
    private final Transfer transfer = new Transfer();
    private final Flicker flicker = new Flicker();

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56.2307, 8.8459, -90);
    private final Pose park = poseFactory.of(10.7815, 106.75, 0);
    private final Pose parkControl1 = poseFactory.of(17.4154, 54.3738, 0);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                instant(() -> shooter.setVelocity(1150.0)),
                instant(() -> intake.setPower(1.0)),
                instant(() -> transfer.setPower(1.0)),
                waitMs(700),
                instant(() -> shooter.setVelocity(0.0)),
                instant(() -> intake.setPower(0.0)),
                instant(() -> transfer.setPower(0.0)),
                follow(follower, park())
        );
    }

    @Override
    public void runOpMode() {
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

    public Path park() {
        return Paths.curve(start, parkControl1, park).linear(start, park);
    }
}
