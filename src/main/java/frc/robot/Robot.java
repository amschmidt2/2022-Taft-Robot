// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
//package edu.wpi.first.wpilibj.arcadedrivexboxcontroller;


// -------Schurz--cool--robot--------------


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.Compressor;

//import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import com.ctre.phoenix.motorcontrol.*; VictorSPX imports
//import com.ctre.phoenix.motorcontrol.can.*; VictorSPX imports

public class Robot extends TimedRobot {
  //Create motors and controllers and stuff

  private final XboxController joy0 = new XboxController(0);
  private final XboxController joy1 = new XboxController(1);


  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  
  Timer timmy = new Timer();
  SpyLord archie = new SpyLord("archie"); //archie stands alone
  
  Compressor pcmCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);

  // Shooter and Intake motors
  private PWMSparkMax intake_motor = new PWMSparkMax(0); 
  private PWMSparkMax shooter_motor = new PWMSparkMax(1);
  private PWMSparkMax sir_shooter_motor = new PWMSparkMax(2);
  private DoubleSolenoid jr_shooter_pneu = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
  private DoubleSolenoid elevator_pneu = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
  // Wheels motors
  private PWMSparkMax mr_right_wheels = new PWMSparkMax(3);
  private PWMSparkMax sir_right_wheels = new PWMSparkMax(4);
  private PWMSparkMax jr_left_wheels = new PWMSparkMax(5);
  private PWMSparkMax mrs_left_wheels = new PWMSparkMax(6);

  private MotorControllerGroup rodger = new MotorControllerGroup(mr_right_wheels, sir_right_wheels);
  private MotorControllerGroup louie = new MotorControllerGroup(jr_left_wheels, mrs_left_wheels);

  private final DifferentialDrive drivechain = new DifferentialDrive(louie, rodger);
  
  public class SpyLord {
    private String spyroom [][]= {
        {"Back move", "Stop move", "2.0"},
        {"Forward move", "Stop move", "2.5"},
        {"Shoot Cargo", "Stay off", "4.5"},
        {"Back move", "Stop move", "5.0"},
        {"Done", "this will never run", "999.9"}
      };
    private int autonomous_counter = 0;
    private double lil_sam = 0;
    private String name;


    public SpyLord(String _name){
      name = _name;
      System.out.println(name + " has entered ");
    }


    public void start(){
      timmy.reset();
      timmy.start();
      briefcase(spyroom[0][0]);
      lil_sam = lil_sam + Double.parseDouble(spyroom[0][2]);
    }

    public void check(){
      if(timmy.get() > lil_sam){  // <-- This is an event
        briefcase(spyroom[autonomous_counter][1]);
        autonomous_counter++;
        briefcase(spyroom[autonomous_counter][0]);
        lil_sam = lil_sam + Double.parseDouble(spyroom[autonomous_counter][2]);

      }
    }

    private void briefcase(String task){
      System.out.println(task);
      switch(task){
        case "Back move":
          System.out.println("The wheels are moving back!");
          // drivechain.arcadeDrive(speed, turn);
        default:
          break;
      }
    }

    public void talk(){
      System.out.println( " Hi, I'm " + name + " I am the boss of autonomous, you can look at tasks in the spyroom and make sure there is a function for it in the briefcase");
    }

  }
//ajax was here

  public void intake(boolean in, boolean out){
    if(out){
      intake_motor.set(.6);
    }
    else if(in){
      intake_motor.set(-0.6);
    }
    else{
      intake_motor.set(0);
    }
  }

  public void shooter(boolean out, boolean off){

    if(out){
      shooter_motor.set(.7);
      sir_shooter_motor.set(.7);
      // contorl how fast shooter moves
    }
    else if(off){
      shooter_motor.set(0);
      sir_shooter_motor.set(0);
    }

  }

  public void jr_shooter(boolean up, boolean down){

    if(up){
      jr_shooter_pneu.set(Value.kForward);
    }
    else if(down){
      jr_shooter_pneu.set(Value.kReverse);
    }
    else{
      jr_shooter_pneu.set(Value.kOff);
    }

  }

  public void elevator(boolean up, boolean down){

    if(up){
      elevator_pneu.set(Value.kForward);
    }
    else if(down){
      elevator_pneu.set(Value.kReverse);
    }
    else{
      elevator_pneu.set(Value.kOff);
    }

  }

  public void wheels(double speed, double turn){
    drivechain.arcadeDrive(-speed, turn);

  }


  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    //rodger.setInverted(true);
    louie.setInverted(true);
    // shooter_motor.setInverted(true);
    sir_shooter_motor.setInverted(true);

    

    // Agents will announce themselves
    archie.talk(); 
  }

  @Override
  public void robotPeriodic() {
    
  }



  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    System.out.println("We are starting autonomous mode");
    archie.start();
  }


  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        archie.check();
    }
  }


  @Override
  public void teleopInit() {}


  @Override
  public void teleopPeriodic() {

    // Xbox A, B, Y, X, tiny two frames (TTF.), tiny three line(T3L), Dpad
    // Right Bummper, Left Bummper, Right Trigger, Left Trigger, Top Joystick, Bottom Joystick
    boolean xbox_A = joy0.getRawButton(1); // xbox A
    boolean xbox_B = joy0.getRawButton(2); // xbox B
    boolean xbox_X = joy0.getRawButton(3); // xbox X
    boolean xbox_Y = joy0.getRawButton(4); // xbox Y
    boolean xbox_ttf = joy0.getRawButton(5); // tiny two frames
    boolean xbox_t3l = joy0.getRawButton(6); // tiny three lines
    boolean xbox_r_bum = joy0.getRawButton(7); // right bummper 
    boolean xbox_l_bum = joy0.getRawButton(8); // left bummper
    // left_joy = joy0.getRawAxis(axis);

    // here is what buttons work with each piece
    elevator(xbox_ttf, xbox_t3l); 
    intake(xbox_X, xbox_Y);
    shooter(xbox_B, xbox_A);
    jr_shooter(xbox_r_bum, xbox_l_bum);

    double joy_left = joy0.getRawAxis(0);
    double joy_right = joy1.getRawAxis(1);
    
    wheels(joy_left, joy_right);

//compressor code :D
//print() compressor-code
  }




  @Override
  public void disabledInit() {
    // Stop the rumble when entering disabled
    // joy0.setRumble(RumbleType.kLeftRumble, 0.0);
    // joy0.setRumble(RumbleType.kRightRumble, 0.0);
  }
  @Override
  public void disabledPeriodic() {}
  @Override
  public void testInit() {}
  @Override
  public void testPeriodic() {}
}  // <--- Leave this close brace
