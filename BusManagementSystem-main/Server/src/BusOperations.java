import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa odpowiedzialna za operacje na danych dotyczących autobusów.
 * Zawiera metody umożliwiające dodawanie, aktualizowanie, usuwanie oraz pobieranie danych dotyczących autobusów.
 */
public class BusOperations {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private PreparedStatement prepare;

    /**
     * Metoda umożliwiająca dodanie danych dotyczących autobusu.
     *
     * @param is strumień wejściowy dla odczytu danych od klienta
     * @param os strumień wyjściowy dla wysłania odpowiedzi do klienta
     * @throws IOException  wyjątek oznaczający problem z operacjami wejścia/wyjścia
     * @throws SQLException wyjątek oznaczający problem z operacjami na bazie danych
     */
    public void busAdd(BufferedReader is, PrintWriter os) throws IOException, SQLException {
        String addData = "INSERT INTO bus (bus_id,location,status,price,date) VALUES(?,?,?,?,?)";
        connection = DatabaseConnection.connectDb();

        String busID = is.readLine();
        System.out.println("Bus ID: " + busID);
        String busLocation = is.readLine();
        System.out.println("Location: " + busLocation);
        String busStatus = is.readLine();
        System.out.println("Status: " + busStatus);
        String busPrice = is.readLine();
        System.out.println("Price: " + busPrice);
        String busDate = is.readLine();
        System.out.println("Date: " + busDate);

        String check = "SELECT bus_id FROM bus WHERE bus_id = '" + busID + "'";

        statement = connection.createStatement();
        result = statement.executeQuery(check);

        if (result.next()) {
            os.println("1");
            os.flush();
        } else {
            prepare = connection.prepareStatement(addData);
            prepare.setString(1, busID);
            prepare.setString(2, busLocation);
            prepare.setString(3, busStatus);
            prepare.setString(4, busPrice);
            prepare.setString(5, busDate);

            prepare.executeUpdate();

            os.println("0");
            os.flush();
            connection.close();
        }
    }

    /**
     * Metoda umożliwiająca aktualizację danych dotyczących autobusu.
     *
     * @param is strumień wejściowy dla odczytu danych od klienta
     * @param os strumień wyjściowy dla wysłania odpowiedzi do klienta
     * @throws IOException  wyjątek oznaczający problem z operacjami wejścia/wyjścia
     * @throws SQLException wyjątek oznaczający problem z operacjami na bazie danych
     */
    public void busUpdate(BufferedReader is, PrintWriter os) throws IOException, SQLException {

        String busID = is.readLine();
        System.out.println("Bus ID: " + busID);
        String busLocation = is.readLine();
        System.out.println("Location: " + busLocation);
        String busStatus = is.readLine();
        System.out.println("Status: " + busStatus);
        String busPrice = is.readLine();
        System.out.println("Price: " + busPrice);
        String busDate = is.readLine();
        System.out.println("Date: " + busDate);

        String updateData = "UPDATE bus SET location = '"
                + busLocation + "', status = '"
                + busStatus
                + "', price = '" + busPrice
                + "', date = '" + busDate
                + "' WHERE bus_id = '" + busID + "'";

        connection = DatabaseConnection.connectDb();

        prepare = connection.prepareStatement(updateData);
        prepare.executeUpdate();

        os.println("0");
        os.flush();
        connection.close();
    }

    /**
     * Metoda umożliwiająca usunięcie danych dotyczących autobusu.
     *
     * @param is strumień wejściowy dla odczytu danych od klienta
     * @param os strumień wyjściowy dla wysłania odpowiedzi do klienta
     * @throws IOException  wyjątek oznaczający problem z operacjami wejścia/wyjścia
     * @throws SQLException wyjątek oznaczający problem z operacjami na bazie danych
     */
    public void busDelete(BufferedReader is, PrintWriter os) throws IOException, SQLException {

        String busID = is.readLine();
        System.out.println("Bus ID: " + busID);
        String busLocation = is.readLine();
        System.out.println("Location: " + busLocation);
        String busStatus = is.readLine();
        System.out.println("Status: " + busStatus);
        String busPrice = is.readLine();
        System.out.println("Price: " + busPrice);
        String busDate = is.readLine();
        System.out.println("Date: " + busDate);

        String deleteData = "DELETE FROM bus WHERE bus_id = '"
                + busID + "'";

        connection = DatabaseConnection.connectDb();

        statement = connection.createStatement();
        statement.executeUpdate(deleteData);

        os.println("0");
        os.flush();
    }

