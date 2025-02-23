package com.trabalhoPoo.util;

import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordUtil {

    // Método para gerar hash com jBCrypt (o método principal, que você *sempre* deve usar para novas senhas)
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Método para verificar a senha com jBCrypt
    public static boolean checkPassword(String candidate, String hashed) {
        return BCrypt.checkpw(candidate, hashed);
    }

    // ************************************************************
    //  MÉTODO ANTIGO (TEMPORÁRIO!) -  Para migrar senhas antigas
    // ************************************************************
    public static String hashPasswordOld(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] hashedPassword = md.digest();
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha (antigo)", e);
        }
    }
    // Remova completamente os métodos: generateSalt, hashPasswordWithSalt, verifyPasswordWithSalt

}