package SEP;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;
import java.util.List;

/**
 * Zeichenflaeche fuer das Gantt-Diagramm.
 */
public class DiagrammCanvas extends Canvas {

    // Scale für das Layout
    private static final double LABEL_BREITE  = 100;
    private static final double REIHE_HOEHE   = 48;
    private static final double KOPF_HOEHE    = 30;
    private static final double BALKEN_ABST   = 7;
    private static final double BALKEN_HOEHE  = REIHE_HOEHE - BALKEN_ABST * 2;

    // Aktuell geladener Datensatz
    private DiagrammDataset datensatz;

    // Liste der aktuell ausgewaehlten Eintraege
    private List<DiagrammEintrag> ausgewaehlt = new ArrayList<>();

    // Jetzt-Markierung: 09:30 als Ausgangspunkt (in Minuten ab 0:00)
    private int jetztMin = 9 * 60 + 30;

    // Variablen für die Rubberband-Auswahl
    private double startX;
    private double startY;
    private double aktuellesX;
    private double aktuellesY;
    private boolean ziehen = false; //auswahlrechteck

    // Wird aufgerufen wenn sich die Auswahl ändert
    private AuswahlListener auswahlListener;

    // Schnittstelle für den Auswahl-Callback
    public interface AuswahlListener {
        void onAuswahlGeaendert(List<DiagrammEintrag> auswahl);
    }

    public DiagrammCanvas() {
        // Maus-Events registrieren
        setOnMousePressed(this::mausGedrueckt);
        setOnMouseDragged(this::mausGezogen);
        setOnMouseReleased(this::mausLosgelassen);
    }

    // Setzt den Listener für Änderungen der Auswahl
    public void setAuswahlListener(AuswahlListener listener) {
        this.auswahlListener = listener;
    }

    // Laedt einen neuen Datensatz und zeichnet das Diagramm neu
    public void laden(DiagrammDataset ds) {
        this.datensatz = ds;
        ausgewaehlt.clear();

        // Grösse der Canvas anpassen
        double breite = LABEL_BREITE + minutenZuPixel(ds.endMin - ds.startMin);
        double hoehe  = KOPF_HOEHE + ds.rows.size() * REIHE_HOEHE;
        setWidth(breite);
        setHeight(hoehe);

        neuzeichnen();
        benachrichtigenAuswahl();
    }

    // Alle Einträge auswählen
    public void alleAuswaehlen() {
        if (datensatz == null) return;
        ausgewaehlt.clear();
        ausgewaehlt.addAll(datensatz.entries);
        neuzeichnen();
        benachrichtigenAuswahl();
    }

    // Auswahl aufheben
    public void auswahlAufheben() {
        ausgewaehlt.clear();
        neuzeichnen();
        benachrichtigenAuswahl();
    }

    // Gibt eine Kopie der aktuellen Auswahl zurueck
    public List<DiagrammEintrag> getAusgewaehlt() {
        return new ArrayList<>(ausgewaehlt);
    }


    private void neuzeichnen() {
        if (datensatz == null) return;

        GraphicsContext gc = getGraphicsContext2D();

        // Hintergrund weiß
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());

        reihenhintergrundZeichnen(gc);
        gitterZeichnen(gc);
        jetztMarkierungZeichnen(gc);
        balkenZeichnen(gc);
        kopfzeileZeichnen(gc);
        beschriftungenZeichnen(gc);

