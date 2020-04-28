import java.util.ArrayList;
import java.util.List;

public class Intalnire  extends Activitate{
    private int numarMembrii;
    private List<String> membrii;
    private String scopIntalnire;

    public Intalnire() {
        super();
        this.numarMembrii = 0;
        this.membrii = new ArrayList<String>();
        this.scopIntalnire = "";
    }

    public Intalnire(String numeActivitate, String locatie, String scopIntalnire) {
        super(numeActivitate, locatie, "intalnire");
        this.numarMembrii = 0;
        this.membrii = new ArrayList<String>();
        this.scopIntalnire = scopIntalnire;
    }

    public Intalnire(Intalnire intalnire) {
        super(intalnire);
        this.numarMembrii = intalnire.numarMembrii;
        this.membrii = new ArrayList<String>(intalnire.membrii);
        this.scopIntalnire = intalnire.scopIntalnire;
    }

    public int getNumarMembrii() {
        return numarMembrii;
    }

    public String getScopIntalnire() {
        return scopIntalnire;
    }

    public void setScopIntalnire(String scopIntalnire) {
        this.scopIntalnire = scopIntalnire;
    }

    public void adaugaMembru(String nume) {
        this.membrii.add(nume);
    }

    public void stergeMembru(String nume) {
        membrii.remove(nume);
    }

    @Override
    public String afisareActivitate() {
        StringBuilder stringBuilder = new StringBuilder(super.afisareActivitate());
        stringBuilder.append(String.format("Scopul intalnirii: %s\nMembrii: ",this.scopIntalnire));
        for (String membru : membrii) {
            if (!membru.equals(membrii.get(membrii.size()-1))) {
                stringBuilder.append(String.format("%s, ", membru));
            } else {
                stringBuilder.append(String.format("%s\n", membru));
            }
        }
        return stringBuilder.toString();
    }
}
