package ui.gui;

import domein.DomeinController;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import language.LanguageResource;
import ui.gui.ucs.usecase1.UseCase1G;

import java.util.Optional;

public class TabsMunchkin extends TabPane {
    private static TabPane pane = new TabPane();

    public static void addTab(Tab tab) {
        pane.getTabs().add(tab);
    }

    public static void addNewGame() {
        Tab tab = new Tab();
        tab.setOnCloseRequest(TabsMunchkin::closeRequest);
        tab.setContent(new UseCase1G(new DomeinController()));
        if (pane.getTabs().size()==0) {
            System.out.println(pane.getTabs().toArray().length);
            tab.setText("Game 1");
        }else{
            String[] stukken = pane.getTabs().get(pane.getTabs().size()-1).getText().split(" ");
            tab.setText(String.format("Game %d", Integer.parseInt(stukken[1])+1));
        }
        TabsMunchkin.addTab(tab);
    }

    public static void closeRequest(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(LanguageResource.getString("close"));
        alert.setHeaderText(LanguageResource.getString("closeconfirm"));
        alert.setContentText(String.format("%s%n%s", LanguageResource.getString("closetext1"), LanguageResource.getString("closetext2")));
        Optional<ButtonType> antwoord = alert.showAndWait();
        if (antwoord.get() == ButtonType.CANCEL) {
            event.consume();
        }
    }

    public static TabPane getPane() {
        return pane;
    }
}