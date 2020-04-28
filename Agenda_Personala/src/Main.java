import javax.xml.soap.SAAJMetaFactory;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        ManagerSarcini listaSarcini =  new ManagerSarcini();

        try {
            listaSarcini.adaugaSarcina(new Sarcina("Proiect PAO","Acasa, PC","03/04/2020 23:59"));
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
            sarcina1.adaugaObiectiv("Utilizare mostenire");
            sarcina1.adaugaObiectiv("Creare colectii");
        }

        Sarcina sarcina2 = listaSarcini.getSarcina("Pregatire Paste");
        if (sarcina2 != null) {
            sarcina2.adaugaObiectiv("Cumparaturi");
            sarcina2.adaugaObiectiv("Curatenie");
            sarcina2.adaugaObiectiv("Pregatire mancare");
        }

        System.out.println(listaSarcini.afisareSarcini());
        if (listaSarcini.getSarcina("Sarcina speciala").esteExpirat()) {
            System.out.println("Sarcina a expirat");
        } else {
            System.out.println("Sarcina inca nu a expirat");
        }

        Intalnire intalnire = new Intalnire("Planificare excursie", "Valcea","Vom alege locatia si ne vom interesa de cazare si mijloace de transport");
        intalnire.adaugaMembru("Alin");
        intalnire.adaugaMembru("Vlad");

        DivertismentDinamic divertisment1 = new DivertismentDinamic("Vizita gradina zoologica","Gradina zoologica Bucuresti",
                7, Arrays.asList("metrou", "autobuz"), Arrays.asList("lei","maimute"));

        DivertismentStatic divertisment2 = new DivertismentStatic("Avatar2", "CinemaCity, Valcea",8,"film", "Sa adauge mai multa istorie a Pandorei");

        try {
            program.adaugaPlanificare("03/04/2020 10:00","03/04/2020 16:00", sarcina1);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            program.adaugaPlanificare("01/04/2020 10:00", "01/04/2020 20:00", divertisment1);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            program.adaugaPlanificare("01/04/2020 11:00", "01/04/2020 13:00", sarcina1);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            program.adaugaPlanificare("01/04/2020 14:00", "01/04/2020 16:00", divertisment2);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            program.adaugaPlanificare("02/04/2020 15:00", "02/04/2020 20:00", intalnire);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            program.adaugaPlanificare("03/04/2020 12:00", "03/04/2020 20:00", sarcina2);
        } catch (Exception e) {
            System.out.println(e);
        }

        Set<Planificare> listaOptima = new TreeSet(program.listaNumarMaximPlanificari());
        for (Planificare planificare : listaOptima) {
            System.out.println(planificare.afisarePlanificare());
        }

        Set<Planificare> listaInterval = new TreeSet(program.listaPlanificariInInterval("02/04/2020 08:00",
                "03/04/2020 20:00"));
        for (Planificare planificare : listaInterval) {
            System.out.println(planificare.afisarePlanificare());
        }

        program.stergePlanificariInInterval("03/04/2020 18:00", "03/04/2020 20:00");

        Planificare planificare1 = program.getPlanificareSarcina("Proiect PAO","01/04/2020 11:00");
        if (planificare1 != null) {
            Sarcina sarcina3 = (Sarcina) planificare1.getActivitate();
            sarcina3.rezolvaObiectiv(0);
            sarcina3.rezolvaObiectiv(1);
            try{
                planificare1.setRezultat("Am completat primii doi pasi");
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
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        Planificare planificare3 = program.getPlanificare("Avatar2");
        if(planificare3 != null) {
            try {
                planificare3.setRezultat("Am vazut filmul, a fost foarte bun");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        System.out.println(program.numarPlanificariRatate());

        listaSarcini.updateStatus();
        System.out.println(listaSarcini.afisareSarcini());

    }
}
