/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;
import java.util.*;
import model.*;
import dao.*;
import view.Utility;

/**
 *
 * @author Admin
 */
public class FeatureController {

    static FeatureDAO sd = new FeatureDAO();
    static TeamController tc = new TeamController();
    static TeamDAO tda = new TeamDAO();
    static ClassUserDAO cu = new ClassUserDAO();

    public void showList(List<Feature> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("============================= List ===============================");

            System.out.println(String.format("%-5s|%-15s|%-15s|%-10s", "ID", "Feature_name", "Team_id", "Status"));

            System.out.println("==================================================================");

            for (int i = 0; i < sList.size(); i++) {
                System.out.println(sList.get(i));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

    //check code subject ton tai chua? 
    public static boolean checkFeatureExist(Feature f) throws Exception {
        List<Feature> sbList = sd.getFeatList();

        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getTeam_id() == f.getTeam_id()
                        && sbList.get(i).getFeature_name().compareToIgnoreCase(f.getFeature_name()) == 0) {

                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //check iteration subject ton tai chua? 
    public static boolean checkFeatureExistByID(int id) throws Exception {

        List<Feature> sbList = sd.getFeatList();

        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getFeature_id() == id) {

                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }

    //them feature moi
    public static void addNewFeature(Feature f) throws Exception {
        List<Feature> sbList = sd.getFeatList();
      

        //check Team ID da ton tai:
        if (tc.checkTeamExistByID(tda.getListWithOneCondition(" status = true "), f.getTeam_id())) {
            //check iteration da ton tai:

            sbList.add(f);
            sd.insertFeatToList(f);
            System.out.println("Add successfully!");
            //    } else {
            //        throw new Exception("From_date must be before To_date!");
        } else {
            throw new Exception("Enter Team ID in list showed only!");
        }

    }

    //find iteration name
    public static int searchFeatureIndexByName(String name) throws Exception {

        List<Feature> sbList = sd.getFeatList();

        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getFeature_name().contains(name)) {
                    return i;
                }
            }
        }
        return -1;
    }

