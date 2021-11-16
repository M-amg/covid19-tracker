package com.mamg.covid19tracker.repositories;


import com.mamg.covid19tracker.entities.CovidCases;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CovidCasesRepository extends JpaRepository<CovidCases,Long> {

    @Query("SELECT COUNT(C),C.country FROM CovidCases C WHERE C.testResult='POSITIVE' AND  C.testDate = :date AND TYPE(C.country) in (:countries) GROUP BY (C.country)")
    public Page<CovidCases> getCovidCasesByDate(@Param("date") String date, @Param("countries") String countries);
    @Query("SELECT COUNT(C),C.country FROM CovidCases C WHERE C.testResult='POSITIVE' AND SUBSTRING(C.testDate,5, 7)=:month AND SUBSTRING(C.testDate,0, 4)=:years  AND C.country in (:country) GROUP BY (C.country)")
    public Page<CovidCases> getCovidCasesMyMonth(@Param("month") String month, @Param("year") String year, @Param("countries") String countries);
    @Query("SELECT COUNT(C),C.country FROM CovidCases C WHERE C.testResult='POSITIVE' AND SUBSTRING(C.testDate,0, 4)=:year  AND C.country in (:country) GROUP BY (C.country)")
    public Page<CovidCases> getCovidCasesByYear(@Param("year") String year, @Param("countries") String countries);

}
