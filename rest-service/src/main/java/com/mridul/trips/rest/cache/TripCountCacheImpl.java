package com.mridul.trips.rest.cache;

import com.google.common.base.Objects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mridul.trips.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TripCountCacheImpl implements TripCountCache {
    private static final Logger log = LoggerFactory.getLogger(TripCountCacheImpl.class);

    private final LoadingCache<CacheKey, Integer> cache;

    @Autowired
    public TripCountCacheImpl(TripService tripService){
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(5000)
                .build(
                        new CacheLoader<CacheKey, Integer>() {
                            public Integer load(CacheKey key) {
                                log.info("Medallion: {}, pickupDate: {} not found in cache. Looking up DB", key.medallion, key.pickupDate);
                                return tripService.getTripCount(key.medallion, key.pickupDate);
                            }
                        }
                );
    }

    @Override
    public void invalidate() {
        log.info("Clearing trip count cache");
        cache.invalidateAll();
    }

    public Integer get(String medallion, LocalDate pickupDate){
        return cache.getUnchecked(new CacheKey(medallion, pickupDate));
    }

    private static class CacheKey {
        private final String medallion;
        private final LocalDate pickupDate;

        public CacheKey(String medallion, LocalDate pickupDate) {
            this.medallion = medallion;
            this.pickupDate = pickupDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return Objects.equal(medallion, cacheKey.medallion) && Objects.equal(pickupDate, cacheKey.pickupDate);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(medallion, pickupDate);
        }
    }
}
