import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JavaFX entry (FXML + ResourceBundle i18n) for In-class Assignment 6.
 */
public class Main extends Application {
    private Stage primaryStage;
    private Locale locale = Locale.US;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        loadUi(locale);
    }

    void switchLanguage(Locale newLocale) {
        this.locale = newLocale;
        try {
            loadUi(locale);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to reload UI for locale " + newLocale, ex);
        }
    }

    private void loadUi(Locale locale) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", locale);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/temperature.fxml"), bundle);
        Parent root = loader.load();
        TemperatureViewController controller = loader.getController();
        controller.setApp(this);
        controller.init(bundle, locale);

        primaryStage.setTitle(bundle.getString("app.title"));
        primaryStage.setScene(new Scene(root, 600, 540));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
