package ui.gui.extras.menubar;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import language.LanguageResource;
import ui.TabExtended;
import ui.gui.TabsMunchkin;
import ui.gui.help.Help;
import ui.gui.settings.Settings;
import ui.gui.ucs.usecase8.UseCase8G;
import ui.gui.ucs.usecase9.UseCase9G;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


public class MenuBarGui extends MenuBar {
    private ToggleGroup toggleGroup;
    private List<RadioMenuItem> choiceItems;

    public MenuBarGui() {
        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu("Options"));
        menus.add(new Menu("Language"));
        menus.add(new Menu("Help"));

        List<MenuItem> menuItemsOptions = new ArrayList<>();
        menuItemsOptions.add(new MenuItem("Settings"));
        menuItemsOptions.get(0).setOnAction(this::openSetting);
        menuItemsOptions.get(0).setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
        menuItemsOptions.add(new MenuItem("New Game"));
        menuItemsOptions.get(1).setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        menuItemsOptions.get(1).setOnAction(event -> {
            try {
                buttonNewGameEventHandler(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        menuItemsOptions.add(new MenuItem("Save Game"));
        menuItemsOptions.get(2).setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        menuItemsOptions.get(2).setOnAction(this::buttonSaveEventHandler);
        menuItemsOptions.add(new MenuItem("Load Game"));
        menuItemsOptions.get(3).setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        menuItemsOptions.get(3).setOnAction(this::buttonOpenEventHandler);
        menuItemsOptions.add(new SeparatorMenuItem());
        menuItemsOptions.add(new MenuItem("Exit Game"));
        menuItemsOptions.get(4).setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        menuItemsOptions.get(4).setOnAction(this::buttonExitEventHandler);
        menuItemsOptions.add(new SeparatorMenuItem());
        menuItemsOptions.add(new MenuItem("Reload Game"));
        menuItemsOptions.get(7).setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
        menuItemsOptions.get(7).setOnAction(this::reloadEventHandler);

        menus.get(0).getItems().addAll(menuItemsOptions);

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
            menus.get(1).getItems().add(ch);
        }
        MenuItem menuItemHelp = new MenuItem("Help");
        menuItemHelp.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        menuItemHelp.setOnAction(this::helpScreen);
        menus.get(2).getItems().add(menuItemHelp);

        getMenus().addAll(menus);

    }

    private void openSetting(ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = new Scene(new Settings(((TabExtended)TabsMunchkin.getPane().getTabs().get(TabsMunchkin.getPane().getSelectionModel().getSelectedIndex()))), 450, 250);
        stage.setScene(scene);
        stage.setTitle("Munchkin - G35 - Settings");
        stage.show();
    }

    private void helpScreen(ActionEvent event) {
        try{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(LanguageResource.getString("help"));
            alert.setHeaderText(String.format("%s",LanguageResource.getString("helptext")));
            alert.setContentText(LanguageResource.getString("helpuser"));
            Optional<ButtonType> antw = alert.showAndWait();
            if(antw.get() == ButtonType.OK ||antw.get() == ButtonType.CANCEL) {
                event.consume();
            }
        }catch (Exception ignored){
        }
    }

    private void reloadEventHandler(ActionEvent actionEvent) {
        System.out.println("Reloading");
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

    private void buttonNewGameEventHandler(ActionEvent event) throws IOException {
        TabsMunchkin.addNewGame();
        //StartUpGui startUpGui = new StartUpGui();
        //startUpGui.reload();
    }

    private void buttonOpenEventHandler(ActionEvent event) {
        try{
            Stage stage = new Stage();
            Scene scene = new Scene(new UseCase9G(), 450, 300);
            stage.setScene(scene);
            stage.setTitle(String.format("Munchkin - G35 - %s", LanguageResource.getString("load")));
            stage.show();
            stage.setResizable(false);
        }catch (Exception ignored){

        }
    }

    private void buttonSaveEventHandler(ActionEvent event) {
        try{
            Stage stage = new Stage();
            Scene scene = new Scene(new UseCase8G(), 450, 300);
            stage.setScene(scene);
            stage.setTitle(String.format("Munchkin - G35 - %s", LanguageResource.getString("save")));
            stage.show();
            stage.setResizable(false);
        }catch (Exception ignored){

        }
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
