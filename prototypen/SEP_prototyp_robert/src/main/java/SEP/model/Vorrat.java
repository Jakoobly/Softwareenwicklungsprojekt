package SEP.model;

import java.util.ArrayList;


public class Vorrat {

    private ArrayList<Planungselement> elements = new ArrayList<>();

    public Vorrat() {}


    public void addElement(Planungselement e) {
        if (!elements.contains(e)) elements.add(e);
    }

    public Planungselement removeElement(Planungselement e) {
        elements.remove(e);
        return e;
    }

    public ArrayList<Planungselement> getElements() { return elements; }

    @Override
    public String toString() {
        return "Vorrat[" + elements.size() + " elements]";
    }
}
