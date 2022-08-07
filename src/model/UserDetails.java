/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Aldrin
 */
public class UserDetails {

    private String fullName, email, mobile, rollNumber, jobPosition, company, address;
    int id;
    int roles;
    private int sex;
    int status;
//    static int AUTO_INCREMENT = 1;

    public UserDetails() {
//        this.id = AUTO_INCREMENT++;
    }

    public UserDetails(int id, String fullName, String email, String mobile, String rollNumber, String jobPosition, String company, String address, int roles, int sex, int status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.rollNumber = rollNumber;
        this.jobPosition = jobPosition;
        this.company = company;
        this.address = address;
        this.roles = roles;
        this.sex = sex;
        this.status = status;
    }

    //fullName, mobile, jobPosition, company, address,roles,sex, status
    public UserDetails(String fullName, String email, String mobile, String jobPosition, String company, String address, int roles, int sex, int status) {
        this.fullName = fullName;
        this.email = email;
        this.rollNumber = rollNumber;
        this.mobile = mobile;
        this.jobPosition = jobPosition;
        this.company = company;
        this.address = address;
        this.id = id;
        this.roles = roles;
        this.sex = sex;
        this.status = status;
    }

    public UserDetails(String fullName, String email, String rollNumber, String mobile, String jobPosition, String company, String address, int roles, int sex, int status) {
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.jobPosition = jobPosition;
        this.company = company;
        this.address = address;
        this.id = id;
        this.roles = roles;
        this.sex = sex;
        this.status = status;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName != null) {
            this.fullName = fullName;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if (email != null) {
            if (email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                this.email = email;
            } else {
                throw new Exception("Wrong format.");
            }
        } else {
            throw new Exception("Email can't be empty.");
        }
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) throws Exception {
        if (mobile != null) {
            if (mobile.matches("[0][0-9]{9,10}")) {
                this.mobile = mobile;
            } else {
                throw new Exception("Wrong format.");
            }
        } else {
            throw new Exception("Mobile can't be empty.");
        }
    }

    public int getSex() {
        return sex;
    }

    public String getSexResult() {
        String sexResult = null;
        switch (sex) {
            case 1:
                sexResult = "Nam";
                break;
            case 2:
                sexResult = "Nữ";
                break;
            case 3:
                sexResult = "Chưa rõ";
                break;
        }
        return sexResult;
    }

    public void setSex(int sex) {
        switch (sex) {
            case 1:
                this.sex = sex;
                break;
            case 2:
                this.sex = sex;
                break;
            case 3:
                this.sex = sex;
                break;
            default:
                return;
        }
    }

    public int getRoles() {
        return roles;
    }

    public void setRoles(int roles) {
        this.roles = roles;
    }

    public String getRolesDetail() {
        String RolePosition = null;
        switch (roles) {
            case 1:
                RolePosition = "Admin";
                break;
            case 2:
                RolePosition = "Author/Manager";
                break;
            case 3:
                RolePosition = "Trainer";
                break;
            case 4:
                RolePosition = "Student";
                break;
        }
        return RolePosition;
    }

    public String getRoles(int roles) {
        String rolesTemp = null;
        switch (roles) {
            case 1:
                rolesTemp = "Role1";
                break;
            case 2:
                rolesTemp = "Role2";
                break;
            case 3:
                rolesTemp = "Role3";
                break;
            case 4:
                rolesTemp = "Role4";
                break;

        }
        return rolesTemp;
    }

    public String getStatus(int status) {
        String statusFinal = null;
        if (status == 1) {
            statusFinal = "Active";
        } else if (status == 2) {
            statusFinal = "Blocked";
        } else if (status == 3) {
            statusFinal = "Pending";
        }
        return statusFinal;
    }

    public String getStatusString() {
        String statusFinal = null;
        if (status == 1) {
            statusFinal = "Active";
        } else if (status == 2) {
            statusFinal = "Blocked";
        } else if (status == 3) {
            statusFinal = "Pending";
        }
        return statusFinal;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("%-10|%-10|%-20s|%-20s|%-20s|%-6s|%-10s|%-10s|%-20s|"
                + "%-10s|%-10s", id, rollNumber, fullName, email, mobile, getSex(), getRoles(),
                getStatus(status), jobPosition, company, address);
    }
}
