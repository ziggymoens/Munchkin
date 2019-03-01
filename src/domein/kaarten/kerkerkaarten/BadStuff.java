/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein.kaarten.kerkerkaarten;

/**
 *
 * @author ziggy
 */
public class BadStuff {

    private String naam;
    private String text;
    private int levelsLost;
    private String gearLost;
    private int itemsTaken;
    private boolean weaponsLost;
    private boolean raceLost;
    private boolean dead;
    private String cardsLost;

    private boolean lawyersCard;
    private boolean orgsCard;
    private boolean ghoulfriends;
    private boolean snailsOnSpeed;
    private boolean wrightBrothers;

    public BadStuff(String naam, String text, int levelsLost) {
        setNaam(naam);
        setText(text);
        setLevelsLost(levelsLost);
    }

    public BadStuff(String naam, String gearLost) {
        setNaam(naam);
        setGearLost(gearLost);
    }

    public BadStuff(String naam, int itemstaken) {
        setNaam(naam);
        setItemsTaken(itemstaken);
    }

    public BadStuff(String naam, boolean weaponsLost, boolean raceLost) {
        setNaam(naam);
        setWeaponsLost(weaponsLost);
        setRaceLost(raceLost);
    }

    public BadStuff(String naam) {
        setNaam(naam);
    }

    public BadStuff(String naam, boolean weaponsLost, boolean raceLost, boolean dead) {
        setNaam(naam);
        setWeaponsLost(weaponsLost);
        setRaceLost(raceLost);
        setDead(dead);
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

    public final void setText(String text) {
        this.text = text;
    }

    public final void setLevelsLost(int levelsLost) {
        this.levelsLost = levelsLost;
    }

    public final void setGearLost(String gearLost) {
        this.gearLost = gearLost;
    }

    public final void setItemsTaken(int itemsTaken) {
        this.itemsTaken = itemsTaken;
    }

    public final void setWeaponsLost(boolean weaponsLost) {
        this.weaponsLost = weaponsLost;
    }

    public final void setRaceLost(boolean raceLost) {
        this.raceLost = raceLost;
    }

    public final void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setCardsLost(String cardsLost) {
        this.cardsLost = cardsLost;
    }

    public void setLawyersCard(boolean lawyersCard) {
        this.lawyersCard = lawyersCard;
    }

    public void setOrgsCard(boolean orgsCard) {
        this.orgsCard = orgsCard;
    }

    public void setGhoulfriends(boolean ghoulfriends) {
        this.ghoulfriends = ghoulfriends;
    }

    public void setSnailsOnSpeed(boolean snailsOnSpeed) {
        this.snailsOnSpeed = snailsOnSpeed;
    }

    public void setWrightBrothers(boolean wrightBrothers) {
        this.wrightBrothers = wrightBrothers;
    }
    
    
}
