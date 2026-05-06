package gantt.view;

import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/*
Klasse für die Menüleiste
-> erstellt die Menüs und Menüeinträge
-> die Aktionen werden vom Controller übergeben
 */

public class Menu extends MenuBar {

    public Menu(Runnable notImplementedAction,
                Runnable projectInfoAction,
                Runnable exitAction) {

        createMenuBar(
                notImplementedAction,
                projectInfoAction,
                exitAction
        );
    }

    // erstellt die obere Menüleiste
    private void createMenuBar(Runnable notImplementedAction,
                               Runnable projectInfoAction,
                               Runnable exitAction) {

        // ===== DATEI =====

        javafx.scene.control.Menu datei =
                new javafx.scene.control.Menu("Datei");

        MenuItem neu = new MenuItem("Neu");
        MenuItem öffnen = new MenuItem("Öffnen");
        MenuItem speichern = new MenuItem("Speichern");

        // Untermenü für verschiedene Import-Arten
        javafx.scene.control.Menu importieren =
                new javafx.scene.control.Menu("Importieren");

        MenuItem bildfahrplanImport =
                new MenuItem("Bildfahrplan");

        MenuItem dienstplanImport =
                new MenuItem("Dienstplan");

        MenuItem maschinenplanImport =
                new MenuItem("Maschinenplan");

        // momentan noch nicht implementiert
        bildfahrplanImport.setOnAction(e -> notImplementedAction.run());
        dienstplanImport.setOnAction(e -> notImplementedAction.run());
        maschinenplanImport.setOnAction(e -> notImplementedAction.run());

        // Unterpunkte zum Import-Menü hinzufügen
        importieren.getItems().addAll(
                bildfahrplanImport,
                dienstplanImport,
                maschinenplanImport
        );

        MenuItem beenden = new MenuItem("Beenden");

        // ===== BEARBEITEN =====

        javafx.scene.control.Menu bearbeiten =
                new javafx.scene.control.Menu("Bearbeiten");

        MenuItem kopieren = new MenuItem("Kopieren");
        MenuItem löschen = new MenuItem("Löschen");

        // ===== ANSICHT =====

        javafx.scene.control.Menu ansicht =
                new javafx.scene.control.Menu("Ansicht");

        MenuItem zoomen = new MenuItem("Zoomen");
        MenuItem zurücksetzen = new MenuItem("Ansicht zurücksetzen");

        // ===== HILFE =====

        javafx.scene.control.Menu hilfe =
                new javafx.scene.control.Menu("Hilfe");

        MenuItem info = new MenuItem("Info");
        MenuItem dokumentation = new MenuItem("Dokumentation");

        // ===== FEHLERMELDUNG =====
        // Standardmeldung für noch nicht implementierte Funktionen

        neu.setOnAction(e -> notImplementedAction.run());
        öffnen.setOnAction(e -> notImplementedAction.run());
        speichern.setOnAction(e -> notImplementedAction.run());

        kopieren.setOnAction(e -> notImplementedAction.run());
        löschen.setOnAction(e -> notImplementedAction.run());

        zoomen.setOnAction(e -> notImplementedAction.run());
        zurücksetzen.setOnAction(e -> notImplementedAction.run());

        info.setOnAction(e -> projectInfoAction.run());
        dokumentation.setOnAction(e -> notImplementedAction.run());

        // Programm beenden
        beenden.setOnAction(e -> exitAction.run());

        // Menüeinträge hinzufügen
        datei.getItems().addAll(
                neu,
                öffnen,
                speichern,
                new SeparatorMenuItem(),
                importieren,
                new SeparatorMenuItem(),
                beenden
        );

        bearbeiten.getItems().addAll(
                kopieren,
                löschen
        );

        ansicht.getItems().addAll(
                zoomen,
                zurücksetzen
        );

        hilfe.getItems().addAll(
                info,
                dokumentation
        );

        // Menüs zur Menüleiste hinzufügen
        getMenus().addAll(
                datei,
                bearbeiten,
                ansicht,
                hilfe
        );
    }
}