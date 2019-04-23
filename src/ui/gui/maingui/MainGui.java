package ui.gui.maingui;

import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import ui.gui.extras.menubar.MenuBarGui;


public class MainGui extends BorderPane {
    private final MenuBarGui mb;

    public MainGui(){
        this.mb = new MenuBarGui();
        mb.setId("mb");
        this.setTop(mb);

        getStylesheets().add("ui/gui/maingui/MainGui.css");
    }

    public MenuBar getMb() {
        return mb;
    }

    public void updateMenuLang(){
        mb.updateMenuBarLang();
    }
}
