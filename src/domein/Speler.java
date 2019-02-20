package domein;

import java.util.*;
import language.LanguageResource;

/**
 *
 * @author G35
 */
public class Speler {

    private int leeftijd;
    private String geslacht;
    private String naam;
    private int level;
    private List<Kaart> kaarten;
    private static LanguageResource bundle = new LanguageResource();
    
    public Speler() {
        this("onbekend", "man", 99, 1, new Locale("en"));
    }
    
    public Speler(String naam, String geslacht, int leeftijd) {
        this(naam, geslacht, leeftijd, 1, new Locale("en"));
    }
    
    public Speler(String naam, String geslacht, int leeftijd, int level) {
        this(naam, geslacht, leeftijd, level, new Locale("en"));
    }
    
    public Speler(String naam, String geslacht, int leeftijd, int level, Locale choice) {
        bundle.setLocale(choice);
        setLeeftijd(leeftijd);
        setGeslacht(geslacht);
        setNaam(naam);
        setLevel(0);
        kaarten = new ArrayList<>();
        
    }
    
    public final void setLeeftijd(int leeftijd) {
        if (leeftijd > 0) {
            this.leeftijd = leeftijd;
        } else {
            throw new IllegalArgumentException(bundle.getString("exception.age"));
        }
    }
    
    public int getLeeftijd() {
        return leeftijd;
    }
    
    public final void setGeslacht(String geslacht) {
        if (geslacht.toLowerCase().equals(bundle.getString("man")) || geslacht.toLowerCase().equals(bundle.getString("woman"))) {
            this.geslacht = geslacht;
        } else {
            throw new IllegalArgumentException(bundle.getString("exception.sex"));
        }
    }
    
    public String getGeslacht() {
        return geslacht;
    }
    
    public final void setNaam(String naam) {
        if (naam.length() >= 6 && naam.length() <= 12) {
            for (int i = 0; i < naam.length(); i++) {
                if (naam.charAt(i) >= 'a' || naam.charAt(i) <= 'z' || naam.charAt(i) >= 'A' || naam.charAt(i) <= 'Z' || naam.charAt(i) == '_' || naam.charAt(i) == '-') {
                    this.naam = naam;
                }
            }
        } else {
            throw new IllegalArgumentException(bundle.getString("exception.name"));
        }
    }
    
    public String getNaam() {
        return naam;
    }
    
    public final void setLevel(int level) {
        if (level > 0) {
            this.level = level;
        } else {
            throw new IllegalArgumentException(bundle.getString("exception.level"));
        }
    }
    
    public int getLevel() {
        return level;
    }
    
    public void voegKaartToe(Kaart kaart) {
        this.kaarten.add(kaart);
    }
    
    public List<Kaart> getKaarten() {
        return kaarten;
    }
    
    public int geefAantalKaarten() {
        int aantalKaarten = 0;
        for (Kaart kaarten1 : kaarten) {
            if (kaarten1 != null) {
                aantalKaarten++;
            }
        }
        return aantalKaarten;
    }
    
    public String kaartenNaarString() {
        String ret = "";
        for (int i = 0; i < geefAantalKaarten(); i++) {
            ret += String.format("%s", kaarten.get(i).getNaam(), kaarten.get(i));
        }
        return ret;
    }
    
    @Override
    public String toString() {
        return String.format("naam = %s, geslacht = %s, leeftijd = %d, level = %d, aantalKaarten = %d, kaarten = %s", naam, geslacht, leeftijd, level, geefAantalKaarten(), kaartenNaarString());
    }
    
}
