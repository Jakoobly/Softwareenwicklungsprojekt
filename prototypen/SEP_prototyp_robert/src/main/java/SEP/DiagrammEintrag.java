package SEP;

import javafx.scene.paint.Color;

/**
 * Repräsentiert einen einzelnen Balken im Diagramm.
 * Verbindet Modellklassen (Prozess, Zug, Arbeiter) mit der Zeichenfläche/DiagrammCanvas.
 */
public class DiagrammEintrag {

    public String id;
    public String row;
    public int    startMin;
    public int    endMin;
    public Color  color;

    public DiagrammEintrag(String id, String row, int startMin, int endMin, Color color) {
        this.id       = id;
        this.row      = row;
        this.startMin = startMin;
        this.endMin   = endMin;
        this.color    = color;
    }

    public int getDuration() {
        return endMin - startMin;
    }
}
