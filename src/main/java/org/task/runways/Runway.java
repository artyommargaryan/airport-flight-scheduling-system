package org.task.runways;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.task.flights.ArrivalFlight;
import org.task.flights.Flight;
import org.task.observer.EventManager;
import org.task.observer.EventType;

public class Runway extends Thread {
    private volatile boolean isOccupied;
    private Flight flight;
    private final EventManager eventManager;
    private static final Logger logger = LoggerFactory.getLogger(Runway.class);

    public Runway(String name, EventManager eventManager) {
        super(name);
        this.isOccupied = false;
        this.eventManager = eventManager;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (this) {
                    if (isOccupied) {
                        logger.info("{} is on runway {}", flight.getFlightNumber(), getName());
                        Thread.sleep(6000);
                        if (flight instanceof ArrivalFlight) {
                            logger.info("{} is no longer on runway {} it is going to gates", flight.getFlightNumber(), getName());
                            eventManager.addEvent(EventType.ARRIVING_GATE, flight);
                        } else {
                            logger.info("{} is no longer on runway {}  it is has flight to {}", flight.getFlightNumber(), getName(), flight.getDestination());
                        }
                        isOccupied = false;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void notifyRunway(Flight flight) {
        isOccupied = true;
        this.flight = flight;
    }

    public boolean isOccupied() {
        return isOccupied;
    }
}
