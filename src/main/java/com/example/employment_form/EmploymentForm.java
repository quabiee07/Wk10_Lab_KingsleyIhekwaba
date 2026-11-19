package com.example.employment_form;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmploymentForm extends Application {
    private final DatabaseConfig databaseConfig = new DatabaseConfig();
    private static final String PAGE_BACKGROUND = "#f5f5dc";
    private static final String TEXT_COLOR = "#263744";
    private static final String MUTED_TEXT = "#6d818a";

    private final TextField fullNameField = new TextField();
    private final TextField addressField = new TextField();
    private final TextField phoneField = new TextField();
    private final TextField emailField = new TextField();
    private final ComboBox<String> highestEducationBox = new ComboBox<>();
    private final TextField desiredPositionField = new TextField();
    private final TextField desiredSalaryField = new TextField();
    private final DatePicker dateAvailablePicker = new DatePicker();
    private final TextField eligibilityExplanationField = new TextField();
    private final TextField signatureField = new TextField();
    private final ToggleGroup genderGroup = new ToggleGroup();
    private final ToggleGroup authorizationGroup = new ToggleGroup();
    private final ToggleGroup relativesGroup = new ToggleGroup();

    @Override
    public void start(Stage stage) {
        HBox formSections = new HBox(18, personalInfo(), employmentEligibility());
        HBox.setHgrow(formSections.getChildren().get(0), Priority.ALWAYS);
        HBox.setHgrow(formSections.getChildren().get(1), Priority.ALWAYS);
        formSections.getChildren().forEach(node -> ((Region) node).setMaxWidth(Double.MAX_VALUE));

        VBox page = new VBox(16, header(), formSections, declaration());
        page.setFillWidth(true);
        page.setStyle("-fx-padding: 22px 30px 24px 30px; -fx-spacing: 16px; -fx-background-color: #f0f8ff;");

        Scene scene = new Scene(page, 1180, 900);
        scene.setFill(Color.web(PAGE_BACKGROUND));
        stage.setTitle("Employment Application");
        stage.setMinWidth(900);
        stage.setMinHeight(760);
        stage.setScene(scene);
        stage.show();
    }

    private HBox header() {
        HBox brand = getBrand();
        Label title = new Label("Employment Application");
        title.setStyle("-fx-alignment: center-right; -fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");
        HBox emBox = new HBox(title);
        emBox.setPadding(new Insets(12, 24, 12, 24));
        emBox.setStyle("-fx-background-color: #6495ed; ");
        HBox header = new HBox(brand, emBox);
        HBox.setHgrow(brand, Priority.ALWAYS);
        header.setStyle("-fx-padding: 0 0 8px 0;");

        return header;
    }

    private static HBox getBrand() {
        Label brandMark = new Label("K");
        brandMark.setStyle("-fx-min-width: 48px; -fx-min-height: 48px; -fx-alignment: center; -fx-background-color: #6495ed; -fx-background-radius: 24px; -fx-text-fill: white; -fx-font-size: 23px; -fx-font-weight: bold;");
        Label companyName = new Label("King's Atelier");
        companyName.setStyle("-fx-text-fill: #203744; -fx-font-size: 18px; -fx-font-weight: bold;");
        Label companySubtitle = new Label("Company");
        companySubtitle.setStyle("-fx-text-fill: " + MUTED_TEXT + "; -fx-font-size: 13px;");
        VBox branding = new VBox(2, companyName, companySubtitle);
        HBox brand = new HBox(14, brandMark, branding);
        brand.setAlignment(Pos.CENTER_LEFT);
        return brand;
    }

    private VBox personalInfo() {
        highestEducationBox.getItems().setAll(
                "High school diploma", "Associate degree", "Bachelor's degree",
                "Master's degree", "Doctoral degree", "Other");
        highestEducationBox.setPromptText("Select education");
        GridPane fields = createGrid();
        addField(fields, 0, 0, "Full name *", fullNameField, 2);
        addField(fields, 0, 1, "Current address *", addressField, 2);
        addField(fields, 0, 2, "Phone *", phoneField, 1);
        addField(fields, 1, 2, "Email address *", emailField, 1);
        addField(fields, 0, 3, "Highest education attained *", highestEducationBox, 1);

        HBox genderChoices = radioChoices(genderGroup, "Male", "Female", "Other");
        addField(fields, 1, 3, "Gender *", genderChoices, 1);
        return section("Personal Information", fields);
    }

    private VBox employmentEligibility() {
        GridPane fields = createGrid();
        addField(fields, 0, 0, "Date available *", dateAvailablePicker, 1);
        addField(fields, 1, 0, "Desired position *", desiredPositionField, 1);
        addField(fields, 0, 1, "Desired salary *", desiredSalaryField, 2);

        HBox authorizationChoices = radioChoices(authorizationGroup, "Yes", "No");
        addPrompt(fields, 0, 2, "Are you legally authorized to work in the country? *", authorizationChoices);

        HBox relativesChoices = radioChoices(relativesGroup, "Yes", "No");
        addPrompt(fields, 0, 3, "Do you have relatives working for our company? *", relativesChoices);
        addField(fields, 0, 4, "If yes, please explain", eligibilityExplanationField, 2);
        return section("Employment Eligibility", fields);
    }

    private VBox declaration() {
        Label declarationText = new Label("By submitting this application, you agree to adhere to company\npolicies and provide accurate information throughout the employment process.");
        declarationText.setWrapText(true);
        declarationText.setStyle("-fx-text-fill: #526873; -fx-font-size: 13px; -fx-line-spacing: 4px;");
        Label signatureLabel = new Label("Applicant's signature *");
        styleLabel(signatureLabel);
        VBox signature = new VBox(8, signatureLabel, signatureField);
        HBox declaration = new HBox(28, declarationText, signature);
        declaration.setStyle("-fx-alignment: center-left; -fx-padding: 12px 18px; -fx-spacing: 20px; -fx-background-color: #e3edef; -fx-background-radius: 10px; -fx-border-color: black; -fx-border-radius: 10px;");
        styleInput(signatureField);
        HBox.setHgrow(declarationText, Priority.ALWAYS);
        HBox.setHgrow(signature, Priority.ALWAYS);
        declarationText.setMaxWidth(Double.MAX_VALUE);
        signature.setMaxWidth(Double.MAX_VALUE);
        Button submitButton = new Button("Submit Application");
        submitButton.setMaxWidth(200);
        submitButton.setOnAction(event -> submitApplication());
        Button clearButton = new Button("Clear Form");
        clearButton.setOnAction(event -> clearForm());
        HBox actions = new HBox(12, submitButton, clearButton);
        return new VBox(12, declaration, actions);
    }

    private void clearForm() {
        fullNameField.clear();
        addressField.clear();
        phoneField.clear();
        emailField.clear();
        highestEducationBox.getSelectionModel().clearSelection();
        desiredPositionField.clear();
        desiredSalaryField.clear();
        dateAvailablePicker.setValue(null);
        eligibilityExplanationField.clear();
        signatureField.clear();
        genderGroup.selectToggle(null);
        authorizationGroup.selectToggle(null);
        relativesGroup.selectToggle(null);
    }

    private void submitApplication() {
        String fullName = fullNameField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String education = highestEducationBox.getValue();
        LocalDate dateAvailable = dateAvailablePicker.getValue();
        String desiredSalary = desiredSalaryField.getText().trim();
        String desiredPosition = desiredPositionField.getText().trim();
        String explanation = eligibilityExplanationField.getText().trim();
        String signature = signatureField.getText().trim();

        String gender = selectedValue(genderGroup);
        String authorizedToWork = selectedValue(authorizationGroup);
        String hasRelatives = selectedValue(relativesGroup);

        List<String> errors = new ArrayList<>();
        if (fullName.isEmpty()) errors.add("Enter your full name.");
        if (address.isEmpty()) errors.add("Enter your current address.");
        if (phone.isEmpty()) {
            errors.add("Enter your phone number.");
        } else if (phone.replaceAll("\\D", "").length() < 7 || phone.replaceAll("\\D", "").length() > 15) {
            errors.add("Enter a valid phone number (7–15 digits).");
        }
        if (email.isEmpty()) {
            errors.add("Enter your email address.");
        } else if (!email.matches("^[\\w.!#$%&'*+/=?^`{|}~-]+@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)+$")) {
            errors.add("Enter a valid email address.");
        }
        if (education == null || education.isBlank()) errors.add("Select your highest education attained.");
        if (gender == null) errors.add("Select a gender.");
        if (dateAvailable == null) {
            errors.add("Select your available start date.");
        } else if (dateAvailable.isBefore(LocalDate.now())) {
            errors.add("The available start date cannot be in the past.");
        }
        if (desiredPosition.isEmpty()) errors.add("Enter the desired position.");
        if (desiredSalary.isEmpty()) {
            errors.add("Enter your desired salary.");
        } else {
            try {
                if (new BigDecimal(desiredSalary).signum() <= 0) {
                    errors.add("Desired salary must be greater than zero.");
                }
            } catch (NumberFormatException exception) {
                errors.add("Enter desired salary as a positive number.");
            }
        }
        if (authorizedToWork == null) errors.add("Answer whether you are legally authorized to work.");
        if (hasRelatives == null) {
            errors.add("Answer whether you have relatives working for the company.");
        } else if (hasRelatives.equals("Yes") && explanation.isEmpty()) {
            errors.add("Please explain which relatives work for the company.");
        }
        if (signature.isEmpty()) errors.add("Enter your signature.");

        if (!errors.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Check your application");
            alert.setHeaderText("Please correct the following before submitting:");
            alert.setContentText(String.join("\n", errors));
            alert.showAndWait();
            return;
        }

        processApplication(fullName, address, phone, email, education, gender,
                dateAvailable, desiredPosition, desiredSalary, authorizedToWork,
                hasRelatives, explanation, signature);
    }

    private void processApplication(
            String fullName, String address,
            String phone, String email,
            String education, String gender, LocalDate dateAvailable,
            String desiredPosition, String desiredSalary,
            String authorizedToWork, String hasRelatives,
            String explanation, String signature) {

        ApplicationModel application =  new ApplicationModel(
                fullName, address, phone,
                email, education, gender,
                dateAvailable, desiredPosition,
                Double.parseDouble(desiredSalary),
                Boolean.parseBoolean(authorizedToWork),
                Boolean.parseBoolean(hasRelatives),
                explanation, signature,
                LocalDate.now());

        try {
            if (databaseConfig.saveApplication(application)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Application has been been submitted.");
                alert.setContentText("Your application has been submitted, a recruiter would reach out to you in 5 business days if you are a good fit");
                alert.showAndWait();
                clearForm();

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getSQLState());
        }

    }

    private String selectedValue(ToggleGroup group) {
        Toggle selectedToggle = group.getSelectedToggle();
        if (selectedToggle instanceof RadioButton radioButton) {
            return radioButton.getText();
        }
        return null;
    }

    private VBox section(String title, GridPane fields) {
        Label heading = new Label(title);
        heading.setStyle("-fx-padding: 9px 16px; -fx-background-color: #6495ed; -fx-background-radius: 5px; -fx-text-fill: white; -fx-font-size: 17px; -fx-font-weight: bold;");
        VBox section = new VBox(20, heading, fields);
        section.setStyle("-fx-padding: 14px 16px 16px 16px; -fx-background-color: #f0f8ff; -fx-background-radius: 8px; -fx-border-color: black; -fx-border-radius: 5px;");
        return section;
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(14);
        grid.setVgap(10);
        grid.setMaxWidth(Double.MAX_VALUE);
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(50);
            column.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(column);
        }
        return grid;
    }

    private void addField(GridPane grid, int column, int row, String labelText, Region control, int span) {
        Label label = new Label(labelText);
        styleLabel(label);
        control.setMaxWidth(Double.MAX_VALUE);
        if (control instanceof Control input) {
            styleInput(input);
        }
        VBox field = new VBox(7, label, control);
        field.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(field, Priority.ALWAYS);
        GridPane.setColumnSpan(field, span);
        grid.add(field, column, row);
    }

    private void addPrompt(GridPane grid, int column, int row, String prompt, HBox choices) {
        Label label = new Label(prompt);
        styleLabel(label);
        choices.setStyle("-fx-spacing: 22px; -fx-padding: 4px 0 2px 0;");
        VBox field = new VBox(9, label, choices);
        GridPane.setColumnSpan(field, 2);
        grid.add(field, column, row);
    }

    private HBox radioChoices(ToggleGroup group, String... options) {
        HBox choices = new HBox(22);
        for (String option : options) {
            RadioButton radio = new RadioButton(option);
            radio.setToggleGroup(group);
            radio.setStyle("-fx-text-fill: #405762; -fx-font-size: 14px;");
            choices.getChildren().add(radio);
        }
        return choices;
    }

    private void styleLabel(Label label) {
        label.setStyle("-fx-text-fill: #334954; -fx-font-size: 12px; -fx-font-weight: bold;");
    }

    private void styleInput(Control input) {
        input.setStyle("-fx-pref-height: 34px; -fx-background-color: white; -fx-background-radius: 6px; -fx-border-color: #c7d5d8; -fx-border-radius: 6px; -fx-border-width: 1px; -fx-padding: 6px 9px; -fx-text-fill: " + TEXT_COLOR + ";");
    }
}
