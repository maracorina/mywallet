package wllt.utils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import static com.google.common.hash.Hashing.sha256;

public class PasswordUtils {

    public static String hash(String password) {
        String hashedPassword = StringUtils.EMPTY;
        if (!password.equals("")) {
            hashedPassword = sha256()
                    .hashString(password, StandardCharsets.UTF_8)
                    .toString();
        }

        return hashedPassword;
    }

    public static boolean verifyUserPassword(String providedPassword, String securedPassword)
    {
        String hashedGivenPass = hash(providedPassword);
        return securedPassword.equals(hashedGivenPass);
    }

    public static String generatePassword(){
        String password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
                StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return password;
    }
}
