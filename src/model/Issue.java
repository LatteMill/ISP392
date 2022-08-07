package model;

import java.util.*;
import view.*;
import controller.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Issue {

    private int issue_id, assignee_id, gitlab_id, team_id, milestone_id, status;
    private String issue_title, description, labels, gitlab_url, function_ids;
    private Date created_at, due_date;

    private String assigneeName, milestoneName, teamName;

    public Issue() {
    }

    public Issue(int issue_id, int assignee_id, int gitlab_id, int team_id, int milestone_id, String function_ids, int status, String issue_title, String description, String labels, String gitlab_url, Date created_at, Date due_date) {
        this.issue_id = issue_id;
        this.assignee_id = assignee_id;
        this.gitlab_id = gitlab_id;
        this.team_id = team_id;
        this.milestone_id = milestone_id;
        this.function_ids = function_ids;
        this.status = status;
        this.issue_title = issue_title;
        this.description = description;
        this.labels = labels;
        this.gitlab_url = gitlab_url;
        this.created_at = created_at;
        this.due_date = due_date;
    }

    public Issue(int assignee_id, int gitlab_id, int team_id, int milestone_id, String function_ids, int status, String issue_title, String description, String labels, String gitlab_url, Date created_at, Date due_date) {
        this.assignee_id = assignee_id;
        this.gitlab_id = gitlab_id;
        this.team_id = team_id;
        this.milestone_id = milestone_id;
        this.function_ids = function_ids;
        this.status = status;
        this.issue_title = issue_title;
        this.description = description;
        this.labels = labels;
        this.gitlab_url = gitlab_url;
        this.created_at = created_at;
        this.due_date = due_date;
    }

    public Issue(int issue_id, int assignee_id, int gitlab_id, int team_id, int milestone_id, int status, String issue_title, String description, String labels, String gitlab_url, String function_ids, Date created_at, Date due_date, String assigneeName, String milestoneName, String teamName) {
        this.issue_id = issue_id;
        this.assignee_id = assignee_id;
        this.gitlab_id = gitlab_id;
        this.team_id = team_id;
        this.milestone_id = milestone_id;
        this.status = status;
        this.issue_title = issue_title;
        this.description = description;
        this.labels = labels;
        this.gitlab_url = gitlab_url;
        this.function_ids = function_ids;
        this.created_at = created_at;
        this.due_date = due_date;
        this.assigneeName = assigneeName;
        this.milestoneName = milestoneName;
        this.teamName = teamName;
    }
    
    

    public int getIssue_id() {
        return issue_id;
    }

    public void setIssue_id(int issue_id) {
        this.issue_id = issue_id;
    }

    public int getAssignee_id() {
        return assignee_id;
    }

    public void setAssignee_id(int assignee_id) {
        this.assignee_id = assignee_id;
    }

    public int getGitlab_id() {
        return gitlab_id;
    }

    public void setGitlab_id(int gitlab_id) {
        this.gitlab_id = gitlab_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(int milestone_id) {
        this.milestone_id = milestone_id;
    }

    public String getFunction_ids() {
        return function_ids;
    }

    public void setFunction_ids(String function_ids) {
        this.function_ids = function_ids;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIssue_title() {
        return issue_title;
    }

    public void setIssue_title(String issue_title) {
        this.issue_title = issue_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public String getGitlab_url() {
        return gitlab_url;
    }

    public void setGitlab_url(String gitlab_url) {
        this.gitlab_url = gitlab_url;
    }

    public String getStatusString() {
        String finalStatus = null;
        switch (status) {
            case 1:
                finalStatus = "Open";
                break;
            case 2:
                finalStatus = "Closed";
                break;
            case 3:
                finalStatus = "Pending";
                break;
        }
        return finalStatus;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    

    @Override
    public String toString() {
            return String.format("%-5d|%-20s|%-20s|%-20s|%-20s|%-20s|%-20s|%-20s|%-10s",
                    issue_id,
                    assigneeName,
                    issue_title,
                    Utility.convertDateToString(due_date),
                    teamName,
                    milestoneName,
                    function_ids,
                    labels,
                    getStatusString());
    }

}
