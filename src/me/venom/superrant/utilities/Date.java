package me.venom.superrant.utilities;

import java.util.Calendar;

public class Date
{
    private int day, month, year, maxDays;

    private final int[] maxDaysBase = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    public Date(int day, int month, int year)
    {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    public Date()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    public String getFriendlyDateString()
    {
        return String.valueOf(day) + '-' + month + '-' + year;
    }

    public int getDay() { return day; }
    public int getMonth() { return month; }
    public int getYear() { return year; }
    public void addDays(int amount)
    {
        day += amount;
        while(day > maxDays)
        {
            day -= maxDays;
            month += 1;
            if(month > 12)
            {
                month = 1;
                year += 1;
            }
        }
    }

    private void setYear(int year) { this.year = year; }
    private void setMonth(int month)
    {
        if(month < 1 || month > 12) month = 1;
        this.month = month;
        if(month == 2 && isLeapYear(year))
        {
            maxDays = 29;
            return;
        }
        maxDays = maxDaysBase[month - 1];
    }
    private void setDay(int day)
    {
        if(day < 1 || day > maxDays) day = 1;
        this.day = day;
    }
    private boolean isLeapYear(int year)
    {
        return year % 4 == 0 || (year % 100 == 0 && year % 400 == 0);
    }
}
