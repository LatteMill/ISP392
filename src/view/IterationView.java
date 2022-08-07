package view;

import java.util.*;
import model.*;
import controller.*;
import dao.*;

public class IterationView {
//    ham goi: View_SubjectSetting.SubjectList();

    static IterationDAO sd = new IterationDAO();
    static IterationController sc = new IterationController();

    public static void dissplayFeature() {
        System.out.println(String.format("%-5s|%-15s|%-15s|%-15s|%-10s", "ID", "Iteration Name", "Subject ID", "Duration", "Status"));
    }

    //display
    public static void displaySubjectList() {
        System.out.println("============================ Iteration List ============================");
        System.out.println("1. Show Iteration List");
        System.out.println("2. Search Iteration");
        System.out.println("3. Sort iteration list");
        System.out.println("4. Add interation into List");
        System.out.println("5. Active/Deactive an iteration");
        System.out.println("6. Update iteration");
        System.out.println("7. Back to DashBoard");
    }

    public static void displaySearch() {
        System.out.println("============================ Search Iteration List ============================");
        System.out.println("1. Search by name");
        System.out.println("2. Search by status");
        System.out.println("3. Back to Iteration List");
    }

    public static void displaySearchStatus() {
        System.out.println("============================ Search by status ============================");
        System.out.println("1. Active");
        System.out.println("2. Deactive");
        System.out.println("3. Back to Search Iteration List");
    }

    public static void displaySort() {
        System.out.println("============================ Sort Iteration ============================");
        System.out.println("1. Sort by name");
        System.out.println("2. Sort by status");
        System.out.println("3. Back to Iteration List");
    }

    public static void displayUpdate() {
        System.out.println("============================ Update Iteration ============================");
        System.out.println("*Skip ( Enter null ) if you dont want to change!");
        System.out.println("*The iteration will be active after update!");
        System.out.println("0. Back to Iteration List");
    }

    public static void IterSetting() throws Exception {
        int option = 0;
        while (option != 7) {
            displaySubjectList();
            int id;
            option = Utility.getInt("OPTION: ", "WRONG", 1, 7);

            String iterName;
            double duration = 0;
            int subject_id;
            boolean status = false;

            switch (option) {
                case 1: { //show List
                    System.out.println("============================ Iteration List ============================");
                    try {
                        sc.showList(sd.getIterList());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
                case 2: { //search iteration
                    displaySearch();
                    int searchOption = Utility.getInt("OPTION: ", "WRONG", 1, 4);
                    if (searchOption == 3) {
                        displaySearchStatus();
                        int searchStatus = Utility.getInt("Enter choice: ", "Wrong!", 1, 3);
                        String searchStatusFinal = Integer.toString(searchStatus);

                        sc.showList(sc.searchByPattern(searchStatusFinal, 3));
                        break;
                    }
                    if (searchOption == 4) {
                        break;
                    }
                    String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING);

                    sc.showList(sc.searchByPattern(pattern, searchOption));
                }
                break;
                case 3: {
                    displaySort();
                    int sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 4);
                    sc.showList(sc.sortIteration(sortOption));
                }
                break;
                case 4: { //add
                    System.out.println("============================ Add New Iteration ============================");
                    System.out.println("*The new iteration will be in ACTIVE mode!");

                    iterName = Utility.getString("Enter iteration name: ", "Wrong.", Utility.REGEX_STRING);
                    subject_id = Utility.getInt("Enter subject_id: ", "Wrong.", Integer.MIN_VALUE, Integer.MAX_VALUE);
                    duration = Utility.getDouble("Enter duration", "Wrong", Double.MIN_VALUE, Double.MAX_VALUE);
                    int statusNumber = Utility.getInt("Enter 1 - active or 2 - deactive: ", "Wrong!", 1, 2);
                    if (statusNumber == 1) {
                        status = true;
                    }
                    if (statusNumber == 2) {
                        status = false;
                    }

                    Iteration sb = new Iteration(subject_id, iterName, duration, statusNumber);

                    try {
                        sc.addNewIteration(sb);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
                case 5: { //active/ deactive
                    System.out.println("============================ Change Iteration status ============================");
                    id = Utility.getInt("Enter Iteration ID to change status: ", "Can't be empty!", 0, Integer.MAX_VALUE);
                    try {
                        sc.changeIterationStatus(id);
                        //    sc.changeSubjectStatus(sb.getSubjectID());
                        //ud.updateUserToList(ur, "verify", verifyString, "boolean");

                        System.out.println("Current iteration detail: ");
                        System.out.println(sc.searchIterationByID(id));

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
                break;
                case 6: { //update           
                    System.out.println("============================ Update Iteration ============================");
                    Iteration sb;
                    do {
                        id = Utility.getInt("Enter iteration ID to update: ", "Wrong!", 1, Integer.MAX_VALUE);
                        sb = sc.searchIterationByID(id);
                    } while (sc.searchIterationByID(id) == null);

                    iterName = Utility.getStringNull("Enter iteration Name to update: ", sb.getIteration_name());
                    subject_id = Utility.getIntNull("Enter subject_id to update", sb.getSubject_id(), Integer.MIN_VALUE, Integer.MAX_VALUE);
                    int statusNumber = Utility.getInt("Enter 1 - active or 2 - deactive: ", "Wrong!", 1, 2);
                    if (statusNumber == 1) {
                        status = true;
                    }
                    if (statusNumber == 2) {
                        status = false;
                    }

                    try {

                        sc.updateIteration(new Iteration(subject_id, iterName, duration, statusNumber));
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
    }

}
