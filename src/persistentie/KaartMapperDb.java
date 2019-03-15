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
import exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * @author ziggy
 */
public class KaartMapperDb {

    private ResultSet rs;
    private Connection conn;
    private final Map<String, Runnable> soorten;
    private final List<Kaart> kaarten;

    public KaartMapperDb() {
        kaarten = new ArrayList<>();
        soorten = new HashMap<>();
        soorten.put("Race", this::raceKaart);
        soorten.put("ConsumablesD", this::consumablesDKaart);
        soorten.put("Curse", this::curseKaart);
        soorten.put("Monster", this::monsterKaart);
        soorten.put("ConsumablesT", this::consumablesTKaart);
        soorten.put("Equipment", this::equipmentKaart);
    }

    private void voegToe() {
        try {
            this.conn = DriverManager.getConnection(Connectie.JDBC_URL);
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    public List<Kaart> geefKaartenType(String type) {
        voegToe();
        kaarten.clear();
        try {
            PreparedStatement query = conn.prepareStatement(String.format("SELECT * FROM ID222177_g35.%s", type));
            rs = query.executeQuery();
            soorten.get(type).run();
            rs.close();
            query.close();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
        return kaarten;
    }

    private void raceKaart() {
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                boolean inGame = rs.getBoolean("inGame");
                if (inGame) {
                    this.kaarten.add(new Race(name, id, description));
                }
            }
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    private void consumablesDKaart() {
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int bonus = rs.getInt("bonus");
                boolean inGame = rs.getBoolean("inGame");
                if (inGame) {
                    kaarten.add(new ConsumablesKerker(name, id, bonus));
                }
            }
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    private void curseKaart() {
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int loseLevel = rs.getInt("loseLevel");
                String loseSomething = rs.getString("loseSomething");
                String description = rs.getString("description");
                boolean inGame = rs.getBoolean("inGame");
                if (inGame) {
                    kaarten.add(new Curse(name, id, loseLevel, loseSomething, description));
                }
            }
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    private void monsterKaart() {
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int level = rs.getInt("level");
                int raceBonus = rs.getInt("raceBonus");
                int escapeBonus = rs.getInt("escapeBonus");
                int tresures = rs.getInt("treasures");
                int levelUp = rs.getInt("levelUp");
                int persueLevel = rs.getInt("pursueLevel");
                String description = rs.getString("description");
                String specialRace = rs.getString("specialRace");
                boolean outRun = rs.getBoolean("outRun");
                boolean inGame = rs.getBoolean("inGame");
                if (inGame) {
                    BadStuff bs = badStuffKaart(rs.getInt("badStuffid"));
                    kaarten.add(new Monster(name, id, level, tresures, levelUp, description, outRun, escapeBonus, new Race(specialRace), raceBonus, persueLevel, bs));
                }
            }
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    private void consumablesTKaart() {
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int goldPieces = rs.getInt("goldPieces");
                int bonus = rs.getInt("bonus");
                String description = rs.getString("description");
                boolean inGame = rs.getBoolean("inGame");
                //boolean killsFloatingNose = rs.getBoolean("killsFloatingNose");
                if (inGame) {
                    kaarten.add(new ConsumablesSchat(name, id, goldPieces, description, bonus));
                }
            }
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    private void equipmentKaart() {
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int goldPieces = rs.getInt("goldPieces");
                String type = rs.getString("type");
                int bonus = rs.getInt("bonus");
                int specialBonus = rs.getInt("bonusRace");
                String usableBy = rs.getString("usableBy");
                String specialRace = rs.getString("specialRace");
                boolean inGame = rs.getBoolean("inGame");
                if (inGame){
                    kaarten.add(new Equipment(name, id, goldPieces, type, bonus, new Race(usableBy), bonus, specialBonus, new Race(specialRace)));
                }
            }
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    private BadStuff badStuffKaart(int badStuffId) {
        BadStuff bs = null;
        try (
                PreparedStatement query = conn.prepareStatement(String.format("SELECT * FROM ID222177_g35.BadStuff WHERE badStuffid = %d", badStuffId));
                ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                //String name = rs.getString("name");
                int id = rs.getInt("badStuffid");
                int loseItems = rs.getInt("loseItems");
                int loseLevels = rs.getInt("loseLevels");
                String loseSomething = rs.getString("loseSomething");
                String badStuffDescription = rs.getString("badStuffDescription");
                bs = new BadStuff(/*name,*/ id, loseItems, loseLevels, loseSomething, badStuffDescription);
            }
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
        return bs;
    }
}
