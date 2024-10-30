package com.example.weatherforecast.Controller;

import com.example.weatherforecast.DTO.WeatherDTO;
import com.example.weatherforecast.Service.WeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
public class WeatherControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherServiceImpl weatherService;

    @Test
    void getWeather_metric_success() throws Exception {
        String city = "London";
        String unit = "metric";

        WeatherDTO mockDTO = new WeatherDTO();
        mockDTO.setCityName(city);
        mockDTO.setTemperatureCelsius(15.0);
        mockDTO.setTemperatureFahrenheit(59.0);
        mockDTO.setPressure(1012);
        mockDTO.setHumidity(80);
        mockDTO.setWeatherDescription("light rain");

        when(weatherService.getWeatherByCity(city, unit)).thenReturn(mockDTO);

        mockMvc.perform(get("/api/weather/{city}", city)
                        .param("unit", unit)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName").value(city))
                .andExpect(jsonPath("$.temperatureCelsius").value(15.0))
                .andExpect(jsonPath("$.temperatureFahrenheit").value(59.0))
                .andExpect(jsonPath("$.pressure").value(1012))
                .andExpect(jsonPath("$.humidity").value(80))
                .andExpect(jsonPath("$.weatherDescription").value("light rain"));
    }

    @Test
    void getWeather_imperial_success() throws Exception {
        String city = "New York";
        String unit = "imperial";

        WeatherDTO mockDTO = new WeatherDTO();
        mockDTO.setCityName(city);
        mockDTO.setTemperatureCelsius(15.0);
        mockDTO.setTemperatureFahrenheit(59.0);
        mockDTO.setPressure(1015);
        mockDTO.setHumidity(70);
        mockDTO.setWeatherDescription("clear sky");

        when(weatherService.getWeatherByCity(city, unit)).thenReturn(mockDTO);

        mockMvc.perform(get("/api/weather/{city}", city)
                        .param("unit", unit)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName").value(city))
                .andExpect(jsonPath("$.temperatureCelsius").value(15.0))
                .andExpect(jsonPath("$.temperatureFahrenheit").value(59.0))
                .andExpect(jsonPath("$.pressure").value(1015))
                .andExpect(jsonPath("$.humidity").value(70))
                .andExpect(jsonPath("$.weatherDescription").value("clear sky"));
    }

    @Test
    void getWeather_notFound() throws Exception {
        String city = "InvalidCity";
        String unit = "metric";

        when(weatherService.getWeatherByCity(city, unit)).thenReturn(null);

        mockMvc.perform(get("/api/weather/{city}", city)
                        .param("unit", unit)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
