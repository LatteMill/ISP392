/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.*;
import dao.*;
import java.sql.*;
import java.util.*;
import model.*;
import static view.Utility.scanner;

/**
 *
 * @author admin
 */
public class UserListView {

    UserListController userListController = new UserListController();
    static UserListDAO userListDAO = new UserListDAO();
    static UserDAO userDAO = new UserDAO();

    void addUserList(UserList st) throws ClassNotFoundException, SQLException, Exception {
        userListController.addUserList(st);
        userListDAO.insertUserListToList(st);
    }

    //email, fullName, rollNumber, mobile, sex, roles, status, jobPosition, company,address,last_login
    void updateUserList(UserList usl) throws ClassNotFoundException, SQLException {
        userListDAO.updateUserListToList(usl, "email", usl.getEmail(), "string");
        userListDAO.updateUserListToList(usl, "fullName", usl.getFullName(), "string");
        userListDAO.updateUserListToList(usl, "rollNumber", usl.getEmail(), "string");
        userListDAO.updateUserListToList(usl, "mobile", usl.getMobile(), "string");
        userListDAO.updateUserListToList(usl, "sex", usl.getSex() + "", "int");
        userListDAO.updateUserListToList(usl, "roles", usl.getRoles() + "", "int");
        userListDAO.updateUserListToList(usl, "status", usl.getStatus() + "", "int");
        userListDAO.updateUserListToList(usl, "jobPosition", usl.getJobPosition(), "string");
        userListDAO.updateUserListToList(usl, "company", usl.getCompany(), "string");
        userListDAO.updateUserListToList(usl, "address", usl.getAddress(), "string");
        userListDAO.updateUserListToList(usl, "last_login", usl.getLastLogin(), "string");

    }

    void addUserDetails() throws Exception {
        System.out.println("                                                    ADD USER TO USER LIST");
        List<UserList> sbList = userListDAO.getList();
        //System.out.println("Email: " + user.getEmail());
        int sexual, roles, status;
        String fullName, email, phoneString, jobPosition, company, address, last_login,rollNumber;

        System.out.println("\n========================User Details========================\n");
        fullName = Utility.getString("Full Name: ", "It must be string!!!", Utility.REGEX_NAME);
        email = Utility.getString("Email: ", "Wrong format", Utility.REGEX_EMAIL);
        rollNumber= Utility.getString("rollNumber: ", "Wrong format", Utility.REGEX_STRING);
        sexual = Utility.getInt("1/Male   2/Female    3/Others \nSex: ", "It must be number", 1, 3);
        phoneString = Utility.getString("Mobile: ", "It must be phoneString", Utility.REGEX_PHONE);
        roles = Utility.getInt("1. Admin    2.Author/Manager     3. Trainer    4.Student \nRoles: ", "It must be string", 1, 4);
        jobPosition = Utility.getString("Job opposite: ", "It must be string", Utility.REGEX_NAME);
        status = Utility.getInt("1/Active    2/Blocked      3/Pending\nStatus: ", "It must be number", 1, 3);
        company = Utility.getString("Company: ", "It must be string", Utility.REGEX_NAME);
        address = Utility.getString("Address: ", "It must be string", Utility.REGEX_STRING);
        last_login = " ";
        UserList user = new UserList(fullName, email,rollNumber, phoneString, sexual, roles, jobPosition, status, company, address, last_login);
        if (userListController.checkDuplicate(phoneString, email) == false) {
            sbList.add(user);
            userListDAO.insertUserListToList(user);
            System.out.println("\nADD SUCCESSFULL");
            List<UserList> list= userListDAO.getList();
            for (int i = 0; i < list.size(); i++) {
                if(i== list.size()-1){
                    displayUserDetails(list.get(i));
                }

            }

        } else {
            System.out.println("\nADD FAIL- DUPLICATE PHONE/ EMAIL!!!");
        }

        System.out.println("\n\n");
    }

