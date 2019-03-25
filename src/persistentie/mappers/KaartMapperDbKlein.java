package persistentie.mappers;

import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.kerkerkaarten.monsterbadstuff.BadStuff;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;
import exceptions.database.KaartDatabaseException;
import persistentie.Connectie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KaartMapperDbKlein {

    private ResultSet rs;
    private Connection conn;
    private final Map<String, Runnable> soorten;
    private final List<Kaart> kaarten;

    public KaartMapperDbKlein() {
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
            throw new KaartDatabaseException(ex.getMessage());
        }
    }

    public List<Kaart> geefKaartenType(String type) {
        voegToe();
        kaarten.clear();
        try {
            PreparedStatement query = conn.prepareStatement(String.format("SELECT * FROM ID222177_g35.K%s", type));
            rs = query.executeQuery();
            soorten.get(type).run();
            rs.close();
            query.close();
        } catch (Exception ex) {
            throw new KaartDatabaseException(ex.getMessage());
        }
        return kaarten;
    }

    private void raceKaart() {
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int combatBonus = rs.getInt("combatBonus");
                int runaway = rs.getInt("runaway");
                this.kaarten.add(new Race(name, id, combatBonus, runaway));
            }
        } catch (Exception ex) {
            throw new KaartDatabaseException(ex.getMessage());
        }
    }

    private void consumablesDKaart() {
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int bonus = rs.getInt("bonus");
                kaarten.add(new ConsumablesKerker(name, id, bonus));
            }
        } catch (Exception ex) {
            throw new KaartDatabaseException(ex.getMessage());
        }
    }

    private void curseKaart() {
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int loseLevel = rs.getInt("loseLevel");
                String description = rs.getString("description");
                kaarten.add(new Curse(name, id, loseLevel, description));
            }
        } catch (Exception ex) {
            throw new KaartDatabaseException(ex.getMessage());
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
                String description = rs.getString("description");
                String specialRace = rs.getString("specialRace");
                BadStuff bs = badStuffKaart(rs.getInt("badStuffid"));
                kaarten.add(new Monster(name, id, level, tresures, description, escapeBonus, new Race(specialRace), raceBonus, bs));
            }
        } catch (Exception ex) {
            throw new KaartDatabaseException(ex.getMessage());
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
                kaarten.add(new ConsumablesSchat(name, id, goldPieces, description, bonus));
            }
        } catch (Exception ex) {
            throw new KaartDatabaseException(ex.getMessage());
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
                int escapeBonus = rs.getInt("escapeBonus");
                boolean inGame = rs.getBoolean("inGame");
                if (inGame) {
                    kaarten.add(new Equipment(name, id, goldPieces, type, bonus, new Race(usableBy), bonus, specialBonus, new Race(specialRace)));
                }
            }
        } catch (Exception ex) {
            throw new KaartDatabaseException(ex.getMessage());
        }
    }

    private BadStuff badStuffKaart(int badStuffId) {
        BadStuff bs = null;
        try (
                PreparedStatement query = conn.prepareStatement(String.format("SELECT * FROM ID222177_g35.BadStuff WHERE badStuffid = %d", badStuffId));
                ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("badStuffid");
                int loseLevels = rs.getInt("loseLevels");
                String badStuffDescription = rs.getString("badStuffDescription");
                bs = new BadStuff(id, loseLevels, badStuffDescription);
            }
        } catch (Exception ex) {
            throw new KaartDatabaseException(ex.getMessage());
        }
        return bs;
    }
}