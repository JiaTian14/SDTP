package com.tian.SDTP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ID extends JFrame {
    private JButton fetchButton;
    private JTextArea outputTextArea;

    public ID() {
        setTitle("Staff with No Admissions");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane, BorderLayout.CENTER);

        fetchButton = new JButton("Fetch Staff with No Admissions");
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchStaffWithNoAdmissions();
            }
        });
        add(fetchButton, BorderLayout.SOUTH);
    }

    public ID(RestTemplate restTemplate) {
    }

    void fetchStaffWithNoAdmissions() {
        try {
            String allocationUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/Allocations";
            String employeeUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/Employees";

            // RestTemplate for making HTTP requests
            RestTemplate restTemplate = new RestTemplate();

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

            // Display result
            if (unallocatedEmployees.length() > 0) {
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < unallocatedEmployees.length(); i++) {
                    JSONObject employee = unallocatedEmployees.getJSONObject(i);
                    String forename = employee.getString("forename");
                    String surname = employee.getString("surname");
                    int employeeId = employee.getInt("id");
                    String employeeDetail = "Employee ID: " + employeeId + ", Name: " + forename + " " + surname;
                    result.append(employeeDetail).append("\n");
                }
                outputTextArea.setText(result.toString());
            } else {
                outputTextArea.setText("All employees have allocations.");
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            outputTextArea.setText("An error occurred while fetching data.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ID app = new ID();
                app.setVisible(true);
            }
        });
    }

    public void setRestTemplate(RestTemplate restTemplate) {
    }

    public JTextArea getOutputTextArea() {
        return outputTextArea;
    }
}