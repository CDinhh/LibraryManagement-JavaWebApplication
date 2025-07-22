/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myListener;

import dao.UserDAO;
import dto.User;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Dan Huy
 */
public class ThongBaoKhiBatMoServer implements ServletContextListener {

    

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Server sắp tắt. Đang gửi email cảnh báo...");

        // Email người gửi (phải bật App Password nếu dùng Gmail)
        String from = "danhuy1219@gmail.com";
        String password = "dvigvpghbpeepoti"; // App password

        // Thiết lập thông số SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Xác thực
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        
        // Tạo session và gửi email
        Session session = Session.getInstance(props, auth);
        UserDAO UDao = new UserDAO();
        ArrayList<User> Ulist = UDao.getAllUSer();
        for (User user : Ulist) {
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail(), false));
                message.setSubject("Server Shutdown Notification");
                message.setText("Thông báo: Server đang được tắt hoặc ứng dụng đang bị undeploy.\n\nThời gian: " + new java.util.Date(), "UTF-8");
                Transport.send(message);
                System.out.println("Email thông báo đã được gửi khi tắt server.");
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Lỗi khi gửi email lúc tắt server.");
            }
        }

    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Server started. Sending email...");

        // Gửi email khi server khởi động        // Email người nhận
        String from = "danhuy1219@gmail.com";       // Email người gửi (bạn)
        String password = "dvigvpghbpeepoti";    // Mật khẩu ứng dụng (App Password)

        // Thiết lập cấu hình SMTP cho Gmail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };       
        // Tạo phiên gửi
        Session session = Session.getInstance(props, auth);
        UserDAO UDao = new UserDAO();
        ArrayList<User> Ulist = UDao.getAllUSer();
        for (User user : Ulist) {
            try {
                MimeMessage message = new MimeMessage(session);
                message.addHeader("Content-type", "text/HTML; charset=UTF-8");
                message.setFrom(from);
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail(), false));
                message.setSubject("Test email java");
                message.setText("Our system is opened!.", "UTF-8");
                Transport.send(message);
                System.out.println(" Mail da duoc gui thanh cong.");
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println(" Loi khi gui mail.");
            }
        }
    }

}
