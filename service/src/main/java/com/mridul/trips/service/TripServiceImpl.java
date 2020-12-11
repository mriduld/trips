package com.mridul.trips.service;

import com.mridul.trips.dao.TripDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class TripServiceImpl implements TripService {
    private static final Logger log = LoggerFactory.getLogger(TripService.class);

    private final TripDao tripDao;

    @Autowired
    public TripServiceImpl(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    @Override
    public Integer getTripCount(String medallion, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = start.with(LocalTime.MAX);
        return tripDao.getTripCountByDateRange(medallion, start, end);
    }
}
