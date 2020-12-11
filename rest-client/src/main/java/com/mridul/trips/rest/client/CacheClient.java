package com.mridul.trips.rest.client;

import com.mridul.trips.rest.client.exception.CacheNotFoundException;
import com.mridul.trips.rest.client.model.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class CacheClient extends AbstractClient {
    private static final Logger log = LoggerFactory.getLogger(TripClient.class);

    @Autowired
    public CacheClient(String host, Integer port, Version version, RestTemplate restTemplate) {
        super(host, port, version, restTemplate);
    }

    public void invalidate(String cacheName) throws CacheNotFoundException {
        URI uri = uriBuilder()
                .pathSegment("caches", cacheName)
                .build()
                .toUri();
        log.info("Uri ====> {}", uri);
        try {
            restTemplate.delete(uri);
        } catch (HttpClientErrorException.NotFound e){
            throw new CacheNotFoundException();
        }
    }
}
