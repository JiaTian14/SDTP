package com.tian.SDTP.controller.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
@RestController
public class AdmissionController {

    @GetMapping("/f1/{patientId}")
    public String getAdmissionsId(@PathVariable final String patientId) {
        StringBuilder result = new StringBuilder();

        try {
            // URL for the API endpoints
            String patientApiUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/Patients";
            String admissionApiUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/Admissions";

            // Create HTTP connection for patient API
            HttpURLConnection patientConnection = createConnection(patientApiUrl);
            JSONArray patientArray = getJsonArray(patientConnection);

            // Get patient details based on the provided ID
            JSONObject patientDetails = null;
            for (int i = 0; i < patientArray.length(); i++) {
                JSONObject patient = patientArray.getJSONObject(i);
                if (Integer.toString(patient.getInt("id")).equals(patientId)) {
                    patientDetails = patient;
                    break;
                }
            }

            // Create HTTP connection for admission API
            HttpURLConnection admissionConnection = createConnection(admissionApiUrl);
            JSONArray admissionArray = getJsonArray(admissionConnection);

            // Get admission details based on the provided patient ID
            List<String> admissionDetails = new ArrayList<>();
            for (int i = 0; i < admissionArray.length(); i++) {
                JSONObject admission = admissionArray.getJSONObject(i);
                if (Integer.toString(admission.getInt("patientID")).equals(patientId)) {
                    admissionDetails.add(admission.toString());
                }
            }

            // Build the result string with patient and admission details
            result.append("Patient Details:\n").append(patientDetails != null ? patientDetails.toString() : "Patient not found").append("\n\n");
            result.append("Admission Details:\n");
            for (String admission : admissionDetails) {
                result.append(admission).append("\n");
            }

            // Close connections
            patientConnection.disconnect();
            admissionConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    HttpURLConnection createConnection(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    private JSONArray getJsonArray(HttpURLConnection connection) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return new JSONArray(response.toString());
    }
}