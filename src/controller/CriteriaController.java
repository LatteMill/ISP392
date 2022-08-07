package controller;

import java.util.*;
import model.*;
import dao.*;
import view.Utility;

public class CriteriaController {

    static CriteriaDAO CriteriaDao = new CriteriaDAO();

    //iteration
    static IterationController iterController = new IterationController();
    static IterationDAO iterDAO = new IterationDAO();

    //check Criteria ton tai chua? 
    public static boolean checkCriteriaExist(Criteria ec) throws Exception {
        List<Criteria> classList = CriteriaDao.getList();

        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getCriteria_id() == ec.getCriteria_id()
                        && classList.get(i).getIteration_id() == ec.getIteration_id()
                        && classList.get(i).getCriteria_order() == ec.getMax_loc()
                        && classList.get(i).getTeam_evaluation() == ec.getTeam_evaluation()) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    public static Criteria searchByIterationID(int subject_id) throws Exception {
        List<Criteria> classList = CriteriaDao.getList();
        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getIteration_id() == subject_id) {
                    return classList.get(i);
                }
            }
        }
        return null;

    }

    //check Criteria ton tai chua? 
    public static boolean checkCriteriaExistByID(List<Criteria> iList, int id) throws Exception {
        if (iList.size() > 0) {
            for (int i = 0; i < iList.size(); i++) {
                if (iList.get(i).getCriteria_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    public static boolean checkExistUserByIterID(List<Criteria> slist, int id) throws Exception {
        for (int i = 0; i < slist.size(); i++) {
            if (slist.get(i).getIteration_id() == id) {
                return true; //exist
            }
        }
        return false; //not exist
    }

    //them Criteria moi
    public static void addNewCriteria(Criteria criterias) throws Exception {
        //check class da ton tai chua
        if (!checkCriteriaExist(criterias)) {
            //check user id da ton tai:
            if (iterController.checkIterationExistByID(iterDAO.getIterList(), criterias.getIteration_id())) {
                //check subject_id da ton tai
                CriteriaDao.insertCriteriaToList(criterias);
                System.out.println("Add successfully!");
            } else {
                throw new Exception("Enter Iter ID in list showed only.");
            }
        } else {
            throw new Exception("Exist Class!");
        }
    }

    //find Criteria index
    public static int searchCriteriaIndexByID(int id) throws Exception {
        List<Criteria> classList = CriteriaDao.getList();
        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getCriteria_id() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static Criteria searchCriteriaByID(int id) throws Exception {
        List<Criteria> classList = CriteriaDao.getList();
        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getCriteria_id() == id) {
                    return classList.get(i);
                }
            }
        }
        return null;
    }

    public List<Criteria> searchByPatternID(int pattern) throws Exception {
        List<Criteria> pList = CriteriaDao.getList();
        List<Criteria> pl = new ArrayList<>();
        //chuyển tất cả về toLowerCase
        try {
            for (int i = 0; i < pList.size(); i++) {
                if (pList.get(i).getCriteria_id() == pattern) {
                    pl.add(pList.get(i));
                }
            }
        } catch (Exception e) {
            throw new Exception("Wrong.");
        }

        return pl;
    }

    //update Criteria 
    public static void updateCriteria(Criteria criterias) throws Exception {

        //check criteria id da ton tai:
        if (!checkCriteriaExist(criterias)) {
            //check iteration id da ton tai
            if (iterController.checkIterationExistByID(iterDAO.getIterList(), criterias.getIteration_id())) {
                //update
                CriteriaDao.updateClassToList(criterias);
                System.out.println("Update sucessfully !");
            } else {
                throw new Exception("Enter Iter ID in list showed only.");
            }
        } else {
            throw new Exception("Exist Criteria!");
        }

    }

    public static void changeCriteriaStatus(int id, int option) throws Exception {
        List<Criteria> criteriaList = CriteriaDao.getList();
        if (checkCriteriaExistByID(criteriaList, id)) {
            try {
                Criteria sb = searchCriteriaByID(id);
                if (option == 1) {
                    sb.setStatus(option);
                } else {
                    sb.setStatus(option);
                }
                CriteriaDao.updateClassToList(sb);
                System.out.println("Change sucessfully !");
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new Exception("Criteria is NOT exist!");
        }
    }

    //seach team_evaluation(1), name(2), status(3)
    public List<Criteria> searchByPattern(String pattern, boolean author, int subject_id) throws Exception {

        List<Criteria> il = new ArrayList<>();

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
        //chuyển tất cả về toLowerCase
        pattern = pattern.toLowerCase();
        il = CriteriaDao.getListForSearch(pattern, author, subject_id);
        if (il.isEmpty()) {
            throw new Exception("Empty list!");
        }
        return il;
    }
    //sap xep Criteria

    public List<Criteria> sortCriteria(int role, int subject_id, int choice) throws Exception {
//        List<Issue> sl = new List<>();
        switch (choice) {
            case 1: // so sánh iteration_id
                return CriteriaDAO.getListForSort(role, subject_id, "iteration_id");
//                break;
            case 2: //so sánh evaluation_weight
                return CriteriaDAO.getListForSort(role, subject_id, "evaluation_weight");
            case 3: //so sanh criteria_order
                return CriteriaDAO.getListForSort(role, subject_id, "criteria_order");
            case 4: //max_loc
                return CriteriaDAO.getListForSort(role, subject_id, "max_loc");
            case 5: //so sanh status
                return CriteriaDAO.getListForSort(role, subject_id, "status");

            //nếu nhập khác thì exit sort
            default:
                System.out.println("Exit sort.");
                break;
        }
        return null;
        //    return sl;
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

    public List<Criteria> pagination(List<Criteria> list, int page) throws Exception {
        //set up list 5 để trả về
        List<Criteria> issueList = new ArrayList<>();
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

    //===============================Hàm của Linh=================
    public boolean checkCriteriaByIDInList(List<Criteria> criteriaList, int id) {
        for (int i = 0; i < criteriaList.size(); i++) {
            if (criteriaList.get(i).getCriteria_id() == id) {
                return true; //tồn tại
            }
        }
        System.out.println("Criteria ID can not find in list showed.");
        return false; // không tồn tại
    }
    //====================Ham cua Anh===================

    public Criteria getCriteriaByID(int id) throws Exception {
        List<Criteria> list = CriteriaDao.getList();
        for (Criteria c : list) {
            if (c.getCriteria_id() == id) {
                return c;
            }
        }
        return null;
    }

}
