/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.TeamController;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Feature;
import model.ClassUser;

/**
 *
 * @author Admin
 */
public class FeatureDAO {
      public List<Feature> getFeatList() throws Exception {

        List<Feature> featList = new ArrayList<>();

        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select * from feature;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//int feat_id, String name,int  team_id, boolean status
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Feature feat = new Feature();
                feat.setFeature_id(rs.getInt(1));
                feat.setFeature_name(rs.getString(2));
                feat.setTeam_id(rs.getInt(3));              
                feat.setStatus(rs.getInt(4));
                featList.add(feat);
            }
            // Đóng kết nối

            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return featList;
    }

    public void insertFeatToList(Feature feat) throws ClassNotFoundException,

            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into feature (feature_name,team_id, status)"
                + " values ( '" + feat.getFeature_name() + "', "
                + " " + feat.getTeam_id() + ", "              
                + " " + feat.isStatus() + "); ";


        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

 public void updateFeatureToList(Feature feat, String tencot, String giatriUpdate, String kieudulieu) throws ClassNotFoundException,

            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "";

        if (kieudulieu.compareToIgnoreCase("string") == 0) {
            sql = "UPDATE feature SET " + tencot + " = '" + giatriUpdate + "' WHERE feature_id = " + feat.getFeature_id()+ ";";
        }

        if (kieudulieu.compareToIgnoreCase("boolean") == 0 || kieudulieu.compareToIgnoreCase("int") == 0) {
            sql = "UPDATE feature SET " + tencot + " = " + giatriUpdate + " WHERE feature_id = " + feat.getFeature_id()+ ";";
        }
        //        if( kieudulieu.compareToIgnoreCase("date")==0){
//            Date validValueDate = Utility.convert(giatriUpdate);
//            sql = "update milestone SET " + tencot +" = '" + Utility.convertDateToStringtoInsert(validValueDate) + "' where milestone_id = " + sb.getMileStone_id()+ ";";
//        }
        

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);

 }
 //show team
 public List<Feature> getListWithOneCondition(String dieukien) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<Feature> sbList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = "Select * from feature where " + dieukien + " ;";

                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    Feature feat = new Feature();
                feat.setFeature_id(rs.getInt(1));
                feat.setFeature_name(rs.getString(2));
                feat.setTeam_id(rs.getInt(3));              
                feat.setStatus(rs.getInt(4));
                sbList.add(feat);
                }
                // Đóng kết nối
                conn.close();
            } catch (SQLException exp) {
                System.out.println("Exception: " + exp.getMessage());
            } catch (ClassNotFoundException exp) {
                System.out.println("Can't connect to DB: " + exp.getMessage());
            }
            return sbList;
        }
    }
}

