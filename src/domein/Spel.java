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
import printer.Printer;

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

    /**
     * Constructor van Spel met alle parameters.
     * @param naam van de speler
     * @param klein grootte databank
     * @param spelers Lijst van de spelers
     * @param ls laatste speler
     * @param volgnummerD Lijst van dungeon kaarten
     * @param volgnummerT Lijst van treasure kaarten
     */
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

    /**
     * Aanmaken van de kaarten bibliotheek die gebruikt wordt wanneer geen geldige internetconnectie gevonden wordt.
     */
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
     * Voeg een kaart toe aan de hand van een speler. --> roept spelers.get(spelernr).voegKaartToe(kaart) aan.
     *
     * @param kaart    De kaart voor de speler
     * @param spelernr De nummer van de speler in het spel, volgens volgorde van ingeven
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
     * Methode die de speler de startkaarten geeft.
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
     * Methode die aantalSpelers get.
     * @return aantal spelers die meedoen aan het spel
     */
    public int getAantalSpelers() {
        return aantalSpelers;
    }

    /**
     * Methode die de volgorde van de spelers controleert en bepaalt.
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
     * Methode die de naam van winnaar teruggeeft.
     * @return String winnaar
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
     * Methode die spelers levels geeft.
     * @param speler
     */
    /*
    public void geefLevel(Speler speler) {
        if (speler.getLevel() == 9 && speler.heeftMonsterVerslaan()) {
            speler.setLevel(speler.getLevel() + 1);
        } else if (speler.getLevel() >= 1 && speler.getLevel() < 9) {
            speler.setLevel(speler.getLevel() + 1);
        }
    }
     */

    /**
     * Methode die spelSituatie weergeeft.
     * @return List van Strings
     */
    public List<String> geefSpelsituatie() {
        List<String> ret = new ArrayList<>();
        for (Speler speler : spelers) {
            //items naar string weg
            ret.add(String.format("%s: %s, %s: %s, %s: %d, %s: %s%n", LanguageResource.getString("player.name"), speler.getNaam(), LanguageResource.getString("player.sex"), speler.getGeslacht(), LanguageResource.getString("player.level"), speler.getLevel(), LanguageResource.getString("player.items"), speler.kaartenNaarString(speler.getItems())));
        }
        return ret;
    }

    /**
     * Methode die de beknopte spelsituatie weergeeft.
     * @return List van Strings
     */
    public List<String> geefBeknopteSpelsituatie(/*boolean vecht*/) {
        List<String> ret = new ArrayList<>();
        for (Speler speler : spelers) {
            ret.add(String.format("%s: %s, %s: %s, %s: %d", LanguageResource.getString("player.name"), Printer.printBlue(speler.getNaam()), LanguageResource.getString("player.sex"), speler.getGeslacht(), LanguageResource.getString("player.level"), speler.getLevel() /*, vecht?"Vecht mee":"Vecht niet mee"*/));
        }
        return ret;
    }

    /**
     * Methode die returnt of nieand gewonnen is.
     * @return false = gewonnen, true = niet gewonnen
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

    /**
     * Methode die nieuwe speler toevoegt aan spelerslijst.
     */
    public void maakNieuweSpeler() {
        spelers.add(new Speler());
    }

    /**
     * Methode die speler een naam toekent na het controleren van die naam. --> roept spelers.get(i).setNaam(naam) aan.
     * @param i index
     * @param naam van de speler
     */
    public void geefSpelerNaam(int i, String naam) {
        controleSpeler(naam);
        spelers.get(i).setNaam(naam);
    }

    /**
     * Methode die de speler een geslacht toekent. --> roept spelers.get(i).setGeslacht(geslacht) aan.
     * @param i huide speler
     * @param geslacht gekozen geslacht
     */
    public void geefSpelerGeslacht(int i, String geslacht) {
        spelers.get(i).setGeslacht(geslacht);
    }

    /**
     * Methode die speler zijn startkaarten toekent, overloopt alle spelers met een enhanced for. --> roept geefStartKaarten(speler) aan.
     */
    public void geefStartKaarten() {
        for (Speler speler : spelers) {
            geefStartKaarten(speler);
        }
    }

    /**
     * Methode die het id van de bovenste Kerkerkaart teruggeeft. --> roept kerkerkaarten.get(0).getId() aan.
     * @return int id van bovenste kaart.
     */
    public int toonBovensteKk() {
        return kerkerkaarten.get(0).getId();
    }

    /**
     * Methode die het typekaart teruggeeft adhv id van de kaart. --> roept kaarten.get(id).getClass().getSimpleName() aan.
     * @param id van de kaart
     * @return String type.
     */
    public String geefTypeKaart(int id) {
        return kaarten.get(id).getClass().getSimpleName();
    }

    /**
     * Methode die een nieuwe Kerkerkaart bovenaan legt.
     */
    public void nieuweBovensteKaartK() {
        Kaart kaart = kerkerkaarten.get(0);
        kerkerkaarten.remove(0);
        kerkerkaarten.add(kaart);
    }

    /**
     * Map waar de kaarten inzitten met hun ids
     * @return de kaarten
     */
    public Map<Integer, Kaart> getKaarten() {
        return kaarten;
    }

    /**
     * Methode die kerkerkaarten aan spelers geeft
     * @param naam van de spelers
     */
    public void geefKerkerkaartAanSpeler(String naam) {
        for (Speler speler : spelers) {
            if (naam.equals(speler.getNaam())) {
                speler.voegKaartToe(kerkerkaarten.get(0));
                kerkerkaarten.remove(0);
            }
        }
    }

    /**
     * Methode die cursekaart speelt
     * @param speler id van speler
     * @param kaart id van kaart
     */
    public void speelCurse(int speler, int kaart) {
        Kaart gespeeldeKaart = geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1);
        geefSpeler(speler).setLevel(getSpelers().get(speler).getLevel() - ((Curse) gespeeldeKaart).getLevelLost());
    }

    /**
     * Methode die cursekaart speelt
     * @param naam van de speler
     */
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

    /*
    public String geefTypeLostCurse() {
        return ((Curse) kerkerkaarten.get(0)).getTypeLost();
    }
    */

    /**
     * Getter voor de naam van de speler
     * @return String naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Setter voor de naam + controle op de naam
     * @param naam van speler
     */
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

    /**
     * Methode die aantal kaarten teruggeeft
     * @param naam van de speler
     * @return int aantal kaarten
     */
    public int getAantalKaarten(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).getAantalKaarten();
    }

    /**
     * Methode die de speler zoekt en de index ervan teruggeeft.
     * @param naam van de speler
     * @return index van de speler
     */
    private int zoekSpeler(String naam) {
        int i = 0;
        for (Speler speler : spelers) {
            if (speler.getNaam().equals(naam)) {
                i = spelers.indexOf(speler);
            }
        }
        return i;
    }

    /**
     * Methode die de bovenste kaart to stringt.
     * @return String van bovenste kaart.
     */
    public String bovensteKaartToString() {
        return kerkerkaarten.get(0).toString();
    }

    /**
     * Getter voor speler aan beurt
     * @return id speler aan beurt
     */
    public int getSpelerAanBeurt() {
        return spelerAanBeurt;
    }

    /**
     * Setter voor speler aan beurt
     * @param spelerAanBeurt de speler aan beurt
     */
    public void setSpelerAanBeurt(int spelerAanBeurt) {
        if (spelerAanBeurt == spelers.size()) {
            this.spelerAanBeurt = 0;
        } else {
            this.spelerAanBeurt = spelerAanBeurt;
        }
    }

    /**
     * Methode die kaarten die naar items kunnen als string teruggeeft. --> roept spelers.get(i).geefKaartenKunnenNaarItems() aan.
     * @param naam van de speler
     * @return String kaarten naar items
     */
    public String geefKaartenKunnenNaarItems(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefKaartenKunnenNaarItems();
    }

    /**
     * Methode die kaarten die verkocht kunnen worden als String teruggeeft. --> roept spelers.get(i).geefVerkoopbareKaarten() aan.
     * @param naam van de speler
     * @return String verkoopbare kaarten
     */
    public String geefVerkoopbareKaarten(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefVerkoopbareKaarten();
    }

    /**
     * Methode die de ids van de verkoopbare kaarten teruggeeft. --> roept spelers.get(i).geefIdVerkoopbareKaarten() aan. Gebruikt zoekSpeler()
     * @param naam van de speler
     * @return List van Integers met ids verkoopbare kaarten
     */
    public List<Integer> geefIdVerkoopbarekaarten(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefIdVerkoopbareKaarten();
    }

    /**
     * Methode die de ids van de kaarten die naar items
     * @param naam naam van de speler
     * @return lijst met ids
     */
    public List<Integer> geefIdKaartenNaarItems(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefIdKaartenNaarItems();
    }

    /*
    public void verwijderKaart(int id) {
        int i = zoekSpeler(naam);
        Kaart kaart = kaarten.get(id);
        spelers.get(i).verwijderKaart(kaart);
    }
    */

    /**
     * Methode die alle niet verkoopbare kaarten teruggeeft als String. --> roept spelers.get(i).geefNietVerkoopbareKaarten() aan. Gebruikt zoekSpeler().
     * @param naam van de speler
     * @return String met niet verkoopbare kaarten
     */
    public String geefNietVerkoopbareKaarten(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefNietVerkoopbareKaarten();
    }

    /**
     * Methode die level verhoogt. --> roept spelers.get(i).setLevel(spelers.get(i).getLevel() + levelUp) aan. Gebruikt zoekSpeler().
     * @param naam van speler
     * @param levelUp aantal levels up
     */
    public void verhoogLevel(String naam, int levelUp) {
        int i = zoekSpeler(naam);
        spelers.get(i).setLevel(spelers.get(i).getLevel() + levelUp);
    }

    /**
     * Methode die boolean teruggeeft wie gewonnen is
     * @param monster id van monster
     * @param speler index
     * @return true = monster wint, false = speler wint
     */
    public boolean gevechtResultaat(int monster, int speler) {
        return monster >= speler;
    }

    /*
    public int geefMonsterLevelsUp(int id) {
        Monster monster = (Monster) kerkerkaarten.get(id);
        return monster.getWinstLevels();
    }
    */

    /**
     * Methode die monster attributen toekent
     * @param id id . van monster
     * @param soort soort van monster
     * @return String met monster value
     */
    public String geefMonsterAttribuut(int id, String soort) {
        String value = null;
        switch (soort) {
            case "level":
                value = String.valueOf(((Monster) (getKaarten().get(id))).getLevel());
                break;
            case "schatkaarten":
                value = String.valueOf(((Monster) getKaarten().get(id)).getWinstTeasures());
                break;
            case "levelsUp":
                value = String.valueOf(((Monster) getKaarten().get(id)).getWinstLevels());
                break;
            case "RunAway":
                value = String.valueOf(((Monster) getKaarten().get(id)).getRunAway());
                break;
        }
        return value;
    }

    /**
     * Methode die badstuff teruggeeft
     * @param id van de kaart
     * @return BadStuff van de kaart
     */
    public BadStuff geefBadStuff(int id) {
        return ((Monster) getKaarten().get(id)).getBadStuff();
    }

    /*
    public Schatkaart geefSchatkaart() {
        Schatkaart schatkaart = (Schatkaart) schatkaarten.get(0);
        schatkaarten.remove(0);
        return schatkaart;
    }
    */

    /**
     * Boolean die grootte databank geeft.
      * @return true = isKlein
     */
    public boolean isKlein() {
        return klein;
    }

    /*
    public void setKlein(boolean klein) {
        this.klein = klein;
    }
    */

    /**
     * Methode die volgorde schatkaarten geeft
     * @return List van Integers met volgorde schatkaarten
     */
    public List<Integer> getVolgordeT() {
        return volgordeT;
    }

    /**
     * %Methode die volgorde kerkerkaarten geeft
     * @return List van Integers met volgorde kerkerkaarten
     */
    public List<Integer> getVolgordeD() {
        return volgordeD;
    }

    /**
     * Methode die volgorde update. Gebruikt setVolgorde(D) en setVolgorde(T).
     */
    public void updateVolgorde() {
        setVolgordeD();
        setVolgordeT();
    }

    /**
     * Methode die de schatkaarten in de juiste volgorde plaatst.
     */
    private void setVolgordeT() {
        volgordeT.clear();
        for (Kaart kaart : schatkaarten) {
            volgordeT.add(kaart.getId());
        }
    }

    /**
     * Methode die de kerkerkaarten in de juist volgorde plaatst.
     */
    private void setVolgordeD() {
        volgordeD.clear();
        for (Kaart kaart : kerkerkaarten) {
            volgordeD.add(kaart.getId());
        }
    }

    /**
     * Methode die de waarde van de schatkaart bepaald en teruggeeft.
     * @param id van de kaart
     * @return waarde van de kaart
     */
    public int getWaardeSchatkaart(int id) {
        int waarde = 0;
        if (kaarten.get(id) instanceof Equipment) {
            waarde = ((Equipment) kaarten.get(id)).getWaarde();
        } else if (kaarten.get(id) instanceof ConsumablesSchat) {
            waarde = ((ConsumablesSchat) kaarten.get(id)).getWaarde();
        }
        return waarde;
    }

    /**
     * Methode die de kaarten in een map steekt samen met hun ids
     * @param kaarten map met kaarten
     */
    public void setKaarten(Map<Integer, Kaart> kaarten) {
        this.kaarten = kaarten;
    }

    /**
     * Setter voor schatkaarten die deze in een List steekt
     * @param schatkaarten list met schatkaarten
     */
    public void setSchatkaarten(List<Kaart> schatkaarten) {
        this.schatkaarten = schatkaarten;
    }

    /**
     * Setter voor kerkerkaarten die deze in een List steekt
     * @param kerkerkaarten list met kerkerkaarten
     */
    public void setKerkerkaarten(List<Kaart> kerkerkaarten) {
        this.kerkerkaarten = kerkerkaarten;
    }

    /**
     * Getter voor schatkaarten
     * @return List met schatkaarten
     */
    public List<Kaart> getSchatkaarten() {
        return schatkaarten;
    }

    /**
     * Getter voor kerkerkaarten
     * @return List met kerkerkaarten
     */
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

    /**
     * Methode die overzicht van de kaarten in hand toont
     * @param speler id van speler
     * @return overzicht van kaarten + type
     */
    public List<String> toonOverzichtKaartenInHand2(int speler) {
        List<String> output = new ArrayList<>();
        output.add(LanguageResource.getString("usecase4.overzicht"));
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

    /**
     * Methode die dobbelsteen gooit
     * @return int tussen 1 en 6
     */
    public int gooiDobbelsteen() {
        return new SecureRandom().nextInt(5) + 1;
    }

    /**
     * Methode die de speler op positie i teruggeeft
     * @param i index van speler
     * @return Speler
     */
    public Speler geefSpeler(int i) {
        return spelers.get(i);
    }

    /**
     * Setter voor monsterBattlePoints. --> roept gevecht.setMonsterBattlePoints(monsterBattlePoints) aan.
     * @param monsterBattlePoints battle points van monster
     */
    public void setMonsterBattlePoints(int monsterBattlePoints) {
        gevecht.setMonsterBattlePoints(monsterBattlePoints);
    }

    /**
     * Setter voor spelerBattlePoints. --> roept gevecht.setSpelerBattlePoints(spelerBattlePoints) aan.
     * @param spelerBattlePoints battle points van speler
     */
    public void setSpelerBattlePoints(int spelerBattlePoints) {
        gevecht.setSpelerBattlePoints(spelerBattlePoints);
    }

    /**
     * Getter voor monsterBattlePoints. --> roept gevecht.getMonsterBattlePoints() aan.
     * @return int points
     */
    public int getMonsterBattlePoints() {
        return gevecht.getMonsterBattlePoints();
    }

    /**
     * Getter voor spelerBattlePoints. --> roept gevecht.getSpelerBattlePoints() aan.
     * @return int points
     */
    public int getSpelerBattlePoints() {
        return gevecht.getSpelerBattlePoints();
    }

    /**
     * Methode die Ids van kaarten in hand teruggeeft. --> roept spelers.get(zoekSpeler(naam)).getIDKaartenInHand() aan.
     * @param naam van speler
     * @return List met Integers die ids van kaarten in hand zijn.
     */
    public List<Integer> geefIDKaartenInHand(String naam) {
        return spelers.get(zoekSpeler(naam)).getIDKaartenInHand();
    }

    /**
     * Methode die kaart onderaan stapel toevoegt
     * @param kr id kaart
     */
    public void voegKaartOnderaanStapelToe(int kr) {
        Kaart kaart = geefSpeler(spelerAanBeurt).getKaarten().get(kr - 1);
        //kaart is een kerkerkaart
        if (kaart instanceof ConsumablesKerker || kaart instanceof Curse || kaart instanceof Monster || kaart instanceof Race) {
            kerkerkaarten.add(kaart);
        }//kaart is een schatkaart
        else {
            schatkaarten.add(kaart);
        }

    }

    /**
     * Methode die kaarten weggooit. --> roept spelers.get(speler).getItems() aan. Gebruikt zoekSpeler()
     * @param naam speler
     * @param gekozenKaarten Lijst met kaarten
     */
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

    /**
     * Methode die kaarten verkoopt. --> roept spelers.get(speler).getKaarten() aan. Gebruikt zoekSpeler().
     * @param naam speler
     * @param gekozenKaarten Lijst met kaarten
     */
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

    /**
     * Methode die kaarten naar items verplaatst. --> roep spelers.get(speler).getKaarten(), spelers.get(speler).getItems() aan.
     * @param naam van de speler
     * @param gekozenKaarten Lijst met kaarten
     */
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

    /**
     * Setter voor help. --> roept gevecht.setHelp(help) aan.
     * @param help sting voor hulp
     */
    public void setHelp(String help) {
        gevecht.setHelp(help);
    }

    /**
     * Setter voor helpt mee. --> roept gevecht.setHelptmee(helptmee)
     * @param helptmee List met booleans
     */
    public void setHelptmee(List<Boolean> helptmee) {
        gevecht.setHelptmee(helptmee);
    }

    /**
     * Setter voor speler aan beurt. --> roept gevecht.setSpelerAanBeurt(spelerAanBeurt) aan.
     * @param spelerAanBeurt huidige speler
     */
    public void setSpelerAanBeurtGevecht(int spelerAanBeurt) {
        gevecht.setSpelerAanBeurt(spelerAanBeurt);
    }

    /**
     * Getter voor help
     * @return hulp nodig
     */
    public String getHelp() {
        return gevecht.getHelp();
    }

    /**
     * Methode die teruggeeft of mensen meehelpen. --> roept gevecht.getHelptmee() aan.
     * @return Lijst met booleans
     */
    public List<Boolean> getHelptmee() {
        return gevecht.getHelptmee();
    }

    /**
     * Getter voor spelerAanBeurtGevecht. --> roept gevecht.getSpelerAanBeurt() aan.
     * @return
     */
    public int getSpelerAanBeurtGevecht() {
        return gevecht.getSpelerAanBeurt();
    }

    /**
     * Methode die badStuff uitvoert. --> Gebruikt geefBadStuff(id) & verhoogLevel.
     * @param id van de kaart
     */
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

    /**
     * Methode die bepaalt hoeveel levels er bij de spelerzijde moeten geteld worden als spelers meehelpen in het gevecht
     * @return extralevels dat er bij moet geteld worden
     */
    public int spelerLevels() {
        List<Boolean> helptmee = getHelptmee();
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

    /**
     * Methode die valideert of de Speler de kaart mag leggen adhv zijn positie in het spel (hij een helper is/tegenspeler of degene die de kaart trekt)
     * @param kaart int kaart die volgt uit de keuze van de speler
     * @return
     */
    public boolean validatieKaartSpeler(int kaart) {
        Kaart kr = kaarten.get(kaart);
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
                        if (gevecht.getHelptmee().get(gevecht.getSpelerAanBeurt())) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Methode die valideert of de speler de Kaart mag spelen adhv zijn positie in het spel (hij een helper is/tegenspeler of degene die de kaart trekt)
     * @param kaart kaart die meegegevn wordt
     * @param monster boolean die bepaalt of er een monster mag gespeeld worden of niet
     * @return boolean die bepaalt of de kaart mag gespeeld worden
     */
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
                        if (gevecht.getHelptmee().get(gevecht.getSpelerAanBeurt())) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean validatieKaartItems2(int kaart){
        int aantalWapens = 0;
        List<Kaart> items = geefSpeler(gevecht.getSpelerAanBeurt()).getItems();
        Kaart kr = kaarten.get(kaart);
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

    /**
     *  Methode die bepaalt of de speler de kaart mag spelen adhv de items die de persoon heeft
     * @param kaart de kaart die meegegeven wordt
     * @return boolean die bepaalt of de kaart gespeeld mag worden
     */
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

    /**
     * Methode die monsterkaart speelt tegen speler. --> roept geefSpeler(gevecht.getSpelerAanBeurt()).getKaarten().get(kaart - 1) aan.
     * @param kaart id van de kaart
     * @param monster true = monster
     */
    public void speelMonster(int kaart, boolean monster) {
        Kaart gespeeldeKaart = geefSpeler(gevecht.getSpelerAanBeurt()).getKaarten().get(kaart - 1);
        if (!monster || gevecht.getHelptmee().get(gevecht.getSpelerAanBeurt())) {
            gevecht.setSpelerBattlePoints(gevecht.getSpelerBattlePoints() + ((Monster) gespeeldeKaart).getLevel());
        } else {
            gevecht.setMonsterBattlePoints(gevecht.getMonsterBattlePoints() + ((Monster) gespeeldeKaart).getLevel());
        }
    }

    /**
     * Methode die consumable speelt tegen monster/speler
     * @param kaart id van de kaart
     */
    public void speelConsumable(int kaart) {
        Kaart gespeeldeKaart = geefSpeler(gevecht.getSpelerAanBeurt()).getKaarten().get(kaart - 1);
        if (gevecht.getHelptmee().get(gevecht.getSpelerAanBeurt())) {
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

    /**
     *
     * @param kaart
     */
    public void itemsBijvoegen(int kaart) {
        Kaart gespeeldeKaart = geefSpeler(gevecht.getSpelerAanBeurt()).getKaarten().get(kaart - 1);
        geefSpeler(gevecht.getSpelerAanBeurt()).getItems().add(gespeeldeKaart);
    }

    /**
     * Methode die de kaarten van de speler teruggeeft.
     * @param naam van de speler
     * @return String kaarten
     */
    public String geefKaartenVanSpeler(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefKaarten();
    }

    /**
     * Methode die items van speler retourneert als String
     * @param naam van de speler
     * @return String items
     */
    public String geefItemsVanSpeler(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefItems();
    }

    /**
     * Methode die Integers van de kaarten van de speler in een array steekt. --> roept spelers.get(i).geefKaartenInt() aan. Gebruikt zoekSpeler().
     * @param naam van de speler
     * @return array van ints
     */
    public int[] geefKaartenVanSpelerInt(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefKaartenInt();
    }

    /**
     * Methode die de Integers van de items van de speler in een array steekt. --> roept spelers.get(i).geefItemsInt() aan. Gebruikt zoekSpeler().
     * @param naam
     * @return
     */
    public int[] geefItemsVanSpelerInt(String naam) {
        int i = zoekSpeler(naam);
        return spelers.get(i).geefItemsInt();
    }

    /**
     * Methode die speler overzicht volgorde teruggeeft.
     * @return List van String met overzicht per speler
     */
    public List<String[]> geefspelerOverzichtVolgorde() {
        List<String[]> retSpelers = new ArrayList<>();
        int teller = 0;
        for (int i = spelerAanBeurt; i < aantalSpelers; i++) {
            if (teller < aantalSpelers) {
                Speler speler = spelers.get(i);
                String[] items = new String[6];
                for (int j = 0; j < speler.geefItemsInt().length; j++) {
                    items[j] = String.valueOf(speler.geefItemsInt()[j]);
                }
                String[] kaarten = new String[5];
                for (int j = 0; j < speler.geefItemsInt().length; j++) {
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

    /**
     * Methode die controleert welke kaart er gespeeld wordt.
     * @param kaart id van de kaart
     * @param monster true = monster
     * @return String met type kaart
     */
    public String controleWelkeKaart(int kaart, boolean monster){
        if (geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1) instanceof Curse) {
            //curseKaart();
            return "Curse";
        } else {
            if (geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1) instanceof Monster) {
                //monsterKaart(gespeeldeKaart);
                //speelMonster(kaart, monster);
                return "Monster";
            }
            if (geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1) instanceof ConsumablesKerker || geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1) instanceof ConsumablesSchat) {
                //consumablesKaart(gespeeldeKaart);
                //speelConsumable(kaart);
                return "Consumable";
            }
            if (geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1) instanceof Equipment || geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1) instanceof Race) {
                //itemsbijvoegen(gespeeldeKaart);
                return "Race/Weapon";
                //itemsBijvoegen(kaart);
            }
        }
        return "error";
    }

    /**
     * Methode die de schatkaarten uitdeelt aan de spelers. --> Gebruikt geefSpeler()
     * @param speler id van de speler
     * @param schatten aantal schatten
     */
    public void deelSchatkaartenUit(int speler, int schatten){
        for(int i = 0; i < schatten; i++){
            geefSpeler(speler).getKaarten().add(schatkaarten.remove(0));
        }
    }
}
