module by.bsuir {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.xml;

    opens by.bsuir to javafx.fxml;
    opens by.bsuir.entities to com.fasterxml.jackson.databind;
    exports by.bsuir;
}