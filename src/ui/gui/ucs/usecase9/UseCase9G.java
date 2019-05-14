package ui.gui.ucs.usecase9;

import domein.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import ui.gui.a_universal.maingui.MainGui;

import java.util.List;


public class UseCase9G extends MainGui{
    private ComboBox cboSpelen;
    private DomeinController dc;

    public UseCase9G(DomeinController dc) {
        getStylesheets().add("ui/gui/ucs/usecase9/UseCase9G.css");
        this.dc = dc;
        buildGui();
    }


    private void buildGui() {
        VBox box;
        box = new VBox();
        cboSpelen = new ComboBox();
        cboSpelen.setPromptText("Kies hier het spel dat u wilt opslaan");
        //mogelijkheden plaatsen voor spellen
        List<String> spelen = dc.geefOverzichtSpelen();
        ObservableList lijst = FXCollections.observableArrayList(spelen);  //FXCollections omdat je niet zomaar new ObservableList() kunt doen
        cboSpelen.setItems(lijst);
        box.getChildren().addAll(cboSpelen);
    }
}
