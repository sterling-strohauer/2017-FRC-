package org.usfirst.frc.team5651.robot;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Command;

import java.util.Vector;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Spark;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	static final int frontLeft = 2;
	static final int rearLeft = 3;
	static final int frontRight = 0;
	static final int rearRight = 1;
	
	//Command winchCommand;
	
	RobotDrive myRobot = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
	Joystick stick = new Joystick(0);
	Timer timer = new Timer();
	Button button = new JoystickButton(stick, 1);
	DoubleSolenoid armExtend = new DoubleSolenoid(0,1);
	DoubleSolenoid pusher = new DoubleSolenoid(2,3);
	DoubleSolenoid shelf = new DoubleSolenoid(4,5);
	
	//Compressor compressor;
	//Talon winch = new Talon(7);//4
	//Spark winch2 = new Spark(6);
	//Talon winch2 = new Talon(4);
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
	}
	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
		Compressor c = new Compressor(0);

		c.setClosedLoopControl(true);
		armExtend.set(DoubleSolenoid.Value.kReverse);
	}

	/**
	 * This function is called periodically during autonomous
	*/
	@SuppressWarnings("deprecation")
	@Override
	public void autonomousPeriodic() {
	
		if (timer.get() <= 2.5) {
			myRobot.drive(-0.5,0.0); //negative curve left positive turn right (value can only be between -1.0 and 1.0)
		}else{
			myRobot.drive(0.0, 0.0);
		} 
		
		// Drive for 10 seconds
		/*if (timer.get() < 10.0) {
			myRobot.drive(-0.5, 0.0); //negative curve left positive turn right (value can only be between -1.0 and 1.0)
		} else if(timer.get() < 30){
			myRobot.drive(0.5, 0.0); // stop robot
			//myRobot.drive(frontRight, 0);
		}*/
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {
		//winchCommand = new RunWinchCommand();
		Compressor c = new Compressor(0);
		c.setClosedLoopControl(true);
	}

	/**
	 * This function is called periodically during operator control
	 */
	
	int ShelfToggle = 0;
	boolean buttonRising = false;
	
	@Override
	public void teleopPeriodic() {
		myRobot.arcadeDrive(stick.getY(), stick.getX()*-1);
		//myRobot.arcadeDrive(stick);
		
		//System.out.println(stick.getX()*-1 + ", " + stick.getY());
		
		if(stick.getRawButton(1)){
			armExtend.set(DoubleSolenoid.Value.kOff);
			armExtend.set(DoubleSolenoid.Value.kReverse);
		}else{
			armExtend.set(DoubleSolenoid.Value.kForward);
		}
		//System.out.println("slider ="+stick.getThrottle() + "= slider");
		//System.out.println("pov = "+stick.getPOV(0) + " = pov");
		/*if(stick.getThrottle() == -1){
			shelf.set(DoubleSolenoid.Value.kOff);
			shelf.set(DoubleSolenoid.Value.kForward);
		}
		if(stick.getThrottle() == 1) {
			shelf.set(DoubleSolenoid.Value.kOff);
			shelf.set(DoubleSolenoid.Value.kReverse);
			
		}
		else{
			shelf.set(DoubleSolenoid.Value.kOff);
			
		}*/
		if(stick.getPOV(0) == 0){
			shelf.set(DoubleSolenoid.Value.kOff);
			shelf.set(DoubleSolenoid.Value.kForward);
		}
		
		if(stick.getPOV(0) == 180){
			shelf.set(DoubleSolenoid.Value.kOff);
			shelf.set(DoubleSolenoid.Value.kReverse);
		}
		if(stick.getPOV(0) == -1){
			shelf.set(DoubleSolenoid.Value.kOff);
			
		}
		
		if(stick.getRawButton(2)){
			pusher.set(DoubleSolenoid.Value.kOff);
			pusher.set(DoubleSolenoid.Value.kForward);
		}else{
			pusher.set(DoubleSolenoid.Value.kOff);
			pusher.set(DoubleSolenoid.Value.kReverse);
		}
	}
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	class RunWinchCommand extends Command {
		@Override
		public
		void initialize(){
			System.out.println("Winch is initialized");
		}
		@Override
		public
		void execute() {
			
			//winch.set(1.0);
		} 
		protected void end() {
			//winch.set(0.0);
		}
		@Override		protected boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}
	}
}
