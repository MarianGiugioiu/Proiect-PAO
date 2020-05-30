import Entitati.*;
import Servicii.*;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.RSAKeyValueResolver;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        ManagerSarcini listaSarcini =  new ManagerSarcini();

        Audit audit = Audit.getInstance();


        CSVFileWriter csvFileWriter = CSVFileWriter.getInstance();
        SarcinaFileReader sarcinaFileReader = SarcinaFileReader.getInstance();
        IntalnireFileReader intalnireFileReader = IntalnireFileReader.getInstance();
        DivertismentDinamicFileReader divertismentDinamicFileReader = DivertismentDinamicFileReader.getInstance();
        DivertismentStaticFileReader divertismentStaticFileReader = DivertismentStaticFileReader.getInstance();

        ArrayList<Sarcina> sarciniInitiale = new ArrayList<Sarcina>(SarcinaFileReader.citesteDate("src/main/java/Date/Sarcina.csv"));
        ArrayList<Intalnire> intalnireInitiale = new ArrayList<Intalnire>(IntalnireFileReader.citesteDate("src/main/java/Date/Intalnire.csv"));
        ArrayList<DivertismentDinamic> divertismenteDinamiceInitiale = new ArrayList<DivertismentDinamic>(DivertismentDinamicFileReader.citesteDate("src/main/java/Date/DivertismentDinamic.csv"));
        ArrayList<DivertismentStatic> divertismenteStaticeInitiale = new ArrayList<DivertismentStatic>(DivertismentStaticFileReader.citesteDate("src/main/java/Date/DivertismentStatic.csv"));

        /*for (Sarcina sarcina : sarciniInitiale) {
            System.out.println(sarcina.afisareActivitate());
        }*/

        /*for (Intalnire intalnire : intalnireInitiale) {
            System.out.println(intalnire.afisareActivitate());
        }*/

        /*for (DivertismentDinamic divertismentDinamic : divertismenteDinamiceInitiale) {
            System.out.println(divertismentDinamic.afisareActivitate());
        }*/

        /*for (DivertismentStatic divertismentStatic : divertismenteStaticeInitiale) {
            System.out.println(divertismentStatic.afisareActivitate());
        }*/

        try {
            listaSarcini.adaugaSarcina(new Sarcina("Proiect PAO","Acasa/PC","03/04/2020 23:59"));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            listaSarcini.adaugaSarcina(new Sarcina("Sarcina speciala",
                    "Bucuresti, Camin Th. Pallady",
                    "23/03/2020 12:30"));
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            listaSarcini.adaugaSarcina(new Sarcina("Pregatire Paste",
                    "Magazine din apropiere + acasa",
                    "18/04/2020 22:00"));
        } catch (Exception e) {
            System.out.println(e);
        }

        Sarcina sarcina1 = listaSarcini.getSarcina("Proiect PAO");
        if (sarcina1 != null) {
            sarcina1.adaugaObiectiv("Creare clase");
            Audit.scrieDate("adaugaObiectiv","src/main/java/Date/Audit.csv");
            sarcina1.adaugaObiectiv("Utilizare mostenire");
            sarcina1.adaugaObiectiv("Creare colectii");
        }

        csvFileWriter.scrieDate(sarcina1,"src/main/java/Date/Sarcina.csv");

        Sarcina sarcina2 = listaSarcini.getSarcina("Pregatire Paste");
        if (sarcina2 != null) {
            sarcina2.adaugaObiectiv("Cumparaturi");
            sarcina2.adaugaObiectiv("Curatenie");
            sarcina2.adaugaObiectiv("Pregatire mancare");
        }
        csvFileWriter.scrieDate(sarcina2,"src/main/java/Date/Sarcina.csv");

        System.out.println(listaSarcini.afisareSarcini());

        if (listaSarcini.getSarcina("Sarcina speciala").esteExpirat()) {
            System.out.println("Sarcina a expirat");
        } else {
            System.out.println("Sarcina inca nu a expirat");
        }
        Audit.scrieDate("esteExpirat","src/main/java/Date/Audit.csv");

        Intalnire intalnire = new Intalnire("Planificare excursie", "Valcea","Vom alege locatia si ne vom interesa de cazare si mijloace de transport");
        intalnire.adaugaMembru("Alin");
        intalnire.adaugaMembru("Vlad");
        csvFileWriter.scrieDate(intalnire,"src/main/java/Date/Intalnire.csv");


        DivertismentDinamic divertisment1 = new DivertismentDinamic("Vizita gradina zoologica","Gradina zoologica Bucuresti",
                7, Arrays.asList("metrou", "autobuz"), Arrays.asList("lei","maimute"));

        csvFileWriter.scrieDate(divertisment1,"src/main/java/Date/DivertismentDinamic.csv");

        DivertismentStatic divertisment2 = new DivertismentStatic("Avatar2", "CinemaCity Valcea",8,"film", "Sa adauge mai multa istorie a Pandorei");

        csvFileWriter.scrieDate(divertisment2,"src/main/java/Date/DivertismentStatic.csv");
        try {
            program.adaugaPlanificare("03/04/2020 10:00","03/04/2020 16:00", sarcina1);
        } catch (Exception e) {
            System.out.println(e);
        }
        Audit.scrieDate("adaugaPlanificare","src/main/java/Date/Audit.csv");

        try {
            program.adaugaPlanificare("01/04/2020 10:00", "01/04/2020 20:00", divertisment1);
        } catch (Exception e) {
            System.out.println(e);
        }
        Audit.scrieDate("adaugaPlanificare","src/main/java/Date/Audit.csv");

        try {
            program.adaugaPlanificare("01/04/2020 11:00", "01/04/2020 13:00", sarcina1);
        } catch (Exception e) {
            System.out.println(e);
        }
        Audit.scrieDate("adaugaPlanificare","src/main/java/Date/Audit.csv");

        try {
            program.adaugaPlanificare("01/04/2020 14:00", "01/04/2020 16:00", divertisment2);
        } catch (Exception e) {
            System.out.println(e);
        }
        Audit.scrieDate("adaugaPlanificare","src/main/java/Date/Audit.csv");

        try {
            program.adaugaPlanificare("02/04/2020 15:00", "02/04/2020 20:00", intalnire);
        } catch (Exception e) {
            System.out.println(e);
        }
        Audit.scrieDate("adaugaPlanificare","src/main/java/Date/Audit.csv");

        try {
            program.adaugaPlanificare("03/04/2020 12:00", "03/04/2020 20:00", sarcina2);
        } catch (Exception e) {
            System.out.println(e);
        }
        Audit.scrieDate("adaugaPlanificare","src/main/java/Date/Audit.csv");

        Set<Planificare> listaOptima = new TreeSet(program.listaNumarMaximPlanificari());
        for (Planificare planificare : listaOptima) {
            System.out.println(planificare.afisarePlanificare());
        }
        Audit.scrieDate("listaNumarMaximPlanificari","src/main/java/Date/Audit.csv");

        Set<Planificare> listaInterval = new TreeSet(program.listaPlanificariInInterval("02/04/2020 08:00",
                "03/04/2020 20:00"));
        for (Planificare planificare : listaInterval) {
            System.out.println(planificare.afisarePlanificare());
        }
        Audit.scrieDate("listaPlanificariInInterval","src/main/java/Date/Audit.csv");

        program.stergePlanificariInInterval("03/04/2020 18:00", "03/04/2020 20:00");
        Audit.scrieDate("stergePlanificariInInterval","src/main/java/Date/Audit.csv");

        Planificare planificare1 = program.getPlanificareSarcina("Proiect PAO","01/04/2020 11:00");
        if (planificare1 != null) {
            Sarcina sarcina3 = (Sarcina) planificare1.getActivitate();
            sarcina3.rezolvaObiectiv(0);
            sarcina3.rezolvaObiectiv(1);
            try{
                planificare1.setRezultat("Am completat primii doi pasi");
                Audit.scrieDate("setRezultat","src/main/java/Date/Audit.csv");
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        Planificare planificare2 = program.getPlanificareSarcina("Proiect PAO","03/04/2020 10:00");
        if (planificare2 != null) {
            Sarcina sarcina4 = (Sarcina) planificare2.getActivitate();
            sarcina4.rezolvaObiectiv(2);
            try {
                planificare2.setRezultat("Am terminat proiectul");
                Audit.scrieDate("setRezultat","src/main/java/Date/Audit.csv");
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        Planificare planificare3 = program.getPlanificare("Avatar2");
        if(planificare3 != null) {
            try {
                planificare3.setRezultat("Am vazut filmul, a fost foarte bun");
                Audit.scrieDate("setRezultat","src/main/java/Date/Audit.csv");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        System.out.println(program.numarPlanificariRatate());
        Audit.scrieDate("numarPlanificariRatate","src/main/java/Date/Audit.csv");

        listaSarcini.updateStatus();
        Audit.scrieDate("updateStatus","src/main/java/Date/Audit.csv");
        System.out.println(listaSarcini.afisareSarcini());

    }
}
