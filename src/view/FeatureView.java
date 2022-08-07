/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import java.util.*;
import model.*;
import controller.*;
import dao.*;
/**
 *
 * @author Admin
 */
public class FeatureView {
     static FeatureDAO sd = new FeatureDAO();
    static FeatureController sc = new FeatureController();
    static TeamController tc = new TeamController();
    static TeamDAO tda = new TeamDAO();
    static UserListController ulc = new UserListController();
    public static void dissplayFeature() {
        System.out.println(String.format("%-5s|%-15s|%-15s|%-10s", "ID", "Feature_name", "Feature_id", "Status"));
    }

 //display
    public static void displayFeatureList() {
        System.out.println("============================ Feature List ============================");
        System.out.println("1. Show Feature List");
        System.out.println("2. Search Feature");
        System.out.println("3. Sort feature list");
        System.out.println("4. Add feature into List");
        System.out.println("5. Change status");
        System.out.println("6. Update feature");
        System.out.println("7. Back to Feature");
    }

    public static void displaySearch() {
        System.out.println("============================ Filter Feature List ============================");
    System.out.println("1. Filter by Team ID");
        System.out.println("2. Filter by Feature Status");
     
        System.out.println("3. Back to Feature");
    }

    public static void displaySearchStatus() {
          System.out.println("============================ Search by status ============================");
        System.out.println("1. Active");
        System.out.println("2. InActive");
        System.out.println("3. Back to Search Feature List");

    }

    public static void displaySort() {
        System.out.println("============================ Sort Feature ============================");
        System.out.println("1. Sort by name");
        System.out.println("2. Sort by status");
        System.out.println("3. Back to Feature List");
    }

    public static void displayUpdate() {
        System.out.println("============================ Update Feature ============================");
        System.out.println("*Skip ( Enter null ) if you dont want to change!");
        System.out.println("*The feature will be active after update!");
        System.out.println("0. Back to Feature List");
    }
    
 public static void displayFeature() {
        System.out.println(String.format("%-5s|%-15s|%-15s|%-10s", "Feature ID","Feature Name", "Team ID", "Status"));
        System.out.println("----------------------------------------------------------------------------------------------------------------");
    }
 public static void FeatSettingStudent(String email) throws Exception{
      sc.showTeamListforStudent(ulc.searchUserIDbyEmail(email));
 }
    public static void FeatSettingTrainer() throws Exception {
        displayFeatureList();
        int id, option = Utility.getInt("OPTION: ", "WRONG", 1, 7);
        String featName;
        int team_id;
        int status = 0;

        switch (option) {
            case 1: { //show List
                System.out.println("============================ Feature List ============================");
                try {
                    sc.showList(sd.getFeatList());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            break;
            case 2: { //search feature
                displaySearch();

                int searchOption = Utility.getInt("OPTION: ", "WRONG", 1, 3);
                if (searchOption == 2) {
                    displaySearchStatus();
                    int searchStatus = Utility.getInt("Enter choice: ", "Wrong!", 1, 3);
                    
                
                if (searchOption == 3) {

                    break;
                }
                String searchStatusFinal = Integer.toString(searchStatus);
               try {
                        sc.showList(sc.searchByPattern(searchStatusFinal, 2));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                if (searchOption == 3) {
                    break;
                }
                String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                try {
                    sc.showList(sc.searchByPattern(pattern, searchOption));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            break;
            case 3: {
                displaySort();
                int sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 4);
                sc.showList(sc.sortFeature(sortOption));
            }
            break;
            case 4: { //add
                System.out.println("============================ Add New Feature ============================");
                System.out.println("*The new feature will be in ACTIVE mode!");
                //show team list to choose - Team ID FK
                System.out.println("Team list available: ");
                tc.showListTeamForFeature(tda.getListWithOneCondition(" status = true "));
                team_id = Utility.getInt("Enter Team ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                System.out.println("");
                
              
               // duration = Utility.getDouble("Enter duration", "Wrong", Double.MIN_VALUE, Double.MAX_VALUE);

                int statusNumber = Utility.getInt("Enter 1 - Active or 2 - InActive : ", "Wrong!", 1, 2);

                  featName = Utility.getString("Enter feature name: ", "Wrong.", Utility.REGEX_STRING);
             //   feat = Utility.getInt("Enter team_id: ", "Wrong.", Integer.MIN_VALUE, Integer.MAX_VALUE);            

                Feature sb = new Feature(team_id, featName, statusNumber);

                try {
                    sc.addNewFeature(sb);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            break;
            case 5: { //active/ deactive
                System.out.println("============================ Change Feature status ============================");
                id = Utility.getInt("Enter Feature ID to change status: ", "Can't be empty!", 0, Integer.MAX_VALUE);
                System.out.println("The current mode is: " + sc.searchFeatureByID(id).getStatusString());
                System.out.println("");
                
                System.out.println("Change into: ");
                displayChangeStatus();

                option = Utility.getInt("Enter option: ", "Wrong!", 1, 2);

                try {
                    sc.changeFeatureStatus(id, option);

                    System.out.println(sc.searchFeatureByID(id));

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
            break;
            case 6: { //update           
                System.out.println("============================ Update Feature ============================");
                Feature sb;
                do {
                    id = Utility.getInt("Enter feature ID to update: ", "Wrong!", 1, Integer.MAX_VALUE);
                    sb = sc.searchFeatureByID(id);
                } while (sc.searchFeatureByID(id) == null);
                System.out.println("Current Feature detail: ");
                displayFeature();
                System.out.println(sc.searchFeatureByID(id));

                //show Team list to choose - Team ID FK
                System.out.println("Team list available: ");
                tc.showListTeamForFeature(tda.getListWithOneCondition(" status = true "));
                team_id = Utility.getIntNull("Enter team ID: ", sb.getTeam_id(), 0, Integer.MAX_VALUE);
                System.out.println("");

                // show class list to choose - class ID FK

                int statusNumber = Utility.getIntNull("Enter 1 - Active or 2 - InActive : ", sb.getStatus(), 1, 2);

                featName = Utility.getStringNull("Enter Feature name: ", sb.getFeature_name());
             //   to_date = Utility.getDateUnchange("Enter To date: ", sb.getTo_date());

                try {
                    sc.updateFeature(new Feature(id,team_id, featName,  statusNumber));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            break;
            case 7: {
                return;
            }

        }
    }
    private static void displayChangeStatus() {

        System.out.println("1. Active ");
        System.out.println("2. InActive");


    }
  
}
