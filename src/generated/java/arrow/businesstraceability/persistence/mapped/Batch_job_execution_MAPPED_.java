//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Batch_job_execution_MAPPED.class)
public class Batch_job_execution_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Batch_job_execution_MAPPED, Batch_job_execution_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Batch_job_execution_MAPPED, java.lang.Integer> job_execution_id;
    public static volatile SingularAttribute<Batch_job_execution_MAPPED, java.util.Date> create_time;
    public static volatile SingularAttribute<Batch_job_execution_MAPPED, java.util.Date> end_time;
    public static volatile SingularAttribute<Batch_job_execution_MAPPED, java.lang.String> exit_code;
    public static volatile SingularAttribute<Batch_job_execution_MAPPED, java.lang.String> exit_message;
    public static volatile SingularAttribute<Batch_job_execution_MAPPED, java.lang.String> job_configuration_location;
    public static volatile SingularAttribute<Batch_job_execution_MAPPED, java.lang.Integer> job_instance_id;
    public static volatile SingularAttribute<Batch_job_execution_MAPPED, java.util.Date> last_updated;
    public static volatile SingularAttribute<Batch_job_execution_MAPPED, java.util.Date> start_time;
    public static volatile SingularAttribute<Batch_job_execution_MAPPED, java.lang.String> status;
    public static volatile SingularAttribute<Batch_job_execution_MAPPED, java.lang.Integer> version;
}