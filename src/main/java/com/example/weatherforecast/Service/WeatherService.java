package com.example.weatherforecast.Service;
import com.example.weatherforecast.DTO.WeatherDTO;
import com.example.weatherforecast.Model.WeatherResponse;

public interface WeatherService {
    public WeatherDTO getWeatherByCity(String city, String unit);
    public String determineUnits(String unit);
    public WeatherDTO mapToDTO(WeatherResponse response, String units);
    public double convertCelsiusToFahrenheit(double tempCelsius);
    public double convertFahrenheitToCelsius(double tempFahrenheit);
}
