package ui.gui.ucs.usecase1;

import domein.DomeinController;
import exceptions.SpelException;
import exceptions.SpelerException;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import language.LanguageResource;
import main.StartUpGuiV2;
import ui.gui.a_universal.TabExtended;
import ui.gui.a_universal.TabsMunchkin;
import ui.gui.a_universal.maingui.MainGui;

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
    private boolean maleClicked = false;
    private boolean femaleClicked = false;
    private int nr;
    private Label labelSpeler;
    private ImageView buttonRight;
    private ImageView buttonLeft;
    private MediaPlayer player;

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
        Media backgroundMusic  = new Media(getClass().getResource("/ui/music/backgroundmusic.mp3").toExternalForm());
        player = new MediaPlayer(backgroundMusic);
        player.setVolume(0.8);
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
        leftVBox.setId("leftVBox");
        rightVBox = new VBox();
        rightVBox.setId("rightVBox");
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
        Image bR = new Image("ui/images/usecase1/Arrow-right-2.png");
        buttonRight = new ImageView();
        buttonRight.setImage(bR);
        buttonRight.setPreserveRatio(true);
        buttonRight.setFitWidth(100);
        buttonRight.setId("bR");
        rightVBox.getChildren().add(buttonRight);
    }

    private void initializeLeftVBox() {
        Image bL = new Image("ui/images/usecase1/Arrow-right-2.png");
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
        buttonRight.setVisible(false);
        Label label = new Label();
        label.setId("string");
        StringBuilder labelText = new StringBuilder();
        for (String loc : talenLoc) {
            labelText.append(String.format("%s%n", LanguageResource.getStringLanguage("languageC", new Locale(loc))));
        }

        label.setText(labelText.toString());

        HBox hBox = new HBox();

        ImageView nederlands = new ImageView(new Image("/ui/images/usecase1/nederlands.png"));
        nederlands.setPreserveRatio(true);
        nederlands.setFitWidth(125);
        nederlands.setOnMouseClicked(Event -> {
            setLocale(new Locale("nl"));
            ((TabExtended)TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).setLocale(new Locale("nl"));
            buttonTaalEventHandler();
        });
        ImageView frans = new ImageView(new Image("/ui/images/usecase1/frans.png"));
        frans.setPreserveRatio(true);
        frans.setFitWidth(125);
        frans.setOnMouseClicked(Event -> {
            setLocale(new Locale("fr"));
            ((TabExtended)TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).setLocale(new Locale("fr"));
            buttonTaalEventHandler();
        });
        ImageView engels = new ImageView(new Image("/ui/images/usecase1/engels.png"));
        engels.setPreserveRatio(true);
        engels.setFitWidth(125);
        engels.setOnMouseClicked(Event -> {
            setLocale(new Locale("en"));
            ((TabExtended)TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).setLocale(new Locale("en"));
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
        buttonRight.setVisible(true);
        Label label = new Label("→");
        label.setId("string");
        label.setText(String.format("%s: %s", LanguageResource.getStringLanguage("picked", getLocale()), LanguageResource.getStringLanguage("language",getLocale())));
        centerVBox.getChildren().add(label);
        updateMenuLang();
        //vragen een nieuw spel te starten
        buttonRight.setOnMouseClicked(this::newGame);
    }

    private void newGame(MouseEvent event) {
        String yes = LanguageResource.getStringLanguage("yes",getLocale());
        String no = LanguageResource.getStringLanguage("no",getLocale());

        bottomHBox.getChildren().clear();
        centerVBox.getChildren().clear();
        buttonRight.setVisible(true);
        rightVBox.getChildren().add(new Label(LanguageResource.getStringLanguage("yes",getLocale())));
        rightVBox.setSpacing(15);
        buttonLeft.setVisible(true);
        leftVBox.getChildren().add(new Label(LanguageResource.getStringLanguage("no",getLocale())));
        leftVBox.setSpacing(15);
        Label label = new Label();
        label.setId("string");
        label.setText(LanguageResource.getStringLanguage("newGame",getLocale()));
//        choiceBoxNewGame = new ChoiceBox<>();
//        choiceBoxNewGame.getItems().addAll(yes, no);
//        choiceBoxNewGame.setValue(yes);
        //Button button = new Button("→");
        //button.setOnAction(this::buttonNewGameEventHandler);
        buttonRight.setOnMouseClicked(this::buttonNewGameEventHandler);
        buttonLeft.setOnMouseClicked(this::buttonQuitQame);
        centerVBox.getChildren().add(label);
        //bottomHBox.getChildren().addAll(choiceBoxNewGame);
    }

    private void buttonNewGameEventHandler(MouseEvent event) {
        bottomHBox.getChildren().clear();
        rightVBox.getChildren().remove(rightVBox.getChildren().get(1));
        leftVBox.getChildren().remove(leftVBox.getChildren().get(1));
        buttonLeft.setVisible(false);
        buttonRight.setVisible(false);
        askPlayers();
    }

    private void buttonQuitQame(MouseEvent event) {
        centerVBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        rightVBox.getChildren().clear();
        leftVBox.getChildren().clear();
        buttonLeft.setVisible(false);
        buttonRight.setVisible(false);
        Label label = new Label(LanguageResource.getStringLanguage("gamestop",getLocale()));
        label.setId("string");
        Button button = new Button(LanguageResource.getStringLanguage("quit",getLocale()));
        button.setOnAction(
                event1 -> Platform.exit()
        );
        bottomHBox.getChildren().add(button);
        centerVBox.getChildren().add(label);
    }

    private void askPlayers() {
        centerVBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        buttonRight.setVisible(false);
        Label label = new Label(LanguageResource.getStringLanguage("amountOfPlayers",getLocale()));
        label.setId("string");
        ImageView nr3 = new ImageView(new Image("/ui/images/usecase1/nr3.png"));
        nr3.setPreserveRatio(true);
        nr3.setFitWidth(125);
        nr3.setOnMouseClicked(Event -> {
            aantalS = 3;
            dc.startSpel(aantalS);
            spelersToevoegen();
        });
        ImageView nr4 = new ImageView(new Image("ui/images/usecase1/nr4.png"));
        nr4.setPreserveRatio(true);
        nr4.setFitWidth(125);
        nr4.setOnMouseClicked(Event -> {
            aantalS = 4;
            dc.startSpel(aantalS);
            spelersToevoegen();
        });
        ImageView nr5 = new ImageView(new Image("ui/images/usecase1/nr5.png"));
        nr5.setPreserveRatio(true);
        nr5.setFitWidth(125);
        nr5.setOnMouseClicked(Event -> {
            aantalS = 5;
            dc.startSpel(aantalS);
            spelersToevoegen();
        });
        ImageView nr6 = new ImageView(new Image("ui/images/usecase1/nr6.png"));
        nr6.setPreserveRatio(true);
        nr6.setFitWidth(125);
        nr6.setOnMouseClicked(Event -> {
            aantalS = 6;
            dc.startSpel(aantalS);
            spelersToevoegen();
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(nr3, nr4, nr5, nr6);
        hBox.setSpacing(15);

        //as = new ChoiceBox<>();
        //for (int i = 3; i <= 6; i++) {
        //    as.getItems().add(i);
        //}
        //as.setValue(3);
        //Button button = new Button(LanguageResource.getStringLanguage("pick"));
        //button.setOnAction(this::buttonPlayersEventHandler);
        //buttonRight.setOnMouseClicked(this::buttonPlayersEventHandler);
        centerVBox.getChildren().add(label);
        bottomHBox.getChildren().add(hBox);
    }

//    private void buttonPlayersEventHandler(/*MouseEvent event*/) {
//        bottomHBox.getChildren().clear();
//        centerVBox.getChildren().clear();
//        buttonRight.setVisible(true);
//        buttonRight.setOnMouseClicked(this::buttonStartGame);
//        printConfirmation();
//        getMessages().setVisible(true);
//        getMessages().setText(LanguageResource.getStringLanguage("spel.made"));
//        visiblePause();
//        //Button button = new Button(LanguageResource.getStringLanguage("next"));
//        //button.setOnAction(this::buttonStartGame);
//
//        //bottomHBox.getChildren().add(button);
//        //aantalS = as.getValue();
//
//    }

//    private void buttonStartGame(MouseEvent event) {
//        centerVBox.getChildren().clear();
//        bottomHBox.getChildren().clear();
//        spelersToevoegen();
//    }

    private void spelersToevoegen() {
        bottomHBox.getChildren().clear();
        centerVBox.getChildren().clear();
        buttonRight.setVisible(true);
        //buttonRight.setOnMouseClicked(this::buttonStartGame);
        printConfirmation();
        getMessages().setVisible(true);
        getMessages().setText(LanguageResource.getStringLanguage("spel.made",getLocale()));
        visiblePause();
        HBox naamveld = new HBox();
        naamveld.setSpacing(10);
        HBox geslachtveld = new HBox();
        geslachtveld.setSpacing(10);
        Label naam = new Label(LanguageResource.getStringLanguage("player.name",getLocale()));
        naamVeld = new TextField();
        naamVeld.setAlignment(Pos.CENTER);
        naamVeld.setTooltip(new Tooltip(LanguageResource.getStringLanguage("exception.speler.name",getLocale())));
        naamVeld.setMinWidth(200);
        naamVeld.setMaxWidth(200);
        ImageView male = new ImageView(new Image("/ui/images/usecase1/male-selected.png"));
        maleClicked = true;
        ImageView female = new ImageView(new Image("/ui/images/usecase1/female.png"));
        male.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                maleClicked= true;
                femaleClicked = false;
                male.setImage(new Image("/ui/images/usecase1/male-selected.png"));
                female.setImage(new Image("ui/images/usecase1/female.png")
                );
            }
        });
        female.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                maleClicked = false;
                femaleClicked = true;
                male.setImage(new Image("/ui/images/usecase1/male.png"));
                female.setImage(new Image("ui/images/usecase1/female-selected.png")
                );
            }
        });

        geslachtveld.getChildren().addAll(male, female);


