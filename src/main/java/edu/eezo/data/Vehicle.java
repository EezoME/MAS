package edu.eezo.data;

import edu.eezo.MainGUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a transportation unit (vehicle), like truck.
 * Created by Eezo on 04.11.2016.
 */
public class Vehicle {
    /**
     * Some unique name for vehicle.
     */
    private String identification;
    /**
     * Current location for vehicle (for GoogleMaps Directions API).
     */
    private String currentLocation;
    /**
     * Current city where this vehicle is located.
     */
    private Place currentPlace;
    /**
     * The maximum allowed weight of the load (in kilograms).
     */
    private int maxWeight;
    /**
     * Current vehicle state.
     */
    private State state;

    public Vehicle(String identification, Place currentPlace, int maxWeight) {
        this(identification, "", currentPlace, maxWeight, State.FREE);
    }

    public Vehicle(String identification, String currentLocation, Place currentPlace, int maxWeight, State state) {
        this.identification = identification;
        this.currentLocation = currentLocation;
        this.currentPlace = currentPlace;
        this.maxWeight = maxWeight;
        this.state = state;
    }

    /**
     * Generates 5 default vehicles with <code>maxWeight</code> = 25.000.
     * @return a list of vehicles
     */
    public static List<Vehicle> generateDefaultVehiclesList() {
        List<Vehicle> vehicles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            vehicles.add(new Vehicle("Vehicle " + (i + 1), MainGUI.myHeadquarter, 25000));
        }
        return vehicles;
    }

    public static String[] getTableColumnsIdentifiers() {
        return new String[]{"№", "Identification", "Current Location", "Max Weight", "State"};
    }

    public Object[] getTableRowData() {
        return new Object[]{0, identification, currentPlace, maxWeight, state.getReadableValue()};
    }

    /**
     * Vehicles possible states.
     */
    enum State {
        FREE {
            @Override
            public String getReadableValue() {
                return "Free";
            }
        },
        ON_THE_ROUTE {
            @Override
            public String getReadableValue() {
                return "On the route";
            }
        },
        ON_LOAD {
            @Override
            public String getReadableValue() {
                return "On load";
            }
        },
        WAITING_FOR_LOAD {
            @Override
            public String getReadableValue() {
                return "Waiting for load";
            }
        },;

        public abstract String getReadableValue();
    }
}