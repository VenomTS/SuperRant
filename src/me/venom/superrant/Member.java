package me.venom.superrant;

import me.venom.superrant.implementations.media.Movie;
import me.venom.superrant.implementations.types.Promotional;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Member
{
    private String name, id;

    private final ArrayList<Rental> rentals, itemsInPossession;

    private ArrayList<Item> itemsInCart;
    private Store currStore;

    public Member(String id)
    {
        this.id = id;
        rentals = new ArrayList<>();
        itemsInPossession = new ArrayList<>();
        loadMemberData();
    }

    public void enterStore(Store store)
    {
        if(store == null) throw new RuntimeException("Person entered null store...");
        currStore = store;
    }

    public String getID() { return id; }

    public void exitStore() { currStore = null; }

    public void viewItemsInCurrentStore()
    {
        if(currStore == null) return;
        //store.displayItems();
    }
    public void putItemInCart(Item item)
    {
        if(currStore == null || !currStore.containsItem(item)) return;
        currStore.removeItemFromShelf(item);
    }
    public void removeItemFromCart(Item item)
    {
        if(currStore == null || !itemsInCart.contains(item)) return;
        itemsInCart.remove(item);
        currStore.putItemOnShelf(item);
    }

    public void returnItem()
    {
    }

    public void rentItemsFromCart()
    {
        RentalInfo rentalInfo;
        Rental rental = new Rental();
        for(Item item : itemsInCart)
        {
            rentalInfo = new RentalInfo(item);
            rental.addRentalInfo(rentalInfo);
        }
        rental.recordRental(this);
        rentals.add(rental);
    }

    public void loadMemberData()
    {
        try
        {
            String path = "RequiredFiles/Members/" + id + ".yml";
            InputStream inputStream = new FileInputStream(path);
            Yaml storeFile = new Yaml();
            Map<String, Object> data = storeFile.load(inputStream);
            this.name = (String) data.get("Name");

            ArrayList listOfRentals = (ArrayList) data.get("Rentals");
            for(Object obj : listOfRentals)
            {
                rentals.add(getRentalWithID((int) obj));
            }
        }
        catch(FileNotFoundException exception)
        {
            System.out.println("File " + id + ".yml could not be found!");
        }
    }

    public Rental getRentalWithID(int rentalID)
    {
        Rental rental = new Rental();
        try
        {
            String path = "RequiredFiles/Rentals/" + rentalID + ".yml";
            InputStream inputStream = new FileInputStream(path);
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            HashMap currMap;
            RentalInfo rentalInfo;
            String dueDate, dateReturned;
            int serialNumber;
            for(int i = 1; i <= data.size(); i++)
            {
                currMap = (HashMap) data.get(i);
                dueDate = (String) currMap.get("DueDate");
                dateReturned = (String) currMap.get("DateReturned");
                serialNumber = (int) currMap.get("ItemSerialNumber");
                rentalInfo = new RentalInfo(dueDate, dateReturned, serialNumber);
                rental.addRentalInfo(rentalInfo);
            }
        }
        catch(FileNotFoundException exception)
        {
            System.out.println("Rental file with ID " + rentalID + " could not be found!");
        }
        return rental;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(name).append(" | ID: ").append(id).append(" | Rentals: \n");
        for(Rental rental : rentals)
        {
            builder.append("\n").append(rental);
        }
        return builder.toString();
    }
}
