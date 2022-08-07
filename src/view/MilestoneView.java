package view;

import com.mysql.cj.util.Util;
import controller.*;
import dao.*;
import java.util.*;
import model.*;

public class MilestoneView {
    //    ham goi: MilestoneView.MilestoneList();

    static MilestoneDAO milestoneDAO = new MilestoneDAO();
    static MilestoneController milestoneController = new MilestoneController();

    static IterationController iterationController = new IterationController();
    static IterationDAO iterationDAO = new IterationDAO();

    static ClassDAO classDAO = new ClassDAO();
    static ClassController classController = new ClassController();

    //display
    public static void displayMilestoneList() {
        System.out.println("============================ Milestone List ============================");
//        System.out.println("1. Show milestone List");
        System.out.println("1. Search milestone");
        System.out.println("2. Sort milestone list");
        System.out.println("3. Add Milestone into List");
        System.out.println("4. Change status a milestone");
        System.out.println("5. Update Milestone profile");
        System.out.println("6. Back to Dashboard");
    }

    public static void displaySearch() {
        System.out.println("============================ Search Milestone List ============================");

    }

    public static void displaySearchStatus() {
        System.out.println("============================ Status ============================");
        System.out.println("1. Open");
        System.out.println("2. Closed");
        System.out.println("3. Cancelled");
    }

    public static void displaySort() {
        System.out.println("============================ Sort Milestone ============================");
        System.out.println("1. Sort by Iterarion ID");
        System.out.println("2. Sort by Class ID");
        System.out.println("3. Sort by Milestone Status");
        System.out.println("4. Sort by From Date");
        System.out.println("5. Sort by To Date");
        System.out.println("6. Back to Milestone");
    }

    public static void displayUpdate() {
        System.out.println("============================ Update Milestone Profile ============================");
        System.out.println("*Skip ( Enter null ) if you dont want to change!");
        System.out.println("If you want to block, please go to change status to make sure.");
    }

