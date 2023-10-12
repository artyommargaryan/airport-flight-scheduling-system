package org.task.observer;

import org.task.flights.Flight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventManager {
    private static volatile EventManager instance;
    private final Map<EventType, EventListener> listeners;

    private EventManager() {
        this.listeners = new ConcurrentHashMap<>();
    }

    public static EventManager getInstance() {
        if (instance == null) {
            synchronized (EventManager.class) {
                if (instance == null) {
                    instance = new EventManager();
                }
            }
        }
        return instance;
    }


    public void subscribe(EventType eventType, EventListener listener) {
        listeners.put(eventType, listener);
    }

    public synchronized void addEvent(EventType eventType, Flight flight) {
        listeners.get(eventType).notifyListener(flight);
    }
}
