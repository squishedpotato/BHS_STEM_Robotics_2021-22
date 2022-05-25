package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "TulipPlantingRobotManual", group = "")
public class TulipPlantingRobotManual extends LinearOpMode {


    private DcMotor frontleft;
    private DcMotor frontright;
    private DcMotor backleft;
    private DcMotor backright;
    private TelemetryDataHelper tellyHelper;
    private DriveSchemes driveHelper;
    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {

        //initializes motors
        motorInitialization(4,true);
        tellyHelper = new TelemetryDataHelper(telemetry, gamepad1, gamepad2);
        waitForStart();
        //Initial Run time setup: runs only once
        if (opModeIsActive()) {
            // Put run blocks here.

        }
        //Continuous Run time: runs in a loop
        while (opModeIsActive()) {
            // Note: DO NOT PRESS MODE BUTTON ON THE CONTROLLER (CHANGES CONTROLS TO IDK WHAT)

            //Y axis on joysticks is reversed: negative is on top, positive is on bottom (don't ask why)
            driveHelper.tankControls(frontleft, backleft,frontright, backright, gamepad1.left_stick_y, gamepad1.right_stick_y);

            tellyHelper.telemetryDisplayControllerG1();
            tellyHelper.telemetryDisplayControllerG2();
            tellyHelper.telemetryDisplayDriveMotors(frontleft,backleft,frontright,backright);
            telemetry.update();
        }
    }

    //Assumes there's only two motor schemes: two motor for four wheels / four motors for four wheels
    //Will maybe generalize code later
    //false = two motor scheme
    //true = four motor scheme
    //isSameOrientation indicates whether or not the motors are mirrored
    //non-mirrored motors do not need to be reversed since they are spinning in the same direction
    private void motorInitialization(int motorScheme, boolean isSameOrientation){
        if(motorScheme == 4){
            frontleft = hardwareMap.get(DcMotor.class, "frontleft");
            frontright = hardwareMap.get(DcMotor.class, "frontright");
            backleft = hardwareMap.get(DcMotor.class, "backleft");
            backright = hardwareMap.get(DcMotor.class, "backright");
        }
        else if(motorScheme == 2){
            //Assignment for two motor setups (hopefully it doesn't break)
            frontleft = hardwareMap.get(DcMotor.class,"leftMotor");
            backleft = frontleft;
            frontright = hardwareMap.get(DcMotor.class,"rightMotor");
            backright = frontright;
        }
        if(!isSameOrientation){
            //Reverses left motors so all spin in same direction.
            frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
            frontright.setDirection(DcMotorSimple.Direction.FORWARD);
            backleft.setDirection(DcMotorSimple.Direction.REVERSE);
            backright.setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }
}
