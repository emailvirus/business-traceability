package arrow.framework.persistence;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class StatementList.
 */
public class StatementList {

    /** The statements. */
    private final List<String> statements = new ArrayList<String>();

    /** The prefix. */
    private String prefix;

    /**
     * Sets the prefix.
     *
     * @param prefix the new prefix
     */
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    /**
     * Gets the prefix.
     *
     * @return the prefix
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * Instantiates a new statement list.
     */
    public StatementList() {}

    /**
     * Instantiates a new statement list.
     *
     * @param prefix the prefix
     */
    public StatementList(final String prefix) {
        this.setPrefix(prefix);
    }

    /**
     * Builds the.
     *
     * @return the string
     */
    public String build() {
        final StringBuilder builder = new StringBuilder();
        if ((this.prefix != null) && (this.statements.size() > 0)) {
            builder.append(this.prefix);
        }

        for (int i = 0; i < this.statements.size(); i++) {
            if (i > 0) {
                builder.append(this.separator);
            }
            builder.append(this.statements.get(i));
        }

        return builder.toString();
    }

    /** The separator. */
    private String separator = " ";

    /**
     * Sets the separator.
     *
     * @param separator the new separator
     */
    public void setSeparator(final String separator) {
        this.separator = separator;
    }

    /**
     * Gets the separator.
     *
     * @return the separator
     */
    public String getSeparator() {
        return this.separator;
    }

    /**
     * Adds the.
     *
     * @param statement the statement
     */
    public void add(final String statement) {
        if ((statement == null) || (statement.trim().length() == 0)) {
            throw new IllegalArgumentException("Argument is empty");
        }
        this.statements.add(statement);
    }
}
