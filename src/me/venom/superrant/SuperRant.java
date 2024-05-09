package me.venom.superrant;

import java.util.ArrayList;

public class SuperRant
{
    private String name, location, phone, manager;
    private ArrayList<DailyLog> dailyLogs;
    private ArrayList<Store> stores;
    private ArrayList<Member> members;

    public SuperRant(String name)
    {
        dailyLogs = new ArrayList<>();
        stores = new ArrayList<>();
        members = new ArrayList<>();
    }

}
