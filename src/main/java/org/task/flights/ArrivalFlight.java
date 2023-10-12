package org.task.flights;

import java.time.LocalDateTime;

public class ArrivalFlight extends Flight {
    public ArrivalFlight(String flightNumber, String source, String destination, LocalDateTime flightTime,
                         LocalDateTime arrivalTime) {
        super(flightNumber, source, destination, flightTime, arrivalTime);

    }
}
