/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.text.*;
import java.util.*;
import model.SettingList;

/**
 *
 * @author admin
 */
public class Utility {

    //global
    static Scanner scanner = new Scanner(System.in);
    public static final String REGEX_STRING = "[a-zA-Z0-9 ]+";
    public static final String REGEX_STRING_WITH_SPECIAL_SIGN = "[a-zA-Z0-9/,.-]+";
    static final String REGEX_NAME = "[a-zA-z ]+";
    static final String REGEX_DATE = "\\d{1,2}[/]\\d{1,2}[/]\\d{1,4}";
    static final String REGEX_PHONE = "[0][0-9]{9,10}";
    static public final String REGEX_Y_N = "[yYnN]";
    static public final String REGEX_UD = "[uUdD]";
    static final String REGEX_EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static String REGEX_EDIT_REMOVE = "[eErR]";
    static String REGEX_NUMBER = "[0-9]+";
//    static final String REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
//    private static String REGEX_EDIT_REMOVE= "[eErR]";
    static final String DATE_FORMAT = "dd/MM/yyyy";
    static final String REGEX_LINK = "https://" + "[a-zA-Z0-9/,.-+?]+";

    public static int getInt(String message, String error, int min, int max) {
        String REGEX_NUMBER = "[0-9]+";
        while (true) {
            System.out.print(message);
            String result = scanner.nextLine();
            if (result.isEmpty()) {
                System.out.println("Input cannot be empty");
                //neu result khop voi cai regex => cho qua
                //neu khong khop (false )
            } else if (result.matches(REGEX_NUMBER) == false) {
                System.out.println(error);

            } else {

                try {
                    int number = Integer.parseInt(result);
                    if (number >= min && number <= max) {
                        return number;

                    } else {
                        System.out.println("Number must in range from " + min + " to " + max);
                    }

                } catch (NumberFormatException e) {
                    System.out.println(error);

                }
            }
        }
    }

    public static float getFloat(String message, String error, float min, float max) {
        String REGEX_NUMBER = "[0-9.]+";
        while (true) {
            System.out.print(message);
            String result = scanner.nextLine();
            if (result.isEmpty()) {
                System.out.println("Input cannot be empty");
                //neu result khop voi cai regex => cho qua
                //neu khong khop (false )
            } else if (result.matches(REGEX_NUMBER) == false) {
                System.out.println(error);
            } else {

                try {
                    float number = Float.parseFloat(result);
                    if (number >= min && number <= max) {
                        return number;

                    } else {
                        System.out.println("number must in range from " + min + " to " + max);

                    }

                } catch (NumberFormatException e) {
                    System.out.println(error);

                }
            }
        }
    }

    public static double getDouble(String message, String error, double min, double max) {
        String REGEX_NUMBER = "[0-9.]+";
        while (true) {
            System.out.print(message);
            String result = scanner.nextLine();
            if (result.isEmpty()) {
                System.out.println("Input cannot be empty");
                //neu result khop voi cai regex => cho qua
                //neu khong khop (false )
            } else if (result.matches(REGEX_NUMBER) == false) {
                System.out.println(error);

            } else {

                try {
                    double number = Double.parseDouble(result);
                    if (number > min && number < max) {
                        return number;

                    } else {
                        System.out.println("number must in range from " + min + " to " + max);

                    }

                } catch (NumberFormatException e) {
                    System.out.println(error);

                }
            }
        }
    }

