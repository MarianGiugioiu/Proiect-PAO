package Entitati;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Planificare implements Comparable<Planificare>{
    private Date inceput;
    private Date sfarsit;
    private Activitate activitate;
    private String rezultat;

    public Planificare(String inceput, String sfarsit, Activitate activitate) {
        try {
            this.inceput = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(inceput);
        } catch (Exception e){
            System.out.println("Data introdusa nu este corecta");
        }
        try {
            this.sfarsit = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(sfarsit);
        } catch (Exception e){
            System.out.println("Data introdusa nu este corecta");
        }
        this.activitate = activitate;
        this.rezultat = "In asteptare";
    }

    public String verificaStare() {
        Date date = new Date();
        if (date.compareTo(this.inceput) < 0) {
            return "Neinceput";
        } else if (date.compareTo(this.sfarsit) > 0) {
            return "Terminat";
        }
        return "In desfasurare";
    }

    public void setRezultat(String rezultat) throws Exception {
        if (this.verificaStare().equals("Terminat")) {
            this.rezultat = rezultat;
        } else {
            throw new Exception("Activitatea inca nu s-a terminat");
        }
    }

    public Activitate getActivitate() {
        return this.activitate;
    }

    public Date getInceput() {
        return inceput;
    }

    public Date getSfarsit() {
        return sfarsit;
    }

    public String getRezultat() {
        return rezultat;
    }

    public void setInceput(String inceput) {
        try {
            this.inceput = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(inceput);
        } catch (Exception e){
            System.out.println("Data introdusa nu este corecta");
        }
    }

    public void setSfarsit(String sfarsit) {
        try {
            this.sfarsit = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(sfarsit);
        } catch (Exception e){
            System.out.println("Data introdusa nu este corecta");
        }
    }

    public String afisarePlanificare() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Incepe la: %tD %tR\nSe termina la: %tD %tR\n",
                this.inceput, this.inceput,
                this.sfarsit, this.sfarsit));
        stringBuilder.append(this.activitate.afisareActivitate());
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Planificare o) {
        if (this.inceput.compareTo(o.inceput) == 0) {
            return this.sfarsit.compareTo(o.sfarsit);
        }
        return this.inceput.compareTo(o.inceput);
    }

}
