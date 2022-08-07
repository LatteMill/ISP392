

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import java.util.*;
import javax.sound.sampled.SourceDataLine;
import model.*;

/**
 *
 * @author admin
 */
public class UserEvaluationDAO {

    public List<UserEvaluation> getList() throws Exception {
        List<UserEvaluation> userEvalList = new ArrayList<>();


        try (Connection conn = CommonLib.getDbConn()) {
            Statement statement = conn.createStatement();
            String strSQL = "select m.member_eval_id, m.evaluation_id, m.criteria_id,e.iteration_id,i.class_id, i.team_id, i.user_id,u.fullName,"
                    + "e.max_loc,i.bonus, i.grade, m.note, e.evaluation_weight \n"
                    + "from member_evaluation as m join evaluation_criteria as e on e.criteria_id= m.criteria_id\n"
                    + "join iteration_evaluation as i on m.evaluation_id= i.evaluation_id\n"
                    + "join class_user as c on c.class_id= i.class_id and c.team_id= i.team_id and c.user_id= i.user_id\n"
                    + "join user as u on c.user_id= u.id;";

//            String strSQL = "select m.member_eval_id, m.evaluation_id, m.criteria_id,e.iteration_id,i.class_id, i.team_id, i.user_id, u.fullName,"
//                    + "e.max_loc/100,i.bonus+i.grade + e.max_loc/100, m.note \n"
//                    + "from member_evaluation as m join evaluation_criteria as e on e.criteria_id= m.criteria_id\n"
//                    + "join iteration_evaluation as i on m.evaluation_id= i.evaluation_id\n"
//                    + "join class_user as c on c.class_id= i.class_id and c.team_id= i.team_id and c.user_id= i.user_id\n"
//                    + "join user as u on c.user_id= u.id;";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                UserEvaluation user = new UserEvaluation();
                user.setMember_eval_id(rs.getInt(1));
                user.setEvaluation_id(rs.getInt(2));
                user.setCriteria_id(rs.getInt(3));
                user.setIteration_id(rs.getInt(4));
                user.setClass_id(rs.getInt(5));
                user.setTeam_id(rs.getInt(6));
                user.setUser_id(rs.getInt(7));
                user.setFullName(rs.getString(8));
                user.setMax_loc(rs.getInt(9));
                user.setBonus(rs.getDouble(10));
                user.setGrade(rs.getDouble(11));
                user.setNotes(rs.getString(12));
                user.setPercent(rs.getDouble(13));
//                user.setConverted_loc(rs.getDouble(9));
//                user.setTotalGrade(rs.getDouble(10));
//                user.setNotes(rs.getString(11));
                userEvalList.add(user);
            }
            // Đóng kết nối
            conn.close();

        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return userEvalList;
    }

