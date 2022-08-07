package view;

import java.util.*;
import controller.*;
import dao.*;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import model.*;


public class UserRegisterView {
//ham goi: UserRegisterView.Register();
    
    static RegisterController rc = new RegisterController();
//    static ManageUser mu = new ManageUser();
    static EmailController ec = new EmailController();
    static Scanner sc = new Scanner(System.in);
    static UserDAO ud = new UserDAO();

    //hiển thị
    //create new user 
    public static void viewUserRegister() {
        System.out.println("============================ USER REGISTER ============================");
        System.out.println("1. Sign up");
        System.out.println("2. Verify email");
        System.out.println("3. Back to Home Page");
    }

    public static void Register() throws Exception {
        viewUserRegister();
        int option = Utility.getInt("OPTION: ", "WRONG", 1, 3);
        String userName, fName, email, mobile, password, jobPosition, company, address, rollNumber;
        int sex, role, status;
        boolean verify;
        switch (option) {
            case 1: {
                System.out.println("============================ SIGN UP ============================");
                        
                /*
        super(id, fullName, email, mobile, sex, roles, status, jobPosition, company, address);
        this.username = username;
        this.password = password;
                 */
                //thao tác hoàn thành profile đăng kí
                //    userName = Ultility.getString("Enter userName: ","Wrong format.", Ultility.REGEX_STRING);
                fName = Utility.getString("Enter full name: ", "Wrong format.", Utility.REGEX_STRING);
                email = Utility.getString("Enter email: ", "Wrong format.", Utility.REGEX_EMAIL);
                mobile = Utility.getString("Enter phone number: ", "Wrong format.", Utility.REGEX_PHONE);
                sex = Utility.getInt("Enter sex choice: (1.Nam; 2.Nữ; 0.Chưa rõ): ", "Please enter in range.", 0, 2);
                role = 1; //student
                status = 1; //active
                verify = false;
                jobPosition = "";
                company = "";
                address = "";
                rollNumber = "";

                password = Utility.getString("PassWord: ", "Minimum eight characters, "
                        + "at least one letter and one number.", "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

                System.out.println("... Sending captcha...\n");
                //addUser:
                int id = rc.getSize() + 1;
                //lưu email vào kho lưu trữ tạm để confirm + tạo captcha:
                //verify == false ( chưa xác minh )
                User ur = new User(password, rc.createCaptcha(), verify, fName, email, mobile, rollNumber, jobPosition, company, address, id, role, sex, status);

                //add tạm vào với verify=false
                try {
                    if (rc.addNewUser(ur) == true) {
                        String verifyString = "";
                        try {
                            //confirm email:
                            if (confirmUserEmail(email) == true) {
                                //đổi xác minh nếu confirm email thành công:
                                ur.setVerify(true);
                                verifyString = "true";
                                System.out.println("Sign up successfully!");
                            } else {
                                //giữ nguyên verify - false nếu chưa confirm email
                                ur.setVerify(false);
                                verifyString = "false";
                                System.out.println("Sign up NOT successfully!");
                            }

                            ud.updateUserToList(ur, "verify", verifyString, "boolean");
                            System.out.println("Thank you!");

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            return;
                        }
                    }
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }

            }
            break;
            case 2:
                System.out.println("============================ VERIFY ACCOUNT ============================");
                viewConfirmEmail();
                boolean option1 = true;
                do {
                    System.out.print("Enter email: ");
                    email = sc.nextLine();
                    if (email.compareToIgnoreCase("exit") == 0) {
                        System.out.println("NOT verify yet!");
                        return;
                    }
                } while (!email.matches(Utility.REGEX_EMAIL));

                User ur = rc.searchByEmail(email);
                if (ur.isVerify() == false) {
                    String verifyString = "";
                    try {
                        //confirm email:
                        if (confirmUserEmail(email) == true) {
                            //đổi xác minh nếu confirm email thành công:
                            ur.setVerify(true);
                            verifyString = "true";
                            System.out.println("Sign up successfully!");
                        } else {
                            //giữ nguyên verify - false nếu chưa confirm email
                            ur.setVerify(false);
                            verifyString = "false";
                            System.out.println("Sign up NOT successfully!");
                        }

                        ud.updateUserToList(ur, "verify", verifyString, "boolean");
                        System.out.println("Thank you!");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                } else {
                    System.out.println("User has been verified!");
                }

                break;

            case 3:
                return;

        }
    }

    public static void viewConfirmEmail() {
        System.out.println("============================ CONFIRM EMAIL ============================");
        System.out.println("* Enter '000000' to send password again!");
        System.out.println("* Enter 'exit' to go back main screen");
    }

    //gửi catpcha + confirm captcha
    public static boolean confirmUserEmail(String email) throws MessagingException, UnsupportedEncodingException, Exception {

        //tìm email tạm thời được lưu trữ
        User ur = rc.searchByEmail(email);

        //gửi captcha:
        ec.sendRandomText(ur, "Confirm your email!", "Your captcha is: ");

        //xác nhận captcha
        String input = "";
        do {
            System.out.println("Check your email and confirm captcha!\n ");
            viewConfirmEmail();
            System.out.print("Please enter captcha: ");
            input = sc.nextLine();

            if (input.compareToIgnoreCase("exit") == 0) {
                return false;
            }

            if (input.compareTo("000000") == 0) {
                //tạo captcha mới:
                String newCaptcha = rc.createCaptcha();
                ur.setCaptcha(newCaptcha);

                //update vào sql:
                ud.updateUserToList(ur, "captcha", ur.getCaptcha(), "string");
            }
        } while (!rc.confirmEmail(input, email));

        return true;
    }
    
    public static boolean checkPasswordRenew(String email) throws MessagingException, UnsupportedEncodingException, Exception {

        //tìm email tạm thời được lưu trữ
        User ur = rc.searchByEmail(email);

        //gửi captcha:
        ec.sendRandomText(ur, "Confirm your email!", "Your password is: ");

        //xác nhận captcha
        String input = "";
        do {
            System.out.println("Check your email and confirm password!\n ");
            viewConfirmEmail();
            System.out.print("Please enter password: ");
            input = sc.nextLine();

            if (input.compareToIgnoreCase("exit") == 0) {
                return false;
            }

            if (input.compareTo("000000") == 0) {
                //tạo captcha mới:
                String newCaptcha = rc.createCaptcha();
                ur.setCaptcha(newCaptcha);

                //update vào sql:
                ud.updateUserToList(ur, "password", ur.getPassword(), "string");
            }
        } while (!rc.confirmEmail(input, email));

        return true;
    }

}
