package gantt.model;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

// Klasse für die Eigenschaften des Plans

public class Plan {

    private final LocalTime startTime;
    private final LocalTime endTime;

    // Reihenfolge der Zeilen im Diagramm
    private final List<String> rowOrder;

    // alle Objekte des Plans
    private final List<PlanObjekt> objects;

    // Farbschema der Zeilen / Objekte
    private final Map<String, String> colors;

    public Plan(LocalTime startTime,
                LocalTime endTime,
                List<String> rowOrder,
                List<PlanObjekt> objects,
                Map<String, String> colors) {

        this.startTime = startTime;
        this.endTime = endTime;
        this.rowOrder = rowOrder;
        this.objects = objects;
        this.colors = colors;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public List<String> getRowOrder() {
        return rowOrder;
    }

    public List<PlanObjekt> getObjects() {
        return objects;
    }

    public Map<String, String> getColors() {
        return colors;
    }
}