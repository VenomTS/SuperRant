package me.venom.superrant.implementations.types;

import me.venom.superrant.interfaces.IMediaType;

public class NewRelease implements IMediaType
{

    private static final int rentDuration = 5;

    @Override
    public int getRentDurationInDays() { return rentDuration; }
}
