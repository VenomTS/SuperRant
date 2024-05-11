package me.venom.superrant;

import me.venom.superrant.utilities.Date;
import me.venom.superrant.utilities.FileManager;
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
        FileManager.StoreNewRental(whoRents, this);
    }

    public double getOverdueFees()
    {
        double fee = 0;
        for(RentalInfo rentalInfo : rentalInfos)
        {
            fee += rentalInfo.getOverdueFee();
        }
        return fee;
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
