package org.firstinspires.ftc.teamcode.TeleOps;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subSystems.Gate;
import org.firstinspires.ftc.teamcode.subSystems.Intake;
import org.firstinspires.ftc.teamcode.subSystems.Shooter;
import org.firstinspires.ftc.teamcode.subSystems.Transfer;

@TeleOp(name = "Teleop")
public class Teleop extends OpMode {
    private Follower follower;
    private Intake intake;
    private Shooter shooter;
    private Transfer transfer;
    private Gate gate;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(new Pose(0, 0, 0));

        intake = new Intake(hardwareMap);
        intake.off().schedule();

        shooter = new Shooter(hardwareMap);
        shooter.off().schedule();

        transfer = new Transfer(hardwareMap);
        transfer.off().schedule();

        gate = new Gate(hardwareMap);
        gate.close().schedule();
    }


    @Override
    public void start() {
        follower.update();
    }

    @Override
    public void loop() {
        Pose currentPost = follower.pose();

        follower.manual(
                ManualDrive.fieldCentric(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x,
                        -gamepad1.right_stick_x,
                        currentPost.heading()
                ));

        telemetry.addData("Robot X", currentPost.x());
        telemetry.addData("Robot Y", currentPost.y());
        telemetry.addData("Robot Heading", Math.toDegrees(currentPost.heading()));

        // Gamepad 1: intake
        if (gamepad1.right_trigger > 0.1) {
            intake.on().schedule();
        } else {
            intake.off().schedule();
        }

        // Gamepad 2: shooter, transfer, gate
        if (gamepad2.right_trigger > 0.1) {
            shooter.on().schedule();
        } else {
            shooter.off().schedule();
        }

        if (gamepad2.left_trigger > 0.1) {
            transfer.on().schedule();
        } else {
            transfer.off().schedule();
        }

        if (gamepad2.square) {
            gate.open().schedule();
        } else {
            gate.close().schedule();
        }

        Scheduler.execute();
    }
}
