/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author leeph
 */
public class Classroom {
    UserListController uc = new UserListController();
    SubjectController subjectController = new SubjectController();
    
    int class_id, trainer_id, subject_id, class_year, class_term, block5_class, status;
    String class_code;
    // block5_class int

    public Classroom() {
    }

    public Classroom(int class_id, String class_code, int trainer_id, int subject_id, int class_year, int class_term, int block5_class, int status) {
        this.class_id = class_id;
        this.class_code = class_code;
        this.trainer_id = trainer_id;
        this.subject_id = subject_id;
        this.class_year = class_year;
        this.class_term = class_term;
        this.block5_class = block5_class;
        this.status = status;
    }

    public Classroom(String class_code, int trainer_id, int subject_id, int class_year, int class_term, int block5_class, int status) {
        this.class_id = class_id;
        this.class_code = class_code;
        this.trainer_id = trainer_id;
        this.subject_id = subject_id;
        this.class_year = class_year;
        this.class_term = class_term;
        this.block5_class = block5_class;
        this.status = status;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(int trainer_id) {
        this.trainer_id = trainer_id;
    }

    public String getTrainerName() throws Exception{
        String trainerName = uc.getUserListByID(trainer_id).getFullName();
        return trainerName;
    }
    
    public int getSubject_id() {
        return subject_id;
    }

    public String getSubjectName() throws Exception{
        String subjectName = subjectController.getSubjectListByID(subject_id).getSubjectName();
        return subjectName;
    }
    
    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getClass_year() {
        return class_year;
    }

    public void setClass_year(int class_year) {
        this.class_year = class_year;
    }

    public int getClass_term() {
        return class_term;
    }

    public String getClassTermString() {
        String classString = null;
        switch (class_term) {
            case 1:
                classString = "Spring";
                break;
            case 2:
                classString = "Summer";
                break;
            case 3:
                classString = "Fall";
                break;
        }
        return classString;
    }

    public void setClass_term(int class_term) {
        this.class_term = class_term;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public int getBlock5_class() {
        return block5_class;
    }

    public String getBlock5String() {
        String classString = null;
        switch (block5_class) {
            case 1:
                classString = "Yes";
                break;
            case 2:
                classString = "No";
                break;
        }
        return classString;
    }

    public void setBlock5_class(int block5_class) {
        this.block5_class = block5_class;
    }

    public int isStatus() {
        return status;
    }

    public String getStatus() {
        String statusResult = null;
        switch (status) {
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
            return String.format("%-5s|%-6s|%-30s|%-20s|%-8s|%-5s|%-14s|%-8s", class_id, class_code, getTrainerName(),
                    getSubjectName(), class_year, getClassTermString(), getBlock5String(), getStatus());
        } catch (Exception ex) {
            Logger.getLogger(Classroom.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
