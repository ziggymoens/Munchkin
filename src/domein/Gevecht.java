package domein;

import java.util.List;

public class Gevecht {
    private String help;
    private List<Boolean> helptmee;
    private int monsterBattlePoints;
    private int spelerBattlePoints;
    private int spelerAanBeurt;

    public Gevecht(){

    }
    //Getters

    /**
     * Getter die teruggeeft of de speler hulp wilt tegen een monster
     * @return String ja of nee in juiste taal
     */
    public String getHelp() {
        return help;
    }

    /**
     * Getter die Spelers teruggeeft die meehelpen tegen het monster
     * @return Lijst van booleans per speler of ze meehelpen tegen het monster of niet
     */
    public List<Boolean> getHelptmee(){ return helptmee; }

    /**
     * Getter die teruggeeft hoeveel de monsterzijde in totaal aan levels heeft op het einde van het gevecht
     * @return List van spelers die meehelpen in het gevecht
     */
    public int getMonsterBattlePoints() {
        return monsterBattlePoints;
    }

    /**
     * Getter die teruggeeft hoeveel de Spelerzijde in totaal aan levels heeft op het einde van het gevecht
     * @return getal hoeveel dat de Monserzijde aan totale levels heeft
     */
    public int getSpelerBattlePoints() {
        return spelerBattlePoints;
    }

    /**
     * Getter die de speler aan beurt binnenin het gevecht teruggeeft (hang lost van de algemene spelerAanBeurt die in Spel staat)
     * @return getal hoeveel dat de Spelerzijde aan totale levels heeft
     */
    public int getSpelerAanBeurt() {
        return spelerAanBeurt;
    }

    //Setters

    /**
     * Setter die set of de speler hulp wilt tijdens het gevecht
     * @param help Aan wat het attribuut help gelijk moet staan
     */
    public void setHelp(String help) {
        this.help = help;
    }

    /**
     * Setter die de lijst set met booleans per speler of ze meehlepen tegen het monster of niet
     * @param helptmee lijst die meegegeven wordt om het attribuut helptmee te setten
     */
    public void setHelptmee(List<Boolean> helptmee){
        this.helptmee = helptmee;
    }

    /**
     * Setter die het totaal aantal levels aan de monsterzijde set
     * @param monsterBattlePoints aantal dat het attribuut monsterBattlePoints moet zijn
     */

    public void setMonsterBattlePoints(int monsterBattlePoints) {
        this.monsterBattlePoints = monsterBattlePoints;
    }

    /**
     * Setter die het totaal aantal levels aan de spelerzijde set
     * @param spelerBattlePoints aantal dat het attribuut monsterBattlePoints moet zijn
     */

    public void setSpelerBattlePoints(int spelerBattlePoints) {
        this.spelerBattlePoints = spelerBattlePoints;
    }

    /**
     * Setter die set welke speler er aan beurt is binnennin het gevecht hangt los van de algemene speleraanbeurt in Spel
     * @param spelerAanBeurt de speler die op dat moment aan de beurt is
     */
    public void setSpelerAanBeurt(int spelerAanBeurt) {
        this.spelerAanBeurt = spelerAanBeurt;
    }

}
