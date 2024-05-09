package me.venom.superrant.interfaces;

public interface IRentalStatistics
{
    void registerNewRent();
    void registerNewUserReview(float newReview);
    void registerNewCriticsReview(float newReview);
    int getTimesRented();
    float getUserReview();
    float getCriticsReview();
}
