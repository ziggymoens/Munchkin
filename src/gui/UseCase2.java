/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;

/**
 *
 * @author ziggy
 */
public class UseCase2 {

    //Declaraties voor gehele usecase.
    private final DomeinController dc;

    /**
     *Constructor voor Use Case 2.
     * @param dc Domeincontroller van het spel aangemaakt in de StartUp
     */
    public UseCase2(DomeinController dc) {
        this.dc = dc;
        speelSpel();
        System.out.println(dc.geefInformatie());
    }

    /**
     *
     */
    public void startUc2() {
        dc.speelSpel();
    }

    /**
     *
     */
    private void speelSpel() {

    }

}
