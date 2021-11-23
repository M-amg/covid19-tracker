package com.mamg.covid19tracker.web;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mamg.covid19tracker.Covid19trackerApplication;
import com.mamg.covid19tracker.entities.CovidCases;
import com.mamg.covid19tracker.entities.Vaccination;
import com.mamg.covid19tracker.enums.*;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = Covid19trackerApplication.class)
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VaccinationControllerTest {
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
    @Rollback(value = false)
    void saveVaccinationTest() throws Exception{
        String uri = "/vaccinations";
        Vaccination vaccination = new Vaccination(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), VaccineType.Sinopharm, Gender.MALE, "Amghar", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "address", Countries.ALGERIA);

        String inputJson = this.mapToJson(vaccination);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    @Rollback(value = false)
    @Order(2)
    void updateVaccinationTest() throws Exception{
        String uri = "/vaccinations/7";
        Vaccination vaccination = new Vaccination(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), VaccineType.Sinopharm, Gender.MALE, "Amg", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "address", Countries.ALGERIA);

        String inputJson = this.mapToJson(vaccination);
        System.out.println(inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        Vaccination vaccine=new ObjectMapper().readValue(content, Vaccination.class);
        assertEquals("Amg",vaccine.getName() );
    }

    @Test
    @Order(3)
    void getVaccinesPreDate() throws  Exception{
        String uri = "/vaccinations";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("date", "2020-01-01");
        params.add("type", PreDateType.DAY.name());
        params.add("name", VaccineType.Sinopharm.name());
        params.add("countries", Countries.ALGERIA.name());
        params.add("isSecondVaccine", "false");
        params.add("page", "0");
        params.add("size", "3");

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).params(params)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();

        JSONObject o = new JSONObject(content);

        assertTrue((Integer) o.get("totalItems") > 0);
    }
    @Test
    @Order(4)
    void getVaccinesPreDateHaveASecondVaccines() throws  Exception{
        String uri = "/vaccinations";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("date", "2020-01-01");
        params.add("type", PreDateType.DAY.name());
        params.add("name", VaccineType.Sinopharm.name());
        params.add("countries", Countries.ALGERIA.name());
        params.add("isSecondVaccine", "true");
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