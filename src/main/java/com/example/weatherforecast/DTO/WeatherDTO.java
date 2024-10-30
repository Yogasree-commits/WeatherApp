package com.example.weatherforecast.DTO;

import lombok.Data;

@Data
public class WeatherDTO {
    private String cityName;
    private double temperatureCelsius;
    private double temperatureFahrenheit;
    private int pressure;
    private int humidity;
    private String weatherDescription;
}
