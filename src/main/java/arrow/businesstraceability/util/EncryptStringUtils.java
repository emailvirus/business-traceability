package arrow.businesstraceability.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;


/**
 * The Class EncryptString to use encrypt string into MD5 . And decrypt string .
 */
public class EncryptStringUtils {

    /** The Constant MY_PRIVATE_KEY. */
    private static final String MY_PRIVATE_KEY = "arrowtechnologiesvietnam";

    /** The Constant AES_ALGORITHM_SCHEMA. */
    private static final String AES_ALGORITHM_SCHEMA = "AES";

    /** The Constant UNICODE_FORMAT. */
    private static final String UNICODE_FORMAT = "UTF8";

    /** The log. */
    private static BaseLogger log = BaseLoggerProducer.getLogger();

    /**
     * Encrypt.
     *
     * @param encryptText the encrypt text
     * @return the string
     */
    public static String encrypt(String encryptText) {
        encryptText = StringUtils.reverse(encryptText);
        byte[] keyBytes;
        byte[] encrypt;
        String resultEncrypted = "";
        try {
            keyBytes = EncryptStringUtils.MY_PRIVATE_KEY.getBytes(EncryptStringUtils.UNICODE_FORMAT);
            final Key key = new SecretKeySpec(keyBytes, EncryptStringUtils.AES_ALGORITHM_SCHEMA);
            final Cipher c = Cipher.getInstance(EncryptStringUtils.AES_ALGORITHM_SCHEMA);
            c.init(Cipher.ENCRYPT_MODE, key);
            encrypt = c.doFinal(encryptText.getBytes(EncryptStringUtils.UNICODE_FORMAT));
            resultEncrypted = new String(Base64.encodeBase64(encrypt), EncryptStringUtils.UNICODE_FORMAT);
        } catch (final NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            EncryptStringUtils.log.debug("EXCEPTION WHEN ENCRYPT STRING", e);
        }
        return resultEncrypted;
    }
}
