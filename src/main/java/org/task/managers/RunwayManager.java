package org.task.managers;

import org.task.flights.Flight;
import org.task.observer.EventListener;
import org.task.runways.Runway;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RunwayManager implements EventListener {
    private static volatile RunwayManager instance;
    private final Queue<Runway> runways;

    private RunwayManager() {
        runways = new ConcurrentLinkedQueue<>();
    }

    public static RunwayManager getInstance() {
        if (instance == null) {
            synchronized (RunwayManager.class) {
                instance = new RunwayManager();
            }
        }

        return instance;
    }

    public void notifyListener(Flight flight) {
        runways.parallelStream()
                .filter(runway -> !runway.isOccupied())
                .findAny()
                .ifPresent(gate -> gate.notifyRunway(flight));
    }

    public void addRunway(Runway runway) {
        runways.add(runway);
    }

    public void stop() {
        for (Runway runway : runways) {
            runway.interrupt();
        }

        for (Runway runway : runways) {
            try {
                runway.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
