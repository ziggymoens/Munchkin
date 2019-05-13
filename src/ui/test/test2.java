package ui.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import printer.Printer;

public class test2 {
    /**
     *constructor voor test2
     */
    test2() {
        Thread th1 = new Thread(() -> {
            try {
                printImage();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print(Printer.exceptionCatch("Exception (UC1)", e, false));
            }
        });
        th1.start();
    }

    /**
     * image printer
     */
    private void printImage(){
        App app = new App();
        app.begin();
    }

    /**
     *innerklasse app
     */
    public static class App extends Application {
        /**
         *satrtup methode voor app
         * @param primaryStage primaire stage
         * @throws Exception exception bij opstart
         */
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

        /**
         * lauch methode
         */
        void begin(){
            Application.launch();
        }
    }
}
