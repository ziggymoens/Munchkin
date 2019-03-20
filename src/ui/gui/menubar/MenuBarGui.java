package ui.gui.menubar;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import language.LanguageResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MenuBarGui extends MenuBar {
    private ToggleGroup toggleGroup;
    private List<RadioMenuItem> choiceItems;

    public MenuBarGui() {
        Menu[] menus = new Menu[3];
        menus[0] = new Menu("Options");
        menus[1] = new Menu("Language");
        menus[2] = new Menu("Help");

        MenuItem[] menuItemsOptions = new MenuItem[5];
        menuItemsOptions[0] = new MenuItem("New Game");
        menuItemsOptions[0].setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        menuItemsOptions[0].setOnAction(this::buttonNewGameEventHandler);
        menuItemsOptions[1] = new MenuItem("Save Game");
        menuItemsOptions[1].setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        menuItemsOptions[1].setOnAction(this::buttonNewGameEventHandler);
        menuItemsOptions[2] = new MenuItem("Load Game");
        menuItemsOptions[2].setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        menuItemsOptions[2].setOnAction(this::buttonNewGameEventHandler);
        menuItemsOptions[3] = new SeparatorMenuItem();
        menuItemsOptions[4] = new MenuItem("Exit Game");
        menuItemsOptions[4].setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
        menuItemsOptions[4].setOnAction(this::buttonNewGameEventHandler);

        menus[0].getItems().addAll(menuItemsOptions);

        choiceItems = new ArrayList<>();
        choiceItems.add(new RadioMenuItem("Nederlands"));
        choiceItems.add(new RadioMenuItem("Français"));
        choiceItems.add(new RadioMenuItem("English"));

        ToggleGroup toggleGroup = new ToggleGroup();
        for (RadioMenuItem ch : choiceItems) {
            ch.setOnAction(this::langToggleSwitch);
            toggleGroup.getToggles().add(ch);
        }
        updateMenuBarLang();


        for (RadioMenuItem ch : choiceItems) {
            menus[1].getItems().add(ch);
        }
        MenuItem menuItemHelp = new MenuItem("Help");
        menus[2].getItems().add(menuItemHelp);

        getMenus().addAll(menus);

    }

    public void updateMenuBarLang(){
        RadioMenuItem item;
        String taal = LanguageResource.getLocale().toString();
        switch (taal) {
            case "nl":
                item = choiceItems.get(0);
                break;
            case "fr":
                item = choiceItems.get(1);
                break;
            default:
            case "en":
                item = choiceItems.get(2);
                break;
        }
        item.setSelected(true);
    }

    private void buttonNewGameEventHandler(ActionEvent event) {

    }

    private void buttonOpenEventHandler(ActionEvent event) {

    }

    private void buttonSaveEventHandler(ActionEvent event) {

    }

    private void buttonExitEventHandler(ActionEvent event) {
        Platform.exit();
    }

    private void langToggleSwitch(ActionEvent event){
        String lang = "";
        for (RadioMenuItem ch:choiceItems){
            if (ch.isSelected()){
                lang = ch.getText().toLowerCase().substring(0,1);
            }
        }
        LanguageResource.setLocale(new Locale(lang));
        updateMenuBarLang();
    }
}
