package SEP;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;

/**
 * Hauptcontroller der Anwendung.
 *
 * Aufgaben:
 *  - Erstellt das Hauptfenster mit Menüleiste, Toolbar, Diagramm und Statusleiste
 *  - Verwaltet den Wechsel zwischen den drei Plantypen
 *  - Reagiert auf Benutzerinteraktionen
 */
public class MainController {

    private final Stage stage;
    private DiagrammCanvas canvas;
    private Label       statusAnzeige; // zeigt Informationen zur aktuellen Auswahl

    public MainController(Stage stage) {
        this.stage = stage;
    }

    /** Erstellt und zeigt das Hauptfenster. */
    public void show() {
        canvas = new DiagrammCanvas();

        // Auswahl-Listener setzen
        canvas.setAuswahlListener(new DiagrammCanvas.AuswahlListener() {
            @Override
            public void onAuswahlGeaendert(List<DiagrammEintrag> auswahl) {
                auswahlAktualisieren(auswahl);
            }
        });

        MenuBar    menuLeiste  = menuLeistenErstellen();
        HBox       toolbar     = toolbarErstellen();
        HBox       tabLeiste   = tabLeisteErstellen();
        ScrollPane scrollBereich = scrollBereichErstellen();
        HBox       statusLeiste  = statusLeisteErstellen();

        VBox root = new VBox(menuLeiste, toolbar, tabLeiste, scrollBereich, statusLeiste);
        VBox.setVgrow(scrollBereich, Priority.ALWAYS);
        root.setStyle("-fx-background-color: #F2F2F2;");

        Scene szene = new Scene(root, 1280, 700);
        szene.setFill(Color.web("#F2F2F2"));

        // Tastatur Shortcuts
        szene.setOnKeyPressed(new javafx.event.EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent ereignis) {
                switch (ereignis.getCode()) {
                    case A:
                        if (ereignis.isControlDown() || ereignis.isMetaDown()) {
                            canvas.alleAuswaehlen();
                        }
                        break;
                    case ESCAPE:
                        canvas.auswahlAufheben();
                        break;
                    default:
                        break;
                }
            }
        });

        // Dienstplan als Standardansicht laden
        planWechseln(SampleDatasets.dienstplan());

        stage.setTitle("Prototyp — Gantt Diagramm Editor");
        stage.setScene(szene);
        stage.setMinWidth(900);
        stage.setMinHeight(550);
        stage.show();
    }

    private MenuBar menuLeistenErstellen() {
        // Datei-Menü
        Menu dateiMenue = new Menu("Datei");
        dateiMenue.getItems().add(menuPunktErstellen("Neu",              "Ctrl+N"));
        dateiMenue.getItems().add(menuPunktErstellen("Öffnen…",          "Ctrl+O"));
        dateiMenue.getItems().add(menuPunktErstellen("Speichern",        "Ctrl+S"));
        dateiMenue.getItems().add(menuPunktErstellen("Speichern unter…", ""));
        dateiMenue.getItems().add(new SeparatorMenuItem());
        dateiMenue.getItems().add(menuPunktErstellen("Exportieren…",     ""));
        dateiMenue.getItems().add(new SeparatorMenuItem());

        // Beenden schliesst das Fenster
        MenuItem beenden = new MenuItem("Beenden");
        beenden.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent ereignis) {
                stage.close();
            }
        });
        dateiMenue.getItems().add(beenden);

        // Bearbeiten-Menü
        Menu bearbeitenMenue = new Menu("Bearbeiten");
        bearbeitenMenue.getItems().add(menuPunktErstellen("Rückgängig",         "Ctrl+Z"));
        bearbeitenMenue.getItems().add(menuPunktErstellen("Wiederholen",        "Ctrl+Y"));
        bearbeitenMenue.getItems().add(new SeparatorMenuItem());

        MenuItem alleWaehlen = new MenuItem("Alle auswählen");
        alleWaehlen.setAccelerator(javafx.scene.input.KeyCombination.keyCombination("Ctrl+A"));
        alleWaehlen.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent ereignis) {
                canvas.alleAuswaehlen();
            }
        });
        bearbeitenMenue.getItems().add(alleWaehlen);

        MenuItem auswahlAufheben = new MenuItem("Auswahl aufheben");
        auswahlAufheben.setAccelerator(javafx.scene.input.KeyCombination.keyCombination("Escape"));
        auswahlAufheben.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent ereignis) {
                canvas.auswahlAufheben();
            }
        });
        bearbeitenMenue.getItems().add(auswahlAufheben);

        bearbeitenMenue.getItems().add(new SeparatorMenuItem());
        bearbeitenMenue.getItems().add(menuPunktErstellen("Eintrag hinzufügen", ""));
        bearbeitenMenue.getItems().add(menuPunktErstellen("Eintrag löschen",    "Delete"));

        // Ansicht-Menü
        Menu ansichtMenue = new Menu("Ansicht");
        ansichtMenue.getItems().add(menuPunktErstellen("Zoom +",         "Ctrl+Plus"));
        ansichtMenue.getItems().add(menuPunktErstellen("Zoom −",         "Ctrl+Minus"));
        ansichtMenue.getItems().add(menuPunktErstellen("Zoom 100%",      ""));
        ansichtMenue.getItems().add(new SeparatorMenuItem());
        ansichtMenue.getItems().add(menuPunktErstellen("Heute anzeigen", ""));

        // Hilfe-Menü
        Menu hilfeMenue = new Menu("Hilfe");
        MenuItem ueber = new MenuItem("Über PlanView");
        ueber.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent ereignis) {
                Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                dialog.setTitle("Über PlanView");
                dialog.setHeaderText("PlanView v1.0");
                dialog.setContentText("Universitätsprojekt — Gantt Diagramm Editor");
                dialog.showAndWait();
            }
        });
        hilfeMenue.getItems().add(ueber);

        MenuBar leiste = new MenuBar(dateiMenue, bearbeitenMenue, ansichtMenue, hilfeMenue);
        leiste.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #C8C8C8; -fx-border-width: 0 0 1 0;");
        return leiste;
    }

    //toolbar erstellen
    private HBox toolbarErstellen() {
        Button oeffnen    = buttonErstellen("📂 Öffnen",       "Datei öffnen");
        Button speichern  = buttonErstellen("💾 Speichern",     "Datei speichern");
        Button hinzufuegen= buttonErstellen("➕ Hinzufügen",    "Eintrag hinzufügen");
        Button bearbeiten = buttonErstellen("✏ Bearbeiten",    "Eintrag bearbeiten");
        Button loeschen   = buttonErstellen("🗑 Löschen",       "Eintrag löschen");

        // Auswahl-Buttons
        Button alleWaehlen = new Button("Alle wählen");
        alleWaehlen.setFont(Font.font("SansSerif", 11));
        alleWaehlen.setStyle("-fx-background-color: #E8E8E8; -fx-border-color: #C8C8C8; -fx-border-width: 1; -fx-background-radius: 3; -fx-border-radius: 3; -fx-padding: 3 9 3 9;");
        alleWaehlen.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent ereignis) {
                canvas.alleAuswaehlen();
            }
        });

        Button auswahlLoeschen = new Button("Auswahl löschen");
        auswahlLoeschen.setFont(Font.font("SansSerif", 11));
        auswahlLoeschen.setStyle("-fx-background-color: #E8E8E8; -fx-border-color: #C8C8C8; -fx-border-width: 1; -fx-background-radius: 3; -fx-border-radius: 3; -fx-padding: 3 9 3 9;");
        auswahlLoeschen.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent ereignis) {
                canvas.auswahlAufheben();
            }
        });

        Button zoomMinus = buttonErstellen("− Zoom", "Zoom verkleinern");
        Button zoom100   = buttonErstellen("100%",   "Zoom zurücksetzen");
        Button zoomPlus  = buttonErstellen("+ Zoom", "Zoom vergrößern");

        // Trennlinien
        Separator trenner1 = new Separator(Orientation.VERTICAL);
        trenner1.setPadding(new Insets(0, 3, 0, 3));
        Separator trenner2 = new Separator(Orientation.VERTICAL);
        trenner2.setPadding(new Insets(0, 3, 0, 3));
        Separator trenner3 = new Separator(Orientation.VERTICAL);
        trenner3.setPadding(new Insets(0, 3, 0, 3));

        Region abstandhalter = new Region();
        HBox.setHgrow(abstandhalter, Priority.ALWAYS);

        HBox toolbar = new HBox(4,
            oeffnen, speichern,
            trenner1,
            hinzufuegen, bearbeiten, loeschen,
            trenner2,
            alleWaehlen, auswahlLoeschen,
            abstandhalter,
            trenner3,
            zoomMinus, zoom100, zoomPlus
        );
        toolbar.setPadding(new Insets(6, 10, 6, 10));
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #C8C8C8; -fx-border-width: 0 0 1 0;");
        return toolbar;
    }


    // Erstellt die Tab-Leiste zum Wechseln zwischen Dienstplan, Fertigungsplan und Bildfahrplan.
    // Beim Klick auf einen Tab wird der jeweilige Beispieldatensatz in die Canvas geladen.
    private HBox tabLeisteErstellen() {
        ToggleGroup gruppe = new ToggleGroup();

        ToggleButton tabDienstplan     = tabButtonErstellen("Dienstplan",     gruppe, true);
        ToggleButton tabFertigungsplan = tabButtonErstellen("Fertigungsplan", gruppe, false);
        ToggleButton tabBildfahrplan   = tabButtonErstellen("Bildfahrplan",   gruppe, false);

        // Beim Klick jeweiligen Datensatz laden

        //Dienstplan
        tabDienstplan.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent ereignis) {
                tabDienstplan.setSelected(true);
                planWechseln(SampleDatasets.dienstplan());
            }
        });
        //fertigungsplan
        tabFertigungsplan.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent ereignis) {
                tabFertigungsplan.setSelected(true);
                planWechseln(SampleDatasets.fertigungsplan());
            }
        });
        //bildfahrplan
        tabBildfahrplan.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent ereignis) {
                tabBildfahrplan.setSelected(true);
                planWechseln(SampleDatasets.bildfahrplan());
            }
        });

        Label bezeichnung = new Label("Plantyp:");
        bezeichnung.setFont(Font.font("SansSerif", 11));
        bezeichnung.setStyle("-fx-text-fill: #777777;");

        HBox leiste = new HBox(6, bezeichnung, tabDienstplan, tabFertigungsplan, tabBildfahrplan);
        leiste.setPadding(new Insets(5, 10, 5, 10));
        leiste.setAlignment(Pos.CENTER_LEFT);
        leiste.setStyle("-fx-background-color: #F8F8F8; -fx-border-color: #C8C8C8; -fx-border-width: 0 0 1 0;");
        return leiste;
    }

    //Einbetten der Canvas in einen scrollbaren Bereich -> große Pläne können gescrolled werden
    private ScrollPane scrollBereichErstellen() {
        ScrollPane scroll = new ScrollPane(canvas);
        scroll.setFitToHeight(false);
        scroll.setFitToWidth(false);
        scroll.setPannable(false); // Panning deaktiviert wegen Rubberband-Auswahl
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle("-fx-background: #F2F2F2; -fx-background-color: #F2F2F2;");
        return scroll;
    }


    // Erstellt die Statusleiste am unteren Rand mit Bedienhinweisen links und
    // Details oder Anzahl der aktuellen Auswahl rechts.
    private HBox statusLeisteErstellen() {
        Label hinweis = new Label(
            "Klicken: auswählen  |  Strg+Klick: Mehrfachauswahl  |" +
            "  Ziehen: Bereichsauswahl  |  Esc: aufheben  |  Strg+A: alle wählen"
        );
        hinweis.setFont(Font.font("SansSerif", 10));
        hinweis.setStyle("-fx-text-fill: #777777;");

        Region abstandhalter = new Region();
        HBox.setHgrow(abstandhalter, Priority.ALWAYS);

        statusAnzeige = new Label("Keine Auswahl");
        statusAnzeige.setFont(Font.font("SansSerif", FontWeight.BOLD, 11));
        statusAnzeige.setStyle("-fx-text-fill: #2471A3;");

        HBox leiste = new HBox(10, hinweis, abstandhalter, statusAnzeige);
        leiste.setPadding(new Insets(4, 10, 4, 10));
        leiste.setAlignment(Pos.CENTER_LEFT);
        leiste.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #C8C8C8; -fx-border-width: 1 0 0 0;");
        return leiste;
    }


    // Lädt einen neuen Plan und aktualisiert den Fenstertitel.
    private void planWechseln(DiagrammDataset datensatz) {
        canvas.laden(datensatz);
        stage.setTitle("SEP Gantt Editor — " + datensatz.title);
        statusAnzeige.setText("Keine Auswahl");
    }

    // Aktualisiert die Statusleiste wenn sich die Auswahl ändert.
    private void auswahlAktualisieren(List<DiagrammEintrag> auswahl) {
        if (auswahl.isEmpty()) {
            statusAnzeige.setText("Keine Auswahl");

        } else if (auswahl.size() == 1) {
            // Einzelauswahl: detaillierte Informationen anzeigen
            DiagrammEintrag eintrag = auswahl.get(0);
            int dauer = eintrag.getDuration();
            String dauerText;
            if (dauer >= 60) {
                dauerText = (dauer / 60) + "h " + (dauer % 60) + "min";
            } else {
                dauerText = dauer + "min";
            }
            statusAnzeige.setText(
                "▶ " + eintrag.id +
                "  |  Reihe: " + eintrag.row +
                "  |  " + zeitFormatieren(eintrag.startMin) +
                " – "   + zeitFormatieren(eintrag.endMin) +
                "  |  Dauer: " + dauerText
            );

        } else {
            // Mehrfachauswahl: nur Anzahl anzeigen
            statusAnzeige.setText(auswahl.size() + " Einträge ausgewählt");
        }
    }

    // Wandelt Minuten ab Mitternacht in das Format HH:MM um.
    private String zeitFormatieren(int minuten) {
        int stunden = minuten / 60;
        int min     = minuten % 60;
        return String.format("%02d:%02d", stunden, min);
    }


    /**
     * Erstellt einen  Toolbar-Button.
     * Zeigt aktuell noch einen Info-Dialog wenn die Funktion noch nicht implementiert ist.
     */
    private Button buttonErstellen(String beschriftung, final String funktionsname) {
        Button button = new Button(beschriftung);
        button.setFont(Font.font("SansSerif", 11));
        button.setStyle(
            "-fx-background-color: #E8E8E8;" +
            "-fx-border-color: #C8C8C8;" +
            "-fx-border-width: 1;" +
            "-fx-background-radius: 3;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 3 9 3 9;"
        );
        button.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent ereignis) {
                nochNichtImplementiert(funktionsname);
            }
        });
        return button;
    }

    // Erstellt einen Tab-Button für die Plantypauswahl.
    private ToggleButton tabButtonErstellen(String beschriftung, ToggleGroup gruppe, boolean ausgewaehlt) {
        ToggleButton button = new ToggleButton(beschriftung);
        button.setToggleGroup(gruppe);
        button.setSelected(ausgewaehlt);
        button.setFont(Font.font("SansSerif", 11));
        tabButtonStilAktualisieren(button, ausgewaehlt);

        // Stil aktualisieren wenn sich der Auswahlzustand aendert
        button.selectedProperty().addListener(new javafx.beans.value.ChangeListener<Boolean>() {
            @Override
            public void changed(javafx.beans.value.ObservableValue<? extends Boolean> quelle,
                                Boolean alt, Boolean neu) {
                tabButtonStilAktualisieren(button, neu);
            }
        });
        return button;
    }

    // Setzt den Stil des Tab-Buttons je nach Auswahlzustand.
    private void tabButtonStilAktualisieren(ToggleButton button, boolean ausgewaehlt) {
        if (ausgewaehlt) {
            button.setStyle(
                "-fx-background-color: #2471A3;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 3;" +
                "-fx-border-radius: 3;" +
                "-fx-border-color: #C8C8C8;" +
                "-fx-border-width: 1;" +
                "-fx-padding: 3 14 3 14;"
            );
        } else {
            button.setStyle(
                "-fx-background-color: #E8E8E8;" +
                "-fx-text-fill: #1A1A1A;" +
                "-fx-background-radius: 3;" +
                "-fx-border-radius: 3;" +
                "-fx-border-color: #C8C8C8;" +
                "-fx-border-width: 1;" +
                "-fx-padding: 3 14 3 14;"
            );
        }
    }


     //Erstellt einen einfachen Menüpunkt ohne Tastaturkürzel.
     //Zeigt einen Hinweisdialog wenn angeklickt.
    private MenuItem menuPunktErstellen(String text, String tastenkuerzel) {
        MenuItem punkt = new MenuItem(text);
        if (!tastenkuerzel.isEmpty()) {
            punkt.setAccelerator(
                javafx.scene.input.KeyCombination.keyCombination(tastenkuerzel)
            );
        }
        final String punktText = text;
        punkt.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent ereignis) {
                nochNichtImplementiert(punktText);
            }
        });
        return punkt;
    }

    // Zeigt einen Hinweisdialog für noch nicht implementierte Funktionen.
    private void nochNichtImplementiert(String funktion) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Nicht implementiert");
        dialog.setHeaderText(funktion);
        dialog.setContentText("Diese Funktion ist noch nicht implementiert.");
        dialog.showAndWait();
    }
}
