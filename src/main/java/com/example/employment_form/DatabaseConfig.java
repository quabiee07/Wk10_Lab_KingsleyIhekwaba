package com.example.employment_form;

import java.sql.*;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employment_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String INSERT_APPLICATION = "INSERT INTO applications "
            + "(full_name, address, phone, email, education, gender, date_available, desired_position, desired_salary, legally_authorized, has_relatives_at_company, relatives_explanation, applicant_signature, submitted_At) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    public boolean saveApplication(ApplicationModel application) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT_APPLICATION)) {
            statement.setString(1, application.fullName);
            statement.setString(2, application.address);
            statement.setString(3, application.phone);
            statement.setString(4, application.email);
            statement.setString(5, application.education);
            statement.setString(6, application.gender);
            statement.setDate(7, Date.valueOf(application.dateAvailable));
            statement.setString(8, application.desiredPosition);
            statement.setDouble(9, application.desiredSalary);
            statement.setBoolean(10, application.legallyAuthorized);
            statement.setBoolean(11, application.hasRelativesAtCompany);
            statement.setString(12, application.relativesExplanation);
            statement.setString(13, application.applicantSignature);
            statement.setDate(14, Date.valueOf(application.submittedAt));
            return statement.executeUpdate() == 1;
        }
    }

}
