package domein;

import connection.Connection;
import domein.kaarten.Kaart;
import domein.kaarten.Schatkaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.kerkerkaarten.monsterbadstuff.BadStuff;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;
import domein.repositories.KaartDbKleinRepository;
import domein.repositories.KaartRepository;
import exceptions.SpelException;
import language.LanguageResource;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ziggy
 */
public class Spel {

    //private PersistentieController pc;
    private KaartDbKleinRepository kr;
    private KaartRepository krOffline;
    private int spelerAanBeurt;
    private int aantalSpelers;
    private final List<Speler> spelers;
    private Map<Integer, Kaart> kaarten;
    private List<Kaart> schatkaarten;
    private List<Integer> volgordeT;
    private List<Kaart> kerkerkaarten;
    private List<Integer> volgordeD;
    private String naam;
    private boolean klein = true;
    private Gevecht gevecht = new Gevecht();

    /**
     * Constructor van Spel zonder parameters spelers = 3
     */
    public Spel() {
        this(3);
    }

    /**
     * Constructor van Spel
     *
     * @param aantalSpelers Het gewenste aantal spelers
     */
    public Spel(int aantalSpelers) {
        setAantalSpelers(aantalSpelers);
        spelers = new ArrayList<>();
        if (Connection.isConnected()) {
            kr = new KaartDbKleinRepository();
            //pc = new PersistentieController();
            //kaarten = new HashMap<>();
            schatkaarten = kr.getSchatkaarten();
            kerkerkaarten = kr.getKerkerkaarten();
        } else {
            krOffline = new KaartRepository();
            schatkaarten = krOffline.getSchatkaarten();
            kerkerkaarten = krOffline.getKerkerkaarten();
        }
        volgordeD = new ArrayList<>();
        volgordeT = new ArrayList<>();
        maakKaartenBib();
        updateVolgorde();
        setSpelerAanBeurt(0);
    }

    public Spel(String naam, boolean klein, List<Speler> spelers, int ls, List<Integer> volgnummerD, List<Integer> volgnummerT) {
        kr = new KaartDbKleinRepository();
        krOffline = new KaartRepository();
        setAantalSpelers(spelers.size());
        setNaam(naam);
        this.spelers = spelers;
        setSpelerAanBeurt(ls);
        //pc = new PersistentieController();
//        kaarten = new HashMap<>();
//        schatkaarten = pc.getSchatkaarten();
//        for (Kaart kaart : pc.getSchatkaarten()) {
//            kaarten.put(kaart.getId(), kaart);
//        }
//        kerkerkaarten = pc.getKerkerkaarten();
//        for (Kaart kaart : pc.getKerkerkaarten()) {
//            kaarten.put(kaart.getId(), kaart);
//        }
        this.volgordeT = volgnummerT;
        this.volgordeD = volgnummerD;
        kaarten = new HashMap<>();
        maakKaartenBib();
    }

    private void maakKaartenBib() {
        if (Connection.isConnected()) {
            kaarten = kr.getKaartenBib();
        } else {
            kaarten = krOffline.getKaartenBib();
        }
    }

    /**
     * Setter voor aantal spelers met controle op aantal volgens
     * DR_AANTAL_SPELERS
     *
     * @param aantalSpelers Het gekozen aantal spelers
     */
    private void setAantalSpelers(int aantalSpelers) {
        if (aantalSpelers < 3 || aantalSpelers > 6) {
            throw new SpelException("exception.spel.players");
        }
        this.aantalSpelers = aantalSpelers;
    }

    /**
     * Geeft elke speler het startlevel 1
     */
    public void startLevels() {
        for (Speler speler : spelers) {
            speler.setLevel(1);
        }
    }

    /**
     * Geeft info over het spel en de spelers
     *
     * @return String Array met de info
     */
    public String[] geefInfo() {
        String[] ret = new String[spelers.size()];
        for (int i = 0; i < spelers.size(); i++) {
            ret[i] = spelers.get(i).toString();
        }
        return ret;
    }

