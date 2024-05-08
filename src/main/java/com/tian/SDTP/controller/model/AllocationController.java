package com.tian.SDTP.controller.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController

public class AllocationController {

    @GetMapping("/f4")
    public String checkAllocations() {
        // URLs for Allocation API and Employee API
        String allocationUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/Allocations";
        String employeeUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/Employees";

        // RestTemplate for making HTTP requests
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Fetch data from Allocation API
            String allocationResponse = restTemplate.getForObject(allocationUrl, String.class);
            JSONArray allocationArray = new JSONArray(allocationResponse);

            // Fetch data from Employee API
            String employeeResponse = restTemplate.getForObject(employeeUrl, String.class);
            JSONArray employeeArray = new JSONArray(employeeResponse);

            // Check which employee IDs do not exist in the allocation response
            JSONArray unallocatedEmployees = new JSONArray();
            for (int i = 0; i < employeeArray.length(); i++) {
                JSONObject employee = employeeArray.getJSONObject(i);
                int employeeID = employee.getInt("id");
                boolean found = false;
                for (int j = 0; j < allocationArray.length(); j++) {
                    JSONObject allocation = allocationArray.getJSONObject(j);
                    int allocationEmployeeID = allocation.getInt("employeeID");
                    if (allocationEmployeeID == employeeID) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    unallocatedEmployees.put(employee);
                }
            }

            return unallocatedEmployees.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to fetch data from one or both APIs.";
        }
    }
}

