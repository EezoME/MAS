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
        return new long[][]{{0L, 179850L, 466934L, 717204L, 156864L, 530098L, 514756L, 86375L, 414600L, 288944L, 894918L, 435833L, 508530L, 752778L, 695116L, 396736L, 520147L, 391869L, 416264L, 344159L, 809157L, 728258L, 413919L, 384312L, 736121L, 553763L, 463548L, 285344L, 176559L, 229198L, 463188L},
                {179850L, 0L, 565062L, 816981L, 127238L, 628226L, 364761L, 267961L, 445881L, 320225L, 952926L, 407010L, 362530L, 918130L, 726632L, 428252L, 551428L, 423384L, 605968L, 315336L, 840673L, 759774L, 601835L, 234273L, 586126L, 743467L, 495064L, 119149L, 340813L, 417114L, 269522L},
                {466934L, 565062L, 0L, 248575L, 614957L, 85310L, 937262L, 477496L, 145502L, 320076L, 384520L, 893925L, 1015923L, 326981L, 211345L, 321107L, 121999L, 574112L, 198555L, 802251L, 540303L, 459404L, 345204L, 806774L, 1286290L, 217717L, 329300L, 691650L, 279951L, 539751L, 823510L},
                {717204L, 816981L, 248575L, 0L, 844266L, 229895L, 1309049L, 706805L, 396639L, 571213L, 152969L, 1123234L, 1245232L, 112779L, 299886L, 600105L, 309188L, 732754L, 395448L, 1031560L, 627633L, 546734L, 491191L, 1057911L, 1515599L, 290420L, 528338L, 942787L, 530416L, 769060L, 1074647L},
                {156864L, 127238L, 614957L, 844266L, 0L, 652273L, 409120L, 140778L, 523823L, 398167L, 957439L, 279882L, 401880L, 967506L, 804340L, 505960L, 629370L, 501093L, 478785L, 188208L, 918381L, 837482L, 474652L, 278676L, 672247L, 616284L, 572772L, 182824L, 327814L, 289931L, 364790L},
                {530098L, 628226L, 85310L, 229895L, 652273L, 0L, 1001305L, 515215L, 209545L, 384119L, 379142L, 931644L, 1053642L, 222417L, 125526L, 392191L, 79576L, 524840L, 277323L, 839970L, 454484L, 373585L, 423972L, 870817L, 1324009L, 296485L, 353912L, 755693L, 343994L, 618395L, 887553L},
                {514756L, 364761L, 937262L, 1309049L, 409120L, 1001305L, 0L, 604208L, 803586L, 677930L, 1420869L, 276557L, 133600L, 1275835L, 1084337L, 785957L, 909133L, 781089L, 942215L, 274589L, 1198378L, 1117479L, 938082L, 133274L, 294655L, 1079714L, 852769L, 233575L, 688211L, 753361L, 135311L},
                {86375L, 267961L, 477496L, 706805L, 140778L, 515215L, 604208L, 0L, 418807L, 371235L, 822042L, 418903L, 540901L, 832109L, 642911L, 479027L, 553566L, 474160L, 343388L, 327229L, 891448L, 810549L, 333726L, 418611L, 811268L, 480887L, 545839L, 322759L, 192417L, 149005L, 525149L},
                {414600L, 445881L, 145502L, 396639L, 523823L, 209545L, 803586L, 418807L, 0L, 185120L, 533720L, 830310L, 816733L, 431700L, 335055L, 175584L, 107272L, 308232L, 274973L, 738636L, 460949L, 380050L, 450147L, 688476L, 1040329L, 366917L, 216180L, 573352L, 246916L, 546324L, 705212L},
                {288944L, 320225L, 320076L, 571213L, 398167L, 384119L, 677930L, 371235L, 185120L, 0L, 708531L, 687669L, 690868L, 606511L, 509866L, 173128L, 290612L, 295420L, 323163L, 595995L, 585549L, 504650L, 498337L, 562611L, 914464L, 460387L, 239940L, 447487L, 196054L, 477842L, 579347L},
                {894918L, 952926L, 384520L, 152969L, 957439L, 379142L, 1420869L, 822042L, 533720L, 708531L, 0L, 1235053L, 1357051L, 281638L, 468745L, 768964L, 459622L, 901613L, 482907L, 1143379L, 796492L, 715593L, 529716L, 1234761L, 1627418L, 328945L, 697197L, 1138909L, 668280L, 880879L, 1341299L},
                {435833L, 407010L, 893925L, 1123234L, 279882L, 931644L, 276557L, 418903L, 830310L, 687669L, 1235053L, 0L, 151203L, 1227241L, 1038043L, 778694L, 902104L, 773827L, 738520L, 72627L, 1191115L, 1110216L, 734387L, 162435L, 421570L, 876019L, 845506L, 269806L, 587549L, 549666L, 331038L},
                {508530L, 362530L, 1015923L, 1245232L, 401880L, 1053642L, 133600L, 540901L, 816733L, 690868L, 1357051L, 151203L, 0L, 1367177L, 1104004L, 805624L, 928800L, 800756L, 878456L, 210830L, 1218045L, 1137146L, 874323L, 127642L, 267681L, 1015955L, 872436L, 245515L, 727485L, 689602L, 274186L},
                {752778L, 918130L, 326981L, 112779L, 967506L, 222417L, 1275835L, 832109L, 431700L, 606511L, 281638L, 1227241L, 1367177L, 0L, 191826L, 492045L, 351235L, 624694L, 534486L, 1111276L, 519573L, 438674L, 617434L, 1158528L, 1595315L, 416663L, 420278L, 1043404L, 615300L, 889701L, 1175265L},
                {695116L, 726632L, 211345L, 299886L, 804340L, 125526L, 1084337L, 642911L, 335055L, 509866L, 468745L, 1038043L, 1104004L, 191826L, 0L, 302827L, 207257L, 435476L, 390508L, 967298L, 331886L, 250987L, 537157L, 969310L, 1321163L, 409670L, 231060L, 854186L, 471322L, 745723L, 986047L},
                {396736L, 428252L, 321107L, 600105L, 505960L, 392191L, 785957L, 479027L, 175584L, 173128L, 768964L, 778694L, 805624L, 492045L, 302827L, 0L, 268698L, 132355L, 421836L, 704329L, 414191L, 333292L, 597010L, 671203L, 1023056L, 559060L, 68582L, 556079L, 316163L, 622433L, 687940L},
                {520147L, 551428L, 121999L, 309188L, 629370L, 79576L, 909133L, 553566L, 107272L, 290612L, 459622L, 902104L, 928800L, 351235L, 207257L, 268698L, 0L, 403356L, 299972L, 877140L, 453506L, 372607L, 467772L, 794036L, 1145889L, 340285L, 208737L, 678912L, 352476L, 655565L, 810772L},
                {391869L, 423384L, 574112L, 732754L, 501093L, 524840L, 781089L, 474160L, 308232L, 295420L, 901613L, 773827L, 800756L, 624694L, 435476L, 132355L, 403356L, 0L, 581562L, 698930L, 546397L, 465498L, 801754L, 665804L, 1017657L, 718786L, 200788L, 550680L, 444006L, 617033L, 682541L},
                {416264L, 605968L, 198555L, 395448L, 478785L, 277323L, 942215L, 343388L, 274973L, 323163L, 482907L, 738520L, 878456L, 534486L, 390508L, 421836L, 299972L, 581562L, 0L, 666162L, 716025L, 635126L, 177391L, 757544L, 1150201L, 143590L, 484175L, 661692L, 241328L, 403662L, 864082L},
                {344159L, 315336L, 802251L, 1031560L, 188208L, 839970L, 274589L, 327229L, 738636L, 595995L, 1143379L, 72627L, 210830L, 1111276L, 967298L, 704329L, 877140L, 698930L, 666162L, 0L, 1120332L, 1039433L, 663604L, 154286L, 481327L, 805236L, 774723L, 194228L, 516766L, 478883L, 322889L},
                {809157L, 840673L, 540303L, 627633L, 918381L, 454484L, 1198378L, 891448L, 460949L, 585549L, 796492L, 1191115L, 1218045L, 519573L, 331886L, 414191L, 453506L, 546397L, 716025L, 1120332L, 0L, 80620L, 865602L, 1083253L, 1435106L, 738115L, 345003L, 968129L, 723462L, 1034483L, 1099990L},
                {728258L, 759774L, 459404L, 546734L, 837482L, 373585L, 1117479L, 810549L, 380050L, 504650L, 715593L, 1110216L, 1137146L, 438674L, 250987L, 333292L, 372607L, 465498L, 635126L, 1039433L, 80620L, 0L, 785179L, 1003202L, 1355055L, 657692L, 264952L, 888078L, 643411L, 954432L, 1019939L},
                {413919L, 601835L, 345204L, 491191L, 474652L, 423972L, 938082L, 333726L, 450147L, 498337L, 529716L, 734387L, 874323L, 617434L, 537157L, 597010L, 467772L, 801754L, 177391L, 663604L, 865602L, 785179L, 0L, 753788L, 1146445L, 184035L, 669751L, 657936L, 355326L, 306067L, 860326L},
                {384312L, 234273L, 806774L, 1057911L, 278676L, 870817L, 133274L, 418611L, 688476L, 562611L, 1234761L, 162435L, 127642L, 1158528L, 969310L, 671203L, 794036L, 665804L, 757544L, 154286L, 1083253L, 1003202L, 753788L, 0L, 350841L, 896073L, 742175L, 115254L, 558172L, 569720L, 171344L},
                {736121L, 586126L, 1286290L, 1515599L, 672247L, 1324009L, 294655L, 811268L, 1040329L, 914464L, 1627418L, 421570L, 267681L, 1595315L, 1321163L, 1023056L, 1145889L, 1017657L, 1150201L, 481327L, 1435106L, 1355055L, 1146445L, 350841L, 0L, 1285570L, 1093400L, 466479L, 997100L, 959217L, 429633L},
                {553763L, 743467L, 217717L, 290420L, 616284L, 296485L, 1079714L, 480887L, 366917L, 460387L, 328945L, 876019L, 1015955L, 416663L, 409670L, 559060L, 340285L, 718786L, 143590L, 805236L, 738115L, 657692L, 184035L, 896073L, 1285570L, 0L, 547262L, 799021L, 378658L, 540991L, 1001411L},
                {463548L, 495064L, 329300L, 528338L, 572772L, 353912L, 852769L, 545839L, 216180L, 239940L, 697197L, 845506L, 872436L, 420278L, 231060L, 68582L, 208737L, 200788L, 484175L, 774723L, 345003L, 264952L, 669751L, 742175L, 1093400L, 547262L, 0L, 622592L, 377925L, 688946L, 754453L},
                {285344L, 119149L, 691650L, 942787L, 182824L, 755693L, 233575L, 322759L, 573352L, 447487L, 1138909L, 269806L, 245515L, 1043404L, 854186L, 556079L, 678912L, 550680L, 661692L, 194228L, 968129L, 888078L, 657936L, 115254L, 466479L, 799021L, 622592L, 0L, 462415L, 473963L, 182229L},
                {176559L, 340813L, 279951L, 530416L, 327814L, 343994L, 688211L, 192417L, 246916L, 196054L, 668280L, 587549L, 727485L, 615300L, 471322L, 316163L, 352476L, 444006L, 241328L, 516766L, 723462L, 643411L, 355326L, 558172L, 997100L, 378658L, 377925L, 462415L, 0L, 297249L, 634391L},
                {229198L, 417114L, 539751L, 769060L, 289931L, 618395L, 753361L, 149005L, 546324L, 477842L, 880879L, 549666L, 689602L, 889701L, 745723L, 622433L, 655565L, 617033L, 403662L, 478883L, 1034483L, 954432L, 306067L, 569720L, 959217L, 540991L, 688946L, 473963L, 297249L, 0L, 668242L},
                {463188L, 269522L, 823510L, 1074647L, 364790L, 887553L, 135311L, 525149L, 705212L, 579347L, 1341299L, 331038L, 274186L, 1175265L, 986047L, 687940L, 810772L, 682541L, 864082L, 322889L, 1099990L, 1019939L, 860326L, 171344L, 429633L, 1001411L, 754453L, 182229L, 634391L, 668242L, 0L}};
    }

    /**
     * Returns an array of distances between locations in <code>placeList</code>.<br>
     * <b>Return cases:</b>
     * <ul>
     * <li><b>empty <code>placeList</code></b> - returns an empty array (<code>long[0][0]</code>)</li>
     * <li><b>placeList.size() == 31</b> (default case) - returns pre-generated array (<code>long[31][31]</code>)</li>
     * <li><b><code>placeList.size() > 31</code></b> (second most possible case) - returns complemented array (<code>long[placeList.size()][placeList.size()]</code>)</li>
     * <li><b><code>placeList.size() < 31</code></b> - returns regenerated array (use multiple Google requests) (<code>long[placeList.size()][placeList.size()]</code>)</li>
     * </ul>
     *
     * @return a two-dimensional array
     * @throws IllegalStateException in unpredictable cases
     */
    public static long[][] getDistancesMatrix() {
        // case: empty placeList
        if (MainGUI.placeList == null || MainGUI.placeList.isEmpty()) {
            return new long[0][0];
        }

        // case: default placeList, not generated distanceMatrix yet (it's OK) -- default situation, calls when program starts
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
            System.out.println("WARNING: exclusive situation with distances. Multiple GoogleMaps Directions API requests has been launched.");
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

    public static int getPlaceIndex(List<Place> placeList, Place place) {
        for (int i = 0; i < placeList.size(); i++) {
            if (placeList.get(i).equals(place)) {
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

    public static long getDistanceBetweenPlaces(Place origin, Place destination) {
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
