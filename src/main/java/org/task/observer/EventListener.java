package org.task.observer;

import org.task.flights.Flight;

public interface EventListener {
    void notifyListener(Flight flight);
}
