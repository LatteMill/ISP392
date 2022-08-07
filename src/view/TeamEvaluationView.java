package view;

import controller.*;
import dao.*;
import model.*;
import java.util.*;

public class TeamEvaluationView {

    //ham goi: TeamEvaluationView.TeamEvaluationList();
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

    //display
    public static void displayTeamEvaluationList(boolean student) {
        if (!student) {
            System.out.println("============================ Team Evaluation List ============================");
            System.out.println("1. Search Team Evaluation");
            System.out.println("2. Sort Team Evaluation list");
            System.out.println("3. Add Team Evaluation into List");
            System.out.println("4. Update Team Evaluation");
            System.out.println("5. Back to Dashboard");
        } else {
            System.out.println("============================ Team Evaluation List ============================");
            System.out.println("1. Search Team Evaluation");
            System.out.println("2. Sort Team Evaluation");
            System.out.println("3. Back to Dashboard");
        }
    }

    public static void displaySearch() {
        System.out.println("============================ Search Team Evaluation List ============================");
    }

    // team_eval_ID, evaluation_id, criteria_id, team_id, grade, note);
    public static void displaySort() {
        System.out.println("============================ Sort Team Evaluation ============================");
        System.out.println("1. Sort by Evaluation ID");
        System.out.println("2. Sort by Criteria ID");
        System.out.println("3. Sort by Team ID");
        System.out.println("4. Sort by Grade");
        System.out.println("5. Back to Team Evaluation");
    }

    public static void displayUpdate() {
        System.out.println("============================ Update Team Evaluation ============================");
        System.out.println("*Skip ( Enter null ) if you dont want to change!");
    }

