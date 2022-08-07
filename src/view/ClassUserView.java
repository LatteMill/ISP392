/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.*;
import dao.*;
import dao.TeamDAO;
import java.util.*;
import model.*;

/**
 *
 * @author admin
 */
public class ClassUserView {

    static ClassUserDAO classUserDAO = new ClassUserDAO();
    static ClassUserController classUserController = new ClassUserController();
    static TeamDAO teamDAO = new TeamDAO();
    static TeamController teamController = new TeamController();
    static ClassController classController = new ClassController();
    static MilestoneController milestoneController = new MilestoneController();
    static MilestoneDAO milestoneDAO = new MilestoneDAO();
    static ClassDAO classDAO = new ClassDAO();
    static UserDAO userDAO = new UserDAO();

    //display
    public static void displayClassUserList() {
        System.out.println("Class User List");
        System.out.println("1. Show Class User List");
        System.out.println("2. Search Class User");
        System.out.println("3. Sort Class User list");
        System.out.println("4. Add Class User Details");
        System.out.println("5. Change status a Class User");
        System.out.println("6. Update Class User profile");
        System.out.println("7. Delete class user");
        System.out.println("8. Back to Dashboard");
    }

    public static void displaySearch() {
        System.out.println("                                                    Search Class User List                                     ");
        System.out.println("1. Search by Class ID");
        System.out.println("2. Search by Team ID");
        System.out.println("3. Search by User ID");
        System.out.println("4. Search by User Name");
        System.out.println("5. Search by Status");
        System.out.println("6. Back to Class User");
    }

    public static void displaySearchStatus() {
        System.out.println("                                                    Search by status                                     ");
        System.out.println("1. Active");
        System.out.println("2. Inactive");
        System.out.println("3. Back to Search Class User List");
    }

    public static void displaySort() {
        System.out.println("                                                    Sort Class User                                     ");
        System.out.println("1. Sort by Class ID");
        System.out.println("2. Sort by Team ID");
        System.out.println("3. Sort by User ID");
        System.out.println("4. Sort by User Name");
        System.out.println("5. Sort by Status");
        System.out.println("6. Sort by dropout_date");
        System.out.println("7. Back to Class User");
    }

    public static void displayUpdate() {
        System.out.println("                                                    Update Class User Profile                                     ");
        System.out.println("*Skip ( Enter null ) if you dont want to change!");
    }

