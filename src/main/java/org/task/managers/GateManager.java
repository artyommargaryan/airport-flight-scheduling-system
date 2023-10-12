package org.task.managers;

import org.task.flights.Flight;
import org.task.gates.Gate;
import org.task.observer.EventListener;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GateManager implements EventListener {
    private static volatile GateManager instance;
    private final Queue<Gate> gates;

    private GateManager() {
        gates = new ConcurrentLinkedQueue<>();
    }

    public static GateManager getInstance() {
        if (instance == null) {
            synchronized (GateManager.class) {
                if (instance == null) {
                    instance = new GateManager();
                }
            }
        }
        return instance;
    }

    public void notifyListener(Flight flight) {
        gates.parallelStream()
                .filter(gate -> !gate.isOccupied())
                .findAny()
                .ifPresent(gate -> gate.notifyGate(flight));
    }

    public void addGate(Gate gate) {
        gates.add(gate);
    }

    public void stop() {
        for (Gate gate : gates) {
            gate.interrupt();
        }

        for (Gate gate : gates) {
            try {
                gate.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
