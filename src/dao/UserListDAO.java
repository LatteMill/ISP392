/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.UserListController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.UserList;

/**
 *
 * @author admin
 */
public class UserListDAO {

    public List<UserList> getList() throws Exception {
        List<UserList> lstResult = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select * FROM user";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);

            while (rs.next()) {
                UserList ur = new UserList();
                ur.setId(rs.getInt(1));
                ur.setEmail(rs.getString(2));
                ur.setFullName(rs.getString(6));
                ur.setRollNumber(rs.getString(7));
                ur.setMobile(rs.getString(8));
                ur.setSex(rs.getInt(9));
                ur.setRoles(rs.getInt(10));
                ur.setStatus(rs.getInt(11));
                ur.setJobPosition(rs.getString(12));
                ur.setCompany(rs.getString(13));
                ur.setAddress(rs.getString(14));
                ur.setLastLogin(rs.getString(15));
                lstResult.add(ur);
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

//    public boolean addNew(UserList stl) {
//        return true;
//    }
    public void insertUserListToList(UserList ur) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into user ( email, fullName, rollNumber, mobile, sex, roles, status, jobPosition, company,address,last_login)"
                + " values ( '" + ur.getEmail() + "', "
                + " '" + ur.getFullName() + "', "
                + " '" + ur.getRollNumber() + "', "
                + " '" + ur.getMobile() + "', "
                + " " + ur.getSex() + ", "
                + " " + ur.getRoles() + ", "
                + " " + ur.getStatus() + ", "
                + "'" + ur.getJobPosition() + "', "
                + " '" + ur.getCompany() + "', "
                + " '" + ur.getAddress() + "', "
                + " '" + ur.getLastLogin() + "'); ";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);
        connection.close();
        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void deleteUserToUserList(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM user "
                + "WHERE id = ?";
        try (Connection connection = CommonLib.getDbConn();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Record deleted successfully");
        } catch (SQLException e) {
        }
    }

    public void updateUserListToList(UserList ur, String tencot, String giatriUpdate, String kieudulieu) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "";
        if (kieudulieu.compareToIgnoreCase("string") == 0) {
            sql = "UPDATE user SET " + tencot + " = '" + giatriUpdate + "' WHERE id = " + ur.getId()+ ";";
        }

        if (kieudulieu.compareToIgnoreCase("boolean") == 0 || kieudulieu.compareToIgnoreCase("int") == 0) {
            sql = "UPDATE user SET " + tencot + " = " + giatriUpdate + " WHERE id = " + ur.getId()+ ";";
        }
//        if( kieudulieu.compareToIgnoreCase("date")==0){
//            Date validValueDate = Utility.convert(giatriUpdate);
//            sql = "update Milestone SET " + tencot +" = '" + Utility.convertDateToStringtoInsert(validValueDate) + "' where milestone_id = " + sb.getMileStone_id()+ ";";
//        }

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }
    public UserList getUserWithCondition(String email) throws Exception {
        UserList uResult = new UserList();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select * FROM user where email= '"+email+"';";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);

            while (rs.next()) {
                UserList ur = new UserList();
                ur.setId(rs.getInt(1));
                ur.setEmail(rs.getString(2));
                ur.setFullName(rs.getString(6));
                ur.setRollNumber(rs.getString(7));
                ur.setMobile(rs.getString(8));
                ur.setSex(rs.getInt(9));
                ur.setRoles(rs.getInt(10));
                ur.setStatus(rs.getInt(11));
                ur.setJobPosition(rs.getString(12));
                ur.setCompany(rs.getString(13));
                ur.setAddress(rs.getString(14));
                ur.setLastLogin(rs.getString(15));
//                lstResult.add(ur);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return uResult;
    }
}