    /**
     * Voeg een kaart toe aan de hand van een speler
     *
     * @param kaart    De kaart voor de speler
     * @param spelernr De nummer van de speler in het spel, volgens volgorde van
     *                 ingeven
     */
    public void voegKaartToe(Kaart kaart, int spelernr) {
        spelers.get(spelernr).voegKaartToe(kaart);
    }

    /**
     * Geeft de spelers van het spel terug
     *
     * @return List van Speler-objecten
     */
    public List<Speler> getSpelers() {
        return spelers;
    }

    /**
     * @param naam
     */
    private void controleSpeler(String naam) {
        for (Speler speler : spelers) {
            if (naam.equals(speler.getNaam())) {
                throw new SpelException("exception.spel.namenotunique");
            }
        }
    }

    /**
     * @param speler
     */
    private void geefStartKaarten(Speler speler) {
        speler.voegKaartToe(schatkaarten.get(0));
        schatkaarten.remove(0);
        speler.voegKaartToe(kerkerkaarten.get(0));
        kerkerkaarten.remove(0);
        speler.voegKaartToe(schatkaarten.get(0));
        schatkaarten.remove(0);
        speler.voegKaartToe(kerkerkaarten.get(0));
        kerkerkaarten.remove(0);
    }

    /**
     * @return
     */
    public int getAantalSpelers() {
        return aantalSpelers;
    }

    /**
     *
     */
    public void controleerVolgorde() {
        Speler speler = spelers.get(0);
        for (Speler speler1 : spelers) {
            if (speler.getNaam().toLowerCase().length() > speler1.getNaam().toLowerCase().length()) {
                speler = speler1;
            } else if (speler.getNaam().toLowerCase().length() == speler1.getNaam().toLowerCase().length()) {
                if (speler.getNaam().compareToIgnoreCase(speler1.getNaam()) <= 0) {
                    speler = speler1;
                }
            }
        }
        spelers.remove(speler);
        spelers.add(0, speler);
        int id = 0;
        for (Speler sp : spelers) {
            sp.setSpelerId(id);
            id++;
        }
    }

    /**
     * @return
     */
    public String geefWinnaar() {
        String naam = "";
        for (Speler speler : spelers) {
            if (speler.getLevel() == 10) {
                naam = speler.getNaam();
                break;
            }
        }
        return naam;
    }

    /**
     * @param speler
     */
    public void geefLevel(Speler speler) {
        if (speler.getLevel() == 9 && speler.heeftMonsterVerslaan()) {
            speler.setLevel(speler.getLevel() + 1);
        } else if (speler.getLevel() >= 1 && speler.getLevel() < 9) {
            speler.setLevel(speler.getLevel() + 1);
        }
    }

    /**
     * @return
     */
    public List<String> geefSpelsituatie() {
        List<String> ret = new ArrayList<>();
        for (Speler speler : spelers) {
            //items naar string weg
            ret.add(String.format("%s: %s, %s: %s, %s: %d, %s: %s%n", LanguageResource.getString("player.name"), speler.getNaam(), LanguageResource.getString("player.sex"), speler.getGeslacht(), LanguageResource.getString("player.level"), speler.getLevel(), LanguageResource.getString("player.items"), speler.kaartenNaarString(speler.getItems())));
        }
        return ret;
    }

    public List<String> geefBeknopteSpelsituatie(/*boolean vecht*/) {
        List<String> ret = new ArrayList<>();
        for (Speler speler : spelers) {
            ret.add(String.format("%s: %s, %s: %s, %s: %d", LanguageResource.getString("player.name"), speler.getNaam(), LanguageResource.getString("player.sex"), speler.getGeslacht(), LanguageResource.getString("player.level"), speler.getLevel() /*, vecht?"Vecht mee":"Vecht niet mee"*/));
        }
        return ret;
    }

    /*public List<String> geeBeknopteSpelsituatieUC5(){

    }*/

