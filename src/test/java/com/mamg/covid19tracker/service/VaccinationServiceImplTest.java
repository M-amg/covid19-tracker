package com.mamg.covid19tracker.service;

import com.mamg.covid19tracker.entities.Vaccination;

import com.mamg.covid19tracker.enums.*;
import com.mamg.covid19tracker.repositories.VaccinationRepository;
import com.mamg.covid19tracker.utils.PreDateFormatValidator;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VaccinationServiceImplTest {
    @Autowired
    private VaccinationRepository VaccinationRepository;
    private VaccinationService VaccinationService;
    private PreDateFormatValidator preDateFormatValidator;

    @BeforeEach
    void setUp() {
        this.VaccinationService = new VaccinationServiceImpl(VaccinationRepository);
        this.preDateFormatValidator = new PreDateFormatValidator();
    }

    @Test
    @Order(1)
    @Rollback(value = false)
    void addVaccineTest() {
        Vaccination Vaccination = new Vaccination(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null, VaccineType.Sinopharm, Gender.MALE, "name", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "address", Countries.ALGERIA);
        VaccinationService.addVaccine(Vaccination);
        Assertions.assertThat(Vaccination.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    void updateVaccineTest() throws Exception {
        Vaccination Vaccination = new Vaccination(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), VaccineType.Sinopharm, Gender.MALE, "Amghar", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "address", Countries.ALGERIA);
        VaccinationService.updateVaccine(7l, Vaccination);
        Assertions.assertThat(Vaccination.getName()).isEqualTo("Amghar");
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    void updateVaccineWhineCaseNotExisting() throws Exception {
        Vaccination Vaccination = new Vaccination(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null, VaccineType.Sinopharm, Gender.MALE, "Amghar", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "address", Countries.ALGERIA);
        Assertions.assertThatThrownBy(() -> VaccinationService.updateVaccine(0l, Vaccination))
                .isInstanceOf(Exception.class).hasMessage("Not Existing Vaccine");

    }

    @SneakyThrows
    @Test
    @Order(4)
    void getVaccinesPreDayTest() {
        Countries[] countries = {Countries.ALGERIA};
        ResponseEntity<Map<String, Object>> responseEntity = VaccinationService.getVaccineOfDay("2020-01-01", countries, PreDateType.DAY, VaccineType.Sinopharm, false, 0, 3);
        Assertions.assertThat((Long) responseEntity.getBody().get("totalItems")).isGreaterThan(0);
    }

    @SneakyThrows
    @Test
    @Order(7)
    void getVaccinesPreDayNotValidTest() {
        Countries[] countries = {Countries.ALGERIA};

        assertThrows(IllegalAccessException.class, () -> VaccinationService.getVaccineOfDay("test2020-01-01", countries, PreDateType.DAY, VaccineType.Sinopharm, false, 0, 3));

    }

    @SneakyThrows
    @Test
    @Order(5)
    void getVaccinesPreMonthTest() {
        Countries[] countries = {Countries.ALGERIA};
        ResponseEntity<Map<String, Object>> responseEntity = VaccinationService.getVaccineOfDay("2020-01", countries, PreDateType.MONTH, VaccineType.Sinopharm, false, 0, 3);
        Assertions.assertThat((Long) responseEntity.getBody().get("totalItems")).isGreaterThan(0);
    }

    @SneakyThrows
    @Test
    @Order(6)
    void getVaccinesPreYearsTest() {
        Countries[] countries = {Countries.ALGERIA};
        ResponseEntity<Map<String, Object>> responseEntity = VaccinationService.getVaccineOfDay("2020", countries, PreDateType.YEAR, VaccineType.Sinopharm, false, 0, 3);
        Assertions.assertThat((Long) responseEntity.getBody().get("totalItems")).isGreaterThan(0);
    }

    @SneakyThrows
    @Test
    @Order(8)
    void getBySecondVaccinesPreDayTest() {
        Countries[] countries = {Countries.ALGERIA};
        ResponseEntity<Map<String, Object>> responseEntity = VaccinationService.getVaccineOfDay("2020-01-01", countries, PreDateType.DAY, VaccineType.Sinopharm, true, 0, 3);
        Assertions.assertThat((Long) responseEntity.getBody().get("totalItems")).isGreaterThan(0);
    }
}