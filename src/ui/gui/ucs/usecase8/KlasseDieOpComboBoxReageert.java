package ui.gui.ucs.usecase8;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

public class KlasseDieOpComboBoxReageert implements EventHandler<ActionEvent> {    //implements omdat het interface is --> abstracte methodes implementeren

    private final ComboBox cboTitels;
    private final UseCase8G UC8G;

    public KlasseDieOpComboBoxReageert(ComboBox cboTitels, UseCase8G UC8G) {
        this.cboTitels = cboTitels;
        this.UC8G = UC8G;
    }

    @Override
    public void handle(ActionEvent event) {
        int index = cboTitels.getSelectionModel().getSelectedIndex();
        UC8G.update(index);
    }

}
