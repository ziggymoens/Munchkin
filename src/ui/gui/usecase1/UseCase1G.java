package ui.gui.usecase1;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import language.LanguageResource;
import ui.gui.maingui.MainGui;

import javax.swing.*;
import java.util.*;


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
    private TextArea naamVeld;
    private ChoiceBox<String> choiceBoxGeslacht;
    private int nr;

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
        getStylesheets().add("ui/gui/usecase1/UseCase1G.css");
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
        Label label = new Label("Welkom");

        //button in Hbox die taal selecteert
        Button button = new Button("next");
        button.setOnAction(this::ButtonWelkomEventHandler);

        vBox.getChildren().add(label);

        //button toevoegen aan Hbox
        hBox.getChildren().add(button);
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
        Button button = new Button("next");
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
        Label label = new Label();
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
        Button button = new Button(LanguageResource.getString("pick"));
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
        naamVeld = new TextArea();
        Label geslacht = new Label(LanguageResource.getString("player.sex"));
        choiceBoxGeslacht = new ChoiceBox<>();
        choiceBoxGeslacht.getItems().addAll(LanguageResource.getString("man"), LanguageResource.getString("woman"));
        Button button = new Button("select");
        button.setOnAction(this::spelerAanmaken);
        for (int i = 0; i < aantalS; i++) {
            nr = i;
            Label label = new Label(String.format("%s %d", LanguageResource.getString("player"), i+1));
            vBox.getChildren().add(label);
            vBox.getChildren().addAll(naam, naamVeld);
            vBox.getChildren().addAll(geslacht, choiceBoxGeslacht);
        }
    }

    private void spelerAanmaken(ActionEvent event) {
        String naam = naamVeld.getText();
        String geslacht = choiceBoxGeslacht.getValue();
        dc.geefSpelerNaam(nr, naam);
        dc.geefSpelerGeslacht(nr, geslacht);
        vBox.getChildren().clear();
        hBox.getChildren().clear();
    }
}
