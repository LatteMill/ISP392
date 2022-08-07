package model;

import controller.*;

public class Team {

    ClassController classController = new ClassController();

    private int team_id, class_id;
    private String team_name, topic_code, topic_name, gitlab_url;
    private boolean status;


    public Team() {
    }

    public Team(int team_id, int class_id, boolean status, String topic_code, String topic_name, String gitlab_url, String team_name) {
        this.team_id = team_id;
        this.class_id = class_id;
        this.status = status;
        this.topic_code = topic_code;
        this.topic_name = topic_name;
        this.gitlab_url = gitlab_url;
        this.team_name = team_name;
    }

    public Team(int class_id, boolean status, String topic_code, String topic_name, String gitlab_url, String team_name) {
        this.team_id = team_id;
        this.class_id = class_id;
        this.status = status;
        this.topic_code = topic_code;
        this.topic_name = topic_name;
        this.gitlab_url = gitlab_url;
        this.team_name = team_name;
    }

    public Team(int teamID) {
        this.team_id = team_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTopic_code() {
        return topic_code;
    }

    public void setTopic_code(String topic_code) {
        this.topic_code = topic_code;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getGitlab_url() {
        return gitlab_url;
    }

    public void setGitlab_url(String gitlab_url) {
        this.gitlab_url = gitlab_url;
    }

    public String getStatusString() {
        String statusFinal = null;
        if (status == true) {
            statusFinal = "Active";
        } else {
            statusFinal = "Inactive";
        }
        return statusFinal;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    @Override
    public String toString() {
        return String.format("%-10d|%-15s|%-10d|%-20s|%-30s|%-30s|%-20s",
                team_id,
                team_name,
                class_id,
                topic_code,
                topic_name,
                gitlab_url,
                getStatusString());
    }

}
