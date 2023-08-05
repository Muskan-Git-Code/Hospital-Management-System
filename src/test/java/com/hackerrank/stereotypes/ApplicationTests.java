package com.hackerrank.stereotypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.stereotypes.controller.ContactController;
import com.hackerrank.stereotypes.model.Patient;
import com.hackerrank.stereotypes.model.Staff;
import com.hackerrank.stereotypes.repository.PatientRepository;
import com.hackerrank.stereotypes.repository.StaffRepository;
import com.hackerrank.stereotypes.service.ContactService;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.Entity;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class ApplicationTests {
    @Autowired
    StaffRepository staffRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        staffRepository.deleteAll();
        patientRepository.deleteAll();
    }

    @Test
    public void checkController() {
        boolean hasAnnotation = false;

        Annotation annotation = AnnotationUtils.findAnnotation(ContactController.class, Controller.class);
        if (annotation != null) {
            hasAnnotation = true;
        }

        assertTrue(hasAnnotation);
    }


    @Test
    public void checkService() {
        boolean hasAnnotation = false;

        Annotation annotation = AnnotationUtils.findAnnotation(ContactService.class, Service.class);
        if (annotation != null) {
            hasAnnotation = true;
        }

        assertTrue(hasAnnotation);
    }

    @Test
    public void checkRepository() {
        boolean hasAnnotation = false;
        Annotation annotation = AnnotationUtils.findAnnotation(StaffRepository.class, Repository.class);
        if (annotation != null) {
            hasAnnotation = true;
        }
        assertTrue(hasAnnotation);

        hasAnnotation = false;
        annotation = AnnotationUtils.findAnnotation(PatientRepository.class, Repository.class);
        if (annotation != null) {
            hasAnnotation = true;
        }
        assertTrue(hasAnnotation);
    }

    @Test
    public void checkEntity() {
        boolean hasAnnotation = false;
        Annotation annotation = AnnotationUtils.findAnnotation(Staff.class, Entity.class);
        if (annotation != null) {
            hasAnnotation = true;
        }
        assertTrue(hasAnnotation);

        hasAnnotation = false;
        annotation = AnnotationUtils.findAnnotation(Patient.class, Entity.class);
        if (annotation != null) {
            hasAnnotation = true;
        }
        assertTrue(hasAnnotation);
    }


    public void testPostStaff(String name, String role, String email, String phone, String password) throws Exception {

        Staff person = new Staff(name, role, email, phone, password);
        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/staff/save")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(person)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.role").value(role))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.password").value(password))
                .andExpect(status().isCreated()).andReturn().getResponse();

        String res = response.getContentAsString();
        System.out.println(res);

        if (!res.equals(null) && !res.equals("") ){
            Integer id = JsonPath.parse(response.getContentAsString()).read("$.id");
            assertEquals(true, staffRepository.findById(id).isPresent());
        }
    }


    public void testPostPatient(String name, Integer age, String email, String phone, String room, String doctorName, String doctorEmail, String admitDate, Integer expenses, String status) throws Exception {

        Patient person = new Patient(name, age, email, phone, room, doctorName, doctorEmail, admitDate, expenses, status);
        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/patient/save")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn().getResponse();

        String res = response.getContentAsString();
        System.out.println(res);

        if (!res.equals(null) && !res.equals("") ){
            Integer id = JsonPath.parse(response.getContentAsString()).read("$.id");
            assertEquals(true, patientRepository.findById(id).isPresent());
        }

    }


    @Test
    public void testRetrievePatientsByStatus() throws Exception {

        testPostData();

        // Find all Patients
        mockMvc.perform(get("/patient/retrieve/"))
                .andDo(print())
                .andExpect(status().isOk());

        //Fetch all patients admitted in hospital
        String status = "Admitted";
        mockMvc.perform(get("/patient/retrieve/" + status))
                .andDo(print())
                .andExpect(status().isOk());

        //Fetch all patients discharged from hospital
        status = "Discharged";
        mockMvc.perform(get("/patient/retrieve/" + status))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testPostData() throws Exception{

        //Adding staff members
        testPostStaff("abc1", "Doctor", "abc1@gmail.com", "9728664901", "xyz1");
        testPostStaff("abc2", "Nurse", "abc2@gmail.com", "8728664901", "xyz2");
        testPostStaff("abc3", "Doctor", "abc3@gmail.com", "7728664901", "xyz3");
        testPostStaff("abc4", "Nurse", "abc4@gmail.com", "6728664901", "xyz4");

        //Adding Patients
        testPostPatient("pqr1", 20, "pqr1@gmail.com", "3247785675", "A1", "abc1", "abc1@gmail.com", LocalDateTime.now().toString(), 3050, "Admitted");
        testPostPatient("pqr2", 42, "pqr2@gmail.com", "4247785675", "A5", "abc3", "abc3@gmail.com", LocalDateTime.now().toString(), 7200, "Admitted");
        testPostPatient("pqr3", 65, "pqr3@gmail.com", "5247785675", "A3", "abc3", "abc3@gmail.com", LocalDateTime.now().toString(), 500, "Admitted");
        testPostPatient("pqr4", 22, "pqr4@gmail.com", "6247785675", "A7", "abc1", "abc1@gmail.com", LocalDateTime.now().toString(), 3800, "Admitted");
        testPostPatient("pqr5", 28, "pqr5@gmail.com", "7247785675", "A6", "abc1", "abc1@gmail.com", LocalDateTime.now().toString(), 3560, "Admitted");

        //Discharge some patients
        testPostPatient("pqr1", 20, "pqr1@gmail.com", "3247785675", "A1", "abc1", "abc1@gmail.com", LocalDateTime.now().toString(), 3050, "Discharged");
        testPostPatient("pqr2", 42, "pqr2@gmail.com", "4247785675", "A5", "abc3", "abc3@gmail.com", LocalDateTime.now().toString(), 7200, "Discharged");
        testPostPatient("pqr3", 65, "pqr3@gmail.com", "5247785675", "A3", "abc3", "abc3@gmail.com", LocalDateTime.now().toString(), 500, "Discharged");



    }
}