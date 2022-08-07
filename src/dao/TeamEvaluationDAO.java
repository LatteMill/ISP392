package dao;

import dao.CommonLib;
import java.sql.*;
import java.util.*;
import model.TeamEvaluation;

public class TeamEvaluationDAO {

    public List<TeamEvaluation> getList() throws Exception {
        List<TeamEvaluation> teamEvaluationList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "select a.*, b.team_name from TeamEvaluation as a join team as b on a.team_id = b.team_id;";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                TeamEvaluation newTeamEvaluation = new TeamEvaluation();

                newTeamEvaluation.setTeam_eval_ID(rs.getInt(1));
                newTeamEvaluation.setEvaluation_id(rs.getInt(2));
                newTeamEvaluation.setCriteria_id(rs.getInt(3));
                newTeamEvaluation.setTeam_id(rs.getInt(4));
                newTeamEvaluation.setGrade(rs.getInt(5));
                newTeamEvaluation.setNote(rs.getString(6));
                newTeamEvaluation.setTeamName(rs.getString(7));

                teamEvaluationList.add(newTeamEvaluation);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return teamEvaluationList;
    }

    public List<TeamEvaluation> getListForSort(boolean student, int team_id, String fieldname) throws Exception {
        List<TeamEvaluation> teamEvaluationList = new ArrayList<>();

        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            if (!student) {
                strSQL = "select a.*, b.team_name from TeamEvaluation as a join team as b on a.team_id = b.team_id ORDER BY a." + fieldname + " ;";
            } else {
                strSQL = "select a.*, b.team_name from TeamEvaluation as a join team as b on a.team_id = b.team_id WHERE a.team_id = " + team_id + " ORDER BY a." + fieldname + " ;";
            }

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                TeamEvaluation newTeamEvaluation = new TeamEvaluation();

                newTeamEvaluation.setTeam_eval_ID(rs.getInt(1));
                newTeamEvaluation.setEvaluation_id(rs.getInt(2));
                newTeamEvaluation.setCriteria_id(rs.getInt(3));
                newTeamEvaluation.setTeam_id(rs.getInt(4));
                newTeamEvaluation.setGrade(rs.getFloat(5));
                newTeamEvaluation.setNote(rs.getString(6));
                newTeamEvaluation.setTeamName(rs.getString(7));

                teamEvaluationList.add(newTeamEvaluation);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return teamEvaluationList;
    }

    public static List<TeamEvaluation> getListForSearch(String pattern, boolean student, int team_id) throws Exception {
        List<TeamEvaluation> teamEvaluationList = new ArrayList<>();

        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            if (!student) {
                strSQL = "select a.*, b.team_name from TeamEvaluation as a join team as b on a.team_id = b.team_id "
                        + " where a.evaluation_id like '%" + pattern + "%' "
                        + " OR a.criteria_id like '%" + pattern + "%' "
                        + " OR a.team_id like '%" + pattern + "%' "
                        + " OR a.grade like '%" + pattern + "%' "
                        + " OR a.note like '%" + pattern + "%';";
            } else {
                strSQL = "select a.*, b.team_name from TeamEvaluation as a join team as b on a.team_id = b.team_id "
                        + " where (a.team_id = " + team_id + " ) AND (a.evaluation_id like '%" + pattern + "%' "
                        + " OR a.criteria_id like '%" + pattern + "%' "
                        + " OR a.grade like '%" + pattern + "%' "
                        + " OR a.note like '%" + pattern + "%');";
            }

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                TeamEvaluation newTeamEvaluation = new TeamEvaluation();

                newTeamEvaluation.setTeam_eval_ID(rs.getInt(1));
                newTeamEvaluation.setEvaluation_id(rs.getInt(2));
                newTeamEvaluation.setCriteria_id(rs.getInt(3));
                newTeamEvaluation.setTeam_id(rs.getInt(4));
                newTeamEvaluation.setGrade(rs.getFloat(5));
                newTeamEvaluation.setNote(rs.getString(6));
                newTeamEvaluation.setTeamName(rs.getString(7));

                teamEvaluationList.add(newTeamEvaluation);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return teamEvaluationList;
    }

    public void insertTeamEvaluationToSQL(TeamEvaluation teamEvaluation) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
//        Connection connection = CommonLib.getDbConn();
        String sql = "insert into TeamEvaluation(evaluation_id, criteria_id, team_id, grade, note) values "
                + "(?,?,?,?,?);";

        try (
                Connection connection = CommonLib.getDbConn();
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, teamEvaluation.getEvaluation_id());
            statement.setInt(2, teamEvaluation.getCriteria_id());
            statement.setInt(3, teamEvaluation.getTeam_id());
            statement.setFloat(4, teamEvaluation.getGrade());
            statement.setString(5, teamEvaluation.getNote());

            statement.execute();

        }

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
//        int rowCount = statement.executeUpdate(sql);
        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateTeamEvaluationToList(TeamEvaluation teamEvaluation) throws ClassNotFoundException,
            SQLException {

        String sql = "UPDATE TeamEvaluation SET "
                + " evaluation_id =?, " //1
                + " criteria_id = ?, " //2
                + " team_id = ?, " //3
                + " grade = ?, " //4
                + " note = ? "//5
                + " where team_eval_id = ?;"; //6


        // Lấy ra kết nối tới cơ sở dữ liệu.
        try (
                Connection connection = CommonLib.getDbConn();
                PreparedStatement preparedStatement = connection.prepareStatement(sql); //        Statement statement = connection.createStatement();
                ) {
            preparedStatement.setInt(1, teamEvaluation.getEvaluation_id());
            preparedStatement.setInt(2, teamEvaluation.getCriteria_id());
            preparedStatement.setInt(3, teamEvaluation.getTeam_id());
            preparedStatement.setFloat(4, teamEvaluation.getGrade());
            preparedStatement.setString(5, teamEvaluation.getNote());
            preparedStatement.setInt(6, teamEvaluation.getTeam_eval_ID());

            preparedStatement.execute();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
//        int rowCount = statement.executeUpdate(sql);
        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public List<TeamEvaluation> getListWithOneCondition(String dieukien1) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<TeamEvaluation> teamEvaluationList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = "select a.*, b.team_name from TeamEvaluation as a join team as b on a.team_id = b.team_id where " + dieukien1 + ";";

                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    TeamEvaluation newTeamEvaluation = new TeamEvaluation();

                    newTeamEvaluation.setTeam_eval_ID(rs.getInt(1));
                    newTeamEvaluation.setEvaluation_id(rs.getInt(2));
                    newTeamEvaluation.setCriteria_id(rs.getInt(3));
                    newTeamEvaluation.setTeam_id(rs.getInt(4));
                    newTeamEvaluation.setGrade(rs.getFloat(5));
                    newTeamEvaluation.setNote(rs.getString(6));
                    newTeamEvaluation.setTeamName(rs.getString(7));

                    teamEvaluationList.add(newTeamEvaluation);
                }
                // Đóng kết nối
                conn.close();
            } catch (SQLException exp) {
                System.out.println("Exception: " + exp.getMessage());
            } catch (ClassNotFoundException exp) {
                System.out.println("Can't connect to DB: " + exp.getMessage());
            }
            return teamEvaluationList;
        }
    }

}
