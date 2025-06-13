import java.sql.Date;

/**
 * Klasa reprezentująca dane klienta.
 */
public class Customer {
    private Integer customerNum;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNum;
    private Integer busId;
    private String location;
    private String type;
    private Integer seatNum;
    private Double total;
    private Date date;

    /**
     * Konstruktor tworzący obiekt klienta.
     *
     * @param customerNum numer klienta
     * @param firstName   imię klienta
     * @param lastName    nazwisko klienta
     * @param gender      płeć klienta
     * @param phoneNum    numer telefonu klienta
     * @param busId       identyfikator autobusu
     * @param location    lokalizacja autobusu
     * @param type        typ klienta
     * @param seatNum     numer miejsca
     * @param total       całkowity koszt
     * @param date        data
     */
    public Customer(Integer customerNum, String firstName, String lastName, String gender, String phoneNum, Integer busId, String location, String type, Integer seatNum, Double total, Date date){
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
     * Metoda zwracająca numer klienta.
     *
     * @return numer klienta
     */
    public Integer getCustomerNum(){
        return customerNum;
    }

    /**
     * Metoda zwracająca imię klienta.
     *
     * @return imię klienta
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * Metoda zwracająca nazwisko klienta.
     *
     * @return nazwisko klienta
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * Metoda zwracająca płeć klienta.
     *
     * @return płeć klienta
     */
    public String getGender(){
        return gender;
    }

    /**
     * Metoda zwracająca numer telefonu klienta.
     *
     * @return numer telefonu klienta
     */
    public String getPhoneNum(){
        return phoneNum;
    }

    /**
     * Metoda zwracająca identyfikator autobusu.
     *
     * @return identyfikator autobusu
     */
    public Integer getBusId(){
        return busId;
    }

    /**
     * Metoda zwracająca lokalizację autobusu.
     *
     * @return lokalizacja autobusu
     */
    public String getLocation(){
        return location;
    }

    /**
     * Metoda zwracająca typ klienta.
     *
     * @return typ klienta
     */
    public String getType(){
        return type;
    }

    /**
     * Metoda zwracająca numer miejsca.
     *
     * @return numer miejsca
     */
    public Integer getSeatNum(){
        return seatNum;
    }

    /**
     * Metoda zwracająca całkowity koszt.
     *
     * @return całkowity koszt
     */
    public Double getTotal(){
        return total;
    }

    /**
     * Metoda zwracająca datę.
     *
     * @return data
     */
    public Date getDate(){
        return date;
    }

}
