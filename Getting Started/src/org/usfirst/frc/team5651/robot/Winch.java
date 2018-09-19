package org.usfirst.frc.team5651.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Command;

public class Winch extends Command{

	Spark winch = new Spark(4);
	
	public
	void initialize() {
		winch.set(1.0);
		System.out.println("Test Winch");
	}
	protected void end() {
		//winch.set(0.0);
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
