/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author ziggy
 */
public class UseCase3 {
    //private Thread th1;
    private final DomeinController dc;
    //private static String locatieImg = null;
    //private static String typeImg = null;
    private final Scanner SCAN = new Scanner(System.in);
    private Map<String, Runnable> types;
    private int huidigeKaart;
    private String naam;
    private final int aantalSpelers;
    private List<String> huidigeSituatie;

    public UseCase3(DomeinController dc, int aantalSpelers) {
        //map die bepaalt hoe kaart afgehandeld wordt
        types = new HashMap<>();
        types.put("ConsumablesKerker", this::geenEffectKaart);
        types.put("Curse", this::curseKaart);
        types.put("Monster", this::monsterKaart);
        types.put("Race", this::geenEffectKaart);
        types.put("ConsumablesSchat", this::geenEffectKaart);
        types.put("Equipment", this::geenEffectKaart);
        this.dc = dc;
        this.aantalSpelers = aantalSpelers;

    }

    /**
     * @param naam
     */
    public void speelBeurt(String naam) {
        this.naam = naam;
        try {
            UniversalMethods.toonSituatie();
            huidigeSituatie = dc.geefSpelsituatie();
            System.out.println(dc.geefKaartenVanSpeler(naam) + "\n");
            System.out.println(dc.geefItemsVanSpeler(naam));
            //locatieImg = dc.toonBovensteKk();
            //typeImg = dc.geefTypeKaart(dc.geefIdBovensteKaart());
            //Thread th1 = new Thread(App::begin);
            //th1.start();
            //System.out.println(dc.toonBovensteKk() + " " + dc.geefTypeKaart(dc.geefIdBovensteKaart()));
            System.out.println(dc.bovensteKaartToString());
            huidigeKaart = dc.geefIdBovensteKaart();
            System.out.println(String.format("%s %s", Printer.printBlue(naam), LanguageResource.getString("usecase3.confirm")));
            String bev = SCAN.next();
            SCAN.nextLine();
            while (!bev.equals(LanguageResource.getString("yes"))) {
                System.out.printf(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + "%s%n%n", LanguageResource.getString("usecase3.novalidconfirm") + ColorsOutput.reset());
                System.out.println(LanguageResource.getString("usecase3.confirm"));
                bev = SCAN.next().toLowerCase();
                SCAN.nextLine();
            }
            //System.out.println(dc.geefTypeKaart(huidigeKaart));
            speelKaart();
            beheerKaarten();
            boolean verschil = true;
            for (int i = 0; i < aantalSpelers; i++) {
                verschil = huidigeSituatie.get(i).toLowerCase().equals(dc.geefSpelsituatie().get(i).toLowerCase());
            }
            if (verschil) {
                System.out.printf("%s%n", LanguageResource.getString("usecase3.nochanges"));
            } else {
                System.out.printf("%s%n", LanguageResource.getString("usecase3.changedsituation"));
                UniversalMethods.toonSituatie();
            }
            dc.nieuweBovensteKaartK();
            //th1.interrupt();
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC3)", e, false));
        }
    }

//    public static class App extends Application {
//        private static App app;
//        @Override
//        public void start(Stage primaryStage) throws Exception {
//            BorderPane bp = new BorderPane();
//            bp.setCenter(new ImageView(new Image(locatieImg)));
//            Scene scene = new Scene(bp);
//            primaryStage.setScene(scene);
//            primaryStage.setTitle(String.format("Munchkin - G35 - %s", typeImg));
//            primaryStage.show();
//            primaryStage.setResizable(false);
//            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//                @Override
//                public void handle(WindowEvent event) {
//                    Platform.exit();
//                    try {
//                        app.finalize();
//                    } catch (Throwable throwable) {
//                        throwable.printStackTrace();
//                    }
//                }
//            });
//        }
//
//        public static void begin() {
//            try {
//                app = new App();
//                app.launch();
//            } catch (Exception e) {
//            }
//        }
//    }

    /**
     *
     */
    private void speelKaart() {
        try {
            String type = dc.geefTypeKaart(huidigeKaart);
            types.get(type).run();
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC3)", e, false));
        }
    }


    /**
     * Kaarten beheren en doorverwijzen naar UC7
     */
    private void beheerKaarten() {
        try {
            UseCase7 uc7 = new UseCase7(this.dc);
            uc7.beheerKaarten();
            while (dc.getAantalKaarten(naam) > 5) {
                uc7.beheerKaarten();
            }
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC3)", e, false));
        }

    }

    /**
     *
     */
    private void geenEffectKaart() {
        try {
            dc.geefKerkerkaartAanSpeler(naam);
            System.out.print(Printer.printGreen(String.format("usecase3.play.%s", dc.geefTypeKaart(huidigeKaart).toLowerCase())));
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC3)", e, false));
        }

    }

    /**
     *
     */
    private void monsterKaart() {
        try {
            UseCase4 uc4 = new UseCase4(this.dc, aantalSpelers, naam);
            uc4.bereidSpelVoor();
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC3)", e, false));
        }
        System.out.print(Printer.printGreen("usecase3.play.monster"));
    }

    /**
     *
     */
    private void curseKaart() {
        try {
            dc.curseKaart(naam);
            System.out.println(Printer.printGreen("usecase3.play.curse"));
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC3)", e, false));
        }
    }
}

