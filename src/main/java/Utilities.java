import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.sql.SQLException;

final public class Utilities {

    private Utilities() {}

    static CheckBox[] getCheckBoxes(String[] textData) {
        CheckBox[] checkBoxes = new CheckBox[textData.length];
        for(int i = 0; i < textData.length; i++) {
            checkBoxes[i] = new CheckBox();
            checkBoxes[i].setText(textData[i]);
        }
        return checkBoxes;
    }

    static boolean[] isVarChar(ResultSet resultSet) {
        boolean[] isVarChar = null;
        try {
            isVarChar = new boolean[resultSet.getMetaData().getColumnCount()];
            for(int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                isVarChar[i] = resultSet.getMetaData().getColumnClassName(i + 1).equals("java.lang.String");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isVarChar;
    }


}
