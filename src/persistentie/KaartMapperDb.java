/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Speler;
import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;
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

    public List<Kaart> geefKaartenType(String type) {
        List<Kaart> kaarten = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(String.format("SELECT * FROM ID222177_g35.%s", type));
                ResultSet rs = query.executeQuery()) {
            switch (type) {
                case "Race":
                    kaarten = raceKaart(rs);
                    break;
                case "ConsumablesD":
                    kaarten = consumablesDKaart(rs);
                    break;
                case "Curse":
                    kaarten = curseKaart(rs);
                    break;
                case "Monster":
                    kaarten = monsterKaart(rs);
                    break;
                case "ConsumablesT":
                    kaarten = consumablesTKaart(rs);
                    break;
                case "Equipment":
                    kaarten = equipmentKaart(rs);
                    break;
                default:
                    break;
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

    private List<Kaart> raceKaart(ResultSet rs) {
        List<Kaart> kaarten = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                kaarten.add(new Race(name, description));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return kaarten;
    }

    public List<Kaart> consumablesDKaart(ResultSet rs) {
        List<Kaart> kaarten = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                kaarten.add(new ConsumablesKerker(name, description));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return kaarten;
    }
    private List<Kaart> curseKaart(ResultSet rs) {
        List<Kaart> kaarten = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                kaarten.add(new Curse(name, description));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return kaarten;
    }

    private List<Kaart> monsterKaart(ResultSet rs) {
        List<Kaart> kaarten = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                kaarten.add(new Monster(name, description));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return kaarten;
    }

    private List<Kaart> consumablesTKaart(ResultSet rs) {
        List<Kaart> kaarten = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                kaarten.add(new ConsumablesSchat(name, description));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return kaarten;
    }

    private List<Kaart> equipmentKaart(ResultSet rs) {
        List<Kaart> kaarten = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                kaarten.add(new Equipment(name, description));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return kaarten;
    }
    }
}
