package controller;

import java.util.*;
import model.*;
import dao.*;
import view.Utility;

public class IterationController {
    //Main code owner of this class: 
    //Have function added in this class: Linh ( cho milestone )
    
    public static IterationDAO IterDao = new IterationDAO();

    public void showList(List<Iteration> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("============================= List ===============================");
            System.out.println(String.format("%-5s|%-15s|%-15s|%-15s|%-10s", "ID", "Iteration_name", "Iteration_id", "Duration", "Status"));
            System.out.println("==================================================================");

            for (int i = 0; i < sList.size(); i++) {
                System.out.println(sList.get(i));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

    public void pagination(List<Iteration> sList) throws Exception {
        if (sList.size() > 0) {
            //caculate page
            int page = sList.size() / 5;

            if (5 * page < sList.size()) {
                page = page + 1;
            }
            System.out.println("*Enter 'exit' to get out!");
            System.out.println("*Enter 'f' to find the page you want to move to!");
            System.out.println("*If you are in THE END of list, enter 'n' to get out!");
            System.out.println("There are " + page + " pages.");
            int index = 0;
//            System.out.println(String.format("%-5s|%-20s|%-15s|%-10s", "ID", "Code", "Name", "Status"));
            int i = 1;
            boolean exit = true;
            int numberObjLeft = page % 5;

            while (exit) {
                System.out.println("==================================================================");
                System.out.println("Page: " + i);
                System.out.println(sList.toString());
                if (page > 1) {
                    if (i < page) {
                        for (int j = 0; j < 5; j++) {
                            if (index < sList.size()) {
                                System.out.println(sList.get(index));
                                index++;
                            } else {
                                break;
                            }
                        }
                    }
                    if (i == page) {//i==page
                        for (int k = 0; k < numberObjLeft; k++) {
                            System.out.println(sList.get(index));
                            index++;
                        }

                    }

                } else {
                    for (int k = 0; k < sList.size(); k++) {
                        System.out.println(sList.get(k));
                    }
                }
                System.out.println("==================================================================");
                String checkContinue = Utility.getString("Go to PREVIOUS(p) page or NEXT(n) page: ", "Enter 'n', 'p', 'exit' or 'f' only!",Utility.REGEX_STRING);
                if (checkContinue.compareToIgnoreCase("p") == 0) {
                    if (i > 1) {
                        i = i - 1;
                    } else {
                        throw new Exception("Can NOT move to that page!");
                    }
                }
                if (checkContinue.compareToIgnoreCase("exit") == 0) {
                    exit = false;
                }
                if (checkContinue.matches("f")) {
                    int findpage = Utility.getInt("Enter page you want to move to: ", "Enter in the range of page number please!", 1, page);
                    i = findpage;
                }
                if (checkContinue.compareToIgnoreCase("n") == 0) {
                    if (i + 1 <= page) {
                        i++;
                    } else {
                        throw new Exception("Can NOT move to that page!");
                    }
                }
            }
        } else {
            throw new Exception("Empty list!");
        }
        return;
    }

    //check code subject ton tai chua? 
    public static boolean checkIterationExistByName(String name) throws Exception {
        List<Iteration> sbList = IterDao.getIterList();

        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getIteration_name().compareToIgnoreCase(name) == 0) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //check iteration subject ton tai chua? 
    public static boolean checkIterationExistByID(int id) throws Exception {
        List<Iteration> sbList = IterDao.getIterList();

        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getIteration_id()== id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }
    
    

    //them iteration moi
    public static void addNewIteration(Iteration sb) throws Exception {
        List<Iteration> sbList = IterDao.getIterList();
        if (!checkIterationExistByName(sb.getIteration_name())) {
            sbList.add(sb);
            IterDao.insertIterToList(sb);
            System.out.println("Add successfully!");
        } else {
            throw new Exception("Exist subject!");
        }
    }

    //find iteration name
    public static int searchIterationIndexByName(String name) throws Exception {
        List<Iteration> sbList = IterDao.getIterList();
        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getIteration_name().contains(name)) {
                    return i;
                }
            }
        }
        return -1;
    }

    //find iteration index
    public static int searchIterationIndexByID(int id) throws Exception {
        List<Iteration> sbList = IterDao.getIterList();
        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getIteration_id() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    //find Iteration
    public static Iteration searchIterationByCode(String name) throws Exception {
        List<Iteration> sbList = IterDao.getIterList();
        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getIteration_name().contains(name)) {
                    return sbList.get(i);
                }
            }
        }
        return null;
    }

    public static Iteration searchIterationByID(int id) throws Exception {
        List<Iteration> sbList = IterDao.getIterList();
        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getIteration_id() == id) {
                    return sbList.get(i);
                }
            }
        }
        return null;
    }
    
    public Iteration getIterationBySubjectID(int subjectID) throws Exception {
        List<Iteration> sbList = IterDao.getIterList();
        ClassUser clu = new ClassUser();
        for (Iteration user : sbList) {
            if (user.getSubject_id()== subjectID) {
                return user;
            }

        }
        return null;
    }

    //update iteration 
    public static void updateIteration(Iteration sb) throws Exception {
        List<Iteration> sbList = IterDao.getIterList();

        if (checkIterationExistByID(sb.getIteration_id())) {
            sbList.set(searchIterationIndexByID(sb.getIteration_id()), sb);
            IterDao.updateIterToList(sb, "iteration_id", sb.getIteration_name(), "string");
            IterDao.updateIterToListint(sb, "subject_id", sb.getIteration_id(), "int");
            IterDao.updateIterToListDouble(sb, "duration", sb.getDuration(), "double");

            ////ud.updateUserToList(ur, "verify", verifyString, "boolean");
            if (sb.getStatus() == 1) {
                IterDao.updateIterToList(sb, "status", "true", "Int");
            }
            if (sb.getStatus() == 2) {
                IterDao.updateIterToList(sb, "status", "false", "Int");
            }
            System.out.println("Update successfully!");;
        } else {
            throw new Exception("Can not find the Iteration!");
        }

    }

    public static void changeIterationStatus(int id) throws Exception {
//        List<Iteration> sbList = IterDao.getIterList();
        if (checkIterationExistByID(id)) {
            try {
                Iteration sb = searchIterationByID(id);
                String changeStatus;
                if (sb.getStatus() == 1) {
                    sb.setStatus(2);
                    changeStatus = "Active";
                    changeStatus="2";
                    ////ud.updateUserToList(ur, "verify", verifyString, "boolean");
                    IterDao.updateIterToList(sb, "status", changeStatus, "Int");
                } else {
                    sb.setStatus(1);
                    changeStatus = "InActive";
                    changeStatus="1";
                    IterDao.updateIterToList(sb, "status", changeStatus, "Int");
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new Exception("Iteration is NOT exist!");
        }
    }

    //seach name(1), status(2)
    public List<Iteration> searchByPattern(String pattern, int choice) throws Exception {
        List<Iteration> pList = IterDao.getIterList();
        List<Iteration> pl = new ArrayList<>();
        //chuyển tất cả về toLowerCase
        pattern = pattern.toUpperCase();
        switch (choice) {
            case 1: //name
                for (int i = 0; i < pList.size(); i++) {
                    if (pList.get(i).getIteration_name().toUpperCase().contains(pattern)) {
                        pl.add(pList.get(i));
                    }
                }
                break;
            case 2: //status
            {
              if (pattern.toUpperCase().compareTo("1") == 0) { //1. Active
                    for (int i = 0; i < pList.size(); i++) {
                        if (pList.get(i).getStatus() == 1) {
                            pl.add(pList.get(i));
                        }
                    }
                } else {
                     if (pattern.toUpperCase().compareTo("2") == 0) {//2. Deactive
                        for (int i = 0; i < pList.size(); i++) {
                            if (pList.get(i).getStatus() == 2) {
                                pl.add(pList.get(i));
                            }
                        }
                    } else {
                        if (pattern.toUpperCase().compareTo("3") == 0) {//3.exit
                            System.out.println("Exit.");
                            break;
                        } else {
                            throw new Exception("Choose in list menu only!");
                        }
                    }
                }

                break;
            }
            default:
                System.out.println("Exit search.");
                break;
        }
        return pl;
    }

    //sap xep iteration
    public List<Iteration> sortIteration(int choice) throws Exception {
        List<Iteration> sl = IterDao.getIterList();
        switch (choice) {
            case 1: //so sánh name
                Collections.sort(sl, new Comparator<Iteration>() {
                    @Override
                    public int compare(Iteration o1, Iteration o2) {
                        return o1.getIteration_name().compareTo(o2.getIteration_name());
                    }
                });
                break;

            case 2: //so sanh active
                Collections.sort(sl, new Comparator<Iteration>() {
                    @Override
                    public int compare(Iteration o1, Iteration o2) {
                        return o1.getStatusString().compareTo(o2.getStatusString());
                    }
                });
                break;

            //nếu nhập khác thì exit sort
            default:
                System.out.println("Exit sort.");
                break;
        }

        return sl;
    }
    
    //=========================Ham cua Phuoc cho Criteria===============================
    
    public static Iteration getIterationListByID(int id) throws Exception {
        List<Iteration> Iteration = IterDao.getIterList();
        //loop run from first to last element of listUserLists
        for (Iteration st : Iteration) {
            //if duplicate id => return SettingList has existed
            if (st.getIteration_id()== id) {
                return st;
            }
        }
        return null;
    }
    
    //========================ham cua Linh cho milestone====================================
   
    
    public static boolean checkIterationExistByID(List<Iteration> c,int id) throws Exception {
        if (c.size() > 0) {
            for (int i = 0; i < c.size(); i++) {
                if (c.get(i).getIteration_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }
 //======================================================================================================
    
}
