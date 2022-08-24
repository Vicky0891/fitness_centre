package service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum DigestUtil {
    INSTANCE;

    /**
     * Method to make hashpassword for user password
     * 
     * @param password User password
     * @return hash password
     */
    public String hash(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(password.getBytes());
            byte[] bytes = messageDigest.digest();
            BigInteger bigInteger = new BigInteger(1, bytes);
            return bigInteger.toString(16).toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

}
