package main;

import domein.DomeinController;
import gui.UseCase1;
import gui.UseCase2;

/**
 *
 * @author g35
 */
public class StartUp {
    //class die gebruikt wordt om use cases uit te voeren
    public static void main(String[] args) {
        DomeinController dc = new DomeinController();
        new UseCase1(dc);
        new UseCase2(dc);
    }
}
