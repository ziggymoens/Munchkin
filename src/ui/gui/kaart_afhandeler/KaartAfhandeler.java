package ui.gui.kaart_afhandeler;

import domein.DomeinController;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;
import java.util.Map;

public class KaartAfhandeler {
    private DomeinController dc;
    private BorderPane center;
    private int kaart;
    private Map<String, Runnable> kaartAfhandelen;

    public KaartAfhandeler(DomeinController dc, BorderPane center) {
        this.dc = dc;
        this.center = center;
        this.kaart = dc.geefIdBovensteKaart();
        kaartAfhandelen = new HashMap<>();
        kaartAfhandelen.put("ConsumablesKerker", this::geenEffectKaart);
        kaartAfhandelen.put("Curse", this::curse);
        kaartAfhandelen.put("Monster", this::monster);
        kaartAfhandelen.put("Race", this::geenEffectKaart);
        kaartAfhandelen.put("ConsumablesSchat", this::geenEffectKaart);
        kaartAfhandelen.put("Equipment", this::geenEffectKaart);
        kaartAfhandelen.get(dc.geefTypeKaart(kaart)).run();
        initLayout();
    }

    private void initLayout(){
        center.setCenter(new Label("kaartenAfhandeler"));
    }

    private void monster() {

    }

    private void geenEffectKaart() {
        dc.geefKerkerkaartAanSpeler(dc.geefNaamSpeler(dc.geefSpelerAanBeurt()));
    }

    private void curse() {
        dc.curseKaart(dc.geefNaamSpeler(dc.geefSpelerAanBeurt()));
    }


}
