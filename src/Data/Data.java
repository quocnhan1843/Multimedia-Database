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

    public Data() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/document_example?user=root&password=&characterEncoding=utf-8&useUnicode=true");
            //System.out.println("Ket noi OK");
        } catch (ClassNotFoundException ex) {
            //System.out.println("Không tìm thấy driver");
        } catch (SQLException ex) {
            //System.out.println("Không kết nối được cơ sở dữ liệu!");
        }
    }
    public static synchronized void createConnection(){
        if(con == null){
            new Data();
        }
    }
    public static ResultSet getResultsetQuery(String sql) throws Exception{
        createConnection();
        Statement cmd = con.createStatement();
        return cmd.executeQuery(sql);        
    }
    public static void getResultsetUpdate(String sql) throws SQLException{
        createConnection();
        Statement cmd = con.createStatement();
        cmd.executeUpdate(sql);
        
    }
}
