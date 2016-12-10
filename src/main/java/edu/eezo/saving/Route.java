package edu.eezo.saving;

import edu.eezo.data.Order;
import edu.eezo.data.Place;
import edu.eezo.data.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the global route.
 * Created by Eezo on 09.11.2016.
 */
public class Route {

    /**
     * A list of orders gor this route.
     */
    private List<Order> routeOrders;

    /**
     * A list of places that vehicle must visited on route.
     */
    private List<Place> routePlaces;

    /**
     * Headquarter location.
     */
    private Place headquarter;

    /**
     * Saving distances.
     */
    private long[] distances;

    public Route(Place headquarter) {
        this.routeOrders = new ArrayList<>();
        this.routePlaces = new ArrayList<>();
        this.headquarter = headquarter;
    }

    /**
     * Makes global route.<br>
     * Steps:<ul>
     * <li><b>1</b>: getting distance array</li>
     * <li><b>2</b>: sorting orders</li>
     * <li><b>3</b>: build global route using saving</li>
     * </ul>
     *
     * @param orderList
     */
    public void makeGlobalRoute(List<Order> orderList) {
        if (orderList == null || orderList.isEmpty()) {
            return;
        }

        // saving requires sorting | table showing requires distances
        distances = SavingTable.sort(orderList);

        // SAVING
        // -1. First add
        // 0. Проверка пока не все пары отмечены.
        //  1. Отмечаем все пары с имеющимися элементами списка.
        //  2а. Если есть пара к последнему пункту в списке, добавляем её к общему списку (избегая уже отмеченных).
        //  2б. Если нет пары - добавляем первую в списке (оба пункта).
        routeOrders.add(orderList.get(0));
        routePlaces.add(orderList.get(0).getOrigin());
        routePlaces.add(orderList.get(0).getDestination());
        boolean cycleContinue = true;
        boolean[] ordersCheckMap = new boolean[orderList.size()];

        Arrays.fill(ordersCheckMap, false);

        ordersCheckMap[0] = true;

        while (cycleContinue) { // 0
            // 1
            for (int i = 0; i < routePlaces.size() - 1; i++) {
                for (int j = 0; j < orderList.size(); j++) {
                    if (ordersCheckMap[j]) continue;

                    if (routePlaces.get(i).equals(orderList.get(j).getOrigin())) { //routePlaces.get(i).equals(orderList.get(j).getDestination())
                        ordersCheckMap[j] = true;
                        // (probably, it may be changed | not necessary checking for destination)
                    }
                }
            }

            // 2a
            for (int i = 0; i < orderList.size(); i++) {
                if (ordersCheckMap[i]) continue;

                if (getLastPlaceFromList(routePlaces).equals(orderList.get(i).getOrigin())) {
                    routeOrders.add(orderList.get(i));
                    routePlaces.add(orderList.get(i).getOrigin());
                    routePlaces.add(orderList.get(i).getDestination());
                    ordersCheckMap[i] = true;
                    break;
                }
            }

            // 2b
            for (int i = 0; i < orderList.size(); i++) {
                if (ordersCheckMap[i]) continue;

                routeOrders.add(orderList.get(i));
                routePlaces.add(orderList.get(i).getOrigin());
                routePlaces.add(orderList.get(i).getDestination());
                ordersCheckMap[i] = true;
                break;
            }

            // 0 check
            int count = 0;

            for (int i = 0; i < ordersCheckMap.length; i++) {
                if (ordersCheckMap[i]) count++;
            }

            cycleContinue = !(count == ordersCheckMap.length);
        }
    }

