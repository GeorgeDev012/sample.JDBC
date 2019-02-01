import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class InsertController implements Initializable {

    @FXML Label insertIntoTableLabel;
    private static String tableName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertIntoTableLabel.setText("Insert into " + tableName);
        System.out.println(insertIntoTableLabel.getText());
    }

    static void setTableName(String text) {
        tableName = text;
    }
}
