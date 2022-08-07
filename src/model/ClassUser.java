/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.*;
import java.util.*;
import java.util.logging.*;
import view.Utility;

/**
 *
 * @author admin
 */
public class ClassUser {

    private int classID, teamID, userID, roles;
    private String fullName, email,teamName;
    private boolean teamLeader;
    private Date dropout_date;
    private String user_note;
    private int final_pres_eval;
    private int final_topic_eval;
    private int status;

    public ClassUser() {
    }

    public ClassUser(int classID, int teamID, int userID, boolean teamLeader, Date dropout_date, String user_note, int final_pres_eval, int final_topic_eval, int status) {
        this.classID = classID;
        this.teamID = teamID;
        this.userID = userID;
        this.teamLeader = teamLeader;
        this.dropout_date = dropout_date;
        this.user_note = user_note;
        this.final_pres_eval = final_pres_eval;
        this.final_topic_eval = final_topic_eval;
        this.status = status;
    }

    public ClassUser(int classID, int teamID, int userID, int roles, String fullName, String email, boolean teamLeader, Date dropout_date, String user_note, int final_pres_eval, int final_topic_eval, int status) {
        this.classID = classID;
        this.teamID = teamID;
        this.userID = userID;
        this.roles = roles;
        this.fullName = fullName;
        this.email = email;
        this.teamLeader = teamLeader;
        this.dropout_date = dropout_date;
        this.user_note = user_note;
        this.final_pres_eval = final_pres_eval;
        this.final_topic_eval = final_topic_eval;
        this.status = status;
    }

    public ClassUser(int classID, int teamID, int userID, int roles, boolean teamLeader, String user_note, int status) {
        this.classID = classID;
        this.teamID = teamID;
        this.userID = userID;
        this.roles = roles;
        this.teamLeader = teamLeader;
        this.user_note = user_note;
        this.status = status;
    }

    public ClassUser(int classID, int teamID, int user_id, String email, String fullName, boolean team_leader, int role, String user_notes, int statusNumber) {
        this.classID = classID;
        this.teamID = teamID;
        this.userID = userID;
        this.roles = roles;
        this.fullName = fullName;
        this.email = email;
        this.teamLeader = teamLeader;
        this.user_note = user_note;
        this.status = status;
    }

    public ClassUser(int classID, int teamID, int user_id, String email, String fullName, boolean team_leader, int role, int final_pres_eval, int final_topic_eval, String user_notes, int statusNumber) {
        this.classID = classID;
        this.teamID = teamID;
        this.userID = userID;
        this.roles = roles;
        this.fullName = fullName;
        this.email = email;
        this.teamLeader = teamLeader;
        this.user_note = user_note;
        this.final_pres_eval = final_pres_eval;
        this.final_topic_eval = final_topic_eval;
        this.status = status;
    }

    public int getRoles() {
        return roles;
    }

    public void setRoles(int roles) {
        this.roles = roles;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(boolean teamLeader) {
        this.teamLeader = teamLeader;
    }

    public Date getDropout_date() {
        return dropout_date;
    }

    public void setDropout_date(Date dropout_date) {
        this.dropout_date = dropout_date;
    }

    public String getUser_note() {
        return user_note;
    }

    public void setUser_note(String user_note) {
        this.user_note = user_note;
    }

    public int getFinal_pres_eval() {
        return final_pres_eval;
    }

    public void setFinal_pres_eval(int final_pres_eval) {
        this.final_pres_eval = final_pres_eval;
    }

    public int getFinal_topic_eval() {
        return final_topic_eval;
    }

    public void setFinal_topic_eval(int final_topic_eval) {
        this.final_topic_eval = final_topic_eval;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusString() {
        String finalStatus = null;
        switch (status) {
            case 1:
                finalStatus = "Active";
                break;
            case 2:
                finalStatus = "Inactive";
                break;
        }
        return finalStatus;
    }

    public String getCLassCode() throws Exception {
        String classCode = null;
        ClassDAO classDAO= new ClassDAO();
        List<Classroom> clr= classDAO.getList();
        for (Classroom o : clr) {
            if(o.getClass_id()== classID){
                classCode= o.getClass_code();
            }
        }
        return classCode;
    }
        public String getTeamName() throws Exception {
        String teamName = null;
        TeamDAO teamDAO= new TeamDAO();
        List<Team> tList= teamDAO.getList();
        for (Team o : tList) {
            if(o.getTeam_id()== teamID){
                teamName= o.getTeam_name();
            }
        }
        return teamName;
    }

    @Override
    public String toString() {
        try {
            return String.format("%-7s|%-10s|%-15s|%-7s|%-5s|%-25s|%-25s|%-10s|%-15s|%-7s|%-15s|%-16s|%-5s", classID, getCLassCode(), getTeamName(), userID, roles, fullName, email, isTeamLeader(),
                    Utility.convertDateToString(dropout_date), user_note, final_pres_eval,final_topic_eval,
                    getStatusString());
        } catch (Exception ex) {
            Logger.getLogger(ClassUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
