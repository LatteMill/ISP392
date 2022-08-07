/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import view.*;

/**
 *
 * @author admin
 */
public class UserEvaluationController {

    static UserEvaluationDAO uEvalDAO = new UserEvaluationDAO();

    static CriteriaController criteriaController = new CriteriaController();
    static CriteriaDAO criteriaDAO = new CriteriaDAO();

    //iteration id
    static IterationController iterationController = new IterationController();
    static IterationDAO iterationDAO = new IterationDAO();

    static TeamDAO teamDAO = new TeamDAO();

    static ClassController classController = new ClassController();
    static TeamController teamController = new TeamController();

    static UserDAO userDAO = new UserDAO();

    static UserListController userListController = new UserListController();

    static public List<UserEvaluation> pagination(List<UserEvaluation> list, int page) throws Exception {
        //set up list 5 để trả về
        List<UserEvaluation> trackingList = new ArrayList<>();
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
                        trackingList.add(list.get(i));
                    }
                }
                i++;
            } while (i < start + 5);

            return trackingList;

        } else {
            throw new Exception("Can't not go to that page");
        }

    }

    public void displayHeader() {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("|%-5s|%-12s|%-12s|%-12s|%-7s|%-7s|%-7s|%-20s|%-10s|%-12s|%-12s|%-5s", "ID", "evaluationID", "criteriaID", "iteration", "class",
                "team", "userID", "fullName", "Loc", "coverted_loc", "totalGrade", "Notes");
        System.out.println("");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");

    }

    public void displayList(List<UserEvaluation> list) throws Exception {
        if (list.size() > 0) {
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("|%-5s|%-12s|%-12s|%-12s|%-7s|%-7s|%-7s|%-20s|%-10s|%-12s|%-12s|%-5s", "ID", "evaluationID", "criteriaID", "iteration", "class",
                    "team", "userID", "fullName", "Loc", "coverted_loc", "totalGrade", "Notes");
            System.out.println("");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
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

    public void pageOfDisplay(List<UserEvaluation> listDisplay) {
        int searchChoice = 0;
        int page = 1;
        while (searchChoice != 4) {
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
                searchChoice = Utility.getInt("Enter choice: ", "Wrong!", 1, 4);
                if (searchChoice == 1) {
                    page = page + 1;
                }
                if (searchChoice == 2) {
                    page = page - 1;
                }
                if (searchChoice == 3) {
                    page = Utility.getInt("Enter page to go: ", "Can NOT go to that page!", 1, maxpage);
                }
                if (0 > page || page > maxpage) {
                    System.out.println("Can NOT go to that page!");
                    page = Utility.getInt("Enter page: ", "Can NOT go to that page", 1, maxpage);
                }
            } while (0 > page || page > maxpage);
        }
    }

    public boolean showUserEvalByUserID(int user_id) throws Exception {
        List<UserEvaluation> uEvalList = uEvalDAO.getList();
        List<UserEvaluation> listSearch = new ArrayList<>();
        for (UserEvaluation u : uEvalList) {
            if (u.getUser_id() == user_id) {
                listSearch.add(u);
            }
        }
        if (listSearch.size() == 0) {
            System.out.println("Not found data...");
            return false;
        } else {
            System.out.println("Your Evaluation...");
            pageOfDisplay(listSearch);
        }
        return true;
    }

    public List<UserEvaluation> showUserEvalByClassID(int class_id) throws Exception {
        List<UserEvaluation> uEvalList = uEvalDAO.getList();
        List<UserEvaluation> listSearch = new ArrayList<>();
        for (UserEvaluation u : uEvalList) {
            if (u.getClass_id() == class_id) {
                listSearch.add(u);
            }
        }
        if (listSearch.size() == 0) {
            System.out.println("Empty List!!!");
            return null;
        } else {
            System.out.println("Your Class Evaluation...");
            pageOfDisplay(listSearch);
            return listSearch;
        }

    }

    public void searchByMember_eval_id(int class_id) throws Exception {
        int id = Utility.getInt("Enter ID: ", "WRONG!!!", 1, Integer.MAX_VALUE);
        List<UserEvaluation> uEvalList = uEvalDAO.getList();
        List<UserEvaluation> listSearch = new ArrayList<>();
        for (UserEvaluation u : uEvalList) {
            if (u.getMember_eval_id() == id
                    && u.getClass_id() == class_id) {
                listSearch.add(u);
            }
        }
        if (listSearch.size() == 0) {
            System.out.println("Empty List!!!");
        } else {
            pageOfDisplay(listSearch);
        }

    }

    public void searchByEvaluation_id(int class_id) throws Exception {
        int eval = Utility.getInt("Enter evaluationID: ", "WRONG!!!", 1, Integer.MAX_VALUE);
        List<UserEvaluation> uEvalList = uEvalDAO.getList();
        List<UserEvaluation> listSearch = new ArrayList<>();
        for (UserEvaluation u : uEvalList) {
            if (u.getEvaluation_id() == eval
                    && u.getClass_id() == class_id) {
                listSearch.add(u);
            }
        }
        if (listSearch.size() == 0) {
            System.out.println("Empty List!!!");
        } else {
            pageOfDisplay(listSearch);
        }

    }

    public void pageOfDisplayIterEval(List<UserEvaluation> listDisplay) {
        int searchChoice = 0;
        int page = 1;
        while (searchChoice != 4) {
            int maxpage = listDisplay.size() / 5;
            if (listDisplay.size() % 5 != 0) {
                maxpage++;
            }
            System.out.println("There are: " + maxpage + " pages.");
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                showListIterEval(pagination(listDisplay, page));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            do {
                displayChoiceForShowList();
                searchChoice = Utility.getInt("Enter choice: ", "Wrong!", 1, 4);
                if (searchChoice == 1) {
                    page = page + 1;
                }
                if (searchChoice == 2) {
                    page = page - 1;
                }
                if (searchChoice == 3) {
                    page = Utility.getInt("Enter page to go: ", "Can NOT go to that page!", 1, maxpage);
                }
                if (0 > page || page > maxpage) {
                    System.out.println("Can NOT go to that page!");
                    page = Utility.getInt("Enter page: ", "Can NOT go to that page", 1, maxpage);
                }
            } while (0 > page || page > maxpage);
        }
    }

    public void searchByCriteria_id(int class_id) throws Exception {
        int crite = Utility.getInt("Enter evaluationID: ", "WRONG!!!", 1, Integer.MAX_VALUE);
        List<UserEvaluation> uEvalList = uEvalDAO.getList();
        List<UserEvaluation> listSearch = new ArrayList<>();
        for (UserEvaluation u : uEvalList) {
            if (u.getCriteria_id() == crite
                    && u.getClass_id() == class_id) {
                listSearch.add(u);
            }
        }
        if (listSearch.size() == 0) {
            System.out.println("Empty List!!!");
        } else {
            pageOfDisplay(listSearch);
        }

    }

    public void searchUserEvalByName(int class_id) throws Exception {
        String name = Utility.getString("Enter name: ", "It must be string", Utility.REGEX_STRING);
        List<UserEvaluation> uEvalList = uEvalDAO.getList();
        List<UserEvaluation> listSearch = new ArrayList<>();
        for (UserEvaluation u : uEvalList) {
            if (u.getFullName().toUpperCase().contains(name.toUpperCase())
                    && u.getClass_id() == class_id) {
                listSearch.add(u);
            }
        }
        if (listSearch.isEmpty()) {
            System.out.println("Empty List!!!");
        } else {
            pageOfDisplay(listSearch);
        }

    }

    public void sortByMember_eval_id(List<UserEvaluation> uEvalList) throws Exception {
//        List<UserEvaluation> uEvalList = uEvalDAO.getList();

        //Loop run from the first to last Person of array 
        for (int i = 0; i < uEvalList.size(); i++) {

            /*Loop run from first element to the 
            element stand before the last unsorted element */
            for (int j = 0; j < uEvalList.size() - i - 1; j++) {

                //Compare each pair adjacent elements
                if (uEvalList.get(j).getMember_eval_id() > uEvalList.get(j + 1).getMember_eval_id()) {
                    UserEvaluation temp = uEvalList.get(j);
                    uEvalList.set(j, uEvalList.get(j + 1));
                    uEvalList.set(j + 1, temp);

                }
            }
        }
        pageOfDisplay(uEvalList);
    }

    public void sortByTotalGrade(List<UserEvaluation> uEvalList) throws Exception {
//        List<UserEvaluation> uEvalList = uEvalDAO.getList();

        //Loop run from the first to last Person of array 
        for (int i = 0; i < uEvalList.size(); i++) {

            /*Loop run from first element to the 
            element stand before the last unsorted element */
            for (int j = 0; j < uEvalList.size() - i - 1; j++) {

                //Compare each pair adjacent elements
                if (uEvalList.get(j).getTotalGrade() > uEvalList.get(j + 1).getTotalGrade()) {
                    UserEvaluation temp = uEvalList.get(j);
                    uEvalList.set(j, uEvalList.get(j + 1));
                    uEvalList.set(j + 1, temp);

                }
            }
        }
        pageOfDisplay(uEvalList);
    }

    public void sortByName(List<UserEvaluation> uEvalList) throws Exception {
//        List<UserEvaluation> uEvalList = uEvalDAO.getList();
        Collections.sort(uEvalList, new Comparator<UserEvaluation>() {
            @Override
            public int compare(UserEvaluation o1, UserEvaluation o2) {

                return o1.getFullName().compareTo(o2.getFullName());

            }
        });

        pageOfDisplay(uEvalList);
    }

    public void showList() throws Exception {
        System.out.println("                                                    Member Evaluation List");
        List<UserEvaluation> uEvalList = uEvalDAO.getList();
        pageOfDisplay(uEvalList);
    }

    public void sortByEvaluation_id(List<UserEvaluation> uEvalList) throws Exception {
//        List<UserEvaluation> uEvalList = uEvalDAO.getList();

        //Loop run from the first to last Person of array 
        for (int i = 0; i < uEvalList.size(); i++) {

            /*Loop run from first element to the 
            element stand before the last unsorted element */
            for (int j = 0; j < uEvalList.size() - i - 1; j++) {

                //Compare each pair adjacent elements
                if (uEvalList.get(j).getEvaluation_id() > uEvalList.get(j + 1).getEvaluation_id()) {
                    UserEvaluation temp = uEvalList.get(j);
                    uEvalList.set(j, uEvalList.get(j + 1));
                    uEvalList.set(j + 1, temp);

                }
            }
        }
        pageOfDisplay(uEvalList);
    }

    public void sortByCriteria_id(List<UserEvaluation> uEvalList) throws Exception {
//        List<UserEvaluation> uEvalList = uEvalDAO.getList();

        //Loop run from the first to last Person of array 
        for (int i = 0; i < uEvalList.size(); i++) {

            /*Loop run from first element to the 
            element stand before the last unsorted element */
            for (int j = 0; j < uEvalList.size() - i - 1; j++) {

                //Compare each pair adjacent elements
                if (uEvalList.get(j).getCriteria_id() > uEvalList.get(j + 1).getCriteria_id()) {
                    UserEvaluation temp = uEvalList.get(j);
                    uEvalList.set(j, uEvalList.get(j + 1));
                    uEvalList.set(j + 1, temp);

                }
            }
        }
        pageOfDisplay(uEvalList);
    }

    /*
    public void addNewMemberEval(int trainer_id, int classID) throws Exception {
        int evaluation_id;
        int criteria_id, max_loc, team_id;
        UserEvaluation newUser = new UserEvaluation();
        List<UserEvaluation> listUser= uEvalDAO.getListIterEvalWithClassID(classID);

        System.out.println("                                                    ADD NEW MEMBER EVALUATION \n\n");

        System.out.println("                                                    List User Iteration Evaluation");

        if (listUser.size() != 0) {
            pageOfDisplayIterEval(listUser);
            int choice = Utility.getInt("\nDo you want to add new member that showed in list above into MEMBER EVALUATION?\n 1- Yes    2- No\n Enter: ",
                    "It must be number!!!", 1, 2);
            //enter new value in table in database 
            if (choice == 1) {
                do {
                    evaluation_id = Utility.getInt("Enter evaluationID: ", "It must be number", 1, Integer.MAX_VALUE);

                } while (uEvalDAO.getListIterEvalWithTwoCondition(classID, evaluation_id) == null);
                newUser = uEvalDAO.getListIterEvalWithTwoCondition(classID, evaluation_id);
                int iteration_id = newUser.getIteration_id();
                String iterString = "iteration_id= " + iteration_id + " ";
                List<Criteria> listCriteIter = criteriaDAO.getCriteriaByID(iterString);

                if (!listCriteIter.isEmpty()) {
                    do {
                        System.out.println("Evaluation Criteria List avaiable: ");
                        CriteriaView.showCriteriaList(listCriteIter);
                        criteria_id = Utility.getInt("Enter Criteria ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                        if (!criteriaController.checkCriteriaExistByID(listCriteIter, criteria_id)) {
                            System.out.println("Must enter criteria ID in list showed.");
                        }
                        System.out.println("");
                        
                    } while (!criteriaController.checkCriteriaExistByID(listCriteIter, criteria_id));
                } else {
                    System.out.println("============================ Add New Criteria ============================");
                    double criteria_weight = Utility.getDouble("Enter criteria_weight: ", "Wrong.", Double.MIN_VALUE, Double.MAX_VALUE);
                    int team_criteria = newUser.getTeam_id();
                    int criteria_order = Utility.getInt("Enter criteria_order: ", "Wrong.", Integer.MIN_VALUE, Integer.MAX_VALUE);
                    max_loc = Utility.getInt("Enter max_loc: ", "Wrong.", Integer.MIN_VALUE, Integer.MAX_VALUE);
                    int status = 1;
                    List<Criteria> criteriaList = criteriaDAO.getList();
                    Criteria sb = new Criteria(iteration_id, criteria_weight, team_criteria, criteria_order, max_loc, status);
                    List<Iteration> iterList = iterationDAO.getListWithOneCondition(" status = 1 and iteration_id =  " + iteration_id);
//                    try {
//                        criteriaController.addNewCriteria(sb);
//                        System.out.println("Current criteria detail: ");
//                        CriteriaView.showCriteriaList(criteriaDAO.getList());
//                    } catch (Exception e) {
//                        System.out.println(e.getMessage());
//                    }

                }
                String notes = Utility.getStringNull("Enter notes: ", "");
//                uEvalDAO.insertUserEvaluationToList(newUser);
                if (isDuplicated(newUser)) {
                    System.out.println("DUPLICATED!!!");
                } else {
                    uEvalDAO.insertIterEvaluationsToList(newUser);
                    uEvalDAO.insertUserEvaluationToList(newUser);
                    System.out.println("ADD SUCESSFULLY!!!\n");
                }
                displayHeader();
                System.out.println(newUser);

            } else {
                //teamID
                List<Team> teamActive = teamController.showActiveTeamListWithClassID(teamDAO.getList(), classID);
                int teamID;

                do {
                    teamID = Utility.getInt("Enter availabe teamID: ", "It must be number", 1, Integer.MAX_VALUE);

                } while (teamController.isTeamIDExistInList(teamActive, teamID)
                        == true);

                //userID
                int userID;
                List<User> userList = userDAO.getList();

                do {
                    userID = Utility.getInt("Enter user ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                } while (UserListController.checkExistUserByID(userList, userID) == false);
                UserList u = userListController.getUserListByID(userID);

                System.out.println(u);
                String fullName = u.getFullName();
                do {
                    evaluation_id = Utility.getInt("Enter evaluationID: ", "It must be number", 1, Integer.MAX_VALUE);

                } while (uEvalDAO.getListIterEvalWithTwoCondition(classID, evaluation_id) == null);
                newUser = uEvalDAO.getListIterEvalWithTwoCondition(classID, evaluation_id);
                int iteration_id = newUser.getIteration_id();
                String iterString = "iteration_id= " + iteration_id + " ";
                List<Criteria> listCriteIter = criteriaDAO.getListWithOneCondition(iterString);

                if (!listCriteIter.isEmpty()) {
                    do {
                        System.out.println("Evaluation Criteria List avaiable: ");
                        CriteriaView.showCriteriaList(listCriteIter);
                        criteria_id = Utility.getInt("Enter Criteria ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                        if (!criteriaController.checkCriteriaExistByID(listCriteIter, criteria_id)) {
                            System.out.println("Must enter criteria ID in list showed.");
                        }
                        System.out.println("");
                    } while (!criteriaController.checkCriteriaExistByID(listCriteIter, criteria_id));
                } else {
                    System.out.println("============================ Add New Criteria ============================");
                    double criteria_weight = Utility.getDouble("Enter criteria_weight: ", "Wrong.", Double.MIN_VALUE, Double.MAX_VALUE);
                    int team_criteria = newUser.getTeam_id();
                    int criteria_order = Utility.getInt("Enter criteria_order: ", "Wrong.", Integer.MIN_VALUE, Integer.MAX_VALUE);
                    max_loc = Utility.getInt("Enter max_loc: ", "Wrong.", Integer.MIN_VALUE, Integer.MAX_VALUE);
                    int status = 1;
                    List<Criteria> criteriaList = criteriaDAO.getList();
                    Criteria sb = new Criteria(iteration_id, criteria_weight, team_criteria, criteria_order, max_loc, status);
                    List<Iteration> iterList = iterationDAO.getListWithOneCondition(" status = 1 and iteration_id =  " + iterationController.searchIterationByID(iteration_id).getIteration_id());
                    try {
                        criteriaController.addNewCriteria(sb);
                        System.out.println("Current criteria detail: ");
                        CriteriaView.showCriteriaList(criteriaDAO.getList());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
                System.out.println("\ntotalGrade= bonus+ grade+ conveted_loc\n");
                double bonus = Utility.getFloat("Bonus must round 1 number after '.'\nEnter bonus: ", "It must be double!!!", 0, 2);
                double grade = Utility.getFloat("Grade must round 1 number after '.'\nEnter grade: ", "It must be double!!!", 0, 10);
                String notes = Utility.getStringNull("Enter notes: ", "");
//                uEvalDAO.insertUserEvaluationToList(newUser);

            }
            if (isDuplicated(newUser)) {
                System.out.println("DUPLICATED!!!");
            } else {
                uEvalDAO.insertIterEvaluationsToList(newUser);
                uEvalDAO.insertUserEvaluationToList(newUser);
                System.out.println("ADD SUCESSFULLY!!!\n");
                displayHeader();
                System.out.println(newUser);
            }
        }

    }
    MODIFY USER EVALUATION
     */
    public List<UserEvaluation> getUserInIterationEval(int classID, int iteration_id) throws Exception {
        List<UserEvaluation> ulist = uEvalDAO.getList();
        List<UserEvaluation> listSearch = uEvalDAO.getListIterEvalWithTwoCondition(classID, iteration_id);
        List<UserEvaluation> listFound3 = new ArrayList<>();
        List<UserEvaluation> listFound4 = new ArrayList<>();
        //list1
//        System.out.println("Iter eval");
        for (UserEvaluation u : listSearch) {
//            System.out.println(u);
            listFound3.add(u);
        }
        //list2
//        System.out.println("User evaluation");
        for (UserEvaluation u : ulist) {
            if (u.getClass_id() == classID && u.getIteration_id() == iteration_id) {
//                System.out.println(u);
//                listFound.add(u);
                listFound3.add(u);
            }
        }
//        System.out.println("......");
//                for (UserEvaluation u : listFound3) {
//            System.out.println(u);
//        }
        int check = 0;
        for (int i = 0; i < listFound3.size(); i++) {
            for (int j = 0; j < listFound3.size(); j++) {
                if (listFound3.get(i).getUser_id() == listFound3.get(j).getUser_id() && i != j) {
                    check += 1;
                }
            }
            if (check == 0) {
                listFound4.add(listFound3.get(i));
            }
            check = 0;
        }

        if (listFound4.size() != 0) {
            //            pageOfDisplayIterEval(listFound4);
            return listFound4;
        }
        return null;

    }

    public UserEvaluation getUserInterByEvalID(int classID, int iteration_id, int evaluation_id) throws Exception {
//        List<UserEvaluation> ulist = uEvalDAO.getList();
        List<UserEvaluation> listSearch = getUserInIterationEval(classID, iteration_id);
        UserEvaluation userFound = new UserEvaluation();
//        pageOfDisplayIterEval(listSearch);
//        for (UserEvaluation u : listSearch) {
//            System.out.println(u);
//        }
        for (UserEvaluation u : listSearch) {
            if (u.getEvaluation_id() == evaluation_id) {
                return u;
            }
        }
        return null;
    }

    public void addNewMemberEval(int trainer_id, int classID) throws Exception {
        int evaluation_id;
        int criteria_id, max_loc, team_id;
        UserEvaluation newUser = new UserEvaluation();
        List<UserEvaluation> listUser = uEvalDAO.getListIterEvalWithClassID(classID);

        System.out.println("                                                    ADD NEW MEMBER EVALUATION \n\n");

        CriteriaView.showCriteriaList(criteriaDAO.getList());

        do {
            System.out.println("Evaluation Criteria List avaiable: ");

            criteria_id = Utility.getInt("Enter Criteria ID: ", "Wrong!", 0, Integer.MAX_VALUE);
            if (!criteriaController.checkCriteriaExistByID(criteriaDAO.getList(), criteria_id)) {
                System.out.println("Must enter criteria ID in list showed.");
            }
            System.out.println("");
        } while (!criteriaController.checkCriteriaExistByID(criteriaDAO.getList(), criteria_id));

        //criteria evaluation table
        Criteria nCri = criteriaController.getCriteriaByID(criteria_id);
        newUser.setCriteria_id(criteria_id);
        newUser.setPercent(nCri.getEvaluation_weight());
        newUser.setMax_loc(nCri.getMax_loc());
        newUser.setIteration_id(nCri.getIteration_id());
        System.out.println("Criteria id: " + newUser.getCriteria_id() + " Percent: " + newUser.getPercent() + " Loc: " + newUser.getMax_loc() + " IterationID: " + newUser.getIteration_id());

        // iteration evaluation table
        if (getUserInIterationEval(classID, nCri.getIteration_id()) != null) {
            pageOfDisplayIterEval(getUserInIterationEval(classID, nCri.getIteration_id()));
            do {
                evaluation_id = Utility.getInt("Enter evaluationID: ", "It must be number", 1, Integer.MAX_VALUE);

            } while (getUserInterByEvalID(classID, nCri.getIteration_id(), evaluation_id) == null);

            UserEvaluation uEval = getIterEvalUserByEvalID(evaluation_id);
            newUser.setGrade(uEval.getGrade());
            newUser.setBonus(uEval.getBonus());
            newUser.setUser_id(uEval.getUser_id());
            newUser.setClass_id(uEval.getClass_id());
            newUser.setTeam_id(uEval.getTeam_id());
            newUser.setEvaluation_id(evaluation_id);
        } //        if (getUserInIterationEval(classID, nCri.getIteration_id()) != null) {
        //            UserEvaluation uEval = getIterEvalUserByEvalID(evaluation_id);
        //            newUser.setGrade(uEval.getGrade());
        //            newUser.setBonus(uEval.getBonus());
        //            newUser.setUser_id(uEval.getUser_id());
        //            newUser.setClass_id(uEval.getClass_id());
        //            newUser.setTeam_id(uEval.getTeam_id());
        //            newUser.setEvaluation_id(evaluation_id);
        //        } 
        //add new
        else {
            //teamID
            List<Team> teamActive = teamController.showActiveTeamListWithClassID(teamDAO.getList(), classID);
            int teamID;

            do {
                teamID = Utility.getInt("Enter availabe teamID: ", "It must be number", 1, Integer.MAX_VALUE);

            } while (teamController.isTeamIDExistInList(teamActive, teamID)
                    == true);

            //userID
            int userID;
            List<User> userList = userDAO.getList();

            do {
                userID = Utility.getInt("Enter user ID: ", "Wrong!", 0, Integer.MAX_VALUE);
            } while (UserListController.checkExistUserByID(userList, userID) == false);
            UserList u = userListController.getUserListByID(userID);
            //fullName
            System.out.println("");
            System.out.println(u);
            String fullName = u.getFullName();
            System.out.println("");
            System.out.println(fullName);

            System.out.println("\ntotalGrade= bonus+ grade+ conveted_loc\n");
            double bonus = Utility.getFloat("Bonus must round 1 number after '.'\nEnter bonus: ", "It must be double!!!", 0, 2);
            double grade = Utility.getFloat("Grade must round 1 number after '.'\nEnter grade: ", "It must be double!!!", 0, 10);
            String notes = Utility.getStringNull("Enter notes: ", "");

            uEvalDAO.insertIterEvaluationsToList(newUser);
            System.out.println(newUser);

        }
        if (isDuplicated(newUser)) {
            System.out.println("DUPLICATED!!!");
        } else {

            uEvalDAO.insertUserEvaluationToList(newUser);
            System.out.println("ADD SUCESSFULLY!!!\n");
            displayHeader();
            System.out.println(newUser);
        }
    }

    public void showListIterEval(List<UserEvaluation> listSearch) throws Exception {
//        UserEvaluationDAO udao = new UserEvaluationDAO();
//        List<UserEvaluation> listSearch = udao.getListIterEvaluations();
        System.out.println("                                                    ITERATION EVALUATION");
        System.out.println("===================================================================================================");
        System.out.println(String.format("|%-15s|%-15s|%-10s|%-10s|%-10s|%-20s|%-10s|%-10s|%-10s", "evaluationID", "iterationID", "class", "teamID", "userID", "fullNane", "bonus", "grade", "note"));
        System.out.println("===================================================================================================");
        for (UserEvaluation u : listSearch) {
            System.out.println(String.format("|%-15s|%-15s|%-10s|%-10s|%-10s|%-20s|%-10s|%-10s|%-10s", u.getEvaluation_id(), u.getIteration_id(), u.getClassCode(), u.getTeam_id(), u.getUser_id(), u.getFullName(), u.getBonus(), u.getGrade(), u.getNotes()));

        }
    }

    public void updateMemberEval(int trainer_id) throws Exception {
        List<UserEvaluation> uEvalList = uEvalDAO.getList();
        System.out.println("                                                    UPDATE NEW MEMBER EVALUATION ");
        System.out.println("*Trainer can only update mark and notes");
        System.out.println("\ntotalGrade= bonus+ grade+ conveted_loc\n");
        int id;
        do {
            id = Utility.getInt("Enter member_eval_id: ", "It must be number!", 1, Integer.MAX_VALUE);
        } while (getUserEvalByID(id) == null);
        UserEvaluation u = getUserEvalByID(id);
        displayHeader();
        System.out.println(u);
        System.out.println("\n");
        System.out.println("Bonus must round 1 number after '.'\n1. Update bonus    2. Unchange bonus\n");
        int choice;
        choice = Utility.getInt("Enter choice: ", "It must be number!!!", 1, 2);
        if (choice == 1) {
            double bonus = Utility.getFloat("Enter bonus: ", "It must be double!!!", 0, 10);
            u.setBonus(bonus);
        }

        System.out.println("\nGrade must round 1 number after '.'\n1. Update grade    2. Unchange grade\n");
        int choice2;
        choice2 = Utility.getInt("Enter choice: ", "It must be number!!!", 1, 2);
        if (choice2 == 1) {
            double grade = Utility.getFloat("\nGrade must round 1 number after '.'\nEnter grade: ", "It must be double!!!", 0, 10);
            u.setGrade(grade);

        }
//        System.out.println(u.getGrade());
//        double mark = u.getGrade() + u.getBonus() + u.getConverted_loc();
//        u.setTotalGrade(mark);
//        System.out.println("totalGrade: " + u.getTotalGrade());
//        u.setTotalGrade(u.getTotalGrade());
        int choice3;
        System.out.println("\nNotes for updating...\n1. Update notes    2. Unchange notes\n");
        choice3 = Utility.getInt("Enter choice: ", "It must be number!!!", 1, 2);
        if (choice3 == 1) {
            String notes = Utility.getStringNull("Enter note: ", "");
            u.setNotes(notes);
        }

        if (notUpdate(u)) {
            System.out.println("YOU ARE NOT UPDATED!!!");
        } else {
            uEvalDAO.updateIterEvaluationsToList(u);

            uEvalDAO.updateUserEvaluationToList(u);

            System.out.println("After updated...");
            displayHeader();
            System.out.println(u);
            System.out.println("");
            System.out.println("UPDATED SUCESSFULLY!!!");

        }

    }

    private boolean isDuplicated(UserEvaluation newUser) throws Exception {
        List<UserEvaluation> uEvalList = uEvalDAO.getList();
        for (UserEvaluation u : uEvalList) {
            if (u.getCriteria_id() == newUser.getCriteria_id()
                    && u.getIteration_id() == newUser.getIteration_id()
                    && u.getCriteria_id() == newUser.getCriteria_id()
                    && u.getClass_id() == newUser.getClass_id()
                    && u.getTeam_id() == newUser.getTeam_id()
                    && u.getUser_id() == newUser.getUser_id()) {
                return true;
            }

        }
        return false;
    }

    private UserEvaluation getUserEvalByID(int id) throws Exception {
        List<UserEvaluation> uEvalList = uEvalDAO.getList();
        for (UserEvaluation u : uEvalList) {
            if (u.getMember_eval_id() == id) {
                return u;
            }
        }
        return null;
    }

    private boolean notUpdate(UserEvaluation newUser) throws Exception {
        List<UserEvaluation> uEvalList = uEvalDAO.getList();
        for (UserEvaluation u : uEvalList) {
            if (u.getCriteria_id() == newUser.getCriteria_id()
                    && u.getIteration_id() == newUser.getIteration_id()
                    && u.getCriteria_id() == newUser.getCriteria_id()
                    && u.getClass_id() == newUser.getClass_id()
                    && u.getTeam_id() == newUser.getTeam_id()
                    && u.getUser_id() == newUser.getUser_id()
                    && u.getBonus() == newUser.getBonus()
                    && u.getGrade() == newUser.getGrade()
                    && u.getNotes().equalsIgnoreCase(newUser.getNotes())) {
                return true;
            }

        }
        return false;
    }

    private UserEvaluation getIterEvalUserByEvalID(int evaluation_id) throws Exception {
        List<UserEvaluation> uEvalList = uEvalDAO.getListIterEvaluations();
//        UserEvaluation uNew= new UserEvaluation();
        for (UserEvaluation u : uEvalList) {
            if (u.getEvaluation_id() == evaluation_id) {
                return u;
            }
        }
        return null;
    }

}
