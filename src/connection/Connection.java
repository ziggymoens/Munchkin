package connection;

public class Connection {
    private static boolean connected;

    /**
     * kijken of het spel verbonden is met internet
     *
     * @return true = connected
     */
    public static boolean isConnected() {
        return connected;
    }

    /**
     * Veranderen van de waarde van connected
     *
     * @param connected true = connected
     */
    public static void setConnected(boolean connected) {
        Connection.connected = connected;
    }
}
