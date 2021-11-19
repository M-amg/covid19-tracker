package com.mamg.covid19tracker.service;

import com.mamg.covid19tracker.entities.Vaccination;
import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.PreDateType;
import com.mamg.covid19tracker.enums.VaccineType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface VaccinationService {
    ResponseEntity<Map<String, Object>> getVaccineOfDay(String date, Countries[] countries, PreDateType preType, VaccineType type, Boolean secondVaccine, int page, int pageSize) throws Exception;

    public Vaccination addVaccine(Vaccination vaccine);

    public Vaccination updateVaccine(Long id, Vaccination vaccine) throws Exception;
}