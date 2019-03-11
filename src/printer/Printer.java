package printer;

import language.LanguageResource;

public class Printer {

    /**
     * Methode die gevangen exception gooit naar terminal en deze controleert op exceptions.
     *
     * @param naam Naam van de gevangen exception
     * @param e    De gevangen exception
     */
    public static String exceptionCatch(String naam, Exception e) {
        String ret = "";
        try {
            ret = String.format(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s: %s%n%n", naam, LanguageResource.getString(e.getMessage()) + ColorsOutput.reset());
        } catch (Exception ex) {
            ret = String.format("\u001B[31m" + "IllegalArgumentException: %s%n%n", LanguageResource.getString(ex.getMessage()) + "\u001B[0m");
        }
        return ret;
    }

    /**
     * Print de meegegeven string in de groene kleur naar de terminal
     *
     * @param key String die omgezet wordt naar groen
     */
    public static String printGreen(String key) {
        return String.format(ColorsOutput.decoration("underline") + ColorsOutput.kleur("green") + "%n%s%n", LanguageResource.getString(String.format("%s", key)) + ColorsOutput.reset());
    }

    public static String exceptionCatch(String naam, Exception e, boolean lang) {
        String ret = "";
        if (lang) {
            exceptionCatch(naam, e);
        } else {
            try {
                ret = String.format(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s: %s%n%n", naam, e.getMessage() + ColorsOutput.reset());
            } catch (Exception ex) {
                ret = String.format("\u001B[31m" + "IllegalArgumentException: %s%n%n", LanguageResource.getString(ex.getMessage()) + "\u001B[0m");
            }
        }
        return ret;
    }
}
