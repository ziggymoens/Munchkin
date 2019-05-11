package ui.gui.kaart_afhandeler;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.LanguageResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KaartAfhandeler extends BorderPane {
    private DomeinController dc;
    private BorderPane center;
    private int kaart;
    private VBox centerVBox;
    private HBox keuzesCenter;
    private ImageView imageView;
    private Image image;
    private Map<String, Runnable> kaartAfhandelen;
    private boolean help;
    private Label textCenter;
    private int spelerAanBeurt;
    private List<Integer> gespeeldeKaarten = new ArrayList<>();

    public KaartAfhandeler(DomeinController dc, BorderPane center) {
        this.dc = dc;
        this.center = center;
        this.kaart = dc.geefIdBovensteKaart();
        spelerAanBeurt = dc.geefSpelerAanBeurt();
        kaartAfhandelen = new HashMap<>();
        kaartAfhandelen.put("ConsumablesKerker", this::geenEffectKaart);
        kaartAfhandelen.put("Curse", this::curse);
        kaartAfhandelen.put("Monster", this::monster);
        kaartAfhandelen.put("Race", this::geenEffectKaart);
        kaartAfhandelen.put("ConsumablesSchat", this::geenEffectKaart);
        kaartAfhandelen.put("Equipment", this::geenEffectKaart);
        image = new Image(dc.toonBovensteKk());
        initLayout();
        kaartAfhandelen.get(dc.geefTypeKaart(kaart)).run();
    }

    private void initLayout() {
        center.setCenter(new Label("kaartenAfhandeler"));
        centerVBox = new VBox();
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.setSpacing(center.getMinWidth() * 0.05);
        keuzesCenter = new HBox();
        keuzesCenter.setAlignment(Pos.CENTER);
        keuzesCenter.setSpacing(center.getMinWidth() * 0.05);
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(center.getMinWidth() * 0.15);
        textCenter = new Label();
        textCenter.setMinWidth(center.getMinWidth());
        textCenter.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(imageView, textCenter, keuzesCenter);
        center.setCenter(centerVBox);
    }

    private void monster() {
        //this.getChildren().clear();
        //Stage stage = new Stage();
        //BorderPane borderPane = new BorderPane();
        //Scene scene = new Scene(borderPane, 500, 500);
        //stage.setTitle("Monster");
        textCenter.setText(LanguageResource.getString("usecase4.ask.help"));
        //Label vraag = new Label(LanguageResource.getString("usecase4.ask.help"));
        Button btnJa = new Button(LanguageResource.getString("yes"));
        btnJa.setMinWidth(center.getMinWidth() * 0.2);
        Button btnNee = new Button(LanguageResource.getString("no"));
        btnNee.setMinWidth(center.getMinWidth() * 0.2);
        btnJa.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                help = true;
                kaartSpeelScherm();
            }
        });
        btnNee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                help = false;
                kaartSpeelScherm();
            }
        });
        //borderPane.setTop(vraag);
        //borderPane.setLeft(btnNee);
        //borderPane.setRight(btnJa);
        //stage.setScene(scene);
        //stage.show();
        //toonKaartenVanSpeler(borderPane);
        keuzesCenter.getChildren().addAll(btnJa, btnNee);
    }

    private void geenEffectKaart() {
        dc.geefKerkerkaartAanSpeler(dc.geefNaamSpeler(dc.geefSpelerAanBeurt()));
    }

    private void curse() {
        dc.curseKaart(dc.geefNaamSpeler(dc.geefSpelerAanBeurt()));
    }

    private void wiltHulp(ActionEvent event) {

    }

    private void wiltGeenHulp(ActionEvent event) {

    }

    private void toonKaartenVanSpeler(BorderPane borderPane) {
        //borderPane.getChildren().clear();
        dc.geefIDKaartenInHand(dc.geefNaamSpeler(spelerAanBeurt));
        //ImageView ivKaart = new ImageView(new )

    }

    private void kaartSpeelScherm() {
        keuzesCenter.getChildren().clear();
        textCenter.setVisible(false);
        List<String[]> spelersInVolgorde = dc.spelerOverzichtVolgorde();
        Stage stage = new Stage();
        BorderPane borderPane = new BorderPane();
        for (int i = 0; i < dc.geefAantalSpelers(); i++) {
            int speler = i;
            stage.setTitle(spelersInVolgorde.get(i)[0]);
            Button kaartSpelenTegenMonster = new Button("kaart tegen monster");
            Button kaartSpelenTegenSpeler = new Button("kaart tegen speler");
            Button niksDoen = new Button("weglopen");
            final VBox[] kaarten = new VBox[1];
            kaartSpelenTegenMonster.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    kaarten[0] = toonKaarten(speler);
                }
            });
            kaartSpelenTegenSpeler.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    kaarten[0] = toonKaarten(speler);
                }
            });
            niksDoen.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    kaarten[0] = toonKaarten(speler);
                }
            });
            borderPane.setCenter(kaarten[0]);
            HBox buttons = new HBox();
            buttons.getChildren().addAll(kaartSpelenTegenMonster, speler==dc.geefSpelerAanBeurt()?kaartSpelenTegenSpeler:new Label() ,niksDoen);
            buttons.setSpacing(center.getMinWidth()*0.1);
            buttons.setAlignment(Pos.CENTER);
            HBox.setMargin(buttons, new Insets(center.getMinWidth()*0.1,center.getMinWidth()*0.1,center.getMinWidth()*0.1,center.getMinWidth()*0.1));
            borderPane.setBottom(buttons);
        }
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setMinHeight(center.getMinHeight());
        stage.setMinWidth(center.getMinWidth());
        stage.show();
    }

    private VBox toonKaarten(int speler) {
        VBox kaartenView = new VBox();
        int[] kaarten = dc.geefKaartenVanSpelerInt(dc.geefNaamSpeler(speler));
        gespeeldeKaarten.clear();
        for (int kaart : kaarten) {
            ImageView imageView = new ImageView(new Image(String.format("/ui/images/kaarten/%d.png", kaart)));
            kaartenView.getChildren().add(imageView);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    //css aanmaken!!!
                    //imageView.setId("");
                    gespeeldeKaarten.add(kaart);
                }
            });
        }
        kaartenView.setAlignment(Pos.CENTER);
        kaartenView.setSpacing(center.getMinWidth() * 0.1);
        return kaartenView;
    }
}




