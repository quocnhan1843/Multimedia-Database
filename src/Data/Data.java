/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author quocn
 */
public class Data {
    private static Connection con = null;

    private static void createData(String nameDatabase) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectString = "jdbc:mysql://localhost:3306/"+ nameDatabase 
                    + "?user=root&password=&characterEncoding=utf-8&useUnicode=true";
            con = DriverManager.getConnection(connectString);
            //System.out.println("Ket noi OK");
        } catch (ClassNotFoundException ex) {
            //System.out.println("Không tìm thấy driver");
        } catch (SQLException ex) {
            //System.out.println("Không kết nối được cơ sở dữ liệu!");
        }
    }
    public static synchronized void createConnection(String nameDatabase){
        createData(nameDatabase);
    }
    public static ResultSet getResultsetQuery(String sql, String nameDatabase) throws Exception{
        createConnection(nameDatabase);
        Statement cmd = con.createStatement();
        return cmd.executeQuery(sql);        
    }
    public static void setResultsetUpdate(String sql, String nameDatabase) throws SQLException{
        createConnection(nameDatabase);
        Statement cmd = con.createStatement();
        cmd.executeUpdate(sql);
    }
    public static void createDatabase(String databaseName){
        try { 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=");
            Statement statement = conn.createStatement();
            int Result=statement.executeUpdate("CREATE DATABASE " + databaseName);
        } catch (SQLException ex) {
            
        }
    }
    
    public static void printSQL(String sql){
        System.out.println("Data Printer");
        System.out.println(sql);
    }
}
