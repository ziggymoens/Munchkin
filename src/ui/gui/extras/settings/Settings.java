package ui.gui.extras.settings;

import connection.Connection;
import domein.DomeinController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import language.LanguageResource;
import printer.Printer;
import ui.gui.a_universal.TabExtended;
import ui.gui.a_universal.TabsMunchkin;

import java.util.Locale;


public class Settings extends TabPane {
    private Locale locale = ((TabExtended) TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).getLocale();
    private DomeinController dc;
    private VBox centerVBox;
    private CheckBox checkboxDevelopper;
    private CheckBox checkBoxConnection;
    private CheckBox checkBoxStacktrace;
    private PauseTransition visiblePause;
    private Label messages;

    public Settings(TabExtended tab) {
        this.dc = tab.getDc();
        centerVBox = new VBox();
        setLayOutTabSettings();
        setLayoutTabGame();
    }

    private void setLayOutTabSettings() {
        BorderPane bp = new BorderPane();
        bp.setCenter(centerVBox);
        VBox developper = new VBox();
        checkboxDevelopper = new CheckBox();
        checkboxDevelopper.setSelected(Printer.getDeveloperMode());
        developper.getChildren().addAll(new Label(LanguageResource.getStringLanguage("usecase1.developer", locale)), checkboxDevelopper);
        VBox connection = new VBox();
        checkBoxConnection = new CheckBox();
        checkBoxConnection.setSelected(Connection.isConnected());
        connection.getChildren().addAll(new Label(LanguageResource.getStringLanguage("connected", locale)), checkBoxConnection);
        VBox stacktrace = new VBox();
        checkBoxStacktrace = new CheckBox();
        checkBoxStacktrace.setSelected(Printer.getPrintStackTrace());
        stacktrace.getChildren().addAll(new Label(LanguageResource.getStringLanguage("usecase1.stacktrace", locale), checkBoxStacktrace));
        Button button = new Button(LanguageResource.getString("save"));
        button.setOnAction(this::saveSettings);
        messages = new Label("Saved");
        visiblePause = new PauseTransition(Duration.seconds(3));
        visiblePause.setOnFinished(event -> messages.setVisible(false));
        messages.setTextFill(Color.web("#00FF00"));
        messages.setVisible(false);
        centerVBox.getChildren().addAll(developper, connection,stacktrace, button, messages);
        Tab tab = new Tab("settings", bp);
        this.getTabs().add(tab);
    }

    public void setLayoutTabGame() {
        try {
            BorderPane borderPane = new BorderPane();
            VBox center = new VBox();
            for (int i = 1; i <= dc.geefAantalSpelers(); i++) {
                Label speler = new Label(String.format("%s %d", LanguageResource.getStringLanguage("player", locale), i));
                TextField naam = new TextField(dc.geefNaamSpeler(i - 1));
                RadioButton man = new RadioButton(LanguageResource.getString("man"));
                man.setSelected(dc.geefGeslachtSpeler(i - 1).equalsIgnoreCase(LanguageResource.getStringLanguage("man", locale)));
                RadioButton woman = new RadioButton(LanguageResource.getString("woman"));
                woman.setSelected(dc.geefGeslachtSpeler(i - 1).equalsIgnoreCase(LanguageResource.getStringLanguage("woman", locale)));
                HBox spelerHBox = new HBox();
                spelerHBox.getChildren().addAll(speler, naam, man, woman);
                center.getChildren().add(spelerHBox);
            }
            borderPane.setCenter(center);
            Tab tab = new Tab("game", borderPane);
            this.getTabs().add(tab);
        }catch (Exception ignore){}
    }

    private void saveSettings(ActionEvent event){
        boolean conn = checkBoxConnection.isSelected();
        boolean dev = checkboxDevelopper.isSelected();
        boolean stack = checkBoxStacktrace.isSelected();
        Connection.setConnected(conn);
        Printer.setDeveloperMode(dev);
        Printer.setPrintStackTrace(stack);
        visiblePause();
    }

    private void visiblePause(){
        visiblePause.play();
    }
}
