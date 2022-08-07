/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.*;
import dao.*;
import java.util.List;
import model.*;

/**
 *
 * @author admin
 */
public class UserEvaluationView {

    static TeamEvaluationController teamEvaluationController = new TeamEvaluationController();
    static TeamEvaluationDAO teamEvaluationDAO = new TeamEvaluationDAO();

    static UserListController userListController = new UserListController();
    static UserDAO userDAO = new UserDAO();

    static TeamController teamController = new TeamController();
    static TeamDAO teamDAO = new TeamDAO();

    static CriteriaController criteriaController = new CriteriaController();
    static CriteriaDAO criteriaDAO = new CriteriaDAO();

    static ClassUserDAO classUserDAO = new ClassUserDAO();
    static ClassUserController classUserController = new ClassUserController();

    static UserEvaluationController userEvalController = new UserEvaluationController();

    static ClassController classController = new ClassController();

    public static void displayTrainerChoice() {
        System.out.println("                                                    MEMBER EVALUATION SCREEN");
        System.out.println("1. Show list");
        System.out.println("2. Search user evaluation");
        System.out.println("3. Sort user evaluation");
        System.out.println("4. Add new user evaluation");
        System.out.println("5. Update user evaluation");
        System.out.println("6. Back to DashBoard");
    }

    public static void displayStudentChoice() {
        System.out.println("                                                    MEMBER EVALUATION SCREEN");
        System.out.println("1. Show list");
        System.out.println("2. Search user evaluation");
        System.out.println("3. Sort user evaluation");
        System.out.println("4. Back to DashBoard");
    }

    public static void displaySearchChoice() {
        System.out.println("                                                    SEARCH MEMBER EVALUATION");
        System.out.println("1. Search by member_eval_id");
        System.out.println("2. Search by evaluation_id");
        System.out.println("3. Search by criteria_id");
        System.out.println("4. Search by user name");
        System.out.println("5. Back ");
    }

    public static void displaySortChoice() {
        System.out.println("                                                    SORT MEMBER EVALUATION");
        System.out.println("1. Sort by member_eval_id");
        System.out.println("2. Sort by evaluation_id");
        System.out.println("3. Sort by criteria_id");
        System.out.println("4. Sort by name");
        System.out.println("5. Sort by grade ");
        System.out.println("6. Back");

    }

    public static void useUserEvaluationScreenForStudent(String username) throws Exception {
        //lấy user_id để phân quyền
        int user_id = userListController.searchUserIDbyEmailInSublist(userDAO.getListWithOneCondition(" email like '" + username + "'"), username);
        User u = userListController.searchUserByID(user_id);
        List<ClassUser> classUser = classUserController.searchClassUserByUserID(user_id);
        if (classUser == null) {
            System.out.println("You do not have data in this list!!!");
        } else {
            //show class id user can work on...
            ClassUserController.pageOfDisplay(classUser);
            int class_id;
            do {
                class_id = Utility.getInt("Enter class that you want working on: ", "It must be number!!!", 1, Integer.MAX_VALUE);
            } while (!classUserController.checkClassUserExistByClassID(class_id, classUser));
//            if (classUserController.checkClassUserExistByClassID(user_id, classUser) == false) {
//                System.out.println("CLASS ID MUST BE SHOWED IN LIST ABOVE");
//            }
            if (userEvalController.showUserEvalByUserID(user_id)) {

                int option = 0;
                while (option != 4) {
                    displayStudentChoice();
                    option = Utility.getInt("Enter your choice: ", "It must be number!!!", 1, 4);
                    switch (option) {
                        case 1:
                            userEvalController.showList();
                            break;
                        case 2:
                            displaySearchChoice();
                            int searchChoice = Utility.getInt("Enter choice: ", "It must be number!!!", 1, 5);
                            if (searchChoice != 5) {
                                switch (searchChoice) {
                                    case 1:
                                        userEvalController.searchByMember_eval_id(class_id);
                                        break;
                                    case 2:
                                        userEvalController.searchByEvaluation_id(class_id);
                                        break;
                                    case 3:
                                        userEvalController.searchByCriteria_id(class_id);
                                        break;
                                    case 4:
                                        userEvalController.searchUserEvalByName(class_id);
                                        break;

                                }

                            } else {
                                return;
                            }

                            break;
                        case 3:
                            displaySortChoice();
                            int sortChoice = Utility.getInt("Enter choice: ", "It must be number!!!", 1, 6);
                            if (sortChoice != 6) {
                                switch (sortChoice) {
                                    case 1:
                                        userEvalController.sortByMember_eval_id(userEvalController.showUserEvalByClassID(class_id));
                                        break;
                                    case 2:
                                        userEvalController.sortByEvaluation_id(userEvalController.showUserEvalByClassID(class_id));
                                        break;
                                    case 3:
                                        userEvalController.sortByCriteria_id(userEvalController.showUserEvalByClassID(class_id));
                                        break;
                                    case 4:
                                        userEvalController.sortByName(userEvalController.showUserEvalByClassID(class_id));
                                        break;
                                    case 5:
                                        userEvalController.sortByTotalGrade(userEvalController.showUserEvalByClassID(class_id));
                                        break;
                                }

                            } else {
                                return;
                            }
                            break;
                        case 4:
                            return;
                    }
                }
            }
        }
    }