    public static String getString(String message, String error, String regex) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty !!!");
            } else {
                if (input.matches(regex)) {
                    return input;
                } else {
                    System.out.println(error);
                }
            }
        }
    }

    public static String getDate(String message, String error, String regex, boolean sosanhToday) {
        while (true) {
            System.out.println(message);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty");
            } else {
                if (!input.matches(regex)) {
                    System.out.println(error);
                } else if (!isValidDate(input)) {
                    System.out.println("Date does not exist ");
                } else {
                    if (sosanhToday == true) { // co can so sanh hom nay khong
                        DateFormat dateformat = new SimpleDateFormat(DATE_FORMAT);
                        Date currentDate = new Date();
                        //format 2 cai date theo  format dd/MM/yyyy
                        String currentDateString = dateformat.format(currentDate);
                        boolean checkDateLessThanCurrentDate = checkDate1_Less_Than_Date2(input, currentDateString);
                        //if variable above == true => return gia tri do
                        //else sout loi 
                        if (checkDateLessThanCurrentDate == false) {
                            System.out.println("Date must be less than " + currentDateString);
                        } else {
                            return input;
                        }
                    } else { // khong can so sanh hom nay
                        return input;
                    }
                }
            }
        }
    }

    public static boolean isValidDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateTest = null;
        dateFormat.setLenient(false);

        try {
            dateTest = dateFormat.parse(date);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    private static boolean checkDate1_Less_Than_Date2(String input, String currentDateString) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date firstDate = dateFormat.parse(input);
            Date secondDate = dateFormat.parse(currentDateString);

            if (firstDate.before(secondDate)) {
                return true;
            } else if (firstDate.after(secondDate)) {
                return false;
            } else {
                return false;
            }

        } catch (ParseException ex) {
            System.out.println("PARSE KO THANH CONG");
        }
        return false;

    }

    public static int getStatus() {
        int status = getInt("Status:    1/ Active    2/ Inactive\nEnter Status: ", "It must be digit", 1, 2);
        return status;
    }

    public static int getOrder() {
        int order = getInt("Enter order: ", "It must be digit", 0, Integer.MAX_VALUE);
        return order;
    }

    public static String getValue() {
        String value = getString("Enter value: ", "It mus be string", REGEX_NAME);
        return value;
    }

    public static int getType() {
        int settingType = getInt("Group: 1/ Subject Catergory   2/ Lesson Type  3/Lesson Category  4/ Project Subject Category\nEnter Group: ", "It must be digit", 1, 4);
        return settingType;
    }

    public static int getID() {
        int id = getInt("Enter id: ", "ID must be letter and digit", 0, Integer.MAX_VALUE);
        return id;
    }

    public static String getName(int id, List<SettingList> listSetting) {
        while (true) {
            String name = getString("Enter name: ", "Name must be letters", REGEX_NAME);
            //if name matches with id => return name
            if (!Utility.checkWrongName(id, name, listSetting)) {
                return name;
            }
        }
    }

    public static String getNameString() {
        String name = getString("Enter name: ", "Name must be letters", REGEX_NAME);
        return name;
    }

    public static String getAction(int status) {
        String actionTemp = null;
        switch (status) {
            case 1:
                actionTemp = "Edit  Deactive";
                System.out.println("Edit  Deactive");
                break;
            case 2:
                actionTemp = "Edit  Active";
                System.out.println("Edit  Active");
                break;

        }
        return actionTemp;
    }

    public static boolean checkWantToUpdate(String message) {
        String test = getString("Do you want to update " + message + " ?: ", "It must be Y or N", REGEX_Y_N);
        if (test.equalsIgnoreCase("y")) {
            return true;
        }
        return false;
    }

    static boolean checkEditOrRemove() {
        String result = Utility.getString("Do you want to edit (E) or remove (R)"
                + " setting. Choose E to edit, R to remove: ", "It must be E or R", Utility.REGEX_EDIT_REMOVE);
        //if result == u => true <=> update
        if (result.equalsIgnoreCase("e")) {
            return true;
        }
        return false;
    }

    public static boolean checkWrongName(int id, String name, List<SettingList> listSettting) {
        //loop from first to last element in list 
        for (SettingList st : listSettting) {
            //if stID = id and stName != name => wrong name, return true
            if (st.getId() == id && st.getNameString().equalsIgnoreCase(name) == false) {
                System.out.println("ID must has name " + st.getNameString());
                return true;
            }
        }
        return false;
    }

    public static boolean checkDataExist(int id, List<SettingList> listSetting) {
        for (SettingList st : listSetting) {
            if (st.getId() == id) {
                return true;
            }

        }
        return false;
    }

    public static String getStringNull(String message, String unchangeIfNull) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                return unchangeIfNull;
            } else {
                return input;
            }
        }
    }

    public static int getIntNull(String message, int unchangeIfNull, int minValueIfNotNull, int maxValueIfNotNull) {
        int result;
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                return unchangeIfNull;
            } else {
                if (input.matches(REGEX_NUMBER)) {
                    try {
                        result = Integer.parseInt(input);
                        if (result >= minValueIfNotNull && result <= maxValueIfNotNull) {
                            return result;

                        } else {
                            System.out.println("Number must in range from " + minValueIfNotNull + " to " + maxValueIfNotNull);
                        }

                    } catch (NumberFormatException e) {
                        System.out.println(e);

                    }
                }
            }
        }
    }

    public static boolean checkYesNo(String name) {
        String result = Utility.getString("Do you want to add filter " + name + " Y/N. Choose Y "
                + "to continue, N to return:  "
                + "main screeen: ", "It must be Y or N", Utility.REGEX_Y_N);
        return result.equalsIgnoreCase("y");
    }

    //=======================================ham xu ly date cho milestone cua LINH ======================
    //đổi String dang dd/MM/yyyy sang Date với Date format DMY 
    public static Date convertStringToDate(String date) throws Exception {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dt.setLenient(false);
        } catch (Exception e) {
            throw new Exception("Invalid date");
        }
        return dt.parse(date);
    }

    //đổi từ Date console sang String với định dạng dd/MM/yyyy
    public static String convertDateToString(Date date) {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        return dt.format(date);
    }

    //change Date console sang dinh dang yyyy-MM-dd
    public static String convertDateToStringtoInsert(Date date) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        return dt.format(date);
    }

    //change
    public static Date convertStringToDatetoInsert(String date) throws Exception {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dt.setLenient(false);
        } catch (Exception e) {
            throw new Exception("Invalid date");
        }
        return dt.parse(date);
    }

    public static String changeDateFormatToInsert(String date) {
        try {
            Date result = convertStringToDate(date); // chuyen date dd/mm/yyyy -> Date type

            return convertDateToStringtoInsert(result); // chuyen date type -> String yyyy-mm-dd
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Date getDateUnchange(String inra, Date unchange) throws Exception {
        Scanner sc = new Scanner(System.in);
        Date date = null;
        String d;
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        dt.setLenient(false);
        boolean ok = false;
        do {
            try {
                System.out.print(inra);
                d = sc.nextLine();
                //kiểm tra độ dài input -> rỗng = unchange
                if (d.length() == 0) {
                    return unchange;
                } else {
                    //chuyển đổi và kiểm tra date nhập vào
                    date = dt.parse(d);
                    ok = true;
                }
            } catch (ParseException e) {
                System.out.println("Invalid date");
            }
        } while (ok == false);
        if (date == null) {
            return unchange;
        } else {
            return date;
        }
    }

    //khong check hom nay
    public static Date getDateFormatReturnDate(String inra, Date datenhohon) throws Exception {
        Scanner sc = new Scanner(System.in);
        Date date;
        String d;

        //set Date format
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        dt.setLenient(false);
        do {
            try {
                System.out.print(inra);
                d = sc.nextLine();
                //đổi từ String d sang date
                date = dt.parse(d);
                if(date.compareTo(datenhohon)<0)
                    System.out.println("Date input must after: " + convertDateToString(datenhohon));
            } catch (ParseException e) {
                System.out.println("Invalid date");
                date = null;
            }

        } while (date == null || date.compareTo(datenhohon) <0);
        return date;
    }
    
    public static Date getDateFormatReturnDate(String inra) throws Exception {
        Scanner sc = new Scanner(System.in);
        Date date;
        String d;

        //set Date format
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        dt.setLenient(false);
        do {
            try {
                System.out.print(inra);
                d = sc.nextLine();
                //đổi từ String d sang date
                date = dt.parse(d); } catch (ParseException e) {
                System.out.println("Invalid date");
                date = null;
            }

        } while (date == null);
        return date;
    }

    public static Date getToday() throws Exception {
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
//        int month = today.get(Calendar.MONTH);
        int month = today.get(Calendar.MONTH) + 1;
        int day = today.get(Calendar.DAY_OF_MONTH);
        String chuoiTrungGian = day + "/" + month + "/" + year;
        Date today1 = convertStringToDate(chuoiTrungGian);
        return today1;
    }

    //test
//    public static void main(String[] args) throws Exception {
//        System.out.println(convertStringToDatetoInsert("2022-12-12"));
//    }
    //============================================================================================
    public static String getStringWithREGEXANDNull(String message, String regex, String unchangeIfNull) throws Exception {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                return unchangeIfNull;
            } else {
                if (input.matches(regex)) {
                    return input;
                } else {
                    throw new Exception("Wrong format!");
                }
            }
        }
    }

    public static boolean checkArrayFids(String funtcions_id) throws Exception {
        Scanner sc = new Scanner(System.in);

        //tach la dau phay
        String[] fids = funtcions_id.replaceAll("\\s+", "").split(",");

        //tạo array int báo size       
        int size = fids.length;
        int[] a = new int[size];
        for (int i = 0; i < size; i++) {
            //ktra định dạng phần tử nhận vào
            if (fids[i].matches("\\d+")) {
                a[i] = Integer.parseInt(fids[i]);
            } else {
                System.out.println("Wrong format!");
                return false; //sai
            }
        }
        return true; //dúng
    }

    static float getFloatNull(String message, float unchange, int min, int max) {
        String REGEX_NUMBER = "[0-9.]+";
        while (true) {
            System.out.print(message);
            String result = scanner.nextLine();
            if (result.isEmpty()) {
                return unchange;
            } else if (result.matches(REGEX_NUMBER) == false) {
                System.out.println("Wrong format!");
            } else {

                try {
                    float number = Float.parseFloat(result);
                    if (number < max && number > min) {
                        return number;
                    } else {
                        System.out.println("number must in range from " + min + " to " + max);

                    }
                } catch (NumberFormatException e) {
                    System.out.println("Wrong format!");
                }
            }
        }
    }
}
