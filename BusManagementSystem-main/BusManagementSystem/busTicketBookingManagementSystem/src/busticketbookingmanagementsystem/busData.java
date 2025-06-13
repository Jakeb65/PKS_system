package busticketbookingmanagementsystem;

import java.io.Serializable;
import java.sql.Date;

/**
 * Klasa reprezentująca dane autobusu.
 */
public class busData implements Serializable {

    private Integer busId;       // ID autobusu
    private String location;     // Lokalizacja
    private String status;       // Status
    private Double price;        // Cena
    private Date date;           // Data

    /**
     * Konstruktor klasy BusData.
     *
     * @param busId    ID autobusu
     * @param location Lokalizacja autobusu
     * @param status   Status autobusu
     * @param price    Cena biletu autobusowego
     * @param date     Data autobusu
     */
    public busData(Integer busId, String location, String status, Double price, Date date) {
        this.busId = busId;
        this.location = location;
        this.status = status;
        this.price = price;
        this.date = date;
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
     * Zwraca lokalizację autobusu.
     *
     * @return Lokalizacja autobusu
     */
    public String getLocation() {
        return location;
    }

    /**
     * Zwraca status autobusu.
     *
     * @return Status autobusu
     */
    public String getStatus() {
        return status;
    }

    /**
     * Zwraca cenę biletu autobusowego.
     *
     * @return Cena biletu autobusowego
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Zwraca datę autobusu.
     *
     * @return Data autobusu
     */
    public Date getDate() {
        return date;
    }
}
