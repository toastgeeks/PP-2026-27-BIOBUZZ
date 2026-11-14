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
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap.Intake;
import org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap.Shooter;
import org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap.Transfer;
import org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap.Flicker;
import org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap.FlowerIntake;

@Autonomous(name = "Solo Auto", group = "Autonomous")
public class LM2Auto extends LinearOpMode {

    private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();
    private final Transfer transfer = new Transfer();
    private final Flicker flicker = new Flicker();
    private final FlowerIntake flowerIntake = new FlowerIntake();

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56.2307, 8.8459, -90);
    private final Pose start_2 = poseFactory.of(11.3967, 47.3815, 180);
    private final Pose start_2Control1 = poseFactory.of(22.3016, 27.2964, 0);
    private final Pose shootStart = poseFactory.of(11.3967, 47.3815, 180);
    private final Pose shoot = poseFactory.of(61.4806, 131.795, -90);
    private final Pose shootControl1 = poseFactory.of(39.7528, 112.5425, 0);
    private final Pose intake1Start = poseFactory.of(61.4806, 131.795, -90);
    private final Pose intake1 = poseFactory.of(47.6736, 130.0089, 90);
    private final Pose intake1Control1 = poseFactory.of(59.0037, 104.3404, 0);
    private final Pose shoot2Start = poseFactory.of(47.6736, 130.0089, 90);
    private final Pose shoot2 = poseFactory.of(61.5288, 131.7801, -90);
    private final Pose shoot2Control1 = poseFactory.of(58.8434, 104.4591, 0);
    private final Pose parkStart = poseFactory.of(61.5288, 131.7801, -90);
    private final Pose park = poseFactory.of(11.3772, 113.1031, 180);
    private final Pose parkControl1 = poseFactory.of(56.1025, 103.4535, 0);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                instant(() -> shooter.setVelocity(1150.0)),
                waitMs(550),
                instant(() -> intake.setPower(1.0)),
                instant(() -> transfer.setPower(1.0)),
                waitMs(1000),
                instant(() -> shooter.setVelocity(0.0)),
                instant(() -> intake.setPower(0.0)),
                instant(() -> transfer.setPower(0.0)),
                instant(() -> flowerIntake.setPosition(5)), // Change the amount of degrees

                follow(follower, startShoot()),
                instant(() -> intake.setPower(1)),
                instant(() -> transfer.setPower(1)),
                waitMs(200),
                instant(() -> transfer.setPower(0.0)),
                waitMs(200),
                instant(() -> intake.setPower(0.0)),

                follow(follower, shoot()),
                instant(() -> flowerIntake.setPosition(45)), // Change the amount of degrees
                instant(() -> shooter.setVelocity(1150.0)),
                waitMs(550),
                instant(() -> intake.setPower(1.0)),
                instant(() -> transfer.setPower(1.0)),
                waitMs(1000),
                instant(() -> shooter.setVelocity(0.0)),
                instant(() -> intake.setPower(0.0)),
                instant(() -> transfer.setPower(0.0)),
                instant(() -> flowerIntake.setPosition(5)),

                follow(follower, intake1()),
                instant(() -> intake.setPower(1)),
                instant(() -> transfer.setPower(1)),
                waitMs(200),
                instant(() -> transfer.setPower(0.0)),
                waitMs(200),
                instant(() -> intake.setPower(0.0)),
                instant(() -> flowerIntake.setPosition(45)),

                follow(follower, shoot2()),
                instant(() -> shooter.setVelocity(1150.0)),
                waitMs(550),
                instant(() -> intake.setPower(1.0)),
                instant(() -> transfer.setPower(1.0)),
                waitMs(1000),
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

    public Path startShoot() {
        return Paths.curve(start, start_2Control1, start_2).linear(start, start_2);
    }

    public Path shoot() {
        return Paths.curve(shootStart, shootControl1, shoot).linear(shootStart, shoot);
    }

    public Path intake1() {
        return Paths.curve(intake1Start, intake1Control1, intake1).linear(intake1Start, intake1);
    }

    public Path shoot2() {
        return Paths.curve(shoot2Start, shoot2Control1, shoot2).linear(shoot2Start, shoot2);
    }

    public Path park() {
        return Paths.curve(parkStart, parkControl1, park).linear(parkStart, park);
    }
}
