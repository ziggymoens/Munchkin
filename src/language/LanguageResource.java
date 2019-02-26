/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

import java.util.*;

/**
 *
 * @author ziggy
 */
public final class LanguageResource {

    private static Locale locale;
    private static ResourceBundle bundle;

    /**
     * Contructor voor LanguageResource zonder parameters, Engels wordt
     * ingesteld als taal
     */
    public LanguageResource() {
        setLocale(new Locale("en"));
        setBundle();
    }

    /**
     * Constructor voor LanguageResource adhv meegegeven taal
     *
     * @param locale gewenste taal
     */
    public LanguageResource(Locale locale) {
        setLocale(locale);
    }

    /**
     * Locale van de LanguageResource aanpassen
     *
     * @param locale nieuwe gewenste taal
     */
    public static void setLocale(Locale locale) {
        if (locale.toString().equals("en") || locale.toString().equals("fr") || locale.toString().equals("nl")) {
            LanguageResource.locale = locale;
        } else {
            LanguageResource.locale = new Locale("en");
        }
        setBundle();
    }

    /**
     * Bundle in LanguageResource initialiseren met de gegeven taal
     */
    public static final void setBundle() {
        LanguageResource.bundle = ResourceBundle.getBundle("language/i18n", getLocale());
    }

    /**
     * Huidige taal van de LanguageResource opvragen
     *
     * @return Locale van de huidige taal
     */
    public static Locale getLocale() {
        return LanguageResource.locale;
    }

    /**
     * De huidige bundle opvragen
     *
     * @return Bundle met huidige taal
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * Woord opvragen in een bepaalde taal dat niet de standaard taal is
     *
     * @param string Key van het opgevraagde woord
     * @param locale Gewenste taal
     * @return String met het woord in de gekozen taal
     */
    public static String getStringLanguage(String string, Locale locale) {
        return ResourceBundle.getBundle("language/i18n", locale).getString(string);
    }

    /**
     * Woord opvragen in de huidige taal
     *
     * @param string Key van het opgevraagde woord
     * @return String met het woord in de huidige taal
     */
    public static String getString(String string) {
        return bundle.getString(string);
    }
}
