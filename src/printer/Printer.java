package printer;

import language.LanguageResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Printer {
    private static Scanner SCAN = new Scanner(System.in);

    private static Exception ex;

    private static Boolean developerMode = false;

    /**
     * Methode die gevangen exception gooit naar terminal en deze controleert op exceptions.
     *
     * @param naam Naam van de gevangen exception
     * @param e    De gevangen exception
     */
    public static String exceptionCatch(String naam, Exception e) {
        setException(e);
        String ret = "";
        try {
            ret = String.format(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%s%n%s%n", developerMode ? String.format("%s -> (%s: %s) -> %s: %d, %s: ", e.getStackTrace()[0].getClassName(), LanguageResource.getString("method"), e.getStackTrace()[0].getMethodName(), LanguageResource.getString("line"), e.getStackTrace()[0].getLineNumber(), naam) : "", e.getMessage() == null ? LanguageResource.getString("nomessage") : LanguageResource.getString(e.getMessage()), developerMode && e.getCause() != null ? String.format("%s %s", LanguageResource.getString("cause"), e.getCause()) : "") + ColorsOutput.reset();
            getInput();
        } catch (Exception ex) {
            ret = String.format("\u001B[31m" + "IllegalArgumentException: %s%n%n", LanguageResource.getString(ex.getMessage()) + "\u001B[0m");
        }
        return ret;
    }

    private static void setException(Exception e) {
        ex = e;
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
        setException(e);
        String ret = "";
        if (lang) {
            exceptionCatch(naam, e);
        } else {
            try {
                ret = String.format(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%s%n%n", developerMode ? String.format("%s -> (%s: %s) -> %s: %d, %s: ", e.getStackTrace()[0].getClassName(), LanguageResource.getString("method"), e.getStackTrace()[0].getMethodName(), LanguageResource.getString("line"), e.getStackTrace()[0].getLineNumber(), naam) : "", e.getMessage() == null ? LanguageResource.getString("nomessage") : e.getMessage(), developerMode && e.getCause() != null ? String.format("%s %s", LanguageResource.getString("cause"), e.getCause()) : "") + ColorsOutput.reset();
                getInput();
            } catch (Exception ex) {
                ret = String.format("\u001B[31m" + "IllegalArgumentException: %s%n%n", ex.getMessage() + "\u001B[0m");
            }
        }
        return ret;
    }

    public static void setDeveloperMode(Boolean developerMode) {
        Printer.developerMode = developerMode;
    }


    private static String str = "";
    private Runnable task;
    private TimerTask timerTask;

//    private void schedule (Runnable runnable, long delay){
//        task = runnable;
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                task.run();
//            }
//        };
//        this.schedule(timerTask, delay);
//    }
//
//    private void reschedule(long delay){
//        timerTask.cancel();
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                task.run();
//            }
//        };
//        schedule(timerTask, delay);
//    }

    static task = new TimerTask() {
        public void run() {
            if (str.equals("[a-zA-Z0-9].*")) {
                //System.out.println( "you input nothing. exit..." );
                ex.printStackTrace();
                //System.exit( 0 );
            }
        }
    };

    public static void getInput() throws Exception {

        Timer timer = new Timer();
        timer.schedule(task, 10 * 100);

        //System.out.println( "Input a string within 10 seconds: " );
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        str = in.readLine();
        timer.cancel();
        //System.out.println( "you have entered: "+ str );
    }
}
