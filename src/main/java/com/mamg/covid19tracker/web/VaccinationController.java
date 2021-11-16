package com.mamg.covid19tracker.web;


import com.mamg.covid19tracker.entities.Vaccination;
import com.mamg.covid19tracker.repositories.VaccinationRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class VaccinationController {
    private VaccinationRepository vaccinationRepository;

    public VaccinationController(VaccinationRepository vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
    }

    @PostMapping(path = "/vaccinations")
    public Vaccination save(@RequestBody Vaccination vaccination) throws Exception {
        return vaccinationRepository.save(vaccination);
    }

    @PutMapping(path = "/vaccinations/{id}")
    public Vaccination update(@RequestParam("id") Long id, @RequestBody Vaccination vaccination) throws Exception {
        Vaccination v= vaccinationRepository.findById(id).get();
        if(v==null){
            throw new Exception("Not Existing case");
        }
        vaccination.setId(id);
        return vaccinationRepository.save(vaccination);
    }
}
