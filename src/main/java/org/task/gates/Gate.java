package org.task.gates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.task.flights.ArrivalFlight;
import org.task.flights.Flight;
import org.task.observer.EventManager;
import org.task.observer.EventType;

public class Gate extends Thread {
    private volatile boolean isOccupied;
    private Flight flight;
    private final EventManager eventManager;
    private static final Logger logger = LoggerFactory.getLogger(Gate.class);

    public Gate(String name, EventManager eventManager) {
        super(name);
        isOccupied = false;
        this.eventManager = eventManager;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (Gate.class) {
                    if (isOccupied) {
                        logger.info("{} is on gate {}", flight.getFlightNumber(), getName());
                        Thread.sleep(6000);
                        if (flight instanceof ArrivalFlight) {
                            logger.info("Passengers of {} are getting out plane {}", flight.getFlightNumber(), getName());
                            Thread.sleep(6000);
                            logger.info("{} is no longer on gate {}", flight.getFlightNumber(), getName());
                        } else {
                            logger.info("Passengers of {} are getting in plane {}", flight.getFlightNumber(), getName());
                            Thread.sleep(6000);
                            logger.info("{} is no longer on gate {}", flight.getFlightNumber(), getName());
                            eventManager.addEvent(EventType.DEPARTING_RUNWAY, flight);
                        }
                        isOccupied = false;
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    public synchronized void notifyGate(Flight flight) {
        this.isOccupied = true;
        this.flight = flight;
    }

    public boolean isOccupied() {
        return isOccupied;
    }
}
