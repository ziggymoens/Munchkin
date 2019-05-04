package ui.gui.a_universal.maingui;

import javafx.scene.layout.BorderPane;


public class MainGui extends BorderPane {
    private final BorderPane pane;

    public MainGui(){
        pane = new BorderPane();
        pane.setId("pane");
        this.setCenter(pane);
        getStylesheets().add("ui/gui/a_universal/maingui/MainGui.css");
    }

    public BorderPane getPane() {
        return pane;
    }

}
