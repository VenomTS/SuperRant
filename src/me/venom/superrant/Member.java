package me.venom.superrant;

import me.venom.superrant.interfaces.IPaymentMethod;
import me.venom.superrant.utilities.FileManager;

import java.util.ArrayList;

public class Member
{
    private String name;
    private final String id;

    // The idea is that user has to set up paymentMethod that he wants to use and cannot switch from one to another without notifying the store...
    private IPaymentMethod paymentMethod;

    private final ArrayList<Rental> rentals;

    private final ArrayList<Item> itemsInCart, itemsInPossession;
    private Store currStore;

    public Member(String id)
    {
        this.id = id;
        rentals = new ArrayList<>();
        itemsInPossession = new ArrayList<>();
        itemsInCart = new ArrayList<>();
        FileManager.loadMemberData(this);
    }

    public void enterStore(Store store)
    {
        if(store == null) throw new RuntimeException("Person entered null store...");
        currStore = store;
        currStore.scanMemberCard(this);
    }
    public void exitStore()
    {
        if(currStore == null) return;
        for(Item item : itemsInCart)
        {
            currStore.putItemOnShelf(item);
        }
        itemsInCart.clear();
        currStore = null;
    }

    public String getID() { return id; }
    public ArrayList<Rental> getAllRentals() { return rentals; }
    public ArrayList<Item> getItemsInCart() { return itemsInCart; }
    public void setName(String name) { this.name = name; }

    public void setPaymentMethod(IPaymentMethod method)
    {
        if(method == null) return;
        paymentMethod = method;
    }

    public boolean doesUserHaveEnoughMoney(double amount)
    {
        return paymentMethod.hasEnoughBalance(amount);
    }

    public void putMoneyIntoPaymentMethod(double amount)
    {
        if(amount <= 0) return;
        paymentMethod.deposit(amount);
    }

    public void addRental(Rental rental) { rentals.add(rental); }
    public void addItemToPossession(Item item) { itemsInPossession.add(item); }

    public void payAmount(double amount)
    {
        if(amount > paymentMethod.getBalance()) return;
        paymentMethod.withdraw(amount);
        FileManager.updateBalance(this, -amount);
    }
    public void putItemInCart(Item item)
    {
        if(currStore == null || !currStore.containsItem(item)) return;
        itemsInCart.add(item);
        currStore.removeItemFromShelf(item);
    }
    public void removeItemFromCart(Item item)
    {
        if(currStore == null || !itemsInCart.contains(item)) return;
        itemsInCart.remove(item);
        currStore.putItemOnShelf(item);
    }

    public void returnItem(Item item)
    {
        // Note
        // I did not have enough time to make an efficient solution for how to update rental information about when the item was returned
        // Thus to check for overdue fees, you will have to manually change the date returned to something before today
        // I am sorry for this inconvenience - should've used SQL for this...
        if(currStore == null || !itemsInPossession.contains(item)) return;
        FileManager.transferItemFromMemberToStore(this, currStore, item);
        currStore.processReturn(item);
    }
    public void rentItemsFromCart()
    {
        if(currStore == null || itemsInCart.isEmpty()) return;
        Rental rental = currStore.scanItems(this, itemsInCart);
        if(rental == null) return;
        rental.recordRental(this);
        rentals.add(rental);
    }

    @Override
    public String toString()
    {
        return "Name: " + name + " | ID: " + id;
    }
}
