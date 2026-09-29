package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(
            c -> {
                c.frontLeftName.set("fl");
                c.backLeftName.set("rl");
                c.frontRightName.set("rf");
                c.backRightName.set("rr");

                c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
                c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
                c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
                c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
            });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        c.xPodOffset.set(-0.0);
        c.yPodOffset.set(-0.0);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(-11577.21251523717);
                Controller secondaryTranslationalForward = Controller.proportional(-4277.470675564369);
                Controller primaryTranslationalLateral = Controller.proportional(62.496301131841996);
                Controller secondaryTranslationalLateral = Controller.proportional(23.0907133362938);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(2.77719154039157));
                c.brake.set(Controller.proportionalFeedforward(2.3606128093328342));

                c.headingFeedback.set(Controller.proportional(4.146462511009497));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.056010523136833815, 0.008860599310687063));

                c.linearBrakeCoefficients.set(Matrix.diag(0.08692332562954078, 0.12469476517213737));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0013590165858895233, 7.949586107426858E-4));

                c.maxAchievableForwardVelocity.set(0.8775987962283983);
                c.maxAchievableStrafeVelocity.set(12.677160526196015);
                c.naturalForwardDeceleration.set(32.72654611922139);
                c.naturalStrafeDeceleration.set(14.440826368712916);
            }
    );
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}