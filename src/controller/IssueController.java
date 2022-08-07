package controller;

import view.Utility;
import model.*;
import java.util.*;
import dao.*;

public class IssueController {

    static IssueDAO issueDAO = new IssueDAO();

    //user
    static UserListController userListController = new UserListController();
    static UserDAO userDAO = new UserDAO();

    //team
    static TeamController teamController = new TeamController();
    static TeamDAO teamDao = new TeamDAO();

    //milestone
    static MilestoneDAO milestoneDao = new MilestoneDAO();
    static MilestoneController milestoneController = new MilestoneController();

    //class_user
    static ClassUserDAO classUserDAO = new ClassUserDAO();
    static ClassUserController classUserController = new ClassUserController();

    //funtion_ids
    static FunctionDAO functionDAO = new FunctionDAO();
    static FunctionController functionController = new FunctionController();

    //check title issue ton tai chua/bi trung khong? 
    public static boolean checkIssueExist(Issue issue) throws Exception {
        List<Issue> issueList = issueDAO.getList();

        if (issueList.size() > 0) {
            for (int i = 0; i < issueList.size(); i++) {
                //check trung title
                if (issue.getIssue_title().compareToIgnoreCase(issueList.get(i).getIssue_title()) == 0
                        && issue.getAssignee_id() == issueList.get(i).getAssignee_id()
                        && issue.getTeam_id() == issueList.get(i).getTeam_id()
                        && issue.getDue_date().compareTo(issueList.get(i).getDue_date()) == 0
                        && issue.getGitlab_id() == issueList.get(i).getGitlab_id()) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //check Issue ton tai khong? 
    public static boolean checkIssueExistByID(List<Issue> issueList, int id) throws Exception {
//        List<Issue> issueList = idc.getList();

        if (issueList.size() > 0) {
            for (int i = 0; i < issueList.size(); i++) {
                if (issueList.get(i).getIssue_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //them issue moi
    public static void addIssue(List<Issue> list, List<Team> teamList, List<ClassUser> classUserList, List<Milestone> milestoneList, Issue issue) throws Exception {
//        List<Issue> issueList = idc.getList();
        //check mile stoen da ton tai:
        if (!checkIssueExist(issue)) {
            //check User ID da ton tai:
            if (classUserController.checkExistUserByID(classUserList, issue.getAssignee_id())) {
                //check Team ID da ton tai:
                if (teamController.checkTeamExistByID(teamList, issue.getTeam_id())) {
                    //check Milestone ID da ton tai
                    if (milestoneController.checkMileStoneExistByID(milestoneList, issue.getMilestone_id())) {
                        //check ngay thang hop le:
                        if (issue.getDue_date().compareTo(issue.getCreated_at()) > 0) {
                            //check hop le cach viet function IDs
                            if (issue.getFunction_ids().length()==0 || functionController.checkExistArrayFunctionID(getFunctions_ids(issue.getFunction_ids()))) {

                                list.add(issue);
                                issueDAO.insertIssueToList(issue);
                                System.out.println("Add successfully!");
                            } else {
                                throw new Exception("Some Function IDs NOT existed");
                            }
                        } else {
                            throw new Exception("Due date must be after today!");
                        }
                    } else {
                        throw new Exception("Enter Milestone ID in list showed only!");
                    }
                } else {
                    throw new Exception("Enter Team ID in list showed only!");
                }
            } else {
                throw new Exception("Enter User ID in list showed only!");
            }
        } else {
            throw new Exception("Exist Issue!");
        }
    }

//find Issue index
    public static int searchIssueIndexByID(int id) throws Exception {
        List<Issue> issueList = issueDAO.getList();
        if (issueList.size() > 0) {
            for (int i = 0; i < issueList.size(); i++) {
                if (issueList.get(i).getIssue_id() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    //fine issue by id
    public static Issue searchIssueByID(int id) throws Exception {
        List<Issue> issueList = issueDAO.getList();
        if (issueList.size() > 0) {
            for (int i = 0; i < issueList.size(); i++) {
                if (issueList.get(i).getIssue_id() == id) {
                    return issueList.get(i);
                }
            }
        }
        return null;
    }

    //update Issue
    public static void updateIssue(List<Issue> list, List<ClassUser> userList, List<Team> teamList, List<Milestone> milestoneList, Issue issue) throws Exception {
//        List<Issue> issueList = idc.getList();

        if (checkIssueExistByID(list, issue.getIssue_id())) {
            //check User ID da ton tai:
            if (classUserController.checkExistUserByID(userList, issue.getAssignee_id())) {
                //check Team ID da ton tai:
                if (teamController.checkTeamExistByID(issue.getTeam_id())) {
                    //check Milestone ID da ton tai
                    if (milestoneController.checkMileStoneExistByID(issue.getMilestone_id())) {
                        //check ngay thang hop le:
                        if (issue.getDue_date().compareTo(issue.getCreated_at()) > 0) {
//                            //check Functions IDS ton tại:
                            if ( issue.getFunction_ids().length()==0 || functionController.checkExistArrayFunctionID(getFunctions_ids(issue.getFunction_ids()))) {

                                list.set(searchIssueIndexByID(issue.getIssue_id()), issue);
                                //các hàm update:
                                issueDAO.updateIssueToList(issue);
                                System.out.println("Update successfully!");

                            } else {
                                throw new Exception("Function IDs issue not correct!");
                            }
                        } else {
                            throw new Exception("Due date must be after today!");
                        }
                    } else {
                        throw new Exception("Enter Milestone ID in list showed only!");
                    }
                } else {
                    throw new Exception("Enter Team ID in list showed only!");
                }
            } else {
                throw new Exception("Enter User ID in list showed only!");
            }
        } else {
            throw new Exception("Can NOT find Issue ID!");
        }

    }

    public static void changeIssueStatus(List<Issue> issueList, int id, int option) throws Exception {
        //    List<Issue> issueList = idc.getList();
        if (checkIssueExistByID(issueList, id)) {
            try {
                Issue issue = searchIssueByID(id);
                issue.setStatus(option);
                //    issueDAO.updateIssueToList(issue, "status", option + "", "int");
                issueDAO.updateIssueToList(issue);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new Exception("Can NOT change this Issue status!");
        }
    }

    //seach title- 1, gitlab_id - 2, created_at - 3,  due_date - 4, team_id - 5, Milestone id - 6, function ids - 7, status - 8
    public List<Issue> searchByPattern(String pattern, boolean student, int user_id) throws Exception {

        List<Issue> issuesList = new ArrayList<>();

        if (pattern.contains("/")) {
            String[] tachDMY = pattern.split("/");

            //nếu tách ra được đúng theo định dạng DMY
            if (tachDMY.length > 1) {
                // trường hợp ngày có 1 chữ số -> thêm số 0
                if (tachDMY[0].matches("\\d")) {
                    tachDMY[0] = "0" + tachDMY[0];
                }
                //trường hợp tháng có 1 chữ số -> thêm số 0
                if (tachDMY[1].matches("\\d")) {
                    tachDMY[1] = "0" + tachDMY[1];
                }
                pattern = tachDMY[0] + "/" + tachDMY[1];
                if (tachDMY.length == 3) {
                    pattern = pattern + "/" + tachDMY[2];
                }
            }
        }
        //chuyển tất cả về toLowerCase
        pattern = pattern.toLowerCase();
        int team_id = 0;
        if (student) {
            team_id = classUserController.getClassUserByUserID(user_id).getTeamID();
        }
        issuesList = issueDAO.getListForSearch(pattern, student, team_id);
        if (issuesList.isEmpty()) {
            throw new Exception("Empty list!");
        }
        return issuesList;
    }

    //sap xep Issue
    //Title(1), created_at(2), due_date(3),status(4)
    public List<Issue> sortIssue(boolean student, int user_id, int choice) throws Exception {
//        List<Issue> sl = new List<>();
        int team_id = 0;
        if (student) {
            team_id = classUserController.getClassUserByUserID(user_id).getTeamID();
        }
        switch (choice) {
            case 1: // so sánh Issue Title
                return issueDAO.getListForSort(student, team_id, "a.issue_title");
//                break;
            case 2: //so sánh created_at
                return issueDAO.getListForSort(student, team_id, "a.created_at");
            case 3: //so sanh due_date
                return issueDAO.getListForSort(student, team_id, "a.due_date");
            case 4: //team_id
                return issueDAO.getListForSort(student, team_id, "a.team_id");
            case 5: //so sanh status
                return issueDAO.getListForSort(student, team_id, "a.status");

            //nếu nhập khác thì exit sort
            default:
                System.out.println("Exit sort.");
                break;
        }
        return null;
        //    return sl;
    }

    //xử lí functions ids
    public static int[] getFunctions_ids(String funtcions_id) throws Exception {
        Scanner sc = new Scanner(System.in);

        //tach la dau phay
        String[] fids = funtcions_id.replaceAll("\\s+", "").split(",");

        //tạo array int báo size       
        int size = fids.length;
        int[] a = new int[size];
        int check = 0;
        try {
            for (int i = 0; i < size; i++) {
                //ktra định dạng phần tử nhận vào
                if (fids[i].matches("\\d+")) {
                    a[i] = Integer.parseInt(fids[i]);
                } else {
                    throw new Exception("Wrong input");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    public static boolean checkArrayFids(String funtcions_id) throws Exception {
        Scanner sc = new Scanner(System.in);

        //tach la dau phay
        String[] fids = funtcions_id.replaceAll("\\s+", "").split(",");

        //tạo array int báo size       
        int size = fids.length;
        int[] a = new int[size];
        for (int i = 0; i < size; i++) {
            //ktra định dạng phần tử nhận vào
            if (fids[i].matches("\\d+")) {
                a[i] = Integer.parseInt(fids[i]);
            } else {
                return false; //sai
            }
        }
        return true; //dúng
    }

    public List<Issue> pagination(List<Issue> list, int page) throws Exception {
        //set up list 5 để trả về
        List<Issue> issueList = new ArrayList<>();
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
                        issueList.add(list.get(i));
                    }
                }
                i++;
            } while (i < start + 5);

            return issueList;

        } else {
            throw new Exception("Can't not go to that page");
        }
    }

}
