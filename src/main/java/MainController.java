import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private TableView tableView1;

    private SQLConnection sqlCon;
    private ObservableList<ObservableList> data;
    boolean isOpen = false;
    static CheckBox[] columnCheckBoxes;
    static Stage selectStage;
    static byte menuChoice;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sqlCon = new SQLConnection();
        sqlCon.setConnection();
        selectStage = new Stage();
    }

     void populateTableView(String statement) {
        TableColumn[] tableColumns = null;
        try {
            ResultSet rs = sqlCon.getConnection().createStatement().executeQuery(statement);

            data = FXCollections.observableArrayList();
            ResultSetMetaData rsmd = rs.getMetaData();
            tableColumns = new TableColumn[rsmd.getColumnCount()];
            tableView1.getColumns().clear();
            for(int i = 0; i < rsmd.getColumnCount(); i++) {
                final int j = i;
                tableColumns[i] = new TableColumn(rsmd.getColumnName(i+1));
                tableColumns[i].setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                    if (param.getValue().get(j) != null) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    } else {
                        return null;
                    }
                });

                tableView1.getColumns().addAll(tableColumns[i]);
            }

            while(rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableView1.setItems(data);
    }

    @FXML void selectMenu() {
        loadChoiceView();
        menuChoice = 1;

    }

    static CheckBox[] getColumnCheckBoxes() {
        return columnCheckBoxes;
    }

    @FXML void insertMenu() {
        loadChoiceView();
        menuChoice = 2;
    }

    @FXML void updateMenu() {
        loadChoiceView();
        menuChoice = 3;
    }

    @FXML void deleteMenu() {
        loadChoiceView();
        menuChoice = 4;
    }

    private void loadChoiceView() {
        try {
            if(!isOpen) {
                VBox root = FXMLLoader.load(getClass().getResource("chooseTableView.fxml"));
                Scene scene = new Scene(root);
                selectStage.setScene(scene);
                selectStage.setResizable(false);
                selectStage.show();
                selectStage.setOnCloseRequest(e -> {
                    isOpen = false;
                });

                isOpen = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}

