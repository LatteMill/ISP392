package view;

import controller.*;
import dao.*;
import java.util.*;
import javax.swing.SpringLayout;
import model.*;

public class IssueView {

    //    ham goi: IssueView.IssueLisst();
    static IssueController issueController = new IssueController();
    static IssueDAO issueDAO = new IssueDAO();

    static MilestoneDAO milestoneDAO = new MilestoneDAO();
    static MilestoneController milestoneController = new MilestoneController();

    static UserListController userListController = new UserListController();
    static UserDAO userDAO = new UserDAO();

    static TeamController teamController = new TeamController();
    static TeamDAO teamDAO = new TeamDAO();

    static ClassUserDAO classUserDAO = new ClassUserDAO();
    static ClassUserController classUserController = new ClassUserController();

    //display
    public static void displayIssueList(boolean student) {
        if (student) {
            System.out.println("============================ Issue List ============================");
//            System.out.println("1. Show list");
            System.out.println("1. Search Issue");
            System.out.println("2. Sort Issue list");
            System.out.println("3. Add Issue into List");
            System.out.println("4. Change status a issue");
            System.out.println("5. Update Issue");
            System.out.println("6. Back to Dashboard");
        } else {
            System.out.println("============================ Issue List ============================");
            System.out.println("1. Search Issue");
            System.out.println("2. Sort Issue");
            System.out.println("3. Back to Dashboard");
        }
    }

    //seach title- 1, gitlab_id - 2, created_at - 3,  due_date - 4, team_id - 5, Milestone id - 6, function ids - 7, status - 8
    public static void displaySearch() {
        System.out.println("============================ Search Issue List ============================");
    }

    //Title(1), created_at(2), due_date(3),status(4)
    public static void displaySort() {
        System.out.println("============================ Sort Issue ============================");
        System.out.println("1. Sort by Issue title");
        System.out.println("2. Sort by Created at (date)");
        System.out.println("3. Sort by Due Date");
        System.out.println("4. Sort by Team ID");
        System.out.println("5. Sort by Status");
        System.out.println("6. Back to Issue");
    }

    public static void displayUpdate() {
        System.out.println("============================ Update Issue Profile ============================");
        System.out.println("*Skip ( Enter null ) if you dont want to change!");
        System.out.println("If you want to Cancelled, please go to change status to make sure.");
    }

    public static void displayFeature() {
        System.out.println("======================================== Issue List ================================================");
        System.out.println(String.format("%-5s|%-20s|%-20s|%-20s|%-20s|%-20s|%-20s|%-20s|%-10s",
                "ID",
                "Assignee To",
                "Issue Title",
                "Due Date",
                "Team Name",
                "Milestone Name",
                "Function IDs",
                "Labels",
                "Status"));
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");

    }

