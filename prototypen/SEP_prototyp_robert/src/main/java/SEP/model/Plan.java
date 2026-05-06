package SEP.model;

import java.util.ArrayList;
import java.util.List;

// TODO: insertFromVorrat(Vorrat, Planungselement)
// TODO: movetoVorrat(Vorrat, Planungselement)
// TODO: addAchseneintrag(String)
// TODO: removeAchseneintrag(String)
// TODO: moveElement(int anker)
// TODO: moveElements(intanker)

public abstract class Plan {

    protected String title;
    protected int    id;

    // Planungselemente auf der Objektachse
    protected List<Planungselement> objektAchse = new ArrayList<>();

    // Achseneinträge als einfache Strings (z.B. Zeitstempel oder Stationsnamen)
    protected ArrayList<String> elements = new ArrayList<>();

    protected float  timeScale;
    protected String theme;

    protected Plan(int id, String title, float timeScale, String theme) {
        this.id        = id;
        this.title     = title;
        this.timeScale = timeScale;
        this.theme     = theme;
    }

    // Fügt ein Planungselement zur Objektachse hinzu
    public void addElement(Planungselement e) {
        if (!objektAchse.contains(e)) objektAchse.add(e);
    }

    // Ändert den Zeitmassstab des Plans
    public void changeScale(int newScale) {
        this.timeScale = newScale;
    }

    // Entfernt das letzte Element von der Objektachse
    public void deleteElement() {
        if (!objektAchse.isEmpty()) objektAchse.remove(objektAchse.size() - 1);
    }


    // Prüft auf Zeitkonflikte zwischen Elementen
    public ArrayList<String> checkKonflikte() {
        ArrayList<String> konflikte = new ArrayList<>();
        for (int i = 0; i < objektAchse.size(); i++) {
            for (int j = i + 1; j < objektAchse.size(); j++) {
                Planungselement a = objektAchse.get(i);
                Planungselement b = objektAchse.get(j);
                if (a.getStartTime() < b.getEndTime() && b.getStartTime() < a.getEndTime()) {
                    konflikte.add("Zeitueberschneidung: " + a.getName() + " / " + b.getName());
                }
            }
        }
        return konflikte;
    }


    public int                   getId()        { return id; }
    public String                getTitle()     { return title; }
    public float                 getTimeScale() { return timeScale; }
    public String                getTheme()     { return theme; }
    public List<Planungselement> getObjektAchse(){ return objektAchse; } //später die hard-code variante aus diagramm canvas ersetzen
    public ArrayList<String>     getElements()  { return elements; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + id + ", title='" + title + "']";
    }
}