    void updateUserList() throws ClassNotFoundException, SQLException, Exception {
        int id, sex, roles, status;
        String fullName, phoneString, jobPosition, company, address;
        id = Utility.getID();
        UserList user = userListController.getUserListByID(id);
        if (user == null) {
            System.out.println("NOT FOUND ID");
            return;
        }
        displayUserDetails(user);
//        userListController.displayBorder();
//        System.out.println(user);
        System.out.println("\n**PRESS ENTER TO UNCHANGE DATA**");
        fullName = Utility.getStringNull("Enter Full Name: ", user.getFullName());
        sex = Utility.getIntNull("1/Nam   2/Nữ    3/Chưa rõ\nSex: ", user.getSex(), 1, 3);
        phoneString = Utility.getStringNull("Enter mobile: ", user.getMobile());
        roles = Utility.getIntNull("1. Admin    2.Author/Manager     3. Trainer    4.Student \nRoles: ", user.getRoles(), 1, 4);
        jobPosition = Utility.getStringNull("Enter job opposite: ", user.getJobPosition());
        company = Utility.getStringNull("Company: ", user.getCompany());
        address = Utility.getStringNull("Address: ", user.getAddress());
        status = Utility.getIntNull("Status:    1/ Active    2/ Blocked     3/ Pending\nEnter Status:  ", user.getStatus(), 1, 2);
//        //check update full name, true => update
//        if (Utility.checkWantToUpdate("Full Name") == true) {
//            fullName = Utility.getString("Enter Full Name: ", "It must be string", Utility.REGEX_NAME);
//        } else {
//            fullName = user.getFullName();
//        }
//        //check update gender, true => update
//        if (Utility.checkWantToUpdate("Sex") == true) {
//            sex = Utility.getInt("1/Nam   2/Nữ    3/Chưa rõ\nSex: ", "It must be number", 1, 3);
//        } else {
//            sex = user.getSex();
//        }
//        //check update order, true => update
//        if (Utility.checkWantToUpdate("Moblie") == true) {
//            phoneString = Utility.getString("Enter mobile: ", "It must be number", Utility.REGEX_PHONE);
//        } else {
//            phoneString = user.getMobile();
//        }
//        //check update roles, true => update
//        if (Utility.checkWantToUpdate("Roles") == true) {
//            roles = Utility.getInt("1. Admin    2.Author/Manager     3. Tester    4.Student \nRoles: ", "It must be string", 1, 3);
//        } else {
//            roles = user.getRoles();
//        }
//
//        //check update job opsition, true => update
//        if (Utility.checkWantToUpdate("Job Oposition") == true) {
//            jobPosition = Utility.getString("Enter job opposite: ", "It must be string", Utility.REGEX_NAME);
//        } else {
//            jobPosition = user.getJobPosition();
//        }
//        //check update company, true => update
//        if (Utility.checkWantToUpdate("Company") == true) {
//            company = Utility.getString("Company: ", "It must be string", Utility.REGEX_NAME);
//        } else {
//            company = user.getCompany();
//        }
//        //check update address, true => update
//        if (Utility.checkWantToUpdate("Address") == true) {
//            address = Utility.getString("Address: ", "It must be string", Utility.REGEX_NAME);
//        } else {
//            address = user.getAddress();
//        }
//
//        //check update status, true => update
//        if (Utility.checkWantToUpdate("Status") == true) {
//            status = Utility.getStatus();
//        } else {
//            status = user.getStatus();
//        }

        if (userListController.checkNotUpdate(fullName, sex, phoneString, roles, jobPosition, company, address, status, user) == true) {
            System.out.println("YOU NOT UPDATE !!!");
        } else if (userListController.checkDuplicatePhone(phoneString, user.getEmail(), user) == true) {
            System.out.println("UPDATE FAIL- DUPLICATE PHONE");
        } else {
            user.setFullName(fullName);
            user.setSex(sex);
            user.setMobile(phoneString);
            user.setRoles(roles);
            user.setJobPosition(jobPosition);
            user.setCompany(company);
            user.setAddress(address);
            user.setStatus(status);
            user.setAction(status);
            updateUserList(user);
            //userListController.updateUserList(fullName, phoneString, company, address, roles, status, st);
            updateUserList(user);
            System.out.println("UPDATE SUCCESSFULL");
            displayUserDetails(user);

        }
        System.out.println("\n\n");
    }

