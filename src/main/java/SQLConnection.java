import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import oracle.jdbc.OracleTranslatingConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLConnection {
    private ObservableList<ObservableList> data;
    private Statement st;
    private ResultSetMetaData rsmd;
    private static int rowCount;
    private static Connection con;
    private ResultSet rs;


    static Connection getConnection() {
        try{
            if(con == null) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection(
                        "jdbc:oracle:thin:@155.158.112.45:1521:oltpstud",
                        "ziibd38","haslo2018");;
            }
        }catch(Exception ex){
            System.out.println("Error" + ex);
        }
        return con;
    }

    void setConnection() {
        try {
            if(con == null) con = DriverManager.getConnection("jdbc:oracle:thin:@155.158.112.45:1521:oltpstud","ziibd38","haslo2018");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ObservableList<String> getTablesNames() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            setConnection();
            rs = con.createStatement().executeQuery("select table_name from user_tables");
            while(rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    ObservableList<String> getColumnNames(String tableName) {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            setConnection();
            rs = con.createStatement().executeQuery("select column_name from user_tab_columns where table_name = '" + tableName + "'");
            while(rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    static String[][] getQueryResults(String statement) {
        String[][] queryResults = new String[rowCount][];
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@155.158.112.45:1521:oltpstud","ziibd38","haslo2018");
            ResultSet rs = con.createStatement().executeQuery(statement);
            int j = 0;
            while(rs.next()) {
                for(int i=0; i<rs.getMetaData().getColumnCount(); i++) {
                    queryResults[j][i] = rs.getString(i+1);
                }
                j++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queryResults;
    }

    void setRowCount(String tableName) {
        int rowCount = 0;
        try {
            rs = con.createStatement().executeQuery("select count(1) from " + tableName);
            rs.next();
            rowCount = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLConnection.rowCount = rowCount;
    }

    public String[][] getContentFromDatabase(String statement) {
        String[][] strings = null;
        try {
            st = con.prepareStatement(statement);
            rs = ((PreparedStatement) st).executeQuery();
            rsmd = rs.getMetaData();
            strings = new String[rowCount][];


            int j = 0;;
            while(rs.next()) {
                String[] strings2 = new String[rowCount];
                for(int i = 1; i <= rsmd.getColumnCount(); i++) {;

                    strings2[i-1] = rs.getString(i);
                }
                strings[j] = strings2;
                j++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strings;
    }



}
