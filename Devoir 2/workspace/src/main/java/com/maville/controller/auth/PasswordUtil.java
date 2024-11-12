package com.maville.controller.auth;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {
    private static PasswordUtil instance;

    private PasswordUtil() {}

    public static PasswordUtil getInstance() {
        if (instance == null) {
            instance = new PasswordUtil();
        }
        return instance;
    }

    public String hashPassword(String password) {
        String strongSalt = BCrypt.gensalt(10);
        return BCrypt.hashpw(password, strongSalt);
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
