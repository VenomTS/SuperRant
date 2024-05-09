package me.venom.superrant.implementations.types;

import me.venom.superrant.interfaces.IMediaType;

public class Promotional implements IMediaType
{

    private static final int rentDuration = 3;

    @Override
    public int getRentDurationInDays() { return rentDuration; }
}
