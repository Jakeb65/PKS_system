import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Klasa Display jest odpowiedzialna za wyświetlanie różnych informacji związanych z systemem zarządzania autobusami.
 * Umożliwia pobieranie danych z bazy danych i przekazywanie ich do strumienia wyjściowego.
 */
public class Display {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private PreparedStatement prepare;

    /**
     * Wyświetla liczbę dostępnych autobusów.
     *
     * @param is Strumień wejściowy
     * @param os Strumień wyjściowy, do którego zostanie przekazana liczba dostępnych autobusów
     * @throws SQLException jeśli wystąpi błąd podczas wykonania zapytania SQL
     */
    public void displayAB(BufferedReader is, PrintWriter os) throws SQLException {
        String sql = "SELECT COUNT(id) FROM bus WHERE status = 'Available'";
        connection = DatabaseConnection.connectDb();

        int countAB = 0;

        prepare = connection.prepareStatement(sql);
        result = prepare.executeQuery();

        while(result.next()){
            countAB = result.getInt("COUNT(id)");
        }

        os.println(countAB);
        os.flush();
    }

    /**
     * Wyświetla dzisiejszy dochód ze sprzedaży biletów.
     *
     * @param is Strumień wejściowy
     * @param os Strumień wyjściowy, do którego zostanie przekazany dzisiejszy dochód
     * @throws IOException jeśli wystąpi błąd wejścia/wyjścia
     * @throws ClassNotFoundException jeśli nie można znaleźć klasy sterownika JDBC
     */
    public void displayIT(BufferedReader is, PrintWriter os) throws IOException, ClassNotFoundException {
        java.util.Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        System.out.println(sqlDate);
        System.out.println(date);

        String sql = "SELECT SUM(total) FROM customer WHERE date ='"+sqlDate+"'";
        connection = DatabaseConnection.connectDb();

        double incomeToday = 0;

        try {
            prepare = connection.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                incomeToday = result.getDouble("SUM(total)");
            }

            os.println(incomeToday);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        os.flush();
    }

    /**
     * Wyświetla całkowity dochód ze sprzedaży biletów.
     *
     * @param is Strumień wejściowy
     * @param os Strumień wyjściowy, do którego zostanie przekazany całkowity dochód
     * @throws IOException jeśli wystąpi błąd wejścia/wyjścia
     * @throws ClassNotFoundException jeśli nie można znaleźć klasy sterownika JDBC
     */
    public void displayTI(BufferedReader is, PrintWriter os) throws IOException, ClassNotFoundException {
        String sql = "SELECT SUM(total) FROM customer";
        connection = DatabaseConnection.connectDb();

        double totalIncome = 0;

        try {
            prepare = connection.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                totalIncome = result.getDouble("SUM(total)");
            }

            os.println(totalIncome);
            System.out.println(totalIncome);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        os.flush();
    }

    /**
     * Wyświetla dane do tworzenia wykresu z dochodami z ostatnich 9 dni.
     *
     * @param is Strumień wejściowy
     * @param os Strumień wyjściowy, do którego zostaną przekazane dane wykresu
     * @throws IOException jeśli wystąpi błąd wejścia/wyjścia
     * @throws ClassNotFoundException jeśli nie można znaleźć klasy sterownika JDBC
     */
    public void displayChart(BufferedReader is, PrintWriter os) throws IOException, ClassNotFoundException {
        String sql = "SELECT date,SUM(total) FROM customer WHERE date != '' GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 9";
        connection = DatabaseConnection.connectDb();

        String data;
        ArrayList<String> dataArray = new ArrayList<>();
        int dataSize = 0;

        try {
            prepare = connection.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                data = result.getString(1) + ";" + result.getInt(2);
                dataArray.add(data);
                dataSize++;
            }

            os.println(dataSize);

            for(int i = 0; i<dataSize; i++){
                os.println(dataArray.get(i));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        os.flush();
    }
}
