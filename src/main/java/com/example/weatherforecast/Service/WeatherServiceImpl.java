package com.example.weatherforecast.Service;

import com.example.weatherforecast.DTO.WeatherDTO;
import com.example.weatherforecast.Model.WeatherResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Data
@Service
public class WeatherServiceImpl implements WeatherService{


    @Value("${openweathermap.api.key}")
    private String apiKey;


    @Value("${openweathermap.base.url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Cacheable(value = "weather", key = "#city + '_' + #unit", unless = "#result == null")
    public WeatherDTO getWeatherByCity(String city, String unit) {
        String units = determineUnits(unit);
        String url = String.format("%s/weather?q=%s&appid=%s&units=%s", baseUrl, city, apiKey, units);
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
        if (response == null) {
            return null;
        }
        return mapToDTO(response, units);
    }

    @Override
    public String determineUnits(String unit) {
        if ("imperial".equalsIgnoreCase(unit)) {
            return "imperial";
        }
        return "metric";
    }

    @Override
    public WeatherDTO mapToDTO(WeatherResponse response, String units) {
        WeatherDTO dto = new WeatherDTO();
        dto.setCityName(response.getName());
        double temp = response.getMain().getTemp();
        if ("imperial".equalsIgnoreCase(units)) {
            dto.setTemperatureFahrenheit(temp);
            dto.setTemperatureCelsius(convertFahrenheitToCelsius(temp));
        } else {
            dto.setTemperatureCelsius(temp);
            dto.setTemperatureFahrenheit(convertCelsiusToFahrenheit(temp));
        }
        dto.setPressure(response.getMain().getPressure());
        dto.setHumidity(response.getMain().getHumidity());
        if (response.getWeather() != null && !response.getWeather().isEmpty()) {
            dto.setWeatherDescription(response.getWeather().get(0).getDescription());
        }
        return dto;
    }

    @Override
    public double convertCelsiusToFahrenheit(double tempCelsius) {
        return (tempCelsius * 9/5) + 32;
    }

    @Override
    public double convertFahrenheitToCelsius(double tempFahrenheit) {
        return (tempFahrenheit - 32) * 5/9;
    }
}
