import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ManagerSarcini {
    private int numarSarcini;
    private Set<Sarcina> sarcini;

    public ManagerSarcini() {
        numarSarcini = 0;
        sarcini = new TreeSet<>();
    }

    public void adaugaSarcina(Sarcina sarcina) throws Exception {
        if (!numeFolosit(sarcina.getNumeActivitate())) {
            sarcini.add(sarcina);
        } else {
            throw new Exception("Nume deja folosit");
        }
    }
    public void stergeSarcina(String numeSarcina) {
        sarcini.removeIf(sarcina -> sarcina.getNumeActivitate().equals(numeSarcina));
    }

    public Sarcina getSarcina (String numeSarcina) {
        for (Sarcina sarcina : sarcini) {
            if (sarcina.getNumeActivitate().equals(numeSarcina)){
                return sarcina;
            }
        }
        return null;
    }

    public Set<Sarcina> updateStatus() {
        Set<Sarcina> sarciniExpirate = new HashSet<Sarcina>();
        for (Sarcina sarcina : this.sarcini) {
            if (sarcina.esteExpirat()) {
                sarciniExpirate.add(sarcina);
            }
        }
        sarcini.removeIf(sarcina -> (sarcina.esteTerminat() || sarcina.esteExpirat()));
        return sarciniExpirate;
    }

    public String afisareSarcini() {
        StringBuilder stringBuilder = new StringBuilder();
        int k = 1;
        for (Sarcina sarcina : sarcini) {
            stringBuilder.append(String.format("%d) %s\n", k++, sarcina.afisareActivitate()));
        }
        return stringBuilder.toString();
    }

    public boolean numeFolosit(String numeSarcina) {
        for (Sarcina sarcina : sarcini) {
            if (sarcina.getNumeActivitate().equals(numeSarcina)) {
                return true;
            }
        }
        return false;
    }


}

