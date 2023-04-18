package com.myProject.school.model;

import java.util.Base64;

public class PasswordUtil {
    public static String encryption(String password){
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static String decryption(String password){
        return new String(Base64.getDecoder().decode(password));
    }
}
