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
    private static final Map<String, String> KLEUREN;
    private static final Map<String, String> ACHTERGROND;
    private static final Map<String, String> DECORATIONS;

    static {
        KLEUREN = new HashMap<>();
        KLEUREN.put("black", "[30m");
        KLEUREN.put("red", "[31m");
        KLEUREN.put("green", "[32m");
        KLEUREN.put("yellow", "[33m");
        KLEUREN.put("blue", "[34m");
        KLEUREN.put("purple", "[35m");
        KLEUREN.put("cyan", "[36m");
        KLEUREN.put("white", "[37m");
        ACHTERGROND = new HashMap<>();
        ACHTERGROND.put("black", "[40m");
        ACHTERGROND.put("red", "[41m");
        ACHTERGROND.put("green", "[42m");
        ACHTERGROND.put("yellow", "[43m");
        ACHTERGROND.put("blue", "[44m");
        ACHTERGROND.put("purple", "[45m");
        ACHTERGROND.put("cyan", "[46m");
        ACHTERGROND.put("white", "[47m");
        DECORATIONS = new HashMap<>();
        DECORATIONS.put("bold", "[1m");
        DECORATIONS.put("underline", "[4m");
        DECORATIONS.put("reversed", "[7m");
    }

    public static String kleur(String kleur) {
        if (!KLEUREN.containsKey(kleur)) {
            throw new IllegalArgumentException("exception.kleuren.key");
        }
        return String.format("\u001B%s", KLEUREN.get(kleur.toLowerCase()));
    }

    public static String achtergrond(String kleur) {
        if (!ACHTERGROND.containsKey(kleur)) {
            throw new IllegalArgumentException("exception.achtergrond.key");
        }
        return String.format("\u001B%s", ACHTERGROND.get(kleur.toLowerCase()));
    }

    public static String decoration(String type) {
        if (!DECORATIONS.containsKey(type)) {
            throw new IllegalArgumentException("exception.decorations.key");
        }
        return String.format("\u001B%s", DECORATIONS.get(type.toLowerCase()));
    }

    public static String reset() {
        return "\u001B[0m";
    }
}
