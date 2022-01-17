// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  private CANSparkMax front_LeftyMotor = new CANSparkMax(1, MotorType.kBrushless);
  private CANSparkMax back_LeftyMotor = new CANSparkMax(2, MotorType.kBrushless);
  private CANSparkMax front_RightyMotor = new CANSparkMax(3, MotorType.kBrushless);
  private CANSparkMax back_RightyMotor = new CANSparkMax(4, MotorType.kBrushless);

  SpeedControllerGroup m_lefty = new SpeedControllerGroup(front_LeftyMotor, back_LeftyMotor);
  SpeedControllerGroup m_righty = new SpeedControllerGroup(front_RightyMotor, back_RightyMotor);


  //Joysticks
  private Joystick joy0 = new Joystick(0);
  private Joystick joy1 = new Joystick(1);

  //Drivechain

  private DifferentialDrive drivechain = new DifferentialDrive(m_lefty, m_righty);

  Room room =  new Room();

  @Override
  public void robotInit() {
    
    


  }

  @Override
  public void robotPeriodic() {

    // Xbox A, B, Y, X, tiny two frames (TTF.), tiny three line(T3L), Dpad
    // Right Bummper, Left Bummper, Right Trigger, Left Trigger, Top Joystick, Bottom Joystick
    // This was the shooter speed these are not things to look at buttons wise.
    boolean xbox_A = joy0.getRawButton(1); // xbox A
    boolean xbox_B = joy0.getRawButton(2); // xbox B
    boolean xbox_X = joy0.getRawButton(3); // xbox X
    boolean xbox_Y = joy0.getRawButton(4); // xbox Y

    
    elevator(xbox_A, xbox_B);

    double joy_Left = joy0.getRawAxis(1);
    double joy_Right = joy0.getRawAxis(0);

    driveTruck(joy_Left, joy_Right);

    
    // checking if the button has changed
    if (xbox_X != room.dumpTruck_prev_but) {
      
      //if state is true make false (this is a if, else statement within a if statement)
      if(room.dumpTruck_state) {
        room.dumpTruck_state = false;
      }
      //else make state true (this else relates to the second true)
      else{
        room.dumpTruck_state = true;
      }
    }
    else{}

    dumpTruck();

    //controller, button
    //some holder variable to track state

   }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

//   @Override
// // drivechain.arcadeDrive(speed, turn);
// // System.out.println("arcade drive speed: "+speed+", turn: "+turn);

// //   }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
 // }

// hii

private void dumpTruck() {

  if (room.dumpTruck_state) {
    System.out.println("dumpTruck moves up");
  }

  else {
    System.out.println("dumpTruck moves down");
  }

}



private void elevator(boolean up, boolean down) {
  if (up){
    System.out.println("Elevator moves up");
  }

  else if(down){
    System.out.println("Elevator moves down");
  }

  else{
    System.out.println("Elevator stops");
  }

}


private void driveTruck(double speed, double turn_raw){

  double turn = Math.pow(turn_raw, 2.0);
  if (turn_raw < 0){
    turn = -turn;
  }

  //drive train control
  drivechain.arcadeDrive(speed, turn);
  System.out.println("arcade drive speed: " + speed + ", turn: " + turn);
}

public class Room{
    boolean dumpTruck_state = false;
    boolean dumpTruck_prev_but = false;
  }

}  // <--- Leave this close brace
