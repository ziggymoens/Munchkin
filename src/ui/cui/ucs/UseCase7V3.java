package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;

import java.util.*;

class UseCase7V3 {
    private DomeinController dc;
    private final Map<Integer, Runnable> keuzes = new HashMap<>();
    private final Scanner scan = new Scanner(System.in);
    private String naam;
    private final List<Integer> gekozenKaarten = new ArrayList<>();
    private int totaleWaarde;

    UseCase7V3(DomeinController dc) {
        this.dc = dc;
        keuzes.put(1, this::naarItems);
        keuzes.put(2, this::verkopenEnWeggooien);
        keuzes.put(3, this::niksDoen);
        naam = dc.geefNaamSpeler(dc.geefSpelerAanBeurt());
    }

    void beheerKaarten() {
        gekozenKaarten.clear();
        overzichtOpties();
        int keuze = maakKeuze();
        keuzes.get(keuze).run();
        UniversalMethods.toonSituatie();
    }

    private void overzichtOpties() {
        System.out.println(String.format("%s %s", naam, LanguageResource.getString("usecase7.actions")));
        for (int i = 1; i <= 3; i++) {
            System.out.println(String.format("%d) %s", i, LanguageResource.getString(String.format("usecase7.action%d", i))));
        }
    }

    private int maakKeuze() {
        int keuze = 0;
        try {
            keuze = scan.nextInt();
            if (keuze < 1 || keuze > 3) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println(LanguageResource.getString("usecase2.choiceinput"));
            scan.nextLine();
        }
        return keuze;
    }

    private void niksDoen() {

    }

    private void verkopenEnWeggooien() {
        overzichtVerkopenWeggooien();
        kiesKaarten("WI");
    }

    private void naarItems() {
        overzichtItems();
        kiesKaarten("I");

    }

    private void overzichtVerkopenWeggooien() {
        System.out.printf("%s%n%s%n%s%n%s%n", ColorsOutput.decoration("bold") + String.format("%s:", LanguageResource.getString("usecase7.sellable") + ColorsOutput.reset()), dc.geefVerkoopbareKaarten(naam), ColorsOutput.decoration("bold") + String.format("%s:", LanguageResource.getString("usecase7.throwaway")) + ColorsOutput.reset(), dc.geefNietVerkoopbareKaarten(naam));
        boolean verkopen = UniversalMethods.controleJaNee("usecase7.asktosell");
        if (verkopen){
            kiesKaarten("V");
            controleerWaarden();
            if (totaleWaarde >=1000){
                dc.verkoopKaarten(naam, gekozenKaarten);
            }else{
                System.out.println("te weinig munten, wilt u de kaarten weggooien (ja/nee)");
                boolean weggooien = UniversalMethods.controleJaNee("usecase2.choiceturn");
                if (weggooien){
                    dc.gooiKaartenWeg(naam, gekozenKaarten);
                }
            }
        } else{
            kiesKaarten("W");
            dc.gooiKaartenWeg(naam, gekozenKaarten);
        }
    }

    private void overzichtItems(){
        System.out.printf("%s%n%s%n", ColorsOutput.decoration("bold") + String.format("%s:", LanguageResource.getString("usecase7.toitems")) + ColorsOutput.reset(), dc.geefKaartenKunnenNaarItems(naam));
        boolean items = UniversalMethods.controleJaNee("usecase7.itemsconfirm");
        if (items){
            kiesKaarten("I");
            dc.verplaatsNaarItems(naam, gekozenKaarten);
        }
    }

    private void kiesKaarten(String type){
        List<Integer> mogelijkheden;
        String antw;
        boolean match = true;
        int teller = 0;
        if (type.equals("V") || type.equals("W")){
            mogelijkheden = dc.geefIDKaartenInHand(naam);
            int keuze = 0;
            /*
            do {
                System.out.printf("Geef de %s ID's, na elke ID enter (999 om te stoppen)", type.equals("V")?"te verkopen":"weg te gooien");
                keuze = scan.nextInt();
                if (mogelijkheden.contains(keuze) && keuze != 999){
                    gekozenKaarten.add(keuze);
                    teller++;
                }else if (keuze == 999){
                    System.out.println("Wilt u stoppen?");
                    stop = scan.next();
                    if(stop.equalsIgnoreCase(LanguageResource.getString("yes"))){
                        break;
                    }
                }else{
                    System.out.println("foute keuze");
                }
            }while (keuze != 999 && teller < mogelijkheden.size() - 1);


             */
            //code uc7v1

            do {
                System.out.println(LanguageResource.getString("usecase7.sellorthrow"));
                antw = scan.next();
                if (antw.equalsIgnoreCase(LanguageResource.getString("usecase7.translationsell"))) {
                    match = false;
                    System.out.println(LanguageResource.getString("usecase7.sell"));
                    System.out.println(String.format("%s: %n%s", LanguageResource.getString("usecase7.sellable"), dc.geefVerkoopbareKaarten(naam)));
                    scan.nextLine();
                    do {
                        System.out.println(LanguageResource.getString("usecase7.whattosell"));
                        keuze = scan.nextInt();
                        if (mogelijkheden.contains(keuze) && keuze != 999) {
                            gekozenKaarten.add(keuze);
                            teller++;
                        } else if (!mogelijkheden.contains(keuze) && keuze != 999) {
                            System.out.println(LanguageResource.getString("usecase7.foutid"));
                        }

                        match = false;
                    } while (keuze != 999 && teller <= mogelijkheden.size() - 1);
                    // System.out.println(ColorsOutput.kleur("white") + ColorsOutput.decoration("bold") + ColorsOutput.achtergrond("red") + " *** totale levels stijgen volgens deling: " + totWaarde/1000 + ColorsOutput.reset());
                    // System.out.println(" *** Dit is de totale waarde: " + totWaarde);

                } else if (antw.equalsIgnoreCase(LanguageResource.getString("usecase7.translationthrow"))) {
                    System.out.println(" *** gekozen voor weggooien -- hardcode, nog aanpassen");
                    System.out.println(LanguageResource.getString("usecase7.throw"));

                    System.out.println(dc.geefNietVerkoopbareKaarten(naam));
                    match = false;
                } else
                    match = true;
            } while (match);



        }else if(type.equals("I")){
            mogelijkheden = dc.geefIdsKunnenNaarItems(naam);
            int keuze = 0;
            do {
                System.out.println("Geef de te verplaatsen ID's, na elke ID enter (999 om te stoppen)");
                keuze = scan.nextInt();
                if (mogelijkheden.contains(keuze) && keuze != 999) {
                    gekozenKaarten.add(keuze);
                    teller++;
                } else if (keuze == 999) {
                } else {
                    System.out.println("foute keuze");
                }
            }while (keuze!=999 && teller < mogelijkheden.size() - 1);
        }
    }

    private void controleerWaarden(){
        totaleWaarde = 0;
        for(Integer id: gekozenKaarten){
            if (dc.getWaardeSchatkaart(id)==999){

                System.out.println("ERROR");
                break;
            }
            totaleWaarde += dc.getWaardeSchatkaart(id);
        }
    }
}
