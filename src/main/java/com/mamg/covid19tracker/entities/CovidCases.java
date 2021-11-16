package com.mamg.covid19tracker.entities;

import com.covid19.tracker.enums.Countries;
import com.covid19.tracker.enums.Gender;
import com.covid19.tracker.enums.TestResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CovidCases extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date testDate;
    @Enumerated(value = EnumType.STRING)
    private TestResult testResult;

    public CovidCases(Date testDate,TestResult testResult,Gender gender, String name, Date dob, String address, Countries country) {
        super(gender, name, dob, address, country);
        this.testDate=testDate;
        this.testResult=testResult;
    }
}
