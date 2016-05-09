package edu.brown.cs32.tempo.workout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableList;

import edu.brown.cs32.tempo.graph.Vertex;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Group;

public class Suggestions {
	// private Graphs graph;
	public final static int NUM_WORKOUTS = 5;
	private final static DateTime START_OF_TIME = new DateTime(Long.MIN_VALUE);

	// essentially a layered graph
	private static Map<Integer, Map<String, Vertex>> layers;
	private static Map<Integer, Double> iTracker;
	private static Map<Integer, Integer> tTracker;
	private static Map<Integer, Double> multi;

	public static ImmutableList<ImmutableList<Workout>>  getSuggestions(Group group, DateTime weekDate) {
		buildMaps();

		// Make sure that weekDate is the last Saturday before the week
		// we are trying to suggest
		for (Athlete a : group.getMembers()) {
			addWorkouts(a.getWorkouts(START_OF_TIME.toDate(), weekDate.toDate()));
		}

		try {
			 multi = Weather.getWeather(group);
		} catch (Exception e) {
			for(int i = 1; i <= 7; i++){
				multi.put(i, 1.0);
			}
			System.out.println("Could not get weather");
			e.printStackTrace();
		}
		
		return ImmutableList.of(SuggestionGenerator.avgWeek(layers, multi, iTracker, tTracker),
		SuggestionGenerator.commonWeek(layers, multi, iTracker, tTracker),
		SuggestionGenerator.hardWeek(layers, multi, iTracker, tTracker),
		SuggestionGenerator.lightWeek(layers, multi, iTracker, tTracker),
		SuggestionGenerator.recentWeek(layers, multi, iTracker, tTracker));

	}
	
	
	public static void addWorkouts(Collection<Workout> workouts) {
		int dayOfWeek;
		DateTime prevDate = START_OF_TIME;
		Iterator<Workout> sorted = workouts.stream().sorted((e1, e2) -> e1.getDate().compareTo(e2.getDate()))
				.iterator();

		List<Vertex> sameDays = new ArrayList<Vertex>();
		List<Vertex> yesterday = new ArrayList<Vertex>();
		Workout w = null;
		Vertex v;
		
		while (sorted.hasNext()) {
			w = sorted.next();
			dayOfWeek = (new DateTime(w.getDate().getTime())).getDayOfWeek();

			v = layers.get(dayOfWeek).get(w.getId());

			if (v != null) {
				v.incFrequency(1);

			} else {
				v = new Vertex(w, dayOfWeek);

				layers.get(dayOfWeek).put(w.getId(), v);
			}

			if (w.getDate().equals(prevDate)) {
				sameDays = makeConnectionCurrent(sameDays, v);
			} else if (oneDay(prevDate, w.getDate()) && prevDate.getDayOfWeek() != 6) {
				yesterday = sameDays;
				sameDays.clear();
				sameDays.add(v);

				makeConnectionForward(yesterday, v);
			} else {
				yesterday.clear();
				sameDays.clear();
				sameDays.add(v);
			}
			tTracker.put(dayOfWeek, tTracker.get(dayOfWeek) + 1);
			iTracker.put(dayOfWeek, iTracker.get(dayOfWeek) + w.getScore()*w.getIntensity());
		}

	}

	private static List<Vertex> makeConnectionCurrent(List<Vertex> sameDays, Vertex current) {
		for (Vertex v : sameDays) {
			v.updateConnectionSameDay(current);
			current.updateConnectionSameDay(v);
		}

		sameDays.add(current);
		return sameDays;
	}

	private static void makeConnectionForward(List<Vertex> yesterday, Vertex current) {
		for (Vertex v : yesterday) {
			v.updateConnectionForward(current);
		}
	}

	private static boolean oneDay(DateTime day1, Date day2) {
		DateTime date2 = new DateTime(day2.getTime());
		DateTime date1 = day1.plusDays(1);

		return date1.getYear() == date2.getYear() && date1.getMonthOfYear() == date2.getMonthOfYear()
				&& date1.getDayOfYear() == date2.getDayOfYear();
	}

	private static void buildMaps() {
		layers = new HashMap<Integer, Map<String, Vertex>>();
		iTracker = new HashMap<Integer, Double>();
		tTracker = new HashMap<Integer, Integer>();
		
		for (int i = 1; i >= 7; i++) {
			iTracker.put(i, 0.0);
			tTracker.put(i, 0);
			layers.put(i, new HashMap<String, Vertex>());
		}
	}

	public Map<Integer, Double> getMulti() {
		return multi;
	}

	public void setMulti(Map<Integer, Double> multi) {
		Suggestions.multi = multi;
	}
}
