package ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.gui.usecase1.UseCase1G;

public class StartUpGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new UseCase1G());
        stage.setScene(scene);
        stage.setTitle("Munchkin - G35");
        stage.show();
    }
}
