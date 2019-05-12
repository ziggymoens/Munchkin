package ui.gui.weggooi_afhandeler;

import domein.DomeinController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        gooiweg();
        getStylesheets().addAll("ui/gui/weggooi_afhandeler/weggooi.css");
    }

    private void gooiweg() {
        BorderPane borderPane = new BorderPane();
        Stage stage = new Stage();
        stage.setTitle(LanguageResource.getString("usecase7.throwscreen"));
        Scene scene = new Scene(borderPane, 1000, 800);
        Label vraag = new Label(LanguageResource.getString("usecase7.throw"));

        HBox kaarten = new HBox();

        for (int i = 0; i < items.size(); i++) {
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

        Button btnThrow = new Button(LanguageResource.getString("usecase7.confirmThrow"));
        btnThrow.setDefaultButton(true);
        Button btnCancel = new Button(LanguageResource.getString("usecase7.cancelThrow"));
        btnCancel.setCancelButton(true);

        buttons.getChildren().addAll(btnThrow, btnCancel);

        borderPane.setBottom(buttons);
        btnThrow.setOnMouseClicked(event -> {
            dc.gooiKaartenWeg(naam, weg);
            popUpScherm();
            stage.close();
        });
        btnCancel.setOnMouseClicked(event -> {
            stage.close();
        });

        buttons.setAlignment(Pos.TOP_CENTER);
        buttons.setSpacing(15);
        buttons.setPadding(new Insets(0, 0, 30, 0));
        kaarten.setSpacing(10);
        kaarten.setMinHeight(125);
        kaarten.setAlignment(Pos.CENTER);
        borderPane.setTop(vraag);
        vraag.setAlignment(Pos.CENTER);
        vraag.setPadding(new Insets(40, 275, 40, 275));
        borderPane.setCenter(kaarten);
        stage.setScene(scene);
        stage.show();

    }

    private void kaartInfoScherm(int item) {
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

    private void popUpScherm() {
        Stage stage = new Stage();
        VBox vbox = new VBox();
        Label label = new Label(LanguageResource.getString("usecase7.succesdelete"));
        label.setPadding(new Insets(20,20,20,20));
        Button btnOk = new Button("OK");
        Scene scene = new Scene(vbox, 300, 135);
        btnOk.setDefaultButton(true);
        btnOk.setOnMouseClicked(event -> {
            stage.close();
        });

        vbox.getChildren().addAll(label, btnOk);

        vbox.setAlignment(Pos.CENTER);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }


}
