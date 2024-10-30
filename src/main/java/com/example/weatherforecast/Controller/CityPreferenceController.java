package com.example.weatherforecast.Controller;


import com.example.weatherforecast.Model.CityPreference;
import com.example.weatherforecast.Service.CityPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferences")
public class CityPreferenceController {
    @Autowired
    private CityPreferenceService preferenceService;
    @GetMapping
    public List<CityPreference> getAllPreferences() {
        return preferenceService.getAllPreferences();
    }

    @PostMapping
    public CityPreference addPreference(@RequestParam String cityName) {
        return preferenceService.addPreference(cityName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreference(@PathVariable Long id) {
        preferenceService.deletePreference(id);
        return ResponseEntity.noContent().build();
    }
}
