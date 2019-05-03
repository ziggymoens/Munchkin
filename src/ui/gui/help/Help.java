package ui.gui.help;


import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class Help extends BorderPane {

    public Help() {
        Label lblTop = new Label("Help");
        this.setTop(lblTop);
    }
}
