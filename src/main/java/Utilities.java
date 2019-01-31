import javafx.scene.control.CheckBox;

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


}
