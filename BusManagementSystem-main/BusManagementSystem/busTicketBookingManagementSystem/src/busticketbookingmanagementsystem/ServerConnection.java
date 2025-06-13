package busticketbookingmanagementsystem;

import java.io.*;
import java.net.Socket;

/**
 * Klasa reprezentująca połączenie z serwerem.
 * Zapewnia komunikację między klientem a serwerem poprzez gniazdo sieciowe.
 * Implementuje wzorzec Singleton, aby zapewnić jedną instancję połączenia.
 */
public class ServerConnection {
    private static ServerConnection serverConnectionInstance = null;
    public Socket socket;
    public BufferedReader bufferedReader;
    public BufferedReader inputStream;
    public PrintWriter outputStream;

    /**
     * Prywatny konstruktor tworzący połączenie z serwerem.
     * Inicjalizuje gniazdo sieciowe oraz strumienie wejścia/wyjścia.
     *
     * @throws IOException jeśli wystąpi błąd wejścia/wyjścia podczas tworzenia połączenia
     */
    private ServerConnection() throws IOException {
        this.socket = new Socket("localhost", 4999);
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.outputStream = new PrintWriter(socket.getOutputStream());
    }

    /**
     * Metoda statyczna zwracająca instancję połączenia z serwerem.
     * Jeśli instancja nie istnieje, tworzy nową.
     *
     * @return instancja połączenia z serwerem
     * @throws IOException jeśli wystąpi błąd wejścia/wyjścia podczas tworzenia połączenia
     */
    public static synchronized ServerConnection getInstance() throws IOException {
        if (serverConnectionInstance == null)
            serverConnectionInstance = new ServerConnection();

        return serverConnectionInstance;
    }
}