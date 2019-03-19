package ui.gui;

import domein.DomeinController;
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
        DomeinController dc = new DomeinController();
        Scene scene = new Scene(new UseCase1G(dc));
        stage.setScene(scene);
        stage.setTitle("Munchkin - G35");
        stage.show();
    }
}
