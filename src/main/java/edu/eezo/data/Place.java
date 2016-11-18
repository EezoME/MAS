package edu.eezo.data;

import edu.eezo.MainGUI;
import edu.eezo.gmap.GMDAPIUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents geolocation (can sent data to GoogleMaps Directions API).
 * Created by Eezo on 02.11.2016.
 */
public class Place {
    private static int counter = 0;

    private int id;
    /**
     * Location title (usually, city name).
     */
    private String title;
    /**
     * A full location title (for GoogleMaps Directions API).<br/>
     * <i>Example:</i> "Николаев, Николаевская область, Украина".
     */
    private String gmapsAddress;

    /**
     * Sets up location data.
     *
     * @param title                city name (like "Николаев")
     * @param gmapsAddressAddition region addition (like "Николаевская область, Украина")
     */
    public Place(String title, String gmapsAddressAddition) {
        this.id = counter++;
        this.title = title;
        this.gmapsAddress = title + ", " + gmapsAddressAddition;
    }

    public String getGmapsAddress() {
        return gmapsAddress;
    }

    /**
     * Generates a list of most known Ukrainian cities (31 places).<br>
     * Method {@link #generateDefaultDistancesMatrix()} generates distances between these locations.
     *
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

    /**
     * Generates a matrix of distances between most known Ukrainian cities (31x31).<br>
     * To figure out what most known Ukrainian places are, look {@link #generateDefaultPlaces()}.
     *
     * @return a two-dimensional array of distances
     */
    public static long[][] generateDefaultDistancesMatrix() {
        return new long[][]{{0, 179850, 466934, 717204, 156864, 530098, 514756, 86375, 414600, 288944, 894918, 435833, 508530, 752778, 695116, 396736, 520147, 391869, 416264, 344159, 809157, 728258, 413919, 384312, 736121, 553763, 463548, 285344, 176559, 229198, 463188},
                {179850, 0, 565062, 816981, 127238, 628226, 364761, 267961, 445881, 320225, 952926, 407010, 362530, 918130, 726632, 428252, 551428, 423384, 605968, 315336, 840673, 759774, 601835, 234273, 586126, 743467, 495064, 119149, 340813, 417114, 269522},
                {466934, 565062, 0, 248575, 614957, 85310, 937262, 477496, 145502, 320076, 384520, 893925, 1015923, 326981, 211345, 321107, 121999, 574112, 198555, 802251, 540303, 459404, 345204, 806774, 1286290, 217717, 329300, 691650, 279951, 539751, 823510},
                {717204, 816981, 248575, 0, 844266, 229895, 1309049, 706805, 396639, 571213, 152969, 1123234, 1245232, 112779, 299886, 600105, 309188, 732754, 395448, 1031560, 627633, 546734, 491191, 1057911, 1515599, 290420, 528338, 942787, 530416, 769060, 1074647},
                {156864, 127238, 614957, 844266, 0, 652273, 409120, 140778, 523823, 398167, 957439, 279882, 401880, 967506, 804340, 505960, 629370, 501093, 478785, 188208, 918381, 837482, 474652, 278676, 672247, 616284, 572772, 182824, 327814, 289931, 364790},
                {530098, 628226, 85310, 229895, 652273, 0, 1001305, 515215, 209545, 384119, 379142, 931644, 1053642, 222417, 125526, 392191, 79576, 524840, 277323, 839970, 454484, 373585, 423972, 870817, 1324009, 296485, 353912, 755693, 343994, 618395, 887553},
                {514756, 364761, 937262, 1309049, 409120, 1001305, 0, 604208, 803586, 677930, 1420869, 276557, 133600, 1275835, 1084337, 785957, 909133, 781089, 942215, 274589, 1198378, 1117479, 938082, 133274, 294655, 1079714, 852769, 233575, 688211, 753361, 135311},
                {86375, 267961, 477496, 706805, 140778, 515215, 604208, 0, 418807, 371235, 822042, 418903, 540901, 832109, 642911, 479027, 553566, 474160, 343388, 327229, 891448, 810549, 333726, 418611, 811268, 480887, 545839, 322759, 192417, 149005, 525149},
                {414600, 445881, 145502, 396639, 523823, 209545, 803586, 418807, 0, 185120, 533720, 830310, 816733, 431700, 335055, 175584, 107272, 308232, 274973, 738636, 460949, 380050, 450147, 688476, 1040329, 366917, 216180, 573352, 246916, 546324, 705212},
                {288944, 320225, 320076, 571213, 398167, 384119, 677930, 371235, 185120, 0, 708531, 687669, 690868, 606511, 509866, 173128, 290612, 295420, 323163, 595995, 585549, 504650, 498337, 562611, 914464, 460387, 239940, 447487, 196054, 477842, 579347},
                {894918, 952926, 384520, 152969, 957439, 379142, 1420869, 822042, 533720, 708531, 0, 1235053, 1357051, 281638, 468745, 768964, 459622, 901613, 482907, 1143379, 796492, 715593, 529716, 1234761, 1627418, 328945, 697197, 1138909, 668280, 880879, 1341299},
                {435833, 407010, 893925, 1123234, 279882, 931644, 276557, 418903, 830310, 687669, 1235053, 0, 151203, 1227241, 1038043, 778694, 902104, 773827, 738520, 72627, 1191115, 1110216, 734387, 162435, 421570, 876019, 845506, 269806, 587549, 549666, 331038},
                {508530, 362530, 1015923, 1245232, 401880, 1053642, 133600, 540901, 816733, 690868, 1357051, 151203, 0, 1367177, 1104004, 805624, 928800, 800756, 878456, 210830, 1218045, 1137146, 874323, 127642, 267681, 1015955, 872436, 245515, 727485, 689602, 274186},
                {752778, 918130, 326981, 112779, 967506, 222417, 1275835, 832109, 431700, 606511, 281638, 1227241, 1367177, 0, 191826, 492045, 351235, 624694, 534486, 1111276, 519573, 438674, 617434, 1158528, 1595315, 416663, 420278, 1043404, 615300, 889701, 1175265},
                {695116, 726632, 211345, 299886, 804340, 125526, 1084337, 642911, 335055, 509866, 468745, 1038043, 1104004, 191826, 0, 302827, 207257, 435476, 390508, 967298, 331886, 250987, 537157, 969310, 1321163, 409670, 231060, 854186, 471322, 745723, 986047},
                {396736, 428252, 321107, 600105, 505960, 392191, 785957, 479027, 175584, 173128, 768964, 778694, 805624, 492045, 302827, 0, 268698, 132355, 421836, 704329, 414191, 333292, 597010, 671203, 1023056, 559060, 68582, 556079, 316163, 622433, 687940},
                {520147, 551428, 121999, 309188, 629370, 79576, 909133, 553566, 107272, 290612, 459622, 902104, 928800, 351235, 207257, 268698, 0, 403356, 299972, 877140, 453506, 372607, 467772, 794036, 1145889, 340285, 208737, 678912, 352476, 655565, 810772},
                {391869, 423384, 574112, 732754, 501093, 524840, 781089, 474160, 308232, 295420, 901613, 773827, 800756, 624694, 435476, 132355, 403356, 0, 581562, 698930, 546397, 465498, 801754, 665804, 1017657, 718786, 200788, 550680, 444006, 617033, 682541},
                {416264, 605968, 198555, 395448, 478785, 277323, 942215, 343388, 274973, 323163, 482907, 738520, 878456, 534486, 390508, 421836, 299972, 581562, 0, 666162, 716025, 635126, 177391, 757544, 1150201, 143590, 484175, 661692, 241328, 403662, 864082},
                {344159, 315336, 802251, 1031560, 188208, 839970, 274589, 327229, 738636, 595995, 1143379, 72627, 210830, 1111276, 967298, 704329, 877140, 698930, 666162, 0, 1120332, 1039433, 663604, 154286, 481327, 805236, 774723, 194228, 516766, 478883, 322889},
                {809157, 840673, 540303, 627633, 918381, 454484, 1198378, 891448, 460949, 585549, 796492, 1191115, 1218045, 519573, 331886, 414191, 453506, 546397, 716025, 1120332, 0, 80620, 865602, 1083253, 1435106, 738115, 345003, 968129, 723462, 1034483, 1099990},
                {728258, 759774, 459404, 546734, 837482, 373585, 1117479, 810549, 380050, 504650, 715593, 1110216, 1137146, 438674, 250987, 333292, 372607, 465498, 635126, 1039433, 80620, 0, 785179, 1003202, 1355055, 657692, 264952, 888078, 643411, 954432, 1019939},
                {413919, 601835, 345204, 491191, 474652, 423972, 938082, 333726, 450147, 498337, 529716, 734387, 874323, 617434, 537157, 597010, 467772, 801754, 177391, 663604, 865602, 785179, 0, 753788, 1146445, 184035, 669751, 657936, 355326, 306067, 860326},
                {384312, 234273, 806774, 1057911, 278676, 870817, 133274, 418611, 688476, 562611, 1234761, 162435, 127642, 1158528, 969310, 671203, 794036, 665804, 757544, 154286, 1083253, 1003202, 753788, 0, 350841, 896073, 742175, 115254, 558172, 569720, 171344},
                {736121, 586126, 1286290, 1515599, 672247, 1324009, 294655, 811268, 1040329, 914464, 1627418, 421570, 267681, 1595315, 1321163, 1023056, 1145889, 1017657, 1150201, 481327, 1435106, 1355055, 1146445, 350841, 0, 1285570, 1093400, 466479, 997100, 959217, 429633},
                {553763, 743467, 217717, 290420, 616284, 296485, 1079714, 480887, 366917, 460387, 328945, 876019, 1015955, 416663, 409670, 559060, 340285, 718786, 143590, 805236, 738115, 657692, 184035, 896073, 1285570, 0, 547262, 799021, 378658, 540991, 1001411},
                {463548, 495064, 329300, 528338, 572772, 353912, 852769, 545839, 216180, 239940, 697197, 845506, 872436, 420278, 231060, 68582, 208737, 200788, 484175, 774723, 345003, 264952, 669751, 742175, 1093400, 547262, 0, 622592, 377925, 688946, 754453},
                {285344, 119149, 691650, 942787, 182824, 755693, 233575, 322759, 573352, 447487, 1138909, 269806, 245515, 1043404, 854186, 556079, 678912, 550680, 661692, 194228, 968129, 888078, 657936, 115254, 466479, 799021, 622592, 0, 462415, 473963, 182229},
                {176559, 340813, 279951, 530416, 327814, 343994, 688211, 192417, 246916, 196054, 668280, 587549, 727485, 615300, 471322, 316163, 352476, 444006, 241328, 516766, 723462, 643411, 355326, 558172, 997100, 378658, 377925, 462415, 0, 297249, 634391},
                {229198, 417114, 539751, 769060, 289931, 618395, 753361, 149005, 546324, 477842, 880879, 549666, 689602, 889701, 745723, 622433, 655565, 617033, 403662, 478883, 1034483, 954432, 306067, 569720, 959217, 540991, 688946, 473963, 297249, 0, 668242},
                {463188, 269522, 823510, 1074647, 364790, 887553, 135311, 525149, 705212, 579347, 1341299, 331038, 274186, 1175265, 986047, 687940, 810772, 682541, 864082, 322889, 1099990, 1019939, 860326, 171344, 429633, 1001411, 754453, 182229, 634391, 668242, 0}};
    }

