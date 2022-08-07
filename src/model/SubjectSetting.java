package model;

public class SubjectSetting{

    private int subjectID, setting_id, type_id, display_order, setting_value;
    private String setting_title;
    private boolean subject_setting_status;
    
    private String subjectCode, typeTitle;
    

    public SubjectSetting() {
    }

    public SubjectSetting( int setting_id, int type_id, String setting_title, int setting_value, int display_order, boolean subject_setting_status, int subjectID) {
        this.subjectID = subjectID;
        this.setting_id = setting_id;
        this.type_id = type_id;
        this.display_order = display_order;
        this.setting_title = setting_title;
        this.setting_value = setting_value;
        this.subject_setting_status = subject_setting_status;
    }

    
//new SubjectSetting(id, type_id, setting_title, setting_value, display_order, status, subjectID));
    public SubjectSetting( int type_id, String setting_title, int setting_value, int display_order, boolean subject_setting_status, int subjectID) {
        this.setting_id = setting_id;
        this.type_id = type_id;
        this.display_order = display_order;
        this.setting_title = setting_title;
        this.setting_value = setting_value;
        this.subject_setting_status = subject_setting_status;
        this.subjectID = subjectID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public boolean isSubject_setting_status() {
        return subject_setting_status;
    }

    public void setSubject_setting_status(boolean subject_setting_status) {
        this.subject_setting_status = subject_setting_status;
    }


    public int getSetting_id() {
        return setting_id;
    }

    public void setSetting_id(int setting_id) {
        this.setting_id = setting_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getSetting_title() {
        return setting_title;
    }

    public void setSetting_title(String setting_title) {
        this.setting_title = setting_title;
    }

    public int getSetting_value() {
        return setting_value;
    }

    public void setSetting_value(int setting_value) {
        this.setting_value = setting_value;
    }


    public int getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(int display_order) {
        this.display_order = display_order;
    }
    

    
    public String getSubjectSetting_Status() {
        String statusFinal = null;
        if (subject_setting_status == true) {
            statusFinal = "Active";
        }else{
            statusFinal = "Deactive";
        }
        return statusFinal;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getType(int type_id) {
        String typeTemp = null;
        switch (type_id) {
            case 1:
                typeTemp = "Subject Category";
                break;
            case 2:
                typeTemp = "Lesson Type";
                break;
            case 3:
                typeTemp = "Lesson Category";
                break;
            case 4:
                typeTemp = "Project Subject Category";
                break;

        }

        return typeTemp;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }
    
    

    @Override
    public String toString() {
        return String.format("%-5d|%-15s|%-25s|%-20s|%-10d|%-15d|%-10s",
                setting_id, subjectCode, getType(type_id), setting_title, setting_value, display_order, getSubjectSetting_Status());
    }

}

