package ui.gui.game_interface;


import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import language.LanguageResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameInterface extends BorderPane {
    private DomeinController dc;
    private BorderPane player1;
    private BorderPane player2;
    private BorderPane player3;
    private BorderPane player4;
    private BorderPane player5;
    private BorderPane player6;
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
    private BorderPane[] spelers3 = {player1, player3, player5};
    private BorderPane[] spelers4 = {player1, player3, player4, player5};
    private BorderPane[] spelers5 = {player1, player3, player4, player5, player6};
    private BorderPane[] spelers6 = {player1, player2, player3, player4, player5, player6};
    private Map<Integer, BorderPane[]> spelersLayOut;
    private BorderPane center;
    private HBox kaarten;

    public GameInterface(DomeinController dc) {
        getStylesheets().add("/ui/gui/game_interface/GameInterface.css");
        this.dc = dc;
        setLayOut();
        //spelersLayOut = new HashMap<>();
        //spelersLayOut.put(3, spelers3);
        //spelersLayOut.put(4, spelers4);
        //spelersLayOut.put(5, spelers5);
        //spelersLayOut.put(6, spelers6);
        spelers = new ArrayList<>();
        initSpelers();
        legBovensteKaart();
    }

    private void setLayOut() {
        topHBox = new HBox();
        topHBox.setId("topHBox");

        bottomHBox = new HBox();
        bottomHBox.setId("bottomHBox");

        leftVBox = new VBox();
        leftVBox.setId("leftVBox");

        rightVBox = new VBox();
        rightVBox.setId("rightVBox");

        center = new BorderPane();
        center.setId("center");

        centerVBoxLeft = new VBox();
        centerVBoxLeft.setId("centerVBoxLeft");
        Label vechtTegen = new Label("Vecht Tegen");
        vechtTegen.setId("vechtTegen");
        centerVBoxLeft.getChildren().add(vechtTegen);
        centerVBoxLeft.setAlignment(Pos.CENTER);
        centerVBoxLeft.setSpacing(15);

        centerVBoxRight = new VBox();
        centerVBoxRight.setId("centerVBoxRight");
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
        kaartenHS = new HBox();
        kaartenHS.setId("kaartenHS");
        kaartenHS.setSpacing(20);
        kaartenHS.setAlignment(Pos.CENTER);
        Label kaartenLabel = new Label("Kaarten");
        Label itemsLabel = new Label("Items");
        itemsDeelHS.getChildren().addAll(itemsLabel, itemsHS);
        itemsDeelHS.setAlignment(Pos.CENTER);
        kaartenDeelHS.getChildren().addAll(kaartenLabel, kaartenHS);
        kaartenDeelHS.setAlignment(Pos.CENTER);

        centerBottomHBox.getChildren().addAll(kaartenDeelHS, itemsDeelHS);
        centerBottomHBox.setSpacing(250);
        centerBottomHBox.setAlignment(Pos.CENTER);

        center.setTop(centerTopHBox);
        center.setBottom(centerBottomHBox);
        center.setLeft(centerVBoxLeft);
        center.setRight(centerVBoxRight);

        setTop(topHBox);
        setBottom(bottomHBox);
        setLeft(leftVBox);
        setRight(rightVBox);
        setCenter(center);
    }

    private void initSpelers() {
        int aantal = dc.geefAantalSpelers();
        for (int i = 0; i < 6; i++) {
            if (i != dc.geefSpelerAanBeurt()) {
                spelers.add(maakSpelerKaart(i));
            } else {
                spelers.add(maakSpelerKaart(i));
                initKaartenHS();
            }
        }
        zetSpelersOpScherm();
    }

    private void zetSpelersOpScherm(){
        bottomHBox.getChildren().add(spelers.get(0));
        bottomHBox.setAlignment(Pos.CENTER);
        leftVBox.getChildren().addAll(spelers.get(1), spelers.get(2));
        leftVBox.setSpacing(200);
        leftVBox.setAlignment(Pos.CENTER);
        topHBox.getChildren().add( spelers.get(3));
        topHBox.setAlignment(Pos.CENTER);
        rightVBox.getChildren().addAll(spelers.get(4), spelers.get(5));
        rightVBox.setSpacing(200);
        rightVBox.setAlignment(Pos.CENTER);
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
                itemsHS.getChildren().add(imageView);
            }
        } else {
            for (Integer i : lijst) {
                ImageView imageView = new ImageView(new Image(String.format("/ui/images/kaarten/%d.png", i)));
                imageView.setFitWidth(50);
                imageView.setPreserveRatio(true);
                kaartenHS.getChildren().add(imageView);
            }
        }
    }

    private BorderPane maakSpelerKaart(int i) {
        BorderPane spelerKaart = new BorderPane();
        Label naamSpeler = new Label();
        naamSpeler.setId("naamSpeler");
        naamSpeler.setText("test123");
        naamSpeler.setAlignment(Pos.CENTER);
        spelerKaart.setTop(naamSpeler);

        HBox kaartenSpeler = new HBox();
        kaartenSpeler.setId("kaartenSpeler");
        for (int j = 1; j < 6; j++) {
            ImageView imageView = new ImageView(new Image(String.format("/ui/images/kaarten/%d.png", j)));
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
            kaartenSpeler.getChildren().add(imageView);
        }
        kaartenSpeler.setSpacing(10);
        spelerKaart.setCenter(kaartenSpeler);

        HBox bottomSpelerHBox = new HBox();
        bottomSpelerHBox.setId("bottomSpelerHBox");
        Label niveauSpeler = new Label();
        niveauSpeler.setId("niveauSpeler");
        Label sexSpeler = new Label();
        sexSpeler.setId("sexSpeler");
        Label raceSpeler = new Label();
        raceSpeler.setId("raceSpeler");
        niveauSpeler.setText("1");
        sexSpeler.setText(LanguageResource.getString("man"));
        raceSpeler.setText("dwarf");
        bottomSpelerHBox.getChildren().addAll(niveauSpeler, sexSpeler, raceSpeler);
        bottomSpelerHBox.setSpacing(20);
        bottomSpelerHBox.setAlignment(Pos.CENTER);
        bottomSpelerHBox.setBorder(new Border(new BorderStroke(Paint.valueOf("BLACK"), BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        spelerKaart.setBottom(bottomSpelerHBox);

        spelerKaart.setBorder(new Border(new BorderStroke(Paint.valueOf("BLACK"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        return spelerKaart;
    }

    private void legBovensteKaart() {
        ImageView imageView = new ImageView(new Image(dc.toonBovensteKk()));
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);
        center.setCenter(imageView);
    }
}
