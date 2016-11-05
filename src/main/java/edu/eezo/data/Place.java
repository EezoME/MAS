package edu.eezo.data;

import edu.eezo.MainGUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents geolocation (can sent data to GoogleMaps Directions API).
 * Created by Eezo on 02.11.2016.
 */
public class Place {
    /**
     * Location title (usually, city name).
     */
    private String title;
    /**
     * A full location title (for GoogleMaps Directions API).<br/>
     * <i>Example:</i> "Николаев, Николаевская область, Украина".
     */
    private String gmapsAddress;
    //private double longitude;
    //private double latitude;

    /**
     * Sets up location data.
     *
     * @param title                city name (like "Николаев")
     * @param gmapsAddressAddition region addition (like "Николаевская область, Украина")
     */
    public Place(String title, String gmapsAddressAddition) {
        this.title = title;
        this.gmapsAddress = title + ", " + gmapsAddressAddition;
    }

    public String getGmapsAddress() {
        return gmapsAddress;
    }

    /**
     * Generates a list of most known Ukrainian cities (31 places).
     * @return a list of locations
     */
    public static List<Place> generateDefaultPlaces() {
        List<Place> places = new ArrayList<>();
        places.add(new Place("Белая Церковь", "Киевская область, Украина"));
        places.add(new Place("Винница", "Винницкая область, Украина"));
        places.add(new Place("Днепр", "Днепропетровская область, Украина"));
        places.add(new Place("Донецк", "Донецкая область, Украина"));
        places.add(new Place("Житомир", "Житомирская область, Украина"));
        places.add(new Place("Запорожье", "Запорожская область, Украина"));
        places.add(new Place("Ивано-Франковск", "Ивано-Франковская область, Украина"));
        places.add(new Place("Киев", "Киевская область, Украина"));
        places.add(new Place("Кривой Рог", "Днепропетровская область, Украина"));
        places.add(new Place("Кропивницкий", "Кировоградская область, Украина"));
        places.add(new Place("Луганск", "Луганская область, Украина"));
        places.add(new Place("Луцк", "Волынская область, Украина"));
        places.add(new Place("Львов", "Львовская область, Украина"));
        places.add(new Place("Мариуполь", "Донецкая область, Украина"));
        places.add(new Place("Мелитополь", "Запорожская область, Украина"));
        places.add(new Place("Николаев", "Николаевская область, Украина"));
        places.add(new Place("Никополь", "Днепропетровская область, Украина"));
        places.add(new Place("Одесса", "Одесская область, Украина"));
        places.add(new Place("Полтава", "Полтавская область, Украина"));
        places.add(new Place("Ровно", "Ровенская область, Украина"));
        places.add(new Place("Севастополь", "Автономная Республика Крым"));
        places.add(new Place("Симферополь", "Автономная Республика Крым"));
        places.add(new Place("Сумы", "Сумская область, Украина"));
        places.add(new Place("Тернополь", "Тернопольская область, Украина"));
        places.add(new Place("Ужгород", "Закарпатская область, Украина"));
        places.add(new Place("Харьков", "Харьковская область, Украина"));
        places.add(new Place("Херсон", "Херсонская область, Украина"));
        places.add(new Place("Хмельницкий", "Хмельницкая область, Украина"));
        places.add(new Place("Черкассы", "Черкасская область, Украина"));
        places.add(new Place("Чернигов", "Черниговская область, Украина"));
        places.add(new Place("Черновцы", "Черновицкая область, Украина"));
        return places;
    }

    public static Place getPlaceByTitle(List<Place> placeList, String title) {
        for (Place place : placeList) {
            if (place.title.equals(title)) {
                return place;
            }
        }
        return null;
    }

    /**
     * Returns a random place form specified list.
     * @param placeList specified list
     * @return place object
     */
    public static Place getRandomPlaceFromList(List<Place> placeList) {
        return placeList.get(MainGUI.getRandomNumberInRange(0, placeList.size()));
    }

    /**
     * Returns a random place that not equal to specified form the list.
     * @param placeList the list
     * @param exclude specified place
     * @return place object
     */
    public static Place getRandomPlaceFromListExclude(List<Place> placeList, Place exclude) {
        Place place;
        do {
            place = placeList.get(MainGUI.getRandomNumberInRange(0, placeList.size()));
        } while (place.equals(exclude));
        return place;
    }

    @Override
    public String toString() {
        return title;
    }
}
