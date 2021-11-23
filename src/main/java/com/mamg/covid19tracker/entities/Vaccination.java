package com.mamg.covid19tracker.entities;

import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.Gender;
import com.mamg.covid19tracker.enums.VaccineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaccination extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "Please provide a date of first vaccine")
    private Date vaccineDate;
    @Temporal(TemporalType.DATE)
    private Date vaccine2Date;
    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Please provide a Vaccine")
    private VaccineType typeName;

    public Vaccination(Date vaccineDate, Date vaccine2Date, VaccineType typeName, Gender gender, String name, Date dob, String address, Countries country) {
        super(gender, name, dob, address,country);
        this.typeName=typeName;
        this.vaccineDate=vaccineDate;
        this.vaccine2Date=vaccine2Date;
    }
}
