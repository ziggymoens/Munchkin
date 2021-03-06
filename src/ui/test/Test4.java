package ui.test;

import domein.DomeinController;
import domein.Speler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import language.LanguageResource;
import ui.gui.game_interface.GameInterface;
import ui.gui.items_afhandeler.NaarItemsAfhandeler;

import java.util.Locale;

public class Test4 extends Application {
    /**
     *Strartup methode test4
     * @param args startup args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *start methode
     * @param primaryStage de primaire stage
     */
    @Override
    public void start(Stage primaryStage) {
        LanguageResource.setLocale(new Locale("nl"));
        DomeinController dc = new DomeinController();
        dc.laadSpelRepo();
        dc.startSpel(6);
        for (int i = 0; i < 6; i++) {
            dc.maakSpeler();
            char c = (char) (i+65);
            dc.geefSpelerNaam(i, String.format("speler%c", c));
            dc.geefSpelerGeslacht(i, String.format("%s", i%2==0? LanguageResource.getString("man"):LanguageResource.getString("woman")));
        }
        dc.controleerVolgorde();
        dc.geefStartKaarten();
        for (Speler speler:dc.spel.getSpelers()){
            speler.updateItems();
            speler.updateKaarten();
        }
        dc.spel.updateVolgorde();
        dc.zetSpelerAanBeurt(0);
        BorderPane bp = new BorderPane();
        GameInterface gm = new GameInterface(dc);
        Stage stage = new Stage();
        Scene scene = new Scene(new NaarItemsAfhandeler(dc, gm, stage));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
