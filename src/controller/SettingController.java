/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SettingDAO;
import model.SettingList;
import java.util.ArrayList;
import java.util.List;
import model.Subject;
import view.Utility;

/**
 *
 * @author admin
 */
public class SettingController {
    //Main code owner of this class: Ánh
    //Have function add in this class: Linh ( cho Subject Setting )

    static SettingDAO settingDAO = new SettingDAO();

    public void displayAllSetting() throws Exception {
        List<SettingList> listSetting = settingDAO.getList();
        displayBorder();
        for (SettingList stl : listSetting) {
            System.out.println(stl);
        }
    }

    public SettingList getSettingListByID(int id) throws Exception {
        List<SettingList> listSetting = settingDAO.getList();
        //loop run from first to last element of listSettingLists
        for (SettingList st : listSetting) {
            //if duplicate id => return SettingList has existed
            if (st.getId() == id) {
                return st;
            }
        }
        return null;
    }

    public List<SettingList> getListSettingLists() throws Exception {
        List<SettingList> listSetting = settingDAO.getList();
        return listSetting;
    }

    public SettingList getSettingListByName(String name) throws Exception {
        List<SettingList> listSetting = settingDAO.getList();
        //loop run from first to last element in listSettingLists
        for (SettingList st : listSetting) {
            if (st.getNameString().toUpperCase().contains(name.toUpperCase())) {
                return st;
            }
        }
        return null;
    }

//    public void deleteSettingList(SettingList st) throws Exception {
//        displayBorder();
//        System.out.println(st);
//        settingDAO.deleteSettingToList(st);
//    }
    public List<SettingList> findSettingListByName(String name) throws Exception {
        List<SettingList> listSetting = settingDAO.getList();
        List<SettingList> listSearch = new ArrayList<>();
        //loop run from first to last element in listSettingLists
        for (SettingList st : listSetting) {
            //compare name to find SettingList by name then add to listSearch
            if (st.getNameString().toUpperCase().contains(name.toUpperCase())) {
                listSearch.add(st);

            }
        }
        //System.out.println(listSearch);
        return listSearch.isEmpty() ? null : listSearch;

    }

    public List<SettingList> findDataByFilter(int settingType, int status) throws Exception {
        List<SettingList> listSetting = settingDAO.getList();
        List<SettingList> listSearch = new ArrayList<>();
        if (settingType == 5) {
            for (SettingList st : listSearch) {
                if (st.getStatus() == status) {
                    listSearch.add(st);
                }
            }
        } else if (status == 3) {
            for (SettingList st : listSearch) {
                if (st.getSettingType() == settingType) {
                    listSearch.add(st);
                }
            }
        } else {
            for (SettingList st : listSearch) {
                if (st.getStatus() == status
                        && st.getSettingType() == settingType) {
                    listSearch.add(st);
                }
            }
        }
        
        return listSearch.isEmpty() ? null : listSearch;
    }

    public void addSettingList(SettingList st) throws Exception {
        List<SettingList> listSetting = settingDAO.getList();
        listSetting.add(st);
        settingDAO.insertSettingToList(st);
    }

    public boolean checkDuplicate(int settingType, String valueString, int order, String name) throws Exception {
        List<SettingList> listSetting = settingDAO.getList();
        //loop from first to last element to check duplicate
        for (SettingList st : listSetting) {
            //if id,valueString, order and name is duplicate then return true
            if (settingType == st.getSettingType()
                    && st.getValueString().equalsIgnoreCase(valueString)
                    && st.getOrder() == order
                    && st.getNameString().equalsIgnoreCase(name)) {
                System.out.println(st.getNameString() + " was ordered " + st.getOrder()
                        + " with value " + st.getValueString());
                return true;
            }
        }
        return false;
    }

//    public void updateSetting(int settingType, String nameString, int order, String valueString, int status, SettingList st) {
//        st.setSettingType(settingType);
//        st.setNameString(nameString);
//        st.setOrder(order);
//        st.setValueString(valueString);
//        st.setStatus(status);
//        st.setAction(status);
//
//    }
    public boolean checkNotUpdate(int settingType, String nameString, int order, String valueString, int status) throws Exception {
        List<SettingList> listSetting = settingDAO.getList();
        for (SettingList st : listSetting) {
            //check duplicate
            if (st.getSettingType() == settingType
                    && st.getNameString().equalsIgnoreCase(nameString)
                    && st.getOrder() == order
                    && st.getValueString().equalsIgnoreCase(valueString)
                    && st.getStatus() == status) {
                return true;
            }

        }
        return false;
    }

    public void displayBorder() {
        System.out.println("==============================================================================================================");
        System.out.printf("%-5s|%-30s|%-20s|%-10s|%-10s|%-10s|%-10s", "ID", "Setting Type", "Name", "Order", "Value", "Status", "Action");
        System.out.println("\n==============================================================================================================");

    }
        public void displayBorderDetails() {
        System.out.println("==============================================================================================================");
        System.out.printf("%-5s|%-30s|%-20s|%-10s|%-10s|%-10s|%-20s|%-10s", "ID", "Setting Type", "Name", "Order", "Value", "Status", "Action", "Discription");
        System.out.println("\n==============================================================================================================");

    }

    public void displayList(List<SettingList> list) throws Exception {
        if (list.size() > 0) {
            System.out.println("==============================================================================================================");
            System.out.printf("%-5s|%-30s|%-20s|%-10s|%-10s|%-10s|%-10s", "ID", "Setting Type", "Name", "Order", "Value", "Status", "Action");
            System.out.println("\n==============================================================================================================");
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

    public List<SettingList> pagination(List<SettingList> list, int page) throws Exception {
        //set up list 5 để trả về
        List<SettingList> settingList = new ArrayList<>();
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
                        settingList.add(list.get(i));
                    }
                }
                i++;
            } while (i < start + 5);

            return settingList;

        } else {
            throw new Exception("Can't not go to that page");
        }

    }

    public void pageOfDisplay(List<SettingList> listDisplay) {
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

    //========================ham cua Linh cho subject setting====================================
    public static boolean checkExistTypeByID(List<SettingList> slist, int id) throws Exception {

        for (int i = 0; i < slist.size(); i++) {
            if (slist.get(i).getSettingType() == id) {
                return true; //exist
            }
        }
        return false; //not exist
    }

    //hien thi voi type id
    public void showTypeIDList(List<SettingList> slist) {
        System.out.println("============= TYPE LIST ===========");
        System.out.println(String.format("%-10s|%-20s", "Type ID", "Type Title"));
        System.out.println("-----------------------------------");
        for (int i = 0; i < slist.size(); i++) {
            System.out.println(String.format("%-10d|%-20s",
                    slist.get(i).getSettingType(),
                    slist.get(i).getType(slist.get(i).getSettingType())));
        }
    }

    //=========================================================================================
    public boolean isDuplicated(String valueString, int order, String nameString) throws Exception {
        List<SettingList> listSetting = settingDAO.getList();
        //loop from first to last element to check duplicate
        for (SettingList st : listSetting) {
            //if id,valueString, order and name is duplicate then return true
            if (st.getValueString().equalsIgnoreCase(valueString)
                    && st.getOrder() == order
                    && st.getNameString().equalsIgnoreCase(nameString)) {
                System.out.println(st.getNameString() + " was ordered " + st.getOrder()
                        + " with value " + st.getValueString());
                return true;
            }
        }
        return false;
    }
}
