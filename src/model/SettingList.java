package model;

public class SettingList {


    private int id, order, status, settingType;
    private String nameString, valueString, action;

    public SettingList() {
    }
   
    public SettingList(int settingType, String nameString, int order, String valueString, int status) {
        this.order = order;
        this.status = status;
        this.action = action;
        this.settingType = settingType;
        this.nameString = nameString;
        this.valueString = valueString;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatus(int status) {
        String statusTemp = null;
        switch (status) {
            case 1:
                statusTemp = "Active";
                break;
            case 2:
                statusTemp = "Inactive";
                break;

        }

        return statusTemp;
    }

    public String getAction() {
        return action;
    }

    public String getAction(int status) {
        String actionTemp = null;
        switch (status) {
            case 1:
                actionTemp = "Edit  Deactive";
                break;
            case 2:
                actionTemp = "Edit  Active";
                break;

        }
        return actionTemp;

    }

    public void setAction(int status) {
        this.action = getAction(status);
    }

    public int getSettingType() {
        return settingType;
    }

    //Linh sua them type
    public String getType(int settingType) {
        String typeTemp = null;
        switch (settingType) {
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

    public void setSettingType(int settingType) {
        this.settingType = settingType;
    }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    @Override
    public String toString() {
        return String.format("%-5s|%-30s|%-20s|%-10s|%-10s|%-10s|%-10s", id, getType(settingType), nameString,
                order, valueString, getStatus(status), getAction(status));
    }
    
    //ham test fix
    public SettingList(int id, int settingType, String nameString, int order, String valueString, int status) {
        this.id = id;
        this.order = order;
        this.status = status;
        this.action = action;
        this.settingType = settingType;
        this.nameString = nameString;
        this.valueString = valueString;
    }

}
