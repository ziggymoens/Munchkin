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
    private Locale choice;

    public Speler(int leeftijd, String geslacht, String naam) {
        this(leeftijd, geslacht, naam, new Locale("en"));
    }

    public Speler(int leeftijd, String geslacht, String naam, Locale choice) {
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
}
