package view;

import java.util.*;
import model.*;
import controller.*;
import dao.*;

public class TeamView {
//    ham goi: TeamView.TeamList();

    static TeamDAO teamDAO = new TeamDAO();
    static TeamController teamController = new TeamController();

    static ClassDAO classDAO = new ClassDAO();
    static ClassController classController = new ClassController();

    //display
    public static void displayTeamList() {
        System.out.println("============================ Team List ============================");
//        System.out.println("1. Show Team List");
        System.out.println("1. Search Team");
        System.out.println("2. Sort Team list");
        System.out.println("3. Add Team into List");
        System.out.println("4. Change status a Team");
        System.out.println("5. Update Team profile");
        System.out.println("6. Back to Dashboard");
    }

    public static void displaySearch() {
        System.out.println("============================ Search Team List ============================");
    }

    public static void displaySort() {
        System.out.println("============================ Sort Team ============================");
        System.out.println("1. Sort by Topic code");
        System.out.println("2. Sort by Topic name");
        System.out.println("3. Sort by Topic status");
        System.out.println("4. Back to Team List");
    }

    public static void displayUpdate() {
        System.out.println("============================ Update Team Profile ============================");
        System.out.println("*Skip ( Enter null ) if you dont want to change!");
    }

    public static void displayFeature() {
        System.out.println(String.format("%-10s|%-15s|%-10s|%-20s|%-30s|%-30s|%-20s", "Team ID", "Team Name", "Class ID", "Topic Code", "Topic Name", "GitLab URL", "Status"));
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");

    }

    private static void displayChangeStatus() {
        System.out.println("1. Active ");
        System.out.println("2. Inactive");
    }

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Function.");
        System.out.println("5. Back");
    }

    public static void TeamList() throws Exception {

        String topicCode, topicName, gitlab_url = null, teamName;
        int class_id, statusNumber = 0;
        boolean status = false;

        int page = 1, showChoice = 0;
        while (showChoice != 5) {
            List<Team> teamList = teamDAO.getList();
            int maxpage = teamList.size() / 5;
            if (maxpage != 1) {
                if (teamList.size() % 5 != 0) {
                    maxpage++;
                }
            }
            System.out.println("There are: " + maxpage + " pages.");
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                showTeamList(teamController.pagination(teamList, page));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
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
                        displayTeamList();
                        int id;
                        option = Utility.getInt("OPTION: ", "WRONG", 1, 6);

                        switch (option) {

                            case 1: { //search team

                                String searchStatusFinal = Utility.getString("Enter value to search: ", "Wrong!", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                                try {
                                    showTeamList(teamController.searchByPattern(searchStatusFinal));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;

                            case 2: { //sort
                                displaySort();
                                int sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 4);
                                try {
                                    showTeamList(teamController.sortTeam(sortOption));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 3: { //add
                                System.out.println("============================ Add New Team ============================");
                                System.out.println("*The new Team should be in ACTIVE mode!");

                                // show class list to choose - class ID FK
                                System.out.println("Class list available: ");
                                showListClassForMileStone(classDAO.getListWithOneCondition(" status = true "));
                                class_id = Utility.getInt("Enter class ID: ", "Wrong!", 0, Integer.MAX_VALUE);

                                teamName = Utility.getString("Enter team name: ", "Wrong!", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                                topicCode = Utility.getString("Enter Topic Code: ", "Wrong.", Utility.REGEX_STRING);
                                topicName = Utility.getString("Enter Topic Name: ", "Wrong.", Utility.REGEX_STRING);
                                gitlab_url = Utility.getString("Enter GibLab_URL: ", "Wrong.", Utility.REGEX_LINK);
                                statusNumber = Utility.getInt("Enter 1 - active or 2 - deactive: ", "Wrong!", 1, 2);
                                if (statusNumber == 1) {
                                    status = true;
                                }
                                if (statusNumber == 2) {
                                    status = false;
                                }

                                Team team = new Team(class_id, status, topicCode, topicName, gitlab_url, teamName);

                                try {
                                    teamController.addNewTeam(team);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 4: { //active/ deactive
                                System.out.println("============================ Change Team status ============================");
                                id = Utility.getInt("Enter Team ID to change status: ", "Wrong!", 0, Integer.MAX_VALUE);
                                System.out.println("The current mode is: " + teamController.searchTeamByID(id).getStatusString());

                                System.out.println("");
                                System.out.println("Change into: ");
                                displayChangeStatus();
                                option = Utility.getInt("Enter option: ", "Wrong!", 1, 2);
                                try {
                                    teamController.changeTeamStatus(id, option);

                                    System.out.println("Current Team detail: ");
                                    displayFeature();
                                    System.out.println(teamController.searchTeamByID(id));

                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            break;
                            case 5: { //update           
                                System.out.println("============================ Update Team ============================");
                                Team team;
                                do {
                                    id = Utility.getInt("Enter Team ID to update: ", "Wrong!", 1, Integer.MAX_VALUE);
                                    team = teamController.searchTeamByID(id);
                                } while (teamController.searchTeamByID(id) == null);

                                System.out.println("Current detail of team: ");
                                displayFeature();
                                System.out.println(team);
                                System.out.println("");

                                // show class list to choose - class ID FK
                                System.out.println("Class list available: ");
                                showListClassForMileStone(classDAO.getListWithOneCondition(" status = true "));
                                class_id = Utility.getIntNull("Enter class ID: ", team.getClass_id(), 0, Integer.MAX_VALUE);

                                teamName = Utility.getStringNull("Enter Team name: ", team.getTeam_name());
                                topicCode = Utility.getStringNull("Enter Topic Code to update: ", team.getTopic_code());
                                topicName = Utility.getStringNull("Enter Topic Name to update: ", team.getTopic_name());
                                gitlab_url = Utility.getStringNull("Enter Gib Lab URL: ", team.getGitlab_url());

                                if (team.isStatus()) {
                                    statusNumber = 1;
                                } else {
                                    statusNumber = 2;
                                }

                                int statusNumberUpdate = Utility.getIntNull("Enter active(1), deactive(2): ", statusNumber, 1, 2);
                                if (statusNumberUpdate == 1) {
                                    status = true;
                                } else {
                                    status = false;
                                }

//                int statusNumber = Utility.getIntNull("Enter active(1), deactive(2) and blocked(3): ",team.getStatus(), 1, 3);
                                try {
                                    teamController.updateTeam(new Team(id, class_id, status, topicCode, topicName, gitlab_url, teamName));
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

    public static void showTeamList(List<Team> sList) throws Exception {

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
}
