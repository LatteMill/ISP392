package controller;

import java.util.*;
import model.*;
import dao.*;
import view.Utility;

public class SubjectController {
    //Main code owner of this class: Linh
    //Have function add in this class: ----
    
    static SubjectDAO subjectDAO = new SubjectDAO();
    static UserDAO userDAO = new UserDAO();
    static UserListController userListController = new UserListController();

    

    public static Subject getSubjectListByID(int id) throws Exception {
        List<Subject> Subject = SubjectDAO.getList();
        //loop run from first to last element of listUserLists
        for (Subject subject : Subject) {
            //if dusearchListicate id => return SettingList has existed
            if (subject.getSubjectID()== id) {
                return subject;
            }
        }
        return null;
    }
    
    public static boolean checkExistSubjectByID(List<Subject> sortListist, int id) throws Exception {

        for (int i = 0; i < sortListist.size(); i++) {
            if (sortListist.get(i).getSubjectID() == id) {
                return true; //exist
            }
        }
        return false; //not exist
    }

    //check code subject ton tai chua? 
    public static boolean checkSubjectExistByCode(String code) throws Exception {
        List<Subject> subjectList = subjectDAO.getList();

        if (subjectList.size() > 0) {
            for (int i = 0; i < subjectList.size(); i++) {
                if (subjectList.get(i).getSubjectCode().compareToIgnoreCase(code) == 0) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //check code subject ton tai chua? 
    public static boolean checkSubjectExistByID(int id) throws Exception {
        List<Subject> subjectList = subjectDAO.getList();

        if (subjectList.size() > 0) {
            for (int i = 0; i < subjectList.size(); i++) {
                if (subjectList.get(i).getSubjectID() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //them subject moi
    public static void addNewSubject(Subject subject) throws Exception {
        List<Subject> subjectList = subjectDAO.getList();
        if (!checkSubjectExistByCode(subject.getSubjectCode())) {
            if (userListController.checkExistUserByID(userDAO.getListWithOneCondition("roles = 1 or roles =2"), subject.getAuthor_id())) {
                subjectList.add(subject);
                subjectDAO.insertSubjectToList(subject);
                System.out.println("Add successfully!");
            } else {
                throw new Exception("Please add the author in suggested list!");
            }
        } else {
            throw new Exception("Exist subject!");
        }
    }

    //find subject index
    public static int searchSubjectIndexByCode(String code) throws Exception {
        List<Subject> subjectList = subjectDAO.getList();
        if (subjectList.size() > 0) {
            for (int i = 0; i < subjectList.size(); i++) {
                if (subjectList.get(i).getSubjectCode().compareToIgnoreCase(code) == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    //find subject index
    public static int searchSubjectIndexByID(int id) throws Exception {
        List<Subject> subjectList = subjectDAO.getList();
        if (subjectList.size() > 0) {
            for (int i = 0; i < subjectList.size(); i++) {
                if (subjectList.get(i).getSubjectID() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    //find Subject
    public static Subject searchSubjectByCode(String code) throws Exception {
        List<Subject> subjectList = subjectDAO.getList();
        if (subjectList.size() > 0) {
            for (int i = 0; i < subjectList.size(); i++) {
                if (subjectList.get(i).getSubjectCode().compareToIgnoreCase(code) == 0) {
                    return subjectList.get(i);
                }
            }
        }
        return null;
    }

    public static Subject searchSubjectByID(int id) throws Exception {
        List<Subject> subjectList = subjectDAO.getList();
        if (subjectList.size() > 0) {
            for (int i = 0; i < subjectList.size(); i++) {
                if (subjectList.get(i).getSubjectID() == id) {
                    return subjectList.get(i);
                }
            }
        }
        return null;
    }
    
    public static Subject searchSubjectByAuthorID(int authorID) throws Exception {
        List<Subject> subjectList = subjectDAO.getList();
        if (subjectList.size() > 0) {
            for (int i = 0; i < subjectList.size(); i++) {
                if (subjectList.get(i).getAuthor_id()== authorID) {
                    return subjectList.get(i);
                }
            }
        }
        return null;
    }

    //update subject 
    public static void updateSubject(Subject subject) throws Exception {
        List<Subject> subjectList = subjectDAO.getList();

        if (checkSubjectExistByID(subject.getSubjectID())) {
            if (userListController.checkExistUserByID(userDAO.getListWithOneCondition("roles = 1 or roles =2"), subject.getAuthor_id())) {
                subjectList.set(searchSubjectIndexByID(subject.getSubjectID()), subject);
                subjectDAO.updateSubjectToList(subject);
                System.out.println("Update successfully!");
            } else {
                throw new Exception("Please add the author in suggested list!");
            }
        } else {
            throw new Exception("Can not find the Subject!");
        }

    }

    public static void changeSubjectStatus(int id) throws Exception {
        List<Subject> subjectList = subjectDAO.getList();
        if (checkSubjectExistByID(id)) {
            try {
                Subject subject = searchSubjectByID(id);
                boolean status = subject.isStatus();
                subject.setStatus(!status);
                subjectDAO.updateSubjectToList(subject);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new Exception("Subject is NOT exist!");
        }
    }

    //seach code(1), name(2), status(3)
    public List<Subject> searchByPattern(String pattern) throws Exception {
        List<Subject> fullList = subjectDAO.getList();
        pattern = pattern.toUpperCase();
        List<Subject> searchList = subjectDAO.getSearchList(pattern);
        //chuyển tất cả về toLowerCase
        
        return searchList;
    }

    //sap xep subject
    public List<Subject> sortSubject(int choice) throws Exception {
        List<Subject> sortList = subjectDAO.getList();
        switch (choice) {
            case 1: // so sánh code
                Collections.sort(sortList, new Comparator<Subject>() {
                    @Override
                    public int compare(Subject subject1, Subject subject2) {
                        return subject1.getSubjectCode().compareTo(subject2.getSubjectCode());
                    }
                });
                break;

            case 2: //so sánh name
                Collections.sort(sortList, new Comparator<Subject>() {
                    @Override
                    public int compare(Subject subject1, Subject subject2) {
                        return subject1.getSubjectName().compareTo(subject2.getSubjectName());
                    }
                });
                break;

            case 3: //so sanh active
                Collections.sort(sortList, new Comparator<Subject>() {
                    @Override
                    public int compare(Subject subject1, Subject subject2) {
                        return subject1.getStatus().compareTo(subject2.getStatus());
                    }
                });
                break;

            //nếu nhập khác thì exit sort
            default:
                System.out.println("Exit sort.");
                break;
        }

        return sortList;
    }

    public static List<Subject> pagination(List<Subject> list, int page) throws Exception {
        //set up list 5 để trả về
        List<Subject> subjectList = new ArrayList<>();
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
                        subjectList.add(list.get(i));
                    }
                }
                i++;
            } while (i < start + 5);

            return subjectList;

        } else {
            throw new Exception("Can't not go to that page");
        }
    }
    
    
}
