// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

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
  //Motor Controllers
  private CANSparkMax leftMotor = new CANSparkMax(1, MotorType.kBrushless);
  private CANSparkMax rightMotor = new CANSparkMax(2, MotorType.kBrushless);

  //Motor group
Spark m_frontLeft = new Spark(1);
Spark m_rearLeft = new Spark(2);
SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

Spark m_frontRight = new Spark(3);
Spark m_rearRight = new Spark(4);
SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

  //Joysticks
  private Joystick joy0 = new Joystick(0);
  private Joystick joy1 = new Joystick(1);

  //Drivechain
  private DifferentialDrive drivechain = new DifferentialDrive(leftMotor, rightMotor);

  @Override
  public void robotInit() {
    PWMVictorSPX frontLeft = new PWMVictorSPX(kFrontLeftChannel);
    PWMVictorSPX rearLeft = new PWMVictorSPX(kRearLeftChannel);
    PWMVictorSPX frontRight = new PWMVictorSPX(kFrontRightChannel);
    PWMVictorSPX rearRight = new PWMVictorSPX(kRearRightChannel);

    //Invert the left side motors.
    //You may need to chnge or remove this to match your robot.
    frontLeft.setInverted(true);
    rearLeft.setInverted(true);

    m_robotDrive = new MecanumDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

    m_stick = new Joystick(kJoystickChannel);

    m_left.setInverted(true);
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
  public void teleopPeriodic() {

// boolean nothing = joy0.getRawButton(x);
// boolean nothing = joy1.getRawButton(x);

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

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
