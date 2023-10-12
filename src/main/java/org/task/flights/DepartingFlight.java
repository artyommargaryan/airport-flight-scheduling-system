package org.task.flights;

import java.time.LocalDateTime;

public class DepartingFlight extends Flight {
    public DepartingFlight(String flightNumber, String source, String destination, LocalDateTime flightTime,
                           LocalDateTime arrivalTime) {
        super(flightNumber, source, destination, flightTime, arrivalTime);

    }
}