        // Rubberband nur zeichnen wenn gerade gezogen wird
        if (ziehen) {
            rubberbandZeichnen(gc);
        }
    }

    // Zeichnet abwechselnd helle und etwas dunklere Reihen
    private void reihenhintergrundZeichnen(GraphicsContext gc) {
        for (int i = 0; i < datensatz.rows.size(); i++) {
            if (i % 2 == 0) {
                gc.setFill(Color.web("#EFEFEF"));
            } else {
                gc.setFill(Color.web("#F7F7F7"));
            }
            gc.fillRect(LABEL_BREITE, reiheY(i), getWidth(), REIHE_HOEHE);
        }
    }

    // Zeichnet vertikale Gitterlinien (jede Stunde dicker, alle 10 min dünner)
    private void gitterZeichnen(GraphicsContext gc) {
        for (int min = datensatz.startMin; min <= datensatz.endMin; min += 10) {
            double x = zeitZuX(min);
            if (min % 60 == 0) {
                gc.setStroke(Color.web("#BBBBBB"));
                gc.setLineWidth(1.0);
            } else {
                gc.setStroke(Color.web("#E0E0E0"));
                gc.setLineWidth(0.5);
            }
            gc.strokeLine(x, KOPF_HOEHE, x, getHeight());
        }
    }

    // Zeichnet die aktuelle-Zeit-Markierung als gestrichelte Linie
    private void jetztMarkierungZeichnen(GraphicsContext gc) {
        double x = zeitZuX(jetztMin); //9:30

        // Halbtransparenter Hintergrund
        gc.setFill(Color.web("#88888820"));
        gc.fillRect(x - 6, KOPF_HOEHE, 12, getHeight() - KOPF_HOEHE);

        // Gestrichelte Linie
        gc.setStroke(Color.web("#777777"));
        gc.setLineWidth(1.5);
        gc.setLineDashes(4, 3);
        gc.strokeLine(x, KOPF_HOEHE, x, getHeight());
        gc.setLineDashes(); // Strichelung zurücksetzen
    }

    // Zeichnet alle Balken des Datensatzes
    private void balkenZeichnen(GraphicsContext gc) {
        for (DiagrammEintrag eintrag : datensatz.entries) {
            int reiheIndex = datensatz.rows.indexOf(eintrag.row);
            if (reiheIndex < 0) continue; // Reihe nicht gefunden

            double x      = zeitZuX(eintrag.startMin);
            double y      = reiheY(reiheIndex) + BALKEN_ABST;
            double breite = Math.max(minutenZuPixel(eintrag.getDuration()), 3);
            boolean istAusgewaehlt = ausgewaehlt.contains(eintrag);

            // Schatten
            gc.setFill(Color.web("#00000015"));
            gc.fillRoundRect(x + 1, y + 1, breite, BALKEN_HOEHE, 4, 4);

            // Balken
            gc.setFill(eintrag.color);
            gc.fillRoundRect(x, y, breite, BALKEN_HOEHE, 4, 4);

            // Auswahlrahmen
            if (istAusgewaehlt) {
                gc.setStroke(Color.web("#1A5276"));
                gc.setLineWidth(2.2);
                gc.strokeRoundRect(x - 1, y - 1, breite + 2, BALKEN_HOEHE + 2, 5, 5);
            } else {
                gc.setStroke(eintrag.color.darker());
                gc.setLineWidth(0.6);
                gc.strokeRoundRect(x, y, breite, BALKEN_HOEHE, 4, 4);
            }

            // Beschriftung im Balken (nur wenn genug Platz)
            if (breite > 16) {
                gc.save();
                gc.beginPath();
                gc.rect(x + 2, y, breite - 4, BALKEN_HOEHE);
                gc.clip();
                gc.setFont(Font.font("SansSerif", FontWeight.BOLD, 10));
                gc.setFill(Color.WHITE);
                gc.setTextAlign(TextAlignment.LEFT);
                gc.setTextBaseline(VPos.CENTER);
                gc.fillText(eintrag.id, x + 4, y + BALKEN_HOEHE / 2.0);
                gc.restore();
            }
        }
    }

    // Zeichnet die Kopfzeile mit den Uhrzeiten
    private void kopfzeileZeichnen(GraphicsContext gc) {
        // Hintergrund
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), KOPF_HOEHE);

        // Stundenbeschriftungen
        gc.setFont(Font.font("SansSerif", 11));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);

        for (int min = datensatz.startMin; min <= datensatz.endMin; min += 60) {
            double x = zeitZuX(min);
            gc.setStroke(Color.web("#BBBBBB"));
            gc.setLineWidth(1);
            gc.strokeLine(x, 0, x, KOPF_HOEHE);
            gc.setFill(Color.web("#1A1A1A"));
            gc.fillText(String.format("%02d:00", min / 60), x, KOPF_HOEHE / 2.0);
        }

        // Untere Trennlinie der Kopfzeile
        gc.setStroke(Color.web("#BBBBBB"));
        gc.setLineWidth(1);
        gc.strokeLine(0, KOPF_HOEHE, getWidth(), KOPF_HOEHE);

        // Hintergrund für das Beschriftungsfeld links oben
        gc.setFill(Color.web("#F0F0F0"));
        gc.fillRect(0, 0, LABEL_BREITE, KOPF_HOEHE);
        gc.setStroke(Color.web("#BBBBBB"));
        gc.strokeLine(0, KOPF_HOEHE, LABEL_BREITE, KOPF_HOEHE);
    }

    // Zeichnet die Reihenbeschriftungen links
    private void beschriftungenZeichnen(GraphicsContext gc) {
        // Hintergrund des Beschriftungsbereichs
        gc.setFill(Color.web("#F0F0F0"));
        gc.fillRect(0, KOPF_HOEHE, LABEL_BREITE, getHeight() - KOPF_HOEHE);

        gc.setFont(Font.font("SansSerif", FontWeight.BOLD, 11));
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.setTextBaseline(VPos.CENTER);

        for (int i = 0; i < datensatz.rows.size(); i++) {
            double y = reiheY(i);

            // Trennlinie zwischen Reihen
            gc.setStroke(Color.web("#E0E0E0"));
            gc.setLineWidth(0.5);
            gc.strokeLine(0, y, getWidth(), y);

            // Reihenbeschriftung
            gc.setFill(Color.web("#1A1A1A"));
            gc.fillText(datensatz.rows.get(i), LABEL_BREITE - 8, y + REIHE_HOEHE / 2.0);
        }

        // Vertikale Trennlinie zwischen Beschriftungen und Diagramm
        gc.setStroke(Color.web("#BBBBBB"));
        gc.setLineWidth(1);
        gc.strokeLine(LABEL_BREITE, 0, LABEL_BREITE, getHeight());
    }

    // Zeichnet das Auswahlrechteck beim Ziehen
    private void rubberbandZeichnen(GraphicsContext gc) {
        double x = Math.min(startX, aktuellesX);
        double y = Math.min(startY, aktuellesY);
        double w = Math.abs(aktuellesX - startX);
        double h = Math.abs(aktuellesY - startY);

        gc.setFill(Color.web("#3498DB30"));
        gc.fillRect(x, y, w, h);
        gc.setStroke(Color.web("#3498DB"));
        gc.setLineWidth(1.2);
        gc.setLineDashes(5, 3);
        gc.strokeRect(x, y, w, h);
        gc.setLineDashes();
    }


    private void mausGedrueckt(MouseEvent ereignis) {
        if (ereignis.getButton() != MouseButton.PRIMARY) return;

        startX     = ereignis.getX();
        startY     = ereignis.getY();
        aktuellesX = ereignis.getX();
        aktuellesY = ereignis.getY();
        ziehen     = false;

        DiagrammEintrag getroffen = balkenBeiPosition(ereignis.getX(), ereignis.getY());
        boolean strg = ereignis.isControlDown() || ereignis.isMetaDown();

        if (getroffen != null) {
            if (strg) {
                // Strg+Klick: umschalten
                if (ausgewaehlt.contains(getroffen)) {
                    ausgewaehlt.remove(getroffen);
                } else {
                    ausgewaehlt.add(getroffen);
                }
            } else {
                // Normaler Klick: nur diesen auswaehlen
                if (!ausgewaehlt.contains(getroffen)) {
                    ausgewaehlt.clear();
                    ausgewaehlt.add(getroffen);
                }
            }
            neuzeichnen();
            benachrichtigenAuswahl();
        } else {
            // Klick auf leere Fläche: Rubberband starten
            ziehen = true;
            if (!strg) {
                ausgewaehlt.clear();
            }
        }
    }

    // Aktualisiert die Endposition des rubberbands während der Nutzer die Maus zieht
    private void mausGezogen(MouseEvent ereignis) {
        aktuellesX = ereignis.getX();
        aktuellesY = ereignis.getY();
        ziehen = true;
        neuzeichnen();
    }

    // Wertet die rubberband-Auswahl aus wenn die Maus losgelassen wird.
    // Bei kleiner Bewegung (< 4px) wird es als normaler Klick behandelt.
    private void mausLosgelassen(MouseEvent ereignis) {
        double zugBreite = Math.abs(aktuellesX - startX);
        double zugHoehe  = Math.abs(aktuellesY - startY);
        boolean wurdeGezogen = zugBreite > 4 || zugHoehe > 4;

        if (ziehen && wurdeGezogen && datensatz != null) {
            // rubberband auswerten
            double x1 = Math.min(startX, aktuellesX);
            double x2 = Math.max(startX, aktuellesX);
            double y1 = Math.min(startY, aktuellesY);
            double y2 = Math.max(startY, aktuellesY);

            boolean strg = ereignis.isControlDown() || ereignis.isMetaDown();
            if (!strg) {
                ausgewaehlt.clear();
            }

            for (DiagrammEintrag eintrag : datensatz.entries) {
                int reiheIndex = datensatz.rows.indexOf(eintrag.row);
                if (reiheIndex < 0) continue;

                double bx = zeitZuX(eintrag.startMin);
                double by = reiheY(reiheIndex) + BALKEN_ABST;
                double bw = Math.max(minutenZuPixel(eintrag.getDuration()), 3);

                // Balken schneidet rubberband?
                boolean schneidetX = bx < x2 && bx + bw > x1;
                boolean schneidetY = by < y2 && by + BALKEN_HOEHE > y1;
                if (schneidetX && schneidetY) {
                    ausgewaehlt.add(eintrag);
                }
            }
            benachrichtigenAuswahl();

        } else if (!wurdeGezogen) {
            // Kurzes Ziehen = eigentlich ein Klick auf leere fläche
            DiagrammEintrag getroffen = balkenBeiPosition(startX, startY);
            if (getroffen == null && !ereignis.isControlDown() && !ereignis.isMetaDown()) {
                ausgewaehlt.clear();
                benachrichtigenAuswahl();
            }
        }

        ziehen = false;
        neuzeichnen();
    }

    // Gibt den Balken an den Koordinaten (mx, my) zurück, oder null
    private DiagrammEintrag balkenBeiPosition(double mx, double my) {
        if (datensatz == null) return null;

        // rückwärts iterieren damit der oberste Balken Vorrang hat
        for (int i = datensatz.entries.size() - 1; i >= 0; i--) {
            DiagrammEintrag eintrag = datensatz.entries.get(i);
            int reiheIndex = datensatz.rows.indexOf(eintrag.row);
            if (reiheIndex < 0) continue;

            double x = zeitZuX(eintrag.startMin);
            double y = reiheY(reiheIndex) + BALKEN_ABST;
            double w = Math.max(minutenZuPixel(eintrag.getDuration()), 3);

            if (mx >= x && mx <= x + w && my >= y && my <= y + BALKEN_HOEHE) {
                return eintrag;
            }
        }
        return null;
    }


    // Hilfsmethode: Wandelt Minuten in Pixel um (1 Minute = 1.8 Pixel) - später über timescale in plan.java
    private double minutenZuPixel(int minuten) {
        return minuten * 1.8;
    }

    // Gibt die X-Koordinate fuer eine Uhrzeit (in Minuten) zurück
    private double zeitZuX(int minuten) {
        return LABEL_BREITE + minutenZuPixel(minuten - datensatz.startMin);
    }

    // Gibt die Y-Koordinate fuer eine Reihe zurück (umgekehrte Reihenfolge)
    private double reiheY(int index) {
        int umgekehrt = datensatz.rows.size() - 1 - index;
        return KOPF_HOEHE + umgekehrt * REIHE_HOEHE;
    }

    // Benachrichtigt den Listener über eine Änderung der Auswahl
    private void benachrichtigenAuswahl() {
        if (auswahlListener != null) {
            auswahlListener.onAuswahlGeaendert(getAusgewaehlt());
        }
    }
}
