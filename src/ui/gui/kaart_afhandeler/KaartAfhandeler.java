package ui.gui.kaart_afhandeler;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import language.LanguageResource;
import ui.gui.game_interface.GameInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KaartAfhandeler extends BorderPane {
    private DomeinController dc;
    private GameInterface gameInterface;
    private BorderPane borderPane;
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
    private List<Integer> gespeeldeKaartenSpeler = new ArrayList<>();
    private int vraagSpeler;


    public KaartAfhandeler(DomeinController dc, BorderPane center, GameInterface gi) {
        this.gameInterface = gi;
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
        dc.setSpelerBattlePoints(dc.geefLevel(spelerAanBeurt));
        dc.setMonsterBattlePoints( Integer.parseInt(dc.geefMonsterAttribuut(kaart, "level").toString()));
        Button btnJa = new Button(LanguageResource.getString("yes"));
        btnJa.setMinWidth(center.getMinWidth() * 0.2);
        Button btnNee = new Button(LanguageResource.getString("no"));
        btnNee.setMinWidth(center.getMinWidth() * 0.2);
        btnJa.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                help = true;
                vraagSpeler  = 0;
                kaartSpeelScherm();
                //textCenter.setText(toonOverzichtGewonnenZijde());
            }
        });
        btnNee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                help = false;
                vraagSpeler = 0;
                kaartSpeelScherm();
                //textCenter.setText(toonOverzichtGewonnenZijde());
            }
        });
        //borderPane.setTop(vraag);
        //borderPane.setLeft(btnNee);
        //borderPane.setRight(btnJa);
        //stage.setScene(scene);
        //stage.show();
        //toonKaartenVanSpeler(borderPane);
        keuzesCenter.getChildren().addAll(btnJa, btnNee);
        //Label eindegevecht = new Label();

    }

    private void geenEffectKaart() {
        dc.geefKerkerkaartAanSpeler(dc.geefNaamSpeler(dc.geefSpelerAanBeurt()));
        gameInterface.kaartGespeeld();
    }

    private void curse() {
        dc.curseKaart(dc.geefNaamSpeler(dc.geefSpelerAanBeurt()));
        gameInterface.kaartGespeeld();
    }

    private void wiltHulp(ActionEvent event) {

    }

    private void wiltGeenHulp(ActionEvent event) {
         
    }

    private String toonOverzichtGewonnenZijde(){
        for(int i = 0; i < gespeeldeKaartenSpeler.size(); i++){
            int kaart = gespeeldeKaartenSpeler.get(i);
            boolean monster = true;
            if(dc.controleWelkeKaart(kaart, monster).equals("Monster")){
                dc.speelMonster(kaart,monster);
            }
            if(dc.controleWelkeKaart(kaart, monster).equals("Curse")){
                curseUitvoeren();
            }
            if(dc.controleWelkeKaart(kaart, monster).equals("Consumable")){
                dc.speelConsumable(kaart);
            }
            if(dc.controleWelkeKaart(kaart, monster).equals("Race/Weapon")){
                dc.itemsBijvoegen(kaart);
            }
        }
        dc.spelerLevels();
        Label eindeGevecht = new Label();
        if(dc.getSpelerBattlePoints() > dc.getMonsterBattlePoints()){
            //eindeGevecht.setText("usecase6.playerwon");

            return LanguageResource.getString("usecase6.playerwon");
        }else{
            //eindeGevecht.setText("usecase6.monsterwon");
            return LanguageResource.getString("usecase6.monsterwon");
        }
    }

    private void curseUitvoeren(){

    }

    private void schatkaartenUitdelen(){
        int aantalSchatkaarten = Integer.parseInt( dc.geefMonsterAttribuut(kaart, "schatkaarten").toString());
        for(int i = 0; i < aantalSchatkaarten; i++){
            if(dc.gethelptmee().get(i)){

            }
        }
    }

    private void kaartSpeelScherm() {
        keuzesCenter.getChildren().clear();
        textCenter.setVisible(false);
        List<String[]> spelersInVolgorde = dc.spelerOverzichtVolgorde();
        toonSpelerHulpScherm(vraagSpeler, spelersInVolgorde);
    }

    /*private void toonSpelerHulpScherm(){

    }*/

    private void toonSpelerHulpScherm(int speler, List<String[]> spelersInVolgorde){
        Stage stage = new Stage();
        borderPane = new BorderPane();
        int aantal = dc.geefSpelerAanBeurt();
        int i = speler;
        stage.setTitle(spelersInVolgorde.get(i)[0]);
        List<VBox> checkBoxes = checkboxImages(dc.geefKaartenVanSpelerInt(dc.geefNaamSpeler(i)));
        HBox centerHBox = new HBox();
        centerHBox.getChildren().addAll(checkBoxes);
        borderPane.setCenter(centerHBox);
        Button kaartSpelenTegenMonster = new Button("kaart tegen monster");
        Button kaartSpelenTegenSpeler = new Button("kaart tegen speler");
        Button niksDoen = new Button("weglopen");
        kaartSpelenTegenMonster.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (help || dc.geefSpelerAanBeurt() == speler) {
                    List<Integer> gespeeldeKaarten = new ArrayList<>();
                    for (Node vBox : centerHBox.getChildren()) {
                        int[] ids = dc.geefKaartenVanSpelerInt(dc.geefNaamSpeler(speler));
                        for (int i = 0; i < ids.length; i++) {
                            if (((CheckBox) (((VBox) vBox).getChildren().get(0))).isSelected()) {
                                int kaart = ids[i];
                                System.out.println(kaart);
                                gespeeldeKaarten.add(kaart);
                            }
                        }
                    }
                    for (int i = 0; i < gespeeldeKaarten.size(); i++) {
                        if (!dc.validatieKaartSpeler(gespeeldeKaarten.get(i))) {
                            popUpscherm("usecase5.invalidcard", "#ff0000");
                        } else {

                        }
                    }
                    System.out.println(gespeeldeKaarten.toString());
                    borderPane.setTop(new Label(gespeeldeKaarten.toString()));
                    vraagSpeler++;
                    toonSpelerHulpScherm(vraagSpeler, spelersInVolgorde);
                }else{
                    //Label helpenNietMogelijk = new Label("usecase4.");
                    popUpscherm("exception.help", "#ff0000");
                }
                stage.close();
            }
            //Node vBox : ((HBox)hBox).getChildren()
        });
        kaartSpelenTegenSpeler.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stage.close();
                vraagSpeler++;
                toonSpelerHulpScherm(vraagSpeler, spelersInVolgorde);
            }
        });
        niksDoen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Label ontsnappen =  new Label(vluchten(borderPane));

                if(ontsnappen.getText().equals(LanguageResource.getString("usecase6.escape1"))){
                    popUpscherm("usecase6.escape1", "#08ff00");
                    stage.close();
                    vraagSpeler++;
                    toonSpelerHulpScherm(vraagSpeler, spelersInVolgorde);
                }else{
                    popUpscherm("usecase6.escape2", "#08ff00");
                    vraagSpeler++;
                    toonSpelerHulpScherm(vraagSpeler, spelersInVolgorde);
                }

            }
        });
        HBox buttons = new HBox();
        buttons.getChildren().addAll(kaartSpelenTegenMonster, speler!=0?kaartSpelenTegenSpeler:new Label() ,niksDoen);
        buttons.setSpacing(center.getMinWidth()*0.1);
        buttons.setAlignment(Pos.CENTER);
        HBox.setMargin(buttons, new Insets(center.getMinWidth()*0.1,center.getMinWidth()*0.1,center.getMinWidth()*0.1,center.getMinWidth()*0.1));
        borderPane.setBottom(buttons);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setMinHeight(center.getMinHeight());
        stage.setMinWidth(center.getMinWidth());
        stage.show();
    }

   /* private VBox toonKaarten(int speler) {
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
    }*/

    private String vluchten(BorderPane borderPane){
        int worp = dc.gooiDobbelsteen();
        int runAway = Integer.parseInt(dc.geefMonsterAttribuut(kaart,"RunAway").toString());
        Map<Integer, ImageView> dobbelsteen = new HashMap<>();
        for(int i = 1; i <= 6;i++){
            dobbelsteen.put(i,new ImageView(new Image(String.format("/ui/images/dobbelsteen/%d.png", i), 300, 300, true, false)));
        }
        borderPane.setCenter(dobbelsteen.get(worp));
        if(worp > 4 - runAway){
            return LanguageResource.getString("usecase6.escape1");
        }else{
            return LanguageResource.getString("usecase6.escape2");
        }
    }

    private void popUpscherm(String string, String kleur) {
        Stage stage = new Stage();
        VBox vb = new VBox();
        Label text = new Label(LanguageResource.getString(string));
        text.setTextFill(Color.web(kleur));
        text.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(vb, center.getMinWidth()*0.45, center.getMinWidth()*0.25);
        Button btnOk = new Button("OK");
        btnOk.setDefaultButton(true);

        vb.getChildren().addAll(text, btnOk);
        vb.setAlignment(Pos.CENTER);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        btnOk.setOnMouseClicked(event -> {
            stage.close();
        });
    }

    private List<VBox> checkboxImages(int[] ids){
        List<VBox> checkBoxes = new ArrayList<>();
        HBox images = new HBox();
        images.setMinWidth(borderPane.getWidth());
        for (int id : ids){
            VBox image = new VBox();
            CheckBox checkBox = new CheckBox();
            ImageView imageView = new ImageView(new Image(String.format("/ui/images/kaarten/%d.png", id)));
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(center.getMinHeight()*0.15);
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
            image.setMinWidth(images.getMinWidth()*0.15);
            image.setAlignment(Pos.CENTER);
            checkBoxes.add(image);
        }
        return checkBoxes;
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
        info.getChildren().addAll(new Label("naam"), new Label("ID"), new Label("waarde"));
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

    /*private void toonKaartenVanSpeler(BorderPane borderPane) {
        //borderPane.getChildren().clear();
        dc.geefIDKaartenInHand(dc.geefNaamSpeler(spelerAanBeurt));
        //ImageView ivKaart = new ImageView(new )
    }*/

    private void kaartGespeeld(){
        gameInterface.kaartGespeeld();
    }
}
