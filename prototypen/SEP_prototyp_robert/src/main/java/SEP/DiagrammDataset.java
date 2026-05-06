package SEP;

import java.util.List;

/**
 * Enthält alle Daten für einen Plan:
 * - Titel, Reihennamen, Zeitfenster und alle Balken.
 * TODO: Über Methoden aus Modellklassen befüllen.
 */
public class DiagrammDataset {

    public String           title;
    public List<String>     rows;
    public int              startMin;
    public int              endMin;
    public List<DiagrammEintrag> entries;

    public DiagrammDataset(String title, List<String> rows,
                           int startMin, int endMin, List<DiagrammEintrag> entries) {
        this.title    = title;
        this.rows     = rows;
        this.startMin = startMin;
        this.endMin   = endMin;
        this.entries = entries;
    }
}
