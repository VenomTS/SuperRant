package me.venom.superrant;

import me.venom.superrant.utilities.Date;
import me.venom.superrant.utilities.FileManager;

import java.util.ArrayList;

public class DailyLog
{
    private Date date;

    private final ArrayList<Rental> rentals;

    public DailyLog(String stringDate)
    {
        rentals = new ArrayList<>();
        String[] newDate = stringDate.split("-");
        int day, month, year;
        day = Integer.parseInt(newDate[0]);
        month = Integer.parseInt(newDate[1]);
        year = Integer.parseInt(newDate[2]);
        date = new Date(day, month, year);
        FileManager.fetchRentalsForDailyLog(this);
    }

    public DailyLog()
    {
        setDate(new Date());
        rentals = new ArrayList<>();
        FileManager.createDailyLog(this);
    }

    public void addRental(Rental rental)
    {
        rentals.add(rental);
    }

    public String getDailyLogDateInFriendlyString()
    {
        return date.getFriendlyDateString();
    }

    private void setDate(Date date)
    {
        this.date = date;
    }

    public Date getDate() { return date; }
}
