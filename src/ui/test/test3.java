package ui.test;

import domein.DomeinController;
import domein.Speler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import language.LanguageResource;
import ui.gui.game_interface.GameInterface;

import java.util.Locale;

public class test3 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

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
        Scene scene = new Scene(new GameInterface(dc));
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
