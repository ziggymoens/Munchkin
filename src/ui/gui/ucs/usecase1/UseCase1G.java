package ui.gui.ucs.usecase1;

import domein.DomeinController;
import exceptions.SpelException;
import exceptions.SpelerException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private HBox topHBox;
    private VBox centerVBox;
    private HBox bottomHBox;
    private VBox leftVBox;
    private VBox rightVBox;
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
    private ImageView buttonRight;
    private ImageView buttonLeft;

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
        topHBox = new HBox();
        centerVBox = new VBox();
        centerVBox.setSpacing(20);
        bottomHBox = new HBox();
        leftVBox = new VBox();
        rightVBox = new VBox();
        rightVBox.setPadding(new Insets(10));
        leftVBox.setPadding(new Insets(10));
        getPane().setTop(topHBox);
        getPane().setCenter(centerVBox);
        getPane().setBottom(bottomHBox);
        getPane().setLeft(leftVBox);
        getPane().setRight(rightVBox);
        initializeRightVBox();
        initializeLeftVBox();
    }

    private void initializeRightVBox() {
        Image bR = new Image("ui/images/Arrow-right.png");
        buttonRight = new ImageView();
        buttonRight.setImage(bR);
        buttonRight.setPreserveRatio(true);
        buttonRight.setFitWidth(100);
        buttonRight.setId("bR");
        rightVBox.getChildren().add(buttonRight);
    }

    private void initializeLeftVBox() {
        Image bL = new Image("ui/images/Arrow-right.png");
        buttonLeft = new ImageView();
        buttonLeft.setImage(bL);
        buttonLeft.setRotate(180);
        buttonLeft.setPreserveRatio(true);
        buttonLeft.setFitWidth(100);
        leftVBox.getChildren().add(buttonLeft);
        buttonLeft.setVisible(false);
    }


    private void welcome() {
        centerVBox.getChildren().clear();
        StringBuilder welkom = new StringBuilder();
        for (String loc : talenLoc) {
            welkom.append(String.format("%s%n", LanguageResource.getStringLanguage("welcome", new Locale(loc))));
        }
        Label welcomeTop = new Label("Munchkin - G35");
        welcomeTop.setId("welcomeTop");
        Label welcome = new Label(welkom.toString());
        welcome.setId("string");
        //button in Hbox die taal selecteert
        //Button button = new Button("→");
        buttonRight.setOnMouseClicked(this::ButtonWelkomEventHandler);
        //button.setId("buttonWelkom");

        topHBox.getChildren().add(welcomeTop);
        centerVBox.getChildren().add(welcome);

        //button toevoegen aan Hbox
        //bottomHBox.getChildren().addAll(button);
    }

    private void ButtonWelkomEventHandler(MouseEvent event) {
        askTaal();
    }

    private void askTaal() {
        centerVBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        Label label = new Label();
        label.setId("string");
        StringBuilder labelText = new StringBuilder();
        for (String loc : talenLoc) {
            labelText.append(String.format("%s%n", LanguageResource.getStringLanguage("languageC", new Locale(loc))));
        }

        label.setText(labelText.toString());

        HBox hBox = new HBox();

        ImageView nederlands = new ImageView(new Image("/ui/images/nederlands.png"));
        nederlands.setPreserveRatio(true);
        nederlands.setFitWidth(125);
        nederlands.setOnMouseClicked(Event -> {
            LanguageResource.setLocale(new Locale("nl"));
            buttonTaalEventHandler();
        });
        ImageView frans = new ImageView(new Image("/ui/images/frans.png"));
        frans.setPreserveRatio(true);
        frans.setFitWidth(125);
        frans.setOnMouseClicked(Event -> {
            LanguageResource.setLocale(new Locale("fr"));
            buttonTaalEventHandler();
        });
        ImageView engels = new ImageView(new Image("/ui/images/engels.png"));
        engels.setPreserveRatio(true);
        engels.setFitWidth(125);
        engels.setOnMouseClicked(Event -> {
            LanguageResource.setLocale(new Locale("en"));
            buttonTaalEventHandler();
        });

        //keuze vak voor talen
//        choiceBoxTaal = new ChoiceBox<>();
//        for (String taal : talen) {
//            choiceBoxTaal.getItems().add(taal);
//        }
//        choiceBoxTaal.setValue(talen.get(0));

        //button in Hbox die taal selecteert
        //Button button = new Button("→");

        //button.setOnAction(this::buttonTaalEventHandler);

        //buttonRight.setOnMouseClicked(this::buttonTaalEventHandler);

        hBox.getChildren().addAll(nederlands, engels, frans);
        hBox.setSpacing(25);
        centerVBox.getChildren().add(label);

        //button en choiceB toevoegen aan Hbox
        bottomHBox.getChildren().add(/*choiceBoxTaal*/hBox);
    }

    private void buttonTaalEventHandler(/*MouseEvent event*/) {
//        switch (choiceBoxTaal.getValue()) {
//            case "Nederlands":
//                locale = new Locale("nl");
//                break;
//            case "Français":
//                locale = new Locale("fr");
//                break;
//            case "English":
//            default:
//                locale = new Locale("en");
//                break;
//        }
//        LanguageResource.setLocale(locale);
        centerVBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        Label label = new Label("→");
        label.setId("string");
        label.setText(String.format("%s: %s", LanguageResource.getString("picked"), LanguageResource.getString("language")));
        getChildren().add(label);
        updateMenuLang();
        //vragen een nieuw spel te starten
        newGame();
    }

    private void newGame() {
        String yes = LanguageResource.getString("yes");
        String no = LanguageResource.getString("no");

        bottomHBox.getChildren().clear();
        centerVBox.getChildren().clear();

        Label label = new Label();
        label.setId("string");
        label.setText(LanguageResource.getString("newGame"));
        choiceBoxNewGame = new ChoiceBox<>();
        choiceBoxNewGame.getItems().addAll(yes, no);
        choiceBoxNewGame.setValue(yes);
        //Button button = new Button("→");
        //button.setOnAction(this::buttonNewGameEventHandler);
        buttonRight.setOnMouseClicked(this::buttonNewGameEventHandler);
        centerVBox.getChildren().add(label);
        bottomHBox.getChildren().addAll(choiceBoxNewGame);
    }

    private void buttonNewGameEventHandler(MouseEvent event) {
        newGame = choiceBoxNewGame.getValue().equals(LanguageResource.getString("yes"));
        centerVBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        if (newGame) {
            askPlayers();
        } else {
            Label label = new Label(LanguageResource.getString("gamestop"));
            label.setId("string");
            Button button = new Button(LanguageResource.getString("quit"));
            button.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Platform.exit();
                        }
                    }
            );
            bottomHBox.getChildren().add(button);
            centerVBox.getChildren().add(label);
        }
    }

    private void askPlayers() {
        centerVBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        Label label = new Label(LanguageResource.getString("amountOfPlayers"));
        label.setId("string");
        ImageView nr3 = new ImageView(new Image("/ui/images/nr3.png"));
        nr3.setPreserveRatio(true);
        nr3.setFitWidth(75);
        nr3.setOnMouseClicked(Event -> {
            aantalS = 3;
            buttonPlayersEventHandler();
        });
        ImageView nr4 = new ImageView(new Image("ui/images/nr4.png"));
        nr4.setPreserveRatio(true);
        nr4.setFitWidth(75);
        nr4.setOnMouseClicked(Event -> {
            aantalS = 4;
            buttonPlayersEventHandler();
        });
        ImageView nr5 = new ImageView(new Image("ui/images/nr5.png"));
        nr5.setPreserveRatio(true);
        nr5.setFitWidth(75);
        nr5.setOnMouseClicked(Event -> {
            aantalS = 5;
            buttonPlayersEventHandler();
        });
        ImageView nr6 = new ImageView(new Image("ui/images/nr6.png"));
        nr6.setPreserveRatio(true);
        nr6.setFitWidth(75);
        nr6.setOnMouseClicked(Event -> {
            aantalS = 6;
            buttonPlayersEventHandler();
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(nr3, nr4, nr5, nr6);
        hBox.setSpacing(15);

        //as = new ChoiceBox<>();
        //for (int i = 3; i <= 6; i++) {
        //    as.getItems().add(i);
        //}
        //as.setValue(3);
        //Button button = new Button(LanguageResource.getString("pick"));
        //button.setOnAction(this::buttonPlayersEventHandler);
        //buttonRight.setOnMouseClicked(this::buttonPlayersEventHandler);
        centerVBox.getChildren().add(label);
        bottomHBox.getChildren().add(hBox);
    }

    private void buttonPlayersEventHandler(/*MouseEvent event*/) {
        bottomHBox.getChildren().clear();
        centerVBox.getChildren().clear();
        printConfirmation();
        getMessages().setVisible(true);
        getMessages().setText(LanguageResource.getString("spel.made"));
        //Button button = new Button(LanguageResource.getString("next"));
        //button.setOnAction(this::buttonStartGame);
        buttonRight.setOnMouseClicked(this::buttonStartGame);
        //bottomHBox.getChildren().add(button);
        //aantalS = as.getValue();
        dc.startSpel(aantalS);
    }

    private void buttonStartGame(MouseEvent event) {
        centerVBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        getMessages().setText("");
        spelersToevoegen();
    }

    private void spelersToevoegen() {
        HBox naamveld = new HBox();
        naamveld.setSpacing(10);
        HBox geslachtveld = new HBox();
        geslachtveld.setSpacing(10);
        Label naam = new Label(LanguageResource.getString("player.name"));
        naamVeld = new TextField();
        naamVeld.setTooltip(new Tooltip(LanguageResource.getString("exception.speler.name")));
        naamVeld.setMinWidth(200);
        naamVeld.setMaxWidth(200);
        Label geslacht = new Label(LanguageResource.getString("player.sex"));
        choiceBoxGeslacht = new ChoiceBox<>();
        choiceBoxGeslacht.setMinWidth(200);
        choiceBoxGeslacht.setMaxWidth(200);
        choiceBoxGeslacht.getItems().addAll(LanguageResource.getString("man"), LanguageResource.getString("woman"));
        choiceBoxGeslacht.setValue(LanguageResource.getString("man"));
        //Button button = new Button(LanguageResource.getString("usecase1.make"));
        //button.setOnAction(this::spelerAanmaken);
        buttonRight.setOnMouseClicked(this::spelerAanmaken);
        centerVBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        nr = 0;
        labelSpeler = new Label(String.format("%s %d", LanguageResource.getString("player"), nr + 1));
        labelSpeler.setId("string");
        naamveld.getChildren().addAll(naam, this.naamVeld);
        geslachtveld.getChildren().addAll(geslacht, choiceBoxGeslacht);
        centerVBox.getChildren().addAll(labelSpeler, naamveld, geslachtveld);
        //bottomHBox.getChildren().add(button);
    }

    private void spelerAanmaken(MouseEvent event) {
        try {
            String naam = naamVeld.getText();
            String geslacht = choiceBoxGeslacht.getValue();
            if (!naam.equals("")) {
                dc.maakSpeler();
                dc.geefSpelerNaam(nr, naam);
            } else
                throw new SpelerException();
            dc.geefSpelerGeslacht(nr, geslacht);
            naamVeld.clear();
            nr++;
            if (nr < dc.geefAantalSpelers()) {
                labelSpeler.setText(String.format("%s %d", LanguageResource.getString("player"), nr + 1));
            }
            if (nr == dc.geefAantalSpelers()) {
                dc.geefStartKaarten();
                buttonRight.setOnMouseClicked(this::toonSpelOverzicht);
                centerVBox.getChildren().clear();
                bottomHBox.getChildren().clear();
            }
        } catch (SpelerException e) {
            printErrors();
            getMessages().setVisible(true);
            getMessages().setText(LanguageResource.getString("exception.speler.name"));
            visiblePause();
            event.consume();
        } catch (SpelException e) {
            printErrors();
            getMessages().setVisible(true);
            getMessages().setText(LanguageResource.getString("exception.spel.namenotunique"));
            visiblePause();
            event.consume();
        }

    }

    private void toonSpelOverzicht(MouseEvent event) {
        centerVBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        centerVBox.getChildren().add(new Label(dc.geefSpelsituatie().toString()));
    }
}