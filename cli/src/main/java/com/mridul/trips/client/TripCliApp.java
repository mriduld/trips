package com.mridul.trips.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

@SpringBootApplication(scanBasePackages = {"com.mridul.trips.client", "com.mridul.trips.rest.client"})
public class TripCliApp implements CommandLineRunner, ExitCodeGenerator {

    private final IFactory factory;
    private final TripCommand tripCommand;
    private int exitCode;

    @Autowired
    public TripCliApp(IFactory factory, TripCommand tripCommand) {
        this.factory = factory;
        this.tripCommand = tripCommand;
    }

    @Override
    public void run(String... args) throws Exception {
        new CommandLine(tripCommand, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TripCliApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        System.exit(SpringApplication.exit(app.run(args)));
    }
}
