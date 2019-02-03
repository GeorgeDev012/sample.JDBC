import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {

    @FXML private Label updateTableLabel;
    @FXML private VBox columnsVBox, columnsVBox2;
    @FXML private Label someDataLabel;

    private String tableName;
    private SQLConnection sqlCon;
    private ResultSet resultSet;

    UpdateController(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTableLabel.setText("Update " + tableName + "\nset");
        sqlCon = new SQLConnection();
        sqlCon.setConnection();
        resultSet = (sqlCon.getResultSetOfColumns(tableName));
        addColumnsToVBox(sqlCon.getColumnNames(tableName));
    }

    private void addColumnsToVBox(List<String> columns) {
        TextField[] columnTextFields = new TextField[columns.size()];
        TextField[] columnTextFields2 = new TextField[columns.size()];
        for(int i=0; i<columns.size(); i++) {
            columnTextFields[i] = new TextField();
            columnTextFields[i].setPromptText(columns.get(i));

            columnTextFields2[i] = new TextField();
            columnTextFields2[i].setPromptText(columns.get(i));

            columnsVBox.getChildren().add(columnTextFields[i]);
            columnsVBox2.getChildren().add(columnTextFields2[i]);
        }
    }

    @FXML private void onUpdateButton() {
        StringBuilder stringBuilder = new StringBuilder("Update " + tableName + " set ");

        getStatementPartFromVBoxColumns(stringBuilder, columnsVBox);


        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(" where ");
        getStatementPartFromVBoxColumns(stringBuilder, columnsVBox2);

        stringBuilder.setLength(stringBuilder.length() - 2);
        if(!stringBuilder.toString().contains("where")) stringBuilder.setLength(stringBuilder.length() - 5);
        String statement = stringBuilder.toString();
        if(statement.contains("=") && statement.contains("set")) {
            boolean isStatementCorrect = sqlCon.DMLQuery(statement);
            if(isStatementCorrect) {
                someDataLabel.setTextFill(Color.GREEN);
                someDataLabel.setText("Data updated successfully.");
            }
            else {
                someDataLabel.setTextFill(Color.RED);
                someDataLabel.setText("Inserted incorrect data");
            }
        }
        else {
            someDataLabel.setTextFill(Color.RED);
            someDataLabel.setText("Please insert missing data");
        }
    }

    private void getStatementPartFromVBoxColumns(StringBuilder stringBuilder, VBox columnsVBox) {
        boolean[] varCharColumns = Utilities.isVarChar(resultSet);
        int i = 0;
        for(Node columnName : columnsVBox.getChildren()) {
            if(columnName instanceof TextField) {
                if( ((TextField) columnName).getText().equals("")) stringBuilder.append("");
                else if(varCharColumns[i]){
                    stringBuilder.append(( (TextField) columnName).getPromptText()).append(" = ");
                    stringBuilder.append('\'').append(( (TextField) columnName).getText()).append('\'');
                    stringBuilder.append(", ");
                }
                else {
                    stringBuilder.append(( (TextField) columnName).getPromptText()).append(" = ");
                    stringBuilder.append(( (TextField) columnName).getText());
                    stringBuilder.append(", ");
                }
            }
            i++;
        }
    }
}