    public static void displayFeature() {
        System.out.println(String.format("%-5s|%-15s|%-15s|%-15s|%-15s|%-15s|%-10s", "ID", "Milestone Name", "Iteration Name", "Class Code", "From Date", "To Date", "Status"));
        System.out.println("---------------------------------------------------------------------------------------------");

    }

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Function");
        System.out.println("5. Back.");
    }

    public static void MilestoneList() throws Exception {

        int id;
        int iteration_id, class_id, status = 0;
        Date from_date, to_date;
        String milestoneName;
        int page = 1, showChoice = 0;

        while (showChoice != 5) {
            List<Milestone> milestoneList = milestoneDAO.getList();
            int maxpage = milestoneList.size() / 5;
            if (maxpage != 1) {
                if (milestoneList.size() % 5 != 0) {
                    maxpage++;
                }
            }
            System.out.println("There are: " + maxpage + " pages.");
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                showMilestoneList(milestoneController.pagination(milestoneList, page));
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

                        displayMilestoneList();
                        option = Utility.getInt("OPTION: ", "WRONG", 1, 6);

                        switch (option) {
                            case 1: { //search team                 
                                String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                                try {
                                    showMilestoneList(milestoneController.searchByPattern(pattern));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 2: { //sort
                                displaySort();
                                int sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 6);
                                showMilestoneList(milestoneController.sortMilestone(sortOption));
                            }
                            break;
                            case 3: { //add
                                System.out.println("============================ Add New Milestone ============================");
                                System.out.println("*The new Milestone should be in ACTIVE mode!");

                                milestoneName = Utility.getString("Enter milestone name: ", "Wrong!", Utility.REGEX_STRING);

                                // show class list to choose - class ID FK
                                System.out.println("Class list available: ");
                                showListClassForMileStone(classDAO.getListWithOneCondition(" status = true "));
                                class_id = Utility.getInt("Enter class ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                                System.out.println("");

                                //show iteration list to choose - Iteration ID FK
                                System.out.println("Iteration list available: ");
                                showListIterationForMilestone(iterationDAO.getListWithOneCondition(" status = true "));
                                iteration_id = Utility.getInt("Enter iteration ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                                System.out.println("");

                                int statusNumber = Utility.getInt("Enter 1 - active or 2 - deactive: ", "Wrong!", 1, 2);
                                from_date = Utility.getDateFormatReturnDate("Enter From date: ");
                                to_date = Utility.getDateFormatReturnDate("Enter To date: ", from_date);

                                Milestone milestone = new Milestone(iteration_id, class_id, statusNumber, from_date, to_date, milestoneName);

                                try {
                                    milestoneController.addNewMilestone(milestone);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 4: { //active/ deactive
                                System.out.println("============================ Change Milestone status ============================");
                                id = Utility.getInt("Enter Milestone ID to change status: ", "Can't be empty!", 0, Integer.MAX_VALUE);
                                System.out.println("The current mode is: " + milestoneController.searchMilestoneByID(id).getStatusString());
                                System.out.println("");

                                System.out.println("Change into: ");
                                displayChangeStatus();
                                option = Utility.getInt("Enter option: ", "Wrong!", 1, 3);
                                try {
                                    milestoneController.changeMilestoneStatus(id, option);

                                    System.out.println("Current Milestone detail: ");
                                    displayFeature();
                                    System.out.println(milestoneController.searchMilestoneByID(id));

                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            break;
                            case 5: { //update           
                                System.out.println("============================ Update Milestone ============================");
                                Milestone milestone;
                                do {
                                    id = Utility.getInt("Enter Milestone ID to update: ", "Wrong!", 1, Integer.MAX_VALUE);
                                    milestone = milestoneController.searchMilestoneByID(id);
                                } while (milestoneController.searchMilestoneByID(id) == null);

//                    System.out.println("Current Milestone detail: ");
//                    displayFeature();
//                    System.out.println(milestoneController.searchMilestoneByID(id));
//                    
                                milestoneName = Utility.getStringNull("Enter milestone name (Current: " + milestone.getMilestoneName() + "): ", milestone.getMilestoneName());

                                // show class list to choose - class ID FK
                                System.out.println("Class list available: ");
                                showListClassForMileStone(classDAO.getListWithOneCondition(" status = true "));
                                class_id = Utility.getIntNull("Enter class ID(Current: " + milestone.getClass_id() + "): ", milestone.getClass_id(), 0, Integer.MAX_VALUE);

                                //show iteraion list to choose - Iteration ID FK
                                System.out.println("Iteration list available: ");
                                showListIterationForMilestone(iterationDAO.getListWithOneCondition(" status = true "));
                                iteration_id = Utility.getIntNull("Enter iteration ID(Current: " + milestone.getIteration_id() + "): ", milestone.getIteration_id(), 0, Integer.MAX_VALUE);
                                System.out.println("");

                                int statusNumber = Utility.getIntNull("Enter 1 - active or 2 - deactive(Current: " + milestone.getStatusString() + "): ", milestone.getStatus(), 1, 2);
                                from_date = Utility.getDateUnchange("Enter From Date(Current: " + Utility.convertDateToString(milestone.getFrom_date()) + "): ", milestone.getFrom_date());
                                to_date = Utility.getDateUnchange("Enter To date(Current: " + Utility.convertDateToString(milestone.getTo_date()) + "): ", milestone.getTo_date());

                                try {
                                    milestoneController.updateMilestone(new Milestone(id, iteration_id, class_id, statusNumber, from_date, to_date, milestoneName));
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

    private static void displayChangeStatus() {
        System.out.println("1. Open ");
        System.out.println("2. Closed");
        System.out.println("3. Cancelled");
    }

    public static void showMilestoneList(List<Milestone> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("====================================== Milestone List =======================================");
            System.out.println(String.format("%-5s|%-15s|%-15s|%-15s|%-15s|%-15s|%-10s", "ID", "Milestone Name", "Iteration Name", "Class Code", "From Date", "To Date", "Status"));
            System.out.println("---------------------------------------------------------------------------------------------");

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

    public static void showListIterationForMilestone(List<Iteration> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("========= Iteration List =========");
            System.out.println(String.format("%-5s|%-15s|%-10s", "ID", "Iteration Name", "Status"));
            System.out.println("----------------------------------");
            for (int i = 0; i < sList.size(); i++) {
                System.out.println(String.format("%-5d|%-15s|%-10s",
                        sList.get(i).getIteration_id(),
                        sList.get(i).getIteration_name(),
                        sList.get(i).getStatusString()));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

}
