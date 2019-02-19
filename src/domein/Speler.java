package domein;

import java.util.*;

/**
 *
 * @author G35
 */
public class Speler {
    private int leeftijd;
    private String geslacht;
    private String naam;
    private int level;
    private Locale choice;
    
    public Speler(){
        setNaam("onbekend");
        setLeeftijd(99);
        setGeslacht("onbekend");
    }

    public Speler(String naam, String geslacht, int leeftijd) {
        this(naam, geslacht, leeftijd, new Locale("en"));
    }

    public Speler(String naam, String geslacht, int leeftijd, Locale choice) {
        setLeeftijd(leeftijd);
        setGeslacht(geslacht);
        setNaam(naam);
        this.choice = choice;
    }

    public final void setLeeftijd(int leeftijd) {
        if (leeftijd > 0) {
            this.leeftijd = leeftijd;
        } else {
            throw new IllegalArgumentException(ResourceBundle.getBundle("/domein/i18n",this.choice).getString("exception.age")); // VRAGEN
        }
    }

    public int getLeeftijd() {
        return leeftijd;
    }

    public final void setGeslacht(String geslacht) {
        if (geslacht.toLowerCase().equals("man") || geslacht.toLowerCase().equals("vrouw")) {
            this.geslacht = geslacht;
        } else {
            throw new IllegalArgumentException(ResourceBundle.getBundle("/domein/i18n",this.choice).getString("exception.sex")); // VRAGEN
        }
    }

    public String getGeslacht() {
        return geslacht;
    }

    public final void setNaam(String naam) {
        if(naam.length() >= 6 && naam.length() <= 12){
            for(int i = 0; i < naam.length(); i++){
                if(naam.charAt(i) >= 'a' || naam.charAt(i) <= 'z' || naam.charAt(i) >= 'A' || naam.charAt(i) <= 'Z' || naam.charAt(i) == '_' || naam.charAt(i) == '-')
                    this.naam = naam;
            }
        }else
            throw new IllegalArgumentException(ResourceBundle.getBundle("/domein/i18n",this.choice).getString("exception.name")); // VRAGEN
        
    }

    public String getNaam() {
        return naam;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
    
    
}
