package org.firstinspires.ftc.teamcode.subSystems;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static com.pedropathing.ivy.commands.Commands.instant;

public class Intake {
    private final DcMotor intake;

    public Intake(HardwareMap hw) {
        intake = hw.get(DcMotor.class, "intake");
        intake.setDirection(DcMotor.Direction.REVERSE);
    }

    public Command on() {
        return instant(() -> intake.setPower(1.0)).requiring(this);
    }

    public Command off() {
        return instant(() -> intake.setPower(0)).requiring(this);
    }
}