package arrow.framework.logging;

import java.io.Serializable;


/**
 * Simple logging interface designed for TH6 portability to other application servers. Delegate to the Application
 * Server's provided logging framework, whatever that maybe.<br />
 * This interface is adapted from org.jboss.logging.BasicLogger interface, with many of less frequently used methods
 * removed<br />
 * For WildFly AS, the provided framework is jboss-logging
 *
 * @author David M. Lloyd, HUGH
 *
 */
public interface BaseLogger extends Serializable {

    /**
     * The Enum Level.
     */
    public enum Level {

        /** The fatal. */
        FATAL,
        /** The error. */
        ERROR,
        /** The warn. */
        WARN,
        /** The info. */
        INFO,
        /** The debug. */
        DEBUG,
        /** The trace. */
        TRACE
    }

    /**
     * Return the name of this logger.
     *
     * @return The name of this logger.
     */
    String getName();


    /**
     * Check to see if the given level is enabled for this logger.
     *
     * @param level the level to check for
     * @return {@code true} if messages may be logged at the given level, {@code false} otherwise
     */
    boolean isEnabled(Level level);

    /**
     * Check to see if the {@code TRACE} level is enabled for this logger.
     *
     * @return {@code true} if messages logged at {@link org.jboss.logging.Logger.Level#TRACE} may be accepted,
     *         {@code false} otherwise
     */
    boolean isTraceEnabled();

    /**
     * Issue a log message with a level of TRACE.
     *
     * @param message the message
     */
    void trace(Object message);

    /**
     * Issue a log message and throwable with a level of TRACE.
     *
     * @param message the message
     * @param ta the throwable
     */
    void trace(Object message, Throwable ta);


    /**
     * Issue a log message with a level of TRACE using {@link java.text.MessageFormat}-style formatting.
     *
     * @param format the message format string
     * @param params the parameters
     */
    void tracev(String format, Object... params);


    /**
     * Issue a log message with a level of TRACE using {@link java.text.MessageFormat}-style formatting.
     *
     * @param ta the throwable
     * @param format the message format string
     * @param params the parameters
     */
    void tracev(Throwable ta, String format, Object... params);


    /**
     * Issue a formatted log message with a level of TRACE.
     *
     * @param format the format string as per {@link String#format(String, Object...)} or resource bundle key therefor
     * @param params the parameters
     */
    void tracef(String format, Object... params);


    /**
     * Issue a formatted log message with a level of TRACE.
     *
     * @param ta the throwable
     * @param format the format string, as per {@link String#format(String, Object...)}
     * @param params the parameters
     */
    void tracef(Throwable ta, String format, Object... params);


    /**
     * Check to see if the {@code DEBUG} level is enabled for this logger.
     *
     * @return {@code true} if messages logged at {@link org.jboss.logging.Logger.Level#DEBUG} may be accepted,
     *         {@code false} otherwise
     */
    boolean isDebugEnabled();

    /**
     * Issue a log message with a level of DEBUG.
     *
     * @param message the message
     */
    void debug(Object message);

    /**
     * Issue a log message and throwable with a level of DEBUG.
     *
     * @param message the message
     * @param ta the throwable
     */
    void debug(Object message, Throwable ta);

    /**
     * Issue a log message and throwable with a level of DEBUG.
     *
     * @param message the message
     * @param ta the throwable
     */
    void debug(final String message, Object... obj);

    void debugln(final String message, Object... params);


    /**
     * Issue a log message with a level of DEBUG using {@link java.text.MessageFormat}-style formatting.
     *
     * @param format the message format string
     * @param params the parameters
     */
    void debugv(String format, Object... params);


    /**
     * Issue a log message with a level of DEBUG using {@link java.text.MessageFormat}-style formatting.
     *
     * @param ta the throwable
     * @param format the message format string
     * @param params the parameters
     */
    void debugv(Throwable ta, String format, Object... params);


    /**
     * Issue a formatted log message with a level of DEBUG.
     *
     * @param format the format string as per {@link String#format(String, Object...)} or resource bundle key therefor
     * @param params the parameters
     */
    void debugf(String format, Object... params);


    /**
     * Issue a formatted log message with a level of DEBUG.
     *
     * @param ta the throwable
     * @param format the format string, as per {@link String#format(String, Object...)}
     * @param params the parameters
     */
    void debugf(Throwable ta, String format, Object... params);


    /**
     * Check to see if the {@code INFO} level is enabled for this logger.
     *
     * @return {@code true} if messages logged at {@link org.jboss.logging.Logger.Level#INFO} may be accepted,
     *         {@code false} otherwise
     */
    boolean isInfoEnabled();

    /**
     * Issue a log message with a level of INFO.
     *
     * @param message the message
     */
    void info(Object message);

    /**
     * Issue a log message and throwable with a level of INFO.
     *
     * @param message the message
     * @param ta the throwable
     */
    void info(Object message, Throwable ta);


    /**
     * Issue a log message with a level of INFO using {@link java.text.MessageFormat}-style formatting.
     *
     * @param format the message format string
     * @param params the parameters
     */
    void infov(String format, Object... params);


    /**
     * Issue a log message with a level of INFO using {@link java.text.MessageFormat}-style formatting.
     *
     * @param ta the throwable
     * @param format the message format string
     * @param params the parameters
     */
    void infov(Throwable ta, String format, Object... params);


