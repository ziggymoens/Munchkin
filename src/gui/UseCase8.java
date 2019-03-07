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
public class UseCase8 {

    private DomeinController dc;

    public UseCase8(DomeinController dc) {
        this.dc = dc;
    }

    public void spelOpslaan(){
        dc.spelOpslaan();
    }
}
