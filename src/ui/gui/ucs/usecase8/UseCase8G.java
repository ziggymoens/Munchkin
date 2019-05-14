package ui.gui.ucs.usecase8;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ui.gui.a_universal.maingui.MainGui;

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
        lblNaam = new Label("Geef het spel een naam: ");
        lblBevestiging = new Label();
        txfNaam = new TextField();
        txfNaam.setEditable(true);
        Button bevestig = new Button("bevestig");
        bevestig.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblBevestiging.setText("Het spel is succesvol opgeslaan.");
                dc.spelOpslaan();
            }
        });
        box.getChildren().addAll(lblNaam, txfNaam, bevestig, lblBevestiging);
        this.getPane().setCenter(box);
        this.setPadding(new Insets(25));   //ruimte aan zijkanten
        //this.setSpacing(10); //tussenruimte tussen 2 componenten
    }

}

