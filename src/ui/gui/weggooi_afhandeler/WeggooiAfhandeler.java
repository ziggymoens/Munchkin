package ui.gui.weggooi_afhandeler;

import domein.DomeinController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import language.LanguageResource;

import java.util.ArrayList;
import java.util.List;

public class WeggooiAfhandeler extends BorderPane {
    private DomeinController dc;
    private int kaart;
    private int spelerAanBeurt;
    private String naam;
    private List<Integer> items;
    private List<Integer> weg;
    private Label Error;

    public WeggooiAfhandeler(DomeinController dc) {
        this.dc = dc;
        kaart = dc.geefIdBovensteKaart();
        spelerAanBeurt = dc.geefSpelerAanBeurt();
        naam = dc.geefNaamSpeler(spelerAanBeurt);
        items = dc.geefIDKaartenInHand(naam);
        weg = new ArrayList<>();
        getStylesheets().add("ui/gui/weggooi_afhandeler/weggooi.css");
    }

    private void gooiweg(){
        BorderPane borderPane = new BorderPane();
        Stage stage = new Stage();
        stage.setTitle(LanguageResource.getString("usecase7.throwscreen"));
        Scene scene = new Scene(borderPane, 500, 500);
        Label vraag = new Label(LanguageResource.getString("usecase7.throw"));

        HBox kaarten = new HBox();

        for(int i = 0; i < items.size(); i++){
            int item = items.get(i);
            Image image = new Image(String.format("/ui/images/kaarten/%d.png", item));
            javafx.scene.image.ImageView img = new ImageView(image);
            img.setId("image");
            img.setPreserveRatio(true);
            img.setFitWidth(150);
            img.setStyle("-fx-opacity: 50%");
            img.setOnMouseClicked(event -> {
                kaartInfoScherm(item);
                img.setStyle("-fx-opacity: 100%");
                weg.add(item);
            });

            kaarten.getChildren().add(img);
        }

        HBox buttons = new HBox();

    }

    private void kaartInfoScherm(int item) {
    }
}
