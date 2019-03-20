package ui.gui;

import domein.DomeinController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.gui.menubar.MenuBarGui;
import ui.gui.usecase1.UseCase1G;

public class StartUpGui extends Application {

    public static DomeinController dc;
    //public static BorderPane borderPane = new BorderPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //borderPane.setTop(new MenuBarGui());
        dc = new DomeinController();
        Scene scene = new Scene(new UseCase1G(dc));
        stage.setScene(scene);
        stage.setTitle("Munchkin - G35");
        stage.show();
        stage.setResizable(false);
    }
}
