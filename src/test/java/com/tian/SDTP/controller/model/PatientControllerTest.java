package com.tian.SDTP.controller.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PatientControllerTest {

    @Mock
    private RestTemplate restTemplate;

    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientController = new PatientController();
        patientController.setRestTemplate(restTemplate);
    }
    @Test
    void identifyStaffWithMostAdmissions() {
        // Mock allocation API response
        String allocationResponse = "[{\"employeeID\": 4},{\"employeeID\": 2},{\"employeeID\": 1}]";
        // Mock employee API response
        String employeeResponse = "[{\"id\": 1,\"forename\": \"John\",\"surname\": \"Doe\"}" +
                ",{\"id\": 2,\"forename\": \"Alice\",\"surname\": \"Smith\"},{\"id\": 4,\"forename\": \"Sarah\",\"surname\": \"Jones\"}]";

        when(restTemplate.getForObject(any(String.class), any(Class.class))).thenReturn(allocationResponse, employeeResponse);

        String expectedOutput = "Staff with the most admissions - EmployeeID: 4, Employee Details: " +
                "{\"forename\":\"Sarah\",\"surname\":\"Jones\",\"id\":4}";
        assertEquals(expectedOutput, patientController.identifyStaffWithMostAdmissions());
    }
}