package com.example.employment_form;

import java.time.LocalDate;

public class ApplicationModel {
    int id;
    String fullName;
    String address;
    String phone;
    String email;
    String education;
    String gender;
    LocalDate dateAvailable;
    String desiredPosition;
    double desiredSalary;
    boolean legallyAuthorized;
    boolean hasRelativesAtCompany;
    String relativesExplanation;
    String applicantSignature;
    LocalDate submittedAt;

    public ApplicationModel(String fullName, String address, String phone, String email, String education, String gender, LocalDate dateAvailable, String desiredPosition, double desiredSalary, boolean legallyAuthorized, boolean hasRelativesAtCompany, String relativesExplanation, String applicantSignature, LocalDate submittedAt) {
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.education = education;
        this.gender = gender;
        this.dateAvailable = dateAvailable;
        this.desiredPosition = desiredPosition;
        this.desiredSalary = desiredSalary;
        this.legallyAuthorized = legallyAuthorized;
        this.hasRelativesAtCompany = hasRelativesAtCompany;
        this.relativesExplanation = relativesExplanation;
        this.applicantSignature = applicantSignature;
        this.submittedAt = submittedAt;
    }

}
