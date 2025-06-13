package busticketbookingmanagementsystem;

import java.sql.Date;

/**
 * Klasa reprezentująca dane klienta.
 */
public class customerData {
    private Integer customerNum;  // Numer klienta
    private String firstName;     // Imię klienta
    private String lastName;      // Nazwisko klienta
    private String gender;        // Płeć klienta
    private String phoneNum;      // Numer telefonu klienta
    private Integer busId;        // ID autobusu
    private String location;      // Lokalizacja
    private String type;          // Typ biletu
    private Integer seatNum;      // Numer miejsca
    private Double total;         // Całkowita cena
    private Date date;            // Data

    /**
     * Konstruktor klasy CustomerData.
     *
     * @param customerNum Numer klienta
     * @param firstName   Imię klienta
     * @param lastName    Nazwisko klienta
     * @param gender      Płeć klienta
     * @param phoneNum    Numer telefonu klienta
     * @param busId       ID autobusu
     * @param location    Lokalizacja
     * @param type        Typ biletu
     * @param seatNum     Numer miejsca
     * @param total       Całkowita cena
     * @param date        Data
     */
    public customerData(Integer customerNum, String firstName, String lastName, String gender, String phoneNum, Integer busId, String location, String type, Integer seatNum, Double total, Date date) {
        this.customerNum = customerNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.busId = busId;
        this.location = location;
        this.type = type;
        this.seatNum = seatNum;
        this.total = total;
        this.date = date;
    }

    /**
     * Zwraca numer klienta.
     *
     * @return Numer klienta
     */
    public Integer getCustomerNum() {
        return customerNum;
    }

    /**
     * Zwraca imię klienta.
     *
     * @return Imię klienta
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Zwraca nazwisko klienta.
     *
     * @return Nazwisko klienta
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Zwraca płeć klienta.
     *
     * @return Płeć klienta
     */
    public String getGender() {
        return gender;
    }

    /**
     * Zwraca numer telefonu klienta.
     *
     * @return Numer telefonu klienta
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * Zwraca ID autobusu.
     *
     * @return ID autobusu
     */
    public Integer getBusId() {
        return busId;
    }

    /**
     * Zwraca lokalizację.
     *
     * @return Lokalizacja
     */
    public String getLocation() {
        return location;
    }

    /**
     * Zwraca typ biletu.
     *
     * @return Typ biletu
     */
    public String getType() {
        return type;
    }

    /**
     * Zwraca numer miejsca.
     *
     * @return Numer miejsca
     */
    public Integer getSeatNum() {
        return seatNum;
    }

    /**
     * Zwraca całkowitą cenę.
     *
     * @return Całkowita cena
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Zwraca datę.
     *
     * @return Data
     */
    public Date getDate() {
        return date;
    }
}
