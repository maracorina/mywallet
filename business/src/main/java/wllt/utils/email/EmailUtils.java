package wllt.utils.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import wllt.dto.EmailDTO;
import wllt.dto.UserDTO;
import wllt.exceptions.BusinessException;
import wllt.exceptions.ValidationException;
import wllt.validators.EmailValidator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class EmailUtils {

    @Autowired
    public static JavaMailSender emailSender;

    public void sendSimpleMessage(EmailDTO emailDTO) throws ValidationException, BusinessException {
        EmailValidator.validate(emailDTO);

        try (InputStream input = EmailUtils.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties props = new Properties();

            if (input == null) {
                throw new BusinessException("Email01", "Sorry, unable to find config.properties");
            }

            props.load(input);

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(props.getProperty("mail.username"),props.getProperty("mail.password"));
                        }
                    });

            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(emailDTO.getTo()));
            message.setSubject(emailDTO.getSubject());
            message.setText(emailDTO.getText());

            Transport.send(message);
            System.out.println("message sent successfully");

        } catch (IOException | MessagingException ex) {
            throw new BusinessException("Email01", ex.getMessage());
        }

    }

    public void sendNewPasswordMessage(UserDTO userDTO) throws ValidationException, BusinessException {
        String message = "Hello, " + userDTO.getFirstName() + "!" + "\n\n" +
                "Your account has been deactivated due to security reasons. You can now log in using your new credentials. \n"+
                "NEW INFO " + '\n' +
                "\tFirst Name : " + userDTO.getFirstName() + '\n' +
                "\tLast Name : " + userDTO.getLastName() + '\n' +
                "\tMobile Number : " + userDTO.getMobileNumber() + '\n' +
                "\tEmail : " + userDTO.getEmail() + '\n' +
                "\tUsername : " + userDTO.getUsername() + '\n' +
                "\tPassword : " + userDTO.getPassword() + '\n' +
                '\n' +
                "Thank you, \n myWallet Team ";
        EmailDTO emailDTO = new EmailDTO(userDTO.getEmail(), "Security Alert", message);
        sendSimpleMessage(emailDTO);
    }

    public void sendWelcomeMessage(UserDTO userDTO) throws ValidationException, BusinessException {
        String message = "Welcome, " + userDTO.getFirstName() + " " + userDTO.getLastName() + "!" + '\n' +
                '\n' +
                "First Name : " + userDTO.getFirstName() + '\n' +
                "Last Name : " + userDTO.getLastName() + '\n' +
                "Mobile Number : " + userDTO.getMobileNumber() + '\n' +
                "Email : " + userDTO.getEmail() + '\n' +
                "Username : " + userDTO.getUsername() + '\n';
        EmailDTO emailDTO = new EmailDTO(userDTO.getEmail(), "New account info", message);
        sendSimpleMessage(emailDTO);
    }

    public void sendUpdatedAccountMessage(UserDTO newUserDTO, UserDTO oldUserDTO) throws ValidationException, BusinessException {
        String message = "User account updated!" + '\n' +
                "NEW INFO " + '\n' +
                "\tFirst Name : " + newUserDTO.getFirstName() + '\n' +
                "\tLast Name : " + newUserDTO.getLastName() + '\n' +
                "\tMobile Number : " + newUserDTO.getMobileNumber() + '\n' +
                "\tEmail : " + newUserDTO.getEmail() + '\n' +
                "\tUsername : " + newUserDTO.getUsername() + '\n' +
                '\n' +
                "OLD INFO " + '\n' +
                "\tFirst Name : " + oldUserDTO.getFirstName() + '\n' +
                "\tLast Name : " + oldUserDTO.getLastName() + '\n' +
                "\tMobile Number : " + oldUserDTO.getMobileNumber() + '\n' +
                "\tEmail : " + oldUserDTO.getEmail() + '\n' +
                "\tUsername : " + oldUserDTO.getUsername() + '\n';

        EmailDTO emailDTO = new EmailDTO(newUserDTO.getEmail(), "New account info", message);
        sendSimpleMessage(emailDTO);
    }
}