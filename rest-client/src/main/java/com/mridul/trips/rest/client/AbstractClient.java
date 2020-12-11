package com.mridul.trips.rest.client;

import com.mridul.trips.rest.client.model.Version;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class AbstractClient {
    protected final String host;
    protected final Integer port;
    protected final Version version;
    protected final RestTemplate restTemplate;

    public AbstractClient(String host, Integer port, Version version, RestTemplate restTemplate) {
        this.host = host;
        this.port = port;
        this.version = version;
        this.restTemplate = restTemplate;
    }

    protected UriComponentsBuilder uriBuilder(){
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(host)
                .port(port)
                .path(version.name());
    }
}
