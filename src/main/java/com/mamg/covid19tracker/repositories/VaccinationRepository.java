package com.mamg.covid19tracker.repositories;

import com.mamg.covid19tracker.entities.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationRepository extends JpaRepository<Vaccination,Long> {
}
