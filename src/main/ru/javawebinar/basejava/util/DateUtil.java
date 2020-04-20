package main.ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {

    public static LocalDate FUTURE = LocalDate.of(3000, 1, 1);
    public static LocalDate NOW = LocalDate.now();

    public static LocalDate of(int year, Month month){
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate checkEndDateAndSet(String string){
        return  string.equals("")? FUTURE : LocalDate.parse(string);
    }

    public static LocalDate checkStartDateAndSet(String string){
        return  string.equals("")? NOW: LocalDate.parse(string);
    }


}
