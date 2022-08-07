/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.UserListDAO;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.mail.MessagingException;
import model.UserList;
import static view.DashBoard.UserListDAO;

/**
 *
 * @author leeph
 */
public class LoginView {

    static DashBoard sl = new DashBoard();

    /**
     * This function display login
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws MessagingException
     * @throws Exception
     */
    public static void login() throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, MessagingException, Exception {
        String email = getEmail();
        String passWord = getPassword();
        while (true) {
            sl.userNameLogin(email, passWord);

            UserListDAO usdao = new UserListDAO();
            List<UserList> userList = usdao.getList();
            for (UserList usl : userList) {
                if (usl.getEmail().equalsIgnoreCase(email)) {
                    //khai báo đối tượng current thuộc class LocalDateTime
                    LocalDateTime current = LocalDateTime.now();
                    //sử dụng class DateTimeFormatter để định dạng ngày giờ theo kiểu pattern
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    //sử dụng phương thức format() để định dạng ngày giờ hiện tại rồi gán cho chuỗi formatted
                    String formatted = current.format(formatter);
                    usl.setLastLogin(formatted);
                    UserListDAO.updateUserListToList(usl, "last_login", formatted, "string");
                }
            }
        }

    }

    private static String getEmail() {
        String username = Utility.getString("Username: ", "Wrong format.",
                Utility.REGEX_EMAIL);
        return username;
    }

    private static String getPassword() {
        String password = Utility.getString("Password: ", "Minimum eight characters, "
                + "at least one letter and one number.", "^[a-zA-Z0-9]{8,}$");
        return password;
    }
}
