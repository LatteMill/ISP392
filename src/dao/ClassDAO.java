package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class ClassDAO {

    public List<Classroom> getList() throws Exception {
        List<Classroom> classList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "SELECT * from class;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Classroom sb = new Classroom();
                sb.setClass_id(rs.getInt(1));
                sb.setClass_code(rs.getString(2));
                sb.setTrainer_id(rs.getInt(3));
                sb.setSubject_id(rs.getInt(4));
                sb.setClass_year(rs.getInt(5));
                sb.setClass_term(rs.getInt(6));
                sb.setBlock5_class(rs.getInt(7));
                sb.setStatus(rs.getInt(8));

                classList.add(sb);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return classList;
    }

    public List<Classroom> getListForSort(int role, int id, String fieldname) throws Exception {
        List<Classroom> classList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            switch (role) {
                case 1:
                    strSQL = "SELECT * from class "
                            + "ORDER BY " + fieldname + " ;";
                    break;
                case 2:
                    strSQL = "SELECT * from class "
                            + "WHERE subject_id = " + id + " ORDER BY " + fieldname + " ;";
                    break;
                default:
                    strSQL = "SELECT * from class "
                            + "WHERE trainer_id = " + id + " ORDER BY " + fieldname + " ;";
                    break;
            }

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Classroom sb = new Classroom();
                sb.setClass_id(rs.getInt(1));
                sb.setClass_code(rs.getString(2));
                sb.setTrainer_id(rs.getInt(3));
                sb.setSubject_id(rs.getInt(4));
                sb.setClass_year(rs.getInt(5));
                sb.setClass_term(rs.getInt(6));
                sb.setBlock5_class(rs.getInt(7));
                sb.setStatus(rs.getInt(8));

                classList.add(sb);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return classList;
    }

    public static List<Classroom> getListForSearch(String pattern, int role, int id) throws Exception {
        List<Classroom> classList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            switch (role) {
                case 1:
                    strSQL = "SELECT class_id, class_code, trainer_id, subject_id, class_year, "
                            + "class_term, block5_class, status FROM class "
                            + "where class_code like '%" + pattern + "%' "
                            + "OR trainer_id like '%" + pattern + "%' "
                            + "OR subject_id like '%" + pattern + "%' "
                            + "OR class_year like '%" + pattern + "%' "
                            + "OR class_term like '%" + pattern + "%' "
                            + "OR block5_class like '%" + pattern + "%' "
                            + "OR status like '%" + pattern + "%' ;";
                    break;
                case 2:
                    strSQL = "SELECT class_id, class_code, trainer_id, subject_id, class_year, "
                            + "class_term, block5_class, status FROM class "
                            + "where (subject_id = " + id + " ) AND ( class_code like '% " + pattern + "%' "
                            + "OR trainer_id like '%" + pattern + "%' "
                            + "OR class_year like '%" + pattern + "%' "
                            + "OR class_term like '%" + pattern + "%' "
                            + "OR block5_class like '%" + pattern + "%' "
                            + "OR status like '%" + pattern + "%') ;";
                    break;
                default:
                    strSQL = "SELECT class_id, class_code, trainer_id, subject_id, class_year, "
                            + "class_term, block5_class, status FROM class "
                            + "where (trainer_id = " + id + " ) AND ( class_code like '% " + pattern + "%' "
                            + "OR subject_id like '%" + pattern + "%' "
                            + "OR class_year like '%" + pattern + "%' "
                            + "OR class_term like '%" + pattern + "%' "
                            + "OR block5_class like '%" + pattern + "%' "
                            + "OR status like '%" + pattern + "%') ;";
                    break;
            }

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Classroom sb = new Classroom();
                sb.setClass_id(rs.getInt(1));
                sb.setClass_code(rs.getString(2));
                sb.setTrainer_id(rs.getInt(3));
                sb.setSubject_id(rs.getInt(4));
                sb.setClass_year(rs.getInt(5));
                sb.setClass_term(rs.getInt(6));
                sb.setBlock5_class(rs.getInt(7));
                sb.setStatus(rs.getInt(8));

                classList.add(sb);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return classList;
    }

    public List<UserDetails> getTrainerListFromClassDaoByID(int id) throws Exception {
        List<UserDetails> setlist = new ArrayList<>();

        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            strSQL = "select class.trainer_id, user.fullName"
                    + " from class join user "
                    + " on class.trainer_id = user.id"
                    + " where class.trainer_id = " + id + ";";

// Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);

            while (rs.next()) {
                UserDetails t = new UserDetails();
                t.setId(rs.getInt(1));
                t.setFullName(rs.getString(2));
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

    public void insertClassroomToList(Classroom sb) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into class (class_code, trainer_id, "
                + "subject_id, class_year, class_term, block5_class, status)"
                + " values ( '" + sb.getClass_code() + "', "
                + " " + sb.getTrainer_id() + ", "
                + " " + sb.getSubject_id() + ", "
                + " " + sb.getClass_year() + ", "
                + " " + sb.getClass_term() + ", "
                + " " + sb.getBlock5_class() + ", "
                + " " + sb.isStatus() + "); ";

//                + " values ( '" + sb.getClass_code()+ "', "
//                + " " + sb.getTrainer_id()+ ", "
//                + " " + sb.getSubject_id()+ ", "
//                + " " + sb.getClass_year()+ ", "
//                + " " + sb.getClass_term()+ ", "
//                + " " + sb.getBlock5_class()+ ", "
//                + " " + sb.getStatus()+ "); ";
        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateClassToList(Classroom classes) throws ClassNotFoundException,
            SQLException {

        String sql = "UPDATE class SET "
                + "class_code =?, " //1
                + "trainer_id = ?, " //2
                + "subject_id = ?, " //3
                + "class_year = ?, " //4
                + "class_term = ?, " //5
                + "block5_class = ?," //6
                + "status = ? " //7
                + "WHERE class_id = ?;"; //8

        // Lấy ra kết nối tới cơ sở dữ liệu.
        try (
                Connection connection = CommonLib.getDbConn();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //        Statement statement = connection.createStatement();
                ) {
            preparedStatement.setString(1, classes.getClass_code());
            preparedStatement.setInt(2, classes.getTrainer_id());
            preparedStatement.setInt(3, classes.getSubject_id());
            preparedStatement.setInt(4, classes.getClass_year());
            preparedStatement.setInt(5, classes.getClass_term());
            preparedStatement.setInt(6, classes.getBlock5_class());
            preparedStatement.setInt(7, classes.isStatus());
            preparedStatement.setInt(8, classes.getClass_id());

            preparedStatement.execute();

        }
    }
    //ham cua Linh cho Milestone:

    public List<Classroom> getListWithOneCondition(String dieukien) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<Classroom> classList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = null;
                strSQL = "SELECT * from class where " + dieukien + " ;";

                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    Classroom sb = new Classroom();
                    sb.setClass_id(rs.getInt(1));
                    sb.setClass_code(rs.getString(2));
                    sb.setTrainer_id(rs.getInt(3));
                    sb.setSubject_id(rs.getInt(4));
                    sb.setClass_year(rs.getInt(5));
                    sb.setClass_term(rs.getInt(6));
                    sb.setBlock5_class(rs.getInt(7));
                    sb.setStatus(rs.getInt(8));

                    classList.add(sb);
                }
                // Đóng kết nối
                conn.close();
            } catch (SQLException exp) {
                System.out.println("Exception: " + exp.getMessage());
            } catch (ClassNotFoundException exp) {
                System.out.println("Can't connect to DB: " + exp.getMessage());
            }
            return classList;
        }
    }

    //note ( không dùng do chưa tối ưu hóa được) 
    public List<Classroom> getPagination(int start) throws Exception {
        List<Classroom> classList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "SELECT * from class "
                    + "LIMIT " + start + ", 5;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Classroom sb = new Classroom();
                sb.setClass_id(rs.getInt(1));
                sb.setClass_code(rs.getString(2));
                sb.setTrainer_id(rs.getInt(3));
                sb.setSubject_id(rs.getInt(4));
                sb.setClass_year(rs.getInt(5));
                sb.setClass_term(rs.getInt(6));
                sb.setBlock5_class(rs.getInt(7));
                sb.setStatus(rs.getInt(8));

                classList.add(sb);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return classList;
    }

}
