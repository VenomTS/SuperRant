package me.venom.superrant.utilities;

import me.venom.superrant.*;
import me.venom.superrant.implementations.media.Media;
import me.venom.superrant.interfaces.IPaymentMethod;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class FileManager
{

    private FileManager() {}

    private static String path;
    private static InputStream inputStream;

    public static Item getItemFromFile(int serialNumber)
    {
        Yaml itemFile = new Yaml();
        Map<String, Object> data;
        path = "RequiredFiles/Items/" + serialNumber + ".yml";
        inputStream = getFileInputStream(path);
        data = itemFile.load(inputStream);

        String[] mediaData = new String[4];
        int[] intData = new int[6];
        mediaData[0] = (String) data.get("Name");
        mediaData[1] = (String) data.get("Description");
        mediaData[2] = (String) data.get("Genre");
        mediaData[3] = (String) data.get("Type");
        intData[0] = (int) data.get("RentalCode");
        intData[1] = (int) data.get("TimesRented");
        intData[2] = (int) data.get("UserReviewCount");
        intData[3] = (int) data.get("CriticsReviewCount");
        intData[4] = (int) data.get("UserReview");
        intData[5] = (int) data.get("CriticsReview");

        Class[] parameterType = new Class[] { String.class, String.class, String.class, int.class, int.class, int.class, int.class, int.class, int.class };

        Item item = null;
        Media media = null;

        try
        {
            media = (Media) Class.forName("me.venom.superrant.implementations.media." + mediaData[2]).getDeclaredConstructor(parameterType).
                    newInstance(mediaData[0], mediaData[1], mediaData[3], intData[0], intData[1], intData[2], intData[3], intData[4], intData[5]);
            item = new Item(serialNumber, media);
        }
        catch (Exception e)
        {
            System.out.println("Could not create media for a file in getItemFromFile!");
        }
        return item;
    }

    public static void loadMemberData(Member member)
    {
        path = "RequiredFiles/Members/" + member.getID() + ".yml";
        inputStream = getFileInputStream(path);
        Yaml storeFile = new Yaml();
        Map<String, Object> data = storeFile.load(inputStream);
        member.setName((String) data.get("Name"));

        ArrayList listOfRentals = (ArrayList) data.get("Rentals");
        for(Object obj : listOfRentals)
        {
            member.addRental(getRentalUsingID((int) obj));
        }
        ArrayList listOfItemsInPossession = (ArrayList) data.get("ItemsInPossession");
        for(Object obj : listOfItemsInPossession)
        {
            member.addItemToPossession(getItemFromFile((int) obj));
        }

        String paymentMethodString = (String) data.get("PaymentMethod");
        double balance = (double) data.get("Balance");
        IPaymentMethod paymentMethod;
        try
        {
            paymentMethod = (IPaymentMethod) Class.forName("me.venom.superrant.implementations.payments." + paymentMethodString).getDeclaredConstructor().newInstance();
            member.setPaymentMethod(paymentMethod);
            member.putMoneyIntoPaymentMethod(balance);
        }
        catch (Exception e)
        {
            System.out.println("Could not create an instance of Payment Method!");
        }
    }

    public static Rental getRentalUsingID(int rentalID)
    {
        Rental rental = new Rental();
        path = "RequiredFiles/Rentals/" + rentalID + ".yml";
        inputStream = getFileInputStream(path);
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
        return rental;
    }

    public static void loadStoreItemsFromFile(Store store)
    {
        ArrayList array = getStoreItemIDs(store);
        Item item;
        for(Object itemID : array)
        {
            if(UtilityClass.isItemAlreadyInDifferentStore((int) itemID, store, store.getParentStore()))
            {
                removeItemFromStore(store, getItemFromFile((int) itemID));
                continue;
            }
            item = getItemFromFile((int) itemID);
            store.putItemOnShelf(item);
            store.addMediaToCatalog(item.getMedia());
        }
    }

    public static void StoreNewRental(Member member, Rental rental)
    {
        File file = getFirstFreeRentalFile();
        int fileNumber = Integer.parseInt(file.getName().replace(".yml", ""));
        storeNewRentInRentalsFile(file, rental);
        addNewRentalIntoMemberFile(member, fileNumber);
        addNewRentalIntoDailyLogsFile(fileNumber);
    }

    public static void transferItemFromStoreToMember(Member member, Store store, Item item)
    {
        addItemToMember(member, item);
        removeItemFromStore(store, item);
    }

    public static void transferItemFromMemberToStore(Member member, Store store, Item item)
    {
        removeItemFromMember(member, item);
        addItemToStore(store, item);
    }

    public static void fetchRentalsForDailyLog(DailyLog dailyLog)
    {
        path = "RequiredFiles/DailyLogs/" + dailyLog.getDailyLogDateInFriendlyString() + ".yml";
        inputStream = getFileInputStream(path);
        Yaml storeFile = new Yaml();
        Map<String, Object> data = storeFile.load(inputStream);
        ArrayList array = (ArrayList) data.get("Rentals");
        Rental rental;
        for(Object obj : array)
        {
            rental = getRentalUsingID((int) obj);
            dailyLog.addRental(rental);
        }
    }

    public static void createDailyLog(DailyLog dailyLog)
    {
        path = "RequiredFiles/DailyLogs/" + dailyLog.getDailyLogDateInFriendlyString() + ".yml";
        File file = new File(path);
        try(Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file.getPath()), StandardCharsets.UTF_8)
        ))
        {
            writer.write("Rentals: []");
        }
        catch(IOException e) { System.out.println("Could not store a new daily log into a file!"); }
    }

    public static void updateBalance(Member member, double amount)
    {
        path = "RequiredFiles/Members/" + member.getID() + ".yml";
        inputStream = getFileInputStream(path);
        Yaml storeFile = new Yaml();
        Map<String, Object> data = storeFile.load(inputStream);
        double balance = (double) data.get("Balance");
        balance += amount;
        data.put("Balance", balance);
        try
        {
            FileWriter writer = new FileWriter(path);
            storeFile.dump(data, writer);
        }
        catch(IOException e) { System.out.println("Could not add item to member!"); }
    }

    private static void removeItemFromStore(Store store, Item item)
    {
        path = "RequiredFiles/Stores/" + store.getName() + ".yml";
        inputStream = getFileInputStream(path);
        Yaml storeFile = new Yaml();
        Map<String, Object> data = storeFile.load(inputStream);
        ArrayList array = (ArrayList) data.get("Items");
        int index = array.indexOf(item.getSerialNumber());
        array.remove(index);
        data.put("Items", array);
        try
        {
            FileWriter writer = new FileWriter(path);
            storeFile.dump(data, writer);
        }
        catch(IOException e) { System.out.println("Could not remove item from store!"); }
    }
    private static void addItemToStore(Store store, Item item)
    {
        path = "RequiredFiles/Stores/" + store.getName() + ".yml";
        inputStream = getFileInputStream(path);
        Yaml storeFile = new Yaml();
        Map<String, Object> data = storeFile.load(inputStream);
        ArrayList array = (ArrayList) data.get("Items");
        array.add(item.getSerialNumber());
        data.put("Items", array);
        try
        {
            FileWriter writer = new FileWriter(path);
            storeFile.dump(data, writer);
        }
        catch(IOException e) { System.out.println("Could not remove item from store!"); }
    }

    private static void addItemToMember(Member member, Item item)
    {
        path = "RequiredFiles/Members/" + member.getID() + ".yml";
        inputStream = getFileInputStream(path);
        Yaml storeFile = new Yaml();
        Map<String, Object> data = storeFile.load(inputStream);
        ArrayList array = (ArrayList) data.get("ItemsInPossession");
        array.add(item.getSerialNumber());
        data.put("ItemsInPossession", array);
        try
        {
            FileWriter writer = new FileWriter(path);
            storeFile.dump(data, writer);
        }
        catch(IOException e) { System.out.println("Could not add item to member!"); }
    }
    private static void removeItemFromMember(Member member, Item item)
    {
        path = "RequiredFiles/Members/" + member.getID() + ".yml";
        inputStream = getFileInputStream(path);
        Yaml storeFile = new Yaml();
        Map<String, Object> data = storeFile.load(inputStream);
        ArrayList array = (ArrayList) data.get("ItemsInPossession");
        int index = array.indexOf(item.getSerialNumber());
        array.remove(index);
        data.put("ItemsInPossession", array);
        try
        {
            FileWriter writer = new FileWriter(path);
            storeFile.dump(data, writer);
        }
        catch(IOException e) { System.out.println("Could not remove item from member!"); }
    }

    private static void addNewRentalIntoDailyLogsFile(int fileNumber)
    {
        Date date = new Date();
        path = "RequiredFiles/DailyLogs/" + date.getFriendlyDateString() + ".yml";
        inputStream = getFileInputStream(path);
        Yaml storeFile = new Yaml();
        Map<String, Object> data = storeFile.load(inputStream);
        ArrayList array = (ArrayList) data.get("Rentals");
        array.add(fileNumber);
        data.put("Rentals", array);
        try
        {
            FileWriter writer = new FileWriter(path);
            storeFile.dump(data, writer);
        }
        catch(IOException e) { System.out.println("Could not save new rental to the user!"); }
    }

    private static void addNewRentalIntoMemberFile(Member member, int fileNumber)
    {
        path = "RequiredFiles/Members/" + member.getID() + ".yml";
        inputStream = getFileInputStream(path);
        Yaml storeFile = new Yaml();
        Map<String, Object> data = storeFile.load(inputStream);
        ArrayList array = (ArrayList) data.get("Rentals");
        array.add(fileNumber);
        data.put("Rentals", array);
        try
        {
            FileWriter writer = new FileWriter(path);
            storeFile.dump(data, writer);
        }
        catch(IOException e) { System.out.println("Could not save new rental to the user!"); }
    }

    private static void storeNewRentInRentalsFile(File file, Rental rental)
    {
        int count = 1;
        StringBuilder builder = new StringBuilder();
        for(RentalInfo info : rental.getAllRentalInfo())
        {
            builder.append(count).append(":").append("\n  DueDate: ").
                    append(info.getDueDateString()).append("\n  DateReturned: ").
                    append(info.getDateReturnedString()).append("\n  ItemSerialNumber: ").append(info.getItemSerialNumber()).append("\n");
            count += 1;
        }
        // This thing is ugly but trying to rewrite it resulted in unknown errors...
        try(Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                new FileOutputStream(file.getPath()), StandardCharsets.UTF_8)
        ))
        {
            writer.write(builder.toString());
        }
        catch(IOException e) { System.out.println("Could not store a new rental into a file!"); }
    }

    private static File getFirstFreeRentalFile()
    {
        path = "RequiredFiles/Rentals/";
        int largestFreeFileNumber = findLargestNumberFileInFolder(path);
        File file = new File(path + largestFreeFileNumber + ".yml");
        while(file.exists())
        {
            largestFreeFileNumber += 1;
            file = new File(path + largestFreeFileNumber + ".yml");
        }
        return file;
    }

    private static ArrayList getStoreItemIDs(Store store)
    {
        path = "RequiredFiles/Stores/" + store.getName() + ".yml";
        inputStream = getFileInputStream(path);
        Yaml storeFile = new Yaml();
        Map<String, Object> data = storeFile.load(inputStream);
        return (ArrayList) data.get("Items");
    }

    private static FileInputStream getFileInputStream(String path)
    {
        try { return new FileInputStream(path); }
        catch (FileNotFoundException e) { System.out.println("Tried accessing non-existant file!\nFile Path: " + path); return null; }
    }

    private static int findLargestNumberFileInFolder(String path)
    {
        File file = new File(path);
        File[] listOfFiles = file.listFiles();
        if(listOfFiles == null) return 1;
        return listOfFiles.length + 1;
    }
}
