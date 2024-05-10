package me.venom.superrant;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class SuperRant
{
    private String name, location, phone, manager;
    private final ArrayList<DailyLog> dailyLogs;
    private final ArrayList<Store> stores;
    private final ArrayList<Member> members;

    public SuperRant(String name)
    {
        dailyLogs = new ArrayList<>();
        stores = new ArrayList<>();
        members = new ArrayList<>();
        loadAllStores();
        loadAllMembers();
    }

    public ArrayList<Store> getStores() { return stores; }

    private void loadAllStores()
    {
        String filePath = "RequiredFiles/Stores/";
        File storesFile = new File(filePath);
        File[] allFiles = storesFile.listFiles();
        if(allFiles == null) return;
        for(File file : allFiles)
        {
            stores.add(new Store(file.getName().replace(".yml", ""), this));
        }
    }

    private void loadAllMembers()
    {
        String filePath = "RequiredFiles/Members/";
        File storesFile = new File(filePath);
        File[] allFiles = storesFile.listFiles();
        if(allFiles == null) return;
        for(File file : allFiles)
        {
            members.add(new Member(file.getName().replace(".yml", "")));
        }
    }

    private void loadAllDailyLogs()
    {
        String filePath = "RequiredFiles/DailyLogs/";
        File storesFile = new File(filePath);
        File[] allFiles = storesFile.listFiles();
        if(allFiles == null) return;
        for(File file : allFiles)
        {
            dailyLogs.add(new DailyLog(file.getName().replace(".yml", "")));
        }
    }
}
