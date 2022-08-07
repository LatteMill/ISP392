/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.*;
/**
 *
 * @author admin
 */

import dao.FunctionDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tracking {

    private int trackingID, teamID, milestoneID, functionID, assignerID;
    private String assignerName;
    private int assigneeID;
    private String assigneeName;
    private String trackingNote;
    private boolean update;
    private int status;

    public Tracking() {
    }

    public Tracking(int trackingID, int teamID, int milestoneID, int functionID, int assignerID, int assigneeID, String trackingNote, boolean update, int status) {
        this.trackingID = trackingID;
        this.teamID = teamID;
        this.milestoneID = milestoneID;
        this.functionID = functionID;
        this.assignerID = assignerID;
        this.assigneeID = assigneeID;
        this.trackingNote = trackingNote;
        this.update = update;
        this.status = status;
    }

    public Tracking(int trackingID, int teamID, int milestoneID, int functionID, int assignerID, String assignerName, int assigneeID, String assigneeName, String trackingNote, boolean update, int status) {
        this.trackingID = trackingID;
        this.teamID = teamID;
        this.milestoneID = milestoneID;
        this.functionID = functionID;
        this.assignerID = assignerID;
        this.assignerName = assignerName;
        this.assigneeID = assigneeID;
        this.assigneeName = assigneeName;
        this.trackingNote = trackingNote;
        this.update = update;
        this.status = status;
    }

    public Tracking(int teamID, int milestoneID, int functionID, int assignerID, int assigneeID, String trackingNote, boolean update, int status) {
        this.teamID = teamID;
        this.milestoneID = milestoneID;
        this.functionID = functionID;
        this.assignerID = assignerID;
        this.assignerName = assignerName;
        this.assigneeID = assigneeID;
        this.assigneeName = assigneeName;
        this.trackingNote = trackingNote;
        this.update = update;
        this.status = status;
    }

    public Tracking(int teamID) {
        this.teamID = teamID;
    }

    public int getTrackingID() {
        return trackingID;
    }

    public void setTrackingID(int trackingID) {
        this.trackingID = trackingID;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public int getMilestoneID() {
        return milestoneID;
    }

    public void setMilestoneID(int milestoneID) {
        this.milestoneID = milestoneID;
    }

    public int getFunctionID() {
        return functionID;
    }

    public void setFunctionID(int functionID) {
        this.functionID = functionID;
    }

    public int getAssignerID() {
        return assignerID;
    }

    public void setAssignerID(int assignerID) {
        this.assignerID = assignerID;
    }

    public String getAssignerName() {
        return assignerName;
    }

    public void setAssignerName(String assignerName) {
        this.assignerName = assignerName;
    }

    public int getAssigneeID() {
        return assigneeID;
    }

    public void setAssigneeID(int assigneeID) {
        this.assigneeID = assigneeID;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getTrackingNote() {
        return trackingNote;
    }

    public void setTrackingNote(String trackingNote) {
        this.trackingNote = trackingNote;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
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
                finalStatus = "Planned";
                break;
            case 2:
                finalStatus = "Analysed";
                break;
            case 3:
                finalStatus = "Designed";
                break;
            case 4:
                finalStatus = "Coded";
                break;
            case 5:
                finalStatus = "Integrated";
                break;
            case 6:
                finalStatus = "Submitted";
                break;
            case 7:
                finalStatus = "Evaluated";
                break;
            case 8:
                finalStatus = "Rejected";
                break;

        }
        return finalStatus;
    }

    public String getFunctionName() throws Exception {
        String functionName = null;
        List<Function> fList = FunctionDAO.getFuncList();
        for (Function function : fList) {
            if (function.getFunction_id() == functionID) {
                functionName = function.getFunction_name();
            }
        }
        return functionName;
    }

    @Override
    public String toString() {
        try {
            return String.format("|%-5s|%-7s|%-12s|%-15s|%-10s|%-20s|%-10s|%-20s|%-10s|%-7s|%-5s", trackingID, teamID, milestoneID, getFunctionName(),
                    assignerID, assignerName, assigneeID, assigneeName, trackingNote, isUpdate(), getStatusString());
        } catch (Exception ex) {
            Logger.getLogger(Tracking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
