package com.tian.SDTP.controller.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AllocationControllerTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private AllocationController allocationController;

    private final String allocationUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/Allocations";
    private final String employeeUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/Employees";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void checkAllocations() {
        // Mock allocation response
        String allocationResponse = "[{\"employeeID\":1},{\"employeeID\":3}]";
        when(restTemplate.getForObject(allocationUrl, String.class)).thenReturn(allocationResponse);

        // Mock employee response
        String employeeResponse = "[{\"forename\":\"Sarah\",\"surname\":\"Finley\",\"id\":1}," +
                "{\"forename\":\"Robert\",\"surname\":\"Jackson\",\"id\":2}," +
                "{\"forename\":\"Patrick\",\"surname\":\"Wicks\",\"id\":5}]";
        when(restTemplate.getForObject(employeeUrl, String.class)).thenReturn(employeeResponse);

        // Invoke the method under test
        String result = allocationController.checkAllocations();

        // Assert the result
        assertEquals("[{\"forename\":\"Sarah\",\"surname\":\"Finley\",\"id\":1},{\"forename\":\"Robert\",\"surname\":\"Jackson\",\"id\":2}" +
                ",{\"forename\":\"Patrick\",\"surname\":\"Wicks\",\"id\":5}]", result);
    }
}