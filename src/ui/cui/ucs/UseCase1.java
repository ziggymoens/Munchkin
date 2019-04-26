package ui.cui.ucs;

import domein.DomeinController;
import exceptions.SpelException;
import exceptions.SpelerException;
import exceptions.database.KaartDatabaseException;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import java.util.*;

/**
 * @author g35
 *
 * KLAAR -- ZIGGY -- 26/4/2019
 * CONTROLE JONA --
 * CONTROLE KILI --
 */
public class UseCase1 {

    //declaraties voor gehele usecase
    private final Scanner SCAN = new Scanner(System.in);
    private final DomeinController dc;
    private final Map<String, Locale> talen;
    private int aantalSpelers;
    private String[] taalCodes = {"nl", "en", "fr"};

    /**
     * constructor voor UseCase 1
     *
     * @param dc Domeincontroller die aangemaakt wordt voor alle use cases in de Main
     */
    public UseCase1(DomeinController dc) {
        this.dc = dc;
        talen = new HashMap<>();

        for (String taal: taalCodes){
            talen.put(taal, new Locale(taal));
        }
        try {
            welcome();
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC1)", e, false));
        }

        //vragen voor developer modus
        askDeveloper();

        boolean newGame = UniversalMethods.controleJaNee("newGame");
        try {
            if (newGame) {
                try {
                    //methode start game op
                    gameStarter();
                } catch (Exception e) {
                    System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
                }

            } else {
                boolean load = UniversalMethods.controleJaNee("usecase1.load");
                if (!load) {
                    try {
                        //methode start game op
                        gameStarter();
                    } catch (Exception e) {
                        System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
                    }
                }
                try {
                    //methode om game te laden
                    gameLoader();
                } catch (Exception e) {
                    System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
                }
            }
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC1)", e, false));
        }
    }

    /**
     * Methode die vraagt om developer modus te activeren
     */
    private void askDeveloper() {
        try {
            boolean dev = UniversalMethods.controleJaNee("usecase1.developer");
            if (dev) {
                Printer.setDeveloperMode(true);
                askStackTrace();
            }
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
        }
    }

    /**
     * Methode die de mogelijkheid geeft om de gebruiker de stacktrace te laten printen
     */
    private void askStackTrace(){
        try{
            Printer.setPrintStackTrace(UniversalMethods.controleJaNee("usecase1.stacktrace"));
        }catch (Exception e){
            System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
        }
    }

    /**
     * methode om het spel te starten
     */
    private void gameStarter() {
        try {
            //methode om spel aan te maken
            maakSpel();
            //methode om spelers toe te voegen
            voegSpelersToe();
            System.out.println(Printer.printGreen("spel.playersadded"));
            //verdergaan naar UC2
            UseCase2 uc2 = new UseCase2(this.dc);
            uc2.speelSpel(aantalSpelers);
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
        }
    }

    /**
     * methode om spel te laden
     */
    private void gameLoader() {
        try {
            //verdergaan naar UC9
            UseCase9 uc9 = new UseCase9(this.dc);
            uc9.spelLaden();
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
        }
    }


    /**
     * welcome message in 3 talen
     */
    private void welcome() {
        try {
            //taal kies boodschap in 3 talen
            for (Map.Entry<String, Locale> taal : talen.entrySet()) {
                System.out.printf("%s %s%n", LanguageResource.getStringLanguage("startUp", taal.getValue()), LanguageResource.getStringLanguage("languageC", taal.getValue()));
            }
            String gekozenTaal = SCAN.next().toLowerCase();
            SCAN.nextLine();
            //zolang gekozen taal niet voldoet aan van frans, nederlands of engels in de eigen taal

            Map<String, String> talenVolledig = new HashMap<>();
            for (String code : taalCodes) {
                for (Map.Entry<String, Locale> taal : talen.entrySet()) {
                    talenVolledig.put(LanguageResource.getStringLanguage(code, taal.getValue()).toLowerCase(), code);
                }
            }
            //controleren of taal correct is
            while (talenVolledig.get(gekozenTaal) == null) {
                for (Map.Entry<String, Locale> taal : talen.entrySet()) {
                    System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n", LanguageResource.getStringLanguage("wrong", taal.getValue()) + ColorsOutput.reset());
                }
                gekozenTaal = SCAN.next().toLowerCase();
                SCAN.nextLine();
            }
            //switch voor de verschillende talen
            LanguageResource.setLocale(talen.get(talenVolledig.get(gekozenTaal)));
            System.out.printf("%s: %s%n", LanguageResource.getString("picked"), LanguageResource.getString("language"));
        }catch (Exception e){
            System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
            SCAN.nextLine();
        }
    }

    /**
     * De gebruiker een aantal spelers laten kiezen, dit tussen 3 en 6 en een
     * spel aanmaken(grenzen incl.)
     */
    private void maakSpel() {
        try {
            //thread initieel starten
            th1.start();
            //thread pauzeren
            th1.suspend();
        }catch (Exception e){
            System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
        }
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                int as = kiesSpelers();
                //thread opnieuw opstarten
                th1.resume();
                dc.startSpel(as);
                //thread stoppen
                th1.stop();
                this.aantalSpelers = as;
                tryAgain = false;
            } catch (SpelException e) {
                //thread pauzeren
                th1.suspend();
                System.out.print(Printer.exceptionCatch("SpelException (UC1)", e));
            } catch (KaartDatabaseException e) {
                //thread pauzeren
                th1.suspend();
                System.out.print(Printer.exceptionCatch("KaartDatabaseException (UC1)", e));
            } catch (InputMismatchException e) {
                //thread pauzeren
                th1.suspend();
                e = new InputMismatchException("usecase2.choiceerror");
                System.out.println(Printer.exceptionCatch("InputException (UC1)", e));
            } catch (Exception e) {
                //thread pauzeren
                th1.suspend();
                System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
            }
        }
        System.out.println("\n" + Printer.printGreen("spel.made"));
    }

    /**
     * methode om een aantal spelers te kiezen
     *
     * @return het aantal spelers
     */
    private int kiesSpelers() {
        int as = 0;
        boolean ta1 = true;
        while (ta1) {
            try {
                System.out.println(LanguageResource.getString("amountOfPlayers"));
                as = SCAN.nextInt();
                if (as < 3 || as > 6) {
                    throw new SpelException("exception.spel.players");
                }
                ta1 = false;
            } catch (InputMismatchException e) {
                e = new InputMismatchException("usecase2.choiceerror");
                System.out.println(Printer.exceptionCatch("InputException (UC1)", e));
                SCAN.nextLine();
            } catch (SpelException e) {
                System.out.println(Printer.exceptionCatch("SpelException (UC1)", e));
                SCAN.nextLine();
            } catch (Exception e) {
                System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
                SCAN.nextLine();
            }
        }
        return as;
    }



    /**
     * Voeg het aantal gekozen aantal spelers toe aan het spel a.d.h.v. naam,
     */
    private void voegSpelersToe() {
        try {
            for (int i = 0; i < aantalSpelers; i++) {
                System.out.println(String.format("%s %d", LanguageResource.getString("player"), i + 1));
                //leeg speler object aanmaken
                dc.maakSpeler();
                //naam toewijzen aan speler
                kiesNaamSpeler(i);
                //geslacht toewijzen aan speler
                kiesGeslachtSpeler(i);
            }
            //geef startkaarten aan spelers in volgorde van ingeven (DR_uc1)
            dc.geefStartKaarten();
        }catch (InputMismatchException e) {
            e = new InputMismatchException("usecase2.choiceerror");
            System.out.println(Printer.exceptionCatch("InputException (UC1)", e));
        }catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
        }
    }

    /**
     * Methode die de gebruiker de naam laat ingeven van de i-de speler
     *
     * @param i hoeveelste speler van het spel
     */
    private void kiesNaamSpeler(int i) {
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(LanguageResource.getString("ask.name"));
                String naam = SCAN.next();
                SCAN.nextLine();
                //voeg naam toe aan speler i
                dc.geefSpelerNaam(i, naam);
                tryAgain = false;
            } catch (SpelerException e) {
                System.out.print(Printer.exceptionCatch("SpelerException (UC1)", e));
                SCAN.nextLine();
            } catch (SpelException e) {
                System.out.print(Printer.exceptionCatch("SpelException (UC1)", e));
                SCAN.nextLine();
            }catch (InputMismatchException e) {
                e = new InputMismatchException("usecase2.choiceerror");
                System.out.println(Printer.exceptionCatch("InputException (UC1)", e));
                SCAN.nextLine();
            }catch (Exception e){
                System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
                SCAN.nextLine();
            }
        }
    }

    /**
     * Methode die de gebruiker het geslacht laat ingeven van de i-de speler
     *
     * @param i hoeveelste speler van het spel
     */
    private void kiesGeslachtSpeler(int i) {
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(LanguageResource.getString("ask.sex"));
                String geslacht = SCAN.next();
                SCAN.nextLine();
                //geslacht toewijzen aan speler i
                dc.geefSpelerGeslacht(i, geslacht);
                tryAgain = false;
            } catch (SpelerException e) {
                System.out.print(Printer.exceptionCatch("SpelerException (UC1)", e));
                SCAN.nextLine();
            } catch (Exception e){
                System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
                SCAN.nextLine();
            }
        }
    }

    /**
     * deze werkt niet goed in netbeans
     */
    private final Thread th1 = new Thread(() -> {
        try{
        System.out.print("\nLoading ");
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                System.out.println(Printer.exceptionCatch("InterruptedException", ex, false));
            }
            System.out.print(".");
        }
        }catch (Exception e){
            System.out.println(Printer.exceptionCatch("Exception (UC1)", e, false));
        }
    });
}

//    /**
//     * methode om te kiezen welke databank wil gebruikt worden
//     *
//     * @return true = kleine databank
//     */
//    private boolean kiesDb() {
//        boolean ta2 = true;
//        boolean kleine = false;
//        while (ta2) {
//            try {
//                SCAN.nextLine();
//                System.out.println(String.format(LanguageResource.getString("dbchoice")));
//                int k = SCAN.nextInt();
//                if (k < 1 || k > 2) {
//                    throw new Exception("usecase2.choiceinput");
//                }
//                if (k == 1) {
//                    kleine = true;
//                }
//                ta2 = false;
//            } catch (InputMismatchException e) {
//                e = new InputMismatchException("usecase2.choiceerror");
//                System.out.println(Printer.exceptionCatch("InputException", e));
//
//            } catch (Exception e) {
//                System.out.println(Printer.exceptionCatch("Exception", e));
//            }
//        }
//        return kleine;
//    }