package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class IterationDAO {

    public List<Iteration> getIterList() throws Exception {

        List<Iteration> iterList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select * from iteration;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, boolean status) {
            while (rs.next()) {
                Iteration iter = new Iteration();
                iter.setIteration_id(rs.getInt(1));
                iter.setIteration_name(rs.getString(2));
                iter.setSubject_id(rs.getInt(3));
                iter.setDuration(rs.getDouble(4));
                iter.setStatus(rs.getInt(5));

                iterList.add(iter);
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return iterList;
    }

    public void insertIterToList(Iteration iter) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into iteration (iteration_name,subject_id,duration, status)"
                + " values ( '" + iter.getIteration_name() + "', "
                + " " + iter.getSubject_id() + ", "
                + " " + iter.getDuration() + ", "

                + " " + iter.getStatus() + "); ";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateIterToList(Iteration iter, String tencot, String giatriUpdate, String kieudulieu) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "";
        if (kieudulieu.compareToIgnoreCase("string") == 0) {
            sql = "UPDATE iteration SET " + tencot + " = '" + giatriUpdate + "' WHERE iteration_id = " + iter.getIteration_id() + ";";
        }


        if (kieudulieu.compareToIgnoreCase("boolean") == 0 || kieudulieu.compareToIgnoreCase("int") == 0 || kieudulieu.compareToIgnoreCase("double") == 0) {

            sql = "UPDATE iteration SET " + tencot + " = " + giatriUpdate + " WHERE iteration_id = " + iter.getIteration_id() + ";";
        }

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateIterToListDouble(Iteration iter, String tencot, double giatriUpdate, String kieudulieu) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "";
        sql = "UPDATE iteration SET " + tencot + " = '" + giatriUpdate + "' WHERE iteration_id = '" + iter.getIteration_id() + "';";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateIterToListint(Iteration iter, String tencot, int giatriUpdate, String kieudulieu) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "";
        sql = "UPDATE iteration SET " + tencot + " = " + giatriUpdate + " WHERE iteration_id = " + iter.getIteration_id() + ";";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }



    //ham cua Linh cho Milestone:
    public List<Iteration> getListWithOneCondition(String dieukien) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<Iteration> iterList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = null;
                strSQL = "Select * from iteration where " + dieukien + " ;";

                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    Iteration iter = new Iteration();
                    iter.setIteration_id(rs.getInt(1));
                    iter.setIteration_name(rs.getString(2));
                    iter.setSubject_id(rs.getInt(3));
                    iter.setDuration(rs.getDouble(4));
                    iter.setStatus(rs.getInt(5));

                    iterList.add(iter);
                }
                // Đóng kết nối
                conn.close();
            } catch (SQLException exp) {
                System.out.println("Exception: " + exp.getMessage());
            } catch (ClassNotFoundException exp) {
                System.out.println("Can't connect to DB: " + exp.getMessage());
            }
            return iterList;
        }
    }

}
