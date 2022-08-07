/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.*;
import java.util.*;
import model.*;
import view.*;

/**
 *
 * @author admin
 */
public class TrackingController {

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

    //show tracking list
    public void showTrackingList() throws Exception {

        System.out.println("                                                    Tracking List\n");
        pageOfDisplay(trackingDAO.getList());

    }

    public static int getTeamIDForTracking(String email, int user_id) throws Exception {
//        List<ClassUser> tList = new ArrayList<>();
        List<Team> teamList = new ArrayList<>();
        int teamID = 0, check = 0;
        List<ClassUser> classUsers = classUserDAO.getList();
        for (ClassUser classUser : classUsers) {
//            if (classUser.getEmail().equalsIgnoreCase(email)) {
//                tList.add(classUser);
            if (classUser.getUserID() == user_id) {
                teamID = classUser.getTeamID();
                System.out.println(teamID);
                Team findTeam = teamController.searchTeamIDByID(teamID);
                teamList.add(findTeam);
            }
        }
        if (teamList.size() == 0) {
            System.out.println("YOU ARE NOT IN ANY TEAM!!!");
            ClassUser newMember = ClassUserView.createNewClassUser(user_id);
            teamID = newMember.getTeamID();
            Tracking newTrack = new Tracking(teamID);
            trackingDAO.updateTrackingToList(newTrack);

        } else {
            System.out.println("You are in Team ");
            System.out.println("______________________________________________________________________");
            System.out.println(String.format("|%-10s|%-25s|%-10s", "TeamID", "TeamName", "Status"));
            System.out.println("______________________________________________________________________");

            for (Team classUser : teamList) {
                System.out.println(String.format("|%-10s|%-25s|%-10s", classUser.getTeam_id(), classUser.getTeam_name(), classUser.getStatusString()));
            }
            do {
                teamID = Utility.getInt("Enter your team to working on: ", "It must be number!!", 1, Integer.MAX_VALUE);
            } while (teamController.isTeamIDExistInList(teamList, teamID) == true);

        }
        return teamID;
    }

//public static int getTeamIDForWork(int)
    public static List<ClassUser> listTrackingTeam(int teamID) throws Exception {
        System.out.println("Team member in team " + teamID);

        List<ClassUser> userInTeam = classUserDAO.getList();
        List<ClassUser> userList = new ArrayList<>();
        System.out.println("___________________________________________________________________");
        System.out.printf(String.format("|%-10s|%-10s|%-30s", "TeamID", "UserID", "FullName"));
        System.out.println("");
        System.out.println("___________________________________________________________________");
        for (ClassUser classUser : userInTeam) {
            if (classUser.getTeamID() == teamID) {
                userList.add(classUser);
                System.out.println(String.format("|%-10s|%-10s|%-30s", classUser.getTeamID(), classUser.getUserID(), classUser.getFullName()));
            }
        }
        return userInTeam;
    }

    public static int getUserIDInTeam(String user, int teamID) throws Exception {
        int userID = 0;
        int check = 0;
        List<ClassUser> userList = listTrackingTeam(teamID);
        do {
            System.out.printf(user);
            userID = Utility.getInt("", "It must be number", 1, Integer.MAX_VALUE);
            for (ClassUser classUser : userList) {
                if (userID == classUser.getUserID()) {
                    check++;
                }
            }
            if (check == 0) {
                System.out.println("YOU CAN ONLY ADD USER ID IN LIST ABOVE!!!");
            }
        } while (check == 0);

        return userID;
    }

