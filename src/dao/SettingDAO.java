/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tstllate file, choose Tools | Tstllates
 * and open the tstllate in the editor.
 */
package dao;

import java.sql.*;
import java.util.*;
import model.*;

/**
 *
 * @author admin
 */
public class SettingDAO {

    public List<SettingList> getList() throws Exception {
        List<SettingList> lstResult = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select * from setting";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                SettingList stl = new SettingList();
                stl.setId(rs.getInt(1));
                stl.setSettingType(rs.getInt(2));
                stl.setNameString(rs.getString(3));
                stl.setValueString(rs.getString(4));
                stl.setOrder(rs.getInt(5));
                stl.setStatus(rs.getInt(6));
                lstResult.add(stl);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return lstResult;
    }

    public void insertSettingToList(SettingList sb) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into setting(type_id, setting_title, setting_value, display_order, status)"
                + "values("
                + " " + sb.getSettingType() + ","
                + "'" + sb.getNameString() + "',"
                + "'" + sb.getValueString() + "',"
                + "'" + sb.getOrder() + "',"
                + " " + sb.getStatus() + ");";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        connection.close();
        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateSettingToList(SettingList sb) throws ClassNotFoundException,
            SQLException {
        String sql = "UPDATE setting SET "
                + "type_id= ?, "
                + "setting_title=?,"
                + "setting_value=?,"
                + "display_order=?,"
                + "status=? "
                + "WHERE setting_id=?;";
        // Lấy ra kết nối tới cơ sở dữ liệu.
        try (
                Connection connection = CommonLib.getDbConn();
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, sb.getSettingType());
            ps.setString(2, sb.getNameString());
            ps.setString(3, sb.getValueString());
            ps.setInt(4, sb.getOrder());
            ps.setInt(5, sb.getStatus());
            ps.setInt(6, sb.getId());

            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    //ham cua Linh cho subject setting: ( them static neu can test )
    public List<SettingList> getType_idList() throws Exception {
        List<SettingList> setlist = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            strSQL = "Select distinct type_id from setting order by type_id ;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                SettingList s = new SettingList();
                s.setSettingType(rs.getInt(1));
                setlist.add(s);

//                System.out.println("vua add: ");
//                System.out.println(s);    
//                
//                System.out.println("current list:");
//                for (int i = 0; i < setlist.size(); i++) {
//                    System.out.println(setlist.get(i));
//                }
//                System.out.println("");
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return setlist;

    }


}
