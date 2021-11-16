package com.mamg.covid19tracker.web;

import com.mamg.covid19tracker.entities.CovidCases;
import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.PreDateType;
import com.mamg.covid19tracker.enums.TestResult;
import com.mamg.covid19tracker.repositories.CovidCasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CovidCasesController {
    @Autowired
    private CovidCasesRepository covidCasesRepository;

    @PostMapping(path = "/covid-cases")
    public CovidCases save(@RequestBody CovidCases cases) throws Exception {
        return covidCasesRepository.save(cases);
    }

    @PutMapping(path = "/covid-cases/{id}")
    public CovidCases update(@RequestParam("id") Long id, @RequestBody CovidCases cases) throws Exception {
        CovidCases c = covidCasesRepository.findById(id).get();
        if (c == null) {
            throw new Exception("Not Existing case");
        }
        cases.setId(id);
        return covidCasesRepository.save(cases);
    }

    @GetMapping(path = "/covid-cases")
    public ResponseEntity<Map<String, Object>> getCase(@Param("date") Date date,
                                                       @Param("type") PreDateType type,
                                                       @Param("date") Countries[] countries,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "3") int size) throws Exception {
        Pageable paging = PageRequest.of(page, size);

        Page<CovidCases> p;
        switch (type) {
            case DAY:
                p=covidCasesRepository.getCovidCasesByDate(date, countries, paging);
                break;
            case MONTH:
                p=covidCasesRepository.getCovidCasesByMonth(date, countries, paging);
                break;
            default:
                p=covidCasesRepository.getCovidCasesByYear(date, countries, paging);

        }
        Map<String, Object> response = new HashMap<>();
        List <CovidCases> results=p.getContent();
        response.put("cases", results);
        response.put("currentPage", p.getNumber());
        response.put("totalItems", p.getTotalElements());
        response.put("totalPages", p.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
