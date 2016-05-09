package edu.brown.cs32.tempo.workout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs32.tempo.graph.Vertex;

public class SuggestionGenerator {
	private static final double HARD_WEEK = 1.5;
	private static final double LIGHT_WEEK = .75;
	private static final double AVERAGE_WEEK = 1.1;

	public static ImmutableMap<Integer, List<Workout>> hardWeek(Map<Integer, Map<String, Vertex>> layers,
			Map<Integer, Double> multi, Map<Integer, Double> iTracker, Map<Integer, Integer> tTracker) {

		Map<Integer, List<Workout>> mow = new HashMap<Integer, List<Workout>>();
		double weeklyTarget;
		Iterator<Vertex> sorted;
		Vertex v;
		double vScore;

		for (int i = 1; i <= 7; i++) {
			mow.put(i, new ArrayList<Workout>());
			weeklyTarget = (iTracker.get(i) / tTracker.get(i)) * HARD_WEEK;
			sorted = layers.get(i).values().stream().sorted((e1, e2) -> e1.compareIntensity(e2)).iterator();

			while (sorted.hasNext()) {
				v = sorted.next();
				vScore = v.getWorkout().getScore() * v.getWorkout().getIntensity();
				if (vScore < weeklyTarget) {
					weeklyTarget -= vScore;
					mow.get(i).add(v.getWorkout());
				}
			}

		}

		return ImmutableMap.copyOf(mow);
	}

	public static ImmutableMap<Integer, List<Workout>> lightWeek(Map<Integer, Map<String, Vertex>> layers,
			Map<Integer, Double> multi, Map<Integer, Double> iTracker, Map<Integer, Integer> tTracker) {
		Map<Integer, List<Workout>> mow = new HashMap<Integer, List<Workout>>();
		double weeklyTarget;
		Iterator<Vertex> sorted;
		Vertex v;
		double vScore;
		for (int i = 1; i <= 7; i++) {
			mow.put(i, new ArrayList<Workout>());
			weeklyTarget = (iTracker.get(i) / tTracker.get(i)) * LIGHT_WEEK;
			sorted = layers.get(i).values().stream().sorted((e1, e2) -> e2.compareIntensity(e1)).iterator();

			while (sorted.hasNext()) {
				v = sorted.next();
				vScore = v.getWorkout().getScore() * v.getWorkout().getIntensity();
				if (vScore < weeklyTarget) {
					weeklyTarget -= vScore;
					mow.get(i).add(v.getWorkout());
				}
			}

		}

		return ImmutableMap.copyOf(mow);
	}

	public static ImmutableMap<Integer, List<Workout>> avgWeek(Map<Integer, Map<String, Vertex>> layers,
			Map<Integer, Double> multi, Map<Integer, Double> iTracker, Map<Integer, Integer> tTracker) {
		Map<Integer, List<Workout>> mow = new HashMap<Integer, List<Workout>>();
		double weeklyTarget;
		Iterator<Vertex> sorted;
		Vertex v;
		double vScore, middle;
		int count;
		for (int i = 1; i <= 7; i++) {
			mow.put(i, new ArrayList<Workout>());
			weeklyTarget = (iTracker.get(i) / tTracker.get(i));
			sorted = layers.get(i).values().stream().sorted((e1, e2) -> e2.compareIntensity(e1)).iterator();
			middle = layers.get(i).size() / 2;
			count = 1;
			while (sorted.hasNext()) {
				if (count >= middle) {
					v = sorted.next();
					vScore = v.getWorkout().getScore() * v.getWorkout().getIntensity();
					if (vScore < weeklyTarget) {
						weeklyTarget -= vScore;
						mow.get(i).add(v.getWorkout());
					}
				} else {
					count++;
				}
			}

		}

		return ImmutableMap.copyOf(mow);
	}

	public static ImmutableMap<Integer, List<Workout>> commonWeek(Map<Integer, Map<String, Vertex>> layers,
			Map<Integer, Double> multi, Map<Integer, Double> iTracker, Map<Integer, Integer> tTracker) {

		Map<Integer, List<Workout>> mow = new HashMap<Integer, List<Workout>>();
		double weeklyTarget;
		Iterator<Vertex> sorted;
		Vertex v;
		double vScore;

		for (int i = 1; i <= 7; i++) {
			mow.put(i, new ArrayList<Workout>());
			weeklyTarget = (iTracker.get(i) / tTracker.get(i)) * AVERAGE_WEEK;
			sorted = layers.get(i).values().stream().sorted((e1, e2) -> e1.compareFrequencyTo(e2)).iterator();

			while (sorted.hasNext()) {
				v = sorted.next();
				vScore = v.getWorkout().getScore() * v.getWorkout().getIntensity();

				if (vScore <= weeklyTarget) {
					weeklyTarget -= vScore;
					mow.get(i).add(v.getWorkout());
				}
			}

		}

		return ImmutableMap.copyOf(mow);
	}

	public static ImmutableMap<Integer, List<Workout>> recentWeek(Map<Integer, Map<String, Vertex>> layers,
			Map<Integer, Double> multi, Map<Integer, Double> iTracker, Map<Integer, Integer> tTracker) {

		Map<Integer, List<Workout>> mow = new HashMap<Integer, List<Workout>>();
		double weeklyTarget;
		Iterator<Vertex> sorted;
		Vertex v;
		double vScore;
		for (int i = 1; i <= 7; i++) {
			mow.put(i, new ArrayList<Workout>());
			weeklyTarget = (iTracker.get(i) / tTracker.get(i));
			sorted = layers.get(i).values().stream()
					.sorted((e1, e2) -> e1.getWorkout().getDate().compareTo(e2.getWorkout().getDate())).iterator();

			while (sorted.hasNext()) {
				v = sorted.next();
				vScore = v.getWorkout().getScore() * v.getWorkout().getIntensity();
				if (vScore < weeklyTarget) {
					weeklyTarget -= vScore;
					mow.get(i).add(v.getWorkout());
				}
			}

		}

		return ImmutableMap.copyOf(mow);
	}
}
