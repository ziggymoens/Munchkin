package ui.gui.ucs.usecase1;

import domein.DomeinController;
import exceptions.SpelException;
import exceptions.SpelerException;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import language.LanguageResource;
import main.StartUpGui;
import ui.gui.a_universal.TabExtended;
import ui.gui.a_universal.TabsMunchkin;
import ui.gui.a_universal.maingui.MainGui;
import ui.gui.game_interface.GameInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class UseCase1G extends MainGui {
    private DomeinController dc;
    private HBox topHBox;
    private VBox centerVBox;
    private HBox bottomHBox;
    private VBox leftVBox;
    private VBox rightVBox;
    private List<String> talen;
    private List<String> talenLoc;
    private int aantalS;
    private TextField naamVeld;
    private boolean maleClicked = false;
    private boolean femaleClicked = false;
    private int nr;
    private Label labelSpeler;
    private ImageView buttonRight;
    private ImageView buttonLeft;
    private MediaPlayer player;
    private Label naamError;

    public UseCase1G(DomeinController dc) {
        this.dc = dc;
        talen = new ArrayList<>();
        talen.add("Nederlands");
        talen.add("English");
        talen.add("Français");
        talenLoc = new ArrayList<>();
        talenLoc.add("nl");
        talenLoc.add("en");
        talenLoc.add("fr");

//        try {
//            Clip clip = AudioSystem.getClip();
//            AudioInputStream inputStream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/ui/music/backgroundmusic.mp3"));
//            clip.open(inputStream);
//            clip.start();
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (UnsupportedAudioFileException e) {
//            e.printStackTrace();
//        }

        //Media backgroundMusic = new Media(getClass().getResource("/ui/music/backgroundmusic.mp3").toExternalForm());
        //AudioInputStream audioInputStream = new AudioInputStream(backgroundMusic);
        //player = new MediaPlayer(backgroundMusic);
        //player.setVolume(0.8);
        layoutUC1();
        //taal laten kiezen
        welcome();
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
        buttonLeft.setId("bL");
        leftVBox.getChildren().add(buttonLeft);
        buttonLeft.setVisible(false);
    }


    private void welcome() {
        centerVBox.getChildren().clear();
        StringBuilder welkom = new StringBuilder();
        for (String loc : talenLoc) {
            welkom.append(String.format("%s %s%n", LanguageResource.getStringLanguage("welcome", new Locale(loc)), String.format("%s%s", System.getProperty("user.name").substring(0, 1).toUpperCase(), System.getProperty("user.name").substring(1))));
        }
        Label welcomeTop = new Label("Munchkin - G35");
        welcomeTop.setId("welcomeTop");
        Label welcome = new Label(welkom.toString());
        welcome.setId("string");
        //button in Hbox die taal selecteert
        buttonRight.setOnMouseClicked(this::ButtonWelkomEventHandler);

        topHBox.getChildren().add(welcomeTop);
        centerVBox.getChildren().add(welcome);
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
        String[] codes = {"nl", "en", "fr"};
        ImageView[] imageViews = new ImageView[3];
        for (int i = 0; i < 3; i++) {
            imageViews[i] = initFlag(talenLoc.get(i));
        }

        hBox.getChildren().addAll(imageViews);
        hBox.setSpacing(25);
        centerVBox.getChildren().add(label);
        //button en choiceB toevoegen aan Hbox
        bottomHBox.getChildren().add(/*choiceBoxTaal*/hBox);
    }

    private ImageView initFlag(String lang) {
        Image flag = new Image(String.format("/ui/images/usecase1/%s.png", LanguageResource.getStringLanguage(lang, new Locale("nl"))));
        ImageView vlag = new ImageView(flag);
        vlag.setPreserveRatio(true);
        vlag.setFitWidth(125);
        vlag.setOnMouseClicked(Event -> {
            setLocale(new Locale("taal"));
            ((TabExtended) TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).setLocale(new Locale(lang));
            buttonTaalEventHandler();
        });
        return vlag;
    }

    private void buttonTaalEventHandler() {
        centerVBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        buttonRight.setVisible(true);
        Label label = new Label("→");
        label.setId("string");
        label.setText(String.format("%s: %s", LanguageResource.getStringLanguage("picked", getLocale()), LanguageResource.getStringLanguage("language", getLocale())));
        centerVBox.getChildren().add(label);
        //vragen een nieuw spel te starten
        buttonRight.setOnMouseClicked(this::newGame);
    }

    private void newGame(MouseEvent event) {
        bottomHBox.getChildren().clear();
        centerVBox.getChildren().clear();
        buttonRight.setVisible(true);
        rightVBox.getChildren().add(new Label(LanguageResource.getStringLanguage("yes", getLocale())));
        rightVBox.setSpacing(15);
        buttonLeft.setVisible(true);
        leftVBox.getChildren().add(new Label(LanguageResource.getStringLanguage("no", getLocale())));
        leftVBox.setSpacing(15);
        Label label = new Label();
        label.setId("string");
        label.setText(LanguageResource.getStringLanguage("newGame", getLocale()));
        buttonRight.setOnMouseClicked(this::buttonNewGameEventHandler);
        buttonLeft.setOnMouseClicked(this::buttonQuitQame);
        centerVBox.getChildren().add(label);
    }

    private void buttonNewGameEventHandler(MouseEvent event) {
        popUpscherm("spel.made");
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
        Label label = new Label(LanguageResource.getStringLanguage("gamestop", getLocale()));
        label.setId("string");
        Button button = new Button(LanguageResource.getStringLanguage("quit", getLocale()));
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
        Label label = new Label(LanguageResource.getStringLanguage("amountOfPlayers", getLocale()));
        label.setId("string");

        ImageView[] imageViews = new ImageView[4];
        for (int i = 0; i < 4; i++) {
            imageViews[i] = initNumbers(i + 3);
        }
        HBox hBox = new HBox();
        hBox.getChildren().addAll(imageViews);
        hBox.setSpacing(15);
        centerVBox.getChildren().add(label);
        bottomHBox.getChildren().add(hBox);
    }

    private ImageView initNumbers(int i) {
        ImageView nr = new ImageView(new Image(String.format("/ui/images/usecase1/nr%d.png", i)));
        nr.setPreserveRatio(true);
        nr.setFitWidth(125);
        nr.setOnMouseClicked(Event -> {
            aantalS = i;
            dc.startSpel(aantalS);
            spelersToevoegen();
        });
        return nr;
    }

    private void spelersToevoegen() {
        bottomHBox.getChildren().clear();
        centerVBox.getChildren().clear();
        buttonRight.setVisible(true);
        //buttonRight.setOnMouseClicked(this::buttonStartGame);

        HBox naamveld = new HBox();
        naamveld.setSpacing(10);
        HBox geslachtveld = new HBox();
        geslachtveld.setSpacing(10);
        naamError = new Label();
        naamError.setTextFill(Color.web("#FF0000"));
        naamVeld = new TextField();
        naamVeld.setPromptText(LanguageResource.getStringLanguage("player.name", getLocale()));
        naamVeld.setAlignment(Pos.CENTER);
        naamVeld.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                naamError.setText("");
                naamError.setVisible(false);
            }
        });
        naamVeld.setTooltip(new Tooltip(LanguageResource.getStringLanguage("exception.speler.name", getLocale())));
        naamVeld.setMinWidth(200);
        naamVeld.setMaxWidth(200);
        ImageView male = new ImageView(new Image("/ui/images/usecase1/male-selected.png"));
        maleClicked = true;
        ImageView female = new ImageView(new Image("/ui/images/usecase1/female.png"));
        male.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                maleClicked = true;
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
        buttonRight.setOnMouseClicked(this::spelerAanmaken);
        centerVBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        nr = 0;
        labelSpeler = new Label(String.format("%s %d", LanguageResource.getStringLanguage("player", getLocale()), nr + 1));
        labelSpeler.setId("string");
        naamveld.getChildren().add(this.naamVeld);
        centerVBox.getChildren().addAll(labelSpeler, naamveld, naamError, geslachtveld);
    }

    private void spelerAanmaken(MouseEvent event) {
        LanguageResource.setLocale(getLocale());
        try {
            String naam = naamVeld.getText();
            String geslacht;
            if (maleClicked)
                geslacht = LanguageResource.getString("man");
            else {
                geslacht = LanguageResource.getString("woman");
            }
            if (!naam.equals("")) {
                dc.maakSpeler();
                dc.geefSpelerNaam(nr, naam);
            } else
                throw new SpelerException();
            dc.geefSpelerGeslacht(nr, geslacht);
            naamVeld.clear();
            nr++;
            if (nr < dc.geefAantalSpelers()) {
                System.out.println(nr);
                labelSpeler.setText(String.format("%s %d", LanguageResource.getStringLanguage("player", getLocale()), nr + 1));
            }
            if (nr == dc.geefAantalSpelers()) {
                toonSpelOverzicht();
            }
        } catch (SpelerException e) {
            naamError.setVisible(true);
            naamError.setText(LanguageResource.getStringLanguage("exception.speler.name", getLocale()));
            event.consume();
        } catch (SpelException e) {
            naamError.setVisible(true);
            naamError.setText(LanguageResource.getStringLanguage("exception.spel.namenotunique", getLocale()));
            event.consume();
        }
    }

    private void toonSpelOverzicht() {
        centerVBox.getChildren().clear();
        dc.geefStartKaarten();
        dc.controleerVolgorde();
        gaNaarSpel();
    }

    private void setLocale(Locale locale) {
        StartUpGui.menuBarGui.changeTextMenu(locale);
    }

    private Locale getLocale() {
        return ((TabExtended) TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).getLocale();
    }

    private void popUpscherm(String text) {
        Stage stage = new Stage();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new Label(LanguageResource.getStringLanguage(text, getLocale())));
        Scene scene = new Scene(borderPane, 200, 50);
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> stage.close());
        delay.play();
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    private void gaNaarSpel() {
        ((TabExtended) TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).setNewContent(new GameInterface(dc));
        StartUpGui.changeWindow(1900, 1050);
    }

}