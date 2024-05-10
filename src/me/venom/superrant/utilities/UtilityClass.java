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
}
