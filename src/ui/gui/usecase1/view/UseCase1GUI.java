package ui.gui.usecase1.view;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class UseCase1GUI {
    public UseCase1GUI(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UseCase1.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
