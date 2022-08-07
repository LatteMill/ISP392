/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.*;
import view.Utility;
/**
 *
 * @author Admin
 */
public class Feature {

    private int feature_id, team_id;
    private String feature_name;
    private int status;

    public Feature() {
    }

    public Feature(int team_id, String feature_name, int status) {
        this.feature_id = feature_id;
        this.team_id = team_id;
        this.feature_name = feature_name;
        this.status = status;
    }

    public Feature(int feature_id, int team_id, String feature_name, int status) {
        this.feature_id = feature_id;
        this.team_id = team_id;
        this.feature_name = feature_name;
        this.status = status;
    }

  

    public int getFeature_id() {
        return feature_id;
    }

    public void setFeature_id(int feature_id) {
        this.feature_id = feature_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getFeature_name() {
        return feature_name;
    }

    public void setFeature_name(String feature_name) {
        this.feature_name = feature_name;
    }

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusString() {
        String finalStatus = null;
        switch (status) {
            case 1:

                finalStatus = "Active";
                break;
            case 2:
                finalStatus = "InActive";
                break;
            

        }
        return finalStatus;
    }

    @Override
    public String toString() {
        return String.format("%-5s|%-15s|%-15s|%-10s", feature_id,
                feature_name, team_id, getStatusString());
    }
}
