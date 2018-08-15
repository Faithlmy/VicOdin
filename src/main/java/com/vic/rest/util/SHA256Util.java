
package com.vic.rest.util;


import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;



public class SHA256Util {
    public final static Integer DEFAULT_ITERATIONS = 24000;
    public final static String algorithm = "pbkdf2_sha256";
    public SHA256Util() {}

    /**
     * @method:getEncodedHash
     * @description:獲取加密結果
     * @author:   
     * @date: -28
     * @param password
     * @param salt
     * @param iterations
     * @return String
     */
    public static String getEncodedHash(String password, String salt, int iterations) {
        // Returns only the last part of whole encoded password
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
            System.err.println("Could NOT retrieve PBKDF2WithHmacSHA256 algorithm");
            System.exit(1);
        }
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(Charset.forName("UTF-8")), iterations, 256);
        SecretKey secret = null;
        try {
            secret = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            System.out.println("Could NOT generate secret key");
            e.printStackTrace();
        }

        byte[] rawHash = secret.getEncoded();
        byte[] hashBase64 = Base64.getEncoder().encode(rawHash);

        return new String(hashBase64);
    }
    
    /**
     * @method:getsalt
     * @description:make salt
     * @author:   
     * @date: -28
     * @return
     */
    private static String getsalt(){
        int length = 12;
        Random rand = new Random();
                char[] rs = new char[length];
                    for(int i = 0; i < length; i++){
                    int t = rand.nextInt(3);
                    if (t == 0) {
                        rs[i] = (char)(rand.nextInt(10)+48);
                    } else if (t == 1) {
                         rs[i] = (char)(rand.nextInt(26)+65);
                    } else {
                        rs[i] = (char)(rand.nextInt(26)+97);
                    }
                }
                return new String(rs);
    }
    
    
    /**
     * @method:encode
     * @description:加密方法
     * @author:   
     * @date: -28
     * @param password
     * @return String
     */
    public static String encode(String password) {
        return encode(password, getsalt());
    }
    
    /**
     * @method:encode
     * @description:加密方法
     * @author:   
     * @date: -28
     * @param password
     * @param iterations
     * @return String
     */
    public static String encode(String password,int iterations) {
        return encode(password, getsalt(),iterations);
    }
    
    /**
     * @method:encode
     * @description:加密方法
     * @author:   
     * @date: -28
     * @param password
     * @param salt
     * @return String
     */
    public static String encode(String password, String salt) {
        return encode(password, salt, DEFAULT_ITERATIONS);
    }
    
    /**
     * @method:encode
     * @description:加密方法
     * @author:   
     * @date: -28
     * @param password
     * @param salt
     * @param iterations
     * @return String
     */
    public static String encode(String password, String salt, int iterations) {
        // returns hashed password, along with algorithm, number of iterations and salt
        String hash = getEncodedHash(password, salt, iterations);
        return String.format("%s$%d$%s$%s", algorithm, iterations, salt, hash);
    }

    /**
     * @method:checkPassword
     * @description:判斷密碼是否與加密密碼匹配
     * @author:   
     * @date: -28
     * @param password
     * @param hashedPassword
     * @return boolean
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        // hashedPassword consist of: ALGORITHM, ITERATIONS_NUMBER, SALT and
        // HASH; parts are joined with dollar character ("$")
        String[] parts = hashedPassword.split("\\$");
        if (parts.length != 4) {
            // wrong hash format
            return false;
        }
        Integer iterations = Integer.parseInt(parts[1]);
        String salt = parts[2];
        String hash = encode(password, salt, iterations);

        return hash.equals(hashedPassword);
    }
}
