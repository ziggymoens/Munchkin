package domein;

import domein.kaarten.Kaart;
import domein.kaarten.Schatkaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;
import exceptions.SpelerException;
import language.LanguageResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author G35
 */
public class Speler {

    //Declaratie attributen
    private int spelerId;
    private int level, aantalSchatkaarten, aantalKerkerkaarten;
    private String geslacht;
    private String naam;
    public List<Kaart> kaarten;
    private final List<Integer> volgordeKaarten;
    public List<Kaart> items;
    private final List<Integer> volgordeItems;
    private boolean heeftMonsterVerslaan;
    private Map<String, Integer> protection;
    private Map<String, Integer> protectionMax;

    /**
     * Constructor van Speler zonder parameters naam = "onbekend", geslacht =
     * "man", leeftijd = 99, level = 1, taal = "en"
     */
    public Speler() {
        setLevel(1);
        kaarten = new ArrayList<>();
        volgordeKaarten = new ArrayList<>();
        items = new ArrayList<>();
        volgordeItems = new ArrayList<>();
        aantalKerkerkaarten = 0;
        aantalSchatkaarten = 0;
        protection = new HashMap<>();
        protection.put("head", 0);
        protection.put("armor", 0);
        protection.put("foot", 0);
        protection.put("weapon", 0);
        protectionMax = new HashMap<>();
        protectionMax.put("head", 1);
        protectionMax.put("armor", 1);
        protectionMax.put("foot", 1);
        protectionMax.put("weapon", 2);
    }

    /**
     * Constructor van Speler zonder opgegeven taal, standaardtaal is "en" en
     * standaard level is 1
     *
     * @param naam     De naam van de speler
     * @param geslacht Het geslacht van de speler
     */
    public Speler(String naam, String geslacht) {
        setGeslacht(geslacht);
        setNaam(naam);
        setLevel(1);
        kaarten = new ArrayList<>();
        volgordeKaarten = new ArrayList<>();
        items = new ArrayList<>();
        volgordeItems = new ArrayList<>();
        aantalKerkerkaarten = 0;
        aantalSchatkaarten = 0;
    }

    /**
     * Constructor van speler
     *
     * @param naam  De naam van de speler
     * @param ges   Het geslacht van de speler
     * @param level Het level van de Speler
     */
    public Speler(String naam, Boolean ges, int level, List<Integer> kaarten, List<Integer> items) {
        String geslacht = ges ? LanguageResource.getString("man") : LanguageResource.getString("woman");
        setGeslacht(geslacht);
        setNaam(naam);
        setLevel(level);
        this.kaarten = new ArrayList<>();
        this.volgordeKaarten = new ArrayList<>();
        volgordeKaarten.addAll(kaarten);
        this.items = new ArrayList<>();
        this.volgordeItems = new ArrayList<>();
        volgordeItems.addAll(items);
        aantalKerkerkaarten = 0;
        aantalSchatkaarten = 0;
    }


    /**
     * Setter voor het geslacht van de speler, controle van geslacht in de taal
     * van de speler
     *
     * @param geslacht Het geslacht van de speler in eigen taal
     */
    public final void setGeslacht(String geslacht) {
        if (!geslacht.toLowerCase().equals(LanguageResource.getString("man")) && !geslacht.toLowerCase().equals(LanguageResource.getString("woman"))) {
            throw new SpelerException("exception.speler.sex");
        }
        this.geslacht = geslacht;

    }

    /**
     * Geslacht van de speler opvragen
     *
     * @return Het geslacht van de speler
     */
    public String getGeslacht() {
        return geslacht;
    }

    /**
     * Setter voor de naam van de speler met controle volgens DR
     *
     * @param naam De naam van de speler
     */
    public final void setNaam(String naam) {
        if (naam.length() >= 6 && naam.length() <= 12 && naam.matches("^[A-Za-z_-]+$")) {
            this.naam = naam;
        } else {
            throw new SpelerException("exception.speler.name");
        }
    }

    /**
     * Naam van de speler opvragen
     *
     * @return De naam van de speler
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Setter voor het level van de speler
     *
     * @param level Het level van de speler (groter dan 0)
     */
    public final void setLevel(int level) {
        if (level >= 1) {
            this.level = level;
        } else {
            this.level = 1;
        }
    }

    /**
     * Het level van de speler opvragen
     *
     * @return Level van de speler
     */
    public int getLevel() {
        return level;
    }

