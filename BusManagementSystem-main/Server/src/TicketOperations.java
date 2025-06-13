import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa odpowiedzialna za operacje na biletach.
 */
public class TicketOperations {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private PreparedStatement prepare;
    private String ticketNum;
    private double totalP = 0;

    /**
     * Metoda obsługująca żądanie dotyczące numeru biletu.
     *
     * @param is strumień wejściowy
     * @param os strumień wyjściowy
     * @throws IOException            wyjątek wejścia-wyjścia
     * @throws ClassNotFoundException wyjątek dotyczący braku klasy
     */
    public void ticketNumber(BufferedReader is, PrintWriter os) throws IOException, ClassNotFoundException {
        String bookingTicket = is.readLine();
        System.out.println("Booking ticket busID: " + bookingTicket);

        String removeSeat = "SELECT seatNum FROM customer WHERE bus_id='"
                +bookingTicket+"'";

        connection = DatabaseConnection.connectDb();

        try {
            prepare = connection.prepareStatement(removeSeat);
            result = prepare.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List list = new ArrayList<>();
        int listLength = 0;

        try {
            while(result.next()) {
                list.add(result.getString("seatNum"));
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
     * Metoda obsługująca żądanie dotyczące rezerwacji biletu.
     *
     * @param is strumień wejściowy
     * @param os strumień wyjściowy
     * @throws IOException wyjątek wejścia-wyjścia
     * @throws SQLException wyjątek SQL
     */
    public void ticketBooking(BufferedReader is, PrintWriter os) throws IOException, SQLException {
        double priceData = 0;

        String firstName = is.readLine();
        System.out.println("First name: " + firstName);
        String lastName = is.readLine();
        System.out.println("Last name: " + lastName);
        String gender = is.readLine();
        System.out.println("Gender: " + gender);
        String phoneNumber = is.readLine();
        System.out.println("Phone number: " + phoneNumber);
        String date = is.readLine();
        System.out.println("Date: " + date);

        String busID = is.readLine();
        System.out.println("Bus ID: " + busID);
        String busLocation = is.readLine();
        System.out.println("Location: " + busLocation);
        String type = is.readLine();
        System.out.println("Type: " + type);
        ticketNum = is.readLine();
        System.out.println("Ticket number: " + ticketNum);

        String totalPrice = "SELECT price FROM bus WHERE location = '"+ busLocation +"'";

        connection = DatabaseConnection.connectDb();
        prepare = connection.prepareStatement(totalPrice);
        result = prepare.executeQuery();

        if (result.next()) {
            priceData = result.getDouble("price");
        }
        if(type.equals("First Class")){
            totalP = (priceData + 100);
        }else if(type.equals("Economy Class")) {
            totalP = priceData;
        }

        os.println("$" + String.valueOf(totalP));
        os.flush();
        connection.close();
    }

    /**
     * Metoda obsługująca żądanie dotyczące płatności za bilet.
     *
     * @param is strumień wejściowy
     * @param os strumień wyjściowy
     * @throws IOException wyjątek wejścia-wyjścia
     * @throws SQLException wyjątek SQL
     */
    public void ticketPay(BufferedReader is, PrintWriter os) throws IOException, SQLException {
        int countRow = 0;

        String payData = "INSERT INTO customer (customer_id,firstName,lastName,gender,phoneNumber,bus_id,location,type,seatNum,total,date)" + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        connection = DatabaseConnection.connectDb();

        // Odczytaj dane od klienta
        String firstName = is.readLine();
        System.out.println("First name: " + firstName);
        String lastName = is.readLine();
        System.out.println("Last name: " + lastName);
        String gender = is.readLine();
        System.out.println("Gender: " + gender);
        String phoneNumber = is.readLine();
        System.out.println("Phone number: " + phoneNumber);
        String date = is.readLine();
        System.out.println("Date: " + date);

        String busID = is.readLine();
        System.out.println("Bus ID: " + busID);
        String busLocation = is.readLine();
        System.out.println("Location: " + busLocation);
        String type = is.readLine();
        System.out.println("Type: " + type);
        String seatNum = is.readLine();
        System.out.println("Ticket number: " + seatNum);

        try{
            // Zlicz liczbę rekordów w tabeli "customer"
            String countNum = "SELECT COUNT(id) FROM customer";
            statement = connection.createStatement();
            result = statement.executeQuery(countNum);
        } catch(Exception e){
            e.printStackTrace();
        }

        while(result.next()){
            countRow = result.getInt("COUNT(id)");
        }

        os.println(countRow);

        // Wykonaj zapytanie INSERT, aby zapisać dane klienta w tabeli "customer"
        prepare = connection.prepareStatement(payData);
        prepare.setString(1, String.valueOf(countRow+1));
        prepare.setString(2, firstName);
        prepare.setString(3, lastName);
        prepare.setString(4, gender);
        prepare.setString(5, phoneNumber);
        prepare.setString(6, busID);
        prepare.setString(7, busLocation);
        prepare.setString(8, type);
        prepare.setString(9, seatNum);
        prepare.setString(10, String.valueOf(totalP));
        prepare.setString(11, date);

        prepare.executeUpdate();

        // Wykonaj zapytanie INSERT, aby zapisać dane klienta w tabeli "customer_receipt"
        String receiptData = "INSERT INTO customer_receipt (customer_id,total,date) VALUES(?,?,?)";

        prepare = connection.prepareStatement(receiptData);
        prepare.setString(1, String.valueOf(countRow+1));
        prepare.setString(2, String.valueOf(totalP));
        prepare.setString(3, date);

        prepare.executeUpdate();

        os.flush();
        connection.close();
    }
}
