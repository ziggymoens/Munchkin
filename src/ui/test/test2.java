package ui.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import printer.Printer;

public class test2 {
    public test2() {
        th1.start();
    }

    public  void printImage(){
        App app = new App();
        app.begin();
    }

    private Thread th1 = new Thread(() -> {
        try {
            printImage();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print(Printer.exceptionCatch("Exception (UC1)", e, false));
        }
    });

    public static class App extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            BorderPane bp = new BorderPane();
            bp.setCenter(new ImageView(new Image("/ui/images/kaarten/1.png")));
            Scene scene = new Scene(bp);
            primaryStage.setScene(scene);
            primaryStage.setTitle(String.format("Munchkin - G35 - %s", "KS"));
            primaryStage.show();
            primaryStage.setResizable(false);
        }

        public void begin(){
            Application.launch();
        }
    }
}
