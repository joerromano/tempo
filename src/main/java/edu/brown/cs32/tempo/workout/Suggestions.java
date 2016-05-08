package edu.brown.cs32.tempo.workout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.json.JSONException;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import edu.brown.cs32.tempo.graph.Vertex;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Group;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;

public class Suggestions {
	// private Graphs graph;
	public final static int NUM_WORKOUTS = 5;
	private final DateTime START_OF_TIME = new DateTime(Long.MIN_VALUE);

	// essentially a layered graph
	private Map<Integer, Map<String, Vertex>> layers;
	private Map<Integer, Double> iTracker;
	private Map<Integer, Integer> tTracker;
	
	private Group group;

	public List<Workout> getSuggestions(Group group, DateTime weekDate) {
		this.group = group;
		layers = new HashMap<Integer, Map<String, Vertex>>();
		iTracker = new HashMap<Integer, Double>();
		tTracker = new HashMap<Integer, Integer>();
		buildMaps();

		// Make sure that weekDate is the last Saturday before the week
		// we are trying to suggest
		for (Athlete a : group.getMembers()) {
			addWorkouts(a.getWorkouts(START_OF_TIME.toDate(), weekDate.toDate()));
		}

		findSuggestions();
		return null;
	}

	private void setWeather(){
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyCLY-qNiBx8jDzJAzLU1S8tewokC6BKQ_M");
		float lat = Float.NaN;
		float lng = Float.NaN;
		String location = group.getTeam.getLocation().getPostalCode(); //Should be getLocation/getTeam for group
		try {
			GeocodingResult[] results =  GeocodingApi.geocode(context,
			    "location").await();
			lat = (float) results[0].geometry.location.lat;
			lng = (float) results[0].geometry.location.lng;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Google API error");
		}
		
		OpenWeatherMap owm = new OpenWeatherMap("d0f921e12344207ea2681ceee2389c41");
		if(lat != Float.NaN && lng != Float.NaN){
			DailyForecast df;
			try {
				df = owm.dailyForecastByCoordinates(lat, lng, Byte.parseByte("7"));
				
				 if (!df.isValid()) {
			            System.out.println("Reponse is inValid!");
			        } else {
			            System.out.println("Reponse is Valid!");

			            if (df.hasCityInstance()) {
			                DailyForecast.City city = df.getCityInstance();
			                if (city.hasCityName()) {
			                    if (city.hasCityCode()) {
			                        System.out.println("City code: " + city.getCityCode());
			                    }
			                    if (city.hasCityName()) {
			                        System.out.println("City name: " + city.getCityName());
			                    }
			                    System.out.println();
			                }
			            }

			            for (int i = 0; i < df.getForecastCount(); i++) {
			                DailyForecast.Forecast forecast = df.getForecastInstance(i);
			                if (forecast.hasDateTime()) {
								System.out.println(forecast.getTemperatureInstance().getDayTemperature());
								System.out.println(forecast.getRain());
								System.out.println(forecast.getSnow());
								System.out.println(forecast.getHumidity());
								System.out.println(forecast.getWindSpeed());
			                }
			            }
			        }
			} catch (NumberFormatException | JSONException e) {
				e.printStackTrace();
				System.out.println("Weather API error");
			}
		}
	}
	
	public void findSuggestions(){
		double multi = 1.0; //Will change with weather
		for(int i = 1; i <= 7; i++){
			findSuggestions(layers.get(i).values(), iTracker.get(i)/tTracker.get(i), multi);
		}
	}
	
	public void findSuggestions(Collection<Vertex> graph, double average, double multi){
		Iterator<Vertex> sorted  = graph.stream().sorted((e1, e2) -> e1.compareFrequencyTo(e2))
				.iterator();
		List<Vertex> alov = new ArrayList<Vertex>();
		double avg = average;
		double oneWorkout;
		Vertex v = null;
		while(sorted.hasNext()) {
			v = sorted.next();
			oneWorkout = v.getWorkout().getScore()*v.getWorkout().getIntensity()*multi;
			
			if(oneWorkout < avg){
				alov.add(v);
				avg -= oneWorkout;
			}
		}
	}
	
	public void addWorkouts(Collection<Workout> workouts) {
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

	private boolean oneDay(DateTime day1, Date day2) {
		DateTime date2 = new DateTime(day2.getTime());
		DateTime date1 = day1.plusDays(1);

		return date1.getYear() == date2.getYear() && date1.getMonthOfYear() == date2.getMonthOfYear()
				&& date1.getDayOfYear() == date2.getDayOfYear();
	}

	private void buildMaps() {
		for (int i = 1; i >= 7; i++) {
			iTracker.put(i, 0.0);
			tTracker.put(i, 0);
			layers.put(i, new HashMap<String, Vertex>());
		}
	}
}
