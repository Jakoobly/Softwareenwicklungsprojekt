package gantt.view;

import gantt.model.Plan;
import gantt.model.PlanObjekt;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Tooltip;

/* hier liegt die gesamte Logik für unser Gantt-Diagramm
wichtig ist: immer von vorne nach hinten arbeiten -> background zu erst usw. denn die Objekte überdecken sich
 */


public class Gantt extends Pane {

    private static final double LEFT_MARGIN = 100;
    private static final double TOP_MARGIN = 50;
    private static final double RIGHT_MARGIN = 40;
    private static final double BOTTOM_MARGIN = 40;

    private static final double ROW_HEIGHT = 50;
    private static final double BAR_HEIGHT = 30;
    private static final int TIME_TICK_MINUTES = 60;

    private static final double CHART_WIDTH = 900;

    private final List<Rectangle> selectedBars = new ArrayList<>();

    public Gantt(Plan plan) {
        draw(plan);
    }

    private void draw(Plan plan) {
        // berechne Länge der y-Achse: #Objekte * breite pro Element
        double chartHeight = plan.getRowOrder().size() * ROW_HEIGHT;
        // berechne untere Kante = x-Achse
        double chartBottom = TOP_MARGIN + chartHeight;
        //berechne gesamte Zeitdauer: z.B. 6:00-18:00 Uhr -> 12 Stunden -> 720min -> 720 pixel
        long totalMinutes = ChronoUnit.MINUTES.between(plan.getStartTime(), plan.getEndTime());

        drawBackground(chartHeight);
        drawAxes(chartBottom);
        drawTimeAxis(plan, totalMinutes, chartHeight);
        drawRows(plan);
        drawObjects(plan, totalMinutes);
    }

    private void drawBackground(double chartHeight) {
        // Rechteck erstellen mit fixen und berechneten Daten
        Rectangle background = new Rectangle(
                LEFT_MARGIN,
                TOP_MARGIN,
                CHART_WIDTH,
                chartHeight
        );
        background.setFill(Color.WHITE);
        background.setStroke(Color.LIGHTGRAY);
        getChildren().add(background); // wird dem Pane (Bereich) hinzugefügt und somit sichtbar
    }

    private void drawAxes(double chartBottom) {
        // erstelle x-Achse: links, unten -> links+breite = rechts, unten
        Line xAxis = new Line(LEFT_MARGIN, chartBottom, LEFT_MARGIN + CHART_WIDTH, chartBottom);
        // erstelle y-Achse: links, oben -> links, unten
        Line yAxis = new Line(LEFT_MARGIN, TOP_MARGIN, LEFT_MARGIN, chartBottom);

        xAxis.setStroke(Color.BLACK);
        yAxis.setStroke(Color.BLACK);

        getChildren().addAll(xAxis, yAxis); // wieder zum Pane (Bereich) hinzufügen damit es sichtbar wird
    }

    // Beschriftung und vertikales Raster
    private void drawTimeAxis(Plan plan, long totalMinutes, double chartHeight) {
        double xAxisY = TOP_MARGIN + chartHeight; // Position der X-Achse unten

        // von 0 bis max Zeit mit vorgegebener Schrittgröße (gerade 60)
        for (int minutes = 0; minutes <= totalMinutes; minutes += TIME_TICK_MINUTES) {
            /* rechnet Zeit in eine x-Achsen-Position um: z.B. 360 / 720 = 0.5 -> genau die Mitte der x-Linie
            konkret: 100 + 0.5 * 900 -> so landet 12:00 Uhr genau zwischen 6:00 und 18:00 Uhr
             */
            double x = LEFT_MARGIN + (minutes / (double) totalMinutes) * CHART_WIDTH;

            // Vertikale Rasterlinie
            Line gridLine = new Line(x, TOP_MARGIN, x, TOP_MARGIN + chartHeight);
            gridLine.setStroke(Color.LIGHTGRAY);
            getChildren().add(gridLine);

            // Zeit-Label unten an der x-Achse
            LocalTime currentTime = plan.getStartTime().plusMinutes(minutes);

            Text timeLabel = new Text(
                    x - 18,
                    xAxisY + 20,
                    currentTime.toString()
            );

            timeLabel.setFont(Font.font(12)); // Schriftart auswählen
            getChildren().add(timeLabel); // wieder zum Pane hinzufügen
        }
    }

