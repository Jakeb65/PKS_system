package busticketbookingmanagementsystem;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Kontroler reprezentujący logikę interfejsu użytkownika klasy dashboard.
 * Implementuje interfejs Initializable, który zapewnia metodę initialize(), która jest automatycznie wywoływana po załadowaniu pliku FXML.
 */
public class dashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button dashboard_Btn;

    @FXML
    private Button availableB_Btn;

    @FXML
    private Button bookingTicket_Btn;

    @FXML
    private Button customers_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_availableB;

    @FXML
    private Label dashboard_incomeToday;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private AnchorPane availableB_form;

    @FXML
    private TextField availableB_busID;

    @FXML
    private TextField availableB_location;

    @FXML
    private ComboBox<?> availableB_status;

    @FXML
    private TextField availableB_price;

    @FXML
    private DatePicker availableB_date;

    @FXML
    private Button availableB_addBtn;

    @FXML
    private Button availableB_updateBtn;

    @FXML
    private Button availableB_resetBtn;

    @FXML
    private Button availableB_deleteBtn;

    @FXML
    private TableView<busData> availableB_tableView;

    @FXML
    private TableColumn<busData, String> availableB_col_busID;

    @FXML
    private TableColumn<busData, String> availableB_col_location;

    @FXML
    private TableColumn<busData, String> availableB_col_status;

    @FXML
    private TableColumn<busData, String> availableB_col_price;

    @FXML
    private TableColumn<busData, String> availableB_col_date;

    @FXML
    private TextField availableB_search;

    @FXML
    private AnchorPane bookingTicket_form;

    @FXML
    private ComboBox<?> bookingTicket_busId;

    @FXML
    private ComboBox<?> bookingTicket_location;

    @FXML
    private ComboBox<?> bookingTicket_type;

    @FXML
    private ComboBox<?> bookingTicket_ticketNum;

    @FXML
    private TextField bookingTicket_firstName;

    @FXML
    private TextField bookingTicket_lastName;

    @FXML
    private ComboBox<?> bookingTicket_gender;

    @FXML
    private TextField bookingTicket_phoneNum;

    @FXML
    private DatePicker bookingTicket_date;

    @FXML
    private Button bookingTicket_selectBtn;

    @FXML
    private Button bookingTicket_resetBtn;

    @FXML
    private Label bookingTicket_sci_firstName;

    @FXML
    private Label bookingTicket_sci_lastNmae;

    @FXML
    private Label bookingTicket_sci_gender;

    @FXML
    private Label bookingTicket_sci_phoneNum;

    @FXML
    private Label bookingTicket_sci_date;

    @FXML
    private Label bookingTicket_sci_busID;

    @FXML
    private Label bookingTicket_sci_location;

    @FXML
    private Label bookingTicket_sci_type;

    @FXML
    private Label bookingTicket_sci_ticketNum;

    @FXML
    private Label bookingTicket_sci_total;

    @FXML
    private Button bookingTicket_sci_pay;

    @FXML
    private Button bookingTicket_sci_receipt;

    @FXML
    private AnchorPane customer_Form;

    @FXML
    private TableView<customerData> customers_tableView;

    @FXML
    private TableColumn<customerData, String> customers_customerNum;

    @FXML
    private TableColumn<customerData, String> customers_ticketNum;

    @FXML
    private TableColumn<customerData, String> customers_firstName;

    @FXML
    private TableColumn<customerData, String> customers_lastName;

    @FXML
    private TableColumn<customerData, String> customers_gender;

    @FXML
    private TableColumn<customerData, String> customers_phoneNum;

    @FXML
    private TableColumn<customerData, String> customers_busID;

    @FXML
    private TableColumn<customerData, String> customers_location;

    @FXML
    private TableColumn<customerData, String> customers_type;

    @FXML
    private TableColumn<customerData, String> customers_date;

    @FXML
    private TextField customers_search;

    private Connection connect;

    private String availableBuses = "0";
    private final ServerConnection serverConnection = ServerConnection.getInstance();
    /**
     * Konstruktor klasy dashboardController.
     * Tworzy instancję klasy dashboardController i inicjalizuje jej właściwości.
     *
     * @throws IOException wyjątek rzucany, gdy wystąpi problem z obsługą plików wejściowych/wyjściowych
     */

    public dashboardController() throws IOException {
    }

    /**
     * Metoda odpowiedzialna za dodawanie dostępnych autobusów.
     * Sprawdza, czy pola nie są puste.
     * Jeśli są puste, wyświetla komunikat o błędzie.
     * W przeciwnym razie wysyła żądanie dodania autobusu do serwera i oczekuje na odpowiedź.
     * Jeśli autobus o podanym ID już istnieje, wyświetla komunikat o błędzie.
     * W przeciwnym razie wyświetla komunikat informacyjny o sukcesie i aktualizuje widok tabeli autobusów.
     * Na koniec resetuje pola formularza.
     */
    public void availableBusAdd() {
        try {

            Alert alert;

            if (availableB_busID.getText().isEmpty()
                    || availableB_location.getText().isEmpty()
                    || availableB_status.getSelectionModel().getSelectedItem() == null
                    || availableB_price.getText().isEmpty()
                    || availableB_date.getValue() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {

                serverConnection.outputStream.println(Requests.BUSADD.getRequest());
                serverConnection.outputStream.println(availableB_busID.getText());
                serverConnection.outputStream.println(availableB_location.getText());
                serverConnection.outputStream.println((String) availableB_status.getSelectionModel().getSelectedItem());
                serverConnection.outputStream.println(availableB_price.getText());
                serverConnection.outputStream.println(availableB_date.getValue());
                serverConnection.outputStream.flush();

                String response = serverConnection.inputStream.readLine();
                System.out.println("Server response: " + response);

                if(response.equals("1")){

                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Bus ID: " + availableB_busID.getText() + " was already exist!");
                    alert.showAndWait();

                } else {

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    availableBShowBusData();
                    availableBusReset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Metoda odpowiedzialna za aktualizację danych autobusu.
     * Sprawdza, czy pola nie są puste.
     * Jeśli są puste, wyświetla komunikat o błędzie.
     * W przeciwnym razie wyświetla potwierdzenie aktualizacji i oczekuje na odpowiedź użytkownika.
     * Jeśli użytkownik zaakceptuje, wysyła żądanie aktualizacji autobusu do serwera i oczekuje na odpowiedź.
     * Jeśli operacja zakończy się sukcesem, wyświetla komunikat informacyjny i aktualizuje widok tabeli autobusów.
     * W przeciwnym razie wyświetla komunikat informacyjny o błędzie.
     */
    public void availableBusUpdate() {
        Alert alert;

        try {
            if (availableB_busID.getText().isEmpty()
                    || availableB_location.getText().isEmpty()
                    || availableB_status.getSelectionModel().getSelectedItem() == null
                    || availableB_price.getText().isEmpty()
                    || availableB_date.getValue() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the item first");
                alert.showAndWait();

            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE data of Bus with ID: " + availableB_busID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    serverConnection.outputStream.println(Requests.BUSUPDATE.getRequest());
                    serverConnection.outputStream.println(availableB_busID.getText());
                    serverConnection.outputStream.println(availableB_location.getText());
                    serverConnection.outputStream.println((String) availableB_status.getSelectionModel().getSelectedItem());
                    serverConnection.outputStream.println(availableB_price.getText());
                    serverConnection.outputStream.println(availableB_date.getValue());
                    serverConnection.outputStream.flush();

                    String response = serverConnection.inputStream.readLine();
                    System.out.println("Server response: " + response);

                    if(response.equals("0")){
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Updated!");
                        alert.showAndWait();

                        availableBShowBusData();
                        availableBusReset();
                    } else {
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Error updating database!");
                        alert.showAndWait();
                    }
                } else {
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda odpowiedzialna za usuwanie autobusu.
     * Sprawdza, czy pola nie są puste.
     * Jeśli są puste, wyświetla komunikat o błędzie.
     * W przeciwnym razie wyświetla potwierdzenie usunięcia i oczekuje na odpowiedź użytkownika.
     * Jeśli użytkownik zaakceptuje, wysyła żądanie usunięcia autobusu do serwera i oczekuje na odpowiedź.
     * Wyświetla komunikat informacyjny o sukcesie i aktualizuje widok tabeli autobusów.
     */
    public void availableBusDelete(){
        try{

            Alert alert;

            if (availableB_busID.getText().isEmpty()
                    || availableB_location.getText().isEmpty()
                    || availableB_price.getText().isEmpty()
                    || availableB_date.getValue() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the item first");
                alert.showAndWait();

            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete data of Bus with ID: " + availableB_busID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){

                    serverConnection.outputStream.println(Requests.BUSDELETE.getRequest());
                    serverConnection.outputStream.println(availableB_busID.getText());
                    serverConnection.outputStream.println(availableB_location.getText());
                    serverConnection.outputStream.println((String) availableB_status.getSelectionModel().getSelectedItem());
                    serverConnection.outputStream.println(availableB_price.getText());
                    serverConnection.outputStream.println(availableB_date.getValue());
                    serverConnection.outputStream.flush();

                    String response = serverConnection.inputStream.readLine();
                    System.out.println("Server response: " + response);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    availableBShowBusData();
                    availableBusReset();

                }else{
                    return;
                }
            }
        }catch(Exception e){e.printStackTrace();}

    }

    /**
     * Metoda resetująca pola formularza dodawania dostępnych autobusów.
     */
    public void availableBusReset() {

        availableB_busID.setText("");
        availableB_location.setText("");
        availableB_status.getSelectionModel().clearSelection();
        availableB_price.setText("");
        availableB_date.setValue(null);

    }

    private String[] statusList = {"Available", "Not Available"};

    /**
     * Ustawia ComboBox statusu, poprzez wypełnienie go wartościami z tablicy statusList.
     */
    public void comboBoxStatus() {

        List<String> listS = new ArrayList<>();

        for (String data : statusList) {
            listS.add(data);
        }

        ObservableList listStatus = FXCollections.observableArrayList(listS);
        availableB_status.setItems(listStatus);

    }

    /**
     * Pobiera dostępne dane o autobusach z serwera i zwraca ObservableList obiektów busData.
     *
     * @return ObservableList obiektów busData zawierający dostępne dane o autobusach.
     * @throws IOException Jeśli wystąpi błąd we/wy.
     */
    public ObservableList<busData> availableBusBusData() throws IOException {

        ObservableList<busData> busListData = FXCollections.observableArrayList();
        busData busD;

            serverConnection.outputStream.println(Requests.BUSAVAILABLE.getRequest());
            serverConnection.outputStream.flush();

            String busNumber = serverConnection.inputStream.readLine();
            System.out.println("Server response bus: " + busNumber);

            String busString;
            for (int i=0;i<Integer.parseInt(busNumber);i++) {
                busString = serverConnection.inputStream.readLine();
                System.out.println("SERVER " + busString);
                Scanner scan = new Scanner(busString);
                scan.useDelimiter(";");
                busD = new busData(Integer.parseInt(scan.next()), scan.next(), scan.next(), Double.parseDouble(scan.next()), java.sql.Date.valueOf(scan.next()));

                busListData.add(busD);
            }

        return busListData;

    }



    private ObservableList<busData> availableBBusListData;

    /**
     * Wyświetla dostępne dane o autobusach.
     *
     * @throws IOException Jeśli wystąpi błąd we/wy.
     * @throws ClassNotFoundException Jeśli nie można odnaleźć klasy.
     */
    public void availableBShowBusData() throws IOException, ClassNotFoundException {

       availableBBusListData = availableBusBusData();

        availableB_col_busID.setCellValueFactory(new PropertyValueFactory<>("busId"));
        availableB_col_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        availableB_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        availableB_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableB_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        availableB_tableView.setItems(availableBBusListData);

    }

    /**
     * Zaznacza wybrane dane o autobusie.
     */
    public void avaialbleBSelectBusData() {

        busData busD = availableB_tableView.getSelectionModel().getSelectedItem();
        int num = availableB_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        availableB_busID.setText(String.valueOf(busD.getBusId()));
        availableB_location.setText(busD.getLocation());
        availableB_price.setText(String.valueOf(busD.getPrice()));
        availableB_date.setValue(LocalDate.parse(String.valueOf(busD.getDate())));

    }

    /**
     * Wyszukuje danych na podstawie wprowadzonego tekstu.
     */
    public void availableSearch(){

        FilteredList<busData> filter = new FilteredList<>(availableBBusListData, e-> true);

        availableB_search.textProperty().addListener((Observable, oldValue, newValue) ->{

            filter.setPredicate(predicateBusData ->{

                if(newValue.isEmpty() || newValue == null){
                    return true;
                }

                String searchKey = newValue.toLowerCase();
                if(predicateBusData.getBusId().toString().contains(searchKey)){
                    return true;
                }else if(predicateBusData.getLocation().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateBusData.getStatus().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateBusData.getDate().toString().contains(searchKey)){
                    return true;
                }else if(predicateBusData.getPrice().toString().contains(searchKey)){
                    return true;
                }else return false;

            });
        });

        SortedList<busData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(availableB_tableView.comparatorProperty());
        availableB_tableView.setItems(sortList);
    }

    /**
     * Pobiera listę identyfikatorów autobusów.
     */
    public void busIdList(){

        try{
            ObservableList listB = FXCollections.observableArrayList();

            serverConnection.outputStream.println(Requests.BUSIDLIST.getRequest());
            serverConnection.outputStream.flush();

            String busNumber = serverConnection.inputStream.readLine();
            System.out.println("Server response: " + busNumber);
            availableBuses = busNumber;

            for (int i=0;i<Integer.parseInt(busNumber);i++) {
                listB.add(serverConnection.inputStream.readLine());
            }

            bookingTicket_busId.setItems(listB);

            ticketNumList();

        }catch(Exception e){e.printStackTrace();}
    }

    /**
     * Pobiera listę lokalizacji autobusów.
     */
    public void LocationList(){
        try{
            ObservableList listL = FXCollections.observableArrayList();

            serverConnection.outputStream.println(Requests.BUSLOCATIONLIST.getRequest());
            serverConnection.outputStream.flush();

            String busNumber = serverConnection.inputStream.readLine();
            System.out.println("Server response: " + busNumber);

            for (int i=0;i<Integer.parseInt(busNumber);i++) {
                listL.add(serverConnection.inputStream.readLine());
            }

            bookingTicket_location.setItems(listL);

            ticketNumList();

        }catch(Exception e){e.printStackTrace();}
    }

    private String[] listT = {"First Class", "Economy Class"};

    /**
     * Ustawia ComboBox typu biletu, poprzez wypełnienie go wartościami z tablicy listT.
     */
    public void typeList(){

        List<String> tList = new ArrayList<>();

        for(String data : listT){
            tList.add(data);
        }

        ObservableList listType = FXCollections.observableArrayList(tList);
        bookingTicket_type.setItems(listType);
    }

    /**
     * Pobiera listę numerów biletów.
     */
    public void ticketNumList(){
        List<String> listTicket = new ArrayList<>();
        for(int q = 1; q <= 40; q++){
            listTicket.add(String.valueOf(q));
        }

        serverConnection.outputStream.println(Requests.TICKETLIST.getRequest());
        serverConnection.outputStream.println(bookingTicket_busId.getSelectionModel().getSelectedItem());
        serverConnection.outputStream.flush();

        try{
            String busNumber = serverConnection.inputStream.readLine();
            System.out.println("Server response: " + busNumber);

            for (int i=0;i<Integer.parseInt(busNumber);i++) {
                listTicket.remove(serverConnection.inputStream.readLine());
            }

            ObservableList listTi = FXCollections.observableArrayList(listTicket);

            bookingTicket_ticketNum.setItems(listTi);
        }catch(Exception e){e.printStackTrace();}
    }

    private double priceData = 0;
    private double totalP = 0;

    /**
     * Zaznacza wybrane dane o rezerwacji biletu i zapisuje je.
     *
     * @throws IOException Jeśli wystąpi błąd we/wy.
     */
    public void bookingTicketSelect() throws IOException {

        String firstName = bookingTicket_firstName.getText();
        String lastName = bookingTicket_lastName.getText();
        String gender = (String)bookingTicket_gender.getSelectionModel().getSelectedItem();
        String phoneNumber = bookingTicket_phoneNum.getText();
        String date = String.valueOf(bookingTicket_date.getValue());

        String busId = (String)bookingTicket_busId.getSelectionModel().getSelectedItem();
        String location = (String)bookingTicket_location.getSelectionModel().getSelectedItem();
        String type = (String)bookingTicket_type.getSelectionModel().getSelectedItem();
        String ticketNum = (String)bookingTicket_ticketNum.getSelectionModel().getSelectedItem();

        Alert alert;

        if(firstName == null || lastName == null
                || gender == null || phoneNumber == null || date == null
                || busId == null || location == null
                || type == null || ticketNum == null){

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        }else{
            serverConnection.outputStream.println(Requests.TICKETBOOKING.getRequest());
            serverConnection.outputStream.println(firstName);
            serverConnection.outputStream.println(lastName);
            serverConnection.outputStream.println(gender);
            serverConnection.outputStream.println(phoneNumber);
            serverConnection.outputStream.println(date);
            serverConnection.outputStream.println(busId);
            serverConnection.outputStream.println(location);
            serverConnection.outputStream.println(type);
            serverConnection.outputStream.println(ticketNum);

            serverConnection.outputStream.flush();

            bookingTicket_sci_total.setText(serverConnection.inputStream.readLine());
            bookingTicket_sci_firstName.setText(firstName);
            bookingTicket_sci_lastNmae.setText(lastName);
            bookingTicket_sci_gender.setText(gender);
            bookingTicket_sci_phoneNum.setText(phoneNumber);
            bookingTicket_sci_date.setText(date);

            bookingTicket_sci_busID.setText(busId);
            bookingTicket_sci_location.setText(location);
            bookingTicket_sci_type.setText(type);
            bookingTicket_sci_ticketNum.setText(ticketNum);

            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Selected!");
            alert.showAndWait();

            bookingTicketReset();
        }
    }

    /**
     * Resetuje pola formularza rezerwacji biletu.
     */
    public void bookingTicketReset(){

        bookingTicket_firstName.setText("");
        bookingTicket_lastName.setText("");
        bookingTicket_gender.getSelectionModel().clearSelection();
        bookingTicket_phoneNum.setText("");
        bookingTicket_date.setValue(null);

    }

    private String[] genderL = {"Male","Female","Others"};

    /**
     * Ustawia ComboBox płci, poprzez wypełnienie go wartościami z tablicy genderL.
     */
    public void genderList(){

        List<String> listG = new ArrayList<>();

        for(String data : genderL){
            listG.add(data);
        }

        ObservableList gList = FXCollections.observableArrayList(listG);
        bookingTicket_gender.setItems(gList);

    }

    private int countRow;
    /**
     * Metoda odpowiedzialna za dokonanie płatności za rezerwację biletu.
     * Pobiera dane wprowadzone przez użytkownika z formularza rezerwacji i wysyła je do serwera.
     * Po dokonaniu płatności wyświetla potwierdzenie oraz resetuje pola formularza.
     */
    public void bookingTicketPay(){
        // Pobranie danych z pól formularza
        String firstName = bookingTicket_sci_firstName.getText();
        String lastName = bookingTicket_sci_lastNmae.getText();
        String gender = bookingTicket_sci_gender.getText();
        String phoneNumber = bookingTicket_sci_phoneNum.getText();
        String date = bookingTicket_sci_date.getText();

        String busId = bookingTicket_sci_busID.getText();
        String location = bookingTicket_sci_location.getText();
        String type = bookingTicket_sci_type.getText();
        String seatNum = bookingTicket_sci_ticketNum.getText();

        try{
            Alert alert;

                // Wysłanie danych do serwera
                serverConnection.outputStream.println(Requests.TICKETPAY.getRequest());
                serverConnection.outputStream.println(firstName);
                serverConnection.outputStream.println(lastName);
                serverConnection.outputStream.println(gender);
                serverConnection.outputStream.println(phoneNumber);
                serverConnection.outputStream.println(date);
                serverConnection.outputStream.println(busId);
                serverConnection.outputStream.println(location);
                serverConnection.outputStream.println(type);
                serverConnection.outputStream.println(seatNum);

                serverConnection.outputStream.flush();

                // Pobranie liczby wierszy z serwera
                countRow = Integer.parseInt(serverConnection.inputStream.readLine());

                // Wyświetlenie potwierdzenia
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                alert.showAndWait();

                // Zaktualizowanie numeru biletu
                getData.number = (countRow + 1);

                // Wyświetlenie informacji o sukcesie
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successful!");
                alert.showAndWait();

                // Resetowanie pól formularza
                bookingTicket_sci_firstName.setText("");
                bookingTicket_sci_lastNmae.setText("");
                bookingTicket_sci_gender.setText("");
                bookingTicket_sci_phoneNum.setText("");
                bookingTicket_sci_date.setText("");
                bookingTicket_sci_busID.setText("");
                bookingTicket_sci_location.setText("");
                bookingTicket_sci_type.setText("");
                bookingTicket_sci_ticketNum.setText("");
                bookingTicket_sci_total.setText("$0.0");

        }catch(Exception e){e.printStackTrace();}
    }


    /**
     * Pobiera dane klientów z serwera i zwraca ObservableList obiektów customerData.
     *
     * @return ObservableList obiektów customerData zawierający dane klientów.
     * @throws IOException Jeśli wystąpi błąd we/wy.
     */
    public ObservableList<customerData> customersDataList() throws IOException {


        ObservableList<customerData> customerList = FXCollections.observableArrayList();
        customerData custD;

        serverConnection.outputStream.println(Requests.CUSTOMERDATA.getRequest());
        serverConnection.outputStream.flush();

        String customerNumber = serverConnection.inputStream.readLine();
        System.out.println("Server response: " + customerNumber);

        String customerString;

        for (int i=0;i<Integer.parseInt(customerNumber);i++) {
            customerString = serverConnection.inputStream.readLine();
            System.out.println("COSTAM SERFVER: " + customerString);
            Scanner scan = new Scanner(customerString);
            scan.useDelimiter(";");
            custD = new customerData(Integer.parseInt(scan.next()), scan.next(), scan.next(), scan.next(), scan.next(), Integer.parseInt(scan.next()), scan.next(), scan.next(), Integer.parseInt(scan.next()), Double.parseDouble(scan.next()), java.sql.Date.valueOf(scan.next()));

            customerList.add(custD);
        }

        return customerList;
    }

    private ObservableList<customerData> customersDataL;

    /**
     * Wyświetla dane klientów w tabeli.
     *
     * @throws IOException Jeśli wystąpi błąd we/wy.
     */
    public void customersShowDataList() throws IOException {

        customersDataL = customersDataList();

        customers_customerNum.setCellValueFactory(new PropertyValueFactory<>("customerNum"));
        customers_ticketNum.setCellValueFactory(new PropertyValueFactory<>("seatNum"));
        customers_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customers_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customers_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        customers_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        customers_busID.setCellValueFactory(new PropertyValueFactory<>("busId"));
        customers_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        customers_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        customers_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        customers_tableView.setItems(customersDataL);

    }

    /**
     * Przeszukuje dane klientów na podstawie wprowadzonego ciągu znaków.
     * Aktualizuje wyświetlaną listę klientów na podstawie wyników wyszukiwania.
     */
    public void customersSearch(){

        FilteredList<customerData> filter = new FilteredList<>(customersDataL, e-> true);

        customers_search.textProperty().addListener((Observable, oldValue, newValue) ->{

            filter.setPredicate(predicateCustomerData ->{

                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if(predicateCustomerData.getCustomerNum().toString().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getSeatNum().toString().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getFirstName().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getLastName().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getGender().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getPhoneNum().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getBusId().toString().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getLocation().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getTotal().toString().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getType().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getDate().toString().contains(searchKey)){
                    return true;
                }else return false;

            });
        });

        SortedList<customerData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(customers_tableView.comparatorProperty());
        customers_tableView.setItems(sortList);
    }

    private double x = 0;
    private double y = 0;

    /**
     * Wylogowuje użytkownika i otwiera nowe okno logowania.
     */
    public void logout() {

        try {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {

                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });
                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ustawia domyślny styl przycisków w interfejsie.
     */
    public void defaultBtn() {
        dashboard_Btn.setStyle("-fx-background-color:#7dbbf5");
        availableB_Btn.setStyle("-fx-background-color:transparent");
        bookingTicket_Btn.setStyle("-fx-background-color:transparent");
        customers_btn.setStyle("-fx-background-color:transparent");
    }

    /**
     * Przełącza widok formularza na podstawie wybranego przycisku.
     *
     * @param event Zdarzenie przycisku.
     * @throws IOException            Jeśli wystąpi błąd we/wy.
     * @throws ClassNotFoundException Jeśli nie można znaleźć klasy.
     */
    public void switchForm(ActionEvent event) throws IOException, ClassNotFoundException {

        // Sprawdzenie, który przycisk został kliknięty
        if (event.getSource() == dashboard_Btn) {
            // Wyświetlenie formularza "Dashboard" i ukrycie pozostałych formularzy
            dashboard_form.setVisible(true);
            availableB_form.setVisible(false);
            bookingTicket_form.setVisible(false);
            customer_Form.setVisible(false);

            // Ustawienie stylu tła przycisków
            dashboard_Btn.setStyle("-fx-background-color:#7dbbf5");
            availableB_Btn.setStyle("-fx-background-color:transparent");
            bookingTicket_Btn.setStyle("-fx-background-color:transparent");
            customers_btn.setStyle("-fx-background-color:transparent");

            // Wywołanie metod odpowiedzialnych za wyświetlanie danych w formularzu "Dashboard"
            dashboardDisplayAB();
            dashboardDisplayIT();
            dashboardDisplayTI();
            dashboardChart();

        } else if (event.getSource() == availableB_Btn) {
            // Wyświetlenie formularza "Dostępne autobusy" i ukrycie pozostałych formularzy
            dashboard_form.setVisible(false);
            availableB_form.setVisible(true);
            bookingTicket_form.setVisible(false);
            customer_Form.setVisible(false);

            // Ustawienie stylu tła przycisków
            availableB_Btn.setStyle("-fx-background-color:#7dbbf5");
            dashboard_Btn.setStyle("-fx-background-color:transparent");
            bookingTicket_Btn.setStyle("-fx-background-color:transparent");
            customers_btn.setStyle("-fx-background-color:transparent");

            // Wywołanie metod odpowiedzialnych za wyświetlanie danych w formularzu "Dostępne autobusy"
            availableBShowBusData();
            availableSearch();

        } else if (event.getSource() == bookingTicket_Btn) {
            // Wyświetlenie formularza "Rezerwacja biletu" i ukrycie pozostałych formularzy
            dashboard_form.setVisible(false);
            availableB_form.setVisible(false);
            bookingTicket_form.setVisible(true);
            customer_Form.setVisible(false);

            // Ustawienie stylu tła przycisków
            bookingTicket_Btn.setStyle("-fx-background-color:#7dbbf5");
            availableB_Btn.setStyle("-fx-background-color:transparent");
            dashboard_Btn.setStyle("-fx-background-color:transparent");
            customers_btn.setStyle("-fx-background-color:transparent");

            // Wywołanie metod odpowiedzialnych za inicjalizację pól formularza "Rezerwacja biletu"
            busIdList();
            LocationList();
            typeList();
            ticketNumList();
            genderList();

        } else if (event.getSource() == customers_btn) {
            // Wyświetlenie formularza "Klienci" i ukrycie pozostałych formularzy
            dashboard_form.setVisible(false);
            availableB_form.setVisible(false);
            bookingTicket_form.setVisible(false);
            customer_Form.setVisible(true);

            // Ustawienie stylu tła przycisków
            customers_btn.setStyle("-fx-background-color:#7dbbf5");
            availableB_Btn.setStyle("-fx-background-color:transparent");
            bookingTicket_Btn.setStyle("-fx-background-color:transparent");
            dashboard_Btn.setStyle("-fx-background-color:transparent");

            // Wywołanie metod odpowiedzialnych za wyświetlanie danych w formularzu "Klienci"
            customersShowDataList();
            customersSearch();
        }
    }

    public void dashboardDisplayAB(){

        // Wysłanie żądania serwerowi dotyczącego wyświetlenia dostępnych autobusów
        serverConnection.outputStream.println(Requests.DISPLAYAB.getRequest());
        serverConnection.outputStream.flush();

        try{
            // Odczytanie odpowiedzi serwera i aktualizacja pola tekstowego na formularzu "Dashboard"
            String response = serverConnection.inputStream.readLine();
            dashboard_availableB.setText(response);
            System.out.println("DASHBOARD: " + response);
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    private double incomeToday = 0;
    public void dashboardDisplayIT(){

        // Wysłanie żądania serwerowi dotyczącego wyświetlenia dzisiejszych dochodów
        serverConnection.outputStream.println(Requests.DISPLAYIT.getRequest());
        serverConnection.outputStream.flush();

        try{
            // Odczytanie odpowiedzi serwera, przetworzenie jej na wartość liczbową i aktualizacja pola tekstowego na formularzu "Dashboard"
            String response = serverConnection.inputStream.readLine();

            incomeToday = Double.parseDouble(response);

            dashboard_incomeToday.setText("$"+(incomeToday));

        } catch (IOException e){e.printStackTrace();}
        serverConnection.outputStream.flush();
    }

    private double totalIncome;
    public void dashboardDisplayTI(){

        // Wysłanie żądania serwerowi dotyczącego wyświetlenia całkowitych dochodów
        serverConnection.outputStream.println(Requests.DISPLAYTI.getRequest());
        serverConnection.outputStream.flush();

        try{
            // Odczytanie odpowiedzi serwera, przetworzenie jej na wartość liczbową i aktualizacja pola tekstowego na formularzu "Dashboard"
            String response = serverConnection.inputStream.readLine();

            totalIncome = Double.parseDouble(response);

            dashboard_totalIncome.setText("$"+ totalIncome);

        }catch(Exception e){
            System.out.println(e);
        }
        serverConnection.outputStream.flush();
    }

    public void dashboardChart(){

        // Wyczyszczenie wykresu
        dashboard_chart.getData().clear();

        // Inicjalizacja serii danych dla wykresu
        XYChart.Series chart = new XYChart.Series();

        // Wysłanie żądania serwerowi dotyczącego wyświetlenia danych do wykresu
        serverConnection.outputStream.println(Requests.DISPLAYCHART.getRequest());
        serverConnection.outputStream.flush();

        try{
            // Odczytanie odpowiedzi serwera i przetworzenie jej w celu dodania danych do serii wykresu
            String response = serverConnection.inputStream.readLine();
            String chartString;
            String first, last;

            for(int i=0;i<Integer.parseInt(response);i++){
                chartString = serverConnection.inputStream.readLine();
                System.out.println(chartString);
                Scanner scan = new Scanner(chartString);
                scan.useDelimiter(";");
                first = scan.next();
                last = scan.next();
                System.out.println("First: " + first + ", Last: " + last);
                chart.getData().add(new XYChart.Data<>(first, Integer.parseInt(last)));
            }

            System.out.println(chart.getData().get(0));
            System.out.println(chart.getData().get(1));
            System.out.println(chart.getData().get(2));
            System.out.println(chart.getData().get(3));
            System.out.println(chart.getData().get(4));

            // Dodanie serii do wykresu
            dashboard_chart.getData().add(chart);

        }catch(Exception e){e.printStackTrace();}
        serverConnection.outputStream.flush();
    }

    public void displayUsername(){
        // Wyświetlenie nazwy użytkownika
        username.setText(getData.username);
    }

    public void close() {
        // Zamknięcie aplikacji
        System.exit(0);
    }
    public void minimize() {
        // Minimalizacja okna aplikacji
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Inicjalizacja domyślnego stanu przycisków i wyświetlenie nazwy użytkownika
        defaultBtn();
        displayUsername();

        // Wywołanie metod odpowiedzialnych za wyświetlanie danych w formularzu "Dashboard"
        dashboardDisplayAB();
        dashboardDisplayIT();
        dashboardDisplayTI();
        dashboardChart();

        // Inicjalizacja pól wyboru
        comboBoxStatus();

        try {
            availableBShowBusData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Inicjalizacja list rozwijanych
        busIdList();
        LocationList();
        typeList();
        ticketNumList();
        genderList();

        try {
            customersShowDataList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
