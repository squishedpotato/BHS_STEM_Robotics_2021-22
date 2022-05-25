package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class DriveSchemes {
    /* Error Codes:
    1: calcFRBLPower Method error
    2: calcFLBRPower Method error
    */


    //stuff I used for mecanum wheel stuff that really should have been local variables, but I'm too lazy to change it
    private double leftStickAngle = 0;
    private double leftStickX = 0;
    double leftStickY = 0;
    double leftStickMagnitude = 0;
    private double horizontalLock = 1;
    private double verticalLock = 1;
    double rightStickAngle = 0;
    double rightStickX = 0;
    double rightStickY = 0;
    double rightStickMagnitude = 0;
    int ringMotorEncoder = 0;
    boolean debugBoolean = false;
    int error = 0;




    //Generic tank Control Scheme: assigns the twin joysticks on gamepad1 directly to each side of the robot
    /***Preconditions
     *leftMotor and rightMotor are properly defined
     *leftPow and rightPow in a range from -1 to 1
     ***/
    public void tankControls(DcMotor leftMotor, DcMotor rightMotor, double leftPow, double rightPow){
        leftMotor.setPower(leftPow);
        rightMotor.setPower(rightPow);
    }
    public void tankControls(DcMotor frontleft, DcMotor backleft, DcMotor frontright, DcMotor backright, double leftPow, double rightPow){
        frontleft.setPower(leftPow);
        backleft.setPower(leftPow);
        frontright.setPower(rightPow);
        backright.setPower(rightPow);
    }

    //Questionable mecanum wheel control scheme that I have no idea how it works anymore
    //It uses a lot of trig math and many questionable coding choices
    public void mecanumControls(DcMotor frontleft, DcMotor backleft, DcMotor frontright, DcMotor backright, Gamepad gamepad1){
        // Use left stick to drive and right stick to turn
        // Bottom If "Locks" Direction
        debugBoolean = false;
        leftStickX = gamepad1.left_stick_x;
        leftStickY = -gamepad1.left_stick_y;
        if(leftStickX == 0 && leftStickY == 0) {
            leftStickAngle = 0;
            leftStickMagnitude = 0;
        }
        else if(leftStickX < 0 && leftStickY >= 0) {
            leftStickAngle = Math.atan(leftStickY/leftStickX) + Math.PI;
            leftStickMagnitude = Math.sqrt(Math.pow(leftStickX,2) + Math.pow(leftStickY,2));
            //debugBoolean = true;
        }
        else if(leftStickX < 0 && leftStickY <= 0) {
            leftStickAngle = Math.atan(leftStickY/leftStickX) + Math.PI;
            leftStickMagnitude = Math.sqrt(Math.pow(leftStickX,2) + Math.pow(leftStickY,2));
            //debugBoolean = true;
        }
        else if(leftStickX >= 0 && leftStickY <= 0) {
            leftStickAngle = Math.atan(leftStickY/leftStickX) + 2*Math.PI;
            leftStickMagnitude = Math.sqrt(Math.pow(leftStickX,2) + Math.pow(leftStickY,2));
            //debugBoolean = true;
        }
        else {
            leftStickAngle = Math.atan(leftStickY/leftStickX);
            leftStickMagnitude = Math.sqrt(Math.pow(leftStickX,2) + Math.pow(leftStickY,2));
        }
        frontleft.setPower(calcFLBRPower(leftStickAngle) + gamepad1.right_stick_x);
        frontright.setPower(calcFRBLPower(leftStickAngle) - gamepad1.right_stick_x);
        backleft.setPower(calcFRBLPower(leftStickAngle) + gamepad1.right_stick_x);
        backright.setPower(calcFLBRPower(leftStickAngle) - gamepad1.right_stick_x);
    }


    //Here be dragons
    //Very questionable code coming up
    private double calcFRBLPower(double angle) {
        //easierDouble keeps code more readable: turns 1/4 pi into units of 1
        double easierDouble = 4*angle/Math.PI;
        //Because I am starting to become tired typing out Math.PI
        double pi = Math.PI;
        //All axis cases
        if(angle == 0){
            return (-1)*leftStickMagnitude;
        }
        if(angle == pi/2){
            return (1)*leftStickMagnitude;
        }
        if(angle == pi){
            return (1)*leftStickMagnitude;
        }
        if(angle == 3*pi/2){
            return (-1)*leftStickMagnitude;
        }
        if(angle == 2*pi){
            return (-1)*leftStickMagnitude;
        }
        //All non-axis cases
        if(angle > 0 && angle < pi/2) {
            return (easierDouble-1)*leftStickMagnitude;
        }
        else if(angle > pi/2 && angle < pi){
            return (1)*leftStickMagnitude;
        }
        else if(angle > pi && angle < 3 * pi/2){
            return (-easierDouble + 5)*leftStickMagnitude;
        }
        else if(angle > 3*pi/2 && angle < 2 * pi){
            return (-1);
        }
        else{
            //error code if this somehow breaks???
            error = 1;
            return 0;
        }
    }
    private double calcFLBRPower(double angle) {
        //easierDouble keeps code more readable: turns 1/4 pi into units of 1
        double easierDouble = 4*angle/Math.PI;
        //Because I need to redefine variables in new methods (functions in javascript)
        double pi = Math.PI;
        //All axis cases
        if(angle == 0){
            return (1)*leftStickMagnitude;
        }
        if(angle == pi/2){
            return (1)*leftStickMagnitude;
        }
        if(angle == pi){
            return (-1)*leftStickMagnitude;
        }
        if(angle == 3*pi/2){
            return (-1)*leftStickMagnitude;
        }
        if(angle == 2*pi){
            return (1)*leftStickMagnitude;
        }

        //All non-axis cases
        if(angle > 0 && angle < pi/2){
            return (1)*leftStickMagnitude;
        }
        else if(angle > pi/2 && angle < pi){
            return (-easierDouble+3)*leftStickMagnitude;
        }
        else if(angle > pi && angle < 3*pi/2){
            return (-1)*leftStickMagnitude;
        }
        else if(angle > 3*pi/2 && angle < 2*pi){
            return (easierDouble-7)*leftStickMagnitude;
        }
        else {
            //error code maybe
            //turns out error codes are EXTREMELY useful tools
            error = 2;
            return 0;
        }
    }
}
