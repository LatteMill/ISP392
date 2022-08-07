/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.*;
;
import java.util.*;
import model.*;
import view.*;

/**
 *
 * @author admin
 */


public class ClassUserController {

    static ClassUserDAO classUserDAO = new ClassUserDAO();
    static UserListDAO userListDAO = new UserListDAO();
    static UserListController userListController = new UserListController();
    static TeamDAO teamDAO = new TeamDAO();
    static ClassDAO classDAO = new ClassDAO();
    static ClassController classController = new ClassController();
    static TeamController teamController = new TeamController();

    public void showList(List<ClassUser> sList) throws Exception {

        System.out.println("                                                    Class User List");
        pageOfDisplay(sList);
    }

    //check code class_user ton tai chua? 
    public static boolean isDuplicated(ClassUser clu) throws Exception {
        List<ClassUser> sbList = classUserDAO.getList();

        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getClassID() == clu.getClassID()
                        && sbList.get(i).getTeamID() == clu.getTeamID()
                        && sbList.get(i).getUserID() == clu.getUserID()) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    public static boolean checkClassUserExistOnOtherList(ClassUser clu) throws Exception {
        List<UserList> uList = userListDAO.getList();
        List<Team> tList = teamDAO.getList();
        List<Classroom> cList = classDAO.getList();
        int check = 0;
        if (cList.size() > 0 && tList.size() > 0 && cList.size() > 0) {
            for (Classroom clr : cList) {
                if (clr.getClass_id() == clu.getClassID()) {
                    check++;
                    ;// khong ton tai class_id

                }
            }

            for (Team t : tList) {
                if (t.getTeam_id() == clu.getTeamID()) {
                    check++;// khong ton tai team id

                }
            }
            for (UserList ul : uList) {
                if (ul.getId() == clu.getUserID()) {
                    check++;// khong ton tai user id

                }
            }
        }

        return check == 3;// ton tai de them vao
    }

    public ClassUser getClassUserByUserID(int user_id) throws Exception {
        List<ClassUser> sbList = classUserDAO.getList();
        ClassUser clu = new ClassUser();
        for (ClassUser user : sbList) {
            if (user.getUserID() == user_id) {
                return user;
            }

        }
        return null;
    }

    public ClassUser getClassUserByTeamID(int team_id) throws Exception {
        List<ClassUser> sbList = classUserDAO.getList();
        ClassUser clu = new ClassUser();
        for (ClassUser user : sbList) {
            if (user.getTeamID() == team_id) {
                return user;
            }

        }
        return null;
    }

