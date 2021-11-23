package com.mamg.covid19tracker.web;

import com.mamg.covid19tracker.entities.CovidCases;
import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.PreDateType;
import com.mamg.covid19tracker.enums.TestResult;
import com.mamg.covid19tracker.service.CovidCasesService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CovidCasesController extends ResponseEntityExceptionHandler {

    private final CovidCasesService covidCasesService;

    public CovidCasesController(CovidCasesService covidCasesService) {
        this.covidCasesService = covidCasesService;
    }

    @PostMapping(path = "/covid-cases")
    @ResponseStatus(HttpStatus.CREATED)
    public CovidCases save(@Valid @RequestBody CovidCases cases) throws Exception {
        return covidCasesService.addCase(cases);
    }

    @PutMapping(path = "/covid-cases/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CovidCases update(@PathVariable("id") @Min(1) Long id, @Valid @RequestBody CovidCases cases) throws Exception {
        System.out.println(id);
        return covidCasesService.updateCase(id,cases);
    }

    @GetMapping(path = "/covid-cases")
    public ResponseEntity<Map<String, Object>> getCase(@RequestParam(value ="date") @NotNull(message = "Please provide a date") String date,
                                                       @RequestParam(value ="type") @NotNull(message = "Please provide a PreDateType") PreDateType type,
                                                       @RequestParam(value ="countries") @NotNull(message = "Please provide a Countries")  Countries[] countries,
                                                       @RequestParam(value ="test_result")  @NotNull(message = "Please provide a TestResult") TestResult testResult,
                                                       @RequestParam(value = "page",defaultValue = "0") @Min(value = 0,message ="Page index must be 0 or higher" ) int page,
                                                       @RequestParam(value = "size",defaultValue = "3") @Min(value = 1,message = "Page size must be 1 or higher") int size) throws Exception {


        return covidCasesService.getCases(date,type,countries,testResult,page,size);
    }


    @Override
    @ExceptionHandler({ ConstraintViolationException.class })
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String,Object> body=new LinkedHashMap<>();
        body.put("timestamp",new Date());
        body.put("status",status.value());

        List<String> errors= ex.getBindingResult().getFieldErrors().stream()
                .map(x -> x.getDefaultMessage()).collect(Collectors.toList());

        body.put("errors",errors);
        return new ResponseEntity<>(body, headers, status);
    }
}
