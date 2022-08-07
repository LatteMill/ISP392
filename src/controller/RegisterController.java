package controller;

import dao.UserDAO;
import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import model.*;

public class RegisterController {
        //Main code owner of this class: Linh


//    ArrayList<UserRegister> urList = new ArrayList<>();
    //List<User> listUser = new ArrayList<User>();
    static EmailController emailController = new EmailController();
    static UserDAO userDAO = new UserDAO();
    

        /**
     * Get a size of listUser
     * @return listUser.size()
     */
    public int getSize() throws Exception{
        List<User> listUser = userDAO.getList();
        return listUser.size();
    }
    
    //====== Add temp storage for new account ====
    public static boolean checkValidEmail(String email) throws Exception {
        List<User> listUser = userDAO.getList();

        if (listUser.size() > 0) {
            for (int i = 0; i < listUser.size(); i++) {
                if (listUser.get(i).getEmail().compareTo(email) == 0) {
                    return true; // bị trùng
                }
            }
        }

        return false; //không bị trùng
    }

    /*
    public void addTempEmail(String email, String password) throws Exception {
        if (!checkValidEmail(email)) {
            //tao captcha luu cho temp email
            String captcha = createCaptcha();

            //luu vao
            urList.add(new UserRegister(captcha, email, password));
        } else {
            throw new Exception("Email existed!");
        }
    }
     */
    public static User searchByEmail(String email) throws Exception {
        List<User> listUser = userDAO.getList();

        for (int i = 0; i < listUser.size(); i++) {
            if (listUser.get(i).getEmail().compareToIgnoreCase(email) == 0) {
                return listUser.get(i);
            }
        }
        return null;
    }

    public int searchIndex(String email) throws Exception {
        List<User> listUser = userDAO.getList();
        for (int i = 0; i < listUser.size(); i++) {
            if (listUser.get(i).getEmail().compareToIgnoreCase(email) == 0) {
                return i;
            }
        }
        return -1;
    }

    //======= Confirm Email =====
    //tong ki tu de random
    private final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private final String digits = "0123456789"; // 0-9
    private final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;

    //ham random
    private Random generator = new Random();

    //method random
    public int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

    //update captcha mới cho User cho email:
    /*
    public User updateCaptcha(String email) throws Exception {
        //tim ur
        User ur = searchByEmail(email);

        // index object   |  new obj ( tao moi captcha , email obj, password obj)
        //tạo captcha mới:
        String newCaptcha = createCaptcha();

        ur.setCaptcha(newCaptcha);
        
        return ur;
    }*/
    //case: no "000000"
    public boolean confirmEmail(String enter, String email) throws MessagingException, UnsupportedEncodingException, Exception {
        //tìm đối tượng trong kho nhớ tạm:
        User ur = searchByEmail(email);

        //kiểm tra xem captcha nhập vào có đúng không
        if (enter.compareTo(ur.getCaptcha()) == 0) {
            //    System.out.println(ur.isVerify());
            System.out.println("Correct captcha!");
            ur.setVerify(true);
            //    System.out.println(ur.isVerify());
            return true;
        }

        //gui captcha mới
        if (enter.compareTo("000000") == 0) {
            emailController.sendRandomText(ur, "Captcha to confirm email!", "Your captcha is: ");
            System.out.println("Sent new captcha!");
            return false;
        }

        if (enter.compareTo(ur.getCaptcha()) != 0 && enter.compareTo("000000") != 0) {
            System.out.println("Incorrect captcha!");
            return false;
        }

        return false;
    }
    

    //tao captcha
    public String createCaptcha() {
        StringBuilder string = new StringBuilder();
        //gioi han 6 ky tu
        for (int i = 0; i < 6; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            string.append(ch);
        }
        //chuyen string builder sang string
        return string.toString();
    }

     public static String generateRandomPassword(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi"
                + "jklmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder string = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            string.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return string.toString();
    }
    
    //======== Add new account =====
    public static boolean addNewUser(User ur) throws Exception {
        List<User> listUser = userDAO.getList();
        try {
            if (checkValidEmail(ur.getEmail()) == false) {//neu khong bi trung
                listUser.add(ur);
                userDAO.insertUserToList(ur);
                if (ur.isVerify() == true) {
                    System.out.println("Sign up successfully!");
                }
                return true;
            } else { //neu bi trung
                if (checkEmailVerify(ur.getEmail()) == true) {
                    throw new Exception("Email existed! Please use another email!");
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean checkEmailVerify(String email) throws Exception {
        List<User> listUser = userDAO.getList();
        for (int i = 0; i < listUser.size(); i++) {
            if ((listUser.get(i).getEmail().compareTo(email) == 0)
                    && (listUser.get(i).isVerify() == false)) {
                return false; //chưa xác minh
            }
        }
        return true; //đã xác minh
    }
    
    
    

}
