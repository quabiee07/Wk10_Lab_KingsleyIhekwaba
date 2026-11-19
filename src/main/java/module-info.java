module com.example.employment_form {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.employment_form to javafx.fxml;
    exports com.example.employment_form;
}
