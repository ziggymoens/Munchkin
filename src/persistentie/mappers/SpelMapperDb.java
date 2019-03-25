package persistentie.mappers;

import domein.Spel;
import domein.kaarten.Kaart;
import exceptions.database.KaartDatabaseException;
import persistentie.Connectie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

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

    public List<Spel> geefSpellen(String type) {
        spellen.clear();
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement(String.format("SELECT * FROM ID222177_g35.%s", type));
            rs = query.executeQuery();

            rs.close();
            query.close();
        } catch (Exception ex) {
            throw new KaartDatabaseException(ex.getMessage());
        }
        return spellen;
    }




    public List<Spel> geefSpellen() {
        List<Spel> spellen = new ArrayList<>();
        return spellen;
    }

    public void addSpel() {

    }
}
