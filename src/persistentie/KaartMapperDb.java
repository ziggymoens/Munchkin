/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Speler;
import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
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

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public void voegToe() {
        try (
                Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);) {
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Kaart> geefSpelers(String type) {
        List<Kaart> kaarten = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(String.format("SELECT * FROM ID222177_g35.%s", type));
                ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                //String description = rs.getString("description");
                if (type.equals("Race")) {
                    kaarten.add(new Race(name));
                } else if (type.equals("ConsumablesD")) {
                    kaarten.add(new ConsumablesKerker(name, 1));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */ }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */ }
        }
        return kaarten;
    }

}