    /**
     * Makes local routes.
     * Returns non-finalized routes.
     *
     * @param vehicleList a list of vehicles (each vehicle is one route)
     * @return a list of local routes
     */
    public List<LocalRoute> makeLocalRoutes(List<Vehicle> vehicleList) {
        if (vehicleList == null || vehicleList.isEmpty()) {
            return new ArrayList<>();
        }

        List<LocalRoute> localRoutes = new ArrayList<>();

        for (int i = 0; i < vehicleList.size(); i++) {
            localRoutes.add(new LocalRoute()); // may create more route than we need, list will be cleaned at the end
        }

        // 0. Проверка пока не все пары отмечены ИЛИ пока не все грузовики заняты
        // Проходим списки заказов с глобального маршрута и машин
        // Пропускаем уже отмеченные заказы и машины
        // 1. Проверяем, можно ли подключить заказ к списку
        // 2а. Если ДА - Подключаем к локальному маршруту
        // 2б. Если НЕТ - замыкаем текущий локальный маршрут и рассматриваем следующий грузовик
        boolean[] ordersCheckMap = new boolean[routeOrders.size()];
        boolean[] vehiclesCheckMap = new boolean[vehicleList.size()];
        boolean cycleContinue = true;

        Arrays.fill(ordersCheckMap, false);
        Arrays.fill(vehiclesCheckMap, false);

        while (cycleContinue) { // 0
            for (int i = 0; i < vehicleList.size(); i++) {
                if (vehiclesCheckMap[i]) continue;

                for (int j = 0; j < routeOrders.size(); j++) {
                    if (ordersCheckMap[j]) continue;

                    // 1
                    if (vehicleList.get(i).addMoreFreight(routeOrders.get(j).getFreightVolume())) {
                        // 2a
                        localRoutes.get(i).addOrderToRoute(routeOrders.get(j));
                        ordersCheckMap[j] = true;
                    } else {
                        // 2b
//                        if (!localRoutes.get(j).isEmpty()) localRoutes.get(j).finalizeRoute(headquarter);
                        vehiclesCheckMap[i] = true;
                        break;
                    }
                }
            }

            int countVehicle = 0;
            int countOrders = 0;

            for (int i = 0; i < vehiclesCheckMap.length; i++) {
                if (vehiclesCheckMap[i]) countVehicle++;
            }

            for (int i = 0; i < ordersCheckMap.length; i++) {
                if (ordersCheckMap[i]) countOrders++;
            }

            cycleContinue = !(countVehicle == vehicleList.size() || countOrders == routeOrders.size());
        }

        // deleting empty local routes
        for (int i = localRoutes.size() - 1; i >= 0; i--) {
            if (localRoutes.get(i).isEmpty()) {
                localRoutes.remove(i);
            }
        }

        return localRoutes;
    }

    /**
     * Makes route to right form (deletes duplicated places,
     * adds headquarter location on the begin and the end of the route).
     */
    public void finalizeRoute() {
        for (int i = 0; i < routePlaces.size() - 1; i++) {
            if (routePlaces.get(i).equals(routePlaces.get(i + 1))) routePlaces.remove(i + 1);
        }

        if (!routePlaces.get(0).equals(headquarter)) routePlaces.add(0, headquarter);
        if (!getLastPlaceFromList(routePlaces).equals(headquarter)) routePlaces.add(headquarter);
    }

    /**
     * Returns a table row data for table.
     *
     * @return a two-dimensional array of objects
     */
    public Object[][] getTableData() {
        Object[][] rowData = new Object[routeOrders.size()][4];

        for (int i = 0; i < rowData.length; i++) {
            rowData[i] = new Object[]{routeOrders.get(i).getId(), routeOrders.get(i).getOrigin(),
                    routeOrders.get(i).getDestination(), distances[i]};
        }

        return rowData;
    }

    /**
     * Returns a string representation of places that route includes.
     *
     * @return a string of places ID
     */
    public String getRoutePathInId() {
        String path = "";

        for (int i = 0; i < routePlaces.size(); i++) {
            path += routePlaces.get(i).getId() + " - ";
        }

        return path.substring(0, path.length() - 3);
    }

    /**
     * Returns the last place in specified place list.
     *
     * @param placeList specified place list
     * @return place instance
     */
    public static Place getLastPlaceFromList(List<Place> placeList) {
        return placeList.get(placeList.size() - 1);
    }
}
