package com.mamg.covid19tracker.service;

import com.mamg.covid19tracker.entities.CovidCases;
import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.Gender;
import com.mamg.covid19tracker.enums.PreDateType;
import com.mamg.covid19tracker.enums.TestResult;
import com.mamg.covid19tracker.repositories.CovidCasesRepository;
import com.mamg.covid19tracker.utils.PreDateFormatValidator;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CovidCasesServiceTest {
    @Autowired
    private CovidCasesRepository covidCasesRepository;
    private CovidCasesService covidCasesService;
    private PreDateFormatValidator preDateFormatValidator;

    @BeforeEach
    void setUp() {
        this.covidCasesService = new CovidCasesServiceImp(covidCasesRepository);
        this.preDateFormatValidator=new PreDateFormatValidator();
    }

    @Test
    @Order(1)
    @Rollback(value = false)
    void addCase() {
        CovidCases covidCases = new CovidCases(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), TestResult.NEGATIVE, Gender.MALE, "name", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "address", Countries.ALGERIA);
        covidCasesService.addCase(covidCases);
        Assertions.assertThat(covidCases.getId()).isGreaterThan(0);
    }

    @Test
    @Order(1)
    @Rollback(value = false)
    void updateCase() throws Exception {
        CovidCases covidCases = new CovidCases(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), TestResult.POSITIVE, Gender.MALE, "Amghar", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "address", Countries.ALGERIA);
        covidCasesService.updateCase(1l,covidCases);
        Assertions.assertThat(covidCases.getName()).isEqualTo("Amghar");
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    void updateCaseWhineCaseNotExisting() throws Exception {
        CovidCases covidCases = new CovidCases(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), TestResult.NEGATIVE, Gender.MALE, "Amghar", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "address", Countries.ALGERIA);
        Assertions.assertThatThrownBy(()->covidCasesService.updateCase(0l,covidCases))
        .isInstanceOf(Exception.class).hasMessage("Not Existing case");

    }

    @SneakyThrows
    @Test
    @Order(4)
    void getCasesPreDayTest() {
        Countries[] countries={Countries.ALGERIA};
        ResponseEntity<Map<String, Object>> responseEntity=covidCasesService.getCases("2020-01-01", PreDateType.DAY,countries,TestResult.POSITIVE,0,3);
        Assertions.assertThat((Long) responseEntity.getBody().get("totalItems")).isGreaterThan(0);
    }

    @SneakyThrows
    @Test
    @Order(7)
    void getCasesPreDayNotValidTest() {
        Countries[] countries={Countries.ALGERIA};

        assertThrows(IllegalAccessException.class, () -> covidCasesService.getCases("not2020-01-01", PreDateType.DAY,countries,TestResult.POSITIVE,0,3));

    }
    @SneakyThrows
    @Test
    @Order(5)
    void getCasesPreMonthTest() {
        Countries[] countries={Countries.ALGERIA};
        ResponseEntity<Map<String, Object>> responseEntity=covidCasesService.getCases("2020-01", PreDateType.MONTH,countries,TestResult.POSITIVE,0,3);
        Assertions.assertThat((Long) responseEntity.getBody().get("totalItems")).isGreaterThan(0);
    }
    @SneakyThrows
    @Test
    @Order(6)
    void getCasesPreYearsTest() {
        Countries[] countries={Countries.ALGERIA};
        ResponseEntity<Map<String, Object>> responseEntity=covidCasesService.getCases("2020", PreDateType.YEAR,countries,TestResult.POSITIVE,0,3);
        Assertions.assertThat((Long) responseEntity.getBody().get("totalItems")).isGreaterThan(0);
    }
}