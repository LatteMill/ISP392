package dao;

import java.sql.*;
import java.util.*;
import model.*;
import view.Utility;

public class IssueDAO {

    public List<Issue> getList() throws Exception {
        List<Issue> iList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "SELECT a.issue_id, "
                    + "a.assignee_id, b.fullname, "
                    + "a.description, a.gitlab_id, a.gitlab_url, DATE_FORMAT(a.created_at,'%d/%m/%Y') as created_at, "
                    + "DATE_FORMAT(a.due_date,'%d/%m/%Y') as due_date, "
                    + "a.team_id, c.team_name, "
                    + "a.milestone_id, d.milestone_name, "
                    + "a.function_ids, a.labels, a.status, a.issue_title  "
                    + "FROM Issue as a join User as b on a.assignee_id = b.id  "
                    + "join team as c on a.team_id = c.team_id  "
                    + "join milestone as d on a.milestone_id = d.milestone_id;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Issue issue = new Issue();
                issue.setIssue_id(rs.getInt(1));
                issue.setAssignee_id(rs.getInt(2));
                issue.setAssigneeName(rs.getString(3));
                issue.setDescription(rs.getString(4));
                issue.setGitlab_id(rs.getInt(5));
                issue.setGitlab_url(rs.getString(6));
                issue.setCreated_at(Utility.convertStringToDate(rs.getString(7)));
                issue.setDue_date(Utility.convertStringToDate(rs.getString(8)));
                issue.setTeam_id(rs.getInt(9));
                issue.setTeamName(rs.getString(10));
                issue.setMilestone_id(rs.getInt(11));
                issue.setMilestoneName(rs.getString(12));
                issue.setFunction_ids(rs.getString(13));
                issue.setLabels(rs.getString(14));
                issue.setStatus(rs.getInt(15));
                issue.setIssue_title(rs.getString(16));

                iList.add(issue);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return iList;
    }

    public List<Issue> getListForSort(boolean student, int team_id, String fieldname) throws Exception {
        List<Issue> iList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            if (!student) {
                strSQL = "SELECT a.issue_id, "
                        + "a.assignee_id, b.fullname, "
                        + "a.description, a.gitlab_id, a.gitlab_url, "
                        + "DATE_FORMAT(a.created_at,'%d/%m/%Y') as created_at, "
                        + "DATE_FORMAT(a.due_date,'%d/%m/%Y') as due_date, "
                        + "a.team_id, c.team_name, "
                        + "a.milestone_id, d.milestone_name, "
                        + "a.function_ids, a.labels, a.status, a.issue_title  "
                        + "FROM Issue as a join User as b on a.assignee_id = b.id  "
                        + "join team as c on a.team_id = c.team_id  "
                        + "join milestone as d on a.milestone_id = d.milestone_id "
                        + "ORDER BY " + fieldname + " ;";
            } else {
                strSQL = "SELECT a.issue_id, "
                        + "a.assignee_id, b.fullname, "
                        + "a.description, a.gitlab_id, a.gitlab_url, DATE_FORMAT(a.created_at,'%d/%m/%Y') as created_at, "
                        + "DATE_FORMAT(a.due_date,'%d/%m/%Y') as due_date, "
                        + "a.team_id, c.team_name, "
                        + "a.milestone_id, d.milestone_name, "
                        + "a.function_ids, a.labels, a.status, a.issue_title  "
                        + "FROM Issue as a join User as b on a.assignee_id = b.id  "
                        + "join team as c on a.team_id = c.team_id  "
                        + "join milestone as d on a.milestone_id = d.milestone_id "
                        + "WHERE a.team_id = " + team_id + " ORDER BY " + fieldname + " ;";
            }

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Issue issue = new Issue();
                
                issue.setIssue_id(rs.getInt(1));
                issue.setAssignee_id(rs.getInt(2));
                issue.setAssigneeName(rs.getString(3));
                issue.setDescription(rs.getString(4));
                issue.setGitlab_id(rs.getInt(5));
                issue.setGitlab_url(rs.getString(6));
                issue.setCreated_at(Utility.convertStringToDate(rs.getString(7)));
                issue.setDue_date(Utility.convertStringToDate(rs.getString(8)));
                issue.setTeam_id(rs.getInt(9));
                issue.setTeamName(rs.getString(10));
                issue.setMilestone_id(rs.getInt(11));
                issue.setMilestoneName(rs.getString(12));
                issue.setFunction_ids(rs.getString(13));
                issue.setLabels(rs.getString(14));
                issue.setStatus(rs.getInt(15));
                issue.setIssue_title(rs.getString(16));

                iList.add(issue);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return iList;
    }

