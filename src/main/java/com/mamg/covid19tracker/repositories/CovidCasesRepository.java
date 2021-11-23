package com.mamg.covid19tracker.repositories;


import com.mamg.covid19tracker.entities.CovidCases;
import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.TestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CovidCasesRepository extends JpaRepository<CovidCases, Long> {
    boolean existsById(Long id);

    @Query("SELECT COUNT(C),C.country FROM CovidCases C WHERE C.testResult=:testResult AND  SUBSTRING(C.testDate,0,10) = SUBSTRING(:date,0,10)  AND C.country in (:countries) GROUP BY (C.country)")
    public Page<CovidCases> getCovidCasesByDate(@Param("date") String date, @Param("countries") Countries[] countries, @Param("testResult") TestResult testResult, Pageable pageable);

    @Query("SELECT COUNT(C),C.country FROM CovidCases C WHERE C.testResult=:testResult AND SUBSTRING(C.testDate,0, 7)=SUBSTRING(:date,0, 7) AND  C.country in (:countries) GROUP BY (C.country)")
    public Page<CovidCases> getCovidCasesByMonth(@Param("date") String date, @Param("countries") Countries[] countries, @Param("testResult") TestResult testResult,Pageable pageable);

    @Query("SELECT COUNT(C),C.country FROM CovidCases C WHERE C.testResult=:testResult AND SUBSTRING(C.testDate,0, 4)=SUBSTRING(:date,0, 4)  AND C.country in (:countries) GROUP BY (C.country)")
    public Page<CovidCases> getCovidCasesByYear(@Param("date") String date, @Param("countries") Countries[] countries, @Param("testResult") TestResult testResult,Pageable pageable);

}
