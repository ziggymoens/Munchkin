package printer;

import language.LanguageResource;

public class Printer {
    private static Boolean developerMode = false;

    /**
     * Methode die gevangen exception gooit naar terminal en deze controleert op exceptions.
     *
     * @param naam Naam van de gevangen exception
     * @param e    De gevangen exception
     */
    public static String exceptionCatch(String naam, Exception e) {
        String ret = "";
        try {
            ret = String.format(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%s%n%s%n", developerMode ? String.format("%s -> (%s: %s) -> %s: %d, %s: ", e.getStackTrace()[0].getClassName(), LanguageResource.getString("method"), e.getStackTrace()[0].getMethodName(), LanguageResource.getString("line"), e.getStackTrace()[0].getLineNumber(), naam) : "", e.getMessage() == null ? LanguageResource.getString("nomessage") : LanguageResource.getString(e.getMessage()), developerMode && e.getCause() != null ? String.format("%s %s", LanguageResource.getString("cause"), e.getCause()) : "") + ColorsOutput.reset();
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
                ret = String.format(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%s%n%n", developerMode ? String.format("%s -> (%s: %s) -> %s: %d, %s: ", e.getStackTrace()[0].getClassName(), LanguageResource.getString("method"), e.getStackTrace()[0].getMethodName(), LanguageResource.getString("line"), e.getStackTrace()[0].getLineNumber(), naam) : "", e.getMessage() == null ? LanguageResource.getString("nomessage") : e.getMessage(), developerMode && e.getCause() != null ? String.format("%s %s", LanguageResource.getString("cause"), e.getCause()) : "") + ColorsOutput.reset();
            } catch (Exception ex) {
                ret = String.format("\u001B[31m" + "IllegalArgumentException: %s%n%n", ex.getMessage() + "\u001B[0m");
            }
        }
        return ret;
    }

    public static void setDeveloperMode(Boolean developerMode) {
        Printer.developerMode = developerMode;
    }
}
