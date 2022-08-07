package dao;

import controller.EmailController;
import controller.RegisterController;
import java.sql.*;
import java.util.*;
import model.*;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.DatatypeConverter;
import sun.misc.BASE64Encoder;

public class UserDAO {

    Connection conn = null;
    Statement statement = null;
    ResultSet resultSet = null;

    private static final String ALGORITHM = "AES";
    static RegisterController rc = new RegisterController();
    static Scanner sc = new Scanner(System.in);
    private static final String KEY = "1Hbfh667adfDEJ78";
    static EmailController ec = new EmailController();
    static UserDAO ud = new UserDAO();

    public List<User> getList() throws Exception {

        List<User> userList = new ArrayList<>();
        try {
            Connection conn = CommonLib.getDbConn();
            Statement statement = conn.createStatement();
            String strSQL = "Select * from user;";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(strSQL);
//String password, String captcha, boolean verify, String fullName, String email, String mobile, 
//String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, int status) {
            while (rs.next()) {
                User ur = new User();
                ur.setId(rs.getInt(1));
                ur.setEmail(rs.getString(2));
                ur.setPassword(rs.getString(3));
                ur.setCaptcha(rs.getString(4));
                ur.setVerify(rs.getBoolean(5));
                ur.setFullName(rs.getString(6));
                ur.setRollNumber(rs.getString(7));
                ur.setMobile(rs.getString(8));
                ur.setSex(rs.getInt(9));
                ur.setRoles(rs.getInt(10));
                ur.setStatus(rs.getInt(11));

                ur.setJobPosition(rs.getString(12));
                ur.setCompany(rs.getString(13));
                ur.setAddress(rs.getString(14));

                userList.add(ur);
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

    public void insertUserToList(User ur) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "insert into user (email, password, captcha, verify, fullName,mobile, sex, roles, status, jobPosition, company, address)"
                + " values ( '" + ur.getEmail() + "', "
                + " '" + ur.getPassword() + "', "
                + " '" + ur.getCaptcha() + "', "
                + " " + ur.isVerify() + ", "
                + " '" + ur.getFullName() + "', "
                + " '" + ur.getMobile() + "', "
                + " " + ur.getSex() + ", "
                + " " + ur.getRoles() + ", "
                + " " + ur.getStatus() + ", "
                + " '" + ur.getJobPosition() + "', "
                + " '" + ur.getCompany() + "', "
                + " '" + ur.getAddress() + "'); ";

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public void updateUserToList(User ur, String tencot, String giatriUpdate, String kieudulieu) throws ClassNotFoundException,
            SQLException {

        // Lấy ra kết nối tới cơ sở dữ liệu.
        Connection connection = CommonLib.getDbConn();

        Statement statement = connection.createStatement();

        String sql = "";
        if (kieudulieu.compareToIgnoreCase("string") == 0) {
            sql = "UPDATE user SET " + tencot + " = '" + giatriUpdate + "' WHERE email = '" + ur.getEmail() + "';";
        }

        if (kieudulieu.compareToIgnoreCase("boolean") == 0 || kieudulieu.compareToIgnoreCase("int") == 0) {
            sql = "UPDATE user SET " + tencot + " = '" + giatriUpdate + " WHERE email = '" + ur.getEmail() + "';";
        }

        // Thực thi câu lệnh.
        // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
        int rowCount = statement.executeUpdate(sql);

        // In ra số dòng được trèn vào bởi câu lệnh trên.
//        System.out.println("Row Count affected = " + rowCount);
    }

    public List<User> getListWithOneCondition(String dieukien1) throws Exception {
        {
            //, String tencot2, String dieukien2, String kieudulieu2
            List<User> sbList = new ArrayList<>();
            try {
                Connection conn = CommonLib.getDbConn();
                Statement statement = conn.createStatement();
                String strSQL = null;
                strSQL = "Select * from user where " + dieukien1 + ";";

                // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(strSQL);
                while (rs.next()) {
                    User ur = new User();
                    ur.setId(rs.getInt(1));
                    ur.setEmail(rs.getString(2));
                    ur.setPassword(rs.getString(3));
                    ur.setCaptcha(rs.getString(4));
                    ur.setVerify(rs.getBoolean(5));
                    ur.setFullName(rs.getString(6));
                    ur.setRollNumber(rs.getString(7));
                    ur.setMobile(rs.getString(8));
                    ur.setSex(rs.getInt(9));
                    ur.setRoles(rs.getInt(10));
                    ur.setStatus(rs.getInt(11));

                    ur.setJobPosition(rs.getString(12));
                    ur.setCompany(rs.getString(13));
                    ur.setAddress(rs.getString(14));

                    sbList.add(ur);
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

    /*

/**
 *
 * @author leeph
     */

    private static Key generateKey() {
        Key key = new SecretKeySpec(UserDAO.KEY.getBytes(), UserDAO.ALGORITHM);
        return key;
    }

    public static String encrypt(String value) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(UserDAO.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
        return encryptedValue64;
    }

    /**
     * Authenticate user's login
     * @param user
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws MessagingException
     * @throws Exception 
     */
    public String authenticateUser(User user) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, MessagingException, Exception {
        String userName = user.getEmail();
        String passWord = user.getPassword();

        String userNameDB = "";
        String passwordDB = "";
        int roleDB;

        try {

            conn = CommonLib.getDbConn(); //establishing connnection
            statement = conn.createStatement(); //Statement is used to write queries.
            resultSet = statement.executeQuery("select email, password, roles from user");
            while (resultSet.next()) // Until next row is present otherwise it retusern false
            {
                userNameDB = resultSet.getString("email"); //fetch the values present in database
                passwordDB = resultSet.getString("password");
                roleDB = resultSet.getInt("roles");
                if (userName.equals(userNameDB) && UserDAO.encrypt(passWord).equals(UserDAO.encrypt(passwordDB)) && roleDB == 1) {
                    conn.close();
                    return "Admin"; ////If the user entered values are already present in database, which means user has already registered so I will retusern SUCCESS message.
                } else if (userName.equals(userNameDB) && UserDAO.encrypt(passWord).equals(UserDAO.encrypt(passwordDB)) && roleDB == 2) {
                    conn.close();
                    return "Author/Manager";
                } else if (userName.equals(userNameDB) && UserDAO.encrypt(passWord).equals(UserDAO.encrypt(passwordDB)) && roleDB == 3) {
                    conn.close();
                    return "Trainer";
                } else if (userName.equals(userNameDB) && UserDAO.encrypt(passWord).equals(UserDAO.encrypt(passwordDB)) && roleDB == 4) {
                    conn.close();
                    return "Student";
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Invalid user credentials"; // Just retuserning appropriate message otherwise
    }

    /**
     * Change user's password
     * @param user
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws MessagingException
     * @throws Exception 
     */
    public String changePassword(User user) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, MessagingException, Exception {
        String userName = user.getEmail();

        String userNameDB = "";
        String passwordDB = "";

        try {

            conn = CommonLib.getDbConn(); //establishing connnection
            statement = conn.createStatement(); //Statement is used to write queries.
            resultSet = statement.executeQuery("select email,password from user");
            System.out.print("Enter your old password: ");
            String passWord = sc.nextLine();

            while (resultSet.next()) // Until next row is present otherwise it retusern false
            {
                userNameDB = resultSet.getString("email"); //fetch the values present in database
                passwordDB = resultSet.getString("password");
                if (userName.equals(userNameDB) && UserDAO.encrypt(passWord).equals(UserDAO.encrypt(passwordDB))) {
                    conn = CommonLib.getDbConn();
                    try {
                        //update vào sql:
                        System.out.print("Enter your new password: ");
                        String newpassword = sc.nextLine();
                        user.setPassword(newpassword);

                        ud.updateUserToList(user, "password", user.getPassword(), "string");
                        System.out.println("Change passowrd sucessfully");
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                } else if (userName.equals(userNameDB) && !UserDAO.encrypt(passWord).equals(UserDAO.encrypt(passwordDB))) {
                    System.out.println("Wrong old passowrd.\nPlease enter again. ");
                    changePassword(user);
                    System.out.println("Change passowrd sucessfully");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Invalid user credentials"; // Just retuserning appropriate message otherwise
    }

    public static User searchUserByUserName(String username) throws Exception {
        List<User> sbList = ud.getList();
        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getEmail().compareToIgnoreCase(username) == 0) {
                    return sbList.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Reset password
     * @param userName
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws MessagingException
     * @throws Exception 
     */
    public boolean forgetPassword(String userName) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, MessagingException, Exception {
        User us = searchUserByUserName(userName);
        String userNameDB = "";
        conn = CommonLib.getDbConn(); //establishing connnection
        statement = conn.createStatement(); //Statement is used to write queries.
        resultSet = statement.executeQuery("select email, password from user");
        try {
            while (resultSet.next()) // Until next row is present otherwise it retusern false
            {
                userNameDB = resultSet.getString("email"); //fetch the values present in database
                if (userName.equals(userNameDB)) {
                    conn = CommonLib.getDbConn();
                    try {
                       ec.sendRandomPassword(userNameDB);
                        //    System.out.println("Gui mail thanh cong");

                        //xác nhận password
                        System.out.println("Check your email and confirm password!\n ");
                        //update vào sql:
                        ud.updateUserToList(us, "password", us.getPassword(), "string");
                        return true;
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                }

            }
        } catch (SQLException exp) {
            System.out.println("Exception: " + exp.getMessage());
        } catch (ClassNotFoundException exp) {
            System.out.println("Can't connect to DB: " + exp.getMessage());
        }
        conn.close();
        return false;
    }

    public void enterPasswordAgain() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
