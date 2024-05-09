package me.venom.superrant.implementations.media;

import me.venom.superrant.implementations.types.Normal;
import me.venom.superrant.interfaces.IMediaType;

public class Movie extends Media
{
    private final float mediaRentPrice = 2.50f;
    private final float mediaOverdueFee = 1.25f;

    private String name, description;
    private int rentalCode, timesRented;
    private int numberOfUserReviews, numberOfCriticReviews;
    private float userReviewGrade, criticsReviewGrade;
    private IMediaType mediaType;

    public Movie(String name, int rentalCode, IMediaType mediaType)
    {
        setName(name);
        setRentalCode(rentalCode);
        setMediaType(mediaType);

        description = "No Description";
        timesRented = 0;
        userReviewGrade = criticsReviewGrade = 0.0f;
    }

    @Override
    public String getName() { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public int getRentalCode() { return rentalCode; }

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
        return userReviewGrade / numberOfUserReviews;
    }

    @Override
    public float getCriticsReview()
    {
        return criticsReviewGrade / numberOfCriticReviews;
    }

    @Override
    public float getRentalPrice() { return mediaRentPrice; }

    @Override
    public float getOverdueFee() { return mediaOverdueFee; }

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
