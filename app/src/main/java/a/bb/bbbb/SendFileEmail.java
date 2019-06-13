package a.bb.bbbb;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.io.File;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendFileEmail extends AsyncTask

{
    public void send(String filepath)
    {
        Log.i("MIAPP","Estoy en SendFileEmail-send- (filepath) "+ filepath);
    onPostExecute(filepath);
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        String filepath = (String) o;
        Log.i("MIAPP","Estoy en SendFileEmail-onPostExecuted- (filepath) "+ filepath);
        // Recipient's email ID needs to be mentioned.
        String to = "reejulu1@gmail.com";
        // Sender's email ID needs to be mentioned
        final String from = "ficharapp2019ap@gmail.com";
        // final String username = "xyz";
        final String pass = "curso2019";
        // Assuming you are sending email from localhost
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.user", from);
        properties.put("mail.smtp.password", pass);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.auth", "true");
        //otro smtp
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Read more: http://mrbool.com/how-to-work-with-java-mail-api-in-android/27800#ixzz3E2T8ZbpJ

        // Get the default Session object.
        //Session session = Session.getDefaultInstance(properties);

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });

        try{
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            // Set Subject: header field
            message.setSubject("This is the Subject Line!");
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Fill the message
            messageBodyPart.setText("This is message body");
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Part two is attachment
            // check if file is present
            File file = new File(filepath);
            if (file.exists()){
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(filepath);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filepath);
                multipart.addBodyPart(messageBodyPart);
            }
            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
            // Send the complete message parts
            message.setContent(multipart);
            // Send message
            Log.i("MIAPP","Se va a enviar message : "+message.getSubject().toString());
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Log.i("MIAPP","Instruccion StrictMode.setThreadPolicy :");

            Transport.send(message);
            Log.i("MIAPP","Instruccion Transport.send hecha :");
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
        super.onPostExecute(o);
    }

}