    public static void showClassUserList() throws Exception {
        int teamID, classID, userID;
        String user_notes;
        boolean team_leader = false;
        int final_pres_eval = 0, final_topic_eval = 0;
        Date dropout_date;
        while (true) {
            displayClassUserList();
            int option = Utility.getInt("\nOPTION: ", "WRONG", 1, 8);
            switch (option) {
                case 1: { //show List
                    try {
                        classUserController.showList(classUserDAO.getList());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
                case 2: { //search team
                    displaySearch();
                    int searchOption = Utility.getInt("OPTION: ", "WRONG", 1, 7);
                    if (searchOption == 7) {
                        break;
                    }
//                    String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                    try {
                        classUserController.searchByFilter(searchOption);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
                case 3: { //sort
                    displaySort();
                    int sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 6);
                    classUserController.sortClassUser(sortOption);
                }
                break;
                case 4: { //add
                    System.out.format("                                         Add Class User Details");
                    System.out.println("*The new Class User should be in ACTIVE mode!\n");
                    List<Classroom> classActive = classController.showActiveCLassList(classDAO.getList());
                    do {
                        classID = Utility.getInt("Enter availabe classID: ", "It must be number", 1, Integer.MAX_VALUE);

                    } while (classController.isClassIDExistInList(classActive, classID));

                    List<Team> teamActive = teamController.showActiveTeamList(teamDAO.getList());
                    do {
                        teamID = Utility.getInt("Enter availabe teamID: ", "It must be number", 1, Integer.MAX_VALUE);

                    } while (teamController.isTeamIDExistInList(teamActive, teamID) == true);
                    List<User> userList = userDAO.getList();
                    do {
                        userID = Utility.getInt("Enter user ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                    } while (UserListController.checkExistUserByID(userList, userID) == false);
                    ClassUser userN;
                    List<ClassUser> cList = classUserDAO.getList();
                    userN = classUserController.getClassUserExistByID(classID, teamID, userID);
                    if (userN != null) {
                        System.out.println("DUPICATED CLASS USER!!!\n");
                        displayHeader();
                        System.out.println(userN);
                    } else {
                        for (ClassUser user : cList) {
                            if (user.getTeamID() == teamID && user.isTeamLeader() == true) {
                                team_leader = false;
                                final_topic_eval = user.getFinal_topic_eval();
                                final_pres_eval = user.getFinal_pres_eval();
                                break;
                            } else {
                                team_leader = true;
//                            final_topic_eval = Utility.getString("Enter final topic eval: ", "It must be number", Utility.REGEX_NUMBER);
//                            final_pres_eval = Utility.getString("Enter final_pres_eval: ", "It must be number", Utility.REGEX_NUMBER);
                                final_topic_eval = Utility.getInt("Enter final topic eval: ", "It must be number", 1, 10);
                                final_pres_eval = Utility.getInt("Enter final_pres_eval: ", "It must be number", 1, 10);
                                break;
                            }
                        }

                        dropout_date = Utility.getDateFormatReturnDate("Enter Dropout Date: ");
                        user_notes = Utility.getString("Enter user notes: ", "It must be string", Utility.REGEX_STRING);
                        int statusNumber = 1;

                        ClassUser user = new ClassUser(classID, teamID, userID, team_leader, dropout_date, user_notes, final_pres_eval, final_topic_eval, statusNumber);
                        try {
                            classUserController.addClassUserDetails(user);
                            displayHeader();
                            System.out.println(user);
                            System.out.println("");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                break;

                case 5: { //active/ inactive
                    System.out.println("                                        Change Class User status                                     ");
                    System.out.println("");
                    ClassUser user;

                    classController.pageOfDisplay(classDAO.getList());
                    do {
                        classID = Utility.getInt("Enter Class ID: ", "It must be number!!!", 1, Integer.MAX_VALUE);

                    } while (classUserController.getClassUserByClassID(classID) == null);
                    classUserController.pageOfDisplay(classUserController.getClassUserListWithCondition(classID, 0, 0));
                    do {
                        teamID = Utility.getInt("Enter Team ID: ", "It must be number!!!", 1, Integer.MAX_VALUE);

                    } while (classUserController.getClassUserByTeamID(teamID) == null);
                    classUserController.pageOfDisplay(classUserController.getClassUserListWithCondition(classID, teamID, 0));
                    do {
                        userID = Utility.getInt("Enter User ID: ", "Wrong!", 1, Integer.MAX_VALUE);

                    } while (classUserController.getClassUserByUserID(userID) == null);
//                    classUserController.pageOfDisplay(classUserController.getClassUserListWithCondition(classID, teamID, userID));

                    user = classUserController.getClassUserExistByID(classID, teamID, userID);
                    if (user == null) {
                        System.out.println("NOT FOUND THIS CLASS USER!!!\n");
                    } else {

                        try {
                            displayHeader();
                            System.out.println(user);
                            System.out.println("");
                            displayChangeStatus();
                            option = Utility.getInt("Enter option: ", "Wrong!", 1, 2);
                            classUserController.changeClassUserStatus(userID, option);

                            System.out.println("Current Class User detail: ");
                            System.out.println(classUserController.searchClassUserByID(userID));

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }

                break;
                case 6: { //update           
                    System.out.println("                                        Update Class User                                     ");
                    ClassUser user;
                    classController.pageOfDisplay(classDAO.getList());
                    do {
                        classID = Utility.getInt("Enter Class ID: ", "It must be number!!!", 1, Integer.MAX_VALUE);

                    } while (classUserController.getClassUserByClassID(classID) == null);
                    classUserController.pageOfDisplay(classUserController.getClassUserListWithCondition(classID, 0, 0));
                    do {
                        teamID = Utility.getInt("Enter Team ID: ", "It must be number!!!", 1, Integer.MAX_VALUE);

                    } while (classUserController.getClassUserByTeamID(teamID) == null);
                    classUserController.pageOfDisplay(classUserController.getClassUserListWithCondition(classID, teamID, 0));
                    do {
                        userID = Utility.getInt("Enter User ID: ", "Wrong!", 1, Integer.MAX_VALUE);

                    } while (classUserController.getClassUserByUserID(userID) == null);
//                    classUserController.pageOfDisplay(classUserController.getClassUserListWithCondition(classID, teamID, userID));

                    user = classUserController.getClassUserExistByID(classID, teamID, userID);
                    if (user == null) {
                        System.out.println("NOT FOUND THIS CLASS USER!!!\n");
                    }
                    System.out.println("\n\nBefore update...\n");
                    displayHeader();
                    System.out.println(user);
                    System.out.println("");
                    classController.pageOfDisplay(classDAO.getList());
                    System.out.println("");
                    do {
                        classID = Utility.getIntNull("Enter class ID: ", user.getClassID(), 0, Integer.MAX_VALUE);
                    } while (classUserController.getClassUserByClassID(classID) == null);

                    teamController.pageOfDisplay(teamDAO.getList());
                    System.out.println("");
                    do {
                        teamID = Utility.getIntNull("Enter team ID: ", user.getTeamID(), 0, Integer.MAX_VALUE);
                    } while (classUserController.getClassUserByTeamID(teamID) == null);

//                    Scanner scanner = new Scanner(System.in);
//                    System.out.printf("Are you team leader? True/False ");
//                    team_leader = scanner.nextBoolean();
                    dropout_date = Utility.getDateUnchange("Enter Dropout Date: ", user.getDropout_date());

                    user_notes = Utility.getStringNull("Enter user notes: ", user.getUser_note());

//                    final_topic_eval = Utility.getStringNull("Enter final topic eval: ", user.getFinal_topic_eval());
//                    final_pres_eval = Utility.getStringNull("Enter final_pres_eval: ", user.getFinal_pres_eval());
                    final_pres_eval = Utility.getIntNull("Enter final_pres_eval: ", user.getFinal_pres_eval(), 1, 10);

                    final_topic_eval = Utility.getIntNull("Enter final topic eval: ", user.getFinal_topic_eval(), 1, 10);

                    int statusNumber = Utility.getIntNull("Enter 1 - active or 2 - deactive: ", user.getStatus(), 1, 2);

                    if (user.getClassID() == classID
                            && user.getTeamID() == teamID
                            && user.getDropout_date() == dropout_date
                            && user.getUser_note().equalsIgnoreCase(user_notes)
                            && user.getFinal_pres_eval() == final_pres_eval
                            && user.getFinal_topic_eval() == final_topic_eval
                            && user.getStatus() == statusNumber) {
                        System.out.println("YOU ARE NOT UPDATE!!!\n\n");
                    } else {
                        user.setStatus(statusNumber);
                        user.setFinal_topic_eval(final_topic_eval);
                        user.setFinal_pres_eval(final_pres_eval);
                        user.setUser_note(user_notes);
                        user.setUserID(user.getUserID());
                        user.setDropout_date(dropout_date);
                        user.setTeamID(teamID);
                        user.setClassID(classID);
                        classUserDAO.updateClassUserToList(user);
                        displayHeader();
                        System.out.println(user);
                        System.out.println("");
                    }

//                    ClassUser newClu = new ClassUser(classID, teamID, userID, team_leader, dropout_date, user_notes, final_pres_eval, final_topic_eval, statusNumber);
//                    try {
//
//                        List<ClassUser> uList = classUserDAO.getList();
//                        if (team_leader == true) {
//                            for (ClassUser classUser : uList) {
//                                if (classUser.getTeamID() == newClu.getTeamID() && classUser.isTeamLeader() == newClu.isTeamLeader()) {
//                                    classUser.setTeamLeader(false);
////                                    classUserController.updateClassUser(classUser);
//                                   
//
//                                }
//                            }
//                        }
//                        classUserController.updateClassUser(newClu);
//                        displayHeader();
//                        System.out.println(newClu);
//                        System.out.println("");
//                    } catch (Exception e) {
//                        System.out.println(e.getMessage());
//                    }
//                    classUserController.updateClassUser(user);
                }
                break;
                case 7:
                    System.out.println("                                        Delete Class User                                     ");
                    ClassUser user;

                    do {
                        classID = Utility.getInt("Enter Class ID: ", "It must be number!!!", 1, Integer.MAX_VALUE);

                    } while (classUserController.getClassUserByClassID(classID) == null);
                    classUserController.pageOfDisplay(classUserController.getClassUserListWithCondition(classID, 0, 0));
                    do {
                        teamID = Utility.getInt("Enter Team ID: ", "It must be number!!!", 1, Integer.MAX_VALUE);

                    } while (classUserController.getClassUserByTeamID(teamID) == null);
                    classUserController.pageOfDisplay(classUserController.getClassUserListWithCondition(classID, teamID, 0));
                    do {
                        userID = Utility.getInt("Enter User ID: ", "Wrong!", 1, Integer.MAX_VALUE);

                    } while (classUserController.getClassUserByUserID(userID) == null);

                    user = classUserController.getClassUserExistByID(classID, teamID, userID);
                    if (user == null) {
                        System.out.println("NOT FOUND THIS CLASS USER!!!\n");
                    }

                    try {

                        classUserDAO.deleteClassUserToList(user);
                        System.out.println("");
                        System.out.println("Delete successfully!!!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 8: {
                    return;
                }

            }
        }
    }

    private static void displayChangeStatus() {
        System.out.println("1. Active ");
        System.out.println("2. Inactive");

    }

    public static void main(String[] args) throws Exception {
        showClassUserList();
    }

    public static void displayHeader() {
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(String.format("%-7s|%-10s|%-15s|%-7s|%-5s|%-25s|%-25s|%-10s|%-15s|%-7s|%-15s|%-16s|%-5s", "ClassID", "ClassCode", "TeamID-Name", "UserID", "Roles", "FullName", "Email", "TeamLeader", "Dropout_date", "Notes", "Final_Pres_Eval", "Final_Topic_Eval", "Status"));
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void showList(List<Team> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("========================================================= Team List ========================================================");
            System.out.println(String.format("%-10s|%-15s|%-10s|%-20s|%-30s|%-30s|%-20s", "Team ID", "Team Name", "Class ID", "Topic Code", "Topic Name", "GitLab URL", "Status"));
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");

            for (int i = 0; i < sList.size(); i++) {
                System.out.println(sList.get(i));
            }

        } else {
            throw new Exception("Empty List!");
        }
    }

    public static void showListClassForMileStone(List<Classroom> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("============ Class List =============");
            System.out.println(String.format("%-10s|%-15s|%-10s", "Class ID", "Class Code", "Status"));
            System.out.println("-------------------------------------");
            for (int i = 0; i < sList.size(); i++) {
                System.out.println(String.format("%-10d|%-15s|%-10s",
                        sList.get(i).getClass_id(),
                        sList.get(i).getClass_code(),
                        sList.get(i).getStatus()));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

//    public static void main(String[] args) throws Exception {
//        showClassUserList();
//    }
    public static ClassUser createNewClassUser(int userID) throws Exception {
        UserListController userListController = new UserListController();
        User u = userListController.searchUserByID(userID);
        String email = u.getEmail();
        String fullName = u.getFullName();
        int teamID, classID, status = 1, role = 4;
        String user_notes;
        boolean team_leader = false;
        int final_pres_eval = 0, final_topic_eval = 0;

        System.out.format("                                         Add Class User Details");
        System.out.println("*The new Class User should be in ACTIVE mode!\n");
        List<Classroom> classActive = classController.showActiveCLassList(classDAO.getList());
        do {
            classID = Utility.getInt("Enter availabe classID: ", "It must be number", 1, Integer.MAX_VALUE);

        } while (classController.isClassIDExistInList(classActive, classID));

        List<Team> teamActive = teamController.showActiveTeamList(teamDAO.getList());
        do {
            teamID = Utility.getInt("Enter availabe teamID: ", "It must be number", 1, Integer.MAX_VALUE);

        } while (teamController.isTeamIDExistInList(teamActive, teamID) == true);

//        userID = Utility.getInt("Enter user ID: ", "Wrong!", 0, Integer.MAX_VALUE);
        List<ClassUser> sl = classUserDAO.getList();

        for (ClassUser user : sl) {
            if (user.getTeamID() == teamID && user.isTeamLeader() == true) {
                team_leader = false;
                final_topic_eval = user.getFinal_topic_eval();
                final_pres_eval = user.getFinal_pres_eval();
                break;
            } else {
                team_leader = true;
                break;
            }
        }

//        dropout_date = Utility.getDateFormatReturnDate("Enter Dropout Date: ");
        user_notes = Utility.getStringNull("Enter user notes: ", " ");
        int statusNumber = 1;

        if (team_leader == true) {
            ClassUser newU = new ClassUser(classID, teamID, userID, email, fullName, team_leader, role, user_notes, statusNumber);
            try {
                classUserController.addClassUserDetails(newU);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return newU;
        } else {
            ClassUser newU = new ClassUser(classID, teamID, userID, email, fullName, team_leader, role, final_pres_eval, final_topic_eval, user_notes, statusNumber);
            try {
                classUserController.addClassUserDetails(newU);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return newU;
        }

    }
}
