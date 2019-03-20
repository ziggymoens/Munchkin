package ui.gui.usecase1;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import language.LanguageResource;
import ui.gui.MenuBarGUI;

import java.util.*;


public class UseCase1G extends BorderPane {
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

    private void layoutUC1(){
        vBox = new VBox();
        hBox = new HBox();
        HBox top = new HBox();
        Label label = new Label("Munchkin");
        label.setId("titel");
        MenuBarGUI menuBarGUI = new MenuBarGUI();
        top.getChildren().addAll(label, menuBarGUI);
        setTop(top);
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
    private void ButtonWelkomEventHandler(ActionEvent event){
        askTaal();
    }

    private void askTaal(){
        vBox.getChildren().clear();
        hBox.getChildren().clear();
        Label label = new Label();
        String labelText = "";
        for(String loc : talenLoc){
            labelText += (String.format("%s%n", LanguageResource.getStringLanguage("languageC", new Locale(loc))));
        }
        label.setText(labelText);

        //keuze vak voor talen
        choiceBoxTaal = new ChoiceBox<>();
        for (String taal : talen) {
            choiceBoxTaal.getItems().add(taal);
        }
        choiceBoxTaal.setValue(talen.get(0));

        //button in Hbox die taal selecteert
        Button button = new Button("next");
        button.setOnAction(this::ButtonTaalEventHandler);

        vBox.getChildren().add(label);

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
        LanguageResource.setLocale(locale);
        vBox.getChildren().clear();
        hBox.getChildren().clear();
        Label label = new Label();
        label.setText(String.format("%s: %s", LanguageResource.getStringLanguage("picked", locale), LanguageResource.getStringLanguage("language", locale)));
        getChildren().add(label);
        //vragen een nieuw spel te starten
        newGame();
    }

    private void newGame() {
        String yes = LanguageResource.getString("yes");
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

        vBox.getChildren().add(label);
        hBox.getChildren().addAll(choiceBoxNewGame, button);
    }

    private void ButtonNewGameEventHandler(ActionEvent event) {
        newGame = choiceBoxNewGame.getValue().equals(LanguageResource.getStringLanguage("yes", locale));
        vBox.getChildren().clear();
        hBox.getChildren().clear();
        if (newGame) {
            askPlayers();
        } else {
            Label label = new Label(LanguageResource.getStringLanguage("gamestop", locale));
            vBox.getChildren().add(label);
        }
    }

    private void askPlayers() {

        vBox.getChildren().clear();
        hBox.getChildren().clear();
        Label label = new Label(LanguageResource.getStringLanguage("amountOfPlayers", locale));
        as = new ChoiceBox<>();
        for (int i =3; i<=6;i++){
            as.getItems().add(i);
        }
        Button button = new Button(LanguageResource.getStringLanguage("pick", locale));
        button.setOnAction(this::ButtonPlayersEventHandler);
        vBox.getChildren().add(label);
        hBox.getChildren().addAll(as, button);
    }

    private void ButtonPlayersEventHandler(ActionEvent event) {
        dc.startSpel(as.getValue());
    }
}
