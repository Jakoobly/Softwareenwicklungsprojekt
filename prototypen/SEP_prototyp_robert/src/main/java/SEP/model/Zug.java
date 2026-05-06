package SEP.model;

public class Zug extends Planungselement {

    private Untertyp startStation;
    private Untertyp endStation;

    public Zug(int id, String name, float startTime, float endTime) {
        super(id, name, startTime, endTime);
    }

    public Untertyp getStartStation()                    { return startStation; }
    public void     setStartStation(Untertyp station)    { this.startStation = station; }

    public Untertyp getEndStation()                      { return endStation; }
    public void     setEndStation(Untertyp station)      { this.endStation = station; }
}