    //find iteration index
    public static int searchFeatureIndexByID(int id) throws Exception {

        List<Feature> sbList = sd.getFeatList();

        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getFeature_id() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    //find Feature
    public static Feature searchFeatureByCode(String name) throws Exception {
        List<Feature> sbList = sd.getFeatList();

        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getFeature_name().contains(name)) {
                    return sbList.get(i);
                }
            }
        }
        return null;
    }

    public static Feature searchFeatureByID(int id) throws Exception {

        List<Feature> sbList = sd.getFeatList();

        if (sbList.size() > 0) {
            for (int i = 0; i < sbList.size(); i++) {
                if (sbList.get(i).getFeature_id() == id) {
                    return sbList.get(i);
                }
            }
        }
        return null;
    }

    //update iteration 
    public static void updateFeature(Feature sb) throws Exception {

        List<Feature> sbList = sd.getFeatList();

        if (checkFeatureExistByID(sb.getFeature_id())) {
            // if (cc.checkClassroomExistByID(cd.getListWithOneCondition(" status = true "), sb.getClass_id())) {
            //check team da ton tai:
            if (tc.checkTeamExistByID(tda.getListWithOneCondition(" status = true "), sb.getTeam_id())) {

                sbList.set(searchFeatureIndexByID(sb.getFeature_id()), sb);
                sd.updateFeatureToList(sb, "team_id", sb.getTeam_id() + "", "int");
                sd.updateFeatureToList(sb, "feature_name", sb.getFeature_name() + "", "String");
                sd.updateFeatureToList(sb, "status", sb.getStatus() + "", "int");
//                  

            } else {
                throw new Exception("Enter Team ID in list showed only!");
            }
            System.out.println("Update successfully!");;
        } else {
            throw new Exception("Can not find the Feature!");
        }

    }


    public static void changeFeatureStatus(int id, int option) throws Exception {
        List<Feature> sbList = sd.getFeatList();

        if (checkFeatureExistByID(id)) {
            try {
                Feature sb = searchFeatureByID(id);
                String changeStatus;

                sd.updateFeatureToList(sb, "status", option + "", "int");

            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new Exception("Feature is NOT exist!");
        }
    }

    //seach name(1), status(2)
    public List<Feature> searchByPattern(String pattern, int choice) throws Exception {

        List<Feature> pList = sd.getFeatList();

        List<Feature> pl = new ArrayList<>();
        //chuyển tất cả về toLowerCase
        pattern = pattern.toUpperCase();
        switch (choice) {
            case 1: //team_id
                try {
                    int numberPattern = Integer.parseInt(pattern);
                    for (int i = 0; i < pList.size(); i++) {
                        if (pList.get(i).getTeam_id() == numberPattern) {
                            pl.add(pList.get(i));
                        }
                    }
                } catch (Exception e) {
                    throw new Exception("Wrong!");
                }
                break;


            case 2: //status
            {
                if (pattern.toUpperCase().compareTo("1") == 0) { //1. Active
                    for (int i = 0; i < pList.size(); i++) {

                        if (pList.get(i).getStatus() == 1) {

                            pl.add(pList.get(i));
                        }
                    }
                } else {
                    if (pattern.toUpperCase().compareTo("2") == 0) {//2. Deactive
                        for (int i = 0; i < pList.size(); i++) {

                            if (pList.get(i).getStatus() == 2) {

                                pl.add(pList.get(i));
                            }
                        }
                    } else {
                        if (pattern.toUpperCase().compareTo("3") == 0) {//3.exit

                            {
                                for (int i = 0; i < pList.size(); i++) {
                                    if (pList.get(i).getStatus() == 3) {
                                        pl.add(pList.get(i));
                                    }
                                }
                            }

                        } else {
                            throw new Exception("Choose in list menu only!");
                        }
                    }
                }

                break;
            }
//            default:
//                System.out.println("Exit search.");
//                break;

        }
        return pl;
    }


    //sap xep feature
    public List<Feature> sortFeature(int choice) throws Exception {
        List<Feature> sl = sd.getFeatList();

        switch (choice) {
            case 1: //so sánh name
                Collections.sort(sl, new Comparator<Feature>() {
                    @Override
                    public int compare(Feature o1, Feature o2) {
                        return o1.getFeature_name().compareTo(o2.getFeature_name());
                    }
                });
                break;

            case 2: //so sanh active
                Collections.sort(sl, new Comparator<Feature>() {
                    @Override
                    public int compare(Feature o1, Feature o2) {

                        return o1.getStatusString().compareTo(o2.getStatusString());

                    }
                });
                break;

            //nếu nhập khác thì exit sort
            default:
                System.out.println("Exit sort.");
                break;
        }

        return sl;
    }


    public static boolean checkFeatureExistByID(List<Feature> c, int id) throws Exception {
        if (c.size() > 0) {
            for (int i = 0; i < c.size(); i++) {
                if (c.get(i).getFeature_id() == id) {
                    return true; //ton tai
                }
            }
        }
        return false; //khong ton tai
    }
    //======================================================================================================
  //show team list
    public void showTeamListforStudent(int user_id) throws Exception{
        System.out.println(String.format("%-10s|%-15s|%-10s|%-20s|%-20s|%-20s", "Team ID", "Class ID", "Topic Code", "Topic Name", "GitLab URL", "Status"));
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println(tc.searchTeamByID(cu.getTeam_idList(user_id).getTeamID()));     
    }
     public void showListFeatureForFunction(List<Feature> sList) throws Exception {

        if (sList.size() > 0) {
            System.out.println("========= Feature List =========");
            System.out.println(String.format("%-5s|%-15s|%-15s|%-10s", "ID", "Feature_name", "Team_id", "Status"));
            System.out.println("----------------------------------");
            for (int i = 0; i < sList.size(); i++) {
                System.out.println(String.format("%-5d|%-15s|%-20s|%-10s",
                        sList.get(i).getFeature_id(),
                        sList.get(i).getFeature_name(),
                        sList.get(i).getTeam_id(),
                        sList.get(i).getStatusString()));
            }
        } else {
            throw new Exception("Empty List!");
        }
    }

}

