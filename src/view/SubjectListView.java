package view;

import java.util.*;
import model.*;
import controller.*;
import dao.*;

public class SubjectListView {
//    ham goi: View_SubjectSetting.SubjectList();

    static SubjectDAO subjectDAO = new SubjectDAO();
    static SubjectController subjectController = new SubjectController();

    static UserListController userListController = new UserListController();
    static UserDAO userDAO = new UserDAO();

    public static void displayFeature() {
        System.out.println(String.format("%-5s|%-15s|%-30s|%-25s|%-20s|%-15s", "ID", "Code", "Name", "Author", "Status", "Description"));
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    //display
    public static void displaySubjectList() {
        System.out.println("============================ Subject List ============================");
//        System.out.println("1. Show Subject List");
        System.out.println("1. Search Subject");
        System.out.println("2. Sort subject list");
        System.out.println("3. Add Subject into List");
        System.out.println("4. Active/Deactive a subject");
        System.out.println("5. Update Subject");
        System.out.println("6. Back to Subject");
    }



    public static void displaySearchStatus() {
        System.out.println("============================ Search by status ============================");
        System.out.println("1. Active");
        System.out.println("2. Deactive");
        System.out.println("3. Back to Search Subject List");
    }

    public static void displaySort() {
        System.out.println("============================ Sort Subject ============================");
        System.out.println("1. Sort by code");
        System.out.println("2. Sort by name");
        System.out.println("3. Sort by status");
        System.out.println("4. Back to Subject List");
    }

    public static void displayUpdate() {
        System.out.println("============================ Update Subject ============================");
        System.out.println("*Skip ( Enter null ) if you dont want to change!");
        System.out.println("*The subject will be active after update!");
//        System.out.println("0. Back to Subject List");
    }

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Function.");
        System.out.println("5. Back");
    }

    public static void SubjectList() throws Exception {
        String subjectCode, subjectName, description;
        int author_id;
        boolean status = false;
        int page = 1, showChoice = 0;
        
        
        while (showChoice != 5) {
            List<Subject> subjectList = subjectDAO.getList();
            int maxpage = subjectList.size() / 5;
            if (maxpage != 1) {
                if (subjectList.size() % 5 != 0) {
                    maxpage++;
                }
            }
            System.out.println("There are: " + maxpage + " pages.");
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                showSubjectList(subjectController.pagination(subjectList, page));
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

                        displaySubjectList();
                        int id;
                        option = Utility.getInt("OPTION: ", "WRONG", 1, 6);

                        switch (option) {
                            case 1: { //search subject
                                String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                                try {
                                    showSubjectList(subjectController.searchByPattern(pattern));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 2: {//sort
                                displaySort();
                                int sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 4);
                                try{
                                showSubjectList(subjectController.sortSubject(sortOption));
                                }catch(Exception e){
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 3: { //add
                                System.out.println("============================ Add New Subject ============================");
                                System.out.println("*The new subject will be in ACTIVE mode!");

                                subjectCode = Utility.getString("Enter Subject Code: ", "Wrong.", Utility.REGEX_STRING);
                                subjectName = Utility.getString("Enter Subject Name: ", "Wrong.", Utility.REGEX_STRING);

                                //subject ID: FK
                                System.out.println("The current author can added list: ");
                                showListAuthor(userDAO.getListWithOneCondition("roles = 1 OR roles = 2"));
                                author_id = Utility.getInt("Enter Author ID: ", "Wrong!", 0, Integer.MAX_VALUE);
                                System.out.println("");

                                int statusNumber = Utility.getInt("Enter 1 - active or 0 - deactive: ", "Wrong!", 0, 1);
                                if (statusNumber == 1) {
                                    status = true;
                                }
                                if (statusNumber == 0) {
                                    status = false;
                                }

                                description = Utility.getStringNull("Enter the description for subject (optional): ", "null");

                                Subject subject = new Subject(subjectCode, subjectName, status, author_id, description);

                                try {
                                    subjectController.addNewSubject(subject);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 4: { //active/ deactive
                                System.out.println("============================ Change Subject status ============================");
                                id = Utility.getInt("Enter Subject ID to change status: ", "Can't be empty!", 0, Integer.MAX_VALUE);
                                System.out.println("The current mode is: " + subjectController.searchSubjectByID(id).getStatus());
                                try {
                                    
                                    subjectController.changeSubjectStatus(id);
                                    //    sc.changeSubjectStatus(sb.getSubjectID());
                                    //ud.updateUserToList(ur, "verify", verifyString, "boolean");

                                    System.out.println("Current subject detail: ");
                                    displayFeature();
                                    System.out.println(subjectController.searchSubjectByID(id));

                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            break;
                            case 5: { //update           
                                System.out.println("============================ Update Subject ============================");
                                Subject subject;
                                do {
                                    id = Utility.getInt("Enter Subject ID to update: ", "Wrong!", 1, Integer.MAX_VALUE);
                                    subject = subjectController.searchSubjectByID(id);
                                } while (subjectController.searchSubjectByID(id) == null);

//                    System.out.println("Current values of subject: ");
//                    displayFeature();
//                    System.out.println(subject);
//                    System.out.println("");
                                subjectCode = Utility.getStringNull("Enter Subject Code to update (Current: " + subject.getSubjectCode() + "): ", subject.getSubjectCode());
                                subjectName = Utility.getStringNull("Enter Subject Name to update (Current: " + subject.getSubjectName() + "): ", subject.getSubjectName());

                                //subject ID: FK
                                System.out.println("The current author can added list: ");
                                showListAuthor(userDAO.getListWithOneCondition("roles = 1 OR roles = 2"));
                                System.out.println("");
                                author_id = Utility.getIntNull("Enter Author ID (Current: " + subject.getAuthor_id() + "): ", subject.getAuthor_id(), 0, Integer.MAX_VALUE);

                                int statusNumber = Utility.getInt("Enter 1 - active or 0 - deactive(Current: " + subject.getStatus() + "): ", "Wrong!", 0, 1);
                                if (statusNumber == 1) {
                                    status = true;
                                }
                                if (statusNumber == 0) {
                                    status = false;
                                }

                                description = Utility.getStringNull("Enter the description for subject (optional): ", subject.getDescription());

                                try {
                                    subjectController.updateSubject(new Subject(id, subjectCode, subjectName, status, author_id, description));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 6: {
                               option=6; 
                            }
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

    public static void showListAuthor(List<User> sList) throws Exception {
        if (sList.size() > 0) {
            System.out.println("============================= User List =========================");
            System.out.println(String.format("%-5s|%-20s|%-10s", "ID", "Name", "Role"));
            System.out.println("-------------------------------------------------------------------");

            for (int i = 0; i < sList.size(); i++) {
                System.out.println(String.format("%-5d|%-20s|%-10s",
                        sList.get(i).getId(),
                        sList.get(i).getFullName(),
                        sList.get(i).getRolesDetail()));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

}

