package org.task.flights;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Flight {
    private final String flightNumber;
    private final String source;
    private final String destination;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;

    public Flight(String flightNumber, String source, String destination, LocalDateTime departureTime,
                  LocalDateTime arrivalTime) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }


    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Flight flight)) {
            return false;
        }

        return Objects.equals(flightNumber, flight.flightNumber) &&
                Objects.equals(source, flight.source) &&
                Objects.equals(destination, flight.destination) &&
                Objects.equals(departureTime, flight.departureTime) &&
                Objects.equals(arrivalTime, flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, source, destination, departureTime, arrivalTime);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", departalTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
