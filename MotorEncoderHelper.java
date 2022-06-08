package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class MotorEncoderHelper{
  private DcMotor[] motorList;
  private int[] tickCountList;
  
  public MotorEncoderHelper(DcMotor[] motorList, int[] tickCountList){
    this.motorList = motorList;
    this.tickCountList = tickCountList;
  }
  public MotorEncoderHelper(){
    this(new DcMotor[1], new int[1]);
  }
  public void runToPosition(DcMotor motor, int encoderTicks, double numRotations, double power, boolean isOneMotor){
    motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  
    motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    motor.setTargetPosition((int)(numRotations * encoderTicks));
    motor.setPower(power);
    while(motor.isBusy() && isOneMotor){ //bad code, gonna remove and replace with method waitUntilMotorStops()
    
    }
  }
  public void runToPosition(DcMotor motor, int encoderTicks, double radiusOfWheel, double distance, double power, boolean isOneMotor){
    double circumferenceOfWheel = 2 * Math.PI * radiusOfWheel;
    double numRotations = distance/circumferenceOfWheel;
    this.runToPosition(motor, encoderTicks, numRotations, power, isOneMotor);
  }
  public void runAllToPosition(double numRotations, double power){
    for(int i = 0; i < motorList.length; i++){
      this.runToPosition(motorList[i], tickCountList[i], numRotations, power, false);
    }
  }
  public void runAllToPosition(double radiusOfwheel, double distance, double power){
    for(int i = 0; i < motorList.length; i++){
      this.runToPosition(motorList[i], tickCountList[i], radiusOfWheel, distance, power, false);
    }
  }
  public DcMotor[] getMotorList(){
    return motorList;
  }
  public void setMotorList(DcMotor[] motorList){
    this.motorList = motorList;
  }
  public int[] getTickCountList(){
    return tickCountList;
  }
  public void setTickCountList(int[] tickCountList){
    this.tickCountList = tickCountList;
  }
}