//        Label geslacht = new Label(LanguageResource.getStringLanguage("player.sex"));
//        choiceBoxGeslacht = new ChoiceBox<>();
//        choiceBoxGeslacht.setMinWidth(200);
//        choiceBoxGeslacht.setMaxWidth(200);
//        choiceBoxGeslacht.getItems().addAll(LanguageResource.getStringLanguage("man"), LanguageResource.getStringLanguage("woman"));
//        choiceBoxGeslacht.setValue(LanguageResource.getStringLanguage("man"));
        //Button button = new Button(LanguageResource.getStringLanguage("usecase1.make"));
        //button.setOnAction(this::spelerAanmaken);
        buttonRight.setOnMouseClicked(this::spelerAanmaken);
        centerVBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        nr = 0;
        labelSpeler = new Label(String.format("%s %d", LanguageResource.getStringLanguage("player",getLocale()), nr + 1));
        labelSpeler.setId("string");
        naamveld.getChildren().addAll(naam, this.naamVeld);
        //geslachtveld.getChildren().addAll(geslacht, choiceBoxGeslacht);
        centerVBox.getChildren().addAll(labelSpeler, naamveld, geslachtveld);
        //bottomHBox.getChildren().add(button);
    }

    private void spelerAanmaken(MouseEvent event) {
        try {
            String naam = naamVeld.getText();
            String geslacht;
            if (maleClicked){
                geslacht = LanguageResource.getStringLanguage("man",getLocale());
            }else{
                geslacht = LanguageResource.getStringLanguage("woman",getLocale());
            }
            //String geslacht = choiceBoxGeslacht.getValue();
            if (!naam.equals("")) {
                dc.maakSpeler();
                dc.geefSpelerNaam(nr, naam);
            } else
                throw new SpelerException();
            dc.geefSpelerGeslacht(nr, geslacht);
            naamVeld.clear();
            nr++;
            System.out.println(dc.geefAantalSpelers());
            if (nr < dc.geefAantalSpelers()) {
                System.out.println(nr);
                labelSpeler.setText(String.format("%s %d", LanguageResource.getStringLanguage("player",getLocale()), nr + 1));
            }
            if (nr == dc.geefAantalSpelers()) {
                dc.geefStartKaarten();
                toonSpelOverzicht();
                centerVBox.getChildren().clear();
                bottomHBox.getChildren().clear();
            }
        } catch (SpelerException e) {
            printErrors();
            getMessages().setVisible(true);
            getMessages().setText(LanguageResource.getStringLanguage("exception.speler.name",getLocale()));
            visiblePause();
            event.consume();
        } catch (SpelException e) {
            printErrors();
            getMessages().setVisible(true);
            getMessages().setText(LanguageResource.getStringLanguage("exception.spel.namenotunique", getLocale()));
            visiblePause();
            event.consume();
        }
    }

    private void toonSpelOverzicht() {
        centerVBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        centerVBox.getChildren().add(new Label(dc.geefSpelsituatie().toString()));
    }
    private void setLocale(Locale locale) {
        this.locale = locale;
        StartUpGuiV2.menuBarGui.changeTextMenu(locale);
    }
    private Locale getLocale(){
        return ((TabExtended)TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).getLocale();
    }
}