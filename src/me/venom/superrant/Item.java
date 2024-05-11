package me.venom.superrant;

import me.venom.superrant.implementations.media.Media;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class Item
{
    private int serialNumber;
    private Media media;

    public Item(int serialNumber, Media media)
    {
        setSerialNumber(serialNumber);
        setMedia(media);
    }

    public int getLengthOfRentInDays()
    {
        return media.getRentDuration();
    }

    public int getSerialNumber() { return serialNumber; }

    private void setSerialNumber(int serialNumber)
    {
        if(serialNumber <= 0) serialNumber = 1;
        this.serialNumber = serialNumber;
    }

    public double getOverdueFee()
    {
        return media.getOverdueFee();
    }

    public double getPrice()
    {
        return media.getRentalPrice();
    }

    private void setMedia(Media media)
    {
        if(media == null) return;
        this.media = media;
    }

    public Media getMedia() { return media; }

    @Override
    public boolean equals(Object object)
    {
        if(object == this) return true;
        if(object == null || getClass() != object.getClass()) return false;
        Item item = (Item) object;
        return serialNumber == item.serialNumber; // Since serial numbers SHOULD be unique, it is enough to compare them
    }

    public String toString()
    {
        return "Serial Number of Item: " + serialNumber + " | " + media;
    }
}