    /**
     * @return
     */
    public boolean niemandGewonnen() {
        boolean ret = true;
        for (Speler speler : spelers) {
            if (speler.getLevel() == 10) {
                return false;
            }
        }
        return ret;
    }

    public void maakNieuweSpeler() {
        spelers.add(new Speler());
    }

    public void geefSpelerNaam(int i, String naam) {
        controleSpeler(naam);
        spelers.get(i).setNaam(naam);
    }

    public void geefSpelerGeslacht(int i, String geslacht) {
        spelers.get(i).setGeslacht(geslacht);
    }

    public void geefStartKaarten() {
        for (Speler speler : spelers) {
            geefStartKaarten(speler);
        }
    }

    public int toonBovensteKk() {
        return kerkerkaarten.get(0).getId();
    }

    public String geefTypeKaart(int id) {
        return kaarten.get(id).getClass().getSimpleName();
    }

    public void nieuwBovensteKaartK() {
        Kaart kaart = kerkerkaarten.get(0);
        kerkerkaarten.remove(0);
        kerkerkaarten.add(kaart);
    }

    public Map<Integer, Kaart> getKaarten() {
        return kaarten;
    }

    public void geefKerkerkaartAanSpeler(String naam) {
        for (Speler speler : spelers) {
            if (naam.equals(speler.getNaam())) {
                speler.voegKaartToe(kerkerkaarten.get(0));
                kerkerkaarten.remove(0);
            }
        }
    }

