/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.test;

import domein.DomeinController;
import domein.Spel;
import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;
import domein.repositories.KaartDbRepository;
import language.LanguageResource;
import persistentie.mappers.PersistentieController;

import java.util.List;
import java.util.Locale;

/**
 * test github intellij kilian
 * @author ziggy
 */
public class Test {

    //i18n testen
    private final LanguageResource bundle = new LanguageResource();
    private final KaartDbRepository kr = new KaartDbRepository();
    private final List<Kaart> sk = kr.getSchatkaarten();
    private final List<Kaart> kk = kr.getKerkerkaarten();
    private final DomeinController dc = new DomeinController();

    public static void main(String[] args) {
        PersistentieController pc = new PersistentieController();
        LanguageResource.setLocale(new Locale("en"));
        Spel spel = new Spel(3);
        spel.maakNieuweSpeler();
        spel.geefSpelerNaam(0, "janjan");
        spel.geefSpelerGeslacht(0, "man");
        spel.maakNieuweSpeler();
        spel.geefSpelerNaam(1, "mariee");
        spel.geefSpelerGeslacht(1, "woman");
        spel.maakNieuweSpeler();
        spel.geefSpelerNaam(2, "zigggy");
        spel.geefSpelerGeslacht(2, "man");
        spel.controleerVolgorde();
        spel.geefStartKaarten();
        spel.setNaam("test123");

        pc.spelOpslaan(spel);

        //SpelMapperDb sm = new SpelMapperDb();
        //sm.addSpel("test1234", 0, true);
        //System.out.println(test.geefKaarten());
    }
    
    private String geefKaarten(){
        int k = 0, s=0;
        int ck=0,c=0,m=0,r=0,cs=0,e=0;
        StringBuilder out = new StringBuilder();
        for (Kaart kaart: sk) {
            out.append(String.format("%5d:%40s -->%20s%n",kaart.getId(), kaart.getNaam(), kaart.getClass().getSimpleName()));
            if (kaart instanceof ConsumablesSchat){
                cs++;
            }
            if (kaart instanceof Equipment){
                e++;
            }
            s++;
        }
        for (Kaart kaart: kk){
            out.append(String.format("%5d:%40s -->%20s%n",kaart.getId(), kaart.getNaam(), kaart.getClass().getSimpleName()));
            if (kaart instanceof ConsumablesKerker){
                ck++;
            }
            if (kaart instanceof Curse){
                c++;
            }
            if (kaart instanceof Monster){
                m++;
            }
            if (kaart instanceof Race){
                r++;
            }
            k++;
        }
        System.err.printf("cs = %d, e = %d, ck = %d, c = %d, m = %d, r = %d, schat = %d, kerker = %d%n", cs, e, ck, c, m, r, s, k);
        return out.toString();
    }
}
