package server;

import Entitati.Intalnire;
import Entitati.Sarcina;
import Servicii.Audit;
import javafx.util.Pair;

import java.io.IOException;
import java.security.SecureRandom;
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
        Statement stmt = connection.createStatement();
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
        Statement stmt = connection.createStatement();
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
        Statement stmt = connection.createStatement();
        String sql = "delete from sarcini where nume_activitate=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, numeSarcina);
        int resultSet = preparedStatement.executeUpdate();
    }

    public static Sarcina getSarcina(String numeSarcina) throws SQLException {
        String sql = "select * from sarcini where nume_activitate=?";
        Statement stmt = connection.createStatement();
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
        Statement stmt = connection.createStatement();
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
        Statement stmt = connection.createStatement();
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
        Statement stmt = connection.createStatement();
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
        Statement stmt = connection.createStatement();
        String sql = "delete from intalniri where nume_activitate=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, numeIntalnire);
        int resultSet = preparedStatement.executeUpdate();
    }

    public static Intalnire getIntalnire(String numeIntalnire) throws SQLException {
        String sql = "select * from intalniri where nume_activitate=?";
        Statement stmt = connection.createStatement();
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
        Statement stmt = connection.createStatement();
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

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        Server server = new Server();
        /*Sarcina sarcina = new Sarcina("Proiect PAO","Acasa/PC","03/04/2020 23:59");
        sarcina.adaugaObiectiv("Creare clase");
        sarcina.adaugaObiectiv("Utilizare mostenire");
        sarcina.adaugaObiectiv("Creare colectii");*/
        //server.addSarcina(sarcina);
        //server.deleteSarcina(sarcina.getNumeActivitate());

        //sarcina.setLocatie("Acasa");
        //server.updateSarcina(sarcina);

        //Sarcina sarcina1 = server.getSarcina(sarcina.getNumeActivitate());
        //System.out.println(sarcina1.afisareActivitate());

        /*ArrayList<Sarcina> sarcini = server.getSarcini();
        for (Sarcina sarcina1 : sarcini) {
            System.out.println(sarcina1.afisareActivitate());
        }*/

        /*Intalnire intalnire = new Intalnire("Planificare vacanta", "Valcea","Vom alege locatia si ne vom interesa de cazare si mijloace de transport");
        intalnire.adaugaMembru("Alin");
        intalnire.adaugaMembru("Vlad");*/
        //server.addIntalnire(intalnire);
        //server.deleteIntalnire(intalnire.getNumeActivitate());
        //intalnire.setLocatie("Bucuresti");
        //server.updateIntalnire(intalnire);
        //server.addIntalnire(intalnire);
        //System.out.println(server.getIntalnire(intalnire.getNumeActivitate()).afisareActivitate());
        /*ArrayList<Intalnire> intalniri = server.getIntalniri();
        for (Intalnire intalnire1 : intalniri) {
            System.out.println(intalnire1.afisareActivitate());
        }*/


    }
}
