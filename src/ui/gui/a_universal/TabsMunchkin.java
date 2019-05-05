package ui.gui.a_universal;

import domein.DomeinController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import language.LanguageResource;
import main.StartUpGui;
import ui.gui.ucs.usecase1.UseCase1G;

import java.util.Locale;
import java.util.Optional;

public class TabsMunchkin extends TabPane {
    private static TabsMunchkin pane = new TabsMunchkin();

    private static void setChangeListner() {
        pane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                StartUpGui.menuBarGui.changeTextMenu(((TabExtended)pane.getTabs().get((Integer) newValue)).getLocale());
            }
        });
    }

    //(ChangeListener<Tab>) (observable, oldValue, newValue) -> MenuBarGui.changeTextMenu(((TabExtended) pane.getSelectionModel().getSelectedItem()).getLocale())
    public static void addTab(TabExtended tab) {
        pane.getTabs().add(tab);
    }

    public static void addNewGame() {
        DomeinController dc = new DomeinController();
        TabExtended tab = new TabExtended(dc);
        tab.setOnCloseRequest(TabsMunchkin::closeRequest);
        tab.setContent(new UseCase1G(dc));
        if (pane.getTabs().size() == 0) {
            tab.setText("Game 1");
        } else {
            String[] stukken = pane.getTabs().get(pane.getTabs().size() - 1).getText().split(" ");
            tab.setText(String.format("Game %d", Integer.parseInt(stukken[1]) + 1));
        }
        TabsMunchkin.addTab(tab);
        setChangeListner();
    }

    public static void closeRequest(Event event) {
        Locale locale = ((TabExtended) getPane().getSelectionModel().getSelectedItem()).getLocale();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(LanguageResource.getStringLanguage("close", locale));
        alert.setHeaderText(LanguageResource.getStringLanguage("closeconfirm", locale));
        alert.setContentText(String.format("%s%n%s", LanguageResource.getStringLanguage("closetext1", locale), LanguageResource.getStringLanguage("closetext2", locale)));
        Optional<ButtonType> antwoord = alert.showAndWait();
        if (antwoord.get() == ButtonType.CANCEL) {
            event.consume();
        }
        if (pane.getTabs().size() == 1) {
            Platform.exit();
        }
    }

    public static TabPane getPane() {
        return pane;
    }
}