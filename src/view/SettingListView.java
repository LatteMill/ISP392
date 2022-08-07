/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.sun.javafx.css.Combinator;
import controller.SettingController;
import dao.SettingDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.SettingList;
import java.util.List;
import static view.Utility.scanner;

/**
 *
 * @author admin
 */
public class SettingListView {

    SettingController settingListController = new SettingController();
    static SettingDAO settingDAO = new SettingDAO();

    void addSetting() throws ClassNotFoundException, SQLException, Exception {

        int order, status, settingType;
        String nameString, valueString, description;
        //id = Utility.getID();

        //check update ID, true => update
        while (true) {
            System.out.println("\n\n                                            ADD SETTING DETAILS\n");
            settingType = Utility.getType();
            nameString = Utility.getString("Enter setting name: ", "It must be string!!!", Utility.REGEX_STRING);
            order = Utility.getOrder();
            valueString = Utility.getStringNull("Enter value: ", "");
            status = 1;
            System.out.println("Description");
            description = scanner.nextLine();
            break;

        }

        SettingList st = new SettingList(settingType, nameString, order, valueString, status);
        if (settingListController.isDuplicated(valueString, order, nameString) == true) {
            System.out.println("ADD FALSE!!! DUPLICATE");
        } else {
            settingDAO.insertSettingToList(st);
            System.out.println("ADD SUCCESSFULL");
            List<SettingList> list = settingDAO.getList();
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    //"ID", "Setting Type", "Name", "Order", "Value", "Status", "Action", "Discription"
                    settingListController.displayBorderDetails();
                    System.out.printf(String.format("%-5s|%-30s|%-20s|%-10s|%-10s|%-10s|%-20s|",
                            list.get(i).getId(), list.get(i).getType(settingType), list.get(i).getNameString(),
                            list.get(i).getOrder(), list.get(i).getValueString(), list.get(i).getStatus(status),
                            list.get(i).getAction(status)));
                    System.out.println(description);
                }

            }
        }
        System.out.println("\n\n");
    }

    void updateSettingList() throws ClassNotFoundException, SQLException, Exception {
        int id, order, status, settingType;
        String nameString, valueString;
        id = Utility.getID();
        SettingList st = settingListController.getSettingListByID(id);
        if (st == null) {
            System.out.println("NOT FOUND ID\n");
            return;
        }
        settingListController.displayBorder();
        System.out.println(st);
//        //check update Setting Type, true => update
//        if (Utility.checkWantToUpdate("Setting Type") == true) {
//            settingType = Utility.getType();
//        } else {
//            settingType = st.getSettingType();
//        }
//        //check update name, true => update
//        if (Utility.checkWantToUpdate("name") == true) {
//            nameString = Utility.getNameString();
//        } else {
//            nameString = st.getNameString();
//        }
//        //check update order, true => update
//        if (Utility.checkWantToUpdate("order") == true) {
//            order = Utility.getOrder();
//        } else {
//            order = st.getOrder();
//        }
//        //check update value, true => update
//        if (Utility.checkWantToUpdate("value") == true) {
//            valueString = Utility.getValue();
//        } else {
//            valueString = st.getValueString();
//        }
//        //check update status, true => update
//        if (Utility.checkWantToUpdate("status") == true) {
//            status = Utility.getStatus();
//        } else {
//            status = st.getStatus();
//        }
        System.out.println("\nPRESS ENTER TO UNCHANGE DATA");
        settingType = Utility.getIntNull("1/ Subject Catergory   2/ Lesson Type  3/Lesson Category  4/ Project Subject Category\nEnter settingType: ", st.getSettingType(), 1, 4);
        nameString = Utility.getStringNull("Enter setting name: ", st.getNameString());
        valueString = Utility.getStringNull("Enter value: ", st.getValueString());
        order = Utility.getIntNull("Enter order: ", st.getOrder(), 1, Integer.MAX_VALUE);
        status = Utility.getIntNull("Status:    1/ Active    2/ Inactive\nEnter Status:  ", st.getStatus(), 1, 2);

        if (settingListController.checkNotUpdate(settingType, nameString, order, valueString, status) == true) {
            System.out.println("YOU NOT UPDATE !!!");
        } else if (settingListController.checkDuplicate(id, valueString, order, nameString) == true) {
            System.out.println("UPDATE FAIL- DUPLICATE");
        } else {
            st.setSettingType(settingType);
            st.setNameString(nameString);
            st.setOrder(order);
            st.setValueString(valueString);
            st.setStatus(status);
            st.setAction(status);
            settingDAO.updateSettingToList(st);
            System.out.println("UPDATE SUCCESSFULL");
            settingListController.displayBorder();
            System.out.println(st);

        }
        System.out.println("\n\n");
    }

    void changeStatus() throws ClassNotFoundException, SQLException, Exception {
        int id = Utility.getID();
        SettingList st = settingListController.getSettingListByID(id);
        if (st.getStatus() == 1) {
            st.setStatus(2);
            st.setAction(2);
        } else {
            st.setStatus(1);
            st.setAction(1);
        }
        System.out.println("SUCCESSFULL");
        settingDAO.updateSettingToList(st);
        settingListController.displayBorder();
        System.out.println(st);
        System.out.println("\n\n");
    }

    void searchByName() throws Exception {
        SettingListView view = new SettingListView();
        String name = Utility.getString("Enter name: ", "It must be string!!", Utility.REGEX_NAME);
        List<SettingList> listSetting = settingDAO.getList();
        listSetting = settingListController.findSettingListByName(name);
        if (listSetting == null) {
            System.out.println("NOT FOUND !!");
            return;
        } else {
            settingListController.pageOfDisplay(listSetting);

        }
        System.out.println("\n\n");
    }

    public static void displayChoiceForSearch() {
        System.out.println("\n                                                  SEARCH SETTING\n");
        System.out.println("   Setting Type             Status           ");
        System.out.println("1. Subject Category         1. Active");
        System.out.println("2. Lesson Type              2. Inactive");
        System.out.println("3. Lesson Category          3. All status");
        System.out.println("4. Project Subject Category");
        System.out.println("5. All setting type");

    }

    void searchSettingByFilter() throws Exception {
        displayChoiceForSearch();
        int choiceSettingType = Utility.getInt("Enter setting type: ", "It must be digit", 1, 5);
        int choiceStatus = Utility.getInt("Enter status: ", "It must be digit", 1, 3);
        List<SettingList> listSetting = new ArrayList<>();
        if (choiceSettingType == 5 && choiceStatus == 3) {
            showList();
        } else {
            listSetting = settingListController.findDataByFilter(choiceSettingType, choiceStatus);
            if (listSetting == null) {
                System.out.println("NOT FOUND !!");
            } else {
                settingListController.pageOfDisplay(listSetting);
            }
        }
        System.out.println("\n\n");
    }

    void sortSetting() throws Exception {
        List<SettingList> listSort = settingDAO.getList();
        int choice;
        while (true) {
            displaySortChoice();
            choice = Utility.getInt("Enter choice: ", "It must be number", 1, 7);
            switch (choice) {
                //sort by setting id
                case 1:
                    Collections.sort(listSort, new Comparator<SettingList>() {
                        @Override
                        public int compare(SettingList o1, SettingList o2) {
                            return Integer.toString(o1.getId()).compareTo(Integer.toString(o2.getId()));
                        }

                    });
                    settingListController.pageOfDisplay(listSort);
                    break;
                //sort by settingType
                case 2:
                    //Loop run from the first to last setting type of array 
                    for (int i = 0; i < listSort.size(); i++) {

                        /*Loop run from first element to the 
            element stand before the last unsorted element */
                        for (int j = 0; j < listSort.size() - i - 1; j++) {

                            //Compare each pair adjacent elements
                            if (listSort.get(j).getSettingType() > listSort.get(j + 1).getSettingType()) {
                                SettingList temp = listSort.get(j);
                                listSort.set(j, listSort.get(j + 1));
                                listSort.set(j + 1, temp);

                            }
                        }
                    }
                    settingListController.pageOfDisplay(listSort);
                    break;
                //sort by setting name
                case 3:
                    Collections.sort(listSort, new Comparator<SettingList>() {
                        @Override
                        public int compare(SettingList o1, SettingList o2) {
                            return o1.getNameString().compareTo(o2.getNameString());
                        }

                    });
                    settingListController.pageOfDisplay(listSort);
                    break;
                //sort by setting value
                case 4:
                    Collections.sort(listSort, new Comparator<SettingList>() {
                        @Override
                        public int compare(SettingList o1, SettingList o2) {
                            return o1.getValueString().compareTo(o2.getValueString());
                        }

                    });
                    settingListController.pageOfDisplay(listSort);
                    break;

                //sort by setting order
                case 5:
                    Collections.sort(listSort, new Comparator<SettingList>() {
                        @Override
                        public int compare(SettingList o1, SettingList o2) {
                            return Integer.toString(o1.getOrder()).compareTo(Integer.toString(o2.getOrder()));
                        }

                    });
                    settingListController.pageOfDisplay(listSort);
                    break;

                //sort by status
                case 6:
                    Collections.sort(listSort, new Comparator<SettingList>() {
                        @Override
                        public int compare(SettingList o1, SettingList o2) {
                            return Integer.toString(o1.getStatus()).compareTo(Integer.toString(o2.getStatus()));
                        }

                    });
                    settingListController.pageOfDisplay(listSort);
                    break;
                case 7:
                    return;
            }
        }

    }

    void showList() throws Exception {
        System.out.println("                                                    SETTING LIST");
        List<SettingList> listSetting = new ArrayList<>();
//        listSetting = settingListController.findDataByFilter(5, 3);
        listSetting = settingDAO.getList();
        settingListController.pageOfDisplay(listSetting);
        System.out.println("\n");

    }

    public static void displayHeader() {
        System.out.println("\nSYSTEM SETTING\n");
        System.out.println("\n1. Show Setting List \n2. Search setting \n3. Sort setting \n4. Add setting\n5. Change Status\n6. Update setting\n7. Return DashBoard\n");
    }

    public static void displaySettingFunction() throws ClassNotFoundException, SQLException, Exception {
        SettingListView view = new SettingListView();
        while (true) {
            displayHeader();
            int option = Utility.getInt("\nEnter option: ", "It must be digit", 1, 7);
            switch (option) {

                case 1:
                    view.showList();
                    break;
                case 2:
                    System.out.println("1. Search setting by filter    2. Type setting name to search");
                    int choice = Utility.getInt("Enter choice: ", "It must be number!!!", 1, 2);
                    if (choice == 1) {
                        view.searchSettingByFilter();
                    } else {
                        view.searchByName();
                    }
                    break;

                case 3:
                    view.sortSetting();
                    break;
                case 4:
                    view.addSetting();
                    break;
                case 5:
                    view.changeStatus();
                    break;
                case 6:
                    view.updateSettingList();
                    break;
                case 7:
                    return;

            }
        }
    }

//    public static void main(String[] args) throws SQLException, Exception {
//        displaySettingFunction();
//    }
    private void displaySortChoice() {
        System.out.println("                                                    SORT SETTING");
        System.out.println("1. Sort by settingID");
        System.out.println("2. Sort by settingType");
        System.out.println("3. Sort by settingName");
        System.out.println("4. Sort by value");
        System.out.println("5. Sort by order");
        System.out.println("6. Sort by status");
        System.out.println("7. Return");
    }

}
