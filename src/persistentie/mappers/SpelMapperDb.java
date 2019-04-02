package persistentie.mappers;

import domein.Spel;
import domein.Speler;
import exceptions.database.SpelDatabaseException;
import language.LanguageResource;
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
    private List<Spel> spellen = new ArrayList<>();
    private static final String INSERT_GAME = "INSERT INTO ID222177_g35.Spel (spelid, naam, kleingroot) VALUES (?, ?, ?)";
    private static final String DELETE_GAME = "DELETE FROM ID222177_g35.Spel WHERE spelid = ?";
    private static final String ALL_GAMES = "SELECT * FROM ID222177_g35.Spel";
    private static final String GAME_CARDSEQ = "SELECT * FROM ID222177_g35.SpelerKaart WHERE spelerid = ?";
    private static final String GAME_GETID = "SELECT spelid FROM ID222177_g35.Spel WHERE naam = ?";
    private static final String PLAYER_GETCARDS = "SELECT * FROM ID222177_g35.SpelerKaart WHERE SpelerId = ?";
    private static final String GAME_GETPLAYERS = "SELECT * FROM ID222177_g35.Speler WHERE spelid = ?";
    private static final String GAME_LOAD = "SELECT * FROM ID222177_g35.Spel WHERE spelid = ?";
    private static final String PLAYER_SAVECARD = "INSERT INTO ID222177_g35.SpelerKaart (spelerid, kaartid, plaatsKaart) VALUES (?, ?, ?)";
    private static final String PLAYER_SAVE = "INSERT INTO ID222177_g35.Speler (spelerid, naam, level, geslacht, spelid) VALUES (?,?,?,?,?)";
    private static final String GAME_CARDSAVE = "INSERT INTO ID222177_g35.SpelKaart (spelid, kaartid, volgnummerD, volgnumerT) values (?,?,?,?)";
    private static final String DELETE_PLAYERS = "DELETE FROM ID222177_g35.Speler WHERE spelid = ?";
    private static final String DELETE_SPELERKAART = "DELETE FROM ID222177_g35.SpelerKaart";
    private static final String DELETE_SPELKAART = "DELETE FROM SpelKaart WHERE spelid = ?";
    private void voegToe() {
        try {
            this.conn = DriverManager.getConnection(Connectie.JDBC_URL);
        } catch (Exception ex) {
            throw new SpelDatabaseException(ex.getMessage());
        }
    }

    public Spel laadSpel(int spelId) {
        spellen.clear();
        Spel spel = null;
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement(GAME_LOAD);
            query.setInt(1, spelId);
            rs = query.executeQuery();
            while (rs.next()) {
                int Id = rs.getInt("spelid");
                String naam = rs.getString("naam");
                Boolean klein = rs.getBoolean("kleingroot");
                List<Speler> spelers = voegSpelersToe(spelId);
                List<Integer> volgnummerD = geefVolgorde("d", spelId);
                List<Integer> volgnummerT = geefVolgorde("t", spelId);
                spel = new Spel(naam, klein, spelers, volgnummerD, volgnummerT);
            }
            rs.close();
            query.close();
            conn.close();
        } catch (Exception ex) {
            throw new SpelDatabaseException(ex.getMessage());
        }
        return spel;
    }

    private List<Speler> voegSpelersToe(int spelId) {
        List<Speler> spelers = new ArrayList<>();
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement(GAME_GETPLAYERS);
            rs = query.executeQuery();
            while (rs.next()) {
                int spelerId = rs.getInt("spelerid");
                String naam = rs.getString("naam");
                Boolean geslacht = rs.getBoolean("geslacht");
                int level = rs.getInt("level");
                List<Integer> kaarten = geefKaarten(spelerId, "k");
                List<Integer> items = geefKaarten(spelerId, "i");
                Speler speler = new Speler(naam, geslacht, level, kaarten, items);
                spelers.add(spelerId, speler);
            }
            rs.close();
            query.close();
            conn.close();
        } catch (Exception ex) {
            throw new SpelDatabaseException(ex.getMessage());
        }
        return spelers;
    }

    private List<Integer> geefKaarten(int spelerId, String type) {
        List<Integer> kaarten = new ArrayList<>();
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement(PLAYER_GETCARDS);
            query.setInt(1, spelerId);
            rs = query.executeQuery();
            while (rs.next()) {
                if (type.equals("i")) {
                    if (rs.getBoolean("plaatsKaart")) {
                        kaarten.add(rs.getInt("kaartId"));
                    }
                } else {
                    if (!rs.getBoolean("plaatsKaart")) {
                        kaarten.add(rs.getInt("kaartId"));
                    }
                }
            }
            rs.close();
            query.close();
            conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }
        return kaarten;
    }

    private List<Integer> geefVolgorde(String type, int spelId) {
        List<Integer> volgorde = new ArrayList<>();
        voegToe();
        int nr;
        try {
            PreparedStatement query = conn.prepareStatement(GAME_CARDSEQ);
            query.setInt(1, spelId);
            rs = query.executeQuery();
            while (rs.next()) {
                if (type.equals("t")) {
                    nr = rs.getInt("volgnumerT");
                } else {
                    nr = rs.getInt("volgnummerD");
                }
                int kaartId = rs.getInt("kaartid");
                volgorde.add(nr, kaartId);
            }
            rs.close();
            query.close();
            conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }
        return volgorde;
    }


    public void addSpel(String naam, int i, boolean klein) {
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement(INSERT_GAME);
            query.setInt(1, 0);
            query.setString(2, naam);
            query.setBoolean(3, klein);
            query.executeUpdate();
            query.close();
            conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }

    }

    public List<String> getOverzicht() {
        List<String> overzicht = new ArrayList<>();
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement(ALL_GAMES);
            rs = query.executeQuery();
            while (rs.next()) {
                int spelId = rs.getInt("spelid");
                String naam = rs.getString("naam");
                List<Speler> spelers = voegSpelersToe(spelId);
                overzicht.add(String.format("%d: %s --> %d", spelId, naam, spelers.size()));
            }
            rs.close();
            query.close();
            conn.close();
        } catch (Exception ex) {
            throw new SpelDatabaseException(ex.getMessage());
        }
        return overzicht;
    }

    public void remove(int index) {
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement(DELETE_GAME);
            query.setInt(1, index);
            query.executeUpdate();
            query.close();
            query = conn.prepareStatement(DELETE_PLAYERS);
            query.setInt(1, index);
            query.executeUpdate();
            query.close();
            query = conn.prepareStatement(DELETE_SPELERKAART);
            query.executeUpdate();
            query.close();
            query = conn.prepareStatement(DELETE_SPELKAART);
            query.setInt(1, index);
            query.executeUpdate();
            query.close();
            conn.close();
        } catch (Exception ex) {
            throw new SpelDatabaseException(ex.getMessage());
        }
    }

    public int getSpelId(String naam) {
        int id;
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement(GAME_GETID);
            query.setString(1, naam);
            query.executeQuery();
            rs = query.executeQuery();
            rs.next();
            id = rs.getInt("spelid");
            query.close();
            conn.close();
        } catch (Exception ex) {
            throw new SpelDatabaseException(ex.getMessage());
        }
        return id;
    }

    public void kaartSpelerOpslaan(int spelerId, Integer id, boolean items) {
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement(PLAYER_SAVECARD);
            query.setInt(1, spelerId);
            query.setInt(2, id);
            query.setBoolean(3, items);
            query.executeUpdate();
            query.close();
            conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }
    }

    public void spelerOpslaan(int spelerId, String naam, int level, String geslacht, int spelId) {
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement(PLAYER_SAVE);
            query.setInt(1, spelerId);
            query.setString(2, naam);
            query.setInt(3, level);
            query.setBoolean(4, (geslacht.equalsIgnoreCase(LanguageResource.getString("man"))));
            query.setInt(5, spelId);
            query.executeUpdate();
            query.close();
            conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }
    }

    public void kaartSpelOpslaan(int spelId, Integer kaart, Object volgnummerD, Object volgnummerT) {
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement(GAME_CARDSAVE);
            query.setInt(1, spelId);
            query.setInt(2, kaart);
            query.setObject(3, volgnummerD);
            query.setObject(4, volgnummerT);
            query.executeUpdate();
            query.close();
            conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }
    }
}
