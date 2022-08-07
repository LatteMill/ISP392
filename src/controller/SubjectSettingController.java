package controller;

import model.*;
import java.util.*;
import dao.*;

public class SubjectSettingController {
    //Main code owner of this class: Linh
    //Have function add in this class: ----

    static SubjectSettingDAO subjectSettingDAO = new SubjectSettingDAO();

    static SubjectDAO subjectDAO = new SubjectDAO();
    static SubjectController subjectController = new SubjectController();

    static SettingDAO settingDAO = new SettingDAO();
    static SettingController settingController = new SettingController();

    

    //check exist
    public static boolean checkExistSubjectSetting(SubjectSetting subjectSetting) throws Exception {
        List<SubjectSetting> subjectSettingList = subjectSettingDAO.getList();
        for (int i = 0; i < subjectSettingList.size(); i++) {
            if (subjectSettingList.get(i).getSetting_title().replaceAll("\\s+", " ").compareToIgnoreCase(subjectSetting.getSetting_title().replaceAll("\\s+", " ")) == 0
                    && subjectSettingList.get(i).getSubjectID() == subjectSetting.getSubjectID()
                    && subjectSettingList.get(i).getType_id() == subjectSetting.getType_id()
                    && subjectSettingList.get(i).getSetting_value() == subjectSetting.getSetting_value()) {
                return true; //ton tai
            }
        }
        return false;// khong ton tai
    }

    public static boolean checkExistSubjectSettingByID(int id) throws Exception {
        List<SubjectSetting> subjectSettingList = subjectSettingDAO.getList();
        for (int i = 0; i < subjectSettingList.size(); i++) {
            if (subjectSettingList.get(i).getSetting_id() == id) {
                return true; //ton tai
            }
        }
        return false;// khong ton tai
    }

    //add Sub Setting
    public static void addNewSubject_Setting(SubjectSetting subjectSetting) throws Exception {
        List<SubjectSetting> subjectSettingList = subjectSettingDAO.getList();

        //kiem tra xem Subject Setting da ton tai hay chua
        if (!checkExistSubjectSetting(subjectSetting)) {

            //kiem tra xem Subject ID co thoa man khong
            if (subjectController.checkExistSubjectByID(subjectDAO.getListWithOneCondition("status", 1 + "", "int"), subjectSetting.getSubjectID())) {
                //kiem tra type ID co thoa man khong
                if (settingController.checkExistTypeByID(settingDAO.getType_idList(), subjectSetting.getType_id())) {
                    subjectSettingList.add(subjectSetting);

                    subjectSettingDAO.insertSubjectSettingToList(subjectSetting);

                    System.out.println("Add successfully!");
                } else {
                    throw new Exception("Type ID is not valid! Please use the type ID in current active list! ");
                }
            } else {
                throw new Exception("Subject ID is not valid! Please use the subject ID in current active list! ");
            }
        } else {
            throw new Exception("Exist subject setting!");
        }

    }

    //search subjectSetting by ID
    public static SubjectSetting searchSubjectSettingByID(int id) throws Exception {
        List<SubjectSetting> subjectSettingList = subjectSettingDAO.getList();

        for (int i = 0; i < subjectSettingList.size(); i++) {
            if (subjectSettingList.get(i).getSetting_id() == id) {
                return subjectSettingList.get(i);
            }
        }
        return null;
    }

    //find SubjectSetting index by ID
    public static int searchSubjectSettingIndexByID(int id) throws Exception {
        List<SubjectSetting> subjectSettingList = subjectSettingDAO.getList();
        for (int i = 0; i < subjectSettingList.size(); i++) {
            if (subjectSettingList.get(i).getSetting_id() == id) {
                return i;
            }
        }
        return -1;
    }

    //update Sub Setting
    public static void updateNewSubjectSetting(SubjectSetting subjectSetting) throws Exception {
        List<SubjectSetting> subjectSettingList = subjectSettingDAO.getList();
        List<Subject> sList = subjectDAO.getList();

        if (checkExistSubjectSettingByID(subjectSetting.getSetting_id())) {
            //kiem tra xem Subject ID co thoa man khong
            if (subjectController.checkExistSubjectByID(subjectDAO.getListWithOneCondition("status", 1 + "", "int"), subjectSetting.getSubjectID())) {
                //kiem tra type ID co thoa man khong
                if (settingController.checkExistTypeByID(settingDAO.getType_idList(), subjectSetting.getType_id())) {

                    subjectSettingList.set(searchSubjectSettingIndexByID(subjectSetting.getSetting_id()), subjectSetting);
                    subjectSettingDAO.updateSubjectSettingToList(subjectSetting);
                    System.out.println("Update successfully!");
                } else {
                    throw new Exception("Type ID is not valid! Please use the type ID in current active list! ");
                }
            } else {
                throw new Exception("Subject ID is not valid! Please use the subject ID in current active list! ");
            }
        } else {
            throw new Exception("Can not find the Subject!");
        }
    }

    //change status
    public static void changeSubjectSettingStatus(int id) throws Exception {
        List<SubjectSetting> subjectSettingList = subjectSettingDAO.getList();
        if (checkExistSubjectSettingByID(id)) {
            try {
                SubjectSetting subjectSetting = searchSubjectSettingByID(id);
                String changeStatus;
                if (subjectSetting.isSubject_setting_status() == true) {
                    subjectSetting.setSubject_setting_status(false);

                    ////ud.updateUserToList(ur, "verify", verifyString, "boolean");
                    subjectSettingDAO.updateSubjectSettingToList(subjectSetting);
                } else {
                    subjectSetting.setSubject_setting_status(true);
                    subjectSettingDAO.updateSubjectSettingToList(subjectSetting);
                }
                System.out.println("Update successfully!");
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new Exception("Subject Setting is NOT exist!");
        }
    }

    //seach code(1), name(2), status(3)
    public List<SubjectSetting> searchByPattern(String pattern) throws Exception {
        List<SubjectSetting> subjectSettingList = subjectSettingDAO.getList();
        List<SubjectSetting> searchList = subjectSettingDAO.getSearchList(pattern);
        
        return searchList;
    }

    //Ham cua Phuoc cho Loc
    public static boolean checkSubjectSettingExistByTypeID(List<SubjectSetting> c, int id) throws Exception {
        if (c.size() > 0) {
            for (int i = 0; i < c.size(); i++) {
                if (c.get(i).getType_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    public static boolean checkSubjectSettingExistBydisplayOrderID(List<SubjectSetting> c, int id) throws Exception {
        if (c.size() > 0) {
            for (int i = 0; i < c.size(); i++) {
                if (c.get(i).getDisplay_order() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }
    
    public static List<SubjectSetting> pagination(List<SubjectSetting> list, int page) throws Exception {
        //set up list 5 để trả về
        List<SubjectSetting> subjectSettingList = new ArrayList<>();
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
                if (i == list.size() ) {
                    break;
                } else {
                    if (list.get(i) != null) {
                        subjectSettingList.add(list.get(i));
                    }
                }
                i++;
            } while (i < start + 5);

            return subjectSettingList;

        } else {
            throw new Exception("Can't not go to that page");
        }
    }
}
