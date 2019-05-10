package ui.gui.ucs.usecase8;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ui.gui.a_universal.maingui.MainGui;

public class UseCase8G extends MainGui {

        private ComboBox cboTitels;
        private Label lblSpelgegevens, lblNaam, lblBevestiging, lblNm;
        private TextArea txaSpelgegevens;
        private TextField txfNaam, txfBevestiging;
        private DomeinController dc;
        private String naam;

        public UseCase8G(DomeinController dc) {
            getStylesheets().add("ui/gui/ucs/usecase8/UseCase8G.css");
            this.dc = dc;
            buildGui();
        }

        private void buildGui(){
            VBox box;
            box = new VBox();
            cboTitels = new ComboBox();
            //mogelijkheden plaatsen voor spellen
            //als standaardtekst in combobox, kies het spel dat u wilt opslaan
            lblNaam = new Label("Geef het spel dat u wilt opslaan een naam: ");
            txfNaam = new TextField();
            txfNaam.setEditable(true);
            Button bevestig = new Button("bevestig");
            bevestig.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    naam = txfNaam.getText();
                }
            });
            lblNm = new Label(naam);

            box.getChildren().addAll(cboTitels,lblNaam, txfNaam,bevestig,lblNm);
            this.getPane().setLeft(box);
        }

   /*     private void buildGui () {

            cboTitels = new ComboBox();
            lblSpelgegevens = new Label("Spelgegevens: ");
            lblNaam = new Label("Geef het spel dat u wilt opslaan een naam: ");
            lblBevestiging = new Label("");
            txaSpelgegevens = new TextArea();
            txfNaam = new TextField();
            txfBevestiging = new TextField();

            this.getChildren().addAll(cboTitels, lblSpelgegevens, txaSpelgegevens, lblNaam, txfNaam, lblBevestiging, txfBevestiging);

            this.setPadding(new Insets(25));   //ruimte aan zijkanten
//        this.setSpacing(10); //tussenruimte tussen 2 componenten

            // combobox opvullen: korte omschrijving spel
            List<String> spellen = new ArrayList<>();
//        spellen.add(dc.geefSpelNaam(naam));
            ObservableList lijst = FXCollections.observableArrayList(spellen);  //FXCollections omdat je niet zomaar new ObservableList() kunt doen
            cboTitels.setItems(lijst);
            cboTitels.setPromptText("Kies hier welk spel je wilt opslaan ");


            //tekst in TextArea en TextField (niet) wijzigbaar maken
            txaSpelgegevens.setEditable(false);
            txfNaam.setEditable(true);
            txfBevestiging.setEditable(false);

            // eventhandeler voor combobox
            cboTitels.setOnAction(new KlasseDieOpComboBoxReageert(cboTitels, this));         //this meegeven voor klasse

        }*/

        public void update ( int index){
//        String spelgegevens = dc.;
//        //String kost = String.format("De kost bedraagt €%.2f", dc.geefBeheerskostWaarde(index));
//        String txaSpelgegevens.setText(spelgegevens);
//        //txfKost.setText(kost);
//        String bevestiging = String.format("U heeft het spel %s onder de naam %s succesvol opgeslaan.");
//
//    }

        }


}

