package controller;

import java.util.*;
import model.*;
import dao.*;
import view.Utility;

public class ClassController {
    //Main code owner of this class: Phuoc
    //Have function add in this class: Linh ( cho Milestone )

    static ClassDAO classDao = new ClassDAO();

    //subject
    static SubjectController sụbjectController = new SubjectController();
    static SubjectDAO subjectDao = new SubjectDAO();

    //trainer
    static UserListController userListControler = new UserListController();
    static UserDAO userDao = new UserDAO();

    //class_user
    static ClassUserDAO classUserDAO = new ClassUserDAO();
    static ClassUserController classUserController = new ClassUserController();

    //check class ton tai chua? 
    public static boolean checkClassExist(Classroom classz) throws Exception {
        List<Classroom> classList = classDao.getList();

        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getClass_id() == classz.getClass_id()
                        && classList.get(i).getTrainer_id() == classz.getTrainer_id()
                        && classList.get(i).getSubject_id() == classz.getSubject_id()
                        && classList.get(i).getClass_year() == classz.getClass_year()
                        && classList.get(i).getClass_term() == classz.getClass_term()) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    public static Classroom searchByTrainerID(int user_id) throws Exception {
        List<Classroom> classList = classDao.getList();
        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getTrainer_id() == user_id) {
                    return classList.get(i);
                }
            }
        }
        return null;

    }

    //check code class ton tai chua? 
    public static boolean checkClassroomExistByID(int id) throws Exception {
        List<Classroom> classList = classDao.getList();

        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getClass_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    public static boolean checkExistUserByTrainerID(List<Classroom> slist, int id) throws Exception {
        for (int i = 0; i < slist.size(); i++) {
            if (slist.get(i).getTrainer_id() == id) {
                return true; //exist
            }
        }
        return false; //not exist
    }

//    public static boolean checkExistClassByTrainerID(int id) throws Exception {
//        List<Classroom> slist = classDao.getList();
//        for (int i = 0; i < slist.size(); i++) {
//            if (slist.get(i).getTrainer_id() == id) {
//                return true; //exist
//            }
//        }
//        return false; //not exist
//    }
    //them class moi
    public static void addNewClass(Classroom classs) throws Exception {
        //check class da ton tai chua
        if (!checkClassExist(classs)) {
            //check subject_id da ton tai
            if (SubjectController.checkExistSubjectByID(subjectDao.getList(), classs.getSubject_id())) {
                classDao.insertClassroomToList(classs);
                System.out.println("Add sucessfully!");
            } else {
                throw new Exception("Enter Subject ID in list showed only.");
            }
        } else {
            throw new Exception("Exist Class!");
        }
    }

//find class index
    public static int searchClassroomIndexByID(int id) throws Exception {
        List<Classroom> classList = classDao.getList();
        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getClass_id() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    //find Class
    public static Classroom searchClassroomByCode(String code) throws Exception {
        List<Classroom> classList = classDao.getList();
        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getClass_code().compareToIgnoreCase(code) == 0) {
                    return classList.get(i);
                }
            }
        }
        return null;
    }

    public static Classroom searchClassroomByID(int id) throws Exception {
        List<Classroom> classList = classDao.getList();
        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getClass_id() == id) {
                    return classList.get(i);
                }
            }
        }
        return null;
    }

    public static Classroom searchClassroomByTrainerID(int id) throws Exception {
        List<Classroom> classList = classDao.getList();
        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getTrainer_id() == id) {
                    return classList.get(i);
                }
            }
        }
        return null;
    }

    //update subject 
    public static void updateClassroom(Classroom classs) throws Exception {
        //check class da ton tai chua
        if (!checkClassExist(classs)) {
            //check subject_id da ton tai
            if (SubjectController.checkExistSubjectByID(subjectDao.getList(), classs.getSubject_id())) {
                classDao.updateClassToList(classs);
            } else {
                throw new Exception("Enter Subject ID in list showed only.");
            }
        } else {
            throw new Exception("Exist Class!");
        }

    }

    public static void changeClassroomStatus(int id, int option) throws Exception {
        if (checkClassroomExistByID(id)) {
            try {
                Classroom sb = searchClassroomByID(id);
                if (option == 1) {
                    sb.setStatus(option);
                } else {
                    sb.setStatus(option);
                }
                classDao.updateClassToList(sb);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new Exception("Can not change this class!");
        }
    }

//    public static void changeBlock5Status(int id, int option) throws Exception {
//        List<Classroom> classList = classDao.getList();
//        if (checkClassroomExistByID(id)) {
//            try {
//                Classroom sb = searchClassroomByID(id);
//                String changeStatus;
//                classDao.updateClassroomToList(sb, "block5_class", "" + option, "int");
//
//            } catch (Exception e) {
//                throw new Exception(e.getMessage());
//            }
//        } else {
//            throw new Exception("Classroom is NOT exist!");
//        }
//    }
    public List<Classroom> searchByPatternID(int pattern) throws Exception {
        List<Classroom> pList = classDao.getList();
        List<Classroom> pl = new ArrayList<>();
        //chuyển tất cả về toLowerCase
        try {
            for (int i = 0; i < pList.size(); i++) {
                if (pList.get(i).getClass_id() == pattern) {
                    pl.add(pList.get(i));
                }
            }
        } catch (Exception e) {
            throw new Exception("Wrong.");
        }

        return pl;
    }

    //sap xep class
    public List<Classroom> sortClassroom(int role, int id, int choice) throws Exception {
//        List<Classroom> sl = classDao.getList();
        switch (choice) {
            case 1: //so sanh class code
                return classDao.getListForSort(role, id, "class_code");
            case 2://so sanh class year
                return classDao.getListForSort(role, id, "class_year");
            case 3://so sanh class term
                return classDao.getListForSort(role, id, "class_term");
            //nếu nhập khác thì exit sort
            default:
                System.out.println("Exit sort.");
                break;
        }
        return null;
//        return sl;
    }

    //xử lí functions ids
    public static int[] getFunctions_ids(String funtcions_id) throws Exception {
        Scanner sc = new Scanner(System.in);

        //tach la dau phay
        String[] fids = funtcions_id.replaceAll("\\s+", "").split(",");

        //tạo array int báo size       
        int size = fids.length;
        int[] a = new int[size];
        int check = 0;
        try {
            for (int i = 0; i < size; i++) {
                //ktra định dạng phần tử nhận vào
                if (fids[i].matches("\\d+")) {
                    a[i] = Integer.parseInt(fids[i]);
                } else {
                    throw new Exception("Wrong input");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    //========================ham cua Linh cho Milestone====================================
    public static boolean checkClassroomExistByID(List<Classroom> c, int id) throws Exception {
        if (c.size() > 0) {
            for (int i = 0; i < c.size(); i++) {
                if (c.get(i).getClass_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    public static boolean checkArrayFids(String funtcions_id) throws Exception {
        Scanner sc = new Scanner(System.in);

        //tach la dau phay
        String[] fids = funtcions_id.replaceAll("\\s+", "").split(",");

        //tạo array int báo size       
        int size = fids.length;
        int[] a = new int[size];
        for (int i = 0; i < size; i++) {
            //ktra định dạng phần tử nhận vào
            if (fids[i].matches("\\d+")) {
                a[i] = Integer.parseInt(fids[i]);
            } else {
                return false; //sai
            }
        }
        return true; //dúng
    }

    public List<Classroom> pagination(List<Classroom> list, int page) throws Exception {
        //set up list 5 để trả về
        List<Classroom> issueList = new ArrayList<>();
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
                if (i == list.size() - 1) {
                    break;
                } else {
                    if (list.get(i) != null) {
                        issueList.add(list.get(i));
                    }
                }
            }

            return issueList;

        } else {
            throw new Exception("Can't not go to that page");
        }

    }
    //====================HAM CUA ANH CHO TRACKING===========================

    public List<Classroom> showActiveCLassList(List<Classroom> sList) throws Exception {
        List<Classroom> classActive = new ArrayList<>();
        System.out.println("\nAVAILABLE CLASS");
        if (sList.size() > 0) {

            for (int i = 0; i < sList.size(); i++) {
                if (sList.get(i).getStatus().equalsIgnoreCase("Active")) {
                    classActive.add(sList.get(i));
                }

            }
            pageOfDisplay(classActive);
        } else {
            throw new Exception("Empty List!");
        }
        return classActive;
    }

    public void showList(List<Classroom> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("                                                Class List");
            System.out.println("==============================================================================================================");
            System.out.println(String.format("%-5s|%-6s|%-30s|%-20s|%-8s|%-5s|%-14s|%-8s",
                    "ID", "Code", "Trainer", "Subject", "Year", "Term", "Block5_status", "Status"));
            System.out.println("==============================================================================================================");

            for (int i = 0; i < sList.size(); i++) {
                System.out.println(sList.get(i));
            }
//            System.out.println(sList.get(0).getFrom_date());
//            System.out.println(Utility.convertDateToString(sList.get(0).getFrom_date()));
//            System.out.println(Utility.convertDateToStringtoInsert(sList.get(0).getFrom_date()));

        } else {
            throw new Exception("Empty List!");
        }
    }

    public boolean isClassIDExistInList(List<Classroom> classActive, int classID) {
        for (Classroom clr : classActive) {

            if (clr.getClass_id() == classID) {
                return false;
            }
        }
        System.out.println("CLASS ID MUST BE SHOWED IN LIST ABOVE");
        return true;
    }

    public boolean isClassroomClassIDExistInList(List<Classroom> classActive, int classID) {
        for (Classroom clr : classActive) {

            if (clr.getClass_id() == classID) {
                return false;
            }
        }
        System.out.println("CLASS ID MUST BE SHOWED IN LIST ABOVE");
        return true;
    }

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Back.");
    }

    public void pageOfDisplay(List<Classroom> listDisplay) {
        int showChoice = 0;
        int page = 1;
        while (showChoice != 4) {
            int maxpage = listDisplay.size() / 5;
            if (listDisplay.size() % 5 != 0) {
                maxpage++;
            }
            System.out.println("There are: " + maxpage + " pages.");
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

    public List<Classroom> searchClassByTrainerID(int trainer_id) throws Exception {
        List<Classroom> pList = classDao.getList();
        List<Classroom> listSearch = new ArrayList<>();
        for (Classroom c : pList) {
            if (c.getTrainer_id() == trainer_id) {
                listSearch.add(c);
            }
        }
        if (listSearch.size() == 0) {
            System.out.println("You do not have class!!!");
            return null;
        } else {
            pageOfDisplay(listSearch);
        }
        return listSearch;
    }

    public List<Classroom> showActiveCLassWithTrainerList(List<Classroom> sList, int trainer_id) throws Exception {
        List<Classroom> classActive = new ArrayList<>();
        System.out.println("\nAVAILABLE CLASS");
        if (sList.size() > 0) {

            for (int i = 0; i < sList.size(); i++) {
                if (sList.get(i).getStatus().equalsIgnoreCase("Active")
                        && sList.get(i).getTrainer_id() == trainer_id) {
                    classActive.add(sList.get(i));
                }

            }
            pageOfDisplay(classActive);
        } else {
            throw new Exception("Empty List!");
        }
        return classActive;
    }
}
