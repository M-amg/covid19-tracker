package com.mamg.covid19tracker.service;

import com.mamg.covid19tracker.entities.Vaccination;
import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.PreDateType;
import com.mamg.covid19tracker.enums.VaccineType;
import com.mamg.covid19tracker.repositories.VaccinationRepository;
import com.mamg.covid19tracker.utils.PreDateFormatValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VaccinationServiceImpl implements VaccinationService {


    private final VaccinationRepository vaccinationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final PreDateFormatValidator preDateFormatValidator;

    public VaccinationServiceImpl(VaccinationRepository vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
        this.preDateFormatValidator = new PreDateFormatValidator();
    }


    @Override
    public Vaccination addVaccine(Vaccination vaccine) {
        return vaccinationRepository.save(vaccine);
    }

    @Override
    public Vaccination updateVaccine(Long id, Vaccination vaccine) throws Exception {
        if (!vaccinationRepository.existsById(id)) {
            throw new Exception("Not Existing Vaccine");
        }
        vaccine.setId(id);
        return vaccinationRepository.save(vaccine);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getVaccineOfDay(String date, Countries[] countries, PreDateType preType, VaccineType type, Boolean secondVaccine, int page, int size) throws Exception {


        if (!preDateFormatValidator.test(date, preType)) {
            throw new IllegalAccessException("Date format is not valid");
        }

        Pageable paging = PageRequest.of(page, size);

        Page<Vaccination> p;
        System.out.println(preType);
        System.out.println(secondVaccine);
        switch (preType) {
            case DAY:
                p = !secondVaccine? vaccinationRepository.getVaccinationByDate(date, countries, type, paging)
                :vaccinationRepository.getSecondVaccinationByDate(date, countries, type, paging);
                break;
            case MONTH:
                p = !secondVaccine? vaccinationRepository.getVaccinationByMonth(date, countries, type, paging)
                : vaccinationRepository.getSecondVaccinationByMonth(date, countries, type, paging);
                break;
            default:
                p = !secondVaccine? vaccinationRepository.getVaccinationByYear(date, countries, type, paging)
                : vaccinationRepository.getSecondVaccinationByYear(date, countries, type, paging);
        }


        Map<String, Object> response = new HashMap<>();
        List<Vaccination> results = p.getContent();
        response.put("vaccines", results);
        response.put("currentPage", p.getNumber());
        response.put("totalItems", p.getTotalElements());
        response.put("totalPages", p.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
