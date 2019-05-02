package ui.gui.ucs.usecase1;

import domein.DomeinController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import language.LanguageResource;
import ui.gui.maingui.MainGui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class UseCase1G extends MainGui {
    private DomeinController dc;
    private Locale locale = new Locale("en");
    private VBox vBox;
    private HBox hBox;
    private ChoiceBox<String> choiceBoxTaal;
    private List<String> talen;
    private List<String> talenLoc;
    private boolean newGame;
    private ChoiceBox<String> choiceBoxNewGame;
    private ChoiceBox<Integer> as;
    private int aantalS;
    private TextField naamVeld;
    private ChoiceBox<String> choiceBoxGeslacht;
    private int nr;
    private Label labelSpeler;

    public UseCase1G(DomeinController dc) {
        this.dc = dc;
        talen = new ArrayList<>();
        talen.add("Nederlands");
        talen.add("Français");
        talen.add("English");
        talenLoc = new ArrayList<>();
        talenLoc.add("nl");
        talenLoc.add("en");
        talenLoc.add("fr");
        layoutUC1();
        //taal laten kiezen
        welcome();
        //LanguageResource.setLocale(locale);
        //vragen een nieuw spel te starten

        //css linken
        getStylesheets().add("ui/gui/ucs/usecase1/UseCase1G.css");
        //layout voor borderbox
    }

    private void layoutUC1() {
        vBox = new VBox();
        hBox = new HBox();
        setCenter(vBox);
        setBottom(hBox);
    }

    private void welcome() {
        vBox.getChildren().clear();
        StringBuilder welkom = new StringBuilder();
        for (String loc : talenLoc) {
            welkom.append(String.format("%s%n", LanguageResource.getStringLanguage("welcome", new Locale(loc))));
        }
        Label welcome = new Label(welkom.toString());
        welcome.setId("welcomeString");
        //button in Hbox die taal selecteert
        Button button = new Button("→");
        button.setOnAction(this::ButtonWelkomEventHandler);
        button.setId("buttonWelkom");


        vBox.getChildren().add(welcome);

        //button toevoegen aan Hbox
        hBox.getChildren().addAll(button);
    }

    private void ButtonWelkomEventHandler(ActionEvent event) {
        askTaal();

    }

    private void askTaal() {
        vBox.getChildren().clear();
        hBox.getChildren().clear();
        Label label = new Label();
        StringBuilder labelText = new StringBuilder();
        for (String loc : talenLoc) {
            labelText.append(String.format("%s%n", LanguageResource.getStringLanguage("languageC", new Locale(loc))));
        }
        label.setText(labelText.toString());

        //keuze vak voor talen
        choiceBoxTaal = new ChoiceBox<>();
        for (String taal : talen) {
            choiceBoxTaal.getItems().add(taal);
        }
        choiceBoxTaal.setValue(talen.get(0));

        //button in Hbox die taal selecteert
        Button button = new Button("→");

        button.setOnAction(this::buttonTaalEventHandler);

        vBox.getChildren().add(label);

        //button en choiceB toevoegen aan Hbox
        hBox.getChildren().addAll(choiceBoxTaal, button);
    }

    private void buttonTaalEventHandler(ActionEvent event) {
        switch (choiceBoxTaal.getValue()) {
            case "Nederlands":
                locale = new Locale("nl");
                break;
            case "Français":
                locale = new Locale("fr");
                break;
            case "English":
            default:
                locale = new Locale("en");
                break;
        }
        LanguageResource.setLocale(locale);
        vBox.getChildren().clear();
        hBox.getChildren().clear();
        Label label = new Label("→");
        label.setText(String.format("%s: %s", LanguageResource.getString("picked"), LanguageResource.getString("language")));
        getChildren().add(label);
        updateMenuLang();
        //vragen een nieuw spel te starten
        newGame();
    }

    private void newGame() {
        String yes = LanguageResource.getString("yes");
        String no = LanguageResource.getString("no");

        hBox.getChildren().clear();
        vBox.getChildren().clear();

        Label label = new Label();
        label.setText(LanguageResource.getString("newGame"));
        choiceBoxNewGame = new ChoiceBox<>();
        choiceBoxNewGame.getItems().addAll(yes, no);
        choiceBoxNewGame.setValue(yes);
        Button button = new Button("→");
        button.setOnAction(this::buttonNewGameEventHandler);

        vBox.getChildren().add(label);
        hBox.getChildren().addAll(choiceBoxNewGame, button);
    }

    private void buttonNewGameEventHandler(ActionEvent event) {
        newGame = choiceBoxNewGame.getValue().equals(LanguageResource.getString("yes"));
        vBox.getChildren().clear();
        hBox.getChildren().clear();
        if (newGame) {
            askPlayers();
        } else {
            Label label = new Label(LanguageResource.getString("gamestop"));
            Button button = new Button(LanguageResource.getString("quit"));
            button.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Platform.exit();
                        }
                    }
            );
            hBox.getChildren().add(button);
            vBox.getChildren().add(label);
        }
    }

    private void askPlayers() {

        vBox.getChildren().clear();
        hBox.getChildren().clear();
        Label label = new Label(LanguageResource.getString("amountOfPlayers"));
        as = new ChoiceBox<>();
        for (int i = 3; i <= 6; i++) {
            as.getItems().add(i);
        }
        as.setValue(3);
        Button button = new Button(LanguageResource.getString("pick"));
        button.setOnAction(this::buttonPlayersEventHandler);
        vBox.getChildren().add(label);
        hBox.getChildren().addAll(as, button);
    }

    private void buttonPlayersEventHandler(ActionEvent event) {
        hBox.getChildren().clear();
        vBox.getChildren().clear();
        vBox.getChildren().add(new Label(LanguageResource.getString("spel.made")));
        Button button = new Button(LanguageResource.getString("next"));
        button.setOnAction(this::buttonStartGame);
        hBox.getChildren().add(button);
        aantalS = as.getValue();
        dc.startSpel(aantalS);
        spelersToevoegen();
    }

    private void buttonStartGame(ActionEvent event) {
        vBox.getChildren().clear();
        hBox.getChildren().clear();
    }

    private void spelersToevoegen() {
        Label naam = new Label(LanguageResource.getString("player.name"));
        naamVeld = new TextField();
        Label geslacht = new Label(LanguageResource.getString("player.sex"));
        choiceBoxGeslacht = new ChoiceBox<>();
        choiceBoxGeslacht.getItems().addAll(LanguageResource.getString("man"), LanguageResource.getString("woman"));
        choiceBoxGeslacht.setValue(LanguageResource.getString("man"));
        Button button = new Button("select");
        button.setOnAction(this::spelerAanmaken);
        vBox.getChildren().clear();
        hBox.getChildren().clear();
        nr = 0;
        labelSpeler = new Label(String.format("%s %d", LanguageResource.getString("player"), nr + 1));
        vBox.getChildren().addAll(labelSpeler, naam, naamVeld);
        vBox.getChildren().addAll(geslacht, choiceBoxGeslacht);
        hBox.getChildren().add(button);
    }

    private void spelerAanmaken(ActionEvent event) {
        dc.maakSpeler();
        String naam = naamVeld.getText();
        String geslacht = choiceBoxGeslacht.getValue();
        dc.geefSpelerNaam(nr, naam);
        dc.geefSpelerGeslacht(nr, geslacht);
        naamVeld.clear();
        nr++;
        if (nr < dc.geefAantalSpelers()) {
            labelSpeler.setText(String.format("%s %d", LanguageResource.getString("player"), nr + 1));
        }
        if (nr == dc.geefAantalSpelers()) {
            vBox.getChildren().clear();
            hBox.getChildren().clear();
            dc.geefStartKaarten();
            toonSpelOverzicht();
        }

    }

    private void toonSpelOverzicht() {
        vBox.getChildren().add(new Label(dc.geefSpelsituatie().toString()));
    }
}