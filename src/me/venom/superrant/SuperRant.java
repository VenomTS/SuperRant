package me.venom.superrant;

import me.venom.superrant.utilities.Date;
import me.venom.superrant.utilities.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

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
        loadAllDailyLogs();
        if(!doesTodaysLogExist())
        {
            dailyLogs.add(new DailyLog());
        }
    }

    public ArrayList<Store> getStores() { return stores; }
    public ArrayList<Member> getMembers() { return members; }

    private boolean doesTodaysLogExist()
    {
        Date today = new Date();
        for(DailyLog log : dailyLogs)
        {
            if(log.getDate().equals(today)) return true;
        }
        return false;
    }

    public ArrayList<Rental> getAllRentalsFromToday(int index)
    {
        if(index < 0 || index >= dailyLogs.size()) return new ArrayList<>();
        DailyLog log = dailyLogs.get(index);
        return log.getRentals();
    }

    public Member getRandomMember()
    {
        Random random = new Random();
        int selected = random.nextInt(members.size());
        return members.get(selected);
    }

    public Store getRandomStore()
    {
        Random random = new Random();
        int selected = random.nextInt(stores.size());
        return stores.get(selected);
    }

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

    public void notifyReturn(Item item, Store store)
    {
        System.out.println("SUPERRANT NOTIFICATION");
        System.out.println("Item " + item + " has been returned to " + store.getName());
    }
}
