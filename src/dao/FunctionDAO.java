/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import dao.CommonLib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.List;
import model.Function;


public class FunctionDAO {



//    public static void main(String[] args) throws Exception {
//        FunctionDAO fdao = new FunctionDAO();
//        List<Function> lst = fdao.getList();
//        for (Function function : lst) {
//            System.out.println(function);
//        }
//    }

     
        public static List<Function> getFuncList() throws Exception {
            
            List<Function> funcList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = " select ft.function_id,team_name,ft.function_name, f.feature_name,ft.access_roles,ft.description,ft.complexity_id,ft.owner_id,ft.priority,ft.status from functionTable as ft\n" +
" join team as t on ft.team_id = t.team_id\n" +
" join feature as f on f.feature_id =ft.feature_id;";

                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
//int feat_id, String name,int  team_id, boolean status
//String rollNumber, String jobPofution, String company, String address, int id, int roles, int sex, boolean status) {
                while (rs.next()) {
                    Function func = new Function();
                    func.setFunction_id(rs.getInt(1));
                    func.setTeam_name(rs.getString(2));                    
                    func.setFunction_name(rs.getString(3));
                    func.setFeature_name(rs.getString(4));
                    func.setAccess_roles(rs.getString(5));
                    func.setDescription(rs.getString(6));
                    func.setComplexity_id(rs.getInt(7));
                    func.setOwner_id(rs.getInt(8));
                    func.setPriority(rs.getString(9));
                    func.setStatus(rs.getInt(10));
                    funcList.add(func);
                }
                // Đóng kết nối
                conn.close();
            } catch (SQLException exp) {
                System.out.println("Exception: " + exp.getMessage());
            } catch (ClassNotFoundException exp) {
                System.out.println("Can't connect to DB: " + exp.getMessage());
            }
            return funcList;
        }
         public List<Function> getListForSort(boolean student, int team_id, String fieldname) throws Exception {
        List<Function> iList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = null;
            if (!student) {
                strSQL = "SELECT * FROM functionTable "
                        + "ORDER BY " + fieldname + " ;";
            } else {
                strSQL = "SELECT * FROM functionTable "
                        + "WHERE team_id = " + team_id + " ORDER BY " + fieldname + " ;";
            }

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
               Function func = new Function();
                    func.setFunction_id(rs.getInt(1));
                    func.setTeam_id(rs.getInt(2));                    
                    func.setFunction_name(rs.getString(3));
                    func.setFeature_id(rs.getInt(4));
                    func.setAccess_roles(rs.getString(5));
                    func.setDescription(rs.getString(6));
                    func.setComplexity_id(rs.getInt(7));
                    func.setOwner_id(rs.getInt(8));
                    func.setPriority(rs.getString(9));
                    func.setStatus(rs.getInt(10));
                    iList.add(func);
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
  public static List<Function> getListForSearch(String pattern) throws Exception {
        List<Function> iList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "SELECT * FROM functionTable "
                    + "where team_id like '%" + pattern + "%' "
                    + "OR function_name like '%" + pattern + "%' "
                    + "OR feature_id like '%" + pattern + "%' "
                    + "OR access_roles like '%" + pattern + "%' "
                    + "OR description like '%" + pattern + "%' "
                    + "OR complexity_id like '%" + pattern + "%' "
                    + "OR owner_id like '%" + pattern + "%' "
                    + "OR priority like '%" + pattern + "%' "
                    + "OR status like '%" + pattern + "%' ; ";
                   
                    

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                 Function func = new Function();
                    func.setFunction_id(rs.getInt(1));
                    func.setTeam_name(rs.getString(2));                    
                    func.setFunction_name(rs.getString(3));
                    func.setFeature_name(rs.getString(4));
                    func.setAccess_roles(rs.getString(5));
                    func.setDescription(rs.getString(6));
                    func.setComplexity_id(rs.getInt(7));
                    func.setOwner_id(rs.getInt(8));
                    func.setPriority(rs.getString(9));
                    func.setStatus(rs.getInt(10));
                    iList.add(func);
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
        public void insertFuncToList(Function func) throws ClassNotFoundException,
                SQLException {

            // Lấy ra kết nối tới cơ sở dữ liệu.
            Connection connection = CommonLib.getDbConn();
            
            Statement statement = connection.createStatement();
            
            String sql = "insert into functionTable ( team_id ,function_name ,feature_id ,access_roles ,description ,complexity_id ,owner_id ,priority ,status)"
                    + " values (" + func.getTeam_id() + ", "
                    + " '" + func.getFunction_name()+ "' , "
                     + " " + func.getFeature_id()+ ", "
                     + " " + func.getAccess_roles() + ", "
                     + " '" + func.getDescription()+ "', "
                     + " " + func.getComplexity_id()+ ", "
                     + " " + func.getOwner_id() + ", "
                     + " " + func.getPriority() + ", "                                       
                    + " " + func.getStatus() + "); ";

            // Thực thi câu lệnh.
            // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
            int rowCount = statement.executeUpdate(sql);

            // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
        }
        
      public void updateFunctionToList(Function f) throws ClassNotFoundException,
            SQLException {

        String sql = "UPDATE functionTable SET "
                + "team_id =?, " //1
                + "function_name = ?, " //2
                + "feature_id = ?, " //3
                + "access_roles = ?, " //4
                + "description = ?, " //5
                + "complexity_id = ?," //6
                + "owner_id = ?, " //7
                + "priority = ?," //8                                        
                + "status= ? " //9
                + "WHERE function_id = ?;"; //10

        // Lấy ra kết nối tới cơ sở dữ liệu.
        try (
                Connection connection = CommonLib.getDbConn();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //        Statement statement = connection.createStatement();
                ) {
            preparedStatement.setInt(1, f.getTeam_id());
            preparedStatement.setString(2, f.getFunction_name());
            preparedStatement.setInt(3, f.getFeature_id());
            preparedStatement.setString(4, f.getAccess_roles());
            preparedStatement.setString(5, f.getDescription());
            preparedStatement.setInt(6, f.getComplexity_id());
            preparedStatement.setInt(7, f.getOwner_id());
            preparedStatement.setString(8, f.getPriority());
            preparedStatement.setInt(9, f.getStatus());
            preparedStatement.setInt(10, f.getFunction_id());

            preparedStatement.execute();

        }

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
//        int rowCount = statement.executeUpdate(sql);
        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }
        
public List<Function> getListWithOneCondition(String dieukien1) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<Function> iList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = "SELECT * FROM functionTable where " + dieukien1 + ";";

                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    Function fu = new Function();
                    fu.setFunction_id(rs.getInt(1));
                    fu.setTeam_id(rs.getInt(2));
                    fu.setFunction_name(rs.getString(3));
                    fu.setFeature_id(rs.getInt(4));
                    fu.setAccess_roles(rs.getString(5));
                    fu.setDescription(rs.getString(6));
                    fu.setComplexity_id(rs.getInt(7));
                    fu.setOwner_id(rs.getInt(8));                
                    fu.setPriority(rs.getString(9));                   
                    fu.setStatus(rs.getInt(10));
                 

                    iList.add(fu);
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
public List<Function> getList() throws Exception {
        List<Function> funcList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select * from functionTable";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
            while (rs.next()) {
                Function func = new Function();
               func.setFunction_id(rs.getInt(1));
                    func.setTeam_id(rs.getInt(2));                    
                    func.setFunction_name(rs.getString(3));
                    func.setFeature_id(rs.getInt(4));
                    func.setAccess_roles(rs.getString(5));
                    func.setDescription(rs.getString(6));
                    func.setComplexity_id(rs.getInt(7));
                    func.setOwner_id(rs.getInt(8));
                    func.setPriority(rs.getString(9));
                    func.setStatus(rs.getInt(10));
                    funcList.add(func);
            
            }
            // Đóng kết nối
            conn.close();
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        return funcList;
    }

    }