    private void drawRows(Plan plan) {
        // iteriert durch alle Elemente die auf die y-Achse sollen
        for (int i = 0; i < plan.getRowOrder().size(); i++) {
            double y = TOP_MARGIN + i * ROW_HEIGHT; // Abstand zwischen (0/0) und dem jeweiligen Element

            Line rowLine = new Line(LEFT_MARGIN, y, LEFT_MARGIN + CHART_WIDTH, y); /* Rasterlinie für die
            jeweilige Zeile
            */
            rowLine.setStroke(Color.LIGHTGRAY);
            getChildren().add(rowLine); // Pzum Pane hinzufügen

            // Name des Arbeitsschrittes / Zugs auf der Y-Achse
            Text rowLabel = new Text(20, y + ROW_HEIGHT / 2 + 5, plan.getRowOrder().get(i));
            rowLabel.setFont(Font.font(14)); // Farbe
            getChildren().add(rowLabel); // zum Pane hinzufügen
        }

        double bottomY = TOP_MARGIN + plan.getRowOrder().size() * ROW_HEIGHT;
        Line bottomLine = new Line(LEFT_MARGIN, bottomY, LEFT_MARGIN + CHART_WIDTH, bottomY);
        bottomLine.setStroke(Color.LIGHTGRAY); // Farbe
        getChildren().add(bottomLine); // Pane hinzufügen
    }

    private void drawObjects(Plan plan, long totalMinutes) {
        // erzeugt die Balken für die Objekte
        for (PlanObjekt object : plan.getObjects()) {
            int rowIndex = plan.getRowOrder().indexOf(object.getRow());

            // wenn der Index nicht existiert überspringen!
            if (rowIndex < 0) {
                continue;
            }

            // rechnet den Abstand zwischen Planstart (z.B. 6Uhr) und Objektstart (z.B. 9Uhr) aus
            long startOffset = ChronoUnit.MINUTES.between(plan.getStartTime(), object.getStart());
            // rechnet den Abstand zwischen Planstart und Objektende aus
            long endOffset = ChronoUnit.MINUTES.between(plan.getStartTime(), object.getEnd());
            // differenz aus start und ende = dauer des Objekts
            long duration = endOffset - startOffset;

            // startpunkt x-Achse
            double x = LEFT_MARGIN + (startOffset / (double) totalMinutes) * CHART_WIDTH;
            // Breite des Objekts
            double width = (duration / (double) totalMinutes) * CHART_WIDTH;
            // Startpunkt y-Achse (mittig zentriert)
            double y = TOP_MARGIN + rowIndex * ROW_HEIGHT + (ROW_HEIGHT - BAR_HEIGHT) / 2.0;

            width = Math.max(width, 6); // mindestbreite 6, sonst wären manche Objekte evt. nicht zu sehen

            // erzeugung Rechteck -> Startpunkt (x,y), Breite, Höhe
            Rectangle bar = new Rectangle(x, y, width, BAR_HEIGHT);
            bar.setFill(Color.STEELBLUE);
            bar.setStroke(Color.BLACK);
            bar.setArcWidth(8);
            bar.setArcHeight(8);

            Text label = new Text(x + 5, y + 20, object.getName());
            label.setFill(Color.WHITE);
            label.setFont(Font.font(12));

            // erzeugung tooltip (infofenster aus javaFX)
            Tooltip tooltip = new Tooltip(
                    object.getName() + "\n" +
                            "Start: " + object.getStart() + "\n" +
                            "Ende: " + object.getEnd() + "\n" +
                            "Dauer: " + duration + " min"
            );

            // Tooltip auf Balken und Text anwenden
            Tooltip.install(bar, tooltip);
            Tooltip.install(label, tooltip);

            bar.setOnMouseClicked(event -> {

                // STRG gedrückt → Mehrfachauswahl
                if (event.isControlDown()) {

                    if (selectedBars.contains(bar)) {
                        // toggle OFF
                        bar.setStroke(Color.BLACK);
                        bar.setStrokeWidth(1);
                        selectedBars.remove(bar);
                    } else {
                        // toggle ON
                        bar.setStroke(Color.RED);
                        bar.setStrokeWidth(3);
                        selectedBars.add(bar);
                    }

                } else {
                    // normaler Klick → alles zurücksetzen

                    for (Rectangle selected : selectedBars) {
                        selected.setStroke(Color.BLACK);
                        selected.setStrokeWidth(1);
                    }

                    selectedBars.clear();

                    // aktuelles auswählen
                    bar.setStroke(Color.RED);
                    bar.setStrokeWidth(3);
                    selectedBars.add(bar);
                }
            });

            label.setOnMouseClicked(bar.getOnMouseClicked());

            // alles zum Pane hinzufügen
            getChildren().addAll(bar, label);
        }
    }
}