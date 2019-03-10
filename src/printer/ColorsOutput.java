/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ziggy
 */
public class ColorsOutput {
    private static final Map<String, String> kleuren;
    private static final Map<String, String> achtergrond;
    private static final Map<String, String> decorations;

    static {
        kleuren = new HashMap<>();
        kleuren.put("black", "[30m");
        kleuren.put("red", "[31m");
        kleuren.put("green", "[32m");
        kleuren.put("yellow", "[33m");
        kleuren.put("blue", "[34m");
        kleuren.put("purple", "[35m");
        kleuren.put("cyan", "[36m");
        kleuren.put("white", "[37m");
        achtergrond = new HashMap<>();
        achtergrond.put("black", "[40m");
        achtergrond.put("red", "[41m");
        achtergrond.put("green", "[42m");
        achtergrond.put("yellow", "[43m");
        achtergrond.put("blue", "[44m");
        achtergrond.put("purple", "[45m");
        achtergrond.put("cyan", "[46m");
        achtergrond.put("white", "[47m");
        decorations = new HashMap<>();
        decorations.put("bold", "[1m");
        decorations.put("underline", "[4m");
        decorations.put("reversed", "[7m");
    }

    public static String kleur(String kleur) {
        if (!kleuren.containsKey(kleur)) {
            throw new IllegalArgumentException("exception.kleuren.key");
        }
        return String.format("\u001B%s", kleuren.get(kleur.toLowerCase()));
    }


    public static String kleur(String kleur, boolean bright) {
        if (!kleuren.containsKey(kleur)) {
            throw new IllegalArgumentException("exception.kleuren.key");
        }
        return String.format("\u001B%s%s", kleuren.get(kleur.toLowerCase()), bright ? ";1m" : "");
    }

    public static String achtergrond(String kleur) {
        if (!kleuren.containsKey(kleur)) {
            throw new IllegalArgumentException("exception.achtergrond.key");
        }
        return String.format("\u001B%s", achtergrond.get(kleur.toLowerCase()));
    }

    public static String achtergrond(String kleur, boolean bright) {
        if (!kleuren.containsKey(kleur)) {
            throw new IllegalArgumentException("exception.achtergrond.key");
        }
        return String.format("\u001B%s%s", achtergrond.get(kleur.toLowerCase()), bright ? ";1m" : "");
    }

    public static String decoration(String kleur) {
        if (!kleuren.containsKey(kleur)) {
            throw new IllegalArgumentException("exception.decorations.key");
        }
        return String.format("\u001B%s", decorations.get(kleur.toLowerCase()));
    }

    public static String reset() {
        return "\u001B[0m";
    }
}
