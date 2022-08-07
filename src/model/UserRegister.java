
package model;

public class UserRegister {
    private String captcha;
    private String tempEmail;
    private String tempPass;

    public UserRegister() {
    }

    public UserRegister(String captcha, String tempEmail, String tempPass) {
        this.captcha = captcha;
        this.tempEmail = tempEmail;
        this.tempPass = tempPass;
    }


    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getTempEmail() {
        return tempEmail;
    }

    public void setTempEmail(String tempEmail) {
        this.tempEmail = tempEmail;
    }

    public String getTempPass() {
        return tempPass;
    }

    public void setTempPass(String tempPass) {
        this.tempPass = tempPass;
    }
    
   
    
}
