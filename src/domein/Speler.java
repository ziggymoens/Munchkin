package domein;

import java.util.*;
import language.LanguageResource;

/**
 *
 * @author G35
 */
public class Speler {

    private int leeftijd, level, aantalSchatkaarten, aantalKerkerkaarten;
    private String geslacht;
    private String naam;
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
        setLevel(level);
        kaarten = new ArrayList<>();
        aantalKerkerkaarten = 0; aantalSchatkaarten = 0;
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
        String man = bundle.getString("man");
        String vrouw = bundle.getString("woman");
        if (geslacht.toLowerCase().equals(man)|| geslacht.toLowerCase().equals(vrouw)) {
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
        return getAantalKerkerkaarten()+getAantalSchatkaarten();
    }
    
    public String kaartenNaarString() {
        String ret = "";
        for (int i = 0; i < geefAantalKaarten(); i++) {
            ret += String.format("%s  ", kaarten.get(i).getNaam());
        }
        return ret;
    }
    
    @Override
    public String toString() {
        return String.format("naam = %s, geslacht = %s, leeftijd = %d, level = %d, aantal schatkaarten = %d, aantal kerkerkaarten = %d, kaarten = %s", naam, geslacht, leeftijd, level, getAantalSchatkaarten(), getAantalKerkerkaarten(), kaartenNaarString());
    }

    public int getAantalSchatkaarten() {
        return aantalSchatkaarten;
    }

    public int getAantalKerkerkaarten() {
        return aantalKerkerkaarten;
    }

    public void setAantalSchatkaarten(int aantalSchatkaarten) {
        this.aantalSchatkaarten = aantalSchatkaarten;
    }

    public void setAantalKerkerkaarten(int aantalKerkerkaarten) {
        this.aantalKerkerkaarten = aantalKerkerkaarten;
    }
    
}
