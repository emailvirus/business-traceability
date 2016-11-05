package arrow.framework.persistence.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The Class Condition.
 */
public class Condition {

    /** The condition. */
    private final String condition;

    /** The parameters. */
    private List<Object> parameters;

    /**
     * Instantiates a new condition.
     */
    private Condition() {
        this.condition = "";
        this.setParameters(new ArrayList<Object>());
    }

    /**
     * Instantiates a new condition.
     *
     * @param condition the condition
     * @param params the params
     */
    public Condition(final String condition, final List<Object> params) {
        this.condition = condition;
        this.setParameters(params == null ? new ArrayList<Object>() : params);
        this.validate();
    }

    /**
     * Instantiates a new condition.
     *
     * @param condition the condition
     * @param params the params
     */
    public Condition(final String condition, final Object... params) {
        this(condition, Arrays.asList(params));
    }

    /**
     * Gets the parameters.
     *
     * @return the parameters
     */
    public List<Object> getParameters() {
        return this.parameters;
    }

    /**
     * Builds the.
     *
     * @return the string
     */
    public String build() {
        return "(" + this.condition + ")";
    }

    /**
     * Validate.
     */
    private void validate() {
        if (this.condition != null) {
            final int qmCount = this.countUnquotedQuestionMarks(this.condition);
            final int paramCount = this.getParameters() == null ? 0 : this.getParameters().size();
            if (qmCount != paramCount) {
                throw new IllegalStateException("Number of ? does not equal number of parameters");
            }
        }
    }

    /**
     * Count unquoted question marks.
     *
     * @param string the string
     * @return the int
     */
    private int countUnquotedQuestionMarks(final String string) {
        boolean search = true;
        int count = 0;

        for (int i = 0; i < string.length(); i++) {
            final char charAt = string.charAt(i);
            if (charAt == '\'') {
                search = !search;
            }
            if (search && (charAt == '?')) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if is empty.
     *
     * @return true, if is empty
     */
    public boolean isEmpty() {
        return ((this.getParameters() == null) || (this.getParameters().size() == 0))
                && (((this.condition != null) && (this.condition.trim().length() == 0)));
    }

    /**
     * Sets the parameters.
     *
     * @param parameters the new parameters
     */
    public void setParameters(final List<Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * The Class Junction.
     */
    public static class Junction extends Condition {

        /** The joiner. */
        private String joiner;

        /** The conditions. */
        private final List<Condition> conditions = new ArrayList<Condition>();

        /* (non-Javadoc)
         * @see arrow.framework.persistence.util.Condition#isEmpty()
         */
        @Override
        public boolean isEmpty() {
            for (final Condition condition : this.conditions) {
                if (!condition.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        /* (non-Javadoc)
         * @see arrow.framework.persistence.util.Condition#build()
         */
        @Override
        public String build() {
            final StringBuffer buffer = new StringBuffer();

            for (final Condition condition : this.conditions) {
                if (!condition.isEmpty()) {
                    if (buffer.length() != 0) {
                        buffer.append(this.getJoiner());
                    }
                    buffer.append(condition.build());
                }
            }

            return "(" + buffer.toString() + ")";
        }

        /**
         * Adds the.
         *
         * @param condition the condition
         * @return the junction
         */
        public Junction add(final Condition condition) {
            if (condition != null) {
                this.conditions.add(condition);
                this.getParameters().addAll(condition.getParameters());
            }

            return this;
        }

        /**
         * Adds the.
         *
         * @param condition the condition
         * @param params the params
         * @return the junction
         */
        public Junction add(final String condition, final Object... params) {
            return this.add(new Condition(condition, params));
        }

        /**
         * Gets the joiner.
         *
         * @return the joiner
         */
        public String getJoiner() {
            return this.joiner;
        }

        /**
         * Sets the joiner.
         *
         * @param joiner the new joiner
         */
        public void setJoiner(final String joiner) {
            this.joiner = joiner;
        }
    }

    /**
     * The Class Conjunction.
     */
    public static class Conjunction extends Junction {

        /**
         * Instantiates a new conjunction.
         */
        public Conjunction() {
            this.setJoiner(" AND ");
        }
    }

    /**
     * The Class Disjunction.
     */
    public static class Disjunction extends Junction {

        /**
         * Instantiates a new disjunction.
         */
        public Disjunction() {
            this.setJoiner(" OR ");
        }
    }
}
