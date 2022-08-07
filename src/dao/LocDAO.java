package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class LocDAO {

    public List<Loc> getList() throws Exception {
        List<Loc> locList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "SELECT * from loc_evaluation;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Loc locs = new Loc();
                locs.setEvaluation_id(rs.getInt(1));
                locs.setEvaluation_time(rs.getDouble(2));
                locs.setEvaluation_note(rs.getString(3));
                locs.setComplexity_id(rs.getInt(4));
                locs.setQuality_id(rs.getInt(5));
                locs.setConverted_loc(rs.getDouble(6));
                locs.setTracking_id(rs.getInt(7));

                locList.add(locs);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return locList;
    }

    public List<Loc> getListForSort(boolean student, int assignee_id, String fieldname) throws Exception {
        List<Loc> locList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            if (!student) {
                strSQL = "SELECT * from loc_evaluation "
                        + "ORDER BY " + fieldname + " ;";
            } else {
                strSQL = "SELECT locs.evaluation_id, locs.evaluation_time, locs.evaluation_note,"
                        + " locs.complexity_id, locs.quality_id, locs.converted_loc,"
                        + " tracks.tracking_id FROM loc_evaluation as locs "
                        + " join tracking as tracks"
                        + " on locs.tracking_id = tracks.tracking_id "
                        + "where tracks.assignee_id = " + assignee_id + " ORDER BY " + fieldname + " ;";
            }

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Loc locs = new Loc();
                locs.setEvaluation_id(rs.getInt(1));
                locs.setEvaluation_time(rs.getDouble(2));
                locs.setEvaluation_note(rs.getString(3));
                locs.setComplexity_id(rs.getInt(4));
                locs.setQuality_id(rs.getInt(5));
                locs.setConverted_loc(rs.getDouble(6));
                locs.setTracking_id(rs.getInt(7));

                locList.add(locs);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return locList;
    }

    public static List<Loc> getListForSearch(String pattern, boolean student, int assignee_id) throws Exception {
        List<Loc> locList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            if (!student) {
                strSQL = "SELECT evaluation_id, evaluation_time, evaluation_note, complexity_id, quality_id, converted_loc, "
                        + "tracking_id FROM loc_evaluation "
                        + "where evaluation_time like '%" + pattern + "%' "
                        + "OR evaluation_note like '%" + pattern + "%' "
                        + "OR complexity_id like '%" + pattern + "%' "
                        + "OR quality_id like '%" + pattern + "%' "
                        + "OR converted_loc like '%" + pattern + "%' "
                        + "OR tracking_id like '%" + pattern + "%' ;";
            }else{
                strSQL = "SELECT locs.evaluation_id, locs.evaluation_time, locs.evaluation_note,"
                        + " locs.complexity_id, locs.quality_id, locs.converted_loc,"
                        + " tracks.tracking_id FROM loc_evaluation as locs "
                        + " join tracking as tracks"
                        + " on locs.tracking_id = tracks.tracking_id "
                        + "where (tracks.assignee_id = " + assignee_id + " ) AND (locs.evaluation_note like '% " + pattern + "%' "
                        + "OR locs.complexity_id like '%" + pattern + "%' "
                        + "OR locs.quality_id like '%" + pattern + "%' "
                        + "OR locs.evaluation_time like '%" + pattern + "%' "
                        + "OR locs.converted_loc like '%" + pattern + "%' "
                        + "OR locs.tracking_id like '%" + pattern + "%');";
            }

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet. 
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Loc locs = new Loc();
                locs.setEvaluation_id(rs.getInt(1));
                locs.setEvaluation_time(rs.getDouble(2));
                locs.setEvaluation_note(rs.getString(3));
                locs.setComplexity_id(rs.getInt(4));
                locs.setQuality_id(rs.getInt(5));
                locs.setConverted_loc(rs.getDouble(6));
                locs.setTracking_id(rs.getInt(7));

                locList.add(locs);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return locList;
    }

    public void insertLocToList(Loc locs) throws ClassNotFoundException,
            SQLException,
            Exception {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into loc_evaluation(evaluation_time, evaluation_note, "
                + "complexity_id, quality_id, converted_loc, tracking_id)"
                + " values ( " + locs.getEvaluation_time() + ", "
                + " '" + locs.getEvaluation_note() + "', "
                + " " + locs.getComplexity_id() + ", "
                + " " + locs.getQuality_id() + ", "
                + " " + locs.getConverted_loc() + ", "
                + " " + locs.getTracking_id() + "); ";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateLocToList(Loc locs) throws ClassNotFoundException,
            SQLException,
            Exception {

        String sql = "UPDATE loc_evaluation SET "
                + "evaluation_time =?, " //1
                + "evaluation_note = ?, " //2
                + "complexity_id = ?, " //3
                + "quality_id = ?, " //4
                + "tracking_id = ?, " //5
                + "converted_loc = ? " //6
                + "WHERE evaluation_id = ?;"; //7

        // Lấy ra kết nối tới cơ sở dữ liệu.
        try (
                Connection connection = CommonLib.getDbConn();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //        Statement statement = connection.createStatement();
                ) {
            preparedStatement.setDouble(1, locs.getEvaluation_time());
            preparedStatement.setString(2, locs.getEvaluation_note());
            preparedStatement.setInt(3, locs.getComplexity_id());
            preparedStatement.setInt(4, locs.getQuality_id());
            preparedStatement.setInt(5, locs.getTracking_id());
            preparedStatement.setDouble(6, locs.getConverted_loc());
            preparedStatement.setInt(7, locs.getEvaluation_id());

            preparedStatement.execute();

        }
    }
    //ham cua Linh cho Milestone:

    public List<Loc> getListWithOneCondition(String dieukien) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<Loc> locList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = null;
                strSQL = "SELECT a.evaluation_id, a.evaluation_time, a.evaluation_note, "
                        + "a.complexity_id, a.quality_id, a.converted_loc, b.tracking_id"
                        + " from loc_evaluation as a join tracking as b on a.tracking_id"
                        + " = b.tracking_id where " + dieukien + " ;";

                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    Loc locs = new Loc();
                    locs.setEvaluation_id(rs.getInt(1));
                    locs.setEvaluation_time(rs.getDouble(2));
                    locs.setEvaluation_note(rs.getString(3));
                    locs.setComplexity_id(rs.getInt(4));
                    locs.setQuality_id(rs.getInt(5));
                    locs.setConverted_loc(rs.getDouble(6));
                    locs.setTracking_id(rs.getInt(7));

                    locList.add(locs);
                }
                // Đóng kết nối
                conn.close();
            } catch (SQLException exp) {
                System.out.println("Exception: " + exp.getMessage());
            } catch (ClassNotFoundException exp) {
                System.out.println("Can't connect to DB: " + exp.getMessage());
            }
            return locList;
        }
    }

    //note ( không dùng do chưa tối ưu hóa được) 
    public List<Loc> getPagination(int start) throws Exception {
        List<Loc> locList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "SELECT * from loc_evaluation "
                    + "LIMIT " + start + ", 5;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Loc locs = new Loc();
                locs.setEvaluation_id(rs.getInt(1));
                locs.setEvaluation_time(rs.getDouble(2));
                locs.setEvaluation_note(rs.getString(3));
                locs.setComplexity_id(rs.getInt(4));
                locs.setQuality_id(rs.getInt(5));
                locs.setConverted_loc(rs.getDouble(6));
                locs.setTracking_id(rs.getInt(7));

                locList.add(locs);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return locList;
    }

}
