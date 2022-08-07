/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ClassUserController;
import controller.FeatureController;
import controller.FunctionController;
import controller.TeamController;
import dao.ClassUserDAO;
import dao.FeatureDAO;
import dao.FunctionDAO;
import dao.TeamDAO;
import java.util.List;
import model.ClassUser;
import model.Feature;
import model.Function;
import model.Team;
import model.User;
import static view.IssueView.classUserController;
import static view.IssueView.classUserDAO;
import static view.IssueView.userDAO;
import static view.IssueView.userListController;

/**
 *
 * @author Admin
 */
public class FunctionView {

    //    ham goi: IssueView.IssueLisst();
    static FunctionController functionController = new FunctionController();
    static FunctionDAO functionDAO = new FunctionDAO();

    static TeamDAO teamDAO = new TeamDAO();
    static TeamController teamController = new TeamController();

    static FeatureController featureController = new FeatureController();
    static FeatureDAO featureDAO = new FeatureDAO();

    static ClassUserDAO classUserDAO = new ClassUserDAO();
    static ClassUserController classUserController = new ClassUserController();

    //display
    public static void displayFunctionList() {
        System.out.println("============================ Function List ============================");
        System.out.println("1. Show Function List");
        System.out.println("2. Search Function");

        System.out.println("3. Add Function into List");
        System.out.println("4. Change status a function");
        System.out.println("5. Update Function");
        System.out.println("6. Back to Dashboard");
    }

    //seach title- 1, gitlab_id - 2, created_at - 3,  due_date - 4, team_id - 5, Milestone id - 6, function ids - 7, status - 8
    public static void displaySearch() {
        System.out.println("============================ Search Func List ============================");
    
        System.out.println("1. Search by Function Status");

        System.out.println("2. Back to Function");
    }

    public static void displaySearchStatus() {
        System.out.println("============================ Search by status ============================");
        System.out.println("1. Pending");
        System.out.println("2. Planned");
        System.out.println("3. Evaluated");
        System.out.println("4. Rejected");
        System.out.println("5. Back to Search Function List");
    }

    //Title(1), created_at(2), due_date(3),status(4)
    public static void displaySort() {
        System.out.println("============================ Sort Function ============================");
        System.out.println("1. Sort by Function name");
        System.out.println("2. Sort by Team ID");
        System.out.println("3. Sort by Feature");
        System.out.println("4. Sort by Status");
        System.out.println("5. Back to Function");
    }

    public static void displayUpdate() {
        System.out.println("============================ Update Function Profile ============================");
        System.out.println("*Skip ( Enter null ) if you dont want to change!");
        System.out.println("If you want to Cancelled, please go to change status to make sure.");
    }

    public static void displayFeature() {
        System.out.println("=============================================================== Function List ================================================================");
        System.out.println(String.format("%-10s|%-10s|%-20s|%-20s|%-10s|%-15s|%-15s|%-15s|%-10s|%-10s",
                "Function ID",
                "Team ID",
                "Function Name",
                "Feature ID",
                "Access Roles",
                "Description",
                "Complexity ID",
                "Owner ID",
                "Priority",
                "Status"));
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    }

