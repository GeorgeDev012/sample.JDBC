import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class JDBC extends Application {
    static FXMLLoader fxmlLoader;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        fxmlLoader = new FXMLLoader();
        VBox root = fxmlLoader.load(getClass().getResource("mainView.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }





}