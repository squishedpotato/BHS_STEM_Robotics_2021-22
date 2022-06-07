package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "StatisticsProject", group = "")
public class StatisticsProject extends LinearOpMode {


    private DcMotor statsMotor;
    private TelemetryDataHelper tellyHelper;
    private DriveSchemes driveHelper;
    private MotorEncoderHelper encoderHelper;
    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {

        //initializes motor
        statsMotor = hardwareMap.get(DcMotor.class, "statsMotor");

        tellyHelper = new TelemetryDataHelper(telemetry, gamepad1, gamepad2);
        driveHelper = new DriveSchemes();
        DcMotor[] motorList = {statsMotor};
        int[] tickCount = {1120};
        encoderHelper = new MotorEncoderHelper(motorList,tickCount);
        waitForStart();
        //Initial Run time setup: runs only once
        if (opModeIsActive()) {
            // Put run blocks here.

        }
        //Continuous Run time: runs in a loop
        while (opModeIsActive()) {
            // Note: DO NOT PRESS MODE BUTTON ON THE CONTROLLER (CHANGES CONTROLS TO IDK WHAT)

            if(gamepad1.a){
              encoderHelper.runToPosition(statsMotor, 1120, 100, 1, true);
            }
            
            tellyHelper.telemetryDisplayControllerG1();
            tellyHelper.telemetryDisplayControllerG2();
            //note to self: make a new method in TelemetryDataHelper that accepts an array of motors
            telemetry.addData("statsMotor power", statsMotor.getPower());
            telemetry.addData("statsMotor encoder", statsMotor.getCurrentPosition());
            telemetry.update();
        }
    }
}
