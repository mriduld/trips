package com.mridul.trips.rest.client.model;

import java.time.LocalDate;

public class TripCount {
    private final String medallion;
    private final LocalDate pickUpDate;
    private final Integer trips;

    public TripCount(String medallion, LocalDate pickUpDate, Integer trips) {
        this.medallion = medallion;
        this.pickUpDate = pickUpDate;
        this.trips = trips;
    }


    public String getMedallion() {
        return medallion;
    }

    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public Integer getTrips() {
        return trips;
    }

    @Override
    public String toString() {
        return "TripCount{" +
                "medallion='" + medallion + '\'' +
                ", pickUpDate=" + pickUpDate +
                ", trips=" + trips +
                '}';
    }
}
