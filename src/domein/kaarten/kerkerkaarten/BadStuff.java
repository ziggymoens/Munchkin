/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein.kaarten.kerkerkaarten;

import exceptions.kaarten.BadStuffException;

/**
 * NAKIJKEN
 *
 * @author ziggy
 */
public class BadStuff {

    private String naam;
    private int id;
    private String text;
    private int levelsLost;
    private String gearLost;
    private int itemsTaken;

    private boolean lawyersCard = false;
    private boolean orgsCard = false;
    private boolean ghoulfriends = false;
    private boolean snailsOnSpeed = false;
    private boolean wrightBrothers = false;
/**
 * 
 * @param naam
 * @param id
 * @param itemsLost
 * @param levelsLost
 * @param gearLost
 * @param text 
 */
    public BadStuff(String naam, int id, int itemsLost, int levelsLost, String gearLost, String text) {
        setNaam(naam);
        setId(id);
        setItemsTaken(itemsLost);
        setLevelsLost(levelsLost);
        setGearLost(gearLost);
        setText(text);
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

    private void setId(int id) {
        if (id < 0) {
            throw new BadStuffException("exception.badstuff.id");
        }
        this.id = id;
    }

    private void setText(String text) {
        if (text == null || text.isBlank()) {
            throw new BadStuffException("exception.badstuff.text");
        }
        this.text = text;
    }

    private void setLevelsLost(int levelsLost) {
        if (levelsLost < 0) {
            throw new BadStuffException("exception.badstuff.levelslost");
        }
        this.levelsLost = levelsLost;
    }

    private void setGearLost(String gearLost) {
        if (gearLost == null || gearLost.isBlank()) {
            throw new BadStuffException("exception.badstuff.gearlost");
        }
        this.gearLost = gearLost;
    }

    private void setItemsTaken(int itemsTaken) {
        if (itemsTaken < 0) {
            throw new BadStuffException("exception.badstuff.itemstaken");
        }
        this.itemsTaken = itemsTaken;
    }

}
//    public BadStuff(String naam, String text, int levelsLost) {
//        setNaam(naam);
//        setText(text);
//        setLevelsLost(levelsLost);
//    }
//
//    public BadStuff(String naam, String gearLost) {
//        setNaam(naam);
//        setGearLost(gearLost);
//    }
//
//    public BadStuff(String naam, int itemstaken) {
//        setNaam(naam);
//        setItemsTaken(itemstaken);
//    }
//
//    public BadStuff(String naam, boolean weaponsLost, boolean raceLost) {
//        setNaam(naam);
//        setWeaponsLost(weaponsLost);
//        setRaceLost(raceLost);
//    }
//
//    public BadStuff(String naam) {
//        setNaam(naam);
//    }
//
//    public BadStuff(String naam, boolean weaponsLost, boolean raceLost, boolean dead) {
//        setNaam(naam);
//        setWeaponsLost(weaponsLost);
//        setRaceLost(raceLost);
//        setDead(dead);
//    }
//    private void setWeaponsLost(boolean weaponsLost) {
//        this.weaponsLost = weaponsLost;
//    }
//
//    private void setRaceLost(boolean raceLost) {
//        this.raceLost = raceLost;
//    }
//
//    private void setDead(boolean dead) {
//        this.dead = dead;
//    }
//
//    private void setCardsLost(String cardsLost) {
//        this.cardsLost = cardsLost;
//    }

