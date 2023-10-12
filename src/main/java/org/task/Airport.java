package org.task;

import org.task.flights.ArrivalFlight;
import org.task.flights.DepartingFlight;
import org.task.flights.Flight;
import org.task.gates.Gate;
import org.task.managers.GateManager;
import org.task.managers.RunwayManager;
import org.task.observer.EventManager;
import org.task.observer.EventType;
import org.task.runways.Runway;
import org.task.tower.AirportTower;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Airport {
    private static int generateFlightsInvocationCount = 0;

    public static void main(String[] args) {
        EventManager eventManager = EventManager.getInstance();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter airport name: ");
        String airportName = scanner.nextLine();

        int numOfGates;
        while (true) {
            System.out.println("Number of gates should be more than 1");
            System.out.println("Runways will be half of the gate count");
            System.out.println("Both arriving and departing flight counts will be about half of the gate count");
            System.out.print("Enter the number of gates: ");
            if (scanner.hasNextInt() && (numOfGates = scanner.nextInt()) >= 1) {
                scanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }


        int numOfRunways = numOfGates / 2;

        System.out.println("runways: " + numOfRunways);

        RunwayManager runwayManager = RunwayManager.getInstance();
        eventManager.subscribe(EventType.ARRIVING, runwayManager);
        eventManager.subscribe(EventType.DEPARTING_RUNWAY, runwayManager);

        for (int i = 1; i <= numOfRunways; i++) {
            Runway runway = new Runway("runway" + i, eventManager);
            runway.start();
            runwayManager.addRunway(runway);
        }

        GateManager gateManager = GateManager.getInstance();
        eventManager.subscribe(EventType.ARRIVING_GATE, gateManager);
        eventManager.subscribe(EventType.DEPARTING, gateManager);

        for (int i = 1; i <= numOfGates; i++) {
            Gate gate = new Gate("gate" + i, eventManager);
            gate.start();
            gateManager.addGate(gate);
        }

        AirportTower airportTower = AirportTower.getInstance(airportName, eventManager);
        airportTower.start();

        ScheduledExecutorService flightScheduler = Executors.newScheduledThreadPool(1);
        int numOfFlightsPerMinute = numOfRunways;

        flightScheduler.scheduleAtFixedRate(() -> generateFlights(airportTower, airportName,
                numOfFlightsPerMinute), 0, 1, TimeUnit.MINUTES);

        Thread userInputThread = new Thread(() -> {
            String next;
            do {
                System.out.println("Enter 'stop' to stop simulation");
                next = scanner.next();
            } while (!next.equalsIgnoreCase("stop"));

            flightScheduler.shutdown();
            runwayManager.stop();
            gateManager.stop();
            airportTower.end();
        });

        userInputThread.start();
    }

    private static void generateFlights(AirportTower airportTower, String airportName, int numOfFlightsPerMinute) {
        for (int i = 0; i < numOfFlightsPerMinute; i++) {
            LocalDateTime arrivalTime = LocalDateTime.now();
            LocalDateTime departureTime = arrivalTime.minusHours(i % 6);

            Flight flight = new ArrivalFlight("ArrivingFlight-" + generateFlightsInvocationCount + "-" + i, "Source" + i,
                    airportName, departureTime, arrivalTime);

            airportTower.addFlight(flight);

            departureTime = LocalDateTime.now();
            arrivalTime = departureTime.plusHours(i % 6);

            flight = new DepartingFlight("DepartingFlight-" + generateFlightsInvocationCount + "-" + i, airportName,
                    "Destination" + i, departureTime, arrivalTime);

            airportTower.addFlight(flight);
        }

        generateFlightsInvocationCount++;
    }
}
