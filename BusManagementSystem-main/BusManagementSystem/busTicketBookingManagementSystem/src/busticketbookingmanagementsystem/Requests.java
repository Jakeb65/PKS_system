package busticketbookingmanagementsystem;

/**
 * Wyliczenie zawierające różne typy żądań używane w systemie zarządzania rezerwacją biletów autobusowych.
 * Każdy typ żądania ma przypisaną odpowiadającą mu wartość tekstową.
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
     * Konstruktor tworzący nową instancję typu żądania z przypisaną wartością tekstową.
     *
     * @param request wartość tekstowa żądania
     */
    Requests(String request) {
        this.request = request;
    }

    /**
     * Metoda zwracająca wartość tekstową żądania.
     *
     * @return wartość tekstowa żądania
     */
    public String getRequest() {
        return request;
    }
}
