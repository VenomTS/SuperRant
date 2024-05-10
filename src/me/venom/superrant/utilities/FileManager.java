package me.venom.superrant.utilities;

import me.venom.superrant.Item;
import me.venom.superrant.implementations.media.Media;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public final class FileManager
{

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

    private static FileInputStream getFileInputStream(String path)
    {
        try { return new FileInputStream(path); }
        catch (FileNotFoundException e) { System.out.println("Tried accessing non-existant file!\nFile Path: " + path); return null; }
    }
}
