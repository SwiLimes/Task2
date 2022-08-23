package practice2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OktmoData {
    private List<Place> places = new ArrayList<>();

    public void readFile(String filename) {
        places.clear();
        Path p = Paths.get(filename);
        try {
            List<String> lines = Files.readAllLines(p, Charset.forName("cp1251"));
            for (String s : lines) {
                readLine(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //"01";"512";"000";"146";"9";"2";"п Калиновка";;;"493";"3";12.08.2021;01.01.2022
    private static final Pattern RE = Pattern.compile("\"(\\d+)\";\"(\\d+)\";\"(\\d+)\";\"(\\d+)\";.+\"([а-я].?)\\s([А-Я-а-я].*?)\"");

    private void readLine(String s) {
        Matcher m = RE.matcher(s);
        if (m.find()) {
            places.add(new Place(
                    Integer.parseInt(m.group(1)),
                    Integer.parseInt(m.group(2)),
                    Integer.parseInt(m.group(3)),
                    Integer.parseInt(m.group(4)),
                    m.group(5), m.group(6)
            ));
        }
    }

    public List<Place> getPlaces() {
        return places;
    }

    /**
     * Поиск место по имени
     * (Пункт 2)
     */
    public Place findPlace(String name) {
        return places
                .stream()
                .filter(place -> place.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Поиск всех НП района
     * (Пункт 3)
     */
    public Stream<Place> getAllPlacesOfDistrict(int code1, int code2) {
        return places
                .stream()
                .filter(place -> (place.getCode1() == code1 && place.getCode2() == code2 && place.getCode4() != 0));

    }

    /**
     * Поиск всех НП региона
     * (Пункт 4)
     */
    public Stream<Place> getAllPlacesOfRegion(int code1) {
        return places
                .stream()
                .filter(place -> (place.getCode1() == code1 && place.getCode4() != 0));
    }


    /**
     * Отображение всех названий НП, заканчивающихся на -ово или содержащих дефис
     * (Пункт 5)
     */
    public void showAllPlaceNamesByFilter() {
        places
                .stream()
                .map(place -> place.getName())
                .filter(name -> name.endsWith("ово") || name.contains("-"))
                .forEach(name -> System.out.println(name));
    }

    /**
     * Отображение списка всех уникальных названий НП, отсортированного по алфавиту
     * (Пункт 6)
     */
    public void showAllSortedPlaceNames() {
        places
                .stream()
                .map(place -> place.getName())
                .sorted()
                .distinct()
                .forEach(name -> System.out.println(name));
    }


    /**
     * Отображение самого длинного названия НП в регионе
     * (Пункт 8)
     */
    public void showLongestNameInRegion(String region) {
        int code1 = findPlace(region).getCode1();

        System.out.println(
                getAllPlacesOfRegion(code1)
                        .map(place -> place.getName())
                        .max(Comparator.comparing(name -> name.length()))
                        .orElse(null)
        );

    }


    /**
     * Отображение таболицы популярности названий НП (название - количество),
     * отсортированной по убыванию количеств
     * (Пункт 9)
     */
    public void showTableRatingNames() {
        places
                .stream()
                .map(place -> place.getName())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue((a,b) -> (int) (b - a)))
                .limit(10)
                .forEach(place -> System.out.printf("НП '%s' найден '%d' раз\n", place.getKey(), place.getValue()));

    }
}
