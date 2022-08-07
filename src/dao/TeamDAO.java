package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class TeamDAO {

    public List<Team> getList() throws Exception {
        List<Team> teamList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select * from team;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Team team = new Team();
                team.setTeam_id(rs.getInt(1));
                team.setTeam_name(rs.getString(2));
                team.setClass_id(rs.getInt(3));
                team.setTopic_code(rs.getString(4));
                team.setTopic_name(rs.getString(5));
                team.setGitlab_url(rs.getString(6));
                team.setStatus(rs.getBoolean(7));

                teamList.add(team);
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

    public void insertTeamToList(Team team) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into team (class_id, topic_code, topic_name, gitlab_url, status, team_name)"
                + " values ( " + team.getClass_id() + ", "
                + " '" + team.getTopic_code() + "', "
                + " '" + team.getTopic_name() + "', "
                + " '" + team.getGitlab_url() + "', "
                + " " + team.isStatus() + ","
                + " '" + team.getTeam_name() + "' ); ";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateTeamToList(Team team) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "UPDATE team SET class_id = " + team.getClass_id() + ", "
                + "topic_code= '" + team.getTopic_code() + "', "
                + "topic_name= '" + team.getTopic_name() + "', "
                + "gitlab_url= '" + team.getGitlab_url() + "', "
                + "status= " + team.isStatus() + ", "
                + "team_name= '" + team.getTeam_name() + "' "
                + "where team_id = " + team.getTeam_id() + ";";
        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }
//ham cua Hoi cho Feature:

    public List<Team> getListWithOneCondition(String dieukien) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<Team> teamList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = null;
                strSQL = "Select * from team where " + dieukien + " ;";

                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    Team team = new Team();
                    team.setTeam_id(rs.getInt(1));
                    team.setTeam_name(rs.getString(2));
                    team.setClass_id(rs.getInt(3));
                    team.setTopic_code(rs.getString(4));
                    team.setTopic_name(rs.getString(5));
                    team.setGitlab_url(rs.getString(6));
                    team.setStatus(rs.getBoolean(7));
                    teamList.add(team);
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

    public List<Team> getSearchList(String pattern) throws Exception {
        List<Team> teamList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select * from team "
                    + "where team_name like '%" + pattern + "%' or "
                    + "class_id like '%" + pattern + "%' or "
                    + "topic_code like '%" + pattern + "%' or "
                    + "topic_name like '%" + pattern + "%' or "
                    + "gitlab_url like '%" + pattern + "%' or "
                    + "status like '%" + pattern + "%';";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Team team = new Team();
                team.setTeam_id(rs.getInt(1));
                team.setTeam_name(rs.getString(2));
                team.setClass_id(rs.getInt(3));
                team.setTopic_code(rs.getString(4));
                team.setTopic_name(rs.getString(5));
                team.setGitlab_url(rs.getString(6));
                team.setStatus(rs.getBoolean(7));

                teamList.add(team);
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