    public static List<Issue> getListForSearch(String pattern, boolean student, int team_id) throws Exception {
        List<Issue> iList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            if (!student) {
                strSQL = "SELECT a.issue_id, "
                        + "a.assignee_id, b.fullname, "
                        + "a.description, a.gitlab_id, a.gitlab_url, DATE_FORMAT(a.created_at,'%d/%m/%Y') as created_at, "
                        + "DATE_FORMAT(a.due_date,'%d/%m/%Y') as due_date, "
                        + "a.team_id, c.team_name, "
                        + "a.milestone_id, d.milestone_name, "
                        + "a.function_ids, a.labels, a.status, a.issue_title  "
                        + "FROM Issue as a join User as b on a.assignee_id = b.id  "
                        + "join team as c on a.team_id = c.team_id  "
                        + "join milestone as d on a.milestone_id = d.milestone_id "
                        + "where a.assignee_id like '%" + pattern + "%' "
                        + "OR a.description like '%" + pattern + "%' "
                        + "OR a.gitlab_id like '%" + pattern + "%' "
                        + "OR a.gitlab_url like '%" + pattern + "%' "
                        + "OR DATE_FORMAT(a.created_at,'%d/%m/%Y') like '%" + pattern + "%' "
                        + "OR DATE_FORMAT(a.due_date,'%d/%m/%Y') like '%" + pattern + "%' "
                        + "OR a.team_id like '%" + pattern + "%' "
                        + "OR a.milestone_id like '%" + pattern + "%' "
                        + "OR a.function_ids like '%" + pattern + "%' "
                        + "OR a.labels like '%" + pattern + "%' "
                        + "OR a.issue_title like '%" + pattern + "%' ;";
            } else {
                strSQL = "SELECT a.issue_id, "
                        + "a.assignee_id, b.fullname, "
                        + "a.description, a.gitlab_id, a.gitlab_url, DATE_FORMAT(a.created_at,'%d/%m/%Y') as created_at, "
                        + "DATE_FORMAT(a.due_date,'%d/%m/%Y') as due_date, "
                        + "a.team_id, c.team_name, "
                        + "a.milestone_id, d.milestone_name, "
                        + "a.function_ids, a.labels, a.status, a.issue_title  "
                        + "FROM Issue as a join User as b on a.assignee_id = b.id  "
                        + "join team as c on a.team_id = c.team_id  "
                        + "join milestone as d on a.milestone_id = d.milestone_id "
                        + "where a.team_id = " + team_id + " and "
                        + " (a.description like '%" + pattern + "%'"
                        + " OR a.gitlab_id like '%" + pattern + "%'"
                        + " OR a.gitlab_url like '%" + pattern + "%'"
                        + " OR DATE_FORMAT(a.created_at,'%d/%m/%Y') like '%" + pattern + "%'"
                        + " OR DATE_FORMAT(a.due_date,'%d/%m/%Y') like '" + pattern + "%'"
                        + " OR a.assignee_id like '%" + pattern + "%'"
                        + " OR a.milestone_id like '%" + pattern + "%'"
                        + " OR a.function_ids like '%" + pattern + "%'"
                        + " OR a.labels like '%" + pattern + "%'"
                        + " OR a.issue_title like '%" + pattern + "%' ) ;";
            }
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Issue issue = new Issue();
                
                issue.setIssue_id(rs.getInt(1));
                issue.setAssignee_id(rs.getInt(2));
                issue.setAssigneeName(rs.getString(3));
                issue.setDescription(rs.getString(4));
                issue.setGitlab_id(rs.getInt(5));
                issue.setGitlab_url(rs.getString(6));
                issue.setCreated_at(Utility.convertStringToDate(rs.getString(7)));
                issue.setDue_date(Utility.convertStringToDate(rs.getString(8)));
                issue.setTeam_id(rs.getInt(9));
                issue.setTeamName(rs.getString(10));
                issue.setMilestone_id(rs.getInt(11));
                issue.setMilestoneName(rs.getString(12));
                issue.setFunction_ids(rs.getString(13));
                issue.setLabels(rs.getString(14));
                issue.setStatus(rs.getInt(15));
                issue.setIssue_title(rs.getString(16));

                iList.add(issue);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return iList;
    }

