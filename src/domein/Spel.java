package domein;

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
        kr = new KaartDbKleinRepository();
        //pc = new PersistentieController();
        //kaarten = new HashMap<>();
        schatkaarten = kr.getSchatkaarten();
        volgordeD = new ArrayList<>();
        volgordeT = new ArrayList<>();
        kerkerkaarten = kr.getKerkerkaarten();
        maakKaartenBib();
        updateVolgorde();
        setSpelerAanBeurt(0);
    }

    public Spel(String naam, boolean klein, List<Speler> spelers, int ls, List<Integer> volgnummerD, List<Integer> volgnummerT) {
        kr= new KaartDbKleinRepository();
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
        kaarten = kr.getKaartenBib();
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
            if (speler.getNaam().toLowerCase().length() >= speler1.getNaam().toLowerCase().length()) {
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
        if (spelerAanBeurt == spelers.size()){
            this.spelerAanBeurt = 0;
        }else {
            this.spelerAanBeurt = spelerAanBeurt;
        }
    }

    public String geefKaartenKunnenNaarItems(String naam) {
        int i = zoekSpeler(naam);
        //List<Kaart> kaarten = spelers.get(i).getKaarten();
        return spelers.get(i).geefKaartenKunnenNaarItems();
    }

    public String geefVerkoopbareKaarten(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefVerkoopbareKaarten();
    }

    public List<Integer> geefIdVerkoopbarekaarten(String naam){
        int i = zoekSpeler(naam);
        return spelers.get(i).geefIdVerkoopbareKaarten();
    }

    public void verwijderKaart(int id){
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

    public BadStuff geefBadStuff(int id){
        return ((Monster) getKaarten().get(id)).getBadStuff();
    }

    public Schatkaart geefSchatkaart(){
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

    private void updateVolgorde() {
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

    public List<Integer> getWaardeSchatkaart(){
        int i = zoekSpeler(naam);
        return spelers.get(i).getWaardeSchatkaart();
    }

    public int getWaardeSchatkaart(int id){
        if (kaarten.get(id).getClass().getSimpleName().toLowerCase().equals("equipment")){
            return ((Equipment)kaarten.get(id)).getWaarde();
        }else if(kaarten.get(id).getClass().getSimpleName().equals("consumablesschat")){
            return ((ConsumablesSchat)kaarten.get(id)).getWaarde();
        }else {
            return 999;

        }
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

    public String toonOverzichtKaartenInHand(int speler){
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
    }

    public int gooiDobbelsteen(){
        return new SecureRandom().nextInt(5) + 1;
    }

    public Speler geefSpeler(int i){
        return spelers.get(i);
    }

    public List<Integer> geefIDKaartenInHand(String naam) {
        return spelers.get(zoekSpeler(naam)).getIDKaartenInHand();
    }

    public List<Integer> geefIdsKunnenNaarItems(String naam) {
        return spelers.get(zoekSpeler(naam)).geefIdsKunnenNaarItems();
    }
}
