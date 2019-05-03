package main;

import domein.DomeinController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import language.LanguageResource;
import ui.gui.TabsMunchkin;

import java.util.Optional;

public class StartUpGuiV2 extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tabs");
        primaryStage.setMinHeight(850);
        primaryStage.setMinWidth(1000);
        Group root = new Group();
        DomeinController dc = new DomeinController();
        Scene scene = new Scene(root, Color.WHITE);
        TabPane tabPane = TabsMunchkin.getPane();
        BorderPane borderPane = new BorderPane();
        TabsMunchkin.addNewGame();
        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(this::closeRequest);
    }

    public void closeRequest(Event event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(LanguageResource.getString("close"));
        alert.setHeaderText(LanguageResource.getString("closeconfirm"));
        alert.setContentText(String.format("%s%n%s", LanguageResource.getString("closetext1"), LanguageResource.getString("closetext2")));
        Optional<ButtonType> antwoord = alert.showAndWait();
        if (antwoord.get() == ButtonType.CANCEL) {
            event.consume();
        } else {
            Platform.exit();
        }
    }
}
