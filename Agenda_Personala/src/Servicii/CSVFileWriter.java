package Servicii;

import Entitati.DivertismentDinamic;
import Entitati.DivertismentStatic;
import Entitati.Intalnire;
import Entitati.Sarcina;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class CSVFileWriter {
    private static CSVFileWriter instance;

    private CSVFileWriter() {

    }

    public static CSVFileWriter getInstance() {
        if (instance == null) {
            instance = new CSVFileWriter();
        }
        return instance;
    }

    public static void scrieDate(Sarcina sarcina, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sarcina.getNumeActivitate());
            stringBuilder.append(",");
            stringBuilder.append(sarcina.getLocatie());
            stringBuilder.append(",");
            StringBuilder stringBuilder1 = new StringBuilder(String.format("%tD %tR",sarcina.getDeadline(),sarcina.getDeadline()));
            String string1 = stringBuilder1.substring(0,2);
            String string2 = stringBuilder1.substring(3,5);
            stringBuilder1.replace(0,2,string2);
            stringBuilder1.replace(3,5,string1);
            stringBuilder.append(stringBuilder1);
            stringBuilder.append(",");
            stringBuilder.append(String.valueOf(sarcina.getNumarObiective()));
            stringBuilder.append(",");
            stringBuilder.append(String.valueOf(sarcina.getNumarObiectiveRezolvate()));
            stringBuilder.append(",");
            int k=0;
            for (Pair<String,String> obiectiv : sarcina.getObiective()) {
                stringBuilder.append(obiectiv.getKey());
                stringBuilder.append("-");
                stringBuilder.append(obiectiv.getValue());
                if (k<sarcina.getObiective().size()-1){
                    stringBuilder.append("&");
                }
                k++;
            }
            printWriter.println(stringBuilder.toString());
            printWriter.flush();
            printWriter.close();

        } catch (Exception e) {
            System.out.println("Probleme cu fisierul");
        }
    }

    public static void scrieDate(Intalnire intalnire, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(intalnire.getNumeActivitate());
            stringBuilder.append(",");
            stringBuilder.append(intalnire.getLocatie());
            stringBuilder.append(",");
            stringBuilder.append(intalnire.getScopIntalnire());
            stringBuilder.append(",");
            stringBuilder.append(String.valueOf(intalnire.getNumarMembrii()));
            stringBuilder.append(",");
            int k=0;
            for (String membru : intalnire.getMembrii()) {
                stringBuilder.append(membru);
                if (k<intalnire.getMembrii().size()-1){
                    stringBuilder.append("&");
                }
                k++;
            }
            printWriter.println(stringBuilder.toString());
            printWriter.flush();
            printWriter.close();

        } catch (Exception e) {
            System.out.println("Probleme cu fisierul");
        }
    }

    public static void scrieDate(DivertismentDinamic divertisment, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(divertisment.getNumeActivitate());
            stringBuilder.append(",");
            stringBuilder.append(divertisment.getLocatie());
            stringBuilder.append(",");
            stringBuilder.append(String.valueOf(divertisment.getEntuziasm()));
            stringBuilder.append(",");
            int k=0;
            for (String mijlocTrans : divertisment.getMijloaceDeTransport()) {
                stringBuilder.append(mijlocTrans);
                if (k<divertisment.getMijloaceDeTransport().size()-1){
                    stringBuilder.append("&");
                }
                k++;
            }
            stringBuilder.append(",");
            k=0;
            for (String obiective : divertisment.getObiectiveDeVizitat()) {
                stringBuilder.append(obiective);
                if (k<divertisment.getObiectiveDeVizitat().size()-1){
                    stringBuilder.append("&");
                }
                k++;
            }

            printWriter.println(stringBuilder.toString());
            printWriter.flush();
            printWriter.close();

        } catch (Exception e) {
            System.out.println("Probleme cu fisierul");
        }
    }

    public static void scrieDate(DivertismentStatic divertisment, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(divertisment.getNumeActivitate());
            stringBuilder.append(",");
            stringBuilder.append(divertisment.getLocatie());
            stringBuilder.append(",");
            stringBuilder.append(String.valueOf(divertisment.getEntuziasm()));
            stringBuilder.append(",");
            stringBuilder.append(divertisment.getTip());
            stringBuilder.append(",");
            stringBuilder.append(divertisment.getAsteptari());

            printWriter.println(stringBuilder.toString());
            printWriter.flush();
            printWriter.close();

        } catch (Exception e) {
            System.out.println("Probleme cu fisierul");
        }
    }
}

