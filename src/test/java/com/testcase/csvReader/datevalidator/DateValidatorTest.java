package com.testcase.csvReader.datevalidator;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
class DateValidatorTest {

    DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
    DateValidator dateValidator = new DateValidator(formatter);

    @Test
    void isValid() {
        assertTrue(dateValidator.isValid("20121218"));
        assertFalse(dateValidator.isValid("20122118"));
    }
}