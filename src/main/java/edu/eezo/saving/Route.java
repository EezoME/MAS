package edu.eezo.saving;

import edu.eezo.MainGUI;
import edu.eezo.data.Order;
import edu.eezo.data.Place;
import edu.eezo.data.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents all global and local routes utils.
 * <p>
 * Created by Eezo on 09.11.2016.
 */
public class Route {

    /**
     * A list of places on route.
     */
    private List<Place> route;

    /**
     * Building global transportation route using saving algorithm.<br>
     * <i>NOTE:</i> Working incorrectly if table has 2+ orders with equals origin (takes into account only the first).
     *
     * @param savingTable
     */
    public void buildGlobalRoute(SavingTable savingTable) {
        if (savingTable == null) {
            return;
        }

        boolean[] isOnRouteChecks = new boolean[savingTable.getClientSavingDistances().length];
        Arrays.fill(isOnRouteChecks, false);
        Order[] clientsOrders = savingTable.getClientOrderList();
        route = new ArrayList<>();
        route.add(MainGUI.myHeadquarter);

        // SAVING
        // 0. Проверка пока не все пары отмечены.
        // 1а. Если есть пара к последнему пункту в списке, добавляем её с общему списку (избегая уже отмеченных).
        // 1б. Если нет пары - добавляем первую в списке (оба пункта).
        // 2. Отмечаем все пары с предпосленим элементом списка.
        while (!isAllRowsChecked(isOnRouteChecks)) {
            Place pair;

            if ((pair = checkSublistForPlace(clientsOrders, route.get(route.size() - 1), isOnRouteChecks)) != null) {
                route.add(pair);
            } else {
                for (int i = 0; i < clientsOrders.length; i++) {
                    if (isOnRouteChecks[i]) continue;

                    route.add(clientsOrders[i].getOrigin());
                    route.add(clientsOrders[i].getDestination());
                    break;
                }
            }

            for (int i = 0; i < clientsOrders.length; i++) {
                if (isOnRouteChecks[i]) continue;

                if (clientsOrders[i].getOrigin().equals(route.get(route.size() - 2)) ||
                        clientsOrders[i].getDestination().equals(route.get(route.size() - 2))) {
                    isOnRouteChecks[i] = true;
                }
            }
        }

        route.add(MainGUI.myHeadquarter);
    }


    public List<LocalRoute> buildLocalRoutes(SavingTable savingTable, List<Vehicle> vehicleList) {
        if (savingTable == null || route == null || route.size() == 0 || vehicleList == null || vehicleList.size() == 0) {
            return null;
        }

        List<LocalRoute> localRoutes = new ArrayList<>();
        Order[] clientsOrders = savingTable.getClientOrderList();
        boolean[] globalRouteCheckMap = new boolean[route.size()];
        Arrays.fill(globalRouteCheckMap, false);
        globalRouteCheckMap[0] = true;
        globalRouteCheckMap[globalRouteCheckMap.length - 1] = true;

        // 0. Проверка пока не все пары отмечены ИЛИ пока не все грузовики заняты
        // Начиная со второго пункта в списке и с первого грузовика
        // 1. Находим заказ, что отвечает пункту
        // 2. Проверяем, можно ли подключить его к списку
        // 2а. Если ДА - Подключаем к локальному маршруту (этот пункт и его пару), возвращаемся к 1
        // 2б. Если НЕТ - замыкаем текущий локальный маршрут и рассматриваем следующий грузовик начиная с 1
        int routesCounter = 0;
        while (!isAllRowsChecked(globalRouteCheckMap) && !isAllVehiclesBusy(vehicleList)) {
            for (int i = 1; i < route.size() - 1; i++) { // skip first and last place - myHeadquarter
                if (globalRouteCheckMap[i]) continue;

                if (localRoutes.size() <= routesCounter) {
                    localRoutes.add(new LocalRoute());
                    break; // to allow more than one addition
                }

                boolean isOriginFound = false;
                for (int j = 0; j < clientsOrders.length; j++) {
                    if (clientsOrders[j].getOrigin().equals(route.get(i))) {
                        if (vehicleList.get(routesCounter).addMoreFreight(clientsOrders[j].getFreightVolume())) {
                            localRoutes.get(routesCounter).localRoute.add(route.get(i)); // can add two equals in a row, FIX IT
                            localRoutes.get(routesCounter).localRoute.add(route.get(i + 1));
                            globalRouteCheckMap[i] = true;
                            isOriginFound = true;
                        } else {
                            localRoutes.get(routesCounter).finalizeRoute();
                            routesCounter++;
                            i--;
                        }
                        break;
                    }
                }

                if (!isOriginFound) {
                    globalRouteCheckMap[i] = true;
                }
            }
        }

        localRoutes.get(routesCounter).finalizeRoute();
        return localRoutes;
    }

    public String getRouteIds() {
        String routeIds = "";

        for (Place place : route) {
            routeIds += place.getId() + " - ";
        }

//        return routeIds;
        return routeIds.substring(0, routeIds.length() - 3); // without last " - "
    }

    protected boolean isAllRowsChecked(boolean[] isOnRouteChecks) {
        for (int i = 0; i < isOnRouteChecks.length; i++) {
            if (!isOnRouteChecks[i]) return false;
        }

        return true;
    }

    protected boolean isAllVehiclesBusy(List<Vehicle> vehicleList) {
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getState().equals(Vehicle.State.FREE)) {
                return false;
            }
        }

        return true;
    }

    private Place checkSublistForPlace(Order[] sublist, Place place, boolean[] checkMap) {
        for (int i = 0; i < sublist.length; i++) {
            if (checkMap[i]) continue;

            if (sublist[i].getOrigin().equals(place)) {
                return sublist[i].getDestination();
            }

            if (sublist[i].getDestination().equals(place)) {
                return sublist[i].getOrigin();
            }
        }

        return null;
    }

    public class LocalRoute {
        private List<Place> localRoute;

        public LocalRoute() {
            this.localRoute = new ArrayList<>();
            this.localRoute.add(MainGUI.myHeadquarter);
        }

        public List<Place> getLocalRoute() {
            return localRoute;
        }

        public void setLocalRoute(List<Place> localRoute) {
            this.localRoute = localRoute;
        }

        public void finalizeRoute() {
            if (!this.localRoute.get(this.localRoute.size() - 1).equals(MainGUI.myHeadquarter)) {
                this.localRoute.add(MainGUI.myHeadquarter);
            }
        }
    }
}
