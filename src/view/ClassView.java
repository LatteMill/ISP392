package view;

import java.util.*;
import model.*;
import controller.*;
import dao.*;

public class ClassView {
//    ham goi: View_ClassroomSetting.ClassroomList();

    static ClassDAO classDao = new ClassDAO();
    static ClassController classController = new ClassController();

    //subject
    static SubjectController sụbjectController = new SubjectController();
    static SubjectDAO subjectDao = new SubjectDAO();

    //trainer
    static UserListController userListControler = new UserListController();
    static UserDAO userDao = new UserDAO();

//    public static void dissplayFeature() {
//        System.out.println(String.format("%-5|%-15|%-15|%-10", "ID", "Classroom Code", "Classroom Name", "Status"));
//    }
    //display
    public static void showClassList(List<Classroom> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("============================= List ===============================");
            System.out.println(String.format("%-5s|%-6s|%-30s|%-20s|%-8s|%-5s|%-14s|%-8s",
                    "ID", "Code", "Trainer", "Subject", "Year", "Term", "Block5_status", "Status"));
            System.out.println("==================================================================");

            for (int i = 0; i < sList.size(); i++) {
                System.out.println(sList.get(i));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

    public static void displayClassroomList(int role) {
        if (role == 1 || role == 2) {
            System.out.println("============================ Class List ============================");
            System.out.println("1. Search Class");
            System.out.println("2. Sort Class list");
            System.out.println("3. Add Class into List");
            System.out.println("4. Change status a Class");
            System.out.println("5. Update Class");
            System.out.println("6. Back to Dashboard");
        } else {
            System.out.println("============================ Class List ============================");
            System.out.println("1. Search Class");
            System.out.println("2. Sort Class list");
            System.out.println("3. Back to Dashboard");
        }
    }

    public static void displaySearch() {
        System.out.println("============================ Search Class List ============================");
    }

    public static void displaySearchStatus() {
        System.out.println("============================ Search by status ============================");
        System.out.println("1. Active");
        System.out.println("2. Deactive");
    }

    public static void displaySort() {
        System.out.println("============================ Sort Class ============================");
        System.out.println("1. Sort by code");
        System.out.println("2. Sort by year");
        System.out.println("3. Sort by term");
        System.out.println("4. Back to Classroom List");
    }

    public static void displayUpdate() {
        System.out.println("============================ Update Class Profile ============================");
        System.out.println("*Skip ( Enter null ) if you dont want to change!");
        System.out.println("*If you want to Cancelled, please go to change status to make sure.");
//        System.out.println("0. Back to Classroom List");
    }

    public static void displayFeature() {
        System.out.println(String.format("%-5s|%-6s|%-30s|%-20s|%-8s|%-5s|%-14s|%-8s",
                "ID", "Code", "Trainer", "Subject", "Year", "Term", "Block5_status", "Status"));
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");

    }

    private static void displayChangeStatus() {
        System.out.println("1. Active ");
        System.out.println("2. Deactive");
    }

    public static void displayChoiceForShowList() {
        System.out.println("1. Next page.");
        System.out.println("2. Previous page.");
        System.out.println("3. Go to specify page.");
        System.out.println("4. Go to class functions.");
        System.out.println("5. Back.");
    }

    public static void ClassroomListForTrainer(String email) throws Exception {
        int user_id = userListControler.searchUserIDbyEmailInSublist(userDao.getListWithOneCondition(" email like '" + email + "'"), email);
        User u = userListControler.searchUserByID(user_id);
        Classroom classes = classController.searchClassroomByTrainerID(user_id);

        List<Classroom> classList = null;
        //class list
        classList = classDao.getListWithOneCondition(" trainer_id = " + classes.getTrainer_id());

        int option = 0;
        int maxpage = classList.size() / 5;
        if (classList.size() % 5 != 0) {
            maxpage++;
        }

        int page = 1, showChoice = 0;

        //show page
        try {
            System.out.println("-----------------");
            System.out.println("Current page: " + page);
            showClassList(classController.pagination(classList, page));
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
                option = 0;
                while (option != 3) {
                    displayClassroomList(u.getRoles());
                    option = Utility.getInt("Enter option: ", "Wrong!", 1, 3);
                    switch (option) {
                        case 1: {
                            displaySearch();
                            String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                            try {
                                List<Classroom> searchList = classDao.getListForSearch(pattern, u.getRoles(), classes.getTrainer_id());
                                showClassList(searchList);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                        case 2: {
                            int sortOption = 0;
                            displaySort();
                            sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 4);
                            try {
                                List<Classroom> searchList = classController.sortClassroom(u.getRoles(), classes.getTrainer_id(), sortOption);
                                showClassList(searchList);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    }
                }
            }
            if (showChoice == 5) {
                return;
            }
        } while (0 > page || page > maxpage);
    }

    public static void ClassroomListForVIP(String email) throws Exception {
        //lấy user_id để phân quyền
        int user_id = userListControler.searchUserIDbyEmailInSublist(userDao.getListWithOneCondition(" email like '" + email + "'"), email);
        User u = userListControler.searchUserByID(user_id);
        Subject subject = sụbjectController.searchSubjectByAuthorID(user_id);
        int page = 1, showChoice = 0;

        int id, trainer_id, subject_id, class_year,
                class_term, block5_class, status;
        String class_code;

        List<Classroom> classList = null;
        List<UserDetails> userList = null;
        List<Subject> subjectList = null;

        /**
         * Phan chia role nguoi dung
         */
        if (u.getRoles() == 1) {
            //class list
            classList = classDao.getList();
            //subject list
            subjectList = subjectDao.getList();
        } else if (u.getRoles() == 2) {
            //class list:
            classList = classDao.getListWithOneCondition(" status = 1 AND subject_id = " + subject.getSubjectID());
            //subject list
            subjectList = subjectDao.getListWithOneCondition(" status = true AND author_id = " + subject.getAuthor_id());
        }

        while (showChoice != 5) {
            int maxpage = classList.size() / 5;
            if (classList.size() % 5 != 0) {
                maxpage++;
            }
            System.out.println("There are: " + maxpage + " pages.");

            //show page
            try {
                System.out.println("-----------------");
                System.out.println("Current page: " + page);
                showClassList(classController.pagination(classList, page));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //pagination
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
                        displayClassroomList(u.getRoles());
                        option = Utility.getInt("OPTION: ", "WRONG", 1, 6);
                        switch (option) {
                            case 1: { //search class
                                displaySearch();
                                String pattern = Utility.getString("Enter value to search: ", "Can't not empty", Utility.REGEX_STRING_WITH_SPECIAL_SIGN);
                                try {
                                    List<Classroom> searchList = null;
                                    if (u.getRoles() == 1) {
                                        searchList = ClassDAO.getListForSearch(pattern, u.getRoles(), 0);
                                    } else {
                                        searchList = ClassDAO.getListForSearch(pattern, u.getRoles(), subject.getSubjectID());
                                    }
                                    showClassList(searchList);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }

                            break;
                            case 2: {
                                displaySort();
                                int sortOption = Utility.getInt("OPTION: ", "WRONG", 1, 4);
                                try {
                                    List<Classroom> sortList = null;
                                    if (u.getRoles() == 1) {
                                        sortList = classController.sortClassroom(u.getRoles(), 0, sortOption);
                                    } else {
                                        sortList = classController.sortClassroom(u.getRoles(), subject.getSubjectID(), sortOption);
                                    }
                                    showClassList(sortList);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 3: { //add
                                System.out.println("============================ Add New Class ============================");
                                System.out.println("*The new subject will be in ACTIVE mode!");

                                class_code = Utility.getString("Enter Class Code: ", "Wrong.", Utility.REGEX_STRING);
                                //Lấy trainer_id (class) available:
                                if (u.getRoles() == 1) {
                                    do {
                                        System.out.println("Trainer list availble: ");
                                        showTrainerIDListForClass(classList);
                                        trainer_id = Utility.getInt("Enter trainer id: ", "Wrong", 0, Integer.MAX_VALUE);
                                        if (!classController.checkClassroomExistByID(classList, trainer_id)) {
                                            System.out.println("Must enter trainer id in list showed only.");
                                        }
                                    } while (!classController.checkClassroomExistByID(classList, trainer_id));
                                } else {
                                    System.out.println("Your trainer information: ");
                                    showTrainerListForClass(classList);
                                    trainer_id = classController.searchByTrainerID(user_id).getTrainer_id();
                                    System.out.println("Your trainer id is: " + trainer_id);
                                }
                                //Lay subject_id (subject) available:
                                if (u.getRoles() == 1) {
                                    do {
                                        //subject list
                                        System.out.println("Subject list available: ");
                                        showSubjectListForClass(subjectList);
                                        subject_id = Utility.getInt("Enter subject id: ", "Wrong", 0, Integer.MAX_VALUE);
                                        if (!sụbjectController.checkExistSubjectByID(subjectList, subject_id)) {
                                            System.out.println("Must enter subject id in list showed only.");
                                        }
                                    } while (!sụbjectController.checkExistSubjectByID(subjectList, subject_id));
                                } else {
                                    System.out.println("Your subject information: ");
                                    showSubjectListForClass(subjectList);
                                    subject_id = sụbjectController.searchSubjectByID(subject.getSubjectID()).getSubjectID();
                                    System.out.println("Your trainer id is: " + subject_id);
                                }
                                class_year = Utility.getInt("Enter class year: ", "Wrong.", Integer.MIN_VALUE, Integer.MAX_VALUE);
                                class_term = Utility.getInt("Enter class term: ", "Wrong.", Integer.MIN_VALUE, Integer.MAX_VALUE);
                                block5_class = Utility.getInt("Enter 1 - yes or 2 - no (block5): ", "Wrong!", 1, 2);
                                status = 1;

                                Classroom classFound = new Classroom(class_code, trainer_id, subject_id, class_year, class_term, block5_class, status);
                                try {
                                    classController.addNewClass(classFound);
                                    System.out.println("Current class detail: ");
                                    if (u.getRoles() == 1) {
                                        showClassList(classDao.getList());
                                    } else {
                                        showClassList(classList);
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            break;
                            case 4: { //active/ deactive status
                                System.out.println("============================ Change Class status ============================");
                                System.out.println("Class list available: ");
                                showClassList(classList);
                                id = Utility.getInt("Enter Class ID to change status: ", "Can't be empty!", 0, Integer.MAX_VALUE);
                                if (classController.searchClassroomByID(id) == null) {
                                    System.out.println("Can not find class !");
                                    break;
                                }
                                System.out.println("The current mode is: " + classController.searchClassroomByID(id).getStatus());

                                System.out.println("");
                                System.out.println("Change into: ");
                                displayChangeStatus();
                                option = Utility.getInt("Enter option: ", "Wrong!", 1, 2);
                                try {
                                    classController.changeClassroomStatus(id, option);
                                    System.out.println("Current classroom detail: ");
                                    displayFeature();
                                    System.out.println(classController.searchClassroomByID(id));

                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            break;
                            case 5: { //update           
                                System.out.println("============================ Update Class ============================");
                                Classroom classFound;
                                do {
                                    System.out.println("Class list available: ");
                                    showClassList(classList);
                                    id = Utility.getInt("Enter Classroom ID to update: ", "Wrong!", 1, Integer.MAX_VALUE);
                                    classFound = classController.searchClassroomByID(id);
                                    if (!classController.checkClassroomExistByID(classList, id)) {
                                        System.out.println("Must enter ID in list showed !");
                                    }
                                } while (classFound == null);
                                /**
                                 * Subject list
                                 */
                                if (u.getRoles() == 1) {
                                    do {
                                        //subject list
                                        System.out.println("Subject list available: ");
                                        showSubjectListForClass(subjectList);
                                        subject_id = Utility.getInt("Enter subject id: ", "Wrong", 0, Integer.MAX_VALUE);
                                        if (!sụbjectController.checkExistSubjectByID(subjectList, subject_id)) {
                                            System.out.println("Must enter subject id in list showed only.");
                                        }
                                    } while (!sụbjectController.checkExistSubjectByID(subjectList, subject_id));
                                } else {
                                    System.out.println("Your subject information: ");
                                    showSubjectListForClass(subjectList);
                                    subject_id = sụbjectController.searchSubjectByID(subject.getSubjectID()).getSubjectID();
                                    System.out.println("Your trainer id is: " + subject_id);
                                }
                                class_code = Utility.getStringNull("Enter Class Code to update: ", classFound.getClass_code());
                                /**
                                 * Trainer list
                                 */
                                if (u.getRoles() == 1) {
                                    do {
                                        System.out.println("Trainer list availble: ");
                                        showTrainerIDListForClass(classList);
                                        trainer_id = Utility.getInt("Enter trainer id: ", "Wrong", 0, Integer.MAX_VALUE);
                                        if (!classController.checkClassroomExistByID(classList, trainer_id)) {
                                            System.out.println("Must enter trainer id in list showed only.");
                                        }
                                    } while (!classController.checkClassroomExistByID(classList, trainer_id));
                                } else {
                                    System.out.println("Your trainer information: ");
                                    showTrainerListForClass(classList);
                                    trainer_id = classController.searchByTrainerID(user_id).getTrainer_id();
                                    System.out.println("Your trainer id is: " + trainer_id);
                                }
                                class_year = Utility.getIntNull("Enter class year to update (Current: " + classFound.getClass_year() + " ): ", classFound.getClass_year(), Integer.MIN_VALUE, Integer.MAX_VALUE);
                                class_term = Utility.getIntNull("Enter class term to update with Spring(1), Summer(2), Fall(3) (Current: " + classFound.getClassTermString() + " ): ", classFound.getClass_term(), Integer.MIN_VALUE, Integer.MAX_VALUE);
                                block5_class = Utility.getInt("Enter 1 - yes or 2 - no(block 5) (Current: " + classFound.getBlock5String() + " ): ", "Wrong!", 1, 2);
                                status = Utility.getIntNull("Enter 1 - active or 2 - deactive(status) (Current: " + classFound.getStatus() + " ): ", classFound.isStatus(), 1, 2);

                                try {
                                    classController.updateClassroom(new Classroom(id, class_code, trainer_id, subject_id, class_year, class_term, block5_class, status));
                                    System.out.println("Current Class detail: ");
                                    displayFeature();
                                    System.out.println(classController.searchClassroomByID(id));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                            case 6:
                                return;
                        }
                    }
                }
            } while (0 > page || page > maxpage);
        }
    }

    public static void showSubjectListForClass(List<Subject> cl) throws Exception {
        System.out.println("=================Subject List ======================");
        System.out.println(String.format("%-3s|%-5s|%-30s|%-25s", "ID", "Code", "Name", "Author"));
        System.out.println("-----------------------------------------------------------------------------------------------------");
        if (cl.size() > 0) {
            for (int i = 0; i < cl.size(); i++) {
                Subject c = cl.get(i);
                System.out.println(String.format("%-3s|%-5s|%-30s|%-25s", c.getSubjectID(), c.getSubjectCode(), c.getSubjectName(), c.getAuthor_Name()));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

    public static void showTrainerListForClass(List<Classroom> cl) throws Exception {
        System.out.println("=================Trainer List ======================");
        System.out.println(String.format("%-4s|%-5s|%-30s", "ID", "Class", "Full name"));
        System.out.println("-----------------------------------------------------------------------------------------------------");
        if (cl.size() > 0) {
            for (int i = 0; i < cl.size(); i++) {
                Classroom c = cl.get(i);
                System.out.println(String.format("%-4s|%-5s|%-30s", c.getClass_id(), c.getClass_code(), c.getTrainerName()));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

    public static void showTrainerIDListForClass(List<Classroom> cl) throws Exception {
        System.out.println("=================Trainer ID List ======================");
        System.out.println(String.format("%-4s|%-30s", "ID", "Full name"));
        System.out.println("-----------------------------------------------------------------------------------------------------");
        if (cl.size() > 0) {
            for (int i = 0; i < cl.size(); i++) {
                Classroom c = cl.get(i);
                System.out.println(String.format("%-4s|%-30s", c.getClass_id(), c.getTrainerName()));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }
}
