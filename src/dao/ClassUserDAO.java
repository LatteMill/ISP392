/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ClassUser;
import model.Team;
import view.Utility;

/**
 *
 * @author admin
 */
public class ClassUserDAO {

    public List<ClassUser> getList() throws Exception {
        List<ClassUser> userList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select c.class_id, t.team_id, cu.user_id, u.roles, u.fullName, "
                    + "u.email, cu.team_leader, DATE_FORMAT(cu.dropout_date,'%d/%m/%Y') as dropout_date, cu.user_notes, "
                    + "cu.final_pres_eval,cu.final_topic_eval, cu.status\n"
                    + "from class_user as cu "
                    + "join class as c on cu.class_id= c.class_id\n"
                    + "join team as t on cu.team_id= t.team_id\n"
                    + "join user as u on cu.user_id= u.id;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                ClassUser user = new ClassUser();
                user.setClassID(rs.getInt(1));
                user.setTeamID(rs.getInt(2));
                user.setUserID(rs.getInt(3));
                user.setRoles(rs.getInt(4));
                user.setFullName(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setTeamLeader(rs.getBoolean(7));
                user.setDropout_date(Utility.convertStringToDate(rs.getString(8)));
                user.setUser_note(rs.getString(9));
                user.setFinal_pres_eval(rs.getInt(10));
                user.setFinal_topic_eval(rs.getInt(11));
                user.setStatus(rs.getInt(12));
                userList.add(user);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return userList;
    }

    public void insertClassUserToList(ClassUser user) throws ClassNotFoundException,
            SQLException {

        try ( // Lấy ra kết nối tới cơ sở dữ liệu.
                Connection connection = CommonLib.getDbConn()) {
            Statement statement = connection.createStatement();
            String sql = "insert into class_user(class_id, team_id, user_id, team_leader,dropout_date, user_notes, final_pres_eval, final_topic_eval, status)"
                    + "values("
                    + " " + user.getClassID() + ","
                    + " " + user.getTeamID() + ","
                    + " " + user.getUserID() + ","
                    + " " + user.isTeamLeader() + ","
                    + "'" + Utility.convertDateToStringtoInsert(user.getDropout_date()) + "',"
                    + "'" + user.getUser_note() + "',"
                    + " " + user.getFinal_pres_eval() + ","
                    + " " + user.getFinal_topic_eval() + ","
                    + " " + user.getStatus() + ");";
            // Thực thi câu lệnh.
            // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
            int rowCount = statement.executeUpdate(sql);
            connection.close();
            // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
        }
    }

    public void insertClassUserWithConditionToList(ClassUser user) throws SQLException, ClassNotFoundException {
        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into class_user(class_id, user_id, team_id) "
                + "values("
                + " " + user.getClass() + ","
                + " " + user.getUserID() + ","
                + " " + user.getTeamID() + ");";
        int rowCount = statement.executeUpdate(sql);
        connection.close();
    }

    public void updateClassUserToList(ClassUser user, String column, String value, String dataType) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "";
        if (dataType.compareToIgnoreCase("string") == 0) {
            sql = "UPDATE class_user SET " + column + " = '" + value + "' WHERE user_id = " + user.getUserID() + ";";
        }

        if (dataType.compareToIgnoreCase("boolean") == 0 || dataType.compareToIgnoreCase("int") == 0) {
            sql = "UPDATE class_user SET " + column + " = " + value + " WHERE user_id = " + user.getUserID() + ";";
        }
//        if( dataType.compareToIgnoreCase("date")==0){
//            Date validValueDate = Utility.convert(value);
//            sql = "update ClassUser SET " + column +" = '" + Utility.convertDateToStringtoInsert(validValueDate) + "' where class_User_id = " + user.getClassUser_id()+ ";";
//        }

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }
        public void updateClassUserToList(ClassUser sb) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE class_user SET "
                + "class_id= ?, "
                + "team_id= ?, "
                + "=?,"
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
            ps.setInt(1, sb.getClassID());
            ps.setInt(2, sb.getTeamID());
            ps.setBoolean(3, sb.isTeamLeader());
            ps.setString(4, Utility.convertDateToStringtoInsert(sb.getDropout_date()));
            ps.setString(5, sb.getUser_note());
            ps.setInt(6, sb.getFinal_pres_eval());
            ps.setInt(7, sb.getFinal_topic_eval());
            ps.setInt(8, sb.getStatus());

            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void deleteClassUserToList(ClassUser stl) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM class_user "
                + "WHERE class_id = ?"
                + "AND team_id = ?"
                + "AND  user_id = ?";
//        try (Connection connection = CommonLib.getDbConn();
//                PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, stl.getClassID());
//            stmt.setInt(2, stl.getTeamID());
//            stmt.setInt(3, stl.getUserID());
//            stmt.executeUpdate(sql);
//            System.out.println("Record deleted successfully");
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
        try {
            Connection connection = CommonLib.getDbConn();
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("DELETE FROM class_user WHERE class_id="+stl.getClassID() +" and team_id= " +stl.getTeamID()+" and user_id= "+ stl.getUserID());
            System.out.println("Record deleted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

//    public List<ClassUser> getClassUserWithCodition(int classID, int teamID, int userID) throws SQLException, ClassNotFoundException, Exception {
//        List<ClassUser> userList = new ArrayList<>();
//        try {
//            Connection conn = CommonLib.getDbConn();
//            Statement statement = conn.createStatement();
//            String sql = null;
//            if (teamID == 0 && userID == 0) {
//                sql = "SELECT* from class_user"
//                        + "WHERE class_id = " + classID + ";";
//                ResultSet rs = statement.executeQuery(sql);
//            } else if (userID == 0) {
//                sql = "SELECT* from class_user"
//                        + "WHERE class_id = " + classID + " AND team_id = " + teamID + ";";
//                ResultSet rs = statement.executeQuery(sql);
//            } else {
//                sql = "SELECT* from class_user"
//                        + "WHERE class_id = " + classID + " AND team_id = " + teamID + " AND user_id = " + userID + ";";
//
//            }
//            ResultSet rs = statement.executeQuery(sql);
//            while (rs.next()) {
//                ClassUser user = new ClassUser();
//                user.setClassID(rs.getInt(1));
//                user.setTeamID(rs.getInt(2));
//                user.setUserID(rs.getInt(3));
//                user.setRoles(rs.getInt(4));
//                user.setFullName(rs.getString(5));
//                user.setEmail(rs.getString(6));
//                user.setTeamLeader(rs.getBoolean(7));
//                user.setDropout_date(Utility.convertStringToDate(rs.getString(8)));
//                user.setUser_note(rs.getString(9));
//                user.setFinal_pres_eval(rs.getInt(10));
//                user.setFinal_topic_eval(rs.getInt(11));
//                user.setStatus(rs.getInt(12));
//                userList.add(user);
//            }
//            // Đóng kết nối
//            conn.close();
//        } catch (SQLException exp) {
//            System.out.println("Exception: " + exp.getMessage());
//        } catch (ClassNotFoundException exp) {
//            System.out.println("Can't connect to DB: " + exp.getMessage());
//        }
//        return userList;
//    }
    public ClassUser getTeam_idList(int id) throws Exception {
        //   List<class_User> setlist = new ArrayList<>();
        ClassUser user = new ClassUser();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            strSQL = "select team_id from class_user where user_id =  " + id + ";";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);

            while (rs.next()) {

                user.setTeamID(rs.getInt(1));
                //        setlist.add(c);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        //    return setlist;
        return user;

    }

//    public static void main(String[] args) throws Exception {
//        ClassUserDAO sdao= new ClassUserDAO();
//        List<class_User> userList= sdao.getList();
//        for (ClassUser user : userList) {
//            System.out.println(user);
//        }
//    }
    public List<Team> getTeamListFromClassUserByUserID(int id) throws Exception {
        List<Team> setlist = new ArrayList<>();

        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            strSQL = "select class_user.team_id, team.team_name, team.topic_code, team.topic_name, team.status  from class_user join team "
                    + " on team.team_id = class_user.team_id "
                    + " where user_id = " + id + ";";

// Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);

            while (rs.next()) {
                Team t = new Team();
                t.setTeam_id(rs.getInt(1));
                t.setTeam_name(rs.getString(2));
                t.setTopic_code(rs.getString(3));
                t.setTopic_name(rs.getString(4));
                t.setStatus(rs.getBoolean(5));
                setlist.add(t);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        //    return setlist;
        return setlist;

    }

    public List<ClassUser> getUserListFromClassUserByTeamID(int team_id) throws Exception {
        List<ClassUser> setlist = new ArrayList<>();

        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            strSQL = "select class_user.user_id, user.fullName, class_user.status from class_user join user on class_user.user_id = user.id where class_user.team_id = " + team_id + ";";

// Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);

            while (rs.next()) {
                ClassUser cu = new ClassUser();
                cu.setUserID(rs.getInt(1));
                cu.setFullName(rs.getString(2));
                cu.setStatus(rs.getInt(3));

                setlist.add(cu);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        //    return setlist;
        return setlist;

    }

}
