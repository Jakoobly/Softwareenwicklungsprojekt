package SEP.model;

/**
 * Untertyp stellt für Bildfahrpläne Stationen und für Fertigungspläne Maschinen dar.
 * TODO: Dienstpläne und Untertypen?
 */
public class Untertyp {

    private String name;
    private int    id;

    public Untertyp(int id, String name) {
        this.id   = id;
        this.name = name;
    }

    public int    getId()             { return id; }
    public void   setId(int id)       { this.id = id; }

    public String getName()              { return name; }
    public void   setName(String name)   { this.name = name; }

    @Override
    public String toString() {
        return "Untertyp[id=" + id + ", name='" + name + "']";
    }
}
