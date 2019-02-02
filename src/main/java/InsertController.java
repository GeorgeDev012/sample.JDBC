import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InsertController implements Initializable {

    @FXML private VBox columnsVBox;
    @FXML private Label insertIntoTableLabel;

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
    }

    private void addColumnsToVBox(List<String> columns) {
        TextField[] columnTextFields = new TextField[columns.size()];
        for(int i=0; i<columns.size(); i++) {
            columnTextFields[i] = new TextField();
            columnTextFields[i].setPromptText(columns.get(i));
            columnsVBox.getChildren().add(columnTextFields[i]);
        }
    }

    @FXML void insertButtonAction() {
        TextField a = (TextField)(columnsVBox.getChildren().get(0));
        System.out.println(a.getText());
        StringBuilder stringBuilder = new StringBuilder("Insert into " + tableName + " values (");

        for(Node columnName :  columnsVBox.getChildren()) {
            if(columnName instanceof TextField) {
                if( ((TextField) columnName).getText().equals("")) stringBuilder.append("null");
                else stringBuilder.append(( (TextField) columnName).getText());
                stringBuilder.append(", ");
            }
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(")");
        String statement = stringBuilder.toString();;
        sqlCon.insert(statement);
    }

}
