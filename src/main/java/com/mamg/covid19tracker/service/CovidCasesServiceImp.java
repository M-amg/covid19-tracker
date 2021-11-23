package com.mamg.covid19tracker.service;

import com.mamg.covid19tracker.entities.CovidCases;
import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.PreDateType;
import com.mamg.covid19tracker.enums.TestResult;
import com.mamg.covid19tracker.repositories.CovidCasesRepository;
import com.mamg.covid19tracker.utils.PreDateFormatValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CovidCasesServiceImp implements CovidCasesService {
    private final CovidCasesRepository covidCasesRepository;
    private final PreDateFormatValidator preDateFormatValidator;

    public CovidCasesServiceImp(CovidCasesRepository covidCasesRepository) {
        this.covidCasesRepository = covidCasesRepository;
        this.preDateFormatValidator = new PreDateFormatValidator();
    }

    @Override
    public CovidCases addCase(CovidCases covidCases){
        return covidCasesRepository.save(covidCases);
    }

    @Override
    public CovidCases updateCase(Long id,CovidCases covidCases) throws Exception {
        if (!covidCasesRepository.existsById(id)) {
            throw new Exception("Not Existing case");
        }
        covidCases.setId(id);
        return covidCasesRepository.save(covidCases);
    }
    @Override
    public ResponseEntity<Map<String, Object>> getCases(String date, PreDateType type, Countries[] countries, TestResult testResult, int page, int size) throws IllegalAccessException {


        if(!preDateFormatValidator.test(date,type)){
            throw new IllegalAccessException("Date format is not valid");
        }

        Pageable paging = PageRequest.of(page, size);

        Page<CovidCases> p;
        switch (type) {
            case DAY:
                p=covidCasesRepository.getCovidCasesByDate(date, countries,testResult, paging);
                break;
            case MONTH:
                p=covidCasesRepository.getCovidCasesByMonth(date, countries,testResult, paging);
                break;
            default:
                p=covidCasesRepository.getCovidCasesByYear(date, countries,testResult, paging);
        }

        Map<String, Object> response = new HashMap<>();
        List<CovidCases> results=p.getContent();
        response.put("cases", results);
        response.put("currentPage", p.getNumber());
        response.put("totalItems", p.getTotalElements());
        response.put("totalPages", p.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
