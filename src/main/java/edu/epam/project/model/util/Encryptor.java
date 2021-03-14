package edu.epam.project.model.util;

import org.mindrot.jbcrypt.BCrypt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class Encryptor, used to hashes user passwords and checks them.
 * @author Sargerion.
 */
public class Encryptor {

    private static final Logger logger = LogManager.getLogger();

    public static String encryptPassword(String password) {
        String salt = BCrypt.gensalt(10);
        String result = BCrypt.hashpw(password, salt);
        logger.info("Password has encrypted");
        return result;
    }

    public static boolean checkInputPassword(String password, String encryptedPassword) {
        boolean result = BCrypt.checkpw(password, encryptedPassword);
        return result;
    }
}