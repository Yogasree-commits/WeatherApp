package com.example.weatherforecast.Repository;

import com.example.weatherforecast.Model.CityPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityPreferenceRepository extends JpaRepository<CityPreference, Long> {
}
