/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.TrackingController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leeph
 */
public class Loc {

    int evaluation_id, complexity_id, quality_id, tracking_id;
    double evaluation_time, converted_loc;
    String evaluation_note;

    TrackingController trackingController = new TrackingController();

    public Loc() {
    }

    public Loc(int evaluation_id, int complexity_id, int quality_id, int tracking_id, double converted_loc, double evaluation_time, String evaluation_note) {
        this.evaluation_id = evaluation_id;
        this.complexity_id = complexity_id;
        this.quality_id = quality_id;
        this.tracking_id = tracking_id;
        this.converted_loc = converted_loc;
        this.evaluation_time = evaluation_time;
        this.evaluation_note = evaluation_note;
    }

    public Loc(int complexity_id, int quality_id, int tracking_id, double converted_loc, double evaluation_time, String evaluation_note) {
        this.evaluation_id = evaluation_id;
        this.complexity_id = complexity_id;
        this.quality_id = quality_id;
        this.tracking_id = tracking_id;
        this.converted_loc = converted_loc;
        this.evaluation_time = evaluation_time;
        this.evaluation_note = evaluation_note;
    }
    
    public Loc(int complexity_id, int quality_id, int tracking_id, double evaluation_time, String evaluation_note) {
        this.evaluation_id = evaluation_id;
        this.complexity_id = complexity_id;
        this.quality_id = quality_id;
        this.tracking_id = tracking_id;
        this.converted_loc = converted_loc;
        this.evaluation_time = evaluation_time;
        this.evaluation_note = evaluation_note;
    }

    public int getEvaluation_id() {
        return evaluation_id;
    }

    public void setEvaluation_id(int evaluation_id) {
        this.evaluation_id = evaluation_id;
    }

    public int getComplexity_id() {
        return complexity_id;
    }

    public String getComplexityString() {
        String ComplexityString = null;
        switch (quality_id) {
            case 1:
                ComplexityString = "Simple";
                break;
            case 2:
                ComplexityString = "Medium";
                break;
            case 3:
                ComplexityString = "Complex";
                break;
        }
        return ComplexityString;
    }

    public int getComplexityLoc() {
        int complexityLoc = 0;
        switch (quality_id) {
            case 1:
                complexityLoc = 60;
                break;
            case 2:
                complexityLoc = 120;
                break;
            case 3:
                complexityLoc = 180;
                break;
        }
        return complexityLoc;
    }

    public void setComplexity_id(int complexity_id) {
        this.complexity_id = complexity_id;
    }

    public int getQuality_id() {
        return quality_id;
    }

    public String getQualityString() {
        String qualityString = null;
        switch (quality_id) {
            case 1:
                qualityString = "Zero";
                break;
            case 2:
                qualityString = "Low";
                break;
            case 3:
                qualityString = "Medium";
                break;
            case 4:
                qualityString = "High";
                break;
        }
        return qualityString;
    }

    public double getQualityPercent() {
        double qualityPercent = 0;
        switch (quality_id) {
            case 1:
                qualityPercent = 0.25;
                break;
            case 2:
                qualityPercent = 0.5;
                break;
            case 3:
                qualityPercent = 0.75;
                break;
            case 4:
                qualityPercent = 1;
                break;
        }
        return qualityPercent;
    }

    public void setQuality_id(int quality_id) {
        this.quality_id = quality_id;
    }

    public int getTracking_id() throws Exception {
        return tracking_id;
    }

    public String getTrackingString() throws Exception {
        String trackingName = trackingController.getTrackingByTrackingID(tracking_id).getTrackingNote();
        return trackingName;
    }

    public void setTracking_id(int tracking_id) {
        this.tracking_id = tracking_id;
    }

    public double getConverted_loc() {
        return converted_loc = getComplexityLoc() * getQualityPercent();
    }

    public void setConverted_loc(double converted_loc) {
        this.converted_loc = converted_loc;
    }

    public double getEvaluation_time() {
        return evaluation_time = evaluation_time;
    }

    public void setEvaluation_time(double evaluation_time) {
        this.evaluation_time = evaluation_time;
    }

    public String getEvaluation_note() {
        return evaluation_note;
    }

    public void setEvaluation_note(String evaluation_note) {
        this.evaluation_note = evaluation_note;
    }

    @Override
    public String toString() {
        try {
            return String.format("%-4s|%-4s|%-20s|%-10s|%-10s|%-6s|%-30s", evaluation_id, evaluation_time, evaluation_note, getComplexityString(), getQualityString(), getConverted_loc(), tracking_id);
        } catch (Exception ex) {
            Logger.getLogger(Loc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
