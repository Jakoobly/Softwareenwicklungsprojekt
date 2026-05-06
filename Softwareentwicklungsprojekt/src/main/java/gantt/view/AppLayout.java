package gantt.view;

import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

/*
Grundlayout der Anwendung
-> oben liegt die Menüleiste
-> in der Mitte liegt das eigentliche Diagramm
 */

public class AppLayout extends BorderPane {

    public AppLayout(MenuBar menuBar, Node content) {

        // Menüleiste oben platzieren
        setTop(menuBar);

        // Gantt-Diagramm in die Mitte
        setCenter(content);
    }
}