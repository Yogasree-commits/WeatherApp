package com.example.weatherforecast.Service;

import com.example.weatherforecast.Model.CityPreference;

import java.util.List;

public interface CityPreferenceService {
    public List<CityPreference> getAllPreferences();
    public CityPreference addPreference(String cityName);
    public void deletePreference(Long id);
}