    //add tracking details
    public void addTrackingDetails(int teamID, int user_id) throws Exception {
        System.out.println("                                                    Add Tracking Details");
        List<Milestone> milestoneActive = milestoneController.showActiveMileStonList(milestoneDAO.getList());
        int milestoneID = 0;

        do {
            milestoneID = Utility.getInt("Enter availabe milestoneID: ", "It must be number", 1, Integer.MAX_VALUE);

        } while (milestoneController.isMilestoneIDExistInList(milestoneActive, milestoneID));
//        System.out.println(milestoneID);
        List<Function> functionActive = functionController.showActiveFunctionList(functionDAO.getFuncList(), teamID);

        int functionID = 0;

        do {
            functionID = Utility.getInt("Enter availabe functionID: ", "It must be number", 1, Integer.MAX_VALUE);

        } while (functionController.isFunctionIDExistInList(functionActive, functionID));
//        System.out.println(functionID);
//        int assigner_id = Utility.getInt("Enter availabe assigner_id: ", "It must be number", 1, Integer.MAX_VALUE);
//        int assigneeID = Utility.getInt("Enter availabe assigneeID: ", "It must be number", 1, Integer.MAX_VALUE);;

//        System.out.println(assigner_id);
        int assigneeID = getUserIDInTeam("ASSIGNEE ID: ", teamID);
//        System.out.println(assigneeID);
        String trackingNote = Utility.getStringNull("Enter note: ", " ");
//        System.out.println(trackingNote);
        boolean update = false;
        int status = 1;
        Tracking tr = new Tracking(teamID, milestoneID, functionID, user_id, assigneeID, trackingNote, update, status);
//        System.out.println(tr);
//        System.out.println("________________________________");
        List<Tracking> trackingList = trackingDAO.getList();
//        pageOfDisplay(trackingList);

        if (!isDuplicated(tr)) {
            trackingDAO.insertTrackingToList(tr);
            trackingList.add(tr);
//                for (Tracking tracking : trackingList) {
//                    System.out.println(tracking);
//                }
            System.out.println("ADD SUCCESSFULLY!!!");
//                List<Tracking> trackList = trackingDAO.getList();
//                displayHeader();
//                System.out.println(trackList.get(trackList.size() - 1));
        } else {
            System.out.println("CANCLE!!!\n data doesnot exist...");
            throw new Exception("Exist tracking!!!");
        }
    }

