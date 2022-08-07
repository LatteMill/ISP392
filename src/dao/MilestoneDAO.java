package dao;

import java.sql.*;
import java.util.*;
import model.Milestone;
import view.Utility;

public class MilestoneDAO {

    public List<Milestone> getList() throws Exception {
        List<Milestone> milestoneList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select a.milestone_id, a.iteration_id, a.class_id, "
                    + "DATE_FORMAT(a.from_date,'%d/%m/%Y') as from_date,  "
                    + "DATE_FORMAT(a.to_date,'%d/%m/%Y') to_date, a.status, "
                    + "a.milestone_name, "
                    + "b.class_code, "
                    + "c.iteration_name "
                    + "from milestone as a join class as b on a.class_id = b.class_id "
                    + "join iteration as c on a.iteration_id = c.iteration_id order by a.milestone_id;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Milestone milestone = new Milestone();
                milestone.setMileStone_id(rs.getInt(1));
                milestone.setIteration_id(rs.getInt(2));
                milestone.setClass_id(rs.getInt(3));
                milestone.setFrom_date(Utility.convertStringToDate(rs.getString(4)));
                milestone.setTo_date(Utility.convertStringToDate(rs.getString(5)));
                milestone.setStatus(rs.getInt(6));
                milestone.setMilestoneName(rs.getString(7));
                milestone.setClassname(rs.getString(8));
                milestone.setIterationName(rs.getString(9));

                milestoneList.add(milestone);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return milestoneList;
    }

    public void insertMilestoneToList(Milestone milestone) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into milestone(milestone_name,iteration_id, class_id, from_date, to_date, status)"
                + "values('" + milestone.getMilestoneName() + "', "
                + " " + milestone.getIteration_id() + ","
                + " " + milestone.getClass_id() + ","
                + "'" + Utility.convertDateToStringtoInsert(milestone.getFrom_date()) + "',"
                + "'" + Utility.convertDateToStringtoInsert(milestone.getTo_date()) + "',"
                + " " + milestone.getStatus() + ");";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateMilestoneToList(Milestone milestone) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "UPDATE milestone SET milestone_name = '" + milestone.getMilestoneName() + "', "
                + " iteration_id = " + milestone.getIteration_id()
                + ", class_id= " + milestone.getClass_id()
                + ", from_date= '" + Utility.convertDateToStringtoInsert(milestone.getFrom_date()) + "' "
                + ", to_date= '" + Utility.convertDateToStringtoInsert(milestone.getTo_date()) + "' "
                + ", status = " + milestone.getStatus()
                + " WHERE milestone_id = " + milestone.getMileStone_id() + ";";

//        if( kieudulieu.compareToIgnoreCase("date")==0){
//            Date validValueDate = Utility.convert(giatriUpdate);
//            sql = "update Milestone SET " + tencot +" = '" + Utility.convertDateToStringtoInsert(validValueDate) + "' where milestone_id = " + milestone.getMileStone_id()+ ";";
//        }
        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public List<Milestone> getListWithOneCondition(String dieukien) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<Milestone> milestoneList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = "Select a.milestone_id, a.iteration_id, a.class_id, "
                        + "DATE_FORMAT(a.from_date,'%d/%m/%Y') as from_date,  "
                        + "DATE_FORMAT(a.to_date,'%d/%m/%Y') to_date, a.status, "
                        + "a.milestone_name, "
                        + "b.class_code, "
                        + "c.iteration_name "
                        + "from milestone as a join class as b on a.class_id = b.class_id "
                        + "join iteration as c on a.iteration_id = c.iteration_id where " + dieukien + " ;";

                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    Milestone milestone = new Milestone();
                    milestone.setMileStone_id(rs.getInt(1));
                    milestone.setIteration_id(rs.getInt(2));
                    milestone.setClass_id(rs.getInt(3));
                    milestone.setFrom_date(Utility.convertStringToDate(rs.getString(4)));
                    milestone.setTo_date(Utility.convertStringToDate(rs.getString(5)));
                    milestone.setStatus(rs.getInt(6));
                    milestone.setMilestoneName(rs.getString(7));
                    milestone.setClassname(rs.getString(8));
                    milestone.setIterationName(rs.getString(9));
                    milestoneList.add(milestone);
                }
                // Đóng kết nối
                conn.close();
            } catch (SQLException exp) {
                System.out.println("Exception: " + exp.getMessage());
            } catch (ClassNotFoundException exp) {
                System.out.println("Can't connect to DB: " + exp.getMessage());
            }
            return milestoneList;
        }
    }

    public List<Milestone> getSearchList(String pattern) throws Exception {
        List<Milestone> milestoneList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select a.milestone_id, a.iteration_id, a.class_id, "
                    + "DATE_FORMAT(a.from_date,'%d/%m/%Y') as from_date,  "
                    + "DATE_FORMAT(a.to_date,'%d/%m/%Y') to_date, a.status, "
                    + "a.milestone_name, "
                    + "b.class_code, "
                    + "c.iteration_name "
                    + "from milestone as a join class as b on a.class_id = b.class_id "
                    + "join iteration as c on a.iteration_id = c.iteration_id "
                    + "where a.milestone like '%" + pattern + "%' or "
                    + "a.iteration_id like '%" + pattern + "%' or "
                    + "a.class_id like '%" + pattern + "%' or "
                    + "DATE_FORMAT(a.from_date,'%d/%m/%Y') like '%" + pattern + "%' or "
                    + "DATE_FORMAT(a.to_date,'%d/%m/%Y') like '%" + pattern + "%' or "
                    + "a.milestone_name like '%" + pattern + "%' ;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Milestone milestone = new Milestone();
                milestone.setMileStone_id(rs.getInt(1));
                milestone.setIteration_id(rs.getInt(2));
                milestone.setClass_id(rs.getInt(3));
                milestone.setFrom_date(Utility.convertStringToDate(rs.getString(4)));
                milestone.setTo_date(Utility.convertStringToDate(rs.getString(5)));
                milestone.setStatus(rs.getInt(6));
                milestone.setMilestoneName(rs.getString(7));
                milestone.setClassname(rs.getString(8));
                milestone.setIterationName(rs.getString(9));

                milestoneList.add(milestone);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return milestoneList;

    }
}
