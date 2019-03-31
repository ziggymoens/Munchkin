package persistentie.mappers;

import domein.Spel;
import domein.Speler;
import exceptions.database.KaartDatabaseException;
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
            throw new KaartDatabaseException(ex.getMessage());
        }
    }

    public List<Spel> geefSpellen() {
        spellen.clear();
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement(String.format("SELECT * FROM ID222177_g35.Spel"));
            rs = query.executeQuery();
            while (rs.next()){
                int spelId = rs.getInt("spelid");
                String naam = rs.getString("naam");
                Boolean klein = rs.getBoolean("kleingroot");
                List<Speler> spelers = voegSpelersToe(spelId);
                Spel spel = new Spel(naam, klein, spelers);


                spellen.add(spelId, spel);
            }
            rs.close();
            query.close();
        } catch (Exception ex) {
            throw new KaartDatabaseException(ex.getMessage());
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
                Speler speler = new Speler(naam,geslacht ,level);
                spelers.add(spelerId, speler);
            }
            rs.close();
        }catch (Exception ex){

        }
        return spelers;
    }



    public void addSpel() {

    }
}