    void changeStatus() throws ClassNotFoundException, SQLException, Exception {
        System.out.println("                                                    CHANGE USER STATUS");
        int id = Utility.getID();
        UserList user = userListController.getUserListByID(id);
        if (user == null) {
            System.out.println("NOT FOUND ID\n");
            return;
        }
        System.out.println("BEFORE CHANGE STATUS");
        userListController.displayBorder();
        System.out.println(user);
        if (user.getStatus() == 1) {
            user.setStatus(2);
            user.setAction(1);
        } else if (user.getStatus() == 2) {
            user.setStatus(1);
            user.setAction(2);
        } else {
            user.setStatus(1);
            user.setAction(3);
        }
        System.out.println("SUCCESSFULL");
        System.out.println("AFTER CHANGE STATUS");
        updateUserList(user);
        userListController.displayBorder();
        System.out.println(user);
        System.out.println("\n\n");
    }

    void searchByName() throws Exception {
        UserListView view = new UserListView();
        int choice;
        String name = null;

        choice = Utility.getInt("1/Search by name    2/ Search by mobile     3/ Search by email\nEnter choice: ", "It must be number", 1, 3);
        if (choice == 1) {
            name = Utility.getString("Enter name: ", "It must be string!!", Utility.REGEX_NAME);
        } else if (choice == 2) {
            name = Utility.getString("Enter mobile: ", "It must be string!!", Utility.REGEX_PHONE);
        } else {
            name = Utility.getString("Enter email: ", "It must be string!!", Utility.REGEX_EMAIL);
        }
        List<UserList> listUser = new ArrayList<>();
//        List<UserList> listUser = userListDAO.getList();
        listUser = userListController.findUserListByStringName(name);
        if (listUser == null) {
            System.out.println("NOT FOUND !!");
            return;
        } else {
            userListController.pageOfDisplay(listUser);
//            view.editUser();
        }
        System.out.println("\n\n");
    }

    public static void displayFeature() {
        System.out.println("                                                    USER LIST");
        System.out.println("________________________________________________________________________________________");
        System.out.println("1. Search by filter");
        System.out.println("--Roles        1. Admin    2.Author/Manager     3. Trainer    4.Student     5.All roles");
        System.out.println("--Status       1. Active   2. Blocked           3. Pending   4. All status");
        System.out.println("________________________________________________________________________________________");

    }

    public static void displayOption() {
        System.out.println("\n                                                  USER LIST\n");
        System.out.println("1. Show user list");
        System.out.println("2. Search by filter");
        System.out.println("3. Sort user list");
        System.out.println("4. Change status User Details");
        System.out.println("5. Update User Details");
        System.out.println("6. Add User Details");
        System.out.println("7. Back to DashBoard\n");
    }

    void searchUserByFilter() throws Exception {
        displayFeature();
        int choiceRoles = Utility.getInt("Enter roles: ", "It must be digit", 1, 5);
        int choiceStatus = Utility.getInt("Enter status: ", "It must be digit", 1, 4);
//        List<UserList> listUser = userListDAO.getList();
        List<UserList> listUser = new ArrayList<>();
        listUser = userListController.findDataByFilter(choiceRoles, choiceStatus);
        if (listUser == null) {
            System.out.println("NOT FOUND !!");
        } else {
            userListController.pageOfDisplay(listUser);
//            view.editUser();
        }
        System.out.println("\n\n");
    }

    void showList() throws Exception {
        List<UserList> listSetting = new ArrayList<>();
        System.out.println("                                                    USER LIST");
        listSetting = userListController.findDataByFilter(5, 4);
        userListController.pageOfDisplay(listSetting);
        System.out.println("\n");

    }

