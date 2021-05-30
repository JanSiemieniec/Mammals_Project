import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Map<String, Mammals> map = new TreeMap<>((o1, o2) -> {              //TreeMap ponieważ mamy Key-Value, kolejność i sort jest istotny
            if (o1.length() >= o2.length())                                 //comparator
                return 1;
            return -1;
        });

        Stream<String> streamFile = Files.lines(Paths.get("mammals.txt"));     //strumień plikowy
        streamFile
                .skip(1)
                .forEach(n -> {
                    List<Double> list = new ArrayList<>();
                    int indexFirst = 0;
                    for (int i = 0; i < n.length(); i++)                        //wczytanie klucza, nazwy ssaka
                        if (n.charAt(i) == ';') {
                            indexFirst = i;
                            break;
                        }
                    String key = n.substring(0, indexFirst);
                    Stream.of(n.split(";"))
                            .skip(1)
                            .map(el -> {                                         //zastąpienie NA zerami
                                        if (el.equals("NA")) {
                                            el = "0";
                                        }
                                        return el;
                                    }
                            )
                            .forEach(el -> {
                                Stream.of(el)
                                        .map(dzies -> {                         //wczytanie liczb
                                            double liczba = 0;
                                            int index = 0;
                                            for (int i = 0; i < dzies.length(); i++) {
                                                if (dzies.charAt(i) == '.')
                                                    index = i;
                                            }
                                            int potega;
                                            if (index == 0)
                                                potega = dzies.length() + 2;        //jest +2 ponieważ był błąd przy liczeniu zamiast (0,7 było 0,69999995)
                                            else potega = index + 2;

                                            for (int i = 0; i < dzies.length(); i++) {
                                                if (dzies.charAt(i) != '.')
                                                    liczba += ((dzies.charAt(i)) - 48) * Math.pow(10, potega--);
                                            }
                                            liczba /= 1000;
                                            list.add(liczba);
                                            return liczba;
                                        }).forEach(x -> System.out.print(""));      //jak tego nie ma to program się wysypuje, close nie działa
                            });

                    map.put(key, new Mammals(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5),      //dodawanie do mapy
                            list.get(6), (list.get(7)), list.get(8), list.get(9)));
                });
        map.forEach((key, val) -> {
            Colours.printColouredWithoutLn(key, Colours.green);                                             //drukowanie mapy, nazwa na kolor zielony
            System.out.println("\n\t" + val);
        });

        map.                                                                                //szukanie zwierzęcia z maksymalną wagą
                entrySet().
                stream().
                max((Map.Entry<String, Mammals> e1, Map.Entry<String, Mammals> e2) -> {
                    if (e1.getValue().getBody_wt() > e2.getValue().getBody_wt())
                        return 1;
                    return -1;
                }).stream().forEach(x -> {
            System.out.print("\n\nNajwiększą masę ciała ma ");
            Colours.printColouredWithoutLn(x.getKey(), Colours.blue);
            System.out.println(" z masą wynoszącą " + x.getValue().getBody_wt() + "kg");
        });

        map.
                entrySet()                                      //3 zwierzęcia z największym % snu sennego w śnie
                .stream()
                .filter(x -> x.getValue().getDreaming() > 0 && x.getValue().getTotal_sleep() > 0)
                .sorted((Map.Entry<String, Mammals> e1, Map.Entry<String, Mammals> e2) -> {
                    if (e1.getValue().getDreaming() / e1.getValue().getTotal_sleep() < e2.getValue().getDreaming() / e2.getValue().getTotal_sleep())
                        return 1;
                    return -1;
                })
                .limit(3)
                .forEach(x -> {
                    Colours.printColouredWithoutLn(x.getKey(), Colours.blue);
                    System.out.println(" śpi sennie przez " + Math.round(x.getValue().getDreaming() * 100 / x.getValue().getTotal_sleep()) + "% snu");
                });

        Colours.printColoured("\nZwierzęta żyjące ponad średnią: ", Colours.green);               //zwierzęta żyjące ponad średnią
        map.entrySet().stream().forEach(x -> {
                    double average = map.entrySet().stream().mapToDouble(y -> y.getValue().getLife_span()).sum() / map.entrySet().stream().count(); //zmiana strumienia na double Life_spanu i podzielenie przez liczbe wierszy
                    if (x.getValue().getLife_span() > average) {
                        Colours.printColouredWithoutLn(x.getKey() + " ", Colours.blue);
                    }
                }
        );
        List<Mammals> mammalsList = new ArrayList<>();          //wczytanie 3 zwierząt z największą masą do listy
        mammalsList =
                map.entrySet().stream().
                        map(x -> x.getValue())
                        .sorted()                               //sort działa za pomocą Comparable w klasie Mammals
                        .limit(3)
                        .collect(Collectors.toList());
        Colours.printColoured("\n\nZwierzęta w ZOO", Colours.red);
        mammalsList.stream().forEach(x -> System.out.println("" + x));


        Colours.printColoured("\nDla ile zwierząt chcesz przeprowadzić symulacje? (1-3)", Colours.magenta);   //symulacje zwierząt
        double weightBeg2 = mammalsList.get(2).getBody_wt();
        double weightBeg1 = mammalsList.get(1).getBody_wt();
        double weightBeg = mammalsList.get(0).getBody_wt();
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextInt()) {
            case 0:
                Colours.printColoured("Koniec symulacji dla 0 zwierząt", Colours.red);
                break;
            case 3:                                                                     //nie ma break bo przechodzimy w dół
                DataBufor db2 = new DataBufor();
                Thread karmienie2 = new Karmienie(db2, mammalsList.get(2), 2);
                Thread spalanie2 = new Spalanie(db2, mammalsList.get(2), 2);
                karmienie2.start();
                spalanie2.start();
            case 2:
                DataBufor db1 = new DataBufor();
                Thread karmienie1 = new Karmienie(db1, mammalsList.get(1), 1);
                Thread spalanie1 = new Spalanie(db1, mammalsList.get(1), 1);
                karmienie1.start();
                spalanie1.start();
            case 1:
                DataBufor db = new DataBufor();
                Thread karmienie = new Karmienie(db, mammalsList.get(0), 0);
                Thread spalanie = new Spalanie(db, mammalsList.get(0), 0);
                karmienie.start();
                spalanie.start();
                karmienie.join();                                                       //tylko jeden joinujemy (ten pierwszy)
                spalanie.join();
                sleep(200);
                Colours.printColoured(weightBeg == mammalsList.get(0).getBody_wt() ? "Dla zwierzęcia pierwszego nie została wykonana symulacja" : "Przed symulacją pierwsze zwierze ważyło " + (int) (weightBeg) + "kg, a po symulacji " + Math.round(mammalsList.get(0).getBody_wt()) + "kg", Colours.orange);
                Colours.printColoured(weightBeg1 == mammalsList.get(1).getBody_wt() ? "Dla zwierzęcia drugiego nie została wykonana symulacja" : "Przed symulacją drugie zwierze ważyło " + (int) (weightBeg1) + "kg, a po symulacji " + Math.round(mammalsList.get(1).getBody_wt()) + "kg", Colours.orange);
                Colours.printColoured(weightBeg2 == mammalsList.get(2).getBody_wt() ? "Dla zwierzęcia trzeciego nie została wykonana symulacja" : "Przed symulacją trzecie zwierze ważyło " + (int) (weightBeg2) + "kg, a po symulacji " + Math.round(mammalsList.get(2).getBody_wt()) + "kg", Colours.orange);
                break;
            default:
                Colours.printColoured("Podałeś niepoprawną liczbę", Colours.red);
                break;
        }
    }
}
