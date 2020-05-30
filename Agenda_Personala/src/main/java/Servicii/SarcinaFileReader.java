package Servicii;

import Entitati.Sarcina;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class SarcinaFileReader {
    private static SarcinaFileReader instance;

    private SarcinaFileReader() {

    }

    public static SarcinaFileReader getInstance() {
        if (instance == null) {
            instance = new SarcinaFileReader();
        }
        return instance;
    }

    public static ArrayList<Sarcina> citesteDate(String filePath){
        ArrayList<Sarcina> sarcini = new ArrayList<Sarcina>();
        try{
            Scanner scanner = new Scanner(new File(filePath));
            scanner.nextLine();
            String linie;
            while (scanner.hasNextLine()) {
                linie = scanner.nextLine();
                String[] proprietati = linie.split(",");
                ArrayList<Pair<String, String>> listaObiective = new ArrayList<Pair<String, String>>();
                String[] obiective = proprietati[5].split("&");
                for (String obiectiv : obiective) {
                    String[] val = obiectiv.split("-");
                    listaObiective.add(new Pair<String, String>(val[0],val[1]));
                }
                sarcini.add(new Sarcina(proprietati[0], proprietati[1], proprietati[2],
                        Integer.parseInt(proprietati[3]),Integer.parseInt(proprietati[4]),listaObiective));
            }
        } catch (Exception e) {
            System.out.println("Probleme cu fisierul");
        }
        return sarcini;
    }
}
