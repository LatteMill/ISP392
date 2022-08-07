/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.*;
import java.util.*;
import model.*;
import view.Utility;

/**
 *
 * @author admin
 */
public class UserListController {
    //Main code owner of this class: Anh
    //Have function add in this class: Linh ( cho Subject )

    static UserListDAO urdao = new UserListDAO();
    static UserDAO udao = new UserDAO();
    
    public UserList getUserListByID(int id) throws Exception {
        List<UserList> listUser = urdao.getList();
        //loop run from first to last element of listUserLists
        for (UserList st : listUser) {
            //if duplicate id => return SettingList has existed
            if (st.getId() == id) {
                return st;
            }
        }
        return null;
    }
    
    public UserList getUserListByName(String name) throws Exception {
        List<UserList> listUser = urdao.getList();
        //loop run from first to last element in listUserLists
        for (UserList url : listUser) {
            if (url.getFullName().toUpperCase().contains(name.toUpperCase())) {
                return url;
            }
        }
        return null;
    }
    
    public List<UserList> findUserListByStringName(String name) throws Exception {
        List<UserList> listUser = urdao.getList();
        List<UserList> listSearch = new ArrayList<>();
        //loop run from first to last element in listUserLists
        for (UserList url : listUser) {
            //compare name to find UserList by name then add to listSearch
            if (url.getFullName().toUpperCase().contains(name.toUpperCase())
                    || url.getMobile().equalsIgnoreCase(name)
                    || url.getEmail().equalsIgnoreCase(name)) {
                listSearch.add(url);
                
            }
        }
        //System.out.println(listSearch);
        return listSearch.isEmpty() ? null : listSearch;
        
    }
    
    public List<UserList> findDataByFilter(int choiceRoles, int status) throws Exception {
        List<UserList> listUser = urdao.getList();
        List<UserList> listSearch = new ArrayList<>();
        //search all role and status
        if (choiceRoles == 5 && status == 4) {
            listSearch = listUser;
        } else if (choiceRoles == 5 && status != 4) {
            for (UserList userList : listUser) {
                if (userList.getStatus() == status) {
                    listSearch.add(userList);
                }
            }
        } else if (choiceRoles != 5 && status == 4) {
            for (UserList userList : listUser) {
                if (userList.getRoles() == choiceRoles) {
                    listSearch.add(userList);
                }
            }
        } else {
            for (UserList userList : listUser) {
                if (userList.getRoles() == choiceRoles && userList.getStatus() == status) {
                    listSearch.add(userList);
                }
            }
        }
        
        return listSearch.isEmpty()
                ? null : listSearch;
    }
    
    public void addUserList(UserList url) throws Exception {
        List<UserList> listUser = urdao.getList();
        listUser.add(url);
    }
    
    public boolean checkDuplicate(String phoneString, String email) throws Exception {
        List<UserList> listUser = urdao.getList();
        //loop from first to last element to check duplicate
        for (UserList url : listUser) {
            if (url.getEmail().equalsIgnoreCase(email) || url.getMobile().equalsIgnoreCase(phoneString)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkDuplicatePhone(String phoneString, String email, UserList st) throws Exception {
        List<UserList> listUser = urdao.getList();
        //loop from first to last element to check duplicate
        for (UserList url : listUser) {
            if (st.getId() == url.getId() && st.getMobile().equalsIgnoreCase(phoneString)) {
                return false;
            } else if (url.getMobile().equalsIgnoreCase(phoneString)) {
                return true;
            }
        }
        return false;
    }
    
    public void displayBorder() {
        System.out.println("===========================================================================================================================================");
        System.out.format("%-5s|%-20s|%-30s|%-20s|%-15s|%-20s|%-10s|%-10s\n", "ID", "Name", "Email", "Mobile", "Role", "Last Login", "Status", "Action");
        System.out.println("===========================================================================================================================================");
        
    }
    
    public void showList(List<UserList> sList) throws Exception {
        
        if (sList.size() > 0) {
            System.out.println("===========================================================================================================================================");
            System.out.format("%-5s|%-20s|%-30s|%-20s|%-15s|%-20s|%-10s|%-10s\n", "ID", "Name", "Email", "Mobile", "Role", "Last Login", "Status", "Action");
            System.out.println("===========================================================================================================================================");
            
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
    
    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Back.");
    }
    
    public void pageOfDisplay(List<UserList> listDisplay) {
        int showChoice = 0;
        int page = 1;
        while (showChoice != 4) {
            int maxpage = listDisplay.size() / 5;
            if (listDisplay.size() % 5 != 0) {
                maxpage++;
            }
            try {
                System.out.println("There are "+maxpage+ " pages.");
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
    
    public List<UserList> pagination(List<UserList> list, int page) throws Exception {
        //set up list 5 để trả về
        List<UserList> userList = new ArrayList<>();
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
                        userList.add(list.get(i));
                    }
                }
                i++;
            } while (i < start + 5);
            
            return userList;
            
        } else {
            throw new Exception("Can't not go to that page");
        }
        
    }

    //========================ham cua Linh cho subject====================================
    public static boolean checkExistUserByID(List<User> slist, int id) throws Exception {
        
        for (int i = 0; i < slist.size(); i++) {
            if (slist.get(i).getId() == id) {
                return true; //exist
            }
        }
        return false; //not exist
    }
    
    public static User searchUserByID(int id) throws Exception {
        List<User> lu = udao.getList();
        for (User user : lu) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    
    public int searchUserIDbyEmailInSublist(List<User> a, String email) throws Exception {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getEmail().compareToIgnoreCase(email) == 0) {
                return a.get(i).getId();
            }
        }
        return -1;
    }
    //==============================================================================

    public boolean checkNotUpdate(String fullName, int sex, String phoneString, int roles, String jpbOpString, String company, String address, int status, UserList user) throws Exception {
        List<UserList> listUser = urdao.getList();
        for (UserList url : listUser) {
            //check not update
            if (url.getId()== user.getId()
                    &&url.getFullName().equalsIgnoreCase(fullName)
                    && url.getSex() == sex
                    && url.getMobile().equalsIgnoreCase(phoneString)
                    && url.getRoles() == roles
                    && url.getJobPosition().equalsIgnoreCase(jpbOpString)
                    && url.getCompany().equalsIgnoreCase(company)
                    && url.getAddress().equalsIgnoreCase(address)
                    && url.getStatus() == status) {
                return true;
            }
            
        }
        return false;
    }
    // cua Hoi nhe!!!!

    public int searchUserIDbyEmail(String email) throws Exception {
        List<User> lu = udao.getList();
        for (int i = 0; i < lu.size(); i++) {
            if (lu.get(i).getEmail().compareToIgnoreCase(email) == 0) {
                return lu.get(i).getId();
            }
        }
        return -1;
    }
    
    public boolean checkDuplicate(String fullName, int sex, String phoneString, int roles, String jobPosition, String company, String address, int status) throws Exception {
        List<UserList> listUser = urdao.getList();
        for (UserList url : listUser) {
            if (url.getFullName().equalsIgnoreCase(fullName)
                    && url.getSex() == sex
                    && url.getMobile().equalsIgnoreCase(phoneString)
                    && url.getRoles() == roles
                    && url.getJobPosition().equalsIgnoreCase(jobPosition)
                    && url.getCompany().equalsIgnoreCase(company)
                    && url.getAddress().equalsIgnoreCase(address)
                    && url.getStatus() == status) {
                return true;
            }
        }
        return false;
    }
    
}
