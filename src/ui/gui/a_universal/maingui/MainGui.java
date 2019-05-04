package ui.gui.a_universal.maingui;

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
    private final Label messages;
    private final BorderPane pane;
    private final PauseTransition visiblePause;

    public MainGui(){
        this.mb = new MenuBarGui();
        this.messages = new Label();
        messages.setMaxWidth(Double.MAX_VALUE);
        messages.setAlignment(Pos.CENTER);
        visiblePause = new PauseTransition(Duration.seconds(3));
        visiblePause.setOnFinished(event -> messages.setVisible(false));
        messages.setVisible(false);
        mb.setId("mb");
        messages.setId("messages");
        pane = new BorderPane();
        pane.setId("pane");
        //this.setTop(mb);
        this.setCenter(pane);
        this.setBottom(messages);
        getStylesheets().add("ui/gui/a_universal/maingui/MainGui.css");
    }

    public MenuBar getMb() {
        return mb;
    }

    public void updateMenuLang(){
        mb.updateMenuBarLang();
    }

    public Label getMessages() {
        return messages;
    }

    public void visiblePause(){
        visiblePause.play();
    }

    public BorderPane getPane() {
        return pane;
    }

    public void printErrors(){
        messages.setTextFill(Color.web("#FF0000"));
    }

    public void printConfirmation(){
        messages.setTextFill(Color.web("#00FF00"));
    }
}
