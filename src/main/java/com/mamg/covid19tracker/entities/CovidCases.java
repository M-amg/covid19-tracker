package com.mamg.covid19tracker.entities;


import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.Gender;
import com.mamg.covid19tracker.enums.TestResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CovidCases extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "Please provide a date of test")
    private Date testDate;
    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Please provide a test of result")
    private TestResult testResult;

    public CovidCases(Date testDate, TestResult testResult, Gender gender, String name, Date dob, String address, Countries country) {
        super(gender, name, dob, address,country);
        this.testDate=testDate;
        this.testResult=testResult;
    }
}
