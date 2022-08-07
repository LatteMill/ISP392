/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.*;
import java.sql.*;
import java.util.*;
import model.*;
import view.*;

/**
 *
 * @author admin
 */
public class TrackingDAO {
//    public static void main(String[] args) throws Exception {
//        TrackingDAO t= new TrackingDAO();
//        List<Tracking> trackingList = t.getList();
//        for (Tracking tracking : trackingList) {
//            System.out.println(tracking);
//        }
//    }

    public List<Tracking> getList() throws Exception {
        List<Tracking> trackingList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "select t.tracking_id, t.team_id, t.milestone_id, t.function_id, t.assigner_id, "
                    + "us.fullName as assignerName, t.assignee_id, u.fullName as assigneeName, t.tracking_note, t.updates, t.status\n"
                    + "from tracking as t join user as u on t.assignee_id= u.id\n"
                    + "                   join user as us on t.assigner_id= us.id\n"
                    + "			  join functiontable as f on t.function_id=f.function_id;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Tracking sb = new Tracking();
                sb.setTrackingID(rs.getInt(1));
                sb.setTeamID(rs.getInt(2));
                sb.setMilestoneID(rs.getInt(3));
                sb.setFunctionID(rs.getInt(4));
                sb.setAssignerID(rs.getInt(5));
                sb.setAssignerName(rs.getString(6));
                sb.setAssigneeID(rs.getInt(7));
                sb.setAssigneeName(rs.getString(8));
                sb.setTrackingNote(rs.getString(9));
                sb.setUpdate(rs.getBoolean(10));
                sb.setStatus(rs.getInt(11));
                trackingList.add(sb);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return trackingList;
    }

    public void insertTrackingToList(Tracking sb) throws ClassNotFoundException,
            SQLException {
        ResultSet rs = null;
        int trackingID = 0;
        // Lấy ra kết nối tới cơ sở dữ liệu.
//        Connection connection = CommonLib.getDbConn();
        String sql = "insert into tracking(team_id, milestone_id, function_id, "
                + "assigner_id,assignee_id,tracking_note, updates,  status) "
                + "values(?,?,?,?,?,?,?,?)";
        try (Connection connection = CommonLib.getDbConn();
                PreparedStatement preparedStatementtatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatementtatement.setInt(1, sb.getTeamID());
            preparedStatementtatement.setInt(2, sb.getMilestoneID());
            preparedStatementtatement.setInt(3, sb.getFunctionID());
            preparedStatementtatement.setInt(4, sb.getAssignerID());
            preparedStatementtatement.setInt(5, sb.getAssigneeID());
            preparedStatementtatement.setString(6, sb.getTrackingNote());
            preparedStatementtatement.setBoolean(7, sb.isUpdate());
            preparedStatementtatement.setInt(8, sb.getStatus());
            int rowAffected = preparedStatementtatement.executeUpdate();
            if (rowAffected == 1) {
                // get candidate id
                rs = preparedStatementtatement.getGeneratedKeys();
                if (rs.next()) {
                    trackingID = rs.getInt(1);
                }

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

//        System.out.println(String.format("A new tracking with id %d has been inserted.", trackingID));
    }

    public void updateTrackingToList(Tracking sb) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE tracking SET "
                + "team_id= ?, "
                + "milestone_id= ?, "
                + "function_id=?,"
                + "assigner_id=?,"
                + "assignee_id=?,"
                + "tracking_note=?,"
                + "updates=?,  "
                + "status=? "
                + "WHERE tracking_id=?;";
        // Lấy ra kết nối tới cơ sở dữ liệu.
        try (
                Connection connection = CommonLib.getDbConn();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setInt(1, sb.getTeamID());
            ps.setInt(2, sb.getMilestoneID());
            ps.setInt(3, sb.getFunctionID());
            ps.setInt(4, sb.getAssignerID());
            ps.setInt(5, sb.getAssigneeID());
            ps.setString(6, sb.getTrackingNote());
            ps.setBoolean(7, sb.isUpdate());
            ps.setInt(8, sb.getStatus());
            ps.setInt(9, sb.getTrackingID());
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void deleteTrackingToList(Tracking stl) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM tracking "
                + "WHERE tracking_id = ?";
        try (Connection connection = CommonLib.getDbConn();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, stl.getTrackingID());
            stmt.executeUpdate();
            System.out.println("Record deleted successfully");
        } catch (SQLException e) {
        }
    }

    //Ham cua Phuoc cho Loc
    public List<Tracking> getListWithOneCondition(String dieukien) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<Tracking> teamList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = null;
                strSQL = "Select * from tracking where " + dieukien + " ;";

                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    Tracking sb = new Tracking();
                    sb.setTrackingID(rs.getInt(1));
                    sb.setTeamID(rs.getInt(2));
                    sb.setMilestoneID(rs.getInt(3));
                    sb.setFunctionID(rs.getInt(4));
                    sb.setAssignerID(rs.getInt(5));
                    sb.setAssigneeID(rs.getInt(6));
                    sb.setTrackingNote(rs.getString(7));
                    sb.setUpdate(rs.getBoolean(8));
                    sb.setStatus(rs.getInt(9));
                    teamList.add(sb);
                }
                // Đóng kết nối
                conn.close();
            } catch (SQLException exp) {
                System.out.println("Exception: " + exp.getMessage());
            } catch (ClassNotFoundException exp) {
                System.out.println("Can't connect to DB: " + exp.getMessage());
            }
            return teamList;
        }
    }

    public List<Tracking> getTrackingList(int tracking_id) throws Exception {
        List<Tracking> setList = new ArrayList<>();

        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            strSQL = "select tracking_id, tracking_note, updates, status from tracking";

            ResultSet rs = statement.executeQuery(strSQL);

            while (rs.next()) {
                Tracking cu = new Tracking();
                cu.setTrackingID(rs.getInt(1));
                cu.setTrackingNote(rs.getString(2));
                cu.setUpdate(rs.getBoolean(3));
                cu.setStatus(rs.getInt(4));
                setList.add(cu);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        //    return setlist;
        return setList;
    }
    public List<Tracking> getListWithTeamID(int team_id) throws Exception {
        List<Tracking> trackingList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "select t.tracking_id, t.team_id, t.milestone_id, t.function_id, t.assigner_id, "
                    + "us.fullName as assignerName, t.assignee_id, u.fullName as assigneeName, t.tracking_note, t.updates, t.status\n"
                    + "from tracking as t join user as u on t.assignee_id= u.id\n"
                    + "                   join user as us on t.assigner_id= us.id\n"
                    + "			  join functiontable as f on t.function_id=f.function_id\n"
                    + "where t.team_id= "+team_id+ " ;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Tracking sb = new Tracking();
                sb.setTrackingID(rs.getInt(1));
                sb.setTeamID(rs.getInt(2));
                sb.setMilestoneID(rs.getInt(3));
                sb.setFunctionID(rs.getInt(4));
                sb.setAssignerID(rs.getInt(5));
                sb.setAssignerName(rs.getString(6));
                sb.setAssigneeID(rs.getInt(7));
                sb.setAssigneeName(rs.getString(8));
                sb.setTrackingNote(rs.getString(9));
                sb.setUpdate(rs.getBoolean(10));
                sb.setStatus(rs.getInt(11));
                trackingList.add(sb);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return trackingList;
    }
    public static void main(String[] args) throws Exception {
        TrackingDAO trdao= new TrackingDAO();
        List<Tracking> track= trdao.getListWithTeamID(2);
        System.out.println(track);
    }

}
