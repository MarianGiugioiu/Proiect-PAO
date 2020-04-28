import com.sun.javafx.binding.StringFormatter;
import javafx.util.Pair;

import java.text.SimpleDateFormat;
import java.util.*;

public class Sarcina extends Activitate  implements Comparable<Sarcina>{
    private List<Pair<String, String> > obiective;
    private int numarObiective;
    private int numarObiectiveRezolvate;
    private Date deadline;

    public Sarcina() {
        super();
        this.obiective = new ArrayList<Pair<String, String>>();
        this.numarObiective = 0;
        this.numarObiectiveRezolvate = 0;
        deadline = new Date();
    }

    public Sarcina(String numeActivitate, String locatie, String deadline) {
        super(numeActivitate, locatie, "sarcina");
        this.obiective = new ArrayList<Pair<String, String>>();
        this.numarObiective = 0;
        this.numarObiectiveRezolvate = 0;
        try {
            this.deadline = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(deadline);
        } catch (Exception e){
            System.out.println("Data introdusa nu este corecta");
        }
    }

    public Sarcina(Sarcina sarcina) {
        super(sarcina);
        this.obiective = new ArrayList<>(sarcina.obiective);
        this.numarObiective = sarcina.numarObiective;
        this.numarObiectiveRezolvate = sarcina.numarObiectiveRezolvate;
        this.deadline = sarcina.deadline;
    }

    public Date getDeadline() {
        return deadline;
    }

    public int getNumarObiective() {
        return numarObiective;
    }

    public void setDeadline(String deadline) {
        try {
            this.deadline = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(deadline);
        } catch (Exception e){
            System.out.println("Data introdusa nu este corecta");
        }
    }

    public void adaugaObiectiv(String obiectiv) {
        this.numarObiective++;
        this.obiective.add(new Pair<>(obiectiv,"nerezolvat"));
    }

    public void rezolvaObiectiv(int id) {
        this.numarObiectiveRezolvate++;
        this.obiective.set(id,new Pair<>(this.obiective.get(id).getKey(),"rezolvat"));
    }

    public boolean esteTerminat(){
        return this.numarObiectiveRezolvate == this.numarObiective;
    }

    public boolean esteExpirat() {
        Date date = new Date();
        if (this.deadline.compareTo(date) <= 0){
            return true;
        }
        return false;
    }

    public String afisareObiective() {
        StringBuilder string = new StringBuilder();
        string.append("Obiective:\n");
        for (int i = 0; i < this.numarObiective; i++) {
            string.append(String.format("%d) %s - %s\n", i, this.obiective.get(i).getKey(), this.obiective.get(i).getValue()));
        }
        return string.toString();
    }

    @Override
    public String afisareActivitate() {
        StringBuilder stringBuilder = new StringBuilder(super.afisareActivitate());
        stringBuilder.append(String.format("Deadline: %tD %tR\n",this.deadline,this.deadline));
        stringBuilder.append(this.afisareObiective());
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return afisareActivitate();
    }

    @Override
    public int compareTo(Sarcina o) {
        return this.deadline.compareTo(o.deadline);
    }


}