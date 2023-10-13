package com.seoultech.gaemanda.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

  public static Integer getFullAge(String birthDate) {
    LocalDate now = LocalDate.now();
    LocalDate parsedBirthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyyMMdd"));

    int americanAge = now.minusYears(parsedBirthDate.getYear()).getYear();

    if (parsedBirthDate.plusYears(americanAge).isAfter(now)) {
      americanAge = americanAge - 1;
    }

    return americanAge;
  }

}
