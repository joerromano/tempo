package edu.brown.cs32.tempo.workout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import edu.brown.cs32.tempo.graph.Vertex;

public class SuggestionGenerator {
	private static final double HARD_WEEK = 1.5;
	private static final double LIGHT_WEEK = .75;

	public static ImmutableList<Workout> hardWeek(Map<Integer, Map<String, Vertex>> layers, Map<Integer, Double> multi,
			Map<Integer, Double> iTracker, Map<Integer, Integer> tTracker) {
		List<Workout> alow = new ArrayList<Workout>();
		double weeklyTarget;
		Iterator<Vertex> sorted;
		Vertex v;
		double vScore;
		for (int i = 1; i <= 7; i++) {
			weeklyTarget = (iTracker.get(i) / tTracker.get(i)) * HARD_WEEK;
			sorted = layers.get(1).values().stream().sorted((e1, e2) -> e1.compareIntensity(e2)).iterator();

			while (sorted.hasNext()) {
				v = sorted.next();
				vScore = v.getWorkout().getScore() * v.getWorkout().getIntensity();
				if (vScore < weeklyTarget) {
					weeklyTarget -= vScore;
					alow.add(v.getWorkout());
				}
			}
		}

		return ImmutableList.copyOf(alow);
	}

	public static ImmutableList<Workout> lightWeek(Map<Integer, Map<String, Vertex>> layers, Map<Integer, Double> multi,
			Map<Integer, Double> iTracker, Map<Integer, Integer> tTracker) {
		List<Workout> alow = new ArrayList<Workout>();
		double weeklyTarget;
		Iterator<Vertex> sorted;
		Vertex v;
		double vScore;
		for (int i = 1; i <= 7; i++) {
			weeklyTarget = (iTracker.get(i) / tTracker.get(i)) * LIGHT_WEEK;
			sorted = layers.get(1).values().stream().sorted((e1, e2) -> e2.compareIntensity(e1)).iterator();

			while (sorted.hasNext()) {
				v = sorted.next();
				vScore = v.getWorkout().getScore() * v.getWorkout().getIntensity();
				if (vScore < weeklyTarget) {
					weeklyTarget -= vScore;
					alow.add(v.getWorkout());
				}
			}
		}

		return ImmutableList.copyOf(alow);
	}

	public static ImmutableList<Workout> avgWeek(Map<Integer, Map<String, Vertex>> layers, Map<Integer, Double> multi,
			Map<Integer, Double> iTracker, Map<Integer, Integer> tTracker) {
		List<Workout> alow = new ArrayList<Workout>();
		double weeklyTarget;
		Iterator<Vertex> sorted;
		Vertex v;
		double vScore;
		for (int i = 1; i <= 7; i++) {
			
			weeklyTarget = (iTracker.get(i) / tTracker.get(i));
			sorted = layers.get(1).values().stream().sorted((e1, e2) -> e2.compareIntensity(e1)).iterator(); // Allowing it to be a little easy

			double middle = layers.get(1).size() / 2;
			int count = 1;
			while (sorted.hasNext()) {
				if (count >= middle) {
					v = sorted.next();
					vScore = v.getWorkout().getScore() * v.getWorkout().getIntensity();
					if (vScore < weeklyTarget) {
						weeklyTarget -= vScore;
						alow.add(v.getWorkout());
					}
				} else {
					count++;
				}
			}
		}
		System.out.println(alow);
		return ImmutableList.copyOf(alow);
	}
	
	public static ImmutableList<Workout> commonWeek(Map<Integer, Map<String, Vertex>> layers, Map<Integer, Double> multi,
			Map<Integer, Double> iTracker, Map<Integer, Integer> tTracker) {
		
		List<Workout> alow = new ArrayList<Workout>();
		double weeklyTarget;
		Iterator<Vertex> sorted;
		Vertex v;
		double vScore;
		for (int i = 1; i <= 7; i++) {
			weeklyTarget = (iTracker.get(i) / tTracker.get(i));
			sorted = layers.get(1).values().stream().sorted((e1, e2) -> e1.compareFrequencyTo(e2)).iterator(); // Allowing it to be a little easy
		
			while (sorted.hasNext()) {
					v = sorted.next();
					vScore = v.getWorkout().getScore() * v.getWorkout().getIntensity();
					if (vScore < weeklyTarget) {
						weeklyTarget -= vScore;
						alow.add(v.getWorkout());
					}
			}
		}

		return ImmutableList.copyOf(alow);
	}
	
	public static ImmutableList<Workout> recentWeek(Map<Integer, Map<String, Vertex>> layers, Map<Integer, Double> multi,
			Map<Integer, Double> iTracker, Map<Integer, Integer> tTracker) {
		
		List<Workout> alow = new ArrayList<Workout>();
		double weeklyTarget;
		Iterator<Vertex> sorted;
		Vertex v;
		double vScore;
		for (int i = 1; i <= 7; i++) {
			weeklyTarget = (iTracker.get(i) / tTracker.get(i));
			sorted = layers.get(1).values().stream().sorted((e1, e2) -> e1.getWorkout().getDate().compareTo(e2.getWorkout().getDate())).iterator(); // Allowing it to be a little easy

			while (sorted.hasNext()) {
					v = sorted.next();
					vScore = v.getWorkout().getScore() * v.getWorkout().getIntensity();
					if (vScore < weeklyTarget) {
						weeklyTarget -= vScore;
						alow.add(v.getWorkout());
					}
			}
		}

		return ImmutableList.copyOf(alow);
	}
}

