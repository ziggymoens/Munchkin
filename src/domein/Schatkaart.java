/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author ziggy
 */
public abstract class Schatkaart extends Kaart {
    private final int waarde;

    public Schatkaart(String naam, int waarde) {
        super(naam);
        this.waarde = waarde;
    }

    public int getWaarde() {
        return waarde;
    }
}
