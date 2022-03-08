// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
//package edu.wpi.first.wpilibj.arcadedrivexboxcontroller;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
// import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

//import org.opencv.core.Mat;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Compressor;

//import edu.wpi.first.wpilibj.Solenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import edu.wpi.first.math.controller.PIDController;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// ***** VictorSPX Code *****
// import com.ctre.phoenix.motorcontrol.*;
// import com.ctre.phoenix.motorcontrol.can.*;
// private VictorSPX izzys_motor = new VictorSPX(6);
// izzys_motor.set(ControlMode.PercentOutput, .3);

public class Robot extends TimedRobot {
  //Create motors and controllers and stuff
  // private final XboxController joy0 = new XboxController(0);
  // private final XboxController joy1 = new XboxController(1);


  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // tiny two frames (ttt), tiny 3 lines (tl)
  // make a strings list of button ids
  String buttons_list[] = {"na", "a", "b", "x", "y", "l_bum", "r_bum", "ttt", "tl"};
  String axis_list[] = {"l_stick_x", "l_stick_y", "l_trig", "r_trig", "r_stick_x", "r_stick_y"};

  public int count_but = 0;
  public int find_but(String button){  
    count_but = 0;  
    for(int i = 0; i < buttons_list.length; i++ ){
      count_but = i;
      if(buttons_list[i].equals(button)){
        break;
      }
    }
    return count_but;
  }
  public int count_airplane = 0;
  public int find_axis(String axis){
    count_airplane = 0;
    for(int i = 0; i < axis_list.length; i++){
      count_airplane = i;
      if(axis_list[i].equals(axis)){
        break;
      }
    }
    return count_airplane;
  }


