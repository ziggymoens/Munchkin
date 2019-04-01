package persistentie.mappers;

import domein.Spel;
import domein.Speler;
import exceptions.database.SpelDatabaseException;
import persistentie.Connectie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SpelMapperDb {
    private Connection conn;
    private ResultSet rs;
    private List<Spel> spellen;

    private void voegToe() {
        try {
            this.conn = DriverManager.getConnection(Connectie.JDBC_URL);
        } catch (Exception ex) {
            throw new SpelDatabaseException(ex.getMessage());
        }
    }

    public List<Spel> geefSpellen() {
//        spellen.clear();
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g35.Spel");
            rs = query.executeQuery();
            while (rs.next()){
                int spelId = rs.getInt("spelid");
                String naam = rs.getString("naam");
                Boolean klein = rs.getBoolean("kleingroot");
                List<Speler> spelers = voegSpelersToe(spelId);
                List<Integer> volgnummerD = geefVolgorde("d", spelId);
                List<Integer> volgnummerT = geefVolgorde("t", spelId);
                // Spel spel = new Spel(naam, klein, spelers);
                // spellen.add(spelId, spel);
            }
            rs.close();
            query.close();
        } catch (Exception ex) {
            throw new SpelDatabaseException(ex.getMessage());
        }
        return spellen;
    }

    private List<Speler> voegSpelersToe(int spelId){
        List<Speler> spelers = new ArrayList<>();
        try {
            PreparedStatement query = conn.prepareStatement(String.format("SELECT * FROM ID222177_g35.Speler WHERE spelid = %d", spelId));
            rs = query.executeQuery();
            while (rs.next()){
                int spelerId = rs.getInt("spelerid");
                String naam = rs.getString("naam");
                Boolean geslacht = rs.getBoolean("geslacht");
                int level = rs.getInt("level");
                List<Integer> kaarten = geefKaarten(spelerId, "k");
                List<Integer> items = geefKaarten(spelerId, "i");
                Speler speler = new Speler(naam,geslacht ,level);
                spelers.add(spelerId, speler);
            }
            rs.close();
        }catch (Exception ex){
            throw new SpelDatabaseException(ex.getMessage());
        }
        return spelers;
    }

    private List<Integer> geefKaarten(int spelerId, String type) {
        List<Integer> kaarten = new ArrayList<>();
        try{
            PreparedStatement query = conn.prepareStatement(String.format("SELECT * FROM ID222177_g35.SpelerKaart WHERE SpelerId = %d", spelerId));
            rs = query.executeQuery();
            while (rs.next()){
                if (type.equals("i")){
                    if (rs.getBoolean("plaatsKaart")){
                        kaarten.add(rs.getInt("kaartId"));
                    }
                }else{
                    if (!rs.getBoolean("plaatsKaart")){
                        kaarten.add(rs.getInt("kaartId"));
                    }
                }
            }
            rs.close();
        } catch (Exception e){
            throw new SpelDatabaseException(e.getMessage());
        }
        return kaarten;
    }

    private List<Integer> geefVolgorde(String type, int spelId){
        List<Integer> volgorde = new ArrayList<>();
        int nr;
        try{
            PreparedStatement query = conn.prepareStatement(String.format("SELECT * FROM ID222177_g35.Speler WHERE spelkaart where spelid = %d", spelId));
            rs = query.executeQuery();
            while (rs.next()){
                if (type.equals("t")){
                    nr = rs.getInt("volgnumerT");
                }else{
                    nr = rs.getInt("volgnummerD");
                }
                int kaartId = rs.getInt("kaartid");
                volgorde.add(nr, kaartId);
            }
        }catch (Exception e){
            throw new SpelDatabaseException(e.getMessage());
        }
        return volgorde;
    }


    public void addSpel() {

    }
}
