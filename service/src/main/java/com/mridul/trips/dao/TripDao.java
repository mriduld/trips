package com.mridul.trips.dao;

import java.time.LocalDateTime;

public interface TripDao {
    Integer getTripCountByDateRange(String medallion, LocalDateTime from, LocalDateTime to);
}
