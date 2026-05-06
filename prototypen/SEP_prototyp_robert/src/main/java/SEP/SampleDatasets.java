package SEP;

import SEP.model.Fertigungsplan;
import SEP.model.Bildfahrplan;
import SEP.model.Prozess;
import SEP.model.Zug;
import SEP.model.Untertyp;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Enthält Beispieldaten fuer alle drei Plantypen.
 * TODO: Ersetzen durch Laden über Modellklassen
 */
public class SampleDatasets {

    // Hilfsmethode minuten ab Mitternacht
    private static int t(int stunden, int minuten) {
        return stunden * 60 + minuten;
    }

    // --- Dienstplan -------------------------------------------------------

    public static DiagrammDataset dienstplan() {
        List<String> rows = new ArrayList<>();
        rows.add("güterzug");
        rows.add("1a");
        rows.add("1b");
        rows.add("7");
        rows.add("5");
        rows.add("6");
        rows.add("2");
        rows.add("3");
        rows.add("4");

        Color lila   = Color.web("#9B59B6");
        Color rot    = Color.web("#C0392B");
        Color orange = Color.web("#E67E22");
        Color tuerkis= Color.web("#16A085");
        Color braun  = Color.web("#7D5A44");
        Color oliv   = Color.web("#A0A020");
        Color gruen  = Color.web("#27AE60");
        Color blau   = Color.web("#2980B9");
        Color grau   = Color.web("#606060");

        List<DiagrammEintrag> eintraege = new ArrayList<>();

        eintraege.add(new DiagrammEintrag("P 200", "4", t(6,0),   t(6,28),  lila));
        eintraege.add(new DiagrammEintrag("P 201", "4", t(6,30),  t(6,58),  lila));
        eintraege.add(new DiagrammEintrag("P 202", "4", t(7,0),   t(7,35),  lila));
        eintraege.add(new DiagrammEintrag("P 203", "4", t(9,30),  t(9,52),  lila));
        eintraege.add(new DiagrammEintrag("P 204", "4", t(10,0),  t(10,22), lila));
        eintraege.add(new DiagrammEintrag("P 205", "4", t(10,25), t(10,50), lila));
        eintraege.add(new DiagrammEintrag("P 206", "4", t(11,0),  t(11,22), lila));
        eintraege.add(new DiagrammEintrag("P 207", "4", t(11,25), t(11,52), lila));
        eintraege.add(new DiagrammEintrag("P 208", "4", t(12,0),  t(12,40), lila));
        eintraege.add(new DiagrammEintrag("P 209", "4", t(13,30), t(13,58), lila));
        eintraege.add(new DiagrammEintrag("P 210", "4", t(14,30), t(14,58), lila));
        eintraege.add(new DiagrammEintrag("P 211", "4", t(15,30), t(15,58), lila));
        eintraege.add(new DiagrammEintrag("P 212", "4", t(16,0),  t(16,38), lila));
        eintraege.add(new DiagrammEintrag("P 213", "4", t(17,30), t(17,58), lila));

        eintraege.add(new DiagrammEintrag("P 102", "3", t(6,0),   t(6,33),  rot));
        eintraege.add(new DiagrammEintrag("P 103", "3", t(7,0),   t(7,43),  rot));
        eintraege.add(new DiagrammEintrag("P 106", "3", t(8,30),  t(8,58),  rot));
        eintraege.add(new DiagrammEintrag("P 107", "3", t(11,0),  t(11,38), rot));
        eintraege.add(new DiagrammEintrag("P 110", "3", t(12,0),  t(12,38), rot));
        eintraege.add(new DiagrammEintrag("P 111", "3", t(16,0),  t(16,38), rot));
        eintraege.add(new DiagrammEintrag("P 114", "3", t(17,30), t(17,58), rot));

        eintraege.add(new DiagrammEintrag("P 101", "2", t(6,0),   t(6,28),  orange));
        eintraege.add(new DiagrammEintrag("P 104", "2", t(7,0),   t(7,28),  orange));
        eintraege.add(new DiagrammEintrag("P 105", "2", t(9,30),  t(9,58),  orange));
        eintraege.add(new DiagrammEintrag("P 108", "2", t(10,30), t(11,8),  orange));
        eintraege.add(new DiagrammEintrag("P 109", "2", t(14,0),  t(14,48), orange));
        eintraege.add(new DiagrammEintrag("P 112", "2", t(15,10), t(15,48), orange));
        eintraege.add(new DiagrammEintrag("P 113", "2", t(17,0),  t(17,48), orange));

        eintraege.add(new DiagrammEintrag("Ng 401", "6", t(6,30),  t(8,10),  tuerkis));
        eintraege.add(new DiagrammEintrag("Ng 402", "6", t(10,0),  t(11,20), tuerkis));
        eintraege.add(new DiagrammEintrag("Ng 403", "6", t(12,30), t(14,0),  tuerkis));
        eintraege.add(new DiagrammEintrag("Ng 404", "6", t(16,0),  t(17,58), tuerkis));

        eintraege.add(new DiagrammEintrag("Ng 321", "5", t(6,0),   t(8,28),  braun));
        eintraege.add(new DiagrammEintrag("Ng 322", "5", t(9,0),   t(11,8),  braun));
        eintraege.add(new DiagrammEintrag("Ng 323", "5", t(12,0),  t(14,8),  braun));
        eintraege.add(new DiagrammEintrag("Ng 324", "5", t(14,40), t(16,28), braun));

        eintraege.add(new DiagrammEintrag("Lz 940", "7", t(7,30),  t(7,45),  oliv));
        eintraege.add(new DiagrammEintrag("Üg 941", "7", t(7,48),  t(8,3),   oliv));
        eintraege.add(new DiagrammEintrag("Üg 942", "7", t(8,5),   t(8,18),  oliv));
        eintraege.add(new DiagrammEintrag("Üg 933", "7", t(8,20),  t(8,38),  oliv));
        eintraege.add(new DiagrammEintrag("Üg 934", "7", t(9,10),  t(9,48),  oliv));
        eintraege.add(new DiagrammEintrag("Lz 941", "7", t(11,20), t(11,38), oliv));
        eintraege.add(new DiagrammEintrag("Lz 942", "7", t(12,30), t(12,48), oliv));
        eintraege.add(new DiagrammEintrag("Üg 935", "7", t(13,0),  t(13,13), oliv));
        eintraege.add(new DiagrammEintrag("Üg 936", "7", t(13,15), t(13,33), oliv));
        eintraege.add(new DiagrammEintrag("Üg 943", "7", t(14,30), t(14,43), oliv));
        eintraege.add(new DiagrammEintrag("Üg 944", "7", t(14,47), t(15,8),  oliv));
        eintraege.add(new DiagrammEintrag("Lz 943", "7", t(15,30), t(15,53), oliv));

        eintraege.add(new DiagrammEintrag("E 21",  "1b", t(7,30),  t(7,53),  gruen));
        eintraege.add(new DiagrammEintrag("E 211", "1b", t(7,55),  t(8,8),   oliv));
        eintraege.add(new DiagrammEintrag("E 212", "1b", t(8,8),   t(8,18),  oliv));
        eintraege.add(new DiagrammEintrag("E 22",  "1b", t(8,18),  t(8,38),  gruen));
        eintraege.add(new DiagrammEintrag("E 23",  "1b", t(12,20), t(12,43), gruen));
        eintraege.add(new DiagrammEintrag("E 214", "1b", t(12,45), t(12,58), oliv));
        eintraege.add(new DiagrammEintrag("E 24",  "1b", t(13,0),  t(13,23), gruen));
        eintraege.add(new DiagrammEintrag("E 25",  "1b", t(16,50), t(17,13), gruen));
        eintraege.add(new DiagrammEintrag("E 216", "1b", t(17,15), t(17,28), oliv));
        eintraege.add(new DiagrammEintrag("E 26",  "1b", t(17,28), t(17,53), gruen));

        eintraege.add(new DiagrammEintrag("E 11",   "1a", t(7,30),  t(7,48),  blau));
        eintraege.add(new DiagrammEintrag("E 211b", "1a", t(7,50),  t(8,3),   oliv));
        eintraege.add(new DiagrammEintrag("E 12",   "1a", t(8,3),   t(8,23),  blau));
        eintraege.add(new DiagrammEintrag("E 13",   "1a", t(12,10), t(12,28), blau));
        eintraege.add(new DiagrammEintrag("E 213",  "1a", t(12,30), t(12,43), oliv));
        eintraege.add(new DiagrammEintrag("E 14",   "1a", t(12,45), t(13,8),  blau));
        eintraege.add(new DiagrammEintrag("E 15",   "1a", t(16,40), t(16,58), blau));
        eintraege.add(new DiagrammEintrag("E 215",  "1a", t(17,0),  t(17,13), oliv));
        eintraege.add(new DiagrammEintrag("E 16",   "1a", t(17,13), t(17,33), blau));

        eintraege.add(new DiagrammEintrag("B 621", "güterzug", t(13,20), t(13,58), grau));
        eintraege.add(new DiagrammEintrag("B 622", "güterzug", t(14,50), t(15,23), grau));

        return new DiagrammDataset("Dienstplan", rows, t(6,0), t(18,0), eintraege);
    }

