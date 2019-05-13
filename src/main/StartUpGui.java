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
import ui.gui.a_universal.TabExtended;
import ui.gui.a_universal.TabsMunchkin;
import ui.gui.extras.menubar.MenuBarGui;

import java.util.Locale;
import java.util.Optional;

public class StartUpGui extends Application {

    private static Stage primaryStage;

    public static MenuBarGui menuBarGui;

    /**
     * Main methode voor GUI
     *
     * @param args startUp args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Startmethode voor de GUI
     *
     * @param primaryStage standaard meegegeven primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Munchkin - G35");
        primaryStage.setMinHeight(850);
        primaryStage.setMinWidth(1000);
        Group root = new Group();
        Scene scene = new Scene(root, Color.WHITE);
        TabPane tabPane = TabsMunchkin.getPane();
        BorderPane borderPane = new BorderPane();
        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        DomeinController dc = new DomeinController();
        TabsMunchkin.addNewGame();
        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.setCenter(tabPane);
        menuBarGui = new MenuBarGui();
        borderPane.setTop(menuBarGui);
        root.getChildren().addAll(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(true);
        primaryStage.setOnCloseRequest(this::closeRequest);
        StartUpGui.primaryStage = primaryStage;
    }

    /**
     * methode voor sluiten van venster
     *
     * @param event event van de sluitknop
     */
    private void closeRequest(Event event) {
        Locale locale = ((TabExtended) TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).getLocale();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(LanguageResource.getStringLanguage("close", locale));
        alert.setHeaderText(LanguageResource.getStringLanguage("closeconfirm", locale));
        alert.setContentText(String.format("%s%n%s", LanguageResource.getStringLanguage("closetext1", locale), LanguageResource.getStringLanguage("closetext2", locale)));
        Optional<ButtonType> antwoord = alert.showAndWait();
        if (antwoord.get() == ButtonType.CANCEL) {
            event.consume();
        } else {
            Platform.exit();
        }
    }

    /**
     * methode om grootte van scherm aan te passen
     *
     * @param w width
     * @param h heigth
     */
    public static void changeWindow(int w, int h) {
        primaryStage.setMinWidth(w);
        primaryStage.setMaxWidth(w);
        primaryStage.setWidth(w);
        primaryStage.setMinHeight(h);
        primaryStage.setMaxHeight(h);
        primaryStage.setHeight(h);
    }

}
