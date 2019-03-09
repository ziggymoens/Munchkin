/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;

/**
 * @author ziggy
 */
public class UseCase4 {
    private final DomeinController dc;

    UseCase4(DomeinController dc) {
        this.dc = dc;
    }

    private void spel(){
        dc.geefInformatie();
    }
}
