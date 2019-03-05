/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.kaarten.Kaart;
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

    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.geefKaarten());
    }
    
    private String geefKaarten(){
        String out = "";
        List<Kaart> kaarten = kr.geefSpelers();
        for (Kaart kaart: kaarten) {
            out += String.format("%s%n", kaart.getNaam());
        }
        return out;
    }
}
