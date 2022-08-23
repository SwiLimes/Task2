package practice2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Tester {
    static OktmoData d;
    @BeforeAll
    static void read() {
        d=new OktmoData();
        d.readFile("oktmo.csv");
    }
    @Test void test() {
        assertEquals(170660, d.getPlaces().size());
    }

    @Test void findPlaceTest() {
        int exceptedCode1 = 8;
        int exceptedCode2 = 655;
        int exceptedCode3 = 448;
        int exceptedCode4 = 0;
        String exceptedStatus = "с";
        String exceptedName = "Некрасовка";

        Place place = d.findPlace("Некрасовка");

        assertEquals(exceptedCode1, place.getCode1());
        assertEquals(exceptedCode2, place.getCode2());
        assertEquals(exceptedCode3, place.getCode3());
        assertEquals(exceptedCode4, place.getCode4());
        assertEquals(exceptedStatus, place.getStatus());
        assertEquals(exceptedName, place.getName());
    }
}
