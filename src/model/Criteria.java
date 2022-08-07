/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.IterationController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leeph
 */
public class Criteria {

    IterationController iterController = new IterationController();
    
    int criteria_id, iteration_id, criteria_order, max_loc, team_evaluation, status;
    double evaluation_weight;
    //team_evaluation int

    public Criteria() {
    }

    public Criteria(int criteria_id ,int iteration_id, double evaluation_weight, int team_evaluation, int criteria_order, int max_loc, int status) {
        this.criteria_id = criteria_id;
        this.iteration_id = iteration_id;
        this.evaluation_weight = evaluation_weight;
        this.team_evaluation = team_evaluation;
        this.criteria_order = criteria_order;
        this.max_loc = max_loc;
        this.status = status;
    }
    public Criteria(int iteration_id, double evaluation_weight, int team_evaluation, int criteria_order, int max_loc, int status) {
        this.criteria_id = criteria_id;
        this.iteration_id = iteration_id;
        this.evaluation_weight = evaluation_weight;
        this.team_evaluation = team_evaluation;
        this.criteria_order = criteria_order;
        this.max_loc = max_loc;
        this.status = status;
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

    public String getIterationName() throws Exception{
        String iterName = iterController.getIterationListByID(iteration_id).getIteration_name();
        return iterName;
    }
    
    public void setIteration_id(int iteration_id) {
        this.iteration_id = iteration_id;
    }

    public int getTeam_evaluation() {
        return team_evaluation;
    }

    public String getTeamString() {
        String teamResult = null;
        switch(team_evaluation){
            case 1:
                teamResult = "Yes";
                break;
            case 2:
                teamResult = "No";
                break;
        }
        return teamResult;
    }

    public void setTeam_evaluation(int team_evaluation) {
        this.team_evaluation = team_evaluation;
    }

    public int getCriteria_order() {
        return criteria_order;
    }

    public void setCriteria_order(int criteria_order) {
        this.criteria_order = criteria_order;
    }

    public int getMax_loc() {
        return max_loc;
    }

    public void setMax_loc(int max_loc) {
        this.max_loc = max_loc;
    }

    public double getEvaluation_weight() {
        return evaluation_weight;
    }

    public void setEvaluation_weight(double evaluation_weight) {
        this.evaluation_weight = evaluation_weight;
    }

    public int isStatus() {
        return status;
    }

    public String getStatus() {
        String statusResult = null;
        switch(status){
            case 1:
                statusResult = "Active";
                break;
            case 2:
                statusResult = "Deactive";
                break;
        }
        return statusResult;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        try { 
            return String.format("%-5s|%-10s|%-8s|%-15s|%-6s|%-5s|%-6s",
                    criteria_id, getIterationName(), evaluation_weight, getTeamString(), criteria_order, max_loc, getStatus());
        } catch (Exception ex) {
            Logger.getLogger(Criteria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
