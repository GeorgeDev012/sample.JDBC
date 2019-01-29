import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCController implements Initializable {
    @FXML private ChoiceBox choiceBox1;
    @FXML private Button button;
    @FXML private ChoiceBox choiceBox2;
    @FXML private TableView tableView1;
    SQLConnection sqlCon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList();

        sqlCon = new SQLConnection();
        sqlCon.getConnection();
        ObservableList<String> abc = sqlCon.getTablesName();
        choiceBox2.setItems(abc);
    }

    @FXML private void handleSubmitButtonAction(ActionEvent e) {
        String statement = choiceBox1.getValue() + " " + choiceBox2.getValue();
        String[][] strings = sqlCon.getContentFromDatabase(statement);
        tableView1.getColumns().addAll(sqlCon.getColumns());
    }










}

