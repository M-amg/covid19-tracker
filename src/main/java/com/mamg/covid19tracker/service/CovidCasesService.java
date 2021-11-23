package com.mamg.covid19tracker.service;

import com.mamg.covid19tracker.entities.CovidCases;
import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.PreDateType;
import com.mamg.covid19tracker.enums.TestResult;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CovidCasesService {
    public CovidCases addCase(CovidCases covidCases);
    public CovidCases updateCase(Long id,CovidCases covidCases) throws Exception;
    ResponseEntity<Map<String, Object>> getCases(String date, PreDateType type, Countries[] countries, TestResult testResult, int page, int size) throws IllegalAccessException;

}