    /**
     * Returns an array of distances between locations in <code>placeList</code>.<br>
     * <b>Return cases:</b>
     * <ul>
     *     <li><b>empty <code>placeList</code></b> - returns an empty array (<code>long[0][0]</code>)</li>
     *     <li><b>placeList.size() == 31</b> (default case) - returns pre-generated array (<code>long[31][31]</code>)</li>
     *     <li><b><code>placeList.size() > 31</code></b> (second most possible case) - returns complemented array (<code>long[placeList.size()][placeList.size()]</code>)</li>
     *     <li><b><code>placeList.size() < 31</code></b> - returns regenerated array (use multiple Google requests) (<code>long[placeList.size()][placeList.size()]</code>)</li>
     * </ul>
     * @return a two-dimensional array
     * @throws IllegalStateException in unpredictable cases
     */
    public static long[][] getDistancesMatrix() {
        // case: empty placeList
        if (MainGUI.placeList == null || MainGUI.placeList.isEmpty()) {
            return new long[0][0];
        }

        // case: default placeList, not generated distanceMatrix yet (it's OK) -- default situation
        if (MainGUI.placeList.size() == 31 && MainGUI.distancesMatrix == null) {
            return generateDefaultDistancesMatrix();
        }

        // case: placeList has extra items (if user added new places), it's OK -- second default situation
        if (MainGUI.placeList.size() > 31 && MainGUI.distancesMatrix.length == 31) {
            int diff = MainGUI.placeList.size() - MainGUI.distancesMatrix.length;
            long[][] newDistanceMatrix = new long[MainGUI.distancesMatrix.length + diff][MainGUI.distancesMatrix.length + diff];
            for (int i = 0; i < MainGUI.distancesMatrix.length; i++) {
                for (int j = 0; j < MainGUI.distancesMatrix[i].length; j++) {
                    newDistanceMatrix[i][j] = MainGUI.distancesMatrix[i][j];
                }
            }
            for (int i = MainGUI.distancesMatrix.length; i < newDistanceMatrix.length; i++) {
                for (int j = 0; j < newDistanceMatrix[i].length; j++) {
                    if (j == i) {
                        newDistanceMatrix[i][j] = 0L;
                    }
                    newDistanceMatrix[i][j] = GMDAPIUtility.getDistance(MainGUI.placeList.get(i), MainGUI.placeList.get(j));
                }
            }
            return newDistanceMatrix;
        }

        // case: placeList has non-standard size -- exclusive situation
        if (MainGUI.placeList.size() < 31) {
            System.out.println("WARNING: exclusive situation with distances. Multiple GoogleMaps Directions API requests has been launch.");
            long[][] distanceMatrix = new long[MainGUI.placeList.size()][MainGUI.placeList.size()];
            for (int i = 0; i < MainGUI.placeList.size(); i++) {
                for (int j = i; j < MainGUI.placeList.size(); j++) {
                    if (i == j) {
                        distanceMatrix[i][j] = 0L;
                        continue;
                    }
                    distanceMatrix[i][j] = GMDAPIUtility.getDistance(MainGUI.placeList.get(i), MainGUI.placeList.get(j));
                    System.out.println(MainGUI.placeList.get(i) + " - " + MainGUI.placeList.get(j) + ": " + distanceMatrix[i][j]);
                    distanceMatrix[j][i] = distanceMatrix[i][j];
                }
            }
            for (int i = 0; i < distanceMatrix.length; i++) {
                for (int j = 0; j < distanceMatrix[i].length; j++) {
                    System.out.print(distanceMatrix[i][j] + " ");
                }
                System.out.println();
            }
            return distanceMatrix;
        }

        // case: other situations
        throw new IllegalStateException("Distances and Places is mixed up.");
    }

