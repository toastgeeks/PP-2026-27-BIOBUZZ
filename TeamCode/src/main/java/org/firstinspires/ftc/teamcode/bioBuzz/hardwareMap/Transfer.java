package org.firstinspires.ftc.teamcode.bioBuzz.hardwareMap;

import com.qualcomm.robotcore.hardware.*;

public class Transfer {

    // Tells the code what the tranfer is in the robot config
    private DcMotor transfer;

    public void init(HardwareMap hwMap) {

        // What the transfer will be called in the code
        transfer = hwMap.get(DcMotor.class, "transfer");

        // Sets whether or not the transfer uses encoders

        transfer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Sets what direction the transfer motor spins

        transfer.setDirection(DcMotor.Direction.FORWARD);

    }

    // Gives the transfer power

    public void setPower(double power) {
        transfer.setPower(power);
    }

}
