package com.mridul.trips.client;

import com.mridul.trips.rest.client.CacheClient;
import com.mridul.trips.rest.client.ClientFactory;
import com.mridul.trips.rest.client.exception.CacheNotFoundException;
import com.mridul.trips.rest.client.model.TripCount;
import com.mridul.trips.rest.client.model.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDate;
import java.util.List;

@Component
@Command(name = "trips", mixinStandardHelpOptions = true,
        subcommands = {TripCountCommand.class, InvalidateCacheCommand.class}
)
public class TripCommand {}

abstract class HttpCommand {
    @Option(names = "--trip-host", required = true, description = "Service host")
    protected String host;

    @Option(names = "--trip-port", required = true, description = "Service port")
    protected Integer port;

    @Option(names = "--trip-version", required = true, description = "Version")
    protected Version version;

    @Autowired
    protected ClientFactory clientFactory;
}

@Component
@Command(name = "count", mixinStandardHelpOptions = true)
class TripCountCommand extends HttpCommand implements Runnable {
    @Option(names = "--date", required = true, description = "trip date")
    private LocalDate date;

    @Option(arity = "1..*", names = "--medallion", required = true, description = "List of medallions")
    private List<String> medallions;

    @Option(arity = "0..1", names = "--ignore-cache", description = "Ignores cache", defaultValue = "false")
    private Boolean ignoreCache;

    @Override
    public void run() {
        List<TripCount> tripCounts = clientFactory
                .getTripClient(host, port, version)
                .getTripCount(medallions, date, ignoreCache);
        for (TripCount tripCount : tripCounts) {
            System.out.printf(
              "[Medallion: %s, PickupDate: %s, Trips: %d]%n"
            , tripCount.getMedallion()
            , tripCount.getPickUpDate()
            , tripCount.getTrips());
        }
    }
}

@Component
@Command(name = "invalidate-cache", mixinStandardHelpOptions = true)
class InvalidateCacheCommand extends HttpCommand implements Runnable {
    @Option(names = "--cache-name", required = true, description = "Name of the cache to invalidate")
    private String cacheName;

    private CacheClient cacheClient(){
        return clientFactory.getCacheClient(host, port, version);
    }

    @Override
    public void run() {
        try {
            cacheClient().invalidate(cacheName);
            System.err.println(cacheName +" invalidated.");
        } catch (CacheNotFoundException e){
            System.err.println("Cache "+ cacheName +" not found.");
        }
    }
}
