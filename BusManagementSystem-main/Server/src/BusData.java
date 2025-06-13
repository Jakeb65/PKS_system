import java.io.Serializable;
import java.sql.Date;

/**
 * Klasa reprezentująca dane o autobusie.
 * Implementuje interfejs Serializable, aby umożliwić serializację obiektów tej klasy.
 */
public class BusData implements Serializable {

    private Integer busId;
    private String location;
    private String status;
    private Double price;
    private Date date;

    /**
     * Konstruktor tworzący obiekt BusData z określonymi parametrami.
     *
     * @param busId    identyfikator autobusu
     * @param location lokalizacja autobusu
     * @param status   status autobusu
     * @param price    cena biletu autobusowego
     * @param date     data autobusu
     */
    public BusData(Integer busId, String location, String status, Double price, Date date) {
        this.busId = busId;
        this.location = location;
        this.status = status;
        this.price = price;
        this.date = date;
    }

    /**
     * Metoda zwracająca identyfikator autobusu.
     *
     * @return identyfikator autobusu
     */
    public Integer getBusId() {
        return busId;
    }

    /**
     * Metoda zwracająca lokalizację autobusu.
     *
     * @return lokalizacja autobusu
     */
    public String getLocation() {
        return location;
    }

    /**
     * Metoda zwracająca status autobusu.
     *
     * @return status autobusu
     */
    public String getStatus() {
        return status;
    }

    /**
     * Metoda zwracająca cenę biletu autobusowego.
     *
     * @return cena biletu autobusowego
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Metoda zwracająca datę autobusu.
     *
     * @return data autobusu
     */
    public Date getDate() {
        return date;
    }
}
