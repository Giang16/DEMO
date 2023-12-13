package com.example.demo.Function;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {
    public static int calculateAge(String dateString) {
        // Parse the input date string
        LocalDate birthDate = LocalDate.parse(dateString);

        // Calculate the age
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);

        return period.getYears();
    }
}
