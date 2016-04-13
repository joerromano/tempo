package edu.brown.cs32.tempo.workout;

public class Run extends Workout{
	private double mileage;	
	
	public void setMileage(double mileage){
		this.mileage = mileage;
	}
	
	public double getMileage(){
		return mileage;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public String toHTML() {
		return null;
	}

}
