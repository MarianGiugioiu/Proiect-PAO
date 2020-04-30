package Servicii;

import Entitati.DivertismentDinamic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DivertismentDinamicFileReader {
    private static DivertismentDinamicFileReader instance;

    private DivertismentDinamicFileReader() {

    }

    public static DivertismentDinamicFileReader getInstance() {
        if (instance == null) {
            instance = new DivertismentDinamicFileReader();
        }
        return instance;
    }

    public static ArrayList<DivertismentDinamic> citesteDate(String filePath){
        ArrayList<DivertismentDinamic> divertismente = new ArrayList<>();
        try{
            Scanner scanner = new Scanner(new File(filePath));
            scanner.nextLine();
            String linie;
            while (scanner.hasNextLine()) {
                linie = scanner.nextLine();
                String[] proprietati = linie.split(",");
                ArrayList<String> listaMijlTrans = new ArrayList<>();
                String[] mijloaceTransport = proprietati[3].split("&");
                listaMijlTrans.addAll(Arrays.asList(mijloaceTransport));
                ArrayList<String> listaObiective = new ArrayList<>();
                String[] obiective = proprietati[4].split("&");
                listaObiective.addAll(Arrays.asList(obiective));
                divertismente.add(new DivertismentDinamic(proprietati[0], proprietati[1], Integer.parseInt(proprietati[2]),
                        listaMijlTrans, listaObiective));
            }
        } catch (Exception e) {
            System.out.println("Probleme cu fisierul");
        }
        return divertismente;
    }
}
