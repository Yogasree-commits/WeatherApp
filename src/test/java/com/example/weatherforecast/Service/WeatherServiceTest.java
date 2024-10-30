package com.example.weatherforecast.Service;


import com.example.weatherforecast.DTO.WeatherDTO;
import com.example.weatherforecast.Model.WeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class WeatherServiceTest {
    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.base.url}")
    private String baseUrl;

    @BeforeEach
    void setUp() {

        weatherService = new WeatherServiceImpl();
        weatherService.setApiKey(apiKey);
        weatherService.setBaseUrl(baseUrl);
    }

    @Test
    void getWeatherByCity_metric_success() {
        String city = "London";
        String unit = "metric";
        String url = String.format("%s/weather?q=%s&appid=%s&units=%s", weatherService.getBaseUrl(), city, weatherService.getApiKey(), "metric");

        WeatherResponse mockResponse = new WeatherResponse();
        mockResponse.setName(city);
        WeatherResponse.Main main = new WeatherResponse.Main();
        main.setTemp(15.0);
        main.setPressure(1012);
        main.setHumidity(80);
        mockResponse.setMain(main);
        WeatherResponse.Weather weather = new WeatherResponse.Weather();
        weather.setDescription("light rain");
        mockResponse.setWeather(java.util.List.of(weather));

        when(restTemplate.getForObject(url, WeatherResponse.class)).thenReturn(mockResponse);

        WeatherDTO response = weatherService.getWeatherByCity(city, unit);
        assertNotNull(response);
        assertEquals(city, response.getCityName());
        assertEquals(15.0, response.getTemperatureCelsius());
        assertEquals(59.0, response.getTemperatureFahrenheit()); // (15 * 9/5) + 32 = 59
        assertEquals(1012, response.getPressure());
        assertEquals(80, response.getHumidity());
        assertEquals("light rain", response.getWeatherDescription());
    }

    @Test
    void getWeatherByCity_imperial_success() {
        String city = "New York";
        String unit = "imperial";
        String url = String.format("%s/weather?q=%s&appid=%s&units=%s", weatherService.getBaseUrl(), city, weatherService.getApiKey(), "imperial");

        WeatherResponse mockResponse = new WeatherResponse();
        mockResponse.setName(city);
        WeatherResponse.Main main = new WeatherResponse.Main();
        main.setTemp(59.0); // Fahrenheit
        main.setPressure(1015);
        main.setHumidity(70);
        mockResponse.setMain(main);
        WeatherResponse.Weather weather = new WeatherResponse.Weather();
        weather.setDescription("clear sky");
        mockResponse.setWeather(java.util.List.of(weather));

        when(restTemplate.getForObject(url, WeatherResponse.class)).thenReturn(mockResponse);

        WeatherDTO response = weatherService.getWeatherByCity(city, unit);
        assertNotNull(response);
        assertEquals(city, response.getCityName());
        assertEquals(15.0, response.getTemperatureCelsius()); // (59 - 32) * 5/9 = 15
        assertEquals(59.0, response.getTemperatureFahrenheit());
        assertEquals(1015, response.getPressure());
        assertEquals(70, response.getHumidity());
        assertEquals("clear sky", response.getWeatherDescription());
    }

    @Test
    void getWeatherByCity_notFound() {
        String city = "InvalidCity";
        String unit = "metric";
        String url = String.format("%s/weather?q=%s&appid=%s&units=%s", weatherService.getBaseUrl(), city, weatherService.getApiKey(), "metric");

        when(restTemplate.getForObject(url, WeatherResponse.class)).thenReturn(null);

        WeatherDTO response = weatherService.getWeatherByCity(city, unit);
        assertNull(response);
    }
}
