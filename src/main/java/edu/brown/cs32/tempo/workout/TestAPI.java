package edu.brown.cs32.tempo.workout;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;

public class TestAPI {

	private static boolean oneDay(Date day1, Date day2) {
		Calendar date1 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		date1.setTime(day1);
		date2.setTime(day2);

		date1.add(Calendar.DATE, 1);
		System.out.println(day1);
		System.out.println(date1.getTime());
		System.out.println(day2);
		System.out.println(date2.getTime());
		return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR)
				&& date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
				&& date1.get(Calendar.DATE) == date2.get(Calendar.DATE);
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		long startTime = System.nanoTime();
		Group group = new Group(null, null, null, new Team(null, "02901", null, null, null));
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyCLY-qNiBx8jDzJAzLU1S8tewokC6BKQ_M");
		GeocodingResult[] results = GeocodingApi.geocode(context, group.getTeam().getLocation().getPostalCode())
				.await();
		GeocodingResult[] results2 = GeocodingApi.geocode(context, "64014").await();
		System.out.println(results[0].geometry.location.lat);
		System.out.println(results[0].geometry.location.lng);
		System.out.println(results[0].formattedAddress);
		System.out.println(results2[0].geometry.location.lat);
		System.out.println(results2[0].geometry.location.lng);
		System.out.println(results2[0].formattedAddress);
		System.out.println((System.nanoTime() - startTime) / Math.pow(10, 9));

		// declaring object of "OpenWeatherMap" class
		OpenWeatherMap owm = new OpenWeatherMap("d0f921e12344207ea2681ceee2389c41");

		// getting current weather data for the "London" city
		// CurrentWeather cwd = owm.currentWeatherByCityName("Springfield");
		// System.out.println(cwd.getCityCode());

		CurrentWeather cwd = owm.currentWeatherByCoordinates((float) results[0].geometry.location.lat,
				(float) results[0].geometry.location.lng);

		// printing city name from the retrieved data
		System.out.println("City: " + cwd.getCityName());

		if (cwd.getMainInstance() != null) {
			// printing the max./min. temperature
			System.out.println("Temperature: " + cwd.getMainInstance().getMaxTemperature() + "/"
					+ cwd.getMainInstance().getMinTemperature() + "\'F");
			System.out
					.println(
							"Weather:" + owm
									.dailyForecastByCoordinates((float) results[0].geometry.location.lat,
											(float) results[0].geometry.location.lng, Byte.parseByte("7"))
									.getMessage());

		}

		Date date1 = new Date();

		date1.setHours(12);
		date1.setMinutes(1);
		date1.setSeconds(1);

		date1.setMonth(1);
		date1.setDate(1);
		date1.setYear(2012);

		Date date2 = new Date();
		date2.setHours(12);
		date2.setMinutes(1);
		date2.setSeconds(1);

		date2.setMonth(1);
		date2.setDate(2);
		date2.setYear(2012);

		Date date3 = new Date();
		date3.setHours(3);
		date3.setMinutes(3);
		date3.setSeconds(2);

		date3.setMonth(12);
		date3.setDate(31);
		date3.setYear(2011);

		System.out.println(oneDay(date1, date2));
		System.out.println(oneDay(date3, date1));

		DailyForecast df = owm.dailyForecastByCityName("London, UK", Byte.parseByte("6"));

		if (!df.isValid()) {
			System.out.println("Reponse is inValid!");
		} else {
			System.out.println("Reponse is Valid!");
			System.out.println();

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

			System.out.println("Total forecast instances: " + df.getForecastCount());
			System.out.println();

			for (int i = 0; i < df.getForecastCount(); i++) {
				DailyForecast.Forecast forecast = df.getForecastInstance(i);

				System.out.println("*** Forecast instance number " + (i + 1) + " ***");

				if (forecast.hasDateTime()) {
					System.out.println(forecast.getDateTime());
					System.out.println(forecast.getTemperatureInstance().getDayTemperature());
					System.out.println(forecast.getRain());
					System.out.println(forecast.getSnow());
					System.out.println(forecast.getHumidity());
					System.out.println(forecast.getWindSpeed());
				}

				System.out.println();
			}
		}

		DateTime test = new DateTime();
		System.out.println(test.toString());
		test = test.withDate(2012, 12, 31).plusDays(1);
		System.out.println(test.toString());
	}

}
