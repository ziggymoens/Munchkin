package ui;

import domein.DomeinController;
import javafx.scene.control.Tab;

public class TabExtended extends Tab {
    private DomeinController dc;
    public TabExtended(DomeinController dc) {
        this.dc = dc;
    }

    public DomeinController getDc() {
        return dc;
    }
}
