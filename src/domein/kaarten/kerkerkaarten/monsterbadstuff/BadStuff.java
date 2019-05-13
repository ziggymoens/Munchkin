/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein.kaarten.kerkerkaarten.monsterbadstuff;

import exceptions.kaarten.kerkerkaarten.monsterbadstuff.BadStuffException;

/**
 * NAKIJKEN
 *
 * @author ziggy
 */
@SuppressWarnings("unused")
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
     * Constructor voor badStuff
     *
     * @param id         id van het bijhorende monster
     * @param itemsLost  hoeveel items verloren gaan
     * @param levelsLost hoeveel levels verloren gaan
     * @param gearLost   welk type gear verloren gaat
     * @param text       bijhorende text van de kaart
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
     * Constructor voor badstuff
     * <p>
     * KLEINE DB
     *
     * @param id                  id van het bijhorende monster
     * @param loseLevels          hoeveel levels verloren gaan
     * @param badStuffDescription bijhorende text
     */
    public BadStuff(int id, int loseLevels, String badStuffDescription) {
        setId(id);
        setLevelsLost(loseLevels);
        setText(badStuffDescription);
    }

    @Override
    public String toString() {
        return String.format("Badstuff: %s ,%s", text, levelsLost == 0 ? "escape is automatic" : String.format("levels lost:%d", levelsLost));
    }

    /**
     * Setter voor de naam die gebruik maakt van een switch om de juiste naam kaart te selecteren
     *
     * @param naam de naam van de kaart
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
     * Setter die id initialiseert als deze positief is
     *
     * @param id de id van het bijhorende monster
     */
    private void setId(int id) {
        if (id < 0) {
            throw new BadStuffException("exception.badstuff.id");
        }
        this.id = id;
    }

    /**
     * Setter die tekst initialiseert als deze niet null is
     *
     * @param text de text bij de kaart
     */
    private void setText(String text) {
        if (text == null) {
            throw new BadStuffException("exception.badstuff.text");
        }
        this.text = text;
    }

    /**
     * Setter die aantal levelLost zet als deze positief is
     *
     * @param levelsLost hoeveel levels verloren worden
     */
    private void setLevelsLost(int levelsLost) {
        if (levelsLost < 0) {
            throw new BadStuffException("exception.badstuff.levelslost");
        }
        this.levelsLost = levelsLost;
    }

    /**
     * Setter die gearLost initialiseert wanneer deze niet null is
     *
     * @param gearLost welk type gear verloren gaat
     */
    private void setGearLost(String gearLost) {
        if (gearLost == null) {
            throw new BadStuffException("exception.badstuff.gearlost");
        }
        this.gearLost = gearLost;
    }

    /**
     * Setter die aantal itemsTaken initialiseert wanneer deze positief is
     *
     * @param itemsTaken hoeveel items de baddstuff afneemt
     */
    private void setItemsTaken(int itemsTaken) {
        if (itemsTaken < 0) {
            throw new BadStuffException("exception.badstuff.itemstaken");
        }
        this.itemsTaken = itemsTaken;
    }

    /**
     * Getter die de naam teruggeeft
     *
     * @return String van naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Getter die id teruggeeft
     *
     * @return int van id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter die text teruggeeft
     *
     * @return String text
     */
    public String getText() {
        return text;
    }

    /**
     * Getter die aantal levelsLost teruggeeft
     *
     * @return int aantal levelsLost
     */
    public int getLevelsLost() {
        return levelsLost;
    }

    /**
     * Getter die String van welke gear verloren wordt, teruggeeft
     *
     * @return String gearLost
     */
    public String getGearLost() {
        return gearLost;
    }

    /**
     * Getter die het aantal itemsTaken teruggeeft
     *
     * @return int van itemsTaken
     */
    public int getItemsTaken() {
        return itemsTaken;
    }

    /**
     * Boolean die zegt of de kaart een LawyersCard is of niet
     *
     * @return true = isLawyersCard, false = geen LawyersCard
     */
    public boolean isLawyersCard() {
        return lawyersCard;
    }

    /**
     * Boolean die zegt of de kaart een OrgsCard is of niet
     *
     * @return true = OrgsCard, false = geen OrgsCard
     */
    public boolean isOrgsCard() {
        return orgsCard;
    }

    /**
     * Boolean die zegt of de kaart een Ghoulfriends is of niet
     *
     * @return true = Ghoulfriends, false = geen Ghoulfriends
     */
    public boolean isGhoulfriends() {
        return ghoulfriends;
    }

    /**
     * Boolean die zegt of de kaart een isSnailsOnSpeed is of niet
     *
     * @return true = isSnailsOnSpeed, false = geen isSnailsOnSpeed
     */
    public boolean isSnailsOnSpeed() {
        return snailsOnSpeed;
    }

    /**
     * Boolean die zegt of de kaart een WrightBrothers is of niet
     *
     * @return true = isWrightBrothers, false = geen WrightBrothers
     */
    public boolean isWrightBrothers() {
        return wrightBrothers;
    }
}

