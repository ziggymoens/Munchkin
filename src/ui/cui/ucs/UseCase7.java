package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Beheer kaarten in hand
 * UC7
 */
public class UseCase7 {
    private final DomeinController dc;
    private final Scanner SCAN = new Scanner(System.in);
    private List<Integer> ids = new ArrayList<>();
    private int g;
    public UseCase7(DomeinController dc) {
        this.dc = dc;
    }

     void beheerKaarten(String naam) {
        int keuze = 0;
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(String.format("%s %s", naam, LanguageResource.getString("usecase7.actions")));
                System.out.println(String.format("1) %s", LanguageResource.getString("usecase7.action1")));
                System.out.println(String.format("2) %s", LanguageResource.getString("usecase7.action2")));
                keuze = SCAN.nextInt();
                if (keuze < 1 || keuze > 2) {
                    throw new Exception();
                }
                tryAgain = false;
            } catch (Exception e) {
                System.out.println(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + "Foute keuze, probeer opnieuw" + ColorsOutput.reset());
                SCAN.nextLine();
            }
        }
        try {
            switch (keuze) {
                case 1: //naarItems (op tafel leggen)
                    try {
                        if (dc.getAantalKaarten(naam) >= 1) {
                            System.out.println(String.format("%s:%n", LanguageResource.getString("usecase7.toitems")));
                            System.out.println(String.format("%s:%n", dc.geefKaartenKunnenNaarItems(naam)));
                        }else{
                            System.out.println(String.format("%s!", LanguageResource.getString("usecase7.nietgenoeg")));
                        }
                    } catch (Exception e) {

                    }
                    break;
                case 2: //verkopen + weggooien
                    try {
                        System.out.println(String.format("%s: %n%s", LanguageResource.getString("usecase7.sellable"), dc.geefVerkoopbareKaarten(naam)));
                        System.out.println(String.format("%s: %n%s", LanguageResource.getString("usecase7.throwaway"), dc.geefNietVerkoopbareKaarten(naam)));
                        System.out.println(LanguageResource.getString("usecase7.asktosell"));
                        String antw = SCAN.next();

                        if(antw.equalsIgnoreCase("yes") || antw.equalsIgnoreCase("oui") || antw.equalsIgnoreCase("ja")){
                            String antwoord;
                            String[] keuzeKaart;
                            int totWaarde = 0;
                            boolean match = false;
                                do {
                                    System.out.println(LanguageResource.getString("usecase7.sellorthrow"));
                                    antwoord = SCAN.next();
                                    if (antwoord.equalsIgnoreCase(LanguageResource.getString("usecase7.translationsell"))) {
                                        System.out.println(LanguageResource.getString("usecase7.sell"));
                                        System.out.println(String.format("%s: %n%s", LanguageResource.getString("usecase7.sellable"), dc.geefVerkoopbareKaarten(naam)));
                                        SCAN.nextLine();
                                        int teller = 0;

                                        // do-while waarin ids van de verkoopbare kaarten toegevoegd worden aan een List om die vervolgens uit te lezen om de waardes op te tellen
                                        // kan verbeterd worden -- OPTIMALISEREN
                                        do {
                                            System.out.println(LanguageResource.getString("usecase7.whattosell"));
                                            g = SCAN.nextInt();
                                            if (dc.geefIdVerkoopbareKaarten().contains(g) && g != 999) {
                                                ids.add(g);

                                                teller++;

                                            }else if(!dc.geefIdVerkoopbareKaarten().contains(g)){
                                                System.out.println(LanguageResource.getString("usecase7.foutid"));

                                            }else if(g == 999){
                                                System.out.println("GESTOPT - HARDCODE");
                                                break;
                                            }
                                            match = true;
                                            //for(int i = 0; i <= teller - 1; i++) {
                                                //dc.verwijderVerkocht(g);
                                            //}
                                        } while (g != 999 && teller <= dc.geefIdVerkoopbareKaarten().size() - 1);
                                        System.out.println(" *** Dit zijn de ingegeven ids: " + ids);
                                        System.out.println(" *** Dit zijn de ids van de te verkopen kaarten: " + dc.geefIdVerkoopbareKaarten());
                                        for(int i = 0; i <= ids.size()-1; i++){
                                            System.out.println(dc.getWaardeSchatkaart().get(i));
                                            totWaarde += dc.getWaardeSchatkaart().get(i);
                                        }
                                        for(int i = 0; i <= dc.getAantalKaarten(naam) - 1; i++) {
                                            dc.verwijderVerkocht(ids.get(i));
                                        }
                                        if(totWaarde/1000 < 1){
                                            System.out.println(ColorsOutput.kleur("yellow") + ColorsOutput.decoration("bold") + LanguageResource.getString("usecase7.kleinewaarde") + ColorsOutput.reset());
                                        }else{
                                            int gedeeldeWaarde = totWaarde/1000;
                                            dc.verhoogLevel(naam, gedeeldeWaarde);
                                        }
                                        //voorlopig laten staan in case bovenstaande methode nie blijkt te werken
                                        //if(totWaarde >= 1000 && totWaarde < 2000){
                                        //    dc.verhoogLevel(naam, 1);
                                        //}else if(totWaarde >= 2000 && totWaarde < 3000){
                                        //    dc.verhoogLevel(naam, 2);}
                                        //else if(totWaarde >= 3000 && totWaarde < 4000){
                                        //    dc.verhoogLevel(naam, 3);}
                                        //else if(totWaarde >= 4000 && totWaarde < 5000){
                                        //    dc.verhoogLevel(naam, 4);}
                                        //else if(totWaarde >= 5000 && totWaarde < 6000)
                                        //    dc.verhoogLevel(naam, 5);
                                        //else if(totWaarde >= 6000 && totWaarde < 7000)
                                        //    dc.verhoogLevel(naam, 7);
                                        //else if(totWaarde < 1000)
                                          //  System.out.println(LanguageResource.getString("usecase7.kleinewaarde"));
                                        System.out.println(" *** totale levels stijgen volgens deling: " + totWaarde/1000);
                                        System.out.println(" *** Dit is de totale waarde: " + totWaarde);

                                    }else if(antwoord.equalsIgnoreCase(LanguageResource.getString("usecase7.translationthrow"))){
                                        System.out.println(" *** gekozen voor weggooien -- hardcode, nog aanpassen");
                                        match = true;
                                    }
                                    System.out.println(dc.geefInformatie());
                                }while (match = false);


                        }
                    } catch (Exception e) {
                        System.out.println(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + e.getMessage());
                    }
                    break;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("usecase2.choiceerror");
        }
    }

    public void verwijderVerkocht(int id){

        dc.verwijderVerkocht(id);
    }

}

