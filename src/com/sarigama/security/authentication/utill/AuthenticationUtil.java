package  com.sarigama.security.authentication.utill;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class AuthenticationUtil {
    
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public String generateSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public String generateUserId(int length) {
        return generateSalt(length);
    }

    public String generateSecurePassword(String password, String salt) throws InvalidKeySpecException {
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }

    private byte[] hash(char[] password, byte[] salt) throws InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        SecretKey returnValue = null;
        KeyGenerator secretKeyGenerator = KeyGenerator.getInstance("DESede");
        secretKeyGenerator.init(112);
        returnValue = secretKeyGenerator.generateKey();
        return returnValue;
    }

    public byte[] encrypt(String securePassword, String accessTokenMaterial) throws InvalidKeySpecException {
        return hash(securePassword.toCharArray(), accessTokenMaterial.getBytes());
    }
 
    public static void main(String[] args) {
        AuthenticationUtil authenticationUtil = new AuthenticationUtil() ;
        String password = "Sharoon808%^&" ;
        String salt = authenticationUtil.generateSalt(30);
        System.out.println( "salt  : " +  salt );
        String sPassword = null ;
        try {
            sPassword = authenticationUtil.generateSecurePassword(password , salt);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        System.out.println( "sPassword  : " +  sPassword );

        try {
            sPassword = authenticationUtil.generateSecurePassword(password , salt);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        System.out.println( "sPassword  : " +  sPassword );


        salt = authenticationUtil.generateSalt(30);
        System.out.println( "salt  : " +  salt );
    }
}