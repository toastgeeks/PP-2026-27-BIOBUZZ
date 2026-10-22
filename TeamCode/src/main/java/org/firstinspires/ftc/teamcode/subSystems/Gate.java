package org.firstinspires.ftc.teamcode.subSystems;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static com.pedropathing.ivy.commands.Commands.instant;

public class Gate {

    private final Servo gate;

    public Gate(HardwareMap hw) {
        gate = hw.get(Servo.class, "gate");
    }

    public Command open() {
        return instant(() -> gate.setPosition(0.3)).requiring(this);
    }

    public Command close() {
        return instant(() -> gate.setPosition(0.5)).requiring(this);
    }
}
