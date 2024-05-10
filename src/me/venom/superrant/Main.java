package me.venom.superrant;

import me.venom.superrant.implementations.media.Movie;
import me.venom.superrant.implementations.types.NewRelease;
import me.venom.superrant.implementations.types.Promotional;

public class Main
{
    public static void main(String[] args)
    {
        //SuperRant superRant = new SuperRant("SuperRant");
        Member member = new Member("230302275");
        member.rentItemsFromCart();
        /*Store store = new Store("Konzum", null);
        for(Item item : store.getItemsInStore())
        {
            System.out.println(item);
        }*/
    }
}
