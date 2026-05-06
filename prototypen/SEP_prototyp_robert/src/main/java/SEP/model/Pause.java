package SEP.model;


public class Pause {

    private int    id;
    private String name;
    private float  startzeit;
    private float  endzeit;

    public Pause(int id, String name, float startzeit, float endzeit) {
        this.id        = id;
        this.name      = name;
        this.startzeit = startzeit;
        this.endzeit   = endzeit;
    }


    public float getDuration() {
        return endzeit - startzeit;
    }


    public int    getId()                    { return id; }
    public void   setId(int id)              { this.id = id; }

    public String getName()                  { return name; }
    public void   setName(String name)       { this.name = name; }

    public float  getStartzeit()                      { return startzeit; }
    public void   setStartzeit(float startzeit)       { this.startzeit = startzeit; }

    public float  getEndzeit()                        { return endzeit; }
    public void   setEndzeit(float endzeit)           { this.endzeit = endzeit; }

    @Override
    public String toString() {
        return "Pause[id=" + id + ", name='" + name +
               "', start=" + startzeit + ", end=" + endzeit + "]";
    }
}
