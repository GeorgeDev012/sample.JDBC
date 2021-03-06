import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class SQLConnection {
    private ObservableList<ObservableList> data;
    private Statement st;
    private ResultSetMetaData rsmd;
    private static int rowCount;
    private static Connection con;
    private ResultSet rs;


    static Connection getConnection() {
        if(con == null) setConnection();
        return con;
    }

    static void setConnection() {
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

    ResultSet getResultSetOfColumns(String tableName) {
        ResultSet resultSet = null;
        try {
            resultSet = con.createStatement().executeQuery("select * from " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    String[][] getQueryResults(String statement) {
        String[][] queryResults = new String[rowCount][];
        try {
            setConnection();
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

    int getRowCount(String tableName) {
        int rowCount = 0;
        try {

            rs = con.createStatement().executeQuery("select count(1) from " + tableName);
            rs.next();
            rowCount = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowCount;
    }

    boolean DMLQuery(String statement) {
        try {
            con.createStatement().executeQuery(statement);

            return true;
        } catch(SQLException e) {
            //e.printStackTrace();
            //System.out.println("Incorrect SQL statement");
            return false;
        }

    }


}
