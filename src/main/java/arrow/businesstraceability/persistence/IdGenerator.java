package arrow.businesstraceability.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * The Class IdGenerator.
 */
public class IdGenerator {

    /** The Constant STEP. */
    private static final int STEP = 1;

    /**
     * Gets the next id.
     *
     * @param em the em
     * @param tableName the table name
     * @param idColumn the id column
     * @return the next id
     */
    public static int getNextId(final EntityManager em, final String tableName, final String idColumn) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT MAX(e.pk.").append(idColumn).append(") FROM ").append(tableName).append(" e");
        final Query query = em.createQuery(sb.toString());
        if ((query.getResultList() != null) && (query.getResultList().get(0) != null)) {
            return (int) query.getResultList().get(0) + IdGenerator.STEP;
        }
        else {
            return IdGenerator.STEP;
        }
    }

    /**
     * Gets the next id.
     *
     * @param em the em
     * @param tableClass the table class
     * @param idColumn the id column
     * @return the next id
     */
    public static int getNextId(final EntityManager em, final Class<?> tableClass, final String idColumn) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT MAX(e.pk.").append(idColumn).append(") + 1 FROM ").append(tableClass.getSimpleName())
                .append(" e");
        return (int) em.createQuery(sb.toString()).getResultList().get(0);
    }
}
