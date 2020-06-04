import Entitati.*;
import Servicii.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {

    public static Program program;
    public static ManagerSarcini listaSarcini;

    private Button buttonSarcini;
    private Button buttonPlanificari;
    private Button buttonInapoi;

    private void EcranSarcini(Stage primaryStage) {
        primaryStage.setTitle("Sarcini");

        final Sarcina[] sarcina = new Sarcina[1];

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        buttonInapoi = new Button("Inapoi");
        buttonInapoi.setOnAction(value -> {
            init(primaryStage);
        });
        HBox hBoxInapoi = new HBox(buttonInapoi);
        hBoxInapoi.setSpacing(20);
        GridPane.setConstraints(hBoxInapoi, 0, 0);
        grid.getChildren().add(hBoxInapoi);

        Label labelExpirat = new Label("Numar Sarcini Expirate:");
        TextField textExpirat = new TextField();
        Button buttonExpirat = new Button("Update Status");
        buttonExpirat.setOnAction(value -> {
            textExpirat.setText(String.valueOf(listaSarcini.updateStatus().size()));
        });
        HBox hBoxExpirat = new HBox(labelExpirat, textExpirat, buttonExpirat);
        hBoxExpirat.setSpacing(20);
        GridPane.setConstraints(hBoxExpirat, 0, 1);
        grid.getChildren().add(hBoxExpirat);

        Label labelObiectiv = new Label("Obiectiv:");
        TextField textObiectiv = new TextField();
        Button buttonAdaugaObiectiv = new Button("Adauga Obiectiv");
        buttonAdaugaObiectiv.setOnAction(value -> {
            String obiectiv = textObiectiv.getText();
            sarcina[0].adaugaObiectiv(obiectiv);
        });
        HBox hBoxObiectiv = new HBox(labelObiectiv, textObiectiv, buttonAdaugaObiectiv);
        hBoxObiectiv.setSpacing(20);
        hBoxObiectiv.setVisible(false);

        Label labelNumeActivitate = new Label("NumeActivitate:");
        TextField textNumeActivitate = new TextField();
        HBox hBoxNumeActivitate = new HBox(labelNumeActivitate, textNumeActivitate);
        hBoxNumeActivitate.setSpacing(20);
        GridPane.setConstraints(hBoxNumeActivitate, 0, 2);
        grid.getChildren().add(hBoxNumeActivitate);

        Label labelLocatie = new Label("Locatie:");
        TextField textLocatie = new TextField();
        HBox hBoxLocatie = new HBox(labelLocatie, textLocatie);
        hBoxLocatie.setSpacing(20);
        GridPane.setConstraints(hBoxLocatie, 0, 3);
        grid.getChildren().add(hBoxLocatie);

        Label labelDeadline = new Label("Deadline:");
        TextField textDeadline = new TextField();
        HBox hBoxDeadline = new HBox(labelDeadline, textDeadline);
        hBoxDeadline.setSpacing(20);
        GridPane.setConstraints(hBoxDeadline, 0, 4);
        grid.getChildren().add(hBoxDeadline);

        Button buttonAdaugaSarcina = new Button("Adauga Sarcina");
        buttonAdaugaSarcina.setOnAction(value -> {
            String numeActivitate = textNumeActivitate.getText();
            String locatie = textLocatie.getText();
            String deadline = textDeadline.getText();
            sarcina[0] = new Sarcina(numeActivitate, locatie, deadline);
            try {
                listaSarcini.adaugaSarcina(sarcina[0]);
                hBoxObiectiv.setVisible(true);
            } catch (Exception e) {
                System.out.println(e);
            }
        });

        Button buttonSarcinaNoua = new Button("Sarcina Noua");
        buttonSarcinaNoua.setOnAction(value -> {
            textNumeActivitate.setText("");
            textLocatie.setText("");
            textDeadline.setText("");
            hBoxObiectiv.setVisible(false);
        });
        HBox hBoxSarcina = new HBox(buttonAdaugaSarcina, buttonSarcinaNoua);
        hBoxSarcina.setSpacing(20);
        GridPane.setConstraints(hBoxSarcina, 0, 5);
        grid.getChildren().add(hBoxSarcina);


        GridPane.setConstraints(hBoxObiectiv, 0, 6);
        grid.getChildren().add(hBoxObiectiv);


        HBox hbox = new HBox(grid);

        Scene scene = new Scene(hbox, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void EcranPlanificari(Stage primaryStage) {
        primaryStage.setTitle("Planificari");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        buttonInapoi = new Button("Inapoi");
        buttonInapoi.setOnAction(value -> {
            init(primaryStage);
        });
        HBox hBoxInapoi = new HBox(buttonInapoi);
        hBoxInapoi.setSpacing(20);
        GridPane.setConstraints(hBoxInapoi, 0, 0);
        grid.getChildren().add(hBoxInapoi);

        Label labelInterval = new Label("Interval:");
        TextField textInceput = new TextField();
        TextField textSfarsit = new TextField();
        HBox hBoxInterval = new HBox(labelInterval, textInceput, textSfarsit);
        hBoxInterval.setSpacing(20);
        GridPane.setConstraints(hBoxInterval, 0, 1);
        grid.getChildren().add(hBoxInterval);

        Button buttonStergePlanificari = new Button("Sterge Planificari In Interval");
        buttonStergePlanificari.setOnAction(value -> {
            program.stergePlanificariInInterval(textInceput.getText(), textSfarsit.getText());
        });
        HBox hBoxStergePlanificari = new HBox(buttonStergePlanificari);
        GridPane.setConstraints(hBoxStergePlanificari, 0, 2);
        grid.getChildren().add(hBoxStergePlanificari);

        TextField textPlanificariRatate = new TextField();
        Button buttonPlanificariRatate = new Button("Numar Planificari Ratate");
        buttonPlanificariRatate.setOnAction(value -> {
            textPlanificariRatate.setText(String.valueOf(program.numarPlanificariRatate()));
        });
        HBox hBoxPlanificariRatate = new HBox(textPlanificariRatate, buttonPlanificariRatate);
        hBoxPlanificariRatate.setSpacing(20);
        GridPane.setConstraints(hBoxPlanificariRatate, 0, 3);
        grid.getChildren().add(hBoxPlanificariRatate);

        Button buttonMaximPlanificari = new Button("Numar Maxim Planificari");
        buttonMaximPlanificari.setOnAction(value -> {
            Set<Planificare> planificari = program.listaNumarMaximPlanificari();
            int nr = 0;

            ObservableList<Node> childrens = grid.getChildren();
            for(Node node : childrens) {
                if(grid.getRowIndex(node) == 6 && grid.getColumnIndex(node) == 0) {
                    grid.getChildren().remove(node);
                    break;
                }
            }
            GridPane grid1 = new GridPane();
            grid1.setPadding(new Insets(10, 10, 10, 10));
            grid1.setVgap(5);
            grid1.setHgap(5);

            for (Planificare planificare : planificari) {
                nr++;
                Pair<String, String> rezumat = planificare.getRezumat();
                Label textNume = new Label();
                Label textInterval = new Label();
                textNume.setText(rezumat.getKey());
                textInterval.setText(rezumat.getValue());
                HBox hBoxRezumat = new HBox(textNume, textInterval);
                hBoxRezumat.setSpacing(20);
                GridPane.setConstraints(hBoxRezumat, 0, nr);
                grid1.getChildren().add(hBoxRezumat);
            }
            GridPane.setConstraints(grid1,0,6);
            grid.getChildren().add(grid1);
        });
        HBox hBoxMaximPlanificari = new HBox(buttonMaximPlanificari);
        hBoxMaximPlanificari.setSpacing(20);
        GridPane.setConstraints(hBoxMaximPlanificari, 0, 4);
        grid.getChildren().add(hBoxMaximPlanificari);

        Button buttonPlanificariInterval = new Button("Numar Planificari Interval");
        buttonPlanificariInterval.setOnAction(value -> {
            List<Planificare> planificari = program.listaPlanificariInInterval(textInceput.getText(),
                    textSfarsit.getText());
            int nr = 0;
            ObservableList<Node> childrens = grid.getChildren();
            for(Node node : childrens) {
                if(grid.getRowIndex(node) == 6 && grid.getColumnIndex(node) == 0) {
                    HBox hBox= new HBox(node);
                    grid.getChildren().remove(hBox);
                    break;
                }
            }

            GridPane grid1 = new GridPane();
            grid1.setPadding(new Insets(10, 10, 10, 10));
            grid1.setVgap(5);
            grid1.setHgap(5);

            for (Planificare planificare : planificari) {
                nr++;
                Pair<String, String> rezumat = planificare.getRezumat();
                Label textNume = new Label();
                Label textInterval = new Label();
                textNume.setText(rezumat.getKey());
                textInterval.setText(rezumat.getValue());
                HBox hBoxRezumat = new HBox(textNume, textInterval);
                hBoxRezumat.setSpacing(20);
                GridPane.setConstraints(hBoxRezumat, 0, nr);
                grid1.getChildren().add(hBoxRezumat);
            }
            GridPane.setConstraints(grid1,0,6);
            grid.getChildren().add(grid1);
        });
        HBox hBoxPlanificariInterval = new HBox(buttonPlanificariInterval);
        hBoxPlanificariInterval.setSpacing(20);
        GridPane.setConstraints(hBoxPlanificariInterval, 0, 5);
        grid.getChildren().add(hBoxPlanificariInterval);

        HBox hbox = new HBox(grid);
        Scene scene = new Scene(hbox, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void init(final Stage primaryStage) {
        primaryStage.setTitle("AgendaPersonala");

        buttonSarcini = new Button("Sarcini");
        buttonPlanificari = new Button("Planificari");

        buttonSarcini.setOnAction(value -> {
            EcranSarcini(primaryStage);
        });

        buttonPlanificari.setOnAction(value -> {
            EcranPlanificari(primaryStage);
        });

        HBox hbox = new HBox(buttonSarcini,buttonPlanificari);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(hbox, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);

    }

    public static void main(String[] args) {
        program = new Program();
        listaSarcini =  new ManagerSarcini();

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

        Thread thread = new Thread() {
            @Override
            public void run() {
                String name = getName();
                System.out.println(name);
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
                    Audit.scrieDate("adaugaObiectiv","src/main/java/Date/Audit.csv",name);
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
                Audit.scrieDate("esteExpirat","src/main/java/Date/Audit.csv",name);

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
                Audit.scrieDate("adaugaPlanificare","src/main/java/Date/Audit.csv",name);

                try {
                    program.adaugaPlanificare("01/04/2020 10:00", "01/04/2020 20:00", divertisment1);
                } catch (Exception e) {
                    System.out.println(e);
                }
                Audit.scrieDate("adaugaPlanificare","src/main/java/Date/Audit.csv",name);

                try {
                    program.adaugaPlanificare("01/04/2020 11:00", "01/04/2020 13:00", sarcina1);
                } catch (Exception e) {
                    System.out.println(e);
                }
                Audit.scrieDate("adaugaPlanificare","src/main/java/Date/Audit.csv",name);

                try {
                    program.adaugaPlanificare("01/04/2020 14:00", "01/04/2020 16:00", divertisment2);
                } catch (Exception e) {
                    System.out.println(e);
                }
                Audit.scrieDate("adaugaPlanificare","src/main/java/Date/Audit.csv",name);

                try {
                    program.adaugaPlanificare("02/04/2020 15:00", "02/04/2020 20:00", intalnire);
                } catch (Exception e) {
                    System.out.println(e);
                }
                Audit.scrieDate("adaugaPlanificare","src/main/java/Date/Audit.csv",name);

                try {
                    program.adaugaPlanificare("03/04/2020 12:00", "03/04/2020 20:00", sarcina2);
                } catch (Exception e) {
                    System.out.println(e);
                }
                Audit.scrieDate("adaugaPlanificare","src/main/java/Date/Audit.csv",name);

                Set<Planificare> listaOptima = new TreeSet(program.listaNumarMaximPlanificari());
                for (Planificare planificare : listaOptima) {
                    System.out.println(planificare.afisarePlanificare());
                }
                Audit.scrieDate("listaNumarMaximPlanificari","src/main/java/Date/Audit.csv",name);

                Set<Planificare> listaInterval = new TreeSet(program.listaPlanificariInInterval("02/04/2020 08:00",
                        "03/04/2020 20:00"));
                for (Planificare planificare : listaInterval) {
                    System.out.println(planificare.afisarePlanificare());
                }
                Audit.scrieDate("listaPlanificariInInterval","src/main/java/Date/Audit.csv",name);

                program.stergePlanificariInInterval("03/04/2020 18:00", "03/04/2020 20:00");
                Audit.scrieDate("stergePlanificariInInterval","src/main/java/Date/Audit.csv",name);

                Planificare planificare1 = program.getPlanificareSarcina("Proiect PAO","01/04/2020 11:00");
                if (planificare1 != null) {
                    Sarcina sarcina3 = (Sarcina) planificare1.getActivitate();
                    sarcina3.rezolvaObiectiv(0);
                    sarcina3.rezolvaObiectiv(1);
                    try{
                        planificare1.setRezultat("Am completat primii doi pasi");
                        Audit.scrieDate("setRezultat","src/main/java/Date/Audit.csv",name);
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
                        Audit.scrieDate("setRezultat","src/main/java/Date/Audit.csv",name);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }

                Planificare planificare3 = program.getPlanificare("Avatar2");
                if(planificare3 != null) {
                    try {
                        planificare3.setRezultat("Am vazut filmul, a fost foarte bun");
                        Audit.scrieDate("setRezultat","src/main/java/Date/Audit.csv",name);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                //System.out.println(program.numarPlanificariRatate());
                //Audit.scrieDate("numarPlanificariRatate","src/main/java/Date/Audit.csv");

                //listaSarcini.updateStatus();
                Audit.scrieDate("updateStatus","src/main/java/Date/Audit.csv",name);
                System.out.println(listaSarcini.afisareSarcini());
            }
        };
        thread.start();

        launch(args);


    }
}
