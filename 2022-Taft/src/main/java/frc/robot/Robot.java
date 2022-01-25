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
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Robot extends TimedRobot {
  //Create motors and controllers and stuff

  
  private CANSparkMax front_LeftyMotor = new CANSparkMax(1, MotorType.kBrushless);
  private CANSparkMax back_LeftyMotor = new CANSparkMax(2, MotorType.kBrushless);
  private CANSparkMax front_RightyMotor = new CANSparkMax(3, MotorType.kBrushless);
  private CANSparkMax back_RightyMotor = new CANSparkMax(4, MotorType.kBrushless);

  private MotorControllerGroup rodger = new MotorControllerGroup(front_RightyMotor, back_RightyMotor);
  private MotorControllerGroup louie = new MotorControllerGroup(front_LeftyMotor, back_LeftyMotor);

  private final DifferentialDrive drivechain = new DifferentialDrive(louie, rodger);
  
  // private final XboxController joy0 = new XboxController(0);
  // private final XboxController joy1 = new XboxController(1);


  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  //homework please
  // make a strings list of button ids
  String buttons_list[] = {"x", "a", "a"};
  
  
  
  /*
  Our Agents

  timmy the Timer

  driver the 0th Controller
  gunner the 1st Controller

  Archie the Autonomous
  
  Wally the Wheels (4m, SparkMax, Neo's)

  [bobby] the BallHandler | Is like a conductor, Interests: gunner and driver and cares a lot about balls
  [izzy] the Intake (1m, VictorSPX, 775, pnem. 2)    *musician
  [conner] the Conveyor (2 or 4m, ?, ?)                *musician
  [sunny] the Shooter (2m, SparkMax, Neo's, pnem. 1)  *musician

  with the possible future inclustion of:
  [todd] the Turret
  [lemon] the LimeLight
  Carol the Climber (2m/2m, VictorSPX, 775)

  */

  Timer timmy = new Timer();
  SpyLord archie = new SpyLord("archie");
  Wheels wally = new Wheels("wally", .7);
  CargoChief bobby = new CargoChief("bobby");
  Intake izzy = new Intake("izzy");
  Conveyor conner = new Conveyor("conner");
  Shooter sunny = new Shooter("sunny");
  Driver driver = new Driver("driver", 0);
  Gunner gunner = new Gunner("gunner", 1);
  
 
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    rodger.setInverted(true);
  

    // Agents will announce themselves
    archie.talk();
    wally.talk();
    bobby.talk();
    izzy.talk();
    conner.talk();
    sunny.talk();
    driver.talk();
    gunner.talk();
  }

  @Override
  public void robotPeriodic() {}

 
 
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

   // Drive Train (4m, SparkMax, Neo)
    // Climber (1m, victorSPX, 775)
    
    // Ball Handling
    // Intake (2 or 4m, ?, ?)
    // Shooter (2m, SparkMax, Neo)
    // Conveyer (2m/2m, victorsSPX,775)
  
    // Xbox A, B, Y, X, tiny two frames (TTF.), tiny three line(T3L), Dpad
    // Right Bummper, Left Bummper, Right Trigger, Left Trigger, Top Joystick, Bottom Joystick
    // This was the shooter speed these are not things to look at buttons wise.
    // boolean xbox_A = joy0.getRawButton(1); // xbox A
    // boolean xbox_B = joy0.getRawButton(2); // xbox B
    // boolean xbox_X = driver.get_but(3); // xbox X
    // boolean xbox_Y = driver.get_but(4); // xbox Y

    dumpTruck(driver.get_but(3), driver.get_but(4));
    // climber(xbox_A, xbox_B);

    //double joy_Left = driver.get_axis(1);
    //double joy_Right = driver.get_axis(0);

   
    wally.check(driver.get_axis(1), driver.get_axis(0));
    // bobby.check();
    //wally.check(left=driver.get_axis('x'), right=driver.get_axis('y'))
    
  }

  public class Driver{
    private String name;
    private XboxController joy;

    public Driver(String _name, int port_num){
        name = _name;
        joy = new XboxController(port_num);
        System.out.println(name + " coming in hot ");
      }

      public void talk(){
        System.out.println(" Hi, I'm " + name + " I am the one who drives, Kachow! ");
      } 

      public boolean get_but(int but_num){
        return joy.getRawButton(but_num);
      }
      public double get_axis(int raw_axis){
        return joy.getRawAxis(raw_axis);
      }
    }

  public class Gunner{
    private String name;
    private XboxController joy;

    public Gunner(String _name, int port_num){
      name = _name;
      joy = new XboxController(port_num);
      System.out.println(name + " coming in hot ");
      }

      public void talk(){
      System.out.println(" Hi, I'm " + name + " I work with the weapons, pew pew! ");
      } 
      public boolean get_but(int but_num){
        return joy.getRawButton(but_num);
      }
      public double get_axis(int raw_axis){
        return joy.getRawAxis(raw_axis);
      }
    }


  
  public class CargoChief{
    private String name;
    boolean wants_cargo[] = {false, false};
    String state = "idle";
    
    public CargoChief(String _name){
      name = _name;
      System.out.println(name + " better bob is here ");
    }

    public void talk(){      
      System.out.println(" Hi, I'm " + name + " I tell you knowledge about cargo");
    } 
    public void check(){
      boolean ahhh [] = {false, false};
      ahhh = event_chk(driver.get_but(1), wants_cargo[1]);
      if (ahhh[0]){
        
        if (ahhh[1]){
          System.out.println("move all this to some other function make code clean like below");
        }

      }
      // This is the code I want to see in bobby's maing check function
      // if(wants_to_fire()){
      //   state = "firing";
      // }
      // else if(wants_cargo()){
      //   state = "get_cargo";
      // }
      
    }
  }


  public class Intake{
      private String name;

      public Intake(String _name){
      name = _name;
      System.out.println(name + " izzy is on the scene ");
      }

      public void talk(){
      System.out.println(" Hi, I'm " + name + " I reach out and collect cargo");
      } 
    }

  public class Conveyor{
    private String name;

    public Conveyor(String _name){
      name = _name;
      System.out.println(name + " has rolled in ");
    }

    public void talk(){
      System.out.println(" Hi, I'm " + name + " I move the cargo ");
    } 
  }

    public class Shooter{
      private String name;

      public Shooter(String _name){
        name = _name;
        System.out.println(name + " i am very happy ");
      }

      public void talk(){
        System.out.println(" Hi, I'm " + name + " I get rid of cargo, pew pew, yuck!");
      } 
    }


  public class Wheels{
    private String name;
    private double max_speed;

    public Wheels(String _name, double _max_speed){
      name = _name;
      max_speed = _max_speed;
      System.out.println(name + " has waddled in ");
    }

    public void talk(){
      System.out.println( " Hi, I'm " + name + " I run the wheels, vroom vroom, Our max speed limit is " + max_speed);
    }
    
    private void check(double speed, double turn){
      sensitive(speed, turn);
    }


    private void drive(double speed, double turn){
      drivechain.arcadeDrive(speed, turn);
    }

    private void sensitive(double speed, double turn){
      turn = Math.pow(turn, 2.0);
      if (turn < 0){
        turn = -turn;
      }
      speed = max_speed * speed;

      drive(speed, turn);
    } 
  } 


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

  boolean[] event_chk(boolean now, boolean past){
    boolean partyroom[] = {false, false};
    if (now != past){
      partyroom[0] = true;
        if(!past){
          partyroom[1] = true;
        }
    }
    return partyroom;
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



