/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class Function {
    private int function_id,team_id;
    private String function_name,team_name,feature_name;        
    private int  feature_id;
    private String description,priority,access_roles;
    private int complexity_id,owner_id;
 //   private String ;
    private int status;

    public Function() {
    }

public Function( int team_id, String function_name, int feature_id, String access_role, String description, int complexity_id, int owner_id, String priority, int status) {
        this.function_id = function_id;
        this.team_id = team_id;
        this.function_name = function_name;
        this.feature_id = feature_id;
        this.access_roles = access_roles;
        this.description = description;
        this.complexity_id = complexity_id;
        this.owner_id = owner_id;
        this.priority = priority;
        this.status = status;
    }
    public Function(int function_id, int team_id, String function_name, int feature_id, String access_role, String description, int complexity_id, int owner_id, String priority, int status) {
        this.function_id = function_id;
        this.team_id = team_id;
        this.function_name = function_name;
        this.feature_id = feature_id;
        this.access_roles = access_roles;
        this.description = description;
        this.complexity_id = complexity_id;
        this.owner_id = owner_id;
        this.priority = priority;
        this.status = status;
    }

    public Function(int function_id, int team_id, String function_name, String team_name, String feature_name, int feature_id, String description, String priority, String access_roles, int complexity_id, int owner_id, int status) {
        this.function_id = function_id;
        this.team_id = team_id;
        this.function_name = function_name;
        this.team_name = team_name;
        this.feature_name = feature_name;
        this.feature_id = feature_id;
        this.description = description;
        this.priority = priority;
        this.access_roles = access_roles;
        this.complexity_id = complexity_id;
        this.owner_id = owner_id;
        this.status = status;
    }

    public Function(int id, int team_id, String function_name, String access_roles, String description, int complexity_id, int owner_id, String priority, int status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getFeature_name() {
        return feature_name;
    }

    public void setFeature_name(String feature_name) {
        this.feature_name = feature_name;
    }

    public int getFunction_id() {
        return function_id;
    }

    public void setFunction_id(int function_id) {
        this.function_id = function_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getFunction_name() {
        return function_name;
    }

    public void setFunction_name(String function_name) {
        this.function_name = function_name;
    }

    public int getFeature_id() {
        return feature_id;
    }

    public void setFeature_id(int feature_id) {
        this.feature_id = feature_id;
    }

    public void setAccess_roles(String access_roles) {
        this.access_roles = access_roles;
    }

    public String getAccess_roles() {
        return access_roles;
    }

 

   

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getComplexity_id() {
        return complexity_id;
    }

    public void setComplexity_id(int complexity_id) {
        this.complexity_id = complexity_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getPriority() {
        return priority;
    }
 public int isStatus() {
        return status;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusString(){
        String finalStatus = null;
        switch(status){
            case 1:
                finalStatus = "Pending";
                break;
            case 2:
                finalStatus = "Planned";
                break;
            case 3:
                finalStatus = "Evaluated";
                break;
            case 4:
                finalStatus = "Rejected";

                break;
        }
        return finalStatus;
    }
    public String displayforStudent(){
        return String.format("%-11s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-10s",function_id ,team_id ,function_name ,feature_id ,access_roles,complexity_id ,owner_id ,priority ,getStatusString());
    }
    @Override
    public String toString() {
        return String.format("%-11s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-10s",
                function_id ,team_name ,function_name ,feature_name ,access_roles,complexity_id ,owner_id ,priority ,getStatusString());
    }
   
}

