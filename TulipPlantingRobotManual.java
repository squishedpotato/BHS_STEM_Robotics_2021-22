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

@TeleOp(name = "TulipPlantingRobotManual", group = "")
public class TulipPlantingRobotManual extends LinearOpMode {
  /* Error Codes:
  1: calcFRBLPower Method error
  2: calcFLBRPower Method error 
  */
  private double horizontalLock = 1;
  private double verticalLock = 1;
  private double leftStickAngle = 0;
  private double leftStickX = 0;
  double leftStickY = 0;
  double leftStickMagnitude = 0;
  double rightStickAngle = 0;
  double rightStickX = 0;
  double rightStickY = 0;
  double rightStickMagnitude = 0;
  int ringMotorEncoder = 0;
  boolean debugBoolean = false;
  int error = 0;
  
  
  private DcMotor frontleft;
  private DcMotor frontright;
  private DcMotor backleft;
  private DcMotor backright;
  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    
    //initializes motors
    motorInitialization(2);
    
    waitForStart();
    //Initial Run time setup: runs only once
    if (opModeIsActive()) {
      // Put run blocks here.
      horizontalLock = 1;
      verticalLock = 1;
      
    }
    //Continuous Run time: runs in a loop
    while (opModeIsActive()) {
      // Note: DO NOT PRESS MODE BUTTON ON THE CONTROLLER (CHANGES CONTROLS TO IDK WHAT)
      
      //Y axis on joysticks is reversed: negative is on top, positive is on bottom (don't ask why)
      twoMotorTankControls(frontleft,frontright,-gamepad1.left_stick_y, -gamepad1.right_stick_y);
      
      telemetryDisplay();
    }
  }
  
  //Assumes there's only two motor schemes: two motor for four wheels / four motors for four wheels
  //Will maybe generalize code later
  //false = two motor scheme
  //true = four motor scheme
  private void motorInitialization(int motorScheme){
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
    //Reverses left motors so all spin in same direction.
    frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
    frontright.setDirection(DcMotorSimple.Direction.FORWARD);
    backleft.setDirection(DcMotorSimple.Direction.REVERSE);
    backright.setDirection(DcMotorSimple.Direction.FORWARD);
  }
  
  //Generic tank Control Scheme: assigns the twin joysticks on gamepad1 directly to each side of the robot
  /***Preconditions
    *leftMotor and rightMotor are properly defined 
    *leftPow and rightPow in a range from -1 to 1
  ***/
  private void twoMotorTankControls(DcMotor leftMotor, DcMotor rightMotor, double leftPow, double rightPow){
    leftMotor.setPower(leftPow);
    rightMotor.setPower(rightPow);
  }
  
  //Questionable mecanum wheel control scheme that I have no idea how it works anymore
  //It uses a lot of trig math and many questionable coding choices
  private void mecanumControls(){
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
  
  //Stick all telemetry data in this method for cleaner readability
  //Displays the telemetry data on the driver station phone on the bottom
  private void telemetryDisplay(){
    telemetry.addData("frontleft pow", frontleft.getPower());
    telemetry.addData("frontleft encoder", frontleft.getCurrentPosition());
    telemetry.addData("frontright pow", frontright.getPower());
    telemetry.addData("frontright encoder", frontright.getCurrentPosition());
    telemetry.addData("backleft pow", backleft.getPower());
    telemetry.addData("backleft encoder", backleft.getCurrentPosition());
    telemetry.addData("backright pow", backright.getPower());
    telemetry.addData("backright encoder", backright.getCurrentPosition());
    telemetry.addData("right stick x axis", rightStickX);
    telemetry.addData("right stick y axis", rightStickY);
    telemetry.addData("RightStickAngleDegrees", rightStickAngle / Math.PI * 180);
    telemetry.addData("RightStickMagnitude", rightStickMagnitude);
    telemetry.addData("Trigger", gamepad1.left_trigger);

    telemetry.addData("Debug", debugBoolean);
    telemetry.addData("Error Code", error);
    telemetry.update();
  }
  
  
  //There be dragons here
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
