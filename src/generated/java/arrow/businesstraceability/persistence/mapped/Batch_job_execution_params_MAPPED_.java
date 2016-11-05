//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Batch_job_execution_params_MAPPED.class)
public class Batch_job_execution_params_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Batch_job_execution_params_MAPPED, Batch_job_execution_params_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Batch_job_execution_params_MAPPED, java.lang.Integer> job_execution_id;
    public static volatile SingularAttribute<Batch_job_execution_params_MAPPED, java.util.Date> date_val;
    public static volatile SingularAttribute<Batch_job_execution_params_MAPPED, java.lang.Double> double_val;
    public static volatile SingularAttribute<Batch_job_execution_params_MAPPED, java.lang.String> key_name;
    public static volatile SingularAttribute<Batch_job_execution_params_MAPPED, java.lang.Integer> long_val;
    public static volatile SingularAttribute<Batch_job_execution_params_MAPPED, java.lang.String> string_val;
    public static volatile SingularAttribute<Batch_job_execution_params_MAPPED, java.lang.String> type_cd;
}