    /**
     * Issue a formatted log message with a level of INFO.
     *
     * @param format the format string as per {@link String#format(String, Object...)} or resource bundle key therefor
     * @param params the parameters
     */
    void infof(String format, Object... params);


    /**
     * Issue a formatted log message with a level of INFO.
     *
     * @param ta the throwable
     * @param format the format string, as per {@link String#format(String, Object...)}
     * @param params the parameters
     */
    void infof(Throwable ta, String format, Object... params);


    /**
     * Issue a log message with a level of WARN.
     *
     * @param message the message
     */
    void warn(Object message);

    /**
     * Issue a log message and throwable with a level of WARN.
     *
     * @param message the message
     * @param ta the throwable
     */
    void warn(Object message, Throwable ta);


    /**
     * Issue a log message with a level of WARN using {@link java.text.MessageFormat}-style formatting.
     *
     * @param format the message format string
     * @param params the parameters
     */
    void warnv(String format, Object... params);


    /**
     * Issue a log message with a level of WARN using {@link java.text.MessageFormat}-style formatting.
     *
     * @param ta the throwable
     * @param format the message format string
     * @param params the parameters
     */
    void warnv(Throwable ta, String format, Object... params);


    /**
     * Issue a formatted log message with a level of WARN.
     *
     * @param format the format string as per {@link String#format(String, Object...)} or resource bundle key therefor
     * @param params the parameters
     */
    void warnf(String format, Object... params);


    /**
     * Issue a formatted log message with a level of WARN.
     *
     * @param ta the throwable
     * @param format the format string, as per {@link String#format(String, Object...)}
     * @param params the parameters
     */
    void warnf(Throwable ta, String format, Object... params);


    /**
     * Issue a log message with a level of ERROR.
     *
     * @param message the message
     */
    void error(Object message);

    /**
     * Issue a log message and throwable with a level of ERROR.
     *
     * @param message the message
     * @param ta the throwable
     */
    void error(Object message, Throwable ta);


    /**
     * Issue a log message with a level of ERROR using {@link java.text.MessageFormat}-style formatting.
     *
     * @param format the message format string
     * @param params the parameters
     */
    void errorv(String format, Object... params);


    /**
     * Issue a log message with a level of ERROR using {@link java.text.MessageFormat}-style formatting.
     *
     * @param ta the throwable
     * @param format the message format string
     * @param params the parameters
     */
    void errorv(Throwable ta, String format, Object... params);


    /**
     * Issue a formatted log message with a level of ERROR.
     *
     * @param format the format string as per {@link String#format(String, Object...)} or resource bundle key therefor
     * @param params the parameters
     */
    void errorf(String format, Object... params);


    /**
     * Issue a formatted log message with a level of ERROR.
     *
     * @param ta the throwable
     * @param format the format string, as per {@link String#format(String, Object...)}
     * @param params the parameters
     */
    void errorf(Throwable ta, String format, Object... params);


    /**
     * Issue a log message with a level of FATAL.
     *
     * @param message the message
     */
    void fatal(Object message);

    /**
     * Issue a log message and throwable with a level of FATAL.
     *
     * @param message the message
     * @param ta the throwable
     */
    void fatal(Object message, Throwable ta);


    /**
     * Issue a log message with a level of FATAL using {@link java.text.MessageFormat}-style formatting.
     *
     * @param format the message format string
     * @param params the parameters
     */
    void fatalv(String format, Object... params);


    /**
     * Issue a log message with a level of FATAL using {@link java.text.MessageFormat}-style formatting.
     *
     * @param ta the throwable
     * @param format the message format string
     * @param params the parameters
     */
    void fatalv(Throwable ta, String format, Object... params);


    /**
     * Issue a formatted log message with a level of FATAL.
     *
     * @param format the format string as per {@link String#format(String, Object...)} or resource bundle key therefor
     * @param params the parameters
     */
    void fatalf(String format, Object... params);


    /**
     * Issue a formatted log message with a level of FATAL.
     *
     * @param ta the throwable
     * @param format the format string, as per {@link String#format(String, Object...)}
     * @param params the parameters
     */
    void fatalf(Throwable ta, String format, Object... params);


    /**
     * Log a message at the given level.
     *
     * @param level the level
     * @param message the message
     */
    void log(Level level, Object message);

    /**
     * Issue a log message and throwable at the given log level.
     *
     * @param level the level
     * @param message the message
     * @param ta the throwable
     */
    void log(Level level, Object message, Throwable ta);


    /**
     * Issue a log message at the given log level using {@link java.text.MessageFormat}-style formatting.
     *
     * @param level the level
     * @param format the message format string
     * @param params the parameters
     */
    void logv(Level level, String format, Object... params);


    /**
     * Issue a log message at the given log level using {@link java.text.MessageFormat}-style formatting.
     *
     * @param level the level
     * @param ta the throwable
     * @param format the message format string
     * @param params the parameters
     */
    void logv(Level level, Throwable ta, String format, Object... params);


    /**
     * Issue a formatted log message at the given log level.
     *
     * @param level the level
     * @param format the format string as per {@link String#format(String, Object...)} or resource bundle key therefor
     * @param params the parameters
     */
    void logf(Level level, String format, Object... params);


    /**
     * Issue a formatted log message at the given log level.
     *
     * @param level the level
     * @param ta the throwable
     * @param format the format string, as per {@link String#format(String, Object...)}
     * @param params the parameters
     */
    void logf(Level level, Throwable ta, String format, Object... params);


    /**
     * Warn draw line.
     */
    void warnDrawLine();
}
