public class DivertismentStatic extends Divertisment{
    private String asteptari;
    private String tip;

    public DivertismentStatic() {
        super();
        this.asteptari = "";
        this.tip = "";
    }

    public DivertismentStatic(String numeActivitate, String locatie, int entuziasm, String tip, String asteptari){
        super(numeActivitate, locatie, entuziasm);
        this.asteptari = asteptari;
        this.tip = tip;
    }

    public DivertismentStatic(DivertismentStatic divertismentStatic) {
        super(divertismentStatic);
        this.asteptari = divertismentStatic.asteptari;
        this.tip = divertismentStatic.tip;
    }

    public String getTip() {
        return tip;
    }

    public String getAsteptari() {
        return asteptari;
    }

    @Override
    public String afisareActivitate() {
        StringBuilder stringBuilder = new StringBuilder(super.afisareActivitate());
        stringBuilder.append(String.format("Tip: %s\nAsteptari: %s\n", this.tip, this.asteptari));
        return stringBuilder.toString();
    }
}
