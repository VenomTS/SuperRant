package me.venom.superrant.implementations.media;

import me.venom.superrant.interfaces.IMediaType;

public class Game extends Media
{

    private String name, description;
    private int rentalCode, timesRented;
    private float userReviewGrade, criticsReviewGrade;
    private IMediaType mediaType;

    public Game(String name, int rentalCode, IMediaType mediaType)
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
    public void registerNewRent()
    {

    }

    @Override
    public void registerNewUserReview(float newReview)
    {

    }

    @Override
    public void registerNewCriticsReview(float newReview)
    {

    }

    @Override
    public int getTimesRented()
    {
        return 0;
    }

    @Override
    public float getUserReview()
    {
        return 0;
    }

    @Override
    public float getCriticsReview()
    {
        return 0;
    }

    private void setName(String name)
    {
        if(name == null || name.isEmpty()) name = "Unnamed";
        this.name = name;
    }

    private void set
}
