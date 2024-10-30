package com.example.weatherforecast.Controller;


import com.example.weatherforecast.DTO.WeatherDTO;
import com.example.weatherforecast.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    public WeatherService weatherService;

    /**
     * Get weather by city with optional unit parameter.
     * @param city The name of the city.
     * @param unit The temperature unit ("metric" for Celsius, "imperial" for Fahrenheit).
     * @return Weather information.
     */
    @GetMapping("/{city}")
    public ResponseEntity<WeatherDTO> getWeather(
            @PathVariable String city,
            @RequestParam(required = false, defaultValue = "metric") String unit) {
        WeatherDTO weather = weatherService.getWeatherByCity(city, unit);
        if (weather != null) {
            return ResponseEntity.ok(weather);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
