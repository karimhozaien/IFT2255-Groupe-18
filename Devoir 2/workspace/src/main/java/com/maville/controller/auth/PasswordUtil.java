package com.maville.controller.auth;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {
    public static String hashPassword(String password) {
        String strongSalt = BCrypt.gensalt(10);
        return BCrypt.hashpw(password, strongSalt);
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
