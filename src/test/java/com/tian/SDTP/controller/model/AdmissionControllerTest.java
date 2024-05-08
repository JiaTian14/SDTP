package com.tian.SDTP.controller.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AdmissionControllerTest {

    @Mock
    private HttpURLConnection connection;

    @InjectMocks
    private AdmissionController admissionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAdmissionsId() throws Exception {
        // Mock Patient API response
        String patientApiResponse = "[{\"forename\":\"Viv\",\"surname\":\"Robinson\",\"id\":1,\"nhsNumber\":\"1113335555\"}," +
                "{\"forename\":\"Heather\",\"surname\":\"Carter\",\"id\":2,\"nhsNumber\":\"2224446666\"}," +
                "{\"forename\":\"Nicky\",\"surname\":\"Barnes\",\"id\":3,\"nhsNumber\":\"6663338888\"}," +
                "{\"forename\":\"Jacky\",\"surname\":\"King\",\"id\":4,\"nhsNumber\":\"7773338888\"}," +
                "{\"forename\":\"Rhi\",\"surname\":\"Sharpe\",\"id\":5,\"nhsNumber\":\"6663339999\"}]";
        mockHttpURLConnection(patientApiResponse);

        // Mock Admission API response
        String admissionApiResponse = "[{\"admissionDate\":\"2020-12-07T22:14:00\",\"patientID\":1,\"dischargeDate\":\"0001-01-01T00:00:00\",\"id\":2}," +
                "{\"admissionDate\":\"2020-11-28T16:45:00\",\"patientID\":2,\"dischargeDate\":\"2020-11-28T23:56:00\",\"id\":1}," +
                "{\"admissionDate\":\"2021-09-23T21:50:00\",\"patientID\":2,\"dischargeDate\":\"2021-09-27T09:56:00\",\"id\":3}," +
                "{\"admissionDate\":\"2024-02-23T21:50:00\",\"patientID\":5,\"dischargeDate\":\"2024-09-27T09:56:00\",\"id\":4}," +
                "{\"admissionDate\":\"2024-04-12T22:55:00\",\"patientID\":5,\"dischargeDate\":\"2024-04-14T11:36:00\",\"id\":5}," +
                "{\"admissionDate\":\"2024-04-19T21:50:00\",\"patientID\":5,\"dischargeDate\":\"0001-01-01T00:00:00\",\"id\":6}]";
        mockHttpURLConnection(admissionApiResponse);

        // Test for patient ID 1
        String expectedResult1 = "Patient Details:\n" +
                "{\"forename\":\"Viv\",\"surname\":\"Robinson\",\"id\":1,\"nhsNumber\":\"1113335555\"}\n\n" +
                "Admission Details:\n" +
                "{\"admissionDate\":\"2020-12-07T22:14:00\",\"patientID\":1,\"dischargeDate\":\"0001-01-01T00:00:00\",\"id\":2}\n";
        String result1 = admissionController.getAdmissionsId("1");
        assertEquals(expectedResult1, result1);

        // Test for patient ID 2
        String expectedResult2 = "Patient Details:\n" +
                "{\"forename\":\"Heather\",\"surname\":\"Carter\",\"id\":2,\"nhsNumber\":\"2224446666\"}\n\n" +
                "Admission Details:\n" +
                "{\"admissionDate\":\"2020-11-28T16:45:00\",\"patientID\":2,\"dischargeDate\":\"2020-11-28T23:56:00\",\"id\":1}\n" +
                "{\"admissionDate\":\"2021-09-23T21:50:00\",\"patientID\":2,\"dischargeDate\":\"2021-09-27T09:56:00\",\"id\":3}\n";
        String result2 = admissionController.getAdmissionsId("2");
        assertEquals(expectedResult2, result2);

        // Test for patient ID 3
        String expectedResult3 = "Patient Details:\n" +
                "{\"forename\":\"Nicky\",\"surname\":\"Barnes\",\"id\":3,\"nhsNumber\":\"6663338888\"}\n\n" +
                "Admission Details:\n";
        String result3 = admissionController.getAdmissionsId("3");
        assertEquals(expectedResult3, result3);

        // Test for patient ID 4
        String expectedResult4 = "Patient Details:\n" +
                "{\"forename\":\"Jacky\",\"surname\":\"King\",\"id\":4,\"nhsNumber\":\"7773338888\"}\n\n" +
                "Admission Details:\n";
        String result4 = admissionController.getAdmissionsId("4");
        assertEquals(expectedResult4, result4);

        // Test for patient ID 5
        String expectedResult5 = "Patient Details:\n" +
                "{\"forename\":\"Rhi\",\"surname\":\"Sharpe\",\"id\":5,\"nhsNumber\":\"6663339999\"}\n\n" +
                "Admission Details:\n" +
                "{\"admissionDate\":\"2024-02-23T21:50:00\",\"patientID\":5,\"dischargeDate\":\"2024-09-27T09:56:00\",\"id\":4}\n" +
                "{\"admissionDate\":\"2024-04-12T22:55:00\",\"patientID\":5,\"dischargeDate\":\"2024-04-14T11:36:00\",\"id\":5}\n" +
                "{\"admissionDate\":\"2024-04-19T21:50:00\",\"patientID\":5,\"dischargeDate\":\"0001-01-01T00:00:00\",\"id\":6}\n";
        String result5 = admissionController.getAdmissionsId("5");
        assertEquals(expectedResult5, result5);
    }

    // Utility method to mock HTTP connection
    private void mockHttpURLConnection(String response) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
        when(connection.getInputStream()).thenReturn(inputStream);
        when(connection.getResponseCode()).thenReturn(200);
        when(connection.getContentType()).thenReturn("application/json");
    }
}