    /**
     * Voeg kaart toe aan hand van speler
     *
     * @param kaart de toegewezen kaart
     */
    public void voegKaartToe(Kaart kaart) {
        //this.kaarten.add(kaart);
        if (kaart instanceof Equipment || kaart instanceof ConsumablesSchat) {
            aantalSchatkaarten++;
        } else if (kaart instanceof Monster || kaart instanceof Curse || kaart instanceof Race || kaart instanceof ConsumablesKerker) {
            aantalKerkerkaarten++;
        }
        kaarten.add(kaart);
    }

    /**
     * Kaarten van de speler opvragen
     *
     * @return List met Kaart-objecten
     */
    public List<Kaart> getKaarten() {
        return kaarten;
    }

    /**
     * Items van speler opvragen
     *
     * @return List met Kaart-objecten
     */
    public List<Kaart> getItems() {
        return items;
    }

    /**
     * Het aantal kaarten van de speler opvragen
     *
     * @return Aantal kaarten van de speler
     */
    public int geefAantalKaarten() {
        return kaarten.size();
    }

    /**
     * Kaarten omzetten naar String
     *
     * @return String met info over de kaart
     */
    public String kaartenNaarString(List<Kaart> kaarten) {
        StringBuilder ret = new StringBuilder();
        if (kaarten.size() == 0) {
            ret.append("empty");
        } else {
            ret.append(kaarten.get(0).getNaam());
            for (int i = 1; i < kaarten.size(); i++) {
                ret.append(String.format(", %s", kaarten.get(i).getNaam()));
            }
        }
        return ret.toString();
    }

    /**
     * Gegevens van Speler naar String omzetten
     *
     * @return String met info over speler
     */
    @Override
    public String toString() {
        return String.format("%s = %s, %s = %s, %s = %d, %s = %d, %s = %d", LanguageResource.getString("player.name"), naam, LanguageResource.getString("player.sex"), geslacht, LanguageResource.getString("player.level"), level, LanguageResource.getString("player.treasurecards"), getAantalSchatkaarten(), LanguageResource.getString("player.dungeoncards"), getAantalKerkerkaarten());
    }

    /**
     * Het aantal schatkaarten van de speler opvragen
     *
     * @return Het aantal schatkaarten
     */
    public int getAantalSchatkaarten() {
        return aantalSchatkaarten;
    }

    /**
     * Het aantal kerkerkaarten van de speler opvragen
     *
     * @return Het aantal kerkerkaarten
     */
    private int getAantalKerkerkaarten() {
        return aantalKerkerkaarten;
    }

    /**
     * Het aantal schatkaarten aanpassen
     *
     * @param aantalSchatkaarten Nieuw aantal schatkaarten
     */
    public void setAantalSchatkaarten(int aantalSchatkaarten) {
        this.aantalSchatkaarten = aantalSchatkaarten;
    }

    /**
     * Het aantal kerkerkaarten aanpassen
     *
     * @param aantalKerkerkaarten Nieuw aantal kerkerkaarten
     */
    public void setAantalKerkerkaarten(int aantalKerkerkaarten) {
        this.aantalKerkerkaarten = aantalKerkerkaarten;
    }


    /**
     *
     * @param heeftMonsterVerslaan
     */
    /*
    public void setHeeftMonsterVerslaan(boolean heeftMonsterVerslaan) {
        this.heeftMonsterVerslaan = heeftMonsterVerslaan;
    }*/

    /**
     * Methode die boolean retourneert of monster verslagen is.
     * @return true = verslagen
     */
    public boolean heeftMonsterVerslaan() {
        return heeftMonsterVerslaan;
    }

    /**
     *
     * @param typeLost
     */
    /*
    public void verwijderItems(String typeLost) {
        for (Kaart kaart : items) {
            if (kaart instanceof Equipment) {
                if (((Equipment) kaart).getType().toLowerCase().equals(typeLost.toLowerCase())) {
                    items.remove(kaart);
                }

            }
            if (typeLost.toLowerCase().equals("race")) {
                if (kaart instanceof Race) {
                    items.remove(kaart);
                }
            }
            if (typeLost.toLowerCase().equals("sex")) {
                if (geslacht.equals(LanguageResource.getString("man"))) {
                    setGeslacht(LanguageResource.getString("woman"));
                } else {
                    setGeslacht(LanguageResource.getString("man"));
                }
            }
        }
        updateKaarten();
    }
    */

    /**
     * Methode die aantalItems retourneert adhv grootte van de lijst van items
     * @return int aantal
     */
    public int getAantalItems() {
        return items.size();
    }

    /**
     * Methode die item verwijdert.
     * @param keuze id van kaart
     */
    public void verwijderItem(int keuze) {
        items.remove(keuze);
        updateKaarten();
    }