  public boolean team_blue = true;
  
  
  Timer timmy = new Timer(); // lil_sam is a timed event
  SpyLord archie = new SpyLord("archie");
  Wheels wally = new Wheels("wally", .7);
  // CargoChief bobby = new CargoChief("bobby");
  Intake izzy = new Intake("izzy", .7);
  Conveyor conner = new Conveyor("conner", 1);
  Shooter sunny = new Shooter("sunny", .6);
  Driver driver = new Driver("driver", 0);
  Gunner gunner = new Gunner("gunner", 1);
  NeoPixel nia = new NeoPixel("nia");
  Turret todd = new Turret("todd");
  LimeLight lucy = new LimeLight("lucy");
  Elevator elle = new Elevator("elle");
  Compressor pcmCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);


                                                                     
  public class Driver{
    private String name;
    private XboxController joy;
    private String wants_cargo_button = "x";
    private String no_cargo_button = "y";
    private String colors_button = "ttt";
    private double lil_sam;
    private boolean rumbling = false;
    private String control_stick[] = {"l_stick_y", "l_stick_x"};  // speed, turn
    private double stick_control[] = {0.0, 0.0};

    public Driver(String _name, int port_num){
        name = _name;
        joy = new XboxController(port_num);
        System.out.println(name + " coming in hot ");
      }
      public void check(){
        if(rumbling){
          if(timmy.get() > lil_sam){
            joy.setRumble(RumbleType.kLeftRumble, 0);
          }
        }
        // Hey, timmy thinks it would be cool if we were able 
        // to count x amount of seconds then make the controller rumble
        // signalling that the driver/gunner should start the climbing procces
      }
      public void test(){
         if(get_but("tl")){
           System.out.println("driver test");
          joy.setRumble(RumbleType.kLeftRumble, 1);
        }
        else{
          joy.setRumble(RumbleType.kLeftRumble, 0);
        }

      }


      public void talk(){
        System.out.println(" Hi, I'm " + name + " I am the one who drives, Kachow! ");
      }
      
      public double[] control_panel(){
        for(int i = 0; i < stick_control.length; i++){ // length of list returns 2
          stick_control[i] = get_axis(control_stick[i]);
        }
        return stick_control;
      }

      public void rumble(double lil_tim){
        joy.setRumble(RumbleType.kLeftRumble, 1);
        lil_sam = timmy.get() + lil_tim;
        rumbling = true;
      }
      public boolean get_but(String but_name){
        return joy.getRawButton(find_but(but_name));
      }
      public double get_axis(String axis_plane){
        return joy.getRawAxis(find_axis(axis_plane));
      }
      public boolean wants_cargo(){
        return get_but(wants_cargo_button);
      }
      public boolean no_cargo(){
        return get_but(no_cargo_button);
      }
      public boolean color(){
        return get_but(colors_button);
      }
    }


  public class Gunner{
    private String name;
    private XboxController joy;
    private double lil_sam;
    private boolean rumbling;
    private String prep_takeoff_button = "a";
    private String firing_cargo_button = "b";
    private String top_dog_button = "x";
    private String lock_on_button = "y";
    private boolean top_dog = false;
    private String move_todd_button = "r_stick_x";
    
    
    
    
    public Gunner(String _name, int port_num){
      name = _name;
      joy = new XboxController(port_num);
      System.out.println(name + " coming in hot ");
      }

      public void check(){
         if(rumbling){
          if(timmy.get() > lil_sam){
            joy.setRumble(RumbleType.kLeftRumble, 0);
          }
        }

        top_dog = get_but(top_dog_button);

      }
      public void test(){
        if(get_but("tl")){
          System.out.println("tiny lines");
          joy.setRumble(RumbleType.kLeftRumble, 1);
        }
        else{
          joy.setRumble(RumbleType.kLeftRumble, 0);
        }
      }
      
      public boolean is_top_dog(){
        return top_dog;
      }

      public void talk(){
      System.out.println(" Hi, I'm " + name + " I work with the weapons, pew pew! ");
      }
      
      public void rumble(double lil_tim){
      joy.setRumble(RumbleType.kLeftRumble, 1);
      lil_sam = timmy.get() + lil_tim;
      rumbling = true;
      }
      public boolean get_but(String Stur_but){
        return joy.getRawButton(find_but(Stur_but));
      }
      public double get_axis(String raw_axis){
        return joy.getRawAxis(find_axis(raw_axis));
      }
      public boolean prep_takeoff(){
        return get_but(prep_takeoff_button);
      }
      public void fire(){
        if(get_but(firing_cargo_button)){
          if(conner.RTF() && sunny.RTF()){
            System.out.println("This code is on firreeeeeee!!");
            conner.fire();
          }
        }
      }
      public boolean lock_on(){
        return get_but(lock_on_button);
      }
      public double move_todd(){
        return get_axis(move_todd_button);
      }
    }


  public class Intake{
    private String name;
    private String state = "sleeping"; //eating, sleeping, vomiting
    private double inhale;
    private DoubleSolenoid lil_iz = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
    private DoubleSolenoid jr_liliz = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
    private CANSparkMax motor = new CANSparkMax(9, MotorType.kBrushed);
    

    public Intake(String _name, double _inhale){
      name = _name;
      System.out.println(name + " izzy is on the scene ");
      inhale = _inhale;
    }

    public void check(){
      if(state.equals("eating")){
        if(conner.full){
          sleep();
        }
        else if(driver.no_cargo()){
          sleep();
        }
        else if(gunner.is_top_dog()){
          sleep();
        }
      }
      
      else if(state.equals("sleeping")){
        if(driver.wants_cargo()){
          if(conner.full){
            driver.rumble(0.25);
          }
          else{
            eat();
          }
        }
      }
    }

    public void test(String xcited, String tiny_lines){
      if(driver.get_but(xcited)){
        move_out(true);
      }
      else{
        move_out(false);
      }

      if(driver.get_but(tiny_lines)){
        set_motors(0.8);
      }
      else{
        set_motors(0);
      }
    }

    public void set_motors(double speed){
      motor.set(speed);
    }

    public void move_out(boolean direction){
      if(direction){
        lil_iz.set(kForward);
        jr_liliz.set(kForward);
      }
      else{
        lil_iz.set(kReverse);
        jr_liliz.set(kReverse);
      }
    }
      
    public String get_state(){
      return state;
    } 

    public void talk(){
      System.out.println(" Hi, I'm " + name + " I reach out and collect cargo");
    }

    public void eat(){
      state = "eating";
      set_motors(inhale);
      move_out(true);
    }

    public void sleep(){
      state = "sleeping";
      set_motors(0);
      move_out(false);
    }
  }


  public class Conveyor{
    private String name;
    public boolean full;
    private String state = "sleeping"; //firing, sleeping, munching

    //private double lil_sam;
    private double sir_sam;

    private boolean ballroom[] = {false, false};
    private boolean color_cargo[] = {false, true};
    private CANSparkMax motor_1 = new CANSparkMax(14, MotorType.kBrushless);
    private CANSparkMax motor_2 = new CANSparkMax(3, MotorType.kBrushless);
    RelativeEncoder eyespy_coder;
    private boolean ready_to_fire = false;
    DigitalInput izzys_spoon = new DigitalInput(0);
    public double flush;
    public double big_bow_wow = 40.0;
    public double lil_bow_wow = 20.0;
    public double sir_bow_wow = 85.0;

    public Conveyor(String _name, double flush){
      name = _name;
      System.out.println(name + " has rolled in ");
      this.flush = flush;
    }

    public void init(){
      eyespy_coder = motor_1.getEncoder();
    }

    public void check(){
      if(state.equals("firing")){
        if(eyespy_coder.getPosition() > sir_sam){ //are we done?
          ballroom[0] = false; 
          ballroom[1] = false; 
          state = "sleeping";
          full = false;
          set_motors(0);
          ready_to_fire = false;
        }
      }

      else if(state.equals("sleeping")){  
        if(!izzys_spoon.get()){
          if(!ballroom[0]){ //we has no cargo
            munch(big_bow_wow);
            ballroom[0] = true;  // {true, false}
            ready_to_fire = true;
          }
          else{ // has 1 in ballroom[0]
            munch(lil_bow_wow);
            ballroom[1] = true;  // {true, true}  
            full = true;
            driver.rumble(1.0);
            gunner.rumble(1.0); 
          }
        }
      }

      else if(state.equals("munching")){
        if(eyespy_coder.getPosition() > sir_sam){
          set_motors(0);
          state = "sleeping";
        }
      }
    }

    public void test(String yoke){
      if(driver.get_but(yoke)){
        set_motors(.4);
      }
      else{
        set_motors(0);
      }

      SmartDashboard.putNumber("con_enc", eyespy_coder.getPosition());
      //System.out.println(" There should be cargo here " + izzys_spoon.get());
    }

    public void set_motors(double speed){
      motor_1.set(speed);
      motor_2.set(-speed);
    }

     public boolean RTF(){
      return ready_to_fire;
    }

    public String get_state(){
      return state;
    } 

    public void munch(double turns){
      System.out.println(" Motors please move conner he is lazy ");
      set_motors(flush);
      state = "munching";
      sir_sam = eyespy_coder.getPosition() + turns;

    }

    public void fire(){
      state = "firing";
      sir_sam = eyespy_coder.getPosition() + sir_bow_wow;
      set_motors(flush);
    }

    public void talk(){
      System.out.println(" Hi, I'm " + name + " I move the cargo ");
    }

    public void set_team(){
      color_cargo[0] = team_blue;
    }
  }


  public class Shooter{
    private String name;
    private String state = "sleeping";
    private CANSparkMax motor_1 = new CANSparkMax(20, MotorType.kBrushless);
    private CANSparkMax motor_2 = new CANSparkMax(16, MotorType.kBrushless);
    private boolean ready_to_fire = false;
    // private double sgt_sam; 
    RelativeEncoder rhino = motor_1.getEncoder();
    private double lil_rhino = 2500; 
    private double max_speed;
    

    public Shooter(String _name, double speed){
      name = _name;
      this.max_speed = speed;
      System.out.println(name + " i am very happy ");
    }

    public void check(){
      ready_to_fire = false;
      if(gunner.prep_takeoff()){
        if(rhino.getVelocity() > lil_rhino){
          set_motors(max_speed);
          ready_to_fire = true;
        }
        else{
          set_motors(1);
          ready_to_fire = false;
        }
      }
      else{
        set_motors(0);
      }
      // System.out.println(conner.ready_to_fire + " :) " + ready_to_fire);
      //System.out.println(" I should be a movin " + rhino.getVelocity() + ready_to_fire);
    }

    public void test(String apple){
       if(driver.get_but(apple)){
        set_motors(max_speed);
      }
      else{
        set_motors(0);
      }
    }

    public void set_motors(double speed){
      motor_1.set(speed);
      motor_2.set(-speed);
    }

    public boolean RTF(){
      return ready_to_fire;
    }

    public String get_state(){
      return state;
    } 

    public void sleep(){
      state = "sleeping";
    }

    public void fire(){
      state = "firing";
    }

    public void talk(){
      System.out.println(" Hi, I'm " + name + " I get rid of cargo, pew pew, yuck!");
    }
  }


  public class Wheels{
    private String name;
    private double max_speed;
    private double max_turn = .7;
    private String turn_axis = "l_stick_x";
    private String speed_axis = "l_stick_y";
    private CANSparkMax front_LeftyMotor = new CANSparkMax(13, MotorType.kBrushless);
    private CANSparkMax back_LeftyMotor = new CANSparkMax(6, MotorType.kBrushless);
    private CANSparkMax front_RightyMotor = new CANSparkMax(7, MotorType.kBrushless);
    private CANSparkMax back_RightyMotor = new CANSparkMax(12, MotorType.kBrushless);

    private MotorControllerGroup rodger = new MotorControllerGroup(front_RightyMotor, back_RightyMotor);
    private MotorControllerGroup louie = new MotorControllerGroup(front_LeftyMotor, back_LeftyMotor);

    private final DifferentialDrive drivechain = new DifferentialDrive(louie, rodger);
    public double[] shut_up = {0.0, 0.0};
    
    public Wheels(String _name, double _max_speed){
      name = _name;
      max_speed = _max_speed;
      System.out.println(name + " has waddled in ");
      rodger.setInverted(true);
    }

    public void talk(){
      System.out.println( " Hi, I'm " + name + " I run the wheels, vroom vroom, Our max speed limit is " + max_speed);
    }

    private void test(){
      sensitive(driver.get_axis(speed_axis), driver.get_axis(turn_axis));
    }

    private void check(){
      if(gunner.is_top_dog()){
        System.out.println(" you know I am top dog! ");
      }
      else{
        sensitive(driver.control_panel()[0], driver.control_panel()[1]);
      }
     
    }

    private void drive(double speed, double turn){
      drivechain.arcadeDrive(-speed, turn);
    }

    private void sensitive(double speed, double raw_turn){
      double turn = Math.pow(raw_turn, 2.0);
      if (raw_turn < 0){
        turn = -turn;
      }
      
      speed = max_speed * speed;
      turn = max_turn * turn;

      drive(speed, turn);
    }

    public void auto(double speed, double turn){
      shut_up[0] = speed;
      shut_up[1] = turn;
    }

    public void shutupstupidbabyshutup(){
      drive(shut_up[0], shut_up[1]);
    }
  }


  public class SpyLord{
    private String spyroom [][]= {
        //auto 2 ball
        {"Starting Izzy", "None", "0.1"},
        {"Moving back", "Stop move", "3.0"},
        {"Stop", "None", "0.1"},
        {"Moving forward", "Stop move", "1.2"},
        {"Starting Sunny", "None", "2.5"},
        {"Starting Conner", "None", "4.5"},
        {"Moving back", "Stop", "1.0"},
        {"Done", "this will never run", "999.9"},
        // 4 ball auto
        /*{"Starting Izzy", "None", "0.1"},
        {"Moving back", "Stop move", "3.0"},
        {"Stop", "None", "0.1"},
        {"Moving forward", "Stop move", "1.2"},
        {"Starting Sunny", "None", "2.5"},
        {"Starting Conner", "Stop", "4.5"},
        {"Starting Izzy", "None", "5.0"},
        {"Left back", "None", "2.0"},
        {"Moving back", "Stop move", "2.0"},
        {"Moving forward", "None", "2.0"},
        //{"Right forward", "Stop move", "3.0"},
        {"Starting Sunny", "None", "2.5"},
        {"Starting Conner", "Stop", "4.5"},
        {"Done", "this will never run", "999.9"},
        */
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
        case "Moving back":
          System.out.println("The wheels are moving back!");
          wally.auto(-.4, 0);
          break;
        case "Moving forward":
          System.out.println("Going Forward");
          wally.auto(.4, 0);
          break;
        case "Stop move":
          System.out.println("Stop Moving");
          wally.auto(0, 0);
          break;
        case "Starting Sunny":
          System.out.println("Sunny Starting");
          sunny.set_motors(.78);
          break;
        case "None":
          System.out.println("Nothing");
          break;
        case "Starting Conner":
          System.out.println("Conner Starting");
          conner.set_motors(.7);
          break;
        case "Starting Izzy":
          System.out.println("Izzy Starting");
          izzy.move_out(true);
          izzy.set_motors(.8);
          break;
        case "Left back":
          System.out.println("Turning left back");
          wally.auto(-.4,.3);
          break;
        case "Right forward":
        System.out.println("Turning right forward");
        wally.auto(.4, .3);
        case "Stop":
          System.out.println("Stopping");
          sunny.set_motors(0);
          conner.set_motors(0);
          wally.auto(0, 0);
          izzy.set_motors(0);
          izzy.move_out(false);
          break;
        default:
          break;
      }
    }

    public void talk(){
      System.out.println( " Hi, I'm " + name + " I am the boss of autonomous, you can look at tasks in the spyroom and make sure there is a function for it in the briefcase");
    }

  }


  public class NeoPixel{
    private String name;
    private AddressableLED billy_strip;
    private AddressableLEDBuffer billy_buffer;

    private int NUM_LEDS = 24;
    // Store what the last hue of the first pixel is
    private int m_rainbowFirstPixelHue;
    private String state = "rainbow";
    private int groovey_counter = 0;
    private int funky_counter = 0;
    private boolean lightroom[] = {false, false};
    
    public NeoPixel(String _name){
      name = _name;
      System.out.println("nice to meet you i'm" + name + " I shine bring with colors! ");
    }

    public void check(){
      lightroom = event_chk(driver.color(), lightroom[1]);
      if(lightroom[0]){  // we saw an event  on the button
        funky_counter++; 
        if(funky_counter == 4){
          funky_counter = 0;
        }
        if(funky_counter == 0){
          state = "rainbow";
        }
        else if(funky_counter == 1){
          state = "seeing";
        }
        else if(funky_counter == 2){
          state = "wave";
        }
        else if(funky_counter == 3){
          state = "sleeping";
        }
        else{
          System.out.println("I should not be here, I don't know this emotion");
        }
      }
      

      if(state.equals("rainbow")){ //0
        rainbow();
      }

      if(state.equals("seeing")){ //1
        see();
      }

      if(state.equals("wave")){  //2
        wave();
      }

      if(state.equals("sleeping")){ //3
        sleep();
      }

      billy_strip.setData(billy_buffer);
    }

    public void whole_strip(int red, int green, int blue){
      for(int i = 0; i < billy_buffer.getLength(); i++){
        billy_buffer.setRGB(i, red, green, blue);
      }
    }

    public void sleep(){
      whole_strip(0, 0, 0);
    }

    public void see(){
      for(int i = 0; i < billy_buffer.getLength(); i++){
        if(i < 10){
          if(izzy.get_state().equals("sleeping")){
            billy_buffer.setRGB(i, 50, 0, 0);
          }
          else if(izzy.get_state().equals("eating")){
            billy_buffer.setRGB(i, 0, 0, 50);
          } 
          else{
            billy_buffer.setRGB(i, 0, 0 ,0);
          }
        } 
        else if(i < 20){
          if(conner.get_state().equals("sleeping")){
            billy_buffer.setRGB(i, 50, 0 ,0);
          }
          else if(conner.get_state().equals("eating")){
            billy_buffer.setRGB(i, 0, 00, 50);
          }
          else if(conner.get_state().equals("moving")){
            billy_buffer.setRGB(i, 50, 0, 50);
          }
          else if(conner.get_state().equals("firing")){
            billy_buffer.setRGB(i, 0, 50, 0);
          }
          else{
            billy_buffer.setRGB(i, 0, 0, 0);
          }
        }  
        else if(i < 30){
          if(todd.get_state().equals("sleeping")){
            billy_buffer.setRGB(i, 50, 0 ,0);
          }
          else if(todd.get_state().equals("firing")){
            billy_buffer.setRGB(i, 0, 50, 0);
          }
          else{
            billy_buffer.setRGB(i, 0, 0, 0);
          }
        }  
        else if(i < 40){
          if(sunny.get_state().equals("sleeping")){
            billy_buffer.setRGB(i, 50, 0, 0);
          }
          else if(sunny.get_state().equals("firing")){
            billy_buffer.setRGB(i, 0, 50, 0);
          }
          else{
            billy_buffer.setRGB(i, 0, 0, 0);
          }
        }
      }
    }

    private void rainbow() {
      // For every pixel
      for (var i = 0; i < billy_buffer.getLength(); i++) {
        // Calculate the hue - hue is easier for rainbows because the color
        // shape is a circle so only one value needs to precess
        final var hue = (m_rainbowFirstPixelHue + (i * 180 / billy_buffer.getLength())) % 180;
        // Set the value
        billy_buffer.setHSV(i, hue, 255, 128);
      }
      
      // Increase by to make the rainbow "move"
      m_rainbowFirstPixelHue += 3;
      // Check bounds
      m_rainbowFirstPixelHue %= 180;
    }

    private void wave(){
      for (var i = 0;  i < billy_buffer.getLength(); i++){
        if (i - groovey_counter >= 0 && i - groovey_counter  <= 3){
          billy_buffer.setRGB(i, 50, 0, 100);
        }
        else{
          billy_buffer.setRGB(i, 0, 0, 0);
        }
      }
      groovey_counter++;
    }


    public void init(){
      billy_strip = new AddressableLED(2);
      billy_buffer = new AddressableLEDBuffer(NUM_LEDS); // <-- total # of led
      billy_strip.setLength(billy_buffer.getLength());
      billy_strip.setData(billy_buffer);
      billy_strip.start();
    }

    public void talk(){
      System.out.println(" Hi, I'm " + name + " I am the color master and can tell you different agents moods");
    }
   
  }

 
  public class Turret{
    private String name;
    private String state = "sleeping";
    private CANSparkMax motor = new CANSparkMax(19, MotorType.kBrushless);
    private PIDController mr_pid_peter = new PIDController(kP, kI, kD);
    private boolean ready_to_fire = false;

    private double initial_setpoint = 0.0;
    private double lil_louie = -30.0;
    private double lil_rodger = 270.0;


    private static final double kP = 0.004;
    private static final double kI = 0.0;
    private static final double kD = 0.0;

    RelativeEncoder spyeye_coder;

    public Turret(String _name){
      name = _name;
      System.out.println(name + " I spin right round, when we go down ");
    }
    
    public void init(){     
      mr_pid_peter.setSetpoint(initial_setpoint);
      spyeye_coder = motor.getEncoder();
      spyeye_coder.setPositionConversionFactor(1.9); //  revolutions  ==>  38:144 1:3 make this degrees   1/360deg  11.3rev
      spyeye_coder.setPosition(initial_setpoint);
    }

    public void check(){
      if(gunner.get_but(gunner.lock_on_button)){ // looking at you lucy ;)
        motor.set(-mr_pid_peter.calculate(new_setpoint()));
      }
      else{       
        if(lil_louie > spyeye_coder.getPosition()){
          if(gunner.move_todd() < 0){
            gunner.rumble(.25);
            motor.set(0); 
          }
          else{
            set_motors(gunner.move_todd());
          }
        }
        else if(spyeye_coder.getPosition() > lil_rodger){
          if(gunner.move_todd() > 0){
            gunner.rumble(.25);
            motor.set(0);
          }
          else{
            set_motors(gunner.move_todd());
          }
        }
        else{
          set_motors(gunner.move_todd());
        }
      }
      System.out.println(spyeye_coder.getPosition());
    }

    public void set_motors(double speed){
      motor.set(speed);
    }

     public boolean RTF(){
      return ready_to_fire;
    }

    public String get_state(){
      return state;
    } 

    public void sleep(){
      state = "sleeping";
    }

    public void fire(){
      state = "firing";
    }

    public double new_setpoint(){
      //get new setpoint from lucy and check for out of range
      double desired_pos = spyeye_coder.getPosition() + lucy.angles()[0];
     
      if(lil_louie < desired_pos || lil_rodger > desired_pos) {
        return desired_pos;
      }
      return spyeye_coder.getPosition(); 
    }
    
    public void test(String left_triangle, String right_triangle){
      if(gunner.get_axis(left_triangle) > 0.05){
        motor.set(-gunner.get_axis(left_triangle)*0.3 );
      }
      else if(gunner.get_axis(right_triangle) > 0.05){
        motor.set(gunner.get_axis(right_triangle) *0.3);
      }
      else{
        motor.set(0);
      }
    }

    public void talk(){
      System.out.println(" Hi, I'm " + name + " I get rid of cargo, swoosh pew, bye bye!");
    }
  }


  public class LimeLight{
    private String name;
    public NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    public NetworkTableEntry tx = table.getEntry("tx");
    public NetworkTableEntry ty = table.getEntry("ty");
    public NetworkTableEntry ta = table.getEntry("ta");
    // public double crossroads[] = {0,0};
    // public double le_pixels[] = {0,0};
    public double le_angles[] = {0,0};

    public LimeLight(String _name){
      name = _name;
      System.out.println(name + " i see all, dun dun dun ");
    }

    public void check(){
      //if(gunner.lock_on()){
        vogue();
      //}
      SmartDashboard.putNumber("lucy_cam0", le_angles[0]);
      SmartDashboard.putNumber("lucy_cam1", le_angles[1]);
    }

    public void test(){
      vogue();
      lights(true);
      SmartDashboard.putNumber("lucy_cam0", le_angles[0]);
      SmartDashboard.putNumber("lucy_cam1", le_angles[1]);
    
    }

    public void talk(){
      System.out.println(" Hi, I'm " + name + " I am the camera which captures all angles, vogue! ");
    }

    public void vogue(){
      le_angles[0] = tx.getDouble(0.0);
      le_angles[1] = ty.getDouble(0.0);
      
      // crossroads[0] = (1/160) * (le_pixels[0] - 159.5);
      // crossroads[1] = (1/120) * (119.5 - le_pixels[1]);

      // le_angles[0] = Math.atan2(1,(2.0 * Math.tan(54/2))/2 * crossroads[0]);
      // le_angles[1] = Math.atan2(1,(2.0 * Math.tan(41/2))/2 * crossroads[1]);
    }

    public double[] angles(){
      return le_angles;
    }

    public void lights(boolean on){
      if(on){
        table.getEntry("ledMode").setNumber(3);
      }
      else{
        table.getEntry("ledMode").setNumber(1);
      }
    }

  }
  

  public class Elevator{
    private String name;
    private CANSparkMax motor_1 = new CANSparkMax(15, MotorType.kBrushless);
    private CANSparkMax motor_2 = new CANSparkMax(10, MotorType.kBrushless);

    public Elevator(String _name){
      name = _name;
      System.out.println(name + " I can get really tall watch! ");
    }

    public void check(){
      // Sunny wishes for work T^T 
    }

    public void test(String right_bum, String left_bum){
      if(driver.get_but(right_bum)){
        motor_1.set(0.4);
        motor_2.set(0.4);
      }
      else if(driver.get_but(left_bum)){
        motor_1.set(-0.3);
        motor_2.set(-0.3);
      }
      else{
        motor_1.set(0);
        motor_2.set(0);
      }
    }
    
    public void talk(){
      System.out.println(" Hello! " + name + " I can carry the whole robot on the monkey bars!");
    }
  }
  



  boolean[] event_chk(boolean now, boolean past){
    boolean partyroom[] = {false, false};
    if (now != past){
      partyroom[0] = true;
        if(now){
          partyroom[1] = true;
        }
    }
    return partyroom;  //{event, button state}
  }

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    // rodger.setInverted(true);
    conner.set_team();
    nia.init();
    conner.init();
    todd.init();


    // Agents will announce themselves
    archie.talk();
    wally.talk();
    nia.talk();
    izzy.talk();
    conner.talk();
    sunny.talk();
    driver.talk();
    gunner.talk();
    todd.talk();
    elle.talk();
    lucy.talk();
    pcmCompressor.enableDigital();
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    System.out.println("We are starting autonomous mode");
    archie.autonomous_counter = 0;
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
        wally.shutupstupidbabyshutup();
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

    // having a talk talk, yes yes check check

    driver.check();
    gunner.check();
    lucy.check();
    wally.check();
    conner.check();
    izzy.check();
    todd.check();
    sunny.check();
    // nia.check();
    gunner.fire();


    // \(^u^ /) hi
    //wally.check(speed=driver.get_axis('l_stick_y'), turn=driver.get_axis('r_stick_x'))

  }




  @Override
  public void disabledInit() {
    // Stop the rumble when entering disabled
    // joy0.setRumble(RumbleType.kLeftRumble, 0.0);
    // joy0.setRumble(RumbleType.kRightRumble, 0.0);
  }
  @Override
  public void disabledPeriodic() {
    lucy.test();

  }
  @Override
  public void testInit() {
   
  }
  @Override
  public void testPeriodic() {
    driver.test();
    gunner.test();
    izzy.test("x", "x");
    sunny.test("b");
    conner.test("b");
    todd.test("r_trig", "l_trig");
    elle.test("l_bum", "r_bum");
    wally.test(); 
    //nia.check();
    lucy.test();

    //todd.set_motors(.3);


  }


  

}  // <--- Leave this close brace (Look at those numbers!)
