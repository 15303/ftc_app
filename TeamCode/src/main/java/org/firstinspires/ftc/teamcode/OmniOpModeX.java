package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="XingOmni", group="Linear Opmode")
//@Disabled

public class OmniOpModeX extends LinearOpMode {

  private ElapsedTime runtime = new ElapsedTime();
  
  private DcMotor driveNW = null;
  private DcMotor driveNE = null;
  private DcMotor driveSE = null;
  private DcMotor driveSW = null;
  
  private DcMotor slider  = null;
  private Servo   grabber = null;
  
  double driveRht   = 0;
  double driveFwd   = 0;
  double driveC     = 0;
  
  double sliderUp   = 0;
  double grabberPos = 0;
  
  @Override
  public void runOpMode() {
    
    telemetry.addData( "Status    " , "X Initialized"  );
    telemetry.update();

    driveNW = hardwareMap.get( DcMotor.class, "driveNW" );
    driveNE = hardwareMap.get( DcMotor.class, "driveNE" );
    driveSE = hardwareMap.get( DcMotor.class, "driveSE" );
    driveSW = hardwareMap.get( DcMotor.class, "driveSW" );
    slider  = hardwareMap.get( DcMotor.class, "slider"  );
    grabber = hardwareMap.get( Servo.class  , "grabber" );

    driveNW.setDirection(DcMotor.Direction.FORWARD);
    driveNE.setDirection(DcMotor.Direction.FORWARD);
    driveSE.setDirection(DcMotor.Direction.FORWARD);
    driveSW.setDirection(DcMotor.Direction.FORWARD);

    waitForStart();
    runtime.reset();

    while (opModeIsActive()) {

      if(gamepad1.x||gamepad2.x){
        
        telemetry.addData( "Status    " , "X Panic"    );
        
        //lock

        driveNW.setPower(  1 );
        driveNE.setPower( -1 );
        driveSE.setPower(  1 );
        driveSW.setPower( -1 );
        
        sleep(3000);

      }else{

        telemetry.addData( "Status     " , "X Running" );
        
        //define

        driveRht = -0.5 * ( gamepad1.left_stick_x + gamepad1.right_stick_x + gamepad2.left_stick_x + gamepad2.right_stick_x );
        driveFwd =  0.5 * ( gamepad1.left_stick_y + gamepad1.right_stick_y + gamepad2.left_stick_y + gamepad2.right_stick_y );
        driveC   =  1.0 * ( gamepad1.left_trigger - gamepad1.right_trigger );
        
        sliderUp = ( gamepad1.dpad_left  || gamepad2.dpad_left  ) ?  1
                 : ( gamepad1.dpad_right || gamepad2.dpad_right ) ? -1
                 :                                                   0;
          

        grabberPos = ( gamepad1.dpad_up   || gamepad2.dpad_up   ) ?  0
                   : ( gamepad1.dpad_down || gamepad2.dpad_down ) ?  0.7
                   :                                                 grabberPos;

        //do

        driveNW.setPower(   driveRht + driveFwd + driveC );
        driveNE.setPower(   driveRht - driveFwd + driveC );
        driveSE.setPower( - driveRht - driveFwd + driveC );
        driveSW.setPower( - driveRht + driveFwd + driveC );
        
        slider.setPower(sliderUp);
        grabber.setPosition(grabberPos);

      }
      
      telemetry.addData( "DriveRht   " , driveRht    );
      telemetry.addData( "DriveFwd   " , driveFwd    );
      telemetry.addData( "DriveC     " , driveC      );
      telemetry.addData( "SliderUp   " , sliderUp    );
      telemetry.addData( "GrabberPos " , grabberPos  );
      telemetry.update();
      
    }
  }
}