    public static void useUserEvaluationScreenForTrainer(String username) throws Exception {
        //lấy user_id để phân quyền
        int user_id = userListController.searchUserIDbyEmailInSublist(userDAO.getListWithOneCondition(" email like '" + username + "'"), username);
        User u = UserListController.searchUserByID(user_id);
        Classroom classTrainer = ClassController.searchByTrainerID(user_id);
//        System.out.println(classTrainer);

        if (classTrainer == null) {
            System.out.println("You do not have any class in this term!!!");
        } else {
            int trainer_id = classTrainer.getTrainer_id();
            List<Classroom> listSearch = classController.searchClassByTrainerID(user_id);

            if (listSearch.size() > 0) {

                int class_id = Utility.getInt("Enter class that you want working on: ", "It must be number!!!", 1, Integer.MAX_VALUE);
                if (userEvalController.showUserEvalByClassID(class_id) != null) {

                    int option = 0;
                    while (option != 6) {
                        displayTrainerChoice();
                        option = Utility.getInt("Enter your choice: ", "It must be number!!!", 1, 6);
                        switch (option) {
                            case 1:
                                userEvalController.showList();
                                break;
                            case 2:
                                displaySearchChoice();
                                int searchChoice = Utility.getInt("Enter choice: ", "It must be number!!!", 1, 5);
                                if (searchChoice != 5) {
                                    switch (searchChoice) {
                                        case 1:

                                            userEvalController.searchByMember_eval_id(class_id);
                                            break;
                                        case 2:
                                            userEvalController.searchByEvaluation_id(class_id);
                                            break;
                                        case 3:
                                            userEvalController.searchByCriteria_id(class_id);
                                            break;
                                        case 4:
                                            userEvalController.searchUserEvalByName(class_id);
                                            break;

                                    }

                                } else {
                                    return;
                                }

                                break;
                            case 3:
                                displaySortChoice();
                                int sortChoice = Utility.getInt("Enter choice: ", "It must be number!!!", 1, 6);
                                if (sortChoice != 6) {
                                    switch (sortChoice) {
                                        case 1:
                                            userEvalController.sortByMember_eval_id(userEvalController.showUserEvalByClassID(class_id));
                                            break;
                                        case 2:
                                            userEvalController.sortByEvaluation_id(userEvalController.showUserEvalByClassID(class_id));
                                            break;
                                        case 3:
                                            userEvalController.sortByCriteria_id(userEvalController.showUserEvalByClassID(class_id));
                                            break;
                                        case 4:
                                            userEvalController.sortByName(userEvalController.showUserEvalByClassID(class_id));
                                            break;
                                        case 5:
                                            userEvalController.sortByTotalGrade(userEvalController.showUserEvalByClassID(class_id));
                                            break;
                                    }

                                } else {
                                    return;
                                }
                                break;
                            case 4:
                                userEvalController.addNewMemberEval(trainer_id, class_id);
                                break;
                            case 5:
                                userEvalController.updateMemberEval(trainer_id);
                                break;
                            case 6:
                                return;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        TH1: trainer co trong list
        useUserEvaluationScreenForTrainer("dunglk@fpt.com");
        //TH2: trainer khong co trong list
//        useUserEvaluationScreenForTrainer("honghanhhng@gmail.com");

//        //TH1: student co trong list
//        useUserEvaluationScreenForStudent("kienntvn@gmail.com");

        //TH2: student khong co trong list
//        useUserEvaluationScreenForStudent("lienngo@gmail.com");
    }

}
