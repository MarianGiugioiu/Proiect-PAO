import java.util.Collection;

public abstract class Activitate{
    protected String numeActivitate;
    protected String locatie;
    protected String tipActivitate;
    public Activitate() {
        this.numeActivitate = "";
        this.locatie = "";
        this.tipActivitate = "";
    }

    public Activitate(String numeActivitate, String locatie, String tipActivitate) {
        this.numeActivitate = numeActivitate;
        this.locatie = locatie;
        this.tipActivitate = tipActivitate;
    }

    public Activitate(Activitate activitate){
        this.numeActivitate = activitate.numeActivitate;
        this.locatie = activitate.locatie;
        this.tipActivitate = activitate.tipActivitate;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getNumeActivitate() {
        return numeActivitate;
    }

    public void setNumeActivitate(String numeActivitate) {
        this.numeActivitate = numeActivitate;
    }

    public String getTipActivitate() {
        return tipActivitate;
    }

    public String afisareActivitate(){
        StringBuilder string = new StringBuilder();
        string.append(String.format("Nume: %s\nLocatie: %s\nTipActivitate: %s\n",
                this.numeActivitate,this.locatie,this.tipActivitate));
        return string.toString();
    }


}
