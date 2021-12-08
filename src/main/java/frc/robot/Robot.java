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
<<<<<<< HEAD
  //Motor Controllers
  private CANSparkMax leftMotor = new CANSparkMax(1, MotorType.kBrushless);
  private CANSparkMax rightMotor = new CANSparkMax(2, MotorType.kBrushless);
=======
  private CANSparkMax front_LeftyMotor = new CANSparkMax(1, MotorType.kBrushless);
  private CanSparkMax back_LeftyMotor = new CanSparkMax(2, MotorType.kBrushless);
  private CANSparkMax front_RightyMotor = new CANSparkMax(3, MotorType.kBrushless);
  private CanSparkMax back_RightyMotor = new CanSparkMax(4, MotorType.kBrushless);

>>>>>>> Angie's-Branch

  //Joysticks
  private Joystick joy0 = new Joystick(0);
  private Joystick joy1 = new Joystick(1);
<<<<<<< HEAD

  //Drivechain
  private DifferentialDrive drivechain = new DifferentialDrive(leftMotor, rightMotor);
=======
  
>>>>>>> Angie's-Branch

  @Override
  public void robotInit() {}

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {

// boolean nothing = joy0.getRawButton(x);
// boolean nothing = joy1.getRawButton(x);

<<<<<<< HEAD
//driving
double speed = joy0.getRawAxis(1);

double turn_raw = joy0.getRawAxis(0);
double turn = Math.pow(turn_raw, 2.0);
if (turn_raw < 0);{
  turn = -turn;
}

//drive train control
drivechain.arcadeDrive(speed, turn);
System.out.println("arcade drive speed: "+speed+", turn: "+turn);




  }
=======


>>>>>>> Angie's-Branch

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
