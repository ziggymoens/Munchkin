package ui.gui.menubar;

import domein.DomeinController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import language.LanguageResource;
import ui.gui.StartUpGui;
import ui.gui.usecase1.UseCase1G;

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
        menuItemsOptions[1].setOnAction(this::buttonSaveEventHandler);
        menuItemsOptions[2] = new MenuItem("Load Game");
        menuItemsOptions[2].setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        menuItemsOptions[2].setOnAction(this::buttonOpenEventHandler);
        menuItemsOptions[3] = new SeparatorMenuItem();
        menuItemsOptions[4] = new MenuItem("Exit Game");
        menuItemsOptions[4].setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        menuItemsOptions[4].setOnAction(this::buttonExitEventHandler);

        menus[0].getItems().addAll(menuItemsOptions);

        choiceItems = new ArrayList<>();
        choiceItems.add(new RadioMenuItem("Nederlands"));
        choiceItems.add(new RadioMenuItem("Français"));
        choiceItems.add(new RadioMenuItem("English"));
        choiceItems.get(0).setOnAction(this::langToggleSwitchNl);
        choiceItems.get(1).setOnAction(this::langToggleSwitchFr);
        choiceItems.get(2).setOnAction(this::langToggleSwitchEn);

        ToggleGroup toggleGroup = new ToggleGroup();
        for (RadioMenuItem ch : choiceItems) {
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
        System.exit(0);
    }

    private void langToggleSwitchNl(ActionEvent event){
        LanguageResource.setLocale(new Locale("nl"));
        updateMenuBarLang();
    }
    private void langToggleSwitchFr(ActionEvent event){
        LanguageResource.setLocale(new Locale("fr"));
        updateMenuBarLang();
    }
    private void langToggleSwitchEn(ActionEvent event){
        LanguageResource.setLocale(new Locale("en"));
        updateMenuBarLang();
    }
}
