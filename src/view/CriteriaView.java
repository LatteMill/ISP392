package view;

import java.util.*;
import model.*;
import controller.*;
import dao.*;

public class CriteriaView {
//    ham goi: View_CriteriaSetting.CriteriaList();

    static CriteriaDAO criteriaDAO = new CriteriaDAO();
    static CriteriaController criteriaController = new CriteriaController();

    //user id
    static UserListController userListControler = new UserListController();
    static UserDAO userDao = new UserDAO();

    //iteration id
    static IterationController iterationController = new IterationController();
    static IterationDAO iterationDAO = new IterationDAO();

    //subject
    static SubjectController sụbjectController = new SubjectController();
    static SubjectDAO subjectDao = new SubjectDAO();

    //display
    public static void showCriteriaList(List<Criteria> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("============================= List ===============================");
            System.out.println(String.format("%-5s|%-11s|%-8s|%-15s|%-6s|%-5s|%-6s",
                    "ID", "Iteration", "Weight", "Team_criteria", " Order", "Loc", "Status"));
            System.out.println("==================================================================");

            for (int i = 0; i < sList.size(); i++) {
                System.out.println(sList.get(i));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

    public static void displayCriteriaList() {
        System.out.println("============================ Criteria List ============================");
        System.out.println("1. Search Criteria");
        System.out.println("2. Sort criteria list");
        System.out.println("3. Add Criteria into List");
        System.out.println("4. Active/Deactive an criteria");
        System.out.println("5. Update Criteria");
        System.out.println("6. Back to Dashboard");
    }

    public static void displaySearch() {
        System.out.println("============================ Search Criteria List ============================");
    }

    public static void displayCriteriaStatus() {
        System.out.println("============================ Criteria by status ============================");
        System.out.println("1. Active");
        System.out.println("2. Deactive");
    }

    public static void displaySort() {
        System.out.println("============================ Sort Criteria ============================");
        System.out.println("1. Sort by iteration id");
        System.out.println("2. Sort by criteria weight");
        System.out.println("3. Sort by criteria order");
        System.out.println("4. Sort by max loc");
        System.out.println("5. Sort by status");
        System.out.println("6. Back to Criteria");
    }

    public static void displayUpdate() {
        System.out.println("============================ Update Criteria ============================");
        System.out.println("*Skip ( Enter null ) if you dont want to change!");
        System.out.println("*The subject will be active after update!");
//        System.out.println("0. Back to Criteria List");
    }

    public static void CriteriaList(String email) throws Exception {
        int user_id = userListControler.searchUserIDbyEmailInSublist(userDao.getListWithOneCondition(" email like '" + email + "'"), email);
        User u = userListControler.searchUserByID(user_id);
        Subject sub = sụbjectController.searchSubjectByAuthorID(user_id);
        boolean author;
        int page = 1, showChoice = 0;

        int id, iteration_id, criteria_order, max_loc, team_criteria, status;
        double criteria_weight;
        List<Criteria> criteriaList = null;
        List<Iteration> iterList = null;
        List<ClassUser> classUserList = null;
        List<Subject> subjectList = null;

        if (u.getRoles() == 1) {
            author = false;
            //criteria list
            criteriaList = criteriaDAO.getList();
            //iteration list
            iterList = iterationDAO.getIterList();
        } else {
            author = true;
            //criteria list
            criteriaList = criteriaDAO.getListWithOneCondition(" a.status = true AND b.subject_id = " + sub.getSubjectID());
            //iteration list
            iterList = iterationDAO.getListWithOneCondition(" status = true AND subject_id = " + sub.getSubjectID());
        }
        while (showChoice != 5) {

//            if (u.getRoles() != 2) {
//                author = false;
//                //criteria list
//                criteriaList = criteriaDAO.getList();
//                //iteration list
//                iterList = iterationDAO.getIterList();
//            }else{
//                author = true;
//                //subject list
//                subjectList = subjectDao.getListWithOneCondition("status = 1 AND author_id = " + sụbjectController.getSubjectListByID(sub.getAuthor_id()).getAuthor_id());
//                //iteration list
//                iterList = iterationDAO.getListWithOneCondition(" status = 1 AND subject_id = " + iterationController.getIterationBySubjectID(sub.getSubjectID()).getSubject_id());
//                //criteria list
//                //Phải làm iteration_evaluation
////                criteriaList = criteriaDAO.getListWithOneCondition(" status = 1 AND iteration_id = "+iterationController.getIterationBySubjectID(user_id));
//            }
            int maxpage = criteriaList.size() / 5;
            if (maxpage != 1) {
                if (criteriaList.size() % 5 != 0) {
                    maxpage++;
                }
            }
            System.out.println("There are: " + maxpage + " pages.");
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                showCriteriaList(criteriaController.pagination(criteriaList, page));
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
                    while (option != 6) {
                        displayCriteriaList();
                        option = Utility.getInt("OPTION: ", "Wrong", 1, 6);
                        switch (option) {
                            case 1: { //search criteria
                                displaySearch();
                                String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                                try {
                                    List<Criteria> searchList = criteriaDAO.getListForSearch(pattern, author, sub.getSubjectID());
                                    showCriteriaList(searchList);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            break;
                            case 2: {
                                displaySort();
                                int sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 5);
                                try {
                                    List<Criteria> sortList = criteriaController.sortCriteria(u.getRoles(), sub.getSubjectID(), sortOption);
                                    showCriteriaList(sortList);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }

                            break;
                            case 3: { //add
                                System.out.println("============================ Add New Criteria ============================");
                                System.out.println("*The new subject will be in ACTIVE mode!");
                                do {
                                    System.out.println("Iteration list availble: ");
                                    showIterationListForCriteria(iterList);
                                    iteration_id = Utility.getInt("Enter Iter ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                                    if (!iterationController.checkIterationExistByID(iterList, iteration_id)) {
                                        System.out.println("Must enter iter ID in list showed only.");
                                    }
                                } while (!iterationController.checkIterationExistByID(iterList, iteration_id));

                                criteria_weight = Utility.getDouble("Enter criteria_weight: ", "Wrong.", Double.MIN_VALUE, Double.MAX_VALUE);
                                team_criteria = Utility.getInt("Enter 1 - yes or 2 - no (team criteria): ", "Wrong!", 1, 2);
                                criteria_order = Utility.getInt("Enter criteria_order: ", "Wrong.", Integer.MIN_VALUE, Integer.MAX_VALUE);
                                max_loc = Utility.getInt("Enter max_loc: ", "Wrong.", Integer.MIN_VALUE, Integer.MAX_VALUE);
                                status = 1;

                                Criteria sb = new Criteria(iteration_id, criteria_weight, team_criteria, criteria_order, max_loc, status);

                                try {
                                    criteriaController.addNewCriteria(sb);
                                    System.out.println("Current criteria detail: ");
                                    if (author = false) {
                                        showCriteriaList(criteriaDAO.getList());
                                    } else {
                                        showCriteriaList(criteriaList);
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            break;
                            case 4: { //active/ deactive
                                System.out.println("============================ Change Criteria status ============================");
                                System.out.println("Criteria list available: ");
                                showCriteriaList(criteriaList);
                                id = Utility.getInt("Enter Criteria ID to change status: ", "Can't be empty!", 0, Integer.MAX_VALUE);
                                if (criteriaController.searchCriteriaByID(id) == null) {
                                    System.out.println("Can NOT find Criteria!");
                                    break;
                                }

                                System.out.println("The current mode is: " + CriteriaController.searchCriteriaByID(id).getStatus());
                                System.out.println("");

                                System.out.println("Change into: ");
                                displayChangeStatus();
                                option = Utility.getInt("Enter option: ", "Wrong!", 1, 2);
                                try {
                                    criteriaController.changeCriteriaStatus(id, option);
                                    System.out.println("Current Criteria detail: ");
                                    displayFeature();
                                    System.out.println(criteriaController.searchCriteriaByID(id));

                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }

                            break;
                            case 5: { //update           
                                System.out.println("============================ Update Criteria ============================");
                                Criteria sb;
                                do {
                                    System.out.println("Criteria list available: ");
                                    showCriteriaList(criteriaList);
                                    id = Utility.getInt("Enter criteria_id to update: ", "Wrong!", 1, Integer.MAX_VALUE);
                                    sb = criteriaController.searchCriteriaByID(id);
                                    if (!criteriaController.checkCriteriaExistByID(criteriaList, id)) {
                                        System.out.println("Must enter ID in list showed !");
                                    }
                                } while (sb == null);

                                do {
                                    System.out.println("Iteration list availble: ");
                                    showIterationListForCriteria(iterList);
                                    iteration_id = Utility.getIntNull("Enter Iter ID(Current: " + sb.getIteration_id() + " ):", sb.getIteration_id(), 0, Integer.MAX_VALUE);
                                    if (!iterationController.checkIterationExistByID(iterList, iteration_id)) {
                                        System.out.println("Must enter iter ID in list showed only.");
                                    }
                                } while (!iterationController.checkIterationExistByID(iterList, iteration_id));
                                criteria_weight = Utility.getDouble("Enter evaluation weight to update: ", "Wrong.", Integer.MIN_VALUE, Integer.MAX_VALUE);
                                team_criteria = Utility.getIntNull("Enter team criteria with Yes(1), No(2) (Current: " + sb.getTeamString() + " ): ", sb.getTeam_evaluation(), 1, 2);
                                criteria_order = Utility.getIntNull("Enter criteria_order to update (Current: " + sb.getCriteria_order() + " ): ", sb.getCriteria_order(), Integer.MIN_VALUE, Integer.MAX_VALUE);
                                max_loc = Utility.getIntNull("Enter max_loc to update (Current: " + sb.getMax_loc() + " ): ", sb.getMax_loc(), Integer.MIN_VALUE, Integer.MAX_VALUE);
                                status = Utility.getIntNull("Enter status with Active(1), Deactive(2) (Current: " + sb.getStatus() + " ): ", sb.isStatus(), 1, 2);

                                try {
                                    criteriaController.updateCriteria(new Criteria(id, iteration_id, criteria_weight, team_criteria, criteria_order, max_loc, status));
                                    System.out.println("Current criteria detail: ");
                                    displayFeature();
                                    System.out.println(criteriaController.searchCriteriaByID(id));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 6: {
                                return;
                            }
                        }
                    }
                }
            } while (0 > page || page > maxpage);
        }
    }

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Go to Criteria function.");
        System.out.println("5. Back.");
    }

    private static void displayChangeStatus() {
        System.out.println("1. Active ");
        System.out.println("2. Deactive");
    }

    private static void displayFeature() {
        System.out.println(String.format("%-5s|%-11s|%-8s|%-15s|%-6s|%-5s|%-6s",
                "ID", "Iteration", "Weight", "Team_criteria", " Order", "Loc", "Status"));
        System.out.println("==================================================================");
    }

    public static void showIterationListForCriteria(List<Iteration> cl) throws Exception {
        System.out.println("=================Iteration List ======================");
        System.out.println(String.format("%-5s|%-20s", "ID", "Name"));
        System.out.println("-----------------------------------------------------------------------------------------------------");
        if (cl.size() > 0) {
            for (int i = 0; i < cl.size(); i++) {
                Iteration c = cl.get(i);
                System.out.println(String.format("%-5s|%-20s", c.getIteration_id(), c.getIteration_name()));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

}
