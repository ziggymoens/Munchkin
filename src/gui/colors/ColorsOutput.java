/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.colors;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ziggy
 */
public class ColorsOutput {
    public static Map<String, String> kleuren;
    static {
        kleuren = new HashMap<>();
        kleuren.put("reset", "\u001B[0m");
        kleuren.put("red", "\u001B[31m");
        kleuren.put("green", "\u001B[32m");
    }
    
    public static String kleur(String kleur){
        if (!kleuren.containsKey(kleur)) {
            throw new IllegalArgumentException("exception.kleuren.key");
        }
        return kleuren.get(kleur.toLowerCase());
    }
    
    public static String reset(){
        return kleuren.get("reset");
    }
}
