import java.io.*;

/**
 * Główna klasa programu, rozpoczyna działanie serwera.
 */
public class Main {

    public static void main(String[] args){
        Server server = new Server();
        try {
            server.serverStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}