import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class SelectController implements Initializable {

    @FXML Label selectLabel;
    @FXML Label fromLabel;
    @FXML Label tableLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SQLConnection sqlCon = new SQLConnection();
        sqlCon.setConnection();

    }

    void setTableLabelText(String text) {
        tableLabel.setText(text);
    }

    @FXML
    public void onMouseClicked(ActionEvent e) {
        CheckBox[] boxes = MainController.getColumnCheckBoxes();
        StringBuilder statement = new StringBuilder();
        statement.append("select ");
        for (CheckBox box : boxes) {
            if (box.isSelected()) {
                statement.append(box.getText()).append(", ");
            }
        }
        statement.setLength(statement.length() - 2);
        statement.append(" from ").append(tableLabel.getText());
        MainController controller = JDBC.fxmlLoader.getController();
        controller.populateTableView(statement.toString());
        MainController.selectStage.close();
        controller.isOpen = false;
    }
}
