import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa zawierająca metody do zarządzania danymi klientów w bazie danych.
 */
public class CustomerData {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private PreparedStatement prepare;

    /**
     * Metoda pobierająca dane klientów z bazy danych i wysyłająca je do strumienia wyjściowego.
     *
     * @param is strumień wejściowy
     * @param os strumień wyjściowy
     * @throws IOException  wyjątek wejścia/wyjścia
     * @throws SQLException wyjątek SQL
     */
    public void customer(BufferedReader is, PrintWriter os) throws IOException, SQLException {

        // Tworzenie zapytania SQL do pobrania danych klientów
        String sql = "SELECT * FROM customer";

        // Nawiązanie połączenia z bazą danych
        connection = DatabaseConnection.connectDb();

        try {
            // Przygotowanie zapytania SQL
            prepare = connection.prepareStatement(sql);
            // Wykonanie zapytania SQL
            result = prepare.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Customer customer;
        List<Customer> customerList = new ArrayList<>();
        int customerDataLength=0;

        try {
            while(result.next()) {
                // Pobieranie danych klientów i tworzenie obiektów Customer
                customer = new Customer(result.getInt("customer_id")
                        , result.getString("firstName")
                        , result.getString("lastName")
                        , result.getString("gender")
                        , result.getString("phoneNumber")
                        , result.getInt("bus_id")
                        , result.getString("location")
                        , result.getString("type")
                        , result.getInt("seatNum")
                        , result.getDouble("total")
                        , result.getDate("date"));

                customerList.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerDataLength = customerList.size();

        String customerString;
        // Wysyłanie liczby klientów do strumienia wyjściowego
        os.println(customerDataLength);

        // Generowanie łańcucha znaków dla każdego klienta i wysyłanie do strumienia wyjściowego
        for(int i=0;i<customerDataLength;i++) {
            customerString = customerList.get(i).getCustomerNum().toString() + ";"
                    + customerList.get(i).getFirstName() + ";"
                    + customerList.get(i).getLastName() + ";"
                    + customerList.get(i).getGender() + ";"
                    + customerList.get(i).getPhoneNum() + ";"
                    + customerList.get(i).getBusId().toString() + ";"
                    + customerList.get(i).getLocation() + ";"
                    + customerList.get(i).getType() + ";"
                    + customerList.get(i).getSeatNum().toString() + ";"
                    + customerList.get(i).getTotal().toString() + ";"
                    + customerList.get(i).getDate().toString();
            System.out.println(customerString);
            os.println(customerString);
        }
        os.flush();
    }
}
