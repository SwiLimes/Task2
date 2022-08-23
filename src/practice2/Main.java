package practice2;

public class Main {

    public static void main(String[] args) {
        OktmoData d = new OktmoData();
        d.readFile("oktmo.csv");
        Place place = d.findPlace("Камышовка");
        System.out.println(place);                                                // Пункт 2
//        d.showAllPlaceNamesByFilter();                                            // Пункт 5
//        d.showAllSortedPlaceNames();                                              // Пункт 6
//        d.showLongestNameInRegion("Биробиджан");                                  // Пункт 8
//        d.showTableRatingNames();                                                 // Пункт 9
    }
}
