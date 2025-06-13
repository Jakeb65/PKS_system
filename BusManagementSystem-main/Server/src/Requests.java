/**
 * Enumeracja zawierająca dostępne żądania obsługiwane przez serwer.
 */
public enum Requests {
    LOGIN("login"),
    BUSADD("busadd"),
    BUSUPDATE("busupdate"),
    BUSDELETE("busdelete"),
    BUSAVAILABLE("busavailable"),
    BUSIDLIST("busidlist"),
    BUSLOCATIONLIST("buslocationlist"),
    TICKETLIST("ticketlist"),
    TICKETBOOKING("ticketbooking"),
    TICKETPAY("ticketpay"),
    CUSTOMERDATA("customerdata"),
    DISPLAYAB("displayab"),
    DISPLAYIT("displayit"),
    DISPLAYTI("displayti"),
    DISPLAYCHART("displaychart");

    private String request;

    /**
     * Konstruktor enuma żądań.
     *
     * @param request nazwa żądania
     */
    Requests(String request) {
        this.request = request;
    }

    /**
     * Zwraca nazwę żądania.
     *
     * @return nazwa żądania
     */
    public String getRequest() {
        return request;
    }
}
