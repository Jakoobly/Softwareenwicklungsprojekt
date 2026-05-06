package SEP.model;


public abstract class Planungselement {

    protected int    id;
    protected String name;
    protected float  startTime;
    protected float  endTime;

    protected Pause pause;


    protected Untertyp untertyp;

    protected Planungselement(int id, String name, float startTime, float endTime) {
        this.id        = id;
        this.name      = name;
        this.startTime = startTime;
        this.endTime   = endTime;
    }


    public float getDuration() {
        return endTime - startTime;
    }


    public Untertyp getDetails() {
        return untertyp;
    }


    public int    getId()        { return id; }
    public String getName()      { return name; }
    public void   setName(String name) { this.name = name; }

    public float  getStartTime()              { return startTime; }
    public void   setStartTime(float startTime){ this.startTime = startTime; }

    public float  getEndTime()               { return endTime; }
    public void   setEndTime(float endTime)  { this.endTime = endTime; }

    public Pause    getPause()               { return pause; }
    public void     setPause(Pause pause)    { this.pause = pause; }

    public Untertyp getUntertyp()                  { return untertyp; }
    public void     setUntertyp(Untertyp untertyp) { this.untertyp = untertyp; }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
               "[id=" + id + ", name='" + name +
               "', start=" + startTime + ", end=" + endTime + "]";
    }
}
