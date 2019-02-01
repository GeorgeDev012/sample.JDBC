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
    @FXML public ChoiceBox<String> tableChoiceBox;

    private SQLConnection sqlCon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sqlCon = new SQLConnection();
        sqlCon.setConnection();
        ObservableList<String> abc = sqlCon.getTablesNames();
        tableChoiceBox.setItems(abc);
    }

    public void chooseButtonAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane root2 = null;
        try {
            root2 = fxmlLoader.load(getClass().getResource("SelectView.fxml").openStream());

            Scene scene2 = new Scene(root2);
            if(tableChoiceBox.getValue() != null) {
                SelectController selectController = fxmlLoader.getController();
                String choiceBoxValue = tableChoiceBox.getValue().toString();
                selectController.setTableLabelText(choiceBoxValue);
                List<String> list = sqlCon.getColumnNames(choiceBoxValue);
                String[] columnNames =  list.toArray(new String[list.size()]);
                MainController.columnCheckBoxes = setCheckBoxesLayout(Utilities.getCheckBoxes(columnNames));
                root2.getChildren().addAll(MainController.columnCheckBoxes);
                //System.out.println(choiceBox2.getValue());
                MainController.selectStage.setScene(scene2);
                tableChoiceBox.setValue(null);
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
