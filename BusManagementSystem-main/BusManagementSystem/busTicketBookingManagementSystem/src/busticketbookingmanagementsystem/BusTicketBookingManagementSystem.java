package busticketbookingmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Klasa główna odpowiedzialna za uruchomienie systemu zarządzania rezerwacją biletów autobusowych.
 */
public class BusTicketBookingManagementSystem extends Application {

        private double x = 0;
        private double y = 0;

        /**
         * Metoda start uruchamiająca aplikację.
         *
         * @param stage Obiekt Stage
         * @throws Exception W przypadku wystąpienia błędu podczas uruchamiania aplikacji
         */
        @Override
        public void start(Stage stage) throws Exception {
                ServerConnection serverConnection = ServerConnection.getInstance();
                System.out.println(serverConnection.socket.getLocalAddress() + ":" + serverConnection.socket.getLocalPort());

                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

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
        }

        /**
         * Główna metoda uruchomieniowa aplikacji.
         *
         * @param args Argumenty wiersza poleceń
         */
        public static void main(String[] args) {
                launch(args);
        }
}
