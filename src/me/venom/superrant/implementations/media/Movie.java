package me.venom.superrant.implementations.media;

import me.venom.superrant.implementations.types.Normal;
import me.venom.superrant.interfaces.IMediaType;

public class Movie extends Media
{
    private final float mediaRentPrice = 3f;
    private final float mediaOverdueFee = 3f;

    private String name, description;
    private int rentalCode, timesRented;
    private int numberOfUserReviews, numberOfCriticReviews;
    private int userReviewGrade, criticsReviewGrade;
    private IMediaType mediaType;

    public Movie(String name, int rentalCode, IMediaType mediaType)
    {
        setName(name);
        setRentalCode(rentalCode);
        setMediaType(mediaType);

        description = "No Description";
        timesRented = 0;
        userReviewGrade = criticsReviewGrade = 0;
    }

    public Movie(String name, String description, String type, int rentalCode, int timesRented, int userReviewCount, int criticsReviewCount, int userReview, int criticsReview) throws Exception
    {
        this.name = name;
        this.description = description;
        this.mediaType = (IMediaType) Class.forName("me.venom.superrant.implementations.types." + type).getDeclaredConstructor().newInstance();
        this.rentalCode = rentalCode;
        this.timesRented = timesRented;
        this.numberOfUserReviews = userReviewCount;
        this.numberOfCriticReviews = criticsReviewCount;
        this.userReviewGrade = userReview;
        this.criticsReviewGrade = criticsReview;
    }

    @Override
    public String getName() { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public int getRentalCode() { return rentalCode; }

    @Override
    public int getRentDuration() { return mediaType.getRentDurationInDays(); }

    @Override
    public IMediaType getMediaType() { return mediaType; }

    @Override
    public void registerNewRent() { timesRented += 1; }

    @Override
    public void registerNewUserReview(float newReview)
    {
        if(newReview < 0 || newReview > 10) newReview = 5;
        userReviewGrade += newReview;
        numberOfUserReviews += 1;
    }

    @Override
    public void registerNewCriticsReview(float newReview)
    {
        if(newReview < 0 || newReview > 10) newReview = 5;
        criticsReviewGrade += newReview;
        numberOfCriticReviews += 1;
    }

    @Override
    public int getTimesRented()
    {
        return timesRented;
    }

    @Override
    public float getUserReview()
    {
        return (userReviewGrade * 1.f) / Math.max(1, numberOfUserReviews);
    }

    @Override
    public float getCriticsReview()
    {
        return (criticsReviewGrade * 1.f) / Math.max(1, numberOfCriticReviews);
    }

    @Override
    public double getRentalPrice() { return mediaRentPrice; }

    @Override
    public double getOverdueFee() { return mediaOverdueFee; }

    /*@Override
    public String toString()
    {
        return "Name: " + name + " | Type: " + mediaType.getType() + " | Genre: Movie | Description: " + description + "\n" +
                "Rental Code: " + rentalCode + " | Media Price: " + mediaRentPrice + "$ | Media Overdue Fee: " + mediaOverdueFee + "$\n" +
                "Rental Statistics: Rented " + timesRented + " times - User Review: " + getUserReview() + " - Critics Review: " + getCriticsReview();
    }*/
    @Override
    public String toString()
    {
        return "Name: " + name + " | Type: " + mediaType.getType() + " | Genre: Movie | Description: " + description;
    }

    private void setName(String name)
    {
        if(name == null || name.isEmpty()) name = "Unnamed";
        this.name = name;
    }

    private void setRentalCode(int rentalCode)
    {
        if(rentalCode < 0) rentalCode = 1;
        this.rentalCode = rentalCode;
    }

    private void setMediaType(IMediaType mediaType)
    {
        if(mediaType == null) mediaType = new Normal();
        this.mediaType = mediaType;
    }
}
