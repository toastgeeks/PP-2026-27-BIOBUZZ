//package org.firstinspires.ftc.teamcode.autos;
//
//import static com.pedropathing.api.Paths.*;
//
//import com.pedropathing.api.PoseFactory;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.math.Pose;
//import com.pedropathing.paths.Path;
//import com.pedropathing.ivy.Command;
//import com.pedropathing.ivy.Scheduler;
//import static com.pedropathing.ivy.Scheduler.schedule;
//import static com.pedropathing.ivy.commands.Commands.instant;
//import static com.pedropathing.ivy.commands.Commands.waitMs;
//import static com.pedropathing.ivy.groups.Groups.sequential;
//import static com.pedropathing.ivy.pedro.PedroCommands.follow;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import org.firstinspires.ftc.teamcode.BioBuzz.RobotHardware;
//import org.firstinspires.ftc.teamcode.pedro.Constants;
//
//@Autonomous(name = "Blue Other Team Doesn't Shoot", group = "Autonomous")
//public class blueAutoOtherTeamDoesntShoot extends LinearOpMode {
//    private Follower follower;
//    private final RobotHardware robot = new RobotHardware();
//
//    private final PoseFactory poseFactory = PoseFactory.degrees().mirrorX(70.75);
//
//    private final Pose shootPreload = poseFactory.of(56.5, 9.5, 90);
//    private final Pose shootPreload_2 = poseFactory.of(56.5, 9.5, 90);
//    private final Pose flowerIntake1 = poseFactory.of(12.2992, 47.4713, 180);
//    private final Pose shootFlower1 = poseFactory.of(58.2304, 127.146, -90);
//    private final Pose shootFlower1Control1 = poseFactory.of(18.2, 99.9, 0);
//    private final Pose flowerIntake2 = poseFactory.of(47.4364, 129.3559, 90);
//    private final Pose flowerIntake2Control1 = poseFactory.of(56.1199, 114.4159, 0);
//    private final Pose shootFlower2 = poseFactory.of(58.3705, 127.3257, -90);
//    private final Pose shootFlower2Control1 = poseFactory.of(56.0878, 114.5654, 0);
//    private final Pose park = poseFactory.of(10.8904, 115.3341, 0);
//    private final Pose parkControl1 = poseFactory.of(55.6423, 119.6622, 0);
//
//    // Autonomous routine
//    public Command autoRoutine() {
//        return sequential(
//
//                // Preload Shooting
//                follow(follower, shootPreload()),
//                instant(() -> robot.setShooterPower(1.0)), // Tune this value so that it doesn't shoot too far or too short
//                waitMs(1000.0), // Tune this value so that as soon as it is full speed, the transfer code runs
//                instant(() -> robot.setTransferPower(1.0)), // Tune this value to make sure that it does not run too fast
//                waitMs(1000.0),
//                instant(() -> robot.setTransferPower(0.0)),
//                instant(() -> robot.setShooterPower(0.0)), // Tune this value to make sure the robot leaves its position as soon as it is done shooting
//
//                // Intaking from flower
//                follow(follower, flowerIntake1()),
//                instant(() -> robot.setIntakePower(1.0)), // Tune this value to make sure the robot does not intake too fast
//                waitMs(500.0), // Tune this value to make sure it leaves as soon as it is done intaking
//                instant(() -> robot.setIntakePower(0.0)),
//
//                // Shooting what we intaked from the flower
//                follow(follower, shootFlower1()),
//                instant(() -> robot.setShooterPower(1.0)), // Tune this value to make sure that the robot does not shoot too far or too short
//                waitMs(1000.0), // Tune this value so that as soon as the intake is full speed, the transfer runs
//                instant(() -> robot.setTransferPower(1.0)), // Tune this value to make sure the transfer does not run too fast
//                waitMs(1000.0), // Tune this value to make sure the robot leaves its position as soon as it is done shooting
//                instant(() -> robot.setTransferPower(0.0)),
//                instant(() -> robot.setShooterPower(0.0)),
//
//                // Intaking from the flower a second time
//                follow(follower, flowerIntake2()),
//                instant(() -> robot.setIntakePower(1.0)), // Tune this value to make sure the intake does not run too fast
//                waitMs(500.0), // Tune this value to make sure the robot leaves the flower as soon as it is done shooting
//                instant(() -> robot.setIntakePower(0.0)),
//
//                // Shooting what we intaked from the flower the second time
//                follow(follower, shootFlower2()),
//                instant(() -> robot.setShooterPower(1.0)), // Tune this value to make sure the robot doesn't shoot too far or too short
//                waitMs(1000.0), // Tune this value to make sure that as soon as the shooter is full speed, the transfer runs
//                instant(() -> robot.setTransferPower(1.0)), // Make sure this doesn't run too fast
//                waitMs(1000.0), // Make sure the transfer and shooter stops running as soon as the balls are done shooting
//                instant(() -> robot.setTransferPower(0.0)),
//                instant(() -> robot.setShooterPower(0.0)),
//
//
//                follow(follower, park())
//        );
//    }
//
//    @Override
//    public void runOpMode() {
//        Scheduler.reset();
//
//        robot.init(hardwareMap);
//        follower = Constants.create(hardwareMap);
//
//        follower.setPose(shootPreload);
//        follower.update();
//
//        waitForStart();
//        schedule(autoRoutine());
//
//        while (opModeIsActive()) {
//            follower.update();
//            Scheduler.execute();
//
//            telemetry.addData("x", follower.pose().x());
//            telemetry.addData("y", follower.pose().y());
//            telemetry.addData("heading", follower.pose().heading());
//
//            if (follower.currentPath() != null) {
//                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
//                telemetry.addData("Path number", follower.pathIndex());
//            }
//
//            telemetry.update();
//        }
//    }
//
//    public Path shootPreload() {
//        return line(shootPreload, shootPreload_2).linear(shootPreload, shootPreload_2);
//    }
//    public Path flowerIntake1() {
//        return line(shootPreload_2, flowerIntake1).linear(shootPreload_2, flowerIntake1);
//    }
//
//    public Path shootFlower1() {
//        return curve(flowerIntake1, shootFlower1Control1, shootFlower1).linear(flowerIntake1, shootFlower1);
//    }
//
//    public Path flowerIntake2() {
//        return curve(shootFlower1, flowerIntake2Control1, flowerIntake2).linear(shootFlower1, flowerIntake2);
//    }
//
//    public Path shootFlower2() {
//        return curve(flowerIntake2, shootFlower2Control1, shootFlower2).linear(flowerIntake2, shootFlower2);
//    }
//
//    public Path park() {
//        return curve(shootFlower2, parkControl1, park).linear(shootFlower2, park);
//    }
//}