    public boolean isDuplicated(Tracking tr) throws Exception {
        List<Tracking> trackingList = trackingDAO.getList();
        for (Tracking trk : trackingList) {
            if (trk.getAssigneeID() == tr.getAssigneeID()
                    && tr.getAssignerID() == trk.getAssignerID()
                    && tr.getMilestoneID() == trk.getMilestoneID()
                    && tr.getFunctionID() == trk.getFunctionID()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isExistInDB(Tracking tr) throws Exception {
//        List<Tracking> trackingList = trackingDAO.getList();
        List<Team> tList = teamDAO.getList();
        List<Milestone> mList = milestoneDAO.getList();
        List<Function> fList = functionDAO.getList();
        List<UserList> uList = userListDAO.getList();
        int check = 0;
        if (fList.size() > 0 && uList.size() > 0 && tList.size() > 0 && mList.size() > 0) {
            for (Team t : tList) {
                if (t.getTeam_id() == tr.getTeamID()) {
                    check++;
                }
            }
            for (Milestone m : mList) {
                if (m.getMileStone_id() == tr.getMilestoneID()) {
                    check++;
                }
            }
            for (Function clr : fList) {
                if (clr.getFunction_id() == tr.getFunctionID()) {
                    check++;
                    ;// khong ton tai function_id

                }
            }

            for (UserList ul : uList) {
                if (ul.getId() == tr.getAssignerID()) {
                    check++;// khong ton tai user id

                }
            }

            for (UserList ul : uList) {
                if (ul.getId() == tr.getAssigneeID()) {
                    check++;// khong ton tai user id

                }
            }
        }

        return check == 5;// ton tai de them vao

    }

    public void updateTracking(int teamID) throws Exception {
        System.out.println("                                                    Update Tracking");
        List<Tracking> trackingList = trackingDAO.getList();
        List<Tracking> trackingIDList = new ArrayList<>();
        for (Tracking tracking : trackingList) {
            if (tracking.getTeamID() == teamID) {
                trackingIDList.add(tracking);
            }
        }
        if (trackingIDList.size() < 0) {
            System.out.println("Team has NO member!!!");
        }
        listTrackingTeam(teamID);
        int trackingID, check = 0;
        Tracking sb = new Tracking();
        do {
            trackingID = Utility.getInt("Enter trackingID to update: ", "Wrong!", 1, Integer.MAX_VALUE);
            for (Tracking tracking : trackingIDList) {
                if (tracking.getTrackingID() == trackingID) {
                    check++;
                }
            }
            if (check == 0) {
                System.out.println("YOU CAN ONLY ADD TRACKING ID IN LIST ABOVE!!!");
            }

        } while (check == 0);
        sb = getTrackingByTrackingID(trackingID);
        displayHeader();
        System.out.println(sb);
        check = 0;

//        System.out.println("AVAILABLE TEAM LIST");
//        teamController.pageOfDisplay(teamDAO.getList());
//        int teamID = 0;
//        do {
//            teamID = Utility.getIntNull("Enter teamID: ", sb.getTeamID(), 1, Integer.MAX_VALUE);
//
//        } while (teamController.isTeamIDExistInList(teamDAO.getList(), teamID) == true);
//        sb.setTeamID(teamID);
        System.out.println("AVAILABLE MILESTONE LIST");
        milestoneController.pageOfDisplay(milestoneDAO.getList());
        int milestoneID = 0;
        do {
            milestoneID = Utility.getIntNull("Enter availabe milestoneID: ", sb.getMilestoneID(), 1, Integer.MAX_VALUE);

        } while (milestoneController.isMilestoneIDExistInList(milestoneDAO.getList(), milestoneID) == true);
        sb.setMilestoneID(milestoneID);

        System.out.println("AVAILABLE FUNCTION LIST");
        List<Function> f = functionController.showActiveFunctionList(functionDAO.getFuncList(), teamID);
        int functionID = 0;
        do {
            functionID = Utility.getIntNull("Enter availabe functionID: ", sb.getFunctionID(), 1, Integer.MAX_VALUE);
        } while (functionController.isFunctionIDExistInList(f, functionID));
        sb.setFunctionID(functionID);

        listTrackingTeam(teamID);
        List<ClassUser> userList = listTrackingTeam(teamID);
        int assigner_id = 0, assignee_id = 0;
        do {

            assigner_id = Utility.getIntNull("Enter availabe assigner_id: ", sb.getAssignerID(), 1, Integer.MAX_VALUE);
            for (ClassUser classUser : userList) {
                if (assigner_id == classUser.getUserID()) {
                    check++;
                }
            }
            if (check == 0) {
                System.out.println("YOU CAN ONLY ADD USER ID IN LIST ABOVE!!!");
            }
        } while (check == 0);
        sb.setAssignerID(assigner_id);
        check = 0;
        do {
            assignee_id = Utility.getIntNull("Enter availabe assignee_id: ", sb.getAssigneeID(), 1, Integer.MAX_VALUE);
            for (ClassUser classUser : userList) {
                if (assignee_id == classUser.getUserID()) {
                    check++;
                }
            }
            if (check == 0) {
                System.out.println("YOU CAN ONLY ADD USER ID IN LIST ABOVE!!!");
            }
        } while (check == 0);
        sb.setAssigneeID(assignee_id);
        check = 0;
//        int assigner_id = Utility.getIntNull("Enter availabe assigner_id: ", sb.getAssignerID(), 1, Integer.MAX_VALUE);
//        sb.setAssignerID(assigner_id);
//
//        int assigneeID = Utility.getIntNull("Enter availabe assigneeID: ", sb.getAssigneeID(), 1, Integer.MAX_VALUE);;
//        sb.setAssigneeID(assigneeID);
        String trackingNote = Utility.getStringNull("Enter note: ", " ");
        sb.setTrackingNote(trackingNote);

        boolean update = false;
        int status = Utility.getIntNull("1. Active  2.Deactive  3.Blocked\nEnter status: ", sb.getStatus(), 1, Integer.MAX_VALUE);
        sb.setStatus(status);

        Tracking sbNew = new Tracking(teamID, milestoneID, functionID, assigner_id, assignee_id, trackingNote, update, status);
        if (isExistInDB(sb)) {
            if (isDuplicated(sbNew)) {
                System.out.println("DUPLICATED!!!");
            } else if (notUpdateTracking(sbNew)) {
                System.out.println("NOT UPDATE!!!");
            } else {
                sbNew.setUpdate(true);
                trackingList.add(sbNew);
                trackingDAO.updateTrackingToList(sbNew);
                System.out.println("UPDATE SUCCESSFULLY!!!\n");
                displayHeader();
                System.out.println(sb);

            }
        } else {
            System.out.println("CANNOT UPDATE!!!\n data doesnot exist in database...");
        }
    }

    public Tracking getTrackingByTrackingID(int trackingID) throws Exception {
        List<Tracking> trackingList = trackingDAO.getList();
        for (Tracking tracking : trackingList) {
            if (tracking.getTrackingID() == trackingID) {
                return tracking;
            }

        }
//        System.out.println("TRACKING ID DOESNOT EXIST!!!");
        return null;
    }

    public Tracking getTrackingByAssigneeID(int assigneeID) throws Exception {
        List<Tracking> trackingList = trackingDAO.getList();
        for (Tracking tracking : trackingList) {
            if (tracking.getAssigneeID() == assigneeID) {
                return tracking;
            }

        }
        System.out.println("ASSIGNEE ID DOES NOT EXIST!!!");
        return null;
    }

    private boolean notUpdateTracking(Tracking sbNew) throws Exception {
        List<Tracking> trackingList = trackingDAO.getList();
        for (Tracking a : trackingList) {
            if (a.getTrackingID() == sbNew.getTrackingID()
                    && a.getTeamID() == sbNew.getTeamID()
                    && a.getFunctionID() == sbNew.getFunctionID()
                    && a.getAssignerID() == sbNew.getAssignerID()
                    && a.getAssigneeID() == sbNew.getAssigneeID()
                    && a.getTrackingNote().equalsIgnoreCase(sbNew.getTrackingNote())
                    && a.getStatus() == sbNew.getStatus()) {
                return true;
            }
        }
        return false;
    }

    public void changeTrackingStatus(int teamID) throws Exception {
        System.out.println("                                                    Change Tracking Status");
        List<Tracking> trackingList = trackingDAO.getList();
        List<Tracking> trackingIDList = new ArrayList<>();
        for (Tracking tracking : trackingList) {
            if (tracking.getTeamID() == teamID) {
                trackingIDList.add(tracking);
            }
        }
        if (trackingIDList.size() < 0) {
            System.out.println("Team has NO member!!!");
        }
        pageOfDisplay(trackingIDList);
        int trackingID, check = 0;
        Tracking sb = new Tracking();
        do {
            trackingID = Utility.getInt("Enter trackingID to update: ", "Wrong!", 1, Integer.MAX_VALUE);
            for (Tracking tracking : trackingIDList) {
                if (tracking.getTrackingID() == trackingID) {
                    check++;
                }
            }
            if (check == 0) {
                System.out.println("YOU CAN ONLY ADD TRACKING ID IN LIST ABOVE!!!");
            }

        } while (check == 0);
        sb = getTrackingByTrackingID(trackingID);
        displayHeader();
        System.out.println(sb);
        check = 0;
        displayTrackingStatus();
        int status = Utility.getInt("Enter your choice: ", "It must be number!!!", 1, 3);
        if (status == sb.getStatus()) {
            sb.setUpdate(false);
        } else {
            sb.setUpdate(true);
        }
        sb.setStatus(status);
        trackingDAO.updateTrackingToList(sb);
        System.out.println(
                "Current tracking detail:");
        displayHeader();
        System.out.println(sb);

    }

    private void displayTrackingStatus() {
        System.out.println("1. Active ");
        System.out.println("2. Deactive");
        System.out.println("3. Blocked");

    }

    public void showTeamTracking(int teamID) throws Exception {
        List<Tracking> trackingList = trackingDAO.getList();
        List<Tracking> teamSearch = new ArrayList<>();
        for (Tracking track : trackingList) {
            if (track.getTeamID() == teamID) {
                teamSearch.add(track);
            }
        }
        if (teamSearch.size() == 0) {
            System.out.println("NOT FOUND TEAM ID!!!");
        } else {
            pageOfDisplay(teamSearch);
        }
    }

    public void searchTrackingByFilter(int choice) throws Exception {
        List<Tracking> trackingList = trackingDAO.getList();
        switch (choice) {
            //1. Search by tracking ID
            case 1:
                int trackingID;
                Tracking tracking = new Tracking();
                trackingID = Utility.getInt("Enter trackingID to search: ", "Wrong!", 1, Integer.MAX_VALUE);
                tracking = getTrackingByTrackingID(trackingID);
                if ((getTrackingByTrackingID(trackingID) == null)) {
                    System.out.println("NOT FOUND TRACKING ID!!!");
                } else {
                    displayHeader();
                    System.out.println(tracking);
                }

                break;
            //2. Search by team ID 
            case 2:
                int teamID;

                teamID = Utility.getInt("Enter teamID to search: ", "Wrong!", 1, Integer.MAX_VALUE);
                List<Tracking> teamSearch = new ArrayList<>();
                for (Tracking track : trackingList) {
                    if (track.getTeamID() == teamID) {
                        teamSearch.add(track);
                    }
                }
                if (teamSearch.size() == 0) {
                    System.out.println("NOT FOUND TEAM ID!!!");
                } else {
                    pageOfDisplay(teamSearch);
                }

                break;
            //3. Search by Milestone ID
            case 3:
                int milestoneID;
                milestoneID = Utility.getInt("Enter milestoneID to search: ", "Wrong!", 1, Integer.MAX_VALUE);
                List<Tracking> milestoneSearch = new ArrayList<>();
                for (Tracking track : trackingList) {
                    if (track.getMilestoneID() == milestoneID) {
                        milestoneSearch.add(track);
                    }
                }
                if (milestoneSearch.size() == 0) {
                    System.out.println("NOT FOUND MILESTONE ID!!!");
                } else {
                    pageOfDisplay(milestoneSearch);
                }

                break;
            //4. Seach by function ID
            case 4:
                int functionID;
                functionID = Utility.getInt("Enter functionID to search: ", "Wrong!", 1, Integer.MAX_VALUE);
                List<Tracking> functionSearch = new ArrayList<>();
                for (Tracking track : trackingList) {
                    if (track.getFunctionID() == functionID) {
                        functionSearch.add(track);
                    }
                }
                if (functionSearch.size() == 0) {
                    System.out.println("NOT FOUND FUNCTION ID!!!");
                } else {
                    pageOfDisplay(functionSearch);
                }

                break;
            //5. Search by assigner ID
            case 5:
                int assignerID;
                assignerID = Utility.getInt("Enter assignerID to search: ", "Wrong!", 1, Integer.MAX_VALUE);
                List<Tracking> assignerSearch = new ArrayList<>();
                for (Tracking track : trackingList) {
                    if (track.getAssignerID() == assignerID) {
                        assignerSearch.add(track);
                    }
                }
                if (assignerSearch.size() == 0) {
                    System.out.println("NOT FOUND ASSIGNER ID!!!");
                } else {
                    pageOfDisplay(assignerSearch);
                }

                break;
            //6. Seach by assignee ID
            case 6:
                int assigneeID;
                assigneeID = Utility.getInt("Enter assigneeID to search: ", "Wrong!", 1, Integer.MAX_VALUE);
                List<Tracking> assigneeSearch = new ArrayList<>();
                for (Tracking track : trackingList) {
//                    System.out.println(track);
                    if (track.getAssigneeID() == assigneeID) {
                        assigneeSearch.add(track);
                        System.out.println(track);
                    }
                }
                System.out.println("_________________________");
                for (Tracking tracking1 : assigneeSearch) {
                    System.out.println(tracking1);
                }
                if (assigneeSearch.size() == 0) {
                    System.out.println("NOT FOUND ASSIGNEE ID!!!");
                } else {
                    System.out.println(assigneeSearch.size());
                    pageOfDisplay(assigneeSearch);
                }

                break;
            //7. Seach by status
            case 7:
                int status;
                status = Utility.getInt("Enter status to search: ", "Wrong!", 1, 3);
                List<Tracking> statusSearch = new ArrayList<>();
                for (Tracking track : trackingList) {
                    if (track.getStatus() == status) {
                        statusSearch.add(track);
                    }
                }
                if (statusSearch.size() == 0) {
                    System.out.println("NOT FOUND STATUS!!!");
                } else {
                    pageOfDisplay(statusSearch);
                }

                break;

        }
    }

    public void searchTrackingTeamByFilter(int choice, int team_id) throws Exception {
        List<Tracking> trackingList = trackingDAO.getListWithTeamID(team_id);
        pageOfDisplay(trackingList);
        switch (choice) {
            //1. Search by tracking ID
            case 1:
                int trackingID;
                Tracking tracking = new Tracking();
                trackingID = Utility.getInt("Enter trackingID to search: ", "Wrong!", 1, Integer.MAX_VALUE);
                tracking = getTrackingByTrackingID(trackingID);
                if ((getTrackingByTrackingID(trackingID) == null)) {
                    System.out.println("NOT FOUND TRACKING ID!!!");
                } else {
                    displayHeader();
                    System.out.println(tracking);
                }

                break;
            //2. Search by team ID 
            case 2:
                int teamID;

                teamID = Utility.getInt("Enter teamID to search: ", "Wrong!", 1, Integer.MAX_VALUE);
                List<Tracking> teamSearch = new ArrayList<>();
                for (Tracking track : trackingList) {
                    if (track.getTeamID() == teamID) {
                        teamSearch.add(track);
                    }
                }
                if (teamSearch.size() == 0) {
                    System.out.println("NOT FOUND TEAM ID!!!");
                } else {
                    pageOfDisplay(teamSearch);
                }

                break;
            //3. Search by Milestone ID
            case 3:
                int milestoneID;
                milestoneID = Utility.getInt("Enter milestoneID to search: ", "Wrong!", 1, Integer.MAX_VALUE);
                List<Tracking> milestoneSearch = new ArrayList<>();
                for (Tracking track : trackingList) {
                    if (track.getMilestoneID() == milestoneID) {
                        milestoneSearch.add(track);
                    }
                }
                if (milestoneSearch.size() == 0) {
                    System.out.println("NOT FOUND MILESTONE ID!!!");
                } else {
                    pageOfDisplay(milestoneSearch);
                }

                break;
            //4. Seach by function ID
            case 4:
                int functionID;
                functionID = Utility.getInt("Enter functionID to search: ", "Wrong!", 1, Integer.MAX_VALUE);
                List<Tracking> functionSearch = new ArrayList<>();
                for (Tracking track : trackingList) {
                    if (track.getFunctionID() == functionID) {
                        functionSearch.add(track);
                    }
                }
                if (functionSearch.size() == 0) {
                    System.out.println("NOT FOUND FUNCTION ID!!!");
                } else {
                    pageOfDisplay(functionSearch);
                }

                break;
            //5. Search by assigner ID
            case 5:
                int assignerID;
                assignerID = Utility.getInt("Enter assignerID to search: ", "Wrong!", 1, Integer.MAX_VALUE);
                List<Tracking> assignerSearch = new ArrayList<>();
                for (Tracking track : trackingList) {
                    if (track.getAssignerID() == assignerID) {
                        assignerSearch.add(track);
                    }
                }
                if (assignerSearch.size() == 0) {
                    System.out.println("NOT FOUND ASSIGNER ID!!!");
                } else {
                    pageOfDisplay(assignerSearch);
                }

                break;
            //6. Seach by assignee ID
            case 6:
                int assigneeID;
                assigneeID = Utility.getInt("Enter assigneeID to search: ", "Wrong!", 1, Integer.MAX_VALUE);
                List<Tracking> assigneeSearch = new ArrayList<>();
                for (Tracking track : trackingList) {
//                    System.out.println(track);
                    if (track.getAssigneeID() == assigneeID) {
                        assigneeSearch.add(track);
                        System.out.println(track);
                    }
                }
                System.out.println("_________________________");
                for (Tracking tracking1 : assigneeSearch) {
                    System.out.println(tracking1);
                }
                if (assigneeSearch.size() == 0) {
                    System.out.println("NOT FOUND ASSIGNEE ID!!!");
                } else {
                    System.out.println(assigneeSearch.size());
                    pageOfDisplay(assigneeSearch);
                }

                break;
            //7. Seach by status
            case 7:
                int status;
                status = Utility.getInt("Enter status to search: ", "Wrong!", 1, 3);
                List<Tracking> statusSearch = new ArrayList<>();
                for (Tracking track : trackingList) {
                    if (track.getStatus() == status) {
                        statusSearch.add(track);
                    }
                }
                if (statusSearch.size() == 0) {
                    System.out.println("NOT FOUND STATUS!!!");
                } else {
                    pageOfDisplay(statusSearch);
                }

                break;

        }
    }

    public void sortTrackingByFilter(int choiceSort) throws Exception {
        List<Tracking> trackingList = trackingDAO.getList();
//        List<Tracking> listSort = new ArrayList<>();
        switch (choiceSort) {
            //1. Sort by tracking ID
            case 1:
                //Loop run from the first to last Tracking of array 
                for (int i = 0; i < trackingList.size(); i++) {

                    /*Loop run from first element to the 
            element stand before the last unsorted element */
                    for (int j = 0; j < trackingList.size() - i - 1; j++) {

                        //Compare each pair adjacent elements
                        if (trackingList.get(j).getTrackingID() > trackingList.get(j + 1).getTrackingID()) {
                            Tracking temp = trackingList.get(j);
                            trackingList.set(j, trackingList.get(j + 1));
                            trackingList.set(j + 1, temp);

                        }
                    }
                }
                pageOfDisplay(trackingList);
                break;
            //2. Sort by team ID 
            case 2:
                Collections.sort(trackingList, new Comparator<Tracking>() {
                    @Override
                    public int compare(Tracking o1, Tracking o2) {
                        return Integer.toString(o1.getTeamID()).compareTo(Integer.toString(o2.getTeamID()));
                    }
                });
                pageOfDisplay(trackingList);
                break;
            //3. Sort by Milestone ID
            case 3:
                //Loop run from the first to last Tracking of array 
                for (int i = 0; i < trackingList.size(); i++) {

                    /*Loop run from first element to the 
            element stand before the last unsorted element */
                    for (int j = 0; j < trackingList.size() - i - 1; j++) {

                        //Compare each pair adjacent elements
                        if (trackingList.get(j).getMilestoneID()> trackingList.get(j + 1).getMilestoneID()) {
                            Tracking temp = trackingList.get(j);
                            trackingList.set(j, trackingList.get(j + 1));
                            trackingList.set(j + 1, temp);

                        }
                    }
                }
                pageOfDisplay(trackingList);
                break;
            //4. Sort by function ID
            case 4:
                Collections.sort(trackingList, new Comparator<Tracking>() {
                    @Override
                    public int compare(Tracking o1, Tracking o2) {
                        return Integer.toString(o1.getFunctionID()).compareTo(Integer.toString(o2.getFunctionID()));
                    }
                });
                pageOfDisplay(trackingList);
                break;
            //5. Sort by assigner ID
            case 5:
                //Loop run from the first to last Tracking of array 
                for (int i = 0; i < trackingList.size(); i++) {

                    /*Loop run from first element to the 
            element stand before the last unsorted element */
                    for (int j = 0; j < trackingList.size() - i - 1; j++) {

                        //Compare each pair adjacent elements
                        if (trackingList.get(j).getAssignerID()> trackingList.get(j + 1).getAssignerID()) {
                            Tracking temp = trackingList.get(j);
                            trackingList.set(j, trackingList.get(j + 1));
                            trackingList.set(j + 1, temp);

                        }
                    }
                }
                pageOfDisplay(trackingList);
                break;
            //6. Sort by assignee ID
            case 6:
                //Loop run from the first to last Tracking of array 
                for (int i = 0; i < trackingList.size(); i++) {

                    /*Loop run from first element to the 
            element stand before the last unsorted element */
                    for (int j = 0; j < trackingList.size() - i - 1; j++) {

                        //Compare each pair adjacent elements
                        if (trackingList.get(j).getAssigneeID()> trackingList.get(j + 1).getAssigneeID()) {
                            Tracking temp = trackingList.get(j);
                            trackingList.set(j, trackingList.get(j + 1));
                            trackingList.set(j + 1, temp);

                        }
                    }
                }
                pageOfDisplay(trackingList);
                break;
            //7. Sort by status
            case 7:
                Collections.sort(trackingList, new Comparator<Tracking>() {
                    @Override
                    public int compare(Tracking o1, Tracking o2) {
                        return o1.getStatusString().compareTo(o2.getStatusString());
                    }
                });
                pageOfDisplay(trackingList);
                break;
        }
    }

    public void sortTrackingTeamByFilter(int choiceSort, int team_id) throws Exception {
        List<Tracking> trackingList = trackingDAO.getListWithTeamID(team_id);
//        List<Tracking> listSort = new ArrayList<>();
        switch (choiceSort) {
            //1. Sort by tracking ID
            case 1:

                //Loop run from the first to last Tracking of array 
                for (int i = 0; i < trackingList.size(); i++) {

                    /*Loop run from first element to the 
            element stand before the last unsorted element */
                    for (int j = 0; j < trackingList.size() - i - 1; j++) {

                        //Compare each pair adjacent elements
                        if (trackingList.get(j).getTrackingID() > trackingList.get(j + 1).getTrackingID()) {
                            Tracking temp = trackingList.get(j);
                            trackingList.set(j, trackingList.get(j + 1));
                            trackingList.set(j + 1, temp);

                        }
                    }
                }

                pageOfDisplay(trackingList);
                break;
            //2. Sort by team ID 
            case 2:
                Collections.sort(trackingList, new Comparator<Tracking>() {
                    @Override
                    public int compare(Tracking o1, Tracking o2) {
                        return Integer.toString(o1.getTeamID()).compareTo(Integer.toString(o2.getTeamID()));
                    }
                });
                pageOfDisplay(trackingList);
                break;
            //3. Sort by Milestone ID
            case 3:
                //Loop run from the first to last Tracking of array 
                for (int i = 0; i < trackingList.size(); i++) {

                    /*Loop run from first element to the 
            element stand before the last unsorted element */
                    for (int j = 0; j < trackingList.size() - i - 1; j++) {

                        //Compare each pair adjacent elements
                        if (trackingList.get(j).getMilestoneID() > trackingList.get(j + 1).getMilestoneID()) {
                            Tracking temp = trackingList.get(j);
                            trackingList.set(j, trackingList.get(j + 1));
                            trackingList.set(j + 1, temp);

                        }
                    }
                }
                pageOfDisplay(trackingList);
                break;
            //4. Sort by function ID
            case 4:
                Collections.sort(trackingList, new Comparator<Tracking>() {
                    @Override
                    public int compare(Tracking o1, Tracking o2) {
                        return Integer.toString(o1.getFunctionID()).compareTo(Integer.toString(o2.getFunctionID()));
                    }
                });
                pageOfDisplay(trackingList);
                break;
            //5. Sort by assigner ID
            case 5:
                //Loop run from the first to last Tracking of array 
                for (int i = 0; i < trackingList.size(); i++) {

                    /*Loop run from first element to the 
            element stand before the last unsorted element */
                    for (int j = 0; j < trackingList.size() - i - 1; j++) {

                        //Compare each pair adjacent elements
                        if (trackingList.get(j).getAssignerID()> trackingList.get(j + 1).getAssignerID()) {
                            Tracking temp = trackingList.get(j);
                            trackingList.set(j, trackingList.get(j + 1));
                            trackingList.set(j + 1, temp);

                        }
                    }
                }
                pageOfDisplay(trackingList);
                break;
            //6. Sort by assignee ID
            case 6:
                //Loop run from the first to last Tracking of array 
                for (int i = 0; i < trackingList.size(); i++) {

                    /*Loop run from first element to the 
            element stand before the last unsorted element */
                    for (int j = 0; j < trackingList.size() - i - 1; j++) {

                        //Compare each pair adjacent elements
                        if (trackingList.get(j).getAssigneeID()> trackingList.get(j + 1).getAssigneeID()) {
                            Tracking temp = trackingList.get(j);
                            trackingList.set(j, trackingList.get(j + 1));
                            trackingList.set(j + 1, temp);

                        }
                    }
                }
                pageOfDisplay(trackingList);
                break;
            //7. Sort by status
            case 7:
                Collections.sort(trackingList, new Comparator<Tracking>() {
                    @Override
                    public int compare(Tracking o1, Tracking o2) {
                        return o1.getStatusString().compareTo(o2.getStatusString());
                    }
                });
                pageOfDisplay(trackingList);
                break;
        }
    }

    public void deleteTracking(int teamID) throws Exception {
        List<Tracking> trackingList = trackingDAO.getList();
        List<Tracking> trackingIDList = new ArrayList<>();
        for (Tracking tracking : trackingList) {
            if (tracking.getTeamID() == teamID) {
                trackingIDList.add(tracking);
            }
        }
        if (trackingIDList.size() < 0) {
            System.out.println("Team has NO member!!!");
        }
        pageOfDisplay(trackingIDList);
        int trackingID, check = 0;
        Tracking sb = new Tracking();
        do {
            trackingID = Utility.getInt("Enter trackingID to delete: ", "Wrong!", 1, Integer.MAX_VALUE);
            for (Tracking tracking : trackingIDList) {
                if (tracking.getTrackingID() == trackingID) {
                    check++;
                }
            }
            if (check == 0) {
                System.out.println("YOU CAN ONLY ADD TRACKING ID IN LIST ABOVE!!!");
            }

        } while (check == 0);
        sb = getTrackingByTrackingID(trackingID);
        displayHeader();
        System.out.println(sb);
        check = 0;
        trackingDAO.deleteTrackingToList(sb);
        System.out.println("");
    }

    public List<Tracking> pagination(List<Tracking> list, int page) throws Exception {
        //set up list 5 để trả về
        List<Tracking> trackingList = new ArrayList<>();
        //setup biến bắt đầu
        int start = 5 * (page - 1);

        //số lượng page có
        int maxpage = list.size() / 5;
        if (list.size() % 5 != 0) {
            maxpage++;
        }

        //thỏa mãn điều kiện page nằm trong danh sách page
        if (0 < page && page <= maxpage) {
            int i = start;
            //chạy vòng lặp cho 5 lần
            do {
                if (i == list.size()) {
                    break;
                } else {
                    if (list.get(i) != null) {
                        trackingList.add(list.get(i));
                    }
                }
                i++;
            } while (i < start + 5);

            return trackingList;

        } else {
            throw new Exception("Can't not go to that page");
        }

    }

    public void displayList(List<Tracking> list) throws Exception {
        if (list.size() > 0) {
            System.out.println("                                                Tracking List");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("|%-5s|%-7s|%-12s|%-15s|%-10s|%-20s|%-10s|%-20s|%-10s|%-7s|%-5s", "ID", "teamID", "milestoneID", "functionName",
                    "assignerID", "assignerName", "assigneeID", "assigneeName", "Note", "Update", "Status");
            System.out.println("");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Back.");
    }

    public void pageOfDisplay(List<Tracking> listDisplay) {
        int showChoice = 0;
        int page = 1;
        while (showChoice != 4) {
            int maxpage = listDisplay.size() / 5;
            if (listDisplay.size() % 5 != 0) {
                maxpage++;
            }
            System.out.println("Total of page: " + maxpage);
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                displayList(pagination(listDisplay, page));
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
    }

    private void displayHeader() {
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("|%-5s|%-7s|%-12s|%-20s|%-10s|%-20s|%-10s|%-20s|%-15s|%-7s|%-5s", "ID", "teamID", "milestoneID", "functionName",
                "assignerID", "assignerName", "assigneeID", "assigneeName", "trackingNote", "Update", "Status");
        System.out.println("");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");

    }

    //Ham cua Phuoc dung cho Loc
    public static boolean checkTrackingExistByID(List<Tracking> c, int id) throws Exception {
        if (c.size() > 0) {
            for (int i = 0; i < c.size(); i++) {
                if (c.get(i).getTrackingID() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    public static Tracking searchByAssigneeID(int user_id) throws Exception {
        List<Tracking> sbList = trackingDAO.getList();
        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getAssigneeID() == user_id) {
                    return sbList.get(i);
                }
            }
        }
        return null;

    }

}
