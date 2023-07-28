package sk.myProject.school.model;

import java.security.SecureRandom;
import java.util.Base64;

public class MyUtils {
    public static String encryption(String password){
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static String decryption(String password){
        return new String(Base64.getDecoder().decode(password));
    }

    public static String generateRandomString(int length) {
        byte[] randomBytes = new byte[length];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
