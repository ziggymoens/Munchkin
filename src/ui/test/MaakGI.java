package ui.test;

import domein.DomeinController;
import domein.Speler;
import javafx.scene.control.TabPane;
import language.LanguageResource;
import ui.gui.a_universal.TabExtended;
import ui.gui.a_universal.TabsMunchkin;
import ui.gui.game_interface.GameInterface;

import java.security.SecureRandom;
import java.util.Locale;

public class MaakGI {
    public MaakGI(int aantal, Locale taal, boolean items, boolean levels) {
        DomeinController dc = new DomeinController();
        dc.laadSpelRepo();
        dc.startSpel(aantal);
        for (int i = 0; i < 6; i++) {
            dc.maakSpeler();
            char c = (char) (i + 65);
            dc.geefSpelerNaam(i, String.format("speler%c", c));
            dc.geefSpelerGeslacht(i, String.format("%s", i % 2 == 0 ? LanguageResource.getString("man") : LanguageResource.getString("woman")));
        }
        dc.controleerVolgorde();
        dc.geefStartKaarten();
        for (
                Speler speler : dc.spel.getSpelers()) {
            speler.updateItems();
            speler.updateKaarten();
        }
        dc.spel.updateVolgorde();
        dc.zetSpelerAanBeurt(0);
        if(items){
            for(Speler speler: dc.spel.getSpelers()){
                for (Integer kaart : speler.geefIdKaartenNaarItems()){
                    speler.items.add(dc.spel.kaarten.get(kaart));
                }
                for (Integer kaart : speler.geefIdKaartenNaarItems()){
                    speler.kaarten.remove(speler.kaarten.indexOf(dc.spel.kaarten.get(kaart)));
                }
            }
        }
        if(levels){
            SecureRandom rand = new SecureRandom();
            for(Speler speler : dc.spel.getSpelers()){
                speler.setLevel(rand.nextInt(9)+1);
            }
        }
        TabExtended tb = new TabExtended(dc);
        tb.setLocale(taal);
        tb.setNewContent(new GameInterface(dc));
        TabPane pane = TabsMunchkin.getPane();
        String[] stukken = pane.getTabs().get(pane.getTabs().size() - 1).getText().split(" ");
        tb.setText(String.format("Game %d", Integer.parseInt(stukken[1]) + 1));
        SecureRandom rand = new SecureRandom();
        tb.setStyle(String.format(" -fx-background-color: #%s;", Integer.toString(rand.nextInt(0X1000000), 16)));
        TabsMunchkin.addTab(tb);

    }
}