    public void speelCurse(int speler, int kaart) {
        Kaart gespeeldeKaart = geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1);
        geefSpeler(speler).setLevel(getSpelers().get(speler).getLevel() - ((Curse) gespeeldeKaart).getLevelLost());
    }

    public void speelCurse(String naam) {
        Kaart kaart = kerkerkaarten.get(0);
        int levelsLost = ((Curse) kaart).getLevelLost();
        int i = zoekSpeler(naam);
        if (spelers.get(i).getLevel() - levelsLost <= 0) {
            spelers.get(i).setLevel(1);
        } else {
            spelers.get(i).setLevel(spelers.get(i).getLevel() - levelsLost);
        }
//        if (kaart instanceof Curse) {
//            if (((Curse) kaart).getTypeLost().equals("none")) {
//                for (Speler speler : spelers) {
//                    if (naam.equals(speler.getNaam())) {
//                        //als type niet item is.
//                        speler.verwijderItems(((Curse) kaart).getTypeLost());
//                    }
//                }
//            } else if (((Curse) kaart).getLevelLost() > 0) {
//                for (Speler speler : spelers) {
//                    if (naam.equals(speler.getNaam())) {
//                        speler.setLevel(speler.getLevel() - ((Curse) kaart).getLevelLost());
//                    }
//                }
//            }
//        }
    }

    public String geefTypeLostCurse() {
        return ((Curse) kerkerkaarten.get(0)).getTypeLost();
    }

    public String[] geefItemsSpeler(String naam) {
        int i = 0;
        String[] ret = new String[0];
        for (Speler speler : spelers) {
            if (speler.getNaam().equals(naam)) {
                ret = new String[speler.getItems().size()];
                for (Kaart kaart : speler.getItems()) {
                    ret[i] += kaart.getNaam();
                    i++;
                }
            }
        }
        return ret;
    }

    public int geefAantalItemsSpeler(String naam) {
        for (Speler speler : spelers) {
            if (naam.equals(speler.getNaam())) {
                return speler.getAantalItems();
            }
        }
        return -99;
    }

    public void verwijderItemSpeler(String naam, int keuze) {
        for (Speler speler : spelers) {
            if (speler.getNaam().equals(naam)) {
                speler.verwijderItem(keuze);
            }
        }
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        if (naam.length() >= 6 && naam.length() <= 12 && naam.matches("^[A-Za-z0-9]+$")) {
            String numbers = naam.replaceAll("[^\\d]", "");
            if (numbers.length() < 3) {
                throw new SpelException("exception.spel.name");
            }
            this.naam = naam;
        } else {
            throw new SpelException("exception.spel.name");
        }
    }

    /**
     * public boolean spelerTeVeelKaarten(String naam) {
     * int i = zoekSpeler(naam);
     * return spelers.get(i).getAantalKaarten() > 5;
     * }
     * <p>
     * public boolean heeftGenoegKaarten(){
     * int i = zoekSpeler(naam);
     * return spelers.get(i).getAantalSchatkaarten() >= 1;
     * }
     **/
    public int getAantalKaarten(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).getAantalKaarten();
    }

    private int zoekSpeler(String naam) {
        int i = 0;
        for (Speler speler : spelers) {
            if (speler.getNaam().equals(naam)) {
                i = spelers.indexOf(speler);
            }
        }
        return i;
    }

    public String bovensteKaartToString() {
        return kerkerkaarten.get(0).toString();
    }

    public int getSpelerAanBeurt() {
        return spelerAanBeurt;
    }

    public void setSpelerAanBeurt(int spelerAanBeurt) {
        if (spelerAanBeurt == spelers.size()) {
            this.spelerAanBeurt = 0;
        } else {
            this.spelerAanBeurt = spelerAanBeurt;
        }
    }

    public String geefKaartenKunnenNaarItems(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefKaartenKunnenNaarItems();
    }

    public String geefVerkoopbareKaarten(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefVerkoopbareKaarten();
    }

    public List<Integer> geefIdVerkoopbarekaarten(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefIdVerkoopbareKaarten();
    }

    public List<Integer> geefIdKaartenNaarItems(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefIdKaartenNaarItems();
    }

    public void verwijderKaart(int id) {
        int i = zoekSpeler(naam);
        Kaart kaart = kaarten.get(id);
        spelers.get(i).verwijderKaart(kaart);
    }

    public String geefNietVerkoopbareKaarten(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefNietVerkoopbareKaarten();
    }

    public void verhoogLevel(String naam, int levelUp) {
        int i = zoekSpeler(naam);
        spelers.get(i).setLevel(spelers.get(i).getLevel() + levelUp);
    }

    //Return true = Monster wint
    //Return false = Speler in kwestie wint
    public boolean gevechtResultaat(int monster, int speler) {
        return monster >= speler;
    }

    public int geefMonsterLevelsUp(int id) {
        Monster monster = (Monster) kerkerkaarten.get(id);
        return monster.getWinstLevels();
    }

    public Object geefMonsterAttribuut(int id, String soort) {
        Object value = null;
        switch (soort) {
            case "level":
                value = ((Monster) (getKaarten().get(id))).getLevel();
                break;
            case "schatkaarten":
                value = ((Monster) getKaarten().get(id)).getWinstTeasures();
                break;
            case "levelsUp":
                value = ((Monster) getKaarten().get(id)).getWinstLevels();
                break;
            case "RunAway":
                value = ((Monster) getKaarten().get(id)).getRunAway();
                break;
        }
        return value;
    }

    public BadStuff geefBadStuff(int id) {
        return ((Monster) getKaarten().get(id)).getBadStuff();
    }

    public Schatkaart geefSchatkaart() {
        Schatkaart schatkaart = (Schatkaart) schatkaarten.get(0);
        schatkaarten.remove(0);
        return schatkaart;
    }


    public boolean isKlein() {
        return klein;
    }

    public void setKlein(boolean klein) {
        this.klein = klein;
    }

    public List<Integer> getVolgordeT() {
        return volgordeT;
    }

    public List<Integer> getVolgordeD() {
        return volgordeD;
    }

    public void updateVolgorde() {
        setVolgordeD();
        setVolgordeT();
    }

    private void setVolgordeT() {
        volgordeT.clear();
        for (Kaart kaart : schatkaarten) {
            volgordeT.add(kaart.getId());
        }
    }

    private void setVolgordeD() {
        volgordeD.clear();
        for (Kaart kaart : kerkerkaarten) {
            volgordeD.add(kaart.getId());
        }
    }

    public List<Integer> getWaardeSchatkaart() {
        int i = zoekSpeler(naam);
        return spelers.get(i).getWaardeSchatkaart();
    }

    public int getWaardeSchatkaart(int id) {
        int waarde = 0;
        if (kaarten.get(id) instanceof Equipment) {
            waarde = ((Equipment) kaarten.get(id)).getWaarde();
        } else if (kaarten.get(id) instanceof ConsumablesSchat) {
            waarde = ((ConsumablesSchat) kaarten.get(id)).getWaarde();
        }
        return waarde;
    }

    public void setKaarten(Map<Integer, Kaart> kaarten) {
        this.kaarten = kaarten;
    }

    public void setSchatkaarten(List<Kaart> schatkaarten) {
        this.schatkaarten = schatkaarten;
    }

    public void setKerkerkaarten(List<Kaart> kerkerkaarten) {
        this.kerkerkaarten = kerkerkaarten;
    }

    public List<Kaart> getSchatkaarten() {
        return schatkaarten;
    }

    public List<Kaart> getKerkerkaarten() {
        return kerkerkaarten;
    }

    /*public String toonOverzichtKaartenInHand(int speler){
        String output = String.format("Dit is een overzicht van je kaarten in je hand %n") ;
        List<Kaart> kaartenInHand =  spelers.get(speler).getKaarten();
        for(int i = 0; i < spelers.get(speler).getKaarten().size(); i++){
            String help = "";
            if(kaartenInHand.get(i) instanceof ConsumablesKerker){
                help = "Consumable";
            }
            if(kaartenInHand.get(i) instanceof Curse){
                help = "Curse";
            }
            if(kaartenInHand.get(i) instanceof Monster){
                help = "Monster";
            }
            if(kaartenInHand.get(i) instanceof Race){
                help = "Race";
            }
            if(kaartenInHand.get(i) instanceof ConsumablesSchat){
                help = "Consumable";
            }
            if(kaartenInHand.get(i) instanceof Equipment){
                help = "Equipment";
            }
            output += String.format("%d)%s: %s%n",i+1, kaartenInHand.get(i).getNaam(), help);
        }
        return output;
    }*/

    public List<String> toonOverzichtKaartenInHand2(int speler) {
        List<String> output = new ArrayList<>();
        output.add("Dit is een overzicht van je kaarten in je hand");
        List<Kaart> kaartenInHand = spelers.get(speler).getKaarten();
        for (int i = 0; i < spelers.get(speler).getKaarten().size(); i++) {
            String help = "";
            if (kaartenInHand.get(i) instanceof ConsumablesKerker) {
                help = "Consumable";
            }
            if (kaartenInHand.get(i) instanceof Curse) {
                help = "Curse";
            }
            if (kaartenInHand.get(i) instanceof Monster) {
                help = "Monster";
            }
            if (kaartenInHand.get(i) instanceof Race) {
                help = "Race";
            }
            if (kaartenInHand.get(i) instanceof ConsumablesSchat) {
                help = "Consumable";
            }
            if (kaartenInHand.get(i) instanceof Equipment) {
                help = "Equipment";
            }
            output.add(String.format("%d)%s: %s", i + 1, kaartenInHand.get(i).getNaam(), help));
        }
        return output;
    }

    public int gooiDobbelsteen() {
        return new SecureRandom().nextInt(5) + 1;
    }

    public Speler geefSpeler(int i) {
        return spelers.get(i);
    }

    public void setMonsterBattlePoints(int monsterBattlePoints) {
        gevecht.setMonsterBattlePoints(monsterBattlePoints);
    }

    public void setSpelerBattlePoints(int spelerBattlePoints) {
        gevecht.setSpelerBattlePoints(spelerBattlePoints);
    }

    public int getMonsterBattlePoints() {
        return gevecht.getMonsterBattlePoints();
    }

    public int getSpelerBattlePoints() {
        return gevecht.getSpelerBattlePoints();
    }

    public List<Integer> geefIDKaartenInHand(String naam) {
        return spelers.get(zoekSpeler(naam)).getIDKaartenInHand();
    }

    public void voegkaartonderaanstapeltoe(Kaart kaart) {
        //kaart is een kerkerkaart
        if (kaart instanceof ConsumablesKerker || kaart instanceof Curse || kaart instanceof Monster || kaart instanceof Race) {
            kerkerkaarten.add(kaart);
        }//kaart is een schatkaart
        else {
            schatkaarten.add(kaart);
        }

    }

    public void gooiKaartenWeg(String naam, List<Integer> gekozenKaarten) {
        int speler = zoekSpeler(naam);
        List<Kaart> temp = spelers.get(speler).getItems();
        for (Integer k : gekozenKaarten) {
            for (Kaart kaart : temp) {
                if (kaart.getId() == k) {
                    temp.remove(kaart);
                    if (kaart instanceof Equipment || kaart instanceof ConsumablesSchat) {
                        schatkaarten.add(kaart);
                    } else {
                        kerkerkaarten.add(kaart);
                    }
                }
            }
        }
        spelers.get(speler).setItems(temp);
        spelers.get(speler).updateItems();
    }

    public void verkoopKaarten(String naam, List<Integer> gekozenKaarten) {
        int speler = zoekSpeler(naam);
        int waarde = 0;
        List<Kaart> temp = spelers.get(speler).getKaarten();
        List<Kaart> verwijderenKaart = new ArrayList<>();
        for (Integer k : gekozenKaarten) {
            for (Kaart kaart : spelers.get(speler).getKaarten()) {
                if (kaart.getId() == k) {
                    waarde += ((Schatkaart) kaart).getWaarde();
                    verwijderenKaart.add(kaart);
                }
            }
        }
        temp.removeAll(verwijderenKaart);
        spelers.get(speler).setKaarten(temp);
        spelers.get(speler).setLevel(spelers.get(speler).getLevel() + (waarde / 1000));
    }

    public void verplaatsNaarItems(String naam, List<Integer> gekozenKaarten) {
        int speler = zoekSpeler(naam);
        List<Kaart> tempK = spelers.get(speler).getKaarten();
        List<Kaart> tempI = spelers.get(speler).getItems();
        List<Kaart> verwijderKaarten = new ArrayList<>();
        for (Kaart kaart : tempK) {
            if (gekozenKaarten.contains(kaart.getId())) {
                verwijderKaarten.add(kaart);
                tempI.add(kaart);
            }
        }
        tempK.removeAll(verwijderKaarten);
        spelers.get(speler).setKaarten(tempK);
        spelers.get(speler).setItems(tempI);
    }

    //Alles wat te maken heeft met gevecht hieronder

    //Setters
    public void setHelp(String help) {
        gevecht.setHelp(help);
    }

    public void setHelptmee(List<Boolean> helptmee) {
        gevecht.setHelptmee(helptmee);
    }

    public void setSpelerAanBeurtGevecht(int spelerAanBeurt) {
        gevecht.setSpelerAanBeurt(spelerAanBeurt);
    }

    //Getters
    public String getHelp() {
        return gevecht.getHelp();
    }

    public List<Boolean> gethelptmee() {
        return gevecht.gethelptmee();
    }

    public int getSpelerAanBeurtGevecht() {
        return gevecht.getSpelerAanBeurt();
    }

    public void voerBadStuffUit(int id) {
        BadStuff bs = geefBadStuff(id);
        //Checkt of het level dat je verliest groter is dan 0 => de badstuff is een level verliezen
        if (bs.getLevelsLost() > 0) {
            System.out.printf(LanguageResource.getString("usecase6.loselevels") + "%n", bs.getLevelsLost());
            verhoogLevel(getSpelers().get(getSpelerAanBeurt()).getNaam(), -bs.getLevelsLost());

        }//als je geen level verliest ontsnap je automatisch  (er is maar 1 kaart die geen level verliest in de kleine kaarten set)
        else {
            System.out.println(LanguageResource.getString("usecase6.escape1"));
        }
    }

    public int spelerLevels() {
        List<Boolean> helptmee = gethelptmee();
        int extraLevels = 0;
        int aantal = 0;
        for (int i = 0; i < helptmee.size(); i++) {
            if (helptmee.get(i)) {
                List<Kaart> items = geefSpeler(i).getItems();

                for (int j = 0; j < items.size(); j++) {
                    if (items.get(j) instanceof Equipment) {
                        aantal += ((Equipment) items.get(j)).getBonus();
                    }
                    if (items.get(j) instanceof Race) {
                        aantal += ((Race) items.get(j)).getBonusCombat();
                    }
                }
                extraLevels += geefSpeler(i).getLevel() + aantal;
            }
        }
        return extraLevels;
    }

    public boolean validatieKaartSpeler(int kaart){
        Kaart kr = getKaarten().get(kaart);
        //Kaarten die de Speler mag spelen
        if (getSpelerAanBeurt() == gevecht.getSpelerAanBeurt()) {
            if (kr instanceof ConsumablesSchat || kr instanceof ConsumablesKerker || kr instanceof Equipment || kr instanceof Race || kr instanceof Monster) {
                if (kr instanceof Monster) {
                    return true;
                }
                return true;
            }
            return false;
            //Kaarten die de Tegenspelers mogen spelen
        } else {
            if (kr instanceof ConsumablesSchat || kr instanceof ConsumablesKerker || kr instanceof Monster || kr instanceof Curse) {
                //Aanpassen dat als speler geen hulp wou, alleen negatieve ConsumablesKerker gespeeld mag worden
                if (gevecht.getHelp().equals(LanguageResource.getString("yes"))) {
                    if (kr instanceof ConsumablesKerker) {
                        return ((ConsumablesKerker) kr).getBonus() >= 0;
                    }
                } else {
                    if (kr instanceof Curse) {
                        if (gevecht.gethelptmee().get(gevecht.getSpelerAanBeurt())) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }


    public boolean validatieKaartSpeler(int kaart, boolean monster) {
        Kaart kr = geefSpeler(gevecht.getSpelerAanBeurt()).getKaarten().get(kaart - 1);
        //Kaarten die de Speler mag spelen
        if (getSpelerAanBeurt() == gevecht.getSpelerAanBeurt()) {
            if (kr instanceof ConsumablesSchat || kr instanceof ConsumablesKerker || kr instanceof Equipment || kr instanceof Race || kr instanceof Monster) {
                if (kr instanceof Monster) {
                    return monster;
                }
                return true;
            }
            return false;
            //Kaarten die de Tegenspelers mogen spelen
        } else {
            if (kr instanceof ConsumablesSchat || kr instanceof ConsumablesKerker || kr instanceof Monster || kr instanceof Curse) {
                //Aanpassen dat als speler geen hulp wou, alleen negatieve ConsumablesKerker gespeeld mag worden
                if (gevecht.getHelp().equals(LanguageResource.getString("yes"))) {
                    if (kr instanceof ConsumablesKerker) {
                        return ((ConsumablesKerker) kr).getBonus() >= 0;
                    }
                } else {
                    if (kr instanceof Curse) {
                        if (gevecht.gethelptmee().get(gevecht.getSpelerAanBeurt())) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean validatieKaartItems(int kaart) {
        int aantalWapens = 0;
        List<Kaart> items = geefSpeler(gevecht.getSpelerAanBeurt()).getItems();
        Kaart kr = geefSpeler(gevecht.getSpelerAanBeurt()).getKaarten().get(kaart - 1);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof Race && kr instanceof Race) {
                return false;
            }
            if (items.get(i) instanceof Equipment && kr instanceof Equipment) {
                Kaart kr2 = items.get(i);
                if (((Equipment) kr2).getType().equals("Head") && ((Equipment) kr).getType().equals("Head")) {
                    return false;
                }
                if (((Equipment) kr2).getType().equals("Armor") && ((Equipment) kr).getType().equals("Armor")) {
                    return false;
                }
                if (((Equipment) kr2).getType().equals("Foot") && ((Equipment) kr).getType().equals("Foot")) {
                    return false;
                }
                if (((Equipment) kr2).getType().equals("Weapon")) {
                    aantalWapens += 1;
                }
            }
        }

        if (aantalWapens == 2 && ((Equipment) kr).getType().equals("Weapon")) {
            return false;
        }
        return true;
    }

    public void speelMonster(int kaart, boolean monster) {
        Kaart gespeeldeKaart = geefSpeler(gevecht.getSpelerAanBeurt()).getKaarten().get(kaart - 1);
        if (!monster || gevecht.gethelptmee().get(gevecht.getSpelerAanBeurt())) {
            gevecht.setSpelerBattlePoints(gevecht.getSpelerBattlePoints() + ((Monster) gespeeldeKaart).getLevel());
        } else {
            gevecht.setMonsterBattlePoints(gevecht.getMonsterBattlePoints() + ((Monster) gespeeldeKaart).getLevel());
        }
    }

    public void speelConsumable(int kaart) {
        Kaart gespeeldeKaart = geefSpeler(gevecht.getSpelerAanBeurt()).getKaarten().get(kaart - 1);
        if (gevecht.gethelptmee().get(gevecht.getSpelerAanBeurt())) {
            if (gespeeldeKaart instanceof ConsumablesSchat) {
                gevecht.setSpelerBattlePoints(gevecht.getSpelerBattlePoints() + ((ConsumablesSchat) gespeeldeKaart).getBattleBonus());
            }
            if (gespeeldeKaart instanceof ConsumablesKerker) {
                gevecht.setSpelerBattlePoints(gevecht.getSpelerBattlePoints() + ((ConsumablesKerker) gespeeldeKaart).getBonus());
            }
        } else {
            if (gespeeldeKaart instanceof ConsumablesSchat) {
                gevecht.setMonsterBattlePoints(gevecht.getMonsterBattlePoints() + ((ConsumablesSchat) gespeeldeKaart).getBattleBonus());
            }
            if (gespeeldeKaart instanceof ConsumablesKerker) {
                gevecht.setMonsterBattlePoints(gevecht.getMonsterBattlePoints() + ((ConsumablesKerker) gespeeldeKaart).getBonus());
            }
        }
    }

    public void itemsBijvoegen(int kaart) {
        Kaart gespeeldeKaart = geefSpeler(gevecht.getSpelerAanBeurt()).getKaarten().get(kaart - 1);
        geefSpeler(gevecht.getSpelerAanBeurt()).getItems().add(gespeeldeKaart);
    }

    public String geefKaartenVanSpeler(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefKaarten();
    }

    public String geefItemsVanSpeler(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefItems();
    }

    public int[] geefKaartenVanSpelerInt(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefKaartenInt();
    }

    public int[] geefItemsVanSpelerInt(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefItemsInt();
    }

    public List<String[]> geefspelerOverzichtVolgorde() {
        List<String[]> retSpelers = new ArrayList<>();
        int teller = 0;
        for (int i = spelerAanBeurt; i < aantalSpelers; i++) {
            if (teller < aantalSpelers) {
                Speler speler = spelers.get(i);
                String[] items = new String[6];
                for(int j = 0; j<speler.geefItemsInt().length; j++){
                    items[j] = String.valueOf(speler.geefItemsInt()[j]);
                }
                String[] kaarten = new String[5];
                for(int j = 0; j<speler.geefItemsInt().length; j++){
                    kaarten[j] = String.valueOf(speler.geefKaartenInt()[j]);
                }
                String[] info = {speler.getNaam(), String.valueOf(speler.getLevel()), speler.getGeslacht(), speler.getRace(), String.valueOf(kaarten), String.valueOf(items)};
                if (i == aantalSpelers - 1) {
                    i = 0;
                }
                retSpelers.add(info);
                teller++;
            }
        }
        return retSpelers;
    }
}
