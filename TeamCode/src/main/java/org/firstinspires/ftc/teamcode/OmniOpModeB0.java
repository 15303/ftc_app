package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="B0 - AutoOmni", group="Linear Opmode")
//@Disabled

public class OmniOpModeB0 extends LinearOpMode {

  private ElapsedTime runtime = new ElapsedTime();
  private DcMotor driveNW = null;
  private DcMotor driveNE = null;
  private DcMotor driveSE = null;
  private DcMotor driveSW = null;
  private DcMotor lslider = null;
  private Servo grabber = null;
  
  double driveIn = 0;
  double driveFwd = 0;

  boolean isGrabbing = false;
  
  private void driveIn(double force,int time){
    
    driveNW.setPower(force);
    driveNE.setPower(force);
    driveSE.setPower(-force);
    driveSW.setPower(-force);
    
    sleep(time);
  }
  
  private void driveFwd(double force,int time){
    
    driveNW.setPower(-force);
    driveNE.setPower(force);
    driveSE.setPower(force);
    driveSW.setPower(-force);
    
    sleep(time);
  }
  
  private void driveCC(double force,int time){
    
    driveNW.setPower(force);
    driveNE.setPower(force);
    driveSE.setPower(force);
    driveSW.setPower(force);
    
    sleep(time);
  }
  
  private void driveRst(){
    
    driveNW.setPower(0);
    driveNE.setPower(0);
    driveSE.setPower(0);
    driveSW.setPower(0);
    
  }

  private void grab(boolean shouldGrab){
    
    if(shouldGrab){
      grabber.setPosition(0.7);
    }else{
      grabber.setPosition(0);
    }
    
    sleep(1000);
  }
  
  @Override
  public void runOpMode() {
    telemetry.addData("Status", "Initialized");
    telemetry.update();

    driveNW = hardwareMap.get(DcMotor.class, "driveNW");
    driveNE  = hardwareMap.get(DcMotor.class, "driveNE");
    driveSE = hardwareMap.get(DcMotor.class, "driveSE");
    driveSW = hardwareMap.get(DcMotor.class, "driveSW");
    lslider = hardwareMap.get(DcMotor.class, "lslider");
    grabber = hardwareMap.get(Servo.class, "grabber");

    driveNW.setDirection(DcMotor.Direction.FORWARD);
    driveNE.setDirection(DcMotor.Direction.FORWARD);
    driveSE.setDirection(DcMotor.Direction.FORWARD);
    driveSW.setDirection(DcMotor.Direction.FORWARD);

    waitForStart();
    runtime.reset();

    driveIn(1,1100);

    grab(false);

    driveIn(-1,550);

    driveFwd(1,400);
    driveRst();
    
    grab(true);

    driveIn(-1,500);
    driveRst();

    grab(true);

    driveFwd(-1,650);
    driveRst();

    grab(true);

    driveCC(1,-100);

    grab(true);
    
    driveRst();
  }
}