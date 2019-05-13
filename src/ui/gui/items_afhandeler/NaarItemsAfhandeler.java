package ui.gui.items_afhandeler;

import domein.DomeinController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import java.util.List;
import java.util.Optional;

public class NaarItemsAfhandeler extends BorderPane {
    private DomeinController dc;
    private BorderPane center;
    private int kaart;
    private int spelerAanBeurt;
    private String naam;
    private List<Integer> mogelijkheden;
    private List<Integer> items;
    private GameInterface gameInterface;

    public NaarItemsAfhandeler(DomeinController dc, GameInterface gameInterface) {
        this.dc = dc;
        this.gameInterface = gameInterface;
        kaart = dc.geefIdBovensteKaart();
        spelerAanBeurt = dc.geefSpelerAanBeurt();
        naam = dc.geefNaamSpeler(spelerAanBeurt);
        mogelijkheden = dc.geefIdsKunnenNaarItems(naam);
        items = new ArrayList<>();
        naarItems();
        getStylesheets().addAll("ui/gui/items_afhandeler/NaarItems.css");
    }

    private void naarItems() {
        BorderPane borderPane = new BorderPane();
        Stage stage = new Stage();
        stage.setTitle(LanguageResource.getString("usecase7.itemsscreen"));
        Scene scene = new Scene(borderPane);
        Label vraag = new Label(LanguageResource.getString("usecase7.items"));

        HBox kaarten = new HBox();

        for (int i = 0; i < dc.geefIdsKunnenNaarItems(naam).size(); i++) {
            int mogelijk = dc.geefIdsKunnenNaarItems(naam).get(i);
            Image image = new Image(String.format("/ui/images/kaarten/%d.png", mogelijk));
            ImageView img = new ImageView(image);
            img.setId("image");
            img.setPreserveRatio(true);
            img.setFitWidth(150);
            img.setStyle("-fx-opacity: 50%");
            img.setOnMouseClicked(event -> {
                kaartInfoScherm(mogelijk);
                img.setStyle("-fx-opacity: 100%");
                items.add(mogelijk);
            });
            kaarten.getChildren().addAll(img);
        }

        HBox buttons = new HBox();

        Button btnConfirm = new Button(LanguageResource.getString("usecase7.confirmItems"));
        btnConfirm.setDefaultButton(true);
        Button btnCancel = new Button(LanguageResource.getString("usecase7.cancelItems"));
        btnCancel.setCancelButton(true);

        buttons.getChildren().addAll(btnConfirm, btnCancel);

        borderPane.setBottom(buttons);
        btnConfirm.setOnMouseClicked(event -> {
            if (!items.isEmpty()) {
                dc.verplaatsNaarItems(naam, items);
                popUpscherm();
                stage.close();
            } else {
                ErrorAfhandeling();
            }
        });
        btnCancel.setOnMouseClicked(event -> {
            stage.close();
        });
        buttons.setAlignment(Pos.TOP_CENTER);
        buttons.setSpacing(15);
        buttons.setPadding(new Insets(25, 0, 25, 0));
        kaarten.setSpacing(10);
        kaarten.setMinHeight(125);
        kaarten.setAlignment(Pos.CENTER);
        borderPane.setTop(vraag);
        vraag.setAlignment(Pos.CENTER);
        vraag.setPadding(new Insets(40, 40, 40, 40));
        borderPane.setCenter(kaarten);
        stage.setScene(scene);
        stage.show();
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

    private void popUpscherm() {
        Stage stage = new Stage();
        VBox vb = new VBox();
        Label text = new Label(LanguageResource.getString("usecase7.itemsSucces"));
        text.setTextFill(Color.web("#08ff00"));
        text.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(vb, 225, 150);
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

    private void ErrorAfhandeling() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(LanguageResource.getString("usecase7.errorItemsTitel"));
        alert.setHeaderText(LanguageResource.getString("usecase7.errorItemsHeader"));
        alert.setContentText(LanguageResource.getString("usecase7.errorItemsContext"));
        Optional<ButtonType> antwoord = alert.showAndWait();
    }

}
