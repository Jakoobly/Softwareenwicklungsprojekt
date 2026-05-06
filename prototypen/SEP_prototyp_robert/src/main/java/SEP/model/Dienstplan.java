package SEP.model;

import java.util.ArrayList;


public class Dienstplan extends Plan {

    private ArrayList<Arbeiter> arbeiter = new ArrayList<>();


    private Vorrat vorrat;

    public Dienstplan(int id, String title, float timeScale, String theme) {
        super(id, title, timeScale, theme);
    }

    // Prüft ob Arbeiter sich zeitlich überschneiden
    public ArrayList<String> arbeiterKollision() {
        ArrayList<String> conflicts = new ArrayList<>();
        for (int i = 0; i < arbeiter.size(); i++) {
            for (int j = i + 1; j < arbeiter.size(); j++) {
                Arbeiter a = arbeiter.get(i);
                Arbeiter b = arbeiter.get(j);
                if (a.getStartTime() < b.getEndTime() && b.getStartTime() < a.getEndTime()) {
                    conflicts.add("Arbeiter überschneiden sich zeitlich: " + a.getName() + " / " + b.getName());
                }
            }
        }
        return conflicts;
    }

    public ArrayList<Arbeiter> getArbeiter() { return arbeiter; }

    public void addArbeiter(Arbeiter a) {
        if (!arbeiter.contains(a)) {
            arbeiter.add(a);
            addElement(a);
        }
    }

    public void removeArbeiter(Arbeiter a) {
        arbeiter.remove(a);
        objektAchse.remove(a);
    }

    public Vorrat getVorrat()         { return vorrat; }
    public void   setVorrat(Vorrat v) { this.vorrat = v; }
}
