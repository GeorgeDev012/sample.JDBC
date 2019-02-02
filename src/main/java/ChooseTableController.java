import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import org.hibernate.sql.Select;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseTableController implements Initializable {
    @FXML public ChoiceBox<String> tableNameChoiceBox;

    private SQLConnection sqlCon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sqlCon = new SQLConnection();
        sqlCon.setConnection();
        ObservableList<String> abc = sqlCon.getTablesNames();
        System.out.println(tableNameChoiceBox + " " + abc);
        tableNameChoiceBox.setItems(abc);
    }

    @FXML public void chooseButtonAction(ActionEvent actionEvent) {
        switch(MainController.menuChoice) {
            case 1: selectMenuItem();
            break;
            case 2: insertMenuItem();
            break;
//            case 3: updateMenuItem();
//            break;
//            case 4: deleteMenuItem();
//            break;
        }
    }

    private void selectMenuItem() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {

            AnchorPane root;
            if(tableNameChoiceBox.getValue() != null) {
                root = fxmlLoader.load(getClass().getResource("SelectView.fxml").openStream());

                Scene scene2 = new Scene(root);
                SelectController selectController = fxmlLoader.getController();
                String choiceBoxValue = tableNameChoiceBox.getValue();
                selectController.setTableLabelText(choiceBoxValue);
                List<String> list = sqlCon.getColumnNames(choiceBoxValue);
                String[] columnNames =  list.toArray(new String[0]);
                MainController.columnCheckBoxes = setCheckBoxesLayout(Utilities.getCheckBoxes(columnNames));
                root.getChildren().addAll(MainController.columnCheckBoxes);
                MainController.secondStage.setScene(scene2);
                tableNameChoiceBox.setValue(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertMenuItem() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane root;
        try {
            if(tableNameChoiceBox.getValue() != null) {
                InsertController insertController = new InsertController(tableNameChoiceBox.getValue());
                fxmlLoader.setController(insertController);
                root = fxmlLoader.load(getClass().getResource("InsertView.fxml").openStream());
                Scene scene2 = new Scene(root);

                //InsertController insertController = fxmlLoader.getController();
                //insertController.setTableName(tableNameChoiceBox.getValue());
                MainController.secondStage.setScene(scene2);
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
}
