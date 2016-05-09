package edu.brown.cs32.tempo.graph;


public class Edge {
	private double weight;
	private int frequency;
	private Vertex start;
	private Vertex end;
	
	public Edge(int frequency, double weight, Vertex v1, Vertex v2){
		this.setWeight(1.0);
		this.frequency = 1;
		setStart(v1);
		setEnd(v2);
	}
	
	public Edge(Vertex v1, Vertex v2){
		this.setWeight(1.0);
		this.frequency = 1;
		setStart(v1);
		setEnd(v2);
	}
	
	public void incFrequency(int i){
		frequency += i;
		setWeight(1/frequency);
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Vertex getStart() {
		return start;
	}

	public void setStart(Vertex start) {
		this.start = start;
	}

	public Vertex getEnd() {
		return end;
	}

	public void setEnd(Vertex end) {
		this.end = end;
	}
	

}
