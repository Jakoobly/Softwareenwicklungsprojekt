package gantt;

import gantt.controller.GanttController;
import gantt.model.Plan;
import gantt.model.PlanObjekt;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/* hier startet das Programm -> erstellt den Plan, erzeugt den Controller,
welcher wiederum die View erzeugt und die Oberfläche zusammenbaut
 */

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        Plan plan;

        plan = new Plan(
                LocalTime.of(6, 0),
                LocalTime.of(18, 0),

                List.of(
                        "Arbeitszug",
                        "1a",
                        "1b",
                        "2",
                        "3",
                        "4",
                        "5",
                        "6",
                        "7"
                ),

                List.of(
                        new PlanObjekt("Arbeitszug", "B 621", "13:28", "13:42"),
                        new PlanObjekt("1a", "E 11", "07:44", "08:01"),
                        new PlanObjekt("1b", "E 21", "07:47", "07:59"),
                        new PlanObjekt("2", "P 101", "06:06", "06:39"),
                        new PlanObjekt("3", "P 102", "06:05", "06:33"),
                        new PlanObjekt("4", "P 200", "06:10", "06:28"),
                        new PlanObjekt("5", "Ng 321", "06:15", "08:30"),
                        new PlanObjekt("6", "Ng 401", "06:43", "08:10"),
                        new PlanObjekt("7", "Lz 940", "07:50", "07:55"),
                        new PlanObjekt("2", "P 109", "14:27", "15:05"),
                        new PlanObjekt("4", "P 210", "14:21", "14:39"),
                        new PlanObjekt("6", "Ng 404", "16:21", "17:36")
                ),

                // Farbschema der Zeilen
                Map.of(
                        "Arbeitszug", "#444444",
                        "1a", "#1f77b4",
                        "1b", "#2ca02c",
                        "2", "#ff7f0e",
                        "3", "#d62728",
                        "4", "#9467bd",
                        "5", "#8c564b",
                        "6", "#17becf",
                        "7", "#bcbd22"
                )
        );

        // Controller erzeugen
        GanttController controller = new GanttController(plan);

        Scene scene = new Scene(controller.getView(), 1100, 600);

        stage.setTitle("Gantt Diagram Prototype");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}