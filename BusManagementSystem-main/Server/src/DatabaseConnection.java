import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Klasa DatabaseConnection odpowiedzialna za nawiązywanie połączenia z bazą danych.
 */
public class DatabaseConnection {

    /**
     * Metoda connectDb() nawiązuje połączenie z bazą danych MySQL.
     *
     * @return Obiekt typu Connection reprezentujący aktywne połączenie z bazą danych.
     */
    public static Connection connectDb(){
        try{
            // Rejestrowanie sterownika JDBC dla MySQL
            Class.forName("com.mysql.jdbc.Driver");
            // Tworzenie połączenia z bazą danych
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/busdata", "root", "");
            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }
}
