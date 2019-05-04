package ui.gui.game_interface;

import domein.DomeinController;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ui.gui.a_universal.maingui.MainGui;

import java.util.HashMap;
import java.util.Map;

public class GameInterface extends MainGui {
    private DomeinController dc;
    private GridPane player1;
    private GridPane player2;
    private GridPane player3;
    private GridPane player4;
    private GridPane player5;
    private GridPane player6;
    private GridPane[] spelers3 = {player1, player3, player5};
    private GridPane[] spelers4 = {player1, player3, player4, player5};
    private GridPane[] spelers5 = {player1, player3, player4, player5, player6};
    private GridPane[] spelers6 = {player1, player2, player3, player4, player5, player6};
    private Map<Integer, GridPane[]> spelersLayOut;
    private BorderPane center;
    private HBox kaarten;

    public GameInterface(DomeinController dc) {
        this.dc = dc;
        setLayOut();
        spelersLayOut = new HashMap<>();
        spelersLayOut.put(3, spelers3);
        spelersLayOut.put(4, spelers4);
        spelersLayOut.put(5, spelers5);
        spelersLayOut.put(6, spelers6);

    }

    private void setLayOut() {
        HBox topHBox = new HBox();
        HBox bottomHBox = new HBox();
        center = new BorderPane();
        kaarten = new HBox();
        center.setBottom(kaarten);
        player1 = new GridPane();
        player2 = new GridPane();
        player3 = new GridPane();
        player4 = new GridPane();
        player5 = new GridPane();
        player6 = new GridPane();
        bottomHBox.getChildren().addAll(player2, player1, player6);
        topHBox.getChildren().addAll(player3, player4, player5);
        setTop(topHBox);
        setBottom(bottomHBox);
        setCenter(center);
    }

    public void zetSpelers() {
        int aantal = dc.geefAantalSpelers();
        int aanBeurt = dc.geefSpelerAanBeurt();
        for (GridPane gp : spelersLayOut.get(aantal)) {
            if (aanBeurt <= aantal){
                aanBeurt = 0;
            }
            gp.add(new Label(dc.geefNaamSpeler(aanBeurt)), 1, 1);
        }
    }


}
