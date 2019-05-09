package ui.gui.kaart_afhandeler;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import language.LanguageResource;
import java.util.HashMap;
import java.util.Map;

public class KaartAfhandeler extends BorderPane{
    private DomeinController dc;
    private BorderPane center;
    private int kaart;
    private Map<String, Runnable> kaartAfhandelen;
    private boolean help;
    private int spelerAanBeurt = dc.geefSpelerAanBeurt();

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
        //this.getChildren().clear();
        Stage stage = new Stage();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 500,500);
        stage.setTitle("Monster");
        Label vraag = new Label(LanguageResource.getString("usecase4.ask.help"));
        Button btnJa = new Button(LanguageResource.getString("yes"));
        Button btnNee = new Button(LanguageResource.getString("no"));
        btnJa.setOnAction(this::wiltHulp);
        btnNee.setOnAction(this::wiltGeenHulp);
        borderPane.setTop(vraag);
        borderPane.setLeft(btnNee);
        borderPane.setRight(btnJa);
        stage.setScene(scene);
        stage.show();
        //toonKaartenVanSpeler(borderPane);
    }

    private void geenEffectKaart() {
        dc.geefKerkerkaartAanSpeler(dc.geefNaamSpeler(dc.geefSpelerAanBeurt()));
    }

    private void curse() {
        dc.curseKaart(dc.geefNaamSpeler(dc.geefSpelerAanBeurt()));
    }

    private void wiltHulp(ActionEvent event){
        help = true;
    }

    private void wiltGeenHulp(ActionEvent event){
        help = false;
    }

   private void toonKaartenVanSpeler(BorderPane borderPane ){
        //borderPane.getChildren().clear();
        dc.geefIDKaartenInHand(dc.geefNaamSpeler(spelerAanBeurt));
        //ImageView ivKaart = new ImageView(new )

    }

}