    public static Place getPlaceByTitle(List<Place> placeList, String title) {
        for (Place place : placeList) {
            if (place.title.equals(title)) {
                return place;
            }
        }
        return null;
    }

    public static int getPlaceIndex(List<Place> placeList, Place place){
        for (int i = 0; i < placeList.size(); i++) {
            if (placeList.get(i).equals(place)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a random place form specified list.
     *
     * @param placeList specified list
     * @return place object
     */
    public static Place getRandomPlaceFromList(List<Place> placeList) {
        return placeList.get(MainGUI.getRandomNumberInRange(0, placeList.size()));
    }

    /**
     * Returns a random place that not equal to specified form the list.
     *
     * @param placeList the list
     * @param exclude   specified place
     * @return place object
     */
    public static Place getRandomPlaceFromListExclude(List<Place> placeList, Place exclude) {
        Place place;
        do {
            place = placeList.get(MainGUI.getRandomNumberInRange(0, placeList.size()));
        } while (place.equals(exclude));
        return place;
    }

    public static long getDistanceBetweenPlaces(Place origin, Place destination){
        int originIndex = getPlaceIndex(MainGUI.placeList, origin);
        int destinationIndex = getPlaceIndex(MainGUI.placeList, destination);
        return MainGUI.distancesMatrix[originIndex][destinationIndex];
    }

    public static boolean isPlaceInList(List<Place> placeList, Place place) {
        for (Place p : placeList) {
            if (p.equals(place)) {
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title + " (" + id + ")";
    }
}
