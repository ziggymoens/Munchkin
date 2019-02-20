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
public class Kaart {
    private  String naam;
    private  String type;
    private  String subtype;

    public Kaart(String naam, String type, String subtype) {
        setNaam(naam);
        setType(type);
        setSubtype(subtype);
    }

    public final void setNaam(String naam) {
        this.naam = naam;
    }

    public final void setType(String type) {
        this.type = type;
    }

    public final void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getNaam() {
        return naam;
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }
    
    
}
