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
    private ArrayList<Kaart> kaarten;
    private ResourceBundle bundle;
    
    
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
        kaarten = new ArrayList<>();
        bundle = ResourceBundle.getBundle("domein/i18n", this.choice);
    }

    public final void setLeeftijd(int leeftijd) {
        if (leeftijd > 0) {
            this.leeftijd = leeftijd;
        } 
        else {
            throw new IllegalArgumentException(bundle.getString("exception.age")); // VRAGEN
        }
    }

    public int getLeeftijd() {
        return leeftijd;
    }

    public final void setGeslacht(String geslacht) {
        if (geslacht.toLowerCase().equals("man") || geslacht.toLowerCase().equals("vrouw")) {
            this.geslacht = geslacht;
        }
        else {
            throw new IllegalArgumentException(bundle.getString("exception.sex")); // VRAGEN
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
        }
        else
            throw new IllegalArgumentException(bundle.getString("exception.name")); // VRAGEN
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
    
    public void voegKaartToe(Kaart kaart){
        this.kaarten.add(kaart);
    }

    public ArrayList getKaarten(){
        return kaarten;
    }

    
    public int geefAantalKaarten(){
        int aantalKaarten = 0;
        for (Kaart kaarten1 : kaarten) {
            if (kaarten1 != null) {
                aantalKaarten++;
            }
        }
        return aantalKaarten;
    }
    
    public String kaartenNaarString(){
        String ret = "";
        for (int i = 0; i < geefAantalKaarten(); i++) {
            ret+= String.format("%s (type: %s, subtype: %s) ", kaarten.get(i).getNaam(), kaarten.get(i).getType(),kaarten.get(i).getSubtype());
        }
        return ret;
    }
    

    @Override
    public String toString() {
        return String.format("naam = %s, geslacht = %s, leeftijd = %d, level = %d, aantalKaarten = %d, kaarten = %s", naam, geslacht, leeftijd, level, geefAantalKaarten(), kaartenNaarString());
    }
    
    
}
