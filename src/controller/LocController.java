/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.ClassController.classDao;
import java.util.*;
import model.*;
import dao.*;
import view.Utility;

/**
 *
 * @author leeph
 */
public class LocController {
    //Main code owner of this class: Phuoc

    static LocDAO locDAO = new LocDAO();

    //setting
    static SubjectSettingDAO subjectSettingDAO = new SubjectSettingDAO();
    static SubjectSettingController subjectSettingController = new SubjectSettingController();

    //tracking
    static TrackingDAO trackingDAO = new TrackingDAO();
    static TrackingController trackingController = new TrackingController();

    //class_user
    static TrackingDAO classUserDAO = new TrackingDAO();
    static TrackingController classUserController = new TrackingController();

    //check Loc ton tai chua ?
    public static boolean checkLocExist(Loc locs) throws Exception {
        List<Loc> classList = locDAO.getList();

        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getEvaluation_id() == locs.getEvaluation_id()
                        && classList.get(i).getComplexity_id() == locs.getComplexity_id()
                        && classList.get(i).getQuality_id() == locs.getQuality_id()
                        && classList.get(i).getTracking_id() == locs.getTracking_id()) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton ta
    }

    public static Loc searchByTrackingID(int tracking_id) throws Exception {
        List<Loc> classList = locDAO.getList();
        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getTracking_id() == tracking_id) {
                    return classList.get(i);
                }
            }
        }
        return null;
    }

    //check Loc ton tai chua? 
    public static boolean checkLocExistByEvaluationID(List<Loc> locList, int id) throws Exception {
        if (locList.size() > 0) {
            for (int i = 0; i < locList.size(); i++) {
                if (locList.get(i).getEvaluation_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    public static boolean checkExistLocByTrackingID(List<Loc> slist, int id) throws Exception {
        for (int i = 0; i < slist.size(); i++) {
            if (slist.get(i).getTracking_id() == id) {
                return true; //exist
            }
        }
        return false; //not exist
    }

    //them loc moi
    public static void addNewLoc(Loc loc) throws Exception {
        //check loc da ton tai chua
        if (!checkLocExist(loc)) {
            if (trackingController.checkTrackingExistByID(trackingDAO.getList(), loc.getTracking_id()) == true) {
                locDAO.insertLocToList(loc);
                System.out.println("Add sucessfully.");
            }else{
                throw new Exception("Enter track id in list only.");
            }
        }else{
            throw new Exception("Not exist loc.");
        }
    }

    //find loc index
    public static int searchLocIndexByID(int id) throws Exception {
        List<Loc> locList = locDAO.getList();
        if (locList.size() > 0) {
            for (int i = 0; i < locList.size(); i++) {
                if (locList.get(i).getEvaluation_id() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static Loc searchLocByID(int id) throws Exception {
        List<Loc> classList = locDAO.getList();
        if (classList.size() > 0) {
            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getEvaluation_id() == id) {
                    return classList.get(i);
                }
            }
        }
        return null;
    }

    //update Loc
    public static void updateLoc(Loc loc) throws Exception {
        //check loc da ton tai chua
        if (checkLocExistByEvaluationID(locDAO.getList(), loc.getEvaluation_id())) {
            if (trackingController.checkTrackingExistByID(trackingDAO.getList(), loc.getTracking_id())) {
                locDAO.updateLocToList(loc);
                System.out.println("Update sucessfully.");
            }else{
                throw new Exception("Enter track id in list only.");
            }
        }else{
            throw new Exception("Not exist Loc Evaluation.");
        }
    }

    public List<Loc> searchByPattern(String pattern, boolean student, int assignee_id) throws Exception {

        List<Loc> locList = new ArrayList<>();

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
        locList = locDAO.getListForSearch(pattern, student, assignee_id);
        if (locList.isEmpty()) {
            throw new Exception("Empty list!");
        }
        return locList;
    }

    //Sap xep loc
    public List<Loc> sortLoc(boolean student, int assignee_id, int choice) throws Exception {
//        List<Loc> sl = new List<>();
        switch (choice) {
            case 1: // so sánh evalaution time
                return locDAO.getListForSort(student, assignee_id, "evaluation_time");
            case 2: //so sánh complexity id
                return locDAO.getListForSort(student, assignee_id, "tracking_id");
            case 3: //so sanh quality id
                return locDAO.getListForSort(student, assignee_id, "quality_id");
            //nếu nhập khác thì exit sort
            default:
                System.out.println("Exit sort.");
                break;
        }
        return null;
        //    return sl;
    }

    public Tracking getLocByTrackingID(int trackingID) throws Exception {
        List<Tracking> sbList = trackingDAO.getList();
        Tracking clu = new Tracking();
        for (Tracking user : sbList) {
            if (user.getTrackingID() == trackingID) {
                return user;

            }
        }
        return null;
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

    public List<Loc> pagination(List<Loc> list, int page) throws Exception {
        //set up list 5 để trả về
        List<Loc> issueList = new ArrayList<>();
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
                        issueList.add(list.get(i));
                    }
                }
                i++;
            } while (i < start + 5);

            return issueList;

        } else {
            throw new Exception("Can't not go to that page");
        }
    }
}
