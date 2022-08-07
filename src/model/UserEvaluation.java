/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.*;
import dao.ClassDAO;
import dao.CriteriaDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class UserEvaluation {

    IterationController iterController = new IterationController();
    UserListController userListController = new UserListController();
    CriteriaDAO criteriaDAO = new CriteriaDAO();

    private int member_eval_id, evaluation_id, criteria_id, iteration_id, class_id, team_id, user_id;
    private String fullName;
    private int max_loc;
    private double converted_loc, bonus, grade, totalGrade;
    private double percent;
    private String notes;

    public UserEvaluation() {
    }

    public int getMember_eval_id() {
        return member_eval_id;
    }

    public void setMember_eval_id(int member_eval_id) {
        this.member_eval_id = member_eval_id;
    }

    public int getEvaluation_id() {
        return evaluation_id;
    }

    public void setEvaluation_id(int evaluation_id) {
        this.evaluation_id = evaluation_id;
    }

    public int getCriteria_id() {
        return criteria_id;
    }

    public void setCriteria_id(int criteria_id) {
        this.criteria_id = criteria_id;
    }

    public int getIteration_id() {
        return iteration_id;
    }

    public void setIteration_id(int iteration_id) {
        this.iteration_id = iteration_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClassCode() throws Exception {
        String classCode = null;
        ClassDAO classDAO = new ClassDAO();
        List<Classroom> clr = classDAO.getList();
        for (Classroom o : clr) {
            if (o.getClass_id() == class_id) {
                classCode = o.getClass_code();
            }
        }
        return classCode;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNameString() throws Exception {
        UserList userName = userListController.getUserListByID(user_id);
        return userName.getFullName();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getMax_loc() {
        return max_loc;
    }

    public void setMax_loc(int max_loc) {
        this.max_loc = max_loc;
    }

    public double getConverted_loc() throws Exception {
        converted_loc= Math.round((percent/max_loc) * 10) / 10.0;
        return converted_loc;
    }

    public void setConverted_loc(double converted_loc) {
        this.converted_loc = converted_loc;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getTotalGrade() {
        totalGrade = bonus + grade + converted_loc;
        if (totalGrade > 10) {
            return 10;
        } else {
            return Math.round(totalGrade*10)/10.0;
        }
    }

    public void setTotalGrade(double totalGrade) {

        this.totalGrade = totalGrade;

    }

    public String getIterationName() throws Exception {
        String iterName = iterController.getIterationListByID(iteration_id).getIteration_name();
        return iterName;
    }

    public UserEvaluation(int member_eval_id, int evaluation_id, int criteria_id, int iteration_id, int class_id, int team_id, int user_id, String fullName, int max_loc, double converted_loc, double bonus, double grade, double totalGrade, String notes) {
        this.member_eval_id = member_eval_id;
        this.evaluation_id = evaluation_id;
        this.criteria_id = criteria_id;
        this.iteration_id = iteration_id;
        this.class_id = class_id;
        this.team_id = team_id;
        this.user_id = user_id;
        this.fullName = fullName;
        this.max_loc = max_loc;
        this.converted_loc = converted_loc;
        this.bonus = bonus;
        this.grade = grade;
        this.totalGrade = totalGrade;
        this.notes = notes;
    }

    public UserEvaluation(int member_eval_id, int evaluation_id, int criteria_id, int iteration_id, int class_id, int team_id, int user_id, String fullName, int max_loc, double bonus, double grade, String notes) {
        this.member_eval_id = member_eval_id;
        this.evaluation_id = evaluation_id;
        this.criteria_id = criteria_id;
        this.iteration_id = iteration_id;
        this.class_id = class_id;
        this.team_id = team_id;
        this.user_id = user_id;
        this.fullName = fullName;
        this.max_loc = max_loc;
        this.bonus = bonus;
        this.grade = grade;
        this.notes = notes;
    }

//    @Override
//    public String toString() {
//        return "UserEvaluation{" + "member_eval_id=" + member_eval_id + ", evaluation_id=" + evaluation_id + ", criteria_id=" + criteria_id + ", iteration_id=" + iteration_id + ", class_id=" + class_id + ", team_id=" + team_id + ", user_id=" + user_id + ", fullName=" + fullName + ", max_loc=" + max_loc + ", converted_loc=" + converted_loc + ", bonus=" + bonus + ", grade=" + grade + ", totalGrade=" + totalGrade + ", notes=" + notes + '}';
//    }
    @Override
    public String toString() {
        try {
            return String.format("|%-5s|%-12s|%-12s|%-12s|%-7s|%-7s|%-7s|%-20s|%-10s|%-12s|%-12s|%-5s",
                    member_eval_id, evaluation_id, criteria_id, getIterationName(), getClassCode(), team_id, user_id, getNameString(), max_loc, getConverted_loc(), getTotalGrade(), notes);
        } catch (Exception ex) {
            Logger.getLogger(UserEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
