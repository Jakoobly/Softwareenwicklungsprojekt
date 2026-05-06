package gantt.controller;

import gantt.model.Plan;
import gantt.view.Gantt;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

/*
Controller-Klasse nach MVC-Prinzip
-> baut die Oberfläche zusammen
-> verbindet Menüleiste und Diagramm
 */

public class GanttController {

    private final Plan plan;
    private final BorderPane root;

    public GanttController(Plan plan) {
        this.plan = plan;
        this.root = new BorderPane();

        // Menüleiste oben platzieren
        root.setTop(createMenuBar());

        // Gantt-Diagramm in die Mitte
        root.setCenter(new Gantt(plan));
    }

    // liefert die komplette Oberfläche zurück
    public BorderPane getView() {
        return root;
    }

    // erstellt die obere Menüleiste
    private MenuBar createMenuBar() {

        MenuBar menuBar = new MenuBar();

        // ===== DATEI =====

        Menu datei = new Menu("Datei");

        MenuItem neu = new MenuItem("Neu");
        MenuItem öffnen = new MenuItem("Öffnen");
        MenuItem speichern = new MenuItem("Speichern");
        MenuItem beenden = new MenuItem("Beenden");

        // ===== BEARBEITEN =====

        Menu bearbeiten = new Menu("Bearbeiten");

        MenuItem kopieren = new MenuItem("Kopieren");
        MenuItem löschen = new MenuItem("Löschen");

        // ===== ANSICHT =====

        Menu ansicht = new Menu("Ansicht");

        MenuItem zoomen = new MenuItem("Zoomen");
        MenuItem zurücksetzen = new MenuItem("Ansicht zurücksetzen");

        // ===== HILFE =====

        Menu hilfe = new Menu("Hilfe");

        MenuItem info = new MenuItem("Info");
        MenuItem dokumentation = new MenuItem("Dokumentation");

        // ===== FEHLERMELDUNG =====
        // Standardmeldung für noch nicht implementierte Funktionen

        neu.setOnAction(e -> showNotImplementedMessage());
        öffnen.setOnAction(e -> showNotImplementedMessage());
        speichern.setOnAction(e -> showNotImplementedMessage());
        kopieren.setOnAction(e -> showNotImplementedMessage());
        löschen.setOnAction(e -> showNotImplementedMessage());
        zoomen.setOnAction(e -> showNotImplementedMessage());
        zurücksetzen.setOnAction(e -> showNotImplementedMessage());
        info.setOnAction(e -> showNotImplementedMessage());
        dokumentation.setOnAction(e -> showNotImplementedMessage());

        // Programm beenden
        beenden.setOnAction(e -> System.exit(0));

        // Menüeinträge hinzufügen
        datei.getItems().addAll(neu, öffnen, speichern, beenden);

        bearbeiten.getItems().addAll(kopieren, löschen);

        ansicht.getItems().addAll(zoomen, zurücksetzen);

        hilfe.getItems().addAll(info, dokumentation);

        // Menüs zur Menüleiste hinzufügen
        menuBar.getMenus().addAll(datei, bearbeiten, ansicht, hilfe);

        return menuBar;
    }

    // Standard-Fehlermeldung für nicht implementierte Funktionen
    private void showNotImplementedMessage() {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Fehler");
        alert.setHeaderText("Funktion nicht verfügbar");
        alert.setContentText("Diese Funktion wurde noch nicht implementiert.");

        alert.showAndWait();
    }
}