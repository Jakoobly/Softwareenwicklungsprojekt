package gantt.controller;

import gantt.view.Gantt;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
Controller für die Auswahl von Objekten im Diagramm
-> Klick auf Balken markiert den Balken
-> STRG-Klick erlaubt Mehrfachauswahl
-> Klick auf freien Bereich entfernt die Auswahl
 */

public class SelectionController {

    private final Gantt gantt;

    private final List<Rectangle> selectedBars = new ArrayList<>();

    public SelectionController(Gantt gantt) {
        this.gantt = gantt;

        installSelectionHandlers();
    }

    private void installSelectionHandlers() {

        // Klick auf freien Bereich -> Auswahl entfernen
        gantt.setOnMouseClicked(event -> clearSelection());

        for (Map.Entry<Rectangle, Text> entry : gantt.getBarLabels().entrySet()) {

            Rectangle bar = entry.getKey();
            Text label = entry.getValue();

            bar.setOnMouseClicked(event -> {

                // verhindert, dass der Klick bis zum Hintergrund weitergegeben wird
                event.consume();

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

                    clearSelection();

                    // aktuelles auswählen
                    bar.setStroke(Color.RED);
                    bar.setStrokeWidth(3);
                    selectedBars.add(bar);
                }
            });

            label.setOnMouseClicked(bar.getOnMouseClicked());
        }
    }

    // entfernt alle aktuellen Markierungen
    private void clearSelection() {

        for (Rectangle selected : selectedBars) {
            selected.setStroke(Color.BLACK);
            selected.setStrokeWidth(1);
        }

        selectedBars.clear();
    }
}