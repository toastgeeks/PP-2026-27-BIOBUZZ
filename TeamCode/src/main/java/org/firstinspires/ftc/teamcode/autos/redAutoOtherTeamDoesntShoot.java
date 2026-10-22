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
//import static com.pedropathing.ivy.groups.Groups.sequential;
//import static com.pedropathing.ivy.pedro.PedroCommands.follow;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import org.firstinspires.ftc.teamcode.pedro.Constants;
//
//import org.firstinspires.ftc.teamcode.BioBuzz.RobotHardware;
//
//@Autonomous(name = "Red Other Team Doesn't Shoot", group = "Autonomous")
//public class redAutoOtherTeamDoesntShoot extends LinearOpMode {
//    private Follower follower;
//
//    // Imports RobotHardware so that we can run the intake, transfer, and shooter
//    private final RobotHardware robot = new RobotHardware();
//
//    private final PoseFactory poseFactory = PoseFactory.degrees();
//
//    private final Pose shootPreload = poseFactory.of(56.5, 9.5, 90);
//    private final Pose shootPreload_2 = poseFactory.of(56.5, 9.5, 90);
//    private final Pose gardenIntake1 = poseFactory.of(12.2992, 47.4713, 180);
//    private final Pose shootGarden1 = poseFactory.of(58.2304, 127.146, -90);
//    private final Pose shootGarden1Control1 = poseFactory.of(18.2, 99.9, 0);
//    private final Pose gardenIntake2 = poseFactory.of(47.4364, 129.3559, 90);
//    private final Pose gardenIntake2Control1 = poseFactory.of(56.1199, 114.4159, 0);
//    private final Pose shootGarden2 = poseFactory.of(58.3705, 127.3257, -90);
//    private final Pose shootGarden2Control1 = poseFactory.of(56.0878, 114.5654, 0);
//    private final Pose park = poseFactory.of(10.8904, 115.3341, 0);
//    private final Pose parkControl1 = poseFactory.of(55.6423, 119.6622, 0);
//
//    // Autonomous routine
//    public Command autoRoutine() {
//        return sequential(
//                follow(follower, shootPreload()),
//                follow(follower, gardenIntake1()),
//                follow(follower, shootGarden1()),
//                follow(follower, gardenIntake2()),
//                follow(follower, shootGarden2()),
//                follow(follower, park())
//        );
//    }
//
//    @Override
//    public void runOpMode() {
//        Scheduler.reset();
//
//        // Initializes the robot's hardware
//        robot.init(hardwareMap);
//        follower = Constants.create(hardwareMap);
//
//        follower.setPose(shootPreload);
//        follower.update();
//
//        follower = Constants.create(hardwareMap);
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
//    public Path gardenIntake1() {
//        return line(shootPreload_2, gardenIntake1).linear(shootPreload_2, gardenIntake1);
//    }
//
//    public Path shootGarden1() {
//        return curve(gardenIntake1, shootGarden1Control1, shootGarden1).linear(gardenIntake1, shootGarden1);
//    }
//
//    public Path gardenIntake2() {
//        return curve(shootGarden1, gardenIntake2Control1, gardenIntake2).linear(shootGarden1, gardenIntake2);
//    }
//
//    public Path shootGarden2() {
//        return curve(gardenIntake2, shootGarden2Control1, shootGarden2).linear(gardenIntake2, shootGarden2);
//    }
//
//    public Path park() {
//        return curve(shootGarden2, parkControl1, park).linear(shootGarden2, park);
//    }
//}
