
package model;
import controller.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Subject {
    UserListController uc = new UserListController();
  
    private int subjectID;
    private String subjectCode;
    private String subjectName;
    private boolean status;
    private int author_id;
    private String description;
    

    public Subject() {
    }

    public Subject(int subjectID, String subjectCode, String subjectName, boolean status, int author_id, String description) {
        this.subjectID = subjectID;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.status = status;
        this.author_id = author_id;
        this.description = description;
    }

    public Subject(String subjectCode, String subjectName, boolean status, int author_id, String description) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.status = status;
        this.author_id = author_id;
        this.description = description;
    }
    

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

    
    public String getAuthor_Name() throws Exception {
        String author_name = uc.getUserListByID(author_id).getFullName();
        return author_name;
    }
     
    public Subject(int subjectID) {
        this.subjectID = subjectID;
    }
    

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getStatus() {
        String statusFinal = null;
        if (status == true) {
            statusFinal = "Active";
        }else{
            statusFinal = "Deactive";
        }
        return statusFinal;
    }

    @Override
    public String toString() {
        try {
            return String.format("%-5d|%-15s|%-30s|%-25s|%-20s|%-15s", subjectID, subjectCode, subjectName, getAuthor_Name(), getStatus(), getDescription() );
        } catch (Exception ex) {
            Logger.getLogger(Subject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
