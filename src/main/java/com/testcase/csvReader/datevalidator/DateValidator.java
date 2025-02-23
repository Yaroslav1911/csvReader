package com.testcase.csvReader.datevalidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * This class is used for data validation in .filter of CsvToBeanBuilder
 * */
public class DateValidator {
    private final DateTimeFormatter dateTimeFormatter;

    public DateValidator(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }
    /**
     * Method accepts a string, and tries to parse it to the chosen dateTimeFormatter. If passed, then date is validate.
     * */
    public boolean isValid(String dateString) {
        try {
            LocalDate.parse(dateString, this.dateTimeFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
