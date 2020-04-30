package Servicii;

import Entitati.Activitate;
import Entitati.Planificare;

import java.text.SimpleDateFormat;
import java.util.*;
//Clasa de serviciu
public class Program {
    private int numarPlanificari;
    private HashMap<String, Activitate> activitati;
    // In activitati vor fi retinute toate activitatiile care nu sunt de tipul sarcina, deoarece pentru o sarcina
    // se pot crea mai multe planificari. Asadar pentru sarcini am creat Servicii.ManagerSarcini
    private Set<Planificare> planificari;

    public Program() {
        this.numarPlanificari = 0;
        activitati = new HashMap<String, Activitate>();
        planificari = new HashSet<Planificare>();
    }

    public String afisareProgram() {
        StringBuilder stringBuilder = new StringBuilder("Servicii.Program:");
        for (Planificare planificare : planificari) {
            stringBuilder.append(planificare.afisarePlanificare());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void adaugaPlanificare(String inceput, String sfarsit, Activitate activitate) throws Exception {
        if (!activitate.getTipActivitate().equals("sarcina")) {
            if (!numeFolosit(activitate.getNumeActivitate())) {
                activitati.put(activitate.getNumeActivitate(),activitate);
                planificari.add(new Planificare(inceput,sfarsit,activitate));
                this.numarPlanificari++;
            } else {
                throw new Exception("Nume deja folosit");
            }
        } else {
            planificari.add(new Planificare(inceput,sfarsit,activitate));
            this.numarPlanificari++;
        }
    }

    public void stergePlanificare(String numeActivitate) {
        activitati.remove(numeActivitate);
        planificari.removeIf(planificare -> planificare.getActivitate().getNumeActivitate().equals(numeActivitate));
    }

    public void stergePlanificare(Planificare planificare) {
        planificari.remove(planificare);
        activitati.remove(planificare.getActivitate().getNumeActivitate());
    }

    public boolean numeFolosit (String nume) {
        if (activitati.containsKey(nume)) {
            return true;
        }
        return false;
    }

    public List<Planificare> listaPlanificariInInterval(String inceputString, String sfarsitString) {
        List<Planificare> rezultat = new ArrayList<Planificare>();
        Date inceput,sfarsit;
        try {
            inceput = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(inceputString);
            sfarsit = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(sfarsitString);
            for (Planificare planificare : planificari) {
                if (!((planificare.getInceput().compareTo(inceput) < 0 &&
                        planificare.getSfarsit().compareTo(inceput) < 0) ||
                        planificare.getInceput().compareTo(sfarsit) > 0 &&
                                planificare.getSfarsit().compareTo(sfarsit) > 0)) {
                    rezultat.add(planificare);
                }
            }
        } catch (Exception e){
            System.out.println("Data introdusa nu este corecta");

        }
        return rezultat;
    }

    public void stergePlanificariInInterval(String inceputString, String sfarsitString) {
        List<Planificare> deSters = new ArrayList<Planificare>(listaPlanificariInInterval(inceputString, sfarsitString));
        for (Planificare planificare : deSters) {
            this.stergePlanificare(planificare);
        }
    }

    public int numarPlanificariRatate() {
        int k = 0;
        Date date = new Date();
        for (Planificare planificare : planificari) {
            if (planificare.getSfarsit().compareTo(date) < 0) {
                if (planificare.getRezultat().equals("In asteptare")) {
                    k++;
                    try {
                        planificare.setRezultat("Eveniment ratat");
                    } catch (Exception e) {
                    }
                }
            }
        }
        return k;
    }

    public Set<Planificare> listaNumarMaximPlanificari() {
        Set<Planificare> rezultat = new HashSet<Planificare>();
        List<Planificare> initial = new ArrayList<Planificare>();
        int k = 0;
        for (Planificare planificare : this.planificari) {
            int j = 0;
            while ((j < k) && (initial.get(j).getSfarsit().compareTo(planificare.getSfarsit()) < 0)) {
                j++;
            }
            initial.add(j, planificare);
            k++;
        }

        k = 0;
        rezultat.add(initial.get(0));
        for(int i = 1; i < initial.size();i++) {
            if (initial.get(i).getInceput().compareTo(initial.get(k).getSfarsit()) >= 0){
                k = i;
                rezultat.add(initial.get(i));
            }
        }
        return rezultat;

    }

    public Planificare getPlanificare(String nume) {
        for (Planificare planificare : planificari) {
            if (planificare.getActivitate().getNumeActivitate().equals(nume)) {
                return planificare;
            }
        }
        return null;
    }

    public Planificare getPlanificareSarcina(String nume, String inceput) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(inceput);
        } catch (Exception e){
            System.out.println("Data introdusa nu este corecta");
        }
        for (Planificare planificare : planificari) {
            if (planificare.getActivitate().getNumeActivitate().equals(nume) && planificare.getInceput().equals(date)) {
                return planificare;
            }
        }
        return null;
    }

}
