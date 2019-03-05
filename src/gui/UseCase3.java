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
public class UseCase3 {

    private final DomeinController dc;
    private final UseCase4 uc4;

    UseCase3(DomeinController dc) {
        this.dc = dc;
        uc4 = new UseCase4(this.dc);
    }

    public void speelBeurt(String naam) {
        try {
            System.out.println(dc.geefSpelsituatie());
            dc.speelBeurt(naam);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
