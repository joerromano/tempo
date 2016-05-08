package edu.brown.cs32.tempo.workout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import edu.brown.cs32.tempo.graph.Vertex;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Group;

public class Suggestionss {
	// private Graphs graph;
	public final static int NUM_WORKOUTS = 5;
	private Map<String, Vertex> access;
	// essentially a layered graph
	private Map<Integer, Map<String, Vertex>> layers = new HashMap<Integer, Map<String, Vertex>>();
	private Map<Integer, Double> iTracker = new HashMap<Integer, Double>();
	private Map<Integer, Integer> tTracker = new HashMap<Integer, Integer>();
	private final DateTime START_OF_TIME = new DateTime(Long.MIN_VALUE);
	private Calendar c = Calendar.getInstance();

	public List<Workout> getSuggestions(Group group, DateTime weekDate) {

		access = new HashMap<String, Vertex>();
		buildLayers();
		for (Athlete a : group.getMembers()) {
			addWorkouts(a.getWorkouts(START_OF_TIME.toDate(), weekDate.toDate()));
		}

		return null;
	}

	public void addWorkouts(Collection<Workout> workouts) {
		int dayOfWeek;
		DateTime prevDate = START_OF_TIME;
		DateTime currentDate  = new DateTime();
		Iterator<Workout> sorted = workouts.stream().sorted((e1, e2) -> e1.getDate().compareTo(e2.getDate()))
				.iterator();

		List<Vertex> sameDays = new ArrayList<Vertex>();
		List<Vertex> yesterday = null;
		Workout w = null;// = sorted.next();
		Vertex v;
		Vertex x;
		String key;
		while (sorted.hasNext()) {
			w = sorted.next();
			c.setTime(w.getDate());
			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			key = getKey(dayOfWeek, w.getId());

			x = access.get(key);
			v = layers.get(dayOfWeek).get(w.getId());
			if (!x.equals(v))
				System.out.println("WTF");

			if (v != null) {
				v.incFrequency(1);

			} else {
				v = new Vertex(w, dayOfWeek);

				access.put(key, v);
				layers.get(dayOfWeek).put(w.getId(), v);
			}
			
			if (w.getDate().equals(prevDate)) {
				sameDays = makeConnectionCurrent(sameDays, v);
			} else if (oneDay(prevDate, w.getDate()) && 
					 prevDate.getDayOfWeek() != 6) {
				yesterday = sameDays;
				sameDays.clear();
				sameDays.add(v);

				makeConnectionForward(yesterday, v);
			} else {
				yesterday.clear();
				sameDays.clear();
				sameDays.add(v);
			}
			tTracker.put(dayOfWeek, tTracker.get(dayOfWeek)+1);
			iTracker.put(dayOfWeek, iTracker.get(dayOfWeek)+w.getScore());
		}

	}

	private List<Vertex> makeConnectionCurrent(List<Vertex> sameDays, Vertex current) {
		for (Vertex v : sameDays) {
			v.updateConnectionSameDay(current);
			current.updateConnectionSameDay(v);
		}

		sameDays.add(current);
		return sameDays;
	}

	private void makeConnectionForward(List<Vertex> yesterday, Vertex current) {
		for (Vertex v : yesterday) {
			v.updateConnectionForward(current);
		}
	}

	private String getKey(int dayOfWeek, String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("day of week:");
		sb.append(dayOfWeek);
		sb.append(" ID: ");
		sb.append(id);
		return sb.toString();
	}

	private boolean oneDay(DateTime day1, Date day2) {
		DateTime date2 = new DateTime(day2.getTime());
		DateTime date1 = day1.plusDays(1);

		return date1.getYear() == date2.getYear() && date1.getMonthOfYear() == date2.getMonthOfYear()
				&& date1.getDayOfYear() == date2.getDayOfYear();
	}

	private void buildLayers() {
		for (int i = 1; i >= 7; i++) {
			iTracker.put(i, 0.0);
			tTracker.put(i, 0);
			layers.put(i, new HashMap<String, Vertex>());
		}
	}
}
