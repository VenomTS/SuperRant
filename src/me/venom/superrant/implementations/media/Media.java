package me.venom.superrant.implementations.media;

import me.venom.superrant.interfaces.IMediaType;
import me.venom.superrant.interfaces.IRentalPricing;
import me.venom.superrant.interfaces.IRentalStatistics;

public abstract class Media implements IRentalStatistics, IRentalPricing
{
    public abstract String getName();
    public abstract String getDescription();
    public abstract int getRentalCode();
    public abstract IMediaType getMediaType();
}
