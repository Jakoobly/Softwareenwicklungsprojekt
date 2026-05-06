package SEP.model;


public class Prozess extends Planungselement {

    private String typ;

    public Prozess(int id, String name, float startTime, float endTime, String typ) {
        super(id, name, startTime, endTime);
        this.typ = typ;
    }

    public String getTyp()            { return typ; }
    public void   setTyp(String typ)  { this.typ = typ; }
}