    static void displayUserListFunction() throws Exception {
        UserListView view = new UserListView();

        while (true) {
            displayOption();
            int option = Utility.getInt("Enter option: ", "It must be digit", 1, 8);
            switch (option) {
                case 1:
                    view.showList();
                    break;
                case 2:
                    System.out.println("1. Search user by filter    2. Type name to search");
                    int choice = Utility.getInt("Enter choice: ", "It must be number!!!", 1, 3);
                    if (choice == 1) {
                        view.searchUserByFilter();
                    }
                    if (choice == 2) {
                        view.searchByName();
                    }
                    break;
                case 3:
                    view.sortUserList();
                    break;
                case 4:
                    view.changeStatus();
                    break;
                case 5:
                    view.updateUserList();
                    break;
                case 6:
                    view.addUserDetails();
                    break;
                case 7:
                    return;

            }
        }
    }

    public static void main(String[] args) throws Exception {
        displayUserListFunction();
    }



    public static void displaySortScreen() {
        System.out.println("                                                    SORT USER LIST");
        System.out.println("1. Sort by user ID");
        System.out.println("2. Sort by fullName");
        System.out.println("3. Sort by role");
        System.out.println("4. Sort by status");

    }

    private void sortUserList() throws Exception {

        List<UserList> listSort = userListDAO.getList();
        displaySortScreen();
        int choice = Utility.getInt("Enter choice: ", "It must be number", 1, 4);
        switch (choice) {
            //sort by id
            case 1:
                //Loop run from the first to last User of array 
                for (int i = 0; i < listSort.size(); i++) {

                    /*Loop run from first element to the 
            element stand before the last unsorted element */
                    for (int j = 0; j < listSort.size() - i - 1; j++) {

                        //Compare each pair adjacent elements
                        if (listSort.get(j).getId() > listSort.get(j + 1).getId()) {
                            UserList temp = listSort.get(j);
                            listSort.set(j, listSort.get(j + 1));
                            listSort.set(j + 1, temp);

                        }
                    }
                }
                userListController.pageOfDisplay(listSort);
//                Collections.sort(listSort, new Comparator<UserList>() {
//
//                    @Override
//                    public int compare(UserList o1, UserList o2) {
//                        return Integer.toString(o1.getId()).compareTo(Integer.toString(o2.getId()));
//                    }
//
//                });
//                userListController.pageOfDisplay(listSort);
                break;
            //sort by name
            case 2:
                Collections.sort(listSort, new Comparator<UserList>() {
                    @Override
                    public int compare(UserList o1, UserList o2) {
                        return o1.getFullName().compareTo(o2.getFullName());
                    }
                });
                userListController.pageOfDisplay(listSort);
                break;
            //sort by roles
            case 3:
                Collections.sort(listSort, new Comparator<UserList>() {
                    @Override
                    public int compare(UserList o1, UserList o2) {
                        return o1.getRolesDetail().compareTo(o2.getRolesDetail());
                    }
                });
                userListController.pageOfDisplay(listSort);
                break;
            //sort by status
            case 4:
                Collections.sort(listSort, new Comparator<UserList>() {
                    @Override
                    public int compare(UserList o1, UserList o2) {
                        return o1.getStatusString().compareTo(o2.getStatusString());
                    }
                });
                userListController.pageOfDisplay(listSort);
                break;
        }
    }

    private void displayUserDetails(UserList user) {
        System.out.println("===========================================================================================================================================");
        System.out.format("%-5s|%-20s|%-30s|%-10s|%-15s|%-7s|%-12s|%-10s|%-20s|%-5s\n", "ID", "Name", "Email", "Mobile", "Role", "Sex", "jobOpposite", "Company", "Address", "Status");
        System.out.println("===========================================================================================================================================");
        System.out.println(String.format("%-5s|%-20s|%-30s|%-10s|%-15s|%-7s|%-12s|%-10s|%-20s|%-5s", user.getId(), user.getFullName(), user.getEmail(),
                user.getMobile(), user.getRolesDetail(), user.getSexResult(), user.getJobPosition(), user.getCompany(), user.getAddress(), user.getStatusString()));

    }
}
