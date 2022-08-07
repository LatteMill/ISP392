/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.*;
import dao.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.mail.MessagingException;

/**
 *
 * @author leeph
 */
public class DashBoard {

    static Scanner sc = new Scanner(System.in);
    static UserDAO UserDAO = new UserDAO();
    static UserListDAO UserListDAO = new UserListDAO();

    /**
     * This function allow user to change their password
     *
     * @param user
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws MessagingException
     * @throws Exception
     */
    private static void changePassword(User user) throws SQLException, NoSuchAlgorithmException, MessagingException, Exception {
        UserDAO.changePassword(user);
        LoginView.login();
    }

    /**
     * This function used for login
     *
     * @param username
     * @param password
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws MessagingException
     * @throws Exception
     */
    public static void userNameLogin(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, MessagingException, Exception {

        User user = new User();
        user.setEmail(username); //setting the username and password through the loginBean object then only you can get it in future.
        user.setPassword(password);
        UserDAO.authenticateUser(user);
        String userValidate = UserDAO.authenticateUser(user); //Calling authenticateUser function

//        UserListDAO usdao = new UserListDAO();
//        List<UserList> userList = usdao.getList();
//        for (UserList usl : userList) {
//            if (usl.getEmail().equalsIgnoreCase(username)) {
//                //khai báo đối tượng current thuộc class LocalDateTime
//                LocalDateTime current = LocalDateTime.now();
//                //sử dụng class DateTimeFormatter để định dạng ngày giờ theo kiểu pattern
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                //sử dụng phương thức format() để định dạng ngày giờ hiện tại rồi gán cho chuỗi formatted
//                String formatted = current.format(formatter);
//                usl.setLastLogin(formatted);
//                UserListDAO.updateUserListToList(usl, "last_login", formatted, "string");
//            }
//        }

        if (userValidate.equals("Admin")) //If function returns success string then user will be rooted to Home page
        {
            System.out.println("Role: Admin");
            System.out.println("============================ DASH BOARD ============================");

            System.out.println("1. User List.");
            System.out.println("2. Setting List");
            System.out.println("3. Subject List.");
            System.out.println("4. Subject Details.");
            System.out.println("5. Class List");
            System.out.println("6. Itertaion List.");
            System.out.println("7. Function List");
            System.out.println("8. Criteria List.");
            System.out.println("9. Change passowrd.");
            System.out.println("10. Log out.");
            int choice = Utility.getInt("Your choice is: ", "Please input in range.", 1, 10);
            switch (choice) {
                case 1:
                    UserListView.displayUserListFunction();
                    break;
                case 2:
                    SettingListView.displaySettingFunction();
                    break;
                case 3:
                    SubjectListView.SubjectList();
                    break;
                case 4:
                    SubjectSettingView.SubjectSettingList();
                    break;
                case 5:
                    ClassView.ClassroomListForVIP(username);

                    break;
                case 6:
                    IterationView.IterSetting();

                    break;
                case 7:
                    FunctionView.FunctionList(username);

                    break;
                case 8:
                    CriteriaView.CriteriaList(username);
                    break;
                case 9:
                    changePassword(user);
                    break;
                case 10:
                    LoginView.login();
                    break;
            }
        } else if (userValidate.equals("Author/Manager")) {
            System.out.println("Role: Author/Manager");
            System.out.println("============================ DASH BOARD ============================");
            System.out.println("1. Subject List.");
            System.out.println("2. Subject Setting List.");
            System.out.println("3. Itertaion List.");
            System.out.println("4. Criteria List.");
            System.out.println("5. Change passowrd.");
            System.out.println("6. Log out.");
            int choice = Utility.getInt("Your choice is: ", "Please input in range.", 1, 7);
            switch (choice) {
                case 1:
                    SubjectListView.SubjectList();
                    break;
                case 2:
                    SubjectSettingView.SubjectSettingList();
                    break;
                case 3:
                    IterationView.IterSetting();
                    break;
                case 4:
                    CriteriaView.CriteriaList(username);
                    break;
                case 5:
                    changePassword(user);
                    break;
                case 6:
                    LoginView.login();
                    break;
            }
        } else if (userValidate.equals("Trainer")) {
            System.out.println("Role: Trainer");
            System.out.println("============================ DASH BOARD ============================");
            System.out.println("1. Class List.");
            System.out.println("2. Milestone list.");
            System.out.println("3. Team list.");
            System.out.println("4. User list.");
            System.out.println("5. Feature list");
            System.out.println("6. Issue list");
            System.out.println("7. Tracking List");
            System.out.println("8. Member Evaluation List");
            System.out.println("9. Loc Evaluation");
            System.out.println("10. Team Evaluation List");
            System.out.println("11. Function View");
            System.out.println("11. Change passowrd.");
            System.out.println("12. Log out.");

            int choice = Utility.getInt("Your choice is: ", "Please input in range.", 1, 13);
            switch (choice) {
                case 1:
                    ClassView.ClassroomListForTrainer(username);
                    break;
                case 2:
                    MilestoneView.MilestoneList();
                    break;
                case 3:
                    TeamView.TeamList();
                    break;
                case 4:
                    ClassUserView.showClassUserList();
                    break;
                case 5:
                    FeatureView.FeatSettingTrainer();
                    break;
                case 6:
                    IssueView.IssueListForTrainer();
                    break;
                case 7:
                    TrackingView.useTrackingScreenForTrainer();
                    break;
                case 8:
                    UserEvaluationView.useUserEvaluationScreenForTrainer(username);
                    break;
                case 9:
                    LocView.LocListForTrainer(username);
                    break;
                case 10:
                    TeamEvaluationView.TeamEvaluationListForTrainer(username);
                    break;
                case 11 :
                    FunctionView.FunctionList(username);
                case 12:
                    changePassword(user);
                    break;
                case 13:
                    LoginView.login();
                    break;


            }
        } else if (userValidate.equals("Student")) {
            System.out.println("Role: Student");
            System.out.println("============================ DASH BOARD ============================");
            System.out.println("1. Change passowrd.");
            System.out.println("2. Feature List");
            System.out.println("3. Tracking List");
            System.out.println("4. Member Evaluation");
            System.out.println("5. Issue List");
            System.out.println("6. Loc Evaluation");
            System.out.println("7. Team Evaluation List");
            System.out.println("8. Function List");
            System.out.println("9. Log out.");

            int choice = Utility.getInt("Your choice is: ", "Please input in range.", 1, 9);
            switch (choice) {
                case 1:
                    changePassword(user);
                    break;
                case 2:
                    FeatureView.FeatSettingStudent(user.getEmail());
                    break;
                case 3:
                    TrackingView.useTrackingScreenForStudent(username);
                    break;
                case 4:
                    UserEvaluationView.useUserEvaluationScreenForStudent(username);
                    break;
                case 5:
                    IssueView.IssueListForStudent(username);
                    break;
                case 6:
                    LocView.LocListForStudent(username);
                    break;
                case 7:
                    TeamEvaluationView.TeamEvaluationForStudent(username);
                    break;
                case 8:
                    FunctionView.FunctionList(username);
                case 9:
                    LoginView.login();
                    break;

            }
        } else {
            //nếu người dùng quên mật khẩu
            System.out.println("============================ Forgot username/password ============================");
            System.out.println("Your choide: ");
            System.out.println("1. Enter username, password again.");
            System.out.println("2. Enter password again.");
            System.out.println("3. Change password.");
            System.out.println("4. Exit program.");
            ForgetPassword.checkUser(username, password);

        }
    }

}
