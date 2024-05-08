package com.tian.SDTP;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class IDTest {
    @InjectMocks
    private ID id;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testFetchStaffWithNoAdmissions() {
        // Initialize the ID instance
        ID id = new ID();


        // Perform the actual test
        id.fetchStaffWithNoAdmissions();

        // Assert that the output text area contains the expected output
        String expectedOutput = "Employee ID: 1, Name: Sarah Finley\n" +
                "Employee ID: 2, Name: Robert Jackson\n" +
                "Employee ID: 5, Name: Patrick Wicks\n";
        assertEquals(expectedOutput, id.getOutputTextArea().getText());
    }
}