package com.mamg.covid19tracker.web;


import com.mamg.covid19tracker.entities.CovidCases;
import com.mamg.covid19tracker.entities.Vaccination;
import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.PreDateType;
import com.mamg.covid19tracker.enums.TestResult;
import com.mamg.covid19tracker.enums.VaccineType;
import com.mamg.covid19tracker.repositories.VaccinationRepository;
import com.mamg.covid19tracker.service.VaccinationService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VaccinationController {
    private VaccinationService vaccinationService;

    public VaccinationController(VaccinationService vaccinationService) {
        this.vaccinationService = vaccinationService;
    }

    @PostMapping(path = "/vaccinations")
    public Vaccination save(@RequestBody Vaccination vaccination) throws Exception {
        return vaccinationService.addVaccine(vaccination);
    }

    @PutMapping(path = "/vaccinations/{id}")
    public Vaccination update(@RequestParam("id") Long id, @RequestBody Vaccination vaccination) throws Exception {

        return vaccinationService.updateVaccine(id,vaccination);
    }


    @GetMapping(path = "/vaccinations")
    public ResponseEntity<Map<String, Object>> getVaccines(@Param("date") @NotBlank(message = "Please provide a date") String date,
                                                       @Param("type") @NotNull(message = "Please provide a PreDateType") PreDateType type,
                                                       @Param("date") @NotNull(message = "Please provide a Countries")  Countries[] countries,
                                                       @Param("name")  @NotNull(message = "Please provide a TestResult") VaccineType name,
                                                       @RequestParam(defaultValue = "false")  boolean isSecondVaccine,
                                                       @RequestParam(defaultValue = "0") @Min(value = 0,message ="Page index must be 0 or higher" ) int page,
                                                       @RequestParam(defaultValue = "3") @Min(value = 1,message = "Page size must be 1 or higher") int size) throws Exception {


        return vaccinationService.getVaccineOfDay(date,countries,type,name,isSecondVaccine,page,size);
    }
}
