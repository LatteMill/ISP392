/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author admin
 */
public class UserList extends UserDetails {

    private String lastLogin, action;

    public UserList() {
    }

    public UserList(String lastLogin, String action, String fullName, String email, String mobile, String rollNumber, String jobPosition, String company, String address, int roles, int id, int sex, int status) {
        super(id, fullName, email, mobile, rollNumber, jobPosition, company, address, roles, sex, status);
        this.lastLogin = lastLogin;
        this.action = action;
    }

    public UserList(String fullName, String email, String mobile, int sex, int roles, String jobPosition, int status, String company, String address, String lastLogin) {
        super(fullName, email, mobile, jobPosition, company, address, roles, sex, status);
        this.lastLogin = lastLogin;
    }

    public UserList(String fullName, String email, String rollNumber, String mobile, int sex, int roles, String jobPosition, int status, String company, String address, String lastLogin) {
        super(fullName, email, rollNumber, mobile, jobPosition, company, address, roles, sex, status);
        this.lastLogin = lastLogin;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public String getAction(int status) {
        String actionTemp = null;
        switch (status) {
            case 1:
                actionTemp = "Edit  Block";
                break;
            case 2:
                actionTemp = "Edit  Unblock";
                break;
            case 3:
                actionTemp = "Edit  Approve";

        }
        return actionTemp;

    }

    public void setAction(int status) {
        this.action = getAction(status);
    }

    @Override
    public String toString() {
        return String.format("%-5s|%-20s|%-30s|%-20s|%-15s|%-20s|%-10s|%-10s", getId(), getFullName(), getEmail(), getMobile(),
                getRolesDetail(), lastLogin, getStatus(status), getAction(status));
    }

}
