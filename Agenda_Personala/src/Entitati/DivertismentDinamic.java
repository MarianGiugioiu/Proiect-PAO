package Entitati;

import java.util.ArrayList;
import java.util.List;

public class DivertismentDinamic extends Divertisment {
    private List<String> mijloaceDeTransport;
    private List<String> obiectiveDeVizitat;

    public DivertismentDinamic() {
        super();
        this.mijloaceDeTransport = new ArrayList<String>();
        this.obiectiveDeVizitat = new ArrayList<String>();
    }

    public DivertismentDinamic(String numeActivitate, String locatie, int entuziasm,
                               List<String> mijloaceDeTransport, List<String> obiectiveDeVizitat) {
        super(numeActivitate, locatie, entuziasm);
        this.mijloaceDeTransport = new ArrayList<String>(mijloaceDeTransport);
        this.obiectiveDeVizitat = new ArrayList<String>(obiectiveDeVizitat);
    }

    public DivertismentDinamic(DivertismentDinamic divertismentDinamic) {
        super(divertismentDinamic);
        this.mijloaceDeTransport = new ArrayList<String>(divertismentDinamic.mijloaceDeTransport);
        this.obiectiveDeVizitat = new ArrayList<String>(divertismentDinamic.obiectiveDeVizitat);
    }

    public List<String> getMijloaceDeTransport() {
        return mijloaceDeTransport;
    }

    public List<String> getObiectiveDeVizitat() {
        return obiectiveDeVizitat;
    }

    @Override
    public String afisareActivitate() {
        StringBuilder stringBuilder = new StringBuilder(super.afisareActivitate());
        stringBuilder.append(String.format("Mijloace de transport: %s\n",String.join(",", mijloaceDeTransport)));
        stringBuilder.append(String.format("Obiective de vizitat: %s\n",String.join(",", obiectiveDeVizitat)));
        return stringBuilder.toString();

    }
}
