package ui.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;


public class MenuBarGUI extends StackPane {
    private MenuBar menuBar;

    public MenuBarGUI() {
        init();
        layoutMenuBar();
    }

    private void init() {
        Menu[] menus = new Menu[3];
        menus[0] = new Menu("Options");
        menus[1] = new Menu("Language");
        menus[2] = new Menu("Help");

        MenuItem[] menuItemsOptions = new MenuItem[4];
        menuItemsOptions[0] = new MenuItem("New Game");
        menuItemsOptions[1] = new MenuItem("Save Game");
        menuItemsOptions[2] = new MenuItem("Load Game");
        menuItemsOptions[3] = new MenuItem("Exit Game");

        menus[0].getItems().addAll(menuItemsOptions);

        MenuItem menuItemOptions = new MenuItem("Options");
        menus[1].getItems().add(menuItemOptions);

        MenuItem menuItemHelp = new MenuItem("Help");
        menus[2].getItems().add(menuItemHelp);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(menus);

    }

    private void layoutMenuBar() {
        menuBar.useSystemMenuBarProperty().set(true);
        getChildren().add(menuBar);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    private void ButtonOpenEventHandler(ActionEvent event) {

    }

    private void ButtonSaveEventHandler(ActionEvent event) {

    }

    private void ButtonExitEventHandler(ActionEvent event) {

    }
}
