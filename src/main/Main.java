/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controller.*;
import model.*;
import view.*;

/**
 *
 * @author leeph
 */
public class Main {

    public static void main(String[] args) throws Exception {
        while (true) {
            displayMenu();
            int option = Utility.getInt("OPTION: ", "WRONG", 1, 15);
            switch (option) {
                case 1:
                    //1.Login
                    LoginView.login();
                    break;
                case 2:
                    //3.Create new user
                    UserRegisterView.Register();
                    break;
                case 3:
                    System.out.println("Bye!");
                    return;
                //test    
                case 4:
                    IterationView.IterSetting();
                    break;
//                case 5:
//                    SubjectSettingView.SubjectSettingList();
                case 7:
                   IterationView.IterSetting();
                    break;
                case 6:
                    FunctionView.FunctionList("maihn@gmail.com");
                case 5:
                    IssueView.IssueListForStudent("anhknhe@gmail.com");
                    break;
            }
        }

    }

    private static void displayMenu() {
        System.out.println("================================================ HOME PAGE ================================================");
        System.out.println("WELCOME TO STUDENT PROGRAMMING MANAGEMENT\n"
                + "1.	Login\n"
                + "2.	User Registration\n"
                + "3.	Exit");
    }
}
