package ui.gui.usecase1;

import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import language.LanguageResource;

import java.util.*;


public class UseCase1G extends BorderPane {
    private VBox vBox;
    private ChoiceBox<String> choiceBoxTaal;
    private List<String> talen;

    public UseCase1G() {
        talen = new ArrayList<>();
        talen.add("Nederlands");
        talen.add("Français");
        talen.add("English");

        Label label = new Label("Munchkin");
        label.setId("titel");

        //taal laten kiezen
        welcome();

        //css linken
        getStylesheets().add("ui/gui/usecase1/UseCase1G.css");
        //layout voor borderbox
        setTop(label);
    }


    private void welcome() {
        vBox = new VBox();
        vBox.getChildren().add(new Label("Welkom"));

        HBox hBox = new HBox();

        //keuze vak voor talen
        choiceBoxTaal = new ChoiceBox<>();
        for (String taal : talen) {
            choiceBoxTaal.getItems().add(taal);
        }
        choiceBoxTaal.setValue(talen.get(0));

        //button in Hbox die taal selecteert
        Button button = new Button("next");
        button.setOnAction(this::ButtonTaalEventHandler);

        //button en choiceB toevoegen aan Hbox
        hBox.getChildren().addAll(choiceBoxTaal, button);
        setCenter(vBox);
        setBottom(hBox);
    }

    private void ButtonTaalEventHandler(ActionEvent event) {
        Locale locale;
        switch (choiceBoxTaal.getValue()) {
            case "Nederlands":
                locale = new Locale("nl");
                break;
            case "Français":
                locale = new Locale("fr");
                break;
            case "English":
            default:
                locale = new Locale("en");
                break;
        }
        LanguageResource.setLocale(locale);
        vBox.getChildren().clear();
        Label label = new Label();
        label.setText(String.format("%s: %s", LanguageResource.getString("picked"), LanguageResource.getString("language")));
        vBox.getChildren().add(label);
    }
}
