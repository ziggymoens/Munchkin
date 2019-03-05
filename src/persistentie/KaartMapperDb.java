/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Speler;
import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.Race;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ziggy
 */
public class KaartMapperDb {

    public void voegToe() {
        try (
            Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);) {
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public List<Kaart> geefSpelers() {
        List<Kaart> kaarten = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g35.Race");
                ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");

                kaarten.add(new Race(name));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return kaarten;
    }
    
    
}