    private static void displayChangeStatus() {
        System.out.println("1. Pending");
        System.out.println("2. Planned");
        System.out.println("3. Evaluated");
        System.out.println("4. Rejected");
    }

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Back.");
    }
    
    public static void FunctionList(String email) throws Exception {
        //lấy user_id để phân quyền
        int user_id = userListController.searchUserIDbyEmailInSublist(userDAO.getListWithOneCondition(" email like '" + email + "'"), email);
        User u = userListController.searchUserByID(user_id);
        boolean student;

        int option = 0;
        while (option != 7) {
            //gọi// cập nhật list
            //set List to check
            List<Function> fList = null;
            List<Team> teamList = null;
            //    List<User> userList = ud.getListWithOneCondition(" status = 1 ");
            List<Feature> featureList = null;
            List<ClassUser> classUserList = null;
            if (u.getRoles() != 4) {
                student = false;
                //function list
                fList = functionDAO.getFuncList();
                //team List:
                teamList = teamDAO.getList();
                featureList = featureDAO.getFeatList();
                            displayFunctionList();
            option = Utility.getInt("OPTION: ", "WRONG", 1, 7);
            int id, team_id, feature_id, complexity_id, owner_id, status;
            String function_name, access_roles, description, priority,team_name,feature_name;

            int page = 1, showChoice = 0;

            switch (option) {
                case 1: { //show List
                    while (showChoice != 4) {
                        int maxpage = fList.size() / 5;
                        if (fList.size() % 5 != 0) {
                            maxpage++;
                        }

                        try {
                            System.out.println("-----------------");
                            System.out.println("Current page: " + page);
                            showFuncList(functionController.pagination(fList, page));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        do {
                            displayChoiceForShowList();
                            showChoice = Utility.getInt("Enter choice: ", "Wrong!", 1, 4);
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
                        } while (0 > page || page > maxpage);
                    }
       //             showIssueList(functionList//);
                }
                break;
                case 2: { //filter
                    displaySearch();
                    int searchOption = Utility.getInt("OPTION: ", "WRONG", 1, 3);
                    if (searchOption == 1) {
                        displaySearchStatus();
                        int searchStatus = Utility.getInt("Enter choice: ", "Wrong!", 1, 5);

                        if (searchOption == 5) {
                            break;
                        }
                        String searchStatusFinal = Integer.toString(searchStatus);
                        try {
                            functionController.showList(functionController.searchByPattern(searchStatusFinal, 2));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    if (searchOption == 2) {
                        break;
                    }
//                    String pattern = Utility.getString("Enter value to filter: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
//                    try {
//                        functionController.showList(functionController.searchByPattern(pattern, searchOption));
//                    } catch (Exception e) {
//                        System.out.println(e.getMessage());
//                    }
                }
                break;

    
                case 3: { //add
                    System.out.println("============================ Add New Function ============================");
                    System.out.println("*The new Function should be in ACTIVE mode!");
               
                    System.out.println("Function list available: ");
          
                    IssueView.showTeamListForIssue(teamList);
                    team_id = Utility.getInt("Enter Team ID: ", "Wrong!", 0, Integer.MAX_VALUE);                         

                    function_name = Utility.getString("Enter function name: ", "Wrong.", Utility.REGEX_STRING); 
                    if(featureList.isEmpty()){
                       System.out.println("Empty Feature to Enter!");
                    }
                    System.out.println("Feature list available: ");
                    showFeatureListForFunc(featureList);
                    feature_id = Utility.getInt("Enter feature_id : ", "Wrong",  1, 2);                              
                    access_roles = Utility.getString("Enter access roles: ", "Wrong", Utility.REGEX_STRING);
                    description = Utility.getString("Enter Description: ", "Wrong", Utility.REGEX_STRING);
                    complexity_id = Utility.getInt("Enter Complexity ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                    owner_id = Utility.getInt("Enter Owner ID : ", "Wrong!", 0, Integer.MAX_VALUE);
                   priority = Utility.getString("Enter Priority :  ", "Wrong", Utility.REGEX_STRING);
                    int statusNumber = Utility.getInt("Enter 1 - Pending or 2 - Planned or 3 - Evaluated or 4 - Rejected : ", "Wrong!", 1, 4);               
                    Function f = new Function(team_id, function_name, feature_id, access_roles, description, complexity_id, owner_id, priority, statusNumber);

                    try {
                        functionController.addFunction(fList, teamList, featureList, f);
                       
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;

                case 4: { //change status
                    System.out.println("============================ Change Function status ============================");
                     showFuncList(fList);
                    id = Utility.getInt("Enter Function ID to change status: ", "Can't be empty!", 0, Integer.MAX_VALUE);
                    if (functionController.searchFunctionByID(id) == null) {
                        System.out.println("Can NOT find Function!");
                        break;
                    } 
                    if (!functionController.checkFunctionExistByID(fList, id)) {
                        System.out.println("Can NOT edit Function!");
                        break;
                    }

                    System.out.println("The current mode is: " + functionController.searchFunctionByID(id).getStatusString());
                    System.out.println("");

                    System.out.println("Change into: ");
                    displayChangeStatus();
                    option = Utility.getInt("Enter option: ", "Wrong!", 1, 4);
                    try {
                        functionController.changeFunctionStatus(fList, id, option);

                        System.out.println("Current Function detail: ");
                        displayFeature();
                        System.out.println(functionController.searchFunctionByID(id));

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
                break;
                case 5: { //update           
                    System.out.println("============================ Update Function ============================");
                    Function f;
                    do {
                        id = Utility.getInt("Enter Function ID to update: ", "Wrong!", 1, Integer.MAX_VALUE);
                        f = functionController.searchFunctionByID(id);
                    } while (functionController.searchFunctionByID(id) == null);

                    if (!functionController.checkFunctionExistByID(id)) {
                        System.out.println("Can NOT edit this Function");
                        break;
                    }
//                System.out.println("Current Issue detail: ");
//                displayFeature();
//                System.out.println(ic.searchIssueByID(id));
                    //function name:
                   function_name = f.getFunction_name() ;

                    //team ID FK:
                    if (u.getRoles() != 4) {
                        System.out.println("Team list available:");
                        IssueView.showTeamListForIssue(teamList);
                        team_id = Utility.getIntNull("Enter Team ID ( Current: " + f.getTeam_id() + ") : ", f.getTeam_id(), 0, Integer.MAX_VALUE);
                        System.out.println("");
                    } else {
                        System.out.println("Your team information ( can't not change Team ID ): ");
                        IssueView.showTeamListForIssue(teamList);
                        team_id = classUserController.searchByUserID(user_id).getTeamID();
                        System.out.println("*Your team ID is: " + team_id);
                    }

                    
             
                    if (u.getRoles() != 4) {
                        //feature list:
                        featureList = featureDAO.getListWithOneCondition(" status = 1 ");
                    feature_id = Utility.getIntNull("Enter feature ID: ", f.getTeam_id(), 0, Integer.MAX_VALUE);
                    System.out.println("");
                    }
                    
                    System.out.println("Feature list available: ");
                    showFeatureListForFunc(featureList);
                    feature_id = Utility.getIntNull("Enter Feature ID ( Current: " + f.getFeature_id() + ") : ", f.getFeature_id(), 0, Integer.MAX_VALUE);
                    System.out.println("");

             
                    //created_at & Due date
                    
                    access_roles = Utility.getStringNull("Enter Access role ( Current: " + f.getAccess_roles()+ "): ", f.getAccess_roles());
                    complexity_id = Utility.getIntNull("Enter Complexity ID (Current: "+ f.getComplexity_id() + "): ",f.getComplexity_id(),0, Integer.MAX_VALUE);
                    priority = Utility.getStringNull("Enter labels ( Current: " + f.getPriority()+ "): ", f.getPriority());
                    owner_id = Utility.getIntNull("Enter Owner ID (Current: "+ f.getOwner_id() + "): ",f.getOwner_id(),0, Integer.MAX_VALUE);
                    description = Utility.getStringNull("Enter Description (optional) ( Current: " + f.getDescription() + "): ", f.getDescription());
                    
                   status = Utility.getIntNull("Enter status with  Pending(1), Planned(2),  Evaluated(3), Evaluated(4) ( Current: " + f.getStatusString() + "): ", f.getStatus(), 1, 4);

                    try {
                        functionController.updateFunction(fList, teamList,featureList, new Function(id, team_id, function_name, access_roles,description, complexity_id,owner_id,priority, status));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                
                break;
                }
          
                case 6: {
                    return;
                }
            }
            } else {
                student = true;
                //function list:
                fList = functionDAO.getListWithOneCondition(" function_id = " + user_id);
                //team list ( lấy team mà user trong đó )
                teamList = classUserDAO.getTeamListFromClassUserByUserID(user_id);
                
                //feature list
                featureList = featureDAO.getListWithOneCondition(" status = 1 AND feature_id = " + featureController.searchFeatureByID(user_id).getFeature_id());
            
                int  showChoice = 1;
                showFuncList(fList);
                break;
            }
        }
    }


    public static void showTeamListForFunc(List<Team> sList) throws Exception {

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

    public static void showFeatureListForFunc(List<Feature> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("============================= List ===============================");
            System.out.println(String.format("%-5s|%-15s|%-15s|%-10s", "ID", "Feature_name", "Team_id", "Status"));
            System.out.println("==================================================================");

            for (int i = 0; i < sList.size(); i++) {
                Feature feat = sList.get(i);
                System.out.println(String.format("%-5s|%-15s|%-15s|%-10s", feat.getFeature_id(), feat.getFeature_name(), feat.getTeam_id(), feat.getStatusString()));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

    public static void showFuncList(List<Function> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("=============================================================== Function List ================================================================");
            System.out.println(String.format("%-11s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-10s",
                    "Function ID",
                    "Team Name",
                    "Function name",
                    "Feature name",
                    "Access Roles",                
                    "Complexity ID",
                    "Owner ID",
                    "Priority",
                    "Status"));
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for (int i = 0; i < sList.size(); i++) {
                System.out.println(sList.get(i));
            }

        } else {
            throw new Exception("Empty List!");
        }
    }

}
