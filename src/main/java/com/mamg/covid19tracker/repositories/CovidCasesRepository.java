package com.mamg.covid19tracker.repositories;


import com.mamg.covid19tracker.entities.CovidCases;
import com.mamg.covid19tracker.enums.Countries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface CovidCasesRepository extends JpaRepository<CovidCases,Long> {

    @Query("SELECT COUNT(C),C.country FROM CovidCases C WHERE C.testResult='POSITIVE' AND  C.testDate = :date AND C.country in (:countries) GROUP BY (C.country)")
    public Page<CovidCases> getCovidCasesByDate(@Param("date") Date date, @Param("countries") Countries[] countries, Pageable pageable);
    @Query("SELECT COUNT(C),C.country FROM CovidCases C WHERE C.testResult='POSITIVE' AND SUBSTRING(C.testDate,5, 7)=SUBSTRING(:date,5, 7) AND SUBSTRING(C.testDate,0, 4)=SUBSTRING(:date,0, 4)  AND C.country in (:countries) GROUP BY (C.country)")
    public Page<CovidCases> getCovidCasesByMonth(@Param("date") Date date, @Param("countries") Countries[] countries, Pageable pageable);
    @Query("SELECT COUNT(C),C.country FROM CovidCases C WHERE C.testResult='POSITIVE' AND SUBSTRING(C.testDate,0, 4)=SUBSTRING(:date,0, 4)  AND C.country in (:countries) GROUP BY (C.country)")
    public Page<CovidCases> getCovidCasesByYear(@Param("date") Date date, @Param("countries") Countries[] countries, Pageable pageable);

}
