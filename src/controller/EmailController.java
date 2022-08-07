package controller;

import static com.sun.org.glassfish.external.amx.AMXUtil.prop;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import model.*;
import java.io.UnsupportedEncodingException;

public class EmailController {
    

    static List<User> listUser = new ArrayList<User>();
    static RegisterController rc = new RegisterController();

    //Code ham RandomText: Linh
    public static void sendRandomText(User ur, String title, String noidung) throws MessagingException, UnsupportedEncodingException {

        final String fromEmail = "gr1.isp392";
        // Mat khai email cua ban
        final String password = "httt123@";
        // dia chi email nguoi nhan
        final String toEmail = ur.getEmail();

        final String subject = title;
        final String body = noidung + ur.getCaptcha();

        Properties props = new Properties();
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
//        props.put("mail.smtp.host", "smtp.office365.com");
//        props.put("mail.smtp.post", "587");
//        props.put("mail.smtp.auth", true);
//        props.put("mail.smtp.starttls.enable", true);
//        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

        msg.setReplyTo(InternetAddress.parse(fromEmail, false));

        msg.setSubject(subject, "UTF-8");

        msg.setText(body, "UTF-8");

        msg.setSentDate(new Date());

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        Transport.send(msg);
        //    System.out.println("Gui mail thanh cong");
    }

    
   // Code: Phuoc 
    public static void sendRandomPassword(String email) throws MessagingException, UnsupportedEncodingException {
        User us = new User();
        final String fromEmail = "gr1.isp392";
        // Mat khai email cua ban
        final String password = "httt123@";
        // dia chi email nguoi nhan
        final String toEmail = email;
        String password1 = rc.generateRandomPassword(10);
        us.setPassword(password1);

        final String subject = "Send password to re-enter again!";
        final String body = "Your password is: " + us.getPassword();

        Properties props = new Properties();
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
//        props.put("mail.smtp.host", "smtp.office365.com");
//        props.put("mail.smtp.post", "587");
//        props.put("mail.smtp.auth", true);
//        props.put("mail.smtp.starttls.enable", true);
//        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

        msg.setReplyTo(InternetAddress.parse(fromEmail, false));

        msg.setSubject(subject, "UTF-8");

        msg.setText(body, "UTF-8");

        msg.setSentDate(new Date());

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        Transport.send(msg);
        //    System.out.println("Gui mail thanh cong");
    }
}
