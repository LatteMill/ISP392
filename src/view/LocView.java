/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.*;
import dao.*;
import java.util.*;
import model.*;

public class LocView {

    static UserListController userListController = new UserListController();
    static UserDAO userDAO = new UserDAO();

    static TrackingController trackingController = new TrackingController();
    static TrackingDAO trackingDAO = new TrackingDAO();

    static ClassUserDAO classUserDAO = new ClassUserDAO();
    static ClassUserController classUserController = new ClassUserController();

    static LocController locController = new LocController();
    static LocDAO locDAO = new LocDAO();

    //display
    public static void displayLocChoiceList(boolean student) {
        if (!student) {
            System.out.println("============================ Your choice ============================");
            System.out.println("1. Search Loc");
            System.out.println("2. Sort Loc list");
            System.out.println("3. Add Loc into List");
            System.out.println("4. Update Loc");
            System.out.println("5. Back to Dashboard");
        } else {
            System.out.println("============================ Team Evaluation List ============================");
            System.out.println("1. Search Loc Evaluation");
            System.out.println("2. Sort Loc Evaluation");
            System.out.println("3. Back to Dashboard");
        }
    }

    public static void displaySearch() {
        System.out.println("============================ Search Loc List ============================");
    }

    public static void displaySort() {
        System.out.println("============================ Sort Loc ============================");
        System.out.println("1. Sort by time");
        System.out.println("2. Sort by tracking id");
        System.out.println("3. Sort by Quality");
        System.out.println("4. Back to Funtions choice");
    }

    public static void displayUpdate() {
        System.out.println("============================ Update Loc Profile ============================");
        System.out.println("*Skip ( Enter null ) if you dont want to change!");
        System.out.println("If you want to Cancelled, please go to change status to make sure.");
    }

