package ui.gui.maingui;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import ui.gui.extras.menubar.MenuBarGui;


public class MainGui extends BorderPane {
    private final MenuBarGui mb;
    private final Label errors;
    private final BorderPane pane;
    private final PauseTransition visiblePause;

    public MainGui(){
        this.mb = new MenuBarGui();
        this.errors = new Label();
        errors.setAlignment(Pos.CENTER);
        visiblePause = new PauseTransition(Duration.seconds(3));
        visiblePause.setOnFinished(event -> errors.setVisible(false));
        errors.setVisible(false);
        mb.setId("mb");
        errors.setId("errors");
        pane = new BorderPane();
        pane.setId("pane");
        this.setTop(mb);
        this.setCenter(pane);
        this.setBottom(errors);
        getStylesheets().add("ui/gui/maingui/MainGui.css");
    }

    public MenuBar getMb() {
        return mb;
    }

    public void updateMenuLang(){
        mb.updateMenuBarLang();
    }

    public Label getErrors() {
        return errors;
    }

    public void visiblePause(){
        visiblePause.play();
    }

    public BorderPane getPane() {
        return pane;
    }

    public void printErrors(){
        errors.setTextFill(Color.web("#FF0000"));
    }

    public void printConfirmation(){
        errors.setTextFill(Color.web("#00FF00"));
    }
}
