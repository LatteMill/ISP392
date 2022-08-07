
package model;

public class User extends UserDetails{

    private String password;
    private String captcha;
    private boolean verify;

    public User() {
    }
    /**
     * @return the username
     */


    public User(String password, String captcha, boolean verify, String fullName, String email, String mobile, String rollNumber, String jobPosition, String company, String address, int id, int roles, int sex, int status) {
        super(id, fullName, email, mobile, rollNumber, jobPosition, company, address, roles, sex, status);
        this.password = password;
        this.captcha = captcha;
        this.verify = verify;
    }

    public User( String password, String captcha, boolean verify) {
        this.password = password;
        this.captcha = captcha;
        this.verify = verify;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }



    
    
}