    /**
     * Methode die aantal kaarten retourneert
     * @return aantal kaarten
     */
    public int getAantalKaarten() {
        return aantalSchatkaarten + aantalKerkerkaarten;
    }

    /**
     * Methode die String retourneert van alle kaarten die naar items kunnen.
     * @return String alle kaarten die naar items kunnen.
     */
    public String geefKaartenKunnenNaarItems() {
        StringBuilder ret = new StringBuilder();
        int j = 0;
        for (Kaart kaart : kaarten) {
            if (kaart instanceof Race || kaart instanceof Equipment) {
                String idKaart = "ID = " + kaart.getId();
                ret.append(String.format("%d) %s - %s %n", j+1, kaart.getNaam(), idKaart));
                j++;
            }
        }
        return ret.toString();
    }

    /**
     * Methode die String retourneert van alle kaarten die verkoopbaar zijn.
     * @return String van verkoopbare kaarten
     */
    public String geefVerkoopbareKaarten() {
        StringBuilder ret = new StringBuilder();
        int j = 1;
        for (Kaart kaart : kaarten) {
            if (kaart instanceof Equipment || kaart instanceof ConsumablesSchat) {
                String idKaart = "ID = " + kaart.getId();
                ret.append(String.format("%d) %s - %s %d %s - %s %n", j, kaart.getNaam(), LanguageResource.getString("kaart.value"), ((Schatkaart) kaart).getWaarde(), LanguageResource.getString("coins"), idKaart));
                j++;
            } else {
                continue;
            }
        }
        return ret.toString();
    }

    /**
     * Methode die lijst van Integers doorgeeft van de id's van de verkoopbare kaarten
     * @return List van Integers met id's verkoopbare kaarten
     */
    public List<Integer> geefIdVerkoopbareKaarten() {
        List<Integer> idKaart = new ArrayList<>();
        for (Kaart kaart : kaarten) {
            if (kaart instanceof Equipment || kaart instanceof ConsumablesSchat) {
                idKaart.add(kaart.getId());
            }
        }
        return idKaart;
    }

    /**
     * Methode die lijst van Integers doorgeeft van de id's van de kaarten die naar items kunnen
     * @return List van Integeres met id's verplaatsbare kaarten
     */
    public List<Integer> geefIdKaartenNaarItems() {
        List<Integer> idKaart = new ArrayList<>();
        for (Kaart kaart : kaarten) {
            if (kaart instanceof Race || kaart instanceof Equipment) {
                idKaart.add(kaart.getId());
            }
        }
        return idKaart;
    }

    /**
     * Methode die kaart verwijdert. --  roept updateKaarten() aan.
     * @param kaart kaart
     */
    public void verwijderKaart(Kaart kaart) {
        kaarten.add(kaart);

        updateKaarten();
    }

    /**
     * Methode om waarde van de schatkaart op te vragen
     * @return List van Integers met waardes van schatkaarten
     */
    public List<Integer> getWaardeSchatkaart() {
        List<Integer> waardes = new ArrayList<>();
        for (Kaart kaart : kaarten) {
            if (kaart instanceof Equipment || kaart instanceof ConsumablesSchat) {
                waardes.add(((Schatkaart) kaart).getWaarde());
            }
        }
        return waardes;
    }

    /**
     * Methode die String van alles niet verkoopbare kaarten teruggeeft.
     * @return String met alle niet verkoopbare kaarten.
     */
    public String geefNietVerkoopbareKaarten() {
        StringBuilder ret = new StringBuilder();
        int j = 1;
        for (Kaart kaart : kaarten) {
            String idKaart = "ID = " + kaart.getId();
            ret.append(String.format("%d) %s - %s %n", j, kaart.getNaam(), idKaart));
            j++;
        }
        return ret.toString();
    }

    /* Systeem toont naam en type van de kaarten in de hand*/
    /*public String toonOverzichtKaartenInHand() {

        String output = "";
        int i = 1;
        for (Kaart kaart : kaarten) {
            output += String.format("%d: %s, %s", i, kaart.getNaam(), kaart.getClass().getSimpleName());
            i++;
        }

        return String.format("Kaarten in de hand: %n%s", output);
    }
    */

    /**
     * Methode die volgorde van de kaarten teruggeeft.
     * @return volgorde van de kaarten
     */
    public List<Integer> getVolgordeKaarten() {
        return volgordeKaarten;
    }

    /**
     * Methode die volgorde van de kaarten instelt.
     */
    private void setVolgordeKaarten() {
        volgordeKaarten.clear();
        for (Kaart kaart : kaarten) {
            volgordeKaarten.add(kaart.getId());
        }
    }