    // --- Fertigungsplan ---------------------------------------------------

    public static DiagrammDataset fertigungsplan() {

        // Maschinen als Untertyp-Objekte anlegen
        Untertyp Maschine1   = new Untertyp(1, "Fräsen");
        Untertyp maschine2 = new Untertyp(2, "Maschine 2");
        Untertyp maschine3 = new Untertyp(3, "Maschine 3");
        Untertyp maschine4 = new Untertyp(4, "Maschine 4");
        Untertyp maschine5 = new Untertyp(5, "Maschine 5");

        // Fertigungsplan-Modellobjekt aufbauen
        Fertigungsplan fp = new Fertigungsplan(1, "Fertigungsplan", 1.0f, "standard");
        fp.addMaschine(Maschine1);
        fp.addMaschine(maschine2);
        fp.addMaschine(maschine3);
        fp.addMaschine(maschine4);
        fp.addMaschine(maschine5);

        // Prozesse anlegen und jeweils einer Maschine (Untertyp) zuweisen
        Prozess a1 = new Prozess(101, "Auftrag A1", t(6,0),   t(8,30),  "Fräsen");
        Prozess a2 = new Prozess(102, "Auftrag A2", t(9,0),   t(12,0),  "Fräsen");
        Prozess a3 = new Prozess(103, "Auftrag A3", t(13,0),  t(16,30), "Fräsen");
        Prozess b1 = new Prozess(104, "Auftrag B1", t(6,30),  t(10,0),  "Drehen");
        Prozess b2 = new Prozess(105, "Auftrag B2", t(10,30), t(14,0),  "Drehen");
        Prozess b3 = new Prozess(106, "Auftrag B3", t(14,30), t(17,30), "Drehen");
        Prozess c1 = new Prozess(107, "Auftrag C1", t(7,0),   t(9,30),  "Bohren");
        Prozess c2 = new Prozess(108, "Auftrag C2", t(11,0),  t(13,30), "Bohren");
        Prozess c3 = new Prozess(109, "Auftrag C3", t(15,0),  t(18,0),  "Bohren");
        Prozess d1 = new Prozess(110, "Auftrag D1", t(6,0),   t(11,0),  "Schleifen");
        Prozess d2 = new Prozess(111, "Auftrag D2", t(12,0),  t(16,0),  "Schleifen");
        Prozess e1 = new Prozess(112, "Auftrag E1", t(8,0),   t(10,30), "Schweißen");
        Prozess e2 = new Prozess(113, "Auftrag E2", t(11,30), t(15,0),  "Schweißen");
        Prozess e3 = new Prozess(114, "Auftrag E3", t(15,30), t(17,30), "Schweißen");

        // Untertyp (Maschine) dem jeweiligen Prozess zuweisen
        a1.setUntertyp(Maschine1);   a2.setUntertyp(Maschine1);   a3.setUntertyp(Maschine1);
        b1.setUntertyp(maschine2); b2.setUntertyp(maschine2); b3.setUntertyp(maschine2);
        c1.setUntertyp(maschine3); c2.setUntertyp(maschine3); c3.setUntertyp(maschine3);
        d1.setUntertyp(maschine4); d2.setUntertyp(maschine4);
        e1.setUntertyp(maschine5); e2.setUntertyp(maschine5); e3.setUntertyp(maschine5);

        // Prozesse zum Fertigungsplan hinzufügen
        fp.addProzess(a1); fp.addProzess(a2); fp.addProzess(a3);
        fp.addProzess(b1); fp.addProzess(b2); fp.addProzess(b3);
        fp.addProzess(c1); fp.addProzess(c2); fp.addProzess(c3);
        fp.addProzess(d1); fp.addProzess(d2);
        fp.addProzess(e1); fp.addProzess(e2); fp.addProzess(e3);

        // Reihen aus den Maschinen-Untertypen ableiten (Option A)
        List<String> rows = new ArrayList<>();
        for (Untertyp m : fp.getMaschinen()) {
            rows.add(m.getName());
        }

        // DiagrammEintraege aus Prozessen erzeugen
        Color blau   = Color.web("#2980B9");
        Color gruen  = Color.web("#27AE60");
        Color orange = Color.web("#E67E22");
        Color lila   = Color.web("#9B59B6");
        Color rot    = Color.web("#C0392B");

        List<DiagrammEintrag> eintraege = new ArrayList<>();
        for (Prozess p : fp.getProzesse()) {
            Color farbe;
            // Farbe anhand der Maschine bestimmen
            if      (p.getUntertyp() == Maschine1)   farbe = blau;
            else if (p.getUntertyp() == maschine2) farbe = gruen;
            else if (p.getUntertyp() == maschine3) farbe = orange;
            else if (p.getUntertyp() == maschine4) farbe = lila;
            else                                   farbe = rot;

            eintraege.add(new DiagrammEintrag(
                    p.getName(),
                    p.getUntertyp().getName(), // Reihe = Maschinenname
                    (int) p.getStartTime(),
                    (int) p.getEndTime(),
                    farbe
            ));
        }

        return new DiagrammDataset("Fertigungsplan", rows, t(6,0), t(18,0), eintraege);
    }

