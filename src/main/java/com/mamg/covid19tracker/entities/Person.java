package com.mamg.covid19tracker.entities;


import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Enumerated(value = EnumType.STRING)
    protected Gender gender;
    protected String name;
    @Temporal(TemporalType.DATE)
    protected Date dob;
    protected String address;
    @Enumerated(value = EnumType.STRING)
    protected Countries country;


}
