package edu.eezo.saving;

import edu.eezo.MainGUI;
import edu.eezo.data.Order;
import edu.eezo.data.Place;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Eezo on 09.11.2016.
 */
public class Route {
    private List<Place> route;

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
                        clientsOrders[i].getDestination().equals(route.get(route.size() - 2))){
                    isOnRouteChecks[i] = true;
                }
            }
        }

        route.add(MainGUI.myHeadquarter);
    }

    public String getRouteIds() {
        String routeIds = "";
        for (Place place : route) {
            routeIds += place.getId() + " - ";
        }
        return routeIds;
        //return routeIds.substring(0, routeIds.length() - 4); // without last " - "
    }

    protected boolean isAllRowsChecked(boolean[] isOnRouteChecks) {
        for (int i = 0; i < isOnRouteChecks.length; i++) {
            if (!isOnRouteChecks[i]) return false;
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
}
