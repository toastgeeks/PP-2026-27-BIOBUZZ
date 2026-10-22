package org.firstinspires.ftc.teamcode.subSystems;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.*;

import static com.pedropathing.ivy.commands.Commands.instant;

public class Shooter {
    private final DcMotorEx shooter1;
    private final DcMotorEx shooter2;

    public Shooter(HardwareMap hw) {
        shooter1 = hw.get(DcMotorEx.class, "shooter1");
        shooter1.setDirection(DcMotorSimple.Direction.FORWARD);
        shooter2 = hw.get(DcMotorEx.class, "shooter2");
        shooter2.setDirection(DcMotorSimple.Direction.REVERSE);

        for (DcMotorEx motor : new DcMotorEx[]{shooter1, shooter2}) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(200.8137, 0, 0, 13.0907));
        }
    }

    private void setVelocity(double ticksPerSecond) {
        shooter1.setVelocity(ticksPerSecond);
        shooter2.setVelocity(ticksPerSecond);
    }

    public Command on() {
        return instant(() -> setVelocity(1750)).requiring(this);
    }

    public Command off() {
        return instant(() -> setVelocity(0)).requiring(this);
    }
}
