package me.venom.superrant.utilities;

import me.venom.superrant.Store;
import me.venom.superrant.SuperRant;

import java.io.File;

public final class UtilityClass
{
    public static boolean isItemAlreadyInDifferentStore(int serialNumber, Store currStore, SuperRant superRant)
    {
        for(Store store : superRant.getStores())
        {
            if(store == currStore) continue;
            if(store.containsItem(serialNumber)) return true;
        }
        return false;
    }

    public static int findLargestNumberFileInFolder(String path)
    {
        File file = new File(path);
        File[] listOfFiles = file.listFiles();
        if(listOfFiles == null) return 1;
        return listOfFiles.length + 1;
    }

    public static int[] getDayMonthYearFromString(String str)
    {
        String[] newStr = str.split("-");
        int day, month, year;
        day = Integer.parseInt(newStr[0]);
        month = Integer.parseInt(newStr[1]);
        year = Integer.parseInt(newStr[2]);
        return new int[] {day, month, year};
    }

    public static int getDifferenceBetweenTwoDates(Date dueDate, Date today)
    {
        int count = 0;
        while(!dueDate.equals(today)) // Fastest way I thought of...
        {
            count += 1;
            dueDate.addDays(1);
        }
        return count;
    }
}
