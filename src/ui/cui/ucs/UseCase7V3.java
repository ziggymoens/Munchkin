package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;

import java.util.*;

public class UseCase7V3 {
    private DomeinController dc;
    private final Map<Integer, Runnable> keuzes = new HashMap<>();
    private final Scanner scan = new Scanner(System.in);
    private String naam;

    private final List<Integer> gekozenKaarten = new ArrayList<>();

    public UseCase7V3(DomeinController dc) {
        this.dc = dc;
        keuzes.put(1, this::naarItems);
        keuzes.put(2, this::verkopenEnWeggooien);
        keuzes.put(3, this::niksDoen);
        naam = dc.geefNaamSpeler(dc.geefSpelerAanBeurt());
    }

    public void beheerKaarten() {
        overzichtOpties();
        int keuze = maakKeuze();
        keuzes.get(keuze).run();
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
        System.out.printf("%s%n%s%n%s%n%s%n%s%n", ColorsOutput.decoration("bold") + String.format("%s:", LanguageResource.getString("usecase7.sellable") + ColorsOutput.reset()), dc.geefVerkoopbareKaarten(naam), ColorsOutput.decoration("bold") + String.format("%s:", LanguageResource.getString("usecase7.throwaway")) + ColorsOutput.reset(), dc.geefNietVerkoopbareKaarten(naam), LanguageResource.getString("usecase7.asktosell"));
    }

    private void overzichtItems(){
        System.out.printf("%s%n%s%n", ColorsOutput.decoration("bold") + String.format("%s:", LanguageResource.getString("usecase7.toitems")) + ColorsOutput.reset(), dc.geefKaartenKunnenNaarItems(naam), LanguageResource.getString(""));
        boolean items = UniversalMethods.controleJaNee("usecase7.itemsconfirm");
        if (items){
            kiesKaarten("I");
        }
    }

    private void kiesKaarten(String type){
        if (type.equals("VW")){

        }else if(type.equals("I")){


        }

    }
}
