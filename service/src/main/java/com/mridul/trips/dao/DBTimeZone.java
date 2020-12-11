package com.mridul.trips.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;

@Configuration
public class DBTimeZone {
    @Bean
    public ZoneId getDBTimeZone(@Value( "${db.timeZone}" ) String zoneId){
        return ZoneId.of(zoneId);
    }
}
