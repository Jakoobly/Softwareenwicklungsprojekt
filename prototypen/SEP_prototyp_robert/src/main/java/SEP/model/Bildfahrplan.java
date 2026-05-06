package SEP.model;

import java.util.ArrayList;


public class Bildfahrplan extends Plan {

    private ArrayList<Zug>    zuege    = new ArrayList<>();
    private ArrayList<Untertyp> stationen = new ArrayList<>();

    public Bildfahrplan(int id, String title, float timeScale, String theme) {
        super(id, title, timeScale, theme);
    }




    public ArrayList<Zug> getZuege()     { return zuege; }

// Prüft, ob sich Züge zeitlich überschneiden
    public ArrayList<String> zugKollision() {
        ArrayList<String> konflikte = new ArrayList<>();
        for (int i = 0; i < zuege.size(); i++) {
            for (int j = i + 1; j < zuege.size(); j++) {
                Zug a = zuege.get(i);
                Zug b = zuege.get(j);
                if (a.getStartTime() < b.getEndTime() && b.getStartTime() < a.getEndTime()) {
                    konflikte.add("Zugkollision: " + a.getName() + " / " + b.getName());
                }
            }
        }
        return konflikte;
    }

    public void addZug(Zug z) {
        if (!zuege.contains(z)) {
            zuege.add(z);
            addElement(z);   // also register on the object axis
        }
    }

    public void removeZug(Zug z) {
        zuege.remove(z);
        objektAchse.remove(z);
    }

    public ArrayList<Untertyp> getStationen()             { return stationen; }
    public void addStation(Untertyp s)                   { stationen.add(s); }
    public void removeStation(Untertyp s)                { stationen.remove(s); }
}
