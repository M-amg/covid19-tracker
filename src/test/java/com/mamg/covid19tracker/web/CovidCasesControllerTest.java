package com.mamg.covid19tracker.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mamg.covid19tracker.Covid19trackerApplication;
import com.mamg.covid19tracker.entities.CovidCases;
import com.mamg.covid19tracker.enums.Countries;
import com.mamg.covid19tracker.enums.Gender;
import com.mamg.covid19tracker.enums.PreDateType;
import com.mamg.covid19tracker.enums.TestResult;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;


import java.util.Calendar;
import java.util.GregorianCalendar;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Covid19trackerApplication.class)
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CovidCasesControllerTest {

    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }


    @Test
    @Order(1)
    void saveCovidCasesTest() throws Exception {
        String uri = "/covid-cases";
        CovidCases covidCases = new CovidCases(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), TestResult.NEGATIVE, Gender.MALE, "name", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "address", Countries.ALGERIA);

        String inputJson = this.mapToJson(covidCases);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    @Order(2)
    void updateCovidCasesTest() throws Exception {
        String uri = "/covid-cases/1";
        CovidCases covidCases = new CovidCases(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), TestResult.NEGATIVE, Gender.MALE, "Amg", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "address", Countries.ALGERIA);

        String inputJson = this.mapToJson(covidCases);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        CovidCases cases=new ObjectMapper().readValue(content, CovidCases.class);
        assertEquals("Amg", cases.getName());
    }


    @SneakyThrows
    @Test
    @Order(3)
    void getCasesPreDayTest() {
        String uri = "/covid-cases";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("date", "2021-11-23");
        params.add("type", PreDateType.DAY.name());
        params.add("countries", Countries.MOROCCO.name());
        params.add("test_result", TestResult.NEGATIVE.name());
        params.add("page", "0");
        params.add("size", "3");

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).params(params)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();

        JSONObject o = new JSONObject(content);

        assertTrue((Integer) o.get("totalItems") > 0);
    }


    @SneakyThrows
    @Test
    @Order(4)
    void getCasesPreMonthTest() {
        String uri = "/covid-cases";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("date", "2021-11");
        params.add("type", PreDateType.MONTH.name());
        params.add("countries", Countries.MOROCCO.name());
        params.add("test_result", TestResult.NEGATIVE.name());
        params.add("page", "0");
        params.add("size", "3");

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).params(params)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();

        JSONObject o = new JSONObject(content);
        assertTrue((Integer) o.get("totalItems") > 0);
    }
    @SneakyThrows
    @Test
    @Order(5)
    void getCasesPreYearsTest() {
        String uri = "/covid-cases";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("date", "2021");
        params.add("type", PreDateType.YEAR.name());
        params.add("countries", Countries.MOROCCO.name());
        params.add("test_result", TestResult.NEGATIVE.name());
        params.add("page", "0");
        params.add("size", "3");

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).params(params)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();

        JSONObject o = new JSONObject(content);
        assertTrue((Integer) o.get("totalItems") > 0);
    }
}