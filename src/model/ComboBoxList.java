package model;

/**
 *
 * @author Aldrin
 */
import java.util.ArrayList;

public class ComboBoxList {

    private String ids;
    private String description;
    

    public ComboBoxList() {
    }

    public ComboBoxList(String ids, String description) {
        this.ids = ids;
        this.description = description;
    }

    /**
     * @return the description
     */
 
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public void setSelectedId(ArrayList<ComboBoxList> Comboboxs, String Id, javax.swing.JComboBox cbo) {
        for (ComboBoxList it : Comboboxs) {
            if (it.getIds().toString().equals(Id)) {
                cbo.setSelectedItem(it);
//                System.out.println("IF ID:" + Id);
            }

        }
    }

    public void setSelectedDescription(ArrayList<ComboBoxList> Comboboxs, String desc, javax.swing.JComboBox cbo) {
        for (ComboBoxList it : Comboboxs) {
            if (it.getDescription().trim().equals(desc.trim())) {
                cbo.setSelectedItem(it);
            }
        }
    }

    public String toString() {
        return this.description;
    }

    /**
     * @return the ids
     */
    public String getIds() {
        return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(String ids) {
        this.ids = ids;
    }

}
