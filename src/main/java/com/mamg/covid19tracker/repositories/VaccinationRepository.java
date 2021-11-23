package com.mamg.covid19tracker.repositories;

import com.mamg.covid19tracker.entities.Vaccination;
import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VaccinationRepository extends JpaRepository<Vaccination,Long> {

    boolean existsById(Long id);

    @Query("SELECT COUNT(C),C.country FROM Vaccination C WHERE C.typeName=:typeName AND  SUBSTRING(C.vaccineDate,0,10) = SUBSTRING(:date,0,10)  AND C.country in (:countries) GROUP BY (C.country)")
    public Page<Vaccination> getVaccinationByDate(@Param("date") String date, @Param("countries") Countries[] countries, @Param("typeName") VaccineType typeName, Pageable pageable);

    @Query("SELECT COUNT(C),C.country FROM Vaccination C WHERE C.typeName=:typeName AND SUBSTRING(C.vaccineDate,0, 7)=SUBSTRING(:date,0, 7) AND  C.country in (:countries) GROUP BY (C.country)")
    public Page<Vaccination> getVaccinationByMonth(@Param("date") String date, @Param("countries") Countries[] countries, @Param("typeName") VaccineType typeName, Pageable pageable);

    @Query("SELECT COUNT(C),C.country FROM Vaccination C WHERE C.typeName=:typeName AND SUBSTRING(C.vaccineDate,0, 4)=SUBSTRING(:date,0, 4)  AND C.country in (:countries) GROUP BY (C.country)")
    public Page<Vaccination> getVaccinationByYear(@Param("date") String date, @Param("countries") Countries[] countries, @Param("typeName") VaccineType typeName, Pageable pageable);

    @Query("SELECT COUNT(C),C.country FROM Vaccination C WHERE C.typeName=:typeName AND  SUBSTRING(C.vaccine2Date,0,10) = SUBSTRING(:date,0,10)  AND C.country in (:countries) GROUP BY (C.country)")
    public Page<Vaccination> getSecondVaccinationByDate(@Param("date") String date, @Param("countries") Countries[] countries, @Param("typeName") VaccineType typeName, Pageable pageable);

    @Query("SELECT COUNT(C),C.country FROM Vaccination C WHERE C.typeName=:typeName AND SUBSTRING(C.vaccine2Date,0, 7)=SUBSTRING(:date,0, 7) AND  C.country in (:countries) GROUP BY (C.country)")
    public Page<Vaccination> getSecondVaccinationByMonth(@Param("date") String date, @Param("countries") Countries[] countries, @Param("typeName") VaccineType typeName, Pageable pageable);

    @Query("SELECT COUNT(C),C.country FROM Vaccination C WHERE C.typeName=:typeName AND SUBSTRING(C.vaccine2Date,0, 4)=SUBSTRING(:date,0, 4)  AND C.country in (:countries) GROUP BY (C.country)")
    public Page<Vaccination> getSecondVaccinationByYear(@Param("date") String date, @Param("countries") Countries[] countries, @Param("typeName") VaccineType typeName, Pageable pageable);


}
