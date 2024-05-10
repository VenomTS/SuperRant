package me.venom.superrant;

import me.venom.superrant.utilities.FileManager;
import me.venom.superrant.utilities.UtilityClass;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class Store
{
    private String name, location, phone, manager;
    private SuperRant superRant;

    private Catalog storeCatalog;

    private final ArrayList<Item> itemsInStore;

    public Store(String name, SuperRant superRant)
    {
        //if(superRant == null) throw new RuntimeException("Every store has to be part of chain of stores under supervision of SuperRant store!");
        setName(name);
        setParentStore(superRant);
        setCatalog();
        itemsInStore = new ArrayList<>();
        loadItemsFromFile();
    }

    public ArrayList<Item> getItemsInStore()
    {
        return itemsInStore;
    }

    private void setName(String name)
    {
        if(name == null || name.isEmpty()) name = "Unnamed store";
        this.name = name;
    }

    private void setCatalog()
    {
        storeCatalog = new Catalog(name);
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

    private void setParentStore(SuperRant superRant)
    {
        this.superRant = superRant;
    }private ArrayList getItemIDs()
{
    try
    {
        String path = "RequiredFiles/Stores/" + name + ".yml";
        InputStream inputStream = new FileInputStream(path);
        Yaml storeFile = new Yaml();
        Map<String, Object> data = storeFile.load(inputStream);

        return (ArrayList) data.get("Items");
    }
    catch(FileNotFoundException exception)
    {
        System.out.println("File " + name + ".yml could not be found!");
    }
    return null;
}

    private void loadItemFromID(Object itemID)
    {
        Item item = FileManager.getItemFromFile((int) itemID);
        itemsInStore.add(item);
        storeCatalog.addMedia(item.getMedia());
    }

    private void loadItemsFromFile()
    {
        ArrayList array = getItemIDs();
        if(array == null) throw new RuntimeException("There was an error while trying to load items!");
        try
        {
            for(Object itemID : array)
            {
                if(UtilityClass.isItemAlreadyInDifferentStore((int) itemID, this, superRant))
                {
                    System.out.println("Found the same item in two stores! It is going to be skipped for " + name + "\n" +
                            "Serial Number: " + itemID);
                    continue;
                }
                loadItemFromID(itemID);
            }
        }
        catch(Exception e)
        {
            // I know this is not the most constructive method, but since I am the one doing calls, I will allow myself to do this
            System.out.println("Something went wrong - Could not load items from file!");
        }
    }

    public void putItemOnShelf(Item item)
    {
        itemsInStore.add(item);
    }
    public void removeItemFromShelf(Item item)
    {
        itemsInStore.remove(item);
    }
}