    /**
     * Metoda umożliwiająca pobranie dostępnych autobusów.
     *
     * @param is strumień wejściowy dla odczytu danych od klienta
     * @param os strumień wyjściowy dla wysłania odpowiedzi do klienta
     * @throws IOException            wyjątek oznaczający problem z operacjami wejścia/wyjścia
     * @throws ClassNotFoundException wyjątek oznaczający brak zdefiniowanej klasy
     */
    public void availableBus(BufferedReader is, PrintWriter os) throws IOException, ClassNotFoundException {
        String sql = "SELECT * FROM bus";

        connection = DatabaseConnection.connectDb();

        try {
            prepare = connection.prepareStatement(sql);
            result = prepare.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        BusData busData;
        List<BusData> busDataList = new ArrayList<>();
        int busDataLength=0;

        try {
            while(result.next()) {
                busData = new BusData(result.getInt("bus_id"),
                    result.getString("location"),
                    result.getString("status"),
                    result.getDouble("price"),
                    result.getDate("date"));

                busDataList.add(busData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        busDataLength=busDataList.size();

        String busString;
        os.println(busDataLength);

        for(int i=0;i<busDataLength;i++) {
            busString = busDataList.get(i).getBusId().toString() + ";"
                    + busDataList.get(i).getLocation() + ";"
                    + busDataList.get(i).getStatus() + ";"
                    + busDataList.get(i).getPrice().toString() + ";"
                    + busDataList.get(i).getDate().toString();
            System.out.println(busString);
            os.println(busString);
        }
        os.flush();
    }

    /**
     * Metoda umożliwiająca pobranie listy identyfikatorów autobusów.
     *
     * @param is strumień wejściowy dla odczytu danych od klienta
     * @param os strumień wyjściowy dla wysłania odpowiedzi do klienta
     * @throws IOException            wyjątek oznaczający problem z operacjami wejścia/wyjścia
     * @throws ClassNotFoundException wyjątek oznaczający brak zdefiniowanej klasy
     */
    public void busIdList(BufferedReader is, PrintWriter os) throws IOException, ClassNotFoundException {
        String busD = "SELECT * FROM bus WHERE status = 'Available'";

        connection = DatabaseConnection.connectDb();

        try {
            prepare = connection.prepareStatement(busD);
            result = prepare.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List list = new ArrayList<>();
        int listLength = 0;

        try {
            while(result.next()) {
                list.add(result.getString("bus_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listLength = list.size();

        String string;
        os.println(listLength);

        for(int i=0;i<listLength;i++) {
            string = list.get(i).toString();
            System.out.println(string);
            os.println(string);
        }
        os.flush();
    }

    /**
     * Metoda umożliwiająca pobranie listy lokalizacji autobusów.
     *
     * @param is strumień wejściowy dla odczytu danych od klienta
     * @param os strumień wyjściowy dla wysłania odpowiedzi do klienta
     * @throws IOException            wyjątek oznaczający problem z operacjami wejścia/wyjścia
     * @throws ClassNotFoundException wyjątek oznaczający brak zdefiniowanej klasy
     */
    public void busLocationList(BufferedReader is, PrintWriter os) throws IOException, ClassNotFoundException {
        String locationL = "SELECT * FROM bus WHERE status = 'Available'";

        connection = DatabaseConnection.connectDb();

        try {
            prepare = connection.prepareStatement(locationL);
            result = prepare.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List list = new ArrayList<>();
        int listLength = 0;

        try {
            while(result.next()) {
                list.add(result.getString("location"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listLength = list.size();

        String string;
        os.println(listLength);

        for(int i=0;i<listLength;i++) {
            string = list.get(i).toString();
            System.out.println(string);
            os.println(string);
        }
        os.flush();
    }
}
