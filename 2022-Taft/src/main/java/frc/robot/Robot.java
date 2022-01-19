// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
//package edu.wpi.first.wpilibj.arcadedrivexboxcontroller;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //Create motors and controllers and stuff
  // private final PWMSparkMax m_leftMotor = new PWMSparkMax(0);
  // private final PWMSparkMax m_rightMotor = new PWMSparkMax(1);
  // private final DifferentialDrive drivechain = new DifferentialDrive(m_leftMotor, m_rightMotor);
  
  
  private final XboxController joy0 = new XboxController(0);
  private final XboxController joy1 = new XboxController(1);


  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  Timer timmy = new Timer();

  int autonomous_counter = 0;
  // 0: move backwards, 2 sec
  // 1: move forwards, 5 sec
  // 2: shoot ball, 10 sec

  double sammy[] = {2.0, 5.0, 10.0, 12.0};

// hello
  String starts[] = {"Motor move the wheels backwards","Motor move the wheels forward","Motor shoot the cargo upwards","Motor move the wheels backward", "we are done"};
  String ends[] = {"Stop moving","Stop moving","Stop shooter","Stop Moving"};

  private void briefcase(String task){
    System.out.println(task);
    switch(task){
      case "Motor move the wheels backwards":
        System.out.println("The wheels are moving back!");
        // drivechain.arcadeDrive(speed, turn);
      default:
        break;
    }
  }

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    System.out.println("We are starting autonomous mode");
    System.out.println("reset timmy");
    timmy.reset();
    timmy.start();
    System.out.println("timmy's time is " + timmy.get());

    briefcase(starts[0]);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        if(autonomous_counter != sammy.length){
          if(timmy.get() > sammy[autonomous_counter]){  // <-- This is an event
            briefcase(ends[autonomous_counter]);
            //System.out.println(ends[autonomous_counter]);        
            autonomous_counter++;
            briefcase(starts[autonomous_counter]);
            //System.out.println(starts[autonomous_counter]);
          }
        }
        

        // if(autonomous_counter == 0){
        //   //we are already moving backwards
        //   if(timmy.get() > sammy[autonomous_counter]){  // <-- This is an event
        //     System.out.println(ends[autonomous_counter]);
        //     //System.out.println("I have two seconds " + timmy.get());
        //     //System.out.println("make some code to start the next routine");
        //     // autonomous_counter = autonomous_counter + 1;
        //     autonomous_counter++;
        //     System.out.println(starts[autonomous_counter]);
        //     //System.out.println("autonomous counter = " + autonomous_counter);
            
        //   }
        //   else{}
        
            
        // }
        // //code for moving forward
        // else if(autonomous_counter == 1){
        //   //
        //   if(timmy.get() > sammy[autonomous_counter]){
        //     System.out.println(ends[autonomous_counter]);
        //     // System.out.println("I have 5 seconds " + timmy.get());
        //     // System.out.println("make some code to start the next routine");
        //     // autonomous_counter = autonomous_counter + 1;
        //     autonomous_counter++;
        //     System.out.println(starts[autonomous_counter]);
        //     //System.out.println("autonomous counter = " + autonomous_counter);
        //   }
        //   else{}
          
        // }
        // else if(autonomous_counter == 2){
        //   //
        //   if(timmy.get() > sammy[autonomous_counter]){
        //     System.out.println(ends[autonomous_counter]);
        //     // System.out.println("I have 10 seconds " + timmy.get());
        //     // System.out.println("make some code to start the next routine");
        //     // autonomous_counter = autonomous_counter + 1;
        //     autonomous_counter++;
        //     // System.out.println("autonomous counter = " + autonomous_counter);
        //   }
        //   else{}
        // }

        
        

        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
   // Drive Train (4m, SparkMax, Neo)
    // Climber (1m, victorSPX, 775)
    
    // Ball Handling
    // Intake (2 or 4m, ?, ?)
    // Shooter (2m, SparkMax, Neo)
    // Conveyer (2m/2m, victorsSPX,775)
  
    // Xbox A, B, Y, X, tiny two frames (TTF.), tiny three line(T3L), Dpad
    // Right Bummper, Left Bummper, Right Trigger, Left Trigger, Top Joystick, Bottom Joystick
    // This was the shooter speed these are not things to look at buttons wise.
    boolean xbox_A = joy0.getRawButton(1); // xbox A
    boolean xbox_B = joy0.getRawButton(2); // xbox B
    boolean xbox_X = joy0.getRawButton(3); // xbox X
    boolean xbox_Y = joy0.getRawButton(4); // xbox Y

    dumpTruck(xbox_X, xbox_Y);
    climber(xbox_A, xbox_B);

    double joy_Left = joy0.getRawAxis(1);
    double joy_Right = joy0.getRawAxis(0);

    //driveTruck(joy_Left, joy_Right);

  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    // Stop the rumble when entering disabled
    joy0.setRumble(RumbleType.kLeftRumble, 0.0);
    joy0.setRumble(RumbleType.kRightRumble, 0.0);
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  private void climber(boolean up, boolean down) {
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
  private void dumpTruck(boolean up, boolean down) {

    if (up) {
      System.out.println("dumpTruck moves up");
    }
  
    else if (down) {
      System.out.println("dumpTruck moves up");
    }
  
    else {
      System.out.println("dumpTruck off");
    }
  
  }

  // private void driveTruck(double speed, double turn_raw){

  //   double turn = Math.pow(turn_raw, 2.0);
  //   if (turn_raw < 0){
  //     turn = -turn;
  //   }
  
  //   //drive train control
  //   drivechain.arcadeDrive(speed, turn);
  //   System.out.println("arcade drive speed: " + speed + ", turn: " + turn);
  // }
}  // <--- Leave this close brace



