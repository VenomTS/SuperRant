package me.venom.superrant;

import me.venom.superrant.utilities.Date;
import me.venom.superrant.utilities.FileManager;
import me.venom.superrant.utilities.UtilityClass;

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
        int[] dueDateInt = UtilityClass.getDayMonthYearFromString(dueDate);
        int[] dateReturnedInt = UtilityClass.getDayMonthYearFromString(dateReturned);
        this.dueDate = new Date(dueDateInt[0], dueDateInt[1], dueDateInt[2]);
        if(dateReturnedInt[2] == 0) this.dateReturned = null;
        else this.dateReturned = new Date(dateReturnedInt[0], dateReturnedInt[1], dateReturnedInt[2]);
        this.rentedItem = FileManager.getItemFromFile(itemSerialNumber);
    }

    public double getOverdueFee()
    {
        if(dateReturned == null) return 0; // Person has still not returned the item, thus we don't know their overdue fees!
        int daysOverdue = UtilityClass.getDifferenceBetweenTwoDates(dueDate, dateReturned);
        return daysOverdue * rentedItem.getOverdueFee();
    }

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
