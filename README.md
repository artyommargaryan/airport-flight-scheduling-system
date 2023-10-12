# Application Documentation

## Table of Contents

1. **Introduction**
    - Purpose of the Application
    - Design Patterns Used

2. **Getting Started**
    - Prerequisites
    - Installation
    - Running the Application

3. **Application Overview**
    - Application Structure
    - Key Components
    - Application Workflow

4. **How to Run the Application**
    - Preparing the Environment
    - Running the Application

5. **Design Patterns Used**
    - List of Design Patterns
    - Explanation and Usage
   
6. **What can be added**
   - Reading input from file
   - Improve flights handling
---

## 1. Introduction

### Purpose of the Application

The application is designed to simulate the operations of an airport, including managing flights, gates, and runways. 
It provides a virtual environment to observe and control the movement of arriving and departing flights within the 
airport.

### Design Patterns Used

The following design patterns are used in this application:

- Singleton Pattern
- Observer Pattern

## 2. Getting Started

### Prerequisites

Before running the application, make sure you have the following prerequisites:

- Java Development Kit (JDK) installed
- Gradle or a similar build tool for Java projects
- Dependencies mentioned in the `build.gradle` file

### Installation

1. Clone the application repository from the provided source.

      ```bash
      git clone https://github.com/artyommargaryan/airport-flight-scheduling-system.git
      ```

   or

      ```bash
      git clone git@github.com:artyommargaryan/airport-flight-scheduling-system.git
      ```

2. Navigate to the project directory.

```bash
cd airport-flight-scheduling-system
```

### Running the Application

To run the application, follow these steps:

1. Build the application using Gradle or your preferred build tool. 
   - for Linux or MacOS
        ```bash
        ./gradlew build
        ```
        or
        ```bash
        ./gradlew clean build
        ```
    - for Windows
        ```commandline
        gradlew.bat build
        ```
        or
        ```commandline
        gradlew.bat clean build
        ```

2. Execute the application using the following command:
    - for Linux or MacOS
      ```bash
      ./gradlew run
       ```
   - for Windows
     ```commandline
     gradlew.bat run
     ```
## 3. Application Overview

### Application Structure

The application is structured as follows:

- `org.task` package: Root package for the application.
    - Subpackages for different components, such as `flights`, `gates`, `managers`, `observer`, `runways`, and `tower`.
- `Airport.java`: Main class containing the entry point of the application.

### Key Components

- `Flight`: Base class representing flight details.
- `ArrivalFlight`: Subclass representing arriving flights.
- `DepartingFlight`: Subclass representing departing flights.
- `Gate`: Class representing airport gates.
- `Runway`: Class representing airport runways.
- `GateManager`: Class managing gates and allocating them to flights.
- `RunwayManager`: Class managing runways and allocating them to flights.
- `AirportTower`: Class managing the central control tower.
- `EventManager`: Class for handling and dispatching events.

### Application Workflow

The application simulates the operations of an airport by managing flights, gates, and runways. Flights are scheduled 
to arrive and depart, and gates/runways are allocated to them based on availability. The central control tower manages 
and coordinates these operations. At the beginning of the application, you should type the name of the airport. After 
this, you should choose gates count, and then the runways count and flight count per minute your airport can handle w
ill be calculated. After these choices, the application will start. To stop the application you need to type stop when
you see `Enter 'stop' to stop simulation`. In the end, all logs will be saved at file `src/main/resources/application.log`


## 4. How to Run the Application

### Preparing the Environment

Before running the application, ensure that you have the required software and dependencies installed (as mentioned in
the Prerequisites section).

### Running the Application

Follow the steps provided in the "Running the Application" section of the Getting Started documentation.

## 5. Design Patterns Used

### List of Design Patterns

#### Singleton Pattern

The Singleton Pattern is used to ensure that only one instance of key classes, such as `GateManager`, `RunwayManager`, 
`EventManager`, and `AirportTower`, are created. This ensures centralized and efficient control of these components.

#### Observer Pattern

The Observer Pattern is used to manage and dispatch events within the application. Key components, such as 
`RunwayManager` and `GateManager`, subscribe to events and respond to them as flights arrive and depart.

### Explanation and Usage

- Singleton Pattern: Ensures single instances of critical components, preventing conflicts and ensuring efficient 
resource allocation.
- Observer Pattern: Facilitates communication and coordination among various components of the airport simulation,
allowing them to react to flight-related events.

Certainly, here's an updated section for what can be added to your application:

## 6. What Can Be Added

To enhance and extend the functionality of your airport simulation application, consider the following possible improvements:

#### Reading Input from File

Implement the ability to read flight data from an external file. This would allow to load flight information from 
various sources, such as text files or databases, and introduce a more dynamic and data-driven aspect to the simulation.

#### Improved Flight Handling

Enhance the flight handling logic to incorporate more realistic features and scenarios. This could include factors such as:

- Different types of aircraft with varying passenger capacities.
- Advanced scheduling algorithms for flight arrivals and departures.
- Handling flight delays and cancellations.

These improvements can make airport simulation more sophisticated and closer to real-world airport operations.
