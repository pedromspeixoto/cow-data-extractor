package com.cde.utils.validations;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

public class Validator {

    public static boolean isValidEmail(String email) {

        // create the EmailValidator instance
        EmailValidator validator = EmailValidator.getInstance();

        // check for valid email addresses using isValid method
        return validator.isValid(email);

    }

    public static boolean isValidPhoneNumber(String phoneNumber) {

        String allCountryRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";
        Pattern pattern = Pattern.compile(allCountryRegex);

        return pattern.matcher(phoneNumber).matches();

    }

    public static boolean isDateInTheFuture(Date referenceDate, Long offset) {
        // create a date after the currente date with the specified offset (in minutes)
        long offsetMilliseconds = System.currentTimeMillis() + 1000*60*offset;
        // create date object
        Date offsetDate = new Date(offsetMilliseconds);
        // compare both dates
        if (referenceDate.after(offsetDate)) {
            return true;
        }
        return false;
    }

    public static boolean isDateInTheFuture(Date referenceDate) {
        Date current = new Date();
        // compare both dates
        if (referenceDate.after(current)) {
            return true;
        }
        return false;
    }

}