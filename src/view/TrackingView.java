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
public class TrackingView {

    static TrackingDAO trackingDAO = new TrackingDAO();
    static UserListDAO userListDAO = new UserListDAO();
    static ClassUserDAO classUserDAO = new ClassUserDAO();
    static TeamDAO teamDAO = new TeamDAO();
    static FunctionDAO functionDAO = new FunctionDAO();
    static FunctionController functionController = new FunctionController();
    static TeamController teamController = new TeamController();
    static ClassDAO ClassDAO = new ClassDAO();
    static MilestoneController milestoneController = new MilestoneController();
    static MilestoneDAO milestoneDAO = new MilestoneDAO();
    static UserListController userListController = new UserListController();
    static UserDAO userDAO = new UserDAO();
    static TrackingController trackingController = new TrackingController();

    public static void displayFeatureForStudent() {
        System.out.println("                                                    TRACKING SCREEN");
        System.out.println("1. Show tracking list");
        System.out.println("2. Search tracking list");
        System.out.println("3. Sort tracking list");
        System.out.println("4. Add new tracking details to list");
        System.out.println("5. Update tracking");
        System.out.println("6. Delete tracking");
        System.out.println("7. Change status");
        System.out.println("8. Back to DashBoard");
    }

    public static void displayFeatureForTrainer() {
        System.out.println("                                                    TRACKING SCREEN");
        System.out.println("1. Show tracking list");
        System.out.println("2. Search tracking list");
        System.out.println("3. Sort tracking list");
        System.out.println("4. Back to DashBoard");
    }

    public static void displayChoice() {
        System.out.println("                                                    TRACKING SCREEN");
        System.out.println("1. Show tracking list");
        System.out.println("2. Search tracking list");
        System.out.println("3. Sort tracking list");
        System.out.println("4. Back to DashBoard");
    }

    public static void displaySearchScreen() {
        System.out.println("                                                    SEARCH TRACKING");
        System.out.println("1. Search by tracking ID                            6. Seach by assignee ID");
        System.out.println("2. Search by team ID                                7. Seach by status");
        System.out.println("3. Search by milestone ID                           8. Back to Tracking");
        System.out.println("4. Seach by function ID");
        System.out.println("5. Search by assigner ID");

    }

    public static void displaySortScreen() {
        System.out.println("                                                    SORT TRACKING");
        System.out.println("1. Sort by tracking ID                            6. Sort by assignee ID");
        System.out.println("2. Sort by team ID                                7. Sort by status");
        System.out.println("3. Sort by milestone ID                           8. Back to Tracking");
        System.out.println("4. Sort by function ID");
        System.out.println("5. Sort by assigner ID");
    }

    public static void useTrackingScreenForTrainer() throws Exception {
        int option = 0;
        while (option != 4) {
            displayFeatureForTrainer();
            option = Utility.getInt("Enter your choice: ", "It must be number!!!", 1, 4);
            switch (option) {
                //SHOW TRACKING LIST
                case 1:
                    trackingController.showTrackingList();
                    break;
                //SEARCH TRACKING
                case 2:
                    displaySearchScreen();
                    int choice = Utility.getInt("Enter your choice: ", "It must be number!!!", 1, 8);
                    if (choice == 8) {
                        return;
                    } else {
                        trackingController.searchTrackingByFilter(choice);
                    }
                    break;
                //SORT TRACKING
                case 3:
                    displaySortScreen();
                    int choiceSort = Utility.getInt("Enter your choice: ", "It must be number!!!", 1, 8);
                    if (choiceSort == 8) {
                        return;
                    } else {
                        trackingController.sortTrackingByFilter(choiceSort);
                    }
                    break;
                case 4:
                    return;
            }
        }
    }

    public static void useTrackingScreenForStudent(String email) throws Exception {
        int user_id = userListController.searchUserIDbyEmailInSublist(userDAO.getListWithOneCondition(" email like '" + email + "'"), email);
        int teamID = TrackingController.getTeamIDForTracking(email, user_id);
//        User u = userListController.searchUserByID(user_id);
        System.out.println("Your Team Tracking is...");
        trackingController.showTeamTracking(teamID);
        int option = 0;
        while (option != 8) {
            displayFeatureForStudent();
            option = Utility.getInt("Enter your choice: ", "It must be number!!!", 1, 8);
            switch (option) {
                //SHOW TRACKING LIST
                case 1:
                    trackingController.showTrackingList();
                    break;
                //SEARCH TRACKING
                case 2:
                    displaySearchScreen();
                    int choice = Utility.getInt("Enter your choice: ", "It must be number!!!", 1, 8);
                    if (choice == 8) {
                        return;
                    } else {
                        trackingController.searchTrackingTeamByFilter(choice, teamID);
                    }
                    break;
                //SORT TRACKING
                case 3:
                    displaySortScreen();
                    int choiceSort = Utility.getInt("Enter your choice: ", "It must be number!!!", 1, 8);
                    if (choiceSort == 8) {
                        return;
                    } else {
                        trackingController.sortTrackingTeamByFilter(choiceSort, teamID);
                    }
                    break;
                //Add new tracking details to list
                case 4:
                    trackingController.addTrackingDetails(teamID, user_id);
                    break;
                //Update tracking
                case 5:
                    trackingController.updateTracking(teamID);
                    break;

                //Delete tracking
                case 6:
                    trackingController.deleteTracking(teamID);
                    break;
                case 7:
                    trackingController.changeTrackingStatus(teamID);
                    break;
                //Back to DashBoard
                case 8:
                    break;

            }
        }
    }

    public static void main(String[] args) throws Exception {
        //TH1 student co trong tracking list
//        useTrackingScreenForStudent("anhknhe@gmail.com");
        //TH2 student khong co trong tracking list
        useTrackingScreenForStudent("aminhhuong@gmail.com");
//        useTrackingScreenForTrainer();
    }

}
