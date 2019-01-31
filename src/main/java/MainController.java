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
    @FXML private ChoiceBox choiceBox1;
    @FXML private Button button;
    @FXML private ChoiceBox choiceBox2;
    @FXML private TableView tableView1;
    SQLConnection sqlCon;
    private Connection con;
    private ObservableList<ObservableList> data;
    private Statement st;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private int rowCount;
    private boolean isOpen = false;

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
        //sqlCon.setRowCount(choiceBox2.getValue() + "");
        populateTableView(statement);
    }

    private void populateTableView(String statement) {
        TableColumn[] tableColumns = null;
        try {
            con = SQLConnection.getConnection();
            rs = con.createStatement().executeQuery(statement);
            data = FXCollections.observableArrayList();
            rsmd = rs.getMetaData();
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
                System.out.println(row);
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableView1.setItems(data);
    }

    @FXML void selectMenu() {
        try {
            if(!isOpen) {
                VBox root2 = new VBox();
                root2.setAlignment(Pos.CENTER);
                Button chooseButton = new Button("Submit");
                Label chooseLabel = new Label("Choose Table");
                chooseLabel.setFont(new Font(24));
                root2.getChildren().addAll(chooseLabel, choiceBox2, chooseButton);
                Stage stage = new Stage();
                Scene scene = new Scene(root2);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.setOnCloseRequest(e -> {
                    isOpen = false;
                });
                isOpen = true;

                FXMLLoader fxmlLoader = new FXMLLoader();
                AnchorPane root = fxmlLoader.load(getClass().getResource("SelectView.fxml").openStream());
                Scene scene2 = new Scene(root);
                chooseButton.setOnAction(e -> {
                    if(choiceBox2.getValue() != null) {
                        SelectController selectController = fxmlLoader.getController();
                        String choiceBoxValue = choiceBox2.getValue().toString();
                        selectController.setTableLabelText(choiceBoxValue);
                        List<String> list = sqlCon.getColumnNames(choiceBoxValue);
                        String[] columnNames =  list.toArray(new String[list.size()]);
                        CheckBox[] columnCheckBoxes = setCheckBoxesLayout(Utilities.getCheckBoxes(columnNames));
                        root.getChildren().addAll(columnCheckBoxes);
                        //System.out.println(choiceBox2.getValue());
                        stage.setScene(scene2);
                        choiceBox2.setValue(null);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private CheckBox[] setCheckBoxesLayout(CheckBox[] checkBoxes) {
        for(int i=0; i<checkBoxes.length; i++) {
            checkBoxes[i].setLayoutX(
                    (221 - 14.0)/2);
            checkBoxes[i].setLayoutY(18 + i*30);
        }
        return checkBoxes;
    }

    @FXML void insertMenu() {

    }

    @FXML void updateMenu() {

    }

    @FXML void deleteMenu() {

    }






}

