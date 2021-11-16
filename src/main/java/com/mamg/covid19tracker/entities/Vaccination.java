package com.mamg.covid19tracker.entities;

import com.covid19.tracker.enums.VaccineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaccination extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date vaccineDate;
    @Temporal(TemporalType.DATE)
    private Date vaccine2Date;
    @Enumerated(value = EnumType.STRING)
    private VaccineType name;


}