    public void insertUserEvaluationToList(UserEvaluation user) throws ClassNotFoundException,
            SQLException,
            Exception {
        ResultSet rs = null;
        int userEvalID = 0;
        // Lấy ra kết nối tới cơ sở dữ liệu.
//        Connection connection = CommonLib.getDbConn();
        String sql = "insert into member_evaluation(evaluation_id, criteria_id,converted_loc, totalGrade, note)"
                + "values(?,?,?,?,?)";
        try (Connection connection = CommonLib.getDbConn();
                PreparedStatement preparedStatementtatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatementtatement.setInt(1, user.getEvaluation_id());
            preparedStatementtatement.setInt(2, user.getCriteria_id());
            preparedStatementtatement.setDouble(3, user.getConverted_loc());
            preparedStatementtatement.setDouble(4, user.getTotalGrade());
            preparedStatementtatement.setString(5, user.getNotes());

            int rowAffected = preparedStatementtatement.executeUpdate();
            if (rowAffected == 1) {
                // get candidate id
                rs = preparedStatementtatement.getGeneratedKeys();
                if (rs.next()) {
                    userEvalID = rs.getInt(1);
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

//        System.out.println(String.format("A new userEval with id %d has been inserted.", userEvalID));
    }

    public void updateUserEvaluationToList(UserEvaluation user) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE member_evaluation SET "
                + "totalGrade= ? , "
                + "note= ?  "
                + "WHERE member_eval_id= ?;";
        // Lấy ra kết nối tới cơ sở dữ liệu.
        try (
                Connection connection = CommonLib.getDbConn();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setDouble(1, user.getTotalGrade());
            ps.setString(2, user.getNotes());
            ps.setInt(3, user.getMember_eval_id());
//            System.out.println(user);
//            System.out.println(ps.executeUpdate());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //iteration_evaluation
    public List<UserEvaluation> getListIterEvaluations() throws Exception {
        List<UserEvaluation> userEvalList = new ArrayList<>();

        try (Connection conn = CommonLib.getDbConn()) {
            Statement statement = conn.createStatement();
            String strSQL = "select * from iteration_evaluation";

            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                UserEvaluation user = new UserEvaluation();
                user.setEvaluation_id(rs.getInt(1));
                user.setIteration_id(rs.getInt(2));
                user.setClass_id(rs.getInt(3));
                user.setTeam_id(rs.getInt(4));
                user.setUser_id(rs.getInt(5));
                user.setBonus(rs.getDouble(6));
                user.setGrade(rs.getDouble(7));
                user.setNotes(rs.getString(8));
//                user.setConverted_loc(rs.getDouble(9));
//                user.setTotalGrade(rs.getDouble(10));
//                user.setNotes(rs.getString(11));
                userEvalList.add(user);
            }
            // Đóng kết nối
            conn.close();

        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return userEvalList;
    }

    public void insertIterEvaluationsToList(UserEvaluation user) throws ClassNotFoundException,
            SQLException {
        ResultSet rs = null;
        int userEvalID = 0;
        // Lấy ra kết nối tới cơ sở dữ liệu.
//        Connection connection = CommonLib.getDbConn();
        String sql = "insert into iteration_evaluation(bonus, grade, evaluation_id, iteration_id, class_id, team_id, user_id, note)"
                + "values(?,?,?,?,?,?,?,?)";
        try (Connection connection = CommonLib.getDbConn();
                PreparedStatement prstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            prstm.setDouble(1, user.getBonus());
            prstm.setDouble(2, user.getGrade());
            prstm.setInt(3, user.getEvaluation_id());
            prstm.setInt(4, user.getIteration_id());
            prstm.setInt(5, user.getClass_id());
            prstm.setInt(6, user.getTeam_id());
            prstm.setInt(7, user.getUser_id());
            prstm.setString(8, user.getNotes());

            int rowAffected = prstm.executeUpdate();
            if (rowAffected == 1) {
                // get candidate id
                rs = prstm.getGeneratedKeys();
                if (rs.next()) {
                    userEvalID = rs.getInt(1);
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

//        System.out.println(String.format("A new userEval with id %d has been inserted.", userEvalID));
    }

//    public void insertIterationToList(UserEvaluation user) throws SQLException, ClassNotFoundException {
//        ResultSet rs = null;
//        int userEvalID = 0;
//        // Lấy ra kết nối tới cơ sở dữ liệu.
////        Connection connection = CommonLib.getDbConn();
//        String sql = "insert into iteration_evaluation(evaluation_id, iteration_id,bonus, grade, note)"
//                + "values(?,?,?,?,?)";
//        try (Connection connection = CommonLib.getDbConn();
//                PreparedStatement preparedStatementtatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
//            preparedStatementtatement.setInt(1, user.getEvaluation_id());
//            preparedStatementtatement.setInt(2, user.getIteration_id());
//            preparedStatementtatement.setDouble(3, user.getBonus());
//            preparedStatementtatement.setDouble(4, user.getGrade());
//            preparedStatementtatement.setString(5, user.getNotes());
//
//            int rowAffected = preparedStatementtatement.executeUpdate();
//            if (rowAffected == 1) {
//                // get candidate id
//                rs = preparedStatementtatement.getGeneratedKeys();
//                if (rs.next()) {
//                    userEvalID = rs.getInt(1);
//                }
//
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
////        System.out.println(String.format("A new userEval with id %d has been inserted.", userEvalID));
//    }
    public void updateIterEvaluationsToList(UserEvaluation user) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE iteration_evaluation SET "
                + "bonus= ?, "
                + "grade= ?"
                + "WHERE evaluation_id= ?;";
        // Lấy ra kết nối tới cơ sở dữ liệu.
        try (
                Connection connection = CommonLib.getDbConn();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setDouble(1, user.getBonus());
            ps.setDouble(2, user.getGrade());
            ps.setInt(3, user.getEvaluation_id());
//            System.out.println(user);
//            System.out.println(ps.executeUpdate());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //iteration_evaluation

    public List<UserEvaluation> getListIterEvalWithClassID(int class_id) throws Exception {
        List<UserEvaluation> userEvalList = new ArrayList<>();

        try (Connection conn = CommonLib.getDbConn()) {
            Statement statement = conn.createStatement();
            String strSQL = "select * from iteration_evaluation "
                    + "WHERE class_id= " + class_id + ";";

            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                UserEvaluation user = new UserEvaluation();
                user.setEvaluation_id(rs.getInt(1));
                user.setIteration_id(rs.getInt(2));
                user.setClass_id(rs.getInt(3));
                user.setTeam_id(rs.getInt(4));
                user.setUser_id(rs.getInt(5));
                user.setBonus(rs.getDouble(6));
                user.setGrade(rs.getDouble(7));
                user.setNotes(rs.getString(8));

                userEvalList.add(user);
            }
            // Đóng kết nối
            conn.close();

        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return userEvalList;
    }

    public List<UserEvaluation> getListIterEvalWithTwoCondition(int class_id, int iteration_id) throws Exception {
        List<UserEvaluation> userEvalList = new ArrayList<>();

        try (Connection conn = CommonLib.getDbConn()) {
            Statement statement = conn.createStatement();
            String strSQL = "select * from iteration_evaluation "
                    + "WHERE class_id= " + class_id + " and iteration_id= " + iteration_id + " ;";

            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                UserEvaluation user = new UserEvaluation();
                user.setEvaluation_id(rs.getInt(1));
                user.setIteration_id(rs.getInt(2));
                user.setClass_id(rs.getInt(3));
                user.setTeam_id(rs.getInt(4));
                user.setUser_id(rs.getInt(5));
                user.setBonus(rs.getDouble(6));
                user.setGrade(rs.getDouble(7));
                user.setNotes(rs.getString(8));

//                userEvalList.setEvaluation_id();
//                user.setConverted_loc(rs.getDouble(9));
//                user.setTotalGrade(rs.getDouble(10));
//                user.setNotes(rs.getString(11));
                userEvalList.add(user);
            }
            // Đóng kết nối
            conn.close();

        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return userEvalList;
    }

    public static void main(String[] args) throws Exception {
        UserEvaluationDAO ud = new UserEvaluationDAO();
        List<UserEvaluation> list = ud.getList();
        for (UserEvaluation u : list) {
            System.out.println(u);
        }
    }
}

