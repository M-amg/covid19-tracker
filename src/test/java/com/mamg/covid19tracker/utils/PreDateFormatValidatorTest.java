package com.mamg.covid19tracker.utils;

import com.mamg.covid19tracker.enums.PreDateType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PreDateFormatValidatorTest {

    private PreDateFormatValidator underTest;

    @BeforeEach
    void setUp() {
        underTest = new PreDateFormatValidator();
    }

    @Test
    void itShouldValidatePreDayDateFormat(){
        //Given
        String date="2021-11-18";
        PreDateType dateType=PreDateType.DAY;
        // When
        boolean isValid=underTest.test(date,dateType);
        // Then
        Assertions.assertThat(isValid).isTrue();
    }
    @Test
    void itShouldValidatePreMonthDateFormat(){
        //Given
        String date="2021-11";
        PreDateType dateType=PreDateType.MONTH;
        // When
        boolean isValid=underTest.test(date,dateType);
        // Then
        Assertions.assertThat(isValid).isTrue();
    }
    @Test
    void itShouldValidatePreYearDateFormat(){
        //Given
        String date="2021";
        PreDateType dateType=PreDateType.YEAR;
        // When
        boolean isValid=underTest.test(date,dateType);
        // Then
        Assertions.assertThat(isValid).isTrue();
    }
    @Test
    void itNotValidatePreDayDateFormatSupDate(){
        //Given
        String date="2023-10-18";
        PreDateType dateType=PreDateType.DAY;
        // When
        boolean isValid=underTest.test(date,dateType);
        // Then
        Assertions.assertThat(isValid).isFalse();
    }@Test
    void itNotValidatePreDayDateFormat(){
        //Given
        String date="2021-50-18";
        PreDateType dateType=PreDateType.DAY;
        // When
        boolean isValid=underTest.test(date,dateType);
        // Then
        Assertions.assertThat(isValid).isFalse();
    }
    @Test
    void itNotValidatePreMonthDateFormat(){
        //Given
        String date="11*2021-11";
        PreDateType dateType=PreDateType.MONTH;
        // When
        boolean isValid=underTest.test(date,dateType);
        // Then
        Assertions.assertThat(isValid).isFalse();
    }
    @Test
    void itNotValidatePreYearDateFormat(){
        //Given
        String date="2021test";
        PreDateType dateType=PreDateType.YEAR;
        // When
        boolean isValid=underTest.test(date,dateType);
        // Then
        Assertions.assertThat(isValid).isFalse();
    }
}