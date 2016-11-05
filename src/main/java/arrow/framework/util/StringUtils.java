package arrow.framework.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.icu.text.Transliterator;

import arrow.businesstraceability.constant.Constants;


/**
 * The Class StringUtils.
 */
public class StringUtils {

    /** The Constant TLD_PATTERN. */
    private static final String TLD_PATTERN = "(\\.(org|net|mil|edu|com|(co\\.[a-z].)|vn|jp))";

    /** The Constant DOMAIN_PATTERN. */
    private static final String DOMAIN_PATTERN = "[a-z0-9\\-\\.]+(\\.com|org|net|mil|edu|(co\\.[a-z].)|vn|jp)[\\/]*";

    /**
     * Gets the last token.
     *
     * @param originStr the origin str
     * @param delimiter the delimiter
     * @return the last token
     */
    public static String getLastToken(final String originStr, final String delimiter) {
        final String[] tokens = originStr.split("\\.");
        return tokens[tokens.length - 1];
    }

    /**
     * Build string.
     *
     * @param ch the ch
     * @param length the length
     * @return the string
     */
    public static String buildString(final char ch, final int length) {
        StringBuilder result = new StringBuilder();
        while (result.length() < length) {
            result.append(ch);
        }
        return result.toString();
    }

    /**
     * Fill before.
     *
     * @param str the str
     * @param ch the ch
     * @param length the length
     * @return the string
     */
    public static String fillBefore(final String str, final char ch, final int length) {
        if ((str == null) || (str.length() >= length)) {
            return str;
        }
        return StringUtils.buildString(ch, length - str.length()) + str;
    }

    /**
     * For input ' Viet Nam ', the output is 'Viet Nam'.
     *
     * @param str the str
     * @return the string
     */
    public static String removeRedundantSpaces(final String str) {
        if (str == null) {
            return null;
        }

        return str.trim().replaceAll("[ ]{1,}", " ");
    }

    /**
     * Removes all spaces. If the input is ' Viet Nam ', the output is 'VietNam'.
     *
     * @param str the str
     * @return the string
     */
    public static String removeAllSpaces(final String str) {
        if (str == null) {
            return null;
        }

        return str.trim().replaceAll("[ ]{1,}", "");
    }

    /**
     * Compare first string with second string.
     *
     * @param firstString the first string
     * @param secondString the second string
     * @return the int
     */
    public static int compare(final String firstString, final String secondString) {
        if (firstString == null) {
            return (secondString == null) ? 0 : -1;
        }
        return secondString == null ? 1 : firstString.compareTo(secondString);
    }

    /**
     * Null trim.
     *
     * @param str the str
     * @return the string
     */
    public static String nullTrim(final String str) {
        return str == null ? "" : str.trim();
    }

