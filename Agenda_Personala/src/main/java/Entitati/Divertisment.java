package Entitati;

public abstract class Divertisment extends Activitate implements Comparable<Divertisment>{
    private int entuziasm;

    public Divertisment() {
        super();
        this.entuziasm = 0;
    }

    public Divertisment(String numeActivitate, String locatie, int entuziasm) {
        super(numeActivitate, locatie, "divertisment");
        this.entuziasm = entuziasm;
    }

    public Divertisment(Divertisment divertisment) {
        super(divertisment);
        this.entuziasm = divertisment.entuziasm;
    }

    public int getEntuziasm() {
        return entuziasm;
    }


    public void setEntuziasm(int entuziasm) {
        this.entuziasm = entuziasm;
    }

    @Override
    public String afisareActivitate() {
        StringBuilder stringBuilder = new StringBuilder(super.afisareActivitate());
        stringBuilder.append(String.format("Nivel Entuziasm: %d\n",this.entuziasm));
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Divertisment o) {
        if (this.entuziasm < o.entuziasm) {
            return -1;
        } else if (this.entuziasm > o.entuziasm) {
            return 1;
        }
        return 0;
    }

}
