package com.mridul.trips.rest.client;

import com.mridul.trips.rest.client.model.TripCount;
import com.mridul.trips.rest.client.model.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

public class TripClient extends AbstractClient {
    private static final Logger log = LoggerFactory.getLogger(TripClient.class);

    @Autowired
    public TripClient(String host, Integer port, Version version, RestTemplate restTemplate) {
        super(host, port, version, restTemplate);
    }

    public List<TripCount> getTripCount(List<String> medallions, LocalDate pickUpdate, Boolean ignoreCache){
        URI uri = uriBuilder()
                .pathSegment("trips", "counts")
                .queryParam("pickupDate", pickUpdate)
                .queryParam("medallions", medallions)
                .queryParam("ignoreCache", ignoreCache)
                .build()
                .toUri();
        log.info("Uri ====> {}", uri);
        return  restTemplate.exchange(
                RequestEntity.get(uri).build()
                , new ParameterizedTypeReference<List<TripCount>>() {})
                .getBody();
    }
}