    public static void displayFeature() {
        System.out.println("======================= Team Evaluation ==================================");
        System.out.println(String.format("%-5s|%-20s|%-15s|%-15s|%-10s|%-20s",
                "ID", "Evaluation ID", "Criteria ID", "Team Name", "Grade", "Note"));
        System.out.println("--------------------------------------------------------------------------");

    }

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Function.");
        System.out.println("5. Back");
    }

    public static void displayChoiceForShowSubList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Back");
    }

    public static void TeamEvaluationForStudent(String email) throws Exception {
        //lấy user_id để phân quyền
        int user_id = userListController.searchUserIDbyEmailInSublist(userDAO.getListWithOneCondition(" email like '" + email + "'"), email);
        User u = userListController.searchUserByID(user_id);
        ClassUser classUser = classUserController.searchClassUserByID(user_id);
        boolean student = true;
        
        List<TeamEvaluation> teamEvaluationList = teamEvaluationDAO.getListWithOneCondition(" a.team_id = " + classUser.getTeamID());
        List<Team> teamList = classUserDAO.getTeamListFromClassUserByUserID(user_id);
        List<ClassUser> classUserList = null;
        int option = 0;
        int page = 1, showChoice = 0;

        //show List
        while (option != 5) {

            int maxpage = teamEvaluationList.size() / 5;
            if (maxpage != 1) {
                if (teamEvaluationList.size() % 5 != 0) {
                    maxpage++;
                }
            }
            System.out.println("There are: " + maxpage + " pages.");

            //show page
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                showTeamEvaluationList(teamEvaluationController.pagination(teamEvaluationList, page));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //pagination
            do {
                displayChoiceForShowList();
                showChoice = Utility.getInt("Enter choice: ", "Wrong!", 1, 5);
                if (showChoice == 5) {
                    return;
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
                if (showChoice == 4) {
                    option = 0;
                    while (option != 3) {
                        displayTeamEvaluationList(student);
                        option = Utility.getInt("Enter option: ", "Wrong!", 0, 3);
                        switch (option) {
                            case 1: {
                                String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                                try {
                                    List<TeamEvaluation> searchList = teamEvaluationDAO.getListForSearch(pattern, student, classUser.getTeamID());
                                    showTeamEvaluationList(searchList);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 2: {
                                int sortOption = 0;
                                displaySort();
                                sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 5);
                                try {
                                    List<TeamEvaluation> sortList = teamEvaluationController.sortTeamEvaluations(student, user_id, sortOption);
                                    showTeamEvaluationList(sortList);
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

    public static void TeamEvaluationListForTrainer(String email) throws Exception {
        //lấy user_id để phân quyền
        int user_id = userListController.searchUserIDbyEmailInSublist(userDAO.getListWithOneCondition(" email like '" + email + "'"), email);
        User u = userListController.searchUserByID(user_id);

        //gọi// cập nhật list
        //set List to check
        boolean student = false;

        int id, evaluation_id, criteria_id, team_id;
        float grade;
        String note;

        int option = 0;
        int page = 1, showChoice = 0;
        //gọi list
        while (showChoice != 5) {
            
            List<TeamEvaluation> teamEvaluationList = teamEvaluationDAO.getList();
            List<Team> teamList = teamDAO.getListWithOneCondition(" status = true ");
            List<Criteria> criteriaList = criteriaDAO.getListWithOneCondition(" a.status = 1 ");
            //          List<it> evaluationCriteriaList = null;

            int maxpage = teamEvaluationList.size() / 5;
            if (maxpage != 1) {
                if (teamEvaluationList.size() % 5 != 0) {
                    maxpage++;
                }
            }
            System.out.println("There are: " + maxpage + " pages.");

            //show page
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                showTeamEvaluationList(teamEvaluationController.pagination(teamEvaluationList, page));
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

                if (showChoice == 4) { //function
                    option = 0;
                    while (option != 5) {
                        
                        displayTeamEvaluationList(student);
                        option = Utility.getInt("OPTION: ", "WRONG", 1, 5);

                        switch (option) {
                            case 1: { //search 
                                displaySearch();
                                String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                                try {
                                    List<TeamEvaluation> searchList = teamEvaluationController.searchByPattern(pattern, student, 0);
                                    showTeamEvaluationList(searchList);

                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 2: { //sort
                                int sortOption = 0;
                                displaySort();
                                sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 5);
                                try {
                                    List<TeamEvaluation> sortList = teamEvaluationController.sortTeamEvaluations(student, user_id, sortOption);
                                    showTeamEvaluationList(sortList);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;

                            case 3: { //add
                                System.out.println("============================ Add New Team Evaluation ============================");
                                System.out.println("*The new Team Evaluation should be in ACTIVE mode!");

                                //list check:
                                //team ID FK:
                                do {
                                    System.out.println("Team list available:");
                                    showTeamListForTeamEvaluation(teamList);
                                    team_id = Utility.getInt("Enter Team ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                                    if (!teamController.checkTeamExistByID(teamList, team_id)) {
                                        System.out.println("Must enter Team ID in list showed!");
                                    }
                                    System.out.println("");

                                } while (!teamController.checkTeamExistByID(teamList, team_id));

                                //evaluation ID
                                //    System.out.println("Iteration Evaluation List available: ");
                                //    
                                evaluation_id = Utility.getInt("Enter Evaluation ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                                System.out.println("");

                                //criteria ID
                                do {
                                    System.out.println("Evaluation Criteria List avaiable: ");
                                    showCriteriaList(criteriaList);
                                    criteria_id = Utility.getInt("Enter Criteria ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                                    if (!criteriaController.checkCriteriaExistByID(criteriaList, criteria_id)) {
                                        System.out.println("Must enter criteria ID in list showed.");
                                    }
                                    System.out.println("");
                                } while (!criteriaController.checkCriteriaExistByID(criteriaList, criteria_id));

                                //grade
                                grade = Utility.getFloat("Enter grade: ", "Wrong!", 0, 10);

                                //note
                                note = Utility.getStringNull("Enter note: ", "");

                                try {
                                    teamEvaluationController.addTeamEvaluation(new TeamEvaluation(evaluation_id, criteria_id, team_id, grade, note));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            break;

                            case 4: { //update           
                                System.out.println("============================ Update Team Evaluation ============================");
                                TeamEvaluation teamEvaluation;
                                do {
                                    id = Utility.getInt("Enter Team Evaluation ID to update: ", "Wrong!", 1, Integer.MAX_VALUE);
                                    teamEvaluation = teamEvaluationController.searchTeamEvaluationByID(id);
                                } while (teamEvaluation == null);

                                //list check:
                                //team ID FK:
                                do {
                                    System.out.println("Team list available:");
                                    showTeamListForTeamEvaluation(teamList);
                                    team_id = Utility.getIntNull("Enter Team ID(Current: " + teamEvaluation.getTeam_id() + "): ", teamEvaluation.getTeam_id(), 0, Integer.MAX_VALUE);
                                    System.out.println("");
                                } while (!teamController.checkTeamExistByID(teamList, team_id));

                                
                                //evaluation ID
                                //System.out.println("Iteration Evaluation List available: ");
                                //    
                                evaluation_id = Utility.getIntNull("Enter Evaluation ID (Current: " + teamEvaluation.getEvaluation_id() + "): ", teamEvaluation.getEvaluation_id(), 0, Integer.MAX_VALUE);
                                System.out.println("");

                                //criteria ID
                                do {
                                    System.out.println("Evaluation Criteria List avaiable: ");
                                    showCriteriaList(criteriaList);
                                    criteria_id = Utility.getIntNull("Enter Criteria ID(Current: " + teamEvaluation.getCriteria_id() + "): ", teamEvaluation.getCriteria_id(), 0, Integer.MAX_VALUE);
                                    System.out.println("");
                                } while (!criteriaController.checkCriteriaExistByID(criteriaList, criteria_id));

                                //grade
                                grade = Utility.getFloatNull("Enter grade(Current: " + teamEvaluation.getGrade() + "): ", teamEvaluation.getGrade(), 0, 10);

                                //note
                                note = Utility.getStringNull("Enter note(Current: " + teamEvaluation.getNote() + "): ", teamEvaluation.getNote());

                                try {
                                    teamEvaluationController.updateTeamEvaluation(new TeamEvaluation(id, evaluation_id, criteria_id, team_id, grade, note));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 5: {
                                option=5;
                            }
                        }
                    }
                }
            } while (0 > page || page > maxpage);
        }
    }

    public static void showTeamListForTeamEvaluation(List<Team> list) throws Exception {

        if (list.size() > 0) {
            System.out.println("===================================== Team List =============================================");
            System.out.println(String.format("%-10s|%-15s|%-20s|%-30s|%-20s", "Team ID", "Team Name", "Topic Code", "Topic Name", "Status"));
            System.out.println("---------------------------------------------------------------------------------------------");

            for (int i = 0; i < list.size(); i++) {
                Team a = list.get(i);
                System.out.println(String.format("%-10d|%-15s|%-20s|%-30s|%-20s", a.getTeam_id(), a.getTeam_name(), a.getTopic_code(), a.getTopic_name(), a.getStatusString()));
            }

        } else {
            throw new Exception("Empty List!");
        }
    }

    public static void showTeamEvaluationList(List<TeamEvaluation> list) throws Exception {
        if (list.size() > 0) {
            displayFeature();
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }

        } else {
            throw new Exception("Empty List!");
        }
    }

    public static void showCriteriaList(List<Criteria> list) throws Exception {

        if (list.size() > 0) {
            System.out.println("============================= Criteria List ===============================");
            System.out.println(String.format("%-5s|%-11s|%-8s|%-15s|%-6s|%-5s|%-6s",
                "ID", "Iteration", "Weight", "Team_criteria", " Order", "Loc", "Status"));
        System.out.println("==================================================================");
    
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

}
