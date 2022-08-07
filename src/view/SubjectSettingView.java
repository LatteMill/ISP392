package view;

import java.util.*;
import model.*;
import controller.*;
import dao.*;

public class SubjectSettingView {
    //ham goi: SubjectSettingView.SubjectSettingList();

    //subject setting:
    static SubjectSettingController subjectSettingController = new SubjectSettingController();
    static SubjectSettingDAO subjectSettingDAO = new SubjectSettingDAO();

    //subject:
    static SubjectDAO subjectDAO = new SubjectDAO();
    static SubjectController subjectController = new SubjectController();

    //setting list
    static SettingController settingController = new SettingController();
    static SettingDAO settingDAO = new SettingDAO();

    //display
    public static void displaySubjectSettingList() {
        System.out.println("============================ Subject Setting List ============================");
//        System.out.println("1. Show Subject Setting List");
        System.out.println("1. Add Subject Setting List");
        System.out.println("2. Active/Deactive status in Subject Setting List");
        System.out.println("3. Search Subject Setting List");
        System.out.println("4. Update a Subject Setting List");
        System.out.println("5. Back to Dashboard");
    }

    public static void displaySearch() {
        System.out.println("============================ Search Subject Setting List ============================");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Type ID");
        System.out.println("3. Search by Subject ID");
        System.out.println("4. Search by Status");
        System.out.println("5. Back to Subject Setting List");
    }

    public static void displaySearchStatus() {
        System.out.println("============================ Search by status ============================");
        System.out.println("1. Active");
        System.out.println("2. Deactive");
        System.out.println("3. Back to Search Subject List");
    }

