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

public class DeleteController implements Initializable {

    @FXML private Label deleteLabel;
    @FXML private VBox columnsVBox;
    @FXML private Label confirmationLabel;

    private SQLConnection sqlCon;
    private ResultSet resultSet;
    private String tableName;

    DeleteController(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deleteLabel.setText("Delete from " + tableName + "\nwhere");
        sqlCon = new SQLConnection();
        SQLConnection.setConnection();
        resultSet = (sqlCon.getResultSetOfColumns(tableName));
        addColumnsToVBox(sqlCon.getColumnNames(tableName));
    }

    private void addColumnsToVBox(List<String> columns) {
        TextField[] columnTextFields = new TextField[columns.size()];
        for(int i=0; i<columns.size(); i++) {
            columnTextFields[i] = new TextField();
            columnTextFields[i].setPromptText(columns.get(i));
            columnsVBox.getChildren().add(columnTextFields[i]);
        }
    }

    @FXML private void onDeleteButton() {
        StringBuilder stringBuilder = new StringBuilder("Delete from " + tableName + " where ");

        getStatementPartFromVBoxColumns(stringBuilder, columnsVBox);

        stringBuilder.setLength(stringBuilder.length() - 4);
        String statement = stringBuilder.toString();
        if(!statement.contains("where")) stringBuilder.setLength(stringBuilder.length() - 2);

        if(sqlCon.DMLQuery(statement)) {
            confirmationLabel.setTextFill(Color.GREEN);
            confirmationLabel.setText("Row(s) successfully deleted.");
        }
        else {
            confirmationLabel.setTextFill(Color.RED);
            confirmationLabel.setText("Inserted incorrect data.");
        }
    }

    private void getStatementPartFromVBoxColumns(StringBuilder stringBuilder, VBox columnsVBox) {
        boolean[] varCharColumns = Utilities.isVarChar(resultSet);
        int i = 0;
        for(Node columnName : columnsVBox.getChildren()) {
            if (!(( (TextField) columnName).getText().equals(""))) {
                if (varCharColumns[i]) {
                    stringBuilder.append(((TextField) columnName).getPromptText()).append(" = ");
                    stringBuilder.append('\'').append(((TextField) columnName).getText()).append('\'');
                    stringBuilder.append(" and ");
                }
                else {
                    stringBuilder.append(((TextField) columnName).getPromptText()).append(" = ");
                    stringBuilder.append(((TextField) columnName).getText());
                    stringBuilder.append(" and ");
                }
            }
            i++;
        }
    }
}
