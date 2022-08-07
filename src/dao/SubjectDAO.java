package dao;

import java.sql.*;
import java.util.*;
import model.*;
import java.security.*;
import javax.xml.bind.DatatypeConverter;

public class SubjectDAO {

    public static List<Subject> getList() throws Exception {
        List<Subject> subjectList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select * from subject;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectID(rs.getInt(1));
                subject.setSubjectCode(rs.getString(2));
                subject.setSubjectName(rs.getString(3));
                subject.setAuthor_id(rs.getInt(4));
                subject.setStatus(rs.getBoolean(5));
                subject.setDescription(rs.getString(6));

                subjectList.add(subject);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can'subject connect to DB: " + exp.getMessage());
        }
        return subjectList;
    }

    public List<Subject> getListWithOneCondition(String tencot1, String dieukien1, String kieudulieu1) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<Subject> subjectList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = null;
                if (kieudulieu1.compareToIgnoreCase("string") == 0) {
                    strSQL = "Select * from subject where " + tencot1 + " = '" + dieukien1 + "' ;";
                }
                if (kieudulieu1.compareToIgnoreCase("int") == 0) {
                    strSQL = "Select * from subject where " + tencot1 + " = " + dieukien1 + " ;";
                }
                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    Subject subject = new Subject();
                    subject.setSubjectID(rs.getInt(1));
                    subject.setSubjectCode(rs.getString(2));
                    subject.setSubjectName(rs.getString(3));
                    subject.setAuthor_id(rs.getInt(4));
                    subject.setStatus(rs.getBoolean(5));
                    subject.setDescription(rs.getString(6));

                    subjectList.add(subject);
                }
                // Đóng kết nối
                conn.close();
            } catch (SQLException exp) {
                System.out.println("Exception: " + exp.getMessage());
            } catch (ClassNotFoundException exp) {
                System.out.println("Can'subject connect to DB: " + exp.getMessage());
            }
            return subjectList;
        }
    }

    public List<Subject> getListWithOneCondition(String dieukien1) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<Subject> subjectList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = null;
                strSQL = "Select * from subject where " + dieukien1 + " ;";
                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    Subject subject = new Subject();
                    subject.setSubjectID(rs.getInt(1));
                    subject.setSubjectCode(rs.getString(2));
                    subject.setSubjectName(rs.getString(3));
                    subject.setAuthor_id(rs.getInt(4));
                    subject.setStatus(rs.getBoolean(5));
                    subject.setDescription(rs.getString(6));

                    subjectList.add(subject);
                }
                // Đóng kết nối
                conn.close();
            } catch (SQLException exp) {
                System.out.println("Exception: " + exp.getMessage());
            } catch (ClassNotFoundException exp) {
                System.out.println("Can'subject connect to DB: " + exp.getMessage());
            }
            return subjectList;
        }
    }

    public List<Subject> getListWithTwoCondition(String tencot1, int dieukien1, String kieudulieu1, String tencot2, String dieukien2, String kieudulieu2) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<Subject> subjectList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = null;
                if (kieudulieu1.compareToIgnoreCase("string") == 0) {
                    strSQL = "Select * from subject where " + tencot1 + " = '" + dieukien1 + "' ";
                }
                if (kieudulieu1.compareToIgnoreCase("int") == 0) {
                    strSQL = "Select * from subject where " + tencot1 + " = " + dieukien1 + " ";
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
                    Subject subject = new Subject();
                    subject.setSubjectID(rs.getInt(1));
                    subject.setSubjectCode(rs.getString(2));
                    subject.setSubjectName(rs.getString(3));
                    subject.setAuthor_id(rs.getInt(4));
                    subject.setStatus(rs.getBoolean(5));
                    subject.setDescription(rs.getString(6));

                    subjectList.add(subject);
                }
                // Đóng kết nối
                conn.close();
            } catch (SQLException exp) {
                System.out.println("Exception: " + exp.getMessage());
            } catch (ClassNotFoundException exp) {
                System.out.println("Can'subject connect to DB: " + exp.getMessage());
            }
            return subjectList;
        }
    }

    public void insertSubjectToList(Subject subject) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into subject (subject_code, subject_name, status, author_id, description)"
                + " values ( '" + subject.getSubjectCode() + "', "
                + " '" + subject.getSubjectName() + "', "
                + " " + subject.isStatus() + ", "
                + " " + subject.getAuthor_id() + ", "
                + " '" + subject.getDescription() + "'); ";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateSubjectToList(Subject subject) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "UPDATE subject SET subject_code= '"+subject.getSubjectCode()+"',"
                + "subject_name= '"+subject.getSubjectName()+"', "
                + "status= "+subject.isStatus()+", "
                + "author_id= "+subject.getAuthor_id()+", "
                + "description= '"+subject.getDescription()+"' "
                + " where subject_id = "+subject.getSubjectID()+"; ";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    //Ham cua phuoc cho class
    public List<Subject> getSubjectListFromClassDaoByAuthorID(int id) throws Exception {
        List<Subject> setlist = new ArrayList<>();

        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            strSQL = "select class.class_id, class.class_code, user.fullname, subject.subject_code, class.class_year, class.class_term, "
                    + "class.block5_class, class.status "
                    + "from subject join class "
                    + "on subject.subject_id = class.subject_id "
                    + "join user on subject.author_id = user.id "
                    + "where class.trainer_id = " + id + ";";

// Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectID(rs.getInt(1));
                subject.setSubjectCode(rs.getString(2));
                subject.setSubjectName(rs.getString(3));
                setlist.add(subject);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can'subject connect to DB: " + exp.getMessage());
        }
        //    return setlist;
        return setlist;

    }

    public List<Subject> getSearchList(String pattern) {
         //, String tencot2, String dieukien2, String kieudulieu2
            List<Subject> subjectList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = null;
                strSQL = "Select * from subject where subject_code like  '%"+pattern+"%' or "
                        + "subject_name like '%"+pattern+"%' or "
                        + "author_id like '%"+pattern+"%' or "
                        + "status like '%"+pattern+"%' or "
                        + "description like '%"+pattern+"%';";
                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    Subject subject = new Subject();
                    subject.setSubjectID(rs.getInt(1));
                    subject.setSubjectCode(rs.getString(2));
                    subject.setSubjectName(rs.getString(3));
                    subject.setAuthor_id(rs.getInt(4));
                    subject.setStatus(rs.getBoolean(5));
                    subject.setDescription(rs.getString(6));

                    subjectList.add(subject);
                }
                // Đóng kết nối
                conn.close();
            } catch (SQLException exp) {
                System.out.println("Exception: " + exp.getMessage());
            } catch (ClassNotFoundException exp) {
                System.out.println("Can'subject connect to DB: " + exp.getMessage());
            }
            return subjectList;
        }
    
    
}
