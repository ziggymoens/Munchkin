package ui.gui.extras.menubar;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import language.LanguageResource;
import ui.gui.a_universal.TabExtended;
import ui.gui.a_universal.TabsMunchkin;
import ui.gui.extras.help.Help;
import ui.gui.extras.settings.Settings;
import ui.gui.ucs.usecase8.UseCase8G;
import ui.gui.ucs.usecase9.UseCase9G;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MenuBarGui extends MenuBar {
    private List<Menu> menus;
    private ToggleGroup toggleGroup;
    private List<RadioMenuItem> choiceItems;

    public MenuBarGui() {
        Locale locale = new Locale("en");
        menus = new ArrayList<>();
        menus.add(new Menu(LanguageResource.getStringLanguage("menu.options", locale)));
        menus.add(new Menu(LanguageResource.getStringLanguage("menu.language", locale)));
        menus.add(new Menu(LanguageResource.getStringLanguage("menu.help", locale)));

        List<MenuItem> menuItemsOptions = new ArrayList<>();
        menuItemsOptions.add(new MenuItem(LanguageResource.getStringLanguage("menu.options.settings", locale)));
        menuItemsOptions.get(0).setOnAction(this::openSetting);
        menuItemsOptions.get(0).setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
        menuItemsOptions.add(new MenuItem(LanguageResource.getStringLanguage("menu.options.newgame", locale)));
        menuItemsOptions.get(1).setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        menuItemsOptions.get(1).setOnAction(event -> {
            try {
                buttonNewGameEventHandler(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        menuItemsOptions.add(new MenuItem(LanguageResource.getStringLanguage("menu.options.savegame", locale)));
        menuItemsOptions.get(2).setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        menuItemsOptions.get(2).setOnAction(this::buttonSaveEventHandler);
        menuItemsOptions.add(new MenuItem(LanguageResource.getStringLanguage("menu.options.loadgame", locale)));
        menuItemsOptions.get(3).setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        menuItemsOptions.get(3).setOnAction(this::buttonOpenEventHandler);
        menuItemsOptions.add(new SeparatorMenuItem());
        menuItemsOptions.add(new MenuItem(LanguageResource.getStringLanguage("menu.options.exitgame", locale)));
        menuItemsOptions.get(5).setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        menuItemsOptions.get(5).setOnAction(this::buttonExitEventHandler);
        menuItemsOptions.add(new SeparatorMenuItem());
        menuItemsOptions.add(new MenuItem(LanguageResource.getStringLanguage("menu.options.reloadgame", locale)));
        menuItemsOptions.get(7).setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
        menuItemsOptions.get(7).setOnAction(this::reloadEventHandler);

        menus.get(0).getItems().addAll(menuItemsOptions);

        choiceItems = new ArrayList<>();
        choiceItems.add(new RadioMenuItem(LanguageResource.getStringLanguage("nl", locale)));
        choiceItems.add(new RadioMenuItem(LanguageResource.getStringLanguage("fr", locale)));
        choiceItems.add(new RadioMenuItem(LanguageResource.getStringLanguage("en", locale)));
        choiceItems.get(0).setOnAction(this::langToggleSwitchNl);
        choiceItems.get(1).setOnAction(this::langToggleSwitchFr);
        choiceItems.get(2).setOnAction(this::langToggleSwitchEn);

        ((Menu) menus.get(0)).setOnShowing((EventHandler<Event>) event -> updateSettingsLang());
        ((Menu) menus.get(1)).setOnShowing((EventHandler<Event>) event -> updateMenuBarLang());
        ((Menu) menus.get(2)).setOnShowing((EventHandler<Event>) event -> updateHelpLang());

        ToggleGroup toggleGroup = new ToggleGroup();
        for (RadioMenuItem ch : choiceItems) {
            toggleGroup.getToggles().add(ch);
        }
        //updateMenuBarLang();


        for (RadioMenuItem ch : choiceItems) {
            menus.get(1).getItems().add(ch);
        }
        MenuItem menuItemHelp = new MenuItem(LanguageResource.getStringLanguage("help", locale));
        menuItemHelp.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        menuItemHelp.setOnAction(this::helpScreen);
        //aangepast
        menus.get(2).getItems().add(menuItemHelp);

        getMenus().addAll(menus);

    }

    private void updateHelpLang() {
        Locale locale = ((TabExtended) TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).getLocale();
        menus.get(2).getItems().get(0).setText(LanguageResource.getStringLanguage("menu.help", locale));
    }

    private void updateSettingsLang() {
        Locale locale = ((TabExtended) TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).getLocale();
        menus.get(0).getItems().get(0).setText(LanguageResource.getStringLanguage("menu.options.settings", locale));
        menus.get(0).getItems().get(1).setText(LanguageResource.getStringLanguage("menu.options.newgame", locale));
        menus.get(0).getItems().get(2).setText(LanguageResource.getStringLanguage("menu.options.savegame", locale));
        menus.get(0).getItems().get(3).setText(LanguageResource.getStringLanguage("menu.options.loadgame", locale));
        menus.get(0).getItems().get(5).setText(LanguageResource.getStringLanguage("menu.options.exitgame", locale));
        menus.get(0).getItems().get(7).setText(LanguageResource.getStringLanguage("menu.options.reloadgame", locale));
    }

    private void openSetting(ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = new Scene(new Settings(((TabExtended) TabsMunchkin.getPane().getTabs().get(TabsMunchkin.getPane().getSelectionModel().getSelectedIndex()))), 450, 250);
        stage.setScene(scene);
        stage.setTitle("Munchkin - G35 - Settings");
        stage.show();
    }

    private void helpScreen(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(new Help(), 450, 300);
            stage.setScene(scene);
            stage.setTitle(String.format("Munchkin - G35 - %s", LanguageResource.getString("help")));
            stage.show();
            stage.setResizable(false);
        } catch (Exception ignored) {

        }
    }

    private void reloadEventHandler(ActionEvent actionEvent) {
        System.out.println("Reloading");
    }

    public void updateMenuBarLang() {
        RadioMenuItem item;
        Locale locale = ((TabExtended) TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).getLocale();
        String taal = locale.toString();
        menus.get(1).getItems().get(0).setText(LanguageResource.getStringLanguage("nl", locale));
        menus.get(1).getItems().get(1).setText(LanguageResource.getStringLanguage("fr", locale));
        menus.get(1).getItems().get(2).setText(LanguageResource.getStringLanguage("en", locale));
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

    private void buttonNewGameEventHandler(ActionEvent event) throws IOException {
        TabsMunchkin.addNewGame();
        //StartUpGui startUpGui = new StartUpGui();
        //startUpGui.reload();
    }

    private void buttonOpenEventHandler(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(new UseCase9G(), 450, 300);
            stage.setScene(scene);
            stage.setTitle(String.format("Munchkin - G35 - %s", LanguageResource.getString("load")));
            stage.show();
            stage.setResizable(false);
        } catch (Exception ignored) {

        }
    }

    private void buttonSaveEventHandler(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(new UseCase8G(), 450, 300);
            stage.setScene(scene);
            stage.setTitle(String.format("Munchkin - G35 - %s", LanguageResource.getString("save")));
            stage.show();
            stage.setResizable(false);
        } catch (Exception ignored) {

        }
    }

    private void buttonExitEventHandler(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    private void langToggleSwitchNl(ActionEvent event) {
        Locale locale = new Locale("nl");
        ((TabExtended) TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).setLocale(locale);
        changeTextMenu(locale);
    }

    private void langToggleSwitchFr(ActionEvent event) {
        Locale locale = new Locale("fr");
        ((TabExtended) TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).setLocale(locale);
        changeTextMenu(locale);
    }

    private void langToggleSwitchEn(ActionEvent event) {
        Locale locale = new Locale("en");
        ((TabExtended) TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).setLocale(locale);
        changeTextMenu(locale);
    }

    public void changeTextMenu(Locale locale) {
        menus.get(0).setText(LanguageResource.getStringLanguage("menu.options", locale));
        menus.get(1).setText(LanguageResource.getStringLanguage("menu.language", locale));
        menus.get(2).setText(LanguageResource.getStringLanguage("menu.help", locale));
    }
}
