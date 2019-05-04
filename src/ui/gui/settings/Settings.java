package ui.gui.settings;

import connection.Connection;
import domein.DomeinController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import language.LanguageResource;
import printer.Printer;
import ui.TabExtended;


public class Settings extends TabPane {
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
        setLayOutTab1();
    }

    public void setLayOutTab1() {
        BorderPane bp = new BorderPane();
        bp.setCenter(centerVBox);
        VBox developper = new VBox();
        checkboxDevelopper = new CheckBox();
        checkboxDevelopper.setSelected(Printer.getDeveloperMode());
        developper.getChildren().addAll(new Label(LanguageResource.getString("usecase1.developer")), checkboxDevelopper);
        VBox connection = new VBox();
        checkBoxConnection = new CheckBox();
        checkBoxConnection.setSelected(Connection.isConnected());
        connection.getChildren().addAll(new Label(LanguageResource.getString("connected")), checkBoxConnection);
        VBox stacktrace = new VBox();
        checkBoxStacktrace = new CheckBox();
        checkBoxStacktrace.setSelected(Printer.getPrintStackTrace());
        stacktrace.getChildren().addAll(new Label(LanguageResource.getString("usecase1.stacktrace"), checkBoxStacktrace));
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
