package me.venom.superrant;

import me.venom.superrant.implementations.media.Media;
import me.venom.superrant.utilities.Date;
import me.venom.superrant.utilities.FileManager;

import java.util.ArrayList;

public class Store
{
    private String name, location, phone, manager;
    private SuperRant superRant;

    private Catalog storeCatalog;

    private final ArrayList<Item> itemsInStore;

    public Store(String name, SuperRant superRant)
    {
        if(superRant == null) throw new RuntimeException("Every store has to be part of chain of stores under supervision of SuperRant store!");
        this.name = name;
        this.superRant = superRant;
        this.storeCatalog = new Catalog(name);
        itemsInStore = new ArrayList<>();
        FileManager.loadStoreItemsFromFile(this);
    }

    public ArrayList<Item> getItemsInStore()
    {
        return itemsInStore;
    }

    public void processReturn(Item item)
    {
        superRant.notifyReturn(item, this);
    }

    public void scanMemberCard(Member member)
    {
        System.out.println("Member Info: \n" + member);
        double overdueFees = 0;
        for(Rental rental : member.getAllRentals())
        {
            overdueFees += rental.getOverdueFees();
        }
        if(!member.doesUserHaveEnoughMoney(overdueFees))
        {
            System.out.println("Member cannot use store services because overdue fees are higher than his balance!");
            member.exitStore();
            return;
        }
        if(overdueFees == 0)
        {
            System.out.println("Customer does not have any overdue fees!");
            return;
        }
        System.out.println("Customer has had a loan of " + overdueFees + "$ which he has just paid");
        member.payAmount(overdueFees);
    }
    public Rental scanItems(Member member, ArrayList<Item> items)
    {
        double price = 0;
        RentalInfo rentalInfo;
        Rental rental = new Rental();
        for(Item item : items)
        {
            price += item.getPrice();
            if(!member.doesUserHaveEnoughMoney(price))
            {
                System.out.println("The user does not have enough money to rent all selected items\nRest of the items will not be scanned");
                price -= item.getPrice();
                break;
            }
            FileManager.transferItemFromStoreToMember(member, this, item);
            rentalInfo = new RentalInfo(item);
            rental.addRentalInfo(rentalInfo);
            member.addItemToPossession(item);
        }
        member.payAmount(price);
        if(price == 0) return null;
        System.out.println("Receipt: " + rental);
        return rental;
    }

    public String getName()
    {
        return this.name;
    }

    public void addMediaToCatalog(Media media)
    {
        storeCatalog.addMedia(media);
    }

    public boolean containsItem(int serialNumber)
    {
        for(Item item : itemsInStore)
        {
            if(item.getSerialNumber() == serialNumber) return true;
        }
        return false;
    }

    public boolean containsItem(Item item)
    {
        return itemsInStore.contains(item);
    }

    public SuperRant getParentStore() { return superRant; }

    public void putItemOnShelf(Item item)
    {
        itemsInStore.add(item);
    }
    public void removeItemFromShelf(Item item)
    {
        itemsInStore.remove(item);
    }
}
