package ui.gui.verkoop_afhandeler;

import domein.DomeinController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.LanguageResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VerkoopAfhandeler extends BorderPane {
    private DomeinController dc;
    private BorderPane center;
    private int kaart;
    private int spelerAanBeurt;
    private String naam;
    private List<Integer> items;
    private List<Integer> verkoop;
    private Label Error;


    public VerkoopAfhandeler(DomeinController dc) {
        this.dc = dc;
        kaart = dc.geefIdBovensteKaart();
        spelerAanBeurt = dc.geefSpelerAanBeurt();
        naam = dc.geefNaamSpeler(spelerAanBeurt);
        items = dc.geefIdVerkoopbareKaarten(naam);
        verkoop = new ArrayList<>();
        verkoop();
        getStylesheets().addAll("ui/gui/verkoop_afhandeler/VerkoopCSS.css");
    }

    private void verkoop() {
        BorderPane borderPane = new BorderPane();
        Stage stage = new Stage();
        stage.setTitle(LanguageResource.getString("usecase7.sellscreen"));
        Scene scene = new Scene(borderPane, 500, 500);
        Label vraag = new Label(LanguageResource.getString("usecase7.sell"));

        HBox kaarten = new HBox();


        for (int i = 0; i < items.size(); i++) {
            int item = items.get(i);
            Image image = new Image(String.format("/ui/images/kaarten/%d.png", item));
            ImageView img = new ImageView(image);
            img.setId("image");
            img.setPreserveRatio(true);
            img.setFitWidth(150);
            img.setStyle("-fx-opacity: 50%");
            verkoop = new ArrayList<>();
            img.setOnMouseClicked(event -> {
                kaartInfoScherm(item);
                img.setStyle("-fx-opacity: 100%");
                verkoop.add(item);
            });
            kaarten.getChildren().add(img);
        }

        HBox buttons = new HBox();

        Button btnConfirm = new Button(LanguageResource.getString("usecase7.confirmSale"));
        btnConfirm.setDefaultButton(true);
        Button btnCancel = new Button(LanguageResource.getString("usecase7.cancelSale"));
        btnCancel.setCancelButton(true);

        buttons.getChildren().addAll(btnConfirm, btnCancel);

        setBottom(buttons);
        btnConfirm.setOnMouseClicked(event -> {
            int totaleWaarde = 0;
            for (Integer id : verkoop) {
                totaleWaarde += dc.getWaardeSchatkaart(id);
            }

            if (totaleWaarde >= 1000) {
                int gedeeldeWaarde = totaleWaarde / 1000;
                dc.verkoopKaarten(naam, verkoop);
                popUpscherm(gedeeldeWaarde);

            } else {
                ErrorAfhandeling();
            }
        });

        btnCancel.setOnMouseClicked(event -> {

        });

        buttons.setAlignment(Pos.TOP_CENTER);
        buttons.setSpacing(15);
        buttons.setPadding(new Insets(0, 0, 30, 0));
        kaarten.setSpacing(10);
        kaarten.setMinHeight(125);
        kaarten.setAlignment(Pos.CENTER);
        setTop(vraag);
        setCenter(kaarten);
        vraag.setAlignment(Pos.CENTER);
        vraag.setPadding(new Insets(40, 40, 40, 40));
        /*borderPane.setCenter(kaarten);
        stage.setScene(scene);
        stage.show();*/
    }

    private void ErrorAfhandeling() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(LanguageResource.getString("usecase7.errorWaardeTitel"));
        alert.setHeaderText(LanguageResource.getString("usecase7.errorWaardeHeader"));
        alert.setContentText(LanguageResource.getString("usecase7.errorWaardeContext"));
        Optional<ButtonType> antwoord = alert.showAndWait();
    }

    public void kaartInfoScherm(int item) {
        BorderPane pane = new BorderPane();
        Label label = new Label(dc.geefTypeKaart(item));
        label.setAlignment(Pos.CENTER);
        label.setMinWidth(450);
        pane.setTop(label);
        pane.getTop().setStyle("-fx-text-alignment: center; -fx-min-height: 20px; -fx-font: bold 30 \"sans-serif\";");
        HBox center = new HBox();
        ImageView imageView1 = new ImageView(new Image(String.format("/ui/images/kaarten/%d.png", item)));
        imageView1.setFitWidth(150);
        imageView1.setPreserveRatio(true);
        center.getChildren().add(imageView1);
        VBox info = new VBox();
        info.getChildren().addAll(new Label("naam"), new Label("ID"), new Label("waarde"));
        info.setSpacing(30);
        info.setAlignment(Pos.CENTER);
        Button ok = new Button("OK");
        center.getChildren().addAll(info, ok);
        center.setAlignment(Pos.CENTER);
        pane.setCenter(center);
        Stage stage2 = new Stage();
        Scene scene2 = new Scene(pane, 450, 300);
        stage2.setTitle(String.format("Munchkin - G35 - Kaart - INFO", LanguageResource.getString("load")));
        stage2.setScene(scene2);
        ok.setOnMouseClicked(event -> {
            stage2.close();
        });
        stage2.show();
        stage2.setResizable(true);

    }

    private void popUpscherm(int gedeeldeWaarde) {
        Stage stage = new Stage();
        VBox vb = new VBox();
        Label text = new Label(LanguageResource.getString("usecase7.saleSucces"));
        text.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(vb, 200, 100);
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
}
