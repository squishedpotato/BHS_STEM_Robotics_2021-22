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
    this(new motorList[1], new tickCountList[1]);
  }
  public void runToPosition(DcMotor motor, int encoderTicks, double numRotations, double power, boolean isOneMotor){
    motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  
    motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    motor.setTargetPosition((int)(numRotations * encoderTicks));
    motor.setPower(power);
    while(motor.isBusy() && isOneMotor){
    
    }
    motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
  }
  public void runToPosition(DcMotor motor, int encoderTicks, double radiusOfWheel, double distance, double power, boolean isOneMotor){
    double circumferenceOfWheel = 2 * Math.PI * radiusOfWheel;
    double numRotations = distance/circumferenceOfWheel;
    this.runToPosition(motor, encoderTicks, numRotations, power, isOneMotor);
  }
  public void runAllToPosition(double numRotations, double power){
    for(int i = 0; i < motorList.length, i++){
      this.runToPosition(motorList[i], tickCountList[i], numRotations, power, false);
    }
  }
}
