package ui.gui.game_interface;


import domein.DomeinController;
import domein.kaarten.Schatkaart;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import language.LanguageResource;
import ui.gui.a_universal.TabExtended;
import ui.gui.a_universal.TabsMunchkin;
import ui.gui.items_afhandeler.NaarItemsAfhandeler;
import ui.gui.kaart_afhandeler.KaartAfhandeler;
import ui.gui.ucs.usecase8.UseCase8G;
import ui.gui.verkoop_afhandeler.VerkoopAfhandeler;
import ui.gui.weggooi_afhandeler.WeggooiAfhandeler;

import java.util.*;

public class GameInterface extends BorderPane {
    private DomeinController dc;
    private VBox centerVBoxLeft;
    private VBox centerVBoxRight;
    private HBox centerBottomHBox;
    private HBox centerTopHBox;
    private HBox topHBox;
    private HBox bottomHBox;
    private VBox leftVBox;
    private VBox rightVBox;
    private VBox kaartenDeelHS;
    private VBox itemsDeelHS;
    private HBox kaartenHS;
    private HBox itemsHS;
    private List<BorderPane> spelers;
    private Map<Integer, Runnable> spelersLayOut;
    private BorderPane center;

    public GameInterface(DomeinController dc) {
        getStylesheets().add("/ui/gui/game_interface/GameInterface.css");
        this.dc = dc;
        setLayOut();
        spelersLayOut = new HashMap<>();
        spelersLayOut.put(3, this::spelers3);
        spelersLayOut.put(4, this::spelers4);
        spelersLayOut.put(5, this::spelers5);
        spelersLayOut.put(6, this::spelers6);
        spelers = new ArrayList<>();
        initSpelers();
        geefBeurtOpties();
    }

    private void changeWindowSize() {
    }

    private void spelers6() {
        bottomHBox.getChildren().add(spelers.get(0));
        leftVBox.getChildren().addAll(spelers.get(2), spelers.get(1));
        topHBox.getChildren().add(spelers.get(3));
        rightVBox.getChildren().addAll(spelers.get(4), spelers.get(5));
    }

    private void spelers5() {
        bottomHBox.getChildren().add(spelers.get(0));
        leftVBox.getChildren().addAll(spelers.get(2), spelers.get(1));
        rightVBox.getChildren().addAll(spelers.get(3), spelers.get(4));
    }

    private void spelers4() {
        bottomHBox.getChildren().add(spelers.get(0));
        leftVBox.getChildren().add(spelers.get(1));
        topHBox.getChildren().add(spelers.get(2));
        rightVBox.getChildren().add(spelers.get(3));
    }

    private void spelers3() {
        bottomHBox.getChildren().add(spelers.get(0));
        leftVBox.getChildren().add(spelers.get(1));
        rightVBox.getChildren().add(spelers.get(2));
        leftVBox.setAlignment(Pos.TOP_CENTER);
        rightVBox.setAlignment(Pos.TOP_CENTER);
    }

