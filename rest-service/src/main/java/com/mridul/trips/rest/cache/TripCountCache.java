package com.mridul.trips.rest.cache;

import java.time.LocalDate;

public interface TripCountCache {
    Integer get(String medallion, LocalDate pickupDate);

    void invalidate();
}
