package ui.gui.a_universal;

import domein.DomeinController;
import javafx.scene.Node;
import javafx.scene.control.Tab;

import java.util.Locale;

public class TabExtended extends Tab {
    private DomeinController dc;
    private Locale locale;

    public TabExtended(DomeinController dc) {
        this.dc = dc;
        this.locale = new Locale("en");
    }

    public DomeinController getDc() {
        return dc;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setNewContent(Node node){
        contentProperty().set(node);
    }
}
