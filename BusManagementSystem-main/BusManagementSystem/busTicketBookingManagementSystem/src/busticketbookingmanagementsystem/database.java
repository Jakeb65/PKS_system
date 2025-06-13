package busticketbookingmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Klasa odpowiadająca za obsługę połączenia z bazą danych MySQL.
 */
public class database {

    /**
     * Metoda służąca do nawiązania połączenia z bazą danych MySQL.
     *
     * @return połączenie do bazy danych
     */
    public static Connection connectDb(){
        
        try{

            // Rejestrowanie sterownika JDBC dla MySQL
            Class.forName("com.mysql.jdbc.Driver");

            // Tworzenie połączenia do bazy danych z odpowiednimi parametrami
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/busdata", "root", ""); 
            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }
}