    private void setLayOut() {
        topHBox = new HBox();
        topHBox.setId("topHBox");
        topHBox.setMinHeight(200);
        topHBox.setMaxHeight(200);

        bottomHBox = new HBox();
        bottomHBox.setId("bottomHBox");
        bottomHBox.setMinHeight(200);
        bottomHBox.setMaxHeight(200);

        leftVBox = new VBox();
        leftVBox.setId("leftVBox");
        leftVBox.setMaxWidth(450);
        leftVBox.setMinWidth(450);

        rightVBox = new VBox();
        rightVBox.setId("rightVBox");
        rightVBox.setMaxWidth(450);
        rightVBox.setMinWidth(450);

        center = new BorderPane();
        center.setId("center");

        centerVBoxLeft = new VBox();
        centerVBoxLeft.setId("centerVBoxLeft");
        centerVBoxLeft.setMinWidth(125);
        centerVBoxLeft.setMaxWidth(125);
        Label vechtTegen = new Label("Vecht Tegen");
        vechtTegen.setId("vechtTegen");
        centerVBoxLeft.getChildren().add(vechtTegen);
        centerVBoxLeft.setAlignment(Pos.CENTER);
        centerVBoxLeft.setSpacing(15);

        centerVBoxRight = new VBox();
        centerVBoxRight.setId("centerVBoxRight");
        centerVBoxRight.setMinWidth(125);
        centerVBoxRight.setMaxWidth(125);
        Label helptMee = new Label("helpt mee");
        helptMee.setId("helptMee");
        centerVBoxRight.getChildren().add(helptMee);
        centerVBoxRight.setSpacing(15);
        centerVBoxRight.setAlignment(Pos.CENTER);

        centerBottomHBox = new HBox();
        centerBottomHBox.setId("centerBottomHBox");

        centerTopHBox = new HBox();
        centerTopHBox.setId("centerTopHBox");

        itemsDeelHS = new VBox();
        itemsDeelHS.setId("itemsDeelHS");
        kaartenDeelHS = new VBox();
        kaartenDeelHS.setId("kaartenDeelHS");
        itemsHS = new HBox();
        itemsHS.setId("itemsHS");
        itemsHS.setAlignment(Pos.CENTER);
        itemsHS.setSpacing(20);
        itemsHS.setMaxHeight(100);
        itemsHS.setMinHeight(100);
        kaartenHS = new HBox();
        kaartenHS.setId("kaartenHS");
        kaartenHS.setSpacing(20);
        kaartenHS.setAlignment(Pos.CENTER);
        kaartenHS.setMinHeight(100);
        kaartenHS.setMinHeight(100);
        Label kaartenLabel = new Label("Kaarten");
        kaartenLabel.setId("kaartenLabel");
        Label itemsLabel = new Label("Items");
        itemsLabel.setId("itemsLabel");
        itemsDeelHS.getChildren().addAll(itemsLabel, itemsHS);
        itemsDeelHS.setAlignment(Pos.CENTER);
        kaartenDeelHS.getChildren().addAll(kaartenLabel, kaartenHS);
        kaartenDeelHS.setAlignment(Pos.CENTER);
        itemsDeelHS.setMinHeight(125);
        itemsDeelHS.setMaxHeight(125);
        kaartenDeelHS.setMaxHeight(125);
        kaartenDeelHS.setMinHeight(125);

        centerBottomHBox.getChildren().addAll(kaartenDeelHS, itemsDeelHS);
        centerBottomHBox.setSpacing(250);
        centerBottomHBox.setAlignment(Pos.CENTER);

        center.setTop(centerTopHBox);
        center.setBottom(centerBottomHBox);
        //center.setLeft(centerVBoxLeft);
        //center.setRight(centerVBoxRight);
        center.setMinWidth(1000);
        center.setMaxWidth(1000);
        center.setMinHeight(550);
        center.setMaxHeight(550);

        topHBox.setAlignment(Pos.CENTER);
        bottomHBox.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.CENTER);
        leftVBox.setSpacing(30);
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.setSpacing(30);

        HBox.setMargin(leftVBox, new Insets(20, 20, 20, 20));
        HBox.setMargin(rightVBox, new Insets(20, 20, 20, 20));
        HBox.setMargin(bottomHBox, new Insets(20, 20, 20, 20));
        HBox.setMargin(topHBox, new Insets(20, 20, 20, 20));



