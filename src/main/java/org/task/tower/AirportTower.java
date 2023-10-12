package org.task.tower;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.task.flights.ArrivalFlight;
import org.task.flights.DepartingFlight;
import org.task.flights.Flight;
import org.task.gates.Gate;
import org.task.observer.EventManager;
import org.task.observer.EventType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AirportTower extends Thread {
    private static volatile AirportTower instance;
    private final EventManager eventManager;
    private final List<Flight> flights;
    private static final Logger logger = LoggerFactory.getLogger(Gate.class);


    private AirportTower(String airportName, EventManager eventManager) {
        super("Tower of airport " + airportName);
        this.eventManager = eventManager;
        this.flights = new CopyOnWriteArrayList<>();
    }

    public static AirportTower getInstance(String airportName, EventManager eventManager) {
        if (instance == null) {
            synchronized (AirportTower.class) {
                if (instance == null) {
                    instance = new AirportTower(airportName, eventManager);
                }
            }
        }
        return instance;
    }

    public synchronized void addFlight(Flight flight) {
        flights.add(flight);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            List<Flight> flightsToRemove = new ArrayList<>();
            for (Flight flight : flights) {
                if (flight instanceof ArrivalFlight && isWithinCurrentMinute(flight.getArrivalTime())) {
                    eventManager.addEvent(EventType.ARRIVING, flight);
                    logger.info("Flight {} arriving", flight.getFlightNumber());
                    flightsToRemove.add(flight);
                } else if (flight instanceof DepartingFlight && isWithinCurrentMinute(flight.getDepartureTime())) {
                    eventManager.addEvent(EventType.DEPARTING, flight);
                    logger.info("Flight {} departing", flight.getFlightNumber());
                    flightsToRemove.add(flight);
                }
            }

            for (Flight flightToRemove : flightsToRemove) {
                flights.remove(flightToRemove);
            }
        }
    }

    public static boolean isWithinCurrentMinute(LocalDateTime dateTimeToCheck) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        return dateTimeToCheck.getMinute() == currentDateTime.getMinute() &&
                dateTimeToCheck.getHour() == currentDateTime.getHour() &&
                dateTimeToCheck.getDayOfMonth() == currentDateTime.getDayOfMonth() &&
                dateTimeToCheck.getMonth() == currentDateTime.getMonth() &&
                dateTimeToCheck.getYear() == currentDateTime.getYear();
    }

    public void end() {
        this.interrupt();
    }
}
