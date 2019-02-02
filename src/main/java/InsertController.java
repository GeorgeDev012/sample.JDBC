import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InsertController implements Initializable {

    @FXML public VBox columnsVBox;
    @FXML Label insertIntoTableLabel;

    private String tableName;
    private SQLConnection sqlCon;

    InsertController(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sqlCon = new SQLConnection();
        sqlCon.setConnection();
        addColumnsToVBox(sqlCon.getColumnNames(tableName));
        insertIntoTableLabel.setText("Insert into " + tableName + "\n values");
        //tableChoiceBox.setItems(abc);
    }

    void setTableName(String tableName) {
        insertIntoTableLabel.setText("Insert into " + tableName + "\n values");
        //this.tableName = tableName;
    }

    private void addColumnsToVBox(List<String> columns) {
        TextField[] textFields = new TextField[columns.size()];
        for(int i=0; i<columns.size(); i++) {
            textFields[i] = new TextField();
            textFields[i].setPromptText(columns.get(i));
            columnsVBox.getChildren().add(textFields[i]);
        }

    }


}
