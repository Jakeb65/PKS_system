import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

/**
 * Klasa odpowiedzialna za uruchomienie serwera oraz obsługę przychodzących klientów.
 */
public class Server {
    /**
     * Metoda rozpoczynająca działanie serwera.
     *
     * @throws IOException w przypadku błędu wejścia/wyjścia
     */
    public void serverStart() throws IOException {
        Socket s = null;
        ServerSocket ss2 = null;
        Connection connection = DatabaseConnection.connectDb();
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));

        // Tworzenie gniazda serwera
        try {
            ss2 = new ServerSocket(4999);
            System.out.println("Server started");
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Server error");
        }

        // Główna pętla serwera, oczekuje na połączenia klientów i tworzy dla nich osobne wątki
        while(true){
            try {
                s = ss2.accept();
                System.out.println("Client connected");
                ServerThread st = new ServerThread(s, connection);
                st.start();
            } catch(NullPointerException e) {
                e.printStackTrace();
                System.out.println("Connection Error");
            }
        }
    }
}
