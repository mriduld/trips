package com.mridul.trips.rest.client;

import com.mridul.trips.rest.client.model.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientFactory {
    private final RestTemplate restTemplate;

    @Autowired
    public ClientFactory(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TripClient getTripClient(String host, Integer port, Version version){
        return new TripClient(host, port, version, restTemplate);
    }

    public CacheClient getCacheClient(String host, Integer port, Version version){
        return new CacheClient(host, port, version, restTemplate);
    }
}