    public static void displayFeature() {
    System.out.println(String.format("%-5s|%-15s|%-25s|%-20s|%-10s|%-15s|%-10s",
                    "ID", "Subject Code", "Type Title", "Setting Title", "Value", "Display order", "Status"));
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
}

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Function.");
        System.out.println("5. Back");
    }

    public static void SubjectSettingList() throws Exception {
        boolean status = false;
        int page = 1, showChoice = 0;

        while (showChoice != 5) {
            List<SubjectSetting> subjectSettingList = subjectSettingDAO.getList();

            int maxpage = subjectSettingList.size() / 5;
            if (maxpage != 1) {
                if (subjectSettingList.size() % 5 != 0) {
                    maxpage++;
                }
            }
            System.out.println("There are: " + maxpage + " pages.");
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                showSubjectSettingList(subjectSettingController.pagination(subjectSettingList, page));
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
                    int option = 0;

                    while (option != 5) {
                        displaySubjectSettingList();
                        option = Utility.getInt("Enter Option: ", "Wrong!", 0, 5);
                        switch (option) {

                            case 1: {//add new
                                System.out.println("============================ Add New Subject Setting ============================");

                                //subject ID: FK
                                System.out.println("The current subject active list: ");
                                showSubjectList(subjectDAO.getListWithOneCondition("status", 1 + "", "int"));
                                int subject_id = Utility.getInt("Enter Subject ID for Subject Setting: ", "Wrong", 1, Integer.MAX_VALUE);
                                System.out.println("");

                                //Type ID: FK
                                System.out.println("The current setting active list: ");
                                showTypeIDList(settingDAO.getType_idList());
                                int type_id = Utility.getInt("Enter Type ID: ", "Wrong", 0, Integer.MAX_VALUE);
                                System.out.println("");

                                //private feature in table
                                String setting_title = Utility.getString("Enter Subject Setting Title: ", "Wrong!", Utility.REGEX_STRING);
                                int setting_value = Utility.getInt("Enter Subject Setting value: ", "Wrong!", 0, 240);
                                int display_order = Utility.getInt("Enter display order: ", "Wrong!", 0, Integer.MAX_VALUE);

                                int statusNumber = Utility.getInt("Enter 1 - active or 0 - deactive: ", "Wrong!", 0, 1);
                                if (statusNumber == 1) {
                                    status = true;
                                }
                                if (statusNumber == 0) {
                                    status = false;
                                }

                                SubjectSetting ss = new SubjectSetting(type_id, setting_title, setting_value, display_order, status, subject_id);
                                try {
                                    subjectSettingController.addNewSubject_Setting(ss);

                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            break;
                            case 2: {//active, deactive
                                System.out.println("======================Change Status=======================");
                                int id = Utility.getInt("Enter Subject Setting ID to change status: ", "Wrong", 1, Integer.MAX_VALUE);
                                try {
                                    subjectSettingController.changeSubjectSettingStatus(id);
                                    System.out.println("Current subject setting detail: ");
                                    displayFeature();
                                    System.out.println(subjectSettingController.searchSubjectSettingByID(id));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            break;
                            case 3: { //search Subject List

                                String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING);
                                try {
                                    showSubjectSettingList(subjectSettingController.searchByPattern(pattern));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 4: { //update
                                System.out.println("============================ Update Subject Setting ============================");
                                System.out.println("*Enter to remain the old data!\n");
                                SubjectSetting subjectSetting;
                                int id;
                                do {
                                    id = Utility.getInt("Enter Subject Setting ID to update: ", "Wrong!", 1, Integer.MAX_VALUE);
                                    subjectSetting = subjectSettingController.searchSubjectSettingByID(id);
                                } while (subjectSettingController.searchSubjectSettingByID(id) == null);
//int type_id, int display_order, String setting_title, String setting_value, boolean subject_setting_status, int subjectID ) {
//                                System.out.println("Current detail of Subject Setting: ");
//                                displayFeature();
//                                System.out.println(subjectSetting);
//                                System.out.println("");

                                //subject ID
                                System.out.println("The current subject active list: ");
                                showSubjectList(subjectDAO.getListWithOneCondition("status", 1 + "", "int"));
                                int subjectID = Utility.getIntNull("Enter Subject ID (Current: "+subjectSetting.getSubjectID()+") : ", subjectSetting.getSubjectID(), 0, Integer.MAX_VALUE);

                                //TypeID
                                System.out.println("The current setting active list: ");
                                showTypeIDList(settingDAO.getType_idList());
                                int type_id = Utility.getIntNull("Enter Type ID (Current: "+subjectSetting.getType_id()+"): ", subjectSetting.getType_id(), 0, Integer.MAX_VALUE);
                                System.out.println("");

                                //Oter feature
                                int display_order = Utility.getIntNull("Enter display order(Current: "+subjectSetting.getDisplay_order()+"): ", subjectSetting.getDisplay_order(), 0, Integer.MAX_VALUE);
                                String setting_title = Utility.getStringNull("Enter Setting Title(Current: "+subjectSetting.getSetting_title()+"): ", subjectSetting.getSetting_title());
                                int setting_value = Utility.getIntNull("Enter Setting Value(Current: "+subjectSetting.getSetting_value()+"): ", subjectSetting.getSetting_value(), 0, 240);

                                int oldStatusNumber = 1;
                                if (!subjectSetting.isSubject_setting_status()) {
                                    oldStatusNumber = 0;
                                }

                                int statusNumber = Utility.getIntNull("Enter 1 - active or 0 - deactive(Current: "+subjectSetting.getSubjectSetting_Status()+"): ", oldStatusNumber, 0, 1);
                                if (statusNumber == 1) {
                                    status = true;
                                }
                                if (statusNumber == 0) {
                                    status = false;
                                }

                                try {
                                    subjectSettingController.updateNewSubjectSetting(new SubjectSetting(id, type_id, setting_title, setting_value, display_order, status, subjectID));
//                                    System.out.println(new SubjectSetting(id, type_id, setting_title, setting_value, display_order, status, subjectID));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 5:
                                option = 5;
                        }
                    }
                }
            } while (0 > page || page > maxpage);
        }
    }

    public static void showSubjectList(List<Subject> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("============================================================== Subject List ===================================================");
            System.out.println(String.format("%-5s|%-15s|%-30s|%-25s|%-20s|%-15s", "ID", "Code", "Name", "Author", "Status", "Description"));
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------");

            for (int i = 0; i < sList.size(); i++) {
                System.out.println(sList.get(i));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

    public static void showSubjectSettingList(List<SubjectSetting> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("=============================================== Subject Setting List =============================================================");
            System.out.println(String.format("%-5s|%-15s|%-25s|%-20s|%-10s|%-15s|%-10s",
                    "ID", "Subject Code", "Type Title", "Setting Title", "Value", "Display order", "Status"));
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------");

            for (int i = 0; i < sList.size(); i++) {
                System.out.println(sList.get(i));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

    //hien thi voi type id
    public static void showTypeIDList(List<SettingList> slist) {
        System.out.println("============= TYPE LIST ===========");
        System.out.println(String.format("%-10s|%-20s", "Type ID", "Type Title"));
        System.out.println("-----------------------------------");
        for (int i = 0; i < slist.size(); i++) {
            System.out.println(String.format("%-10d|%-20s",
                    slist.get(i).getSettingType(),
                    slist.get(i).getType(slist.get(i).getSettingType())));
        }
    }
}