    public static ClassUser getClassUserExistByID(int class_id, int team_id, int user_id) throws Exception {
        List<ClassUser> sbList = classUserDAO.getList();

        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getClassID() == class_id
                        && sbList.get(i).getTeamID() == team_id
                        && sbList.get(i).getUserID() == user_id) {
                    return sbList.get(i); //ton tai
                }
            }
        }
        return null; //khong ton tai
    }

    //check code class_user ton tai chua? 
    public static boolean checkClassUserExistByID(int class_id, int team_id, int user_id) throws Exception {
        List<ClassUser> sbList = classUserDAO.getList();

        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getClassID() == class_id
                        && sbList.get(i).getTeamID() == team_id
                        && sbList.get(i).getUserID() == user_id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //them class_user moi
    //find class_user index
    public static int searchClassUserIndexByID(int class_id, int team_id, int user_id) throws Exception {
        List<ClassUser> sbList = classUserDAO.getList();
        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getClassID() == class_id
                        && sbList.get(i).getTeamID() == team_id
                        && sbList.get(i).getUserID() == user_id) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static ClassUser searchClassUserByID(int user_id) throws Exception {
        List<ClassUser> sbList = classUserDAO.getList();
        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
//                if (sbList.get(i).getClassID() == class_id
//                        && sbList.get(i).getTeamID() == team_id
//                        && sbList.get(i).getUserID() == user_id) {
                if (sbList.get(i).getUserID() == user_id) {
                    return sbList.get(i);
                }
            }
        }
        return null;
    }

    public static ClassUser searchByUserID(int user_id) throws Exception {
        List<ClassUser> sbList = classUserDAO.getList();
        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getUserID() == user_id) {
                    return sbList.get(i);
                }
            }
        }
        return null;

    }

    //update class_user 
    public static void updateClassUser(ClassUser sb) throws Exception {
        List<ClassUser> sbList = classUserDAO.getList();

        //    List<Classroom> c = cd.getList();
        if (checkClassUserExistByID(sb.getClassID(), sb.getTeamID(), sb.getUserID())) {
//            sbList.set(searchClassUserIndexByID(sb.getClassID(), sb.getTeamID(), sb.getUserID()), sb);
//            classUserDAO.updateClassUserToList(sb, "class_id", sb.getClassID() + "", "int");
//            classUserDAO.updateClassUserToList(sb, "team_id", sb.getTeamID() + "", "int");
//            classUserDAO.updateClassUserToList(sb, "user_id", sb.getUserID() + "", "int");
//            classUserDAO.updateClassUserToList(sb, "team_leader", sb.isTeamLeader() + "", "String");
//            classUserDAO.updateClassUserToList(sb, "dropout_date", Utility.convertDateToStringtoInsert(sb.getDropout_date()) + "", "string");
//            classUserDAO.updateClassUserToList(sb, "user_notes", sb.getUser_note() + "", "String");
//            classUserDAO.updateClassUserToList(sb, "final_pres_eval", sb.getFinal_pres_eval() + "", "int");
//            classUserDAO.updateClassUserToList(sb, "final_topic_eval", sb.getFinal_topic_eval() + "", "String");
//            classUserDAO.updateClassUserToList(sb, "status", sb.getStatus() + "", "int");
            classUserDAO.updateClassUserToList(sb);
            System.out.println("Update successfully!");
        } else {
            throw new Exception("Can not update!");
        }

    }

    public static void changeClassUserStatus(int user_id, int option) throws Exception {

//        if (checkClassUserExistByID(user_id, user_id, user_id)) {
        try {
            ClassUser sb = searchByUserID(user_id);
            classUserDAO.updateClassUserToList(sb, "status", option + "", "int");

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
//        } else {
//            throw new Exception("ClassUser is NOT exist!");
    }

    //seach code(1), name(2), status(3)
    public List<ClassUser> searchByFilter(int choice) throws Exception {
        List<ClassUser> pList = classUserDAO.getList();
        List<ClassUser> listSearch = new ArrayList<>();
        //chuyển tất cả về toLowerCase
//        pattern = pattern.toUpperCase();
        switch (choice) {
            case 1: //class_id
                try {
                    int numberPattern = Utility.getInt("Enter class_id to search: ", "It must be number!!!", 1, Integer.MAX_VALUE);
                    for (int i = 0; i < pList.size(); i++) {
                        if (pList.get(i).getClassID() == numberPattern) {
                            listSearch.add(pList.get(i));
                        }
                    }
                    pageOfDisplay(listSearch);
                } catch (Exception e) {
                    throw new Exception("Wrong input!");
                }
                break;
            case 2: //team_id
                try {
                    int numberPattern = Utility.getInt("Enter team_id to search: ", "It must be number!!!", 1, Integer.MAX_VALUE);
                    for (int i = 0; i < pList.size(); i++) {
                        if (pList.get(i).getTeamID() == numberPattern) {
                            listSearch.add(pList.get(i));
                        }
                    }
                    pageOfDisplay(listSearch);
                } catch (Exception e) {
                    throw new Exception("Wrong input!");
                }
                break;
            case 3: //user_id
                try {
                    int numberPattern = Utility.getInt("Enter user_id to search: ", "It must be number!!!", 1, Integer.MAX_VALUE);
                    for (int i = 0; i < pList.size(); i++) {
                        if (pList.get(i).getUserID() == numberPattern) {
                            listSearch.add(pList.get(i));
                        }
                    }
                } catch (Exception e) {
                    throw new Exception("Wrong input!");
                }
                pageOfDisplay(listSearch);
                break;
            case 4: //fullName
                try {
                    String pattern = Utility.getString("Enter name to search: ", "It must be string", Utility.REGEX_STRING);
                    for (int i = 0; i < pList.size(); i++) {
                        if (pList.get(i).getFullName().contains(pattern)) {
                            listSearch.add(pList.get(i));
                        }
                    }

                } catch (Exception e) {
                    throw new Exception("Wrong input!");
                }
                pageOfDisplay(listSearch);
                break;
            case 5: //status
                System.out.println("1. Active   2. Deactive     3. Blocked");
                String pattern = Utility.getString("Enter status to search: ", "It must be string", Utility.REGEX_STRING);

                if (pattern.toUpperCase().compareTo("1") == 0) { //1. Active
                    for (int i = 0; i < pList.size(); i++) {
                        if (pList.get(i).getStatus() == 1) {
                            listSearch.add(pList.get(i));
                        }
                    }
                } else {
                    if (pattern.toUpperCase().compareTo("2") == 0) {//2. Deactive
                        for (int i = 0; i < pList.size(); i++) {
                            if (pList.get(i).getStatus() == 2) {
                                listSearch.add(pList.get(i));
                            }
                        }
                    } else {
                        if (pattern.toUpperCase().compareTo("3") == 0) {//3.Block
                            {
                                for (int i = 0; i < pList.size(); i++) {
                                    if (pList.get(i).getStatus() == 3) {
                                        listSearch.add(pList.get(i));
                                    }
                                }
                            }
                        } else {
                            throw new Exception("Choose in list menu only!");
                        }
                    }
                    pageOfDisplay(listSearch);

                    break;
                }

            default:
                System.out.println("Exit search.");
                break;
        }
        return listSearch;
    }
    //sap xep class_user

    public List<ClassUser> sortClassUser(int choice) throws Exception {
        List<ClassUser> sl = classUserDAO.getList();
        switch (choice) {
            case 1: // sort class_id
                Collections.sort(sl, new Comparator<ClassUser>() {
                    @Override
                    public int compare(ClassUser o1, ClassUser o2) {
                        return Integer.toString(o1.getClassID()).compareTo(Integer.toString(o2.getClassID()));
                    }
                });
                pageOfDisplay(sl);
                break;

            case 2: //sort team_id
                Collections.sort(sl, new Comparator<ClassUser>() {
                    @Override
                    public int compare(ClassUser o1, ClassUser o2) {
                        return Integer.toString(o1.getTeamID()).compareTo(Integer.toString(o2.getTeamID()));
                    }
                });
                pageOfDisplay(sl);
                break;
            case 3: // sort user_id
                Collections.sort(sl, new Comparator<ClassUser>() {
                    @Override
                    public int compare(ClassUser o1, ClassUser o2) {
                        return Integer.toString(o1.getUserID()).compareTo(Integer.toString(o2.getUserID()));
                    }
                });
                pageOfDisplay(sl);
                break;

            case 4: //sort fullName
                Collections.sort(sl, new Comparator<ClassUser>() {
                    @Override
                    public int compare(ClassUser o1, ClassUser o2) {
                        return o1.getFullName().compareTo(o2.getFullName());
                    }
                });
                pageOfDisplay(sl);
                break;
            case 5: //sort status
                Collections.sort(sl, new Comparator<ClassUser>() {
                    @Override
                    public int compare(ClassUser o1, ClassUser o2) {
                        return o1.getStatusString().compareTo(o2.getStatusString());
                    }
                });
                pageOfDisplay(sl);
                break;

            case 6: //sort dropout_date
                Collections.sort(sl, new Comparator<ClassUser>() {
                    @Override
                    public int compare(ClassUser o1, ClassUser o2) {
                        return o1.getDropout_date().compareTo(o2.getDropout_date());
                    }
                });
                pageOfDisplay(sl);
                break;

            //nếu nhập khác thì exit sort
            case 7:
                System.out.println("Exit sort.");

                break;
        }

        return sl;
    }

    public static List<ClassUser> getClassUserListWithCondition(int classID, int teamID, int userID) throws Exception {
        List<ClassUser> listSearch = classUserDAO.getList();
        List<ClassUser> listFound = new ArrayList<>();
        if (teamID == 0 && userID == 0) {
            for (ClassUser user : listSearch) {
                if (user.getClassID() == classID) {
                    listFound.add(user);
                }
            }
        } else if (userID == 0) {
            for (ClassUser user : listSearch) {
                if (user.getClassID() == classID
                        && user.getTeamID() == teamID) {
                    listFound.add(user);
                }
            }
        } else {
            for (ClassUser user : listSearch) {
                if (user.getClassID() == classID
                        && user.getTeamID() == teamID
                        && user.getUserID() == userID) {
                    listFound.add(user);
                }
            }
        }
        return listFound;
    }

    public static void addClassUserDetails(ClassUser user) throws Exception {
        List<ClassUser> ssList = classUserDAO.getList();

        //kiem tra xem class user da ton tai hay chua
        if (checkClassUserExistOnOtherList(user)) {
            if (!isDuplicated(user)) {

                //kiem tra type ID co thoa man khong
//                if (!checkClassUserExistByID(ss.getClassID(), ss.getTeamID(), ss.getUserID())) {
                ssList.add(user);
                classUserDAO.insertClassUserToList(user);

                System.out.println("Add successfully!");
//                } else {
//                    throw new Exception("Add false!!!\nExist class_user!");
//                }
            } else {
                System.out.println("Duplicated");
            }
        } else {
            System.out.println("Data doesnot exist");
        }
    }

    public static List<ClassUser> pagination(List<ClassUser> list, int page) throws Exception {
        //set up list 5 để trả về
        List<ClassUser> classUserList = new ArrayList<>();
        //setup biến bắt đầu
        int start = 5 * (page - 1);

        //số lượng page có
        int maxpage = list.size() / 5;
        if (list.size() % 5 != 0) {
            maxpage++;
        }

        //thỏa mãn điều kiện page nằm trong danh sách page
        if (0 < page && page <= maxpage) {
            //chạy vòng lặp ho 5 lần
            for (int i = start; i < start + 5; i++) {
//                System.out.println(list.get(i));
                if (i == list.size()) {
                    break;
                } else {
                    if (list.get(i) != null) {
                        classUserList.add(list.get(i));
                    }
                }
            }

            return classUserList;

        } else {
            throw new Exception("Can't not go to that page");
        }

    }

    public static void displayList(List<ClassUser> list) throws Exception {
        if (list.size() > 0) {
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(String.format("%-7s|%-10s|%-15s|%-7s|%-5s|%-25s|%-25s|%-10s|%-15s|%-7s|%-15s|%-16s|%-5s", "ClassID", "ClassCode", "TeamID-Name", "UserID", "Roles", "FullName", "Email", "TeamLeader", "Dropout_date", "Notes", "Final_Pres_Eval", "Final_Topic_Eval", "Status"));
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
        }
    }

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Back.");
    }

    public static void pageOfDisplay(List<ClassUser> listDisplay) {
        int showChoice = 0;
        int page = 1;
        if (listDisplay.size() == 0) {
            System.out.println("List Empty!!!");
        } else {
            while (showChoice != 4) {
                int maxpage = listDisplay.size() / 5;
                if (listDisplay.size() % 5 != 0) {
                    maxpage++;
                }
                System.out.println("There are: " + maxpage + " pages.");
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
    }

    //=======================Hàm của Linh==========
//    public int searchTeamIDByUserID(int id) throws Exception{
//        List<ClassUser> ssList = classUserDAO.getList();
//        for (int i=0; i<ssList.size();i++){
//            if(ssList.get(i).getUserID()==id)
//                return ssList.get(i).getTeamID();
//        }
//        return -1;
//    }
//    
//    public int searchClassIDByUserID(int id) throws Exception{
//        List<ClassUser> ssList = classUserDAO.getList();
//        for (int i=0; i<ssList.size();i++){
//            if(ssList.get(i).getUserID()==id)
//                return ssList.get(i).getClassID();
//        }
//        return -1;
//    }
    public static boolean checkExistUserByID(List<ClassUser> slist, int id) throws Exception {
        for (int i = 0; i < slist.size(); i++) {
            if (slist.get(i).getUserID() == id) {
                return true; //exist
            }
        }
        return false; //not exist
    }
    //=======================================================

    public boolean isUserInTeamIDExistInList(List<ClassUser> milestoneActive, int teamID) {
        for (ClassUser team : milestoneActive) {

            if (team.getTeamID() == teamID) {
                return false;
            }
        }
        System.out.println("MILESTONE ID MUST BE SHOWED IN LIST ABOVE");
        return true;
    }

    public ClassUser getClassUserByClassID(int classID) throws Exception {
        List<ClassUser> sbList = classUserDAO.getList();
        ClassUser clu = new ClassUser();
        for (ClassUser user : sbList) {
            if (user.getClassID() == classID) {
                return user;
            }

        }
        return null;
    }
//User evaluation

    public static List<ClassUser> searchClassUserByUserID(int user_id) throws Exception {
        List<ClassUser> sbList = classUserDAO.getList();
        List<ClassUser> listSearch = new ArrayList<>();
        for (ClassUser u : sbList) {
            if (u.getUserID() == user_id) {
                listSearch.add(u);
            }

        }
        if (listSearch.isEmpty()) {
            return null;
        } else {
            return listSearch;
        }
    }

    public static boolean checkClassUserExistByClassID(int class_id, List<ClassUser> list) throws Exception {
        for (ClassUser u : list) {
            if (u.getClassID() == class_id) {
                return true;
            }
        }
        System.out.println("CLASS ID MUST BE SHOWED IN LIST ABOVE");
        return false;
    }
}
