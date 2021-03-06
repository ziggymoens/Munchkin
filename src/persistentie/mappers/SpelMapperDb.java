package persistentie.mappers;

import domein.Spel;
import domein.Speler;
import exceptions.database.SpelDatabaseException;
import language.LanguageResource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class SpelMapperDb {
    private Connection conn;
    private ResultSet rs;
    private ResultSet rs1;
    private ResultSet rs2;
    private ResultSet rs3;
    private ResultSet rs7;
    private ResultSet rs11;
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
    private PreparedStatement query11;
    private List<Spel> spellen = new ArrayList<>();
    private static final String INSERT_GAME = "INSERT INTO ID222177_g35.Spel (spelid, naam, kleingroot, spelerAanBeurt) VALUES (?, ?, ?, ?)";
    private static final String DELETE_GAME = "DELETE FROM ID222177_g35.Spel WHERE spelid = ?";
    private static final String ALL_GAMES = "SELECT * FROM ID222177_g35.Spel";
    private static final String GAME_CARDSEQ = "SELECT * FROM ID222177_g35.SpelKaart WHERE spelidSpelKaart = ?";
    private static final String GAME_GETID = "SELECT spelid FROM ID222177_g35.Spel WHERE naam = ?";
    private static final String PLAYER_GETCARDS = "SELECT * FROM ID222177_g35.SpelerKaart WHERE spelerid = ? AND spelidSpelerKaart = ?";
    private static final String GAME_GETPLAYERS = "SELECT * FROM ID222177_g35.Speler WHERE spelidSpel = ?";
    private static final String GAME_LOAD = "SELECT * FROM ID222177_g35.Spel WHERE spelid = ?";
    private static final String PLAYER_SAVECARD = "INSERT INTO ID222177_g35.SpelerKaart (spelerid, kaartidSpelerKaart, plaatsKaart, spelidSpelerKaart) VALUES (?, ?, ?, ?)";
    private static final String PLAYER_SAVE = "INSERT INTO ID222177_g35.Speler (spelerid, naam, level, geslacht, spelidSpel) VALUES (?,?,?,?,?)";
    private static final String GAME_CARDSAVE = "INSERT INTO ID222177_g35.SpelKaart (kaartidSpelKaart, volgnummerD, volgnumerT, spelidSpelKaart) values (?,?,?,?)";
    private static final String DELETE_PLAYERS = "DELETE FROM ID222177_g35.Speler WHERE spelidSpel = ?";
    private static final String DELETE_SPELERKAART = "DELETE ID222177_g35.SpelerKaart FROM ID222177_g35.SpelerKaart INNER JOIN ID222177_g35.Speler ON ID222177_g35.SpelerKaart.spelerid = ID222177_g35.Speler.spelerid where ID222177_g35.Speler.spelidSpel = ?";
    private static final String DELETE_SPELKAART = "DELETE FROM ID222177_g35.SpelKaart WHERE spelidSpelKaart = ?";
    private static final String RESET_INCREMENT_SPEL = "ALTER TABLE ID222177_g35.`Spel` AUTO_INCREMENT = 1;";

    /**
     * voeg connectie toe
     */
    private void voegToe() {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://ID222177_g35.db.webhosting.be?serverTimezone=UTC&useLegacyDatetimeCode=false&user=ID222177_g35&password=Te6VibUp");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SpelDatabaseException(ex.getMessage());
        }
    }

    /**
     * methode die spel oplaad
     *
     * @param spelId spelid van gekozen spel
     * @return het gekozen spel
     */
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
        } finally {
            try {
                rs.close();
                query.close();
                conn.close();
            } catch (SQLException ignored) {
            }
        }
        return spel;
    }

    /**
     * methode reset het increment van de spellen in de db
     */
    public void resetIncrement() {
        voegToe();
        try {
            PreparedStatement query = conn.prepareStatement(RESET_INCREMENT_SPEL);
            query.executeUpdate();
        } catch (Exception ex) {
            throw new SpelDatabaseException(ex.getMessage());
        }
    }

    /**
     * voegt spelers toe aan het spel uit db
     *
     * @param spelId het opgeladen spel
     * @return list met spelers
     */
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
                List<Integer> kaarten = geefKaarten(spelerId, 'k', spelId);
                List<Integer> items = geefKaarten(spelerId, 'i', spelId);
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

    /**
     * geeft de kaarten van het gekozen spel per speler
     *
     * @param spelerId de speler van het spel
     * @param type     i = items, k = kaarten
     * @param spelId   id van het spel
     * @return list met kaarten
     */
    private List<Integer> geefKaarten(int spelerId, char type, int spelId) {
        List<Integer> kaarten = new ArrayList<>();
        voegToe();
        try {
            query2 = conn.prepareStatement(PLAYER_GETCARDS);
            query2.setInt(1, spelerId);
            query2.setInt(2, spelId);
            rs2 = query2.executeQuery();
            while (rs2.next()) {
                if (type == 'i') {
                    if (rs2.getBoolean("plaatsKaart")) {
                        kaarten.add(rs2.getInt("kaartIdSpelerKaart"));
                    }
                } else {
                    if (!rs2.getBoolean("plaatsKaart")) {
                        kaarten.add(rs2.getInt("kaartIdSpelerKaart"));
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

    /**
     * geeft de volgorde van de kaarten van het spel
     *
     * @param type   t = treasure, d = dungeon
     * @param spelId het huidige spel
     * @return list met ids
     */
    private List<Integer> geefVolgorde(String type, int spelId) {
        List<Integer> volgorde;
        voegToe();
        try {
            volgorde = new ArrayList<Integer>(Collections.nCopies(200, 0));
            query3 = conn.prepareStatement(GAME_CARDSEQ);
            query3.setInt(1, spelId);
            rs3 = query3.executeQuery();
            while (rs3.next()) {
                if (type.equals("t")) {
                    if (rs3.getInt("volgnumerT") != -1) {
                        volgorde.add(rs3.getInt("volgnumerT"), rs3.getInt("kaartidSpelKaart"));
                    }
                } else {
                    if (rs3.getInt("volgnummerD") != -1) {
                        volgorde.add(rs3.getInt("volgnummerD"), rs3.getInt("kaartidSpelKaart"));
                    }
                }
            }
            int teller = 0;
            for (Integer i : volgorde) {
                if (i == 0) {
                    teller++;
                }
            }
            List<Integer> test = new ArrayList<Integer>(Collections.nCopies(teller, 0));
            volgorde.remove(test);
            rs3.close();
            query3.close();
            //conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SpelDatabaseException(e.getMessage());
        }
        return volgorde;
    }

    /**
     * voeg spel toe aan db
     *
     * @param naam          naam van spel
     * @param i             id van spel
     * @param klein         true is kleine db
     * @param laatsteSpeler de laatste speler aan beurt
     */
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

    /**
     * methode geeft overzicht van spelen in de db
     *
     * @return list met strings met info
     */
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
                String add = String.format("ID = %d: %s --> %d", spelId, naam, spelerAanbeurt);
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

    /**
     * methode verwijdert een spel uit de db
     *
     * @param index id van gekozen spel
     */
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

    /**
     * methode geeft de id van het spel adhv de naam
     */
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

    /**
     * methode slaat kaarten op per speler
     *
     * @param spelerId id van de speler
     * @param ids      ids van de kaarten
     * @param items    true = items, false = kaarten
     * @param spelid   id van het huidige spel
     */
    void kaartSpelerOpslaan(int spelerId, List<Integer> ids, boolean items, int spelid) {
        voegToe();
        try {
            for (Integer id : ids) {
                query8 = conn.prepareStatement(PLAYER_SAVECARD);
                query8.setInt(1, spelerId);
                query8.setInt(2, id);
                query8.setBoolean(3, items);
                query8.setInt(4, spelid);
                query8.executeUpdate();
                query8.close();
            }
            //conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }
    }

    /**
     * speler opslmaan in db
     *
     * @param spelerId id van de speler
     * @param naam     naal van de speler
     * @param level    level van de speler
     * @param geslacht geslacht van de speler
     * @param spelId   id van huidige spel
     */
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

    /**
     * kaarten van spel opslaan
     *
     * @param spelId      huidige spel id
     * @param kaart       lijst met ids van kaarten
     * @param volgnummerD lijst met volgnummers kerker
     * @param volgnummerT lijst met volgnummers schat
     */
    void kaartSpelOpslaan(int spelId, List<Integer> kaart, List<Integer> volgnummerD, List<Integer> volgnummerT) {
        voegToe();
        try {
            for (int id : kaart) {
                query10 = conn.prepareStatement(GAME_CARDSAVE);
                query10.setInt(1, id);
                query10.setObject(2, volgnummerD.indexOf(id));
                query10.setObject(3, volgnummerT.indexOf(id));
                query10.setInt(4, spelId);
                query10.executeUpdate();
                query10.close();
            }
            //conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }
    }

    /**
     * methode geeft alle beschikbare spelids
     *
     * @return list met ids
     */
    List<Integer> geefSpelIds() {
        List<Integer> ids = new ArrayList<>();
        voegToe();
        try {
            query11 = conn.prepareStatement(ALL_GAMES);
            rs11 = query11.executeQuery();
            while (rs11.next()) {
                int spelId = rs11.getInt("spelid");
                ids.add(spelId);
            }
            rs11.close();
            query11.close();
            conn.close();
        } catch (Exception e) {
            throw new SpelDatabaseException(e.getMessage());
        }
        return ids;
    }
}
