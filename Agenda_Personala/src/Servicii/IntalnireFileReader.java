package Servicii;

import Entitati.Intalnire;
import Entitati.Sarcina;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class IntalnireFileReader {
    private static IntalnireFileReader instance;

    private IntalnireFileReader() {

    }

    public static IntalnireFileReader getInstance() {
        if (instance == null) {
            instance = new IntalnireFileReader();
        }
        return instance;
    }

    public static ArrayList<Intalnire> citesteDate(String filePath){
        ArrayList<Intalnire> intalniri = new ArrayList<>();
        try{
            Scanner scanner = new Scanner(new File(filePath));
            scanner.nextLine();
            String linie;
            while (scanner.hasNextLine()) {
                linie = scanner.nextLine();
                String[] proprietati = linie.split(",");
                ArrayList<String> listamembrii = new ArrayList<>();
                String[] membrii = proprietati[4].split("&");
                listamembrii.addAll(Arrays.asList(membrii));
                intalniri.add(new Intalnire(proprietati[0], proprietati[1], proprietati[2],
                        Integer.parseInt(proprietati[3]),listamembrii));
            }
        } catch (Exception e) {
            System.out.println("Probleme cu fisierul");
        }
        return intalniri;
    }
}
