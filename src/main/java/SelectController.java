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
    @FXML ChoiceBox<String> tablesChoiceBox;
    private String tableLabelValue = "a";
    String statement;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SQLConnection sqlCon = new SQLConnection();
        sqlCon.getConnection();
        System.out.println("abc");
        tableLabel.setText(tableLabelValue);
        //ObservableList<String> abc = sqlCon.getTablesName();
        //tablesChoiceBox.setItems(abc);
        //Stage stage = (Stage) tablesChoiceBox.getScene().getWindow();
        //setTablesChoiceBoxOnAction();

    }

    private void setTablesChoiceBoxOnAction () {
        Stage stage = (Stage) tablesChoiceBox.getScene().getWindow();
        tablesChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) ->

                        stage.setScene(new Scene(new StackPane(),300,300))
                       );
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
        this.statement = statement.toString();
    }
}
