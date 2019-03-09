/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;
import domein.repositories.KaartDbRepository;
import java.util.List;
import language.LanguageResource;

/**
 *
 * @author ziggy
 */
public class Test {

    //i18n testen
    private final LanguageResource bundle = new LanguageResource();
    private final KaartDbRepository kr = new KaartDbRepository();
    private final List<Kaart> sk = kr.getSchatkaarten();
    private final List<Kaart> kk = kr.getKerkerkaarten();

    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.geefKaarten());
    }
    
    private String geefKaarten(){
        int k = 0, s=0;
        int ck=0,c=0,m=0,r=0,cs=0,e=0;
        String out = "";
        for (Kaart kaart: sk) {
            out += String.format("%s%n", kaart.getNaam());
            if (kaart instanceof ConsumablesSchat){
                cs++;
            }
            if (kaart instanceof Equipment){
                e++;
            }
            s++;
        }
        for (Kaart kaart: kk){
            out += String.format("%s%n", kaart.getNaam());
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
        return out;
    }
}
