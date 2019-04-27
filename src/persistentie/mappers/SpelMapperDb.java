package persistentie.mappers;

import domein.Spel;
import domein.Speler;
import exceptions.database.SpelDatabaseException;
import language.LanguageResource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpelMapperDb {
    private Connection conn;
    private ResultSet rs;
    private ResultSet rs1;
    private ResultSet rs2;
    private ResultSet rs3;
    private ResultSet rs7;
    private PreparedStatement query;
    private PreparedStatement query1;
    private PreparedStatement query2;
    private PreparedStatement query3;
    private PreparedStatement query4;
    private PreparedStatement query6;
    private PreparedStatement query7;
    private PreparedStatement query8;
    private PreparedStatement query9;
    private PreparedStatement query10;
    private List<Spel> spellen = new ArrayList<>();
    private static final String INSERT_GAME = "INSERT INTO ID222177_g35.Spel (spelid, naam, kleingroot, spelerAanBeurt) VALUES (?, ?, ?, ?)";
    private static final String DELETE_GAME = "DELETE FROM ID222177_g35.Spel WHERE spelid = ?";
    private static final String ALL_GAMES = "SELECT * FROM ID222177_g35.Spel";
    private static final String GAME_CARDSEQ = "SELECT * FROM ID222177_g35.SpelKaart WHERE spelid = ?";
    private static final String GAME_GETID = "SELECT spelid FROM ID222177_g35.Spel WHERE naam = ?";
    private static final String PLAYER_GETCARDS = "SELECT * FROM ID222177_g35.SpelerKaart WHERE SpelerId = ?";
    private static final String GAME_GETPLAYERS = "SELECT * FROM ID222177_g35.Speler WHERE spelid = ?";
    private static final String GAME_LOAD = "SELECT * FROM ID222177_g35.Spel WHERE spelid = ?";
    private static final String PLAYER_SAVECARD = "INSERT INTO ID222177_g35.SpelerKaart (spelerid, kaartid, plaatsKaart) VALUES (?, ?, ?)";
    private static final String PLAYER_SAVE = "INSERT INTO ID222177_g35.Speler (spelerid, naam, level, geslacht, spelid) VALUES (?,?,?,?,?)";
    private static final String GAME_CARDSAVE = "INSERT INTO ID222177_g35.SpelKaart (spelid, kaartid, volgnummerD, volgnumerT) values (?,?,?,?)";
    private static final String DELETE_PLAYERS = "DELETE FROM ID222177_g35.Speler WHERE spelid = ?";
    private static final String DELETE_SPELERKAART = "DELETE ID222177_g35.SpelerKaart FROM ID222177_g35.SpelerKaart INNER JOIN ID222177_g35.Speler ON ID222177_g35.SpelerKaart.spelerid = ID222177_g35.Speler.spelerid where ID222177_g35.Speler.spelid = ?";
    private static final String DELETE_SPELKAART = "DELETE FROM ID222177_g35.SpelKaart WHERE spelid = ?";

    private void voegToe() {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://ID222177_g35.db.webhosting.be?serverTimezone=UTC&useLegacyDatetimeCode=false&user=ID222177_g35&password=Te6VibUp");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SpelDatabaseException(ex.getMessage());
        }
    }

    Spel laadSpel(int spelId) {
        spellen.clear();
        Spel spel = null;
        voegToe();
        try {
            query = conn.prepareStatement(GAME_LOAD);
            query.setInt(1, spelId);
            rs = query.executeQuery();
            while (rs.next()) {
                int Id = rs.getInt("spelid");
                String naam = rs.getString("naam");
                boolean klein = rs.getBoolean("kleingroot");
                int ls = rs.getInt("spelerAanBeurt");
                List<Speler> spelers = voegSpelersToe(spelId);
                List<Integer> volgnummerD = geefVolgorde("d", spelId);
                List<Integer> volgnummerT = geefVolgorde("t", spelId);
                spel = new Spel(naam, klein, spelers, ls, volgnummerD, volgnummerT);
            }
            //rs.close();
            //query.close();
            //conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SpelDatabaseException(ex.getMessage());
        }finally {
            try {
                rs.close();
                query.close();
                conn.close();
            } catch (SQLException ignored){
            }
        }
        return spel;
    }

    private List<Speler> voegSpelersToe(int spelId) {
        List<Speler> spelers = new ArrayList<>();
        voegToe();
        try {
            query1 = conn.prepareStatement(GAME_GETPLAYERS);
            query1.setInt(1, spelId);
            rs1 = query1.executeQuery();
            while (rs1.next()) {
                int spelerId = rs1.getInt("spelerid");
                String naam = rs1.getString("naam");
                Boolean geslacht = rs1.getBoolean("geslacht");
                int level = rs1.getInt("level");
                List<Integer> kaarten = geefKaarten(spelerId, "k");
                List<Integer> items = geefKaarten(spelerId, "i");
                Speler speler = new Speler(naam, geslacht, level, kaarten, items);
                spelers.add(spelerId, speler);
            }

        } catch (Exception ex) {
            throw new SpelDatabaseException(ex.getMessage());
        } finally {
            try {
                rs1.close();
                query1.close();
                //conn.close();
            } catch (SQLException ignored) {
            }
        }
        return spelers;
    }

    private List<Integer> geefKaarten(int spelerId, String type) {
        List<Integer> kaarten = new ArrayList<>();
        voegToe();
        try {
            query2 = conn.prepareStatement(PLAYER_GETCARDS);
            query2.setInt(1, spelerId);
            rs2 = query2.executeQuery();
            while (rs2.next()) {
                if (type.equals("i")) {
                    if (rs2.getBoolean("plaatsKaart")) {
                        kaarten.add(rs2.getInt("kaartId"));
                    }
                } else {
                    if (!rs2.getBoolean("plaatsKaart")) {
                        kaarten.add(rs2.getInt("kaartId"));
                    }
                }
            }
            rs2.close();
            query2.close();
            //conn.close();
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
            query3 = conn.prepareStatement(GAME_CARDSEQ);
            query3.setInt(1, spelId);
            rs3 = query3.executeQuery();
            while (rs3.next()) {
                if (type.equals("t")) {
                    nr = rs3.getInt("volgnumerT");
                } else {
                    nr = rs3.getInt("volgnummerD");
                }
                int kaartId = rs.getInt("kaartid");
                volgorde.add(nr, kaartId);
            }
            rs3.close();
            query3.close();
            //conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }
        return volgorde;
    }


    void addSpel(String naam, int i, boolean klein, int laatsteSpeler) {
        voegToe();
        try {
            query4 = conn.prepareStatement(INSERT_GAME);
            query4.setInt(1, 0);
            query4.setString(2, naam);
            query4.setBoolean(3, klein);
            query4.setInt(4, laatsteSpeler);
            query4.executeUpdate();
            query4.close();
            //conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }

    }

    List<String> getOverzicht() {
        List<String> overzicht = new ArrayList<>();
        voegToe();
        try {
            PreparedStatement query5 = conn.prepareStatement(ALL_GAMES);
            ResultSet rs5 = query5.executeQuery();
            while (rs5.next()) {
                int spelId = rs5.getInt("spelid");
                String naam = rs5.getString("naam");
                int spelerAanbeurt = rs5.getInt("spelerAanBeurt");
                //System.out.println(spelerAanbeurt);
                String add = String.format("%d: %s --> %d", spelId, naam, spelerAanbeurt);
                //System.out.println(add);
                overzicht.add(add);
            }
            rs5.close();
            query5.close();
            //conn.close();
        } catch (Exception ex) {
            throw new SpelDatabaseException(ex.getMessage());
        }
        return overzicht;
    }

    void remove(int index) {
        voegToe();
        try {
            query6 = conn.prepareStatement(DELETE_SPELERKAART);
            query6.setInt(1, index);
            query6.executeUpdate();
            query6.close();
            query6 = conn.prepareStatement(DELETE_PLAYERS);
            query6.setInt(1, index);
            query6.executeUpdate();
            query6.close();
            query6 = conn.prepareStatement(DELETE_SPELKAART);
            query6.setInt(1, index);
            query6.executeUpdate();
            query6.close();
            query6 = conn.prepareStatement(DELETE_GAME);
            query6.setInt(1, index);
            query6.executeUpdate();
            query6.close();
            //conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SpelDatabaseException(ex.getMessage());
        }
    }

    int getSpelId(String naam) {
        int id;
        voegToe();
        try {
            query7 = conn.prepareStatement(GAME_GETID);
            query7.setString(1, naam);
            query7.executeQuery();
            rs7 = query7.executeQuery();
            rs7.next();
            id = rs7.getInt("spelid");
            query7.close();
            //conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SpelDatabaseException(ex.getMessage());
        }
        return id;
    }

    //LUSSEN
    void kaartSpelerOpslaan(int spelerId, List<Integer> ids, boolean items) {
        voegToe();
        try {
            for (Integer id : ids) {
                query8 = conn.prepareStatement(PLAYER_SAVECARD);
                query8.setInt(1, spelerId);
                query8.setInt(2, id);
                query8.setBoolean(3, items);
                query8.executeUpdate();
                query8.close();
            }
            //conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }
    }

    void spelerOpslaan(int spelerId, String naam, int level, String geslacht, int spelId) {
        voegToe();
        try {
            query9 = conn.prepareStatement(PLAYER_SAVE);
            query9.setInt(1, spelerId);
            query9.setString(2, naam);
            query9.setInt(3, level);
            query9.setBoolean(4, (geslacht.equalsIgnoreCase(LanguageResource.getString("man"))));
            query9.setInt(5, spelId);
            query9.executeUpdate();
            query9.close();
            //conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }
    }

    void kaartSpelOpslaan(int spelId, List<Integer> kaart, List<Integer> volgnummerD, List<Integer> volgnummerT) {
        voegToe();
        try {
            for (int id : kaart) {
                query10 = conn.prepareStatement(GAME_CARDSAVE);
                query10.setInt(1, spelId);
                query10.setInt(2, id);
                query10.setObject(3, volgnummerD.indexOf(id));
                query10.setObject(4, volgnummerT.indexOf(id));
                query10.executeUpdate();
                query10.close();
            }
            //conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }
    }
}
