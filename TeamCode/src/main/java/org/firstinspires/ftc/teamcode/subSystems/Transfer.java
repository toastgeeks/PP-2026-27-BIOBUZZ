package org.firstinspires.ftc.teamcode.subSystems;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static com.pedropathing.ivy.commands.Commands.instant;

public class Transfer {
    private final DcMotorEx transfer;

    public Transfer(HardwareMap hw) {
        transfer = hw.get(DcMotorEx.class, "transfer");
        transfer.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public Command on() {
        return instant(() -> transfer.setPower(0.5)).requiring(this);
    }

    public Command off() {
        return instant(() -> transfer.setPower(0)).requiring(this);
    }
}
