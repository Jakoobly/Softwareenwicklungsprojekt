package SEP;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        MainController controller = new MainController(stage);
        controller.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
