package com.mridul.trips.service;

import java.time.LocalDate;

public interface TripService {
    Integer getTripCount(String medallion, LocalDate date);
}
