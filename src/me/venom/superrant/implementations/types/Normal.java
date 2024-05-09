package me.venom.superrant.implementations.types;

import me.venom.superrant.interfaces.IMediaType;

public class Normal implements IMediaType
{

    private static final int rentDuration = 7;

    @Override
    public int getRentDurationInDays() { return rentDuration; }
}
