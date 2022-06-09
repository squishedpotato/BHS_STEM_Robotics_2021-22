package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TelemetryDataHelper {
    private Telemetry telemetry;
    private Gamepad gamepad1;
    private Gamepad gamepad2;
    //Custom class for cleaning up main class (and can be used for other opModes)
    //Slightly scuffed coding, but it'll probably work
    public TelemetryDataHelper(Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2){
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }
    //Stick all telemetry data in these methods for cleaner readability
    //Displays the telemetry data on the driver station phone on the bottom
    //MUST ADD         telemetry.update();          after all desired telemetry data is added (or else it starts flashing data)
    public void telemetryDisplayDriveMotors(DcMotor frontleft, DcMotor backleft, DcMotor frontright, DcMotor backright){
        telemetry.addData("frontleft pow", frontleft.getPower());
        telemetry.addData("frontleft encoder", frontleft.getCurrentPosition());
        telemetry.addData("frontright pow", frontright.getPower());
        telemetry.addData("frontright encoder", frontright.getCurrentPosition());
        telemetry.addData("backleft pow", backleft.getPower());
        telemetry.addData("backleft encoder", backleft.getCurrentPosition());
        telemetry.addData("backright pow", backright.getPower());
        telemetry.addData("backright encoder", backright.getCurrentPosition());
    }
    public void telemetryDisplayMotor(DcMotor motor, String motorName){
        telemetry.addData(motorName, motor.getPower());
        telemetry.addData(motorName, motor.getCurrentPosition);
    }
    /* Preconditions: motor and motorName arrays are the same length, where each motor maps to a motorName
     * @param motor An array of DcMotors to be displayed as data
     * @param motorName An array of Strings that name each DcMotor in the array
     * @throws IllegalArgumentException if motor.length != motorName.length
     */
    public void telemetryDisplayMotorArray(DcMotor[] motor, String[] motorName){
        for(int i = 0; i < motor.length; i++){
            telemetryDisplayMotor(motor[i], motorName[i]);
        }
    }
    public void telemetryDisplayControllerG1(){
        telemetry.addData("GP1 Left Stick X ", gamepad1.left_stick_x);
        telemetry.addData("GP1 Left Stick Y ", gamepad1.left_stick_y);
        telemetry.addData("GP1 Right Stick X ", gamepad1.right_stick_x);
        telemetry.addData("GP1 Right Stick Y ", gamepad1.right_stick_y);
        telemetry.addData("GP1 Left Trigger ", gamepad1.left_trigger);
        telemetry.addData("GP1 Right Trigger ", gamepad1.right_trigger);
    }
    public void telemetryDisplayControllerG2(){
        telemetry.addData("GP2 Left Stick X ", gamepad2.left_stick_x);
        telemetry.addData("GP2 Left Stick Y ", gamepad2.left_stick_y);
        telemetry.addData("GP2 Right Stick X ", gamepad2.right_stick_x);
        telemetry.addData("GP2 Right Stick Y ", gamepad2.right_stick_y);
        telemetry.addData("GP2 Left Trigger ", gamepad2.left_trigger);
        telemetry.addData("GP2 Right Trigger ", gamepad2.right_trigger);
    }
}
