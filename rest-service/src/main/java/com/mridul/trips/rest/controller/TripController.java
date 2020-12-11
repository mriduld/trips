package com.mridul.trips.rest.controller;

import com.google.common.collect.Sets;
import com.mridul.trips.rest.cache.TripCountCache;
import com.mridul.trips.rest.model.TripCount;
import com.mridul.trips.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class TripController {
    private static final Logger log = LoggerFactory.getLogger(TripController.class);

    private final TripService tripService;

    private final TripCountCache cache;

    @Autowired
    public TripController(TripService tripService, TripCountCache cache) {
        this.tripService = tripService;
        this.cache = cache;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/trips/counts")
    public List<TripCount> tripCountsByDate(
      @RequestParam("medallions") Collection<String> medallions
    , @RequestParam("pickupDate")
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pickupDate
    , @RequestParam("ignoreCache") Boolean ignoreCache) {
        return Sets.newHashSet(medallions)
           .stream()
           .map(medallion -> getTripCount(medallion, pickupDate, ignoreCache))
           .collect(Collectors.toList());
    }

    TripCount getTripCount(String medallion, LocalDate pickUpDate, Boolean ignoreCache){
        Integer count = ignoreCache ? tripService.getTripCount(medallion, pickUpDate) : cache.get(medallion, pickUpDate);
        return new TripCount(medallion, pickUpDate, count);
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, value = "/caches/{cacheName}")
    public ResponseEntity<Void> invalidate(@PathVariable("cacheName") String cacheName) {
        log.info("Invalidating cache ...... {}", cacheName);
        if (cacheName.equals("tripCounts")){
            cache.invalidate();
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
