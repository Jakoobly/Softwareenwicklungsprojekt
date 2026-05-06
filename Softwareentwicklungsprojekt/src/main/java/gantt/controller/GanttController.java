package gantt.controller;

import gantt.model.Plan;
import gantt.view.AppLayout;
import gantt.view.Gantt;
import gantt.view.Menu;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

/*
Controller-Klasse nach MVC-Prinzip
-> baut die Oberfläche zusammen
-> verbindet Menüleiste und Diagramm
 */

public class GanttController {

    private final Plan plan;
    private final AppLayout root;

    public GanttController(Plan plan) {
        this.plan = plan;

        // Gantt-Diagramm erzeugen
        Gantt gantt = new Gantt(plan);

        // Controller für Klicks / Markierungen erzeugen
        new SelectionController(gantt);

        // Menüleiste erzeugen
        Menu menu = new Menu(
                this::showNotImplementedMessage,
                this::showProjectInfo,
                () -> System.exit(0)
        );

        // Oberfläche zusammensetzen
        this.root = new AppLayout(menu, gantt);
    }

    // liefert die komplette Oberfläche zurück
    public BorderPane getView() {
        return root;
    }

    // Standard-Fehlermeldung für nicht implementierte Funktionen
    private void showNotImplementedMessage() {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Fehler");
        alert.setHeaderText("Funktion nicht verfügbar");
        alert.setContentText("Diese Funktion wurde noch nicht implementiert.");

        alert.showAndWait();
    }

    // Informationsfenster zum Projekt
    private void showProjectInfo() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Projektinformationen");

        alert.setHeaderText("SEP Gantt-/Planungssystem");

        alert.setContentText(
                "Dieses Programm entsteht im Rahmen eines " +
                        "Softwareentwicklungsprojekts an der Universität.\n\n" +

                        "Projektteam:\n" +
                        "- 5 Studierende\n\n" +

                        "Ziel des Projekts:\n" +
                        "Entwicklung eines flexiblen Planungssystems " +
                        "für verschiedene Diagrammtypen wie:\n" +
                        "- Bildfahrpläne\n" +
                        "- Dienstpläne\n" +
                        "- Maschinenpläne\n\n" +

                        "Die Anwendung wird mit Java und JavaFX entwickelt."
        );

        alert.showAndWait();
    }
}