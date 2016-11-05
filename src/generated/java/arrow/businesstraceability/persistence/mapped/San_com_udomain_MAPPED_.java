//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.San_com_info;

@StaticMetamodel(San_com_udomain_MAPPED.class)
public class San_com_udomain_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<San_com_udomain_MAPPED, San_com_udomain_MAPPED.Pk> pk;
    public static volatile SingularAttribute<San_com_udomain_MAPPED, java.lang.Integer> id_urldomain;
    public static volatile SingularAttribute<San_com_udomain_MAPPED, java.util.Date> date_latest_exchange;
    public static volatile SingularAttribute<San_com_udomain_MAPPED, java.lang.Integer> id_company;
    public static volatile SingularAttribute<San_com_udomain_MAPPED, java.lang.Integer> n_dupinfo;
    public static volatile SingularAttribute<San_com_udomain_MAPPED, java.lang.String> url_domain;
    public static volatile SingularAttribute<San_com_udomain_MAPPED, San_com_info> san_com_info;
}