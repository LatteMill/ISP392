
package model;
import controller.ClassController;
import controller.IterationController;
import java.util.*;
import dao.*;
import view.Utility;

public class Milestone {
    private int mileStone_id, iteration_id, class_id, status;
    private Date from_date, to_date;
    private String milestoneName;
    private String classname, iterationName;

    public Milestone() {
    }

    public Milestone(int mileStone_id, int iteration_id, int class_id, int status, Date from_date, Date to_date, String milestoneName) {
        this.mileStone_id = mileStone_id;
        this.iteration_id = iteration_id;
        this.class_id = class_id;
        this.status = status;
        this.from_date = from_date;
        this.to_date = to_date;
        this.milestoneName = milestoneName;
    }
    
     public Milestone(int iteration_id, int class_id, int status, Date from_date, Date to_date, String milestoneName) {
        this.mileStone_id = mileStone_id;
        this.iteration_id = iteration_id;
        this.class_id = class_id;
        this.status = status;
        this.from_date = from_date;
        this.to_date = to_date;
        this.milestoneName = milestoneName;
    }

    public Milestone(int mileStone_id, int iteration_id, int class_id, int status, Date from_date, Date to_date, String milestoneName, String classname, String iterationName) {
        this.mileStone_id = mileStone_id;
        this.iteration_id = iteration_id;
        this.class_id = class_id;
        this.status = status;
        this.from_date = from_date;
        this.to_date = to_date;
        this.milestoneName = milestoneName;
        this.classname = classname;
        this.iterationName = iterationName;
    }
     

    public int getMileStone_id() {
        return mileStone_id;
    }

    public void setMileStone_id(int mileStone_id) {
        this.mileStone_id = mileStone_id;
    }

    public int getIteration_id() {
        return iteration_id;
    }

    public void setIteration_id(int iteration_id) {
        this.iteration_id = iteration_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getFrom_date() {
        return from_date;
    }

    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    public Date getTo_date() {
        return to_date;
    }

    public void setTo_date(Date to_date) {
        this.to_date = to_date;
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
                finalStatus = "Cancelled";
                break;
        }
        return finalStatus;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getIterationName() {
        return iterationName;
    }

    public void setIterationName(String iterationName) {
        this.iterationName = iterationName;
    }

    
       
    @Override
    public String toString() {
        return String.format("%-5d|%-15s|%-15s|%-15s|%-15s|%-15s|%-10s",
                mileStone_id,
                milestoneName, 
                iterationName, 
                classname,
                Utility.convertDateToString(from_date), 
                Utility.convertDateToString(to_date), 
                getStatusString());
    }
     
    
}
