package domein;

import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.Curse;
import domein.repositories.KaartDbRepository;
import exceptions.SpelException;

import java.util.*;

import exceptions.SpelerException;
import language.LanguageResource;

/**
 * @author ziggy
 */
public class Spel {

    //Declaratie attributen
    private int aantalSpelers;
    private final List<Speler> spelers;
    private final Map<Integer, Kaart> kaarten;
    private final List<Kaart> schatkaarten;
    private final List<Kaart> kerkerkaarten;
    private String naam;

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
        KaartDbRepository kr = new KaartDbRepository();
        kaarten = new HashMap<>();
        schatkaarten = kr.getSchatkaarten();
        for (Kaart kaart: kr.getSchatkaarten()){
            kaarten.put(kaart.getId(), kaart);
        }
        kerkerkaarten = kr.getKerkerkaarten();
        for (Kaart kaart: kr.getKerkerkaarten()){
            kaarten.put(kaart.getId(), kaart);
        }
    }

    /**
     * Setter voor aantal spelers met controle op aantal volgens
     * DR_AANTAL_SPELERS
     *
     * @param aantalSpelers Het gekozen aantal spelers
     */
    private void setAantalSpelers(int aantalSpelers) {
        if (aantalSpelers >= 3 && aantalSpelers <= 6) {
            this.aantalSpelers = aantalSpelers;
        } else {
            throw new SpelException("exception.spel.players");
        }
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
        if (speler.getLevel() == 9 && speler.isHeeftMonsterVerslaan()) {
            speler.setLevel(speler.getLevel() + 1);
        } else if (speler.getLevel() >= 1 && speler.getLevel() < 9) {
            speler.setLevel(speler.getLevel() + 1);
        }
    }

    /**
     * @return
     */
    public String geefSpelsituatie() {
        StringBuilder ret = new StringBuilder();
        for (Speler speler : spelers) {
            //items naar string weg
            ret.append(String.format("%s: %s, %s: %s, %s: %d, %s: %s%n", LanguageResource.getString("player.name"), speler.getNaam(), LanguageResource.getString("player.sex"), speler.getGeslacht(), LanguageResource.getString("player.level"), speler.getLevel(), LanguageResource.getString("player.items"), speler.kaartenNaarString(speler.getItems())));
        }
        return ret.toString();
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
        for (Speler speler: spelers){
            if (naam.equals(speler.getNaam())){
                speler.voegKaartToe(kerkerkaarten.get(0));
                kerkerkaarten.remove(0);
            }
        }
    }

    public void speelKerkerkaart(String naam) {
        Kaart kaart = kerkerkaarten.get(0);
        if(kaart instanceof Curse){
            if(((Curse) kaart).getTypeLost().equals("none")){
                for (Speler speler:spelers){
                    if (naam.equals(speler.getNaam())){
                        //als type niet item is.
                        speler.verwijderItems(((Curse) kaart).getTypeLost());
                    }
                }
            }else if(((Curse) kaart).getLevelLost() > 0){
                for (Speler speler:spelers){
                    if (naam.equals(speler.getNaam())){
                        speler.setLevel(speler.getLevel()-((Curse) kaart).getLevelLost());
                    }
                }
            }
        }
    }

    public String geefTypeLostCurse() {
        return ((Curse)kerkerkaarten.get(0)).getTypeLost();
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
        for (Speler speler : spelers){
            if (naam.equals(speler.getNaam())){
                return speler.getAantalItems();
            }
        }
        return -99;
    }

    public void verwijderItemSpeler(String naam, int keuze) {
        for (Speler speler:spelers){
            if (speler.getNaam().equals(naam)) {
                speler.verwijderItem(keuze);
            }
        }
    }

    public void geefSpelNaam(String naam){
        setNaam(naam);
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        int aantal = 0;
        if (naam.length() >= 6 && naam.length() <= 12 && naam.matches("^[A-Za-z0-9]+$")) {
            for(int i = 0; i < naam.length(); i++){
                if(Character.isDigit(naam.charAt(i))){
                    aantal++;
                }
            }
                if(aantal >= 3){
                    this.naam = naam;
                }else{
                    throw new SpelException("exception.spel.name");
                }

        } else {
            throw new SpelException("exception.spel.name");
        }
    }
}