    public static void displayFeature() {
        System.out.println("=============================================================== Loc List ================================================================");
        System.out.println(String.format("%-4s|%-4s|%-20s|%-10s|%-10s|%-6s|%-30s", "ID", "Time", "Note", "Complexity", "Quality", "Loc", "TrackingID"));
    }

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Go to Loc function.");
        System.out.println("5. Back.");
    }

    //lam nhu cua Linh dung classUser
    public static void showLocList(List<Loc> sList) throws Exception {

        if (sList.size() > 0) {
            displayFeature();
            for (int i = 0; i < sList.size(); i++) {
                System.out.println(sList.get(i));
            }

        } else {
            throw new Exception("Empty List!");
        }
    }

    public static void LocListForStudent(String email) throws Exception {
        int user_id = userListController.searchUserIDbyEmailInSublist(userDAO.getListWithOneCondition(" email like '" + email + "'"), email);
        Tracking tracks = trackingController.searchByAssigneeID(user_id);
        boolean student = true;

        List<Loc> locList = null;

        //loc list
        locList = locDAO.getListWithOneCondition(" b.assignee_id = " + tracks.getAssigneeID());

        int option = 0;
        int maxpage = locList.size() / 5;
        if (locList.size() % 5 != 0) {
            maxpage++;
        }

        int page = 1, showChoice = 0;

        //show page
        try {
            System.out.println("-----------------");
            System.out.println("Current page: " + page);
            showLocList(locController.pagination(locList, page));
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
                option = 0;
                while (option != 3) {
                    displayLocChoiceList(student);
                    option = Utility.getInt("Enter option: ", "Wrong!", 1, 3);
                    switch (option) {
                        case 1: {
                            displaySearch();
                            String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                            try {
                                List<Loc> searchList = locDAO.getListForSearch(pattern, student, tracks.getAssigneeID());
                                showLocList(searchList);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                        case 2: {
                            int sortOption = 0;
                            displaySort();
                            sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 4);
                            try {
                                List<Loc> sortList = locController.sortLoc(student, user_id, sortOption);
                                showLocList(sortList);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    }
                }
            }
            if (showChoice == 5) {
                return;
            }
        } while (0 > page || page > maxpage);

    }

    public static void LocListForTrainer(String email) throws Exception {
        //lấy user_id để phân quyền
        int user_id = userListController.searchUserIDbyEmailInSublist(userDAO.getListWithOneCondition(" email like '" + email + "'"), email);
        User u = userListController.searchUserByID(user_id);
        Tracking tracks = trackingController.searchByAssigneeID(user_id);
        boolean student = false;

        List<Tracking> trackingList = trackingDAO.getList();
        List<Loc> locList = locDAO.getList();

        int evaluation_id, comlexity_id, quality_id, tracking_id;
        String evalation_note;
        double evaluation_time, converted_loc;
        int page = 1, showChoice = 0;

        while (showChoice != 5) {

            int maxpage = locList.size() / 5;
            if (maxpage != 1) {
                if (locList.size() % 5 != 0) {
                    maxpage++;
                }
            }
            System.out.println("There are: " + maxpage + " pages.");

            //show page
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                showLocList(locController.pagination(locList, page));
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
                    while (option != 5) {
                        displayLocChoiceList(student);
                        option = Utility.getInt("OPTION: ", "Wrong", 1, 5);
                        switch (option) {
                            case 1:  //search 
                                displaySearch();
                                String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                                try {
                                    List<Loc> searchList = locDAO.getListForSearch(pattern, student, 0);
                                    showLocList(searchList);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 2:  //sort
                                int sortOption = 0;
                                displaySort();
                                sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 4);
                                try {
                                    List<Loc> sortList = locController.sortLoc(student, user_id, sortOption);
                                    showLocList(sortList);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 3:
                                System.out.println("============================ Add New Loc ============================");
                                //list check
                                //tracking ID FK:
                                do {
                                    System.out.println("Tracking list available:");
                                    showTrackingListForLoc(trackingList);
                                    tracking_id = Utility.getInt("Enter Tracking ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                                    if (!trackingController.checkTrackingExistByID(trackingList, tracking_id)) {
                                        System.out.println("Must enter tracking ID in list showed!");
                                    }
                                } while (!trackingController.checkTrackingExistByID(trackingList, tracking_id));
                                //lay tracking list available
                                evaluation_time = Utility.getDouble("Enter evaluation time: ", "Wrong.", 0, Double.MAX_VALUE);
                                evalation_note = Utility.getString("Enter evaluation note: ", "Wrong.", Utility.REGEX_STRING);
                                comlexity_id = Utility.getInt("Enter complexity with Simple(1), Medium(2), Complex(3): ", "Wrong.", 1, 3);
                                quality_id = Utility.getInt("Enter quality with Zero(1), Low(2), Medium(3), High(4):  ", "Wrong.", 1, 4);
                                Loc loc = new Loc(comlexity_id, quality_id, tracking_id, evaluation_time, evalation_note);
                                converted_loc = loc.getQualityPercent() * loc.getComplexityLoc();
                                try {
                                    locController.addNewLoc(new Loc(comlexity_id, quality_id, tracking_id, converted_loc, evaluation_time, evalation_note));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                                break;
                            case 4:  //update
                                System.out.println("============================ Update Loc ============================");
                                Loc locs;
                                do {
                                    System.out.println("Loc list available: ");
                                    showLocList(locList);
                                    evaluation_id = Utility.getInt("Enter evaluation id to update: ", "Wrong.", 1, Integer.MAX_VALUE);
                                    locs = locController.searchLocByID(evaluation_id);
                                    if (!locController.checkLocExistByEvaluationID(locList, evaluation_id)) {
                                        System.out.println("Must enter ID in list showed !");
                                    }
                                } while(locs == null);

                                do {
                                    System.out.println("Tracking list available:");
                                    showTrackingListForLoc(trackingList);
                                    tracking_id = Utility.getIntNull("Enter Tracking ID (Current: " + locs.getTracking_id() + " ): ", locs.getTracking_id(), 0, Integer.MAX_VALUE);
                                    if (!trackingController.checkTrackingExistByID(trackingList, tracking_id)) {
                                        System.out.println("Must enter tracking ID in list showed!");
                                    }
                                } while (!trackingController.checkTrackingExistByID(trackingList, tracking_id));
                                //tracking ID FK:
                                //lay tracking list available
                                evaluation_time = Utility.getDouble("Enter evaluation time: ", "Wrong.", 0, Double.MAX_VALUE);
                                evalation_note = Utility.getStringNull("Enter note  (Current: " + locs.getEvaluation_note() + "): ", locs.getEvaluation_note());
                                comlexity_id = Utility.getIntNull("Enter complexity with Simple(1), Medium(2), Complex(3) (Current: " + locs.getComplexityString() + "): ", locs.getComplexity_id(), 1, 3);
                                quality_id = Utility.getIntNull("Enter quality with Zero(1), Low(2), Medium(3), High(4) (Current: " + locs.getQualityString() + " ): ", locs.getQuality_id(), 1, 4);
//                                new Loc(comlexity_id, quality_id, tracking_id, evaluation_time, evalation_note);
                                converted_loc = locs.getQualityPercent() * locs.getComplexityLoc();
                                try {
                                    locController.updateLoc(new Loc(evaluation_id, comlexity_id, quality_id, tracking_id, converted_loc, evaluation_time, evalation_note));
                                    locList = locDAO.getList();
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                                break;
                            case 5:
                                return;

                        }
                    }
                }
            } while (0 > page || page > maxpage);

        }
    }

    public static void showTrackingListForLoc(List<Tracking> trackList) throws Exception {
        if (trackList.size() > 0) {
            System.out.println("======================================== Tracking List =========================================");
            System.out.println(String.format("%-3s|%-20s|%-20s|%-20s", "ID", "Assignee name", "Assigner name", "Note"));
            System.out.println("---------------------------------------------------------------------------------------------");

            for (int i = 0; i < trackList.size(); i++) {
                Tracking tracks = trackList.get(i);
                System.out.println(String.format("%-3s|%-20s|%-20s|%-20s", tracks.getTrackingID(),
                        tracks.getAssigneeName(), tracks.getAssignerName(), tracks.getTrackingNote()));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

}
