package arrow.businesstraceability.util;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Tokenizer.Mode;

import arrow.framework.util.StringUtils;

/**
 * The Class StringUtils. This class contains methods which used for specific business. Meanwhile {
 * {@link arrow.framework.util.StringUtils} contains methods which used for common usage (which is more generic) .From
 * business and action classes, if need call methods from {#link arrow.framework.util.StringUtils}, just write a
 * shortcut method from here.
 */
public class ArrowStringUtils {

    /** The Constant NUMBER_REGEXP. */
    protected static final Pattern NUMBER_REGEXP = Pattern.compile("\\d+");

    /** The Constant EMPTY_STRING. */
    public static final String EMPTY_STRING = "";

    /** The Constant SPACE. */
    public static final String SPACE = " ";

    /** The Constant COMMA. */
    public static final String COMMA = ",";

    /** The Constant MAX_LENGTH_PASSWORD. */
    public static final int MAX_LENGTH_PASSWORD = 10;

    /** The Constant MIN_LENGTH_PASSWORD. */
    public static final int MIN_LENGTH_PASSWORD = 6;

    /**
     * check Numeric.
     *
     * @author lehoangngochan
     * @param str the s
     * @return true if number
     */
    public static boolean isNumeric(final String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        return ArrowStringUtils.NUMBER_REGEXP.matcher(str).matches();
    }

    /**
     * check Alphanumeric.
     *
     * @author lehoangngochan
     * @param str the s
     * @return true if alphanumeric
     */
    public static boolean isAlphanumeric(final String str) {
        return str.matches("[A-Za-z0-9]{1,10}");
    }

    /**
     * Trim.
     *
     * @param value the value
     * @return the string
     */
    public static String trim(final String value) {
        return arrow.framework.util.StringUtils.trim(value);
    }

    /**
     * Id trim.
     *
     * @param value the value
     * @return the string
     */
    public static String idTrim(final String value) {
        return arrow.framework.util.StringUtils.idTrim(value);
    }

    /**
     * Convert kanji to hiragana.
     *
     * @param kanji the kanji
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String convertKanjiToHiragana(final String kanji) throws IOException {
        Tokenizer tokenizer = Tokenizer.builder().mode(Mode.EXTENDED).build();
        StringBuilder output = new StringBuilder();
        List<Token> tokens = tokenizer.tokenize(kanji);
        for (Token token : tokens) {
            output.append(ArrowStringUtils.convertKatakanaToHiragana(
                    token.getReading() != null ? token.getReading() : token.getSurfaceForm()));
        }
        return output.toString();
    }

    /**
     * Convert katakana to hiragana.
     *
     * @param katakana the katakana
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String convertKatakanaToHiragana(final String katakana) throws IOException {
        System.setProperty("kakasi.kanwaDictionary", ".");
        final com.arrow.kakasi_java.Kakasi kakasi = new com.arrow.kakasi_java.Kakasi();
        kakasi.setupKatakanaConverter(com.arrow.kakasi_java.Kakasi.HIRAGANA);
        return kakasi.doString(katakana);
    }

    /**
     * Detect katakana.
     *
     * @param inputText the input text
     * @return true, if successful
     */
    public static boolean detectKatakana(final String inputText) {
        return Character.UnicodeBlock.KATAKANA == Character.UnicodeBlock.of(inputText.charAt(0));
    }

    /**
     * Translate text to hiragana.
     *
     * @param inputText the input text
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String translateTextToHiragana(final String inputText) throws IOException {
        if (StringUtils.isEmpty(inputText) || ArrowStringUtils.detectKatakana(inputText)) {
            return inputText;
        }
        return ArrowStringUtils.convertKanjiToHiragana(inputText);
    }
}
