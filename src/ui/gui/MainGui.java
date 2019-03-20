package ui.gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class MainGui extends BorderPane {
    private DomeinController dc;

    public MainGui(DomeinController dc) {
        this.dc = dc;
        MenuBar mb = new MenuBar();
        Menu m1 = new Menu("Options");
        MenuItem open = new MenuItem("Open...");
        MenuItem opslaan = new MenuItem("Save...");
        MenuItem quit = new MenuItem("Quit");
        open.setOnAction(this::ButtonOpenEventHandler);
        opslaan.setOnAction(this::ButtonSaveEventHandler);
        quit.setOnAction(this::ButtonExitEventHandler);
        m1.getItems().addAll(open, opslaan, quit);
        mb.getMenus().add(m1);
        setTop(mb);

    }

    private void ButtonOpenEventHandler(ActionEvent event) {

    }

    private void ButtonSaveEventHandler(ActionEvent event) {

    }

    private void ButtonExitEventHandler(ActionEvent event) {

    }


}
