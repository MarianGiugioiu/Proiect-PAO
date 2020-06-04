package Servicii;

import Entitati.*;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {
    private static final String URL = "jdbc:mysql://localhost:3306/pao";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static Connection connection;

    public Server() throws SQLException{
        System.out.println("Connecting to database...");
        connection = DriverManager.getConnection(URL,USER,PASS);
        System.out.println("Connected  to database");

        /*Statement stmt = connection.createStatement();
        String sql = "CREATE TABLE sarcini " +
                "(nume_activitate VARCHAR(255), " +
                " locatie VARCHAR(255), " +
                " deadline VARCHAR(255), " +
                " numar_obiective INTEGER , " +
                " numar_obiective_rezolvate INTEGER , " +
                " obiective VARCHAR(255), " +
                " PRIMARY KEY ( nume_activitate ))";
        stmt.executeUpdate(sql);*/

        /*Statement stmt = connection.createStatement();
        String sql = "CREATE TABLE intalniri " +
                "(nume_activitate VARCHAR(255), " +
                " locatie VARCHAR(255), " +
                " scop_intalnire VARCHAR(255), " +
                " numar_membrii INTEGER , " +
                " membrii VARCHAR(255), " +
                " PRIMARY KEY ( nume_activitate ))";
        stmt.executeUpdate(sql);*/

        /*Statement stmt = connection.createStatement();
        String sql = "CREATE TABLE divertismente_dinamice " +
                "(nume_activitate VARCHAR(255), " +
                " locatie VARCHAR(255), " +
                " entuziasm INTEGER , " +
                " mijloace_transport VARCHAR(255), " +
                " obiective_vizitat VARCHAR(255), " +
                " PRIMARY KEY ( nume_activitate ))";
        stmt.executeUpdate(sql);*/

        /*Statement stmt = connection.createStatement();
        String sql = "CREATE TABLE divertismente_statice " +
                "(nume_activitate VARCHAR(255), " +
                " locatie VARCHAR(255), " +
                " entuziasm INTEGER , " +
                " tip VARCHAR(255), " +
                " asteptari VARCHAR(255), " +
                " PRIMARY KEY ( nume_activitate ))";
        stmt.executeUpdate(sql);*/
    }

    //Sarcini

    public static void addSarcina(Sarcina sarcina) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into sarcini(nume_activitate, locatie, deadline, numar_obiective, numar_obiective_rezolvate, obiective) VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, sarcina.getNumeActivitate());
        preparedStatement.setString(2, sarcina.getLocatie());
        StringBuilder stringBuilder1 = new StringBuilder(String.format("%tD %tR",sarcina.getDeadline(),sarcina.getDeadline()));
        String string1 = stringBuilder1.substring(0,2);
        String string2 = stringBuilder1.substring(3,5);
        stringBuilder1.replace(0,2,string2);
        stringBuilder1.replace(3,5,string1);
        preparedStatement.setString(3, stringBuilder1.toString());
        //preparedStatement.setDate(3, new java.sql.Date(sarcina.getDeadline().getTime()));
        preparedStatement.setInt(4, sarcina.getNumarObiective());
        preparedStatement.setInt(5, sarcina.getNumarObiectiveRezolvate());
        StringBuilder stringBuilder = new StringBuilder();
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
        preparedStatement.setString(6, stringBuilder.toString());
        int resultSet = preparedStatement.executeUpdate();

    }

    public static void updateSarcina(Sarcina sarcina) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update sarcini set nume_activitate=?, locatie=?, deadline=?, numar_obiective=?, numar_obiective_rezolvate=?, obiective=? where nume_activitate=?");
        preparedStatement.setString(1, sarcina.getNumeActivitate());
        preparedStatement.setString(2, sarcina.getLocatie());
        StringBuilder stringBuilder1 = new StringBuilder(String.format("%tD %tR",sarcina.getDeadline(),sarcina.getDeadline()));
        String string1 = stringBuilder1.substring(0,2);
        String string2 = stringBuilder1.substring(3,5);
        stringBuilder1.replace(0,2,string2);
        stringBuilder1.replace(3,5,string1);
        preparedStatement.setString(3, stringBuilder1.toString());
        //preparedStatement.setDate(3, new java.sql.Date(sarcina.getDeadline().getTime()));
        preparedStatement.setInt(4, sarcina.getNumarObiective());
        preparedStatement.setInt(5, sarcina.getNumarObiectiveRezolvate());
        StringBuilder stringBuilder = new StringBuilder();
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
        preparedStatement.setString(6, stringBuilder.toString());
        preparedStatement.setString(7, sarcina.getNumeActivitate());
        int resultSet = preparedStatement.executeUpdate();

    }

    public static void deleteSarcina(String numeSarcina) throws SQLException {
        String sql = "delete from sarcini where nume_activitate=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, numeSarcina);
        int resultSet = preparedStatement.executeUpdate();
    }

    public static Sarcina getSarcina(String numeSarcina) throws SQLException {
        String sql = "select * from sarcini where nume_activitate=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, numeSarcina);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Pair<String, String>> listaObiective = new ArrayList<Pair<String, String>>();
        resultSet.next();
        String[] obiective = resultSet.getString("obiective").split("&");
        for (String obiectiv : obiective) {
            String[] val = obiectiv.split("-");
            listaObiective.add(new Pair<String, String>(val[0],val[1]));
        }
        return new Sarcina(resultSet.getString("nume_activitate"),
                resultSet.getString("locatie"),
                resultSet.getString("deadline"),
                resultSet.getInt("numar_obiective"),
                resultSet.getInt("numar_obiective_rezolvate"),
                listaObiective);
    }

    public static ArrayList<Sarcina> getSarcini() throws SQLException {
        ArrayList<Sarcina> sarcini = new ArrayList<Sarcina>();
        String sql = "select * from sarcini";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            ArrayList<Pair<String, String>> listaObiective = new ArrayList<Pair<String, String>>();
            String[] obiective = resultSet.getString("obiective").split("&");
            for (String obiectiv : obiective) {
                String[] val = obiectiv.split("-");
                listaObiective.add(new Pair<String, String>(val[0], val[1]));
            }
            sarcini.add(new Sarcina(resultSet.getString("nume_activitate"),
                    resultSet.getString("locatie"),
                    resultSet.getString("deadline"),
                    resultSet.getInt("numar_obiective"),
                    resultSet.getInt("numar_obiective_rezolvate"),
                    listaObiective));
        }
        return sarcini;
    }

    //Intalniri

    public static void addIntalnire(Intalnire intalnire) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into intalniri(nume_activitate, locatie, scop_intalnire, numar_membrii, membrii) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, intalnire.getNumeActivitate());
        preparedStatement.setString(2, intalnire.getLocatie());
        preparedStatement.setString(3, intalnire.getScopIntalnire());
        preparedStatement.setInt(4, intalnire.getNumarMembrii());
        StringBuilder stringBuilder = new StringBuilder();
        int k=0;
        for (String membru : intalnire.getMembrii()) {
            stringBuilder.append(membru);
            if (k<intalnire.getMembrii().size()-1){
                stringBuilder.append("&");
            }
            k++;
        }
        preparedStatement.setString(5, stringBuilder.toString());
        int resultSet = preparedStatement.executeUpdate();

    }

    public static void updateIntalnire(Intalnire intalnire) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update intalniri set nume_activitate=?, locatie=?, scop_intalnire=?, numar_membrii=?, membrii=? where nume_activitate=?");
        preparedStatement.setString(1, intalnire.getNumeActivitate());
        preparedStatement.setString(2, intalnire.getLocatie());
        preparedStatement.setString(3, intalnire.getScopIntalnire());
        preparedStatement.setInt(4, intalnire.getNumarMembrii());
        StringBuilder stringBuilder = new StringBuilder();
        int k=0;
        for (String membru : intalnire.getMembrii()) {
            stringBuilder.append(membru);
            if (k<intalnire.getMembrii().size()-1){
                stringBuilder.append("&");
            }
            k++;
        }
        preparedStatement.setString(5, stringBuilder.toString());
        preparedStatement.setString(6, intalnire.getNumeActivitate());
        int resultSet = preparedStatement.executeUpdate();
    }

    public static void deleteIntalnire(String numeIntalnire) throws SQLException {
        String sql = "delete from intalniri where nume_activitate=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, numeIntalnire);
        int resultSet = preparedStatement.executeUpdate();
    }

    public static Intalnire getIntalnire(String numeIntalnire) throws SQLException {
        String sql = "select * from intalniri where nume_activitate=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, numeIntalnire);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        ArrayList<String> listaMembrii = new ArrayList<String>();
        String[] membrii = resultSet.getString("membrii").split("&");
        listaMembrii.addAll(Arrays.asList(membrii));

        return new Intalnire(resultSet.getString("nume_activitate"),
                resultSet.getString("locatie"),
                resultSet.getString("scop_intalnire"),
                resultSet.getInt("numar_membrii"),
                listaMembrii);
    }

    public static ArrayList<Intalnire> getIntalniri() throws SQLException {
        ArrayList<Intalnire> intalniri = new ArrayList<Intalnire>();
        String sql = "select * from intalniri ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            ArrayList<String> listaMembrii = new ArrayList<String>();
            String[] membrii = resultSet.getString("membrii").split("&");
            listaMembrii.addAll(Arrays.asList(membrii));
            intalniri.add(new Intalnire(resultSet.getString("nume_activitate"),
                    resultSet.getString("locatie"),
                    resultSet.getString("scop_intalnire"),
                    resultSet.getInt("numar_membrii"),
                    listaMembrii));
        }
        return intalniri;
    }

    //Divertismente dinamice

    public static void addDivertismentDinamic(DivertismentDinamic divertismentDinamic) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into divertismente_dinamice(nume_activitate, locatie, entuziasm, mijloace_transport, obiective_vizitat) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, divertismentDinamic.getNumeActivitate());
        preparedStatement.setString(2, divertismentDinamic.getLocatie());
        preparedStatement.setInt(3, divertismentDinamic.getEntuziasm());
        StringBuilder stringBuilder = new StringBuilder();
        int k=0;
        for (String mijlocTrans : divertismentDinamic.getMijloaceDeTransport()) {
            stringBuilder.append(mijlocTrans);
            if (k<divertismentDinamic.getMijloaceDeTransport().size()-1){
                stringBuilder.append("&");
            }
            k++;
        }
        preparedStatement.setString(4, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        k=0;
        for (String obiective : divertismentDinamic.getObiectiveDeVizitat()) {
            stringBuilder.append(obiective);
            if (k<divertismentDinamic.getObiectiveDeVizitat().size()-1){
                stringBuilder.append("&");
            }
            k++;
        }
        preparedStatement.setString(5, stringBuilder.toString());
        int resultSet = preparedStatement.executeUpdate();

    }

    public static void updateDivertismentDinamic(DivertismentDinamic divertismentDinamic) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update divertismente_dinamice set nume_activitate=?, locatie=?, entuziasm=?, mijloace_transport=?, obiective_vizitat=? where nume_activitate=?");
        preparedStatement.setString(1, divertismentDinamic.getNumeActivitate());
        preparedStatement.setString(2, divertismentDinamic.getLocatie());
        preparedStatement.setInt(3, divertismentDinamic.getEntuziasm());
        StringBuilder stringBuilder = new StringBuilder();
        int k=0;
        for (String mijlocTrans : divertismentDinamic.getMijloaceDeTransport()) {
            stringBuilder.append(mijlocTrans);
            if (k<divertismentDinamic.getMijloaceDeTransport().size()-1){
                stringBuilder.append("&");
            }
            k++;
        }
        preparedStatement.setString(4, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        k=0;
        for (String obiective : divertismentDinamic.getObiectiveDeVizitat()) {
            stringBuilder.append(obiective);
            if (k<divertismentDinamic.getObiectiveDeVizitat().size()-1){
                stringBuilder.append("&");
            }
            k++;
        }
        preparedStatement.setString(5, stringBuilder.toString());
        preparedStatement.setString(6, divertismentDinamic.getNumeActivitate());
        int resultSet = preparedStatement.executeUpdate();
    }

    public static void deleteDivertismentDinamic(String numeDivertismentDinamic) throws SQLException {
        String sql = "delete from divertismente_dinamice where nume_activitate=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, numeDivertismentDinamic);
        int resultSet = preparedStatement.executeUpdate();
    }

    public static DivertismentDinamic getDivertismentDinamic(String numeDivertismentDinamic) throws SQLException {
        String sql = "select * from divertismente_dinamice where nume_activitate=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, numeDivertismentDinamic);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        ArrayList<String> listaMijloaceDeTransport = new ArrayList<String>();
        String[] mijloaceDeTransport = resultSet.getString("mijloace_transport").split("&");
        listaMijloaceDeTransport.addAll(Arrays.asList(mijloaceDeTransport));

        ArrayList<String> listaObiectiveDeVizitat = new ArrayList<String>();
        String[] obiectiveDeVizitat = resultSet.getString("obiective_vizitat").split("&");
        listaObiectiveDeVizitat.addAll(Arrays.asList(obiectiveDeVizitat));

        return new DivertismentDinamic(resultSet.getString("nume_activitate"),
                resultSet.getString("locatie"),
                resultSet.getInt("entuziasm"),
                listaMijloaceDeTransport, listaObiectiveDeVizitat);
    }

    public static ArrayList<DivertismentDinamic> getDivertismenteDinamice() throws SQLException {
        ArrayList<DivertismentDinamic>  divertismenteDinamice= new ArrayList<DivertismentDinamic>();
        String sql = "select * from divertismente_dinamice ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            ArrayList<String> listaMijloaceDeTransport = new ArrayList<String>();
            String[] mijloaceDeTransport = resultSet.getString("mijloace_transport").split("&");
            listaMijloaceDeTransport.addAll(Arrays.asList(mijloaceDeTransport));

            ArrayList<String> listaObiectiveDeVizitat = new ArrayList<String>();
            String[] obiectiveDeVizitat = resultSet.getString("obiective_vizitat").split("&");
            listaObiectiveDeVizitat.addAll(Arrays.asList(obiectiveDeVizitat));

            divertismenteDinamice.add(new DivertismentDinamic(resultSet.getString("nume_activitate"),
                    resultSet.getString("locatie"),
                    resultSet.getInt("entuziasm"),
                    listaMijloaceDeTransport, listaObiectiveDeVizitat));
        }
        return divertismenteDinamice;
    }

    //Divertismente Statice

    public static void addDivertismentStatic(DivertismentStatic divertismentStatic) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into divertismente_statice(nume_activitate, locatie, entuziasm, tip, asteptari) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, divertismentStatic.getNumeActivitate());
        preparedStatement.setString(2, divertismentStatic.getLocatie());
        preparedStatement.setInt(3, divertismentStatic.getEntuziasm());
        preparedStatement.setString(4, divertismentStatic.getTip());
        preparedStatement.setString(5, divertismentStatic.getAsteptari());
        int resultSet = preparedStatement.executeUpdate();

    }

    public static void updateDivertismentStatic(DivertismentStatic divertismentStatic) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update divertismente_statice set nume_activitate=?, locatie=?, entuziasm=?,tip=?, asteptari=? where nume_activitate=?");
        preparedStatement.setString(1, divertismentStatic.getNumeActivitate());
        preparedStatement.setString(2, divertismentStatic.getLocatie());
        preparedStatement.setInt(3, divertismentStatic.getEntuziasm());
        preparedStatement.setString(4, divertismentStatic.getTip());
        preparedStatement.setString(5, divertismentStatic.getAsteptari());
        preparedStatement.setString(6, divertismentStatic.getNumeActivitate());
        int resultSet = preparedStatement.executeUpdate();
    }

    public static void deleteDivertismentStatic(String numeDivertismentStatic) throws SQLException {
        String sql = "delete from divertismente_statice where nume_activitate=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, numeDivertismentStatic);
        int resultSet = preparedStatement.executeUpdate();
    }

    public static DivertismentStatic getDivertismentStatic(String numeDivertismentStatic) throws SQLException {
        String sql = "select * from divertismente_statice where nume_activitate=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, numeDivertismentStatic);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        return new DivertismentStatic(resultSet.getString("nume_activitate"),
                resultSet.getString("locatie"),
                resultSet.getInt("entuziasm"),
                resultSet.getString("tip"),
                resultSet.getString("asteptari"));
    }

    public static ArrayList<DivertismentStatic> getDivertismenteStatice() throws SQLException {
        ArrayList<DivertismentStatic>  divertismenteStatice= new ArrayList<DivertismentStatic>();
        String sql = "select * from divertismente_statice ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            divertismenteStatice.add(new DivertismentStatic(resultSet.getString("nume_activitate"),
                    resultSet.getString("locatie"),
                    resultSet.getInt("entuziasm"),
                    resultSet.getString("tip"),
                    resultSet.getString("asteptari")));
        }
        return divertismenteStatice;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        Server server = new Server();
        Audit audit = Audit.getInstance();

        Thread threadSarcina = new Thread() {
            @Override
            public void run() {
                try {
                    String name = getName();
                    Sarcina sarcina = new Sarcina("Proiect PAO","Acasa/PC","03/04/2020 23:59");
                    sarcina.adaugaObiectiv("Creare clase");
                    Audit.scrieDate("adaugaObiectiv","src/main/java/Date/Audit.csv",name);
                    sarcina.adaugaObiectiv("Utilizare mostenire");
                    Audit.scrieDate("adaugaObiectiv","src/main/java/Date/Audit.csv",name);
                    sarcina.adaugaObiectiv("Creare colectii");
                    Audit.scrieDate("adaugaObiectiv","src/main/java/Date/Audit.csv",name);
                    //server.addSarcina(sarcina);
                    //server.deleteSarcina(sarcina.getNumeActivitate());

                    //sarcina.setLocatie("Acasa");
                    Audit.scrieDate("setLocatie","src/main/java/Date/Audit.csv",name);
                    //server.updateSarcina(sarcina);

                    //Sarcina sarcina1 = server.getSarcina(sarcina.getNumeActivitate());
                    //System.out.println(saracina1.afisareActivitate());
                    Audit.scrieDate("afisareActivitate","src/main/java/Date/Audit.csv",name);

                    ArrayList<Sarcina> sarcini = server.getSarcini();
                    for (Sarcina sarcina1 : sarcini) {
                        System.out.println(sarcina1.afisareActivitate());
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        Thread threadIntalnire = new Thread() {
            @Override
            public void run() {
                try {
                    String name = getName();
                    Intalnire intalnire = new Intalnire("Planificare vacanta", "Valcea","Vom alege locatia si ne vom interesa de cazare si mijloace de transport");
                    intalnire.adaugaMembru("Alin");
                    Audit.scrieDate("adaugaMemrbu","src/main/java/Date/Audit.csv",name);
                    intalnire.adaugaMembru("Vlad");
                    Audit.scrieDate("adaugaMembru","src/main/java/Date/Audit.csv",name);
                    server.addIntalnire(intalnire);
                    //server.deleteIntalnire(intalnire.getNumeActivitate());
                    //intalnire.setLocatie("Bucuresti");
                    Audit.scrieDate("setLocatie","src/main/java/Date/Audit.csv",name);
                    //server.updateIntalnire(intalnire);
                    //server.addIntalnire(intalnire);
                    //System.out.println(server.getIntalnire(intalnire.getNumeActivitate()).afisareActivitate());
                    Audit.scrieDate("afisareActivitate","src/main/java/Date/Audit.csv",name);
                    ArrayList<Intalnire> intalniri = server.getIntalniri();
                    for (Intalnire intalnire1 : intalniri) {
                        System.out.println(intalnire1.afisareActivitate());
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };

        Thread threadDinamic = new Thread() {
            @Override
            public void run() {
                try {
                    String name = getName();
                    DivertismentDinamic divertisment1 = new DivertismentDinamic("Vizita gradina zoologica","Gradina zoologica Bucuresti",
                            7, Arrays.asList("metrou", "autobuz"), Arrays.asList("lei","maimute"));
                    //server.addDivertismentDinamic(divertisment1);
                    //divertisment1.setEntuziasm(8);
                    Audit.scrieDate("setEntuziasm","src/main/java/Date/Audit.csv",name);
                    //server.updateDivertismentDinamic(divertisment1);
                    //server.deleteDivertismentDinamic(divertisment1.getNumeActivitate());
                    //System.out.println(server.getDivertismentDinamic(divertisment1.getNumeActivitate()).afisareActivitate());
                    Audit.scrieDate("afisareActivitate","src/main/java/Date/Audit.csv",name);
                    ArrayList<DivertismentDinamic> divertismenteDinamice = server.getDivertismenteDinamice();
                    for (DivertismentDinamic divertismentDinamic : divertismenteDinamice) {
                        System.out.println(divertismentDinamic.afisareActivitate());
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };

        Thread threadStatic = new Thread() {
            @Override
            public void run() {
                try {
                    String name = getName();
                    DivertismentStatic divertisment2 = new DivertismentStatic("Avatar23", "CinemaCity Valcea",
                            8,"film", "Sa adauge mai multa istorie a Pandorei");
                    //server.addDivertismentStatic(divertisment2);
                    //divertisment2.setEntuziasm(5);
                    Audit.scrieDate("setEntuziasm","src/main/java/Date/Audit.csv",name);
                    //server.updateDivertismentStatic(divertisment2);
                    //server.deleteDivertismentStatic(divertisment2.getNumeActivitate());
                    //System.out.println(server.getDivertismentStatic(divertisment2.getNumeActivitate()).afisareActivitate());
                    Audit.scrieDate("afisareActivitate","src/main/java/Date/Audit.csv",name);
                    ArrayList<DivertismentStatic> divertismenteStatice = server.getDivertismenteStatice();
                    for (DivertismentStatic divertismentStatic : divertismenteStatice) {
                        System.out.println(divertismentStatic.afisareActivitate());
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };

        threadSarcina.start();
        threadIntalnire.start();
        threadDinamic.start();
        threadStatic.start();
    }
}
