package com.mamg.covid19tracker.entities;


import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.Gender;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id ;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Please provide a gender")
    private Gender gender;
    @NotBlank(message = "Please provide a name")
    private String name;
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Please provide a dob")
    private Date dob;
    @NotBlank(message = "Please provide a address")
    private String address;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Please provide a country")
    private Countries country;


    public Person(Gender gender, String name, Date dob, String address,Countries country) {
        this.gender=gender;
        this.name=name;
        this.dob=dob;
        this.address=address;
        this.country=country;
    }
}
