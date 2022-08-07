package dao;

import java.sql.*;
import java.util.*;
import model.*;
import java.security.*;
import javax.xml.bind.DatatypeConverter;

public class SubjectSettingDAO {

    public List<SubjectSetting> getList() throws Exception {
        List<SubjectSetting> subjectSettingList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "select a.setting_id, a.subject_id, a.type_id, a.setting_title, a.setting_value, a.display_order, a.status,\n"
                    + " b.subject_code from subject_setting as a join subject as b on a.subject_id = b.subject_id order by a.setting_id;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                SubjectSetting subjectSetting = new SubjectSetting();
                subjectSetting.setSetting_id(rs.getInt(1));
                subjectSetting.setSubjectID(rs.getInt(2));
                subjectSetting.setType_id(rs.getInt(3));
                subjectSetting.setSetting_title(rs.getString(4));
                subjectSetting.setSetting_value(rs.getInt(5));
                subjectSetting.setDisplay_order(rs.getInt(6));
                subjectSetting.setSubject_setting_status(rs.getBoolean(7));
                subjectSetting.setSubjectCode(rs.getString(8));

                subjectSettingList.add(subjectSetting);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return subjectSettingList;
    }

    public void insertSubjectSettingToList(SubjectSetting subjectSetting) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into subject_setting ( subject_id , type_id, setting_title, setting_value, display_order, status)"
                + " values ( " + subjectSetting.getSubjectID() + ", "
                + " " + subjectSetting.getType_id() + ", "
                + " '" + subjectSetting.getSetting_title() + "', "
                + " '" + subjectSetting.getSetting_value() + "', "
                + " " + subjectSetting.getDisplay_order() + ", "
                + " " + subjectSetting.isSubject_setting_status() + "); ";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateSubjectSettingToList(SubjectSetting subjectSetting) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "UPDATE subject_setting SET subject_id = " + subjectSetting.getSubjectID() + ", "
                + "type_id = " + subjectSetting.getType_id() + ", "
                + "setting_title = '" + subjectSetting.getSetting_title() + "', "
                + "setting_value = " + subjectSetting.getSetting_value() + ", "
                + "display_order = " + subjectSetting.getDisplay_order() + ", "
                + "status = " + subjectSetting.isSubject_setting_status() + " "
                + "where setting_id = " + subjectSetting.getSetting_id() + ";";

    //    System.out.println(subjectSetting);
        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public List<SubjectSetting> getSearchList(String pattern) throws Exception {
        List<SubjectSetting> subjectSettingList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "select a.setting_id, a.subject_id, a.type_id, a.setting_title, a.setting_value, a.display_order, a.status,\n"
                    + " b.subject_code from subject_setting as a join subject as b on a.subject_id = b.subject_id "
                    + "where  a.setting_id like '%" + pattern + "%' or "
                    + "a.subject_id like '%" + pattern + "%' or "
                    + "a.type_id like '%" + pattern + "%' or "
                    + "a.setting_title like '%" + pattern + "%' or "
                    + "a.setting_value like '%" + pattern + "%' or "
                    + "a.display_order like '%" + pattern + "%' or "
                    + "a.status like '%" + pattern + "%' order by a.setting_id;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                SubjectSetting subjectSetting = new SubjectSetting();
                subjectSetting.setSetting_id(rs.getInt(1));
                subjectSetting.setSubjectID(rs.getInt(2));
                subjectSetting.setType_id(rs.getInt(3));
                subjectSetting.setSetting_title(rs.getString(4));
                subjectSetting.setSetting_value(rs.getInt(5));
                subjectSetting.setDisplay_order(rs.getInt(6));
                subjectSetting.setSubject_setting_status(rs.getBoolean(7));
                subjectSetting.setSubjectCode(rs.getString(8));

                subjectSettingList.add(subjectSetting);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return subjectSettingList;
    }

}
