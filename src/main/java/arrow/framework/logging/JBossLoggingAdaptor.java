package arrow.framework.logging;


/**
 * Adaptor for jboss-logging implementation provided by WildFly AS.
 */

public class JBossLoggingAdaptor implements BaseLogger {

    /** The delegate. */
    private final org.jboss.logging.Logger delegate;

    /**
     * Instantiates a new j boss logging adaptor.
     *
     * @param name the name
     */
    // private constructor
    private JBossLoggingAdaptor(final String name) {
        this.delegate = org.jboss.logging.Logger.getLogger(name);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#getName()
     */
    @Override
    public String getName() {
        return this.delegate.getName();
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#isEnabled(arrow.framework.logging.BaseLogger.Level)
     */
    @Override
    public boolean isEnabled(final Level level) {
        return this.delegate.isEnabled(JBossLoggingAdaptor.translate(level));
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#isTraceEnabled()
     */
    @Override
    public boolean isTraceEnabled() {
        return this.delegate.isEnabled(org.jboss.logging.Logger.Level.TRACE);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#trace(java.lang.Object)
     */
    @Override
    public void trace(final Object message) {
        this.delegate.trace(message);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#trace(java.lang.Object, java.lang.Throwable)
     */
    @Override
    public void trace(final Object message, final Throwable ta) {
        this.delegate.trace(message, ta);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#tracev(java.lang.String, java.lang.Object[])
     */
    @Override
    public void tracev(final String format, final Object... params) {
        this.delegate.tracev(format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#tracev(java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void tracev(final Throwable ta, final String format, final Object... params) {
        this.delegate.tracev(ta, format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#tracef(java.lang.String, java.lang.Object[])
     */
    @Override
    public void tracef(final String format, final Object... params) {
        this.delegate.tracef(format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#tracef(java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void tracef(final Throwable ta, final String format, final Object... params) {
        this.delegate.tracef(ta, format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#isDebugEnabled()
     */
    @Override
    public boolean isDebugEnabled() {
        return this.delegate.isEnabled(org.jboss.logging.Logger.Level.DEBUG);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#debug(java.lang.Object)
     */
    @Override
    public void debug(final Object message) {
        this.delegate.debug(message);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#debug(java.lang.Object, java.lang.Throwable)
     */
    @Override
    public void debug(final Object message, final Throwable ta) {
        this.delegate.debug(message, ta);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#debugv(java.lang.String, java.lang.Object[])
     */
    @Override
    public void debugv(final String format, final Object... params) {
        this.delegate.debugv(format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#debugv(java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void debugv(final Throwable ta, final String format, final Object... params) {
        this.delegate.debugv(ta, format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#debugf(java.lang.String, java.lang.Object[])
     */
    @Override
    public void debugf(final String format, final Object... params) {
        this.delegate.debugf(format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#debugf(java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void debugf(final Throwable ta, final String format, final Object... params) {
        this.delegate.debugf(ta, format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#isInfoEnabled()
     */
    @Override
    public boolean isInfoEnabled() {
        return this.delegate.isEnabled(org.jboss.logging.Logger.Level.INFO);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#info(java.lang.Object)
     */
    @Override
    public void info(final Object message) {
        this.delegate.info(message);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#info(java.lang.Object, java.lang.Throwable)
     */
    @Override
    public void info(final Object message, final Throwable ta) {
        this.delegate.info(message, ta);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#infov(java.lang.String, java.lang.Object[])
     */
    @Override
    public void infov(final String format, final Object... params) {
        this.delegate.infov(format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#infov(java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void infov(final Throwable ta, final String format, final Object... params) {
        this.delegate.infov(ta, format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#infof(java.lang.String, java.lang.Object[])
     */
    @Override
    public void infof(final String format, final Object... params) {
        this.delegate.infof(format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#infof(java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void infof(final Throwable ta, final String format, final Object... params) {
        this.delegate.infof(ta, format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#warn(java.lang.Object)
     */
    @Override
    public void warn(final Object message) {
        this.delegate.warn(message);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#warn(java.lang.Object, java.lang.Throwable)
     */
    @Override
    public void warn(final Object message, final Throwable ta) {
        this.delegate.warn(message, ta);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#warnv(java.lang.String, java.lang.Object[])
     */
    @Override
    public void warnv(final String format, final Object... params) {
        this.delegate.warnv(format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#warnv(java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void warnv(final Throwable ta, final String format, final Object... params) {
        this.delegate.warnv(ta, format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#warnf(java.lang.String, java.lang.Object[])
     */
    @Override
    public void warnf(final String format, final Object... params) {
        this.delegate.warnf(format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#warnf(java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void warnf(final Throwable ta, final String format, final Object... params) {
        this.delegate.warnf(ta, format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#error(java.lang.Object)
     */
    @Override
    public void error(final Object message) {
        this.delegate.error(message);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#error(java.lang.Object, java.lang.Throwable)
     */
    @Override
    public void error(final Object message, final Throwable ta) {
        this.delegate.error(message, ta);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#errorv(java.lang.String, java.lang.Object[])
     */
    @Override
    public void errorv(final String format, final Object... params) {
        this.delegate.errorv(format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#errorv(java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void errorv(final Throwable ta, final String format, final Object... params) {
        this.delegate.errorv(ta, format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#errorf(java.lang.String, java.lang.Object[])
     */
    @Override
    public void errorf(final String format, final Object... params) {
        this.delegate.errorf(format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#errorf(java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void errorf(final Throwable ta, final String format, final Object... params) {
        this.delegate.errorf(ta, format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#fatal(java.lang.Object)
     */
    @Override
    public void fatal(final Object message) {
        this.delegate.fatal(message);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#fatal(java.lang.Object, java.lang.Throwable)
     */
    @Override
    public void fatal(final Object message, final Throwable ta) {
        this.delegate.fatal(message, ta);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#fatalv(java.lang.String, java.lang.Object[])
     */
    @Override
    public void fatalv(final String format, final Object... params) {
        this.delegate.fatalv(format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#fatalv(java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void fatalv(final Throwable ta, final String format, final Object... params) {
        this.delegate.fatalv(ta, format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#fatalf(java.lang.String, java.lang.Object[])
     */
    @Override
    public void fatalf(final String format, final Object... params) {
        this.delegate.fatalf(format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#fatalf(java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void fatalf(final Throwable ta, final String format, final Object... params) {
        this.delegate.fatalf(ta, format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#log(arrow.framework.logging.BaseLogger.Level, java.lang.Object)
     */
    @Override
    public void log(final Level level, final Object message) {
        this.delegate.log(JBossLoggingAdaptor.translate(level), message);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger<br />
     * #log(arrow.framework.logging.BaseLogger.Level, java.lang.Object, java.lang.Throwable)
     */
    @Override
    public void log(final Level level, final Object message, final Throwable ta) {
        this.delegate.log(JBossLoggingAdaptor.translate(level), message, ta);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger<br />
     * #logv(arrow.framework.logging.BaseLogger.Level, java.lang.String, java.lang.Object[])
     */
    @Override
    public void logv(final Level level, final String format, final Object... params) {
        this.delegate.logv(JBossLoggingAdaptor.translate(level), format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger<br />
     * #logv(arrow.framework.logging.BaseLogger.Level, java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void logv(final Level level, final Throwable ta, final String format, final Object... params) {
        this.delegate.logv(JBossLoggingAdaptor.translate(level), ta, format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger<br />
     * #logf(arrow.framework.logging.BaseLogger.Level, java.lang.String, java.lang.Object[])
     */
    @Override
    public void logf(final Level level, final String format, final Object... params) {
        this.delegate.logf(JBossLoggingAdaptor.translate(level), format, params);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger<br />
     * #logf(arrow.framework.logging.BaseLogger.Level, java.lang.Throwable, java.lang.String, java.lang.Object[])
     */
    @Override
    public void logf(final Level level, final Throwable ta, final String format, final Object... params) {
        this.delegate.logf(JBossLoggingAdaptor.translate(level), ta, format, params);
    }


    /**
     * Translate from SynLogger.Level to org.jboss.logging.Logger.Level
     *
     * @param level the level
     * @return the org.jboss.logging. logger. level
     */

    private static org.jboss.logging.Logger.Level translate(final Level level) {
        if (level != null) {
            switch (level) {
                case FATAL:
                    return org.jboss.logging.Logger.Level.FATAL;
                case ERROR:
                    return org.jboss.logging.Logger.Level.ERROR;
                case WARN:
                    return org.jboss.logging.Logger.Level.WARN;
                case INFO:
                    return org.jboss.logging.Logger.Level.INFO;
                case DEBUG:
                    return org.jboss.logging.Logger.Level.DEBUG;
                case TRACE:
                    return org.jboss.logging.Logger.Level.TRACE;
                default:
                    return null;
            }
        }
        return null;
    }


    /**
     * Get a Logger instance given the logger name.
     *
     * @param name the logger name
     *
     * @return the logger
     */
    public static JBossLoggingAdaptor getLogger(final String name) {
        return new JBossLoggingAdaptor(name);
    }

    /**
     * Get a Logger instance given the logger name with the given suffix. This will include a logger separator between
     * logger name and suffix.
     *
     * @param name the logger name
     * @param suffix a suffix to append to the logger name
     *
     * @return the logger
     */
    public static JBossLoggingAdaptor getLogger(final String name, final String suffix) {
        return JBossLoggingAdaptor.getLogger((name == null) || (name.length() == 0) ? suffix : name + "." + suffix);
    }

    /**
     * Get a Logger instance given the name of a class. This simply calls create(clazz.getName()).
     *
     * @param clazz the Class whose name will be used as the logger name
     *
     * @return the logger
     */
    public static JBossLoggingAdaptor getLogger(final Class<?> clazz) {
        return JBossLoggingAdaptor.getLogger(clazz.getName());
    }

    /**
     * Get a Logger instance given the name of a class with the given suffix. This will include a logger separator
     * between logger name and suffix
     *
     * @param clazz the Class whose name will be used as the logger name
     * @param suffix a suffix to append to the logger name
     *
     * @return the logger
     */
    public static JBossLoggingAdaptor getLogger(final Class<?> clazz, final String suffix) {
        return JBossLoggingAdaptor.getLogger(clazz.getName(), suffix);
    }


    /* (non-Javadoc)
     * @see arrow.framework.logging.BaseLogger#warnDrawLine()
     */
    @Override
    public void warnDrawLine() {
        this.delegate.warn("=================================================");

    }


    @Override
    public void debug(final String message, final Object... obj) {
        if ((message != null) && !message.endsWith("\n")) {
            this.delegate.debugf(message + "\n", obj);
        } else {
            this.delegate.debugf(message, obj);
        }
    }


    @Override
    public void debugln(final String message, final Object... params) {
        if ((message != null) && !message.endsWith("\n")) {
            this.delegate.debugf(message + "\n", params);
        } else {
            this.delegate.debugf(message, params);
        }
    }
}
