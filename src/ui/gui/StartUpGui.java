package ui.gui;

import domein.DomeinController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.gui.usecase1.UseCase1G;

public class StartUpGui extends Application {

    public static DomeinController dc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        dc = new DomeinController();
        Parent root = new UseCase1G(dc);
        Scene scene = new MainGui(root);
        stage.setScene(scene);
        stage.setTitle("Munchkin - G35");
        stage.show();
    }
}
