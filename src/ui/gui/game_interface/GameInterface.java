package ui.gui.game_interface;


import domein.DomeinController;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ui.gui.a_universal.maingui.MainGui;

import java.util.HashMap;
import java.util.Map;

public class GameInterface extends MainGui {
    private DomeinController dc;
    private BorderPane player1 = new BorderPane();
    private BorderPane player2 = new BorderPane();
    private BorderPane player3 = new BorderPane();
    private BorderPane player4 = new BorderPane();
    private BorderPane player5 = new BorderPane();
    private BorderPane player6 = new BorderPane();
    private BorderPane[] spelers3 = {player1, player3, player5};
    private BorderPane[] spelers4 = {player1, player3, player4, player5};
    private BorderPane[] spelers5 = {player1, player3, player4, player5, player6};
    private BorderPane[] spelers6 = {player1, player2, player3, player4, player5, player6};
    private Map<Integer, BorderPane[]> spelersLayOut;
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
        //HBox topHBox = new HBox();
        //HBox bottomHBox = new HBox();
        center = new BorderPane();
        //kaarten = new HBox();
        center.setBottom(kaarten);
        //bottomHBox.getChildren().add(player1);
        //setRight(player6);
        //setLeft(player1);
        //topHBox.getChildren().addAll(player3, player4, player5);
        //setTop(topHBox);
        //setBottom(bottomHBox);
        center.setCenter(new Label("Game"));
        setCenter(center);
    }

    public void zetSpelers() {
        int i = 1;
        for (BorderPane bp : spelersLayOut.get(6)){
            Label label = new Label(String.format("%d", i));
            bp.setCenter(label);
            i++;
        }
    }


}
