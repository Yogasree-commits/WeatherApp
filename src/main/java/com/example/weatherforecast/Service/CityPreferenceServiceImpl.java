package com.example.weatherforecast.Service;

import com.example.weatherforecast.Model.CityPreference;
import com.example.weatherforecast.Repository.CityPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityPreferenceServiceImpl implements CityPreferenceService{
    @Autowired
    private CityPreferenceRepository cityPreferenceRepository;


    @Override
    public List<CityPreference> getAllPreferences() {
        return cityPreferenceRepository.findAll();
    }

    @Override
    public CityPreference addPreference(String cityName) {
        CityPreference preference = new CityPreference();
        preference.setCityName(cityName);
        return cityPreferenceRepository.save(preference);
    }

    @Override
    public void deletePreference(Long id) {
        cityPreferenceRepository.deleteById(id);
    }
}
