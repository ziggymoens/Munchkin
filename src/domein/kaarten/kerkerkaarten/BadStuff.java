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
     * @param id
     * @param itemsLost
     * @param levelsLost
     * @param gearLost
     * @param text
     */
    public BadStuff(int id, int itemsLost, int levelsLost, String gearLost, String text) {
        //setNaam(naam);
        setId(id);
        setItemsTaken(itemsLost);
        setLevelsLost(levelsLost);
        setGearLost(gearLost);
        setText(text);
    }

    /**
     *
     * @param naam
     */
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

    /**
     *
     * @param id
     */
    private void setId(int id) {
        if (id < 0) {
            throw new BadStuffException("exception.badstuff.id");
        }
        this.id = id;
    }

    /**
     *
     * @param text
     */
    private void setText(String text) {
        if (text == null || text.isBlank()) {
            throw new BadStuffException("exception.badstuff.text");
        }
        this.text = text;
    }

    /**
     *
     * @param levelsLost
     */
    private void setLevelsLost(int levelsLost) {
        if (levelsLost < 0) {
            throw new BadStuffException("exception.badstuff.levelslost");
        }
        this.levelsLost = levelsLost;
    }

    /**
     *
     * @param gearLost
     */
    private void setGearLost(String gearLost) {
        if (gearLost == null || gearLost.isBlank()) {
            throw new BadStuffException("exception.badstuff.gearlost");
        }
        this.gearLost = gearLost;
    }

    /**
     *
     * @param itemsTaken
     */
    private void setItemsTaken(int itemsTaken) {
        if (itemsTaken < 0) {
            throw new BadStuffException("exception.badstuff.itemstaken");
        }
        this.itemsTaken = itemsTaken;
    }

    /**
     *
     * @return
     */
    public String getNaam() {
        return naam;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @return
     */
    public int getLevelsLost() {
        return levelsLost;
    }

    /**
     *
     * @return
     */
    public String getGearLost() {
        return gearLost;
    }

    /**
     *
     * @return
     */
    public int getItemsTaken() {
        return itemsTaken;
    }

    /**
     *
     * @return
     */
    public boolean isLawyersCard() {
        return lawyersCard;
    }

    /**
     *
     * @return
     */
    public boolean isOrgsCard() {
        return orgsCard;
    }

    /**
     *
     * @return
     */
    public boolean isGhoulfriends() {
        return ghoulfriends;
    }

    /**
     *
     * @return
     */
    public boolean isSnailsOnSpeed() {
        return snailsOnSpeed;
    }

    /**
     *
     * @return
     */
    public boolean isWrightBrothers() {
        return wrightBrothers;
    }
}

