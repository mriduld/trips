package com.mridul.trips.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com.mridul.trips")
public class TripRestApp {
    public static void main(String[] args) {
        SpringApplication.run(TripRestApp.class, args);
    }
}