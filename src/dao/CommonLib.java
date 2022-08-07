/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CommonLib {

    public static Connection getDbConn() throws SQLException,
            ClassNotFoundException {
        String hostName = "localhost";
//        String hostName= "127.0.0.1";

        String dbName = "manageuser";

        String userName = "root";

      String password = "studentfu";
  //      String password = "123456";


        Class.forName("com.mysql.cj.jdbc.Driver");

        String connURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        Connection conn = DriverManager.getConnection(connURL, userName, password);
        return conn;
    }

    //
    // Test Connection ...
    //
    public static void main(String[] args) throws SQLException,
            ClassNotFoundException {

        System.out.println("Get connection ... ");

        // Lấy ra đối tượng Connection kết nối vào database.
        Connection conn = CommonLib.getDbConn();

        System.out.println("Get connection " + conn);

        System.out.println("Done!");
    }

}
