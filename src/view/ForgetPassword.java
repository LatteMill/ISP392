/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.mail.MessagingException;
import model.User;
import static view.DashBoard.UserDAO;
import static view.DashBoard.userNameLogin;

/**
 *
 * @author leeph
 */
public class ForgetPassword {
    public static void checkUser(String username, String password) throws SQLException, NoSuchAlgorithmException, MessagingException, Exception {
        int choice = Utility.getInt("Enter your choice: ", "Must be in range.", 1, 4);
        switch (choice) {
            case 1:
                username = Utility.getString("Enter username: ", "Wrong.", Utility.REGEX_EMAIL);
                password = Utility.getString("PassWord: ", "Minimum eight characters, "
                        + "at least one letter and one number.", "^[a-zA-Z0-9]{8,}$");
                userNameLogin(username, password);
                break;
            case 2:
                password = Utility.getString("PassWord: ", "Minimum eight characters, "
                        + "at least one letter and one number.", "^[a-zA-Z0-9]{8,}$");
                userNameLogin(username, password);
                break;
            case 3:
                if (checkWantToUpdate("password") == true) {
                    try {
                        username = Utility.getString("Enter your email: ", "Wrong.", Utility.REGEX_EMAIL);
                        try {
                            //thêm lựa chọn đk hoặc đăng nhập
                            //confirm email:
                            if (UserDAO.forgetPassword(username) == true) {
                                System.out.println("Change passowrd successfully!\n"
                                        + "Please login again.");
                                LoginView.login();
                            } else {
                                System.out.println("Change passowrd NOT successfully!");
                            }

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            return;
                        }
                    } catch (Exception e) {
                        System.out.println("This account doesn't exist.");
                    }

                }
                break;

            case 4:
                System.out.println("Bye");
                System.exit(0);
        }
    }
    
     /**
     * Asking user want to update or not
     *
     * @param string
     * @return
     */
    private static boolean checkWantToUpdate(String string) {
        String answer = Utility.getString("Do you want to change " + string + " ? (y/n)", "It must be y or n ", Utility.REGEX_Y_N);
        //if user want to update
        if (answer.equalsIgnoreCase("y")) {
            return true;
        }//if user don't want to update
        else {
            return false;
        }
    }
}
