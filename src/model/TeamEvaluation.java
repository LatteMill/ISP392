
package model;

public class TeamEvaluation {
    private int team_eval_ID, evaluation_id, criteria_id, team_id;
    private float grade;
    private String note;
    private String teamName;

    public TeamEvaluation() {
    }

    public TeamEvaluation(int team_eval_ID, int evaluation_id, int criteria_id, int team_id, float grade, String note) {
        this.team_eval_ID = team_eval_ID;
        this.evaluation_id = evaluation_id;
        this.criteria_id = criteria_id;
        this.team_id = team_id;
        this.grade = grade;
        this.note = note;
    }
    
    public TeamEvaluation(int evaluation_id, int criteria_id, int team_id, float grade, String note) {
        this.evaluation_id = evaluation_id;
        this.criteria_id = criteria_id;
        this.team_id = team_id;
        this.grade = grade;
        this.note = note;
    }

    public TeamEvaluation(int team_eval_ID, int evaluation_id, int criteria_id, int team_id, float grade, String note, String teamName) {
        this.team_eval_ID = team_eval_ID;
        this.evaluation_id = evaluation_id;
        this.criteria_id = criteria_id;
        this.team_id = team_id;
        this.grade = grade;
        this.note = note;
        this.teamName = teamName;
    }
    

    public int getTeam_eval_ID() {
        return team_eval_ID;
    }

    public void setTeam_eval_ID(int team_eval_ID) {
        this.team_eval_ID = team_eval_ID;
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

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    

    @Override
    public String toString() {
        return String.format("%-5d|%-20d|%-15d|%-15s|%-10.2f|%-20s",
                team_eval_ID, 
                evaluation_id, 
                criteria_id, 
                teamName, 
                grade, 
                note);
    }
    
    
    
}
