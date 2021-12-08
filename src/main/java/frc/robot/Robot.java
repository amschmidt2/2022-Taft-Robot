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
  private CanSparkMax back_LeftyMotor = new CanSparkMax(2, MotorType.kBrushless);
  SpeedControllerGroup m_lefty = new SpeedControllerGroup(front_LeftyMotor, back_LeftyMotor);

  private CANSparkMax front_RightyMotor = new CANSparkMax(3, MotorType.kBrushless);
  private CanSparkMax back_RightyMotor = new CanSparkMax(4, MotorType.kBrushless);
  SpeedControllerGroup m_righty = new SpeedControllerGroup(front_RightyMotor, back_RightyMotor);

  DifferentialDrive m_drive = new DifferentialDrive(m_righty, m_lefty);
 

  private Joystick joy0 = new Joystick(0);
  private Joystick joy1 = new Joystick(1);
  

  @Override
  public void robotInit() {

    m_lefty.setInverted(true); // If you wanna invert the entire side you can do it here

  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {}

// boolean nothing = joy0.getRawButton(x);
// boolean nothing = joy1.getRawButton(x);




  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
