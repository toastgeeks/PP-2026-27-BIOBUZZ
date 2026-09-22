package org.firstinspires.ftc.teamcode.pedro;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class Paths {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(10, 84, 90);
    private final Pose path1Start = poseFactory.of(10, 84, 0);
    private final Pose path1 = poseFactory.of(10, 106, 0);

    public Path path1() {
        return line(path1Start, path1).linear(path1Start, path1);
    }
}