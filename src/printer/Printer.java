package printer;

import language.LanguageResource;

public class Printer {

    /**
     * Methode die gevangen exception gooit naar terminal en deze controleert op exceptions.
     *
     * @param naam Naam van de gevangen exception
     * @param e    De gevangen exception
     */
    public static void exceptionCatch(String naam, Exception e) {
        try {
            System.out.printf(ColorsOutput.kleur("red") + "%s: %s%n%n", naam, LanguageResource.getString(e.getMessage()) + ColorsOutput.reset());
        } catch (Exception ex) {
            System.out.printf("\u001B[31m" + "IllegalArgumentException: %s%n%n", LanguageResource.getString(ex.getMessage()) + "\u001B[0m");
        }
    }

    /**
     * Print de meegegeven string in de groene kleur naar de terminal
     *
     * @param key String die omgezet wordt naar groen
     */
    public static void printGreen(String key) {
        System.out.printf(ColorsOutput.kleur("green") + "%n%s%n", LanguageResource.getString(String.format("%s", key)) + ColorsOutput.reset());
    }
}
