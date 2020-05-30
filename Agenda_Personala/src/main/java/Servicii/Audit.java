package Servicii;

import Entitati.Sarcina;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Audit {
    private static Audit instance;

    private Audit () {

    }

    public static Audit getInstance() {
        if (instance == null) {
            instance = new Audit();
        }
        return instance;
    }

    public static void scrieDate(String numeFunctie, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            StringBuilder stringBuilder = new StringBuilder(numeFunctie);
            stringBuilder.append(",");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            stringBuilder.append(dateFormat.format(new Date()));
            printWriter.println(stringBuilder.toString());
            printWriter.flush();
            printWriter.close();

        } catch (Exception e) {
            System.out.println("Probleme cu fisierul");
        }
    }
}
