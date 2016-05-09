package edu.brown.cs32.tempo.graph;

import java.util.HashMap;
import java.util.Map;

import edu.brown.cs32.tempo.workout.Workout;

public class Vertex{
	  private String id;
	  private Workout workout;
	  private int frequency;
	  private Map<Vertex, Edge> forward;
	  private int date;
	  private Map<Vertex, Edge> sameDay;
	  
	public Vertex(Workout w, int date){
		setWorkout(w);
		this.id = w.getId();
		setForward(new HashMap<Vertex, Edge>());
		sameDay = new HashMap<Vertex, Edge>();
		this.date = date;
		frequency = 1;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public Workout getWorkout() {
		return workout;
	}

	public void setWorkout(Workout workout) {
		this.workout = workout;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public void incFrequency(int i){
		this.frequency += i;
	}

	public void updateConnectionForward(Vertex current) {
		Edge e = sameDay.get(current);
		if(e != null){
			e.incFrequency(1);
		} else {
			sameDay.put(current, new Edge(this, current));
		}
		
	}

	public void updateConnectionSameDay(Vertex current) {
		Edge e = sameDay.get(current);
		if(e != null){
			e.incFrequency(1);
		} else {
			sameDay.put(current, new Edge(this, current));
		}
	}

	public int compareFrequencyTo(Vertex e2) {
		return frequency - e2.getFrequency();
	}

	public Map<Vertex, Edge> getForward() {
		return forward;
	}

	public void setForward(Map<Vertex, Edge> forward) {
		this.forward = forward;
	}
}