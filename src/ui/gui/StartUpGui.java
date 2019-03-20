package ui.gui;

import domein.DomeinController;
import java.util.Locale;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import language.LanguageResource;
import ui.gui.menubar.MenuBarGui;
import ui.gui.usecase1.UseCase1G;

public class StartUpGui extends Application {

    public static DomeinController dc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        dc = new DomeinController();
        Scene scene = new Scene(new UseCase1G(dc));
        stage.setScene(scene);
        stage.setTitle("Munchkin - G35");
        stage.show();
        stage.setResizable(false);
        stage.setOnCloseRequest(
                event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle(LanguageResource.getString("close"));
                    alert.setHeaderText(LanguageResource.getString("closeconfirm"));
                    alert.setContentText(String.format("%s%n%s", LanguageResource.getString("closetext1"), LanguageResource.getString("closetext2")));
    //                alert.setContentText(LanguageResource.getString("closetext1"));
    //                alert.setContentText(LanguageResource.getString("closetext2"));
                    Optional<ButtonType> antwoord = alert.showAndWait();
                    if (antwoord.get() == ButtonType.CANCEL) {
                        event.consume();
                    } else {
                        Platform.exit();
                    }
                });
    }
}
