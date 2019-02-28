/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.*;
import language.LanguageResource;

/**
 *
 * @author ziggy
 */
public class Test {

    //i18n testen
    private LanguageResource bundle = new LanguageResource();

    public static void main(String[] args) {
        Test test = new Test();
        Locale locale = new Locale("fr");

        test.makeBundle(locale);

        System.out.println(test.stringFromBundle("ask.name"));
        test.changeLanguage(new Locale("nl"));
        System.out.println(test.stringFromBundle("ask.name"));

        System.out.println(test.stringFromBundle("ask.name", new Locale("en")));
    }

    private void makeBundle(Locale locale) {
        bundle = new LanguageResource();
    }

    private void changeLanguage(Locale locale) {
        LanguageResource.setLocale(locale);
    }

    private String stringFromBundle(String string) {
        return LanguageResource.getString(string);
    }

    private String stringFromBundle(String string, Locale locale) {
        return LanguageResource.getStringLanguage(string, locale);
    }

    private void test() {
        System.out.println("Netbeans test");
    }
}