    public void insertIssueToList(Issue issue) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into Issue(assignee_id, description, gitlab_id, gitlab_url, "
                + "created_at, due_date, team_id, milestone_id, function_ids, labels, status, issue_title)"
                + "values ("
                + issue.getAssignee_id() + ","
                + " '" + issue.getDescription() + "',"
                + issue.getGitlab_id() + ","
                + " '" + issue.getGitlab_url() + "',"
                + " '" + Utility.convertDateToStringtoInsert(issue.getCreated_at()) + "',"
                + " '" + Utility.convertDateToStringtoInsert(issue.getDue_date()) + "',"
                + issue.getTeam_id() + ","
                + issue.getMilestone_id() + ","
                + "'" + issue.getFunction_ids() + "',"
                + "'" + issue.getLabels() + "',"
                + issue.getStatus() + ","
                + " '" + issue.getIssue_title() + "');";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        connection.close();
        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateIssueToList(Issue issue) throws ClassNotFoundException,
            SQLException {

        String sql = "UPDATE Issue SET "
                + "assignee_id =?, " //1
                + "issue_title = ?, " //2
                + "description = ?, " //3
                + "gitlab_id = ?, " //4
                + "gitlab_url = ?, " //5
                + "created_at = ?," //6
                + "due_date = ?, " //7
                + "team_id = ?," //8
                + "milestone_id = ?," //9
                + "function_ids = ?," //10
                + "labels = ?," //11
                + "status= ? " //12
                + "WHERE issue_id = ?;"; //13

        // Lấy ra kết nối tới cơ sở dữ liệu.
        try (
                Connection connection = CommonLib.getDbConn();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //        Statement statement = connection.createStatement();
                ) {
            preparedStatement.setInt(1, issue.getAssignee_id());
            preparedStatement.setString(2, issue.getIssue_title());
            preparedStatement.setString(3, issue.getDescription());
            preparedStatement.setInt(4, issue.getGitlab_id());
            preparedStatement.setString(5, issue.getGitlab_url());
            preparedStatement.setString(6, Utility.convertDateToStringtoInsert(issue.getCreated_at()));
            preparedStatement.setString(7, Utility.convertDateToStringtoInsert(issue.getDue_date()));
            preparedStatement.setInt(8, issue.getTeam_id());
            preparedStatement.setInt(9, issue.getMilestone_id());
            preparedStatement.setString(10, issue.getFunction_ids());
            preparedStatement.setString(11, issue.getLabels());
            preparedStatement.setInt(12, issue.getStatus());
            preparedStatement.setInt(13, issue.getIssue_id());

            preparedStatement.execute();

        }

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
//        int rowCount = statement.executeUpdate(sql);
        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public List<Issue> getListWithOneCondition(String dieukien1) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<Issue> iList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = "SELECT a.issue_id, "
                        + "a.assignee_id, b.fullname, "
                        + "a.description, a.gitlab_id, a.gitlab_url, "
                        + "DATE_FORMAT(a.created_at,'%d/%m/%Y') as created_at, "
                        + "DATE_FORMAT(a.due_date,'%d/%m/%Y') as due_date, "
                        + "a.team_id, c.team_name, "
                        + "a.milestone_id, d.milestone_name, "
                        + "a.function_ids, a.labels, a.status, a.issue_title  "
                        + "FROM Issue as a join User as b on a.assignee_id = b.id  "
                        + "join team as c on a.team_id = c.team_id  "
                        + "join milestone as d on a.milestone_id = d.milestone_id "
                        + " where " + dieukien1 + ";";

                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    Issue issue = new Issue();
                    issue.setIssue_id(rs.getInt(1));
                    issue.setAssignee_id(rs.getInt(2));
                    issue.setAssigneeName(rs.getString(3));
                    issue.setDescription(rs.getString(4));
                    issue.setGitlab_id(rs.getInt(5));
                    issue.setGitlab_url(rs.getString(6));
                    issue.setCreated_at(Utility.convertStringToDate(rs.getString(7)));
                    issue.setDue_date(Utility.convertStringToDate(rs.getString(8)));
                    issue.setTeam_id(rs.getInt(9));
                    issue.setTeamName(rs.getString(10));
                    issue.setMilestone_id(rs.getInt(11));
                    issue.setMilestoneName(rs.getString(12));
                    issue.setFunction_ids(rs.getString(13));
                    issue.setLabels(rs.getString(14));
                    issue.setStatus(rs.getInt(15));
                    issue.setIssue_title(rs.getString(16));

                    iList.add(issue);
                }
                // Đóng kết nối
                conn.close();
            } catch (SQLException exp) {
                System.out.println("Exception: " + exp.getMessage());
            } catch (ClassNotFoundException exp) {
                System.out.println("Can't connect to DB: " + exp.getMessage());
            }
            return iList;
        }
    }

    //note ( không dùng do chưa tối ưu hóa được) 
    public List<Issue> getPagination(int start) throws Exception {
        List<Issue> iList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "SELECT issue_id, assignee_id, description, gitlab_id, gitlab_url, "
                    + "DATE_FORMAT(created_at,'%d/%m/%Y') as created_at, "
                    + "DATE_FORMAT(due_date,'%d/%m/%Y') as due_date, "
                    + "team_id, milestone_id, function_ids, labels, status, issue_title FROM Issue "
                    + "LIMIT " + start + ", 5;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Issue issue = new Issue();
                issue.setIssue_id(rs.getInt(1));
                issue.setAssignee_id(rs.getInt(2));
                issue.setDescription(rs.getString(3));
                issue.setGitlab_id(rs.getInt(4));
                issue.setGitlab_url(rs.getString(5));
                issue.setCreated_at(Utility.convertStringToDate(rs.getString(6)));
                issue.setDue_date(Utility.convertStringToDate(rs.getString(7)));
                issue.setTeam_id(rs.getInt(8));
                issue.setMilestone_id(rs.getInt(9));
                issue.setFunction_ids(rs.getString(10));
                issue.setLabels(rs.getString(11));
                issue.setStatus(rs.getInt(12));
                issue.setIssue_title(rs.getString(13));

                iList.add(issue);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return iList;
    }
}
