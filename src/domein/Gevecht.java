package domein;
import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.kerkerkaarten.monsterbadstuff.BadStuff;
import domein.kaarten.schatkaarten.Equipment;
import language.LanguageResource;

import java.util.List;

public class Gevecht {
    private String help;
    private List<Boolean> helptmee;
    private int monsterBattlePoints;
    private int spelerBattlePoints;
    private int spelerAanBeurt;

    public Gevecht(){

    }
    //Methoden






    //Getters
    public String getHelp() {
        return help;
    }
    public List<Boolean> gethelptmee(){ return helptmee; }

    public int getMonsterBattlePoints() {
        return monsterBattlePoints;
    }

    public int getSpelerBattlePoints() {
        return spelerBattlePoints;
    }

    public int getSpelerAanBeurt() {
        return spelerAanBeurt;
    }

    //Setters
    public void setHelp(String help) {
        this.help = help;
    }

    public void setHelptmee(List<Boolean> helptmee){
        this.helptmee = helptmee;
    }

    public void setMonsterBattlePoints(int monsterBattlePoints) {
        this.monsterBattlePoints = monsterBattlePoints;
    }

    public void setSpelerBattlePoints(int spelerBattlePoints) {
        this.spelerBattlePoints = spelerBattlePoints;
    }

    public void setSpelerAanBeurt(int spelerAanBeurt) {
        this.spelerAanBeurt = spelerAanBeurt;
    }

}
