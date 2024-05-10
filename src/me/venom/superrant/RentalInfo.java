package me.venom.superrant;

import me.venom.superrant.implementations.media.Movie;
import me.venom.superrant.implementations.types.Normal;
import me.venom.superrant.utilities.Date;
import me.venom.superrant.utilities.FileManager;

import java.util.Arrays;

public class RentalInfo
{
    private Date dueDate;
    private Date dateReturned;
    private Item rentedItem;

    public RentalInfo(Item item)
    {
        setRentedItem(item);
        setDueDate();
        dateReturned = null;
    }

    public RentalInfo(String dueDate, String dateReturned, int itemSerialNumber)
    {
        String[] newDueDate = dueDate.split("-");
        String[] newDateReturned = dateReturned.split("-");
        int day, month, year;
        day = Integer.parseInt(newDueDate[0]);
        month = Integer.parseInt(newDueDate[1]);
        year = Integer.parseInt(newDueDate[2]);
        this.dueDate = new Date(day, month, year);
        day = Integer.parseInt(newDateReturned[0]);
        month = Integer.parseInt(newDateReturned[1]);
        year = Integer.parseInt(newDateReturned[2]);
        if(year == 0) this.dateReturned = null;
        else this.dateReturned = new Date(day, month, year);
        this.rentedItem = FileManager.getItemFromFile(itemSerialNumber);
    }

    public boolean isItemReturned() { return dateReturned != null; }

    public void markItemReturned()
    {
        dateReturned = new Date();
    }

    public Item getRentedItem() { return rentedItem; }
    public int getItemSerialNumber() { return rentedItem.getSerialNumber(); }

    public Date getDueDate() { return dueDate; }

    public String getDueDateString() { return dueDate.getFriendlyDateString(); }
    public String getDateReturnedString()
    {
        if(dateReturned == null) return "00-00-0000";
        return dateReturned.getFriendlyDateString();
    }

    private void setDueDate()
    {
        Date date = new Date();
        date.addDays(rentedItem.getLengthOfRentInDays());
        dueDate = date;
    }

    private void setRentedItem(Item item)
    {
        if(item == null) item = new Item(1, null);
        this.rentedItem = item;
    }

    @Override
    public String toString()
    {
        return getDueDateString() + " | " + getDateReturnedString() + " | " + rentedItem;
    }
}
