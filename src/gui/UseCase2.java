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
    private final DomeinController dc;

    public UseCase2(DomeinController dc) {
        this.dc = dc;
        dc.speelSpel();
        System.out.println(dc.geefInformatie());
    }


}
