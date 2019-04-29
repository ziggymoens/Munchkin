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
import java.util.List;

/**
 * @author G35
 */
public class Speler {

    //Declaratie attributen
    private int spelerId;
    private int level, aantalSchatkaarten, aantalKerkerkaarten;
    private String geslacht;
    private String naam;
    private List<Kaart> kaarten;
    private final List<Integer> volgordeKaarten;
    private List<Kaart> items;
    private final List<Integer> volgordeItems;
    private boolean heeftMonsterVerslaan;

    /**
     * Constructor van Speler zonder parameters naam = "onbekend", geslacht =
     * "man", leeftijd = 99, level = 1, taal = "en"
     */
    public Speler() {
        setLevel(4);
        kaarten = new ArrayList<>();
        volgordeKaarten = new ArrayList<>();
        items = new ArrayList<>();
        volgordeItems = new ArrayList<>();
        aantalKerkerkaarten = 0;
        aantalSchatkaarten = 0;
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
        volgordeKaarten = kaarten;
        this.items = new ArrayList<>();
        volgordeItems = items;
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
//            for (int i = 0; i < naam.length(); i++) {
//                if (naam.charAt(i) >= 'a' || naam.charAt(i) <= 'z' || naam.charAt(i) >= 'A' || naam.charAt(i) <= 'Z' || naam.charAt(i) == '_' || naam.charAt(i) == '-') {
//                    this.naam = naam;
//                }
//            }
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
     * @param heeftMonsterVerslaan
     */
    public void setHeeftMonsterVerslaan(boolean heeftMonsterVerslaan) {
        this.heeftMonsterVerslaan = heeftMonsterVerslaan;
    }

    /**
     * @return
     */
    public boolean heeftMonsterVerslaan() {
        return heeftMonsterVerslaan;
    }

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

    public int getAantalItems() {
        return items.size();
    }

    public void verwijderItem(int keuze) {
        items.remove(keuze);
        updateKaarten();
    }

    public int getAantalKaarten() {
        return aantalSchatkaarten + aantalKerkerkaarten;
    }

    public String geefKaartenKunnenNaarItems() {
        StringBuilder ret = new StringBuilder();
        int j = 0;
        for (Kaart kaart : kaarten) {
            if (kaart instanceof Race || kaart instanceof Equipment) {
                String idKaart = "ID = " + kaart.getId();
                ret.append(String.format("%d) %s - %s %n", j, kaart.getNaam(), idKaart));
                j++;
            }
        }
        return ret.toString();
    }

    public String geefVerkoopbareKaarten() {
        StringBuilder ret = new StringBuilder();
        int j = 0;
        for (Kaart kaart : kaarten) {
            if (kaart instanceof Equipment || kaart instanceof ConsumablesSchat) {
                String idKaart = "ID = " + kaart.getId();
                ret.append(String.format("%d) %s - %s %d %s - %s %n", j, kaart.getNaam(), LanguageResource.getString("kaart.value"), ((Schatkaart) kaart).getWaarde(), LanguageResource.getString("coins") ,idKaart));
                j++;
            } else {
                continue;
            }
        }
        return ret.toString();
    }

    public List<Integer> geefIdVerkoopbareKaarten(){
        List<Integer> idKaart = new ArrayList<>();
        for(Kaart kaart : kaarten){
            if(kaart instanceof Equipment || kaart instanceof ConsumablesSchat){
                idKaart.add(kaart.getId());
            }
        }
        return idKaart;
    }

    public List<Integer> geefIdKaartenNaarItems(){
        List<Integer> idKaart = new ArrayList<>();
        for(Kaart kaart : kaarten){
            if(kaart instanceof Race || kaart instanceof Equipment){
                idKaart.add(kaart.getId());
            }
        }
        return idKaart;
    }

    public void verwijderKaart(Kaart kaart) {
        kaarten.add(kaart);

        updateKaarten();
    }

    //methode om waarde van schatkaart op te vragen
    public List<Integer> getWaardeSchatkaart(){
        List<Integer> waardes = new ArrayList<>();
        for(Kaart kaart : kaarten) {
            if (kaart instanceof Equipment || kaart instanceof ConsumablesSchat) {
                waardes.add(((Schatkaart) kaart).getWaarde());
            }
        }
        return waardes;
    }


    public String geefNietVerkoopbareKaarten() {
        StringBuilder ret = new StringBuilder();
        int j = 0;
        for (Kaart kaart : kaarten) {
            String idKaart = "ID = " + kaart.getId();
            ret.append(String.format("%d) %s - %s %n", j, kaart.getNaam(), idKaart));
            j++;
        }
        return ret.toString();
    }

    //fix
    /* Systeem toont naam en type van de kaarten in de hand*/
    public String toonOverzichtKaartenInHand() {

        String output = "";
        int i = 1;
        for (Kaart kaart : kaarten) {
            output += String.format("%d: %s, %s", i, kaart.getNaam(), kaart.getClass().getSimpleName());
            i++;
        }

        return String.format("Kaarten in de hand: %n%s", output);
    }

    public List<Integer> getVolgordeKaarten() {
        return volgordeKaarten;
    }

    private void setVolgordeKaarten(){
        volgordeKaarten.clear();
        for (Kaart kaart : kaarten){
            volgordeKaarten.add(kaart.getId());
        }
    }

    public List<Integer> getVolgordeItems() {
        return volgordeItems;
    }

    private void setVolgordeItems(){
        volgordeItems.clear();
        for (Kaart kaart: items){
            volgordeItems.add(kaart.getId());
        }
    }

    public void updateKaarten(){
        setVolgordeKaarten();
        setVolgordeItems();
    }

    public void setSpelerId(int spelerId) {
        this.spelerId = spelerId;
    }

    public int getSpelerId() {
        return spelerId;
    }

    public void setKaarten(List<Kaart> kaarten) {
        this.kaarten = kaarten;
        updateKaarten();
    }

    public void setItems(List<Kaart> items) {
        this.items = items;
        updateKaarten();
    }


    public List<Integer> getIDKaartenInHand() {
        List<Integer> ids = new ArrayList<>();
        for (Kaart kaart: kaarten){
            ids.add(kaart.getId());
        }
        return ids;
    }

}
