package gantt.model.objekt;

import java.time.LocalTime;

// Klasse für die einzelnen Objekte des Diagramms

public class PlanObjekt {
    private final String row;
    private final String name;
    private final LocalTime start;
    private final LocalTime end;

    public PlanObjekt(String row, String name, String start, String end) {
        this.row = row;
        this.name = name;
        this.start = LocalTime.parse(start);
        this.end = LocalTime.parse(end);
    }

    public String getRow() {
        return row;
    }

    public String getName() {
        return name;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}