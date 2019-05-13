package ui.test;

import domein.DomeinController;
import domein.kaarten.Kaart;
import domein.repositories.KaartDbRepository;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import language.LanguageResource;
import persistentie.mappers.PersistentieController;
import persistentie.mappers.SpelMapperDb;
import printer.Printer;

import java.util.List;
import java.util.Locale;

/**
 * @author ziggy
 */
public class Test {

    //i18n testen
    private final LanguageResource bundle = new LanguageResource();
    private final KaartDbRepository kr = new KaartDbRepository();
    private final List<Kaart> sk = kr.getSchatkaarten();
    private final List<Kaart> kk = kr.getKerkerkaarten();
    private static final DomeinController dc = new DomeinController();

    /**
     * methode om test 1 te runnen
     * @param args startup args
     */
    public static void main(String[] args) {

//        KaartMapper km = new KaartMapper();
//        List<Kaart> kkk = km.getKaarten();
//        int k = 0, s=0;
//        int ck=0,c=0,m=0,r=0,cs=0,e=0;
//        StringBuilder out = new StringBuilder();
//        for (Kaart kaart: kkk) {
//            out.append(String.format("%5d:%40s -->%20s%n",kaart.getId(), kaart.getNaam(), kaart.getClass().getSimpleName()));
//            if (kaart instanceof ConsumablesSchat){
//                cs++;
//                s++;
//            }
//            if (kaart instanceof Equipment){
//                e++;
//                s++;
//            }
//
//        }
//        for (Kaart kaart: kkk){
//            out.append(String.format("%5d:%40s -->%20s%n",kaart.getId(), kaart.getNaam(), kaart.getClass().getSimpleName()));
//            if (kaart instanceof ConsumablesKerker){
//                ck++;
//                k++;}
//            if (kaart instanceof Curse){
//                c++;
//                k++;
//            }
//            if (kaart instanceof Monster){
//                m++;
//                k++;
//            }
//            if (kaart instanceof Race){
//                r++;
//                k++;
//            }
//
//        }
//        //test push
//        System.err.printf("cs = %d, e = %d, ck = %d, c = %d, m = %d, r = %d, schat = %d, kerker = %d%n", cs, e, ck, c, m, r, s, k);
//        //System.out.println(out.toString());


//        try{
//            String host="redhat.com";
//            int port=80;
//            int timeOutInMilliSec=5000;// 5 Seconds
//            Socket socket = new Socket();
//            socket.connect(new InetSocketAddress(host, port), timeOutInMilliSec);
//            System.out.println("Internet is Available");
//        }
//        catch(Exception ex){
//            System.out.println("No Connectivity");
//        }

//        int test = 1002;
//        int test2 = 2000;
//        System.out.println(test/1000);
//        System.out.println(test2/1000);
//



        PersistentieController pc = new PersistentieController();
        SpelMapperDb spelMapperDb = new SpelMapperDb();
        LanguageResource.setLocale(new Locale("en"));
//        Connection.setConnected(false);
//        Spel spel = new Spel(3);
//        spel.maakNieuweSpeler();
//        spel.geefSpelerNaam(0, "janjan");
//        spel.geefSpelerGeslacht(0, "man");
//        spel.maakNieuweSpeler();
//        spel.geefSpelerNaam(1, "mariee");
//        spel.geefSpelerGeslacht(1, "woman");
//        spel.maakNieuweSpeler();
//        spel.geefSpelerNaam(2, "zigggy");
//        spel.geefSpelerGeslacht(2, "man");
//        spel.geefStartKaarten();
//        spel.controleerVolgorde();
//        for (Speler speler:spel.getSpelers()){
//            speler.updateItems();
//            speler.updateKaarten();
//        }
//        spel.updateVolgorde();
//        //System.out.println(spel.getSchatkaarten().toString());
//        //System.out.println(spel.getKerkerkaarten().toString());
//        //System.out.println(spel.getKaarten().toString());
//        //spel.geefStartKaarten();
//        spel.setSpelerAanBeurt(1);
//        spel.setNaam("test007");
//        //spelMapperDb.resetIncrement();
//        //pc.spelOpslaan(spel);
//        System.out.println(pc.getOverzicht().toString());
//        Spel spel1 = pc.laadSpel(1);
//        System.out.println(spel1.geefSpelsituatie());
//        //System.out.println(spel1.getKerkerkaarten().toString());
//        //System.out.println(spel1.getSchatkaarten().toString());
//        for (Speler speler: spel.getSpelers()){
//            System.out.println(speler.getKaarten().toString());
//        }

//        List<Integer> test = new ArrayList<>();
//        test.add(1);
//        test.add(2);
//        test.add(3);
//        System.out.println(test.toString());
//        test.clear();
//        System.out.println(test.toString());



////        //pc.remove("test123");
////
        //System.out.println(pc.getOverzicht().toString());

        //Spel spel1 = pc.laadSpel(3);
        //System.out.println(spel1.geefSpelsituatie());
        //for (Speler speler:spel1.getSpelers()){
        //    System.out.println(speler.toString());
        //    System.out.println(speler.kaartenNaarString(speler.getKaarten()));
        //}
////
////        System.out.println(pc.getOverzicht());
//        //SpelMapperDb sm = new SpelMapperDb();
//        //sm.addSpel("test1234", 0, true);
//        //System.out.println(test.geefKaarten());
//    }
//
//    private String geefKaarten(){
//        int k = 0, s=0;
//        int ck=0,c=0,m=0,r=0,cs=0,e=0;
//        StringBuilder out = new StringBuilder();
//        for (Kaart kaart: sk) {
//            out.append(String.format("%5d:%40s -->%20s%n",kaart.getId(), kaart.getNaam(), kaart.getClass().getSimpleName()));
//            if (kaart instanceof ConsumablesSchat){
//                cs++;
//            }
//            if (kaart instanceof Equipment){
//                e++;
//            }
//            s++;
//        }
//        for (Kaart kaart: kk){
//            out.append(String.format("%5d:%40s -->%20s%n",kaart.getId(), kaart.getNaam(), kaart.getClass().getSimpleName()));
//            if (kaart instanceof ConsumablesKerker){
//                ck++;
//            }
//            if (kaart instanceof Curse){
//                c++;
//            }
//            if (kaart instanceof Monster){
//                m++;
//            }
//            if (kaart instanceof Race){
//                r++;
//            }
//            k++;
//        }
//        System.err.printf("cs = %d, e = %d, ck = %d, c = %d, m = %d, r = %d, schat = %d, kerker = %d%n", cs, e, ck, c, m, r, s, k);
//        return out.toString();
        test2 t2 = new test2();

    }

    /**
     *thread test
     */
    private static void start(){
        th1.start();
        printImage();
    }


    /**
     *image om te printen
     */
    private static void printImage(){
        App app = new App();
        app.begin();
    }

    /**
     *thread die img laat printen
     */
    private static final Thread th1 = new Thread(() -> {
        try {
            printImage();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print(Printer.exceptionCatch("Exception (UC1)", e, false));
        }
    });

    /**
     * inner klasse app
     */
    public static class App extends Application {
        /**
         * constructor voor app
         */
        App() {
            super();
        }

        /**
         *start methode
         * @param primaryStage primaire stage
         * @throws Exception exception bij opstartfout
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
         * op app op te starten
         */
        void begin(){
            Application.launch();
        }
    }
}
