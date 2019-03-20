package ui.gui.usecase1;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import language.LanguageResource;

import java.util.*;


public class UseCase1G extends BorderPane {
    private DomeinController dc;
    private Locale locale = new Locale("en");
    private VBox vBox;
    private HBox hBox;
    private ChoiceBox<String> choiceBoxTaal;
    private List<String> talen;
    private boolean newGame;
    private ChoiceBox<String> choiceBoxNewGame;

    public UseCase1G(DomeinController dc) {
        this.dc = dc;
        talen = new ArrayList<>();
        talen.add("Nederlands");
        talen.add("Français");
        talen.add("English");

        Label label = new Label("Munchkin");
        label.setId("titel");



        //taal laten kiezen
        welcome();
        //LanguageResource.setLocale(locale);
        //vragen een nieuw spel te starten
        newGame();

        //css linken
        getStylesheets().add("ui/gui/usecase1/UseCase1G.css");
        //layout voor borderbox
        setTop(label);
        setCenter(vBox);
        setBottom(hBox);
    }

    private void welcome() {
        vBox = new VBox();
        vBox.getChildren().add(new Label("Welkom"));

        hBox = new HBox();

        //keuze vak voor talen
        choiceBoxTaal = new ChoiceBox<>();
        for (String taal : talen) {
            choiceBoxTaal.getItems().add(taal);
        }
        choiceBoxTaal.setValue(talen.get(0));

        //button in Hbox die taal selecteert
        Button button = new Button("next");
        button.setOnAction(this::ButtonTaalEventHandler);

        //button en choiceB toevoegen aan Hbox
        hBox.getChildren().addAll(choiceBoxTaal, button);
    }
//
//    private void ButtonOpenEventHandler(ActionEvent event){
//
//    }
//    private void ButtonSaveEventHandler(ActionEvent event){
//
//    }
//    private void ButtonExitEventHandler(ActionEvent event){
//
//    }

    private void ButtonTaalEventHandler(ActionEvent event) {
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
        //LanguageResource.setLocale(locale);
        vBox.getChildren().clear();
        Label label = new Label();
        label.setText(String.format("%s: %s", LanguageResource.getString("picked"), LanguageResource.getString("language")));
        vBox.getChildren().add(label);
    }

    private void newGame() {
        String yes = LanguageResource.getStringLanguage("yes", locale);
        String no = LanguageResource.getStringLanguage("no", locale);

        hBox.getChildren().clear();
        vBox.getChildren().clear();

        Label label = new Label();
        label.setText(LanguageResource.getStringLanguage("newGame", locale));
        choiceBoxNewGame = new ChoiceBox<>();
        choiceBoxNewGame.getItems().addAll(yes, no);
        choiceBoxNewGame.setValue(yes);
        Button button = new Button(LanguageResource.getStringLanguage("pick", locale));
        button.setOnAction(this::ButtonNewGameEventHandler);

        vBox.getChildren().addAll(label, choiceBoxNewGame, button);
    }

    private void ButtonNewGameEventHandler(ActionEvent event) {
        newGame = choiceBoxNewGame.getValue().equals(LanguageResource.getStringLanguage("yes", locale));
        vBox.getChildren().clear();
        if (newGame) {
            askPlayers();
        } else {
            Label label = new Label(LanguageResource.getStringLanguage("gamestop", locale));
            vBox.getChildren().add(label);
        }
    }

    private void askPlayers() {

        Label label = new Label(LanguageResource.getStringLanguage("amountOfPlayers", locale));
        TextArea as = new TextArea();
        Button button = new Button(LanguageResource.getStringLanguage("pick", locale));
        button.setOnAction(this::ButtonPlayersEventHandler);
        vBox.getChildren().addAll(label, as, button);
    }

    private void ButtonPlayersEventHandler(ActionEvent event) {

    }
}
