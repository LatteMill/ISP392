/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author leeph
 */
public class Iteration {

    int iteration_id, subject_id;
    String iteration_name;
    double duration;
    int status;
    static int AUTO_INCREMENT = 1;

    public Iteration() {
        this.iteration_id = iteration_id;
    }

    public Iteration(int subject_id, String iteration_name, double duration, int status) {
        this.iteration_id = iteration_id;
        this.subject_id = subject_id;
        this.iteration_name = iteration_name;
        this.duration = duration;
        this.status = status;
    }

    public int getIteration_id() {
        return iteration_id;
    }

    public void setIteration_id(int iteration_id) {
        this.iteration_id = iteration_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getIteration_name() {
        return iteration_name;
    }

    public void setIteration_name(String iteration_name) {
        this.iteration_name = iteration_name;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
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
        return String.format("%-5s|%-15s|%-15s|%-15s|%-10s\n", iteration_id,
                iteration_name, subject_id, duration, getStatusString());
    }

}
