package com.mridul.trips.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Repository
public class TripDaoImpl implements TripDao {
    private static final Logger log = LoggerFactory.getLogger(TripDaoImpl.class);

    private final NamedParameterJdbcTemplate template;
    private final ZoneId dbTimeZone;

    @Autowired
    public TripDaoImpl(NamedParameterJdbcTemplate template, ZoneId dbTimeZone) {
        this.template = template;
        this.dbTimeZone = dbTimeZone;
    }

    @Override
    public Integer getTripCountByDateRange(String medallion, LocalDateTime from, LocalDateTime to) {
        log.debug("Getting count for {} from {} to {}", medallion, from, to);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("medallion", medallion);
        params.addValue("from", toTimestamp(from));
        params.addValue("to", toTimestamp(to));
        String query = "SELECT count(*) AS trips FROM trips.cab_trip_data WHERE medallion = :medallion AND pickup_datetime BETWEEN :from and :to";
        return template.queryForObject(query, params, Integer.class);
    }

    private Timestamp toTimestamp(LocalDateTime date){
        return new Timestamp(date.atZone(dbTimeZone).toInstant().toEpochMilli());
    }
}
