package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class CriteriaDAO {

    public List<Criteria> getList() throws Exception {
        List<Criteria> sbList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select * from evaluation_criteria;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Criteria sb = new Criteria();
                sb.setCriteria_id(rs.getInt(1));
                sb.setIteration_id(rs.getInt(2));
                sb.setEvaluation_weight(rs.getDouble(3));
                sb.setTeam_evaluation(rs.getInt(4));
                sb.setCriteria_order(rs.getInt(5));
                sb.setMax_loc(rs.getInt(6));
                sb.setStatus(rs.getInt(7));

                sbList.add(sb);
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

    public static List<Criteria> getListForSort(int role, int subject_id, String fieldname) throws Exception {
        List<Criteria> criteriaList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            if (role == 1) {
                strSQL = "SELECT * from evaluation_criteria "
                        + "ORDER BY " + fieldname + " ;";
            } else {
                strSQL = "SELECT a* from evaluation_criteria as a join iteration as b on"
                        + " a.iteration_id = b.iteration_id "
                        + "WHERE b.subject_id = " + subject_id + " ORDER BY " + fieldname + " ;";
            }

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Criteria sb = new Criteria();
                sb.setCriteria_id(rs.getInt(1));
                sb.setIteration_id(rs.getInt(2));
                sb.setEvaluation_weight(rs.getDouble(3));
                sb.setTeam_evaluation(rs.getInt(4));
                sb.setCriteria_order(rs.getInt(5));
                sb.setMax_loc(rs.getInt(6));
                sb.setStatus(rs.getInt(7));

                criteriaList.add(sb);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return criteriaList;
    }

    public static List<Criteria> getListForSearch(String pattern, boolean author, int subject_id) throws Exception {
        List<Criteria> criteriaList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            if (!author) {
                strSQL = "SELECT criteria_id, iteration_id, evaluation_weight, team_evaluation, criteria_order, "
                        + "max_loc, status FROM evaluation_criteria "
                        + "where iteration_id like '%" + pattern + "%' "
                        + "OR evaluation_weight like '%" + pattern + "%' "
                        + "OR team_evaluation like '%" + pattern + "%' "
                        + "OR criteria_order like '%" + pattern + "%' "
                        + "OR max_loc like '%" + pattern + "%' "
                        + "OR status like '%" + pattern + "%' ;";
            } else {
                strSQL = "SELECT a.criteria_id, b.iteration_id, a.evaluation_weight, a.team_evaluation, a.criteria_order, "
                        + "a.max_loc, a.status from evaluation_criteria as a join iteration as b"
                        + " on a.iteration_id = b.iteration_id "
                        + "where (b.subject_id = " + subject_id + " ) AND (a.iteration_id like '%" + pattern + "%' "
                        + "OR a.evaluation_weight like '%" + pattern + "%' "
                        + "OR a.team_evaluation like '%" + pattern + "%' "
                        + "OR a.criteria_order like '%" + pattern + "%' "
                        + "OR a.max_loc like '%" + pattern + "%' "
                        + "OR a.status like '%" + pattern + "%') ;";
            }

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Criteria sb = new Criteria();
                sb.setCriteria_id(rs.getInt(1));
                sb.setIteration_id(rs.getInt(2));
                sb.setEvaluation_weight(rs.getDouble(3));
                sb.setTeam_evaluation(rs.getInt(4));
                sb.setCriteria_order(rs.getInt(5));
                sb.setMax_loc(rs.getInt(6));
                sb.setStatus(rs.getInt(7));

                criteriaList.add(sb);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return criteriaList;
    }

    public List<Iteration> getIterationListFromCriteriaDaoByID(int id) throws Exception {
        List<Iteration> setlist = new ArrayList<>();

        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            strSQL = "select evaluation_criteria.iteration_id, iteration.iteration_name"
                    + " from evaluation_criteria join iteration "
                    + " on evaluation_criteria.iteration_id = iteration.iteration_id"
                    + " where evaluation_criteria.iteration_id = " + id + ";";

// Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);

            while (rs.next()) {
                Iteration t = new Iteration();
                t.setIteration_id(rs.getInt(1));
                t.setIteration_name(rs.getString(2));
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

    public void insertCriteriaToList(Criteria sb) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into evaluation_criteria (iteration_id, evaluation_weight"
                + ", team_evaluation, criteria_order, max_loc, status)"
                + " values ( " + sb.getIteration_id() + ", "
                + " " + sb.getEvaluation_weight() + ", "
                + " " + sb.getTeam_evaluation() + ", "
                + " " + sb.getCriteria_order() + ", "
                + " " + sb.getMax_loc() + ", "
                + " " + sb.isStatus() + "); ";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateClassToList(Criteria criterias) throws ClassNotFoundException,
            SQLException {

        String sql = "UPDATE evaluation_criteria SET "
                + "iteration_id = ?, " //1
                + "evaluation_weight = ?, " //2
                + "team_evaluation = ?, " //3
                + "criteria_order = ?, " //4
                + "max_loc = ?," //5
                + "status = ? " //6
                + "WHERE criteria_id = ?;"; //7

        // Lấy ra kết nối tới cơ sở dữ liệu.
        try (
                Connection connection = CommonLib.getDbConn();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //        Statement statement = connection.createStatement();
                ) {
            preparedStatement.setInt(1, criterias.getIteration_id());
            preparedStatement.setDouble(2, criterias.getEvaluation_weight());
            preparedStatement.setInt(3, criterias.getTeam_evaluation());
            preparedStatement.setInt(4, criterias.getCriteria_order());
            preparedStatement.setInt(5, criterias.getMax_loc());
            preparedStatement.setInt(6, criterias.isStatus());
            preparedStatement.setInt(7, criterias.getCriteria_id());

            preparedStatement.execute();

        }
    }

    public List<Criteria> getListWithOneCondition(String dieukien) throws Exception {
        List<Criteria> sbList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select a.* from evaluation_criteria as a join iteration as b"
                    + " on a.iteration_id = b.iteration_id where " + dieukien + ";";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Criteria sb = new Criteria();
                sb.setCriteria_id(rs.getInt(1));
                sb.setIteration_id(rs.getInt(2));
                sb.setEvaluation_weight(rs.getDouble(3));
                sb.setTeam_evaluation(rs.getInt(4));
                sb.setCriteria_order(rs.getInt(5));
                sb.setMax_loc(rs.getInt(6));
                sb.setStatus(rs.getInt(7));

                sbList.add(sb);
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

    public List<Criteria> getCriteriaListWithTwoCondition(String tencot1, int dieukien1, String kieudulieu1, String tencot2, String dieukien2, String kieudulieu2) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<Criteria> sbList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = null;
                if (kieudulieu1.compareToIgnoreCase("string") == 0) {
                    strSQL = "Select * from evaluation_criteria where " + tencot1 + " = '" + dieukien1 + "' ";
                }
                if (kieudulieu1.compareToIgnoreCase("int") == 0) {
                    strSQL = "Select * from evaluation_criteria where " + tencot1 + " = " + dieukien1 + " ";
                }
                if (kieudulieu2.compareToIgnoreCase("string") == 0) {
                    strSQL = strSQL + "AND " + tencot2 + " = '" + dieukien2 + "';";
                }
                if (kieudulieu2.compareToIgnoreCase("int") == 0) {
                    strSQL = strSQL + "AND " + tencot2 + " = " + dieukien2 + ";";
                }

                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
                while (rs.next()) {
                    Criteria sb = new Criteria();
                    sb.setCriteria_id(rs.getInt(1));
                    sb.setIteration_id(rs.getInt(2));
                    sb.setEvaluation_weight(rs.getDouble(3));
                    sb.setTeam_evaluation(rs.getInt(4));
                    sb.setCriteria_order(rs.getInt(5));
                    sb.setStatus(rs.getInt(6));

                    sbList.add(sb);
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

    //note ( không dùng do chưa tối ưu hóa được) 
    public List<Criteria> getPagination(int start) throws Exception {
        List<Criteria> criteriaList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "SELECT * from evaluation_criteria "
                    + "LIMIT " + start + ", 5;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Criteria sb = new Criteria();
                sb.setCriteria_id(rs.getInt(1));
                sb.setIteration_id(rs.getInt(2));
                sb.setEvaluation_weight(rs.getDouble(3));
                sb.setTeam_evaluation(rs.getInt(4));
                sb.setCriteria_order(rs.getInt(5));
                sb.setStatus(rs.getInt(6));

                criteriaList.add(sb);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return criteriaList;
    }

    public List<Criteria> getCriteriaByID(String id) throws Exception {
        List<Criteria> listAdd = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select * from evaluation_criteria "
                    + "WHERE " + id + ";";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Criteria sb = new Criteria();
                sb.setCriteria_id(rs.getInt(1));
                sb.setIteration_id(rs.getInt(2));
                sb.setEvaluation_weight(rs.getDouble(3));
                sb.setTeam_evaluation(rs.getInt(4));
                sb.setCriteria_order(rs.getInt(5));
                sb.setMax_loc(rs.getInt(6));
                sb.setStatus(rs.getInt(7));
                sb.setCriteria_id(rs.getInt(8));
                listAdd.add(sb);

            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return listAdd;
    }
}
