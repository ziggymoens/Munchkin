package ui.gui.ucs.usecase8;

import domein.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ui.gui.a_universal.maingui.MainGui;

import java.util.List;

public class UseCase8G extends MainGui {

    private ComboBox cboTitels;
    private Label lblNaam, lblBevestiging;
    private TextField txfNaam;
    private DomeinController dc;

    public UseCase8G(DomeinController dc) {
        getStylesheets().add("ui/gui/ucs/usecase8/UseCase8G.css");
        this.dc = dc;
        buildGui();
    }

    private void buildGui() {
        VBox box;
        box = new VBox();
        cboTitels = new ComboBox();
        cboTitels.setPromptText("Kies hier het spel dat u wilt opslaan");
        //mogelijkheden plaatsen voor spellen
        List<String> spelen = dc.geefOverzichtSpelen();
        ObservableList lijst = FXCollections.observableArrayList(spelen);  //FXCollections omdat je niet zomaar new ObservableList() kunt doen
        cboTitels.setItems(lijst);
        lblNaam = new Label("Geef het gekozen spel een naam: ");
        lblBevestiging = new Label();
        txfNaam = new TextField();
        txfNaam.setEditable(true);
        Button bevestig = new Button("bevestig");
        bevestig.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblBevestiging.setText("Het spel is succesvol opgeslaan.");
            }
        });
        box.getChildren().addAll(cboTitels, lblNaam, txfNaam, bevestig, lblBevestiging);
        this.getPane().setCenter(box);
        this.setPadding(new Insets(25));   //ruimte aan zijkanten
        //this.setSpacing(10); //tussenruimte tussen 2 componenten
    }

}

