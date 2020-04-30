package Servicii;

import Entitati.DivertismentStatic;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DivertismentStaticFileReader {
    private static DivertismentStaticFileReader instance;

    private DivertismentStaticFileReader() {

    }

    public static DivertismentStaticFileReader getInstance() {
        if (instance == null) {
            instance = new DivertismentStaticFileReader();
        }
        return instance;
    }

    public static ArrayList<DivertismentStatic> citesteDate(String filePath){
        ArrayList<DivertismentStatic> divertismente = new ArrayList<>();
        try{
            Scanner scanner = new Scanner(new File(filePath));
            scanner.nextLine();
            String linie;
            while (scanner.hasNextLine()) {
                linie = scanner.nextLine();
                String[] proprietati = linie.split(",");
                divertismente.add(new DivertismentStatic(proprietati[0], proprietati[1], Integer.parseInt(proprietati[2]),
                        proprietati[3], proprietati[4]));
            }
        } catch (Exception e) {
            System.out.println("Probleme cu fisierul");
        }
        return divertismente;
    }
}
