import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Klasa Login obsługuje proces logowania użytkownika.
 * Umożliwia weryfikację danych logowania użytkownika w bazie danych.
 */
public class Login {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private PreparedStatement prepare;

    /**
     * Metoda umożliwia logowanie użytkownika.
     *
     * @param is Strumień wejściowy, z którego pobierane są dane logowania
     * @param os Strumień wyjściowy, do którego zostanie przekazany wynik logowania (1 - sukces, 0 - błąd)
     * @throws IOException jeśli wystąpi błąd wejścia/wyjścia
     * @throws SQLException jeśli wystąpi błąd podczas wykonania zapytania SQL
     */
    public void userLogin(BufferedReader is, PrintWriter os) throws IOException, SQLException {
        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";
        connection = DatabaseConnection.connectDb();

        String username = is.readLine();
        System.out.println("username: " + username);
        String password = is.readLine();
        System.out.println("password: " + password);

        prepare = connection.prepareStatement(sql);
        prepare.setString(1, username);
        prepare.setString(2, password);

        result = prepare.executeQuery();

        if  (result.next()) {
            os.println("1");
        } else {
            os.println("0");
        }
        os.flush();
    }
}
