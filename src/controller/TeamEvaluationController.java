package controller;

import dao.*;
import java.util.*;
import model.*;

public class TeamEvaluationController {

    //Team Evaluation
    static TeamEvaluationDAO teamEvaluationDAO = new TeamEvaluationDAO();

    //Iteration_Evaluation
    //Criteria
    static CriteriaDAO criteriaDAO = new CriteriaDAO();
    static CriteriaController criteriaController = new CriteriaController();

    //Team
    static TeamDAO teamDao = new TeamDAO();
    static TeamController teamController = new TeamController();

    //check object ton tai chua/bi trung khong? 
    public static boolean checkTeamEvaluationExist(TeamEvaluation teamEvaluation) throws Exception {
        List<TeamEvaluation> teamEvaluationList = teamEvaluationDAO.getList();

        if (teamEvaluationList.size() > 0) {
            for (int i = 0; i < teamEvaluationList.size(); i++) {
                //check trung title
                if (teamEvaluation.getEvaluation_id() == teamEvaluationList.get(i).getEvaluation_id()
                        && teamEvaluation.getCriteria_id() == teamEvaluationList.get(i).getCriteria_id()
                        && teamEvaluation.getTeam_id() == teamEvaluationList.get(i).getTeam_id()) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //check TE ton tai khong? 
    public static boolean checkTeamEvaluationExistByID(List<TeamEvaluation> teamEvaluationList, int id) throws Exception {
        if (teamEvaluationList.size() > 0) {
            for (int i = 0; i < teamEvaluationList.size(); i++) {
                if (teamEvaluationList.get(i).getTeam_eval_ID() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //them issue moi
    public static void addTeamEvaluation(TeamEvaluation teamEvaluation) throws Exception {
        // check data ton tai chưa
        if (!checkTeamEvaluationExist(teamEvaluation)) {
            //check Iteration_id ( trong iteration_evaluation ) da ton tai:
            //    if (ite) {
            //check Team ID da ton tai:
            if (teamController.checkTeamExistByID(teamDao.getList(), teamEvaluation.getTeam_id())) {
                //check Milestone ID da ton tai
                if (criteriaController.checkCriteriaExistByID(criteriaDAO.getList(), teamEvaluation.getCriteria_id())) {
                    teamEvaluationDAO.getList().add(teamEvaluation);
                    teamEvaluationDAO.insertTeamEvaluationToSQL(teamEvaluation);
                    System.out.println("Add successfully!");
//                            
                } else {
                    throw new Exception("Enter Criteria ID in list showed only!!");
                }
            } else {
                throw new Exception("Enter Team ID in list showed only!");
            }
        } else {
            throw new Exception("Existed Team Evaluation!");
        }
    }

//find Team Evaluation index
    public static int searchTeamEvaluationIndexByID(int id) throws Exception {
        List<TeamEvaluation> teamEvaluationsList = teamEvaluationDAO.getList();
        if (teamEvaluationsList.size() > 0) {
            for (int i = 0; i < teamEvaluationsList.size(); i++) {
                if (teamEvaluationsList.get(i).getTeam_eval_ID() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    //find evaluation by id
    public TeamEvaluation searchTeamEvaluationByID(int id) throws Exception {
        List<TeamEvaluation> teamEvaluationList = teamEvaluationDAO.getList();
        if (teamEvaluationList.size() > 0) {
            for (int i = 0; i < teamEvaluationList.size(); i++) {
                if (teamEvaluationList.get(i).getTeam_eval_ID() == id) {
                    return teamEvaluationList.get(i);
                }
            }
        }
        return null;
    }

    //update TeamEvaluation
    public void updateTeamEvaluation(TeamEvaluation teamEvaluation) throws Exception {
        List<TeamEvaluation> teamEvaluationList = teamEvaluationDAO.getList();
        if (checkTeamEvaluationExistByID(teamEvaluationDAO.getList(), teamEvaluation.getTeam_eval_ID())) {
            //check Iteration_id ( trong iteration_evaluation ) da ton tai:
            //    if (ite) {
            //check Team ID da ton tai:
            if (teamController.checkTeamExistByID(teamDao.getList(), teamEvaluation.getTeam_id())) {
                //check Milestone ID da ton tai
                if (criteriaController.checkCriteriaExistByID(criteriaDAO.getList(), teamEvaluation.getCriteria_id())) {
                    
                    teamEvaluationList.set(searchTeamEvaluationIndexByID(teamEvaluation.getTeam_eval_ID()), teamEvaluation);
                    teamEvaluationDAO.updateTeamEvaluationToList(teamEvaluation);
                    System.out.println("Update successfully!");

                } else {
                    throw new Exception("Enter Criteria ID in list showed only!!");
                }
            } else {
                throw new Exception("Enter Team ID in list showed only!");
            }
        } else {
            throw new Exception("NOT existed Team Evaluation!");
        }

    }

    //seach title- 1, gitlab_id - 2, created_at - 3,  due_date - 4, team_id - 5, Milestone id - 6, function ids - 7, status - 8
    public List<TeamEvaluation> searchByPattern( String pattern, boolean student, int team_id) throws Exception {

        List<TeamEvaluation> teamEvaluationResult = new ArrayList<>();

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
        teamEvaluationResult = teamEvaluationDAO.getListForSearch(pattern,student, team_id);
        if (teamEvaluationResult.isEmpty()) {
            throw new Exception("Empty list!");
        }
        return teamEvaluationResult;
    }

    //sap xep TeamEvaluation
    public List<TeamEvaluation> sortTeamEvaluations(boolean student, int team_id, int choice) throws Exception {
//        List<Issue> sl = new List<>();
        switch (choice) {
            case 1: // so sánh evaluation_id
                return teamEvaluationDAO.getListForSort(student, team_id, "evaluation_id");
//                break;
            case 2: //so sánh Criteria_id
                return teamEvaluationDAO.getListForSort(student, team_id, "criteria_id");
            case 3: //so sanh Team_id
                return teamEvaluationDAO.getListForSort(student, team_id, "team_id");
            case 4: //Grade
                return teamEvaluationDAO.getListForSort(student, team_id, "grade");

            //nếu nhập khác thì exit sort
            default:
                System.out.println("Exit sort.");
                break;
        }
        return null;
        //    return sl;
    }

    public List<TeamEvaluation> pagination(List<TeamEvaluation> list, int page) throws Exception {
        //set up list 5 để trả về
        List<TeamEvaluation> teamEvaluationsList = new ArrayList<>();
        //setup biến bắt đầu
        int start = 5 * (page - 1);

        //số lượng page có
        int maxpage = list.size() / 5;
        if (list.size() % 5 != 0) {
            maxpage++;
        }

        //thỏa mãn điều kiện page nằm trong danh sách page
        if (0 < page && page <= maxpage) {

            //chạy vòng lặp ho 5 lần
            for (int i = start; i < start + 5; i++) {
//                System.out.println(list.get(i));
                if (i == list.size()) {
                    break;
                } else {
                    if (list.get(i) != null) {
                        teamEvaluationsList.add(list.get(i));
                    }
                }
            }

            return teamEvaluationsList;

        } else {
            throw new Exception("Can't not go to that page");
        }
    }
}
