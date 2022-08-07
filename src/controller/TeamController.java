package controller;

import dao.*;
import java.util.*;
import model.*;
import view.Utility;

public class TeamController {
    //Main code owner of this class: Linh
    //Have function add in this class: Anh ( for Tracking class)

    static TeamDAO teamDAO = new TeamDAO();
    static ClassDAO classDAO = new ClassDAO();
    static ClassController classController = new ClassController();

    //check code subject ton tai chua? 
    public static boolean checkTeamExist(Team te) throws Exception {
        List<Team> teamList = teamDAO.getList();

        if (teamList.size() > 0) {
            for (int i = 0; i < teamList.size(); i++) {
                if (teamList.get(i).getClass_id() == te.getClass_id()
                        && teamList.get(i).getTopic_code().compareToIgnoreCase(te.getTopic_code()) == 0
                        && teamList.get(i).getTopic_name().compareToIgnoreCase(te.getTopic_name()) == 0
                        && teamList.get(i).getGitlab_url().compareToIgnoreCase(te.getGitlab_url()) == 0) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //check code subject ton tai chua? 
    public static boolean checkTeamExistByID(int id) throws Exception {
        List<Team> teamList = teamDAO.getList();

        if (teamList.size() > 0) {
            for (int i = 0; i < teamList.size(); i++) {
                if (teamList.get(i).getTeam_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //them subject moi
    public static void addNewTeam(Team te) throws Exception {
        List<Team> teamList = teamDAO.getList();

        if (!checkTeamExist(te)) {
            if (classController.checkClassroomExistByID(classDAO.getListWithOneCondition(" status = true "), te.getClass_id())) {
                teamList.add(te);
                teamDAO.insertTeamToList(te);
                System.out.println("Add successfully!");
            } else {
                throw new Exception("Enter Class ID in available list only!");
            }

        } else {
            throw new Exception("Exist Team!");
        }
    }

//    //find subject index
//    public static int searchTeamIndexByCode(String code) throws Exception {
//        List<Team> teamList = teamDAO.getList();
//        if (teamList.size() > 0) {
//            for (int i = 0; i < teamList.size(); i++) {
//                if (teamList.get(i).getTopic_code().compareToIgnoreCase(code) == 0) {
//                    return i;
//                }
//            }
//        }
//        return -1;
//    }
    //find subject index
    public static int searchTeamIndexByID(int id) throws Exception {
        List<Team> teamList = teamDAO.getList();
        if (teamList.size() > 0) {
            for (int i = 0; i < teamList.size(); i++) {
                if (teamList.get(i).getTeam_id() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    /*
    //find Subject
    public static Team searchSubjectByCode(String code) throws Exception {
        List<Team> teamList = teamDAO.getList();
        if (teamList.size() > 0) {
            for (int i = 0; i < teamList.size(); i++) {
                if (teamList.get(i).getSubjectCode().compareToIgnoreCase(code) == 0) {
                    return teamList.get(i);
                }
            }
        }
        return null;
    }
     */
    public static Team searchTeamByID(int id) throws Exception {
        List<Team> teamList = teamDAO.getList();
        if (teamList.size() > 0) {
            for (int i = 0; i < teamList.size(); i++) {
                if (teamList.get(i).getTeam_id() == id) {
                    return teamList.get(i);
                }
            }
        }
        return null;
    }

    //update subject 
    public static void updateTeam(Team team) throws Exception {
        List<Team> teamList = teamDAO.getList();

        if (checkTeamExistByID(team.getTeam_id())) {
            if (classController.checkClassroomExistByID(classDAO.getListWithOneCondition(" status = true "), team.getClass_id())) //    for (int i = 0; i < teamList.size(); i++) {
            //        if (team.getClass_id() == teamList.get(i).getClass_id()) {
            {
                teamList.set(searchTeamIndexByID(team.getClass_id()), team);

                teamDAO.updateTeamToList(team);

                System.out.println("Update successfully!");;
                //        } else {
                //            throw new Exception("Class ID is NOT exist!");
                //        }
                //    }
            } else {
                throw new Exception("Enter Class ID in available list only!");
            }
        } else {
            throw new Exception("Can not find the team!");
        }

    }

    public static void changeTeamStatus(int id, int option) throws Exception {
        List<Team> teamList = teamDAO.getList();
        if (checkTeamExistByID(id)) {
            try {
                Team team = searchTeamByID(id);
                if (option == 1) {
                    team.setStatus(true);
                } else {
                    team.setStatus(false);
                }
                teamDAO.updateTeamToList(team);

            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new Exception("Team is NOT exist!");
        }
    }

    //seach code(1), name(2), status(3)
    public List<Team> searchByPattern(String pattern) throws Exception {
        List<Team> fullList = teamDAO.getList();
        List<Team> searchList = teamDAO.getSearchList(pattern);
        return searchList;
    }
    //sap xep subject

    public List<Team> sortTeam(int choice) throws Exception {
        List<Team> sortList = teamDAO.getList();
        switch (choice) {
            case 1: // so sánh code
                Collections.sort(sortList, new Comparator<Team>() {
                    @Override
                    public int compare(Team o1, Team o2) {
                        return o1.getTopic_code().compareTo(o2.getTopic_code());
                    }
                });
                break;

            case 2: //so sánh name
                Collections.sort(sortList, new Comparator<Team>() {
                    @Override
                    public int compare(Team o1, Team o2) {
                        return o1.getTopic_name().compareTo(o2.getTopic_name());
                    }
                });
                break;

            case 3: //so sanh active
                Collections.sort(sortList, new Comparator<Team>() {
                    @Override
                    public int compare(Team o1, Team o2) {
                        return o1.getStatusString().compareTo(o2.getStatusString());
                    }
                });
                break;
            case 4: //so sanh class_id
                Collections.sort(sortList, new Comparator<Team>() {
                    @Override
                    public int compare(Team o1, Team o2) {
                        return Integer.toString(o1.getTeam_id()).compareTo(Integer.toString(o2.getClass_id()));
                    }
                });
                break;

            //nếu nhập khác thì exit sort
            default:
                System.out.println("Exit sort.");
                break;
        }

        return sortList;
    }

    //========================ham cua Hoi cho Feature====================================
    public void showListTeamForFeature(List<Team> teamList) throws Exception {

        if (teamList.size() > 0) {
            System.out.println("========= Team List =========");
            System.out.println(String.format("%-5s|%-15s|%-10s", "ID", "Topic_name", "Status"));
            System.out.println("----------------------------------");
            for (int i = 0; i < teamList.size(); i++) {
                System.out.println(String.format("%-5d|%-15s|%-20s|%-10s",
                        teamList.get(i).getTeam_id(),
                        teamList.get(i).getTopic_code(),
                        teamList.get(i).getTopic_name(),
                        teamList.get(i).getStatusString()));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

    public static boolean checkTeamExistByID(List<Team> teamList, int id) throws Exception {
        if (teamList.size() > 0) {
            for (int i = 0; i < teamList.size(); i++) {
                if (teamList.get(i).getTeam_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }
    //======================================================================================================

//    public static int searchClassIDByTeamID(int team_id) throws Exception{
//        List<Team> tl= teamDAO.getList();
//        for(int i=0;i<tl.size();i++){
//            if(tl.get(i).getTeam_id()==team_id){
//                return tl.get(i).getClass_id();
//            }
//        }
//        return -1;
//    }
    //====================HAM CUA ANH CHO TRACKING===========================
    public List<Team> pagination(List<Team> list, int page) throws Exception {
        //set up list 5 để trả về
        List<Team> trackingList = new ArrayList<>();
        //setup biến bắt đầu
        int start = 5 * (page - 1);

        //số lượng page có
        int maxpage = list.size() / 5;
        if (list.size() % 5 != 0) {
            maxpage++;
        }
        System.out.println("Total of page: " + maxpage);
        //thỏa mãn điều kiện page nằm trong danh sách page
        if (0 < page && page <= maxpage) {

            //chạy vòng lặp ho 5 lần
            for (int i = start; i < start + 5; i++) {
//                System.out.println(list.get(i));
                if (i == list.size()) {
                    break;
                } else {
                    if (list.get(i) != null) {
                        trackingList.add(list.get(i));
                    }
                }
            }

            return trackingList;

        } else {
            throw new Exception("Can't not go to that page");
        }

    }

    public List<Team> showActiveTeamList(List<Team> teamList) throws Exception {
        List<Team> activeTeam = new ArrayList<>();
        System.out.println("AVAILABLE TEAM");
        if (teamList.size() > 0) {
            for (int i = 0; i < teamList.size(); i++) {
                if (teamList.get(i).getStatusString().equalsIgnoreCase("Active")) {
                    activeTeam.add(teamList.get(i));
                }

            }
            pageOfDisplay(activeTeam);

        } else {
            throw new Exception("Empty List!");
        }
        return activeTeam;
    }

    public void showList(List<Team> teamList) throws Exception {

        if (teamList.size() > 0) {
            System.out.println("                                                Team List");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println(String.format("%-10s|%-15s|%-10s", "Team ID", "Team Name", "Status"));
            System.out.println("--------------------------------------------------------------------------------------");

            for (int i = 0; i < teamList.size(); i++) {
                System.out.println(String.format("%-10s|%-15s|%-10s",
                        teamList.get(i).getTeam_id(),
                        teamList.get(i).getTeam_name(),
                        teamList.get(i).getStatusString()));
            }

        } else {
            throw new Exception("Empty List!");
        }

    }

    public boolean isTeamIDExistInList(List<Team> teamList, int teamID) {
        for (Team team : teamList) {
            if (team.getTeam_id() == teamID) {
                return false;
            }
        }
        System.out.println("TEAM ID MUST BE SHOWED IN LIST ABOVE");
        return true;
    }

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Back.");
    }

    public void pageOfDisplay(List<Team> listDisplay) {
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
                showList(pagination(listDisplay, page));
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

    //==============================Cua Hoi nhe============================================
    public static Team searchTeamIDByID(int id) throws Exception {
        List<Team> lu = teamDAO.getList();
        for (Team team : lu) {
            if (team.getTeam_id() == id) {
                return team;
            }
        }
        return null;
    }

    public List<Team> showActiveTeamListWithClassID(List<Team> teamList, int classID) throws Exception {
        List<Team> activeTeam = new ArrayList<>();
        System.out.println("AVAILABLE TEAM");
        if (teamList.size() > 0) {
            for (int i = 0; i < teamList.size(); i++) {
                if (teamList.get(i).getStatusString().equalsIgnoreCase("Active")
                        && teamList.get(i).getClass_id()== classID) {
                    activeTeam.add(teamList.get(i));
                }

            }
            pageOfDisplay(activeTeam);

        } else {
            throw new Exception("Empty List!");
        }
        return activeTeam;
    }
}
