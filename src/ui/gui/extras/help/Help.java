package ui.gui.extras.help;


import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import ui.gui.a_universal.TabExtended;
import ui.gui.a_universal.TabsMunchkin;

import java.util.Locale;


public class Help extends BorderPane {

    private Locale locale = ((TabExtended)TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).getLocale();

    public Help() {
        Label lblTop = new Label("Help");
        this.setTop(lblTop);
    }
}
