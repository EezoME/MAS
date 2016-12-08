package edu.eezo.data;

import edu.eezo.MainGUI;
import edu.eezo.saving.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class represents a transportation unit (vehicle), like truck.
 * Created by Eezo on 04.11.2016.
 */
public class Vehicle implements ITableViewable {

    private static int vehicleCounter = 0;

    private int id;

    /**
     * Some unique name for vehicle.
     */
    private String identification;

    /**
     * The maximum allowed weight of the load (in tons).
     */
    private double freight;

    /**
     * Vehicles average speed (km/hour).
     */
    private int averageSpeed;

    /**
     * Time that vehicle needs for loading the freight (hrs).
     */
    private double timeForLoad;

    /**
     * Time that vehicle needs for unloading the freight (hrs).
     */
    private double timeForUnload;

    /**
     * Cost of transportation of cargo for 1 km (UAH).
     */
    private double transportationCost;

    /**
     * Current vehicle's location.
     */
    private String currentLocation;

    /**
     * Current vehicle's state.
     */
    private State state;


    /* Output data for agent */

    /**
     * On which route is.
     */
    private String currentRoute;

    /**
     * How many kilometers vehicle already passed.
     */
    private double routeCompletedLength;

    /**
     * How long vehicle is on route (hrs).
     */
    private double timeOnRoute;

    /**
     * Current number of freight (tons).
     */
    private double currentFreight;


    Vehicle() {
        this.id = vehicleCounter++;
    }

    public Vehicle(String identification, double freight, int averageSpeed, double timeForLoad, double timeForUnload, double transportationCost) {
        this(identification, freight, averageSpeed, timeForLoad, timeForUnload, transportationCost,
                MainGUI.myHeadquarter.getGmapsAddress(), State.FREE, null, 0.0, 0.0, 0.0);
    }

    public Vehicle(String identification, double freight, int averageSpeed, double timeForLoad, double timeForUnload,
                   double transportationCost, String currentLocation, State state, String currentRoute,
                   double routeCompletedLength, double timeOnRoute, double currentFreight) {
        this.id = vehicleCounter++;
        this.identification = identification;
        this.freight = freight;
        this.averageSpeed = averageSpeed;
        this.timeForLoad = timeForLoad;
        this.timeForUnload = timeForUnload;
        this.transportationCost = transportationCost;
        this.currentLocation = currentLocation;
        this.state = state;
        this.currentRoute = currentRoute;
        this.routeCompletedLength = routeCompletedLength;
        this.timeOnRoute = timeOnRoute;
        this.currentFreight = currentFreight;
    }


    /**
     * Generates 5 default vehicles with <code>freight</code> = 25,000 kg (25 ton).
     *
     * @return a list of vehicles
     */
    public static List<Vehicle> generateDefaultVehiclesList() {
        List<Vehicle> vehicles = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            vehicles.add(new Vehicle("Vehicle " + (i + 1), 25.0d, 70, 3.0d, 2.5d, 4.0d));
        }

        return vehicles;
    }

    public static Vehicle generateDefaultVehicle() {
        return new Vehicle("V" + new Random().nextInt(), 0.0d, 0, 0.0d, 0.0d, 0.0d);
    }

    /**
     * Help method for <code>Object[] getTableRowData()</code>.
     *
     * @return an array of columns identifiers
     */
    public static String[] getTableColumnsIdentifiers() {
        return new String[]{"№", "Identification", "Freight", "Average Speed", "Time For Load", "Time For Unload",
                "Cost", "Location", "State"};
    }

    public Object[] getTableRowData() {
        return new Object[]{id, identification, getReadableFreight(), getReadableSpeed(), getReadableTime(timeForLoad),
                getReadableTime(timeForUnload), getReadableCost(), currentLocation, state.getReadableValue()};
    }

    public Object[] getTableRowActualData() {
        return new Object[]{id, getState(), getCurrentRoute(), getRouteCompletedLength(), getTimeOnRoute(), getCurrentFreight()};
    }

    private String getReadableFreight() {
        return freight + " tn";
    }

    private String getReadableSpeed() {
        return averageSpeed + " km/h";
    }

    private String getReadableTime(double time) {
        return time + " hrs";
    }

    private String getReadableCost() {
        return transportationCost + " UAH";
    }


    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getTimeForLoad() {
        return timeForLoad;
    }

    public void setTimeForLoad(double timeForLoad) {
        this.timeForLoad = timeForLoad;
    }

    public double getTimeForUnload() {
        return timeForUnload;
    }

    public void setTimeForUnload(double timeForUnload) {
        this.timeForUnload = timeForUnload;
    }

    public double getTransportationCost() {
        return transportationCost;
    }

    public void setTransportationCost(double transportationCost) {
        this.transportationCost = transportationCost;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(String currentRoute) {
        this.currentRoute = currentRoute;
    }

    public double getRouteCompletedLength() {
        return routeCompletedLength;
    }

    public void setRouteCompletedLength(double routeCompletedLength) {
        this.routeCompletedLength = routeCompletedLength;
    }

    public double getTimeOnRoute() {
        return timeOnRoute;
    }

    public void setTimeOnRoute(double timeOnRoute) {
        this.timeOnRoute = timeOnRoute;
    }

    public double getCurrentFreight() {
        return currentFreight;
    }

    public void setCurrentFreight(double currentFreight) {
        this.currentFreight = currentFreight;
    }

    /**
     * If it's possible, increases current freight of this vehicle.
     *
     * @param freight additional freight (tons)
     * @return <b>true</b> if addition was completed successfully, <b>false</b> - otherwise
     */
    public boolean addMoreFreight(double freight) {
        if (this.currentFreight + freight > this.freight) {
            return false;
        }

        this.currentFreight += freight;

        return true;
    }

    /**
     * Vehicle possible states.
     */
    public enum State {
        FREE {
            @Override
            public String getReadableValue() {
                return "Free";
            }
        },
        ON_UNLOAD {
            @Override
            public String getReadableValue() {
                return "Unloading";
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
                return "Loading";
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