package SEP.model;

import java.util.ArrayList;


public class Fertigungsplan extends Plan {

    private ArrayList<Prozess> prozesse = new ArrayList<>();
    private ArrayList<Untertyp> maschinen = new ArrayList<>();

    public Fertigungsplan(int id, String title, float timeScale, String theme) {
        super(id, title, timeScale, theme);
    }

    // prüft ob Maschinen sich zeitlich überschneiden

    public ArrayList<String> maschinenKollision() {
        ArrayList<String> konflikte = new ArrayList<>();
        for (int i = 0; i < prozesse.size(); i++) {
            for (int j = i + 1; j < prozesse.size(); j++) {
                Prozess a = prozesse.get(i);
                Prozess b = prozesse.get(j);
                if (a.getStartTime() < b.getEndTime() && b.getStartTime() < a.getEndTime()) {
                    konflikte.add("Maschinen überschneiden sich zeitlich: " + a.getName() + " / " + b.getName());
                }
            }
        }
        return konflikte;
    }

    public ArrayList<Prozess> getProzesse() { return prozesse; }

    public void addProzess(Prozess p) {
        if (!prozesse.contains(p)) {
            prozesse.add(p);
            addElement(p);
        }
    }

    public void removeProzess(Prozess p) {
        prozesse.remove(p);
        objektAchse.remove(p);
    }

    public void addMaschine(Untertyp m)    { maschinen.add(m); }
    public void removeMaschine(Untertyp m) { maschinen.remove(m); }
    public ArrayList<Untertyp> getMaschinen() { return maschinen; }
}
