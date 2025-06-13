import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Wątek serwera odpowiedzialny za obsługę indywidualnego połączenia klienta.
 */
public class ServerThread extends Thread {

    BufferedReader  is;
    PrintWriter os;
    Socket s;
    Connection connection;
    Login login = new Login();
    BusOperations busOperations = new BusOperations();
    TicketOperations ticketOperations = new TicketOperations();
    CustomerData customerData = new CustomerData();
    Display display = new Display();
    String inputStream;

    /**
     * Konstruktor klasy ServerThread.
     *
     * @param s           gniazdo połączenia klienta
     * @param connection połączenie do bazy danych
     */
    public ServerThread(Socket s, Connection connection){
        this.s=s;
        this.connection = connection;
    }

    /**
     * Metoda uruchamiana po rozpoczęciu wątku.
     * Odpowiada za inicjalizację strumieni wejściowych i wyjściowych oraz obsługę żądań klienta.
     */
    public void run() {
        try {
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = new PrintWriter(s.getOutputStream());
        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }

        // Główna pętla wątku, nasłuchuje żądań klienta i obsługuje je
        while(true) {
            try {
                is.ready();
                inputStream = is.readLine();
                System.out.println(inputStream);
                if(inputStream.equals(Requests.LOGIN.getRequest())) {
                    login.userLogin(is, os);
                } else if(inputStream.equals(Requests.BUSADD.getRequest())) {
                    busOperations.busAdd(is, os);
                } else if(inputStream.equals(Requests.BUSUPDATE.getRequest())) {
                    busOperations.busUpdate(is, os);
                } else if(inputStream.equals(Requests.BUSDELETE.getRequest())) {
                    busOperations.busDelete(is, os);
                } else if(inputStream.equals(Requests.BUSAVAILABLE.getRequest())) {
                    busOperations.availableBus(is, os);
                } else if(inputStream.equals(Requests.BUSIDLIST.getRequest())) {
                    busOperations.busIdList(is, os);
                } else if(inputStream.equals(Requests.BUSLOCATIONLIST.getRequest())) {
                    busOperations.busLocationList(is, os);
                } else if(inputStream.equals(Requests.TICKETLIST.getRequest())) {
                    ticketOperations.ticketNumber(is, os);
                } else if(inputStream.equals(Requests.TICKETBOOKING.getRequest())) {
                    ticketOperations.ticketBooking(is, os);
                } else if(inputStream.equals(Requests.TICKETPAY.getRequest())) {
                    ticketOperations.ticketPay(is, os);
                } else if(inputStream.equals(Requests.CUSTOMERDATA.getRequest())) {
                    customerData.customer(is, os);
                } else if(inputStream.equals(Requests.DISPLAYAB.getRequest())) {
                    display.displayAB(is, os);
                } else if(inputStream.equals(Requests.DISPLAYIT.getRequest())) {
                    display.displayIT(is, os);
                } else if(inputStream.equals(Requests.DISPLAYTI.getRequest())) {
                    display.displayTI(is, os);
                } else if(inputStream.equals(Requests.DISPLAYCHART.getRequest())) {
                    display.displayChart(is, os);
                }
            } catch (IOException ie) {
                System.out.println(ie);
                break;
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        }
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (os != null) {
            os.close();
        }
        if (s != null) {
            try {
                s.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(s);
        }
    }
}
