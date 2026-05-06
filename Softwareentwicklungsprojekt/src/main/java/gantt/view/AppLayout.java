package gantt.view;

import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

/*
Grundlayout der Anwendung
-> oben liegt die Menüleiste
-> in der Mitte liegt das eigentliche Diagramm
-> das Diagramm liegt in einer ScrollPane, damit man es verschieben kann
 */

public class AppLayout extends BorderPane {

    public AppLayout(MenuBar menuBar, Node content) {

        // Menüleiste oben platzieren
        setTop(menuBar);

        // ScrollPane für das Diagramm erzeugen
        ScrollPane scrollPane = new ScrollPane(content);

        // horizontale Scrollbar nur anzeigen, wenn der Inhalt breiter als das Fenster ist
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // vertikale Scrollbar nur anzeigen, wenn der Inhalt höher als das Fenster ist
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Diagramm nicht automatisch auf Fenstergröße quetschen
        scrollPane.setFitToWidth(false);
        scrollPane.setFitToHeight(false);

        // Diagramm / Inhalt in die Mitte platzieren
        setCenter(scrollPane);
    }
}