    /**
     * Make upper case first char keep remaing unchanged.
     *
     * @param str the str
     * @return the string
     */
    public static String makeUpperCaseFirstCharKeepRemaingUnchanged(final String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Compare first value with second value.
     *
     * @param firstValue the first value
     * @param secondValue the second value
     * @return true, if successful
     */
    public static boolean areEqual(final String firstValue, final String secondValue) {
        if ((firstValue == null) || (secondValue == null)) {
            return (firstValue == null) && (secondValue == null);
        }

        return firstValue.equals(secondValue);
    }

    /**
     * Checks if is empty.
     *
     * @param str the str
     * @return true, if is empty
     */
    public static boolean isEmpty(final String str) {
        return (str == null) || "".equals(str.trim());
    }

    /**
     * Checks if is not empty.
     *
     * @param str the str
     * @return true, if is not empty
     */
    public static boolean isNotEmpty(final String str) {
        return !StringUtils.isEmpty(str);
    }

    /**
     * Build like value expression.
     *
     * @param value the value
     * @return the string
     */
    public static String buildLikeValueExpression(final String value) {
        if (value == null) {
            return "%%";
        }

        if (value.contains("%")) {
            return value;
        }

        return "%" + value + "%";
    }

    // escapeSql(String) is copied from Apache Common Lang 2.x. The method was removed from Lang 3
    // because it was misleading developers to not use
    // PreparedStatement.
    // All credits go to Apache.

    /**
     * <p>
     * Escapes the characters in a <code>String</code> to be suitable to pass to an SQL query.
     * </p>
     * <p>
     * For example,
     *
     * <pre>
     * statement.executeQuery(&quot;SELECT * FROM MOVIES <br />
     * WHERE TITLE='&quot; + StringEscapeUtils.escapeSql(&quot;McHale's Navy&quot;) + &quot;'&quot;);
     * </pre>
     *
     * </p>
     * <p>
     * At present, this method only turns single-quotes into doubled single-quotes ( <code>"McHale's Navy"</code> =>
     * <code>"McHale''s Navy"</code>). It does not handle the cases of percent (%) or underscore (_) for use in LIKE
     * clauses.
     * </p>
     * see http://www.jguru.com/faq/view.jsp?EID=8881
     *
     * @param str the string to escape, may be null
     * @return a new String, escaped for SQL, <code>null</code> if null string input
     */
    public static String escapeSql(final String str) {
        if (str == null) {
            return null;
        }
        return org.apache.commons.lang3.StringUtils.replace(str, "'", "''");
    }

    /**
     * Trim.
     *
     * @param str the str
     * @return the string
     */
    public static String trim(final String str) {
        return org.apache.commons.lang3.StringUtils.trim(str);
    }

    /**
     * Id trim.
     *
     * @param str the str
     * @return the string
     */
    public static String idTrim(final String str) {
        return org.apache.commons.lang3.StringUtils.isEmpty(str) ? null : org.apache.commons.lang3.StringUtils
            .trim(str).toUpperCase();
    }

    /**
     * Gets the java package name.
     *
     * @param str the str
     * @return the java package name
     */
    public static String getJavaPackageName(final String str) {
        return str.trim().toLowerCase().replace(File.pathSeparator, ".");
    }

    /**
     * Make upper case first char only.
     *
     * @param str the str
     * @return the string
     */
    public static String makeUpperCaseFirstCharOnly(final String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * Make upper camel case for dash delimited string.
     *
     * @param str the str
     * @return the string
     */
    public static String makeUpperCamelCaseForDashDelimitedString(String str) {
        str = str.trim().toLowerCase();

        final String[] fragments = str.split("\\-");
        // String result = "";
        StringBuilder result = new StringBuilder();

        for (final String fragment : fragments) {
            result.append(StringUtils.makeUpperCaseFirstCharOnly(fragment));
        }

        return result.toString();
    }

    /**
     * Replace under score by space by upper case first char.
     *
     * @param str the str
     * @return the string
     */
    public static String replaceUnderscoreBySpaceByUpperCaseFirstChar(final String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        } else {
            final StringTokenizer tokenizer = new StringTokenizer(str, "_");
            final StringBuilder strBuilder = new StringBuilder();
            while (tokenizer.hasMoreTokens()) {
                strBuilder.append(StringUtils.makeUpperCaseFirstCharOnly(tokenizer.nextToken()) + " ");
            }
            strBuilder.deleteCharAt(strBuilder.length() - 1);
            return strBuilder.toString();
        }
    }

    /**
     * Build like with pattern.
     *
     * @param str the str
     * @return the string
     */
    public static String likePattern(final String str) {
        return "%"
            + StringUtils.nullTrim(str).toUpperCase().replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_")
            + "%";
    }

    /** The Constant BIG. */
    private static final double BIG = 1e30; // Double.MAX_VALUE/MIN_VALUE would cause SQL 302 error

    /** The Constant SMALL. */
    private static final double SMALL = 1e-10; // small positive value. to cater for the case where

    /** The Constant EMPTY_STRING. */
    // user do not
    public static final String EMPTY_STRING = org.apache.commons.lang3.StringUtils.EMPTY;

    /**
     * parse string to a pair of integers for range filter s = 123 -> return {123, 123} s = > 123 -> return {123,
     * Integer.MAX_INTEGER} s = < 123 -> return {Integer.MIN_INTEGER, 123} s = 123 < 456 return {123, 456}
     *
     * @param str the str
     * @return the double[]
     */
    public static Double[] parseNumberRange(String str) {
        // enter a range

        try {
            if (StringUtils.isEmpty(str)) {
                return new Double[] {};
            }

            final Double[] range = new Double[2];
            str = str.trim();

            if (str.startsWith(">")) {
                range[0] = Double.parseDouble(str.substring(1).trim());
                range[1] = StringUtils.BIG;
            } else if (str.startsWith("<")) {
                range[0] = -StringUtils.BIG;
                range[1] = Double.parseDouble(str.substring(1).trim());
            } else if (str.contains("<")) {
                final String[] splits = str.split("<");
                range[0] = Double.parseDouble(splits[0].trim());
                range[1] = Double.parseDouble(splits[1].trim());
            } else {
                final double d = Double.parseDouble(str);
                range[0] = d - StringUtils.SMALL;
                range[1] = d + StringUtils.SMALL;
            }

            return range;
        } catch (final NumberFormatException e) {
            return new Double[] {};
        }
    }

    /**
     * Write to file.
     *
     * @param str the str
     * @param file the file
     */
    public static void writeFile(final String str, final File file) {
        BufferedWriter out = null;
        try {
            // out = new BufferedWriter(new FileWriter(file));
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Constants.UTF8_ENCODING));
            out.write(str);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (final IOException ignored) {
                    System.out.println(ignored.getMessage());
                }
            }
        }
    }

    /**
     * Equals ignore case.
     *
     * @param expect the expect
     * @param actual the actual
     * @return true, if successful
     */
    public static boolean equalsIgnoreCase(final String expect, final String actual) {
        return org.apache.commons.lang3.StringUtils.equalsIgnoreCase(expect, actual);
    }

    /**
     * remove http, https ,www out of Url .
     *
     * @param input the input
     * @return the string
     */
    public static String removeUrlToken(final String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }
        String output = input.toLowerCase().replaceAll("https://", "").replaceAll("http://", "");

        if (output.startsWith("www")) {
            output = output.substring(output.indexOf(".") + 1, output.length());
        }
        return output;
    }

    /**
     * Replace special character.
     *
     * @param input the input
     * @return the string
     */
    public static String replaceSpecialCharacter(final String input) {

        if (input.isEmpty()) {
            return StringUtils.EMPTY_STRING;
        }

        return input.replaceAll("\\\\", "\\\\\\\\").replaceAll("!", "\\\\!").replaceAll("\\(", "\\\\(")
            .replaceAll("\\)", "\\\\)").replaceAll("\\{", "\\\\{").replaceAll("\\}", "\\\\}")
            .replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]").replaceAll("\\^", "\\\\^")
            .replaceAll("\\*", "\\\\*").replaceAll("\\?", "\\\\?").replaceAll("\\:", "\\\\:")
            .replaceAll("\\/", "\\\\/").replaceAll("\\\"", "\\\\\"");
    }

    /**
     * Extract domain. for example: http://arrow-tech.vn => arrow-tech
     *
     * @param value the value
     * @return the domain
     */
    public static String extractDomain(final String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        Pattern pattern = Pattern.compile(StringUtils.DOMAIN_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            String out = value.substring(matcher.start(0), matcher.end(0));
            if (out.startsWith("www.")) {
                out = out.substring(4);
            }
            out = out.replaceFirst(StringUtils.TLD_PATTERN, "");
            out = out.replaceAll("\\/", "");
            return out;
        }
        return value;
    }

    /**
     * Extract domain extension. http://arrow-tech.vn => vn
     *
     * @param value the value
     * @return the string
     */
    public static String extractDomainExt(final String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        String domainPattern = StringUtils.TLD_PATTERN + "[\\/]*";
        Pattern pattern = Pattern.compile(domainPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            String out = value.substring(matcher.start(2), matcher.end(2));
            out = out.replaceAll("\\/", "");
            return out;
        }
        return value;
    }

    /**
     * Convert to half width.
     *
     * @param input the input
     * @return the string
     */
    public static String convertToHalfWidth(final String input) {
        Transliterator transliterator = Transliterator.getInstance("Fullwidth-Halfwidth");
        return transliterator.transliterate(input);
    }

    /**
     * Convert to katakana.
     *
     * @param input the input
     * @return the string
     */
    public static String convertToKatakana(final String input) {
        Transliterator transliterator = Transliterator.getInstance("Katakana");
        String output = transliterator.transliterate(input);
        char[] specialChars = new char[] {'́', '̌', '̀', ' '};
        for (char c : specialChars) {
            output = org.apache.commons.lang3.StringUtils.remove(output, c);
        }
        return output;
    }

    /**
     * Convert to half width kata.
     *
     * @param input the input
     * @return the string
     */
    public static String convertToHalfWidthKata(final String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }
        return StringUtils.convertToHalfWidth(StringUtils.convertToKatakana(input));
    }

    /**
     * Return domain only. for example: http://arrow-tech.vn => arrow-tech.vn
     *
     * @param url the url
     * @return the string
     */
    public static String cleanupUrl(final String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        String out = StringUtils.removeUrlToken(url);
        if (out.indexOf("/") >= 0) {
            out = out.substring(0, out.indexOf("/"));
        }

        return out;
    }

    /**
     * Check string is integer type.
     *
     * @param value
     * @return
     */
    public static boolean isIntegerValue(final String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /** The Constant ENCODING_ISO_8859_1. */
    public static final String ENCODING_ISO_8859_1 = "ISO-8859-1";

    /** The Constant ENCODING_UTF_8. */
    public static final String ENCODING_UTF_8 = "UTF-8";

}
