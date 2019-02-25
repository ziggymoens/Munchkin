/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein.kaarten;

/**
 *
 * @author ziggy
 */
public class BadStuff {

    private String naam;
    private String uitleg;
    private int levelsLost;
    private String gearLost;
    private int itemstaken;
    private boolean weaponsLost;
    private boolean raceLost;
    private boolean dead;
    private String cardsLost;

    private boolean lawyersCard;
    private boolean orgsCard;
    private boolean ghoulfriends;
    private boolean snailsOnSpeed;
    private boolean wrightBrothers;

    public BadStuff(String naam, String uitleg, int levelsLost) {
        setNaam(naam);
        this.uitleg = uitleg;
        this.levelsLost = levelsLost;
    }

    public BadStuff(String naam, String gearLost) {
        setNaam(naam);
        this.gearLost = gearLost;
    }

    public BadStuff(String naam, int itemstaken) {
        setNaam(naam);
        this.itemstaken = itemstaken;
    }

    public BadStuff(String naam, boolean weaponsLost, boolean raceLost) {
        setNaam(naam);
        this.weaponsLost = weaponsLost;
        this.raceLost = raceLost;
    }

    public BadStuff(String naam) {
        setNaam(naam);
    }

    public BadStuff(String naam, boolean weaponsLost, boolean raceLost, boolean dead) {
        setNaam(naam);
        this.weaponsLost = weaponsLost;
        this.raceLost = raceLost;
        this.dead = dead;
    }

    private void setNaam(String naam) {
        String check = naam.toLowerCase();
        switch (check) {
            case "orgs":
                orgsCard = true;
                break;
            case "lawyers":
                lawyersCard = true;
                break;
            case "ghoulfriends":
                ghoulfriends = true;
                break;
            case "snails on speed":
                snailsOnSpeed = true;
                break;
            case "wright brothers":
                wrightBrothers = true;
                break;
            default:
                break;
        }
        this.naam = naam;
    }
}
