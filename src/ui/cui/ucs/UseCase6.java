package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;

import java.util.List;

/**
 * Deze UC nummer is onbekend en zal later worden aangepast
 * deze UC zal "Vecht met monster zijn"
 * UC6
 */

class UseCase6 {
    private final DomeinController dc;

    /**
     *
     * @param dc
     */
    UseCase6(DomeinController dc) {
        this.dc = dc;
    }

    /**
     * Methode die de uitkomst van het gevecht bepaalt
     * @param aantalSpelers het aantalspelers in het gehele spel
     */
    void vechtMetMonster(int aantalSpelers) {
        List<Boolean> helptmee = dc.gethelptmee();
        boolean gevecht = dc.gevechtResultaat(dc.getMonsterBattlePoints(), dc.getSpelerBattlePoints());
        int id = dc.geefIdBovensteKaart();
        //int levelMonster = Integer.parseInt(dc.geefMonsterAttribuut(id, "level").toString());
        //Als je het gevecht wint
        if (!gevecht) {
            System.out.println(LanguageResource.getString("usecase6.playerwon"));
            verhoogLevelsGewonnen(id, helptmee);
            geefSchatkaarten(id, aantalSpelers, helptmee);
        //Als je het gevecht verliest
        } else {
            System.out.println(LanguageResource.getString("usecase6.monsterwon"));
            ontsnappen(id);
        }
        UniversalMethods.toonSituatie();
    }

    /**
     * Methode die het aantal levels van de spelers die meehielpen tegen het monster verhoogt
     * @param id van de kaart
     * @param helptmee List met booleans van elke speler of ze meegeholpen hebben in het gevecht of niet
     */
    private void verhoogLevelsGewonnen(int id, List<Boolean> helptmee){
        try {
            int levelsUp = Integer.parseInt(dc.geefMonsterAttribuut(id, "levelsUp").toString());
            for (int i = 0; i < helptmee.size(); i++) {
                if (helptmee.get(i)) {
                    dc.verhoogLevel(dc.geefNaamSpeler(i), levelsUp);
                }
            }
        }catch(Exception e){
            System.out.println(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + LanguageResource.getString("somethingWrong") + ColorsOutput.reset());
        }
    }

    /**
     * Methode die Schatkaarten uitdeelt aan elke speler die meegevochten heeft
     * @param id van de kaart
     * @param aantalSpelers het totaal aantal spelers in het gehele spel
     * @param helptmee List van spelers of ze meehelpen in het gevecht of niet
     */
    private void geefSchatkaarten(int id, int aantalSpelers, List<Boolean> helptmee){
        try {
            int aantal = dc.geefSpelerAanBeurt();
            int aantalSchatkaarten = Integer.parseInt(dc.geefMonsterAttribuut(id, "schatkaarten").toString());
            for (int i = 0; i < aantalSchatkaarten; i++) {
                if (aantal < aantalSpelers) {
                    if (helptmee.get(aantal)) {
                        dc.deelSchatkaartenUit(aantal, aantalSchatkaarten);
                    }
                } else {
                    aantal = -1;
                }
                aantal++;
            }
        }catch(Exception e){
            System.out.println(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + LanguageResource.getString("somethingWrong") + ColorsOutput.reset());
        }
    }

    /**
     * Methode die bepaalt of je mag ontsnappen of niet (zie gooiDobbelsteen)
     * @param id van het monster waarvan de speler weg moet lopen
     */
    private void ontsnappen(int id) {
        try {
            int runAway = Integer.parseInt(dc.geefMonsterAttribuut(id, "RunAway").toString());
            int worp = dc.gooiDobbelsteen();
            System.out.printf(LanguageResource.getString("usecase6.diceroll") + "%n", worp);
            //Speler ontsnapt
            if (worp > 4 - runAway) {
                System.out.println(LanguageResource.getString("usecase6.escape1"));
            }// Speler ontsnapt niet
            else {
                System.out.println(LanguageResource.getString("usecase6.escape2"));
                dc.voerBadStuffUit(id);
            }
        }catch(Exception e){
            System.out.println(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + LanguageResource.getString("somethingWrong") + ColorsOutput.reset());
        }
    }
}
