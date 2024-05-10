package me.venom.superrant;

import me.venom.superrant.utilities.Date;
import me.venom.superrant.utilities.UtilityClass;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

public class Rental
{
    private Date rentDate;

    private final ArrayList<RentalInfo> rentalInfos;

    public Rental()
    {
        setDate(new Date());
        rentalInfos = new ArrayList<>();
    }

    public void recordRental(Member whoRents)
    {
        String path = "RequiredFiles/Rentals/";
        int largestFileNumber = UtilityClass.findLargestNumberFileInFolder(path);
        File file = new File(path + largestFileNumber + ".yml");
        if(file.exists()) throw new RuntimeException("An error occurred, multiple files with same names / bad order!");
        try
        {
            if(!file.createNewFile()) throw new RuntimeException("An error occurred, multiple files with same names / bad order!");
            int count = 1;
            StringBuilder stringBuilder = new StringBuilder();
            for(RentalInfo info : rentalInfos)
            {
                stringBuilder.append(count).append(":").append("\n  DueDate: ").
                        append(info.getDueDateString()).append("\n  DateReturned: ").
                        append(info.getDateReturnedString()).append("\n  ItemSerialNumber: ").append(info.getItemSerialNumber()).append("\n");
                count += 1;
            }
            try(Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file.getPath()), StandardCharsets.UTF_8)
            ))
            {
                writer.write(stringBuilder.toString());
            }
        }
        catch(IOException e)
        {
            System.out.println("Could not create new rental file - Check admin privileges!");
        }
        try
        {
            path = "RequiredFiles/Members/" + whoRents.getID() + ".yml";
            InputStream inputStream = new FileInputStream(path);
            Yaml storeFile = new Yaml();
            Map<String, Object> data = storeFile.load(inputStream);
            ArrayList array = (ArrayList) data.get("Rentals");
            array.add(largestFileNumber);
            data.put("Rentals", array);
            FileWriter writer = new FileWriter(path);
            storeFile.dump(data, writer);
        }
        catch (Exception e)
        {
            System.out.println("Error while adding rentals to Member");
        }
    }

    public void addRentalInfo(RentalInfo rentalInfo)
    {
        if(rentalInfo == null) return;
        rentalInfos.add(rentalInfo);
    }

    public ArrayList<RentalInfo> getAllRentalInfo() { return rentalInfos; }

    public Date getDate() { return rentDate; }

    private void setDate(Date date)
    {
        this.rentDate = date;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Rent Date: ").append(rentDate.getFriendlyDateString()).append(" | Rental Infos: ");
        for(RentalInfo rentalInfo : rentalInfos)
        {
            builder.append("\n").append(rentalInfo);
        }
        return builder.toString();
    }
}
