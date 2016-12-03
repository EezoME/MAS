package edu.eezo.gmap;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import edu.eezo.data.Place;

/**
 * GoogleMaps DirectionsAPI Utility
 * Created by Eezo on 02.11.2016.
 */
public class GMDAPIUtility {

    public static long getDistance(Place from, Place to) {
        GeoApiContext context = new GeoApiContext().setApiKey(ApiKey.API_KEY); // see how to get API_KEY on the web
        DirectionsApiRequest dar = DirectionsApi.newRequest(context);

        dar.origin(from.getGmapsAddress());
        dar.destination(to.getGmapsAddress());
        dar.alternatives(false);

        try {
            return dar.await().routes[0].legs[0].distance.inMeters;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return 0L;
    }
}
