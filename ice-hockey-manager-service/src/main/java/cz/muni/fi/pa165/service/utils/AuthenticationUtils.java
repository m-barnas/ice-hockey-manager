package cz.muni.fi.pa165.service.utils;

import com.google.common.base.Preconditions;
import cz.muni.fi.pa165.exceptions.AuthenticationException;
import cz.muni.fi.pa165.service.exceptions.IncorrectPasswordException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Authentication utility class.
 *
 * @author Martin Barnas 433523@mail.muni.cz
 */
public class AuthenticationUtils {

    /**
     * Utility class.
     */
    private AuthenticationUtils() {}

    /**
     * Create hash from given unencryptedPassword.
     *
     * @param unencryptedPassword to hash
     * @return hash of given unencryptedPassword
     * @throws AuthenticationException when using invalid entries for pbkdf2 algorithm
     */
    public static String createHash(String unencryptedPassword) throws AuthenticationException {
        // define constants
        final int SALT_BYTE_SIZE = 24;
        final int HASH_BYTE_SIZE = 18;
        final int PBKDF2_ITERATIONS = 1000;

        // generate a random salt
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        secureRandom.nextBytes(salt);

        // hash the password
        byte[] hash = pbkdf2(unencryptedPassword.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);

        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toBase64(salt) + ":" + toBase64(hash);
    }

    /**
     * Verify given password.
     *
     * @param unencryptedPassword to verify
     * @param correctHash to be compared with given password
     * @return true if unencryptedPassword's hash matches correctHash
     * @throws AuthenticationException when using invalid entries for pbkdf2 algorithm
     */
    public static boolean verifyPassword(String unencryptedPassword, String correctHash)
            throws AuthenticationException {
        // preconditions
        Preconditions.checkNotNull(correctHash, "Correct hash must not be null.");
        if (unencryptedPassword == null) {
            return false;
        }

        // split correct hash
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromBase64(params[1]);
        byte[] hash = fromBase64(params[2]);

        // create hash to be tested
        byte[] testHash = pbkdf2(unencryptedPassword.toCharArray(), salt, iterations, hash.length);

        // compare hashes
        return slowEquals(hash, testHash);
    }

    public static void checkPasswordLength(String password) {
        if (password.length() < 8 || password.length() > 16) {
            throw new IncorrectPasswordException("Password must be 8-16 characters.");
        }
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws AuthenticationException {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException|InvalidKeySpecException ex) {
            throw new AuthenticationException("Invalid entries for pbkdf2 algorithm.", ex);
        }
    }

    private static byte[] fromBase64(String hex) {
        return DatatypeConverter.parseBase64Binary(hex);
    }

    private static String toBase64(byte[] array) {
        return DatatypeConverter.printBase64Binary(array);
    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }
}
