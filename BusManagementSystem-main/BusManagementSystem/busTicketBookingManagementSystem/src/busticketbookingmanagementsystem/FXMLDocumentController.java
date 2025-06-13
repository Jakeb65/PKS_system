package busticketbookingmanagementsystem;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Button close;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private double x = 0;
    private double y = 0;

    /**
     * Metoda obsługująca zdarzenie logowania.
     * Wywoływana po naciśnięciu przycisku logowania.
     * Sprawdza poprawność wprowadzonych danych logowania i wyświetla odpowiednie komunikaty.
     *
     * @throws IOException jeśli wystąpi błąd podczas ładowania pliku FXML dla panelu głównego.
     */
    public void login() throws IOException {
        // Kod obsługujący logikę logowania
        ServerConnection serverConnection = ServerConnection.getInstance();
        Alert alert;

        try {

            if (username.getText().isEmpty() || password.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {

                serverConnection.outputStream.println(Requests.LOGIN.getRequest());
                serverConnection.outputStream.println(username.getText());
                serverConnection.outputStream.println(password.getText());
                serverConnection.outputStream.flush();

                String response = serverConnection.inputStream.readLine();
                System.out.println("Server response: " + response);

                if(response.equals("1")){
                    getData.username = username.getText();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    login.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Metoda obsługująca zdarzenie zamknięcia aplikacji.
     * Wywoływana po naciśnięciu przycisku zamknięcia aplikacji.
     * Zamyka aplikację.
     */
    public void close() {
        System.exit(0);
    }

    /**
     * Metoda inicjalizacyjna wywoływana przy inicjalizacji kontrolera.
     * Aktualnie nie wykonuje żadnych operacji.
     *
     * @param url           adres URL pliku FXML.
     * @param rb obiekt ResourceBundle zawierający zasoby lokalizacyjne.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
