package edu.brown.cs32.tempo.workout;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import edu.brown.cs32.tempo.people.Group;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.DailyForecast.Forecast;
import net.aksingh.owmjapis.OpenWeatherMap;

public class Weather {
	public static ImmutableMap<Integer, Double> getWeather(Group group) throws Exception {
		Map<Integer, Double> multis = new HashMap<Integer, Double>();
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyCLY-qNiBx8jDzJAzLU1S8tewokC6BKQ_M");
		float lat = Float.NaN;
		float lng = Float.NaN;
		String location;
		
		if(group.getMembers().size() == 1){
			location = group.getMembers().stream().findFirst().get().getLocation().getPostalCode();
		} else{
			location = group.getTeam().getLocation().getPostalCode();
		}
		
		GeocodingResult[] results = GeocodingApi.geocode(context, location).await();
		lat = (float) results[0].geometry.location.lat;
		lng = (float) results[0].geometry.location.lng;

		OpenWeatherMap owm = new OpenWeatherMap("d0f921e12344207ea2681ceee2389c41");
		if (lat != Float.NaN && lng != Float.NaN) {
			DailyForecast df;

			df = owm.dailyForecastByCoordinates(lat, lng, Byte.parseByte("7"));

			if (df.isValid()) {
				for (int i = 0; i < df.getForecastCount(); i++) {
					DailyForecast.Forecast forecast = df.getForecastInstance(i);
					if (forecast.hasDateTime()) {
						multis.put(i+1, 
						weatherMulti(
						forecast.getTemperatureInstance().getDayTemperature(),
						forecast.getRain(),
						forecast.getSnow(),
						forecast.getHumidity(),
						forecast.getWindSpeed()));
					}
				}
			}
		}
		
		return ImmutableMap.copyOf(multis);
	}
	
	public static Forecast getWeather(String location) throws Exception {
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyCLY-qNiBx8jDzJAzLU1S8tewokC6BKQ_M");
		float lat = Float.NaN;
		float lng = Float.NaN;
		
		GeocodingResult[] results = GeocodingApi.geocode(context, location).await();
		lat = (float) results[0].geometry.location.lat;
		lng = (float) results[0].geometry.location.lng;

		OpenWeatherMap owm = new OpenWeatherMap("d0f921e12344207ea2681ceee2389c41");
		if (lat != Float.NaN && lng != Float.NaN) {
			DailyForecast df;

			df = owm.dailyForecastByCoordinates(lat, lng, Byte.parseByte("7"));

			if (df.isValid()) {
				for (int i = 0; i < df.getForecastCount(); i++) {
					DailyForecast.Forecast forecast = df.getForecastInstance(i);
					if (forecast.hasDateTime()) {
						return forecast;
					}
				}
			}
		}
		
		return null;
	}
	
	private static double weatherMulti(float temp, float rain, float snow, float humid, float wind){
		double multi = 1.0;
		if(temp != Float.NaN){
			multi *= Math.abs(temp - 50)/30;
		}
		
		if(rain != Float.NaN){
			multi += rain/100;
		}
		if(snow != Float.NaN){
			multi += snow/100;
		}
		if(humid != Float.NaN){
			
		}
		if(wind != Float.NaN){
			
		}
		
		return multi;
	}
}