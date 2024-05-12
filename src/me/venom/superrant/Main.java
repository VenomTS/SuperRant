package me.venom.superrant;

import me.venom.superrant.utilities.FileManager;

public class Main
{
    public static void main(String[] args)
    {
        SuperRant superRant = new SuperRant("SuperRant");

        //buyItems(superRant);

        //returnItem(superRant);
    }

    private static void buyItems(SuperRant superRant)
    {
        Member member = superRant.getRandomMember();
        Store konzum = superRant.getStores().get(0);

        Item firstItem = konzum.getItemsInStore().get(0);
        Item secondItem = konzum.getItemsInStore().get(1);

        // Customer enters the store
        // First thing that customer does is goes to the cashier and gives their card to be scanned (we do not want any non-members walking around our stores do we?)
        // After scanning the card, system displays if there were any loans and if they were paid or not
        member.enterStore(konzum);

        // Customer picks items to buy
        member.putItemInCart(firstItem);
        member.putItemInCart(secondItem);

        // Customer comes to the cashier and tries to rent items
        // Cashier scans all the items
        // In case customer does not have money, sales assistant will stop scanning
        // This part is responsible for scanning items and receiving money from customer
        // After handling payments, FileManager is called, and it manages the inventory, daily log and everything else
        member.rentItemsFromCart();

        // Customer finally exists the store
        member.exitStore();
    }

    private static void returnItem(SuperRant superRant)
    {
        Item firstItem = FileManager.getItemFromFile(1);
        Store merkator = superRant.getStores().get(1);
        Member member = superRant.getRandomMember();
        member.enterStore(merkator);
        member.returnItem(firstItem);
    }
}
