package ui.gui;

import domein.DomeinController;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainGui extends Scene {
    private DomeinController dc = StartUpGui.dc;

    public MainGui(Parent root) {
        super(root);
    }
}
