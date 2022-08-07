/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import dao.FunctionDAO;

import java.util.ArrayList;

import dao.TeamDAO;
import dao.FeatureDAO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;
import java.util.Scanner;
import model.Feature;
import model.Function;
import model.Team;
import view.Utility;


public class FunctionController {
static FunctionDAO functionDAO = new FunctionDAO();
    static TeamController teamController = new TeamController();
    static TeamDAO teamDAO = new TeamDAO();
    static FeatureDAO featureDAO = new FeatureDAO();
    static FeatureController featureController = new FeatureController();

    public void showList(List<Function> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("============================= List ===============================");
            System.out.println(String.format("%-10s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-10s", "Function ID", "Team_Name", "Function_name", "Feature_Name", "Access_roles",
                    "Complexity_id", "Owner_ID", "Priority", "Status"));
            System.out.println("==================================================================");

            for (int i = 0; i < sList.size(); i++) {
                System.out.println(sList.get(i));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

    //check Function ton tai chua? 
    public static boolean checkFunctionExist(Function f) throws Exception {
        List<Function> fList = functionDAO.getFuncList();

        if (fList.size() > 0) {
            for (int i = 0; i < fList.size(); i++) {
                if (fList.get(i).getTeam_id() == f.getTeam_id()
                        && fList.get(i).getFeature_id() == f.getFeature_id()
                        && fList.get(i).getFunction_name().compareToIgnoreCase(f.getFunction_name()) == 0) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //check function ton tai chua? 
    public static boolean checkFunctionExistByID(List<Function> fList, int id) throws Exception {
        List<Function> sbList = functionDAO.getFuncList();

        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getFunction_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }
 public static void addNewFunction(Function f) throws Exception {
        List<Function> fList = functionDAO.getFuncList();
       if (!checkFunctionExist(f)) {
        //check Team ID da ton tai:
        if (teamController.checkTeamExistByID(teamDAO.getListWithOneCondition(" status = 1 "), f.getTeam_id())) {
            //check feature da ton tai:
            if(featureController.checkFeatureExistByID(featureDAO.getListWithOneCondition(" status = 1 "), f.getFeature_id())) {
            fList.add(f);
            functionDAO.insertFuncToList(f);
            System.out.println("Add successfully!");
            //    } else {
            //        throw new Exception("From_date must be before To_date!");
        } else {
            throw new Exception("Enter Feature ID in list showed only!");
        }
        }else {
            throw new Exception("Enter Team ID in list showed only!");
        }
       }else {
            throw new Exception("Exist Function!");
        }
    }
 
    //them function moi
    public static void addFunction(List<Function> l, List<Team> teamList, List<Feature> featureList, Function f) throws Exception {

        
        if (!checkFunctionExist(f)) {
            //check Team ID da ton tai:
            if (teamController.checkTeamExistByID(teamList, f.getTeam_id())) {
                //check Feature ID da ton tai:
                if (featureController.checkFeatureExistByID(featureList, f.getFeature_id())) {
                    l.add(f);
                    functionDAO.insertFuncToList(f);
                    System.out.println("Add successfully!");
                    //    } else {
                    //        throw new Exception("From_date must be before To_date!");
                } else {
                    throw new Exception("Enter Team ID in list showed only!");
                }
            }else{
                throw new Exception("Enter Feature ID in list showed only!");
            }

        } else {
            throw new Exception("Exist Function!");
        }
    }
    //find Function name
    public static int searchFunctionIndexByName(String name) throws Exception {
        List<Function> fList = functionDAO.getFuncList();
        if (fList.size() > 0) {
            for (int i = 0; i < fList.size(); i++) {
                if (fList.get(i).getFunction_name().contains(name)) {
                    return i;
                }
            }
        }
        return -1;
    }

    //find function index
    public static int searchFunctionIndexByID(int id) throws Exception {
        List<Function> fList = functionDAO.getFuncList();
        if (fList.size() > 0) {
            for (int i = 0; i < fList.size(); i++) {
                if (fList.get(i).getFunction_id() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    //find Feature
    public static Function searchFunctionByName(String name) throws Exception {
        List<Function> fList = functionDAO.getFuncList();
        if (fList.size() > 0) {
            for (int i = 0; i < fList.size(); i++) {
                if (fList.get(i).getFunction_name().contains(name)) {
                    return fList.get(i);
                }
            }
        }
        return null;
    }

    public static Function searchFunctionByID(int id) throws Exception {
        List<Function> fList = functionDAO.getFuncList();
        if (fList.size() > 0) {
            for (int i = 0; i < fList.size(); i++) {
                if (fList.get(i).getFunction_id() == id) {
                    return fList.get(i);
                }
            }
        }
        return null;
    }
    public static Function filterFunctionByTeamName(String name) throws Exception {
        List<Function> fList = functionDAO.getFuncList();
        if (fList.size() > 0) {
            for (int i = 0; i < fList.size(); i++) {
                if (fList.get(i).getTeam_name() == name) {
                    return fList.get(i);
                }
            }
        }
        return null;
    }
    public static void updateFunction(List<Function> l, List<Team> teamList, List<Feature> featureList, Function f) throws Exception {

        if (checkFunctionExistByID(l, f.getFunction_id())) {
            //check Team ID da ton tai:
            if (teamController.checkTeamExistByID(f.getTeam_id())) {
                //check Milestone ID da ton tai
                if (featureController.checkFeatureExistByID(f.getFeature_id())) {

                    l.set(searchFunctionIndexByID(f.getFunction_id()), f);
                    functionDAO.updateFunctionToList(f);

                    
                } else {
                    throw new Exception("Enter Feature ID in list showed only!");
                }
            } else {
                throw new Exception("Enter Team ID in list showed only!");
            }
        } else {
            throw new Exception("Can NOT find Function ID!");
        }
    }

    
    
    
    public static void changeFunctionStatus(List<Function> fList, int id, int option) throws Exception {

        if (checkFunctionExistByID(fList, id)) {
            try {
                Function f = searchFunctionByID(id);
                f.setStatus(option);
                functionDAO.updateFunctionToList(f);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new Exception("Cannot change status!");
        }
    }

    //seach name(1), status(2)
    public List<Function> searchByPattern(String pattern, int choice) throws Exception {
        List<Function> fList = functionDAO.getFuncList();
        List<Function> fl = new ArrayList<>();
        //chuyển tất cả về toLowerCase
        pattern = pattern.toUpperCase();
        switch (choice) {
            case 1: //team_name
                try {
                   
                   // int numberPattern = Integer.parseInt(pattern);
                    for (int i = 0; i < fList.size(); i++) {
                        if (fList.get(i).getTeam_name() == pattern) {
                            fl.add(fList.get(i));
                        }
                    }
                } catch (Exception e) {
                    throw new Exception("Wrong!");
                }
                break;
            case 2://feature
                
            case 3: //status
            {
                if (pattern.toUpperCase().compareTo("1") == 0) { //1. Pending,   
                    for (int i = 0; i < fList.size(); i++) {
                        if (fList.get(i).getStatus() == 1) {
                            fl.add(fList.get(i));
                        }
                    }
                } else {
                    if (pattern.toUpperCase().compareTo("2") == 0) {//2.  Planned,
                        for (int i = 0; i < fList.size(); i++) {
                            if (fList.get(i).getStatus() == 2) {
                                fl.add(fList.get(i));
                            }
                        }
                    }
                else {
                        if (pattern.toUpperCase().compareTo("3") == 0) {//3.Evaluated,

                            for (int i = 0; i < fList.size(); i++) {
                                if (fList.get(i).getStatus() == 3) {
                                    fl.add(fList.get(i));
                                }
                            }
                        } else {
                            if (pattern.toUpperCase().compareTo("4") == 0) {//4.Rejected
                                
                                    for (int i = 0; i < fList.size(); i++) {
                                        if (fList.get(i).getStatus() == 4) {
                                            fl.add(fList.get(i));
                                        }
                                    }                              
                            }else {
                            if (pattern.toUpperCase().compareTo("5") == 0) {//5.exit
                                
                                    for (int i = 0; i < fList.size(); i++) {
                                        if (fList.get(i).getStatus() == 5) {
                                            fl.add(fList.get(i));
                                        }
                                    } 
                            
                            }else {
                                throw new Exception("Choose in list menu only!");
                            }
                        }
                    }
                   
                }
            } break;
            } 
        } return fl;
    }
        //sap xep feature
        public List<Function> sortFunction(boolean student, int user_id, int choice) throws Exception {
        //   List<Feature> sl = functionDAO.getFuncList();
        switch (choice) {
            case 1: //so sánh name
                return functionDAO.getListForSort(student, user_id, "function_name");
            //    break;
            case 2: //so sanh team_id
                return functionDAO.getListForSort(student, user_id, "team_id");
            case 3: // so sanh feature_id
                return functionDAO.getListForSort(student, user_id, "feature_id");
            case 4: //so sanh status
                return functionDAO.getListForSort(student, user_id, "status");
            //nếu nhập khác thì exit sort
            default:
                System.out.println("Exit sort.");
                break;
        }

        return null;
    }
    

    public static boolean checkFeatureExistByID(List<Feature> c, int id) throws Exception {
        if (c.size() > 0) {
            for (int i = 0; i < c.size(); i++) {
                if (c.get(i).getFeature_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai

    }

    public List<Function> pagination(List<Function> list, int page) throws Exception {
        //set up list 5 để trả về
        List<Function> fList = new ArrayList<>();
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
                        fList.add(list.get(i));
                    }
                }
                i++;
            } while (i < start + 5);

            return fList;

        } else {
            throw new Exception("Can't not go to that page");
        }
    }
    //====================HAM CUA ANH CHO TRACKING===========================

    public List<Function> showActiveFunctionList(List<Function> sList, int teamID) throws Exception {
        List<Function> functionActive = new ArrayList<>();
        System.out.println("\nAVAILABLE FUNCTION");
        if (sList.size() > 0) {
           
            for (int i = 0; i < sList.size(); i++) {
                if (sList.get(i).getStatusString().equalsIgnoreCase("Pending")) {
                    functionActive.add(sList.get(i));
                }

            }
            pageOfDisplay(functionActive);
        } else {
            throw new Exception("Empty List!");
        }
        return functionActive;
    }

    public void showListForTracking(List<Function> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("====================================== Function List =======================================");
            System.out.println(String.format("%-15s|%-10s|%-20s|%-15s", "Function ID", "TeamID", "Function Name", "Status"));
            System.out.println("---------------------------------------------------------------------------------------------");

            for (int i = 0; i < sList.size(); i++) {
                System.out.println(String.format("%-15s|%-10s|%-20s|%-15s",
                        sList.get(i).getFunction_id(),
                        sList.get(i).getTeam_id(),
                        sList.get(i).getFunction_name(), 
                        sList.get(i).getStatusString()));
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

    public void pageOfDisplay(List<Function> listDisplay) {
        int showChoice = 0;
        int page = 1;
        while (showChoice != 4) {
            int maxpage = listDisplay.size() / 5;
            if (listDisplay.size() % 5 != 0) {
                maxpage++;
            }
            System.out.println("Total of page: " + maxpage);
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                showListForTracking(pagination(listDisplay, page));
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

    
    public boolean isFunctionIDExistInList(List<Function> functionActive, int functionID) {
        for (Function function : functionActive) {

            if (function.getFunction_id() == functionID) {
                return false;
            }
        }
        System.out.println("FUNCTION ID MUST BE SHOWED IN LIST ABOVE");
        return true;
    }
    //==========================================================================
    //=======================================================Hàm của Linh cho Issue ===========================================
    public boolean checkFunctionExistByID(int id) throws Exception {
        List<Function> functionList = functionDAO.getFuncList();
        for (int i = 0; i < functionList.size(); i++) {
            if (functionList.get(i).getFunction_id() == id) {
                return true; //tồn tại
            }
        }
        return false; //không tồn tại
    }
    

    public boolean checkExistArrayFunctionID(int a[]) throws Exception {
        for (int i = 0; i < a.length; i++) {
            if (!checkFunctionExistByID(a[i])) //nếu không tồn tại:
            {
                return false; //không thỏa mãn Array Funtions ID
            }
        }
        return true; //thỏa mãn
    }
    //=============================================================================
}

