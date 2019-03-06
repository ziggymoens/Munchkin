/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.BadStuff;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;
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

    private List<Kaart> consumablesDKaart(ResultSet rs) {
        List<Kaart> kaarten = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int bonus = rs.getInt("bonus");
                kaarten.add(new ConsumablesKerker(name, bonus));
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
                Boolean changeSex = rs.getBoolean("changeSex");
                int loseLevel = rs.getInt("loseLevel");
                String loseSomething = rs.getString("loseSomething");
                String description = rs.getString("description");
                kaarten.add(new Curse(name, description, loseLevel, loseSomething, changeSex));
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
                int level = rs.getInt("level");
                int raceBonus = rs.getInt("raceBonus");
                int escapeBonus = rs.getInt("escapeBonus");
                int tresures = rs.getInt("treasures");
                int levelUp = rs.getInt("levelUp");
                int persueLevel = rs.getInt("persueLevel");
                String description = rs.getString("description");
                String specialRace = rs.getString("specialRace");
                Boolean outRun = rs.getBoolean("outRun");
                int levelsLostHigherLevel = rs.getInt("levelsLostHigherLevel");
                String specialRaceReason = rs.getString("specialRaceReason");
                BadStuff bs = badStuffKaart();
                kaarten.add(new Monster(name, level, tresures, levelUp, description, outRun, escapeBonus, new Race(specialRace), raceBonus, specialRaceReason, persueLevel, levelsLostHigherLevel, bs));
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
                int goldPieces = rs.getInt("goldPieces");
                int bonus = rs.getInt("bonus");
                String description = rs.getString("description");
                boolean killsFloatingNose = rs.getBoolean("killsFloatingNose");
                kaarten.add(new ConsumablesSchat(name, goldPieces, description, bonus));
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
                int goldPieces = rs.getInt("goldPieces");
                int bonus = rs.getInt("bonus");
                int bonusRace = rs.getInt("bonusRace");
                int specialBonus = rs.getInt("specialBonus");
                int escapeBonus = rs.getInt("escapeBonus");
                String usableBy = rs.getString("usableBy");
                String specialRace = rs.getString("specialRace");
                kaarten.add(new Equipment(name, goldPieces, type, bonus, new Race(usableBy), name, bonus, specialBonus, specialRace));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return kaarten;
    }

    private BadStuff badStuffKaart() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