    // --- Bildfahrplan -----------------------------------------------------

    public static DiagrammDataset bildfahrplan() {

        // Stationen als Untertyp-Objekte anlegen
        Untertyp stuttgartHbf  = new Untertyp(1, "Stuttgart Hbf");
        Untertyp karlsruheHbf  = new Untertyp(2, "Karlsruhe Hbf");
        Untertyp frankfurtHbf  = new Untertyp(3, "Frankfurt Hbf");
        Untertyp mannheimHbf   = new Untertyp(4, "Mannheim Hbf");
        Untertyp heidelbergHbf = new Untertyp(5, "Heidelberg Hbf");

        // Bildfahrplan-Modellobjekt aufbauen
        Bildfahrplan bfp = new Bildfahrplan(2, "Bildfahrplan", 1.0f, "standard");
        bfp.addStation(stuttgartHbf);
        bfp.addStation(karlsruheHbf);
        bfp.addStation(frankfurtHbf);
        bfp.addStation(mannheimHbf);
        bfp.addStation(heidelbergHbf);

        // Züge anlegen mit Start- und Endstation
        Zug ice571 = new Zug(301, "ICE 571", t(6,0),   t(8,30));
        Zug ice573 = new Zug(302, "ICE 573", t(9,0),   t(11,30));
        Zug ice575 = new Zug(303, "ICE 575", t(12,0),  t(14,30));
        Zug ice577 = new Zug(304, "ICE 577", t(15,0),  t(17,30));
        Zug ic2312 = new Zug(305, "IC 2312", t(6,30),  t(9,0));
        Zug ic2314 = new Zug(306, "IC 2314", t(10,0),  t(12,30));
        Zug ic2316 = new Zug(307, "IC 2316", t(13,30), t(16,0));
        Zug re431  = new Zug(308, "RE 19431",t(6,0),   t(7,30));
        Zug re433  = new Zug(309, "RE 19433",t(8,0),   t(9,30));
        Zug re435  = new Zug(310, "RE 19435",t(10,30), t(12,0));
        Zug re437  = new Zug(311, "RE 19437",t(13,0),  t(14,30));
        Zug re439  = new Zug(312, "RE 19439",t(15,30), t(17,0));
        Zug az501  = new Zug(313, "Az 501",  t(7,0),   t(8,0));
        Zug az502  = new Zug(314, "Az 502",  t(13,0),  t(14,30));

        // Start- und Endstation setzen
        ice571.setStartStation(stuttgartHbf);  ice571.setEndStation(frankfurtHbf);
        ice573.setStartStation(stuttgartHbf);  ice573.setEndStation(frankfurtHbf);
        ice575.setStartStation(stuttgartHbf);  ice575.setEndStation(frankfurtHbf);
        ice577.setStartStation(stuttgartHbf);  ice577.setEndStation(frankfurtHbf);
        ic2312.setStartStation(stuttgartHbf);  ic2312.setEndStation(mannheimHbf);
        ic2314.setStartStation(stuttgartHbf);  ic2314.setEndStation(mannheimHbf);
        ic2316.setStartStation(stuttgartHbf);  ic2316.setEndStation(mannheimHbf);
        re431.setStartStation(karlsruheHbf);   re431.setEndStation(stuttgartHbf);
        re433.setStartStation(karlsruheHbf);   re433.setEndStation(stuttgartHbf);
        re435.setStartStation(karlsruheHbf);   re435.setEndStation(stuttgartHbf);
        re437.setStartStation(karlsruheHbf);   re437.setEndStation(stuttgartHbf);
        re439.setStartStation(karlsruheHbf);   re439.setEndStation(stuttgartHbf);
        az501.setStartStation(heidelbergHbf);  az501.setEndStation(mannheimHbf);
        az502.setStartStation(heidelbergHbf);  az502.setEndStation(mannheimHbf);

        // Züge zum Bildfahrplan hinzufügen
        bfp.addZug(ice571); bfp.addZug(ice573); bfp.addZug(ice575); bfp.addZug(ice577);
        bfp.addZug(ic2312); bfp.addZug(ic2314); bfp.addZug(ic2316);
        bfp.addZug(re431);  bfp.addZug(re433);  bfp.addZug(re435);
        bfp.addZug(re437);  bfp.addZug(re439);
        bfp.addZug(az501);  bfp.addZug(az502);

        // Reihen aus den Stationen ableiten (Option A)
        List<String> rows = new ArrayList<>();
        rows.add("Arbeitszug");
        rows.add("Regional");
        rows.add("S-Bahn");
        rows.add("Fernverkehr IC");
        rows.add("Fernverkehr ICE");

        // DiagrammEintraege aus Zügen erzeugen
        Color rot    = Color.web("#C0392B");
        Color orange = Color.web("#E67E22");
        Color blau   = Color.web("#2980B9");
        Color gruen  = Color.web("#27AE60");
        Color grau   = Color.web("#606060");

        List<DiagrammEintrag> eintraege = new ArrayList<>();

        // ICE-Züge
        for (Zug z : bfp.getZuege()) {
            String reihe;
            Color farbe;
            String name = z.getName();

            if      (name.startsWith("ICE")) { reihe = "Fernverkehr ICE"; farbe = rot; }
            else if (name.startsWith("IC"))  { reihe = "Fernverkehr IC";  farbe = orange; }
            else if (name.startsWith("RE"))  { reihe = "Regional";        farbe = gruen; }
            else if (name.startsWith("Az"))  { reihe = "Arbeitszug";      farbe = grau; }
            else                             { reihe = "Regional";        farbe = blau; }

            eintraege.add(new DiagrammEintrag(
                    z.getName(),
                    reihe,
                    (int) z.getStartTime(),
                    (int) z.getEndTime(),
                    farbe
            ));
        }

        // S-Bahn im 30-Minuten-Takt (keine Modellklasse, direkt als Eintrag)
        for (int stunde = 6; stunde < 18; stunde++) {
            eintraege.add(new DiagrammEintrag("S5", "S-Bahn", t(stunde, 0),  t(stunde, 28), blau));
            eintraege.add(new DiagrammEintrag("S5", "S-Bahn", t(stunde, 30), t(stunde, 58), blau));
        }

        return new DiagrammDataset("Bildfahrplan", rows, t(6,0), t(18,0), eintraege);
    }
}