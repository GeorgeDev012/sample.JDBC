import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLConnection {
    private Connection con;
    private Statement st;
    private ResultSet rs;

    void getConnection() {

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@155.158.112.45:1521:oltpstud","ziibd38","haslo2018");


            st = con.prepareStatement("Select * from employees");
            rs = ((PreparedStatement) st).executeQuery();

            rs.next();
            System.out.println(rs.getString(2) + " " + rs.getString(3));
        }catch(Exception ex){
            System.out.println("Error" + ex);
        }
    }

    ObservableList<String> getTablesName() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            st = con.prepareStatement("select table_name from user_tables");
            rs = ((PreparedStatement) st).executeQuery();
            while(rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public String[][] getContentFromDatabase(String statement) {
        String[][] strings = null;
        try {
            st = con.prepareStatement(statement);
            rs = ((PreparedStatement) st).executeQuery();

            int i = 0;
            int j = 0;
            while(rs.next()) {
                strings[i][j] = new String();
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strings;
    }

    TableColumn[] getColumns() {
        ResultSetMetaData rsmd = null;
        TableColumn[] tableColumns = null;
        try {
            rsmd = rs.getMetaData();
            tableColumns = new TableColumn[rsmd.getColumnCount()];
            for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                tableColumns[i-1] = new TableColumn(rsmd.getColumnName(i));
                System.out.println(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableColumns;
    }
}
