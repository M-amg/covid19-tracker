package com.mamg.covid19tracker;

import com.mamg.covid19tracker.entities.CovidCases;
import com.mamg.covid19tracker.entities.Vaccination;
import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.Gender;
import com.mamg.covid19tracker.enums.TestResult;
import com.mamg.covid19tracker.enums.VaccineType;
import com.mamg.covid19tracker.repositories.CovidCasesRepository;
import com.mamg.covid19tracker.repositories.VaccinationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootApplication
public class Covid19trackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Covid19trackerApplication.class, args);
    }
    @Bean
    CommandLineRunner start(CovidCasesRepository casesService, VaccinationRepository vaccinationRepository) {
        return args -> {
            casesService.save(new CovidCases(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), TestResult.POSITIVE, Gender.MALE, "Amghar lahcen", new SimpleDateFormat("yyyy-MM-dd").parse("1992-11-27"), "Casablanca", Countries.MOROCCO));
            casesService.save(new CovidCases(new Date(), TestResult.NEGATIVE, Gender.FEMALE, "Toubali Hayate", new SimpleDateFormat("yyyy-MM-dd").parse("1994-09-26"), "Casablanca", Countries.MOROCCO));
            casesService.save(new CovidCases(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), TestResult.POSITIVE, Gender.MALE, "Anoire Morad", new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-14"), "Oran", Countries.ALGERIA));
            casesService.save(new CovidCases(new Date(), TestResult.POSITIVE, Gender.MALE, "Bouslham mohamed", new SimpleDateFormat("yyyy-MM-dd").parse("2002-08-10"), "Algeria", Countries.ALGERIA));
            casesService.save(new CovidCases(new Date(), TestResult.NEGATIVE, Gender.FEMALE, "Touri Hajar", new SimpleDateFormat("yyyy-MM-dd").parse("2007-09-24"), "Paris", Countries.FRANCE));
            casesService.save(new CovidCases(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), TestResult.POSITIVE, Gender.FEMALE, "Mouftir Hassna", new SimpleDateFormat("yyyy-MM-dd").parse("1980-06-18"), "new delhi", Countries.INDIA));
            vaccinationRepository.save(new Vaccination(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),null, VaccineType.Sinopharm, Gender.MALE, "name", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "address", Countries.ALGERIA));

        };
    }


}