    /**
     * Methode die volgorde van de items retourneert
     * @return List van Integers met id's in volgorde
     */
    public List<Integer> getVolgordeItems() {
        return volgordeItems;
    }

    /**
     * Methode die volgorde van de items instelt.
     */
    private void setVolgordeItems() {
        volgordeItems.clear();
        for (Kaart kaart : items) {
            volgordeItems.add(kaart.getId());
        }
    }

    /**
     * Methode die de kaarten update, zijnde in de juiste volgorde zet.
     */
    public void updateKaarten() {
        setVolgordeKaarten();
        setVolgordeItems();
    }

    /**
     * Setter die de juiste id toekent aan de juiste speler
     * @param spelerId
     */
    public void setSpelerId(int spelerId) {
        this.spelerId = spelerId;
    }

    /**
     * Getter van de spelerId
     * @return int spelerid
     */
    public int getSpelerId() {
        return spelerId;
    }

    /**
     * Setter van de kaarten. --  roept updateKaarten() aan.
     * @param kaarten Lijst van kaarten
     */
    public void setKaarten(List<Kaart> kaarten) {
        this.kaarten = kaarten;
        updateKaarten();
    }

    /**
     * Setter van de items. --  roept updateKaarten() aan.
     * @param items Lijst van items
     */
    public void setItems(List<Kaart> items) {
        this.items = items;
        updateKaarten();
    }

    /**
     * Methode die ID's van kaarten in hand retourneert
     * @return Lijst van Integers met id's kaarten in hand
     */
    public List<Integer> getIDKaartenInHand() {
        List<Integer> ids = new ArrayList<>();
        for (Kaart kaart : kaarten) {
            ids.add(kaart.getId());
        }
        return ids;
    }

    /**
     * Methode die items update wanneer er een andere vorm van bescherming gekozen wordt.
     */
    public void updateItems(){
        for (Kaart item: items) {
            if (item instanceof Equipment) {
                int old = protection.get(((Equipment) item).getType());
                protection.replace(((Equipment) item).getType(), old + 1);
            }
        }
    }

    /*
    private boolean controleItems() {
        String[] types = {"head", "armor", "foot", "weapon"};
        boolean oke = true;
        int aantalRace = 0;
        for (String type : types) {
            oke = protection.get(type) <= protectionMax.get(type);
        }
        for (Kaart item : items) {
            if (item instanceof Race) {
                aantalRace++;
            }
        }
        oke = aantalRace >= 1;
        return oke;
    }
    */

    /**
     * Methode die de kaarten aan de speler geeft in Stringvorm. --  roept overzichtMaken(ret, items) aan.
     * @return String met kaarten.
     */
    public String geefKaarten(){
        StringBuilder ret = new StringBuilder();
        ret.append(String.format("%s%n", "Kaarten"));
        return overzichtMaken(ret, kaarten);
    }

    /**
     * Methode die de items aan de speler geeft in Strinvorm. --  roept overzichtMaken(ret, items) aan.
     * @return String met items.
     */
    public String geefItems(){
        StringBuilder ret = new StringBuilder();
        ret.append(String.format("%s%n", "Items"));
        return overzichtMaken(ret, items);
    }

    /**
     * Methode die overzicht maakt van de verschillende kaarten.
     * @param sb Stringbuilder
     * @param lijst van kaarten
     * @return String met overzicht.
     */
    private String overzichtMaken(StringBuilder sb, List<Kaart> lijst){
        for (Kaart kaart : lijst) {
            String idKaart = "ID = " + kaart.getId();
            sb.append(String.format("%s - %s%n", kaart.getNaam(), idKaart));
        }
        return sb.toString();
    }

    /**
     * Methode die int van kaarten teruggeeft in een array.
     * @return array van ints
     */
    public int[] geefKaartenInt() {
        int[] k = new int[kaarten.size()];
        for (int i = 0; i<kaarten.size(); i++){
            k[i] = kaarten.get(i).getId();
        }
        return k;
    }

    /**
     * Methode die int van items teruggeeft in een array.
     * @return array van ints
     */
    public int[] geefItemsInt() {
        int[] k = new int[items.size()];
        for (int i = 0; i<items.size(); i++){
            k[i] = items.get(i).getId();
        }
        return k;
    }

    /**
     * Getter voor race, is standaard human.
     * @return String race
     */
    public String getRace() {
        for(Kaart kaart: items){
            if(kaart instanceof Race){
                return ((Race)kaart).getType();
            }
        }
        return "Human";
    }
}
