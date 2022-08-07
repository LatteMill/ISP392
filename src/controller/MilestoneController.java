package controller;

import dao.*;
import java.util.*;
import model.*;
import view.Utility;

public class MilestoneController {

    //Main code owner of this class: Linh
    //Have function add in this class: Anh for Tracking
    
    static MilestoneDAO milestoneDAO = new MilestoneDAO();

    static IterationController iterationController = new IterationController();
    static IterationDAO iterationDAO = new IterationDAO();

    static ClassDAO classDAO = new ClassDAO();
    static ClassController classController = new ClassController();

    

    //check code subject ton tai chua? 
    public static boolean checkMilestoneExist(Milestone m) throws Exception {
        List<Milestone> milestoneList = milestoneDAO.getList();

        if (milestoneList.size() > 0) {
            for (int i = 0; i < milestoneList.size(); i++) {
                if (milestoneList.get(i).getClass_id() == m.getClass_id()
                        && milestoneList.get(i).getIteration_id() == m.getIteration_id()
                        && milestoneList.get(i).getFrom_date().compareTo(m.getFrom_date()) == 0
                        && milestoneList.get(i).getTo_date().compareTo(m.getTo_date()) == 0) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //check code subject ton tai chua? 
    public static boolean checkMileStoneExistByID(int id) throws Exception {
        List<Milestone> milestoneList = milestoneDAO.getList();

        if (milestoneList.size() > 0) {
            for (int i = 0; i < milestoneList.size(); i++) {
                if (milestoneList.get(i).getMileStone_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //them subject moi
    public static void addNewMilestone(Milestone m) throws Exception {
        List<Milestone> milestoneList = milestoneDAO.getList();
        //check mile stoen da ton tai:
        if (!checkMilestoneExist(m)) {
            //check class ID da ton tai:
            if (classController.checkClassroomExistByID(classDAO.getListWithOneCondition(" status = true "), m.getClass_id())) {
                //check iteration da ton tai:
                if (iterationController.checkIterationExistByID(iterationDAO.getListWithOneCondition(" status = true "), m.getIteration_id())) {
                    //check ngay thang hop le:
                    if (m.getFrom_date().compareTo(m.getTo_date()) < 0) {
                        milestoneList.add(m);
                        milestoneDAO.insertMilestoneToList(m);
                        System.out.println("Add successfully!");
                    } else {
                        throw new Exception("From_date must be before To_date!");
                    }
                } else {
                    throw new Exception("Enter Iteration ID in list showed only!");
                }
            } else {
                throw new Exception("Enter Class ID in list showed only!");
            }
        } else {
            throw new Exception("Existed Milestone!");
        }
    }

    /*
    //find Milestone index
    public static int searchSubjectIndexByCode(String code) throws Exception {
        List<milestone> milestoneList = milestoneDAO.getList();
        if (milestoneList.size() > 0) {
            for (int i = 0; i < milestoneList.size(); i++) {
                if (milestoneList.get(i).getTopic_code().compareToIgnoreCase(code) == 0) {
                    return i;
                }
            }
        }
        return -1;
    }
     */
    //find subject index
    public static int searchMilestoneIndexByID(int id) throws Exception {
        List<Milestone> milestoneList = milestoneDAO.getList();
        if (milestoneList.size() > 0) {
            for (int i = 0; i < milestoneList.size(); i++) {
                if (milestoneList.get(i).getMileStone_id() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    /*
    //find Subject
    public static Team searchSubjectByCode(String code) throws Exception {
        List<Team> milestoneList = milestoneDAO.getList();
        if (milestoneList.size() > 0) {
            for (int i = 0; i < milestoneList.size(); i++) {
                if (milestoneList.get(i).getSubjectCode().compareToIgnoreCase(code) == 0) {
                    return milestoneList.get(i);
                }
            }
        }
        return null;
    }
     */
    public static Milestone searchMilestoneByID(int id) throws Exception {
        List<Milestone> milestoneList = milestoneDAO.getList();
        if (milestoneList.size() > 0) {
            for (int i = 0; i < milestoneList.size(); i++) {
                if (milestoneList.get(i).getMileStone_id() == id) {
                    return milestoneList.get(i);
                }
            }
        }
        return null;
    }

    //update subject 
    public static void updateMilestone(Milestone milestone) throws Exception {
        List<Milestone> milestoneList = milestoneDAO.getList();

        //    List<Classroom> c = classDAO.getList();
        if (checkMileStoneExistByID(milestone.getMileStone_id())) {
            if (classController.checkClassroomExistByID(classDAO.getListWithOneCondition(" status = true "), milestone.getClass_id())) {
                //check iteration da ton tai:
                if (iterationController.checkIterationExistByID(iterationDAO.getListWithOneCondition(" status = true "), milestone.getIteration_id())) {
                    //check ngay thang hop le:
                    if (milestone.getFrom_date().compareTo(milestone.getTo_date()) < 0) {
                        milestoneList.set(searchMilestoneIndexByID(milestone.getMileStone_id()), milestone);

                        milestoneDAO.updateMilestoneToList(milestone);
                    } else {
                        throw new Exception("From_date must be before To_date!");
                    }
                } else {
                    throw new Exception("Enter Iteration ID in list showed only!");
                }
            } else {
                throw new Exception("Enter Class ID in list showed only!");
            }
            System.out.println("Update successfully!");
        } else {
            throw new Exception("Can not find the team!");
        }

    }

    public static void changeMilestoneStatus(int id, int option) throws Exception {
        List<Milestone> milestoneList = milestoneDAO.getList();
        if (checkMileStoneExistByID(id)) {
            try {
                Milestone milestone = searchMilestoneByID(id);
                milestone.setStatus(option);
                milestoneDAO.updateMilestoneToList(milestone);

            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new Exception("Milestone is NOT exist!");
        }
    }

    //seach code(1), name(2), status(3)
    public List<Milestone> searchByPattern(String pattern) throws Exception {
        List<Milestone> fullList = milestoneDAO.getList();
        if (pattern.contains("/")) {
            String[] tachDMY = pattern.split("/");

            //nếu tách ra được đúng theo định dạng DMY
            if (tachDMY.length > 1) {
                // trường hợp ngày có 1 chữ số -> thêm số 0
                if (tachDMY[0].matches("\\d")) {
                    tachDMY[0] = "0" + tachDMY[0];
                }
                //trường hợp tháng có 1 chữ số -> thêm số 0
                if (tachDMY[1].matches("\\d")) {
                    tachDMY[1] = "0" + tachDMY[1];
                }
                pattern = tachDMY[0] + "/" + tachDMY[1];
                if (tachDMY.length == 3) {
                    pattern = pattern + "/" + tachDMY[2];
                }
            }
        }

        List<Milestone> searchList = milestoneDAO.getSearchList(pattern);
        return searchList;
    }
    //sap xep subject

    public List<Milestone> sortMilestone(int choice) throws Exception {
        List<Milestone> fullList = milestoneDAO.getList();
        switch (choice) {
            case 1: // so sánh iterarion_id
                Collections.sort(fullList, new Comparator<Milestone>() {
                    @Override
                    public int compare(Milestone milestone1, Milestone milestone2) {
                        return Integer.toString(milestone1.getIteration_id()).compareTo(Integer.toString(milestone2.getIteration_id()));
                    }
                });
                break;

            case 2: //so sánh class_id
                Collections.sort(fullList, new Comparator<Milestone>() {
                    @Override
                    public int compare(Milestone milestone1, Milestone milestone2) {
                        return Integer.toString(milestone1.getClass_id()).compareTo(Integer.toString(milestone2.getClass_id()));
                    }
                });
                break;

            case 3: //so sanh active
                Collections.sort(fullList, new Comparator<Milestone>() {
                    @Override
                    public int compare(Milestone milestone1, Milestone milestone2) {
                        return milestone1.getStatusString().compareTo(milestone2.getStatusString());
                    }
                });
                break;
            case 4: //so sanh from_date
                Collections.sort(fullList, new Comparator<Milestone>() {
                    @Override
                    public int compare(Milestone milestone1, Milestone milestone2) {
                        return milestone1.getFrom_date().compareTo(milestone2.getFrom_date());
                    }
                });
                break;
            case 5: //so sanh to_date
                Collections.sort(fullList, new Comparator<Milestone>() {
                    @Override
                    public int compare(Milestone milestone1, Milestone milestone2) {
                        return milestone1.getTo_date().compareTo(milestone2.getTo_date());
                    }
                });
                break;

            //nếu nhập khác thì exit sort
            default:
                System.out.println("Exit sort.");
                break;
        }

        return fullList;
    }
    
    //================================Linh ===============
    public static boolean checkMileStoneExistByID(List<Milestone> milestoneList, int id) throws Exception {
        if (milestoneList.size() > 0) {
            for (int i = 0; i < milestoneList.size(); i++) {
                if (milestoneList.get(i).getMileStone_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }
    
    public static List<Milestone> getMilestoneWithMatchClassID( int class_id) throws Exception{
        List<Milestone> ml = milestoneDAO.getList();
        List<Milestone> m = new ArrayList<>();
        for(int i=0; i<ml.size();i++){
            if(ml.get(i).getClass_id()==class_id)
                m.add(ml.get(i));
        }
        return m;
    }
  //====================HAM CUA ANH CHO TRACKING===========================

    public List<Milestone> showActiveMileStonList(List<Milestone> milestoneList) throws Exception {
        List<Milestone> milestoneActive = new ArrayList<>();
        System.out.println("\nAVAILABLE MILESTONE");
        if (milestoneList.size() > 0) {
           
            for (int i = 0; i < milestoneList.size(); i++) {
                if (milestoneList.get(i).getStatusString().equalsIgnoreCase("open")) {
                    milestoneActive.add(milestoneList.get(i));
                }

            }
            pageOfDisplay(milestoneActive);
        } else {
            throw new Exception("Empty List!");
        }
        return milestoneActive;
    }

    public void showList(List<Milestone> milestoneList) throws Exception {

        if (milestoneList.size() > 0) {
            System.out.println("====================================== Milestone List =======================================");
            System.out.println(String.format("%-15s|%-15s|%-15s|%-15s|%-15s|%-10s", "Milestone ID", "Iteration ID", "Class ID", "From Date", "To Date", "Status"));
            System.out.println("---------------------------------------------------------------------------------------------");

            for (int i = 0; i < milestoneList.size(); i++) {
                System.out.println(milestoneList.get(i));
            }
//            System.out.println(milestoneList.get(0).getFrom_date());
//            System.out.println(Utility.convertDateToString(milestoneList.get(0).getFrom_date()));
//            System.out.println(Utility.convertDateToStringtoInsert(milestoneList.get(0).getFrom_date()));

        } else {
            throw new Exception("Empty List!");
        }
    }

    public boolean isMilestoneIDExistInList(List<Milestone> milestoneActive, int milestoneID) {
        for (Milestone milestone : milestoneActive) {

            if (milestone.getMileStone_id() == milestoneID) {
                return false;
            }
        }
        System.out.println("MILESTONE ID MUST BE SHOWED IN LIST ABOVE");
        return true;
    }
        public boolean isMilestoneClassIDExistInList(List<Milestone> milestoneActive, int classID) {
        for (Milestone milestone : milestoneActive) {

            if (milestone.getMileStone_id() == classID) {
                return false;
            }
        }
        System.out.println("MILESTONE ID MUST BE SHOWED IN LIST ABOVE");
        return true;
    }
    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Back.");
    }

    public void pageOfDisplay(List<Milestone> listDisplay) {
        int showChoice = 0;
        int page = 1;
        while (showChoice != 4) {
            int maxpage = listDisplay.size() / 5;
            if (listDisplay.size() % 5 != 0) {
                maxpage++;
            }
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

    public List<Milestone> pagination(List<Milestone> list, int page) throws Exception {
        //set up list 5 để trả về
        List<Milestone> trackingList = new ArrayList<>();
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
                if (i == list.size() ) {
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
    //==========================================================================
}
