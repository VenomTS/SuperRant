package me.venom.superrant;

import me.venom.superrant.implementations.media.Media;

import java.util.ArrayList;

public class Catalog
{
    private String catalogName;

    private final ArrayList<Media> offeredMedia;

    public Catalog(String catalogName)
    {
        setCatalogName(catalogName);
        offeredMedia = new ArrayList<>();
    }

    private void setCatalogName(String catalogName)
    {
        if(catalogName == null || catalogName.isEmpty()) catalogName = "Unnamed Catalog";
        this.catalogName = catalogName;
    }

    public void addMedia(Media media)
    {
        offeredMedia.add(media);
    }

    public void displayAvailableMedia()
    {
        System.out.println("Store " + catalogName + " offers following media: ");
        for(Media media : offeredMedia)
        {
            System.out.println(media);
        }
    }

    // TODO - Add displayMedia that store has to offer!
}