    private static void displayChangeStatus() {
        System.out.println("1. Open ");
        System.out.println("2. Closed");
        System.out.println("3. Pending");
    }

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Function.");
        System.out.println("5. Back.");
    }

    public static void IssueListForTrainer() throws Exception {
        boolean student = false;
        List<Issue> issueList = issueDAO.getList();

        int option = 0;
        int page = 1, showChoice = 0;
        while (showChoice != 5) {
            // Show page==================
            int maxpage = issueList.size() / 5;
            if (issueList.size() % 5 != 0) {
                maxpage++;
            }
            System.out.println("There are: " + maxpage + " pages.");
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                showIssueList(issueController.pagination(issueList, page));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            //==============================
            do {
                //show menu chuyen trang/function
                displayChoiceForShowList();
                showChoice = Utility.getInt("Enter choice: ", "Wrong!", 1, 5);
                if (showChoice == 5) {
                    break;
                }
                if (showChoice == 1) {
                    page = page + 1;
                }
                if (showChoice == 2) {
                    page = page - 1;
                }
                if (showChoice == 3) {
                    page = Utility.getInt("Enter page to go: ", "Can NOT go to that page!", 1, maxpage);
                }
                if (0 > page || page > maxpage) {
                    System.out.println("Can NOT go to that page!");
                    page = Utility.getInt("Enter page: ", "Can NOT go to that page", 1, maxpage);
                }
                if (showChoice == 4) { //chọn function
                    //hiển thị mục function
                    int sortOption = 0, optionFunction = 0;
                    while (optionFunction != 3) { //3 sẽ rời khỏi
                        displayIssueList(student);
                        optionFunction = Utility.getInt("Enter option: ", "Wrong!", 0, 3);

                        switch (optionFunction) {
                            case 1: //search
                            {
                                displaySearch();
                                String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                                try {
                                    List<Issue> searchList = issueController.searchByPattern(pattern, student, 0);
                                    showIssueList(searchList);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 2: {
                                sortOption = 0;
                                displaySort();
                                sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 6);
                                try {
                                    List<Issue> sortList = issueController.sortIssue(student, 0, sortOption);
                                    showIssueList(sortList);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                        }
                    }
                }
            } while (0 > page || page > maxpage);
        }
    }

    public static void IssueListForStudent(String email) throws Exception {
        //lấy user_id để phân quyền
        int user_id = userListController.searchUserIDbyEmailInSublist(userDAO.getList(), email);
        ClassUser classUser = classUserController.searchClassUserByID(user_id);
        User user = userListController.searchUserByID(user_id);
        boolean student = true;
        int page = 1, showChoice = 0;

        while (showChoice != 5) {

            List<Issue> issueList = issueDAO.getListWithOneCondition(" a.team_id = " + classUser.getTeamID());
            List<Team> teamList = classUserDAO.getTeamListFromClassUserByUserID(user_id);
            List<Milestone> milestoneList = milestoneDAO.getListWithOneCondition(" a.status = 1 AND a.class_id = " + classUser.getClassID());
            List<ClassUser> classUserList = null;
            int maxpage = issueList.size() / 5;
            if (issueList.size() % 5 != 0) {
                maxpage++;
            }
            System.out.println("There are: " + maxpage + " pages.");
            if (issueList.isEmpty()) {
                break;
            }

            //show page
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                showIssueList(issueController.pagination(issueList, page));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //pagination
            do {
                displayChoiceForShowList();

                showChoice = Utility.getInt("Enter choice: ", "Wrong!", 1, 5);

                if (showChoice == 1) {
                    page = page + 1;
                }
                if (showChoice == 2) {
                    page = page - 1;
                }
                if (showChoice == 3) {
                    page = Utility.getInt("Enter page to go: ", "Can NOT go to that page!", 1, maxpage);
                }
                if (0 > page || page > maxpage) {
                    System.out.println("Can NOT go to that page!");
                    page = Utility.getInt("Enter page: ", "Can NOT go to that page", 1, maxpage);
                }
                if (showChoice == 4) {

                    int option = 0;
                    while (option != 6) {
                        //gọi// cập nhật list
                        //set List to check

                        displayIssueList(student);
                        option = Utility.getInt("OPTION: ", "WRONG", 1, 7);
                        int id, assignee_id, gitlab_id, team_id, milestone_id, status;
                        String issue_title, description, labels, function_ids, gitlab_url;
                        Date created_at, due_date;

                        switch (option) {

                            case 1: { //search 
                                displaySearch();
                                String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                                try {
                                    List<Issue> searchList = issueController.searchByPattern(pattern, student, user_id);
                                    showIssueList(searchList);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            break;
                            case 2: { //sort
                                int sortOption = 0;
                                displaySort();
                                sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 6);
                                try {
                                    List<Issue> sortList = issueController.sortIssue(student, user_id, sortOption);
                                    showIssueList(issueController.pagination(sortList, page));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            break;

                            case 3: { //add
                                System.out.println("============================ Add New Issue ============================");
                                System.out.println("*The new Issue should be in ACTIVE mode!");

                                //list check:
                                //issue title:
                                issue_title = Utility.getString("Enter Issue title: ", "Wrong!", Utility.REGEX_STRING);

                                System.out.println("Your team information: ");
                                showTeamListForIssue(teamList);
                                team_id = classUserController.searchByUserID(user_id).getTeamID();
                                System.out.println("*Your team ID is: " + team_id);

                                System.out.println("");
                                //lấy Class_User ( User list) available:
                                classUserList = classUserDAO.getUserListFromClassUserByTeamID(team_id);
                                if (classUserList.isEmpty()) {
                                    System.out.println("Empty User List to assignee!");
                                    break;
                                }

                                gitlab_id = Utility.getIntNull("Enter Gitlab ID: ", 0, 0, Integer.MAX_VALUE);
                                do {
                                    gitlab_url = Utility.getStringNull("Enter Gitlab URL: ", "");
                                } while (gitlab_url.length() > 0 && !gitlab_url.matches(Utility.REGEX_LINK));
                                System.out.println("");

                                //assignee_ID FK
                                do {
                                    System.out.println("User list available to assign to: ");
                                    showUserListForIssue(classUserList);
                                    assignee_id = Utility.getInt("Enter Assignee ID: ", "Wrong!", 0, Integer.MAX_VALUE);

                                    if (!classUserController.checkExistUserByID(classUserList, assignee_id)) {
                                        System.out.println("Enter Assignee ID in list only!");
                                    }
                                    System.out.println("");
                                } while (!classUserController.checkExistUserByID(classUserList, assignee_id));

                                //mileseton_id FK
                                if (milestoneList.isEmpty()) {
                                    System.out.println("Empty Milestone to enter!");
                                    break;
                                }
                                do {
                                    System.out.println("Milestone list available: ");
                                    showMilestoneListForIssue(milestoneList);
                                    milestone_id = Utility.getInt("Enter Milestone ID: ", "Wrong!", 0, Integer.MAX_VALUE);

                                    if (!milestoneController.checkMileStoneExistByID(milestoneList, milestone_id)) {
                                        System.out.println("Enter Milestone ID in list only!");
                                    }

                                    System.out.println("");
                                } while (!milestoneController.checkMileStoneExistByID(milestoneList, milestone_id));

                                boolean check = true;
                                do {
                                    //function_ids
                                    function_ids = Utility.getStringNull("Enter function_ids (with , if more than 1): ", "");
                                    if (function_ids.length() != 0) {
                                        check = Utility.checkArrayFids(function_ids);
                                    } else {
                                        break;
                                    }
                                } while (!check);

                                //created_at & Due date
                                created_at = Utility.getToday();
                                due_date = Utility.getDateFormatReturnDate("Enter Due date: ", created_at);

                                labels = Utility.getStringNull("Enter labels: ", "");

                                description = Utility.getStringNull("Enter Description (optional): ", "");

                                status = Utility.getInt("Enter status with open(1), closed(2), pending(3): ", "Wrong!", 1, 3);
                                try {
                                    issueController.addIssue(issueList, teamList, classUserList, milestoneList, new Issue(assignee_id, gitlab_id, team_id, milestone_id, function_ids, status, issue_title, description, labels, gitlab_url, created_at, due_date));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }

                            break;

                            case 4: { //active/ deactive
                                System.out.println("============================ Change Issue status ============================");
                                id = Utility.getInt("Enter Issue ID to change status: ", "Can't be empty!", 0, Integer.MAX_VALUE);
                                if (issueController.searchIssueByID(id) == null) {
                                    System.out.println("Can NOT find Issue!");
                                    break;
                                }

                                if (!issueController.checkIssueExistByID(issueList, id)) {
                                    System.out.println("Can NOT edit this ISSUE");
                                    break;
                                }

                                System.out.println("The current mode is: " + issueController.searchIssueByID(id).getStatusString());
                                System.out.println("");

                                System.out.println("Change into: ");
                                displayChangeStatus();
                                option = Utility.getInt("Enter option: ", "Wrong!", 1, 3);
                                try {
                                    issueController.changeIssueStatus(issueList, id, option);

                                    System.out.println("Current Issue detail: ");
                                    displayFeature();
                                    System.out.println(issueController.searchIssueByID(id));

                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            break;
                            case 5: { //update           
                                System.out.println("============================ Update Issue ============================");
                                Issue is;
                                do {
                                    id = Utility.getInt("Enter Issue ID to update: ", "Wrong!", 1, Integer.MAX_VALUE);
                                    is = issueController.searchIssueByID(id);
                                } while (issueController.searchIssueByID(id) == null);

                                if (!issueController.checkIssueExistByID(issueList, id)) {
                                    System.out.println("Can NOT edit this ISSUE");
                                    break;
                                }

                                //issue title:
                                issue_title = Utility.getStringNull("Enter Issue title ( Current: " + is.getIssue_title() + ") : ", is.getIssue_title());

                                System.out.println("Your team information ( can't not change Team ID ): ");
                                showTeamListForIssue(teamList);
                                team_id = classUserController.searchByUserID(user_id).getTeamID();
                                System.out.println("*Your team ID is: " + team_id);
//                    }

                                classUserList = classUserDAO.getUserListFromClassUserByTeamID(team_id);
                                if (classUserList.isEmpty()) {
                                    System.out.println("Empty User List to assignee!");
                                    break;
                                }

                                //assignee_ID FK
                                System.out.println("User list available to assign to: ");
                                showUserListForIssue(classUserList);
                                assignee_id = Utility.getIntNull("Enter Assignee ID ( Current: " + is.getAssignee_id() + ") : ", is.getAssignee_id(), 0, Integer.MAX_VALUE);
                                System.out.println("");

                                gitlab_id = Utility.getIntNull("Enter Gitlab ID ( Current: " + is.getGitlab_id() + " ) : ", is.getGitlab_id(), 0, Integer.MAX_VALUE);
                                gitlab_url = Utility.getStringWithREGEXANDNull("Enter Gitlab URL ( Current: " + is.getGitlab_url() + ") : ", Utility.REGEX_LINK, is.getGitlab_url());

                                //mileseton_id FK
                                if (user.getRoles() != 4) {
                                    //milestone list:
                                    milestoneList = milestoneDAO.getListWithOneCondition(" a.status = 1 AND a.class_id = " + classUserController.searchByUserID(assignee_id).getClassID());
                                }

                                System.out.println("Milestone list available: ");
                                showMilestoneListForIssue(milestoneList);
                                milestone_id = Utility.getIntNull("Enter Milestone ID ( Current: " + is.getMilestone_id() + ") : ", is.getMilestone_id(), 0, Integer.MAX_VALUE);
                                System.out.println("");

                                do {
                                    //function_ids
                                    function_ids = Utility.getStringNull("Enter function_ids (with , if more than 1) ( Current: " + is.getFunction_ids() + "): ", is.getFunction_ids());
                                } while (!Utility.checkArrayFids(function_ids) && function_ids.length() != 0);

                                //created_at & Due date
                                created_at = is.getCreated_at();
                                due_date = Utility.getDateUnchange("Enter due date ( Current: " + Utility.convertDateToString(is.getDue_date()) + "): ", is.getDue_date());

                                labels = Utility.getStringNull("Enter labels ( Current: " + is.getLabels() + "): ", is.getLabels());

                                description = Utility.getStringNull("Enter Description (optional) ( Current: " + is.getDescription() + "): ", is.getDescription());

                                status = Utility.getIntNull("Enter status with open(1), closed(2), pending(3) ( Current: " + is.getStatusString() + "): ", is.getStatus(), 1, 3);

                                try {
                                    issueController.updateIssue(issueList, classUserList, teamList, milestoneList, new Issue(id, assignee_id, gitlab_id, team_id, milestone_id, function_ids, status, issue_title, description, labels, gitlab_url, created_at, due_date));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 6: {
                                option = 6;
                            }
                        }
                    }
                }
            } while (0 > page || page > maxpage);
        }
    }

    public static void showUserListForIssue(List<ClassUser> cl) throws Exception {
        System.out.println("=================User List ======================");
        System.out.println(String.format("%-10s|%-20s|%-10s", "User ID", "User Name", "Status"));
        System.out.println("-------------------------------------------------");
        if (cl.size() > 0) {
            for (int i = 0; i < cl.size(); i++) {
                ClassUser c = cl.get(i);
                System.out.println(String.format("%-10d|%-20s|%-10s", c.getUserID(), c.getFullName(), c.getStatusString()));
            }
        } else {
            throw new Exception("Empty List!");
        }

    }

    public static void showTeamListForIssue(List<Team> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("===================================== Team List =============================================");
            System.out.println(String.format("%-10s|%-15s|%-20s|%-30s|%-20s", "Team ID", "Team Name", "Topic Code", "Topic Name", "Status"));
            System.out.println("---------------------------------------------------------------------------------------------");

            for (int i = 0; i < sList.size(); i++) {
                Team a = sList.get(i);
                System.out.println(String.format("%-10d|%-15s|%-20s|%-30s|%-20s", a.getTeam_id(), a.getTeam_name(), a.getTopic_code(), a.getTopic_name(), a.getStatusString()));
            }

        } else {
            throw new Exception("Empty List!");
        }
    }

    public static void showMilestoneListForIssue(List<Milestone> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("====================================== Milestone List =======================================");
            System.out.println(String.format("%-5s|%-15s|%-15s|%-15s|%-15s|%-15s|%-10s", "ID", "Milestone Name", "Iteration Name", "Class Code", "From Date", "To Date", "Status"));
            System.out.println("---------------------------------------------------------------------------------------------");

            for (int i = 0; i < sList.size(); i++) {
                System.out.println(sList.get(i));
            }
//            System.out.println(sList.get(0).getFrom_date());
//            System.out.println(Utility.convertDateToString(sList.get(0).getFrom_date()));
//            System.out.println(Utility.convertDateToStringtoInsert(sList.get(0).getFrom_date()));

        } else {
            throw new Exception("Empty List!");
        }
    }

    public static void showIssueList(List<Issue> sList) throws Exception {

        if (sList.size() > 0) {
            displayFeature();
            for (int i = 0; i < sList.size(); i++) {
                System.out.println(sList.get(i));
            }

        } else {
            throw new Exception("Empty List!");
        }
    }

//    public static void main(String[] args) throws Exception {
//        IssueList("philiple182@gmail.com");
//    }
}