        setTop(topHBox);
        setBottom(bottomHBox);
        setLeft(leftVBox);
        setRight(rightVBox);
        setCenter(center);
    }

    private void initSpelers() {
        int aantal = dc.geefAantalSpelers();
        int spelerAanBeurt = dc.geefSpelerAanBeurt();
        int teller = 0;
        for (int i = spelerAanBeurt; i < aantal; i++) {
            if (teller < aantal) {
                if (i != dc.geefSpelerAanBeurt()) {
                    spelers.add(maakSpelerKaart(i));
                } else {
                    spelers.add(maakSpelerKaart(i));
                    initKaartenHS();
                }
                if (i == aantal - 1) {
                    i = 0;
                }
                teller++;
            }
        }
        spelersLayOut.get(aantal).run();
    }

    private void initKaartenHS() {
        int[] kaarten = dc.geefKaartenVanSpelerInt(dc.geefNaamSpeler(dc.geefSpelerAanBeurt()));
        int[] items = dc.geefItemsVanSpelerInt(dc.geefNaamSpeler(dc.geefSpelerAanBeurt()));
        voegKaartenToeHS(kaarten, "k");
        voegKaartenToeHS(items, "i");
    }

    private void voegKaartenToeHS(int[] lijst, String type) {
        if (type.equals("i")) {
            for (Integer i : lijst) {
                ImageView imageView = new ImageView(new Image(String.format("/ui/images/kaarten/%d.png", i)));
                imageView.setFitWidth(50);
                imageView.setPreserveRatio(true);
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        kaartSchermpje(i);
                    }
                });
                itemsHS.getChildren().add(imageView);
            }
        } else {
            for (Integer i : lijst) {
                ImageView imageView = new ImageView(new Image(String.format("/ui/images/kaarten/%d.png", i)));
                imageView.setFitWidth(50);
                imageView.setPreserveRatio(true);
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        kaartSchermpje(i);
                    }
                });
                kaartenHS.getChildren().add(imageView);
            }
            //checkboxImages(lijst);
        }

    }

    private BorderPane maakSpelerKaart(int i) {
        BorderPane spelerKaart = new BorderPane();
        spelerKaart.setId("spelerKaart");
        spelerKaart.setMinWidth(400);
        spelerKaart.setMaxWidth(400);
        spelerKaart.setMaxHeight(175);
        spelerKaart.setMinHeight(175);
        Label naamSpeler = new Label();
        naamSpeler.setId("naamSpeler");
        naamSpeler.setText(dc.geefNaamSpeler(i));
        naamSpeler.setMinWidth(spelerKaart.getMinWidth());
        naamSpeler.setAlignment(Pos.CENTER);
        spelerKaart.setTop(naamSpeler);

        HBox kaartenSpeler = new HBox();
        kaartenSpeler.setId("kaartenSpeler");

        int[] items = dc.geefItemsVanSpelerInt(dc.geefNaamSpeler(i));
        //kaarten laden van de speler nog opvragen via DC
        for (int j = 0; j < items.length; j++) {
            int finalJ = items[j];
            ImageView imageView = new ImageView(new Image(String.format("/ui/images/kaarten/%d.png", finalJ)));
            imageView.setFitWidth(spelerKaart.getMinWidth() * 0.17);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    kaartSchermpje(finalJ);
                }
            });
            kaartenSpeler.getChildren().add(imageView);
        }
        kaartenSpeler.setSpacing(10);
        kaartenSpeler.setMinHeight(125);
        kaartenSpeler.setAlignment(Pos.CENTER);
        spelerKaart.setCenter(kaartenSpeler);

        HBox bottomSpelerHBox = new HBox();
        bottomSpelerHBox.setId("bottomSpelerHBox");
        Label niveauSpeler = new Label();
        niveauSpeler.setId("niveauSpeler");
        niveauSpeler.setMinWidth(spelerKaart.getMinWidth() * 0.25);
        Label sexSpeler = new Label();
        sexSpeler.setId("sexSpeler");
        sexSpeler.setMinWidth(spelerKaart.getMinWidth() * 0.25);
        Label raceSpeler = new Label();
        raceSpeler.setId("raceSpeler");
        raceSpeler.setMinWidth(spelerKaart.getMinWidth() * 0.25);
        niveauSpeler.setText(String.format("%d", dc.geefLevel(i)));
        //opvragen via DC
        sexSpeler.setText(dc.geefGeslachtSpeler(i));
        //opvragen via DC
        raceSpeler.setText(dc.geefRaceSpeler(i));

        bottomSpelerHBox.getChildren().addAll(niveauSpeler, sexSpeler, raceSpeler);
        bottomSpelerHBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomSpelerHBox.setBorder(new Border(new BorderStroke(Paint.valueOf("BLACK"), BorderStrokeStyle.DASHED, new CornerRadii(10), BorderWidths.DEFAULT)));
        spelerKaart.setBottom(bottomSpelerHBox);

        spelerKaart.setBorder(new Border(new BorderStroke(Paint.valueOf("BLACK"), BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));

        return spelerKaart;
    }

    private void legBovensteKaart(Event event) {
        VBox bovensteKaart = new VBox();
        bovensteKaart.setMinWidth(center.getMinWidth()-centerVBoxRight.getMinWidth()*2);
        bovensteKaart.setSpacing(20);
        bovensteKaart.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(dc.toonBovensteKk()));
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);
        Button button = new Button(LanguageResource.getStringLanguage("usecase3.confirm", getLocale()));
        GameInterface gameInterface = this;
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new KaartAfhandeler(dc, center, gameInterface);

            }
        });
        bovensteKaart.getChildren().addAll(imageView, button);
        center.setCenter(bovensteKaart);
    }

    private void geefBeurtOpties() {
        Button speelBeurt = new Button(LanguageResource.getStringLanguage("usecase2.turn.play", getLocale()));
        Button opslaan = new Button(LanguageResource.getStringLanguage("usecase2.turn.save", getLocale()));
        Button stoppen = new Button(LanguageResource.getStringLanguage("usecase2.turn.stop", getLocale()));
        speelBeurt.setOnAction(this::legBovensteKaart);
        opslaan.setOnAction(this::opslaan);
        stoppen.setOnAction(this::stoppen);
        speelBeurt.setMinWidth(center.getMinWidth()*0.4);
        opslaan.setMinWidth(center.getMinWidth()*0.4);
        stoppen.setMinWidth(center.getMinWidth()*0.4);
        VBox opties = new VBox();
        opties.getChildren().addAll(speelBeurt, opslaan, stoppen);
        opties.setSpacing(30);
        opties.setAlignment(Pos.CENTER);
        center.setCenter(opties);
    }

    private void stoppen(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(LanguageResource.getStringLanguage("close", getLocale()));
        alert.setHeaderText(LanguageResource.getStringLanguage("closeconfirm", getLocale()));
        alert.setContentText(String.format("%s%n%s", LanguageResource.getStringLanguage("closetext1", getLocale()), LanguageResource.getStringLanguage("closetext2", getLocale())));
        Optional<ButtonType> antwoord = alert.showAndWait();
        if (antwoord.get() == ButtonType.CANCEL) {
            event.consume();
        } else {
            Platform.exit();
            System.exit(0);
        }
    }

    private void opslaan(ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = new Scene(new UseCase8G(dc), 450, 300);
        stage.setScene(scene);
        stage.setTitle(String.format("Munchkin - G35 - %s", LanguageResource.getStringLanguage("save", getLocale())));
        stage.show();
        stage.setResizable(false);
    }

    private Locale getLocale() {
        return ((TabExtended) TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).getLocale();
    }

    private void kaartSchermpje(int finalJ) {
        BorderPane pane = new BorderPane();
        Label label = new Label(dc.geefTypeKaart(finalJ));
        label.setAlignment(Pos.CENTER);
        label.setMinWidth(450);
        pane.setTop(label);
        pane.getTop().setStyle("-fx-text-alignment: center; -fx-min-height: 20px; -fx-font: bold 30 \"sans-serif\";");
        HBox center = new HBox();
        ImageView imageView1 = new ImageView(new Image(String.format("/ui/images/kaarten/%d" +
                ".png", finalJ)));
        imageView1.setFitWidth(150);
        imageView1.setPreserveRatio(true);
        center.getChildren().add(imageView1);
        VBox info = new VBox();
        info.getChildren().addAll(new Label(dc.spel.kaarten.get(finalJ).getNaam()), new Label(String.format("%d", finalJ)), dc.spel.kaarten.get(finalJ) instanceof Schatkaart? new Label(String.format("%d",((Schatkaart)dc.spel.kaarten.get(finalJ)).getWaarde())):new Label());
        info.setSpacing(30);
        info.setAlignment(Pos.CENTER);
        center.getChildren().add(info);
        center.setSpacing(30);
        center.setAlignment(Pos.CENTER);
        pane.setCenter(center);
        Stage stage = new Stage();
        Scene scene = new Scene(pane, 450, 300);
        stage.setScene(scene);
        stage.setTitle(String.format("Munchkin - G35 - Kaart - INFO", LanguageResource.getString("load")));
        stage.show();
        stage.setResizable(true);
    }

    private void checkboxImages(int[] ids){
        kaartenHS.getChildren().clear();
        for (int id : ids){
            VBox image = new VBox();
            CheckBox checkBox = new CheckBox();
            ImageView imageView = new ImageView(new Image(String.format("/ui/images/kaarten/%d.png", id)));
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(center.getMinHeight()*0.10);
            checkBox.setGraphic(imageView);
            checkBox.setId("chimg");
            Button info = new Button("info");
            info.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    kaartSchermpje(id);
                }
            });
            image.getChildren().addAll(checkBox, info);
            image.setSpacing(5);
            image.setMinWidth(kaartenDeelHS.getMinWidth()*0.15);
            image.setAlignment(Pos.CENTER);
            kaartenHS.getChildren().add(image);
        }
    }

    public void kaartGespeeld(){
        VBox buttons = new VBox();
        buttons.setMinWidth(center.getMinWidth()*0.6);
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER);
        Button verkopen = new Button(LanguageResource.getStringLanguage("usecase7.action2", getLocale()));
        Button naarItems = new Button(LanguageResource.getStringLanguage("usecase7.action1", getLocale()));
        Button niks = new Button(LanguageResource.getStringLanguage("usecase7.action3", getLocale()));
        Locale locale = getLocale();
        GameInterface gameInterface = this;
        verkopen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button verkopen2 = new Button(LanguageResource.getStringLanguage("usecase7.translationsell", locale));
                Button weggooien = new Button(LanguageResource.getStringLanguage("usecase7.translationthrow", locale));
                verkopen2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Scene scene = new Scene(new VerkoopAfhandeler(dc, gameInterface, stage));
                        stage.setScene(scene);
                        stage.show();
                    }
                });
                weggooien.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Scene scene = new Scene(new WeggooiAfhandeler(dc, gameInterface, stage));
                        stage.setScene(scene);
                        stage.show();
                    }
                });
                buttons.getChildren().clear();
                buttons.getChildren().addAll(verkopen2, weggooien);
                center.setCenter(buttons);
            }
        });
        naarItems.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                Scene scene = new Scene(new NaarItemsAfhandeler(dc, gameInterface, stage));
                stage.setScene(scene);
                stage.show();
            }
        });
        niks.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nieuweInterface();
            }
        });
        buttons.getChildren().addAll(verkopen, naarItems, niks);
        center.setCenter(buttons);
    }

    public void nieuweInterface(){

        dc.zetSpelerAanBeurt(dc.geefSpelerAanBeurt()+1);
        if(dc.geefSpelerAanBeurt() == dc.geefAantalSpelers()){
            dc.zetSpelerAanBeurt(0);
        }

        dc.nieuweBovensteKaartK();
        ((TabExtended) TabsMunchkin.getPane().getSelectionModel().getSelectedItem()).setNewContent(new GameInterface(dc));
    }
}